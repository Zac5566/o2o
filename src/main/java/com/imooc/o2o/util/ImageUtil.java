package com.imooc.o2o.util;

import com.imooc.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ImageUtil {
    private static final Random r = new Random();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /*
     * 將CommonsMultipartFile轉換為file
     * */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IllegalStateException e) {
            logger.error(e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 处理缩略图，并返回新生成图片的相对值路径
     *
     * @param imageHolder
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder imageHolder, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(imageHolder.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is:" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(imageHolder.getImage()).size(200, 200).watermark(Positions.BOTTOM_RIGHT,
                    ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f).outputQuality(0.8f)
                    .toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }
    /**
     * 返回詳細图片的相对值路径
     * @Author: l5125
     * @Date: 2020/6/22
     * @param imageHolderList:
     * @param targetAddr:
     * @return: java.lang.String
     */
    public static List<String> generateThumbnails(List<ImageHolder> imageHolderList,String targetAddr) {
        makeDirPath(targetAddr);
        List<String> imageListAddr = new ArrayList<String>();
        for(ImageHolder holder:imageHolderList){
            String realFileName = getRandomFileName();
            String extension = getFileExtension(holder.getImageName());
            String relativeAddr = targetAddr + realFileName + extension;
            logger.debug("current relativeAddr is:" + relativeAddr);
            File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
            logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
            try {
                Thumbnails.of(holder.getImage()).size(337, 640).watermark(Positions.BOTTOM_RIGHT,
                        ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f).outputQuality(0.9f)
                        .toFile(dest);
            } catch (IOException e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
            imageListAddr.add(relativeAddr);
        }
        return imageListAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/xiangze/xxx.jpg, 那么 home work xiangze
     * 这三个文件夹都得自动创建
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     *
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {

        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("C:\\Users\\L\\Desktop\\test.jpg"))
                .size(200, 200).watermark(Positions.BOTTOM_RIGHT
                , ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
                .outputQuality(0.8f).toFile("C:\\Users\\L\\Desktop\\test2.jpg");
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        int ranNum = r.nextInt(89999) + 10000;
        String ranPath = simpleDateFormat.format(new Date()) + ranNum;
        return ranPath;
    }

    /**
     * 刪除圖片的方法
     *   1.傳入的路徑是資料夾
     *       -刪除該資料夾內所有照片與該資料夾
     *   2.傳入的是圖片路徑
     *       -刪除該圖片
     *
     * @Author: l5125
     * @Date: 2020/6/22
     * @param storePath:數據庫中的相對路徑
     * @return: void
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        //判斷該路徑下是否存在文件或資料夾
        if (fileOrPath.exists()) {
            //判斷是否是資料夾，如果是就刪除資料夾內的所有文件
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

}
