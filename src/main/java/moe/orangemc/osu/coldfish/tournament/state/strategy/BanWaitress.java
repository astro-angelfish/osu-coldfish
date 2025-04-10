package moe.orangemc.osu.coldfish.tournament.state.strategy;

import moe.orangemc.osu.al1s.api.mutltiplayer.MultiplayerTeam;
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
        room.setCurrentBeatmap(room.getMapPool().getBeatmap(mapId));

        room.transitState((state, actors) -> {
            MultiplayerTeam lastTeam = actors.poll();
            assert lastTeam != null;

            if (room.getActionSwitch()) {
                state.pop();
                assert state.peek() instanceof PickingWaitress;

                MultiplayerTeam next = room.getSession().doRevertInitialPickOrder() ? lastTeam.getOpposite() : lastTeam;
                actors.offer(next);
                actors.offer(next.getOpposite());
            }
        });
    }

    @Override
    public int getTimeout(Room room) {
        return room.getSession().getBanTime() * 60;
    }
}
