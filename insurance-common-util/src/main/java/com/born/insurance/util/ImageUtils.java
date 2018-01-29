package com.born.insurance.util;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.FileOutputStream;


public final class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    public ImageUtils() {

    }

    public final static void pressImage(HttpServletRequest request, String pressImg, String targetImg) {
        try {
            String pathArray = null;
            WebApplicationContext wac = (WebApplicationContext) request
                    .getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
            ServletContext context = wac.getServletContext();
            pathArray = context.getRealPath("") + File.separator;
            //目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            //水印文件
            File _filebiao = new File(pathArray + pressImg);
            logger.info("水印文件路径："+_filebiao);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            //水印文件结束
            g.dispose();

            String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
            ImageIO.write(image, formatName , new File(targetImg) );

//            FileOutputStream out = new FileOutputStream(targetImg);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(image);
//            out.close();
        } catch (Exception e) {
            logger.error("水印图片出错，错误原因:"+e.getMessage(),e);
        }
    }


    public static void main(String[] args) {

//        pressImage("http://127.0.0.1:8088/resources/images/common/rn.png","C:\\Users\\min\\Desktop\\4cbc56c1a57e26873c140000.jpg");
    }
}
