package config;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public enum Languages {
    EN("English"),
    VN("Tiếng Việt");

    private String text;

    private Languages(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
