package com.ste.enginestreamportal.util;

import java.math.BigDecimal;

public class Utils {

	//For strings validation
	 public static boolean isNullOrEmpty(String str) {
	        if(str == null || str.isEmpty())
	            return true;
	        return false;
	    }
	 
	 public static boolean emailAddressNotMatchesFormat(String str) {
		 String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	      if(str.matches(regex))
	    	  return false;
	      return true;
	 }
	 
	 public static boolean isZeroOrNull(Long num) {
		 if(num == null || num == 0)
			 return true;
		 return false;
	 }
	 public static boolean isZeroOrNull(BigDecimal num) {
		 if(num == null || num.equals(BigDecimal.ZERO))
			 return true;
		 return false;
	 }
 }
