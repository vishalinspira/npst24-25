package com.monthly.compcertificate.asset;

import com.daily.average.service.model.MnCompCertificate;
import com.daily.average.service.service.MnCompCertificateLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.monthly.compcertificate.constants.MonthlyCompCertificatePortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + MonthlyCompCertificatePortletKeys.MONTHLYCOMPCERTIFICATE},
        service = AssetRendererFactory.class
		)
public class MonthlyCompCertificateAssetRenderFactory extends BaseAssetRendererFactory<MnCompCertificate>{

	public MonthlyCompCertificateAssetRenderFactory() {
		setClassName(MnCompCertificate.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(MonthlyCompCertificatePortletKeys.MONTHLYCOMPCERTIFICATE);
        setSearchable(true);
        setSelectable(true);
	}
	
	@Override
	public AssetRenderer<MnCompCertificate> getAssetRenderer(long classPK, int type) throws PortalException {
		MnCompCertificate mnCompCertificate = mnCompCertificateLocalService.getMnCompCertificate(classPK);
		MonthlyCompCertificateAssetRender assetRender = new MonthlyCompCertificateAssetRender(mnCompCertificate);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "MnCompCertificate";
	}
	
	@Override
    public String getClassName() {
		return MnCompCertificate.class.getName();
	}

	@Reference
	MnCompCertificateLocalService mnCompCertificateLocalService;
}
