package moe.orangemc.osu.coldfish.tournament.state;

import moe.orangemc.osu.al1s.api.chat.command.CommandBase;
import moe.orangemc.osu.al1s.api.event.multiplayer.MatchRoomEvent;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.coldfish.tournament.Room;

public interface StateWaitress {
    void engage(Room room);
    void timeout(Room room);

    default void captureEvent(Room room, MatchRoomEvent evt) {}
    default void captureCommandIssue(Room room, CommandBase cmd, User issuer) {}
}
