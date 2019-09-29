package mine.project;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * MD5
 */
public class MD5 {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String str = "Hello MD5";
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] res = digest.digest(str.getBytes());
        System.out.println("RES:" + Hex.encodeHexString(res));
        System.out.println(Long.parseLong("aaccccccc01", 16));
    }
}