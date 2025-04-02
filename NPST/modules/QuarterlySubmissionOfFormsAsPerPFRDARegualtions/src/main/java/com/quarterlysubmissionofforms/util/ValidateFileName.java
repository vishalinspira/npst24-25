package com.quarterlysubmissionofforms.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class ValidateFileName {

	public static List<String> fileNames=new ArrayList<String>();
	static {
	fileNames.add("Form 1 - Report of transaction in securities by KP.pdf");
	fileNames.add("Form 2 and 3 - Quarterly Compliance Forms.xlsx");
	fileNames.add("Form - 4 Overview of Portfolio Positioning And Evaluation.pdf");
	fileNames.add("Form 3 Report by Director and KP.pdf");
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
