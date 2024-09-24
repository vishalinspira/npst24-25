package com.nps.master.admin.web.scheduler;

import com.daily.average.service.model.IntermediaryList;
import com.daily.average.service.model.ReportMaster;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.service.IntermediaryListLocalServiceUtil;
import com.daily.average.service.service.ReportMasterLocalServiceUtil;
import com.daily.average.service.service.ReportUploadLogLocalServiceUtil;
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
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.springframework.scheduling.support.CronSequenceGenerator;

@Component(immediate = true, service = BaseMessageListener.class)
public class GenReportDateScheduler extends BaseMessageListener {

	private static final Log LOG = LogFactoryUtil.getLog(GenReportDateScheduler.class);

	private static final String DEFAULT_CRON_EXPRESSION = "0 0 23 * * ?";

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
			Trigger jobTrigger = _triggerFactory.createTrigger(listenerClass, listenerClass, new Date(), null,
					DEFAULT_CRON_EXPRESSION);
			SchedulerEntryImpl schedulerEntryImpl = new SchedulerEntryImpl(listenerClass, jobTrigger);

			if (_initialized) {
				deactive();
			}

			_schedulerEngineHelper.register(this, schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);

			LOG.info("Registered Scheduler with Cron Expression: " + DEFAULT_CRON_EXPRESSION);

			_initialized = true;
		} catch (Exception e) {
			LOG.error("Registered Scheduler with Cron Expression. Error: " + e.getMessage());
		}

		// createNewReportLogs();
	}

	@Deactivate
	protected void deactive() {
		if (_initialized) {
			try {
				_schedulerEngineHelper.unregister(this);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}

		_initialized = false;
	}

	@Override
	protected void doReceive(Message message) {
		LOG.info("scheduler called.");
		Date date = new Date();
		createNewReportLogs(date);
	}

	public void createNewReportLogs(Date reportDate) {
		LOG.info("Running Scheduler to Create Report Logs");

		List<ReportMaster> reportMasterList = ReportMasterLocalServiceUtil.getReportMasters(QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ReportMaster rm : reportMasterList) {
			try {
			CronSequenceGenerator generator = new CronSequenceGenerator(rm.getDueDate());
			Date nextRunDate = generator.next(reportDate);
			Date today = reportDate;
			Boolean checkMonth = false;
			if (rm.getReportType().equalsIgnoreCase("Monthly")) {
				Calendar todayCal = Calendar.getInstance();
				todayCal.setTime(today);
				int currentMonth = todayCal.get(Calendar.MONTH);
				int currentYear = todayCal.get(Calendar.YEAR);

				Calendar reportCal = Calendar.getInstance();
				reportCal.setTime(nextRunDate);
				int reportMonth = reportCal.get(Calendar.MONTH);
				int reportYear = reportCal.get(Calendar.YEAR);
				LOG.info(rm + "  date " + nextRunDate + "(currentMonth>reportMonth)" + (currentMonth >= reportMonth)
						+ "currentMonth" + currentMonth);
				checkMonth = currentMonth >= reportMonth && (currentYear == reportYear);
			} else if (rm.getReportType().equalsIgnoreCase("Quarterly")
					|| rm.getReportType().equalsIgnoreCase("Annually")
					|| rm.getReportType().equalsIgnoreCase("Half-Yearly")) {
				Calendar todayCal = Calendar.getInstance();
				todayCal.setTime(today);
				int currentMonth = todayCal.get(Calendar.MONTH);
				int currentYear = todayCal.get(Calendar.YEAR);

				Calendar reportCal = Calendar.getInstance();
				reportCal.setTime(nextRunDate);
				int reportMonth = reportCal.get(Calendar.MONTH);
				int reportYear = reportCal.get(Calendar.YEAR);
				checkMonth = (currentMonth >= reportMonth) && (currentYear == reportYear);
			} else {
				checkMonth = true;
			}
			if (checkMonth) {
				LOG.info(rm + "  date " + nextRunDate);
				List<IntermediaryList> intermediaryLists = IntermediaryListLocalServiceUtil
						.fetchIntermediaryListByTypeId(rm.getIntermediarytype());
				if (Validator.isNotNull(intermediaryLists) && intermediaryLists.size() > 0) {
					for (IntermediaryList intermediaryList : intermediaryLists) {
						ReportUploadLog reportUploadLog = ReportUploadLogLocalServiceUtil
								.fetchByReportMasterIdAndReportDateAndIntermediaryid(rm.getReportMasterId(),
										nextRunDate, intermediaryList.getId());
						if (Validator.isNull(reportUploadLog)) {
							ReportUploadLogLocalServiceUtil.addReportUploadLogWithIntermediary(rm.getReportMasterId(),
									nextRunDate, null, 0L, 0L, false, intermediaryList.getIntermediaryname(),
									intermediaryList.getId());
						}
					}
				} else {
					ReportUploadLog reportUploadLog = ReportUploadLogLocalServiceUtil
							.fetchByReportMasterIdAndReportDate(rm.getReportMasterId(), nextRunDate);

					if (Validator.isNull(reportUploadLog)) {
						ReportUploadLogLocalServiceUtil.addReportUploadLog(rm.getReportMasterId(), nextRunDate, null,
								0L, 0L, false);
					}
				}
//				LOG.info((rm.getReportMasterId() > 9 ? rm.getReportMasterId() : "0" + rm.getReportMasterId())  + " ::::: " + nextRunDate);

			}
		}catch (Exception e) {
			LOG.error("error while generating due date:  "+e.getMessage());
		}
		}
	}

}
