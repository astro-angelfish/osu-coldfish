package moe.orangemc.osu.coldfish.tournament.state.strategy;

import moe.orangemc.osu.al1s.api.mutltiplayer.MultiplayerTeam;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.Room;

public class ProtectWaitress extends PickingWaitress {
    public static final ProtectWaitress INSTANCE = new ProtectWaitress();

    private ProtectWaitress() {}

    public void captureCommandIssue(Room room, User issuer, String mapId) {
        if (room.notStateCurrent(this) || room.notActiveTeam(room.getPlayerTeam(issuer))) {
            return;
        }

        room.getMapPool().markProtect(mapId);
        room.setCurrentBeatmap(room.getMapPool().getBeatmap(mapId));

        room.transitState((state, actors) -> {
            MultiplayerTeam lastTeam = actors.poll();
            assert lastTeam != null;

            if (room.getActionSwitch()) {
                state.pop();
                assert state.peek() instanceof BanWaitress;

                // we do not re-initiate actor series here, they should be already configured during room initialization.
            }
        });
    }
}
