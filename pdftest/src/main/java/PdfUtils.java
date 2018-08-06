import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @program: pdftest
 * @description: 修改pdf指定位置内容
 * @author: gbai
 * @create: 2018-06-11 11:57
 **/
public class PdfUtils {
    private String CHINESE_FONT_PATh = "simfang.ttf";//仿宋字体，在C:/Windows/fonts里找的font文件放到src目录下，参见 博客1
    private Integer fontSize = 12;//字体大小，默认为12
    private String filePath = "";//pdf模板读取路径
    private String savePath = "";//pdf生成路径

    public String getCHINESE_FONT_PATh() {
        return CHINESE_FONT_PATh;
    }

    public void setCHINESE_FONT_PATh(String CHINESE_FONT_PATh) {
        this.CHINESE_FONT_PATh = CHINESE_FONT_PATh;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    /**
     * 填充指定页，指定位置的内容
     *
     * @param value
     * @param location
     * @param pageNum
     */
    private String fillData(String value, Rectangle2D.Float location, int pageNum) {
        PdfReader reader = null;
        try {
            reader = new PdfReader(filePath);
            FileOutputStream out = new FileOutputStream(savePath);
            PdfStamper stamper = new PdfStamper(reader,out );
            PdfContentByte canves = stamper.getUnderContent(pageNum);
            canves.saveState();
            canves.beginText();
            canves.moveText(location.x + location.width + 2, location.y);
            canves.setFontAndSize(BaseFont.createFont(CHINESE_FONT_PATh, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), fontSize);
            canves.showText(value);
            canves.endText();
            canves.restoreState();
            stamper.close();
            out.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "文件读取异常";
        } catch (DocumentException e) {
            e.printStackTrace();
            return "文档处理异常";
        }
        return "SUCCESS";
    }

    /**
     *
     * @param data
     * @return
     */
    public String modifyPDFForm(Map<String,String> data) {
        PdfReader reader = null;
        String temp;
        Set<String> strs = data.keySet();
        try {
            for (String str : strs) {
            reader = new PdfReader(filePath);
            //新建一个PDF解析对象
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            //包含了PDF页面的信息，作为处理的对象，此处只获取位置，不需要处理对象
                for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                    //新建一个ImageRenderListener对象，该对象实现了RenderListener接口，作为处理PDF的主要类
                    RanderListenerImpl listener = new RanderListenerImpl();
                    listener.keyWords = str;
                    //解析PDF，并处理里面的文字
                    parser.processContent(i, listener);
                    List<Map<String, Rectangle2D.Float>> text_rect = listener.rows_text_rect;
                    if(text_rect.size() > 0){
                        Map<String, Rectangle2D.Float> stringFloatMap = text_rect.get(0);
                        fillData(data.get(str),stringFloatMap.get(str),i);
                    }
                }
                reader.close();
                temp = filePath;
                filePath = savePath;
                savePath = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "FAIL";
        }
        return filePath;
    }

    /**
     * 利用模板，生成pdf文件
     * @param templatePdfPath 模板路径
     * @param generatePdfPath 生成文件存放路径
     * @param data 填充数据
     * @param font 填充字体
     * @param fontSize 字体大小
     * @return
     */
    public static String generatePDF(String templatePdfPath, String generatePdfPath, Map<String, String> data,BaseFont font,float fontSize) {
        OutputStream fos = null;
        ByteArrayOutputStream bos = null;
        try{
            PdfReader reader = new PdfReader(templatePdfPath);
            bos = new ByteArrayOutputStream();
            /* 将要生成的目标PDF文件名称 */
            PdfStamper ps = new PdfStamper(reader, bos);
            /* 使用中文字体 */
            /* 取出报表模板中的所有字段 */
            AcroFields fields = ps.getAcroFields();
            System.out.println(fields.getFieldPositions("name").toString());
            fillData(fields, data,font,fontSize);
            /* 必须要调用这个，否则文档不会生成的  如果为false那么生成的PDF文件还能编辑，一定要设为true*/
            ps.setFormFlattening(true);
            ps.close();
            fos = new FileOutputStream(generatePdfPath);
            fos.write(bos.toByteArray());
            fos.flush();
            return generatePdfPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     *电子签约
     * @param fields
     * @param data
     * @param baseFont
     * @param textSize
     * @throws IOException
     * @throws com.itextpdf.text.DocumentException
     */
    private static void fillData(AcroFields fields, Map<String, String> data,BaseFont baseFont,float textSize) throws IOException, com.itextpdf.text.DocumentException {
        List<String> keys = new ArrayList<String>();
        Map<String, AcroFields.Item> formFields = fields.getFields();
        for (String key : data.keySet()) {
            if(formFields.containsKey(key)){
                String value = data.get(key);
                fields.setFieldProperty(key, "textfont", baseFont, null);
                fields.setFieldProperty(key, "textsize", textSize, null);
                fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的
                keys.add(key);
            }
        }
        Iterator<String> itemsKey = formFields.keySet().iterator();
        while(itemsKey.hasNext()){
            String itemKey = itemsKey.next();
            if(!keys.contains(itemKey)){
                fields.setField(itemKey, " ");
            }
        }
    }


}