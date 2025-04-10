package moe.orangemc.osu.coldfish.tournament.state.early;

import moe.orangemc.osu.al1s.api.mutltiplayer.MultiplayerTeam;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;
import moe.orangemc.osu.coldfish.tournament.state.strategy.OperationDecisionWaitress;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RollWaitress implements StateWaitress {
    public static final RollWaitress INSTANCE = new RollWaitress();

    private final Map<Room, Integer> rollDelta = new HashMap<>();
    private final Set<Room> completedRoom = new HashSet<>();

    private RollWaitress() {}

    @Override
    public void engage(Room room) {

    }

    @Override
    public void timeout(Room room) {

    }

    @Override
    public int getTimeout(Room room) {
        return room.getSession().getForfeitTime() * 60;
    }

    public void captureRoll(Room room, User roller, int value) {
        if (completedRoom.contains(room) || room.notStateCurrent(this)) {
            return;
        }

        int finalPoint = value *
                (room.getPlayerTeam(roller) == MultiplayerTeam.RED ? 1 : -1);

        if (rollDelta.containsKey(room)) {
            if (rollDelta.get(room) * finalPoint > 0) { // same team rolling.
                return;
            }

            completedRoom.add(room);
            room.transitState((state, actor) -> {
                state.pop();

                MultiplayerTeam rollWinner = rollDelta.get(room) > 0 ? MultiplayerTeam.RED : MultiplayerTeam.BLUE;
                state.push(OperationDecisionWaitress.INSTANCE);
                actor.offer(rollWinner);
            });
        }

        rollDelta.compute(room, (key, val) -> val == null ? finalPoint : val + finalPoint);
    }
}
