package utilities;

import com.mongodb.util.JSON;
import com.relevantcodes.extentreports.LogStatus;
import config.Configs;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.ITestResult;
import privates.Setting;

import java.util.*;

import static io.restassured.RestAssured.given;

public class SlackIncomingWebhook {
    private static final String slackWebhook = "services/T0609B9AUTC/B060YBQCF6C/HjrtO29MEe93jQfqiAGo2qQm";
    private static final String channel = "report-app";

    public static void postStartRequest(String message, String env) {
        RestAssured.baseURI = "https://hooks.slack.com/";
        RestAssured.basePath = "";

        String icon, author;
        icon = Setting.author1.getSlackIcon();
        author = Setting.author1.getSlackID();

        Map<String, Object> field = new HashMap<String, Object>();
        field.put("title", "Run by:");
        field.put("value", icon + " " + author);
        field.put("short", false);

        Map<String, Object> field2 = new HashMap<String, Object>();
        field2.put("title", "Environment:");
        field2.put("value", env);
        field2.put("short", false);

        Map<String, Object> field3 = new HashMap<String, Object>();
        field3.put("title", "OS:");
        String osSystem;
        if(Configs.isAndroid) osSystem = "Android";
        else osSystem = "iOS";
        field3.put("value", osSystem);
        field3.put("short", false);

        Map<String, Object> field4 = new HashMap<String, Object>();
        field4.put("title", "isRealDevice:");
        String isReal;
        if(Configs.isEmulator) isReal = "FALSE";
        else isReal = "TRUE";
        field4.put("value", isReal);
        field4.put("short", false);

        Map<String, Object> field5 = new HashMap<String, Object>();
        field5.put("title", "Device size:");
        String deviceSize, des;
//        if(Configs.isTabletVer) {
//            deviceSize = "Tablet";
//            if(Configs.isAndroid) des = Configs.AndroidTablet.getName();
//            else des = Configs.iPad.getName();
//        } else {
            deviceSize = "Phone";
            if(Configs.isAndroid) des = Configs.AndroidPhone.getName();
            else des = Configs.iPhone.getName();
//        }
        field5.put("value", deviceSize + " (" + des + ")");
        field5.put("short", false);

        List<Object> fields = new ArrayList<>();
        fields.add(field);
        fields.add(field2);
        fields.add(field3);
        fields.add(field4);
        fields.add(field5);

        Map<String, Object> content = new HashMap<>();
        content.put("title", message);
        content.put("color", Setting.author1.getSlackColor());
        content.put("fields", fields);

        List<Object> attachments = new ArrayList<>();
        attachments.add(content);

        Map<String, Object> payload = new HashMap<>();
        payload.put("channel", channel);
        payload.put("attachments", attachments);

        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
//                .header("Content-Type","application/json")
                .when()
                .body(JSON.serialize(payload))
                .post(slackWebhook);
    }

    public static void postEndRequest(String message, String reportUrl, String logUrl, boolean isPass) {
        RestAssured.baseURI = "https://hooks.slack.com/";
        RestAssured.basePath = "";

        String color, report;
        if (isPass) {
            color = "#10e020";
            report = " is `PASSED`";
        } else {
            color = "#e01010";
            report = " is `FAILED`";
        }

        Map<String, Object> field = new HashMap<String, Object>();
        field.put("title", message);
        field.put("value", report);
        field.put("short", false);

        List<Object> fields = new ArrayList<>();
        fields.add(field);

        Map<String, Object> content = new HashMap<String, Object>();
        content.put("title", "Report here: " + reportUrl);
        content.put("text", "Log here: " + logUrl);
        content.put("fields", fields);
        content.put("color", color);

        List<Object> attachments = new ArrayList<>();
        attachments.add(content);

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("channel", channel);
        payload.put("attachments", attachments);

        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .when()
                .body(JSON.serialize(payload))
                .post(slackWebhook);
    }

