package com.ste.enginestreamportal.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CommonMethods {
	
	//file must contain only one dot
	/*
	 * public static boolean isValidImageName(String fileName) {
	 * return (fileName != null && !("".equals(fileName)) &&
	 * fileName.split("[.]").length == 2 && fileName.split("[.]")[1].equals("jpg")
	 * )? true : false;
	 * }
	 */
	
	//file must contain only one dot
		public static boolean isValidImageName(String fileName) {
			String allowedImageTypes[] = {"jpg","png","jpeg"};
			List<String> allowedImageTypesList = Arrays.asList(allowedImageTypes);
			return (fileName != null && fileName.split("[.]").length == 2 && allowedImageTypesList.contains(fileName.split("[.]")[1]) )? true : false;
		}
		
			public static String getFormattedCurrentDateTime() {
				Date date = new Date() ;
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
		        String s = dateFormat.format(date).replaceAll("[ ]+","_");
		        return s;
			}

}
