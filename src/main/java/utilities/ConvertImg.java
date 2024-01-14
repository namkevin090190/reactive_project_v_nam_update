package utilities;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ConvertImg {
    public static String convertImgToBase64(String imgPath) throws IOException {
        File file = new File(imgPath);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte byeArray[] = new byte[(int)file.length()];
        fileInputStream.read(byeArray);
        String imgStringInBase64 = Base64.encodeBase64String(byeArray);

        return imgStringInBase64;
    }
}
