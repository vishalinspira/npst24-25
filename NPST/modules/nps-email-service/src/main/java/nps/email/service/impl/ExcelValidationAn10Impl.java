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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.ws.rs.WebApplicationException;

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
import nps.email.service.client.RestClient;
import nps.email.service.constants.ErrorAPIDetailsMap;
import nps.email.service.constants.ErrorAPIKeys;
import nps.email.service.model.ErrorAPIDetails;


@Component(immediate = true, property = {}, service = ExcelValidationAn10Api.class)
public class ExcelValidationAn10Impl  extends RestClient implements ExcelValidationAn10Api {
	private static final Log LOG = LogFactoryUtil.getLog(ExcelValidationAn10Impl.class);

	private static final String XML_START = "<contents><streams><in_xml><Value><TABLE>";
	
	private static final String XML_END = "</TABLE></Value></in_xml></streams></contents>";
	
	@Override
	public JSONObject validateExcelFile(File file, PortletRequest portletRequest) {
		LOG.info("Excel validation Begins");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		String XML_INNER_CONENT = StringPool.BLANK;
		List<String> columnHeaders = new ArrayList<String>();
		
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		
		resultJsonObject.put("status", true);
		
		try {
			if(Validator.isNotNull(file)) {
				LOG.info("file"+file+"portletRequest"+portletRequest);
				OPCPackage pkg = OPCPackage.open(file);
				XSSFWorkbook workbook  = new XSSFWorkbook(pkg);
				Iterator<Sheet> sheets = workbook.sheetIterator();
				while (sheets.hasNext()) {
					XSSFSheet sheet = (XSSFSheet) sheets.next();
					Iterator<Row> rows = sheet.rowIterator();
					int lastRowNum = sheet.getLastRowNum();
					columnHeaders = fetchColumnsHeader(sheet);
					int rowCount = 1;
					LOG.info("Sheet Name"+sheet.getSheetName());
					ErrorAPIDetails errorAPIDetails = ErrorAPIDetailsMap.errorAPIDetailsMap.get(sheet.getSheetName());
					LOG.info("URL---"+errorAPIDetails);
					if(Validator.isNotNull(errorAPIDetails)) {
						StringBuffer stringBufferXML_INNER_CONENT = new StringBuffer(XML_START);
						rowLoop:
						while (rows.hasNext()) {
							
							XSSFRow row = (XSSFRow) rows.next();
							if (lastRowNum>0) {
								//LOG.info(row);
								if (rowCount > 1) {
									//XML_INNER_CONENT = XML_INNER_CONENT + "<inputds>";
									boolean blankRow = false;
									stringBufferXML_INNER_CONENT.append("<inputds>");
									for (int i = 0; i < columnHeaders.size(); i++) {
										DataFormatter formatter = new DataFormatter();
										
										stringBufferXML_INNER_CONENT.append("<");
										stringBufferXML_INNER_CONENT.append(columnHeaders.get(i).toString().trim());
										stringBufferXML_INNER_CONENT.append("><![CDATA[");
										stringBufferXML_INNER_CONENT
												.append(formatter.formatCellValue(row.getCell(i)));
										stringBufferXML_INNER_CONENT.append("]]></");
										stringBufferXML_INNER_CONENT.append(columnHeaders.get(i).toString().trim());
										stringBufferXML_INNER_CONENT.append(">");
										if (i == 0 && Validator.isNull(formatter.formatCellValue(row.getCell(i)))) {
											blankRow = true;
											/*stringBufferXML_INNER_CONENT.append("</inputds>");
											break rowLoop;*/
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
						
						//LOG.info(stringBufferXML_INNER_CONENT.toString());
						
						//LOG.info("\n\n\n\n\n");
						
						long start = System.currentTimeMillis();
						LOG.info("STARTED API CALL"+sheet.getSheetName());
						
						//String url = ErrorAPIKeys.ERROR_API_DOMAIN+"/SASBIWS/rest/storedProcesses/Services/"+sheet.getSheetName();
					
						LOG.info("url--"+errorAPIDetails.getApiEndPoint()+" - NodeTree --"+errorAPIDetails.getApiNodeTree());
						//LOG.info(stringBufferXML_INNER_CONENT.toString());
						String response = StringPool.BLANK;
						try {
							response = execute2(errorAPIDetails.getApiEndPoint() , stringBufferXML_INNER_CONENT.toString());
						} catch (WebApplicationException e) {
							LOG.error(e);
							resultJsonObject.put("status", false);
							resultJsonObject.put("apierror", true);
							return resultJsonObject;
						} catch (RuntimeException e) {
							LOG.error(e);
							resultJsonObject.put("status", false);
							resultJsonObject.put("apierror", true);
							return resultJsonObject;
						} catch (Exception e1) {
							LOG.error(e1);
							resultJsonObject.put("status", false);
							resultJsonObject.put("apierror", true);
							return resultJsonObject;
						}
						
						LOG.info(response);
						long end = System.currentTimeMillis();
						LOG.info("ENDED API CALL");
						LOG.info("Time taken ::::: " + (end - start) + " ms");
						try {
							XSSFSheet errorsheet = (XSSFSheet) errorExcel.createSheet();
							errorExcel.setSheetName(errorExcel.getSheetIndex(errorsheet), sheet.getSheetName()); 
							JSONObject outputJsonObject = createErrorExcelFileForDailyAverageService2(portletRequest, response, columnHeaders, errorAPIDetails.getApiNodeTree(), errorsheet);
							resultJsonObject.put("status", resultJsonObject.getBoolean("status") && outputJsonObject.getBoolean("status"));
						} catch (IOException e) {
							 LOG.error(e);
						} catch (DocumentException e) {
							 LOG.error(e);
						}catch(Exception e) {
							LOG.error(e);
						}
					}
					
					
					/*try {
						resultJsonObject = createErrorExcelFileForDailyAverageService2(portletRequest, response, columnHeaders, errorAPIDetails.getApiNodeTree());
					} catch (IOException e) {
						 LOG.error(e);
					} catch (DocumentException e) {
						 LOG.error(e);
					}*/
				} 
				
			}
		} catch(Exception e) {
			LOG.error(e);
		} finally {
			//uploadPortletRequest.cleanUp();
		}
		
		if(!resultJsonObject.getBoolean("status")) {
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
			resultJsonObject.put("msg", "An error occurred. To download the error excel file click <a href="+downloadUrl+">here</a>");
			
		}
		
		//resultJsonObject.put("status", false);
		return resultJsonObject;
	}
	
	private List<String> fetchColumnsHeader(XSSFSheet sheet) {
		List<String> columnHeaders = new ArrayList<>();
		
		//Iterator<Row> rows = sheet.getRow(0);
		XSSFRow headingRow = (XSSFRow) sheet.getRow(0);
		
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
	private JSONObject createErrorExcelFileForDailyAverageService2(PortletRequest portletRequest, String xmlString, List<String> columnHeaders, String apiNodeTree, XSSFSheet sheet) throws IOException, DocumentException {
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
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
			List<Node> nodesTABLE=document.selectNodes(apiNodeTree);
			List<Node> nodes=document.selectNodes(apiNodeTree+"/outputds");
			int j=1;
			
			LOG.info(nodesTABLE.get(0).getText()+"nodes outputds");
			LOG.info(nodes);
			
			if(nodesTABLE.get(0).getText().equalsIgnoreCase("_") || (nodes!=null && nodes.size() == 0)) {
				//resultJsonObject.put("downloadUrl", "");
				resultJsonObject.put("status", true);
				LOG.info(nodesTABLE.get(0).getText()+" nodes outputds");
			}else if (nodes!=null) {
				LOG.info(nodes);
				for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
					//LOG.info(nodes);
					XSSFRow row = sheet.createRow(j);
					Node node = (Node) iterator.next();
					List<Node> nodes2 = node.selectNodes("*");
					int k=0;
					for (Iterator iterator2 = nodes2.iterator(); iterator2.hasNext();) {
						XSSFCell cell = row.createCell(k);
						
						Node node2 = (Node) iterator2.next();
						//LOG.info(node2.getText());
						cell.setCellValue(node2.getText());
						k++;
					}
					j++;
				}
				resultJsonObject.put("status", false);
				resultJsonObject.put("msg", "An error occurred while parsing file.");
			}
			
		} catch (DocumentException e1) {
			//resultJsonObject.put("downloadUrl", "");
			resultJsonObject.put("status", true);
		}finally {
			
		}
		
		return resultJsonObject;
	}
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
