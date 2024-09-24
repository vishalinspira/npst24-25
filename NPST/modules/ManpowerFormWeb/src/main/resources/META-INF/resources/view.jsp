<%-- <%@page import="com.nps.manpower.model.ManpowerEmployee"%>
<%@page import="com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil"%> --%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldName"%>
<%@page import="com.nps.manpower.util.ManpowerUtil"%>
<%@page import="com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil"%>
<%@page import="com.nps.manpower.model.ManpowerEmployee"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.nps.manpower.constants.ManpowerEmployeeFieldLabel"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="keyPersonal" var="keyPersonal">
</portlet:resourceURL>

<%-- <div><p><a href="<%=addCompositionCommitteeURL%>">Back</a></p></div>--%>
<!-- <input type="button" value="Back" onclick="history.back()"/> -->
<%-- <div class="container">
<a href="<%=viewManpowerEmployeeURL%>" ><%=ManpowerEmployeeFieldLabel.KEYPERSONAL_EMPLOYEE_BUTTON %> </a>
<a href="<%=viewManpowerDirectorURL%>"><%=ManpowerEmployeeFieldLabel.DIRECTOR_EMPLOYEE_BUTTON %> </a>
<a href="<%=viewInvestmentCommitteeURL %>" ><%=ManpowerEmployeeFieldLabel.INVESTMENT_COMMITTEE_BUTTON %> </a>
<a href="<%=viewRiskManagementCommitteeURL%>"><%=ManpowerEmployeeFieldLabel.RISK_MANAGEMENT_COMMITTEEE_BUTTON %> </a>

</div> --%>

