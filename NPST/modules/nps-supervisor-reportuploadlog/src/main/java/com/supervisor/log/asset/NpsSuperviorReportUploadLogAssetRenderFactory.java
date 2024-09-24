package com.supervisor.log.asset;

import com.daily.average.service.model.ReportUploadLogSupervisor;
import com.daily.average.service.service.ReportUploadLogSupervisorLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.supervisor.log.constants.NpsSupervisorReportuploadlogPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpsSupervisorReportuploadlogPortletKeys.NPSSUPERVISORREPORTUPLOADLOG},
        service = AssetRendererFactory.class
)
public class NpsSuperviorReportUploadLogAssetRenderFactory extends BaseAssetRendererFactory<ReportUploadLogSupervisor>{
	
	public NpsSuperviorReportUploadLogAssetRenderFactory() {
		setClassName(ReportUploadLogSupervisor.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpsSupervisorReportuploadlogPortletKeys.NPSSUPERVISORREPORTUPLOADLOG);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<ReportUploadLogSupervisor> getAssetRenderer(long classPK, int type) throws PortalException {
		ReportUploadLogSupervisor reportUploadSupervisorLog = reportUploadLogSupervisorLocalService.getReportUploadLogSupervisor(classPK);
		NpsSuperviorReportUploadLogAssetRender assetRender = new NpsSuperviorReportUploadLogAssetRender(reportUploadSupervisorLog);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "ReportUploadLogSupervisor";
	}
	
	@Override
	public String getClassName() {
		return ReportUploadLogSupervisor.class.getName();
	}
	
	@Reference
	ReportUploadLogSupervisorLocalService reportUploadLogSupervisorLocalService;

}