    public static void postRequest(ITestResult status, String message) {
        RestAssured.baseURI = "https://hooks.slack.com/";
        RestAssured.basePath = "";

        String color, result;
        if (status.getStatus() == ITestResult.SUCCESS) {
            color = "#10e020";
            result = "PASSED";
        } else if (status.getStatus() == ITestResult.FAILURE) {
            color = "#e01010";
            result = "FAILED";
        } else {
            color = "#e4b822";
            result = "UNKNOWN";
        }

        Date today = new Date();
        Double currentTime = Math.floor(today.getTime() / 1000);

        Map<String, Object> field = new HashMap<String, Object>();
        field.put("title", "Result:");
        field.put("value", result);
        field.put("short", false);

        List<Object> fields = new ArrayList<>();
        fields.add(field);

        Map<String, Object> content = new HashMap<String, Object>();
        content.put("title", message);
//        content.put("title_link", "https://www.google.com/");
//        content.put("text", "Log here: ...");
        content.put("color", color);
        content.put("fields", fields);
        content.put("footer", "FinX");
        content.put("footer_icon", "");
        content.put("ts", currentTime);

        List<Object> attachments = new ArrayList<>();
        attachments.add(content);

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("channel", channel);
        payload.put("attachments", attachments);

        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .when()
                .body(JSON.serialize(payload))
                .post(slackWebhook);
    }

    public static void postRequestCmdLine(LogStatus status, String message) {
        RestAssured.baseURI = "https://hooks.slack.com/";
        RestAssured.basePath = "";

        String color, result;
        if (status == LogStatus.PASS) {
            color = "#10e020";
            result = "PASSED";
        } else if (status == LogStatus.FAIL) {
            color = "#e01010";
            result = "FAILED";
        } else {
            color = "#e4b822";
            result = "UNKNOWN";
        }

        Date today = new Date();
        Double currentTime = Math.floor(today.getTime() / 1000);

        Map<String, Object> field = new HashMap<String, Object>();
        field.put("title", "Result:");
        field.put("value", result);
        field.put("short", false);

        List<Object> fields = new ArrayList<>();
        fields.add(field);

        Map<String, Object> content = new HashMap<String, Object>();
        content.put("title", message);
//        content.put("title_link", "https://www.google.com/");
//        content.put("text", "Log here: ...");
        content.put("color", color);
        content.put("fields", fields);
        content.put("footer", "FinX");
        content.put("footer_icon", "");
        content.put("ts", currentTime);

        List<Object> attachments = new ArrayList<>();
        attachments.add(content);

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("channel", channel);
        payload.put("attachments", attachments);

        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .when()
                .body(JSON.serialize(payload))
                .post(slackWebhook);
    }

    public static void postEndRequestCmdLine(String message, String reportUrl, String logUrl, boolean isPass) {
        RestAssured.baseURI = "https://hooks.slack.com/";
        RestAssured.basePath = "";

        String color, report;
        if (isPass) {
            color = "#10e020";
            report = " is `PASSED`";
        } else {
            color = "#e01010";
            report = " is `FAILED`";
        }

        Date today = new Date();
        Double currentTime = Math.floor(today.getTime() / 1000);

        Map<String, Object> field = new HashMap<String, Object>();
        field.put("title", "Result:");
        field.put("value", report);
        field.put("short", false);

        Map<String, Object> field2 = new HashMap<String, Object>();
        field2.put("title", "Environment:");
        field2.put("value", "Update later");
        field2.put("short", false);

        List<Object> fields = new ArrayList<>();
        fields.add(field2);
        fields.add(field);

        Map<String, Object> content = new HashMap<String, Object>();
        content.put("author_name", "Run by: " + Setting.author1.getSlackIcon() + " " + Setting.author1.getSlackID());
        content.put("title", message);
        content.put("title_link", reportUrl);
        content.put("text", "Log here: " + logUrl);
        content.put("fields", fields);
        content.put("color", color);
        content.put("footer", "FinX");
        content.put("footer_icon", "");
        content.put("ts", currentTime);

        List<Object> attachments = new ArrayList<>();
        attachments.add(content);

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("channel", channel);
        payload.put("attachments", attachments);

        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .when()
                .body(JSON.serialize(payload))
                .post(slackWebhook);
    }

    /**
     * Create by: chaule
     */
    public static void main(String[] args) {

    }
}
