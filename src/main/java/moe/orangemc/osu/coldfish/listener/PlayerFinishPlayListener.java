package moe.orangemc.osu.coldfish.listener;

import moe.orangemc.osu.al1s.api.event.EventBus;
import moe.orangemc.osu.al1s.api.event.EventHandler;
import moe.orangemc.osu.al1s.api.event.multiplayer.MatchRoomEvent;
import moe.orangemc.osu.al1s.api.event.multiplayer.game.PlayerFinishPlayEvent;
import moe.orangemc.osu.al1s.inject.api.Inject;
import moe.orangemc.osu.coldfish.tournament.ColdfishRoomManager;
import moe.orangemc.osu.coldfish.tournament.state.play.PlayingWaitress;

public class PlayerFinishPlayListener {
    @Inject
    private EventBus bus;
    @Inject
    private ColdfishRoomManager roomManager;

    @EventHandler
    public void handleGetPlayerScore(MatchRoomEvent event) {
        if (!(event instanceof PlayerFinishPlayEvent playerFinishPlayEvent)) {
            return;
        }
        PlayingWaitress.INSTANCE.capturePlayingScores(roomManager.findRoom(event.getRoom()),
                playerFinishPlayEvent.getUser(),
                playerFinishPlayEvent.getScore());
    }
}
