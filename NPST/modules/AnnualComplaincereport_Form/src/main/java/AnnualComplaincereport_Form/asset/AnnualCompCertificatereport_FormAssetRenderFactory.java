package AnnualComplaincereport_Form.asset;

import com.daily.average.service.model.AnnualCompCertificate;
import com.daily.average.service.service.AnnualCompCertificateLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import AnnualComplaincereport_Form.constants.AnnualComplaincereport_FormPortletKeys;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + AnnualComplaincereport_FormPortletKeys.ANNUALCOMPLAINCEREPORT_FORM,},
        service = AssetRendererFactory.class
		)

public class AnnualCompCertificatereport_FormAssetRenderFactory extends BaseAssetRendererFactory<AnnualCompCertificate>{
	
	public AnnualCompCertificatereport_FormAssetRenderFactory() {
		setClassName(AnnualCompCertificate.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(AnnualComplaincereport_FormPortletKeys.ANNUALCOMPLAINCEREPORT_FORM);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<AnnualCompCertificate> getAssetRenderer(long classPK, int type) throws PortalException {
		AnnualCompCertificate mnCompCertificate = annualCompCertificateLocalService.getAnnualCompCertificate(classPK);
		AnnualComplaincereport_FormAssetRender assetRender = new AnnualComplaincereport_FormAssetRender(mnCompCertificate);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "AnnualCompCertificate";
	}
    
	@Override
    public String getClassName() {
		return AnnualCompCertificate.class.getName();
	}
	
	@Reference
	AnnualCompCertificateLocalService annualCompCertificateLocalService;
}
