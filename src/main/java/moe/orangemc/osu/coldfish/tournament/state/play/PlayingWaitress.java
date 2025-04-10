package moe.orangemc.osu.coldfish.tournament.state.play;

import moe.orangemc.osu.al1s.api.event.multiplayer.MatchRoomEvent;
import moe.orangemc.osu.al1s.api.event.multiplayer.game.PlayerFinishPlayEvent;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;

import java.util.HashMap;
import java.util.Map;

public class PlayingWaitress implements StateWaitress {
    public static final PlayingWaitress INSTANCE = new PlayingWaitress();

    private final Map<Room, Map<User,Integer>> playerScoreDeltas = new HashMap<>();

    private PlayingWaitress() {}

    @Override
    public void engage(Room room) {
        playerScoreDeltas.put(room, new HashMap<>());
        room.getMatchRoom().start(7);
    }

    @Override
    public void timeout(Room room) {

    }

    public void captureEvent(Room room, MatchRoomEvent evt) {
        if (!(evt instanceof PlayerFinishPlayEvent playerFinishPlayEvent)) {
            return;
        }
        playerScoreDeltas.get(room).put(playerFinishPlayEvent.getUser()
                ,playerFinishPlayEvent.getScore().score());
    }
}
