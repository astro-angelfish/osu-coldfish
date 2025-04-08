package moe.orangemc.osu.coldfish.tournament.map;

import moe.orangemc.osu.al1s.api.beatmap.Beatmap;

import java.util.*;
import java.util.regex.Matcher;

public class MapPool {
    private final Map<String, MapIdentifier> mapIdentifiers = new HashMap<>();

    public Beatmap getBeatmap(String id) {
        Matcher matcher = MapIdentifier.mapIdPattern.matcher(id);

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

    public MapIdentifier getMapIdentifier(String id) {
        Matcher matcher = MapIdentifier.mapIdPattern.matcher(id);

        if (matcher.find()) {
            String mapId = matcher.group(1);
            MapIdentifier mapIdentifier = mapIdentifiers.get(mapId);
            if (mapIdentifier != null) {
                return mapIdentifier;
            } else {
                throw new NoSuchElementException(mapId);
            }
        }

        throw new IllegalArgumentException("Map not found: " + id);
    }

    public Set<MapIdentifier> getAvailableIdentifiers() {
        return new HashSet<>(mapIdentifiers.values());
    }
}
