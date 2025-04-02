package com.annexure.one.pfm.details.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class ValidateFileName {

	public static List<String> fileNames=new ArrayList<String>();
	static {
	fileNames.add("Annexure I - PFM AUC Details.xlsx");

	}
	
	public static boolean isValidfile(String fileName) {
		boolean isValidFile=false;
		if(fileNames.contains(fileName)) {
			isValidFile=true;
		}
		_log.info("is Valid file ::   "+isValidFile +  "        filName  ::::         "+fileName);
		return isValidFile;
	}
	public static boolean validatefileNames(List<String> uploadedFileNames) {
		boolean isValidFile=true;
		for(String fileName:uploadedFileNames) {
			if(!fileNames.contains(fileName)) {
				isValidFile=false;
			}
			_log.info("is Valid file ::   "+isValidFile +  "        filName  ::::         "+fileName);
		}
		
		return isValidFile;
	}
	private static Log _log = LogFactoryUtil.getLog(ValidateFileName.class);
}
