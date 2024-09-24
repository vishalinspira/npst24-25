package com.trusteebank.annex9.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import TrusteeBankMonthlyAnnexure_9.constants.TrusteeBankMonthlyAnnexure_9PortletKeys;
import compliance.service.model.PerformanceRep;
import compliance.service.service.PerformanceRepLocalService;


@Component(
        immediate = true,
        property = {"javax.portlet.name=" + TrusteeBankMonthlyAnnexure_9PortletKeys.TRUSTEEBANKMONTHLYANNEXURE_9},
        service = AssetRendererFactory.class
)

public class NPSTrusteebankAnnex9AssetRenderFactory extends BaseAssetRendererFactory<PerformanceRep> {
	
	public NPSTrusteebankAnnex9AssetRenderFactory() {
        setClassName(PerformanceRep.class.getName());
        setCategorizable(true);
        setLinkable(true);
        setPortletId(TrusteeBankMonthlyAnnexure_9PortletKeys.TRUSTEEBANKMONTHLYANNEXURE_9);
        setSearchable(true);
        setSelectable(true);
    }
	
	@Override
    public AssetRenderer<PerformanceRep> getAssetRenderer(long classPK, int type) throws PortalException {
		PerformanceRep performanceRep = performanceRepLocalService.getPerformanceRep(classPK);
		NPSTrusteebankAnnex9AssetRenderer assetRenders = new NPSTrusteebankAnnex9AssetRenderer(performanceRep);
		assetRenders.setAssetRendererType(type);
        return assetRenders;
    }
	

	@Override
	public String getType() {
		return "accountStatement";
	}
	
	@Override
    public String getClassName() {
        return PerformanceRep.class.getName();
    }

    @Reference
    PerformanceRepLocalService performanceRepLocalService;
}
