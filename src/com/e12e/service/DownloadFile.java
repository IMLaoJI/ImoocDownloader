package com.e12e.service;

import java.io.ByteArrayOutputStream;
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

		//文件不存在才进行下载
		if (!file.exists()) {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时为3秒
			conn.setConnectTimeout(3 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			// 得到输入流
			InputStream inputStream = conn.getInputStream();
			// 获取自己数组
			byte[] getData = readInputStream(inputStream);

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(getData);
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
}
