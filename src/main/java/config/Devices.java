package config;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public enum Devices {
    // for Android
    Emulator("emulator-5554", "13.0", "emulator-5554"),

    // for iOS
    Iphone13("Chau iPhone 13", "16.1", "00008110-000249AE21BA801E"),

    IphoneNamVo("iPhone của Khang","15.0", "12fe16dc353531f7ceb77baf7d4c0f6c1b538e4c"),
    Iphone11ProMax("NamKevin", "16.0","00008030-0011344A14A2802E"),
    ;

    private String name;

    public String getName() {
        return name;
    }

    private String version;

    public String getVersion() {
        return version;
    }

    private String udid;

    public String getUdid() {
        return udid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    private Devices(String name, String version) {
        this.name = name;
        this.version = version;
    }

    private Devices(String name, String version, String udid) {
        this.name = name;
        this.version = version;
        this.udid = udid;
    }
}