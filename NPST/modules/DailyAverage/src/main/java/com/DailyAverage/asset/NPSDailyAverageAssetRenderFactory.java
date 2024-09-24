package com.DailyAverage.asset;

import com.DailyAverage.constants.DailyAveragePortletKeys;
import com.daily.average.service.model.DailyAverage;
import com.daily.average.service.service.DailyAverageLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(
        immediate = true,
        property = {"javax.portlet.name=" + DailyAveragePortletKeys.DAILYAVERAGE},
        service = AssetRendererFactory.class
)

public class NPSDailyAverageAssetRenderFactory extends BaseAssetRendererFactory<DailyAverage> {
	
	public NPSDailyAverageAssetRenderFactory() {
        setClassName(DailyAverage.class.getName());
        setCategorizable(true);
        setLinkable(true);
        setPortletId(DailyAveragePortletKeys.DAILYAVERAGE);
        setSearchable(true);
        setSelectable(true);
    }
	
	@Override
    public AssetRenderer<DailyAverage> getAssetRenderer(long classPK, int type) throws PortalException {
		DailyAverage dailyAverage = dailyAverageLocalService.getDailyAverage(classPK);
		NPSDailyAverageAssetRenderer assetRenders = new NPSDailyAverageAssetRenderer(dailyAverage);
		assetRenders.setAssetRendererType(type);
        return assetRenders;
    }
	

	@Override
	public String getType() {
		return "dailyAverage";
	}
	
	@Override
    public String getClassName() {
        return DailyAverage.class.getName();
    }

    @Reference
    DailyAverageLocalService dailyAverageLocalService;
    
}
