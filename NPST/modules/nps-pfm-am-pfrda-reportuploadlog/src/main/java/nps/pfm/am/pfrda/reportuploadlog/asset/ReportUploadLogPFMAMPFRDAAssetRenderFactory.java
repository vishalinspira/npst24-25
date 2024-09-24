package nps.pfm.am.pfrda.reportuploadlog.asset;

import com.daily.average.service.model.ReportUploadLogPFMAMPFRDA;
import com.daily.average.service.service.ReportUploadLogPFMAMPFRDALocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.pfm.am.pfrda.reportuploadlog.constants.NpsPfmAmPfrdaReportuploadlogPortletKeys;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsPfmAmPfrdaReportuploadlogPortletKeys.NPSPFMAMPFRDAREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class ReportUploadLogPFMAMPFRDAAssetRenderFactory extends BaseAssetRendererFactory<ReportUploadLogPFMAMPFRDA>{
	public ReportUploadLogPFMAMPFRDAAssetRenderFactory() {
		setClassName(ReportUploadLogPFMAMPFRDA.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsPfmAmPfrdaReportuploadlogPortletKeys.NPSPFMAMPFRDAREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogPFMAMPFRDA> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogPFMAMPFRDA reportUploadLogPFMAMPFRDA = reportUploadLogPFMAMPFRDALocalService.getReportUploadLogPFMAMPFRDA(classPK);
		ReportUploadLogPFMAMPFRDAAssetRender assetRender = new ReportUploadLogPFMAMPFRDAAssetRender(reportUploadLogPFMAMPFRDA);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogPFMAMPFRDA";
	}
	
	@Override
	public String getClassName() {
		return ReportUploadLogPFMAMPFRDA.class.getName();
	}
	
	@Reference
	ReportUploadLogPFMAMPFRDALocalService reportUploadLogPFMAMPFRDALocalService;
}
