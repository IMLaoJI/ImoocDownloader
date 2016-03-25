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
	public static void doGetFile(int classNo , String className) throws IOException {
		// ���Ҫ��������ҳ�ĵ�
		Document doc = null;
		doc = Jsoup.connect("http://www.imooc.com/learn/" + classNo).get();

		Elements efilePaths = doc.select("ul.downlist li");

		// �����������и���
		for (Element efilePath : efilePaths) {
			String filePath = efilePath.select("a").attr("href");
			String[] s = filePath.split("\\.");
			String lastName = s[s.length - 1];
			String fileName = efilePath.select("span").text();
			DownloadFile.downLoadFromUrl(filePath, fileName + "." + lastName,
					"./download/" + className + "/�γ�����/");
		}
	}
}
