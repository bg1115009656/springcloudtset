import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: pdftest
 * @description:
 * @author: gbai
 * @create: 2018-06-11 10:07
 **/
public class GetPosition2 {
    private static int i = 0;
    private static com.itextpdf.awt.geom.Rectangle2D.Float boundingRectange =null;

    private static StringBuilder content;

    private static List<Object[]> arrays = new ArrayList();



    private static List<Object[]> getKeyWords(String filePath, final String keyWord) {

        try {
            PdfReader pdfReader = new PdfReader(filePath);
            int pageNum = pdfReader.getNumberOfPages();
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);

            for (i = 1; i < pageNum; i++) {
                content = new StringBuilder();
                boundingRectange =new com.itextpdf.awt.geom.Rectangle2D.Float();
                pdfReaderContentParser.processContent(i, new RenderListener() {
                    public void beginTextBlock() {

                    }

                    public void renderText(TextRenderInfo textRenderInfo) {
                        String text = textRenderInfo.getText(); // 整页内容
                        content.append(text);

                        boundingRectange= textRenderInfo.getBaseline().getBoundingRectange();

                    }

                    public void endTextBlock() {

                    }

                    public void renderImage(ImageRenderInfo imageRenderInfo) {

                    }
                });

                if (null != content && content.toString().contains(keyWord)) {
                    Object[] resu = new Object[4];
                    resu[0] = content;
                    resu[1] = boundingRectange.x;
                    resu[2] = boundingRectange.y;
                    resu[3] = i;
                    arrays.add(resu);
                }

                //    System.out.println("第"+i+"页，内容："+content);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrays;
    }

    public static void main(String[] args) {
        String path = "C:\\document\\e鉴证服务接入说明3.pdf";
        List<Object[]> keyWords = getKeyWords(path, "供货商签名处：");
        for (int i = 0;i < keyWords.size(); i++){
            System.out.println(Arrays.toString(keyWords.get(i)));
        }
    }
}
