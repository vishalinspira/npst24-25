package nps.common.service.util;

import com.daily.average.service.model.ReportUploadFileLog;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PreviewFileURLUtil {

	private static final Log _log = LogFactoryUtil.getLog(PreviewFileURLUtil.class);
	private static final String STATUS="status";
	private static final String STATUS_MESSAGE="message";
	
	public static JSONArray getPreviewFileURL(ThemeDisplay themeDisplay, List<ReportUploadFileLog> reportUploadFileLogs) {
		JSONArray urlArray = JSONFactoryUtil.createJSONArray();
		reportUploadFileLogs=reportUploadFileLogs.stream().sorted(Comparator.comparing(ReportUploadFileLog::getFileEntryId)).collect(Collectors.toList());
		int i = 0;
		List<String> excelFormats = Arrays.asList("xls","xlsx");
		for(ReportUploadFileLog fileLog: reportUploadFileLogs) {
			JSONObject object = JSONFactoryUtil.createJSONObject();
			try {
				FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileLog.getFileEntryId());
				object.put("reportUploadFileLogId",fileLog.getReportUploadFileLogId());
				object.put("signature",fileLog.getSignature());
				//_log.info("signature"+fileLog.getSignature());
				object.put("fileEntryId",fileLog.getFileEntryId());
				object.put("downloadURL", DLURLHelperUtil.getDownloadURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK));
				object.put("version", ++i);
				object.put("isExcel", excelFormats.contains(fileEntry.getExtension()));
				if(excelFormats.contains(fileEntry.getExtension())) {
					object.put("previewObject", generatePreviewCode(fileLog.getFileEntryId()));
				}else {
					JSONObject previewObject = JSONFactoryUtil.createJSONObject();
					previewObject.put(STATUS, Boolean.TRUE);
					previewObject.put("previewURL", DLURLHelperUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK));
					
					object.put("previewObject", previewObject);
				}
				urlArray.put(object);
				
			} catch (PortalException e) {
				_log.error("Exception on fetching the url : "+e.getMessage(),e);
			}
		}
		
		return urlArray;
	}
	
	public static JSONArray getPreviewMultiFileURL(ThemeDisplay themeDisplay, List<ReportUploadFileLog> reportUploadFileLogs) {
		JSONArray urlArray = JSONFactoryUtil.createJSONArray();
		reportUploadFileLogs=reportUploadFileLogs.stream().sorted(Comparator.comparing(ReportUploadFileLog::getFileEntryId)).collect(Collectors.toList());
		int i = 0;
		List<String> excelFormats = Arrays.asList("xls","xlsx");
		for(ReportUploadFileLog fileLog: reportUploadFileLogs) {
			JSONObject object = JSONFactoryUtil.createJSONObject();
			try {
				if(Validator.isNotNull(fileLog.getFileList())) {
					try {
						JSONArray fileListArray = JSONFactoryUtil.createJSONArray();
						JSONArray fileList = JSONFactoryUtil.createJSONArray(fileLog.getFileList());
						fileList.iterator();
						for (int j = 0; j < fileList.length(); j++) {
							Long fileEntryID = fileList.getLong(j);
							_log.info("fileList fileEntryID---"+fileEntryID);
							FileEntry fileListEntry = DLAppServiceUtil.getFileEntry(fileEntryID);
							JSONObject fileEntryObj = JSONFactoryUtil.createJSONObject();
							fileEntryObj.put("fileEntryId", fileEntryID);
							fileEntryObj.put("downloadURL", DLURLHelperUtil.getDownloadURL(fileListEntry, fileListEntry.getFileVersion(), themeDisplay, StringPool.BLANK));
							fileListArray.put(fileEntryObj);
						}
						object.put("fileList", fileListArray);
					} catch (Exception e) {
						_log.error("Exception on fetching filelist url : "+e.getMessage(),e);
					}
				}
					FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileLog.getFileEntryId());
					object.put("reportUploadFileLogId",fileLog.getReportUploadFileLogId());
					object.put("signature",fileLog.getSignature());
					//_log.info("signature"+fileLog.getSignature());
					object.put("fileEntryId",fileLog.getFileEntryId());
					object.put("downloadURL", DLURLHelperUtil.getDownloadURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK));
					object.put("version", ++i);
					object.put("isExcel", excelFormats.contains(fileEntry.getExtension()));
					if(excelFormats.contains(fileEntry.getExtension()))
						object.put("previewObject", generatePreviewCode(fileLog.getFileEntryId()));
					else {
						JSONObject previewObject = JSONFactoryUtil.createJSONObject();
						previewObject.put(STATUS, Boolean.TRUE);
						previewObject.put("previewURL", DLURLHelperUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK));
						
						object.put("previewObject", previewObject);
					}
				
				
				urlArray.put(object);
				
			} catch (PortalException e) {
				_log.error("Exception on fetching the url : "+e.getMessage(),e);
			}
		}
		
		return urlArray;
	}
	
	public static JSONArray getPreviewMultiFileURL2(ThemeDisplay themeDisplay, List<ReportUploadFileLog> reportUploadFileLogs) {
		JSONArray urlArray = JSONFactoryUtil.createJSONArray();
		reportUploadFileLogs=reportUploadFileLogs.stream().sorted(Comparator.comparing(ReportUploadFileLog::getFileEntryId)).collect(Collectors.toList());
		int i = 0;
		List<String> excelFormats = Arrays.asList("xls","xlsx");
		for(ReportUploadFileLog fileLog: reportUploadFileLogs) {
			JSONObject object = JSONFactoryUtil.createJSONObject();
			try {
				if(Validator.isNotNull(fileLog.getFileList())) {
					try {
						JSONArray fileListArray = JSONFactoryUtil.createJSONArray();
						JSONArray fileList = JSONFactoryUtil.createJSONArray(fileLog.getFileList());
						fileList.iterator();
						for (int j = 0; j < fileList.length(); j++) {
							JSONObject jo = fileList.getJSONObject(j);
							Long fileEntryID = jo.getLong("fileEntryId");
							_log.info("fileList fileEntryID---"+fileEntryID);
							FileEntry fileListEntry = DLAppServiceUtil.getFileEntry(fileEntryID);
							JSONObject fileEntryObj = JSONFactoryUtil.createJSONObject();
							fileEntryObj.put("fileEntryId", fileEntryID);
							fileEntryObj.put("downloadURL", DLURLHelperUtil.getDownloadURL(fileListEntry, fileListEntry.getFileVersion(), themeDisplay, StringPool.BLANK));
							fileListArray.put(fileEntryObj);
						}
						object.put("fileList", fileListArray);
					} catch (Exception e) {
						_log.error("Exception on fetching filelist url : "+e.getMessage(),e);
					}
				}
					FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileLog.getFileEntryId());
					object.put("reportUploadFileLogId",fileLog.getReportUploadFileLogId());
					object.put("signature",fileLog.getSignature());
					//_log.info("signature"+fileLog.getSignature());
					object.put("fileEntryId",fileLog.getFileEntryId());
					object.put("downloadURL", DLURLHelperUtil.getDownloadURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK));
					object.put("version", ++i);
					object.put("isExcel", excelFormats.contains(fileEntry.getExtension()));
					if(excelFormats.contains(fileEntry.getExtension()))
						object.put("previewObject", generatePreviewCode(fileLog.getFileEntryId()));
					else {
						JSONObject previewObject = JSONFactoryUtil.createJSONObject();
						previewObject.put(STATUS, Boolean.TRUE);
						previewObject.put("previewURL", DLURLHelperUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK));
						
						object.put("previewObject", previewObject);
					}
				
				
				urlArray.put(object);
				
			} catch (PortalException e) {
				_log.error("Exception on fetching the url : "+e.getMessage(),e);
			}
		}
		
		return urlArray;
	}
	
	private static JSONObject generatePreviewCode(long fileEntryId) {
		JSONObject previewobject = JSONFactoryUtil.createJSONObject();
		 if (fileEntryId > 0) {
	            InputStream inputStream = null;
	            String fileExtention = StringPool.BLANK;
	            try {
	            	FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);
	            	fileExtention = fileEntry.getExtension();
	            	inputStream = FileEntryUtil.getContentStream(fileEntry);
		            _log.info("Is File exist : "+inputStream);
	            }catch (PortalException e) {
	            	_log.error("Error while downloading file : "+e);
	            	
	            	previewobject.put(STATUS, false);
	            	previewobject.put(STATUS_MESSAGE, "Provided file not getting downloaded from tab for preview.");
	            }
	            if (null != inputStream) {
	            	_log.info("Xlsx File downloaded properly and file is exist. Now convert file to PDF and then to base64 code.");
	            	try {
		            	String base64code = PreviewFileURLUtil.documentConvert(inputStream, fileExtention, "pdf").trim();
		            	previewobject.put(STATUS, true);
		            	previewobject.put("baseCode", base64code);
	            	} catch (Exception e) {
						_log.error("Error on converting the xlsx file to pdf: "+e);
						previewobject.put(STATUS, false);
						previewobject.put(STATUS_MESSAGE, "Error on reading the xlsx file to generate preview. ");
					}
	            }else {
	            	_log.info("File Does not exist.");
	            	previewobject.put(STATUS, false);
	            	previewobject.put(STATUS_MESSAGE, "Provided file not getting downloaded from tab for preview.");
	            }
	        }else {
	        	_log.info("File is not valid");
	        	previewobject.put(STATUS, false);
	        	previewobject.put(STATUS_MESSAGE, "Please provide a valid file url");
	        }
		 
		 return previewobject;
	}
	
	public static String documentConvert(InputStream inputStream, String sourceExtension, String targetExtension) {
		String result = StringPool.BLANK;
		_log.info("xlsx File to be converted to pdf : "+inputStream);
		
		try {
			_log.info("Converting xlsx to pdf.");
			Timestamp stamp = new Timestamp(System.currentTimeMillis());
			File image = DocumentConversionUtil.convert(String.valueOf(stamp.getTime()), inputStream, sourceExtension, targetExtension);
			_log.info("Converted PDF file : "+image);
			if (Validator.isNotNull(image)) {
				result = Base64.getEncoder().encodeToString(FileUtil.getBytes(image));
			}

		} catch (IOException e) {
			_log.error("***Error occured while converting the document****" + e.getMessage(), e);
		}

		return result;
	}
	
}
