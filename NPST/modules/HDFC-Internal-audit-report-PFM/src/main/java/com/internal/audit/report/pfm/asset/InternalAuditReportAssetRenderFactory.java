package com.internal.audit.report.pfm.asset;

import com.daily.average.service.model.HDFCInternal_Audit_Report;
import com.daily.average.service.service.HDFCInternal_Audit_ReportLocalService;
import com.internal.audit.report.pfm.constants.HDFCInternalAuditReportPFMPortletKeys;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" +  HDFCInternalAuditReportPFMPortletKeys.HDFCINTERNALAUDITREPORTPFM},
        service = AssetRendererFactory.class
		)
public class InternalAuditReportAssetRenderFactory extends BaseAssetRendererFactory<HDFCInternal_Audit_Report>{
	
	public InternalAuditReportAssetRenderFactory() {
		setClassName(HDFCInternal_Audit_Report.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(HDFCInternalAuditReportPFMPortletKeys.HDFCINTERNALAUDITREPORTPFM);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<HDFCInternal_Audit_Report> getAssetRenderer(long classPK, int type) throws PortalException {
		HDFCInternal_Audit_Report auditreport = hdfcinternal_Audit_ReportLocalService.getHDFCInternal_Audit_Report(classPK);
		InternalAuditReportAssetRender assetRender = new InternalAuditReportAssetRender(auditreport);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "hdfc_internal_Audit_Report";
	}
	
	@Override
	public String getClassName() {
		return HDFCInternal_Audit_Report.class.getName();
	}
	
	@Reference
	HDFCInternal_Audit_ReportLocalService hdfcinternal_Audit_ReportLocalService;

}
