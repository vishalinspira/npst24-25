package nps.email.service.impl;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletRequest;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;

import nps.email.api.api.ExcelValidationAn10Api;
import nps.email.api.api.ExcelValidationAn7Api;
import nps.email.service.client.RestClient;
import nps.email.service.constants.ErrorAPIKeys;

@Component(immediate = true, property = {}, service = ExcelValidationAn7Api.class)
public class ExcelValidationAn7Impl   extends RestClient implements ExcelValidationAn7Api {
	private static final Log LOG = LogFactoryUtil.getLog(ExcelValidationAn7Api.class);

	private static final String XML_START = "<contents><streams><in_xml><Value><TABLE>";
	
	private static final String XML_END = "</TABLE></Value></in_xml></streams></contents>";
	
	@Override
	public JSONObject validateExcelFile(File file, String fileName) {
		LOG.info("Excel validation Begins");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		/*
		 * UploadPortletRequest uploadPortletRequest =
		 * PortalUtil.getUploadPortletRequest(portletRequest);
		 * 
		 * File file = uploadPortletRequest.getFile(excelFileName);
		 */
		
		List<String> columnHeaders = new ArrayList<String>();
		
		try {
			if(Validator.isNotNull(file)) {
				OPCPackage pkg = OPCPackage.open(file);
				XSSFWorkbook workbook  = new XSSFWorkbook(pkg);
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while (sheets.hasNext()) {
					
					StringBuffer stringBufferXML_INNER_CONENT = new StringBuffer(XML_START);
					String XML_INNER_CONENT = StringPool.BLANK;
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					int lastRowNum = sheet.getLastRowNum();
					LOG.info("Sheet Name"+sheet.getSheetName()+"lastRowNum"+lastRowNum);
					Iterator<Row> rows = sheet.rowIterator();
					
					columnHeaders = fetchColumnsHeader(sheet);
					int rowCount = 1;
					rowLoop:
					while (rows.hasNext()) {
						XSSFRow row = (XSSFRow) rows.next();
						if (lastRowNum>0) {
							//LOG.info(row);
							if (rowCount > 1) {
								//XML_INNER_CONENT = XML_INNER_CONENT + "<inputds>";
								stringBufferXML_INNER_CONENT.append("<inputds>");
								boolean blankRow = false;

								for (int i = 0; i < columnHeaders.size(); i++) {
									DataFormatter formatter = new DataFormatter();
									/* Change code for date format */
									String dateValue="";
									String inputDate= formatter.formatCellValue(row.getCell(i));
									/* Change code for date format */

									stringBufferXML_INNER_CONENT.append("<");
									stringBufferXML_INNER_CONENT.append(columnHeaders.get(i).toString().trim());
									stringBufferXML_INNER_CONENT.append("><![CDATA[");
									/*Commented below line for date format*/
									/*stringBufferXML_INNER_CONENT.append(formatter.formatCellValue(row.getCell(i)).trim());*/
									
									/* Change code for date format */
									
									/*String datePattern = "\\d{2}/\\d{2}/\\d{4}";
									 Pattern pattern = Pattern.compile(datePattern);
									 Matcher matcher = pattern.matcher(inputDate);
									 if(matcher.matches()) {
										    SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
										    Date date = inputFormat.parse(inputDate);
										    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
										    dateValue = outputFormat.format(date);
										    stringBufferXML_INNER_CONENT
											.append(dateValue.trim());
									 }else {*/
										 stringBufferXML_INNER_CONENT
											.append(formatter.formatCellValue(row.getCell(i)).trim());
									/* } */
									
									/* Change code for date format */
									stringBufferXML_INNER_CONENT.append("]]></");
									stringBufferXML_INNER_CONENT.append(columnHeaders.get(i).toString().trim());
									stringBufferXML_INNER_CONENT.append(">");
									if (i == 0 && Validator.isNull(formatter.formatCellValue(row.getCell(i)))) {
										blankRow = true;
									}
									//LOG.info("columnHeaders.get(i)"+columnHeaders.get(i));
									//XML_INNER_CONENT = XML_INNER_CONENT + "<"+ columnHeaders.get(i).toString().trim() +">"+ formatter.formatCellValue(row.getCell(i)).trim() +"</"+ columnHeaders.get(i).toString().trim() +">";
								}
								stringBufferXML_INNER_CONENT.append("</inputds>");
								if (blankRow) {
									break rowLoop;
								}
								//XML_INNER_CONENT = XML_INNER_CONENT + "</inputds>";
							} 
						}else {
							stringBufferXML_INNER_CONENT.append("<inputds>");
							boolean blankRow = false;

							for (int i = 0; i < columnHeaders.size(); i++) {
								DataFormatter formatter = new DataFormatter();

								stringBufferXML_INNER_CONENT.append("<");
								stringBufferXML_INNER_CONENT.append(columnHeaders.get(i).toString().trim());
								stringBufferXML_INNER_CONENT.append("><![CDATA[");
								/*stringBufferXML_INNER_CONENT
										.append(formatter.formatCellValue(row.getCell(i)).trim());*/
								stringBufferXML_INNER_CONENT.append("]]></");
								stringBufferXML_INNER_CONENT.append(columnHeaders.get(i).toString().trim());
								stringBufferXML_INNER_CONENT.append(">");
								if (i == 0 && Validator.isNull(formatter.formatCellValue(row.getCell(i)))) {
									blankRow = true;
								}
								//LOG.info("columnHeaders.get(i)"+columnHeaders.get(i));
								//XML_INNER_CONENT = XML_INNER_CONENT + "<"+ columnHeaders.get(i).toString().trim() +">"+ formatter.formatCellValue(row.getCell(i)).trim() +"</"+ columnHeaders.get(i).toString().trim() +">";
							}
							stringBufferXML_INNER_CONENT.append("</inputds>");
							if (blankRow) {
								break rowLoop;
							}
						}
						rowCount++;
					}
					stringBufferXML_INNER_CONENT.append(XML_END);
					//String XML_FINAL_STRING = XML_START + XML_INNER_CONENT + XML_END;
					
					LOG.info(stringBufferXML_INNER_CONENT.toString());
					
					//LOG.info("\n\n\n\n\n");
					
					/*long start = System.currentTimeMillis();
					LOG.info("STARTED API CALL"+sheet.getSheetName());
					String url = ErrorAPIKeys.ERROR_API_DOMAIN+"/SASBIWS/rest/storedProcesses/Services/"+sheet.getSheetName();
					String response = execute2(url , stringBufferXML_INNER_CONENT.toString());//validateExcelFile(XML_FINAL_STRING);
					LOG.info(response);
					long end = System.currentTimeMillis();
					LOG.info("ENDED API CALL");
					LOG.info("Time taken ::::: " + (end - start) + " ms");*/
					/*try {
						resultJsonObject = createErrorExcelFileForDailyAverageService2(portletRequest, response, columnHeaders);
					} catch (IOException e) {
						LOG.error(e);
					} catch (DocumentException e) {
						LOG.error(e)
					}*/
					File xmlFile = new File("D:\\creant\\nps\\"+sheet.getSheetName()+".xml");
					FileWriter fileWriter = new FileWriter(xmlFile);
					fileWriter.write(stringBufferXML_INNER_CONENT.toString());
					fileWriter.close();
				} 
				
			}
		} catch(Exception e) {
			LOG.error(e.getMessage());
		} finally {
			//uploadPortletRequest.cleanUp();
		}
		
		/*JSONObject jsonObject = null;
		try {
			jsonObject = JSONFactoryUtil.createJSONObject(response);
			//LOG.info(jsonObject.toString());
		} catch(Exception e) {
			LOG.error(e.getMessage());
		}*/
		
		/*try {
			resultJsonObject.put("downloadUrl", createErrorExcelFileForDailyAverageService(portletRequest, jsonObject));
			resultJsonObject.put("status", false);
			return resultJsonObject;
		} catch (IOException e) {
			LOG.info(e.getMessage());
		}*/
		resultJsonObject.put("status", false);
		return resultJsonObject;
	}
	
