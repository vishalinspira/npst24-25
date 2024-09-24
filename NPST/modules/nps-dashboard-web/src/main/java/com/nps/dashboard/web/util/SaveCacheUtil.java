package com.nps.dashboard.web.util;

import nps.cache.schema.service.ReportStatusLocalServiceUtil;

public class SaveCacheUtil {

	public static void saveCache(String skey, String data) {
		ReportStatusLocalServiceUtil.saveCache(skey, data);
	}
}
