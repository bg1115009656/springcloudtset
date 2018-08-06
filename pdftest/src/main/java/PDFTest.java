import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PDFTest {
    public static void main(String[] args) {

        try {
            PDDocument document = PDDocument.load(new File("C:\\document\\e签宝个人银行四要素实名认证服务对接说明v2.0.pdf"));

            if(!document.isEncrypted()) {
//                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
//                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);

                String[] lines = pdfFileInText.split("\\r?\\n");
                PDPage page = document.getPage(document.getNumberOfPages()-1);
                for (int i = 0;i < lines.length;i++){
                    System.out.println(lines[i]);
                    if ("gbai".equals(lines[i])){
                        System.out.println(i);
                    }
                }

            }

        } catch (InvalidPasswordException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
    private static String CHINESE_FONT = "simfang.ttf";
    @Test
    public void test2() {
        File file = new File("C:\\document\\e签宝个人银行四要素实名认证服务对接说明v2.0.pdf");
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\document\\e签宝个人银行四要素实名认证服务对接说明v2.0.pdf"));
            document.open();
            PdfContentByte canves = writer.getDirectContentUnder();
            writer.setCompressionLevel(0);
            canves.saveState();
            canves.beginText();
            canves.moveText(100,100);
            canves.setFontAndSize(BaseFont.createFont(CHINESE_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED),12);
            canves.showText("证件类型：");
            canves.endText();
            canves.restoreState();
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test3() throws IOException, DocumentException {
        PdfReader reader = new PdfReader("C:\\document\\e鉴证服务接入说明3.pdf");
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("C:\\document\\e鉴证服务接入说明5.pdf"));
        PdfContentByte canves = stamper.getUnderContent(1);
        canves.saveState();
        canves.beginText();
        canves.moveText(375,81);
        canves.setFontAndSize(BaseFont.createFont(CHINESE_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED),12);
        canves.showText("白刚");
        canves.endText();
        Image image = Image.getInstance("C:\\document\\u1161.png");
        image.scaleAbsolute(50,50);
        image.setAbsolutePosition(0,700);
        canves.addImage(image);
        canves.restoreState();
        stamper.close();
        reader.close();
    }

    /**
     * 字母  日期或时间元素  表示  示例
     * G      Era 标志符     Text  AD
     * y     年              Year  1996; 96
     * M     年中的月份      Month  July; Jul; 07
     * w     年中的周数      Number  27
     * W     月份中的周数    Number  2
     * D     年中的天数      Number  189
     * d     月份中的天数    Number  10
     * F     月份中的星期    Number  2
     * E     星期中的天数    Text  Tuesday; Tue
     * a     Am/pm 标记      Text  PM
     * H     一天中的小时数（0-23）  Number  0
     * k     一天中的小时数（1-24）  Number  24
     * K     am/pm 中的小时数（0-11）  Number  0
     * h     am/pm 中的小时数（1-12）  Number  12
     * m     小时中的分钟数    Number  30
     * s     分钟中的秒数      Number  55
     * S     毫秒数            Number  978
     * z     时区  General time zone  Pacific Standard Time; PST; GMT-08:00
     * Z     时区  RFC 822    time zone  -0800
     */
    @Test
    public void test4(){
        long millis = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = format.format(millis);
        Date date = new Date();
        System.out.println(s +"--"+ date);
    }

        public static String md5Password(String password) {

            try {
                // 得到一个信息摘要器
                MessageDigest digest = MessageDigest.getInstance("md5");
                byte[] result = digest.digest(password.getBytes());
                StringBuffer buffer = new StringBuffer();
                // 把每一个byte 做一个与运算 0xff;
                for (byte b : result) {
                    // 与运算
                    int number = b & 0xff;// 加盐
                    String str = Integer.toHexString(number);
                    if (str.length() == 1) {
                        buffer.append("0");
                    }
                    buffer.append(str);
                }

                // 标准的md5加密后的结果
                return buffer.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return "";
            }
        }
        @Test
        public void test5(){
            System.out.println(md5Password("baigang"));
            System.out.println(md5Password("baigang"));

        }

}
