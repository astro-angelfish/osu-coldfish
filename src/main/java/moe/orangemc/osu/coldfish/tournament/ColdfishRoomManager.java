package moe.orangemc.osu.coldfish.tournament;

import moe.orangemc.osu.al1s.api.mutltiplayer.MatchRoom;
import moe.orangemc.osu.al1s.inject.api.Provides;

import java.util.HashMap;
import java.util.Map;

public class ColdfishRoomManager {
    private final Map<MatchRoom, Room> roomMap = new HashMap<>();

    public Room findRoom(MatchRoom from) {
        if (!roomMap.containsKey(from)) {
            throw new IllegalArgumentException("I'm not a referee to room: " + from.getName() + ", at least I'm not configured for it");
        }

        return roomMap.get(from);
    }

    public void registerRoom(MatchRoom osuRoom, Room tournamentRoom) {
        if (roomMap.containsKey(osuRoom)) {
            throw new IllegalStateException("Room " + osuRoom.getName() + " is already registered as a coldfish tournament room");
        }

        roomMap.put(osuRoom, tournamentRoom);
    }

    public static class Provider {
        private final ColdfishRoomManager manager = new ColdfishRoomManager();

        @Provides
        public ColdfishRoomManager provideColdfishRoomManager() {
            return manager;
        }
    }
}
