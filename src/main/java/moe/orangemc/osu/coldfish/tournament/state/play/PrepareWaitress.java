package moe.orangemc.osu.coldfish.tournament.state.play;

import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;

public class PrepareWaitress implements StateWaitress {
    public static final PrepareWaitress INSTANCE = new PrepareWaitress();

    private PrepareWaitress() {}

    @Override
    public void engage(Room room) {

    }

    @Override
    public void timeout(Room room) {

    }
}
