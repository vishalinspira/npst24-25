package com.weekly.finalupload.resource;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Weekly.FinalUploadFile.constants.WeeklyFinalUploadFilePortletKeys;
//import compliance.service.model.APY;
//import compliance.service.model.CorporateCG;
//import compliance.service.model.NPSLite;
//import compliance.service.model.SchemeATier_I;
//import compliance.service.model.SchemeCG;
//import compliance.service.model.SchemeCTier_I;
//import compliance.service.model.SchemeCTier_II;
//import compliance.service.model.SchemeETier_I;
//import compliance.service.model.SchemeETier_II;
//import compliance.service.model.SchemeGTier_I;
//import compliance.service.model.SchemeGTier_II;
//import compliance.service.model.SchemeSG;
//import compliance.service.model.TTS;
//import compliance.service.service.APYLocalService;
//import compliance.service.service.CorporateCGLocalService;
//import compliance.service.service.NPSLiteLocalService;
//import compliance.service.service.SchemeATier_ILocalService;
//import compliance.service.service.SchemeCGLocalService;
//import compliance.service.service.SchemeCTier_IILocalService;
//import compliance.service.service.SchemeCTier_ILocalService;
//import compliance.service.service.SchemeETier_IILocalService;
//import compliance.service.service.SchemeETier_ILocalService;
//import compliance.service.service.SchemeGTier_IILocalService;
//import compliance.service.service.SchemeGTier_ILocalService;
//import compliance.service.service.SchemeSGLocalService;
//import compliance.service.service.TTSLocalService;

//@Component(property = { 
//		"javax.portlet.name=" + WeeklyFinalUploadFilePortletKeys.WEEKLYFINALUPLOADFILE,
//		"mvc.command.name=" + WeeklyFinalUploadFilePortletKeys.finalUpload, 
//		}, 
//service = MVCResourceCommand.class)
//
//public class FinalUploadFile implements MVCResourceCommand {
//	
//	private static Log _log = LogFactoryUtil.getLog(FinalUploadFile.class);
//
//	@Override
//	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
//			throws PortletException {
//		
//		_log.info("Serve Resource method");
//		
//		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
//		JSONObject resultJsonObject = fileUpload_1(themeDisplay, resourceRequest);
//		try {
//			PrintWriter writer = resourceResponse.getWriter();
//			writer.write(resultJsonObject.toString());
//		} catch (IOException e) {
//			 _log.error(e);
//		}
//		
//		return false;
//	}
	
