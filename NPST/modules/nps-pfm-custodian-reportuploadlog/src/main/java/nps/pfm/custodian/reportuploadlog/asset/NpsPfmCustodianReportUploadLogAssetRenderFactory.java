package nps.pfm.custodian.reportuploadlog.asset;

import com.daily.average.service.model.ReportUploadLogPFMCustodian;
import com.daily.average.service.service.ReportUploadLogPFMCustodianLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.pfm.custodian.reportuploadlog.constants.NpsPfmCustodianReportuploadlogPortletKeys;

@Component(immediate = true, property = { "javax.portlet.name="
		+ NpsPfmCustodianReportuploadlogPortletKeys.NPSPFMCUSTODIANREPORTUPLOADLOG }, service = AssetRendererFactory.class)
public class NpsPfmCustodianReportUploadLogAssetRenderFactory
		extends BaseAssetRendererFactory<ReportUploadLogPFMCustodian> {

	public NpsPfmCustodianReportUploadLogAssetRenderFactory() {
		setClassName(ReportUploadLogPFMCustodian.class.getName());
		setCategorizable(true);
		setLinkable(true);
		setPortletId(NpsPfmCustodianReportuploadlogPortletKeys.NPSPFMCUSTODIANREPORTUPLOADLOG);
		setSearchable(true);
		setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogPFMCustodian> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogPFMCustodian reportUploadLog = reportUploadLogPFMCustodianLocalService.getReportUploadLogPFMCustodian(classPK);
		NpsPfmCustodianReportUploadLogAssetRender assetRender = new NpsPfmCustodianReportUploadLogAssetRender(reportUploadLog);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogPFMCustodian";
	}

	@Override
	public String getClassName() {
		return ReportUploadLogPFMCustodian.class.getName();
	}

	@Reference
	ReportUploadLogPFMCustodianLocalService reportUploadLogPFMCustodianLocalService;

}
