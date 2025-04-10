package moe.orangemc.osu.coldfish.tournament.map;

import moe.orangemc.osu.al1s.api.beatmap.Beatmap;
import moe.orangemc.osu.al1s.api.ruleset.Mod;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public record MapIdentifier(String id, Set<Mod> mods, List<Beatmap> beatmaps) {
    public static final Pattern mapIdPattern = Pattern.compile("^([a-zA-Z]+)(\\d+)$");

    public Beatmap getBeatmap(int index) {
        return beatmaps.get(index - 1);
    }
}
