package moe.orangemc.osu.coldfish.tournament.state.early;

import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.Room;
import moe.orangemc.osu.coldfish.tournament.state.StateWaitress;

public class RoomCreationWaitress implements StateWaitress {
    public static final RoomCreationWaitress INSTANCE = new RoomCreationWaitress();

    private RoomCreationWaitress() {}

    @Override
    public void engage(Room room) {
        room.initiateMatchRoom();

        for (User u : room.getParticipants()) {
            room.getMatchRoom().invitePlayer(u);
        }
    }

    @Override
    public void timeout(Room room) {

    }
}
