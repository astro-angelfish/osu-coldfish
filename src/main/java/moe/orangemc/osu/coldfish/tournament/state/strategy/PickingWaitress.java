package moe.orangemc.osu.coldfish.tournament.state.strategy;

import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;

public class PickingWaitress implements StateWaitress {
    public static final PickingWaitress INSTANCE = new PickingWaitress();

    protected PickingWaitress() {}

    @Override
    public void engage(Room room) {

    }

    @Override
    public void timeout(Room room) {

    }
}
