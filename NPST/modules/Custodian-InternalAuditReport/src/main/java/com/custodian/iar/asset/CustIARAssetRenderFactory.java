package com.custodian.iar.asset;

import com.custodian.iar.constants.CustodianInternalAuditReportPortletKeys;
import com.daily.average.service.model.CustIAR;
import com.daily.average.service.service.CustIARLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + CustodianInternalAuditReportPortletKeys.CUSTODIANINTERNALAUDITREPORT},
        service = AssetRendererFactory.class
		)
public class CustIARAssetRenderFactory extends BaseAssetRendererFactory<CustIAR> {
	
	public CustIARAssetRenderFactory() {
		setClassName(CustIAR.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(CustodianInternalAuditReportPortletKeys.CUSTODIANINTERNALAUDITREPORT);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<CustIAR> getAssetRenderer(long classPK, int type) throws PortalException {
		CustIAR stewardshipReport = custIARLocalService.getCustIAR(classPK);
		CustIARAssetRender assetRender = new CustIARAssetRender(stewardshipReport);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "CustIAR";
	}
	
	@Override
    public String getClassName() {
		return CustIAR.class.getName();
	}
	
	@Reference
	CustIARLocalService custIARLocalService;

}
