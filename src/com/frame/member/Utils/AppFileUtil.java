package com.frame.member.Utils;

import java.io.File;
import java.io.IOException;

public class AppFileUtil {

	public static File createFile(String filePath) {
		File file = new File(filePath);
		File parentFile = file.getParentFile();
		if (!parentFile.exists())
			parentFile.mkdirs();
		if (file.exists())
			file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
