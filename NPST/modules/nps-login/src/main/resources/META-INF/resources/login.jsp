<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/js/jquery-captcha.min.js"></script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsencrypt/3.2.1/jsencrypt.min.js"></script>


<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		String signedInAs = HtmlUtil.escape(user.getFullName());

		if (themeDisplay.isShowMyAccountIcon() && (themeDisplay.getURLMyAccount() != null)) {
			String myAccountURL = String.valueOf(themeDisplay.getURLMyAccount());

			signedInAs = "<a class=\"signed-in\" href=\"" + HtmlUtil.escape(myAccountURL) + "\">" + signedInAs + "</a>";
		}
		%>

		<liferay-ui:message arguments="<%= signedInAs %>" key="you-are-signed-in-as-x" translateArguments="<%= false %>" />
	</c:when>
	<c:otherwise>

		<%
		String formName = "loginForm";

		if (windowState.equals(LiferayWindowState.EXCLUSIVE)) {
			formName += "Modal";
		}

		String redirect = ParamUtil.getString(request, "redirect");

		String login = (String)SessionErrors.get(renderRequest, "login");

		if (Validator.isNull(login)) {
			login = LoginUtil.getLogin(request, "login", company);
		}

		String password = StringPool.BLANK;
		boolean rememberMe = ParamUtil.getBoolean(request, "rememberMe");

		if (Validator.isNull(authType)) {
			authType = company.getAuthType();
		}
		%>
		
		
		<div class="nps-login-wrap">
        <div class="container-fluid position-relative"> 
            <div class="row">
                <div class="col-lg-6 col-md-12 col-sm-12 col-12">
                    <div class="nps-login-img text-center mb-lg-0 mb-md-4 mb-4">
                        <img src="<%=request.getContextPath()%>/images/logo.png?v=1.0" alt="logo-img">
                        <div class="nps-main-img">
                            <img src="<%=request.getContextPath()%>/images/login-img.png?v=1.0" alt="login-img" class="w-100">
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-12 col-sm-12 col-12">
                    <div class="nps-login-form">
                        <h1 class="text-uppercase mb-2">MEMBER LOGIN</h1>
                        
                        
                        <portlet:actionURL name="/login/login" secure="<%= PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS || request.isSecure() %>" var="loginURL">
							<portlet:param name="mvcRenderCommandName" value="/login/login" />
						</portlet:actionURL>
			
						<aui:form action="<%= loginURL %>" autocomplete='<%= PropsValues.COMPANY_SECURITY_LOGIN_FORM_AUTOCOMPLETE ? "on" : "off" %>' cssClass="sign-in-form" method="post" name="<%= formName %>" onSubmit="event.preventDefault();" validateOnBlur="<%= false %>">
							<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
							<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
							<aui:input name="doActionAfterLogin" type="hidden" value="<%= portletName.equals(PortletKeys.FAST_LOGIN) ? true : false %>" />
			
							<div class="inline-alert-container lfr-alert-container"></div>
			
							<liferay-util:dynamic-include key="com.liferay.login.web#/login.jsp#alertPre" />
			
							<c:choose>
								<c:when test='<%= SessionMessages.contains(request, "forgotPasswordSent") %>'>
									<div class="alert alert-success">
										<liferay-ui:message key="your-request-completed-successfully" />
									</div>
								</c:when>
								<c:when test='<%= SessionMessages.contains(request, "userAdded") %>'>
			
									<%
									String userEmailAddress = (String)SessionMessages.get(request, "userAdded");
									%>
			
									<div class="alert alert-success">
										<liferay-ui:message key="thank-you-for-creating-an-account" />
			
										<c:if test="<%= company.isStrangersVerify() %>">
											<liferay-ui:message arguments="<%= HtmlUtil.escape(userEmailAddress) %>" key="your-email-verification-code-was-sent-to-x" translateArguments="<%= false %>" />
										</c:if>
			
										<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED) %>">
											<c:choose>
												<c:when test="<%= PropsValues.LOGIN_CREATE_ACCOUNT_ALLOW_CUSTOM_PASSWORD %>">
													<liferay-ui:message key="use-your-password-to-login" />
												</c:when>
												<c:otherwise>
													<liferay-ui:message arguments="<%= HtmlUtil.escape(userEmailAddress) %>" key="you-can-set-your-password-following-instructions-sent-to-x" translateArguments="<%= false %>" />
												</c:otherwise>
											</c:choose>
										</c:if>
									</div>
								</c:when>
								<c:when test='<%= SessionMessages.contains(request, "userPending") %>'>
			
									<%
									String userEmailAddress = (String)SessionMessages.get(request, "userPending");
									%>
			
									<div class="alert alert-success">
										<liferay-ui:message arguments="<%= HtmlUtil.escape(userEmailAddress) %>" key="thank-you-for-creating-an-account.-you-will-be-notified-via-email-at-x-when-your-account-has-been-approved" translateArguments="<%= false %>" />
									</div>
								</c:when>
							</c:choose>
			
							<liferay-ui:error exception="<%= AuthException.class %>" message="authentication-failed" />
							<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-log-in-because-the-maximum-number-of-users-has-been-reached" />
							<liferay-ui:error exception="<%= CookieNotSupportedException.class %>" message="authentication-failed-please-enable-browser-cookies" />
							<liferay-ui:error exception="<%= NoSuchUserException.class %>" message="authentication-failed" />
							<liferay-ui:error exception="<%= PasswordExpiredException.class %>" message="your-password-has-expired" />
							<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeNull.class %>" message="please-enter-an-email-address" />
							<liferay-ui:error exception="<%= UserLockoutException.LDAPLockout.class %>" message="this-account-is-locked" />
			
							<liferay-ui:error exception="<%= UserLockoutException.PasswordPolicyLockout.class %>">
			
								<%
								UserLockoutException.PasswordPolicyLockout ule = (UserLockoutException.PasswordPolicyLockout)errorException;
								%>
			
								<c:choose>
									<c:when test="<%= ule.passwordPolicy.isRequireUnlock() %>">
										<liferay-ui:message key="this-account-is-locked" />
									</c:when>
									<c:otherwise>
			
										<%
										Format dateFormat = FastDateFormatFactoryUtil.getDateTime(FastDateFormatConstants.SHORT, FastDateFormatConstants.LONG, locale, TimeZone.getTimeZone(ule.user.getTimeZoneId()));
										%>
			
										<liferay-ui:message arguments="<%= dateFormat.format(ule.user.getUnlockDate()) %>" key="this-account-is-locked-until-x" translateArguments="<%= false %>" />
									</c:otherwise>
								</c:choose>
							</liferay-ui:error>
			
							<liferay-ui:error exception="<%= UserPasswordException.class %>" message="authentication-failed" />
							<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeNull.class %>" message="the-screen-name-cannot-be-blank" />
			
							<liferay-util:dynamic-include key="com.liferay.login.web#/login.jsp#alertPost" />
			
							<aui:fieldset>
			
								<%
								String loginLabel = null;
			
								if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
									loginLabel = "email-address";
								}
								else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
									loginLabel = "screen-name";
								}
								else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
									loginLabel = "id";
								}
								%>
								
								 <div class="nps-input-box">
									 <div class="nps-input-icon">
										<aui:input autocomplete="off" autoFocus="<%= windowState.equals(LiferayWindowState.EXCLUSIVE) || windowState.equals(WindowState.MAXIMIZED) %>" cssClass="clearable" label="<%= loginLabel %>" name="login" showRequiredLabel="<%= false %>" type="text" placeholder="Email Address">									
											<aui:validator name="required" />
					
											<c:if test="<%= authType.equals(CompanyConstants.AUTH_TYPE_EA) %>">
												<aui:validator name="email" />
											</c:if>
										</aui:input>
										<span>
	                                        <svg width="21" height="19" viewBox="0 0 21 19" fill="none" xmlns="http://www.w3.org/2000/svg">
	                                            <path d="M21 3.01474C21 1.82577 20.055 0.852974 18.9 0.852974H2.1C0.945 0.852974 0 1.82577 0 3.01474V15.9853C0 17.1743 0.945 18.1471 2.1 18.1471H18.9C20.055 18.1471 21 17.1743 21 15.9853V3.01474ZM18.9 3.01474L10.5 8.41915L2.1 3.01474H18.9ZM18.9 15.9853H2.1V5.1765L10.5 10.5809L18.9 5.1765V15.9853Z" fill="white"></path>
	                                        </svg>                                            
	                                    </span>
                                    </div>
								</div>
								 
									
								<div class="nps-input-box">
									<div class="nps-input-icon"> 
										<aui:input autocomplete="off" name="password" showRequiredLabel="<%= false %>" type="password" value="<%= password %>" placeholder="Password">
											<aui:validator name="required" />
										</aui:input>
										<span>
	                                        <svg width="18" height="23" viewBox="0 0 18 23" fill="none" xmlns="http://www.w3.org/2000/svg">
	                                            <path d="M9 2.7C10.8608 2.7 12.375 4.1806 12.375 6H14.625C14.625 2.9673 12.1016 0.5 9 0.5C5.89838 0.5 3.375 2.9673 3.375 6V8.2H2.25C1.00912 8.2 0 9.1867 0 10.4V20.3C0 21.5133 1.00912 22.5 2.25 22.5H15.75C16.9909 22.5 18 21.5133 18 20.3V10.4C18 9.1867 16.9909 8.2 15.75 8.2H5.625V6C5.625 4.1806 7.13925 2.7 9 2.7ZM15.7523 20.3H10.125V17.7942C10.7944 17.4125 11.25 16.7107 11.25 15.9C11.25 14.6867 10.2409 13.7 9 13.7C7.75912 13.7 6.75 14.6867 6.75 15.9C6.75 16.7096 7.20562 17.4125 7.875 17.7942V20.3H2.25V10.4H15.75L15.7523 20.3Z" fill="white"/>
	                                        </svg>                                                                                     
	                                    </span>
	                                    <i class="toggle-password fa fa-fw fa-eye-slash"></i>
                                    </div>
								</div>
			
								<span id="<portlet:namespace />passwordCapsLockSpan" style="display: none;"><liferay-ui:message key="caps-lock-is-on" /></span>
			
								<ul class="d-flex align-items-center justify-content-between mt-2 nps-input-box nps-login-remember">
	                                <li>
	                                    <c:if test="<%= company.isAutoLogin() && !PropsValues.SESSION_DISABLED %>">
											<aui:input cssClass="checkbox" checked="<%= rememberMe %>" name="rememberMe" type="checkbox" />
										</c:if>
	                                </li> 
	                                
	                                <li class="forgot-psd-links">
	                                	<%@ include file="/navigation.jspf" %> 
	                                </li>
	                            </ul>
	                            <div class="lr-captcha">
	                             	<input type="hidden" name="salt" id="salt" >
	                            	<liferay-captcha:captcha />
	                            </div>
	                            
	                            <%-- <ul class="nps-captcha d-flex align-items-center justify-content-start nps-input-box captcha-box">
									<li class="nps-captcha-img text-center">
										<canvas id="canvas"></canvas>
									</li>
									<li class="d-flex align-items-center">
										<aui:input autocomplete="off" name="code" cssClass="pl-2" showRequiredLabel="<%= false %>" label="" placeholder="Captcha Code">
											<aui:validator name="required" />
												<aui:validator name="custom" errorMessage="text-verification-failed" >
												    function(val, fieldNode, ruleValue) {
												        if(val){
												            return checkCaptcha(val);
												        }else{
												            return false;
												        }
												    }
												</aui:validator>
										</aui:input>
										<span class="ps-2">
											<button type="button" class="refresh-captcha bg-transparent border-0 text-white" title="Refresh Captcha"><i class="fas fa-redo"></i></button>
										</span>
									</li>
								</ul>  --%>
							</aui:fieldset>
			
							<aui:button-row>
								<aui:button cssClass="btn-block" type="submit" value="sign-in" />
							</aui:button-row>
						</aui:form>
                    </div>
                </div>
            </div>
        </div>
    </div> 
    
