package com.quarterly.stewardship.report.asset;

import com.daily.average.service.model.QtrStewardshipReport;
import com.daily.average.service.service.QtrStewardshipReportLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.quarterly.stewardship.report.constants.QuarterlyStewardshipReportPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {"javax.portlet.name=" + QuarterlyStewardshipReportPortletKeys.QUARTERLYSTEWARDSHIPREPORT},
        service = AssetRendererFactory.class
		)
public class QuarterlyStewardshipReportAssetRenderFactory extends BaseAssetRendererFactory<QtrStewardshipReport> {
	
	public QuarterlyStewardshipReportAssetRenderFactory() {
		setClassName(QtrStewardshipReport.class.getName());
		setCategorizable(true);
        setLinkable(true);
        setPortletId(QuarterlyStewardshipReportPortletKeys.QUARTERLYSTEWARDSHIPREPORT);
        setSearchable(true);
        setSelectable(true);
	}

	@Override
	public AssetRenderer<QtrStewardshipReport> getAssetRenderer(long classPK, int type) throws PortalException {
		QtrStewardshipReport stewardshipReport = qtrStewardshipReportLocalService.getQtrStewardshipReport(classPK);
		QuarterlyStewardshipReportAssetRender assetRender = new QuarterlyStewardshipReportAssetRender(stewardshipReport);
		assetRender.setAssetRendererType(type);
		return assetRender;
	}

	@Override
	public String getType() {
		return "QtrStewardshipReport";
	}
	
	@Override
    public String getClassName() {
		return QtrStewardshipReport.class.getName();
	}
	
	@Reference
	QtrStewardshipReportLocalService qtrStewardshipReportLocalService;

}
