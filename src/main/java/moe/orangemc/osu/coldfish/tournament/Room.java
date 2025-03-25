package moe.orangemc.osu.coldfish.tournament;

import moe.orangemc.osu.al1s.api.mutltiplayer.MatchRoom;
import moe.orangemc.osu.al1s.api.mutltiplayer.MultiplayerTeam;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;

import java.util.*;
import java.util.function.BiConsumer;

public class Room {
    private final Session session;

    private final Map<User, MultiplayerTeam> players = new HashMap<>();
    private MatchRoom room = null;

    // ok this is basically a state machine.
    // but we use a stack, because we do always return to some previous state
    private final Stack<StateWaitress> stateStack = new Stack<>();

    // and we make active team as a queue
    // some tournament rule may have special orders of teams.
    // usually changes together with TournamentRoom#stateStack.
    // but we might occasionally insert to the header when special events occurs.
    private final Queue<MultiplayerTeam> activeTeam = new LinkedList<>();

    private final Map<MultiplayerTeam, Integer> scores = new HashMap<>();

    private final int roundsToWin;
    private final long startTime;

    public Room(Session session, int roundsToWin, long startTime) {
        this.session = session;

        this.roundsToWin = roundsToWin;
        this.startTime = startTime;
    }

    public void initiateMatchRoom() {

    }

    public MatchRoom getMatchRoom() {
        return room;
    }

    public Session getSession() {
        return session;
    }

    public MultiplayerTeam getPlayerTeam(User user) {
        return players.get(user);
    }
    public Set<User> getParticipants() {
        return players.keySet();
    }

    public void transitState(BiConsumer<Stack<StateWaitress>, Queue<MultiplayerTeam>> state) {
        state.accept(stateStack, activeTeam);
        stateStack.peek().engage(this);
    }
}
