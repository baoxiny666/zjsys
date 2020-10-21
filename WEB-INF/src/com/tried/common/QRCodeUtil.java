package com.tried.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil { // 图片宽度的一般  
    private static final int IMAGE_WIDTH = 40;  
    private static final int IMAGE_HEIGHT = 40;  
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;  
    private static final int FRAME_WIDTH = 1;  
    // 二维码写码器  
    private static MultiFormatWriter mutiWriter = new MultiFormatWriter();  
    //带logo的二维码
    public static void encode(String content, int width, int height,  String srcImagePath, String destImagePath) {  
        try {  
            ImageIO.write(genBarcode(content, width, height, srcImagePath), "jpg", new File(destImagePath));  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (WriterException e) {  
            e.printStackTrace();  
        }  
    }
    /**
     * @author liuyawei
     * @description 不带logo的二维码
     * @param content
     * @param width
     * @param height
     * @param destImagePath
     */
    public static void encode(String content, int width, int height,String destImagePath) {  
        try {  
            ImageIO.write(noLogoGenBarcode(content, width, height), "jpg", new File(destImagePath));  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (WriterException e) {  
            e.printStackTrace();  
        }  
    }
  
    /**
     * 带logo二维码生产器
      * @Description
      * @author liuxd
      * @date 2017-3-28 上午11:49:45
      * @version V1.0
     */
    private static BufferedImage genBarcode(String content, int width,  
            int height, String srcImagePath) throws WriterException,  
            IOException {  
        // 读取源图像  
        BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH,IMAGE_HEIGHT, true);  
        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];  
        for (int i = 0; i < scaleImage.getWidth(); i++) {  
            for (int j = 0; j < scaleImage.getHeight(); j++) {  
                srcPixels[i][j] = scaleImage.getRGB(i, j);  
            }  
        }  
  
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();  
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
        hint.put(EncodeHintType.MARGIN, 1);
        // 生成二维码  
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,  
                width, height, hint);  
       // matrix = deleteWhite(matrix);//删除白边
        // 二维矩阵转为一维像素数组  
        int halfW = matrix.getWidth() / 2;  
        int halfH = matrix.getHeight() / 2;  
        int[] pixels = new int[width * height];  
  
        for (int y = 0; y < matrix.getHeight(); y++) {  
            for (int x = 0; x < matrix.getWidth(); x++) {  
                // 读取图片  
                if (x > halfW - IMAGE_HALF_WIDTH  
                        && x < halfW + IMAGE_HALF_WIDTH  
                        && y > halfH - IMAGE_HALF_WIDTH  
                        && y < halfH + IMAGE_HALF_WIDTH) {  
                    pixels[y * width + x] = srcPixels[x - halfW  
                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];  
                }   
                // 在图片四周形成边框  
                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH  
                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH  
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)  
                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH  
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH  
                                && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                                + IMAGE_HALF_WIDTH + FRAME_WIDTH)  
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH  
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH  
                                && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                                - IMAGE_HALF_WIDTH + FRAME_WIDTH)  
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH  
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH  
                                && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                                + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {  
                    pixels[y * width + x] = 0xfffffff;  
                } else {  
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；  
                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000  
                            : 0xfffffff;  
                }  
            }  
        }  
  
        BufferedImage image = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        image.getRaster().setDataElements(0, 0, width, height, pixels);  
  
        return image;  
    }  
    
    /**
      * @Description 不带logo图标二维码
      * @author liuxd
      * @date 2017-3-28 上午11:50:39
      * @version V1.0
     */
    private static BufferedImage noLogoGenBarcode(String content, int width, int height ) throws WriterException, IOException {  
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();  
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
        hint.put(EncodeHintType.MARGIN, 1);
        // 生成二维码  
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,  
                width, height, hint);  
      // matrix = deleteWhite(matrix);//删除白边
        // 二维矩阵转为一维像素数组  
        int[] pixels = new int[width * height];  
        for (int y = 0; y < matrix.getHeight(); y++) {  
            for (int x = 0; x < matrix.getWidth(); x++) {  
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；  
              pixels[y * width + x] = matrix.get(x, y) ? 0xff000000: 0xfffffff;  
            }  
        }  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        image.getRaster().setDataElements(0, 0, width, height, pixels);  
  
        return image;  
    } 
  
    /** 
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标 
     *  
     * @param srcImageFile 
     *            源文件地址 
     * @param height 
     *            目标高度 
     * @param width 
     *            目标宽度 
     * @param hasFiller 
     *            比例不对时是否需要补白：true为补白; false为不补白; 
     * @throws IOException 
     */  
    private static BufferedImage scale(String srcImageFile, int height,  
            int width, boolean hasFiller) throws IOException {  
        double ratio = 0.0; // 缩放比例  
        File file = new File(srcImageFile);  
        BufferedImage srcImage = ImageIO.read(file);  
        Image destImage = srcImage.getScaledInstance(width, height,  
                BufferedImage.SCALE_SMOOTH);  
        // 计算比例  
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {  
            if (srcImage.getHeight() > srcImage.getWidth()) {  
                ratio = (new Integer(height)).doubleValue()  
                        / srcImage.getHeight();  
            } else {  
                ratio = (new Integer(width)).doubleValue()  
                        / srcImage.getWidth();  
            }  
            AffineTransformOp op = new AffineTransformOp(  
                    AffineTransform.getScaleInstance(ratio, ratio), null);  
            destImage = op.filter(srcImage, null);  
        }  
        if (hasFiller) {// 补白  
            BufferedImage image = new BufferedImage(width, height,  
                    BufferedImage.TYPE_INT_RGB);  
            Graphics2D graphic = image.createGraphics();  
            graphic.setColor(Color.white);  
            graphic.fillRect(0, 0, width, height);  
            if (width == destImage.getWidth(null))  
                graphic.drawImage(destImage, 0,  
                        (height - destImage.getHeight(null)) / 2,  
                        destImage.getWidth(null), destImage.getHeight(null),  
                        Color.white, null);  
            else  
                graphic.drawImage(destImage,  
                        (width - destImage.getWidth(null)) / 2, 0,  
                        destImage.getWidth(null), destImage.getHeight(null),  
                        Color.white, null);  
            graphic.dispose();  
            destImage = image;  
        }  
        return (BufferedImage) destImage;  
    }
    
  
    public static void main(String[] args) throws Exception{  
 
    	    String content="现今，黑白相间的二维码伴随智能手机的快速发展，逐渐进入大家的视野。因其信息容量大、易制作，成本低，持久耐用。容错能力强等特点。目前在日、韩等国家二维码应用已非常普遍，普及率高达96%以上，而中国的二维码应用也在飞速发展，2012年成为中国的二维码元年，据统计，目前全国每月扫码量超过1.6亿次。如此神速发展的二维码已应用在生活的方方面面，如电子凭证服务、公交信息服务、企业网站链接、个人定位服务、手机应用下载、包装附加信息、物流跟踪管理、安全信息服务、旅游信息提示身份识别服务、金融防伪鉴定、产品信息溯源、移动会员服务等。";
 
	    	String space=" ";
	    	int spacelen=110-content.length();
	    	for(int i=0;i<spacelen;i++){
	    		space+=" ";
	    	}
	    	System.out.println(space.toCharArray().length);
	    	System.out.println(content.length());
	    	content=content+space;
	    	System.out.println(content.length());
    		//String content="北京华电宝光电气成套设备有限公司";
    		String destImagePath="d:\\reportpng.png";
 
	    	int width=75,height=75;
 
    		//ImageIO.write(noLogoGenBarcode(content, width, height), "jpg", new File(destImagePath)); 
            System.out.println("完成");
            QRCodeUtil.encode(content,  width,height, "D:/ntcisys/QrcodeCenter/basePic.png", destImagePath);
     
    }
}
