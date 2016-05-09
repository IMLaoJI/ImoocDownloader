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
	 * ����course_info.txt�ļ�
	 * 
	 * @param classNo
	 *            �γ̱�ţ����ڱ�����ȡ�γ���Ϣ
	 * @param className
	 *            �γ����ƣ�����ָ��·������course_info.txt�ļ�
	 * @throws IOException
	 */
	public static void doGetInfo(int classNo, String className)
			throws IOException {
		File file = new File("./download/" + className + "/course_info.txt");

		file.createNewFile();

		FileWriter txtWriter;

		txtWriter = new FileWriter(file);
		// ���Ҫ��������ҳ�ĵ�
		Document doc = Jsoup.connect("http://www.imooc.com/view/" + classNo)
				.timeout(10 * 1000).get();
		String title = doc.select("h2").html();
		txtWriter.write("���γ̡���" + title + "\r\n\r\n");

		String author = doc.select("span.tit a").html();
		txtWriter.write("����ʦ����" + author + "\r\n");

		String time = doc.select(".static-time span").first().text();
		txtWriter.write("��ʱ������" + time + "\r\n");

		String hard = doc.select(".statics .static-item span").first().text();
		txtWriter.write("���Ѷȡ���" + hard + "\r\n\r\n\r\n");

		String intruc = doc.select(".auto-wrap").html();
		txtWriter.write("���γ̽��ܡ���\r\n" + intruc + "\r\n\r\n\r\n");

		String know = doc.select(".course-info-tip .first dd").html();
		txtWriter.write("���γ���֪����\r\n" + know + "\r\n\r\n\r\n");

		String what = doc.select(".course-info-tip dd").last().html();
		txtWriter.write("����ʦ��������ѧ��ʲô����\r\n" + what + "\r\n\r\n\r\n");

		txtWriter.write("���γ���١���\r\n\r\n");
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