//	public JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
//		Long userId = themeDisplay.getUserId();
//		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
//
//		String fileName = uploadPortletRequest.getFileName("finalUploadFile");
//
//		File file = uploadPortletRequest.getFile("finalUploadFile");
//
//		String mimeType = uploadPortletRequest.getContentType("finalUploadFile");
//
//		String title = fileName;
//
//		JSONArray etierIJsonArray = JSONFactoryUtil.createJSONArray();
//		List<SchemeETier_I> eTierIList = new ArrayList<SchemeETier_I>();
//		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
//		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
//		XSSFWorkbook workbook = null;
//		
//		/**
//		 * Create error excel
//		 */
//		XSSFWorkbook errorExcel = new XSSFWorkbook();
//		XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
//		XSSFRow xxx = xx.createRow(1);
//		XSSFCell rowNumCel = xxx.createCell(1);
//		rowNumCel.setCellValue("RowNum");
//		XSSFCell fileNamecell = xxx.createCell(2);
//		fileNamecell.setCellValue("Pension Fund");
//		
//		
//		boolean isexcelhaserror = false;
//		try {
//			if (file != null) {
//				OPCPackage pkg = OPCPackage.open(file);
//				workbook = new XSSFWorkbook(pkg);
//				XSSFSheet sheet = workbook.getSheetAt(0);
//				
//				Iterator<Row> rows = sheet.rowIterator();
//				int rowCount = 1;
//				int errorRowCount = 2;
//
//				rowloop:
//				while (rows.hasNext()) {
//					
//					SchemeETier_I eTierI = (SchemeETier_I) eTier_ILocalService.
//							createSchemeETier_I(CounterLocalServiceUtil.increment(SchemeETier_I.class.getName()));
//					
//					eTierI.setCreatedby(userId);
//					
//					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
//					errorObj.put("rownum", rowCount);
//					boolean isError = false;
//					XSSFRow row = (XSSFRow) rows.next();
//					XSSFRow errorRow = null;
//					
//					for (int i = 0; i < 12; i++) {
//						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
//						
//						if (cell != null) {
//
//							DataFormatter formatter = new DataFormatter();
//							
//							String val = formatter.formatCellValue(cell);
//							if (val != null)
//								val = val.trim();
//							if(rowCount > 2) {
//								_log.info("Val: " + val);
//								if (i == 1) {
//									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
//										//Long srno=Long.parseLong(val);
//										eTierI.setPensionfund(val);
//									}
//									 else {
//											errorObj.put("cellno", 2);
//											errorObj.put("msg", "It is not a number");
//											
//											isError = true;
//										}
//								}
//								else if (i == 2) {
//									
//									eTierI.setInceptiondate(val);
//								}
//								else if (i == 3) {
//									eTierI.setAum(val);
//								} 
//								else if (i == 4) {
//									eTierI.setSubscribers(val);
//								} 
//								else if (i == 5) {
//									eTierI.setNav(val);
//								} 
//								else if (i == 6) {
//									eTierI.setReturn_1year(val);
//								}
//								else if (i == 7) {
//									eTierI.setReturn_3year(val);
//								}
//								else if (i == 8) {
//									eTierI.setReturn_5year(val);
//								}
//								else if (i == 9) {
//									eTierI.setReturn_7year(val);
//								} 
//								else if (i == 10) {
//									eTierI.setReturn_10year(val);
//								} 
//								else if (i == 11) {
//									eTierI.setReturninception(val);
//								} 
//								
//							}
//						} else if(i == 1 && rowCount > 2) {
//							_log.info("outside if");
//							break rowloop;
//						}
//					}
//					eTierI.setCreatedon(new Date());
//						
//						if (isError && rowCount > 2) {
//							errorArray.put(errorObj);
//							errorRow = xx.createRow(errorRowCount);
//							errorRowCount++;
//							XSSFCell rowCountCel = errorRow.createCell(1);
//							rowCountCel.setCellValue(rowCount);
//							XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
//							cellError.setCellValue(errorObj.getString("msg"));
//							isexcelhaserror = true;
//						} else if (rowCount > 2) {
//							JSONObject eTierIJsonObject = JSONFactoryUtil.createJSONObject(eTierI.toString());
//							etierIJsonArray.put(eTierIJsonObject);
//							eTierIList.add(eTierI);
//						}
//						rowCount++;
//					}
//					
//				}
//			}catch (Exception e) {
//				 _log.error(e);
//			}
//			_log.info(etierIJsonArray.toString());
//			
//			
//			
//			if (!isexcelhaserror) {
//				eTier_ILocalService.addETierI(eTierIList);
//				
//				uploadFILETOFOLDER(themeDisplay, resourceRequest);
//				try {
//					errorExcel.close();
//					//resultJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
//				} catch (Exception e) {
//					 _log.error(e);
//				}
//				resultJsonObject.put("status", true);
//			} else {
//				String downloadUrl = null;
//				try {
//
//					OutputStream stream = null;
//
//					File errortempfile = File.createTempFile("error", "xlsx");
//					stream = new FileOutputStream(errortempfile);
//
//					errorExcel.write(stream);
//
//					String filePath = errortempfile.getPath();
//					String filename = errortempfile.getName();
//					stream.close();
//					errorExcel.close();
//					downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
//				} catch (Exception e) {
//					 _log.error(e);
//				} 
//				resultJsonObject.put("downloadUrl", downloadUrl);
//				resultJsonObject.put("status", false);
//			}
//			return resultJsonObject;
//		}
	
