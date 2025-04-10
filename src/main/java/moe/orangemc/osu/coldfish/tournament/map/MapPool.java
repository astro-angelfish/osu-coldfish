package moe.orangemc.osu.coldfish.tournament.map;

import moe.orangemc.osu.al1s.api.beatmap.Beatmap;
import moe.orangemc.osu.coldfish.io.sheet.SheetLoader;

import java.util.*;
import java.util.regex.Matcher;

public class MapPool {
    private final Map<String, MapIdentifier> mapIdentifiers = new HashMap<>();

    public MapPool(SheetLoader sheetLoader, String currentRound) {
        Map<Beatmap, Integer> indexes = new HashMap<>();
        Map<String, List<Beatmap>> beatmaps = new HashMap<>();

        readBeatmapPool(sheetLoader, currentRound, indexes, beatmaps);
        updateLocalIdentifiers(sheetLoader, beatmaps, indexes);
    }

    private void updateLocalIdentifiers(SheetLoader sheetLoader, Map<String, List<Beatmap>> beatmaps, Map<Beatmap, Integer> indexes) {
        for (Map.Entry<String, List<Beatmap>> entry : beatmaps.entrySet()) {
            entry.getValue().sort((a, b) -> {
                int indexA = indexes.get(a);
                int indexB = indexes.get(b);
                return Integer.compare(indexA, indexB);
            });

            mapIdentifiers.put(entry.getKey(), new MapIdentifier(entry.getKey(), sheetLoader.readAppliedMods(entry.getKey()), entry.getValue()));
        }
    }

    private static void readBeatmapPool(SheetLoader sheetLoader, String currentRound, Map<Beatmap, Integer> indexes, Map<String, List<Beatmap>> beatmaps) {
        for (Map.Entry<String, Beatmap> entry : sheetLoader.readMapPool(currentRound).entrySet()) {
            String mapId = entry.getKey();
            Beatmap beatmap = entry.getValue();

            Matcher matcher = MapIdentifier.mapIdPattern.matcher(mapId);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid map ID: " + mapId);
            }

            String modId = matcher.group(1);
            int index = Integer.parseInt(matcher.group(2));

            indexes.put(beatmap, index);
            if (!beatmaps.containsKey(modId)) {
                beatmaps.put(modId, new ArrayList<>());
            }
            beatmaps.get(modId).add(beatmap);
        }
    }

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
