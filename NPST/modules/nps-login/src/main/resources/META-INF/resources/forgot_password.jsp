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

<%
User user2 = (User)request.getAttribute(WebKeys.FORGOT_PASSWORD_REMINDER_USER);

if (Validator.isNull(authType)) {
	authType = company.getAuthType();
}

String login = (String)portletSession.getAttribute(WebKeys.FORGOT_PASSWORD_REMINDER_USER_EMAIL_ADDRESS);

Integer reminderAttempts = (Integer)portletSession.getAttribute(WebKeys.FORGOT_PASSWORD_REMINDER_ATTEMPTS);

if (reminderAttempts == null) {
	reminderAttempts = 0;
}

renderResponse.setTitle(LanguageUtil.get(request, "forgot-password"));
%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/js/jquery-captcha.min.js"></script>


<portlet:actionURL name="/login/forgot_password" var="forgotPasswordURL">
	<portlet:param name="mvcRenderCommandName" value="/login/forgot_password" />
</portlet:actionURL>
 
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
                        <h1 class="text-uppercase mb-2">FORGOT PASSWORD</h1> 
							<div class="login-container">
								<aui:form action="<%= forgotPasswordURL %>" method="post" name="fm">
									<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
							
									<liferay-ui:error exception="<%= CaptchaConfigurationException.class %>" message="a-captcha-error-occurred-please-contact-an-administrator" />
									<liferay-ui:error exception="<%= CaptchaException.class %>" message="captcha-verification-failed" />
									<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
									<liferay-ui:error exception="<%= NoSuchUserException.class %>" message='<%= "the-" + TextFormatter.format(HtmlUtil.escape(authType), TextFormatter.K) + "-you-requested-is-not-registered-in-our-database" %>' />
									<liferay-ui:error exception="<%= RequiredReminderQueryException.class %>" message="you-have-not-configured-a-reminder-query" />
									<liferay-ui:error exception="<%= SendPasswordException.MustBeEnabled.class %>" message="password-recovery-is-disabled" />
									<liferay-ui:error exception="<%= UserActiveException.class %>" message="your-account-is-not-active" />
									<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeNull.class %>" message="please-enter-an-email-address" />
									<liferay-ui:error exception="<%= UserEmailAddressException.MustValidate.class %>" message="please-enter-a-valid-email-address" />
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
							
									<liferay-ui:error exception="<%= UserReminderQueryException.class %>" message="your-answer-does-not-match-what-is-in-our-database" />
							
									<aui:fieldset>
										<c:choose>
											<c:when test="<%= user2 == null %>">
							
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
							
												<aui:input name="step" type="hidden" value="1" />
							
												<c:if test="<%= !PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_ENABLED, PropsValues.USERS_REMINDER_QUERIES_ENABLED) %>">
													<portlet:renderURL var="redirectURL">
														<portlet:param name="mvcRenderCommandName" value="/login/login" />
													</portlet:renderURL>							
													<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
												</c:if>
												
												<div class="nps-input-box">
										 			<div class="nps-input-icon">
											 			<aui:input autocomplete="off" label="<%= loginLabel %>" name="login" size="30" type="text" value="<%= login %>" placeholder="Email Address">
															<aui:validator name="required" />
														</aui:input>
														<span>
					                                        <svg width="21" height="19" viewBox="0 0 21 19" fill="none" xmlns="http://www.w3.org/2000/svg">
					                                            <path d="M21 3.01474C21 1.82577 20.055 0.852974 18.9 0.852974H2.1C0.945 0.852974 0 1.82577 0 3.01474V15.9853C0 17.1743 0.945 18.1471 2.1 18.1471H18.9C20.055 18.1471 21 17.1743 21 15.9853V3.01474ZM18.9 3.01474L10.5 8.41915L2.1 3.01474H18.9ZM18.9 15.9853H2.1V5.1765L10.5 10.5809L18.9 5.1765V15.9853Z" fill="white"></path>
					                                        </svg>                                            
					                                    </span>
													 </div>
												 </div> 
							
												<div class="lr-captcha">
													<c:if test="<%= captchaConfiguration.sendPasswordCaptchaEnabled() %>">
														<liferay-captcha:captcha />
													</c:if>
												</div>
												 
												 
													<%-- <ul class="nps-captcha d-flex align-items-center justify-content-start nps-input-box captcha-box">
														<li class="nps-captcha-img text-center">
															<canvas id="canvas"></canvas>
														</li>
														<li class="d-flex align-items-center">
															<aui:input name="code" cssClass="pl-2" showRequiredLabel="<%= false %>" label="" placeholder="Captcha Code">
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
													</ul> --%>	
							
												<aui:button-row>
													<aui:button type="submit" value='<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_ENABLED, PropsValues.USERS_REMINDER_QUERIES_ENABLED) ? "next" : "send-new-password" %>' />
												</aui:button-row>
											</c:when>
											<c:when test="<%= user2 != null %>">
												<aui:input name="step" type="hidden" value="2" />
							
												<portlet:renderURL var="redirectURL">
													<portlet:param name="mvcRenderCommandName" value="/login/login" />
												</portlet:renderURL>
							
												<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
							
												<c:if test="<%= Validator.isNotNull(user2.getReminderQueryQuestion()) && Validator.isNotNull(user2.getReminderQueryAnswer()) %>">
													<div class="alert alert-info">
														<liferay-ui:message arguments="<%= HtmlUtil.escape(login) %>" key="an-email-will-be-sent-to-x-if-you-can-correctly-answer-the-following-question" translateArguments="<%= false %>" />
													</div>
							
													<aui:input autoFocus="<%= true %>" label="<%= HtmlUtil.escape(LanguageUtil.get(request, user2.getReminderQueryQuestion())) %>" name="answer" type="password" autocomplete="off"/>
												</c:if>
							
												<c:choose>
													<c:when test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_REQUIRED, PropsValues.USERS_REMINDER_QUERIES_REQUIRED) && !user2.hasReminderQuery() %>">
														<div class="alert alert-info">
															<liferay-ui:message key="the-password-cannot-be-reset-because-you-have-not-configured-a-reminder-query" />
														</div>
													</c:when>
													<c:otherwise>
														<c:if test="<%= reminderAttempts >= 3 %>">
															<liferay-captcha:captcha />
														</c:if>
							
														<aui:button-row>
															<aui:button type="submit" value='<%= company.isSendPasswordResetLink() ? "send-password-reset-link" : "send-new-password" %>' />
														</aui:button-row>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<div class="alert alert-warning">
													<liferay-ui:message key="the-system-cannot-send-you-a-new-password-because-you-have-not-provided-an-email-address" />
												</div>
											</c:otherwise>
										</c:choose> 		
									</aui:fieldset>
								</aui:form>			
						<%@ include file="/navigation.jspf" %>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<style>
.portlet-icon-back, .taglib-icon-list li:nth-child(2), .reference-mark {
	display: none !important;
}

.taglib-icon-list li a .taglib-text {
    color: #fff;
    font-size: 16px;
    text-decoration: none !important;
    font-weight: 700;
}

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
	
	$("#<portlet:namespace/>login").change(function(){
		let email = $("#<portlet:namespace/>login").val();
		let compid = themeDisplay.getCompanyId();
		$("button[type='submit']").attr("disabled",true);
		$.get("/o/user/email/"+email+"/compid/20097", function(data, status){
			if(data.emailReged){
				$("button[type='submit']").attr("disabled",false);
				$("#<portlet:namespace/>loginTextHelper").remove();
			}else{
				let error = '<div class="form-validator-stack help-block" id="<portlet:namespace/>loginTextHelper">'+
				'<div role="alert" class="required">Please enter your registered email.</div></div>';
				if($("#<portlet:namespace/>loginTextHelper").length > 0){
					
				}else{
					$("#<portlet:namespace/>login").after(error);
				}
			}
		});
	})
</script>