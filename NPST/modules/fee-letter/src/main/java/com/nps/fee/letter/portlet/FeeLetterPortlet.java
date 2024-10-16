package com.nps.fee.letter.portlet;

import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.nps.fee.letter.constants.FeeLetterPortletKeys;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;

/**
 * @author VishalKumar
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=FeeLetter",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + FeeLetterPortletKeys.FEELETTER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class FeeLetterPortlet extends MVCPortlet {
	private static final Log LOG = LogFactoryUtil.getLog(FeeLetterPortlet.class);
	
	
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		 DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
			
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		//String fileName = uploadPortletRequest.getFileName("feeletter");
		String pfmName=ParamUtil.getString(resourceRequest, "pfmName");
		File file = uploadPortletRequest.getFile("feeletter");
		String receivedDate=ParamUtil.getString(resourceRequest,"receivedDate");
		//Date receivedDate=ParamUtil.getDate(resourceRequest,"receivedDate",dateFormat);
		String month=ParamUtil.getString(resourceRequest,"month");

		String signeturies=ParamUtil.getString(resourceRequest,"signeturies");
		Date letterDate=ParamUtil.getDate(resourceRequest,"letterDate",dateFormat);
		
		/*
		 * File file=null; DLFileEntry fileenrty= null; InputStream
		 * fileInputStream=null; try {
		 * 
		 * 
		 * // for(DLFileEntry dl:DLFileEntryLocalServiceUtil.getFileEntries(20119, 0)) {
		 * // System.out.print("name: "+dl.getName()+" id:  "+dl.getFileEntryId()); // }
		 * fileenrty=DLFileEntryLocalServiceUtil.getFileEntry(52501); fileInputStream =
		 * DLFileEntryLocalServiceUtil.getFileAsStream(fileenrty.getFileEntryId(),
		 * fileenrty.getVersion()); } catch (PortalException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */
		//File file=new File(fileInputStream);
		String downloadUrl="";
		//LOG.info("uploeded file : "+file);
	//	Aspose.convertPDFTODOC(fileInputStream);
		//ConvertPDFtoWord.convertPdftoWord(file);
		LOG.info("file uploaded successfully........"+file+"    pfmname:  "+pfmName);
		try {
			JSONArray jsonArray=JSONFactoryUtil.createJSONArray();
			if(Validator.isNotNull(file)) {
				
				OPCPackage pkg = OPCPackage.open(file);
				XSSFWorkbook workbook  = new XSSFWorkbook(pkg);
				//Iterator<Sheet> sheets = workbook.sheetIterator();
				Sheet sheet=workbook.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				//for (Row row : sheet) {
				
				
				JSONObject jsonObject1=JSONFactoryUtil.createJSONObject();
				jsonObject1.put("schemeName","Scheme Name" );
				jsonObject1.put("imf","IMF" );
				jsonObject1.put("gst", "GST");
				jsonObject1.put("total","Total" );
				jsonObject1.put("trustfee","NPS Trust Fee" );
				jsonArray.put(jsonObject1);
				while (rows.hasNext()) {
					boolean isSamePFM=false;
					XSSFRow row = (XSSFRow) rows.next();
					JSONObject jsonObject=JSONFactoryUtil.createJSONObject();
					try {
						
						for (int i = 0; i < 6; i++) {
							XSSFCell cell = row.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
							if (cell != null) {

								DataFormatter formatter = new DataFormatter();
								String val = formatter.formatCellValue(cell);
								if(i==0) {
									isSamePFM=val.contains(pfmName)?true:false;
								}
								else if (i == 1 && isSamePFM) {
										jsonObject.put("schemeName",val );	
									}
									else if (i == 2 && isSamePFM) {
										jsonObject.put("imf", formatNumberInIndianStyle(val));
										//formatNumberWithComma(val);
									}
									else if (i == 3 && isSamePFM) {
										jsonObject.put("gst",formatNumberInIndianStyle(val) );
									} 
									else if (i == 4 && isSamePFM) {
										jsonObject.put("total",formatNumberInIndianStyle(val) );
									}
									else if (i == 5 && isSamePFM) {
										jsonObject.put("trustfee", formatNumberInIndianStyle(val));
									}
							}
						}
							if(isSamePFM) {	
								LOG.info("schemeName: "+jsonObject.getString("schemeName")+" imf : "+jsonObject.getString("imf")+"  gst  : "+jsonObject.getString("gst")+"  total  :"+jsonObject.getString("total")+"  trustfee : "+jsonObject.getString("trustfee"));
						jsonArray.put(jsonObject);
							}
						
						
					}catch (Exception e) {
						LOG.error(e);
					}
				}	
			}
			File fileLetterFile=PensionFundPDF.generateFeeLetter(pfmName,jsonArray,letterDate,receivedDate,month,signeturies);
			FileEntry entry = uploadFile(fileLetterFile, themeDisplay, serviceContext);
			FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
	}catch (Exception e) {
		LOG.error(e);
	}
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(downloadUrl);
		} catch (IOException e) {
			LOG.error(e);
		}
}
    public static String formatNumberInIndianStyle(String numStr) {
    	if(numStr==null || numStr=="" || numStr.equalsIgnoreCase("")) {
    		return numStr;
    	}
        StringBuilder result = new StringBuilder();
       numStr= numStr.replaceAll(",", "");
       LOG.info(numStr);
       String nums[]=numStr.split("\\.");
       LOG.info(nums.length);
       String decValue="";
       numStr=nums[0];
       try {
			decValue="."+nums[1];		
				}catch (Exception e) {
					// TODO: handle exception
				}
        int len = numStr.length();
         int length = numStr.length();
         LOG.info("len first: "+len);
        // Handle the first group of three digits
        if (len > 3) {
            result.append(numStr.substring(0, len - 3)).append(",");
            numStr = numStr.substring(len - 3);
            len = numStr.length();
        }

        // Add last three digits
        result.append(numStr);

        // Add commas for every two digits
        LOG.info("len: "+len +" result : "+result.toString());
        for (int i = length-len; i > 1; i -= 2) {
            if (i - 1 > 0) {
                 result.insert(i-2, ",");
            }
        }
        String finalResult=result.toString()+decValue;
        if(finalResult.charAt(0)==',') {
        	return finalResult.replaceFirst(",", "");
        }else {
        	return finalResult;
        }
        
    }
    

		
	
	private FileEntry uploadFile(File file,ThemeDisplay themeDisplay,ServiceContext serviceContext){
		Date date=new Date();
		FileEntry fileEntry =null;
		String fileName = date.getTime()+"_"+file.getName()+".pdf";
		String title = fileName; 
		String description = "Fee Letter PDF";
		
		String mimeType =  MimeTypesUtil.getContentType(file);
		long repositoryId = themeDisplay.getScopeGroupId();
		try {
			Folder folder = getFolder(themeDisplay,serviceContext);
			 fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,"", file, serviceContext);
			return fileEntry;
		}catch (Exception e) {
			LOG.error("error while uploading file:  "+e.getMessage());
		}
		return fileEntry;	
	}
	

	public static Folder getFolder(ThemeDisplay themeDisplay,ServiceContext serviceContext) {
		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), 0, "Fee Letter");
		} catch (Exception e) {
			try {
			DLAppServiceUtil.addFolder(themeDisplay.getScopeGroupId(), 0, "Fee Letter", "Fee Letter", serviceContext);
			}catch (Exception e1) {
			LOG.error(e1);
			}
		}
		return folder;
		
	

	}
	
}