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
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.xml.SAXReaderFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletRequest;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;

import nps.email.api.api.ExcelValidationApi;
import nps.email.service.client.RestClient;
import nps.email.service.constants.ErrorAPIKeys;

@Component(immediate = true, property = {}, service = ExcelValidationApi.class)
public class ExcelValidationImpl extends RestClient implements ExcelValidationApi {
	
	
	private static final Log _log = LogFactoryUtil.getLog(ExcelValidationImpl.class);

	private static final String XML_START = "<contents><streams><in_xml><Value><TABLE>";
	
	private static final String XML_END = "</TABLE></Value></in_xml></streams></contents>";
	
	@Override
	public JSONObject validateExcelFile(PortletRequest portletRequest, String excelFileName) {
		_log.info("Excel validation Begins");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(portletRequest);
		
		File file = uploadPortletRequest.getFile(excelFileName);
		StringBuffer stringBufferXML_INNER_CONENT = new StringBuffer(XML_START);
		String XML_INNER_CONENT = StringPool.BLANK;
		List<String> columnHeaders = new ArrayList<String>();
		
		try {
			if(Validator.isNotNull(file)) {
				OPCPackage pkg = OPCPackage.open(file);
				XSSFWorkbook workbook  = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("ALL AC");
				Iterator<Row> rows = sheet.rowIterator();
				
				columnHeaders = fetchColumnsHeader(sheet);
				int rowCount = 1;
				rowLoop:
				while (rows.hasNext()) {
					XSSFRow row = (XSSFRow) rows.next();
					//LOG.info(row);
					if(rowCount >1) {
						//XML_INNER_CONENT = XML_INNER_CONENT + "<inputds>";
						stringBufferXML_INNER_CONENT.append("<inputds>");
						for(int i=0;i<columnHeaders.size();i++) {
							DataFormatter formatter = new DataFormatter();
							if(i==0 && Validator.isNull(formatter.formatCellValue(row.getCell(i)))) {
								stringBufferXML_INNER_CONENT.append("</inputds>");
								break rowLoop;
							}
							stringBufferXML_INNER_CONENT.append("<");
								stringBufferXML_INNER_CONENT.append(columnHeaders.get(i).toString().trim());
							stringBufferXML_INNER_CONENT.append("><![CDATA[");
									stringBufferXML_INNER_CONENT.append(formatter.formatCellValue(row.getCell(i)).trim());
							stringBufferXML_INNER_CONENT.append("]]></");
								stringBufferXML_INNER_CONENT.append(columnHeaders.get(i).toString().trim());
							stringBufferXML_INNER_CONENT.append(">");
							//LOG.info("columnHeaders.get(i)"+columnHeaders.get(i));
							//XML_INNER_CONENT = XML_INNER_CONENT + "<"+ columnHeaders.get(i).toString().trim() +">"+ formatter.formatCellValue(row.getCell(i)).trim() +"</"+ columnHeaders.get(i).toString().trim() +">";
						}
						stringBufferXML_INNER_CONENT.append("</inputds>");
						//XML_INNER_CONENT = XML_INNER_CONENT + "</inputds>";
					}
					rowCount++;
				}
			}
		} catch(Exception e) {
			_log.error(e.getMessage());
		} finally {
			//uploadPortletRequest.cleanUp();
		}
		stringBufferXML_INNER_CONENT.append(XML_END);
		//String XML_FINAL_STRING = XML_START + XML_INNER_CONENT + XML_END;
		
		_log.info(stringBufferXML_INNER_CONENT.toString());
		
		//LOG.info("\n\n\n\n\n");
		
		long start = System.currentTimeMillis();
		_log.info("STARTED API CALL");
		String url = ErrorAPIKeys.ERROR_API_DOMAIN+"STP1";
		_log.info(url);
		String response = execute2(url , stringBufferXML_INNER_CONENT.toString());//validateExcelFile(XML_FINAL_STRING);
		_log.info(response);
		long end = System.currentTimeMillis();
		_log.info("ENDED API CALL");
		_log.info("Time taken ::::: " + (end - start) + " ms");
		try {
			resultJsonObject = createErrorExcelFileForDailyAverageService2(portletRequest, response, columnHeaders);
		} catch (IOException e) {
			 _log.error(e);
		} catch (DocumentException e) {
			 _log.error(e);
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
			_log.error(e.getMessage());
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
	private JSONObject createErrorExcelFileForDailyAverageService2(PortletRequest portletRequest, String xmlString, List<String> columnHeaders) throws IOException, DocumentException {
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		XSSFWorkbook errorExcel = new XSSFWorkbook();
		XSSFSheet sheet = (XSSFSheet) errorExcel.createSheet();
		
		XSSFRow headerRow = sheet.createRow(0);
		int i =0;
		columnHeaders.add("NPS_Acc_Number1");
		columnHeaders.add("row");
		columnHeaders.add("Error");
		columnHeaders.add("acc_type");
		columnHeaders.add("ERROR1");
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
			_log.info(nodesTABLE.get(0).getText());
			if(nodesTABLE.get(0).getText().equalsIgnoreCase("_") || (nodes!=null && nodes.size() == 0)) {
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
						_log.info(node2.getText());
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
					_log.error(e.getMessage());
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
			_log.info(keyArrayList);
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
							_log.info("keyname : "+keyName+" tempJSONObject : "+tempJSONObject);
							_log.info("key: "+JSON_OBJECT_KEYS[i]+", data : "+tempJSONObject.getString(JSON_OBJECT_KEYS[i]));
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
			_log.info(e.getMessage());
		}
		
		return downloadUrl;
	}
	
}
