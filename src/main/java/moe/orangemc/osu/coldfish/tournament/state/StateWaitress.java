package moe.orangemc.osu.coldfish.tournament.state;

import moe.orangemc.osu.coldfish.tournament.Room;

public interface StateWaitress {
    void engage(Room room);
    void timeout(Room room);

    default int getTimeout(Room room) {
        return room.getSession().getPickTime();
    }
}
