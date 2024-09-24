package com.quarter.ended.asset;

import com.daily.average.service.model.QuarterEnded;
import com.daily.average.service.service.QuarterEndedLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.quarter.ended.constants.quarterEndedPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(
        immediate = true,
        property = {"javax.portlet.name=" + quarterEndedPortletKeys.QUARTERENDED},
        service = AssetRendererFactory.class
)

public class NPSQuarterEndedAssetRenderFactory extends BaseAssetRendererFactory<QuarterEnded> {
	
	public NPSQuarterEndedAssetRenderFactory() {
        setClassName(QuarterEnded.class.getName());
        setCategorizable(true);
        setLinkable(true);
        setPortletId(quarterEndedPortletKeys.QUARTERENDED);
        setSearchable(true);
        setSelectable(true);
    }
	
	@Override
    public AssetRenderer<QuarterEnded> getAssetRenderer(long classPK, int type) throws PortalException {
		QuarterEnded quarterEnded = quarterEndedLocalService.getQuarterEnded(classPK);
		NPSQuarterEndedAssetRenderer assetRenders = new NPSQuarterEndedAssetRenderer(quarterEnded);
		assetRenders.setAssetRendererType(type);
        return assetRenders ;
    }
	

	@Override
	public String getType() {
		return "quarterEnded";
	}
	
	@Override
    public String getClassName() {
        return QuarterEnded.class.getName();
    }

    @Reference
    QuarterEndedLocalService quarterEndedLocalService;
 }
