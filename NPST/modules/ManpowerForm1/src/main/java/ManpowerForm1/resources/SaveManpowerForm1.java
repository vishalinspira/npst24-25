package ManpowerForm1.resources;

import com.daily.average.service.model.Manpowerform_i;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.Manpowerform_iLocalService;
import com.daily.average.service.service.Manpowerform_iLocalServiceUtil;

import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.daily.pfm.service.service.manpowersec1LocalService;

import com.daily.pfm.service.service.manpowersec2LocalService;
import com.daily.pfm.service.service.manpowersec2aLocalService;
import com.daily.pfm.service.service.manpowersec2bLocalService;
import com.daily.pfm.service.service.manpowersec3LocalService;
import com.daily.pfm.service.service.manpowersec4LocalService;
import com.daily.pfm.service.service.manpowersec5LocalService;
import com.daily.pfm.service.service.manpowersec5aLocalService;
import com.daily.pfm.service.service.manpowersec5bLocalService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import ManpowerForm1.constants.ManpowerForm1PortletKeys;
import ManpowerForm1.util.Docutil;
import nps.common.service.util.CommonRoleService;

@Component(property = { 
		"javax.portlet.name=" + ManpowerForm1PortletKeys.MANPOWERFORM1,
		 "mvc.command.name=" + ManpowerForm1PortletKeys.manpower, 
		}, 
service = MVCResourceCommand.class)
public class SaveManpowerForm1 extends BaseMVCResourceCommand {
	private static Log _log = LogFactoryUtil.getLog(SaveManpowerForm1.class);

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		// TODO Auto-generated method stub
 _log.info("Serve Resource method");
		
		boolean isError = false;
		
		try {
			
			createMPF( resourceRequest,  resourceResponse);
			
		} catch (Exception e) {
			isError = true;
			_log.error(e);
		}
		
