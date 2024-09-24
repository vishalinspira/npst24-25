package nps.griev.am.pfrda.reportuploadlog.asset;

import com.daily.average.service.model.ReportUploadLogGrievAMPFRDA;
import com.daily.average.service.service.ReportUploadLogGrievAMPFRDALocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.griev.am.pfrda.reportuploadlog.constants.NpsGrievAmPfrdaReportuploadlogPortletKeys;


@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsGrievAmPfrdaReportuploadlogPortletKeys.NPSGRIEVAMPFRDAREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class ReportUploadLogGrievAMPFRDAAssetRenderFactory  extends BaseAssetRendererFactory<ReportUploadLogGrievAMPFRDA>{
	public ReportUploadLogGrievAMPFRDAAssetRenderFactory() {
		setClassName(ReportUploadLogGrievAMPFRDA.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsGrievAmPfrdaReportuploadlogPortletKeys.NPSGRIEVAMPFRDAREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogGrievAMPFRDA> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogGrievAMPFRDA reportUploadLogCustAMPFRDA = reportUploadLogGrievAMPFRDA.getReportUploadLogGrievAMPFRDA(classPK);
		ReportUploadLogGrievAMPFRDAAssetRender assetRender = new ReportUploadLogGrievAMPFRDAAssetRender(reportUploadLogCustAMPFRDA);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogGrievAMPFRDA";
	}
	
	@Override
	public String getClassName() {
		return ReportUploadLogGrievAMPFRDA.class.getName();
	}
	
	@Reference
	ReportUploadLogGrievAMPFRDALocalService reportUploadLogGrievAMPFRDA;

}
