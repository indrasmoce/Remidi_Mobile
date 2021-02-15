package com.example.katalog.API;

import java.util.HashMap;
import java.util.Map;

public class TeamData {

    private String strTeam;
    private String intFormedYear;
    private String strDescriptionEN;
    private String strTeamBadge;

    public TeamData(String strTeam, String intFormedYear, String strDescriptionEN, String strTeamBadge) {
        this.strTeam = strTeam;
        this.intFormedYear = intFormedYear;
        this.strDescriptionEN = strDescriptionEN;
        this.strTeamBadge = strTeamBadge;
    }

    public String getStrTeam() {
        return strTeam;
    }

    public void setStrTeam(String strTeam) {
        this.strTeam = strTeam;
    }

    public String getIntFormedYear() {
        return intFormedYear;
    }

    public void setIntFormedYear(String intFormedYear) {
        this.intFormedYear = intFormedYear;
    }

    public String getStrDescriptionEN() {
        return strDescriptionEN;
    }

    public void setStrDescriptionEN(String strDescriptionEN) {
        this.strDescriptionEN = strDescriptionEN;
    }

    public String getStrTeamBadge() {
        return strTeamBadge;
    }

    public void setStrTeamBadge(String strTeamBadge) {
        this.strTeamBadge = strTeamBadge;
    }
}