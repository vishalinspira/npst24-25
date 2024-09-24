

package com.daily.average.service.service.impl;

import com.daily.average.service.model.Annexvarejection;
import com.daily.average.service.service.base.AnnexvarejectionLocalServiceBaseImpl;

import java.util.Iterator;
import java.util.List;


public class AnnexvarejectionLocalServiceImpl
	extends AnnexvarejectionLocalServiceBaseImpl {
	public void addAnnexvarejection(List<Annexvarejection> annexvarejections) {
		for (Iterator iterator = annexvarejections.iterator(); iterator.hasNext();) {
			Annexvarejection annexvarejection = (Annexvarejection) iterator.next();
			annexvarejectionLocalService.addAnnexvarejection(annexvarejection);
		}
	}
	
	public void deleteAnnexvarejectionByReportUploadLogId(Long reportUploadLogId) {
		List<Annexvarejection> annexvarejections = annexvarejectionPersistence.findByReportUploadLogId(reportUploadLogId);
		for (Annexvarejection annexvarejection : annexvarejections) {
			annexvarejectionLocalService.deleteAnnexvarejection(annexvarejection);
		}
	}
}