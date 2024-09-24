package nps.pfm.am.reportuploadlog.asset;

import com.daily.average.service.model.ReportUploadLogPFMAM;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.pfm.am.reportuploadlog.constants.NpsPfmAmReportuploadlogPortletKeys;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsPfmAmReportuploadlogPortletKeys.NPSPFMAMREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class NpsPfmAmReportUploadLogAssetRenderFactory  extends BaseAssetRendererFactory<ReportUploadLogPFMAM>{
	
	public NpsPfmAmReportUploadLogAssetRenderFactory(){
		setClassName(ReportUploadLogPFMAM.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsPfmAmReportuploadlogPortletKeys.NPSPFMAMREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogPFMAM> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogPFMAM reportUploadLogPFMAM = reportUploadLogPFMAMLocalService.getReportUploadLogPFMAM(classPK);
		NpsPfmAmReportUploadLogAssetRender assetRender = new NpsPfmAmReportUploadLogAssetRender(reportUploadLogPFMAM);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogPFMAM";
	}
	
	@Override
    public String getClassName() {
		return ReportUploadLogPFMAM.class.getName();
	}

	@Reference
	ReportUploadLogPFMAMLocalService reportUploadLogPFMAMLocalService;
}
