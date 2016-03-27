package com.e12e.main;

import java.io.File;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.e12e.service.DownloadFile;
import com.e12e.service.GetFile;
import com.e12e.service.GetInfo;
import com.e12e.service.GetInput;
import com.e12e.service.GetList;

/**
 * ����
 * 
 * @author Coande
 *
 */
public class Main {

	public static void main(String[] args) {
		int curruntCount;
		// һ���γ�����һ�μ��ɣ�����Ƿ���Ҫ����
		boolean flag;

		while (true) {
			curruntCount = 0;
			flag = true;
			int classNo = GetInput.getInputClassNo();
			// ���Ҫ��������ҳ�ĵ�
			Document doc = null;
			try {
				doc = Jsoup.connect("http://www.imooc.com/learn/" + classNo)
						.get();
			} catch (IOException e) {
				System.out.println("��ȡ�γ���Ϣʱ�����쳣�������Ժ�����~\n");
				continue;
			}
			// ��ÿγ̱��⣺
			String title = doc.getElementsByTag("h2").html();

			// �����ļ��зǷ��ַ�
			title = title.replaceAll("[\\\\/:\\*\\?\"<>\\|]", "#");
			String savePath = "./download/" + title + "/";
			File file = new File(savePath);

			Elements videos = doc.select(".video a");
			if (title.equals("") && videos.size() == 0) {
				System.out.println("��Ǹ��û�иÿγ̣�\n");
				continue;
			}

			// �Ƚ��м��㣺
			int count = 0;
			for (Element video : videos) {
				String[] videoNos = video.attr("href").split("/");

				// ����ÿγ̲�����Ƶ��������
				if (!videoNos[1].equals("video")) {
					continue;
				}
				count++;
			}
			System.out.print("\nҪ���صĿγ̱���Ϊ��" + title + "����");
			System.out.println("�� " + videos.size() + " �ڿγ̣�������Ƶ�γ��� " + count
					+ " ��\n");

			int videoDef = GetInput.getInputVideoDef();

			System.out.println("\n�������أ������ĵȴ���\n");

			// ����������Ƶ
			for (Element video : videos) {
				curruntCount++;
				Document jsonDoc;
				String[] videoNos = video.attr("href").split("/");

				// ���ƿγ������Ϣֻ��ȡһ��
				if (flag) {
					// �����γ��ļ���
					file.mkdirs();
					// ��ÿγ���Ϣ���б���
					try {
						GetInfo.doGetInfo(classNo, title);

						System.out.println("course_info.txt\t���ɳɹ���");
					} catch (Exception e2) {
						System.out.println("����course_info.txtʱ�����쳣��");

					}

					// ����course_list.html
					try {
						GetList.doGetList(videos, savePath);
						System.out.println("course_list.html\t���ɳɹ���");
					} catch (Exception e1) {
						System.out.println("����course_list.htmlʱ�����쳣��");
					}

					// ��ȡ�γ����ϸ���
					try {
						GetFile.doGetFile(videoNos[2], title);
						System.out.println("�γ����ϸ���\t���سɹ���\n");
					} catch (IOException e) {
						System.out.println("���ؿγ����ϸ���ʱ�����쳣��\n");
					}

					flag = false;
				}

				// ����ÿγ̲�����Ƶ��������
				if (!videoNos[1].equals("video")) {
					continue;
				}

				// �����Ƶ�γ����Ʋ����������ַ�
				String videoName = video.html()
						.substring(0, video.html().length() - 7).trim();
				videoName = videoName.replaceAll("[\\\\/:\\*\\?\"<>\\|]", "#");
				String videoNo = videoNos[2];

				// ��ȡ��Ƶ���ص�ַ
				try {
					jsonDoc = Jsoup.connect(
							"http://www.imooc.com/course/ajaxmediainfo/?mid="
									+ videoNo + "&mode=flash").get();
				} catch (IOException e) {
					System.out.println("��" + curruntCount + "��" + videoName
							+ "\t�����쳣����ַ��ȡʧ�ܣ�");
					continue;
				}
				String jsonData = jsonDoc.text();
				JSONObject jsonObject = new JSONObject(jsonData);
				JSONArray mpath = jsonObject.optJSONObject("data")
						.optJSONObject("result").optJSONArray("mpath");
				String downloadPath = mpath.getString(videoDef).trim();

				// ��������
				try {
					DownloadFile.downLoadFromUrl(downloadPath, videoName
							+ ".mp4", savePath);
					System.out.println("��" + curruntCount + "��" + videoName
							+ " \t���سɹ���");
				} catch (IOException e) {
					System.out.println("��" + curruntCount + "����\t" + videoName
							+ " \t�����쳣������ʧ�ܣ�");
				}

			}

			System.out
					.println("\n��"
							+ title
							+ "���γ̵�������������ɣ����������ص��ó�������Ŀ"
							+ "¼download�ļ����¡�\nĽ������Ƶ�������ع��� v1.3  By Coande"
							+ "\n-------------------------------------------------------\n");
		}
	}

}
