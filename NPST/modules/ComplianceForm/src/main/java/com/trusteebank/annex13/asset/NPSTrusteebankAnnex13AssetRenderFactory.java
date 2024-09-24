package com.trusteebank.annex13.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import ComplianceForm.constants.ComplianceFormPortletKeys;
import compliance.service.model.Compliance_Cert;
import compliance.service.service.Compliance_CertLocalService;


@Component(
        immediate = true,
        property = {"javax.portlet.name=" + ComplianceFormPortletKeys.COMPLIANCEFORM},
        service = AssetRendererFactory.class
)

public class NPSTrusteebankAnnex13AssetRenderFactory extends BaseAssetRendererFactory<Compliance_Cert> {
	
	public NPSTrusteebankAnnex13AssetRenderFactory() {
        setClassName(Compliance_Cert.class.getName());
        setCategorizable(true);
        setLinkable(true);
        setPortletId(ComplianceFormPortletKeys.COMPLIANCEFORM);
        setSearchable(true);
        setSelectable(true);
    }
	
	@Override
    public AssetRenderer<Compliance_Cert> getAssetRenderer(long classPK, int type) throws PortalException {
    	Compliance_Cert compliance_Cert = cLocalService.getCompliance_Cert(classPK);
    	NPSTrusteebankAnnex13AssetRenderer assetRenders  = new NPSTrusteebankAnnex13AssetRenderer(compliance_Cert);
		assetRenders.setAssetRendererType(type);
        return assetRenders;
    }
	

	@Override
	public String getType() {
		return "compliance_Cert";
	}
	
	@Override
    public String getClassName() {
        return Compliance_Cert.class.getName();
    }

    @Reference
    Compliance_CertLocalService cLocalService;
    
	
}
