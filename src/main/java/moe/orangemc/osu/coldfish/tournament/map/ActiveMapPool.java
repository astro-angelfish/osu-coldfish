package moe.orangemc.osu.coldfish.tournament.map;

import java.util.HashMap;
import java.util.Map;

public class ActiveMapPool {
    private final MapPool parent;

    // who would use more than 32 maps on a mod
    private final Map<MapIdentifier, Integer> pickedMask = new HashMap<>();
    private final Map<MapIdentifier, Integer> bannedMask = new HashMap<>();
    private final Map<MapIdentifier, Integer> protectedMask = new HashMap<>();

    public ActiveMapPool(MapPool pool) {
        this.parent = pool;

        for (MapIdentifier identifier : pool.getAvailableIdentifiers()) {
            pickedMask.put(identifier, 0);
            bannedMask.put(identifier, 0);
            protectedMask.put(identifier, 0);
        }
    }

    public void markBan(String id) {
        MapIdentifier identifier = parent.getMapIdentifier(id);
        int idx = Integer.parseInt(MapIdentifier.mapIdPattern.matcher(id).group(2));

        int mask = 1 << idx;
        checkProtect(identifier, mask);
        checkBanPick(identifier, mask);

        bannedMask.put(identifier, bannedMask.get(identifier) | mask);
    }

    private void checkBanPick(MapIdentifier identifier, int mask) {
        if ((bannedMask.get(identifier) & mask) == mask) {
            throw new IllegalStateException("This map is already banned");
        }

        // rare to happen, but occurs when there is picks in between bans
        if ((pickedMask.get(identifier) & mask) == mask) {
            throw new IllegalStateException("This map is already picked");
        }
    }

    public void markProtect(String id) {
        MapIdentifier identifier = parent.getMapIdentifier(id);
        int idx = Integer.parseInt(MapIdentifier.mapIdPattern.matcher(id).group(2));

        int mask = 1 << idx;
        checkProtect(identifier, mask);
        checkBanPick(identifier, mask);

        protectedMask.put(identifier, protectedMask.get(identifier) | mask);
    }

    private void checkProtect(MapIdentifier identifier, int mask) {
        if ((protectedMask.get(identifier) & mask) == mask) {
            throw new IllegalStateException("This map is already protected");
        }
    }

    public void markPick(String id) {
        MapIdentifier identifier = parent.getMapIdentifier(id);
        int idx = Integer.parseInt(MapIdentifier.mapIdPattern.matcher(id).group(2));

        int mask = 1 << idx;
        checkBanPick(identifier, mask);

        pickedMask.put(identifier, pickedMask.get(identifier) | mask);
    }
}
