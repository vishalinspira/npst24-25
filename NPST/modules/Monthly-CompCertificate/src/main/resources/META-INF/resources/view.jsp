<%@page import="com.nps.form.autosave.constants.MonthlyCompFormConstants"%>
<%@page import="com.nps.form.autosave.constants.AutoSaveConstants"%>
<%@page import="com.nps.form.autosave.constants.FormsPortletsKeyConstants"%>
<%@page import="com.daily.average.service.model.MnCompCertificate"%>
<%@page import="com.daily.average.service.service.MnCompCertificateLocalServiceUtil"%>
<%@page import="com.monthly.compcertificate.reeource.MonthlyCompCertificate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.monthly.compcertificate.util.MonthlyCompCertificateUtil"%>
<%@page import="com.monthly.compcertificate.util.Pre_Populate_scrutinydata"%>
<%@page import="com.daily.average.service.service.MnCompCertificateScrutinyLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.daily.average.service.model.MnCompCertificateScrutiny"%>
<%@page import="com.monthly.compcertificate.constants.MonthlyCompCertificatePortletKeys"%>
<%@ include file="/init.jsp" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" />

<portlet:resourceURL id="<%=MonthlyCompCertificatePortletKeys.mnCompCertificate%>" var="mnCompCertificateResourceURL" />

<%-- <%

Pre_Populate_scrutinydata sd = new Pre_Populate_scrutinydata();
sd.pre_populate_scrutiny_data(request);

MnCompCertificateScrutiny mnCompCertificateScrutiny = Validator.isNotNull(request.getAttribute("mnCompCertificateScrutiny")) ? (MnCompCertificateScrutiny) request.getAttribute("mnCompCertificateScrutiny") : MnCompCertificateScrutinyLocalServiceUtil.createMnCompCertificateScrutiny(0);

System.out.println("JSP mnCompCertificateScrutiny ------------------- "+mnCompCertificateScrutiny.toString());

%> --%>


<% 
Pre_Populate_scrutinydata sd = new Pre_Populate_scrutinydata();
sd.pre_populate_scrutiny_data(request);

MnCompCertificateScrutiny mnCompCertificateScrutiny = Validator.isNotNull(request.getAttribute("mnCompCertificateScrutiny")) ? (MnCompCertificateScrutiny) request.getAttribute("mnCompCertificateScrutiny") : MnCompCertificateScrutinyLocalServiceUtil.createMnCompCertificateScrutiny(0);

//System.out.println("JSP mnCompCertificateScrutiny ------------------- "+mnCompCertificateScrutiny.toString());

MonthlyCompCertificateUtil monthlyCompCertificateUtil = new MonthlyCompCertificateUtil();
boolean isNonNPSUser = monthlyCompCertificateUtil.isNonNpsUser(themeDisplay.getUserId());

SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
SimpleDateFormat reportDateFormat = new SimpleDateFormat("MMMM/yyyy");
long reportuploadlogId=0;
//long reportuploadlogId=ParamUtil.getLong(request, "reportuploadlogId");
String formDate1="";
try{
	
	String dDate[]=((String)request.getAttribute("reportDate")).split("-");
	int month=Integer.parseInt(dDate[1]);
	int year=Integer.parseInt(dDate[0]);
	if(month==1){
		year=year-1;
		month=12;
	}else{
		month=month-1;	
	}
	formDate1=month+"/"+year;
}catch(Exception e1){
	
}
 
//out.println("reportuploadlogId:  "+reportuploadlogId);
MnCompCertificate mnCompCertificate=null;
try{
	reportuploadlogId=(Long)request.getAttribute("reportUploadLogId");
	//out.println((String)request.getAttribute("reportDate")+"  upload log id: "+reportuploadlogId);
	mnCompCertificate = MnCompCertificateLocalServiceUtil.fetchMnCompCertificate(reportuploadlogId);
}catch(Exception e){
	mnCompCertificate=MnCompCertificateLocalServiceUtil.createMnCompCertificate(0);
}
if(Validator.isNull(mnCompCertificate)){
	mnCompCertificate=MnCompCertificateLocalServiceUtil.createMnCompCertificate(0);
}
if(Validator.isNull(mnCompCertificateScrutiny)){
	mnCompCertificateScrutiny=MnCompCertificateScrutinyLocalServiceUtil.createMnCompCertificateScrutiny(0);
}

%>



<div class="modal fade" id="success_tic" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center">
      	<i class="fas fa-check-circle text-success fa-4x d-block mb-4"></i>      
        Data sent for Review.
      </div>       
    </div>
  </div>
</div>
<div class="modal fade" id="errorExcel" tabindex="-1" aria-labelledby="success_ticLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center text-danger">
      	<i class="fas fa-times-circle  fa-4x d-block mb-4"></i>      
        <span id="output"></span>
      </div>       
    </div>
  </div>
</div>
<div class="nps-page-main nps-upload-report nps-statement-wrapper">
	<div class="row mb-3">
	     <div class="col-12">
	        <div class="text-right">
	            <p  class="back_btn"><a class="backbtn" href="/web/guest/monthly-average-pfm"><i class="fas fa-arrow-left text-white pr-3"></i>Back</a></p>
	        </div>
	     </div>
	  </div>
   <div class="row" id="canvasD">
      <div class="col-lg-12 col-md-12 col-sm-12 col-12">
         <div class="nps-report">
            <h4>Monthly Compliance Certificate</h4>
            <form class="form" id="monthlyCompCertificate" action="#" method="post">
            <input type="hidden" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId" value="${reportUploadLogId }"/>
				<input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
             	 <div class="row">
	             	<div class="col-lg-6 col-md-6 col-sm-12 col-12 nps-input-box mt-0">
	                    <label for="name">Report Due Date</label>
	                    <input class="reportDate" name="reportDate" type="date" value="${reportDate }" readonly="readonly">
	                </div>
                </div>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>For the Month Ended </label>
                       <%--  <input type="date" class="date_1" id="date_1" value="<%=Validator.isNotNull(mnCompCertificate.getDate_1())? dateFormat.format(mnCompCertificate.getDate_1()):"" %>" readOnly="false" name="<portlet:namespace />date_1"> --%>
                     <input type="date" class="date" id="date_1" name="date_1"/>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="col-md-9">
<p class="font-weight-bold mb-0">To,</p>
<p class=" font-weight-bold mb-0">The Chief Executive Officer</p>
<p class=" font-weight-bold mb-0">National Pension System Trust,</p>
<p class=" font-weight-bold mb-0">B-302, Third Floor,</p>
<p class="font-weight-bold mb-0">Tower B, World Trade Center,</p>
<p class="font-weight-bold mb-0">Nauroji Nagar, New Delhi-110029</p>

                     </div>
                  </div>
               </div>
               <br>
               <p>Sir,</p>
               <br>
               <p>In my opinion and to the best of my information and
                  according to the examinations carried out by me and explanations
                  furnished to me, I certify the following in respect of the month
                  mentioned above
               </p>
               <br>
               <hr/>
