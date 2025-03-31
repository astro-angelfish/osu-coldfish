package moe.orangemc.osu.coldfish.tournament.state.play;

import moe.orangemc.osu.al1s.api.event.multiplayer.MatchRoomEvent;
import moe.orangemc.osu.al1s.api.event.multiplayer.game.AllReadyEvent;
import moe.orangemc.osu.al1s.api.mutltiplayer.MatchRoom;
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
        if (checkPlayerMod(room)){
            room.getMatchRoom().sendMessage("mods don`t match with the rules");
            return;
        }
        if (checkPlayerSlot(room)){
            changePlayerSlot(room.getMatchRoom());
        }
        room.getMatchRoom().start(7);
    }

    @Override
    public void captureEvent(Room room, MatchRoomEvent evt) {
        if (evt instanceof AllReadyEvent) {
            if (checkPlayerMod(room)){
                room.getMatchRoom().sendMessage("mods don`t match with the rules");
                return;
            }
            if (checkPlayerNumber(room)){
                room.getMatchRoom().sendMessage("Make sure your team have enough players");
                return;
            }
            room.getMatchRoom().sendMessage("!mp aborttimer");
            if (checkPlayerSlot(room)){
                changePlayerSlot(room.getMatchRoom());
            }
            room.getMatchRoom().start(7);
        }
    }

    // TODO: completed the check functions
    private boolean checkPlayerMod(Room room) {
        return true;
    }
    private boolean checkPlayerNumber(Room room) {
        return true;
    }
    private boolean checkPlayerSlot(Room room) {
        return true;
    }
    // make sure players in the right slot
    private void changePlayerSlot(MatchRoom room) {

    }
}
