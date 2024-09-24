package com.detailedauditreportform.asset;

import com.daily.average.service.model.DAR;
import com.daily.average.service.service.DARLocalService;
import com.detailedauditreportform.constants.DetailedAuditReportFormPortletKeys;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + DetailedAuditReportFormPortletKeys.DETAILEDAUDITREPORTFORM},
        service = AssetRendererFactory.class
		)
public class DARAssetRenderFactory extends BaseAssetRendererFactory<DAR>{

	public DARAssetRenderFactory() {
		setClassName(DAR.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(DetailedAuditReportFormPortletKeys.DETAILEDAUDITREPORTFORM);
        setSearchable(true);
        setSelectable(true);
	}
	
	@Override
	public AssetRenderer<DAR> getAssetRenderer(long classPK, int type) throws PortalException {
		DAR dar = darLocalService.getDAR(classPK);
		DARAssetRender assetRender = new DARAssetRender(dar);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "DAR";
	}
	
	@Override
    public String getClassName() {
		return DAR.class.getName();
	}

	@Reference
	DARLocalService darLocalService;
}
