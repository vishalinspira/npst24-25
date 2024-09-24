package com.nps.cra.am.reportuploadlog.asset;

import com.daily.average.service.model.ReportUploadLogCRAAM;
import com.daily.average.service.service.ReportUploadLogCRAAMLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.nps.cra.am.reportuploadlog.constants.NpsCraAmReportuploadlogPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsCraAmReportuploadlogPortletKeys.NPSCRAAMREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class NpsCraReportUploadlogAssetRenderFactory extends BaseAssetRendererFactory<ReportUploadLogCRAAM> {

	public NpsCraReportUploadlogAssetRenderFactory() {
		setClassName(ReportUploadLogCRAAM.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsCraAmReportuploadlogPortletKeys.NPSCRAAMREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogCRAAM> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogCRAAM reportUploadLogGrievances = reportUploadLogCRAAMLocalService.getReportUploadLogCRAAM(classPK);
		NpsCraAMReportUploadlogAssetRender assetRender = new NpsCraAMReportUploadlogAssetRender(reportUploadLogGrievances);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogCRA";
	}
	
	@Override
    public String getClassName() {
		return ReportUploadLogCRAAM.class.getName();
	}

	@Reference
	ReportUploadLogCRAAMLocalService reportUploadLogCRAAMLocalService;
}
