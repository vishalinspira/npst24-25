package com.account.statement.portlet.resource;

import com.account.statement.constants.accountStatementPortletKeys;
import com.daily.average.service.model.AccountStatement;
import com.daily.average.service.model.AccountStatementTransaction;
import com.daily.average.service.model.ReportUploadFileLog;
import com.daily.average.service.service.AccountStatementLocalServiceUtil;
import com.daily.average.service.service.AccountStatementTransactionLocalServiceUtil;
import com.daily.average.service.service.ReportUploadFileLogLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogMakerLocalServiceUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
//import com.quarter.ended.constants.quarterEndedPortletKeys;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.osgi.service.component.annotations.Component;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;

@Component(property = { "javax.portlet.name=" + accountStatementPortletKeys.ACCOUNTSTATEMENT,
		"mvc.command.name=/accountStatement/save" }, service = MVCResourceCommand.class)
public class SaveAccountStatement implements MVCResourceCommand {

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = accountupload(resourceRequest, resourceResponse, themeDisplay);
		try { 
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			_log.error(e);
		}
		return false;
	}

	public JSONObject accountupload(ResourceRequest resourceRequest, ResourceResponse resourceResponse, ThemeDisplay themeDisplay) {
		long userId = 0L;
		String userName = "NA";
		try {
			userId = themeDisplay.getUserId();
			userName = themeDisplay.getUser().getFullName();
		} catch(Exception e) {
			_log.error(e);
		}
		
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			_log.error(e);
		}
		
		
		Long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");

		//String accountname = ParamUtil.getString(resourceRequest, "accountname");
		//String statement = ParamUtil.getString(resourceRequest, "statement");
		//String adressi = ParamUtil.getString(resourceRequest, "adressi");
		//String adressii = ParamUtil.getString(resourceRequest, "adressii");
		//String adressiii = ParamUtil.getString(resourceRequest, "adressiii");
		//String adressiv = ParamUtil.getString(resourceRequest, "adressiv");
		//String adressv = ParamUtil.getString(resourceRequest, "adressv");
		//long pin = ParamUtil.getLong(resourceRequest, "pin");
		//long customerno = ParamUtil.getLong(resourceRequest, "customerno");
		//String scheme = ParamUtil.getString(resourceRequest, "scheme");
		//String curencyinr = ParamUtil.getString(resourceRequest, "curencyinr");
		//long statementacno = ParamUtil.getLong(resourceRequest, "statementacno");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Date formdate = ParamUtil.getDate(resourceRequest, "formdate", dateFormat);
		//String formDateString = ParamUtil.getString(resourceRequest, "formdate");
		//Date todate = ParamUtil.getDate(resourceRequest, "todate", dateFormat);
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		//_log.info(formdate);
		
		//int rowcount = ParamUtil.getInteger(resourceRequest, "rowcount");
		
		//long todebit = ParamUtil.getLong(resourceRequest, "todebit");
		//long tocredit = ParamUtil.getLong(resourceRequest, "tocredit");
		//long clbalance = ParamUtil.getLong(resourceRequest, "clbalance");
		//long clinitbr = ParamUtil.getLong(resourceRequest, "clinitbr");
		//long annexure2FileId=uploadFILETOFOLDER(themeDisplay, resourceRequest, "annexure2");
		
		//AccountStatement accountStatement = AccountStatementLocalServiceUtil.addAccountStatement(accountname, statement,
			//	adressi, adressii, adressiii, adressiv, adressv, pin, customerno, scheme, curencyinr, statementacno,
				//formdate, todate, todebit, tocredit, clbalance, clinitbr, userId, new Date(), userId, new Date(), WorkflowConstants.STATUS_PENDING, userName);

