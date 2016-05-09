package com.e12e.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAttachFile {
	/**
	 * ���ؿγ����ϸ���
	 * 
	 * @param videoNo
	 *            һ���γ̵�����С�ڵĿγ̺�
	 * @param className
	 *            �γ����ƣ����ڻ��·������Ÿ���
	 * @throws IOException
	 */
	public static void doGetFile(String videoNo, String className)
			throws IOException {
		// ���Ҫ��������ҳ�ĵ�
		Document doc = null;
		String filePath;
		String[] s;
		String lastName;
		String fileName;

		doc = Jsoup.connect("http://www.imooc.com/video/" + videoNo)
				.timeout(10 * 1000).get();

		Elements efilePaths = doc.select(".coursedownload a");

		// �����������и���
		for (Element efilePath : efilePaths) {
			filePath = efilePath.attr("href");
			s = filePath.split("\\.");
			lastName = s[s.length - 1];
			fileName = efilePath.attr("title");
			DownloadFile.downLoadFromUrl(filePath, fileName + "." + lastName,
					"./download/" + className + "/�γ�����/");
		}
	}
}
