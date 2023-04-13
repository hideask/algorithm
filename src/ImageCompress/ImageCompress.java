package ImageCompress;

import javafx.beans.binding.IntegerBinding;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:ImageCompress
 * create user: songj
 * date : 2020/10/10 17:04
 */
public class ImageCompress {
    public static void main(String[] args) {
        System.out.println("开始处理...");
        imageCompress();
        System.out.println("处理完成");
    }

    public static List getConfig() {

        List list = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:\\picture\\config.txt"))));
            String str = null;
            while (true) {
                if (!((str = br.readLine()) != null)) break;
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<File> getFiles(String path) {
        File file = new File(path);
        List<File> files = new ArrayList<File>();
        String[] strArray = file.getName().split("\\.");
        int suffixIndex = strArray.length - 1;
        String type = strArray[suffixIndex].toLowerCase();
        if (!file.isDirectory() && ("jpeg".equals(type) || "jpg".equals(type))) {
            files.add(file);
        } else {
            File[] subFiles = file.listFiles();
            for (File f : subFiles) {
                strArray = f.getName().split("\\.");
                suffixIndex = strArray.length - 1;
                type = strArray[suffixIndex].toLowerCase();
                if ("jpeg".equals(type) || "jpg".equals(type)) {
                    files.addAll(getFiles(f.getAbsolutePath()));
                }
            }
        }
        return files;
    }

    public static void imageCompress() {
        List<String> list = getConfig();
        List<File> filelist = getFiles(list.get(0));
        for (File file : filelist) {
            try {
                thumbnail(file, new Integer(list.get(2)), new Integer(list.get(3)), new File(list.get(1) + "\\" + file.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 按照固定宽高原图压缩
     *
     * @param img    源图片文件
     * @param width  宽
     * @param height 高
     * @param out    输出流
     * @throws IOException the io exception
     */
    public static void thumbnail(File img, int width, int height, File out) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(img);
        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = tag.getGraphics();
        graphics.setColor(Color.RED);
        // 绘制处理后的图
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        ImageIO.write(tag, "JPG", out);
    }

}
