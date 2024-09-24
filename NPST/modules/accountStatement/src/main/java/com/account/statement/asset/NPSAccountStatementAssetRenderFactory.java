package com.account.statement.asset;

import com.account.statement.constants.accountStatementPortletKeys;
import com.daily.average.service.model.AccountStatement;
import com.daily.average.service.service.AccountStatementLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(
        immediate = true,
        property = {"javax.portlet.name=" + accountStatementPortletKeys.ACCOUNTSTATEMENT},
        service = AssetRendererFactory.class
)

public class NPSAccountStatementAssetRenderFactory extends BaseAssetRendererFactory<AccountStatement> {
	
	public NPSAccountStatementAssetRenderFactory() {
        setClassName(AccountStatement.class.getName());
        setCategorizable(true);
        setLinkable(true);
        setPortletId(accountStatementPortletKeys.ACCOUNTSTATEMENT);
        setSearchable(true);
        setSelectable(true);
    }
	
	@Override
    public AssetRenderer<AccountStatement> getAssetRenderer(long classPK, int type) throws PortalException {
		AccountStatement accountStatement = accountStatementLocalService.getAccountStatement(classPK);
		NPSAccountStatementAssetRenderer assetRenders = new NPSAccountStatementAssetRenderer(accountStatement);
        assetRenders.setAssetRendererType(type);
        return assetRenders;
    }
	

	@Override
	public String getType() {
		return "accountStatement";
	}
	
	@Override
    public String getClassName() {
        return AccountStatement.class.getName();
    }

    @Reference
    AccountStatementLocalService accountStatementLocalService;
	
}