<div class="row">
<div class="col-md-1">
<h5>SL.NO</h5>
</div>
<div class="col-md-5">
<h5>Parameters </h5>
</div>
<div class="col-md-2">
<h5>Yes/No/NA</h5>
</div>
<div class="col-md-2">
<h5>PFM Remarks</h5>
</div>
<div class="col-md-2">
<h5>NPST Remarks</h5>
</div>
</div>
<hr/>
               <h5>1 . Purchase of securities</h5>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>1.1</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <p>Whether purchase of securities adhere to the Investment guidelines issued by PFRDA. 
                        (prescribed securities/ percentage/ limit/ prudential & exposure norms)
                        Details of deviations provided in annexure A.
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase1" id="purchase_1" name='<portlet:namespace/>purchase1' value="Yes" <%=(mnCompCertificate.getPurchase_of_securities()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase1" id="purchase_2" name='<portlet:namespace/>purchase1' value="No" <%=(mnCompCertificate.getPurchase_of_securities()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase1" id="purchase_3" name='<portlet:namespace/>purchase1' value="NA" <%=(mnCompCertificate.getPurchase_of_securities()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                  <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  	<textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Purchase_of_securitiesremarks_1_1_2' required><%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getPurchase_of_sec_rem_1_1_2():"" %></textarea>
                    	<textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_1" ><%=mnCompCertificateScrutiny.getPurchase_of_securities_rem() == null ? "" :  mnCompCertificateScrutiny.getPurchase_of_securities_rem()%></textarea>
                     </div>
                  </div>
               </div><br>
               <div class="row">
                  <div class="col-md-1">
                     <p>1.2</p>
                  </div>
                  <div class="col-md-5">
                     <p>(a) Whether a detailed Investment process manual is prepared and approved by Board / board delegated authority and whether actual process is established as per approved manual.
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_4" name='<portlet:namespace/>purchase2_1' value="Yes" <%=(mnCompCertificate.getDetailed_investment()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_5" name='<portlet:namespace/>purchase2_1' value="No" <%=(mnCompCertificate.getDetailed_investment()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_6" name='<portlet:namespace/>purchase2_1' value="NA" <%=(mnCompCertificate.getDetailed_investment()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                   <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Purchase_of_securitiesremarks_1_2a_2' required><%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getPurchase_of_sec_rem_1_2a_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2a"><%=mnCompCertificateScrutiny.getDetailed_investment_rem() == null ? "" :  mnCompCertificateScrutiny.getDetailed_investment_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        (b) Whether investments are approved by the committee/competent authority as per Approval delegation matrix
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_7" name='<portlet:namespace/>purchase2_2' value="Yes" <%=(mnCompCertificate.getInvestments_approved()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_8" name='<portlet:namespace/>purchase2_2' value="No" <%=(mnCompCertificate.getInvestments_approved()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_9" name='<portlet:namespace/>purchase2_2' value="NA" <%=(mnCompCertificate.getInvestments_approved()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Purchase_of_securitiesremarks_1_2b_2' required><%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getPurchase_of_sec_rem_1_2b_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2b"><%=mnCompCertificateScrutiny.getInvestments_approved_rem() == null ? "" :  mnCompCertificateScrutiny.getInvestments_approved_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        (c) Whether each decision of investment is properly documented and record is maintained at individual transaction level.
                        (Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_10" name='<portlet:namespace/>purchase2_3' value="Yes" <%=(mnCompCertificate.getDecision_of_investment()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_11" name='<portlet:namespace/>purchase2_3' value="No" <%=(mnCompCertificate.getDecision_of_investment()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_12" name='<portlet:namespace/>purchase2_3' value="NA" <%=(mnCompCertificate.getDecision_of_investment()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                      <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  	<textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Purchase_of_securitiesremarks_1_2c_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getPurchase_of_sec_rem_1_2c_2():"" %></textarea>
                    	<textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2c"><%=mnCompCertificateScrutiny.getDecision_of_investment_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_of_investment_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        (d) Whether investments for non-dematerialized securities are supported by certificates/ statements.
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_13" name='<portlet:namespace/>purchase2_4' value="Yes" <%=(mnCompCertificate.getInvestments_non_dematerialized()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_14" name='<portlet:namespace/>purchase2_4' value="No" <%=(mnCompCertificate.getInvestments_non_dematerialized()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_15" name='<portlet:namespace/>purchase2_4' value="NA" <%=(mnCompCertificate.getInvestments_non_dematerialized()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                      <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Purchase_of_securitiesremarks_1_2d_2' required><%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getPurchase_of_sec_rem_1_2d_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2d"><%=mnCompCertificateScrutiny.getInvest_non_dematerialized_rem() == null ? "" :  mnCompCertificateScrutiny.getInvest_non_dematerialized_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        (e) Whether all investments from funds received under NPS schemes are held in the name of NPS Trust
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_16" name='<portlet:namespace/>purchase2_5' value="Yes" <%=(mnCompCertificate.getAll_investments_from_funds()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_17" name='<portlet:namespace/>purchase2_5' value="No" <%=(mnCompCertificate.getAll_investments_from_funds()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase2" id="purchase_18" name='<portlet:namespace/>purchase2_5' value="NA" <%=(mnCompCertificate.getAll_investments_from_funds()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Purchase_of_securitiesremarks_1_2e_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getPurchase_of_sec_rem_1_2e_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_2e"><%=mnCompCertificateScrutiny.getAll_investments_from_funds_rem() == null ? "" :  mnCompCertificateScrutiny.getAll_investments_from_funds_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>1.3</p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        Whether delivery of securities is taken immediately on purchase as per settlement cycle/ terms for each transaction.
                        <br>
                        Details of exceptions to normal settlement procedure such as hand delivery, short delivery, trade reversal etc. provided in Annexure B.
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase3" id="purchase_19" name='<portlet:namespace/>purchase3' value="Yes" <%=(mnCompCertificate.getDelivery_of_securities_purch()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase3" id="purchase_20" name='<portlet:namespace/>purchase3' value="No" <%=(mnCompCertificate.getDelivery_of_securities_purch()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase3" id="purchase_21" name='<portlet:namespace/>purchase3' value="NA" <%=(mnCompCertificate.getDelivery_of_securities_purch()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3" placeholder="Remarks if any" name='<portlet:namespace/>Purchase_of_securitiesremarks_1_3_2' required ><%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getPurchase_of_sec_rem_1_3_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_3"><%=mnCompCertificateScrutiny.getDelivery_of_security_purch_rem() == null ? "" :  mnCompCertificateScrutiny.getDelivery_of_security_purch_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>1.4</p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        Whether any application/investment is done in Initial Public Offer (IPO), Follow on Public Offer (FPO) and/or Offer for sale (OFS) during the period?
                     </p>
                     <p>Details of Investments provided in Annexure C.</p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase4" id="purchase_22" name='<portlet:namespace/>purchase4' value="Yes" <%=(mnCompCertificate.getInvestment_done_in_ipo()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase4" id="purchase_23" name='<portlet:namespace/>purchase4' value="No" <%=(mnCompCertificate.getInvestment_done_in_ipo()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="purchase4" id="purchase_24" name='<portlet:namespace/>purchase4' value="NA" <%=(mnCompCertificate.getInvestment_done_in_ipo()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                   <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Purchase_of_securitiesremarks_1_4_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getPurchase_of_secu_rem_1_4_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Purchase_of_securitiesremarks_1_4"><%=mnCompCertificateScrutiny.getInvestment_done_in_ipo_rem() == null ? "" :  mnCompCertificateScrutiny.getInvestment_done_in_ipo_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <h5>2 . Securities held</h5>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>2.1</p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        Whether scheme investments adhere to the Investment guidelines issued by PFRDA. 
                        (prescribed securities/ percentage/ limit/ prudential & exposure norms)
                     </p>
                     <p>Details of deviations provided in annexure A.</p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities1" id="securities_1" name='<portlet:namespace/>securities1' value="Yes" <%=(mnCompCertificate.getScheme_investments()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities1" id="securities_2" name='<portlet:namespace/>securities1' value="No" <%=(mnCompCertificate.getScheme_investments()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities1" id="securities_3" name='<portlet:namespace/>securities1' value="NA" <%=(mnCompCertificate.getScheme_investments()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Securities_held_2_1_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSecurities_held_2_1_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Securities_held_2_1"><%=mnCompCertificateScrutiny.getScheme_investments_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_investments_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2.2</p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        a) Whether stop loss/ any other review trigger has occurred for any security (equity/debt/alternate) during the month as per Investment policy of the Pension Fund. 
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities2" id="securities2_1" name='<portlet:namespace/>securities2_1' value="Yes" <%=(mnCompCertificate.getStop_loss_trigger()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities2" id="securities2_2" name='<portlet:namespace/>securities2_1' value="No" <%=(mnCompCertificate.getStop_loss_trigger()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities2" id="securities2_3" name='<portlet:namespace/>securities2_1' value="NA" <%=(mnCompCertificate.getStop_loss_trigger()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Securities_held_2_2a_2' required><%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSecurities_held_2_2a_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Securities_held_2_2a"><%=mnCompCertificateScrutiny.getStop_loss_trigger_rem() == null ? "" :  mnCompCertificateScrutiny.getStop_loss_trigger_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        b) Whether decision in such a scenario is approved by the committee/competent authority as per Approval delegation matrix 
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities2" id="securities2_4" name='<portlet:namespace/>securities2_2' value="Yes" <%=(mnCompCertificate.getDecision_approved_by_committee()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities2" id="securities2_5" name='<portlet:namespace/>securities2_2' value="No" <%=(mnCompCertificate.getDecision_approved_by_committee()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities2" id="securities2_6" name='<portlet:namespace/>securities2_2' value="NA" <%=(mnCompCertificate.getDecision_approved_by_committee()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-4"   placeholder="Remarks if any" name='<portlet:namespace/>Securities_held_2_2b_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSecurities_held_2_2b_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Securities_held_2_2b"><%=mnCompCertificateScrutiny.getDecision_appr_by_committee_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_appr_by_committee_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        c) Whether each decision along with rationale is properly documented and record is maintained at individual scrip level.
                       <br> (Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)
                     </p>
                     <p>Details of stop loss/review triggered during the month and its decision provided in Annexure D.</p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities2" id="securities2_7" name='<portlet:namespace/>securities2_3' value="Yes" <%=(mnCompCertificate.getDecision_properly_documented()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities2" id="securities2_8" name='<portlet:namespace/>securities2_3' value="No" <%=(mnCompCertificate.getDecision_properly_documented()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities2" id="securities2_9" name='<portlet:namespace/>securities2_3' value="NA" <%=(mnCompCertificate.getDecision_properly_documented()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Securities_held_2_2c_2' required><%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSecurities_held_2_2c_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Securities_held_2_2c"><%=mnCompCertificateScrutiny.getDecision_prop_documented_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_prop_documented_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2.3</p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        Whether inter-scheme transfer of securities complies with Investment Guidelines issued by PFRDA. 
                     </p>
                     <p>Details of inter scheme transfer provided in Annexure E.</p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities3" id="securities_10" name='<portlet:namespace/>securities3' value="Yes" <%=(mnCompCertificate.getInter_scheme_transfer()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities3" id="securities_11" name='<portlet:namespace/>securities3' value="No" <%=(mnCompCertificate.getInter_scheme_transfer()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities3" id="securities_12" name='<portlet:namespace/>securities3' value="NA" <%=(mnCompCertificate.getInter_scheme_transfer()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Securities_held_2_3_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSecurities_held_2_3_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Securities_held_2_3"><%=mnCompCertificateScrutiny.getInter_scheme_transfer_rem() == null ? "" :  mnCompCertificateScrutiny.getInter_scheme_transfer_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2.4</p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        Whether investment is held in any equity shares of a body corporate which is not listed in top 200 list of stocks prepared by NPS Trust and is pending for rebalancing. If yes, whether the decision to hold such stocks has been approved by the investment committee and informed to the Board of Pension Fund
                     </p>
                     <p>Details provided in Annexure F</p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities4" id="securities_10" name='<portlet:namespace/>securities4' value="Yes" <%=(mnCompCertificate.getInvestment_held_in_equity()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities4" id="securities_11" name='<portlet:namespace/>securities4' value="No" <%=(mnCompCertificate.getInvestment_held_in_equity()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities4" id="securities_12" name='<portlet:namespace/>securities4' value="NA" <%=(mnCompCertificate.getInvestment_held_in_equity()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Securities_held_2_4_2' required><%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSecurities_held_2_4_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Securities_held_2_4"><%=mnCompCertificateScrutiny.getInvestment_held_in_equity_rem() == null ? "" :  mnCompCertificateScrutiny.getInvestment_held_in_equity_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>2.5</p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        Whether investment in any equity shares through IPO/FPO/OFS does not fulfil the market capitalization condition prescribed under investment guidelines post listing.
                     </p>
                     <p>Details provided in Annexure G.</p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities5" id="securities_13" name='<portlet:namespace/>securities5' value="Yes" <%=(mnCompCertificate.getInvestment_in_equity_shares()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities5" id="securities_14" name='<portlet:namespace/>securities5' value="No" <%=(mnCompCertificate.getInvestment_in_equity_shares()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities5" id="securities_15" name='<portlet:namespace/>securities5' value="NA" <%=(mnCompCertificate.getInvestment_in_equity_shares()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Securities_held_2_5_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSecurities_held_2_5_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Securities_held_2_5"><%=mnCompCertificateScrutiny.getInvest_in_equity_shares_rem() == null ? "" :  mnCompCertificateScrutiny.getInvest_in_equity_shares_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
                <div class="row">
                  <div class="col-md-1">
                     <p>2.6</p>
                  </div>
                  <div class="col-md-5">
                     <p>
                       Whether the Pension Fund has segregated the deviations/breaches into active and passive as per PFRDA Circular No. PFRDA/2022/04/SUP-PF/01 dated 04.02.2022 and regularized the active breaches/deviations immediately within 3 business days from the date of active deviation happened.
                     </p>
                     <p>Details provided in Annexure H.</p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities6" id="securities_16" name='<portlet:namespace/>securities6' value="Yes" <%=(mnCompCertificate.getActive_passive_breaches()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities6" id="securities_17" name='<portlet:namespace/>securities6' value="No" <%=(mnCompCertificate.getActive_passive_breaches()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="securities6" id="securities_18" name='<portlet:namespace/>securities6' value="NA" <%=(mnCompCertificate.getActive_passive_breaches()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Securities_held_2_6_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSecurities_held_2_6_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Securities_held_2_6"><%=mnCompCertificateScrutiny.getActive_passive_breaches_rem() == null ? "" :  mnCompCertificateScrutiny.getActive_passive_breaches_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               
               <h5>3 . Sale of securities</h5>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <p>3.1</p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        (a) Whether disinvestments made are approved by the committee/competent authority as per delegation matrix
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="sale1" id="sale_1" name='<portlet:namespace/>sale1_1' value="Yes" <%=(mnCompCertificate.getDisinvestments_approved()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="sale1" id="sale_2" name='<portlet:namespace/>sale1_1' value="No" <%=(mnCompCertificate.getDisinvestments_approved()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="sale1" id="sale_3" name='<portlet:namespace/>sale1_1' value="NA" <%=(mnCompCertificate.getDisinvestments_approved()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3" placeholder="Remarks if any" name='<portlet:namespace/>Sale_of_securities_3_1a_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSale_of_securities_3_1a_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Sale_of_securities_3_1a"><%=mnCompCertificateScrutiny.getDisinvestments_approved_rem() == null ? "" :  mnCompCertificateScrutiny.getDisinvestments_approved_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p></p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        (b) Whether each decision of disinvestment is properly documented and record is maintained at individual transaction level.
                        <br>(Supporting documents evidencing due diligence including analysis of Investee company, Industry, Group etc. should be maintained along with justification of investment decision)
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="sale1" id="sale_4" name='<portlet:namespace/>sale1_2' value="Yes" <%=(mnCompCertificate.getDecision_of_disinvestment()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="sale1" id="sale_5" name='<portlet:namespace/>sale1_2' value="No" <%=(mnCompCertificate.getDecision_of_disinvestment()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="sale1" id="sale_6" name='<portlet:namespace/>sale1_2' value="NA" <%=(mnCompCertificate.getDecision_of_disinvestment()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Sale_of_securities_3_1b_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSale_of_securities_3_1b_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Sale_of_securities_3_1b"><%=mnCompCertificateScrutiny.getDecision_of_disinvestment_rem() == null ? "" :  mnCompCertificateScrutiny.getDecision_of_disinvestment_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <p>3.2</p>
                  </div>
                  <div class="col-md-5">
                     <p>
                        Whether delivery of securities is given immediately on sale as per settlement cycle/ terms for each transaction. Please provide details of exceptions, if any, to normal settlement procedure such as hand delivery, short delivery, trade reversal etc.
                     </p>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="sale2" id="sale_7" name='<portlet:namespace/>sale2' value="Yes" <%=(mnCompCertificate.getDelivery_of_securities_sale()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="sale2" id="sale_8" name='<portlet:namespace/>sale2' value="No" <%=(mnCompCertificate.getDelivery_of_securities_sale()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="sale2" id="sale_9" name='<portlet:namespace/>sale2' value="NA" <%=(mnCompCertificate.getDelivery_of_securities_sale()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Sale_of_securities_3_2_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getSale_of_securities_3_2_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Sale_of_securities_3_2"><%=mnCompCertificateScrutiny.getDelivery_of_security_sale_rem() == null ? "" :  mnCompCertificateScrutiny.getDelivery_of_security_sale_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <h5>4. NAV calculations and daily reconciliations </h5>
               <hr>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>4.1</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(a) Whether scheme-wise number of units are tallied with CRA records on daily basis.
                        </p>
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_1" name='<portlet:namespace/>reports1_1' value="Yes" <%=(mnCompCertificate.getAll_investment_charges()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_2" name='<portlet:namespace/>reports1_1' value="No" <%=(mnCompCertificate.getAll_investment_charges()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_3" name='<portlet:namespace/>reports1_1' value="NA" <%=(mnCompCertificate.getAll_investment_charges()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Reports_and_Disclosures_4_1a_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getReports_and_Disclosure_4_1a_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1a"><%=mnCompCertificateScrutiny.getAll_investment_charges_rem() == null ? "" :  mnCompCertificateScrutiny.getAll_investment_charges_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p></p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(b) Whether the securities held in the schemes are tallied with the Custodian records on a daily basis.
                        </p>
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_4" name='<portlet:namespace/>reports1_2' value="Yes" <%=(mnCompCertificate.getPfm_adhered()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_5" name='<portlet:namespace/>reports1_2' value="No" <%=(mnCompCertificate.getPfm_adhered()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_6" name='<portlet:namespace/>reports1_2' value="NA" <%=(mnCompCertificate.getPfm_adhered()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                     <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Reports_and_Disclosures_4_1b_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getReports_and_Disclosure_4_1b_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1b"><%=mnCompCertificateScrutiny.getPfm_adhered_rem() == null ? "" :  mnCompCertificateScrutiny.getPfm_adhered_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p></p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(c) Whether all investment charges (Investment management fees, Custody and related charges, SEBI charges, NPS Trust fees etc.) are loaded onto the scheme-wise NAV on a daily basis.
                        </p>
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_7" name='<portlet:namespace/>reports1_3' value="Yes" <%=(mnCompCertificate.getRecords_of_the_audit_of_nav()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_8" name='<portlet:namespace/>reports1_3' value="No" <%=(mnCompCertificate.getRecords_of_the_audit_of_nav()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_9" name='<portlet:namespace/>reports1_3' value="NA" <%=(mnCompCertificate.getRecords_of_the_audit_of_nav()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Reports_and_Disclosures_4_1c_2' required><%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getReports_and_Disclosure_4_1c_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1c"><%=mnCompCertificateScrutiny.getRecords_of_audit_of_nav_rem() == null ? "" :  mnCompCertificateScrutiny.getRecords_of_audit_of_nav_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p></p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(d) Whether the Pension Fund has adhered to instructions of PFRDA to get the scheme-wise NAV verified by the auditors on a daily basis.
                        </p>
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_10" name='<portlet:namespace/>reports1_4' value="Yes" <%=(mnCompCertificate.getScheme_wise_nav_uploaded()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_11" name='<portlet:namespace/>reports1_4' value="No" <%=(mnCompCertificate.getScheme_wise_nav_uploaded()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_12" name='<portlet:namespace/>reports1_4' value="NA" <%=(mnCompCertificate.getScheme_wise_nav_uploaded()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea   class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Reports_and_Disclosures_4_1d_2' required > <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getReports_and_Disclosure_4_1d_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1d"  ><%=mnCompCertificateScrutiny.getScheme_wise_nav_uploaded_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_wise_nav_uploaded_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p></p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(e) Whether the records of the audit of scheme-wise NAV have been maintained by the pension fund for future inspection. 
                        </p>
                        
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_13" name='<portlet:namespace/>reports1_5' value="Yes" <%=(mnCompCertificate.getScheme_wise_nav_submitted()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_14" name='<portlet:namespace/>reports1_5' value="No" <%=(mnCompCertificate.getScheme_wise_nav_submitted()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="reports1" id="reports_15" name='<portlet:namespace/>reports1_5' value="NA" <%=(mnCompCertificate.getScheme_wise_nav_submitted()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"  placeholder="Remarks if any" name='<portlet:namespace/>Reports_and_Disclosures_4_1e_2' required><%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getReports_and_Disclosure_4_1e_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Reports_and_Disclosures_4_1e"><%=mnCompCertificateScrutiny.getScheme_wise_nav_submitted_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_wise_nav_submitted_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               
               <h5>5. Reports and Disclosure requirements </h5>
               <hr>
               
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p>5.1</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(a) Whether scheme-wise NAV (latest & historical) is uploaded daily on the Pension Fund's website within the prescribed time limit.
                        </p>
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_1" name='<portlet:namespace/>disclosure_1_1' value="Yes" <%=(mnCompCertificate.getScheme_wise()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_2" name='<portlet:namespace/>disclosure_1_1' value="No" <%=(mnCompCertificate.getScheme_wise()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_3" name='<portlet:namespace/>disclosure_1_1' value="NA" <%=(mnCompCertificate.getScheme_wise()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	   <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Disclosure_requirement_5_1a_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getDisclosure_requirements_5_1a_2():"" %></textarea>
                       <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Disclosure_requirement_5_1a"><%=mnCompCertificateScrutiny.getScheme_wise_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_wise_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p></p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(b)Whether scheme-wise NAV is submitted daily to all the CRA's within the prescribed time limit.
                        <br>Instances of delays during the month in uploading NAV on Pension Fund's website and submission to CRA with the reasons provided in Annexure I.
                        </p>
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_4" name='<portlet:namespace/>disclosure_1_2' value="Yes" <%=(mnCompCertificate.getScheme_wise_daily()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_5" name='<portlet:namespace/>disclosure_1_2' value="No" <%=(mnCompCertificate.getScheme_wise_daily()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_6" name='<portlet:namespace/>disclosure_1_2' value="NA" <%=(mnCompCertificate.getScheme_wise_daily()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Disclosure_requirement_5_1b_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getDisclosure_requirements_5_1b_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Disclosure_requirement_5_1b"><%=mnCompCertificateScrutiny.getScheme_wise_daily_rem() == null ? "" :  mnCompCertificateScrutiny.getScheme_wise_daily_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p></p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(c) Whether monthly periodic reports as per schedule V of PFRDA (PF) Regulations, 2015 and subsequent amendments are submitted to NPS Trust within 10 days from the end of the month.
                        </p>
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_7" name='<portlet:namespace/>disclosure_1_3' value="Yes" <%=(mnCompCertificate.getPeriodic_reports_monthly()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_8" name='<portlet:namespace/>disclosure_1_3' value="No" <%=(mnCompCertificate.getPeriodic_reports_monthly()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_9" name='<portlet:namespace/>disclosure_1_3' value="NA" <%=(mnCompCertificate.getPeriodic_reports_monthly()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Disclosure_requirement_5_1c_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getDisclosure_requirements_5_1c_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Disclosure_requirement_5_1c"><%=mnCompCertificateScrutiny.getPeriodic_reports_monthly_rem() == null ? "" :  mnCompCertificateScrutiny.getPeriodic_reports_monthly_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               
                 <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p></p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(d) Whether scrip wise details of portfolio of each scheme (excel file) is uploaded on the website, in the prescribed format, within 10 days from the end of the month.
                        </p>
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_10" name='<portlet:namespace/>disclosure_1_4' value="Yes" <%=(mnCompCertificate.getScrip_wise_details_portfolio()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_11" name='<portlet:namespace/>disclosure_1_4' value="No" <%=(mnCompCertificate.getScrip_wise_details_portfolio()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_12" name='<portlet:namespace/>disclosure_1_4' value="NA" <%=(mnCompCertificate.getScrip_wise_details_portfolio()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Disclosure_requirement_5_1d_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getDisclosure_requirements_5_1d_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Disclosure_requirement_5_1d"><%=mnCompCertificateScrutiny.getScrip_wise_details_pf_rem() == null ? "" :  mnCompCertificateScrutiny.getScrip_wise_details_pf_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               
               <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p></p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(e) Whether the pension fund has published on its website a list of its group companies and those of its sponsor.
                        </p>
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_13" name='<portlet:namespace/>disclosure_1_5' value="Yes" <%=(mnCompCertificate.getPension_fund_published()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_14" name='<portlet:namespace/>disclosure_1_5' value="No" <%=(mnCompCertificate.getPension_fund_published()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_15" name='<portlet:namespace/>disclosure_1_5' value="NA" <%=(mnCompCertificate.getPension_fund_published()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Disclosure_requirement_5_1e_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getDisclosure_requirements_5_1e_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Disclosure_requirement_5_1e"><%=mnCompCertificateScrutiny.getPension_fund_published_rem() == null ? "" :  mnCompCertificateScrutiny.getPension_fund_published_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               
                <div class="row">
                  <div class="col-md-1">
                     <div class="form-group">
                        <p></p>
                     </div>
                  </div>
                  <div class="col-md-5">
                     <div class="form-group">
                        <p>(f) Whether the pension fund has disclosed the scheme returns in the manner and in the format as available in public domain hosted by National Pension System Trust
                        </p>
                     </div>
                  </div>
                  <div class="col-md-2 pr-0">
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_16" name='<portlet:namespace/>disclosure_1_6' value="Yes" <%=(mnCompCertificate.getPension_fund_disclosed()).equals("Yes")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; Yes</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_17" name='<portlet:namespace/>disclosure_1_6' value="No" <%=(mnCompCertificate.getPension_fund_disclosed()).equals("No")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; No</label>
                     </div>
                     <div class="form-check form-check-inline">
                        <input type="radio" class="disclosure1" id="disclosure_18" name='<portlet:namespace/>disclosure_1_6' value="NA" <%=(mnCompCertificate.getPension_fund_disclosed()).equals("NA")?"checked":"" %>>
                        <label class="form-check-label">&nbsp; NA</label>
                     </div>
                  </div>
                    <div class="col-md-4">
                  	  <div class="form-check form-check-inline">
                  	  <textarea class="form-control mr-3"   placeholder="Remarks if any" name='<portlet:namespace/>Disclosure_requirement_5_1f_2' required> <%= (Validator.isNotNull(mnCompCertificate))? mnCompCertificate.getDisclosure_requirements_5_1f_2():"" %></textarea>
                      <textarea class="form-control" readonly="readonly"  placeholder="Remarks if any" name="Disclosure_requirement_5_1f"><%=mnCompCertificateScrutiny.getPension_fund_disclosed_rem() == null ? "" :  mnCompCertificateScrutiny.getPension_fund_disclosed_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               
               
               
               
               
               
               <strong>
                  <p>Note:</p>
               </strong>
               <br>
               <p>1) Wherever there is non-compliance due to any reason, the remark/reason thereof has been separately appended there to.</p>
               <div class="form-inline row">
                 <%--  <p class="ml-1">2) This Compliance Certificate(s) shall be put up to the Board at its meeting to be held on <input type="date" class="date_2 border-0" id="date_2" name="<portlet:namespace />date_2"> and the remarks related thereto, if any, would be forwarded to NPS Trust subsequently.</p> --%>
             	<p class="ml-1">2) This Compliance Certificate(s)n shall be put up to the Board on / has been put up to the Board on <input type="date" class="date_2 border-0" id="date_2" name="<portlet:namespace />date_2"></p>
               </div>
           
               <p>Certified that the information given, herein are correct and complete to the best of my knowledge and belief.</p>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <label for="companies">For</label><br>
                     <!--<select class="w-100" name='<portlet:namespace/>companies' id="companies">
                     	<option value="">Select</option>
                        <option value="Aditya Birla Sun Life Pension Management Limited">Aditya Birla Sun Life Pension Management Limited </option>
                        <option value="HDFC Pension Management Company Limited">HDFC Pension Management Company Limited </option>
                        <option value="ICICI Prudential Pension Funds Management Company Limited">ICICI Prudential Pension Funds Management Company Limited </option>
                        <option value="Kotak Mahindra Pension Fund Limited">Kotak Mahindra Pension Fund Limited </option>
                        <option value="LIC Pension Fund Limited">LIC Pension Fund Limited </option>
                        <option value="SBI Pension Funds Private Limited">SBI Pension Funds Private Limited </option>
                        <option value="UTI Retirement Solutions Limited">UTI Retirement Solutions Limited </option>
                     </select>-->
                     <input type="text" class="w-100" readonly="readonly" name='<portlet:namespace/>companies' value="<%=companies %>">
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <%-- <input type="text" class="employeeName border-0 p-2 w-100" id="employeeName" name="<portlet:namespace />employeeName"> --%>
                     <div class="nps-input-box mt-0">
                        <label>Name: </label>
                        <input type="text" class="employeeName" id="employeeName" name="<portlet:namespace />employeeName">
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                  	 <label for="roles">Role:</label><br>
                  	 <input type="text" name='<portlet:namespace/>roles' id="roles">
                     <%-- <select class="w-100" name='<portlet:namespace/>roles' id="roles">
                     	<option value="">Select</option>
                        <option value="Chief Executive Officer">Chief Executive Officer  </option>
                     </select> --%>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Date: </label>
                        <input type="text" class="date_3" id="date_3" name="<portlet:namespace />date_3" readonly>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box mt-0">
                        <label>Place: </label>
                        <input type="text" class="place" id="place" name="<portlet:namespace />place">
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexures to monthly compliance certificate</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile1">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureA_I" name="<portlet:namespace />annexureA_I" accept=".xlsx"/>
                           </div>
                           <label id="error1" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                    <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	 <textarea class=""   placeholder="Remarks if any" name="Document_Ai"><%=mnCompCertificateScrutiny.getAnnexure_a_i_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_a_i_rem()%></textarea>
                     </div>
                  </div>
               </div>
              <%--  <div class="row">
               		<div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexure A (ii): Breach in Investment guidelines - Securities held in portfolio. </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile2">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureA_II" name="<portlet:namespace />annexureA_II" accept=".xlsx"/>
                           </div>
                           <label id="error2" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                    <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	 <textarea class=""   placeholder="Remarks if any" name="AnnexureAii"><%=mnCompCertificateScrutiny.getAnnexure_a_ii_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_a_ii_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br> --%>
            <%--    <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexure B: Details of application/investment in IPO/FPO/OFS </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile3">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureB" name="<portlet:namespace />annexureB" accept=".xlsx"/>
                           </div>
                           <label id="error3" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                    <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	 <textarea class=""   placeholder="Remarks if any" name="AnnexureB"><%=mnCompCertificateScrutiny.getAnnexure_b_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_b_rem()%></textarea>
                     </div>
                  </div>
               </div> --%>
               <%-- <div class="row">
               	<div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexure C: Details of stop loss triggered during the month </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile4">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureC" name="<portlet:namespace />annexureC" accept=".xlsx"/>
                           </div>
                           <label id="error4" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                     <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	 <textarea class=""   placeholder="Remarks if any" name="AnnexureC"><%=mnCompCertificateScrutiny.getAnnexure_c_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_c_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexure D: Details of inter-scheme transfer of securities </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile5">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureD" name="<portlet:namespace />annexureD" accept=".xlsx"/>
                           </div>
                           <label id="error5" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                    <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	 <textarea class=""   placeholder="Remarks if any" name="AnnexureD"><%=mnCompCertificateScrutiny.getAnnexure_d_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_d_rem()%></textarea>
                     </div>
                  </div>
               </div> --%>
               <%-- <div class="row">
               	 <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexure E: Details of stocks held outside the list of top 200 stocks (pending for rebalancing/rebalanced during the month)</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile6">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureE" name="<portlet:namespace />annexureE" accept=".xlsx"/>
                           </div>
                           <label id="error6" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                    <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	 <textarea class=""   placeholder="Remarks if any" name="AnnexureE"><%=mnCompCertificateScrutiny.getAnnexure_e_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_e_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexure F: Details of sale of shares allotted in IPO/FPO/OFS where stock does not meet market capitalization condition post listing. </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile7">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureF" name="<portlet:namespace />annexureF" accept=".xlsx"/>
                           </div>
                           <label id="error7" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                    <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	 <textarea class=""   placeholder="Remarks if any" name="AnnexureF"><%=mnCompCertificateScrutiny.getAnnexure_f_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_f_rem()%></textarea>
                     </div>
                  </div>
               </div> --%>
              <%--  <div class="row">
               	   <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexure G: Instances of delays in uploading NAV on website and submission to CRA</label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile8">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureG" name="<portlet:namespace />annexureG" accept=".xlsx"/>
                           </div>
                           <label id="error8" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                    <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	 <textarea class=""   placeholder="Remarks if any" name="AnnexureG"><%=mnCompCertificateScrutiny.getAnnexure_g_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_g_rem()%></textarea>
                     </div>
                  </div>
               </div>
               <br>
               <div class="row">
                  <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                     <div class="nps-input-box file-upload">
                        <div class="nps-input-box mt-0">
                           <label>Annexure H: Segregation of Active and Passive deviations/breaches </label>
                           <div class="file-select">
                              <div class="file-select-name" id="noFile9">File Name</div>
                              <div class="file-select-button common-btn" id="fileName">Choose File</div>
                              <input type="file" class="annexure-upload" id="annexureH" name="<portlet:namespace />annexureH" accept=".xlsx"/>
                           </div>
                           <label id="error9" class="error-message text-danger"></label>
                           <br>
                        </div>
                     </div>
                  </div>
                     <div class="col-md-6 mt-5">
                  	  <div class="d-md-grid form-check form-check-inline">
                    	 <textarea class=""   placeholder="Remarks if any" name="AnnexureH"><%=mnCompCertificateScrutiny.getAnnexure_h_rem() == null ? "" :  mnCompCertificateScrutiny.getAnnexure_h_rem()%></textarea>
                     </div>
                  </div>
               </div>--%>
               <br> 
               <div class="row" id="sub">
	               <div class="row text-center">
	                  <div class="col-md-12">
	                     <input type="submit" class="common-btn d-inline-block text-light border-0 mt-3" id="btn-submit" value="Submit">
	                  </div>
	               </div>
	           </div>
            </form>
         </div>
      </div>
   </div>
</div>

<div class="animaion" style="display:none;">
   <div class="row">
      <div class="loading-animation"></div>
   </div>
</div>

<style>
	select {
    background-color: #E9F3FC;
    color: #111111;
    border-radius: 5px;
    padding: 5px 20px;
}

.modal {
    display: none;
}

.modal-dialog {
	margin: 5% 40%;
}

.modal-content {
	width: 20vw;
}
</style>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	$("textarea").each(function () {
		this.setAttribute("style", "height:" + (this.scrollHeight) + "px;overflow-y:hidden;");
	}).on("input", function () {
		this.style.height = 0;
		this.style.height = (this.scrollHeight) + "px";
	});
	toastr.options = {
		  "debug": false,
		  "positionClass": "toast-bottom-right",
		  "onclick": null,
		  "fadeIn": 300,
		  "fadeOut": 1000,
		  //"timeOut": 6000,
		  "extendedTimeOut": 1000
		}
	
	var currentDate  = new Date(),
    currentDay   = currentDate.getDate() < 10 
                 ? '0' + currentDate.getDate() 
                 : currentDate.getDate(),
    currentMonth = currentDate.getMonth() < 9 
                 ? '0' + (currentDate.getMonth() + 1) 
                 : (currentDate.getMonth() + 1);

	document.getElementById("date_3").value = currentDay + '-' + currentMonth + '-' +  currentDate.getFullYear();
	
	/* render file name in choose file starts */
	$('#annexureA_I').bind('change', function () {
		  var filename = $("#annexureA_I").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile1").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile1").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	/* $('#annexureA_II').bind('change', function () {
		  var filename = $("#annexureA_II").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile2").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile2").text(filename.replace("C:\\fakepath\\", ""));
		  }
		}); */
	
/* 	$('#annexureB').bind('change', function () {
		  var filename = $("#annexureB").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile3").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile3").text(filename.replace("C:\\fakepath\\", ""));
		  }
		}); */
	
/* 	$('#annexureC').bind('change', function () {
		  var filename = $("#annexureC").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile4").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile4").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#annexureD').bind('change', function () {
		  var filename = $("#annexureD").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile5").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile5").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#annexureE').bind('change', function () {
		  var filename = $("#annexureE").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile6").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile6").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	 */
	/* $('#annexureF').bind('change', function () {
		  var filename = $("#annexureF").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile7").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile7").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#annexureG').bind('change', function () {
		  var filename = $("#annexureG").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile8").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile8").text(filename.replace("C:\\fakepath\\", ""));
		  }
		});
	
	$('#annexureH').bind('change', function () {
		  var filename = $("#annexureH").val();
		  if (/^\s*$/.test(filename)) {
		     $(".file-upload").removeClass('active');
		     $("#noFile9").text("No file chosen...");
		  }
		  else {
		     $(".file-upload").addClass('active');
		     $("#noFile9").text(filename.replace("C:\\fakepath\\", ""));
		  }
		}); */
	/* render file name in choose file ends */
	
	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-zA-Z ]+$/i.test(value);
		}, "Please enter characters only"); 
	
	$.validator.addMethod('annexureA_I_validateFileName', function(value, element) {
			console.log("Entered in method");
			console.log("Value= ", value);
			console.log("Element= ", element);
			
			let filename = 'Annexures to monthly compliance certificate.xlsx';
		   return this.optional(element) || (value === filename); 
		}, "Please upload Annexures to monthly compliance certificate.xlsx");
	
	/* $.validator.addMethod('annexureA_II_validateFileName', function(value, element) {
		let filename = 'Annexure A (ii) Breach in Investment guidelines - Securities held in portfolio.xlsx';
	   return this.optional(element) || (value === filename); 
	}, "Please upload Annexure A (ii) Breach in Investment guidelines - Securities held in portfolio.xlsx");
	
	$.validator.addMethod('annexureB_validateFileName', function(value, element) {
		let filename = 'Annexure B Details of applicationinvestment in IPOFPOOFS.xlsx';
	   return this.optional(element) || (value === filename); 
	}, "Please upload Annexure B Details of applicationinvestment in IPOFPOOFS.xlsx");
	
	$.validator.addMethod('annexureC_validateFileName', function(value, element) {
		let filename = 'Annexure C Details of stop loss triggered during the month.xlsx';
	   return this.optional(element) || (value === filename); 
	}, "Please upload Annexure C Details of stop loss triggered during the month.xlsx");
	
	$.validator.addMethod('annexureD_validateFileName', function(value, element) {
		let filename = 'Annexure D Details of inter-scheme transfer of securities.xlsx';
	   return this.optional(element) || (value === filename); 
	}, "Please upload Annexure D Details of inter-scheme transfer of securities.xlsx");
	
	$.validator.addMethod('annexureE_validateFileName', function(value, element) {
		let filename = 'Annexure E Details of stocks held outside the list of top 200 stocks (pending for rebalancingrebalanced during the month).xlsx';
	   return this.optional(element) || (value === filename); 
	}, "Please upload Annexure E Details of stocks held outside the list of top 200 stocks (pending for rebalancingrebalanced during the month).xlsx");
	
	$.validator.addMethod('annexureF_validateFileName', function(value, element) {
		let filename = 'Annexure F Details of sale of shares allotted in IPOFPOOFS where stock does not meet market capitalization condition post listing.xlsx';
	   return this.optional(element) || (value === filename); 
	}, "Please upload Annexure F Details of sale of shares allotted in IPOFPOOFS where stock does not meet market capitalization condition post listing.xlsx");
	
	$.validator.addMethod('annexureG_validateFileName', function(value, element) {
		let filename = 'Annexure G Instances of delays in uploading NAV on website and submission to CRA.xlsx';
	   return this.optional(element) || (value === filename); 
	}, "Please upload Annexure G Instances of delays in uploading NAV on website and submission to CRA.xlsx");
	
	$.validator.addMethod('annexureH_validateFileName', function(value, element) {
		let filename = 'Annexure H Segregation of Active and Passive deviationsbreaches.xlsx';
	   return this.optional(element) || (value === filename); 
	}, "Please upload Annexure H Segregation of Active and Passive deviationsbreaches.xlsx"); */
	
	
	 $("#monthlyCompCertificate").validate({
	  	rules: {
		<portlet:namespace/>date_1: {
	      	required: true
	    },
	     <portlet:namespace/>date_2: {
		    required: true
		}, 
		<portlet:namespace/>companies:{
			required: true
		},
		<portlet:namespace/>employeeName:{
			required: true,
			lettersonly: true
		},
		<portlet:namespace/>roles:{
			required: true
		},
		<portlet:namespace/>place:{
			required: true,
			lettersonly: true
		},
		<portlet:namespace/>annexureA_I:{
			required: true, 
			annexureA_I_validateFileName: true
		},
		/* <portlet:namespace/>annexureA_II:{
			required: true,
			annexureA_II_validateFileName: true
		},
		<portlet:namespace/>annexureB:{
			required: true,
			annexureB_validateFileName: true
		},
		<portlet:namespace/>annexureC:{
			required: true,
			annexureC_validateFileName: true
		},
		<portlet:namespace/>annexureD:{
			required: true,
			annexureD_validateFileName: true
		},
		<portlet:namespace/>annexureE:{
			required: true,
			annexureE_validateFileName: true
		},
		<portlet:namespace/>annexureF:{
			required: true,
			annexureF_validateFileName: true
		},
		<portlet:namespace/>annexureG:{
			required: true,
			annexureG_validateFileName: true
		},
		<portlet:namespace/>annexureH:{
			required: true,
			annexureH_validateFileName: true
		},									
		 */
	  },
	  errorPlacement: function (error, element) {
         if (element.attr("name") == "<portlet:namespace />annexureA_I") {
              error.appendTo("#error1");
         /*  } else if (element.attr("name") == "<portlet:namespace />annexureA_II") {
              error.appendTo("#error2");
          } else if (element.attr("name") == "<portlet:namespace />annexureB") {
              error.appendTo("#error3");
          } else if (element.attr("name") == "<portlet:namespace />annexureC") {
              error.appendTo("#error4");
          } else if (element.attr("name") == "<portlet:namespace />annexureD") {
              error.appendTo("#error5");
          } else if (element.attr("name") == "<portlet:namespace />annexureE") {
              error.appendTo("#error6");
          } else if (element.attr("name") == "<portlet:namespace />annexureF") {
              error.appendTo("#error7");
          } else if (element.attr("name") == "<portlet:namespace />annexureG") {
              error.appendTo("#error8");
          } else if (element.attr("name") == "<portlet:namespace />annexureH") {
              error.appendTo("#error9"); */
          
          } else {
        	  error.appendTo(element.parent().parent().after());
          }
      },

	}); 
	
	 $('.purchase1').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.purchase2').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.purchase3').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.purchase4').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.securities1').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.securities2').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.securities3').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.securities4').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.securities5').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.securities6').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.sale1').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.sale2').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
	$('.reports1').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    });
	
/*	$('.reports2').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
*/	
	$('.disclosure1').each(function() {
        $(this).rules("add", 
            {
                required: true,
                messages: {
                    required: "This field is required.",
                }
            }
        );
    }); 
	
	$("#monthlyCompCertificate").on('submit', function(e) {
    	
		e.preventDefault();
		
		/* if ($('input[name="annualreport"]').get(0).files.length === 0) {
    	    $("#error1").html("Please select a file to upload");
    	    return false;
    	}
    	else if($('input[name="annualreport"]').get(0).files[0].name != "Annual Audit Report.pdf"){
    		$("#error1").html("Please upload Annual Audit Report.pdf");
    	    return false;
    	} */
    	
    	/* if ($('input[name="<portlet:namespace/>annexureA_I"]').get(0).files.length === 0) {
    	    $("#error1").html("Please select a file to upload");
    	    return false;
    	}else if($('input[name="<portlet:namespace/>annexureA_I"]').get(0).files[0].name != "Annexure A (i): Breach in Investment guidelines - Securities Purchased.xlsx"){
			console.log("No files selected.");
			$("#error1").html("Please upload Annexure A (i): Breach in Investment guidelines - Securities Purchased.xlsx");
		    return false;
		} else {
			$("#error1").html('');
		} */
		
    	
    	
		
    		
    		if($( "#monthlyCompCertificate" ).valid()){
    			
    			$("#sub").addClass("hide");
    			
    			var formData = new FormData($("#monthlyCompCertificate")[0]);
   				
   				/* var htmlWidth = $("#canvasD").width();
				var htmlHeight = $("#canvasD").height();
				var pdfWidth = htmlWidth + (15 * 2);
				var pdfHeight = (pdfWidth * 1.5) + (15 * 2);
				var doc = new jsPDF('p', 'pt', [pdfWidth, pdfHeight]);

				var pageCount = Math.ceil(htmlHeight / pdfHeight) - 1;


				html2canvas($("#canvasD")[0], { allowTaint: true }).then(function(canvas) {
					canvas.getContext('2d');

					var image = canvas.toDataURL("image/jpeg", 1.0);
					doc.addImage(image, 'JPEG', 15, 15, htmlWidth, htmlHeight);


					for (var i = 1; i <= pageCount; i++) {
						doc.addPage(pdfWidth, pdfHeight);
						doc.addImage(image, 'JPEG', 15, -(pdfHeight * i)+15, htmlWidth, htmlHeight);
					}

					console.log("doc.output('bloburl') ::: ", doc.output('arraybuffer')); */
	/* 				doc.save("split-to-multi-page.pdf");
				     window.open(doc.output('bloburl')); */
				     
					/* var file = new Blob([new Uint8Array(doc.output('arraybuffer'))], {type: 'application/pdf'});
				     
					console.log("file::: ", file);
					
					formData.append("monthly_comp_certificate", file); */
   				
   				$(".nps-report").hide();
   				$(".animaion").show();
   				$.ajax({
   		            type: "POST",
   		            enctype: 'multipart/form-data',
   		            processData: false,
   		            contentType: false,
   		            cache: false,
   		            url: '${mnCompCertificateResourceURL}',
   		            data: formData,
   		            success: function(data) {
   		            	$(".nps-report").show();
   		            	$(".animaion").hide();
   		            	$('#success_tic').modal('show');
   		            	$("#monthlyCompCertificate")[0].reset();
   		             	$('#success_tic').on('hidden.bs.modal', function (e) {
   		             		location.reload(); 
   		           		})
   		            },
   		            error: function() {
   		            	$(".animaion").hide();
	            		$('#output').html('An error occured while submitting the form. Please try again');
	            		$('#errorExcel').modal('show');
   		            	console.log("Error Occured in ajax call");
   		            },
	   		         complete: function(){
			            	$(".animaion").hide();
							console.log("Complete");
			            	if($("#sub").hasClass("hide")){
				            	$("#sub").removeClass("hide");
			            	}
				        }
			        }); 
	
				/* }); */
		    	
	 		}
		});
	//});
});

</script>


<portlet:resourceURL var="autoSaveResourceURL" id="<%=FormsPortletsKeyConstants.FORM_AUTOSAVE_COMMAND %>"/>
<script type="text/javascript">
            var autoSaveId="";
            $(document).ready(function(){
            	autoSaveId="<%=reportuploadlogId%>";
            	
            	  $("input[name=<portlet:namespace/>purchase1]").change(function(){
            	    var name=$("input[name=<portlet:namespace/>purchase1]:checked").val();
            	    console.log("name"+name)
            	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PURCHASE_OF_SECURITIES%>",name);
            	  });
            	  
            	  $("input[name=<portlet:namespace/>purchase2_1]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>purchase2_1]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DETAILED_INVESTMENT%>",name);
              	  });
            	  
            	  $("input[name=<portlet:namespace/>purchase2_2]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>purchase2_2]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.INVESTMENTS_APPROVED%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>purchase2_3]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>purchase2_3]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DECISION_OF_INVESTMENT%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>purchase2_4]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>purchase2_4]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.INVESTMENTS_NON_DEMATERIALIZED%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>purchase2_5]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>purchase2_5]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.ALL_INVESTMENTS_FROM_FUNDS%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>purchase3]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>purchase3]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DELIVERY_OF_SECURITIES_PURCH%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>purchase4]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>purchase4]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.INVESTMENT_DONE_IN_IPO%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>securities1]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>securities1]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SCHEME_INVESTMENTS%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>securities2_1]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>securities2_1]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.STOP_LOSS_TRIGGER%>",name);
              	  });
            	  
            	  $("input[name=<portlet:namespace/>securities2_2]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>securities2_2]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DECISION_APPROVED_BY_COMMITTEE%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>securities2_3]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>securities2_3]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DECISION_PROPERLY_DOCUMENTED%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>securities3]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>securities3]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.INTER_SCHEME_TRANSFER%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>securities4]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>securities4]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.INVESTMENT_HELD_IN_EQUITY%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>securities5]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>securities5]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.INVESTMENT_IN_EQUITY_SHARES%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>securities6]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>securities6]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.ACTIVE_PASSIVE_BREACHES%>",name);
              	  }); 
            	  $("input[name=<portlet:namespace/>sale1_1]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>sale1_1]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DISINVESTMENTS_APPROVED%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>sale1_2]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>sale1_2]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DECISION_OF_DISINVESTMENT%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>sale2]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>sale2]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DELIVERY_OF_SECURITIES_SALE%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>reports1_1]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>reports1_1]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.ALL_INVESTMENT_CHARGES%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>reports1_2]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>reports1_2]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PFM_ADHERED%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>reports1_3]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>reports1_3]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.RECORDS_OF_THE_AUDIT_OF_NAV%>",name);
              	  });
            	  
            	  $("input[name=<portlet:namespace/>reports1_4]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>reports1_4]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SCHEME_WISE_NAV_UPLOADED%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>reports1_5]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>reports1_5]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SCHEME_WISE_NAV_SUBMITTED%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>reports2_1]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>reports2_1]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.MONTHLY_REPORTS_SUBMITTED%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>reports2_2]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>reports2_2]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SCRIP_WISE_DETAILS%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>disclosure_1_1]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>disclosure_1_1]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SCHEME_WISE%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>disclosure_1_2]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>disclosure_1_2]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SCHEME_WISE_DAILY%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>disclosure_1_3]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>disclosure_1_3]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PERIODIC_REPORTS_MONTHLY%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>disclosure_1_4]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>disclosure_1_4]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SCRIP_WISE_DETAILS_PORTFOLIO%>",name);
              	  });
            	  $("input[name=<portlet:namespace/>disclosure_1_5]").change(function(){
                	    var name=$("input[name=<portlet:namespace/>disclosure_1_5]:checked").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PENSION_FUND_PUBLISHED%>",name);
                	  });
            	  $("input[name=<portlet:namespace/>disclosure_1_6]").change(function(){
              	    var name=$("input[name=<portlet:namespace/>disclosure_1_6]:checked").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PENSION_FUND_DISCLOSED%>",name);
              	  });
	  
            	  $("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_1_2]").change(function(){
                	    var name=$("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_1_2]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PURCHASE_OF_SEC_REM_1_1_2%>",name);
                	  });
            	  $("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_2a_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_2a_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PURCHASE_OF_SEC_REM_1_2A_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_2b_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_2b_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PURCHASE_OF_SEC_REM_1_2B_2%>",name);
              	  });
            	  
            	  $("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_2c_2]").change(function(){
                	    var name=$("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_2c_2]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PURCHASE_OF_SEC_REM_1_2C_2%>",name);
                	  });
            	  
            	  $("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_2d_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_2d_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PURCHASE_OF_SEC_REM_1_2D_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_2e_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_2e_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PURCHASE_OF_SEC_REM_1_2E_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_3_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_3_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PURCHASE_OF_SEC_REM_1_3_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_4_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Purchase_of_securitiesremarks_1_4_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.PURCHASE_OF_SEC_REM_1_4_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Securities_held_2_1_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Securities_held_2_1_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SECURITIES_HELD_2_1_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Securities_held_2_2a_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Securities_held_2_2a_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SECURITIES_HELD_2_2A_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Securities_held_2_2b_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Securities_held_2_2b_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SECURITIES_HELD_2_2B_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Securities_held_2_2c_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Securities_held_2_2c_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SECURITIES_HELD_2_2C_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Securities_held_2_3_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Securities_held_2_3_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SECURITIES_HELD_2_3_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Securities_held_2_4_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Securities_held_2_4_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SECURITIES_HELD_2_4_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Securities_held_2_5_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Securities_held_2_5_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SECURITIES_HELD_2_5_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Securities_held_2_6_2]").change(function(){
                	    var name=$("textarea[name=<portlet:namespace/>Securities_held_2_6_2]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SECURITIES_HELD_2_6_2%>",name);
                	  });
            	  $("textarea[name=<portlet:namespace/>Sale_of_securities_3_1a_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Sale_of_securities_3_1a_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SALE_OF_SECURITIES_3_1A_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Sale_of_securities_3_1b_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Sale_of_securities_3_1b_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SALE_OF_SECURITIES_3_1B_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Sale_of_securities_3_2_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Sale_of_securities_3_2_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.SALE_OF_SECURITIES_3_2_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_1a_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_1a_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.REPORTS_AND_DISCLOSURE_4_1A_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_1b_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_1b_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.REPORTS_AND_DISCLOSURE_4_1B_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_1c_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_1c_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.REPORTS_AND_DISCLOSURE_4_1C_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_1d_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_1d_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.REPORTS_AND_DISCLOSURE_4_1D_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_1e_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_1e_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.REPORTS_AND_DISCLOSURE_4_1E_2%>",name);
              	  });
            <%--	  $("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_2a_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_2a_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.REPORTS_AND_DISCLOSURE_4_2A_2%>",name);
              	  });
            	  $("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_2b_2]").change(function(){
              	    var name=$("textarea[name=<portlet:namespace/>Reports_and_Disclosures_4_2b_2]").val();
              	    console.log("name"+name)
              	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.REPORTS_AND_DISCLOSURE_4_2B_2%>",name);
              	  });
            	--%>
            	 $("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1a_2]").change(function(){
               	    var name=$("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1a_2]").val();
               	    console.log("name"+name)
               	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DISCLOSURE_REQUIREMENTS_5_1A_2%>",name);
               	  });
            	 $("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1b_2]").change(function(){
                	    var name=$("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1b_2]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DISCLOSURE_REQUIREMENTS_5_1B_2%>",name);
                	  });
            	 $("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1c_2]").change(function(){
                	    var name=$("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1c_2]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DISCLOSURE_REQUIREMENTS_5_1C_2%>",name);
                	  });
             	 $("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1d_2]").change(function(){
                 	    var name=$("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1d_2]").val();
                 	    console.log("name"+name)
                 	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DISCLOSURE_REQUIREMENTS_5_1D_2%>",name);
                 	  });
             	 $("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1e_2]").change(function(){
                	    var name=$("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1e_2]").val();
                	    console.log("name"+name)
                	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DISCLOSURE_REQUIREMENTS_5_1E_2%>",name);
                	  });
             	 $("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1f_2]").change(function(){
                 	    var name=$("textarea[name=<portlet:namespace/>Disclosure_requirement_5_1f_2]").val();
                 	    console.log("name"+name)
                 	    autoSave("<%=AutoSaveConstants.FIELD_TYPE_STRING%>","<%=MonthlyCompFormConstants.DISCLOSURE_REQUIREMENTS_5_1F_2%>",name);
                 	  });
             	  
             	  
             	  

            });

            function autoSave(fieldType,fieldName,fieldValue) {
console.log("autoSaveId : "+autoSaveId)
            	const formData = new FormData();
            	  formData.append('<portlet:namespace /><%=AutoSaveConstants.FIELD_TYPE%>', fieldType);
            	  formData.append('<portlet:namespace /><%=AutoSaveConstants.FIELD_NAME%>', fieldName);
            	  formData.append('<portlet:namespace /><%=AutoSaveConstants.FIELD_VALUE%>', fieldValue);
            	  formData.append('<portlet:namespace /><%=AutoSaveConstants.REPORT_UPLOAD_LOG_ID%>', autoSaveId); 

            	  const xhr = new XMLHttpRequest();
            	  xhr.open('POST', '<%=autoSaveResourceURL%>', true);

            	  xhr.onload = function () {
            	    if (xhr.status === 200) {
            	    	
            	    } else {
            	      //document.getElementById('uploadStatus').textContent = 'File upload failed.';
            	    }
            	  };

            	  xhr.send(formData);
            	}
            </script>
