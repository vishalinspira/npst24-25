package com.nps.custodian.reportuploadlog.asset;

import com.daily.average.service.model.ReportUploadLogCustodian;
import com.daily.average.service.service.ReportUploadLogCustodianLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.nps.custodian.reportuploadlog.constants.NpsCustodianReportuploadlogPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsCustodianReportuploadlogPortletKeys.NPSCUSTODIANREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class NpsCustodianReportuploadlogAssetFactory extends BaseAssetRendererFactory<ReportUploadLogCustodian>{
	
	public NpsCustodianReportuploadlogAssetFactory() {
	
		setClassName(ReportUploadLogCustodian.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsCustodianReportuploadlogPortletKeys.NPSCUSTODIANREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogCustodian> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogCustodian reportUploadLogCustodian = reportUploadLogCustodianLocalService.getReportUploadLogCustodian(classPK);
		NpsCustodianReportuploadlogAsset assetRender = new NpsCustodianReportuploadlogAsset(reportUploadLogCustodian);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogCustodian";
	}
	
	@Override
    public String getClassName() {
		return ReportUploadLogCustodian.class.getName();
	}

	@Reference
	ReportUploadLogCustodianLocalService reportUploadLogCustodianLocalService;
}
