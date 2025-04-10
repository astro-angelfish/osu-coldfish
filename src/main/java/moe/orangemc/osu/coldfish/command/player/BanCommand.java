package moe.orangemc.osu.coldfish.command.player;

import moe.orangemc.osu.al1s.api.chat.OsuChannel;
import moe.orangemc.osu.al1s.api.chat.command.Command;
import moe.orangemc.osu.al1s.api.chat.command.CommandBase;
import moe.orangemc.osu.al1s.api.mutltiplayer.MatchRoom;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.al1s.inject.api.Inject;
import moe.orangemc.osu.coldfish.tournament.ColdfishRoomManager;
import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.strategy.BanWaitress;

public class BanCommand implements CommandBase {
    @Inject
    private ColdfishRoomManager roomManager;

    public static final BanCommand INSTANCE = new BanCommand();

    private BanCommand() {}

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "Ban a map, preventing them from getting picked";
    }

    @Override
    public String getUsage() {
        return "<Map id>";
    }

    @Command
    public void ban(User issuer, OsuChannel source, String mapId) {
        if (!(source instanceof MatchRoom room)) {
            source.sendMessage("This command can only be used in a match room.");
            return;
        }

        Room tournamentRoom = roomManager.findRoom(room);
        if (tournamentRoom == null) {
            source.sendMessage("This room is not tracked as a tournament room.");
            return;
        }

        BanWaitress.INSTANCE.captureCommandIssue(tournamentRoom, issuer, mapId);
    }
}
