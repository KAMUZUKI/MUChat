package com.mu.utils;

import java.security.MessageDigest;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

/**
 * ¹¤¾ßÀà
 * @author MUZUKI
 *
 */
public class Utils {
	static char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String transferSize(long size) {
		if (size / 1024 / 1024 / 1024 / 1024 > 0) {
			return size / 1024 / 1024 / 1024 / 1024 + "T";
		} else if (size / 1024 / 1024 / 1024 > 0) {
			return size / 1024 / 1024 / 1024 + "G";
		} else if (size / 1024 / 1024 > 0) {
			return size / 1024 / 1024 + "M";
		} else if (size / 1024 > 0) {
			return size / 1024 + "K";
		} else {
			return size + "B";
		}
	}
	
	public static ImageData setImageAlpha(Image image) {
		ImageData fullImageData = image.getImageData();
		int width = fullImageData.width;
		int height = fullImageData.height;
		byte[] alphaData = new byte[height * width];
		for(int y=0;y<height;y++){
			byte[] alphaRow = new byte[width];
			for(int x=0;x<width;x++){
				alphaRow[x] = (byte) ((200 * y) /height);
			}
			System.arraycopy(alphaRow,0,alphaData,y*width,width);
		}
		fullImageData.alphaData = alphaData;
		return fullImageData;
	}
	
	public static String pathInvert(String path) {
		String[] tmpStr = path.split("src");
		return tmpStr[1]; 
	}
}
