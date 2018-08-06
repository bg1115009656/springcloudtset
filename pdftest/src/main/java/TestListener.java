import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: pdftest
 * @description:
 * @author: gbai
 * @create: 2018-06-06 09:08
 **/
public class TestListener implements RenderListener {

    //用来存放文字的矩形
    List<Rectangle2D.Float> rectText = new ArrayList<Rectangle2D.Float>();
    //用来存放文字
    List<String> textList = new ArrayList<String>();
    //用来存放文字的y坐标
    List<Float> listY = new ArrayList<Float>();
    //用来存放每一行文字的坐标位置
    List<Map<String,Rectangle2D.Float>> rows_text_rect = new ArrayList<Map<String,Rectangle2D.Float>>();
    //PDF文件的路径
    protected String filepath = null;
    String keyWords = "";

    public TestListener(){

    }

    public void beginTextBlock() {

    }

    public void renderText(TextRenderInfo renderInfo) {
        String text = renderInfo.getText();
        System.out.println(text);
        /*if(text.length() > 0){
            RectangularShape rectBase = renderInfo.getBaseline().getBoundingRectange();
            //获取文字下面的矩形
            Rectangle2D.Float rectAscen = renderInfo.getAscentLine().getBoundingRectange();
            //计算出文字的边框矩形
            float leftX = (float) rectBase.getMinX();
            float leftY = (float) rectBase.getMinY()-1;
            float rightX = (float) rectAscen.getMaxX();
            float rightY = (float) rectAscen.getMaxY()+1;

            Rectangle2D.Float rect = new Rectangle2D.Float(leftX, leftY, rightX - leftX, rightY - leftY);
                System.out.println("text:"+text+"--x:"+rect.x + "--y:"+rect.y + "--width:"+rect.width + "--height:"+rect.height);
        *//*    if(text.contains(keyWords)){
                rectText.add(rect);
                Map<String,Rectangle2D.Float> map = new HashMap<String,Rectangle2D.Float>();
                map.put(text,rect);
                rows_text_rect.add(map);
            }*//*

//            if(listY.contains(rect.y)){
//                int index = listY.indexOf(rect.y);
//                float tempx = rect.x > rectText.get(index).x ? rectText.get(index).x : rect.x;
//                rectText.set(index,new Rectangle2D.Float(tempx,rect.y,rect.width + rectText.get(index).width,rect.height));
//                textList.set(index,textList.get(index) + text);
//            }else{
//                rectText.add(rect);
//                textList.add(text);
//                listY.add(rect.y);
//            }


        }*/
    }

    public void endTextBlock() {

    }

    public void renderImage(ImageRenderInfo imageRenderInfo) {

    }
}
