package com.nps.master.admin.web.scheduler;

import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.service.CalendarBookingServiceUtil;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.springframework.scheduling.support.CronSequenceGenerator;

import nps.common.service.constants.NPSTRoleConstants;
import nps.email.api.api.NpsEmailApi;
import nps.email.api.props.PropKeys;
import nps.email.api.props.PropValues;

@Component(immediate = true, service = BaseMessageListener.class)
public class AlertScheduler  extends BaseMessageListener {
private static final Log LOG = LogFactoryUtil.getLog(MasterAdminScheduler.class);
	
	private static final String DEFAULT_CRON_EXPRESSION = "0 0/10 * * * ?";
	
	private TriggerFactory _triggerFactory;
	
	private volatile boolean _initialized;
	
	private SchedulerEngineHelper _schedulerEngineHelper;
	
	@Reference(unbind = "-")
	protected void setTriggerFactory(TriggerFactory triggerFactory) {
		_triggerFactory = triggerFactory;
	}
	
	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(SchedulerEngineHelper schedulerEngineHelper) {
		_schedulerEngineHelper = schedulerEngineHelper;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		try {
			String listenerClass = getClass().getName();
			Trigger jobTrigger = _triggerFactory.createTrigger(listenerClass, listenerClass, new Date(), null, DEFAULT_CRON_EXPRESSION);
			SchedulerEntryImpl schedulerEntryImpl = new SchedulerEntryImpl(listenerClass, jobTrigger);
			
			if(_initialized) {
				deactive();
			}
			
			_schedulerEngineHelper.register(this, schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);
			
			LOG.info("Registered Scheduler with Cron Expression: " + DEFAULT_CRON_EXPRESSION);
			
			_initialized = true;
		} catch (Exception e) {
			LOG.error("Registered Scheduler with Cron Expression. Error: " + e.getMessage());
		}
	}
	
	@Deactivate
	protected void deactive() {
		if(_initialized) {
			try {
				_schedulerEngineHelper.unregister(this);
			}
			catch(Exception e) {
				LOG.error(e.getMessage());
			}
		}
		
		_initialized = false;
	}

	@Override
	protected void doReceive(Message message) {
		
		
		//sendAlert();
	}
	
	public void sendAlert() {
		
		
		if (!isHoliday()) {
			List<ReportMaster> reportMasterList = ReportMasterLocalServiceUtil.getReportMasters(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			Date date = new Date();
			Date reportDate1 = date;
			Date reportDate2 = date;
			String status = "Intimation";
			try {
				reportDate1 = addDate(date, 2);
				reportDate2 = addDate(date, 2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				LOG.error(e);
			}
			
			/*for(ReportMaster rm : reportMasterList) {
				CronSequenceGenerator generator = new CronSequenceGenerator(rm.getDueDate());
				Date nextRunDate = generator.next(new Date());*/
			List<ReportUploadLog>  reportUploadLogs = null;//ReportUploadLogLocalServiceUtil.fetchReportUploadLogByReportDate(reportDate1, reportDate2, 0);
			
			for (ReportUploadLog reportUploadLog : reportUploadLogs) {
				ReportMaster reportMaster = ReportMasterLocalServiceUtil.fetchReportMaster(reportUploadLog.getReportMasterId());
				String department = reportMaster.getDepartment();
				if(department == "Trustee Bank") {
					
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, NPSTRoleConstants.MAKER, status , reportMaster.getReportName());
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, NPSTRoleConstants.CHECKER, status , reportMaster.getReportName());
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, NPSTRoleConstants.SUPERVISOR, status , reportMaster.getReportName());
				}else if(department == "CRA") {
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, reportUploadLog.getIntermediaryname()+"-"+NPSTRoleConstants.MAKER, status , reportMaster.getReportName());
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, reportUploadLog.getIntermediaryname()+"-"+NPSTRoleConstants.CHECKER, status , reportMaster.getReportName());
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, reportUploadLog.getIntermediaryname()+"-"+NPSTRoleConstants.SUPERVISOR, status , reportMaster.getReportName());
				}else if (department == "Custodian") {
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, NPSTRoleConstants.CUSTODIAN_CHECKER, status , reportMaster.getReportName());
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, NPSTRoleConstants.CUSTODIAN_MAKER, status , reportMaster.getReportName());
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, NPSTRoleConstants.CUSTODIAN_SUPERVISOR, status , reportMaster.getReportName());
				}else if (department == "Grievances") {
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, reportUploadLog.getIntermediaryname()+"-"+NPSTRoleConstants.MAKER, status , reportMaster.getReportName());
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, reportUploadLog.getIntermediaryname()+"-"+NPSTRoleConstants.CHECKER, status , reportMaster.getReportName());
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, reportUploadLog.getIntermediaryname()+"-"+NPSTRoleConstants.SUPERVISOR, status , reportMaster.getReportName());
				}else if (department == "PFM") {
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, NPSTRoleConstants.PFM_MAKER+"-"+reportUploadLog.getIntermediaryname(), status , reportMaster.getReportName());
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, NPSTRoleConstants.PFM_CHECKER+"-"+reportUploadLog.getIntermediaryname(), status , reportMaster.getReportName());
					npsEmailApi.sendAlertEmail(PropValues.COMPANY_ID, NPSTRoleConstants.PFM_SUPERVISOR+"-"+reportUploadLog.getIntermediaryname(), status , reportMaster.getReportName());
				}
				
			}
			
			
		}
		
		
	}
	
	private boolean isHoliday() {
		Date date = new Date();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		boolean holiday = isWeekend(date);
		//List<Calendar> calendars = CalendarLocalServiceUtil.getCalendars(-1, -1);
		Calendar  calendar = CalendarLocalServiceUtil.fetchGroupCalendar(PropValues.COMPANY_ID, PropValues.GROUP_ID, "Liferay DXP");
		//LOG.info("calendar1"+calendar1);
		//for (Calendar calendar : calendars) {}
		LOG.info("calendar"+calendar);
		List<CalendarBooking>  calendarBookings =  CalendarBookingLocalServiceUtil.getCalendarBookings(calendar.getCalendarId());
		for (CalendarBooking calendarBooking : calendarBookings) {
			Date eventDate = new Date(calendarBooking.getStartTime());
			LOG.info("calendarBooking title "+ calendarBooking.getTitle());
			LOG.info("calendarBooking start time "+eventDate);
			holiday = dateFormat.format(date).equals(dateFormat.format(eventDate));
		}
		
		return holiday;
	}
	
	private  boolean isWeekend(Date date) {
		 
        // get Calendar instance
		java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
 
 
        // get Day of week for the passed LocalDate
        return (calendar.get(java.util.Calendar.DAY_OF_WEEK) == 1) 
                || (calendar.get(java.util.Calendar.DAY_OF_WEEK) == 7);
    }
	
	private Date addDate(Date date, int days) throws ParseException {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(java.util.Calendar.DAY_OF_MONTH , days);  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String dateAfter = sdf.format(calendar.getTime());  
        return sdf.parse(dateAfter);
	}
	
	@Reference
	NpsEmailApi npsEmailApi;
}
