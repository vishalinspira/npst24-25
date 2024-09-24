package com.npsGrievances.reportUploadlog.asset;

import com.daily.average.service.model.ReportUploadLogGrievances;
import com.daily.average.service.service.ReportUploadLogGrievancesLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.npsGrievances.reportUploadlog.constants.NpsGrievancesReportUploadlogPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsGrievancesReportUploadlogPortletKeys.NPSGRIEVANCESREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)

public class NpsGrievancesReportUploadlogAssetRenderFactory extends BaseAssetRendererFactory<ReportUploadLogGrievances>{
	
	public NpsGrievancesReportUploadlogAssetRenderFactory() {
		setClassName(ReportUploadLogGrievances.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsGrievancesReportUploadlogPortletKeys.NPSGRIEVANCESREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogGrievances> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogGrievances reportUploadLogGrievances = reportUploadLogGrievancesLocalService.getReportUploadLogGrievances(classPK);
		NpsGrievancesReportUploadlogAssetRender assetRender = new NpsGrievancesReportUploadlogAssetRender(reportUploadLogGrievances);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogGrievances";
	}
	
	@Override
    public String getClassName() {
		return ReportUploadLogGrievances.class.getName();
	}

	@Reference
	ReportUploadLogGrievancesLocalService reportUploadLogGrievancesLocalService;
}
