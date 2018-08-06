import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.List;

/**
 * @program: pdftest
 * @description: test
 * @author: gbai
 * @create: 2018-06-05 14:14
 **/
public class TestPDF {
    private static String CHINESE_FONT = "simfang.ttf";//仿宋字体，在C:/Windows/fonts里找的font文件放到src目录下，参见 博客1
    public static final Rectangle PAGE_SIZE = PageSize.A4;
    public static final float MARGIN_LEFT = 50;
    public static final float MARGIN_RIGHT = 50;
    public static final float MARGIN_TOP = 50;
    public static final float MARGIN_BOTTOM = 50;
    public static final float SPACING = 20;

    @Test
    public void test1() throws IOException, DocumentException, com.itextpdf.text.DocumentException {
        File file = new File("C:\\document\\e签宝个人银行四要素实名认证服务对接说明v2.0.pdf");//生成的文件
        FileOutputStream fout = new FileOutputStream(file);//输出流
        Document document = new Document(PAGE_SIZE, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM);//页面大小以及布局
        PdfWriter.getInstance(document, fout);//将文档添加的输出流
        document.open();//打开文档准备写入
        BaseFont baseFont = BaseFont.createFont(CHINESE_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);//创建一个支持中文的字体
        Font chinese = new Font(baseFont, 20.0f, Font.BOLDITALIC, BaseColor.RED);//博客1里的BaseColor在我下载的jar包中没有就用java.awt.color代替了
        //System.out.println(document.getPageNumber());
        document.add(new Paragraph("正在使用iText创建一个包含中文的pdf文档", chinese));//写入内容　　　　　　
        document.close();//关闭文档
    }

    /**
     * @param templatePdfPath 模板pdf路径
     * @param generatePdfPath 生成pdf路径
     * @param data            数据
     */
    public static String generatePDF(String templatePdfPath, String generatePdfPath, Map<String, String> data, BaseFont font, float fontSize) {
        OutputStream fos = null;
        ByteArrayOutputStream bos = null;
        try {
            PdfReader reader = new PdfReader(templatePdfPath);
            bos = new ByteArrayOutputStream();
            /* 将要生成的目标PDF文件名称 */
            PdfStamper ps = new PdfStamper(reader, bos);
            /* 使用中文字体 */
            /* 取出报表模板中的所有字段 */
            AcroFields fields = ps.getAcroFields();
            List<AcroFields.FieldPosition> fieldPositions = fields.getFieldPositions("sign");
            if (fieldPositions.size() > 0){
                fieldPositions.stream().forEach(e -> {
                    System.out.println(e.page +"----"+ e.position.getLeft() +"---"+ e.position.getRight());
                    System.out.println( e.position.getTop() +"---"+ e.position.getHeight());
                    System.out.println(e.position.getBottom() +"---"+ e.position.getWidth());
                    System.out.println(e.position.toString() );
                });
            }
            fillData(fields, data, font, fontSize);
            /* 必须要调用这个，否则文档不会生成的  如果为false那么生成的PDF文件还能编辑，一定要设为true*/
            ps.setFormFlattening(true);
            ps.close();
            fos = new FileOutputStream(generatePdfPath);
            fos.write(bos.toByteArray());
            fos.flush();
            return generatePdfPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    private static void fillData(AcroFields fields, Map<String, String> data, BaseFont baseFont, float textSize) throws IOException, com.itextpdf.text.DocumentException {
        List<String> keys = new ArrayList<String>();
        Map<String, AcroFields.Item> formFields = fields.getFields();
        for (String key : data.keySet()) {
            if (formFields.containsKey(key)) {
                String value = data.get(key);
                fields.setFieldProperty(key, "textfont", baseFont, null);
                fields.setFieldProperty(key, "textsize", textSize, null);
                fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的
                keys.add(key);
            }
        }
        Iterator<String> itemsKey = formFields.keySet().iterator();
        while (itemsKey.hasNext()) {
            String itemKey = itemsKey.next();
            if (!keys.contains(itemKey)) {
                fields.setField(itemKey, " ");
            }
        }
    }

    @Test
    public void fillPDF() throws IOException, DocumentException {
            BaseFont baseFont = BaseFont.createFont(CHINESE_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Map<String, String> data = new HashMap<String, String>();
        //key为pdf模板的form表单的名字，value为需要填充的值
        data.put("name", "111");

        generatePDF("C:\\document\\excelTip.pdf",
                "C:\\document\\excelTip2.pdf", data, baseFont, 14f);
    }

    @Test
    public void updateFile() {
        PdfUtils utils = new PdfUtils();
        utils.setFilePath("C:\\document\\e签宝个人银行四要素实名认证服务对接说明v2.0.pdf");
        utils.setSavePath("C:\\document\\e签宝个人银行四要素实名认证服务对接说明3v2.0.pdf");
        Map<String, String> map = new HashMap<>();
        map.put("姓名：", "白刚.................");
        map.put("银行卡号：", "414521321");
        map.put("身份证号：", "5416351344");
        map.put("银行预留手机号：", "465169841321");
        System.out.println(utils.modifyPDFForm(map));
    }

    @Test
    public void test3() {
        OutputStream fos = null;
        ByteArrayOutputStream bos = null;
        try {
            PdfReader reader = new PdfReader("C:\\document\\修改.pdf");
            bos = new ByteArrayOutputStream();
            /* 将要生成的目标PDF文件名称 */
            PdfStamper ps = new PdfStamper(reader, bos);
            /* 使用中文字体 */
            /* 取出报表模板中的所有字段 */
            AcroFields fields = ps.getAcroFields();
            Map<String, AcroFields.Item> fields1 = fields.getFields();
            fields1.entrySet().stream().forEach(e -> {
                System.out.println(e.getKey() +"----"+ e.getValue());
            });
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getFieldsFromPDF() {
        Set<String> set = new HashSet<>();
        ByteArrayOutputStream bos = null;
        try {
            PdfReader reader = new PdfReader("C:\\document\\修改.pdf");
            bos = new ByteArrayOutputStream();
            /* 将要生成的目标PDF文件名称 */
            PdfStamper ps = new PdfStamper(reader, bos);
            /* 取出报表模板中的所有字段 */
            AcroFields fields = ps.getAcroFields();
            Map<String, AcroFields.Item> fields1 = fields.getFields();
            set = fields1.keySet();
            fields1.entrySet().stream().forEach(e -> {
                System.out.println(e.getKey() +"----"+ e.getValue());
            });
            ps.close();
            reader.close();
            //return set;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //return set;
    }
}
