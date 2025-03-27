package moe.orangemc.osu.coldfish.tournament;

import moe.orangemc.osu.al1s.api.mutltiplayer.MultiplayerTeam;
import moe.orangemc.osu.al1s.api.user.User;

import java.util.List;

public class Team {
    private final MultiplayerTeam teamColor;
    private final String teamName;
    private final List<User> users;
    public Team(MultiplayerTeam teamColor, String teamName, List<User> users) {
        this.teamColor = teamColor;
        this.teamName = teamName;
        this.users = users;
    }
    public MultiplayerTeam getTeamColor() {
        return teamColor;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<User> getUsers() {
        return users;
    }
}
