package moe.orangemc.osu.coldfish.listener;

import moe.orangemc.osu.al1s.api.chat.OsuChannel;
import moe.orangemc.osu.al1s.api.event.EventBus;
import moe.orangemc.osu.al1s.api.event.EventHandler;
import moe.orangemc.osu.al1s.api.event.chat.MultiplayerRoomChatEvent;
import moe.orangemc.osu.al1s.api.event.chat.SystemMessagePoll;
import moe.orangemc.osu.al1s.api.mutltiplayer.MatchRoom;
import moe.orangemc.osu.al1s.api.user.User;
import moe.orangemc.osu.al1s.inject.api.Inject;
import moe.orangemc.osu.coldfish.event.MatchRollEvent;

import java.util.*;
import java.util.regex.Pattern;

public class PlayerRollListener {
    @Inject
    private EventBus bus;

    private static final Pattern rollMessage = Pattern.compile("(.+) rolls (\\d+) point\\(s\\)");

    private final Map<MatchRoom, Set<User>> pendingRoll = new HashMap<>();

    @EventHandler
    public void handleRollRequest(MultiplayerRoomChatEvent event) {
        if (!event.getMessage().strip().equals("!roll")) {
            return;
        }

        MatchRoom room = event.getRoom();
        User user = event.getSender();
        if (!pendingRoll.containsKey(room)) {
            pendingRoll.put(room, new HashSet<>());
        }

        pendingRoll.get(room).add(user);
    }

    @EventHandler
    public void handleRollEnd(SystemMessagePoll event) {
        OsuChannel channel = event.getChannel();
        if (!(channel instanceof MatchRoom room)) {
            return;
        }

        if (!pendingRoll.containsKey(room)) {
            return;
        }

        Set<User> users = pendingRoll.get(room);

        for (String message : event.getMessages()) {
            var matcher = rollMessage.matcher(message);
            if (matcher.find()) {
                String userName = matcher.group(1);
                int rollValue = Integer.parseInt(matcher.group(2));

                for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
                    User user = iterator.next();
                    if (!user.getUsername().equalsIgnoreCase(userName)) {
                        continue;
                    }

                    bus.fire(new MatchRollEvent(room, user, rollValue));
                    iterator.remove();
                    break;
                }
            }
        }
    }
}
