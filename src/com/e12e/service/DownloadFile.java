package com.e12e.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载视频类
 * 
 * @author Coande
 *
 */
public class DownloadFile {
	public static void downLoadFromUrl(String urlStr, String fileName,
			String savePath) throws IOException {
		// 文件保存位置
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File file = new File(saveDir + File.separator + fileName);

		// 文件不存在才进行下载
		if (!file.exists()) {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时为10秒
			conn.setConnectTimeout(10 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

			// 得到输入流
			InputStream inputStream = conn.getInputStream();

			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int len;
			while ((len = inputStream.read(temp)) != -1) {
				fos.write(temp, 0, len);
			}

			fos.close();
			inputStream.close();
		}
	}

}
