package moe.orangemc.osu.coldfish.event;

import moe.orangemc.osu.al1s.api.event.multiplayer.user.UserActInRoomEvent;
import moe.orangemc.osu.al1s.api.mutltiplayer.MatchRoom;
import moe.orangemc.osu.al1s.api.user.User;

public class MatchRollEvent extends UserActInRoomEvent {
    private final int roll;

    public MatchRollEvent(MatchRoom room, User user, int roll) {
        super(room, user);
        this.roll = roll;
    }

    public int getRoll() {
        return roll;
    }
}
