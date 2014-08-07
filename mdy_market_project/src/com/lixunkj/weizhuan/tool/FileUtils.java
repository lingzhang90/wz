package com.lixunkj.weizhuan.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

public class FileUtils {

	private static FileUtils buildUtils;

	public static FileUtils getInstance() {
		if (buildUtils == null) {
			buildUtils = new FileUtils();
		}
		return buildUtils;
	}

	public static final String FILEFROMNAME = "share.png";
	public static final String TODIR = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "weizhuan_share.png";

	public boolean copyFileFromAssets(Context context, String fileFromName,
			String toDir) {
		try {
			InputStream its = context.getAssets().open(fileFromName);
			int fileLength = its.available();
			File book_file = new File(toDir);
			if (!book_file.exists()) {
				book_file.createNewFile();
			}
			else{
				return true;
			}

			FileOutputStream fots = new FileOutputStream(book_file, true);
			byte[] buffer = new byte[fileLength];
			int readCount = 0; // 已经成功读取的字节的个数
			while (readCount < fileLength) {
				readCount += its
						.read(buffer, readCount, fileLength - readCount);
			}
			fots.write(buffer, 0, fileLength);

			its.close();
			fots.close();
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
}
