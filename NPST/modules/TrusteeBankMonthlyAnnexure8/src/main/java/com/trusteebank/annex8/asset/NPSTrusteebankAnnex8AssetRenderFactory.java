package com.trusteebank.annex8.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import TrusteeBankMonthlyAnnexure8.constants.TrusteeBankMonthlyAnnexure8PortletKeys;
import compliance.service.model.ClosingBal;
import compliance.service.service.ClosingBalLocalService;


@Component(
        immediate = true,
        property = {"javax.portlet.name=" + TrusteeBankMonthlyAnnexure8PortletKeys.TRUSTEEBANKMONTHLYANNEXURE8},
        service = AssetRendererFactory.class
)

public class NPSTrusteebankAnnex8AssetRenderFactory extends BaseAssetRendererFactory<ClosingBal> {
	
	public NPSTrusteebankAnnex8AssetRenderFactory() {
        setClassName(ClosingBal.class.getName());
        setCategorizable(true);
        setLinkable(true);
        setPortletId(TrusteeBankMonthlyAnnexure8PortletKeys.TRUSTEEBANKMONTHLYANNEXURE8);
        setSearchable(true);
        setSelectable(true);
    }
	
	@Override
    public AssetRenderer<ClosingBal> getAssetRenderer(long classPK, int type) throws PortalException {
		ClosingBal closingBal = closingBalLocalService.getClosingBal(classPK);
		NPSTrusteebankAnnex8AssetRenderer assetRenders = new NPSTrusteebankAnnex8AssetRenderer(closingBal);
		assetRenders.setAssetRendererType(type);
        return assetRenders;
    }
	

	@Override
	public String getType() {
		return "closingBal";
	}
	
	@Override
    public String getClassName() {
        return ClosingBal.class.getName();
    }

    @Reference
    ClosingBalLocalService closingBalLocalService;
}