	private List<String> fetchColumnsHeader(XSSFSheet sheet) {
		List<String> columnHeaders = new ArrayList<>();
		
		Iterator<Row> rows = sheet.rowIterator();
		XSSFRow headingRow = (XSSFRow) rows.next();
		
		int columnCount = 0;
		while(Validator.isNotNull(fetchColumnValue(headingRow, columnCount)) && !(fetchColumnValue(headingRow, columnCount).equals(StringPool.BLANK))) {
			columnHeaders.add(fetchColumnValue(headingRow, columnCount));
			columnCount++;
		}
		//LOG.info(columnHeaders);
		return columnHeaders;
	}
	
	private String fetchColumnValue(XSSFRow row, int columnIndex) {
		String value = StringPool.BLANK;
		//LOG.info(row);
		//LOG.info(columnIndex);
		try {
			if(!Validator.isNull(row.getCell(columnIndex))) {
				value = row.getCell(columnIndex).toString();
			}			
		} catch(Exception e) {
			LOG.error(e.getMessage());
		}
		
		return value;
	}
	/*
	private String validateExcelFile(String body) {
		
		try {
			StringBuilder result = new StringBuilder();
			CloseableHttpClient client = HttpClientBuilder.create().build();
			
			HttpPost post = new HttpPost("http://localhost:5000/test");
			post.setEntity(new StringEntity(body));
			
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			return result.toString();
		} catch(Exception e) {
			return StringPool.BLANK;
		}
		
	}
	*/
	/*private JSONObject createErrorExcelFileForDailyAverageService2(PortletRequest portletRequest, String xmlString, List<String> columnHeaders) throws IOException, DocumentException {
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet sheet = (XSSFSheet) errorExcel.createSheet();
		
		XSSFRow headerRow = sheet.createRow(0);
		int i =0;
		//columnHeaders.add("NPS_Acc_Number1");
		columnHeaders.add("row");
		columnHeaders.add("Error");
		//columnHeaders.add("acc_type");
		//columnHeaders.add("ERROR1");
		for (Iterator iterator = columnHeaders.iterator(); iterator.hasNext();) {
			String columnHeader = (String) iterator.next();
			XSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(columnHeader);
			i++;
		}
		
		try {
			Document document= SAXReaderUtil.read(xmlString, false);
			List<Node> nodesTABLE=document.selectNodes("sTP1Response/sTP1Result/Streams/out_xml/Value/TABLE");
			List<Node> nodes=document.selectNodes("sTP1Response/sTP1Result/Streams/out_xml/Value/TABLE/outputds");
			int j=1;
			LOG.info(nodesTABLE.get(0).getText());
			if(nodesTABLE.get(0).getText().equalsIgnoreCase("_")) {
				resultJsonObject.put("downloadUrl", "");
				resultJsonObject.put("status", true);
			}else if (nodes!=null) {
				for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
					XSSFRow row = sheet.createRow(j);
					Node node = (Node) iterator.next();
					List<Node> nodes2 = node.selectNodes("*");
					int k=0;
					for (Iterator iterator2 = nodes2.iterator(); iterator2.hasNext();) {
						XSSFCell cell = row.createCell(k);
						
						Node node2 = (Node) iterator2.next();
						LOG.info(node2.getText());
						cell.setCellValue(node2.getText());
						k++;
					}
					j++;
				}
				String downloadUrl = "javascript:void(0);";
				try {
					File errortempfile = File.createTempFile("error", "xlsx");
					OutputStream stream = new FileOutputStream(errortempfile);
	
					errorExcel.write(stream);
	
					String filePath = errortempfile.getPath();
					String filename = errortempfile.getName();
					stream.close();
					errorExcel.close();
					downloadUrl = fileUpload(themeDisplay, filePath, filename, portletRequest);
				} catch (Exception e) {
					LOG.error(e.getMessage());
				} 
				resultJsonObject.put("downloadUrl", downloadUrl);
				resultJsonObject.put("status", false);
				
			}
			
		} catch (DocumentException e1) {
			resultJsonObject.put("downloadUrl", "");
			resultJsonObject.put("status", true);
		}finally {
			errorExcel.close();
		}
		
		return resultJsonObject;
	}*/
	/*
	private String createErrorExcelFileForDailyAverageService(PortletRequest portletRequest, JSONObject jsonObject) throws IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String[] JSON_OBJECT_KEYS = {"cust_id", "nps_acc_number", "nps_acc_name", "tran_date", "tran_id", "part_tran_sno_psrl", "chq_no", "chq_date", "debit_trans", "credit_trans", "running_bal", "trans_particulars", "trans_remarks", "sol_id", "counter_party_acc_number" };
		
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet sheet = (XSSFSheet) errorExcel.createSheet();
		
		XSSFRow headerRow = sheet.createRow(0);
		for(int i=0;i<JSON_OBJECT_KEYS.length;i++) {
			XSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(JSON_OBJECT_KEYS[i]);
		}
		
		if(Validator.isNotNull(jsonObject)) {
			
			Iterator<String> keysItr = jsonObject.keys();
			ArrayList<String> keyArrayList = (ArrayList<String>) IteratorUtils.toList(keysItr);
			//keyArrayList.sort(String.);
			Collections.sort(keyArrayList);
			LOG.info(keyArrayList);
			int rowCount = 1;
			while(keysItr.hasNext()) {
				String keyName = keysItr.next();
				//LOG.info(rowCount + ". KEY IS " + keyName);
				try {
					JSONObject tempJSONObject = jsonObject.getJSONObject(keyName);
					
					XSSFRow row = sheet.createRow(rowCount);
					for(int i=0;i<JSON_OBJECT_KEYS.length;i++) {
						XSSFCell cell = row.createCell(i);
						cell.setCellValue(tempJSONObject.getString(JSON_OBJECT_KEYS[i]));
						if(rowCount == 1) {
							LOG.info("keyname : "+keyName+" tempJSONObject : "+tempJSONObject);
							LOG.info("key: "+JSON_OBJECT_KEYS[i]+", data : "+tempJSONObject.getString(JSON_OBJECT_KEYS[i]));
						}
					}
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
				
				rowCount++;
			}
		}
		
		String downloadUrl = "javascript:void(0);";
		try {
			File errortempfile = File.createTempFile("error", "xlsx");
			OutputStream stream = new FileOutputStream(errortempfile);

			errorExcel.write(stream);

			String filePath = errortempfile.getPath();
			String filename = errortempfile.getName();
			stream.close();
			errorExcel.close();
			downloadUrl = fileUpload(themeDisplay, filePath, filename, portletRequest);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			errorExcel.close();
		}
		
		return downloadUrl;
	}
	*/
	@SuppressWarnings("deprecation")
	public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename, PortletRequest portletRequest) {
		File file = new File(filepath);
		String mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		String title = filename + ".xlsx";
		String description = "This file is added via programatically";
		long repositoryId = themeDisplay.getScopeGroupId();
		
		String downloadUrl = StringPool.BLANK;
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), portletRequest);
			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, 0, title, mimeType, title, description, "", file, serviceContext);
			FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
		
		return downloadUrl;
	}

}
