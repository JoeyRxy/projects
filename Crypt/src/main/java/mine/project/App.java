package mine.project;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * Hello world!
 *
 */
public class App {
    private final long key;

    public App(String keystr) throws NoSuchAlgorithmException {
        byte[] md5Key = MessageDigest.getInstance("MD5").digest(keystr.getBytes());
        String s = new String(Hex.encodeHex(md5Key));
        key = Long.parseLong(s, 0, 15, 16);
    }

    public void encry(File srcFile, File encryFile) throws IOException {
        if (!srcFile.exists()) {
            System.out.println("没有源文件");
            return;
        }
        if (!encryFile.exists()) {
            System.out.println("目标加密文件被创建");
            try {
                encryFile.createNewFile();
            } catch (IOException e) {
                System.out.println("目标文件创建失败");
                e.printStackTrace();
            }
        }
        InputStream fileInputStream = new FileInputStream(srcFile);
        OutputStream fileOutputStream = new FileOutputStream(encryFile);
        byte[] a = fileInputStream.readAllBytes();

        int len = a.length;
        for (int i = 0; i < len; i++) {
            a[i] ^= key;
        }
        fileOutputStream.write(a);

        fileInputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public void decry(File encryFile, File decryFile) throws IOException {
        if (!encryFile.exists()) {
            System.out.println("没有已加密文件");
            return;
        }
        if (!decryFile.exists()) {
            System.out.println("解密文件被创建");
            try {
                decryFile.createNewFile();
            } catch (IOException e) {
                System.out.println("解密文件创建失败");
                e.printStackTrace();
            }
        }
        InputStream fileInputStream = new FileInputStream(encryFile);
        OutputStream fileOutputStream = new FileOutputStream(decryFile);
        byte[] all = fileInputStream.readAllBytes();

        int len = all.length;
        for (int i = 0; i < len; i++)
            all[i] ^= key;
        fileOutputStream.write(all);

        fileInputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        File srcFile = new File("test.jpg");
        File encryFile = new File("encryFile");
        File decryFile = new File("decryFile.jpg");
        App app = new App("73699rxy");
        app.encry(srcFile, encryFile);
        app.decry(encryFile, decryFile);
    }
}
