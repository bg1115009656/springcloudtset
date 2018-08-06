import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: pdftest
 * @description: iText
 * @author: gbai
 * @create: 2018-06-05 11:28
 **/
public class ItextTest {
    private static String CHINESE_FONT = "simfang.ttf";//仿宋字体，在C:/Windows/fonts
    public static final Rectangle PAGE_SIZE = PageSize.A4;
    public static final float MARGIN_LEFT = 50;
    public static final float MARGIN_RIGHT = 50;
    public static final float MARGIN_TOP = 50;
    public static final float MARGIN_BOTTOM = 50;

    public static void  createPDF(String fileName,String content) throws DocumentException, IOException {
        File file = new File(fileName);//生成的文件
        FileOutputStream fout = new FileOutputStream(file);//输出流
        Document document = new Document(PAGE_SIZE, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM);//页面大小以及布局
        PdfWriter.getInstance(document, fout);//将文档添加的输出流
        document.open();//打开文档准备写入
        BaseFont baseFont = BaseFont.createFont(CHINESE_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);//创建一个支持中文的字体
        Font chinese = new Font(baseFont, 20, Font.BOLDITALIC, Color.red);
        document.add(new Paragraph(content, chinese));//写入内容　　　　　　
        document.close();//关闭文档
    }
    public static void main(String[] args) throws DocumentException, IOException {
        createPDF("2.pdf", "中文测试pdf文档！");
    }
}
