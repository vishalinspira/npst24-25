<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${html_title}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" /> 
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/animate.min.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/all.min.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/toastr.min.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/fonts.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/fullcalendar.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/owl.carousel.min.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/resource.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/dataTables.bootstrap5.min.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/datatables.min.css"/>
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/style.css"/>	
	<@liferay_util["include"] page=top_head_include />
</head>

<body class="${css_class}">

<@liferay_ui["quick-access"] contentId="#main-content" />

<@liferay_util["include"] page=body_top_include />

<#if is_signed_in && permissionChecker.isOmniadmin()>
	<@liferay.control_menu />
	
	<style>
		#portlet_com_liferay_my_account_web_portlet_MyAccountPortlet .container-form-lg .col-lg-3 .nav-item:nth-child(2), .nav-item:nth-child(3), .nav-item:nth-child(4), .nav-item:nth-child(6) {
			display: block !important;
		}
		
		#portlet_com_liferay_my_account_web_portlet_MyAccountPortlet .page-header .navbar {
			display: block !important;
		}
	</style>
</#if>

<div class="nps-dashboard-wrapper" id="wrapper">	 
	<#include "${full_templates_path}/header.ftl" /> 	 

	<div class="nps-main-content">
		<#if has_navigation && is_setup_complete>
			<#include "${full_templates_path}/navigation.ftl" />
		</#if> 
	

		<div class="nps-body-main">		
			<section id="content" class="nps-page-main">		
			<div class="nps-breadcrumbs d-none">
				<@liferay.breadcrumbs />
			</div>	
				<#if selectable>
					<@liferay_util["include"] page=content_include />
				<#else>
					${portletDisplay.recycle()}

					${portletDisplay.setTitle(the_title)}

					<@liferay_theme["wrap-portlet"] page="portlet.ftl">
						<@liferay_util["include"] page=content_include />
					</@>
				</#if>
			</section>

			
			<footer id="footer" role="contentinfo">		 
				<p>&#169; 2023 - National Pension System Trust. All rights reserved.</p>                 
			</footer>  
		</div>
	</div> 
</div>


<script src="${javascript_folder}/jquery.dataTables.min.js"></script>
<script src="${javascript_folder}/bootstrap.min.js"></script>
<script src="${javascript_folder}/jquery-ui.js"></script>
<script src="${javascript_folder}/moment.min.js"></script>
<script src="${javascript_folder}/datetime-moment.js"></script>
<script src="${javascript_folder}/owl.carousel.min.js"></script>
<script src="${javascript_folder}/toastr.min.js"></script>
<script src="${javascript_folder}/jquery.validate.min.js"></script>
<script src="${javascript_folder}/custom.js"></script>

<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />

</body>

</html>