<script>
	const captcha = new Captcha($('#canvas'),{ length: 4 });

	function checkCaptcha(captchaValue){
		const ans = captcha.valid(captchaValue);
		/* captcha.refresh(); */
		if(!ans)
			$(".pl-2").val("");
		return ans;
	}
	
	$(".refresh-captcha").click(function() {
	    captcha.refresh();
	});
	console.log('${publicKey}');
	</script>

	<!-- </script> -->

		<aui:script sandbox="<%= true %>">
		
			$(function(){
				$("#salt").attr("dt","aW1d!");
				$("#salt").attr("ds","0");
				$("#<portlet:namespace/>login").change(function(){
					let email = $("#<portlet:namespace/>login").val();
					let compid = themeDisplay.getCompanyId();
					$("button[type='submit']").attr("disabled",true);
					$.get("/o/pk/morning/"+compid+"/"+email, function(data, status){
						$("#salt").attr("dt",data);
						$("#salt").attr("ds","1");
						console.log(data);
						$("button[type='submit']").attr("disabled",false);
					});
				})
			});
			console.log('${publicKey}');
			var form = document.getElementById('<portlet:namespace /><%= formName %>');

			if (form) {
				form.addEventListener('submit', function (event) {
					<c:if test="<%= Validator.isNotNull(redirect) %>">
						var redirect = form.querySelector('#<portlet:namespace />redirect');

						if (redirect) {
							var redirectVal = redirect.getAttribute('value');

							redirect.setAttribute('value', redirectVal + window.location.hash);
						}
					</c:if>
					let qs = $("#<portlet:namespace />loginForm").attr("action").split('?')[1];
					let queryString = new URLSearchParams(qs);
					let pauth = queryString.get("p_auth");
					var password1 = $('#<portlet:namespace />password').val();

					if (password1) {
						var passwordVal = pauth+password1+$("#salt").attr("dt");
	                                let rsaEncrypt = new JSEncrypt();
	                                rsaEncrypt.setPublicKey('MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4EKqKRPmifbZuxC3655lHQvpBjV9e/zJwTLCA/JhfsLxrxv1bGtP1XB6YiqsGLnumdAqtQx2JyZ/QYLOb2KW4efLY+luWk6sPhcNPznvr4p+DWxNTu/FWBxjL3scW+G0uVTprBfI5CvS7jpR8S2cFVxHXSg09H/mrY8x3cyGKpQIDAQAB'); 
	                                var encrypted = rsaEncrypt.encrypt(passwordVal,true);
	                               $('#<portlet:namespace />password').val(encrypted);
					}

					submitForm(form);
				});

				var password = form.querySelector('#<portlet:namespace />password');

				if (password) {
										
					password.addEventListener('keypress', function (event) {
						Liferay.Util.showCapsLock(
							event,
							'<portlet:namespace />passwordCapsLockSpan'
						);
					});
				}
			}
		</aui:script>
	</c:otherwise>
</c:choose>

<style>
.lr-captcha .taglib-captcha {
	display: flex;
}

.lr-captcha .taglib-captcha .captcha {
    height: 50px;
    margin-right: 15px;
}

.lr-captcha .input-text-wrapper label {
	display: none;
}

.lr-captcha .form-control {
	margin-left: 15px;
    height: 50px;
}

.lfr-icon-item svg {
	margin-top: 15px;
    fill: #fff;
}
</style>