<div class="row">
    <div class=" col-lg-3 col-md-3 col-sm-6 col-12">
        <a href="<%=viewManpowerEmployeeURL%>" class="nps-box text-center position-relative">
            <div class="nps-icon">
                <span>
                    <svg width="53" height="38" viewBox="0 0 53 38" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M26.5 0.25C21.535 0.25 17.2188 2.57125 14.1363 5.99312C13.8813 5.96125 13.6525 5.875 13.375 5.875C9.25 5.875 5.875 9.25 5.875 13.375C2.63125 15.3531 0.25 18.6925 0.25 22.75C0.25 28.9375 5.3125 34 11.5 34H20.875V30.25H11.5C7.33938 30.25 4 26.9106 4 22.75C3.99965 21.2761 4.43285 19.8346 5.24566 18.605C6.05848 17.3755 7.21501 16.4121 8.57125 15.835L9.85938 15.31L9.68313 13.9037C9.65922 13.728 9.63984 13.5517 9.625 13.375C9.62093 12.8068 9.74707 12.2453 9.99375 11.7334C10.2404 11.2215 10.6011 10.773 11.048 10.4221C11.495 10.0713 12.0164 9.82754 12.5722 9.70951C13.128 9.59148 13.7034 9.60234 14.2544 9.74125L15.4262 10.0356L16.1294 9.09812C17.3551 7.51418 18.9268 6.23141 20.7242 5.34782C22.5216 4.46423 24.4972 4.00322 26.5 4C32.6444 4 37.7725 8.22625 39.2144 13.9019L39.5669 15.3663L41.1475 15.31C41.5431 15.2875 41.6312 15.25 41.5 15.25C45.6606 15.25 49 18.5894 49 22.75C49 26.9106 45.6606 30.25 41.5 30.25H32.125V34H41.5C47.6875 34 52.75 28.9375 52.75 22.75C52.75 16.8325 48.085 12.0925 42.2612 11.6762C39.97 5.07625 33.8613 0.25 26.5 0.25ZM26.5 15.25L19 22.75H24.625V37.75H28.375V22.75H34L26.5 15.25Z"
                            fill="url(#paint0_linear_130_1931)" />
                        <defs>
                            <linearGradient id="paint0_linear_130_1931" x1="26.5" y1="0.25" x2="26.5" y2="37.75" gradientUnits="userSpaceOnUse">
                                <stop stop-color="#3485D8" />
                                <stop offset="1" stop-color="#06C6FC" />
                            </linearGradient>
                        </defs>
                    </svg>
                </span>
            </div>
            <h4><%=ManpowerEmployeeFieldLabel.KEYPERSONAL_EMPLOYEE_BUTTON %></h4>
        </a>
    </div>
    <div class=" col-lg-3 col-md-3 col-sm-6 col-12">
        <a href="<%=viewManpowerDirectorURL%>" class="nps-box text-center position-relative nps-bg2">
            <div class="nps-icon">
                <span>
                    <svg width="54" height="43" viewBox="0 0 54 43" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M26.5 4.5415C21.535 4.5415 17.2188 6.86275 14.1363 10.2846C13.8813 10.2528 13.6525 10.1665 13.375 10.1665C9.25 10.1665 5.875 13.5415 5.875 17.6665C2.63125 19.6446 0.25 22.984 0.25 27.0415C0.25 33.229 5.3125 38.2915 11.5 38.2915H20.875V34.5415H11.5C7.33938 34.5415 4 31.2021 4 27.0415C3.99965 25.5676 4.43285 24.1261 5.24566 22.8965C6.05848 21.667 7.21501 20.7036 8.57125 20.1265L9.85938 19.6015L9.68313 18.1953C9.65922 18.0195 9.63984 17.8432 9.625 17.6665C9.62093 17.0983 9.74707 16.5368 9.99375 16.0249C10.2404 15.513 10.6011 15.0645 11.048 14.7137C11.495 14.3628 12.0164 14.119 12.5722 14.001C13.128 13.883 13.7034 13.8938 14.2544 14.0328L15.4262 14.3271L16.1294 13.3896C17.3551 11.8057 18.9268 10.5229 20.7242 9.63933C22.5216 8.75574 24.4972 8.29472 26.5 8.2915C32.6444 8.2915 37.7725 12.5178 39.2144 18.1934L39.5669 19.6578L41.1475 19.6015C41.5431 19.579 41.6312 19.5415 41.5 19.5415C45.6606 19.5415 49 22.8809 49 27.0415C49 31.2021 45.6606 34.5415 41.5 34.5415H32.125V38.2915H41.5C47.6875 38.2915 52.75 33.229 52.75 27.0415C52.75 21.124 48.085 16.384 42.2612 15.9678C39.97 9.36775 33.8613 4.5415 26.5 4.5415ZM26.5 19.5415L19 27.0415H24.625V42.0415H28.375V27.0415H34L26.5 19.5415Z"
                            fill="url(#paint0_linear_0_1)" />
                        <path d="M43.625 11.4585H47.875V12.4585H42.125V6.4585H43.125V10.9585V11.4585H43.625ZM42.625 1.4585C36.8349 1.4585 32.125 6.16835 32.125 11.9585C32.125 17.7486 36.8349 22.4585 42.625 22.4585C48.4151 22.4585 53.125 17.7486 53.125 11.9585C53.125 6.16835 48.4151 1.4585 42.625 1.4585Z"
                            fill="url(#paint1_linear_0_1)" stroke="white" />
                        <defs>
                            <linearGradient id="paint0_linear_0_1" x1="26.5" y1="4.5415" x2="26.5" y2="42.0415" gradientUnits="userSpaceOnUse">
                                <stop stop-color="#F59225" />
                                <stop offset="1" stop-color="#F9BF3A" />
                            </linearGradient>
                            <linearGradient id="paint1_linear_0_1" x1="42.625" y1="1.9585" x2="42.625" y2="21.9585" gradientUnits="userSpaceOnUse">
                                <stop stop-color="#F59225" />
                                <stop offset="1" stop-color="#F9BF3A" />
                            </linearGradient>
                        </defs>
                    </svg>
                </span>
            </div>
            <h4><%=ManpowerEmployeeFieldLabel.DIRECTOR_EMPLOYEE_BUTTON %></h4>
        </a>
    </div>
    <div class=" col-lg-3 col-md-3 col-sm-6 col-12">
        <a href="<%=viewInvestmentCommitteeURL %>" class="nps-box text-center position-relative nps-bg3">
            <div class="nps-icon">
                <span>
                    <svg width="39" height="50" viewBox="0 0 39 50" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M9.1875 28.4375H22.9375V31.875H9.1875V28.4375ZM9.1875 19.8438H29.8125V23.2812H9.1875V19.8438ZM9.1875 37.0312H17.7812V40.4688H9.1875V37.0312Z"
                            fill="url(#paint0_linear_130_1938)" />
                        <path d="M34.9688 6.09375H29.8125V4.375C29.8125 3.46332 29.4503 2.58898 28.8057 1.94432C28.161 1.29966 27.2867 0.9375 26.375 0.9375H12.625C11.7133 0.9375 10.839 1.29966 10.1943 1.94432C9.54966 2.58898 9.1875 3.46332 9.1875 4.375V6.09375H4.03125C3.11957 6.09375 2.24523 6.45591 1.60057 7.10057C0.955914 7.74523 0.59375 8.61957 0.59375 9.53125V45.625C0.59375 46.5367 0.955914 47.411 1.60057 48.0557C2.24523 48.7003 3.11957 49.0625 4.03125 49.0625H34.9688C35.8804 49.0625 36.7548 48.7003 37.3994 48.0557C38.0441 47.411 38.4062 46.5367 38.4062 45.625V9.53125C38.4062 8.61957 38.0441 7.74523 37.3994 7.10057C36.7548 6.45591 35.8804 6.09375 34.9688 6.09375ZM12.625 4.375H26.375V11.25H12.625V4.375ZM34.9688 45.625H4.03125V9.53125H9.1875V14.6875H29.8125V9.53125H34.9688V45.625Z"
                            fill="url(#paint1_linear_130_1938)" />
                        <defs>
                            <linearGradient id="paint0_linear_130_1938" x1="19.5" y1="19.8438" x2="19.5" y2="40.4688" gradientUnits="userSpaceOnUse">
                                <stop stop-color="#F37699" />
                                <stop offset="1" stop-color="#FD8F79" />
                            </linearGradient>
                            <linearGradient id="paint1_linear_130_1938" x1="19.5" y1="0.9375" x2="19.5" y2="49.0625" gradientUnits="userSpaceOnUse">
                                <stop stop-color="#F37699" />
                                <stop offset="1" stop-color="#FD8F79" />
                            </linearGradient>
                        </defs>
                    </svg>
                </span>
            </div>
            <h4><%=ManpowerEmployeeFieldLabel.INVESTMENT_COMMITTEE_BUTTON %></h4>
        </a>
    </div>
    <div class=" col-lg-3 col-md-3 col-sm-6 col-12">
        <a href="<%=viewRiskManagementCommitteeURL%>" class="nps-box text-center position-relative nps-bg4">
            <div class="nps-icon">
                <span>
                    <svg width="49" height="46" viewBox="0 0 49 46" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M2.08496 29.1539L0.127133 28.7453C0.099093 28.8797 0.0849609 29.0166 0.0849609 29.1539H2.08496ZM7.68871 2.2998V0.299805C6.7416 0.299805 5.92435 0.964121 5.73088 1.89126L7.68871 2.2998ZM41.3112 2.2998L43.269 1.89126C43.0756 0.964121 42.2583 0.299805 41.3112 0.299805V2.2998ZM46.915 29.1539H48.915C48.915 29.0166 48.9008 28.8797 48.8728 28.7453L46.915 29.1539ZM14.3112 29.1539L16.225 28.573C15.9692 27.7301 15.1921 27.1539 14.3112 27.1539V29.1539ZM16.3487 35.8674L14.4349 36.4482C14.6908 37.2911 15.4679 37.8674 16.3487 37.8674V35.8674ZM32.6512 35.8674V37.8674C33.532 37.8674 34.3092 37.2911 34.565 36.4482L32.6512 35.8674ZM34.6887 29.1539V27.1539C33.8079 27.1539 33.0307 27.7301 32.7749 28.573L34.6887 29.1539ZM46.915 43.6998V45.6998C48.0195 45.6998 48.915 44.8044 48.915 43.6998H46.915ZM2.08496 43.6998H0.0849609C0.0849609 44.8044 0.980391 45.6998 2.08496 45.6998L2.08496 43.6998ZM20.4298 15.8014C19.7208 14.9544 18.4594 14.8426 17.6124 15.5516C16.7654 16.2607 16.6536 17.522 17.3626 18.369L20.4298 15.8014ZM23.3792 22.4403L21.8456 23.7242C22.2057 24.1542 22.7297 24.4133 23.29 24.4384C23.8503 24.4634 24.3954 24.252 24.7923 23.8557L23.3792 22.4403ZM33.7583 14.9044C34.54 14.124 34.541 12.8576 33.7606 12.0759C32.9802 11.2943 31.7138 11.2932 30.9322 12.0736L33.7583 14.9044ZM4.04279 29.5624L9.64654 2.70835L5.73088 1.89126L0.127133 28.7453L4.04279 29.5624ZM7.68871 4.2998H41.3112V0.299805H7.68871V4.2998ZM39.3534 2.70835L44.9571 29.5624L48.8728 28.7453L43.269 1.89126L39.3534 2.70835ZM2.08496 31.1539H14.3112V27.1539H2.08496V31.1539ZM12.3974 29.7347L14.4349 36.4482L18.2625 35.2865L16.225 28.573L12.3974 29.7347ZM16.3487 37.8674H32.6512V33.8674H16.3487V37.8674ZM34.565 36.4482L36.6025 29.7347L32.7749 28.573L30.7374 35.2865L34.565 36.4482ZM34.6887 31.1539H46.915V27.1539H34.6887V31.1539ZM44.915 29.1539V43.6998H48.915V29.1539H44.915ZM46.915 41.6998H2.08496V45.6998H46.915V41.6998ZM4.08496 43.6998V29.1539H0.0849609V43.6998H4.08496ZM17.3626 18.369L21.8456 23.7242L24.9128 21.1565L20.4298 15.8014L17.3626 18.369ZM24.7923 23.8557L33.7583 14.9044L30.9322 12.0736L21.9662 21.025L24.7923 23.8557Z"
                            fill="url(#paint0_linear_130_1941)" />
                        <defs>
                            <linearGradient id="paint0_linear_130_1941" x1="25.6207" y1="13.489" x2="25.6207" y2="22.4404" gradientUnits="userSpaceOnUse">
                                <stop stop-color="#07CAEA" />
                                <stop offset="1" stop-color="#01E5D6" />
                            </linearGradient>
                        </defs>
                    </svg>
                </span>
            </div>
            <h4><%=ManpowerEmployeeFieldLabel.RISK_MANAGEMENT_COMMITTEEE_BUTTON %></h4>
        </a>
    </div>
    
