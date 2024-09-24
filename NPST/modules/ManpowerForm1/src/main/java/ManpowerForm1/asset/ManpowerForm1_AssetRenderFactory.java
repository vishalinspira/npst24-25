package ManpowerForm1.asset;

import com.daily.average.service.model.Manpowerform_i;
import com.daily.average.service.model.Manpowerform_i;
import com.daily.average.service.service.Manpowerform_iLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;


import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import ManpowerForm1.constants.ManpowerForm1PortletKeys;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" +ManpowerForm1PortletKeys.MANPOWERFORM1},
        service = AssetRendererFactory.class
		)
public class ManpowerForm1_AssetRenderFactory extends BaseAssetRendererFactory<Manpowerform_i>{
	public ManpowerForm1_AssetRenderFactory() {
		setClassName(Manpowerform_i.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(ManpowerForm1PortletKeys.MANPOWERFORM1);
        setSearchable(true);
        setSelectable(true);
	}
	
	@Override
	public AssetRenderer<Manpowerform_i> getAssetRenderer(long classPK, int type) throws PortalException {
		Manpowerform_i manpowerform_i = manpowerform_iLocalService.getManpowerform_i(classPK);
		ManpowerForm1_AssetRender assetRender = new ManpowerForm1_AssetRender(manpowerform_i);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "Manpowerform_i";
	}
	
	@Override
    public String getClassName() {
		return Manpowerform_i.class.getName();
	}
	
	

	@Reference
	Manpowerform_iLocalService manpowerform_iLocalService;
}
