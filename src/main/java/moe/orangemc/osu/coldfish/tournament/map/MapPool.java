package moe.orangemc.osu.coldfish.tournament.map;

import moe.orangemc.osu.al1s.api.beatmap.Beatmap;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapPool {
    private static final Pattern mapIdPattern = Pattern.compile("^([a-zA-Z]+)(\\d+)$");
    private final Map<String, MapIdentifier> mapIdentifiers = new HashMap<>();

    public Beatmap getBeatmap(String id) {
        Matcher matcher = mapIdPattern.matcher(id);

        if (matcher.find()) {
            String mapId = matcher.group(1);
            int index = Integer.parseInt(matcher.group(2));
            MapIdentifier mapIdentifier = mapIdentifiers.get(mapId);
            if (mapIdentifier != null) {
                return mapIdentifier.getBeatmap(index);
            } else {
                throw new NoSuchElementException(mapId);
            }
        }
        throw new IllegalArgumentException("Map not found: " + id);
    }
}
