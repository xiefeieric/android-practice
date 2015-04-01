package eric.com.lingchat.Utility;

/**
 * Created by Eric on 27/03/2015.
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CipherUtil {

    private CipherUtil() {

    }

    //字节数组转化为16进制字符串
    public static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        char[] resultCharArray =new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    //字节数组md5算法
    public static byte[] md5 (byte [] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}