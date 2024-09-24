package grievance.top.five.entities.month.mvc;

import com.daily.average.service.service.GrievanceTopFiveEntitiesMonthLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import grievance.top.five.entities.month.constants.GrievanceTopFiveEntitiesMonthPortletKeys;
import grievance.top.five.entities.month.util.GrievanceTopFiveEntitiesUtil;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + GrievanceTopFiveEntitiesMonthPortletKeys.GRIEVANCETOPFIVEENTITIESMONTH,
				"mvc.command.name=" + GrievanceTopFiveEntitiesMonthPortletKeys.UPLOAD_EXCEL_RESOURCE_COMMAND
		},
		service = MVCResourceCommand.class
)
public class UploadGrievanceExcelMVCResourceCommand extends BaseMVCResourceCommand {
	
	private static final Log LOG = LogFactoryUtil.getLog(UploadGrievanceExcelMVCResourceCommand.class);

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		LOG.info("Grievance Top Five Entities ::::: Start");
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		File file = uploadPortletRequest.getFile("grienvanceExcelFile");
		
		try {
			if(Validator.isNotNull(file)) {
				OPCPackage pkg = OPCPackage.open(file);
				XSSFWorkbook workbook  = new XSSFWorkbook(pkg);
				
				uploadMinistryWiseExcelData(workbook);
				uploadStateWiseExcelData(workbook);
				uploadPOPWiseExcelData(workbook);
				uploadCHOWiseExcelData(workbook);
			}
		} catch(Exception e) {
			LOG.error(e.getMessage());
		} finally {
			uploadPortletRequest.cleanUp();
		}
		
		try {
			PrintWriter out = resourceResponse.getWriter();
			out.println("Successfully Added the Data to Database.");
			out.flush();
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
		
		LOG.info("Grievance Top Five Entities ::::: End");
	}
	
	private void uploadMinistryWiseExcelData(XSSFWorkbook workbook) {
		List<Integer> rowsForMinistryWiseExcel = grievanceTopFiveEntitiesUtil.getRowsForMinistryWiseExcel();
		XSSFSheet sheet = workbook.getSheet("Ministry wise pending");
		uploadExcelData(rowsForMinistryWiseExcel, sheet, "Ministry Wise");
	}
	
	private void uploadStateWiseExcelData(XSSFWorkbook workbook) {
		List<Integer> rowsForMinistryWiseExcel = grievanceTopFiveEntitiesUtil.getRowsForStateWiseExcel();
		XSSFSheet sheet = workbook.getSheet("State wise pending");
		uploadExcelData(rowsForMinistryWiseExcel, sheet, "State Wise");
	}

	private void uploadPOPWiseExcelData(XSSFWorkbook workbook) {
		List<Integer> rowsForMinistryWiseExcel = grievanceTopFiveEntitiesUtil.getRowsForPOPWiseExcel();
		XSSFSheet sheet = workbook.getSheet("POP wise pending");
		uploadExcelData(rowsForMinistryWiseExcel, sheet, "POP Wise");
	}
	
	private void uploadCHOWiseExcelData(XSSFWorkbook workbook) {
		List<Integer> rowsForMinistryWiseExcel = grievanceTopFiveEntitiesUtil.getRowsForCHOWiseExcel();
		XSSFSheet sheet = workbook.getSheet("CHO wise pending");
		uploadExcelData(rowsForMinistryWiseExcel, sheet, "CHO Wise");
	}                                                                                                                               
	
	private void uploadExcelData(List<Integer> rowsForMinistryWiseExcel, XSSFSheet sheet, String wiseType) {
		for(Integer i : rowsForMinistryWiseExcel) {
			Integer rownum = i - 1;
			
			try {
				XSSFRow row = sheet.getRow(rownum);
				grievanceTopFiveEntitiesMonthLocalService.addGrievanceTopFiveEntitiesMonth(row.getCell(0).toString().trim(), 
																						   row.getCell(1).toString().trim(), 
																						   row.getCell(2).toString().trim(), 
																						   row.getCell(3).toString().trim(), 
																						   row.getCell(4).toString().trim(), 
																						   row.getCell(5).toString().trim(), 
																						   row.getCell(6).toString().trim(), 
																						   row.getCell(7).toString().trim(), 
																						   row.getCell(8).toString().trim(), 
																						   wiseType);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
	}
	
	@Reference
	GrievanceTopFiveEntitiesUtil grievanceTopFiveEntitiesUtil;
	
	@Reference
	GrievanceTopFiveEntitiesMonthLocalService grievanceTopFiveEntitiesMonthLocalService;
	
}
