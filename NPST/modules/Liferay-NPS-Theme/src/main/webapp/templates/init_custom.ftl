<#assign pageFriendlyURL=""/>
<#assign pageFriendlyURL = themeDisplay.getLayout().getFriendlyURL() />

<#if pageFriendlyURL = "/login">
	<style>
		.nps-dashboard-wrapper .nps-main-content .nps-body-main {
			width: 100% !important;
			padding-top: 0px !important;
		}
		
		.nps-dashboard-wrapper .nps-main-content .nps-body-main .nps-page-main {
			padding: 0px 15px !important;
			margin-bottom: 0 !important;
		}
		
		body {
			background: #fff !important;
		}		 
	
		footer, .nps-header, .deshbordSidebar {
			display: none !important;
		}
		
		.nps-page-main .col-md-12 {
			padding: 0px !important;
		}
		
		.nps-page-main .col-md-12 .autofit-float {
			margin: 0px !important;
		}
		
		.nps-breadcrumbs {
			display: none !important;
		}
	</style>	
</#if>