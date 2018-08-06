import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

/**
 * @program: pdftest
 * @description: testWrite
 * @author: gbai
 * @create: 2018-06-04 18:02
 **/
public class WritePDF {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\document\\e签宝个人银行四要素实名认证服务对接说明v2.0.pdf");
        PDDocument doc = PDDocument.load(file);
        PDPage page = doc.getPage(0);
        PDImageXObject pdImage = PDImageXObject.createFromFile("C:\\document\\u1161.png", doc);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        contentStream.drawImage(pdImage, 70, 250);
        contentStream.close();
        doc.save(file);
        doc.close();



    }
    static class WriteText{
        public static void main(String[] args) throws IOException {
            File file = new File("C:\\document\\e签宝个人银行四要素实名认证服务对接说明v2.0.pdf");
            PDDocument doc = PDDocument.load(file);
            int pages = doc.getNumberOfPages();
            PDPage page = doc.getPage(pages-1);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            contentStream.beginText();
            contentStream.newLineAtOffset(0,1);
            String text = "供应商签名处";
            contentStream.setFont(PDType1Font.SYMBOL, 14);
            contentStream.showText(text);
            contentStream.endText();
            contentStream.close();
            doc.save(file);
            doc.close();
        }
    }
}
