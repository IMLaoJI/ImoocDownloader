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
 * 主类
 * 
 * @author Coande
 *
 */
public class Main {

	public static void main(String[] args) {
		int curruntCount;
		// 一个课程下载一次即可，标记是否需要下载
		boolean flag;

		while (true) {
			curruntCount = 0;
			flag = true;
			int classNo = GetInput.getInputClassNo();
			// 获得要解析的网页文档
			Document doc = null;
			try {
				doc = Jsoup.connect("http://www.imooc.com/learn/" + classNo)
						.get();
			} catch (IOException e) {
				System.out.println("获取课程信息时网络异常！可以稍后重试~\n");
				continue;
			}
			// 获得课程标题：
			String title = doc.getElementsByTag("h2").html();

			// 过滤文件夹非法字符
			title = title.replaceAll("[\\\\/:\\*\\?\"<>\\|]", "#");
			String savePath = "./download/" + title + "/";
			File file = new File(savePath);

			Elements videos = doc.select(".video a");
			if (title.equals("") && videos.size() == 0) {
				System.out.println("抱歉，没有该课程！\n");
				continue;
			}

			// 先进行计算：
			int count = 0;
			for (Element video : videos) {
				String[] videoNos = video.attr("href").split("/");

				// 如果该课程不是视频则不用下载
				if (!videoNos[1].equals("video")) {
					continue;
				}
				count++;
			}
			System.out.print("\n要下载的课程标题为【" + title + "】，");
			System.out.println("共 " + videos.size() + " 节课程，其中视频课程有 " + count
					+ " 节\n");

			int videoDef = GetInput.getInputVideoDef();

			System.out.println("\n正在下载，请耐心等待…\n");

			// 遍历所有视频
			for (Element video : videos) {
				curruntCount++;
				Document jsonDoc;
				String[] videoNos = video.attr("href").split("/");

				// 控制课程相关信息只获取一次
				if (flag) {
					// 创建课程文件夹
					file.mkdirs();
					// 获得课程信息进行保存
					try {
						GetInfo.doGetInfo(classNo, title);

						System.out.println("course_info.txt\t生成成功！");
					} catch (Exception e2) {
						System.out.println("生成course_info.txt时出现异常！");

					}

					// 生成course_list.html
					try {
						GetList.doGetList(videos, savePath);
						System.out.println("course_list.html\t生成成功！");
					} catch (Exception e1) {
						System.out.println("生成course_list.html时出现异常！");
					}

					// 获取课程资料附件
					try {
						GetFile.doGetFile(videoNos[2], title);
						System.out.println("课程资料附件\t下载成功！\n");
					} catch (IOException e) {
						System.out.println("下载课程资料附件时出现异常！\n");
					}

					flag = false;
				}

				// 如果该课程不是视频则不用下载
				if (!videoNos[1].equals("video")) {
					continue;
				}

				// 获得视频课程名称并过滤特殊字符
				String videoName = video.html()
						.substring(0, video.html().length() - 7).trim();
				videoName = videoName.replaceAll("[\\\\/:\\*\\?\"<>\\|]", "#");
				String videoNo = videoNos[2];

				// 获取视频下载地址
				try {
					jsonDoc = Jsoup.connect(
							"http://www.imooc.com/course/ajaxmediainfo/?mid="
									+ videoNo + "&mode=flash").get();
				} catch (IOException e) {
					System.out.println("【" + curruntCount + "】" + videoName
							+ "\t网络异常，地址获取失败！");
					continue;
				}
				String jsonData = jsonDoc.text();
				JSONObject jsonObject = new JSONObject(jsonData);
				JSONArray mpath = jsonObject.optJSONObject("data")
						.optJSONObject("result").optJSONArray("mpath");
				String downloadPath = mpath.getString(videoDef).trim();

				// 进行下载
				try {
					DownloadFile.downLoadFromUrl(downloadPath, videoName
							+ ".mp4", savePath);
					System.out.println("【" + curruntCount + "】" + videoName
							+ " \t下载成功！");
				} catch (IOException e) {
					System.out.println("【" + curruntCount + "】：\t" + videoName
							+ " \t网络异常，下载失败！");
				}

			}

			System.out
					.println("\n【"
							+ title
							+ "】课程的下载任务已完成！！！已下载到该程序所在目"
							+ "录download文件夹下。\n慕课网视频批量下载工具 v1.3  By Coande"
							+ "\n-------------------------------------------------------\n");
		}
	}

}
