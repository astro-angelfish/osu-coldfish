package moe.orangemc.osu.coldfish.tournament.state.early;

import moe.orangemc.osu.al1s.api.event.multiplayer.MatchRoomEvent;
import moe.orangemc.osu.al1s.api.mutltiplayer.MultiplayerTeam;
import moe.orangemc.osu.coldfish.event.MatchRollEvent;
import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;

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
    public void captureEvent(Room room, MatchRoomEvent evt) {
        if (!(evt instanceof MatchRollEvent rollEvt)) {
            return;
        }

        if (completedRoom.contains(room)) {
            return;
        }

        int finalPoint = rollEvt.getRoll() *
                (room.getPlayerTeam(rollEvt.getUser()) == MultiplayerTeam.RED ? 1 : -1);

        if (rollDelta.containsKey(room)) {
            if (rollDelta.get(room) * finalPoint > 0) { // same team rolling.
                return;
            }

            completedRoom.add(room);
            room.transitState((state, actor) -> {
                state.pop();

                if (rollDelta.get(room) > 0) {
                    actor.add(MultiplayerTeam.RED);
                    actor.add(MultiplayerTeam.BLUE);
                } else {
                    actor.add(MultiplayerTeam.BLUE);
                    actor.add(MultiplayerTeam.RED);
                }
            });
        }

        rollDelta.compute(room, (key, val) -> val == null ? finalPoint : val + finalPoint);
    }
}
