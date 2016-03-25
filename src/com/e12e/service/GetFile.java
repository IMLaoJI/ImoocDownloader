package com.e12e.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetFile {
	/**
	 * 下载课程资料附件
	 * 
	 * @param videoNo
	 *            一个课程的任意小节的课程号
	 * @param className
	 *            课程名称，用于获得路径，存放附件
	 * @throws IOException 
	 */
	public static void doGetFile(int classNo , String className) throws IOException {
		// 获得要解析的网页文档
		Document doc = null;
		doc = Jsoup.connect("http://www.imooc.com/learn/" + classNo).get();

		Elements efilePaths = doc.select("ul.downlist li");

		// 遍历下载所有附件
		for (Element efilePath : efilePaths) {
			String filePath = efilePath.select("a").attr("href");
			String[] s = filePath.split("\\.");
			String lastName = s[s.length - 1];
			String fileName = efilePath.select("span").text();
			DownloadFile.downLoadFromUrl(filePath, fileName + "." + lastName,
					"./download/" + className + "/课程资料/");
		}
	}
}
