package com.trusteebank.annex11.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import TrusteeBankQuarterlyAnnexure_11.constants.TrusteeBankQuarterlyAnnexure_11PortletKeys;
import compliance.service.model.QuarterlyAuditor;
import compliance.service.service.QuarterlyAuditorLocalService;


@Component(
        immediate = true,
        property = {"javax.portlet.name=" + TrusteeBankQuarterlyAnnexure_11PortletKeys.TRUSTEEBANKQUARTERLYANNEXURE_11,},
        service = AssetRendererFactory.class
)

public class NPSQuarterlyAuditorAssetRenderFactory extends BaseAssetRendererFactory<QuarterlyAuditor>{

	public NPSQuarterlyAuditorAssetRenderFactory() {
        setClassName(QuarterlyAuditor.class.getName());
        setCategorizable(true);
        setLinkable(true);
        setPortletId(TrusteeBankQuarterlyAnnexure_11PortletKeys.TRUSTEEBANKQUARTERLYANNEXURE_11);
        setSearchable(true);
        setSelectable(true);
	}
	
	@Override
	public AssetRenderer<QuarterlyAuditor> getAssetRenderer(long classPK, int type) throws PortalException {
		QuarterlyAuditor quarterlyAuditor = quarterlyAuditorLocalService.getQuarterlyAuditor(classPK);
		NPSQuarterlyAuditorAssetRenderer assetRender = new NPSQuarterlyAuditorAssetRenderer(quarterlyAuditor);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "quarterlyAuditor";
	}
	
	@Override
	public String getClassName() {
		return QuarterlyAuditor.class.getName();
	}
	
	@Reference
	QuarterlyAuditorLocalService quarterlyAuditorLocalService;

}
