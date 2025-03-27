package moe.orangemc.osu.coldfish.tournament;

import moe.orangemc.osu.al1s.api.mutltiplayer.TeamMode;
import moe.orangemc.osu.al1s.api.mutltiplayer.WinCondition;
import moe.orangemc.osu.al1s.api.ruleset.Mod;
import moe.orangemc.osu.al1s.api.ruleset.Ruleset;
import moe.orangemc.osu.coldfish.io.sheet.GoogleSheetLoader;
import moe.orangemc.osu.coldfish.tournament.map.MapPool;

import java.util.*;

public class Session {
    private final Ruleset ruleset;
    private final String tournamentAbbreviation;
    private final TeamMode teamMode;
    private final WinCondition winCondition;

    // below are counted in seconds
    private final int pickTime;
    private final int prepareTime;

    // these are minutes; seconds are way too long
    private final int banTime;
    private final int forfeitTime;
    private final int pauseTime;

    // Number of pauses
    private final int pauseNumber;

    private final int banChoices;
    private final int protectChoices;

    private final int banInterval;

    private final List<Boolean> initialOrders = new ArrayList<>();
    private final boolean revertInitialPickOrder;

    // eg: hd = [ hd, ez, [hd, ez] ], hr = [ hr, [hd, hr] ]
    private final Map<Mod, Set<Set<Mod>>> equivalentMods = new HashMap<>();
    // Applied after vanilla osu! score modifier.
    private final Map<Mod, Double> scoreModifier = new HashMap<>();

    private final Map<String, MapPool> rounds = new HashMap<>();
    private final Set<Room> brackets = new HashSet<>();

    public Session(GoogleSheetLoader loader) {

    }

    public Ruleset getRuleset() {
        return ruleset;
    }

    public String getTournamentAbbreviation() {
        return tournamentAbbreviation;
    }

    public TeamMode getTeamMode() {
        return teamMode;
    }

    public WinCondition getWinCondition() {
        return winCondition;
    }

    public int getPickTime() {
        return pickTime;
    }

    public int getPrepareTime() {
        return prepareTime;
    }

    public int getBanTime() {
        return banTime;
    }

    public int getForfeitTime() {
        return forfeitTime;
    }

    public int getPauseTime() {return pauseTime;}

    public int getPauseNumber() {return pauseNumber;}

    public int getBanChoices() {
        return banChoices;
    }

    public int getProtectChoices() {
        return protectChoices;
    }

    public int getBanInterval() {
        return banInterval;
    }

    public List<Boolean> getInitialOrders() {
        return initialOrders;
    }

    public boolean isRevertInitialPickOrder() {
        return revertInitialPickOrder;
    }

    public Map<Mod, Set<Set<Mod>>> getEquivalentMods() {
        return equivalentMods;
    }

    public Map<Mod, Double> getScoreModifier() {
        return scoreModifier;
    }
}
