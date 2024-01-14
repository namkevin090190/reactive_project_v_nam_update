package config;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public enum Apps {
    /**
     * - Get bundleId: updating...
     * - Get appPackage & appActivity on Mac:
     * 1. Open app on device
     * 2. adb shell dumpsys window | grep -E 'mCurrentFocus'
     */
    VIKKI_SIT("com.galaxyfinx.vikki.sit", "com.galaxyfinx.vikki.MainActivity", "com.galaxyfinx.vikki.sit", "", "SIT"),
    VIKKI_UAT("", "", "", "", "UAT"),
    ;

    private String appPackage;
    private String appActivity;
    private String bundleId;
    private String andPackageID;
    private String env;

    private Apps(String appPackage, String appActivity, String bundleId, String andPackageID, String env) {
        this.appPackage = appPackage;
        this.appActivity = appActivity;
        this.bundleId = bundleId;
        this.andPackageID = andPackageID;
        this.env = env;
    }

    public String getAppPackage() {
        return this.appPackage;
    }

    public String getAppActivity() {
        return this.appActivity;
    }

    public String getBundleId() {
        return this.bundleId;
    }

    public String getAndPackageID() {
        return this.andPackageID;
    }

    public String getEnv() {
        return this.env;
    }
}
