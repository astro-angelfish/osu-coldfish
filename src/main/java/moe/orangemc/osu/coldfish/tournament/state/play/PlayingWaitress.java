package moe.orangemc.osu.coldfish.tournament.state.play;

import moe.orangemc.osu.al1s.api.ruleset.PlayScore;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;

import java.util.HashMap;
import java.util.Map;

public class PlayingWaitress implements StateWaitress {
    public static final PlayingWaitress INSTANCE = new PlayingWaitress();

    private final Map<Room, Map<User,PlayScore>> playerScoreDeltas = new HashMap<>();

    private PlayingWaitress() {}

    @Override
    public void engage(Room room) {
        playerScoreDeltas.put(room, new HashMap<>());
        room.getMatchRoom().start(7);
    }

    @Override
    public void timeout(Room room) {

    }

    public void capturePlayingScores(Room room , User Player, PlayScore Score) {
        if (room.notStateCurrent(this)) {
            return;
        }
        playerScoreDeltas.get(room).put(Player,Score);
        int playerNumber = room.getSession().getTeamSize()*2;
        if (playerScoreDeltas.get(room).size() == playerNumber) {
            int teamRedScore = 0;
            int teamBlueScore = 0;
            for (Map.Entry<User,PlayScore> playersScoreEntry : playerScoreDeltas.get(room).entrySet()) {
                switch (room.getPlayerTeam(playersScoreEntry.getKey())){
                    case BLUE:
                        teamBlueScore += playersScoreEntry.getValue().score();
                        break;
                    case RED:
                        teamRedScore += playersScoreEntry.getValue().score();
                }
            }
            if (teamBlueScore > teamRedScore) {
                room.getMatchRoom().sendMessage("blue win");
            }else {
                room.getMatchRoom().sendMessage("red win");
            }
            room.transitState((state, actors) -> {
                state.pop();
            });
        }
    }
}