//		long accid = accountStatement.getId();
//		_log.info("rowcount"+rowcount);
//		List<AccountStatementTransaction> accountStatementTransactions = new ArrayList<AccountStatementTransaction>();
//		for (int i = 1; i <= rowcount; i++) {
//			Date trandate = ParamUtil.getDate(resourceRequest, "trandate"+String.valueOf(i), dateFormat);
//			long chqno = ParamUtil.getLong(resourceRequest, "chqno"+String.valueOf(i));
//			long particulars = ParamUtil.getLong(resourceRequest, "particulars"+String.valueOf(i));
//			long debit = ParamUtil.getLong(resourceRequest, "debit"+String.valueOf(i));
//			long credit = ParamUtil.getLong(resourceRequest, "credit"+String.valueOf(i));
//			long balance = ParamUtil.getLong(resourceRequest, "balance"+String.valueOf(i));
//			long initbr = ParamUtil.getLong(resourceRequest, "initbr"+String.valueOf(i));
//			_log.info("trandate"+trandate);
//			AccountStatementTransaction accountStatementTransaction =  AccountStatementTransactionLocalServiceUtil.addAccountStatementTransaction(accid, trandate, chqno, particulars,
//					debit, credit, balance, initbr);
//			accountStatementTransactions.add(accountStatementTransaction);			
//		}
		
		

		/*
		 * if(Validator.isNotNull(accountStatement)) { AssetEntry assetEntry = null; try
		 * { assetEntry =
		 * AssetEntryLocalServiceUtil.updateEntry(themeDisplay.getUser().getUserId(),
		 * serviceContext.getScopeGroupId(), new Date(), new Date(), AccountStatement
		 * .class.getName(), accountStatement.getId(), accountStatement.getUuid(), 0,
		 * null, null, true, false, new Date(), null, new Date(), null,
		 * ContentTypes.TEXT_HTML, "Account Statement Asset",
		 * "Account Statement Asset with Id: " + accountStatement.getId(), null, null,
		 * null, 0, 0, null);
		 * 
		 * Indexer<AccountStatement> indexer =
		 * IndexerRegistryUtil.nullSafeGetIndexer(AccountStatement.class);
		 * indexer.reindex(accountStatement);
		 * 
		 * WorkflowHandlerRegistryUtil.startWorkflowInstance(themeDisplay.getCompanyId()
		 * , themeDisplay.getScopeGroup().getGroupId(), themeDisplay.getUserId(),
		 * AccountStatement.class.getName(), accountStatement.getId(), accountStatement,
		 * serviceContext);
		 * 
		 * } catch (Exception e) { _log.error(e); } }
		 */		
		String remarks = "";
		//resultJsonObject.put("pdfURL",  pdfTable(accountStatement, accountStatementTransactions, themeDisplay, resourceRequest, userId, reportUploadLogId, userName, serviceContext, remarks));
		resultJsonObject.put("pdfURL",fileUpload(themeDisplay, resourceRequest, userId, reportUploadLogId, statusByUserName, serviceContext, remarks));
		resultJsonObject.put("status", true);
		//ReportUploadFileLogLocalServiceUtil.addReportUploadFileLog(reportUploadLogId, annexure2FileId, userId);
		/*npsEmailApi.sendOTP("priyanka@yopmail.com");
		String mobileNo = "918637002546";
		
		String name = "Priyanka";
		npsSmsApi.sendFormSubmitSuccessfulSMS(mobileNo, name);*/
		return resultJsonObject;
	}

	/*@Reference
	NpsEmailApi npsEmailApi;
	@Reference
	NpsSmsApi npsSmsApi;*/
	
	
