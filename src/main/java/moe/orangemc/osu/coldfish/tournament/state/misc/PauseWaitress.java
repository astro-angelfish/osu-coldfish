package moe.orangemc.osu.coldfish.tournament.state.misc;

import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;

public class PauseWaitress implements StateWaitress {
    @Override
    public void engage(Room room) {

    }

    @Override
    public void timeout(Room room) {

    }

    @Override
    public int getTimeout(Room room) {
        return room.getSession().getPauseTime() * 60;
    }
}
