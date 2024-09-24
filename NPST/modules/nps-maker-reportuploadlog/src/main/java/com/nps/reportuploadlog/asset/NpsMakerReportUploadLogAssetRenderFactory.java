package com.nps.reportuploadlog.asset;

import com.daily.average.service.model.ReportUploadLogMaker;
import com.daily.average.service.service.ReportUploadLogMakerLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.nps.reportuploadlog.constants.NpsMakerReportuploadlogPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsMakerReportuploadlogPortletKeys.NPSMAKERREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class NpsMakerReportUploadLogAssetRenderFactory extends BaseAssetRendererFactory<ReportUploadLogMaker>{
	
	public NpsMakerReportUploadLogAssetRenderFactory(){
		setClassName(ReportUploadLogMaker.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsMakerReportuploadlogPortletKeys.NPSMAKERREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogMaker> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogMaker reportUploadLogMaker = reportUploadLogMakerLocalService.getReportUploadLogMaker(classPK);
		NpsMakerReportUploadLogAssetRender assetRender = new NpsMakerReportUploadLogAssetRender(reportUploadLogMaker);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogMaker";
	}
	
	@Override
    public String getClassName() {
		return ReportUploadLogMaker.class.getName();
	}

	@Reference
	ReportUploadLogMakerLocalService reportUploadLogMakerLocalService;
}