		try {
			PrintWriter writer = resourceResponse.getWriter();
			if(!isError) {
				_log.info("Success");
				writer.print("Success");
			}
			else {
				_log.info("Error");
				writer.print("Error");
			}
		} catch (Exception e) {
			_log.error(e);
		}	

	}
	
	public void createMPF(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		manpower(resourceRequest, resourceResponse);
		manpower1(resourceRequest, resourceResponse);
		manpower2(resourceRequest, resourceResponse);
		manpower3(resourceRequest, resourceResponse);
		manpower4(resourceRequest, resourceResponse);
		manpower5(resourceRequest, resourceResponse);
		manpower6(resourceRequest, resourceResponse);
		manpower7(resourceRequest, resourceResponse);
		manpower8(resourceRequest, resourceResponse);
		saveworkflow(resourceRequest, resourceResponse);
	}
	public void saveworkflow(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long pdf_file_fileEntryId = 0l;
		try {
			_log.info("pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
			pdf_file_fileEntryId = Docutil.addDocuments(resourceRequest, "Manpower_Form_1_File", "pdf");
			_log.info("Manpower_Form_1_File -------------  pdf_file_fileEntryId  :::: "+pdf_file_fileEntryId);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		
		Long createdBy = themeDisplay.getUserId();
		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			_log.error("Exception on getting servicecontext : "+e.getMessage(),e);
		}
		
		Long fileEntryId =0l;
		
		try {
			//List<ReportUploadLog> reportUploadLog = ReportUploadLogLocalServiceUtil.fetchByReportMasterIdAndUploadedAndIntermediaryid(reportMasterId, 0, 0);
			long intermediaryid = commonRoleService.getUserIntermediaryId(2, createdBy, themeDisplay.getCompanyId());
					
			String intermediaryname = IntermediaryListLocalServiceUtil.fetchIntermediaryList(intermediaryid).getIntermediaryname();
			ReportUploadLog reportUploadLog= ReportUploadLogLocalServiceUtil.addReportUploadLogWithIntermediary(132, new Date(), new Date(), createdBy, 
					fileEntryId, false, intermediaryname, intermediaryid);
			
			Manpowerform_i manpowerform_i =manpowerform_iLocalService.addManpowerform_i(reportUploadLog.getReportUploadLogId(), new Date(), createdBy);
			_log.info("statusByUserName"+statusByUserName);
			Manpowerform_iLocalServiceUtil.updateReportUploadLog(new Date(), createdBy, pdf_file_fileEntryId, true, 
					reportUploadLog.getReportUploadLogId(), WorkflowConstants.STATUS_PENDING, createdBy, statusByUserName, new Date(), serviceContext, "", true);
		} catch (Exception e) {
			
		}
		
	}
	public void manpower(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		_log.info("Inside manpowerform");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
		
		
		Long biodata1 = Docutil.addDocument(resourceRequest, "biodata_1");
		Long biodata2 = Docutil.addDocument(resourceRequest, "biodata_2");
		
		List<Long> biodatafileEntryId = new ArrayList<Long>();
		biodatafileEntryId.add(biodata1);
		biodatafileEntryId.add(biodata2);
		
		
		try {
			for (int i = 0; i < 2; i++) {
				
				String destination = ParamUtil.getString(resourceRequest, "destination"+i+"_i[]");
				String name = ParamUtil.getString(resourceRequest, "name"+i+"_i[]");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date  dateofappoinment= ParamUtil.getDate(resourceRequest, "dateofappoinment"+i+"_i[]", dateFormat);
				long contactno = ParamUtil.getLong(resourceRequest, "contactno"+i+"_i[]");
				String email = ParamUtil.getString(resourceRequest, "email"+i+"_i[]");
				String highestqualification = ParamUtil.getString(resourceRequest, "highestqualification"+i+"_i[]");
				long totalprofexp = ParamUtil.getLong(resourceRequest, "totalprofexp"+i+"_i[]");
				String deputation = ParamUtil.getString(resourceRequest, "deputation"+i+"_i[]");
				long linkedinid = ParamUtil.getLong(resourceRequest, "linkedinid"+i+"_i[]");
				String position = ParamUtil.getString(resourceRequest, "position"+i+"_i[]");
				Date dateOfBoardMeetngApprove = ParamUtil.getDate(resourceRequest, "dateOfBoardMeetngApprove"+i+"_i[]",dateFormat);
				
				manpowersec1LocalService.addmanpowersec1(destination, name, dateofappoinment, contactno, email, 
						highestqualification, totalprofexp, deputation, linkedinid, position, dateOfBoardMeetngApprove, biodatafileEntryId.get(i), reportUploadLogId);
			
				_log.info("======= " +destination + " ====== " + name +  " ====== " + dateofappoinment +  " ====== " + contactno + " ====== " + email + " ====== " + highestqualification + " ====== " + totalprofexp +
						" ====== " + deputation + " ====== " + linkedinid + " ====== " + position + " ====== " + dateOfBoardMeetngApprove  + "======"+ dateofappoinment+ "======"+biodatafileEntryId);
			
			}
			
			
			} catch (Exception e) {
			_log.info(e.getMessage());
		}
	}

	public void manpower1(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		_log.info("Inside manpowerform");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
		
		String iccstatus = ParamUtil.getString(resourceRequest, "iccstatus");
		String rmccstatus = ParamUtil.getString(resourceRequest, "rmccstatus");
		
		try {
			manpowersec2LocalService.addmanpowersec2(reportUploadLogId,iccstatus, rmccstatus);
		} catch (Exception e) {
			
			 _log.error(e);
		}
		
		
		//_log.info("======= " +type + " ====== " + status);
		
		
	}
  
	public void manpower2(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		_log.info("Inside manpowerform");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
		
		
		try {
				for (int i = 0; i < 7; i++) {
					
					String designation = ParamUtil.getString(resourceRequest, "designation"+i+"_ib[]");
					String name = ParamUtil.getString(resourceRequest, "name"+i+"_ib[]");
					String chairperson_member = ParamUtil.getString(resourceRequest, "chairpersonmember"+i+"_ib[]");
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date dateofappointmentinthecommittee = ParamUtil.getDate(resourceRequest, "dateofappointmentinthecommittee"+i+"_ib[]",dateFormat);
					
					
					/*_log.info("======= " +designation[i] + " ====== " + name[i] + " ====== " 
				+ chairperson_member[i] + " ====== " + dateofappointmentinthecommittee[i]);*/
					
					manpowersec2aLocalService.addmanpowersec2a(designation, name, chairperson_member, 
							dateofappointmentinthecommittee, reportUploadLogId);
				}
		} catch (Exception e) {
			
			 _log.error(e);
		}
		
		//_log.info("======= " +designation + " ====== " + name + " ====== " + chairperson_member + " ====== " + dateofappointmentinthecommittee);
		
	}
	 
	public void manpower3(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		_log.info("Inside manpowerform");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
		
		
		try {
			for (int i = 1; i < 8; i++) {
				
				String designation = ParamUtil.getString(resourceRequest, "designation"+i+"_ic[]");
				String name = ParamUtil.getString(resourceRequest, "name"+i+"_ic[]");
				String chairperson_member = ParamUtil.getString(resourceRequest, "chairpersonmember"+i+"_ic[]");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateofappointmentinthecommitte = ParamUtil.getDate(resourceRequest, "dateofappointmentinthecommittee"+i+"_ic[]",dateFormat);
				
				
				manpowersec2bLocalService.addmanpowersec2b(designation, name, chairperson_member, dateofappointmentinthecommitte, reportUploadLogId);

				_log.info("======= " +designation + " ====== " + name + " ====== " + chairperson_member + " ====== " + dateofappointmentinthecommitte);
				
				}
			} catch (Exception e) {
			
			 _log.error(e);
		}
		
	}
	
	public void manpower4(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		_log.info("Inside manpowerform");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
		
		String name = ParamUtil.getString(resourceRequest, "name_ii");
		String typeofdirector = ParamUtil.getString(resourceRequest, "typeofdirector_ii");
		String chairperson_member = ParamUtil.getString(resourceRequest, "Chairpersonmember_ii");
		String Independent_nonindependent = ParamUtil.getString(resourceRequest, "Independent_Nonindependent_ii");
		String directoridentificationnumber = ParamUtil.getString(resourceRequest, "directorIdentificationNumber_ii");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateofappointment = ParamUtil.getDate(resourceRequest, "dateofappointment_ii",dateFormat);
		String highestqualification = ParamUtil.getString(resourceRequest, "highestQualification_ii");
		long totalprofessionalexperience = ParamUtil.getLong(resourceRequest,"totalprofessionalexperience_ii");
		//long resume_biodata = ParamUtil.getLong(resourceRequest, "resume_Biodata_ii");
		String formMBP1ofthedirector = ParamUtil.getString(resourceRequest, "formMBP1ofthedirector_ii");
		long resume_BiodatafileEntryId= Docutil.addDocument(resourceRequest, "resumeBiodata_ii");
		
		
		try {
			manpowersec3LocalService.addmanpowersec3(name, typeofdirector, chairperson_member, Independent_nonindependent, directoridentificationnumber, dateofappointment, highestqualification, totalprofessionalexperience, resume_BiodatafileEntryId, formMBP1ofthedirector, reportUploadLogId);
		} catch (Exception e) {
			
			 _log.error(e);
		}
		
		_log.info(" ====== " + name +  " ====== " + typeofdirector + " ====== " + chairperson_member + " ====== " + Independent_nonindependent + " ====== " + directoridentificationnumber +
				" ====== " + dateofappointment + " ====== " + highestqualification + " ====== " + totalprofessionalexperience + " ====== " + resume_BiodatafileEntryId + " ====== " + formMBP1ofthedirector);
		
	}
	
	public void manpower5(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		_log.info("Inside manpowerform");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
		
		
		try {
			for (int i = 0; i < 3; i++) {
				
				String namesofthecompanies = ParamUtil.getString(resourceRequest, "namesofthecompanies"+i+"_iia[]");
				String natureofinterest = ParamUtil.getString(resourceRequest, "natureofinterest"+i+"_iia[]");
				String shareholding = ParamUtil.getString(resourceRequest, "shareholding"+i+"_iia[]");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateoninterestorconcernarose = ParamUtil.getDate(resourceRequest, "dateoninterestorconcernarose"+i+"_iia[]",dateFormat);
				
				
				manpowersec4LocalService.addmanpowersec4(namesofthecompanies, natureofinterest, shareholding, dateoninterestorconcernarose, reportUploadLogId);
				

				_log.info("======= " +namesofthecompanies + " ====== " + natureofinterest + " ====== " + shareholding + " ====== " + dateoninterestorconcernarose);
				
			
			} 
			}catch (Exception e) {
			
			 _log.error(e);
			}
		
	}
	public void manpower6(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		_log.info("Inside manpowerform");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
		
		String iccstatusi = ParamUtil.getString(resourceRequest, "iccstatusi");
		String rmccstatusi = ParamUtil.getString(resourceRequest, "rmccstatusi");
		
		try {
			manpowersec5LocalService.addmanpowersec5(reportUploadLogId,iccstatusi, rmccstatusi);
			
			//_log.info("======= " +type + " ====== " + status);
		} catch (Exception e) {
			
			 _log.error(e);
		}
		
		
		
	}
	
	public void manpower7(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		_log.info("Inside manpowerform");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
		
		
		try {
			for (int i = 1; i < 8; i++) {
				
				String designation = ParamUtil.getString(resourceRequest, "designation"+i+"_iib[]");
				String name = ParamUtil.getString(resourceRequest, "name"+i+"_iib[]");
				String chairperson_member = ParamUtil.getString(resourceRequest, "chairpersonmember"+i+"_iib[]");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateofappointmentinthecommitte = ParamUtil.getDate(resourceRequest, "dateofappointmentinthecommittee"+i+"_iib[]",dateFormat);
				
				
				_log.info("======= " +designation + " ====== " 
						+ name + " ====== " + chairperson_member + " ====== " + dateofappointmentinthecommitte);
							
				manpowersec5aLocalService.addmanpowersec5a(designation, name, chairperson_member, dateofappointmentinthecommitte, reportUploadLogId);
			

				_log.info("======= " +designation + " ====== " + name + " ====== " + chairperson_member + " ====== " + dateofappointmentinthecommitte);
				
			}
			
		} catch (Exception e) {
			
			 _log.error(e);
		}
		
		
	}
	
	public void manpower8(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		_log.info("Inside manpowerform");
		
		int rowCount = ParamUtil.getInteger(resourceRequest, "rowCountFT");
		_log.info("rowcount " + rowCount);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long reportUploadLogId = ParamUtil.getLong(resourceRequest, "reportUploadLogId");
		
		_log.info("reportUploadLogId " + reportUploadLogId);
		
		/*String designation[] = ParamUtil.getStringValues(resourceRequest, "designation_iic[]");
		String name[] = ParamUtil.getStringValues(resourceRequest, "name_iic[]");
		String chairperson_member[] = ParamUtil.getStringValues(resourceRequest, "chairpersonmember_iic[]");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateofappointmentinthecommitte[] = ParamUtil.getDateValues(resourceRequest, "dateofappointmentinthecommittee_iic[]",dateFormat);*/
		
		
		try {
			
			for (int i = 0; i < 7; i++) {
				String designation = ParamUtil.getString(resourceRequest, "designation"+i+"_iic[]");
				String name = ParamUtil.getString(resourceRequest, "name"+i+"_iic[]");
				String chairperson_member = ParamUtil.getString(resourceRequest, "chairpersonmember"+i+"_iic[]");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateofappointmentinthecommitte = ParamUtil.getDate(resourceRequest, "dateofappointmentinthecommittee"+i+"_iic[]",dateFormat);
				
				manpowersec5bLocalService.addmanpowersec5b(designation, name, chairperson_member, dateofappointmentinthecommitte, reportUploadLogId);
				
				_log.info("======= " +designation + " ====== " + name + " ====== " + chairperson_member + " ====== " + dateofappointmentinthecommitte);
				
			}
		} catch (Exception e) {
			
			 _log.error(e);
		}
		
		
	}
	

	@Reference
	manpowersec1LocalService manpowersec1LocalService;
	@Reference
	manpowersec2LocalService manpowersec2LocalService;
	@Reference
	manpowersec2aLocalService manpowersec2aLocalService;
	@Reference
	manpowersec2bLocalService manpowersec2bLocalService;
	@Reference
	manpowersec3LocalService manpowersec3LocalService;
	@Reference
	manpowersec4LocalService manpowersec4LocalService ;
	@Reference
	manpowersec5aLocalService manpowersec5aLocalService;
	@Reference
	manpowersec5bLocalService manpowersec5bLocalService;
	@Reference
	manpowersec5LocalService manpowersec5LocalService;
	
	@Reference
	Manpowerform_iLocalService manpowerform_iLocalService;
	
	@Reference
	private CommonRoleService commonRoleService;
}
