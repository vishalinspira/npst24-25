package nps.cust.am.pfrda.reportuploadlog.asset;

import com.daily.average.service.model.ReportUploadLogCustAMPFRDA;
import com.daily.average.service.service.ReportUploadLogCustAMPFRDALocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.cust.am.pfrda.reportuploadlog.constants.NpsCustAmPfrdaReportuploadlogPortletKeys;


@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsCustAmPfrdaReportuploadlogPortletKeys.NPSCUSTAMPFRDAREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class ReportUploadLogCustAMPFRDAAssetRenderFactory extends BaseAssetRendererFactory<ReportUploadLogCustAMPFRDA>{
	public ReportUploadLogCustAMPFRDAAssetRenderFactory() {
		setClassName(ReportUploadLogCustAMPFRDA.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsCustAmPfrdaReportuploadlogPortletKeys.NPSCUSTAMPFRDAREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogCustAMPFRDA> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogCustAMPFRDA reportUploadLogCustAMPFRDA = reportUploadLogCustAMPFRDALocalService.getReportUploadLogCustAMPFRDA(classPK);
		ReportUploadLogCustAMPFRDAAssetRender assetRender = new ReportUploadLogCustAMPFRDAAssetRender(reportUploadLogCustAMPFRDA);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogCustAMPFRDA";
	}
	
	@Override
	public String getClassName() {
		return ReportUploadLogCustAMPFRDA.class.getName();
	}
	
	@Reference
	ReportUploadLogCustAMPFRDALocalService reportUploadLogCustAMPFRDALocalService;
}
