package moe.orangemc.osu.coldfish.command.player;

import moe.orangemc.osu.al1s.api.chat.OsuChannel;
import moe.orangemc.osu.al1s.api.chat.command.Command;
import moe.orangemc.osu.al1s.api.chat.command.CommandBase;
import moe.orangemc.osu.al1s.api.mutltiplayer.MatchRoom;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.al1s.inject.api.Inject;
import moe.orangemc.osu.coldfish.tournament.ColdfishRoomManager;
import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.strategy.PickingWaitress;

public class PickCommand implements CommandBase {
    @Inject
    private ColdfishRoomManager roomManager;

    @Override
    public String getName() {
        return "pick";
    }

    @Override
    public String getDescription() {
        return "Pick a map from the map pool";
    }

    @Override
    public String getUsage() {
        return "<Map id>";
    }

    @Command
    public void pick(User issuer, OsuChannel source, String mapId) {
        if (!(source instanceof MatchRoom room)) {
            source.sendMessage("This command can only be used in a match room.");
            return;
        }

        Room tournamentRoom = roomManager.findRoom(room);
        if (tournamentRoom == null) {
            source.sendMessage("This room is not tracked as a tournament room.");
            return;
        }

        PickingWaitress.INSTANCE.captureCommandIssue(tournamentRoom, issuer, mapId);
    }
}
