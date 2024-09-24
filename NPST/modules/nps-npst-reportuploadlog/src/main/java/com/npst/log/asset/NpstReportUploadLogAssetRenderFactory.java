package com.npst.log.asset;

import com.daily.average.service.model.ReportUploadLogNPST;
import com.daily.average.service.service.ReportUploadLogNPSTLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.npst.log.constants.NpsNpstReportuploadlogPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsNpstReportuploadlogPortletKeys.NPSNPSTREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class NpstReportUploadLogAssetRenderFactory extends BaseAssetRendererFactory<ReportUploadLogNPST> {

	public NpstReportUploadLogAssetRenderFactory() {
		setClassName(ReportUploadLogNPST.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsNpstReportuploadlogPortletKeys.NPSNPSTREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogNPST> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogNPST reportUploadLogNPST = reportUploadLogNPSTLocalService.getReportUploadLogNPST(classPK);
		NpstReportUploadLogAssetRender assetRender = new NpstReportUploadLogAssetRender(reportUploadLogNPST);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogNPST";
	}
	
	@Override
	public String getClassName() {
		return ReportUploadLogNPST.class.getName();
	}
	
	@Reference
	ReportUploadLogNPSTLocalService reportUploadLogNPSTLocalService;
	
}
