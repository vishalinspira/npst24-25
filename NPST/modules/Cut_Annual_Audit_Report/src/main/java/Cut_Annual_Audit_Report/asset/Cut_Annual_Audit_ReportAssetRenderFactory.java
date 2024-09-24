package Cut_Annual_Audit_Report.asset;

import com.daily.average.service.model.CustAnnualAuditReport;
import com.daily.average.service.service.CustAnnualAuditReportLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import Cut_Annual_Audit_Report.constants.Cut_Annual_Audit_ReportPortletKeys;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + Cut_Annual_Audit_ReportPortletKeys.CUT_ANNUAL_AUDIT_REPORT,},
        service = AssetRendererFactory.class
		)

public class Cut_Annual_Audit_ReportAssetRenderFactory extends BaseAssetRendererFactory<CustAnnualAuditReport>{
  
	public Cut_Annual_Audit_ReportAssetRenderFactory() {
		setClassName(CustAnnualAuditReport.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(Cut_Annual_Audit_ReportPortletKeys.CUT_ANNUAL_AUDIT_REPORT);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<CustAnnualAuditReport> getAssetRenderer(long classPK, int type) throws PortalException {
		CustAnnualAuditReport custannualaudit = custAnnualAuditReportLocalService.getCustAnnualAuditReport(classPK);
		Cut_Annual_Audit_ReportAssetRender assetRender = new Cut_Annual_Audit_ReportAssetRender(custannualaudit);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "CustAnnualAuditReport";
	}
    
	@Override
    public String getClassName() {
		return CustAnnualAuditReport.class.getName();
	}
	
	@Reference
	CustAnnualAuditReportLocalService custAnnualAuditReportLocalService;
}
