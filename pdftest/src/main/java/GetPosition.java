import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: pdftest
 * @description:
 * @author: gbai
 * @create: 2018-06-06 09:45
 **/
public class GetPosition {

    public static void main(String[] args) throws IOException, DocumentException {
        PdfReader reader = new PdfReader("C:\\document\\e签宝个人银行四要素实名认证服务对接说明v2.0.pdf");
        //新建一个PDF解析对象
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
//        //包含了PDF页面的信息，作为处理的对象，此处只获取位置，不需要处理对象
//        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("C:\\document\\e鉴证服务接入说明4.pdf"));
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            //新建一个ImageRenderListener对象，该对象实现了RenderListener接口，作为处理PDF的主要类
            TestListener listener = new TestListener();
            listener.keyWords = "";
            //解析PDF，并处理里面的文字
            parser.processContent(i, listener);
            //获取文字的矩形边框
            List<Rectangle2D.Float> rectText = listener.rectText;
            List<Map<String, Rectangle2D.Float>> list_text = listener.rows_text_rect;

            for (int k = 0; k < list_text.size(); k++) {
                Map<String, Rectangle2D.Float> map = list_text.get(k);
                for (Map.Entry<String, Rectangle2D.Float> entry : map.entrySet()) {
                    System.out.println(entry.getKey() + "---" + entry.getValue());
                }
            }
        }
    }
}