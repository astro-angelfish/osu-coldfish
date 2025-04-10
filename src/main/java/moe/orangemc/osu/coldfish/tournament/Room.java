package moe.orangemc.osu.coldfish.tournament;

import moe.orangemc.osu.al1s.api.beatmap.Beatmap;
import moe.orangemc.osu.al1s.api.mutltiplayer.MatchRoom;
import moe.orangemc.osu.al1s.api.mutltiplayer.MultiplayerTeam;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.map.ActiveMapPool;
import moe.orangemc.osu.coldfish.tournament.map.MapPool;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;
import moe.orangemc.osu.coldfish.tournament.state.early.RollWaitress;
import moe.orangemc.osu.coldfish.tournament.state.strategy.BanWaitress;
import moe.orangemc.osu.coldfish.tournament.state.strategy.PickingWaitress;
import moe.orangemc.osu.coldfish.tournament.state.strategy.ProtectWaitress;

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

    private final ActiveMapPool mapPool;

    private boolean actionSwitch = false;

    public Room(Session session, int roundsToWin, long startTime, MapPool mapPool) {
        this.session = session;

        this.roundsToWin = roundsToWin;
        this.startTime = startTime;
        this.mapPool = new ActiveMapPool(mapPool);
    }

    public void initiateMatchRoom() {
        initiateRoomState();
    }

    private void initiateRoomState() {
        this.stateStack.clear();

        int remainBanChoices = session.getBanChoices();
        int remainProtectChoices = session.getProtectChoices();

        this.stateStack.push(PickingWaitress.INSTANCE);
        boolean inserted = false;

        while (remainProtectChoices > 0 || remainBanChoices > 0) {
            for (int i = 0; i < session.getBanInterval() && inserted; i ++) {
                this.stateStack.push(PickingWaitress.INSTANCE);
            }

            inserted = false;

            if (remainBanChoices > 0) {
                this.stateStack.push(BanWaitress.INSTANCE);
                remainBanChoices--;
                inserted = true;
            }
            if (remainProtectChoices > 0) {
                this.stateStack.push(ProtectWaitress.INSTANCE);
                remainProtectChoices--;
                inserted = true;
            }
        }

        this.stateStack.push(RollWaitress.INSTANCE);
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
        this.actionSwitch = !this.actionSwitch;
        stateStack.peek().engage(this);
    }

    public ActiveMapPool getMapPool() {
        return mapPool;
    }

    public boolean notStateCurrent(StateWaitress who) {
        return stateStack.peek() != who;
    }

    public boolean notActiveTeam(MultiplayerTeam team) {
        return activeTeam.peek() != team;
    }

    public void setCurrentBeatmap(Beatmap beatmap) {
        this.room.setCurrentBeatmap(beatmap);
    }

    public boolean getActionSwitch() {
        return actionSwitch;
    }
}
