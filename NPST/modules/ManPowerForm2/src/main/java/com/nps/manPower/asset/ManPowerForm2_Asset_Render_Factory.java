package com.nps.manPower.asset;

import com.daily.average.service.model.Manpowerform_ii;
import com.daily.average.service.service.Manpowerform_iiLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.nps.manPower.constants.ManPowerForm2PortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" +ManPowerForm2PortletKeys.MANPOWERFORM2},
        service = AssetRendererFactory.class
		)

public class ManPowerForm2_Asset_Render_Factory extends BaseAssetRendererFactory<Manpowerform_ii>{
	public ManPowerForm2_Asset_Render_Factory() {
		setClassName(Manpowerform_ii.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(ManPowerForm2PortletKeys.MANPOWERFORM2);
        setSearchable(true);
        setSelectable(true);
	}
	
	@Override
	public AssetRenderer<Manpowerform_ii> getAssetRenderer(long classPK, int type) throws PortalException {
		Manpowerform_ii manpowerform_ii = manpowerform_iiLocalService.getManpowerform_ii(classPK);
		ManPowerForm2_Asset_Render assetRender = new ManPowerForm2_Asset_Render(manpowerform_ii);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "Manpowerform_ii";
	}
	
	@Override
    public String getClassName() {
		return Manpowerform_ii.class.getName();
	}
	
	

	@Reference
	Manpowerform_iiLocalService manpowerform_iiLocalService;
}