//	public JSONObject fileUpload_1(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
//		Long userId = themeDisplay.getUserId();
//		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
//
//		String fileName = uploadPortletRequest.getFileName("finalUploadFile");
//
//		File file = uploadPortletRequest.getFile("finalUploadFile");
//
//		String mimeType = uploadPortletRequest.getContentType("finalUploadFile");
//
//		String title = fileName;
//
//		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
//		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
//		XSSFWorkbook workbook = null;
//		
//		//ETier_1 et1 = new ETier_1();
//		
//		/**
//		 * Create error excel
//		 */
//		XSSFWorkbook errorExcel = new XSSFWorkbook();
//		XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
//		XSSFRow xxx = xx.createRow(1);
//		XSSFCell rowNumCel = xxx.createCell(1);
//		rowNumCel.setCellValue("RowNum");
//		XSSFCell fileNamecell = xxx.createCell(2);
//		fileNamecell.setCellValue("Pension Fund");
//		
//		
//		boolean isexcelhaserror = false;
//		try {
//			if (file != null) {
//				OPCPackage pkg = OPCPackage.open(file);
//				workbook = new XSSFWorkbook(pkg);
//				//XSSFSheet sheet = workbook.getSheetAt(0);
//				
//				//Iterator<Row> rows = sheet.rowIterator();
//				int rowCount = 1;
//				int errorRowCount = 2;
//				int sheetCount = 0;
//				
//				Iterator<Sheet> sheetIterator  = workbook.sheetIterator();
//				_log.info(sheetIterator.hasNext());
//				
//				while(sheetIterator.hasNext()) {
//					XSSFSheet sheets = (XSSFSheet) sheetIterator.next();
//					_log.info(sheets.getSheetName());
//					Iterator<Row> rows = sheets.iterator();
//					_log.info(rows.next().getCell(1));
//					
//					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
//					errorObj.put("rownum", rowCount);
//					boolean isError = false;
//					//XSSFRow row = (XSSFRow) rows.next();
//					XSSFRow errorRow = null;
//					
//					if(sheetCount == 0 || sheetCount == 1 || sheetCount == 2 || 
//							sheetCount == 3 || sheetCount == 4 || sheetCount == 5 || 
//							sheetCount == 6 || sheetCount == 7 || sheetCount == 8 || 
//							sheetCount == 9 || sheetCount == 10 || sheetCount == 11 || 
//							sheetCount == 12  && sheets != null) {
//						schemeETeir_1(themeDisplay, rows, resourceRequest);
//						//ETier_1 et1 = new ETier_1();
//						//et1.schemeETeir_1(themeDisplay, rows, resourceRequest);
//						//_log.info("et1" + et1.schemeETeir_1(themeDisplay, rows, resourceRequest));
//						//et1.printInfo();
//					} 
////					else if(sheetCount == 1 && sheets != null) {
////						schemeCTeir_1(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 2 && sheets != null) {
////						schemeGTeir_1(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 3 && sheets != null) {
////						schemeATeir_1(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 4 && sheets != null) {
////						schemeETeir_2(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 5 && sheets != null) {
////						schemeCTeir_2(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 6 && sheets != null) {
////						schemeGTeir_2(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 7 && sheets != null) {
////						schemeCG(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 8 && sheets != null) {
////						schemeSG(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 9 && sheets != null) {
////						corporateCG(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 10 && sheets != null) {
////						npsLite(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 11 && sheets != null) {
////						schemeAPY(themeDisplay, rows, resourceRequest);
////					} else if(sheetCount == 12 && sheets != null) {
////						schemeTTS(themeDisplay, rows, resourceRequest);
////					} 
//					
//					
//					if (isError && rowCount > 2) {
//						errorArray.put(errorObj);
//						errorRow = xx.createRow(errorRowCount);
//						errorRowCount++;
//						XSSFCell rowCountCel = errorRow.createCell(1);
//						rowCountCel.setCellValue(rowCount);
//						XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
//						cellError.setCellValue(errorObj.getString("msg"));
//						isexcelhaserror = true;
//					}
//					sheetCount++;
//				}
//					
//				}
//			}catch (Exception e) {
//				 _log.error(e);
//			}
//
//		
//			if (!isexcelhaserror) {
//				
//				uploadFILETOFOLDER(themeDisplay, resourceRequest);
//				try {
//					errorExcel.close();
//					//resultJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
//				} catch (Exception e) {
//					 _log.error(e);
//				}
//				resultJsonObject.put("status", true);
//			} else {
//				String downloadUrl = null;
//				try {
//
//					OutputStream stream = null;
//
//					File errortempfile = File.createTempFile("error", "xlsx");
//					stream = new FileOutputStream(errortempfile);
//
//					errorExcel.write(stream);
//
//					String filePath = errortempfile.getPath();
//					String filename = errortempfile.getName();
//					stream.close();
//					errorExcel.close();
//					downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
//				} catch (Exception e) {
//					 _log.error(e);
//				} 
//				resultJsonObject.put("downloadUrl", downloadUrl);
//				resultJsonObject.put("status", false);
//			}
//			return resultJsonObject;
//		}
//
//	public JSONObject schemeETeir_1(ThemeDisplay themeDisplay, Iterator<Row> rows, ResourceRequest resourceRequest) throws InvalidFormatException, IOException, JSONException {
//		
//		JSONObject eTierIJsonObject = null;
//		JSONArray etierIJsonArray = JSONFactoryUtil.createJSONArray();
//		List<compliance.service.model.FinalUploadFile> eTierIList = new ArrayList<SchemeETier_I>();
//		
//		int rowCount = 1;
//		//int errorRowCount = 2;
//		
//			rowloop:
//			while (rows.hasNext()) {
//				
//				SchemeETier_I eTierI = (SchemeETier_I) eTier_ILocalService.
//						createSchemeETier_I(CounterLocalServiceUtil.increment(SchemeETier_I.class.getName()));
//				
//				eTierI.setCreatedby(themeDisplay.getUserId());
//				
////				JSONObject errorObj = JSONFactoryUtil.createJSONObject();
////				errorObj.put("rownum", rowCount);
////				boolean isError = false;
////				row = (XSSFRow) rows.next();
////				XSSFRow errorRow = null;
//				
//				XSSFRow row = (XSSFRow) rows.next();
//				
//				int lastColumn = Math.max(row.getLastCellNum(), 0);
//				
//				for (int i = 0; i < lastColumn; i++) {
//					//XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
//					
//					XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
//					
//					if (cell != null) {
//
//						DataFormatter formatter = new DataFormatter();
//						
//						String val = formatter.formatCellValue(cell);
//						if (val != null)
//							val = val.trim();
//						if(rowCount > 0) {
//							_log.info("Val: " + val);
//							if (i == 1) {
//								if (val != null && Validator.isNotNull(val) && val.length() > 0) {
//									//Long srno=Long.parseLong(val);
//									eTierI.setPensionfund(val);
//								}
//							}
//							else if (i == 2) {
//								_log.info("cell2 " + cell.getCellType());
//								eTierI.setInceptiondate(val);
//							}
//							else if (i == 3) {
//								eTierI.setAum(val);
//							} 
//							else if (i == 4) {
//								eTierI.setSubscribers(val);
//							} 
//							else if (i == 5) {
//								eTierI.setNav(val);
//							} 
//							else if (i == 6) {
//								eTierI.setReturn_1year(val);
//							}
//							else if (i == 7) {
//								eTierI.setReturn_3year(val);
//							}
//							else if (i == 8) {
//								eTierI.setReturn_5year(val);
//							}
//							else if (i == 9) {
//								eTierI.setReturn_7year(val);
//							} 
//							else if (i == 10) {
//								eTierI.setReturn_10year(val);
//							} 
//							else if (i == 11) {
//								eTierI.setReturninception(val);
//							} 
//							
//						}
//					} else if(i == 1 && rowCount > 0) {
//						_log.info("outside if");
//						break rowloop;
//					}
//				}
//				eTierI.setCreatedon(new Date());
//				
//				if (rowCount > 0) {
//					eTierIJsonObject = JSONFactoryUtil.createJSONObject(eTierI.toString());
//					etierIJsonArray.put(eTierIJsonObject);
//					eTierIList.add(eTierI);
//				}
//				rowCount++;
//
//			}
//		
//		try {
//			eTier_ILocalService.addETierI(eTierIList);
//			//eTierIJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
//		} catch(Exception e) {
//			 _log.error(e);
//		}
//		
//		_log.info(etierIJsonArray.toString());
//		
//		return eTierIJsonObject;
//		
//	}
//
//	
//	@SuppressWarnings("deprecation")
//	public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename,
//			ResourceRequest resourceRequest) {
//
//		File file = new File(filepath);
//		String mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//		String title = filename + ".xlsx";
//		String description = "This file is added via programatically";
//		long repositoryId = themeDisplay.getScopeGroupId();
//		String downloadUrl = StringPool.BLANK;
//		try {
//
//			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
//					resourceRequest);
//			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, 0, title, mimeType, title, description, "",
//					file, serviceContext);
//			FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
//			downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
//		} catch (Exception e) {
//			_log.info(e.getMessage());
//			 _log.error(e);
//		}
//		return downloadUrl;
//	}
//	
//	@SuppressWarnings("deprecation")
//	public void uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
//
//		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
//
//		String fileName = uploadPortletRequest.getFileName("finalUploadFile") + "_" + getRandomNumber();
//
//		File file = uploadPortletRequest.getFile("finalUploadFile");
//
//		String mimeType = uploadPortletRequest.getContentType("finalUploadFile");
//
//		String title = fileName;
//
//		String description = "finalUploadFile";
//
//		long repositoryId = themeDisplay.getScopeGroupId();
//	
//
//		try {
//
//			Folder folder = getFolder(themeDisplay);
//
//			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
//					resourceRequest);
//
//		DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
//					"", file, serviceContext);
//		
//
//		} catch (Exception e) {
//
//			_log.info(e.getMessage());
//
//			 _log.error(e);
//		}
//	}
//	
//	private int getRandomNumber() {
//		Random random = new Random();
//		int x = random.nextInt(900) + 100;
//		return x;
//	}
//	
//	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
//	
//	public Folder getFolder(ThemeDisplay themeDisplay) {
//
//		Folder folder = null;
//		try {
//			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Weekly");
//			_log.info(folder);
//
//		} catch (Exception e) {
//
//			_log.info(e.getMessage());
//		}
//		return folder;
//
//	}
//	
//	@Reference
//	SchemeETier_ILocalService eTier_ILocalService;
	
//	@Reference
//	SchemeCTier_ILocalService cTier_ILocalService;
//	
//	@Reference
//	SchemeGTier_ILocalService gTier_ILocalService;
//	
//	@Reference
//	SchemeATier_ILocalService aTier_ILocalService;
//	
//	@Reference
//	SchemeETier_IILocalService eTier_IILocalService;
//	
//	@Reference
//	SchemeCTier_IILocalService cTier_IILocalService;
//	
//	@Reference
//	SchemeGTier_IILocalService gTier_IILocalService;
//	
//	@Reference
//	SchemeCGLocalService cgLocalService;
//	
//	@Reference
//	SchemeSGLocalService sgLocalService;
//	
//	@Reference
//	CorporateCGLocalService corporateCGLocalService;
//	
//	@Reference
//	NPSLiteLocalService npsLiteLocalService;
//	
//	@Reference
//	APYLocalService apyLocalService;
//	
//	@Reference
//	TTSLocalService ttsLocalService;
	
//}
