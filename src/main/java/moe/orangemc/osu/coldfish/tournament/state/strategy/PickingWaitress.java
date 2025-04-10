package moe.orangemc.osu.coldfish.tournament.state.strategy;

import moe.orangemc.osu.al1s.api.mutltiplayer.MultiplayerTeam;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;
import moe.orangemc.osu.coldfish.tournament.state.play.PrepareWaitress;

public class PickingWaitress implements StateWaitress {
    public static final PickingWaitress INSTANCE = new PickingWaitress();

    protected PickingWaitress() {}

    @Override
    public void engage(Room room) {

    }

    @Override
    public void timeout(Room room) {

    }

    public void captureCommandIssue(Room room, User performer, String mapId) {
        if (!room.isStateCurrent(this) || !room.isActiveTeam(room.getPlayerTeam(performer))) {
            return;
        }

        room.getMapPool().markPick(mapId);
        room.setCurrentBeatmap(room.getMapPool().getBeatmap(mapId));

        room.transitState((state, actors) -> {
            MultiplayerTeam lastTeam = actors.poll();
            assert lastTeam != null;

            if (room.getActionSwitch()) {
                if (state.size() > 1) { // infinite picking and playing, until tiebreaker.
                    state.pop();
                }

                boolean nextBp = state.peek() instanceof BanWaitress || state.peek() instanceof ProtectWaitress;
                MultiplayerTeam nextTeam = nextBp ?
                        (room.getSession().doRevertInitialPickOrder() ? lastTeam.getOpposite() : lastTeam) : // initiate secondary ban/protect. they should be already put on state stack.
                                                                                                             // TODO: some tournaments might re-initiate action series base on the score. we'll create a hook for those extension.
                        lastTeam.getOpposite();

                actors.offer(nextTeam);
                actors.offer(nextTeam.getOpposite());
            }

            state.push(PrepareWaitress.INSTANCE);
        });
    }
}
