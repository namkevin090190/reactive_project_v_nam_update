package utilities;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.*;
import java.util.Properties;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public class FileUploadS3 {
    private static String awsId;
    private static String awsKey;
    private static String bucketName;
    private static String urlReturn;
    private static String urlPathReturn;
    private static String urlLogPathReturn;
    private static String urlImgPathReturn;

    /**
     * Upload & get url for Report
     */
    public static boolean uploadFileToS3(String fileName, String pathFile) {
        getProperties();
        BasicAWSCredentials creds = new BasicAWSCredentials(awsId, awsKey);
        AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion("ap-southeast-1").withCredentials(new AWSStaticCredentialsProvider(creds)).build();
//        Log.addLog("Debug aws upload - fileName: " + fileName);
//        Log.addLog("Debug aws upload - pathFile: " + pathFile);
        try {
            File file = new File(pathFile);
            s3client.putObject(new PutObjectRequest(bucketName, urlPathReturn + fileName, file));
            return true;
        } catch (AmazonClientException ace) {
            System.out.println("AmazonClientException: " + ace.getMessage());
            return false;
        }
    }

    public static String getFileFromS3(String fileName) {
        getProperties();

        return urlReturn + urlPathReturn + fileName;
    }

    /**
     * Upload & get url for Log
     */
    public static boolean uploadLogFileToS3(String fileName, String pathFile) {
        getProperties();
        BasicAWSCredentials creds = new BasicAWSCredentials(awsId, awsKey);
        AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion("ap-southeast-1").withCredentials(new AWSStaticCredentialsProvider(creds)).build();
        try {
//            File file = new File(pathFile);
//            s3client.putObject(new PutObjectRequest(bucketName, urlLogPathReturn + fileName, file));
            InputStream file = null;
            try {
                file = new FileInputStream(pathFile);
            } catch (Exception e) {
                Log.addLog("Can't load path file: " + pathFile);
            }

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.addUserMetadata("Content-Type", "text/plain;charset=utf-8");

            PutObjectRequest s3Obj = new PutObjectRequest(bucketName, urlLogPathReturn + fileName, file, metadata);
            s3client.putObject(s3Obj);
            return true;
        } catch (AmazonClientException ace) {
            System.out.println("AmazonClientException: " + ace.getMessage());
            return false;
        }
    }

    public static String getLogFileFromS3(String fileName) {
        getProperties();

        return urlReturn + urlLogPathReturn + fileName;
    }

    /**
     * Upload & get url for Img
     */
    public static boolean uploadImgToS3(String fileName, String pathFile) {
        getProperties();
        BasicAWSCredentials creds = new BasicAWSCredentials(awsId, awsKey);
        AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion("ap-southeast-1").withCredentials(new AWSStaticCredentialsProvider(creds)).build();
        try {
            File file = new File(pathFile);
            s3client.putObject(new PutObjectRequest(bucketName, urlImgPathReturn + fileName, file));
            return true;
        } catch (AmazonClientException ace) {
            System.out.println("AmazonClientException: " + ace.getMessage());
            return false;
        }
    }

    public static String getImgFromS3(String fileName) {
        getProperties();

        return urlReturn + urlImgPathReturn + fileName;
    }

    /**
     * get Setting
     */
    private static void getProperties() {
        Properties prop = new Properties();
        FileInputStream propFile1 = null;
        try {
            propFile1 = new FileInputStream(System.getProperty("user.dir") + "/resources/aws.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (propFile1 != null) {
                prop.load(propFile1);
                awsId = prop.getProperty("aws_access_key_id");
                awsKey = prop.getProperty("aws_secret_access_key");
                bucketName = prop.getProperty("aws_namecard_bucket");
                urlReturn = prop.getProperty("aws_url_return_prefix");
                urlPathReturn = prop.getProperty("aws_url_return_env_path");
                urlLogPathReturn = prop.getProperty("aws_url_return_log_path");
                urlImgPathReturn = prop.getProperty("aws_url_return_img_path");
            }
        } catch (IOException ex) {
            System.out.println("getProperties: " + ex);
        }
    }

    /**
     * upload  to S3
     * Create by: chaule
     * note: only run when upload .jar to S3
     */
    public static void main(String[] args) {
        uploadFileToS3("", "");
        Log.addLog("url: " + getFileFromS3(""));
    }
}
