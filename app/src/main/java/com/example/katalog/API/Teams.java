package com.example.katalog.API;

import java.util.List;

public class Teams {
    private List<TeamData> teams;

    public void setTeams(List<TeamData> teams){
        this.teams = teams;
    }

    public List<TeamData> getTeams() {
        return teams;
    }
}
