package com.e12e.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetFile {
	/**
	 * ���ؿγ����ϸ���
	 * 
	 * @param videoNo
	 *            һ���γ̵�����С�ڵĿγ̺�
	 * @param className
	 *            �γ����ƣ����ڻ��·������Ÿ���
	 * @throws IOException 
	 */
	public static void doGetFile(String videoNo, String className) throws IOException {
		// ���Ҫ��������ҳ�ĵ�
		Document doc = null;
		doc = Jsoup.connect("http://www.imooc.com/video/" + videoNo).get();

		Elements efilePaths = doc.select(".coursedownload a");

		// �����������и���
		for (Element efilePath : efilePaths) {
			String filePath = efilePath.attr("href");
			String[] s = filePath.split("\\.");
			String lastName = s[s.length - 1];
			String fileName = efilePath.attr("title");
			DownloadFile.downLoadFromUrl(filePath, fileName + "." + lastName,
					"./download/" + className + "/�γ�����/");
		}
	}
}
