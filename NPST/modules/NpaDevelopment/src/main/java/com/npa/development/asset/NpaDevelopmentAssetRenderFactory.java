package com.npa.development.asset;

import com.daily.average.service.model.MnNpaDevelopment;
import com.daily.average.service.service.MnNpaDevelopmentLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.npa.development.constants.NpaDevelopmentPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + NpaDevelopmentPortletKeys.NPADEVELOPMENT},
        service = AssetRendererFactory.class
		)
public class NpaDevelopmentAssetRenderFactory extends BaseAssetRendererFactory<MnNpaDevelopment>  {
	
	public NpaDevelopmentAssetRenderFactory() {
		setClassName(MnNpaDevelopment.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(NpaDevelopmentPortletKeys.NPADEVELOPMENT);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<MnNpaDevelopment> getAssetRenderer(long classPK, int type) throws PortalException {
		MnNpaDevelopment npaDevelopment = mnNpaDevelopmentLocalService.getMnNpaDevelopment(classPK);
		NpaDevelopmentAssetRender assetRender = new NpaDevelopmentAssetRender(npaDevelopment);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "MnNpaDevelopment";
	}
	
	@Override
    public String getClassName() {
		return MnNpaDevelopment.class.getName();
	}
	
	@Reference
	MnNpaDevelopmentLocalService mnNpaDevelopmentLocalService;

}
