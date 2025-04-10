package moe.orangemc.osu.coldfish.tournament.state.strategy;

import moe.orangemc.osu.al1s.api.mutltiplayer.MultiplayerTeam;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;

public class OperationDecisionWaitress implements StateWaitress {
    public static final OperationDecisionWaitress INSTANCE = new OperationDecisionWaitress();

    private OperationDecisionWaitress() {}

    @Override
    public void engage(Room room) {

    }

    @Override
    public void timeout(Room room) {

    }

    public void captureCommandIssue(Room room, User performer, boolean actFirst) {
        if (room.notStateCurrent(this) || room.notActiveTeam(room.getPlayerTeam(performer))) {
            return;
        }

        room.transitState((state, actor) -> {
            MultiplayerTeam lastTeam = actor.poll();
            assert lastTeam != null;

            MultiplayerTeam firstActor = actFirst ? lastTeam : lastTeam.getOpposite();

            for (boolean useWinner : room.getSession().getInitialOrders()) {
                MultiplayerTeam current = useWinner ? firstActor : firstActor.getOpposite();
                actor.offer(current);
            }
        });
    }
}
