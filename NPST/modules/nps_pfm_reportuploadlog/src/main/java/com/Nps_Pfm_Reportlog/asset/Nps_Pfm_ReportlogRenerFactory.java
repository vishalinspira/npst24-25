package com.Nps_Pfm_Reportlog.asset;

import com.Nps_Pfm_Reportlog.constants.Nps_Pfm_ReportlogPortletKeys;
import com.daily.average.service.model.ReportUploadLogPFM;
import com.daily.average.service.service.ReportUploadLogPFMLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + Nps_Pfm_ReportlogPortletKeys.NPS_PFM_REPORTLOG},
        service = AssetRendererFactory.class
)
public class Nps_Pfm_ReportlogRenerFactory extends BaseAssetRendererFactory<ReportUploadLogPFM>{
	
	public Nps_Pfm_ReportlogRenerFactory(){
		setClassName(ReportUploadLogPFM.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(Nps_Pfm_ReportlogPortletKeys.NPS_PFM_REPORTLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogPFM> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogPFM reportUploadLogPFM = reportUploadLogPFMLocalService.getReportUploadLogPFM(classPK);
		Nps_Pfm_ReportlogRender assetRender = new Nps_Pfm_ReportlogRender(reportUploadLogPFM);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogPFM";
	}
	
	@Override
    public String getClassName() {
		return ReportUploadLogPFM.class.getName();
	}

	@Reference
	ReportUploadLogPFMLocalService reportUploadLogPFMLocalService;
}

