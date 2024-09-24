package com.nps.pfm.hy.comp.cert.asset;

import com.daily.average.service.model.PFM_hy_comp_cert;
import com.daily.average.service.service.PFM_hy_comp_certLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.nps.pfm.hy.comp.cert.constants.PFM_hy_comp_certPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + PFM_hy_comp_certPortletKeys.PFM_HY_COMP_CERT},
        service = AssetRendererFactory.class
		)

public class PFM_hy_comp_certAssetRenderFactory extends BaseAssetRendererFactory<PFM_hy_comp_cert>{
	
	
	public PFM_hy_comp_certAssetRenderFactory() {
		setClassName(PFM_hy_comp_cert.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(PFM_hy_comp_certPortletKeys.PFM_HY_COMP_CERT);
        setSearchable(true);
        setSelectable(true);
	}
	
	@Override
	public AssetRenderer<PFM_hy_comp_cert> getAssetRenderer(long classPK, int type) throws PortalException {
		PFM_hy_comp_cert pfm_hy_comp_cert = pfm_hy_comp_certLocalService.getPFM_hy_comp_cert(classPK);
		PFM_hy_comp_certAssetRender assetRender = new PFM_hy_comp_certAssetRender(pfm_hy_comp_cert);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}
	
	@Override
	public String getType() {
		return "PFM_hy_comp_cert";
	}
	
	@Override
    public String getClassName() {
		return PFM_hy_comp_cert.class.getName();
	}
	
	@Reference
	PFM_hy_comp_certLocalService pfm_hy_comp_certLocalService;

}
