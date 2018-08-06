import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @program: pdftest
 * @description: ItextRead
 * @author: gbai
 * @create: 2018-06-05 11:43
 **/
public class ItextReadPDF {
    public static void main(String[] args) {
        File file = new File("C:\\document\\e签宝个人银行四要素实名认证服务对接说明v2.0.pdf");
        RandomAccessRead in = null;
        try {
            in = new RandomAccessBufferedFileInputStream(file);
            PDFParser parser = new PDFParser(in);
            parser.parse();
            PDDocument pdDocument = parser.getPDDocument();
            PDPage page = pdDocument.getPage(0);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pdDocument);
            String[] lines = text.split("\\r?\\n");
            for (int i = 0;i < lines.length;i++){
                System.out.println(lines[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
