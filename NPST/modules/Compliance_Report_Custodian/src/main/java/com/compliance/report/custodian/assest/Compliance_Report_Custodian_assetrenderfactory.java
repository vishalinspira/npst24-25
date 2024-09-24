package com.compliance.report.custodian.assest;

import com.compliance.report.custodian.constants.compliance_report_custodianPortletKeys;
import com.compliance.report.custodian.assest.Compliance_Report_Custodian_assestrender;
import com.daily.average.service.model.CustodianCompForm;
import com.daily.average.service.service.CustodianCompFormLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" +compliance_report_custodianPortletKeys.COMPLIANCE_REPORT_CUSTODIAN},
        service = AssetRendererFactory.class
		)

public class Compliance_Report_Custodian_assetrenderfactory extends BaseAssetRendererFactory<CustodianCompForm>{

	public Compliance_Report_Custodian_assetrenderfactory() {
		setClassName(CustodianCompForm.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(compliance_report_custodianPortletKeys.COMPLIANCE_REPORT_CUSTODIAN);
        setSearchable(true);
        setSelectable(true);
	}
	
	@Override
	public AssetRenderer<CustodianCompForm> getAssetRenderer(long classPK, int type) throws PortalException {
		CustodianCompForm custodianCompForm = custodianCompFormLocalService.getCustodianCompForm(classPK);
		Compliance_Report_Custodian_assestrender assetRender = new Compliance_Report_Custodian_assestrender(custodianCompForm);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "CustodianCompForm";
	}
	
	@Override
    public String getClassName() {
		return CustodianCompForm.class.getName();
	}

	@Reference
	CustodianCompFormLocalService custodianCompFormLocalService;
}

