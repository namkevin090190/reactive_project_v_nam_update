package config;

import privates.Users;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public class Configs {
    public static final boolean isStartDevice = true;
    public static final boolean isEmulator = false;
    public static final boolean isMobileWeb = false;
    public static final boolean isAndroid = false;

    public static final Devices iPhone = Devices.IphoneNamVo;
    public static final Devices AndroidPhone = Devices.Emulator;
    public static final Users user = Users.CHAU;
    public static final String env = Apps.VIKKI_SIT.getEnv();

    public static final boolean isStartAppiumServer = true;

    // with appium 1.xx => isNotReset is not affect with iOS
    // with appium 2 => isNotReset will reset data of Android & restart with iOS => fix iOS always TRUE
    public static final boolean isNotReset = true;

    public static final boolean isUploadImg = false;
    public static final boolean isUploadFile = false;
    public static final boolean isUploadImgToS3 = false;
    public static final boolean isHookToSlack = true;
    public static final boolean onlyHookFailed = true;

    /**
     * Setting AWS
     * Create by: Chaule
     * Notes: Don't edit
     */
    public static final String aws_access_key_id = "";
    public static final String aws_secret_access_key = "";
    public static final String aws_namecard_bucket = "";
    public static final String aws_url_return_prefix = "";

    public static final Languages defaultLanguage = Languages.VN;

    public static final int ServerSleep = 2000;
    public static final int ServerDeepSleep = 6000;

    /**
     * iOS
     * Notes:
     * - get Bundle ID: later
     */
    public static final String xcodeSigningId = "iPhone Developer";
    public static final String xcodeOrgId = "6JB8H55789"; // check README
}