<%--         <div class=" col-lg-3 col-md-3 col-sm-6 col-12">
        <a href="<%=viewDesignationHistoryURL%>" class="nps-box text-center position-relative nps-bg4">
            <div class="nps-icon">
                <span>
                    <svg width="49" height="46" viewBox="0 0 49 46" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M2.08496 29.1539L0.127133 28.7453C0.099093 28.8797 0.0849609 29.0166 0.0849609 29.1539H2.08496ZM7.68871 2.2998V0.299805C6.7416 0.299805 5.92435 0.964121 5.73088 1.89126L7.68871 2.2998ZM41.3112 2.2998L43.269 1.89126C43.0756 0.964121 42.2583 0.299805 41.3112 0.299805V2.2998ZM46.915 29.1539H48.915C48.915 29.0166 48.9008 28.8797 48.8728 28.7453L46.915 29.1539ZM14.3112 29.1539L16.225 28.573C15.9692 27.7301 15.1921 27.1539 14.3112 27.1539V29.1539ZM16.3487 35.8674L14.4349 36.4482C14.6908 37.2911 15.4679 37.8674 16.3487 37.8674V35.8674ZM32.6512 35.8674V37.8674C33.532 37.8674 34.3092 37.2911 34.565 36.4482L32.6512 35.8674ZM34.6887 29.1539V27.1539C33.8079 27.1539 33.0307 27.7301 32.7749 28.573L34.6887 29.1539ZM46.915 43.6998V45.6998C48.0195 45.6998 48.915 44.8044 48.915 43.6998H46.915ZM2.08496 43.6998H0.0849609C0.0849609 44.8044 0.980391 45.6998 2.08496 45.6998L2.08496 43.6998ZM20.4298 15.8014C19.7208 14.9544 18.4594 14.8426 17.6124 15.5516C16.7654 16.2607 16.6536 17.522 17.3626 18.369L20.4298 15.8014ZM23.3792 22.4403L21.8456 23.7242C22.2057 24.1542 22.7297 24.4133 23.29 24.4384C23.8503 24.4634 24.3954 24.252 24.7923 23.8557L23.3792 22.4403ZM33.7583 14.9044C34.54 14.124 34.541 12.8576 33.7606 12.0759C32.9802 11.2943 31.7138 11.2932 30.9322 12.0736L33.7583 14.9044ZM4.04279 29.5624L9.64654 2.70835L5.73088 1.89126L0.127133 28.7453L4.04279 29.5624ZM7.68871 4.2998H41.3112V0.299805H7.68871V4.2998ZM39.3534 2.70835L44.9571 29.5624L48.8728 28.7453L43.269 1.89126L39.3534 2.70835ZM2.08496 31.1539H14.3112V27.1539H2.08496V31.1539ZM12.3974 29.7347L14.4349 36.4482L18.2625 35.2865L16.225 28.573L12.3974 29.7347ZM16.3487 37.8674H32.6512V33.8674H16.3487V37.8674ZM34.565 36.4482L36.6025 29.7347L32.7749 28.573L30.7374 35.2865L34.565 36.4482ZM34.6887 31.1539H46.915V27.1539H34.6887V31.1539ZM44.915 29.1539V43.6998H48.915V29.1539H44.915ZM46.915 41.6998H2.08496V45.6998H46.915V41.6998ZM4.08496 43.6998V29.1539H0.0849609V43.6998H4.08496ZM17.3626 18.369L21.8456 23.7242L24.9128 21.1565L20.4298 15.8014L17.3626 18.369ZM24.7923 23.8557L33.7583 14.9044L30.9322 12.0736L21.9662 21.025L24.7923 23.8557Z"
                            fill="url(#paint0_linear_130_1941)" />
                        <defs>
                            <linearGradient id="paint0_linear_130_1941" x1="25.6207" y1="13.489" x2="25.6207" y2="22.4404" gradientUnits="userSpaceOnUse">
                                <stop stop-color="#07CAEA" />
                                <stop offset="1" stop-color="#01E5D6" />
                            </linearGradient>
                        </defs>
                    </svg>
                </span>
            </div>
            <h4><%=ManpowerEmployeeFieldLabel.DESIGNATION_HISTORY_BUTTON%></h4>
        </a>
    </div> --%>
    
</div>



<script>
function keyPersonal(){
	 
	 window.location.href="<%=viewManpowerEmployeeURL%>";
}

function director(){
   	
   	window.location.href="<%=viewManpowerDirectorURL%>";
        
}

<%-- $(document).ready(function() {
    $('#userTable').dataTable( {
        "ajax": "<%=usersObjectArrayURL%>",
        "columns": [
            { "data": "<%=ManpowerEmployeeFieldLabelName.MANPOWER_EMPLOYEE_ID %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.DESIGNATION %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.NAME %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.APPOINTMENTDATE %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.CONTACT_NO %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.EMAIL %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.QUALIFICATION %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.EXPERIENCE %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.DEPUTATION %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.LINKEDINID %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.APPROVING_APPOINTMENT_DATE %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.BIODATA_FILE_ID %>" },
            { "data": "<%=ManpowerEmployeeFieldLabelName.COMMITTEE_MEMBERSHIP_TYPE %>" },
        ]
    } );
} ); --%>
</script>
