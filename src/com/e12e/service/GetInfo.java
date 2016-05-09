package com.e12e.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetInfo {
	/**
	 * 生成course_info.txt文件
	 * 
	 * @param classNo
	 *            课程编号，用于遍历获取课程信息
	 * @param className
	 *            课程名称，用于指定路径保存course_info.txt文件
	 * @throws IOException
	 */
	public static void doGetInfo(int classNo, String className)
			throws IOException {
		File file = new File("./download/" + className + "/course_info.txt");

		file.createNewFile();

		FileWriter txtWriter;

		txtWriter = new FileWriter(file);
		// 获得要解析的网页文档
		Document doc = Jsoup.connect("http://www.imooc.com/view/" + classNo)
				.timeout(10 * 1000).get();
		String title = doc.select("h2").html();
		txtWriter.write("【课程】：" + title + "\r\n\r\n");

		String author = doc.select("span.tit a").html();
		txtWriter.write("【讲师】：" + author + "\r\n");

		String time = doc.select(".static-time span").first().text();
		txtWriter.write("【时长】：" + time + "\r\n");

		String hard = doc.select(".statics .static-item span").first().text();
		txtWriter.write("【难度】：" + hard + "\r\n\r\n\r\n");

		String intruc = doc.select(".auto-wrap").html();
		txtWriter.write("【课程介绍】：\r\n" + intruc + "\r\n\r\n\r\n");

		String know = doc.select(".course-info-tip .first dd").html();
		txtWriter.write("【课程须知】：\r\n" + know + "\r\n\r\n\r\n");

		String what = doc.select(".course-info-tip dd").last().html();
		txtWriter.write("【老师告诉你能学到什么？】\r\n" + what + "\r\n\r\n\r\n");

		txtWriter.write("【课程提纲】：\r\n\r\n");
		Elements chapters = doc.select(".chapter-bd");
		for (Element chapter : chapters) {
			String chaptername = chapter.select("h5").html();
			txtWriter.write(chaptername + "\r\n");
			String chapterdesc = chapter.select("p").html();
			txtWriter.write(chapterdesc + "\r\n\r\n");

		}

		txtWriter.close();

	}
}
