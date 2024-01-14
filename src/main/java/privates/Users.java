package privates;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public enum Users {
    CHAU("<@U0602MT6F70>", "\uD83D\uDCA5","#370fe0"),
    FINX_SYSTEM("FINX SYSTEM", "\uD83D\uDC7B","#dbaf60"),

    NAMVO("<@U069AJ38QJH>", "\uD83D\uDCA5","#370fe0"),
    ;

    private String slackID;
    private String slackIcon;
    private String slackColor;

    private Users(String slackID, String slackIcon, String slackColor) {
        this.slackIcon = slackIcon;
        this.slackID = slackID;
        this.slackColor = slackColor;
    }

    public String getSlackID() {
        return this.slackID;
    }
    public String getSlackIcon() {
        return this.slackIcon;
    }
    public String getSlackColor() {
        return this.slackColor;
    }
}