package moe.orangemc.osu.coldfish.command.player;

import moe.orangemc.osu.al1s.api.chat.OsuChannel;
import moe.orangemc.osu.al1s.api.chat.command.Command;
import moe.orangemc.osu.al1s.api.chat.command.CommandBase;
import moe.orangemc.osu.al1s.api.mutltiplayer.MatchRoom;
import moe.orangemc.osu.al1s.inject.api.Inject;
import moe.orangemc.osu.coldfish.tournament.ColdfishRoomManager;
import net.dv8tion.jda.api.entities.User;

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
    }
}
