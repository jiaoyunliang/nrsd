package com.rsd.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

public class PdfCreator {


	/**
	 * 生成pdf文件
	 * 
	 * @param elements
	 *            要写入pdf的信息
	 * @param pdfPath
	 *            生成pdf文件位置
	 * @throws Exception
	 */
	public static void createPdf(List<ElementBean> elements, String pdfPath) throws Exception {

		// 创建PDF文档
		Document doc = null;
		FileOutputStream fos = null;
		try {
			doc = new Document(PageSize.A4, 50, 50, 50, 50);

			String pdfFile = StringUtils.cleanPath(pdfPath);
			File f = new File(pdfFile);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			} else {
				if (!f.exists()) {
					f.createNewFile();
				}
			}

			fos = new FileOutputStream(pdfFile);
			PdfWriter writer = PdfWriter.getInstance(doc, fos);
			writer.setStrictImageSequence(true);

			doc.open();
			// 写入图片
			for (ElementBean ele : elements) {
				if (ele.getType().equals(ElementBean.TYPE_IMAGE)) {// 图片
					String location = ele.getContent();
					try{
						Image image = Image.getInstance(location);
						BufferedImage img = null;
						if(ele.getContent().startsWith("http://")){
							img = ImageIO.read(new URL(ele.getContent()));
						} else {
							img = ImageIO.read(new File(ele.getContent()));
						}
						int width = img.getWidth();
						if (width > 700) {
							image.scalePercent((700f / Float.valueOf(width)) * 80f);
						}
						image.setAlignment(Image.MIDDLE);
						doc.add(image);
						doc.newPage();
					}catch(Exception e){
					}
				}

				if (ele.getType().equals(ElementBean.TYPE_PARAGRAPH)) {// 文字

					BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 设置中文字体
					Font headFont = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小

					Paragraph paragraph = new Paragraph(ele.getContent(), headFont);
					paragraph.setAlignment(Paragraph.ALIGN_CENTER);
					doc.add(paragraph);
				}

				if (ele.getType().equals(ElementBean.TYPE_CHUNK)) {
					BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 设置中文字体
					Font headFont = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
					Chunk text = new Chunk(ele.getContent(), headFont);
					doc.add(text);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (doc != null && doc.isOpen())
				doc.close();
			if (fos != null)
				fos.close();
		}

	}
}
