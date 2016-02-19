package com.e12e.service;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetList {

	/**
	 * ����course_list.html�ļ�
	 * @throws Exception 
	 */
	public static void doGetList(Elements videos, String savePath) throws Exception {

		int curruntGlobalCount = 0;
		FileWriter htmlWriter = new FileWriter(savePath + "course_list.html");

		String htmlText = "<!DOCTYPE html><html><head>"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
				+ "<title>�γ��б�</title><link type=\"text/css\" rel=\"stylesheet\" href=\""
				+ "http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css\" />"
				+ "</head><body><table  data-role=table data-mode=reflow class=\"ui-responsive table-stroke\">"
				+ "<thead><th>No.</th><th>�γ�����</th><th>���ߵ�ַ</th></thead><tbody>";
		
		// ƴ��HTML
		for (Element video : videos) {
			curruntGlobalCount++;

			String[] videoNos = video.attr("href").split("/");
			String url = "http://www.imooc.com" + video.attr("href");

			// ��¼��html�У�
			if (!videoNos[1].equals("video")) {
				String codeName = video.html().trim();
				htmlText += "<tr><td>" + curruntGlobalCount + "</td><td>"
						+ codeName + "</td><td><a href='" + url
						+ "'>ȥĽ������ϰ*</a></td></tr>\n";
				continue;
			}else{
				// �����Ƶ�γ�����
				String videoName = video.html()
						.substring(0, video.html().length() - 7).trim();

				htmlText += "<tr><td>" + curruntGlobalCount + "</td><td>"
						+ videoName + "</td><td><a href='" + url
						+ "'>ȥĽ�����ۿ�</a></td></tr>\n";
			}

		}

		htmlText += "</tbody></table><script src=\"http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js\">"
				+ "</script><script src=\"http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js\"></script></body></html>";
		htmlWriter.write(htmlText);
		htmlWriter.close();

	}
}
