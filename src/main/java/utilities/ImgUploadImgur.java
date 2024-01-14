package utilities;

import io.restassured.RestAssured;

import java.io.FileInputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class ImgUploadImgur {
    public static String uploadImgToImgur(String imgLocalPath) throws Exception {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/Resources/ConfigFileNProperties/config.properties");
        properties.load(fileInputStream);

        RestAssured.baseURI = "https://api.imgur.com/3/image";
        RestAssured.basePath = "";
        String imgPath = "";
        imgPath = given().relaxedHTTPSValidation()
                .header("Authorization", properties.getProperty("authorization"))
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("image", ConvertImg.convertImgToBase64(imgLocalPath))
                .when().post()
                .then().statusCode(200)
                .extract().path("data.link");

        return imgPath;
    }

    public static String getImgUploaded(String imgLocalPath) throws Exception {
        Properties properties = new Properties();

        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/Resources/ConfigFileNProperties/config.properties");
        properties.load(fileInputStream);

        RestAssured.baseURI = "https://api.imgur.com/3/image";
        RestAssured.basePath = "";
        String resp = given().relaxedHTTPSValidation()
                .header("Authorization", properties.getProperty("authorization"))
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("image", ConvertImg.convertImgToBase64(imgLocalPath))
                .when().post()
                .then().statusCode(200)
                .extract().asString();

        return resp;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(uploadImgToImgur(System.getProperty("user.dir") + "/test-output/img/Buyer_CartNCheckout_TC1_Step1-13-05-2022.png"));
//        System.out.println(getImgUploaded(System.getProperty("user.dir") + "/test-output/img/Buyer_CartNCheckout_TC1_Step1-13-05-2022.png"));
    }
}
