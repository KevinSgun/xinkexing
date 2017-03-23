package com.zitech.framework.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;

public class IoUtil {

	private static final int BUFFER_SIZE = 8 * 1024;

	public static String toString(InputStream input) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw);
		return sw.toString();
	}

	public static void deleteFile(String path){
		File file=new File(path);
		if(file.exists()&&file.isFile()){
			file.delete();
		}
	}
	public static void copyFileToDirectory(File src, File dir) throws IOException {
		if (dir == null) {
			throw new NullPointerException("destination must not be null");
		}
		if (dir.exists() && dir.isDirectory() == false) {
			throw new IllegalArgumentException("destination is not directory");
		}
		copy(src, new File(dir, src.getName()));
	}

	public static void copy(InputStream input, Writer output) throws IOException {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);
	}

	public static int copy(Reader input, Writer output) throws IOException {
		long count = 0;
		char[] buffer = new char[BUFFER_SIZE];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	public static void copy(File src, File dest) throws IOException {
		if (src == null) {
			throw new NullPointerException("source must not be null");
		}
		if (dest == null) {
			throw new NullPointerException("distination must not be null");
		}
		if (src.isDirectory()) {
			throw new IOException("source is a directory");
		}
		if (src.getCanonicalPath().equals(dest.getCanonicalPath())) {
			throw new IOException("source and destination are the same");
		}
		if (dest.getParentFile() != null && dest.getParentFile().exists() == false) {
			if (dest.getParentFile().mkdirs() == false) {
				throw new IOException("destination directory cannot be created");
			}
		}
		if (dest.exists() && dest.canWrite() == false) {
			throw new IOException("destination exists but is read-only");
		}
		doCopy(src, dest);
	}

	public static void doCopy(File src, File dest) throws IOException {
		if (dest.exists() && dest.isDirectory()) {
			throw new IOException("destination is a derectory");
		}
		FileInputStream input = new FileInputStream(src);
		try {
			FileOutputStream output = new FileOutputStream(dest);
			try {
				copy(input, output);
			} finally {
				closeQuietly(output);
			}
		} finally {
			closeQuietly(input);
		}
		if (src.length() != dest.length()) {
			throw new IOException("Failed to copy full content from src to destination");
		}
	}

	public static int copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return (int) count;
	}

	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File" + file + " is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File" + file + " cannot be read");
			}
		} else {
			throw new FileNotFoundException("File" + file + " does not exist");
		}
		return new FileInputStream(file);
	}

	public static FileOutputStream openOutputStream(File file) throws IOException {
		if (file == null) {
			throw new IOException("File " + file + " is a Null");
		} else if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File " + file + " is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException("File " + file + " cannot be writen");
			}
		} else {
			File parent = file.getParentFile();
			if (parent != null && parent.exists() == false) {
				if (parent.mkdirs() == false) {
					throw new IOException("File " + file + " could not be created");
				}
			}
		}
		return new FileOutputStream(file);
	}

	public static void closeQuietly(OutputStream output) {
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException e) {

		}
	}

	public static void closeQuietly(InputStream input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException e) {

		}
	}

//	public static void clearDir(File parent) {
//		if (parent.exists()) {
//			if (parent.isDirectory()) {
//				File[] files = parent.listFiles();
//				for (File file : files) {
//					clearDir(file);
//				}
//			} else {
//				parent.delete();
//			}
//		}
//	}
//
//	public static double sizeOf(File file) {
//		// 判断文件是否存在
//		if (file.exists()) {
//			// 如果是目录则递归计算其内容的总大小
//			if (file.isDirectory()) {
//				File[] childFiles = file.listFiles();
//				double size = 0;
//				for (File f : childFiles)
//					size += sizeOf(f);
//				return size;
//			} else {
//				double size = (double) file.length() / 1024 / 1024;
//				return size;
//			}
//		} else {
//			return 0;
//		}
//	}

	public static float getTotalCacheSize(Context context) throws Exception {
		long cacheSize = getFolderSize(context.getCacheDir());
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			cacheSize += getFolderSize(context.getExternalCacheDir());
		}
		return (float) cacheSize/1024f /1024f;
	}


	public static void clearAllCache(Context context) {
		deleteDir(context.getCacheDir());
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			deleteDir(context.getExternalCacheDir());
		}
	}

	private static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	// 获取文件
	//Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
	//Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				// 如果下面还有文件
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 格式化单位
	 *
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
//            return size + "Byte";
			return "0K";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}

}
