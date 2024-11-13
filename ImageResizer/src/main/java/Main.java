import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import org.imgscalr.*;
public class Main {
private static int newWidth = 300;
    public static void main(String[] args) {



        String srcFolder = "C:\\java_basics\\Multithreading\\ImageResizer\\resources\\src";
        String dstFolder = "C:\\java_basics\\Multithreading\\ImageResizer\\resources\\dst";
        int coreCount = Runtime.getRuntime().availableProcessors();

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int minLength = files.length / coreCount;
        int startPosition = 0;

        for (int i = 0; i < coreCount; i++) {
            File[] files1 = new File[minLength];
            System.arraycopy(files, startPosition, files1, 0, minLength);
            if (i == coreCount - 1) {
                files1 = new File[files.length - startPosition];
                System.arraycopy(files, startPosition, files1, 0, files1.length);
            }
            ImageResizer imageResizer = new ImageResizer(files1, newWidth, dstFolder, start);
            new Thread (imageResizer).start();
            startPosition = startPosition + minLength;

        }
    }
}
