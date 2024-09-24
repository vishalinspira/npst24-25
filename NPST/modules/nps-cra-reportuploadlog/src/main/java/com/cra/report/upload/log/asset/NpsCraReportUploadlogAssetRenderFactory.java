package com.cra.report.upload.log.asset;

import com.cra.report.upload.log.constants.NpsCraReportuploadlogPortletKeys;
import com.daily.average.service.model.ReportUploadLogCRA;
import com.daily.average.service.service.ReportUploadLogCRALocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsCraReportuploadlogPortletKeys.NPSCRAREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class NpsCraReportUploadlogAssetRenderFactory extends BaseAssetRendererFactory<ReportUploadLogCRA>{
	public NpsCraReportUploadlogAssetRenderFactory() {
		setClassName(ReportUploadLogCRA.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsCraReportuploadlogPortletKeys.NPSCRAREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogCRA> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogCRA reportUploadLogGrievances = reportUploadLogCRALocalService.getReportUploadLogCRA(classPK);
		NpsCraReportUploadlogAssetRender assetRender = new NpsCraReportUploadlogAssetRender(reportUploadLogGrievances);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogCRA";
	}
	
	@Override
    public String getClassName() {
		return ReportUploadLogCRA.class.getName();
	}

	@Reference
	ReportUploadLogCRALocalService reportUploadLogCRALocalService;
}
