package nps.pfm.cra.reportuploadlog.asset;


import com.daily.average.service.model.ReportUploadLogPFMCRA;
import com.daily.average.service.service.ReportUploadLogPFMCRALocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import nps.pfm.cra.reportuploadlog.constants.NpsPfmCraReportuploadlogPortletKeys;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsPfmCraReportuploadlogPortletKeys.NPSPFMCRAREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class NpsPfmCraReportUploadLogAssetRenderFactory  extends BaseAssetRendererFactory<ReportUploadLogPFMCRA>{
	
	public NpsPfmCraReportUploadLogAssetRenderFactory(){
		setClassName(ReportUploadLogPFMCRA.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsPfmCraReportuploadlogPortletKeys.NPSPFMCRAREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogPFMCRA> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogPFMCRA reportUploadLogMaker = reportUploadLogPFMAMLocalService.getReportUploadLogPFMCRA(classPK);
		NpsPfmCraReportUploadLogAssetRender assetRender = new NpsPfmCraReportUploadLogAssetRender(reportUploadLogMaker);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogPFMCRA";
	}
	
	@Override
    public String getClassName() {
		return ReportUploadLogPFMCRA.class.getName();
	}

	@Reference
	ReportUploadLogPFMCRALocalService reportUploadLogPFMAMLocalService;
}
