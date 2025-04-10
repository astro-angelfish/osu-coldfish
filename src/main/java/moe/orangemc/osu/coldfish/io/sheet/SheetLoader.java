package moe.orangemc.osu.coldfish.io.sheet;

import moe.orangemc.osu.al1s.api.beatmap.Beatmap;
import moe.orangemc.osu.al1s.api.mutltiplayer.TeamMode;
import moe.orangemc.osu.al1s.api.mutltiplayer.WinCondition;
import moe.orangemc.osu.al1s.api.ruleset.Mod;
import moe.orangemc.osu.al1s.api.ruleset.Ruleset;
import moe.orangemc.osu.coldfish.tournament.map.MapPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SheetLoader {
    Ruleset readRuleset();
    String readTournamentAbbreviation();
    TeamMode readTeamMode();
    WinCondition readWinCondition();

    int readPickTime();
    int readPrepareTime();

    int readBanTime();
    int readForfeitTime();
    int readPauseTime();

    int readPauseNumber();

    int readBanChoices();
    int readProtectChoices();

    int readBanInterval();

    void readInitialOrders(List<Boolean> initialOrders);
    boolean readRevertInitialPickOrder();

    void readEquivalentMods(Map<Mod, Set<Set<Mod>>> equivalentMods);
    void readScoreModifier(Map<Mod, Double> scoreModifier);

    void readRounds(Map<String, MapPool> rounds);

    Map<String, Beatmap> readMapPool(String roundName);
    Set<Mod> readAppliedMods(String modId);
}
