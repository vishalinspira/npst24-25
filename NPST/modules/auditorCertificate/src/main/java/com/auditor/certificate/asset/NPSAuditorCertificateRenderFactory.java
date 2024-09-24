package com.auditor.certificate.asset;

import com.auditor.certificate.constants.auditorCertificatePortletKeys;
import com.daily.average.service.model.AuditorCertificate;
import com.daily.average.service.service.AuditorCertificateLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + auditorCertificatePortletKeys.AUDITORCERTIFICATE},
        service = AssetRendererFactory.class
)
public class NPSAuditorCertificateRenderFactory extends BaseAssetRendererFactory<AuditorCertificate>{
	
	public NPSAuditorCertificateRenderFactory() {
        setClassName(AuditorCertificate.class.getName());
        setCategorizable(true);
        setLinkable(true);
        setPortletId(auditorCertificatePortletKeys.AUDITORCERTIFICATE);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<AuditorCertificate> getAssetRenderer(long classPK, int type) throws PortalException {
		AuditorCertificate auditorCertificate = auditorCertificateLocalService.getAuditorCertificate(classPK);
		NPSAuditorCertificateAssetRender assetRender = new NPSAuditorCertificateAssetRender(auditorCertificate);
        assetRender.setAssetRendererType(type);
        return assetRender;
	}

	@Override
	public String getType() {
		return "auditorCertificate";
	}
	
	@Override
	public String getClassName() {
		return AuditorCertificate.class.getName();
	}

	@Reference
	AuditorCertificateLocalService auditorCertificateLocalService;
}