//	public String pdfTable(AccountStatement accountStatement, List<AccountStatementTransaction> accountStatementTransactions, ThemeDisplay themeDisplay, ResourceRequest resourceRequest, long createdBy, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext2, String remarks) throws IOException {
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        PDFont fontPlain = PDType1Font.HELVETICA;
//        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
//        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
//        PDFont fontMono = PDType1Font.COURIER;
//
//        // Create a document and add a page to it
//        PDDocument document = new PDDocument();
//        PDPage page = new PDPage(PDRectangle.A4);
//        
//        // PDRectangle.LETTER and others are also possible
//        PDRectangle rect = page.getMediaBox();
//        
//        // rect can be used to get the page width and height
//        document.addPage(page);
//
//        // Start a new content stream which will "hold" the to be created content
//        PDPageContentStream cos = new PDPageContentStream(document, page);
//        //cos.b
//	    //Dummy Table
//        float margin = 10;
//        _log.info("rect.getHeight()"+rect.getHeight());
//        
//        // starting y position is whole page height subtracted by top and bottom margin
//        float yStartNewPage = rect.getUpperRightY() - (2 * margin);
//        
//        // we want table across whole page width (subtracted by left and right margin ofcourse)
//        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
//
//        boolean drawContent = true;
//        float yStart = yStartNewPage;
//        float topMargin = 0;
//        float bottomMargin = 10;
//        
//        // y position is your coordinate of top left corner of the table
//        float yPosition = rect.getUpperRightY()-margin;
//
//        BaseTable table = new BaseTable(yPosition, yStartNewPage,topMargin,
//            bottomMargin, tableWidth, margin, document, page, false, drawContent);
//        //new Baset
//
//        // the parameter is the row height
//        Row<PDPage> headerRow = table.createRow(20);
//        
//        // the first parameter is the cell width        
//        Cell<PDPage> cell = headerRow.createCell(100, "Annexure2-Format of Account Statement");
//        
//        //cell.setFont(fontBold);
//        cell.setFontSize(20);
//       
//        cell.setAlign(HorizontalAlignment.CENTER);
//        
//        table.addHeaderRow(headerRow);
//        table.draw();
//
//        float tableHeight = table.getHeaderAndDataHeight();
//        _log.info("tableHeight = "+tableHeight);
//        
//        float table2YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) -30;
//        BaseTable table2 = new BaseTable(table2YPosition, yStartNewPage,topMargin,
//                bottomMargin, (tableWidth-100), (margin +50), document, page, false, drawContent);
//        
//        Row<PDPage> table2row = table2.createRow(20);
//        Cell<PDPage> table2cell = table2row.createCell(30, accountStatement.getAccountname());
//        table2cell.setAlign(HorizontalAlignment.LEFT);
//        table2cell = table2row.createCell(40, "");
//		
//		 table2cell.setAlign(HorizontalAlignment.CENTER); table2cell =
//		 table2row.createCell(30, "");
//		 table2cell.setAlign(HorizontalAlignment.CENTER);
//		 
//        table2.addHeaderRow(table2row);
//        
//        
//        table2row = table2.createRow(20);
//        table2cell = table2row.createCell(30, "");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(40, "");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(30, "Statement Generated By: "+accountStatement.getStatement());
//        table2cell.setAlign(HorizontalAlignment.LEFT);
//        
//        table2row = table2.createRow(20);
//        table2row.createCell(30, accountStatement.getAdressi());
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(40, "");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(30, "");
//        
//        table2row = table2.createRow(20);
//        table2row.createCell(30, accountStatement.getAdressii());
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(40, "");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(30, "Customer no: "+accountStatement.getCustomerno());
//        table2cell.setAlign(HorizontalAlignment.LEFT);
//        
//        table2row = table2.createRow(20);
//        table2row.createCell(30, accountStatement.getAdressiii());
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(40, "");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(30, "Scheme: "+accountStatement.getScheme());
//        table2cell.setAlign(HorizontalAlignment.LEFT);
//        
//        
//        table2row = table2.createRow(20);
//        table2row.createCell(30, accountStatement.getAdressiv());
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(40, "");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(30, "Currency:INR "+accountStatement.getCurencyinr());
//        table2cell.setAlign(HorizontalAlignment.LEFT);
//        
//        
//        table2row = table2.createRow(20);
//        table2row.createCell(30, accountStatement.getAdressv());
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(40, "");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(30, "");
//     
//        
//        table2row = table2.createRow(20);
//        table2row.createCell(30, String.valueOf( accountStatement.getPin()));
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(40, "");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(30, "");
//        
//        
//        table2row = table2.createRow(20);
//        table2row.createCell(30, String.valueOf(accountStatement.getStatementacno()));
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(20, "For the Period of:");
//        table2cell.setAlign(HorizontalAlignment.CENTER);
//        table2cell = table2row.createCell(25, "Form Date "+dateFormat.format(accountStatement.getFormdate()));
//        table2cell.setAlign(HorizontalAlignment.LEFT);
//        table2cell = table2row.createCell(25, "To Date "+dateFormat.format(accountStatement.getTodate()));
//        table2cell.setAlign(HorizontalAlignment.LEFT);
//        
//        table2.draw();
//        
//        
//        float tableHeight2 = table2.getHeaderAndDataHeight();
//        _log.info("tableHeight2 = "+tableHeight2);
//        
//        float table3YPosition = rect.getUpperRightY() - tableHeight - (2 * margin) - 30- tableHeight2 - 100;
//        BaseTable table3 = new BaseTable(table3YPosition, yStartNewPage,topMargin,
//                bottomMargin, (tableWidth-100), (margin +50), document, page, true, drawContent);
//        
//        Row<PDPage> table3row = table3.createRow(20);
//        Cell<PDPage> table3cell = table3row.createCell(16, "TranDate");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "Chq No");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "Particulars");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "Debit");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "Credit");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "Balance");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "Init.Br");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        
//        table3row = table3.createRow(20);
//        table3cell = table3row.createCell(16, "");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "Opening Balance");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        table3cell = table3row.createCell(14, "");
//        table3cell.setAlign(HorizontalAlignment.CENTER);
//        
//        for (Iterator iterator = accountStatementTransactions.iterator(); iterator.hasNext();) {
//			AccountStatementTransaction accountStatementTransaction = (AccountStatementTransaction) iterator.next();
//			
//			 	table3row = table3.createRow(20);
//		        table3cell = table3row.createCell(16,dateFormat.format(accountStatementTransaction.getTrandate()));
//		        table3cell.setAlign(HorizontalAlignment.CENTER);
//		        table3cell = table3row.createCell(14,String.valueOf(accountStatementTransaction.getChqno()));
//		        table3cell.setAlign(HorizontalAlignment.CENTER);
//		        table3cell = table3row.createCell(14,String.valueOf(accountStatementTransaction.getParticulars()));
//		        table3cell.setAlign(HorizontalAlignment.CENTER);
//		        table3cell = table3row.createCell(14,String.valueOf(accountStatementTransaction.getDebit()));
//		        table3cell.setAlign(HorizontalAlignment.CENTER);
//		        table3cell = table3row.createCell(14,String.valueOf(accountStatementTransaction.getCredit()));
//		        table3cell.setAlign(HorizontalAlignment.CENTER);
//		        table3cell = table3row.createCell(14,String.valueOf(accountStatementTransaction.getBalance()));
//		        table3cell.setAlign(HorizontalAlignment.CENTER);
//		        table3cell = table3row.createCell(14,String.valueOf(accountStatementTransaction.getInitbr()));
//		        table3cell.setAlign(HorizontalAlignment.CENTER);
//        }
//        
//		table3row = table3.createRow(20);
//		table3cell = table3row.createCell(16,"");
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,"");
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,"Transaction Total");
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,String.valueOf(accountStatement.getTodebit()));
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,String.valueOf(accountStatement.getTocredit()));
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,"");
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,"");
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//        
//		table3row = table3.createRow(20);
//		table3cell = table3row.createCell(16,"");
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,"");
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,"Closing Balance");
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,"");
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,"");
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,String.valueOf(accountStatement.getClbalance()));
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//		table3cell = table3row.createCell(14,String.valueOf(accountStatement.getClinitbr()));
//		table3cell.setAlign(HorizontalAlignment.CENTER);
//        
//        table3.draw();
//        
//        cos.close();
//        
//        OutputStream outputStream = null;
//		File accountStatementtempfile = File.createTempFile("AccountStatement"+accountStatement.getId(), "pdf");
//		
//		outputStream = new FileOutputStream(accountStatementtempfile);
//        document.save(outputStream);
//        String filepath = accountStatementtempfile.getPath();
//		String filename = accountStatementtempfile.getName();
//        //document.save("C:\\Users\\abhis\\Documents\\panto\\liferay_day1\\blank.pdf");
//        document.close();
//        outputStream.close();
//        return fileUpload(themeDisplay, filepath, filename, resourceRequest, createdBy, reportUploadLogId, statusByUserName, serviceContext2, remarks);
//	}
	
	public  long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest, String param) {
		try {
		Date date = new Date();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime()+"_"+uploadPortletRequest.getFileName(param);
		
		String title = fileName;
		
		String description = "Account Statement.";
	
		File file = uploadPortletRequest.getFile(param);

		String mimeType =  MimeTypesUtil.getContentType(file);

		long repositoryId = themeDisplay.getScopeGroupId();

			Folder folder = getFolder(themeDisplay,"Monthly");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getFileEntryId();

		} catch (Exception e) {
			 _log.error(e);
			
		}
		return 0;
	}

	
	@SuppressWarnings("deprecation")
	public String fileUpload(ThemeDisplay themeDisplay,
			ResourceRequest resourceRequest, long createdBy, long reportUploadLogId, String statusByUserName, ServiceContext serviceContext2, String remarks ) {
		//UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		//Date date = new Date();
	//	File file = uploadPortletRequest.getFile("annexure2");
	//	String fileName = date.getTime()+"_"+file.getName();
//		String mimeType = "application/pdf";
//		String title = fileName;
//		String description = "This file is added via programatically";
//		long repositoryId = themeDisplay.getScopeGroupId();
		String downloadUrl = StringPool.BLANK;
		
		try {
		//	Folder folder = getFolder(themeDisplay,"NPSPDF");
			//ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),resourceRequest);
			//FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), title, mimeType, title, description, "",file, serviceContext);
			long annexure2FileId=uploadFILETOFOLDER(themeDisplay, resourceRequest, "annexure2");
			FileEntry entry=DLAppServiceUtil.getFileEntry(annexure2FileId);
			updateReportLog(createdBy,annexure2FileId, true, reportUploadLogId, WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext2, remarks);
			
			FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
		} catch (Exception e) {
			_log.info(e.getMessage());
			_log.error(e);
		}
		return downloadUrl;
	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

	public Folder getFolder(ThemeDisplay themeDisplay,String folderName) {

		Folder folder = null;
		try {
			//folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "NPSPDF");
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, folderName);
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	public void updateReportLog( long createdBy,long pdfFileId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogMakerLocalServiceUtil.updateReportUploadLog(createDate, createdBy, pdfFileId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext, remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	
		
	JSONArray jsonArray=JSONFactoryUtil.createJSONArray();
	JSONObject jsonObject=JSONFactoryUtil.createJSONObject();
	jsonObject.put("Name", "Annexure2");
	jsonObject.put("fileEntryId", pdfFileId);
	jsonArray.put(jsonObject);
	List<ReportUploadFileLog> fileLogs= ReportUploadFileLogLocalServiceUtil.findByReportUploadLogId(reportUploadLogId);
	ReportUploadFileLog fileLog=fileLogs.get(fileLogs.size()-1);
	fileLog.setFileList(jsonArray.toString());
	ReportUploadFileLogLocalServiceUtil.updateReportUploadFileLog(fileLog);
	}
	Log _log = LogFactoryUtil.getLog(getClass());
}
