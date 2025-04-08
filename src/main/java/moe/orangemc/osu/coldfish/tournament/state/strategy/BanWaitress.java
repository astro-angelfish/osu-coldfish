package moe.orangemc.osu.coldfish.tournament.state.strategy;

import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.Room;

public class BanWaitress extends PickingWaitress {
    public static final BanWaitress INSTANCE = new BanWaitress();

    protected BanWaitress() {
        super();
    }

    public void captureCommandIssue(Room room, User performer, String mapId) {
        if (!room.isStateCurrent(this) || !room.isActiveTeam(room.getPlayerTeam(performer))) {
            return;
        }

        room.getMapPool().markBan(mapId);
    }
}
