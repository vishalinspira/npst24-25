<%@page import="CGMS.Agenda.constants.CGMSAgendaPortletKeys"%>
<%@ include file="/init.jsp" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>

<portlet:resourceURL id="<%=CGMSAgendaPortletKeys.agenda%>" var="cgmsAgendaResourceURL" />

    <div class="card_blocks">
        <div class="row">
            <div class="col">
                <div class="page_title">
                    <img src="<%=request.getContextPath()%>/images/contact-form-title.png" alt="Upload Reports" /> MEMORANDUM TO THE BOARD OF NPS TRUST
                </div>
            </div>
        </div>

        <div class="form_block mx-4">
        	<form class="form" id="bmcgmsAgenda" action="#" method="post">
	            <div class="form-inline row mb-4">
	                <div class="col-12">
	                    <p class="mb-3"><strong>F. No.</strong><input class="form-control" type="text" id="fileNumber" name="<portlet:namespace />fileNumber"/></p>
	                </div>
	            </div>

	            <div class="form-inline row text-center mb-5">
	                <div class="col-12">
	                    <p class="mb-3">एन पी एस ट्रस्ट बोर्ड के लिए मेमोरंडम</p>
	                    <p class="mb-3"><strong>MEMORANDUM TO THE BOARD OF NPS TRUST</strong></p>
	                    <p class="mb-3"><strong>बी संख्या  /B. No. </strong><input type="text" class="form-control" id="bNumber" name="<portlet:namespace />bNumber"></p>
	                    <p class="mb-3"><strong>आइटम क्रमांक  / Item No. </strong> <input type="text" class="form-control" id="itemNumber" name="<portlet:namespace />itemNumber"></p>
	                </div>
	            </div>
	
	            <div class="form-inline row text-center mb-5">
	                <div class="col-12">
	                    <p class="mb-3"><strong>Subscriber & other complaints report for the quarter ended</strong> <input class="form-control" type="text" id="date_1" name="<portlet:namespace />date_1"/></p>
	                </div>
	            </div>

	            <div class="table_one">
	                <div class="form-inline row mb-5">
	                    <div class="col-12">
	                        <p class="mb-3">
	                            1. NCRA and KCRA have submitted the data on Central Grievance Management System (CGMS) for the quarter ended <input class="form-control" type="text" id="date_2" name="<portlet:namespace />date_2"/> 2021 in the format approved by the Board of Trustees of NPS Trust. The executive summary of combined data of grievances handled in CGMS
	                            of both the CRAs (which includes NPS, APY and NPS-Lite grievances in NSDL CGMS, and NPS grievances in Kfintech CGMS) for the quarter ended
	                            <input class="form-control" type="text" id="date_3" name="<portlet:namespace />date_3"/> 2021 is as follows:
	                        </p>
	                    </div>
	                </div>
		
	                <div class="row">
	                    <div class="col-12">
	                        <table class="table table-bordered tables w-100 mb-3" id="table1">
	                            <tr>
	                                <td colspan=8 class="text-center">
	                                    <strong>TABLE 1 : Summary of overall grievances handled in CGMS of KCRA & NCRA (NPS + NPS LITE & APY)</strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td rowspan=2>
	                                    <strong>Sl. No</strong>
	                                </td>
	                                <td rowspan=2>
	                                    <strong>Entities</strong>
	                                </td>
	                                <td>
	                                    <strong>Opening balance</strong>
	                                </td>
	                                <td>
	                                    <strong>Escalated to NPST out of opening balance</strong>
	                                </td>
	                                <td><strong>Grievances received during the quarter</strong>
	                                </td>
	                                <td><strong>Grievances assigned / reviewed by NPST</strong>
	                                </td>
	                                <td><strong>Grievances resolved during the quarter</strong>
	                                </td>
	                                <td><strong>Outstanding grievances as at end of the quarter</strong>
	                                </td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>(A)</strong></td>
	                                <td><strong>(B)</strong></td>
	                                <td><strong>(C)</strong></td>
	                                <td><strong>(D)</strong></td>
	                                <td><strong>(E)</strong></td>
	                                <td><strong>(F=A+B+C+D-E)</strong></td>
	                            </tr>
	
	                            <tr>
								    <td>1</td>
								    <td>CG Nodal Office</td>
								    <td><input type="number" class="form-control cg column_input_a" id="opening_balance_0" name="<portlet:namespace />opening_balance_0"></td>
								    <td><input type="number" class="form-control cg column_input_b" id="escalated_to_npst_0" name="<portlet:namespace />escalated_to_npst_0"></td>
								    <td><input type="number" class="form-control cg column_input_c" id="grievances_received_0" name="<portlet:namespace />grievances_received_0"></td>
								    <td><input type="number" class="form-control cg column_input_d" id="grievances_assigned_0" name="<portlet:namespace />grievances_assigned_0"></td>
								    <td><input type="number" class="form-control cg column_input_e" id="grievances_resolved_0" name="<portlet:namespace />grievances_resolved_0"></td>
								    <td><input type="number" class="form-control cg column_input_f" id="outstanding_grievances_0" name="<portlet:namespace />outstanding_grievances_0"></td>
								</tr>
	
	                            <tr>
	                                <td>2</td>
	                                <td>SG Nodal Office</td>
	                                <td><input type="number" class="form-control sg column_input_a" id="opening_balance_1" name="<portlet:namespace />opening_balance_1"></td>
								    <td><input type="number" class="form-control sg column_input_b" id="escalated_to_npst_1" name="<portlet:namespace />escalated_to_npst_1"></td>
								    <td><input type="number" class="form-control sg column_input_c" id="grievances_received_1" name="<portlet:namespace />grievances_received_1"></td>
								    <td><input type="number" class="form-control sg column_input_d" id="grievances_assigned_1" name="<portlet:namespace />grievances_assigned_1"></td>
								    <td><input type="number" class="form-control sg column_input_e" id="grievances_resolved_1" name="<portlet:namespace />grievances_resolved_1"></td>
								    <td><input type="number" class="form-control sg column_input_f" id="outstanding_grievances_1" name="<portlet:namespace />outstanding_grievances_1"></td>
	                            </tr>
	
	                            <tr>
	                                <td>3</td>
	                                <td>PoP</td>
	                                <td><input type="number" class="form-control pop column_input_a" id="opening_balance_2" name="<portlet:namespace />opening_balance_2"></td>
								    <td><input type="number" class="form-control pop column_input_b" id="escalated_to_npst_2" name="<portlet:namespace />escalated_to_npst_2"></td>
								    <td><input type="number" class="form-control pop column_input_c" id="grievances_received_2" name="<portlet:namespace />grievances_received_2"></td>
								    <td><input type="number" class="form-control pop column_input_d" id="grievances_assigned_2" name="<portlet:namespace />grievances_assigned_2"></td>
								    <td><input type="number" class="form-control pop column_input_e" id="grievances_resolved_2" name="<portlet:namespace />grievances_resolved_2"></td>
								    <td><input type="number" class="form-control pop column_input_f" id="outstanding_grievances_2" name="<portlet:namespace />outstanding_grievances_2"></td>
	                            </tr>
	
	                            <tr>
	                                <td>4</td>
	                                <td>Corporate Nodal Office</td>
	                                <td><input type="number" class="form-control corporate column_input_a" id="opening_balance_3" name="<portlet:namespace />opening_balance_3"></td>
								    <td><input type="number" class="form-control corporate column_input_b" id="escalated_to_npst_3" name="<portlet:namespace />escalated_to_npst_3"></td>
								    <td><input type="number" class="form-control corporate column_input_c" id="grievances_received_3" name="<portlet:namespace />grievances_received_3"></td>
								    <td><input type="number" class="form-control corporate column_input_d" id="grievances_assigned_3" name="<portlet:namespace />grievances_assigned_3"></td>
								    <td><input type="number" class="form-control corporate column_input_e" id="grievances_resolved_3" name="<portlet:namespace />grievances_resolved_3"></td>
								    <td><input type="number" class="form-control corporate column_input_f" id="outstanding_grievances_3" name="<portlet:namespace />outstanding_grievances_3"></td>
	                            </tr>
	
	                            <tr>
	                                <td>5</td>
	                                <td>CRA</td>
	                                <td><input type="number" class="form-control cra column_input_a" id="opening_balance_4" name="<portlet:namespace />opening_balance_4"></td>
								    <td><input type="number" class="form-control cra column_input_b" id="escalated_to_npst_4" name="<portlet:namespace />escalated_to_npst_4"></td>
								    <td><input type="number" class="form-control cra column_input_c" id="grievances_received_4" name="<portlet:namespace />grievances_received_4"></td>
								    <td><input type="number" class="form-control cra column_input_d" id="grievances_assigned_4" name="<portlet:namespace />grievances_assigned_4"></td>
								    <td><input type="number" class="form-control cra column_input_e" id="grievances_resolved_4" name="<portlet:namespace />grievances_resolved_4"></td>
								    <td><input type="number" class="form-control cra column_input_f" id="outstanding_grievances_4" name="<portlet:namespace />outstanding_grievances_4"></td>
	                            </tr>
	
	                            <tr>
	                                <td>6</td>
	                                <td>Trustee Bank</td> 
	                                <td><input type="number" class="form-control tb column_input_a" id="opening_balance_5" name="<portlet:namespace />opening_balance_5"></td>
								    <td><input type="number" class="form-control tb column_input_b" id="escalated_to_npst_5" name="<portlet:namespace />escalated_to_npst_5"></td>
								    <td><input type="number" class="form-control tb column_input_c" id="grievances_received_5" name="<portlet:namespace />grievances_received_5"></td>
								    <td><input type="number" class="form-control tb column_input_d" id="grievances_assigned_5" name="<portlet:namespace />grievances_assigned_5"></td>
								    <td><input type="number" class="form-control tb column_input_e" id="grievances_resolved_5" name="<portlet:namespace />grievances_resolved_5"></td>
								    <td><input type="number" class="form-control tb column_input_f" id="outstanding_grievances_5" name="<portlet:namespace />outstanding_grievances_5"></td>
	                            </tr>
	
	                            <tr>
	                                <td>7</td>
	                                <td>NPS Trust</td>
	                                <td><input type="number" class="form-control nps column_input_a" id="opening_balance_6" name="<portlet:namespace />opening_balance_6"></td>
								    <td><input type="number" class="form-control nps column_input_b" id="escalated_to_npst_6" name="<portlet:namespace />escalated_to_npst_6"></td>
								    <td><input type="number" class="form-control nps column_input_c" id="grievances_received_6" name="<portlet:namespace />grievances_received_6"></td>
								    <td><input type="number" class="form-control nps column_input_d" id="grievances_assigned_6" name="<portlet:namespace />grievances_assigned_6"></td>
								    <td><input type="number" class="form-control nps column_input_e" id="grievances_resolved_6" name="<portlet:namespace />grievances_resolved_6"></td>
								    <td><input type="number" class="form-control nps column_input_f" id="outstanding_grievances_6" name="<portlet:namespace />outstanding_grievances_6"></td>
	                            </tr>
	
	                            <tr>
	                                <td>8</td>
	                                <td>Other (e-NPS)</td>
	                               	<td><input type="number" class="form-control other column_input_a" id="opening_balance_7" name="<portlet:namespace />opening_balance_7"></td>
								    <td><input type="number" class="form-control other column_input_b" id="escalated_to_npst_7" name="<portlet:namespace />escalated_to_npst_7"></td>
								    <td><input type="number" class="form-control other column_input_c" id="grievances_received_7" name="<portlet:namespace />grievances_received_7"></td>
								    <td><input type="number" class="form-control other column_input_d" id="grievances_assigned_7" name="<portlet:namespace />grievances_assigned_7"></td>
								    <td><input type="number" class="form-control other column_input_e" id="grievances_resolved_7" name="<portlet:namespace />grievances_resolved_7"></td>
								    <td><input type="number" class="form-control other column_input_f" id="outstanding_grievances_7" name="<portlet:namespace />outstanding_grievances_7"></td>
	                            </tr>
	
	                            <tr>
	                                <td>9</td>
	                                <td>NPS Lite (NLAO/NLCC)</td>
	                                <td><input type="number" class="form-control npsLite column_input_a" id="opening_balance_8" name="<portlet:namespace />opening_balance_8"></td>
								    <td><input type="number" class="form-control npsLite column_input_b" id="escalated_to_npst_8" name="<portlet:namespace />escalated_to_npst_8"></td>
								    <td><input type="number" class="form-control npsLite column_input_c" id="grievances_received_8" name="<portlet:namespace />grievances_received_8"></td>
								    <td><input type="number" class="form-control npsLite column_input_d" id="grievances_assigned_8" name="<portlet:namespace />grievances_assigned_8"></td>
								    <td><input type="number" class="form-control npsLite column_input_e" id="grievances_resolved_8" name="<portlet:namespace />grievances_resolved_8"></td>
								    <td><input type="number" class="form-control npsLite column_input_f" id="outstanding_grievances_8" name="<portlet:namespace />outstanding_grievances_8"></td>
	                            </tr>
	
	                            <tr>
	                                <td>10</td>
	                                <td>APY- SP</td>
	                                <td><input type="number" class="form-control apy column_input_a" id="opening_balance_9" name="<portlet:namespace />opening_balance_9"></td>
								    <td><input type="number" class="form-control apy column_input_b" id="escalated_to_npst_9" name="<portlet:namespace />escalated_to_npst_9"></td>
								    <td><input type="number" class="form-control apy column_input_c" id="grievances_received_9" name="<portlet:namespace />grievances_received_9"></td>
								    <td><input type="number" class="form-control apy column_input_d" id="grievances_assigned_9" name="<portlet:namespace />grievances_assigned_9"></td>
								    <td><input type="number" class="form-control apy column_input_e" id="grievances_resolved_9" name="<portlet:namespace />grievances_resolved_9"></td>
								    <td><input type="number" class="form-control apy column_input_f" id="outstanding_grievances_9" name="<portlet:namespace />outstanding_grievances_9"></td>
	                            </tr>
	
	                            <tr>
	                                <td colspan=2><strong>Quarter<input class="form-control date_4" type="text" id="date_4" name="<portlet:namespace />date_4"/></strong></td>
	                                <td><input type="text" class="form-control quarter" id="opening_balance_10" name="<portlet:namespace />opening_balance_10" readonly></td>
								    <td><input type="text" class="form-control quarter" id="escalated_to_npst_10" name="<portlet:namespace />escalated_to_npst_10" readonly></td>
								    <td><input type="text" class="form-control quarter" id="grievances_received_10" name="<portlet:namespace />grievances_received_10" readonly></td>
								    <td><input type="text" class="form-control quarter" id="grievances_assigned_10" name="<portlet:namespace />grievances_assigned_10" readonly></td>
								    <td><input type="text" class="form-control quarter" id="grievances_resolved_10" name="<portlet:namespace />grievances_resolved_10" readonly></td>
								    <td><input type="text" class="form-control quarter" id="outstanding_grievances_10" name="<portlet:namespace />outstanding_grievances_10" readonly></td>
	                            </tr>
	                        </table>
	
	                        <table class="mt-5 table table-bordered w-100" id="table2">
	                            <tr>
	                                <td class="text-center" colspan=4><strong>Scheme wise (NPS/NPS Lite/APY) split-up: </strong></td>
	                            </tr>
	                            <tr>
	                                <td><strong>Particulars</strong>
	                                </td>
	
	                                <td><strong>NPS</strong>
	                                </td>
	                                <td><strong>NPS Lite & APY</strong>
	                                </td>
	                                <td><strong>Total</strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td>Opening balance</td>
	                                <td><input type="number" class="form-control opening openbal_col" id="nps_0" name="<portlet:namespace />nps_0"></td>
								    <td><input type="number" class="form-control opening openbal_col" id="npsLite_0" name="<portlet:namespace />npsLite_0"></td>
								    <td><input type="text" class="form-control opening" id="total_0" name="<portlet:namespace />total_0" readonly></td>
	                            </tr>
	                            <tr>
	                                <td>Received</td>
	                                <td><input type="number" class="form-control received received_col" id="nps_1" name="<portlet:namespace />nps_1"></td>
								    <td><input type="number" class="form-control received received_col" id="npsLite_1" name="<portlet:namespace />npsLite_1"></td>
								    <td><input type="text" class="form-control received" id="total_1" name="<portlet:namespace />total_1" readonly></td>
	                            </tr>
	                            <tr>
	                                <td>Resolved</td>
	                                <td><input type="number" class="form-control resolved resolved_col" id="nps_2" name="<portlet:namespace />nps_2"></td>
								    <td><input type="number" class="form-control resolved resolved_col" id="npsLite_2" name="<portlet:namespace />npsLite_2"></td>
								    <td><input type="text" class="form-control resolved" id="total_2" name="<portlet:namespace />total_2" readonly></td>
	                            </tr>
	                            <tr>
	                                <td>Outstanding</td>
	                                <td><input type="number" class="form-control outstanding outstanding_col" id="nps_3" name="<portlet:namespace />nps_3"></td>
								    <td><input type="number" class="form-control outstanding outstanding_col" id="npsLite_3" name="<portlet:namespace />npsLite_3"></td>
								    <td><input type="text" class="form-control outstanding" id="total_3" name="<portlet:namespace />total_3" readonly></td>
	                            </tr>
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <!--TABLE 1 : Summary of overall grievances handled in CGMS of KCRA & NCRA (NPS + NPS LITE & APY)-->
	
	            <div class="table_two mt-5">
	                <div class="row">
	                    <div class="col-12">
	                        <table class="table table-bordered w-100" id="table3">
	                            <tr>
	                                <td class="text-center" colspan=8>
	                                    <strong>TABLE 2 : Comparison of overall summary of grievances handled in CGMS of KCRA+NCRA</strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td rowspan=2><strong>Grievances</strong></td>
	                                <td rowspan=2><strong>Q1 (FY <span class="fy">xxx</span>)</strong></td>
	                                <td rowspan=2><strong>Q2 (FY <span class="fy">xx</span>)</strong></td>
	                                <td rowspan=2><strong>Q3 (FY <span class="fy">xxx</span>)</strong></td>
	                                <td rowspan=2><strong>Q4 (FY <span class="fy">xxx</span>)</strong></td>
	                                <td rowspan=2><strong>Q1 (FY <span class="fy">xx</span>)</strong></td>
	                                <td rowspan=2><strong>Q2 (FY <span class="fy">xxxx</span>)</strong></td>
	                                <td><strong>Percentage change</strong></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>(Q2&nbsp; vis-&#224;-vis Q1)</strong></td>
	                            </tr>
	                            <tr>
	                                <td>Opening balance</td>
	                                <td><input type="number" class="form-control summaryOpening openbal_t2" id="overall_summary_q1_0" name="<portlet:namespace />overall_summary_q1_0"></td>
								    <td><input type="number" class="form-control summaryOpening openbal_t2" id="overall_summary_q2_0" name="<portlet:namespace />overall_summary_q2_0"></td>
								    <td><input type="number" class="form-control summaryOpening openbal_t2" id="overall_summary_q3_0" name="<portlet:namespace />overall_summary_q3_0"></td>
								    <td><input type="number" class="form-control summaryOpening openbal_t2" id="overall_summary_q4_0" name="<portlet:namespace />overall_summary_q4_0"></td>
								    <td><input type="number" class="form-control summaryOpening openbal_t2" id="overall_summary_q1One_0" name="<portlet:namespace />overall_summary_q1One_0"></td>
								    <td><input type="number" class="form-control summaryOpening openbal_t2" id="overall_summary_q2One_0" name="<portlet:namespace />overall_summary_q2One_0"></td>
								    <td><input type="text" class="form-control summaryOpening openbal_t2" id="percentage_change_0" name="<portlet:namespace />percentage_change_0"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Received</td>
	                                <td><input type="number" class="form-control summaryReceived received_t2" id="overall_summary_q1_1" name="<portlet:namespace />overall_summary_q1_1"></td>
								    <td><input type="number" class="form-control summaryReceived received_t2" id="overall_summary_q2_1" name="<portlet:namespace />overall_summary_q2_1"></td>
								    <td><input type="number" class="form-control summaryReceived received_t2" id="overall_summary_q3_1" name="<portlet:namespace />overall_summary_q3_1"></td>
								    <td><input type="number" class="form-control summaryReceived received_t2" id="overall_summary_q4_1" name="<portlet:namespace />overall_summary_q4_1"></td>
								    <td><input type="number" class="form-control summaryReceived received_t2" id="overall_summary_q1One_1" name="<portlet:namespace />overall_summary_q1One_1"></td>
								    <td><input type="number" class="form-control summaryReceived received_t2" id="overall_summary_q2One_1" name="<portlet:namespace />overall_summary_q2One_1"></td>
								    <td><input type="text" class="form-control summaryReceived received_t2" id="percentage_change_1" name="<portlet:namespace />percentage_change_1"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Resolved</td>
	                                <td><input type="number" class="form-control summaryResolved resolved_t2" id="overall_summary_q1_2" name="<portlet:namespace />overall_summary_q1_2"></td>
								    <td><input type="number" class="form-control summaryResolved resolved_t2" id="overall_summary_q2_2" name="<portlet:namespace />overall_summary_q2_2"></td>
								    <td><input type="number" class="form-control summaryResolved resolved_t2" id="overall_summary_q3_2" name="<portlet:namespace />overall_summary_q3_2"></td>
								    <td><input type="number" class="form-control summaryResolved resolved_t2" id="overall_summary_q4_2" name="<portlet:namespace />overall_summary_q4_2"></td>
								    <td><input type="number" class="form-control summaryResolved resolved_t2" id="overall_summary_q1One_2" name="<portlet:namespace />overall_summary_q1One_2"></td>
								    <td><input type="number" class="form-control summaryResolved resolved_t2" id="overall_summary_q2One_2" name="<portlet:namespace />overall_summary_q2One_2"></td>
								    <td><input type="text" class="form-control summaryResolved resolved_t2" id="percentage_change_2" name="<portlet:namespace />percentage_change_2"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Outstanding</td>
	                                <td><input type="number" class="form-control summaryOutstanding out_t2" id="overall_summary_q1_3" name="<portlet:namespace />overall_summary_q1_3"></td>
								    <td><input type="number" class="form-control summaryOutstanding out_t2" id="overall_summary_q2_3" name="<portlet:namespace />overall_summary_q2_3"></td>
								    <td><input type="number" class="form-control summaryOutstanding out_t2" id="overall_summary_q3_3" name="<portlet:namespace />overall_summary_q3_3"></td>
								    <td><input type="number" class="form-control summaryOutstanding out_t2" id="overall_summary_q4_3" name="<portlet:namespace />overall_summary_q4_3"></td>
								    <td><input type="number" class="form-control summaryOutstanding out_t2" id="overall_summary_q1One_3" name="<portlet:namespace />overall_summary_q1One_3"></td>
								    <td><input type="number" class="form-control summaryOutstanding out_t2" id="overall_summary_q2One_3" name="<portlet:namespace />overall_summary_q2One_3"></td>
								    <td><input type="text" class="form-control summaryOutstanding out_t2" id="percentage_change_3" name="<portlet:namespace />percentage_change_3"></td>
	                            </tr>
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <!-- TABLE 2 : Comparison of overall summary of grievances handled in CGMS of KCRA+NCRA -->
	
	            <div class="table_threea mt-5">
	                <div class="row">
	                    <div class="col-12">
	                        <table class="table table-bordered w-100" id="table4">
	                            <tr>
	                                <td class="text-center" colspan=5>
	                                    <strong>TABLE 3(a) : Comparison of cause-wise number of grievances received during quarters (NPS)</strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td rowspan=2><strong>Category</strong></td>
	                                <td><strong>Q1</strong></td>
	                                <td><strong>Q2</strong></td>
	                                <td><strong>Q3</strong></td>
	                                <td><strong>Q4</strong></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>Q1 (FY <span class="fy">xxxx</span>)</strong></td>
	                                <td><strong>Q2 (FY <span class="fy">xxx</span>)</strong></td>
	                                <td><strong>Q3 (FY <span class="fy">xxxx</span>)</strong></td>
	                                <td><strong>Q4 (FY <span class="fy">xxx</span>)</strong></td>
	                            </tr>
	
	                            <tr>
	                                <td>Against NPS Trust</td>
	                                <td><input type="number" class="form-control againstNps t3a_q1" id="cause_wise_q1_0" name="<portlet:namespace />cause_wise_q1_0"></td>
								    <td><input type="number" class="form-control againstNps t3a_q2" id="cause_wise_q2_0" name="<portlet:namespace />cause_wise_q2_0"></td>
								    <td><input type="number" class="form-control againstNps t3a_q3" id="cause_wise_q3_0" name="<portlet:namespace />cause_wise_q3_0"></td>
								    <td><input type="number" class="form-control againstNps t3a_q4" id="cause_wise_q4_0" name="<portlet:namespace />cause_wise_q4_0"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Contribution amount not reflected in account</td>
	                                <td><input type="number" class="form-control amtNotReflected t3a_q1" id="cause_wise_q1_1" name="<portlet:namespace />cause_wise_q1_1"></td>
								    <td><input type="number" class="form-control amtNotReflected t3a_q2" id="cause_wise_q2_1" name="<portlet:namespace />cause_wise_q2_1"></td>
								    <td><input type="number" class="form-control amtNotReflected t3a_q3" id="cause_wise_q3_1" name="<portlet:namespace />cause_wise_q3_1"></td>
								    <td><input type="number" class="form-control amtNotReflected t3a_q4" id="cause_wise_q4_1" name="<portlet:namespace />cause_wise_q4_1"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Delays in Issuance of PRAN Cards</td>
	                                <td><input type="number" class="form-control pran t3a_q1" id="cause_wise_q1_2" name="<portlet:namespace />cause_wise_q1_2"></td>
								    <td><input type="number" class="form-control pran t3a_q2" id="cause_wise_q2_2" name="<portlet:namespace />cause_wise_q2_2"></td>
								    <td><input type="number" class="form-control pran t3a_q3" id="cause_wise_q3_2" name="<portlet:namespace />cause_wise_q3_2"></td>
								    <td><input type="number" class="form-control pran t3a_q4" id="cause_wise_q4_2" name="<portlet:namespace />cause_wise_q4_2"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Delays in Uploading of Contribution Amounts</td>
	                                <td><input type="number" class="form-control uploadingAmt t3a_q1" id="cause_wise_q1_3" name="<portlet:namespace />cause_wise_q1_3"></td>
								    <td><input type="number" class="form-control uploadingAmt t3a_q2" id="cause_wise_q2_3" name="<portlet:namespace />cause_wise_q2_3"></td>
								    <td><input type="number" class="form-control uploadingAmt t3a_q3" id="cause_wise_q3_3" name="<portlet:namespace />cause_wise_q3_3"></td>
								    <td><input type="number" class="form-control uploadingAmt t3a_q4" id="cause_wise_q4_3" name="<portlet:namespace />cause_wise_q4_3"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Email/SMS alerts not received</td>
	                                <td><input type="number" class="form-control emailAlert t3a_q1" id="cause_wise_q1_4" name="<portlet:namespace />cause_wise_q1_4"></td>
								    <td><input type="number" class="form-control emailAlert t3a_q2" id="cause_wise_q2_4" name="<portlet:namespace />cause_wise_q2_4"></td>
								    <td><input type="number" class="form-control emailAlert t3a_q3" id="cause_wise_q3_4" name="<portlet:namespace />cause_wise_q3_4"></td>
								    <td><input type="number" class="form-control emailAlert t3a_q4" id="cause_wise_q4_4" name="<portlet:namespace />cause_wise_q4_4"></td>
	                            </tr>
	
	                            <tr>
	                                <td>General Query</td>
	                                <td><input type="number" class="form-control generalQuery t3a_q1" id="cause_wise_q1_5" name="<portlet:namespace />cause_wise_q1_5"></td>
								    <td><input type="number" class="form-control generalQuery t3a_q2" id="cause_wise_q2_5" name="<portlet:namespace />cause_wise_q2_5"></td>
								    <td><input type="number" class="form-control generalQuery t3a_q3" id="cause_wise_q3_5" name="<portlet:namespace />cause_wise_q3_5"></td>
								    <td><input type="number" class="form-control generalQuery t3a_q4" id="cause_wise_q4_5" name="<portlet:namespace />cause_wise_q4_5"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Grievance of BANK against PAO/POP SP / CRA</td>
	                                <td><input type="number" class="form-control grievanceOfBank t3a_q1" id="cause_wise_q1_6" name="<portlet:namespace />cause_wise_q1_6"></td>
								    <td><input type="number" class="form-control grievanceOfBank t3a_q2" id="cause_wise_q2_6" name="<portlet:namespace />cause_wise_q2_6"></td>
								    <td><input type="number" class="form-control grievanceOfBank t3a_q3" id="cause_wise_q3_6" name="<portlet:namespace />cause_wise_q3_6"></td>
								    <td><input type="number" class="form-control grievanceOfBank t3a_q4" id="cause_wise_q4_6" name="<portlet:namespace />cause_wise_q4_6"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Incorrect contribution amount reflected</td>
	                                <td><input type="number" class="form-control incorrectAmt t3a_q1" id="cause_wise_q1_7" name="<portlet:namespace />cause_wise_q1_7"></td>
								    <td><input type="number" class="form-control incorrectAmt t3a_q2" id="cause_wise_q2_7" name="<portlet:namespace />cause_wise_q2_7"></td>
								    <td><input type="number" class="form-control incorrectAmt t3a_q3" id="cause_wise_q3_7" name="<portlet:namespace />cause_wise_q3_7"></td>
								    <td><input type="number" class="form-control incorrectAmt t3a_q4" id="cause_wise_q4_7" name="<portlet:namespace />cause_wise_q4_7"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Incorrect Processing of Subscriber Details</td>
	                                <td><input type="number" class="form-control subscriberDetails t3a_q1" id="cause_wise_q1_8" name="<portlet:namespace />cause_wise_q1_8"></td>
								    <td><input type="number" class="form-control subscriberDetails t3a_q2" id="cause_wise_q2_8" name="<portlet:namespace />cause_wise_q2_8"></td>
								    <td><input type="number" class="form-control subscriberDetails t3a_q3" id="cause_wise_q3_8" name="<portlet:namespace />cause_wise_q3_8"></td>
								    <td><input type="number" class="form-control subscriberDetails t3a_q4" id="cause_wise_q4_8" name="<portlet:namespace />cause_wise_q4_8"></td>
	                            </tr>
	
	                            <tr>
	                                <td>I-PIN, T-PIN Related - Others</td>
	                                <td><input type="number" class="form-control ipin t3a_q1" id="cause_wise_q1_9" name="<portlet:namespace />cause_wise_q1_9"></td>
								    <td><input type="number" class="form-control ipin t3a_q2" id="cause_wise_q2_9" name="<portlet:namespace />cause_wise_q2_9"></td>
								    <td><input type="number" class="form-control ipin t3a_q3" id="cause_wise_q3_9" name="<portlet:namespace />cause_wise_q3_9"></td>
								    <td><input type="number" class="form-control ipin t3a_q4" id="cause_wise_q4_9" name="<portlet:namespace />cause_wise_q4_9"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Not Processed/Delay in Processing Subscriber Changes Request</td>
	                                <td><input type="number" class="form-control subscriberChanges t3a_q1" id="cause_wise_q1_10" name="<portlet:namespace />cause_wise_q1_10"></td>
								    <td><input type="number" class="form-control subscriberChanges t3a_q2" id="cause_wise_q2_10" name="<portlet:namespace />cause_wise_q2_10"></td>
								    <td><input type="number" class="form-control subscriberChanges t3a_q3" id="cause_wise_q3_10" name="<portlet:namespace />cause_wise_q3_10"></td>
								    <td><input type="number" class="form-control subscriberChanges t3a_q4" id="cause_wise_q4_10" name="<portlet:namespace />cause_wise_q4_10"></td>
	                            </tr>
	
	                            <tr>
	                                <td>PRAN Card related</td>
	                                <td><input type="number" class="form-control pranCardRelated t3a_q1" id="cause_wise_q1_11" name="<portlet:namespace />cause_wise_q1_11"></td>
								    <td><input type="number" class="form-control pranCardRelated t3a_q2" id="cause_wise_q2_11" name="<portlet:namespace />cause_wise_q2_11"></td>
								    <td><input type="number" class="form-control pranCardRelated t3a_q3" id="cause_wise_q3_11" name="<portlet:namespace />cause_wise_q3_11"></td>
								    <td><input type="number" class="form-control pranCardRelated t3a_q4" id="cause_wise_q4_11" name="<portlet:namespace />cause_wise_q4_11"></td>
	                            </tr>
	
	                            <tr>
	                                <td>SOT Related</td>
	                                <td><input type="number" class="form-control sot t3a_q1" id="cause_wise_q1_12" name="<portlet:namespace />cause_wise_q1_12"></td>
								    <td><input type="number" class="form-control sot t3a_q2" id="cause_wise_q2_12" name="<portlet:namespace />cause_wise_q2_12"></td>
								    <td><input type="number" class="form-control sot t3a_q3" id="cause_wise_q3_12" name="<portlet:namespace />cause_wise_q3_12"></td>
								    <td><input type="number" class="form-control sot t3a_q4" id="cause_wise_q4_12" name="<portlet:namespace />cause_wise_q4_12"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Tier II related</td>
	                                <td><input type="number" class="form-control tierTwo t3a_q1" id="cause_wise_q1_13" name="<portlet:namespace />cause_wise_q1_13"></td>
								    <td><input type="number" class="form-control tierTwo t3a_q2" id="cause_wise_q2_13" name="<portlet:namespace />cause_wise_q2_13"></td>
								    <td><input type="number" class="form-control tierTwo t3a_q3" id="cause_wise_q3_13" name="<portlet:namespace />cause_wise_q3_13"></td>
								    <td><input type="number" class="form-control tierTwo t3a_q4" id="cause_wise_q4_13" name="<portlet:namespace />cause_wise_q4_13"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Withdrawal Related</td>
	                                <td><input type="number" class="form-control withdrawal t3a_q1" id="cause_wise_q1_14" name="<portlet:namespace />cause_wise_q1_14"></td>
								    <td><input type="number" class="form-control withdrawal t3a_q2" id="cause_wise_q2_14" name="<portlet:namespace />cause_wise_q2_14"></td>
								    <td><input type="number" class="form-control withdrawal t3a_q3" id="cause_wise_q3_14" name="<portlet:namespace />cause_wise_q3_14"></td>
								    <td><input type="number" class="form-control withdrawal t3a_q4" id="cause_wise_q4_14" name="<portlet:namespace />cause_wise_q4_14"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Death withdrawal not initiated / not authorised / amount not received</td>
	                                <td><input type="number" class="form-control deathWithdrawal t3a_q1" id="cause_wise_q1_15" name="<portlet:namespace />cause_wise_q1_15"></td>
								    <td><input type="number" class="form-control deathWithdrawal t3a_q2" id="cause_wise_q2_15" name="<portlet:namespace />cause_wise_q2_15"></td>
								    <td><input type="number" class="form-control deathWithdrawal t3a_q3" id="cause_wise_q3_15" name="<portlet:namespace />cause_wise_q3_15"></td>
								    <td><input type="number" class="form-control deathWithdrawal t3a_q4" id="cause_wise_q4_15" name="<portlet:namespace />cause_wise_q4_15"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Exit not initiated / not authorised / amount not received</td>
	                                <td><input type="number" class="form-control exit t3a_q1" id="cause_wise_q1_16" name="<portlet:namespace />cause_wise_q1_16"></td>
								    <td><input type="number" class="form-control exit t3a_q2" id="cause_wise_q2_16" name="<portlet:namespace />cause_wise_q2_16"></td>
								    <td><input type="number" class="form-control exit t3a_q3" id="cause_wise_q3_16" name="<portlet:namespace />cause_wise_q3_16"></td>
								    <td><input type="number" class="form-control exit t3a_q4" id="cause_wise_q4_16" name="<portlet:namespace />cause_wise_q4_16"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Partial withdrawal not initiated / not authorised / amount not received</td>
	                               	<td><input type="number" class="form-control partialWithdrawal t3a_q1" id="cause_wise_q1_17" name="<portlet:namespace />cause_wise_q1_17"></td>
								    <td><input type="number" class="form-control partialWithdrawal t3a_q2" id="cause_wise_q2_17" name="<portlet:namespace />cause_wise_q2_17"></td>
								    <td><input type="number" class="form-control partialWithdrawal t3a_q3" id="cause_wise_q3_17" name="<portlet:namespace />cause_wise_q3_17"></td>
								    <td><input type="number" class="form-control partialWithdrawal t3a_q4" id="cause_wise_q4_17" name="<portlet:namespace />cause_wise_q4_17"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Pre-mature withdrawal not initiated / not authorised / amount not received</td>
	                                <td><input type="number" class="form-control preMatureWithdrawal t3a_q1" id="cause_wise_q1_18" name="<portlet:namespace />cause_wise_q1_18"></td>
								    <td><input type="number" class="form-control preMatureWithdrawal t3a_q2" id="cause_wise_q2_18" name="<portlet:namespace />cause_wise_q2_18"></td>
								    <td><input type="number" class="form-control preMatureWithdrawal t3a_q3" id="cause_wise_q3_18" name="<portlet:namespace />cause_wise_q3_18"></td>
								    <td><input type="number" class="form-control preMatureWithdrawal t3a_q4" id="cause_wise_q4_18" name="<portlet:namespace />cause_wise_q4_18"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Other grievances</td>
	                                <td><input type="number" class="form-control otherGrievances t3a_q1" id="cause_wise_q1_19" name="<portlet:namespace />cause_wise_q1_19"></td>
								    <td><input type="number" class="form-control otherGrievances t3a_q2" id="cause_wise_q2_19" name="<portlet:namespace />cause_wise_q2_19"></td>
								    <td><input type="number" class="form-control otherGrievances t3a_q3" id="cause_wise_q3_19" name="<portlet:namespace />cause_wise_q3_19"></td>
								    <td><input type="number" class="form-control otherGrievances t3a_q4" id="cause_wise_q4_19" name="<portlet:namespace />cause_wise_q4_19"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Change Request- Others</td>
	                                <td><input type="number" class="form-control changeRequestOthers t3a_q1" id="cause_wise_q1_20" name="<portlet:namespace />cause_wise_q1_20"></td>
								    <td><input type="number" class="form-control changeRequestOthers t3a_q2" id="cause_wise_q2_20" name="<portlet:namespace />cause_wise_q2_20"></td>
								    <td><input type="number" class="form-control changeRequestOthers t3a_q3" id="cause_wise_q3_20" name="<portlet:namespace />cause_wise_q3_20"></td>
								    <td><input type="number" class="form-control changeRequestOthers t3a_q4" id="cause_wise_q4_20" name="<portlet:namespace />cause_wise_q4_20"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Change Request- Processed Incorrectly</td>
	                               	<td><input type="number" class="form-control changeRequestProcessed t3a_q1" id="cause_wise_q1_21" name="<portlet:namespace />cause_wise_q1_21"></td>
								    <td><input type="number" class="form-control changeRequestProcessed t3a_q2" id="cause_wise_q2_21" name="<portlet:namespace />cause_wise_q2_21"></td>
								    <td><input type="number" class="form-control changeRequestProcessed t3a_q3" id="cause_wise_q3_21" name="<portlet:namespace />cause_wise_q3_21"></td>
								    <td><input type="number" class="form-control changeRequestProcessed t3a_q4" id="cause_wise_q4_21" name="<portlet:namespace />cause_wise_q4_21"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Contribution-others</td>
	                                <td><input type="number" class="form-control contributionOthers t3a_q1" id="cause_wise_q1_22" name="<portlet:namespace />cause_wise_q1_22"></td>
								    <td><input type="number" class="form-control contributionOthers t3a_q2" id="cause_wise_q2_22" name="<portlet:namespace />cause_wise_q2_22"></td>
								    <td><input type="number" class="form-control contributionOthers t3a_q3" id="cause_wise_q3_22" name="<portlet:namespace />cause_wise_q3_22"></td>
								    <td><input type="number" class="form-control contributionOthers t3a_q4" id="cause_wise_q4_22" name="<portlet:namespace />cause_wise_q4_22"></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>GRAND TOTAL</strong></td>
	                                <td><input type="text" class="form-control grandTotal" id="cause_wise_q1_23" name="<portlet:namespace />cause_wise_q1_23" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="cause_wise_q2_23" name="<portlet:namespace />cause_wise_q2_23" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="cause_wise_q3_23" name="<portlet:namespace />cause_wise_q3_23" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="cause_wise_q4_23" name="<portlet:namespace />cause_wise_q4_23" readonly></td>
	                            </tr>
	
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <!-- TABLE 3(a) : Comparison of cause-wise number of grievances received during quarters (NPS) -->
	
	            <div class="table_threeb mt-5">
	                <div class="row">
	                    <div class="col-12">
	                        <table class="table table-bordered w-100" id="table5">
	                            <tr>
	                                <td class="text-center" colspan=7>
	                                    <strong>TABLE 3(b) : Cause-wise number of grievances received during quarters (NPS Lite & APY)</strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td rowspan=2><strong>Category</strong></td>
	                                <td><strong>Q1</strong></td>
	                                <td><strong>Q2</strong></td>
	                                <td><strong>Q3</strong></td>
	                                <td><strong>Q4</strong></td>
	                                <td><strong>Q1</strong></td>
	                                <td><strong>Q2</strong></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>(FY <span class="fy">xxxx</span>)</strong></td>
	                                <td><strong>(FY <span class="fy">xxxxx</span>)</strong></td>
	                                <td><strong>(FY <span class="fy">xxx</span>)</strong></td>
	                                <td><strong>(FY <span class="fy">xxxxx</span>)</strong></td>
	                                <td><strong>(FY <span class="fy">xxxxx</span>)</strong></td>
	                                <td><strong>(FY <span class="fy">xxx</span>)</strong></td>
	                            </tr>
	
	                            <tr>
	                                <td>Against NPS Trust</td>
	                                <td><input type="number" class="form-control causeWiseAgainstNps t3b_q1" id="grievances_3b_q1_0" name="<portlet:namespace />grievances_3b_q1_0"></td>
								    <td><input type="number" class="form-control causeWiseAgainstNps t3b_q2" id="grievances_3b_q2_0" name="<portlet:namespace />grievances_3b_q2_0"></td>
								    <td><input type="number" class="form-control causeWiseAgainstNps t3b_q3" id="grievances_3b_q3_0" name="<portlet:namespace />grievances_3b_q3_0"></td>
								    <td><input type="number" class="form-control causeWiseAgainstNps t3b_q4" id="grievances_3b_q4_0" name="<portlet:namespace />grievances_3b_q4_0"></td>
								    <td><input type="number" class="form-control causeWiseAgainstNps t3b_qOne" id="grievances_3b_q1One_0" name="<portlet:namespace />grievances_3b_q1One_0"></td>
								    <td><input type="number" class="form-control causeWiseAgainstNps t3b_qTwo" id="grievances_3b_q2One_0" name="<portlet:namespace />grievances_3b_q2One_0"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Contribution amount not reflected in account</td>
	                                <td><input type="number" class="form-control causeWiseAmtNotReflected t3b_q1" id="grievances_3b_q1_1" name="<portlet:namespace />grievances_3b_q1_1"></td>
								    <td><input type="number" class="form-control causeWiseAmtNotReflected t3b_q2" id="grievances_3b_q2_1" name="<portlet:namespace />grievances_3b_q2_1"></td>
								    <td><input type="number" class="form-control causeWiseAmtNotReflected t3b_q3" id="grievances_3b_q3_1" name="<portlet:namespace />grievances_3b_q3_1"></td>
								    <td><input type="number" class="form-control causeWiseAmtNotReflected t3b_q4" id="grievances_3b_q4_1" name="<portlet:namespace />grievances_3b_q4_1"></td>
								    <td><input type="number" class="form-control causeWiseAmtNotReflected t3b_qOne" id="grievances_3b_q1One_1" name="<portlet:namespace />grievances_3b_q1One_1"></td>
								    <td><input type="number" class="form-control causeWiseAmtNotReflected t3b_qTwo" id="grievances_3b_q2One_1" name="<portlet:namespace />grievances_3b_q2One_1"></td>
	                            </tr>
	
	                            <tr>
	                                <td>General Query</td>
	                                <td><input type="number" class="form-control causeWiseGeneralQuery t3b_q1" id="grievances_3b_q1_2" name="<portlet:namespace />grievances_3b_q1_2"></td>
								    <td><input type="number" class="form-control causeWiseGeneralQuery t3b_q2" id="grievances_3b_q2_2" name="<portlet:namespace />grievances_3b_q2_2"></td>
								    <td><input type="number" class="form-control causeWiseGeneralQuery t3b_q3" id="grievances_3b_q3_2" name="<portlet:namespace />grievances_3b_q3_2"></td>
								    <td><input type="number" class="form-control causeWiseGeneralQuery t3b_q4" id="grievances_3b_q4_2" name="<portlet:namespace />grievances_3b_q4_2"></td>
								    <td><input type="number" class="form-control causeWiseGeneralQuery t3b_qOne" id="grievances_3b_q1One_2" name="<portlet:namespace />grievances_3b_q1One_2"></td>
								    <td><input type="number" class="form-control causeWiseGeneralQuery t3b_qTwo" id="grievances_3b_q2One_2" name="<portlet:namespace />grievances_3b_q2One_2"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Incorrect Contribution Reflected</td>
	                                <td><input type="number" class="form-control causeWiseIncorrectContribution t3b_q1" id="grievances_3b_q1_3" name="<portlet:namespace />grievances_3b_q1_3"></td>
								    <td><input type="number" class="form-control causeWiseIncorrectContribution t3b_q2" id="grievances_3b_q2_3" name="<portlet:namespace />grievances_3b_q2_3"></td>
								    <td><input type="number" class="form-control causeWiseIncorrectContribution t3b_q3" id="grievances_3b_q3_3" name="<portlet:namespace />grievances_3b_q3_3"></td>
								    <td><input type="number" class="form-control causeWiseIncorrectContribution t3b_q4" id="grievances_3b_q4_3" name="<portlet:namespace />grievances_3b_q4_3"></td>
								    <td><input type="number" class="form-control causeWiseIncorrectContribution t3b_qOne" id="grievances_3b_q1One_3" name="<portlet:namespace />grievances_3b_q1One_3"></td>
								    <td><input type="number" class="form-control causeWiseIncorrectContribution t3b_qTwo" id="grievances_3b_q2One_3" name="<portlet:namespace />grievances_3b_q2One_3"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Incorrect Processing of Subscriber Details</td>
	                                <td><input type="number" class="form-control causeWiseSubscriberDetails t3b_q1" id="grievances_3b_q1_4" name="<portlet:namespace />grievances_3b_q1_4"></td>
								    <td><input type="number" class="form-control causeWiseSubscriberDetails t3b_q2" id="grievances_3b_q2_4" name="<portlet:namespace />grievances_3b_q2_4"></td>
								    <td><input type="number" class="form-control causeWiseSubscriberDetails t3b_q3" id="grievances_3b_q3_4" name="<portlet:namespace />grievances_3b_q3_4"></td>
								    <td><input type="number" class="form-control causeWiseSubscriberDetails t3b_q4" id="grievances_3b_q4_4" name="<portlet:namespace />grievances_3b_q4_4"></td>
								    <td><input type="number" class="form-control causeWiseSubscriberDetails t3b_qOne" id="grievances_3b_q1One_4" name="<portlet:namespace />grievances_3b_q1One_4"></td>
								    <td><input type="number" class="form-control causeWiseSubscriberDetails t3b_qTwo" id="grievances_3b_q2One_4" name="<portlet:namespace />grievances_3b_q2One_4"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Other Grievances</td>
	                                <td><input type="number" class="form-control causeWiseOtherGrievances t3b_q1" id="grievances_3b_q1_5" name="<portlet:namespace />grievances_3b_q1_5"></td>
								    <td><input type="number" class="form-control causeWiseOtherGrievances t3b_q2" id="grievances_3b_q2_5" name="<portlet:namespace />grievances_3b_q2_5"></td>
								    <td><input type="number" class="form-control causeWiseOtherGrievances t3b_q3" id="grievances_3b_q3_5" name="<portlet:namespace />grievances_3b_q3_5"></td>
								    <td><input type="number" class="form-control causeWiseOtherGrievances t3b_q4" id="grievances_3b_q4_5" name="<portlet:namespace />grievances_3b_q4_5"></td>
								    <td><input type="number" class="form-control causeWiseOtherGrievances t3b_qOne" id="grievances_3b_q1One_5" name="<portlet:namespace />grievances_3b_q1One_5"></td>
								    <td><input type="number" class="form-control causeWiseOtherGrievances t3b_qTwo" id="grievances_3b_q2One_5" name="<portlet:namespace />grievances_3b_q2One_5"></td>
	                            </tr>
	
	                            <tr>
	                                <td>PRAN Card Related</td>
	                                <td><input type="number" class="form-control causeWisePran t3b_q1" id="grievances_3b_q1_6" name="<portlet:namespace />grievances_3b_q1_6"></td>
								    <td><input type="number" class="form-control causeWisePran t3b_q2" id="grievances_3b_q2_6" name="<portlet:namespace />grievances_3b_q2_6"></td>
								    <td><input type="number" class="form-control causeWisePran t3b_q3" id="grievances_3b_q3_6" name="<portlet:namespace />grievances_3b_q3_6"></td>
								    <td><input type="number" class="form-control causeWisePran t3b_q4" id="grievances_3b_q4_6" name="<portlet:namespace />grievances_3b_q4_6"></td>
								    <td><input type="number" class="form-control causeWisePran t3b_qOne" id="grievances_3b_q1One_6" name="<portlet:namespace />grievances_3b_q1One_6"></td>
								    <td><input type="number" class="form-control causeWisePran t3b_qTwo" id="grievances_3b_q2One_6" name="<portlet:namespace />grievances_3b_q2One_6"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Processing of change request by Nodal Office</td>
	                                <td><input type="number" class="form-control causeWiseChangeRequest t3b_q1" id="grievances_3b_q1_7" name="<portlet:namespace />grievances_3b_q1_7"></td>
								    <td><input type="number" class="form-control causeWiseChangeRequest t3b_q2" id="grievances_3b_q2_7" name="<portlet:namespace />grievances_3b_q2_7"></td>
								    <td><input type="number" class="form-control causeWiseChangeRequest t3b_q3" id="grievances_3b_q3_7" name="<portlet:namespace />grievances_3b_q3_7"></td>
								    <td><input type="number" class="form-control causeWiseChangeRequest t3b_q4" id="grievances_3b_q4_7" name="<portlet:namespace />grievances_3b_q4_7"></td>
								    <td><input type="number" class="form-control causeWiseChangeRequest t3b_qOne" id="grievances_3b_q1One_7" name="<portlet:namespace />grievances_3b_q1One_7"></td>
								    <td><input type="number" class="form-control causeWiseChangeRequest t3b_qTwo" id="grievances_3b_q2One_7" name="<portlet:namespace />grievances_3b_q2One_7"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Service not received</td>
	                                <td><input type="number" class="form-control causeWiseService t3b_q1" id="grievances_3b_q1_8" name="<portlet:namespace />grievances_3b_q1_8"></td>
								    <td><input type="number" class="form-control causeWiseService t3b_q2" id="grievances_3b_q2_8" name="<portlet:namespace />grievances_3b_q2_8"></td>
								    <td><input type="number" class="form-control causeWiseService t3b_q3" id="grievances_3b_q3_8" name="<portlet:namespace />grievances_3b_q3_8"></td>
								    <td><input type="number" class="form-control causeWiseService t3b_q4" id="grievances_3b_q4_8" name="<portlet:namespace />grievances_3b_q4_8"></td>
								    <td><input type="number" class="form-control causeWiseService t3b_qOne" id="grievances_3b_q1One_8" name="<portlet:namespace />grievances_3b_q1One_8"></td>
								    <td><input type="number" class="form-control causeWiseService t3b_qTwo" id="grievances_3b_q2One_8" name="<portlet:namespace />grievances_3b_q2One_8"></td>
	                            </tr>
	
	                            <tr>
	                                <td>SOT Related</td>
	                                <td><input type="number" class="form-control causeWiseSot t3b_q1" id="grievances_3b_q1_9" name="<portlet:namespace />grievances_3b_q1_9"></td>
								    <td><input type="number" class="form-control causeWiseSot t3b_q2" id="grievances_3b_q2_9" name="<portlet:namespace />grievances_3b_q2_9"></td>
								    <td><input type="number" class="form-control causeWiseSot t3b_q3" id="grievances_3b_q3_9" name="<portlet:namespace />grievances_3b_q3_9"></td>
								    <td><input type="number" class="form-control causeWiseSot t3b_q4" id="grievances_3b_q4_9" name="<portlet:namespace />grievances_3b_q4_9"></td>
								    <td><input type="number" class="form-control causeWiseSot t3b_qOne" id="grievances_3b_q1One_9" name="<portlet:namespace />grievances_3b_q1One_9"></td>
								    <td><input type="number" class="form-control causeWiseSot t3b_qTwo" id="grievances_3b_q2One_9" name="<portlet:namespace />grievances_3b_q2One_9"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Withdrawal Related</td>
	                                <td><input type="number" class="form-control causeWiseWithdrawal t3b_q1" id="grievances_3b_q1_10" name="<portlet:namespace />grievances_3b_q1_10"></td>
								    <td><input type="number" class="form-control causeWiseWithdrawal t3b_q2" id="grievances_3b_q2_10" name="<portlet:namespace />grievances_3b_q2_10"></td>
								    <td><input type="number" class="form-control causeWiseWithdrawal t3b_q3" id="grievances_3b_q3_10" name="<portlet:namespace />grievances_3b_q3_10"></td>
								    <td><input type="number" class="form-control causeWiseWithdrawal t3b_q4" id="grievances_3b_q4_10" name="<portlet:namespace />grievances_3b_q4_10"></td>
								    <td><input type="number" class="form-control causeWiseWithdrawal t3b_qOne" id="grievances_3b_q1One_10" name="<portlet:namespace />grievances_3b_q1One_10"></td>
								    <td><input type="number" class="form-control causeWiseWithdrawal t3b_qTwo" id="grievances_3b_q2One_10" name="<portlet:namespace />grievances_3b_q2One_10"></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>GRAND TOTAL</strong></td>
	                               	<td><input type="text" class="form-control grandTotal" id="grievances_3b_q1_11" name="<portlet:namespace />grievances_3b_q1_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="grievances_3b_q2_11" name="<portlet:namespace />grievances_3b_q2_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="grievances_3b_q3_11" name="<portlet:namespace />grievances_3b_q3_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="grievances_3b_q4_11" name="<portlet:namespace />grievances_3b_q4_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="grievances_3b_q1One_11" name="<portlet:namespace />grievances_3b_q1One_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="grievances_3b_q2One_11" name="<portlet:namespace />grievances_3b_q2One_11" readonly></td>
	                            </tr>
	
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <!-- TABLE 3(b) : Cause-wise number of grievances received during quarters (NPS Lite & APY) -->
	
	            <div class="table_four mt-5">
	                <div class="row">
	                    <div class="col-12">
	                        <table class="table table-bordered w-100" id="table6">
	                            <tr>
	                                <td class="text-center" colspan=7>
	                                    <strong>TABLE 4 : Entity wise Outstanding grievances at the end of quarters (NPS +NPS Lite+ APY)</strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td rowspan=2><strong>Entity</strong></td>
	                                <td><strong>Q1</strong></td>
	                                <td><strong>Q2</strong></td>
	                                <td><strong>Q3</strong></td>
	                                <td><strong>Q4</strong></td>
	                                <td><strong>Q1</strong></td>
	                                <td><strong>Q2</strong></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>(FY <span class="fy">xxxx</span>)</strong></td>
	                                <td><strong>(FY <span class="fy">xxxxx</span>)</strong></td>
	                                <td><strong>(FY <span class="fy">xxx</span>)</strong></td>
	                                <td><strong>(FY <span class="fy">xxxxx</span>)</strong></td>
	                                <td><strong>(FY <span class="fy">xxxxx</span>)</strong></td>
	                                <td><strong>(FY <span class="fy">xxx</span>)</strong></td>
	                            </tr>
	
	                            <tr>
	                                <td>CG Nodal Office</td>
	                                <td><input type="number" class="form-control entityWiseCG t4_q1" id="entity_wise_outstanding_q1_0" name="<portlet:namespace />entity_wise_outstanding_q1_0"></td>
								    <td><input type="number" class="form-control entityWiseCG t4_q2" id="entity_wise_outstanding_q2_0" name="<portlet:namespace />entity_wise_outstanding_q2_0"></td>
								    <td><input type="number" class="form-control entityWiseCG t4_q3" id="entity_wise_outstanding_q3_0" name="<portlet:namespace />entity_wise_outstanding_q3_0"></td>
								    <td><input type="number" class="form-control entityWiseCG t4_q4" id="entity_wise_outstanding_q4_0" name="<portlet:namespace />entity_wise_outstanding_q4_0"></td>
								    <td><input type="number" class="form-control entityWiseCG t4_qOne" id="entity_wise_outstanding_q1One_0" name="<portlet:namespace />entity_wise_outstanding_q1One_0"></td>
								    <td><input type="number" class="form-control entityWiseCG t4_qTwo" id="entity_wise_outstanding_q2One_0" name="<portlet:namespace />entity_wise_outstanding_q2One_0"></td>
	                            </tr>
	
	                            <tr>
	                                <td>SG Nodal Office</td>
	                                <td><input type="number" class="form-control entityWiseSG t4_q1" id="entity_wise_outstanding_q1_1" name="<portlet:namespace />entity_wise_outstanding_q1_1"></td>
								    <td><input type="number" class="form-control entityWiseSG t4_q2" id="entity_wise_outstanding_q2_1" name="<portlet:namespace />entity_wise_outstanding_q2_1"></td>
								    <td><input type="number" class="form-control entityWiseSG t4_q3" id="entity_wise_outstanding_q3_1" name="<portlet:namespace />entity_wise_outstanding_q3_1"></td>
								    <td><input type="number" class="form-control entityWiseSG t4_q4" id="entity_wise_outstanding_q4_1" name="<portlet:namespace />entity_wise_outstanding_q4_1"></td>
								    <td><input type="number" class="form-control entityWiseSG t4_qOne" id="entity_wise_outstanding_q1One_1" name="<portlet:namespace />entity_wise_outstanding_q1One_1"></td>
								    <td><input type="number" class="form-control entityWiseSG t4_qTwo" id="entity_wise_outstanding_q2One_1" name="<portlet:namespace />entity_wise_outstanding_q2One_1"></td>
	                            </tr>
	
	                            <tr>
	                                <td>PoP</td>
	                               	<td><input type="number" class="form-control entityWisePop t4_q1" id="entity_wise_outstanding_q1_2" name="<portlet:namespace />entity_wise_outstanding_q1_2"></td>
								    <td><input type="number" class="form-control entityWisePop t4_q2" id="entity_wise_outstanding_q2_2" name="<portlet:namespace />entity_wise_outstanding_q2_2"></td>
								    <td><input type="number" class="form-control entityWisePop t4_q3" id="entity_wise_outstanding_q3_2" name="<portlet:namespace />entity_wise_outstanding_q3_2"></td>
								    <td><input type="number" class="form-control entityWisePop t4_q4" id="entity_wise_outstanding_q4_2" name="<portlet:namespace />entity_wise_outstanding_q4_2"></td>
								    <td><input type="number" class="form-control entityWisePop t4_qOne" id="entity_wise_outstanding_q1One_2" name="<portlet:namespace />entity_wise_outstanding_q1One_2"></td>
								    <td><input type="number" class="form-control entityWisePop t4_qTwo" id="entity_wise_outstanding_q2One_2" name="<portlet:namespace />entity_wise_outstanding_q2One_2"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Corporate Nodal Office</td>
	                                <td><input type="number" class="form-control entityWiseCorp t4_q1" id="entity_wise_outstanding_q1_3" name="<portlet:namespace />entity_wise_outstanding_q1_3"></td>
								    <td><input type="number" class="form-control entityWiseCorp t4_q2" id="entity_wise_outstanding_q2_3" name="<portlet:namespace />entity_wise_outstanding_q2_3"></td>
								    <td><input type="number" class="form-control entityWiseCorp t4_q3" id="entity_wise_outstanding_q3_3" name="<portlet:namespace />entity_wise_outstanding_q3_3"></td>
								    <td><input type="number" class="form-control entityWiseCorp t4_q4" id="entity_wise_outstanding_q4_3" name="<portlet:namespace />entity_wise_outstanding_q4_3"></td>
								    <td><input type="number" class="form-control entityWiseCorp t4_qOne" id="entity_wise_outstanding_q1One_3" name="<portlet:namespace />entity_wise_outstanding_q1One_3"></td>
								    <td><input type="number" class="form-control entityWiseCorp t4_qTwo" id="entity_wise_outstanding_q2One_3" name="<portlet:namespace />entity_wise_outstanding_q2One_3"></td>
	                            </tr>
	
	                            <tr>
	                                <td>CRA</td>
	                                <td><input type="number" class="form-control entityWiseCRA t4_q1" id="entity_wise_outstanding_q1_4" name="<portlet:namespace />entity_wise_outstanding_q1_4"></td>
								    <td><input type="number" class="form-control entityWiseCRA t4_q2" id="entity_wise_outstanding_q2_4" name="<portlet:namespace />entity_wise_outstanding_q2_4"></td>
								    <td><input type="number" class="form-control entityWiseCRA t4_q3" id="entity_wise_outstanding_q3_4" name="<portlet:namespace />entity_wise_outstanding_q3_4"></td>
								    <td><input type="number" class="form-control entityWiseCRA t4_q4" id="entity_wise_outstanding_q4_4" name="<portlet:namespace />entity_wise_outstanding_q4_4"></td>
								    <td><input type="number" class="form-control entityWiseCRA t4_qOne" id="entity_wise_outstanding_q1One_4" name="<portlet:namespace />entity_wise_outstanding_q1One_4"></td>
								    <td><input type="number" class="form-control entityWiseCRA t4_qTwo" id="entity_wise_outstanding_q2One_4" name="<portlet:namespace />entity_wise_outstanding_q2One_4"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Trustee Bank</td>
	                                <td><input type="number" class="form-control entityWiseTB t4_q1" id="entity_wise_outstanding_q1_5" name="<portlet:namespace />entity_wise_outstanding_q1_5"></td>
								    <td><input type="number" class="form-control entityWiseTB t4_q2" id="entity_wise_outstanding_q2_5" name="<portlet:namespace />entity_wise_outstanding_q2_5"></td>
								    <td><input type="number" class="form-control entityWiseTB t4_q3" id="entity_wise_outstanding_q3_5" name="<portlet:namespace />entity_wise_outstanding_q3_5"></td>
								    <td><input type="number" class="form-control entityWiseTB t4_q4" id="entity_wise_outstanding_q4_5" name="<portlet:namespace />entity_wise_outstanding_q4_5"></td>
								    <td><input type="number" class="form-control entityWiseTB t4_qOne" id="entity_wise_outstanding_q1One_5" name="<portlet:namespace />entity_wise_outstanding_q1One_5"></td>
								    <td><input type="number" class="form-control entityWiseTB t4_qTwo" id="entity_wise_outstanding_q2One_5" name="<portlet:namespace />entity_wise_outstanding_q2One_5"></td>
	                            </tr>
	
	                            <tr>
	                                <td>NPS Trust</td>
	                                <td><input type="number" class="form-control entityWiseNPS t4_q1" id="entity_wise_outstanding_q1_6" name="<portlet:namespace />entity_wise_outstanding_q1_6"></td>
								    <td><input type="number" class="form-control entityWiseNPS t4_q2" id="entity_wise_outstanding_q2_6" name="<portlet:namespace />entity_wise_outstanding_q2_6"></td>
								    <td><input type="number" class="form-control entityWiseNPS t4_q3" id="entity_wise_outstanding_q3_6" name="<portlet:namespace />entity_wise_outstanding_q3_6"></td>
								    <td><input type="number" class="form-control entityWiseNPS t4_q4" id="entity_wise_outstanding_q4_6" name="<portlet:namespace />entity_wise_outstanding_q4_6"></td>
								    <td><input type="number" class="form-control entityWiseNPS t4_qOne" id="entity_wise_outstanding_q1One_6" name="<portlet:namespace />entity_wise_outstanding_q1One_6"></td>
								    <td><input type="number" class="form-control entityWiseNPS t4_qTwo" id="entity_wise_outstanding_q2One_6" name="<portlet:namespace />entity_wise_outstanding_q2One_6"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Other (e-NPS)</td>
	                                <td><input type="number" class="form-control entityWiseOther t4_q1" id="entity_wise_outstanding_q1_7" name="<portlet:namespace />entity_wise_outstanding_q1_7"></td>
								    <td><input type="number" class="form-control entityWiseOther t4_q2" id="entity_wise_outstanding_q2_7" name="<portlet:namespace />entity_wise_outstanding_q2_7"></td>
								    <td><input type="number" class="form-control entityWiseOther t4_q3" id="entity_wise_outstanding_q3_7" name="<portlet:namespace />entity_wise_outstanding_q3_7"></td>
								    <td><input type="number" class="form-control entityWiseOther t4_q4" id="entity_wise_outstanding_q4_7" name="<portlet:namespace />entity_wise_outstanding_q4_7"></td>
								    <td><input type="number" class="form-control entityWiseOther t4_qOne" id="entity_wise_outstanding_q1One_7" name="<portlet:namespace />entity_wise_outstanding_q1One_7"></td>
								    <td><input type="number" class="form-control entityWiseOther t4_qTwo" id="entity_wise_outstanding_q2One_7" name="<portlet:namespace />entity_wise_outstanding_q2One_7"></td>
	                            </tr>
	
	                            <tr>
	                                <td>NPS Lite (NLAO/NLCC) </td>
	                                <td><input type="number" class="form-control entityWiseLite t4_q1" id="entity_wise_outstanding_q1_8" name="<portlet:namespace />entity_wise_outstanding_q1_8"></td>
								    <td><input type="number" class="form-control entityWiseLite t4_q2" id="entity_wise_outstanding_q2_8" name="<portlet:namespace />entity_wise_outstanding_q2_8"></td>
								    <td><input type="number" class="form-control entityWiseLite t4_q3" id="entity_wise_outstanding_q3_8" name="<portlet:namespace />entity_wise_outstanding_q3_8"></td>
								    <td><input type="number" class="form-control entityWiseLite t4_q4" id="entity_wise_outstanding_q4_8" name="<portlet:namespace />entity_wise_outstanding_q4_8"></td>
								    <td><input type="number" class="form-control entityWiseLite t4_qOne" id="entity_wise_outstanding_q1One_8" name="<portlet:namespace />entity_wise_outstanding_q1One_8"></td>
								    <td><input type="number" class="form-control entityWiseLite t4_qTwo" id="entity_wise_outstanding_q2One_8" name="<portlet:namespace />entity_wise_outstanding_q2One_8"></td>
	                            </tr>
	
	                            <tr>
	                                <td>APY- SP</td>
	                                <td><input type="number" class="form-control entityWiseAPY t4_q1" id="entity_wise_outstanding_q1_9" name="<portlet:namespace />entity_wise_outstanding_q1_9"></td>
								    <td><input type="number" class="form-control entityWiseAPY t4_q2" id="entity_wise_outstanding_q2_9" name="<portlet:namespace />entity_wise_outstanding_q2_9"></td>
								    <td><input type="number" class="form-control entityWiseAPY t4_q3" id="entity_wise_outstanding_q3_9" name="<portlet:namespace />entity_wise_outstanding_q3_9"></td>
								    <td><input type="number" class="form-control entityWiseAPY t4_q4" id="entity_wise_outstanding_q4_9" name="<portlet:namespace />entity_wise_outstanding_q4_9"></td>
								    <td><input type="number" class="form-control entityWiseAPY t4_qOne" id="entity_wise_outstanding_q1One_9" name="<portlet:namespace />entity_wise_outstanding_q1One_9"></td>
								    <td><input type="number" class="form-control entityWiseAPY t4_qTwo" id="entity_wise_outstanding_q2One_9" name="<portlet:namespace />entity_wise_outstanding_q2One_9"></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>GRAND TOTAL</strong></td>
	                                <td><input type="text" class="form-control grandTotal" id="entity_wise_outstanding_q1_10" name="<portlet:namespace />entity_wise_outstanding_q1_10" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="entity_wise_outstanding_q2_10" name="<portlet:namespace />entity_wise_outstanding_q2_10" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="entity_wise_outstanding_q3_10" name="<portlet:namespace />entity_wise_outstanding_q3_10" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="entity_wise_outstanding_q4_10" name="<portlet:namespace />entity_wise_outstanding_q4_10" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="entity_wise_outstanding_q1One_10" name="<portlet:namespace />entity_wise_outstanding_q1One_10" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="entity_wise_outstanding_q2One_10" name="<portlet:namespace />entity_wise_outstanding_q2One_10" readonly></td>
	                            </tr>
	
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <!-- TABLE 4 : Entity wise Outstanding grievances at the end of quarters (NPS +NPS Lite+ APY) -->
	
	            <div class="table_foura mt-5">
	                <div class="row">
	                    <div class="col-12">
	                        <table class="mt-5 table table-bordered w-100" id="table7">
	                            <tr>
	                                <td class="text-center" colspan=9>
	                                    <strong>TABLE 4(a) : Ageing of entity-wise outstanding grievances at the end of Quarter x (FY <span class="fy">xxxxx</span>) (NPS)</strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td><strong>Entity</strong> </td>
	                                <td><strong>Referrals at the end of Quarter x (FY <span class="fy">xxx</span>)</strong></td>
	                                <td><strong>0-7</strong></td>
	                                <td><strong>8-15</strong></td>
	                                <td><strong>16-31</strong></td>
	                                <td><strong>32-90</strong></td>
	                                <td><strong>91-180</strong></td>
	                                <td><strong>181-365</strong></td>
	                                <td><strong>366 & above</strong></td>
	                            </tr>
	                            <tr>
	                                <td>CRA</td>
	                                <td><input type="number" class="form-control ageingCRA t4a_cra" id="t4a_referrals_0" name="<portlet:namespace />t4a_referrals_0"></td>
								    <td><input type="number" class="form-control ageingCRA t4a_cg" id="t4a_0_7_0" name="<portlet:namespace />t4a_0_7_0"></td>
								    <td><input type="number" class="form-control ageingCRA t4a_sg" id="t4a_8_15_0" name="<portlet:namespace />t4a_8_15_0"></td>
								    <td><input type="number" class="form-control ageingCRA t4a_pop" id="t4a_16_31_0" name="<portlet:namespace />t4a_16_31_0"></td>
								    <td><input type="number" class="form-control ageingCRA t4a_corp" id="t4a_32_90_0" name="<portlet:namespace />t4a_32_90_0"></td>
								    <td><input type="number" class="form-control ageingCRA t4a_tb" id="t4a_91_180_0" name="<portlet:namespace />t4a_91_180_0"></td>
								    <td><input type="number" class="form-control ageingCRA t4a_nps" id="t4a_185_365_0" name="<portlet:namespace />t4a_185_365_0"></td>
								    <td><input type="number" class="form-control ageingCRA t4a_others" id="t4a_366_and_above_0" name="<portlet:namespace />t4a_366_and_above_0"></td>
	                            </tr>
	                            <tr>
	                                <td>CG Nodal Office</td>
	                                <td><input type="number" class="form-control ageingCG t4a_cra" id="t4a_referrals_1" name="<portlet:namespace />t4a_referrals_1"></td>
								    <td><input type="number" class="form-control ageingCG t4a_cg" id="t4a_0_7_1" name="<portlet:namespace />t4a_0_7_1"></td>
								    <td><input type="number" class="form-control ageingCG t4a_sg" id="t4a_8_15_1" name="<portlet:namespace />t4a_8_15_1"></td>
								    <td><input type="number" class="form-control ageingCG t4a_pop" id="t4a_16_31_1" name="<portlet:namespace />t4a_16_31_1"></td>
								    <td><input type="number" class="form-control ageingCG t4a_corp" id="t4a_32_90_1" name="<portlet:namespace />t4a_32_90_1"></td>
								    <td><input type="number" class="form-control ageingCG t4a_tb" id="t4a_91_180_1" name="<portlet:namespace />t4a_91_180_1"></td>
								    <td><input type="number" class="form-control ageingCG t4a_nps" id="t4a_185_365_1" name="<portlet:namespace />t4a_185_365_1"></td>
								    <td><input type="number" class="form-control ageingCG t4a_others" id="t4a_366_and_above_1" name="<portlet:namespace />t4a_366_and_above_1"></td>
	                            </tr>
	                            <tr>
	                                <td>SG Nodal Office</td>
	                                <td><input type="number" class="form-control ageingSG t4a_cra" id="t4a_referrals_2" name="<portlet:namespace />t4a_referrals_2"></td>
								    <td><input type="number" class="form-control ageingSG t4a_cg" id="t4a_0_7_2" name="<portlet:namespace />t4a_0_7_2"></td>
								    <td><input type="number" class="form-control ageingSG t4a_sg" id="t4a_8_15_2" name="<portlet:namespace />t4a_8_15_2"></td>
								    <td><input type="number" class="form-control ageingSG t4a_pop" id="t4a_16_31_2" name="<portlet:namespace />t4a_16_31_2"></td>
								    <td><input type="number" class="form-control ageingSG t4a_corp" id="t4a_32_90_2" name="<portlet:namespace />t4a_32_90_2"></td>
								    <td><input type="number" class="form-control ageingSG t4a_tb" id="t4a_91_180_2" name="<portlet:namespace />t4a_91_180_2"></td>
								    <td><input type="number" class="form-control ageingSG t4a_nps" id="t4a_185_365_2" name="<portlet:namespace />t4a_185_365_2"></td>
								    <td><input type="number" class="form-control ageingSG t4a_others" id="t4a_366_and_above_2" name="<portlet:namespace />t4a_366_and_above_2"></td>
	                            </tr>
	                            <tr>
	                                <td>POP-SP</td>
	                                <td><input type="number" class="form-control ageingPOP t4a_cra" id="t4a_referrals_3" name="<portlet:namespace />t4a_referrals_3"></td>
								    <td><input type="number" class="form-control ageingPOP t4a_cg" id="t4a_0_7_3" name="<portlet:namespace />t4a_0_7_3"></td>
								    <td><input type="number" class="form-control ageingPOP t4a_sg" id="t4a_8_15_3" name="<portlet:namespace />t4a_8_15_3"></td>
								    <td><input type="number" class="form-control ageingPOP t4a_pop" id="t4a_16_31_3" name="<portlet:namespace />t4a_16_31_3"></td>
								    <td><input type="number" class="form-control ageingPOP t4a_corp" id="t4a_32_90_3" name="<portlet:namespace />t4a_32_90_3"></td>
								    <td><input type="number" class="form-control ageingPOP t4a_tb" id="t4a_91_180_3" name="<portlet:namespace />t4a_91_180_3"></td>
								    <td><input type="number" class="form-control ageingPOP t4a_nps" id="t4a_185_365_3" name="<portlet:namespace />t4a_185_365_3"></td>
								    <td><input type="number" class="form-control ageingPOP t4a_others" id="t4a_366_and_above_3" name="<portlet:namespace />t4a_366_and_above_3"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Corporate</td>
	                                <td><input type="number" class="form-control ageingCorp t4a_cra" id="t4a_referrals_4" name="<portlet:namespace />t4a_referrals_4"></td>
								    <td><input type="number" class="form-control ageingCorp t4a_cg" id="t4a_0_7_4" name="<portlet:namespace />t4a_0_7_4"></td>
								    <td><input type="number" class="form-control ageingCorp t4a_sg" id="t4a_8_15_4" name="<portlet:namespace />t4a_8_15_4"></td>
								    <td><input type="number" class="form-control ageingCorp t4a_pop" id="t4a_16_31_4" name="<portlet:namespace />t4a_16_31_4"></td>
								    <td><input type="number" class="form-control ageingCorp t4a_corp" id="t4a_32_90_4" name="<portlet:namespace />t4a_32_90_4"></td>
								    <td><input type="number" class="form-control ageingCorp t4a_tb" id="t4a_91_180_4" name="<portlet:namespace />t4a_91_180_4"></td>
								    <td><input type="number" class="form-control ageingCorp t4a_nps" id="t4a_185_365_4" name="<portlet:namespace />t4a_185_365_4"></td>
								    <td><input type="number" class="form-control ageingCorp t4a_others" id="t4a_366_and_above_4" name="<portlet:namespace />t4a_366_and_above_4"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Trustee Bank</td>
	                                <td><input type="number" class="form-control ageingTB t4a_cra" id="t4a_referrals_5" name="<portlet:namespace />t4a_referrals_5"></td>
								    <td><input type="number" class="form-control ageingTB t4a_cg" id="t4a_0_7_5" name="<portlet:namespace />t4a_0_7_5"></td>
								    <td><input type="number" class="form-control ageingTB t4a_sg" id="t4a_8_15_5" name="<portlet:namespace />t4a_8_15_5"></td>
								    <td><input type="number" class="form-control ageingTB t4a_pop" id="t4a_16_31_5" name="<portlet:namespace />t4a_16_31_5"></td>
								    <td><input type="number" class="form-control ageingTB t4a_corp" id="t4a_32_90_5" name="<portlet:namespace />t4a_32_90_5"></td>
								    <td><input type="number" class="form-control ageingTB t4a_tb" id="t4a_91_180_5" name="<portlet:namespace />t4a_91_180_5"></td>
								    <td><input type="number" class="form-control ageingTB t4a_nps" id="t4a_185_365_5" name="<portlet:namespace />t4a_185_365_5"></td>
								    <td><input type="number" class="form-control ageingTB t4a_others" id="t4a_366_and_above_5" name="<portlet:namespace />t4a_366_and_above_5"></td>
	                            </tr>
	
	                            <tr>
	                                <td>NPS Trust</td>
	                                <td><input type="number" class="form-control ageingNPS t4a_cra" id="t4a_referrals_6" name="<portlet:namespace />t4a_referrals_6"></td>
								    <td><input type="number" class="form-control ageingNPS t4a_cg" id="t4a_0_7_6" name="<portlet:namespace />t4a_0_7_6"></td>
								    <td><input type="number" class="form-control ageingNPS t4a_sg" id="t4a_8_15_6" name="<portlet:namespace />t4a_8_15_6"></td>
								    <td><input type="number" class="form-control ageingNPS t4a_pop" id="t4a_16_31_6" name="<portlet:namespace />t4a_16_31_6"></td>
								    <td><input type="number" class="form-control ageingNPS t4a_corp" id="t4a_32_90_6" name="<portlet:namespace />t4a_32_90_6"></td>
								    <td><input type="number" class="form-control ageingNPS t4a_tb" id="t4a_91_180_6" name="<portlet:namespace />t4a_91_180_6"></td>
								    <td><input type="number" class="form-control ageingNPS t4a_nps" id="t4a_185_365_6" name="<portlet:namespace />t4a_185_365_6"></td>
								    <td><input type="number" class="form-control ageingNPS t4a_others" id="t4a_366_and_above_6" name="<portlet:namespace />t4a_366_and_above_6"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Others (e-NPS)</td>
	                                <td><input type="number" class="form-control ageingOthers t4a_cra" id="t4a_referrals_7" name="<portlet:namespace />t4a_referrals_7"></td>
								    <td><input type="number" class="form-control ageingOthers t4a_cg" id="t4a_0_7_7" name="<portlet:namespace />t4a_0_7_7"></td>
								    <td><input type="number" class="form-control ageingOthers t4a_sg" id="t4a_8_15_7" name="<portlet:namespace />t4a_8_15_7"></td>
								    <td><input type="number" class="form-control ageingOthers t4a_pop" id="t4a_16_31_7" name="<portlet:namespace />t4a_16_31_7"></td>
								    <td><input type="number" class="form-control ageingOthers t4a_corp" id="t4a_32_90_7" name="<portlet:namespace />t4a_32_90_7"></td>
								    <td><input type="number" class="form-control ageingOthers t4a_tb" id="t4a_91_180_7" name="<portlet:namespace />t4a_91_180_7"></td>
								    <td><input type="number" class="form-control ageingOthers t4a_nps" id="t4a_185_365_7" name="<portlet:namespace />t4a_185_365_7"></td>
								    <td><input type="number" class="form-control ageingOthers t4a_others" id="t4a_366_and_above_7" name="<portlet:namespace />t4a_366_and_above_7"></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>GRAND TOTAL</strong></td>
	                                <td><input type="text" class="form-control grandTotal" id="t4a_referrals_8" name="<portlet:namespace />t4a_referrals_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4a_0_7_8" name="<portlet:namespace />t4a_0_7_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4a_8_15_8" name="<portlet:namespace />t4a_8_15_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4a_16_31_8" name="<portlet:namespace />t4a_16_31_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4a_32_90_8" name="<portlet:namespace />t4a_32_90_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4a_91_180_8" name="<portlet:namespace />t4a_91_180_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4a_185_365_8" name="<portlet:namespace />t4a_185_365_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4a_366_and_above_8" name="<portlet:namespace />t4a_366_and_above_8" readonly></td>
	                            </tr>
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <!-- TABLE 4(a) : Ageing of entity-wise outstanding grievances at the end of Quarter x (FYxxxxx ) (NPS) -->
	
	            <div class="table_fourb mt-5">
	                <div class="row">
	                    <div class="col-12">
	                        <table class="mt-5 table table-bordered w-100" id="table8">
	                            <tr>
	                                <td class="text-center" colspan=9>
	                                    <strong>TABLE 4(b) : Ageing of category-wise grievances at the end of Quarter X (FY <span class="fy">xxxxx</span>) (NPS)</strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td><strong>Category</strong> </td>
	                                <td><strong>Referrals at the end of Quarter X (FY <span class="fy">xxxx</span>)</strong></td>
	                                <td><strong>0-7</strong></td>
	                                <td><strong>8-15</strong></td>
	                                <td><strong>16-31</strong></td>
	                                <td><strong>32-90</strong></td>
	                                <td><strong>91-180</strong></td>
	                                <td><strong>181-365</strong></td>
	                                <td><strong>366 & above</strong></td>
	                            </tr>
	                            <tr>
	                                <td>Against NPS Trust</td>
	                                <td><input type="number" class="form-control catWiseAgainstNPS t4b_1" id="t4b_referrals_0" name="<portlet:namespace />t4b_referrals_0"></td>
									<td><input type="number" class="form-control catWiseAgainstNPS t4b_2" id="t4b_0_7_0" name="<portlet:namespace />t4b_0_7_0"></td>
								    <td><input type="number" class="form-control catWiseAgainstNPS t4b_3" id="t4b_8_15_0" name="<portlet:namespace />t4b_8_15_0"></td>
								    <td><input type="number" class="form-control catWiseAgainstNPS t4b_4" id="t4b_16_31_0" name="<portlet:namespace />t4b_16_31_0"></td>
								    <td><input type="number" class="form-control catWiseAgainstNPS t4b_5" id="t4b_32_90_0" name="<portlet:namespace />t4b_32_90_0"></td>
								    <td><input type="number" class="form-control catWiseAgainstNPS t4b_6" id="t4b_91_180_0" name="<portlet:namespace />t4b_91_180_0"></td>
								    <td><input type="number" class="form-control catWiseAgainstNPS t4b_7" id="t4b_185_365_0" name="<portlet:namespace />t4b_185_365_0"></td>
								    <td><input type="number" class="form-control catWiseAgainstNPS t4b_8" id="t4b_366_and_above_0" name="<portlet:namespace />t4b_366_and_above_0"></td>
	                            </tr>
	                            <tr>
	                                <td>Contribution amount not reflected in account</td>
	                                <td><input type="number" class="form-control catWiseAmtNotReflected t4b_1" id="t4b_referrals_1" name="<portlet:namespace />t4b_referrals_1"></td>
									<td><input type="number" class="form-control catWiseAmtNotReflected t4b_2" id="t4b_0_7_1" name="<portlet:namespace />t4b_0_7_1"></td>
								    <td><input type="number" class="form-control catWiseAmtNotReflected t4b_3" id="t4b_8_15_1" name="<portlet:namespace />t4b_8_15_1"></td>
								    <td><input type="number" class="form-control catWiseAmtNotReflected t4b_4" id="t4b_16_31_1" name="<portlet:namespace />t4b_16_31_1"></td>
								    <td><input type="number" class="form-control catWiseAmtNotReflected t4b_5" id="t4b_32_90_1" name="<portlet:namespace />t4b_32_90_1"></td>
								    <td><input type="number" class="form-control catWiseAmtNotReflected t4b_6" id="t4b_91_180_1" name="<portlet:namespace />t4b_91_180_1"></td>
								    <td><input type="number" class="form-control catWiseAmtNotReflected t4b_7" id="t4b_185_365_1" name="<portlet:namespace />t4b_185_365_1"></td>
								    <td><input type="number" class="form-control catWiseAmtNotReflected t4b_8" id="t4b_366_and_above_1" name="<portlet:namespace />t4b_366_and_above_1"></td>
	                            </tr>
	                            <tr>
	                                <td>Delays in Issuance of PRAN Cards</td>
	                                <td><input type="number" class="form-control catWisePran t4b_1" id="t4b_referrals_2" name="<portlet:namespace />t4b_referrals_2"></td>
									<td><input type="number" class="form-control catWisePran t4b_2" id="t4b_0_7_2" name="<portlet:namespace />t4b_0_7_2"></td>
								    <td><input type="number" class="form-control catWisePran t4b_3" id="t4b_8_15_2" name="<portlet:namespace />t4b_8_15_2"></td>
								    <td><input type="number" class="form-control catWisePran t4b_4" id="t4b_16_31_2" name="<portlet:namespace />t4b_16_31_2"></td>
								    <td><input type="number" class="form-control catWisePran t4b_5" id="t4b_32_90_2" name="<portlet:namespace />t4b_32_90_2"></td>
								    <td><input type="number" class="form-control catWisePran t4b_6" id="t4b_91_180_2" name="<portlet:namespace />t4b_91_180_2"></td>
								    <td><input type="number" class="form-control catWisePran t4b_7" id="t4b_185_365_2" name="<portlet:namespace />t4b_185_365_2"></td>
								    <td><input type="number" class="form-control catWisePran t4b_8" id="t4b_366_and_above_2" name="<portlet:namespace />t4b_366_and_above_2"></td>
	                            </tr>
	                            <tr>
	                                <td>Delays in Uploading of Contribution Amounts</td>
	                               	<td><input type="number" class="form-control catWiseUploadingAmt t4b_1" id="t4b_referrals_3" name="<portlet:namespace />t4b_referrals_3"></td>
									<td><input type="number" class="form-control catWiseUploadingAmt t4b_2" id="t4b_0_7_3" name="<portlet:namespace />t4b_0_7_3"></td>
								    <td><input type="number" class="form-control catWiseUploadingAmt t4b_3" id="t4b_8_15_3" name="<portlet:namespace />t4b_8_15_3"></td>
								    <td><input type="number" class="form-control catWiseUploadingAmt t4b_4" id="t4b_16_31_3" name="<portlet:namespace />t4b_16_31_3"></td>
								    <td><input type="number" class="form-control catWiseUploadingAmt t4b_5" id="t4b_32_90_3" name="<portlet:namespace />t4b_32_90_3"></td>
								    <td><input type="number" class="form-control catWiseUploadingAmt t4b_6" id="t4b_91_180_3" name="<portlet:namespace />t4b_91_180_3"></td>
								    <td><input type="number" class="form-control catWiseUploadingAmt t4b_7" id="t4b_185_365_3" name="<portlet:namespace />t4b_185_365_3"></td>
								    <td><input type="number" class="form-control catWiseUploadingAmt t4b_8" id="t4b_366_and_above_3" name="<portlet:namespace />t4b_366_and_above_3"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Email/SMS alerts not received</td>
	                                <td><input type="number" class="form-control catWiseEmailAlert t4b_1" id="t4b_referrals_4" name="<portlet:namespace />t4b_referrals_4"></td>
									<td><input type="number" class="form-control catWiseEmailAlert t4b_2" id="t4b_0_7_4" name="<portlet:namespace />t4b_0_7_4"></td>
								    <td><input type="number" class="form-control catWiseEmailAlert t4b_3" id="t4b_8_15_4" name="<portlet:namespace />t4b_8_15_4"></td>
								    <td><input type="number" class="form-control catWiseEmailAlert t4b_4" id="t4b_16_31_4" name="<portlet:namespace />t4b_16_31_4"></td>
								    <td><input type="number" class="form-control catWiseEmailAlert t4b_5" id="t4b_32_90_4" name="<portlet:namespace />t4b_32_90_4"></td>
								    <td><input type="number" class="form-control catWiseEmailAlert t4b_6" id="t4b_91_180_4" name="<portlet:namespace />t4b_91_180_4"></td>
								    <td><input type="number" class="form-control catWiseEmailAlert t4b_7" id="t4b_185_365_4" name="<portlet:namespace />t4b_185_365_4"></td>
								    <td><input type="number" class="form-control catWiseEmailAlert t4b_8" id="t4b_366_and_above_4" name="<portlet:namespace />t4b_366_and_above_4"></td>
	                            </tr>
	
	                            <tr>
	                                <td>General Query</td>
	                                <td><input type="number" class="form-control catWiseGeneralQuery t4b_1" id="t4b_referrals_5" name="<portlet:namespace />t4b_referrals_5"></td>
									<td><input type="number" class="form-control catWiseGeneralQuery t4b_2" id="t4b_0_7_5" name="<portlet:namespace />t4b_0_7_5"></td>
								    <td><input type="number" class="form-control catWiseGeneralQuery t4b_3" id="t4b_8_15_5" name="<portlet:namespace />t4b_8_15_5"></td>
								    <td><input type="number" class="form-control catWiseGeneralQuery t4b_4" id="t4b_16_31_5" name="<portlet:namespace />t4b_16_31_5"></td>
								    <td><input type="number" class="form-control catWiseGeneralQuery t4b_5" id="t4b_32_90_5" name="<portlet:namespace />t4b_32_90_5"></td>
								    <td><input type="number" class="form-control catWiseGeneralQuery t4b_6" id="t4b_91_180_5" name="<portlet:namespace />t4b_91_180_5"></td>
								    <td><input type="number" class="form-control catWiseGeneralQuery t4b_7" id="t4b_185_365_5" name="<portlet:namespace />t4b_185_365_5"></td>
								    <td><input type="number" class="form-control catWiseGeneralQuery t4b_8" id="t4b_366_and_above_5" name="<portlet:namespace />t4b_366_and_above_5"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Grievance of BANK against PAO/POP SP / CRA</td>
	                                <td><input type="number" class="form-control catWiseGrievanceOfBank t4b_1" id="t4b_referrals_6" name="<portlet:namespace />t4b_referrals_6"></td>
									<td><input type="number" class="form-control catWiseGrievanceOfBank t4b_2" id="t4b_0_7_6" name="<portlet:namespace />t4b_0_7_6"></td>
								    <td><input type="number" class="form-control catWiseGrievanceOfBank t4b_3" id="t4b_8_15_6" name="<portlet:namespace />t4b_8_15_6"></td>
								    <td><input type="number" class="form-control catWiseGrievanceOfBank t4b_4" id="t4b_16_31_6" name="<portlet:namespace />t4b_16_31_6"></td>
								    <td><input type="number" class="form-control catWiseGrievanceOfBank t4b_5" id="t4b_32_90_6" name="<portlet:namespace />t4b_32_90_6"></td>
								    <td><input type="number" class="form-control catWiseGrievanceOfBank t4b_6" id="t4b_91_180_6" name="<portlet:namespace />t4b_91_180_6"></td>
								    <td><input type="number" class="form-control catWiseGrievanceOfBank t4b_7" id="t4b_185_365_6" name="<portlet:namespace />t4b_185_365_6"></td>
								    <td><input type="number" class="form-control catWiseGrievanceOfBank t4b_8" id="t4b_366_and_above_6" name="<portlet:namespace />t4b_366_and_above_6"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Incorrect contribution amount reflected</td>
	                                <td><input type="number" class="form-control catWiseIncorrectAmt t4b_1" id="t4b_referrals_7" name="<portlet:namespace />t4b_referrals_7"></td>
									<td><input type="number" class="form-control catWiseIncorrectAmt t4b_2" id="t4b_0_7_7" name="<portlet:namespace />t4b_0_7_7"></td>
								    <td><input type="number" class="form-control catWiseIncorrectAmt t4b_3" id="t4b_8_15_7" name="<portlet:namespace />t4b_8_15_7"></td>
								    <td><input type="number" class="form-control catWiseIncorrectAmt t4b_4" id="t4b_16_31_7" name="<portlet:namespace />t4b_16_31_7"></td>
								    <td><input type="number" class="form-control catWiseIncorrectAmt t4b_5" id="t4b_32_90_7" name="<portlet:namespace />t4b_32_90_7"></td>
								    <td><input type="number" class="form-control catWiseIncorrectAmt t4b_6" id="t4b_91_180_7" name="<portlet:namespace />t4b_91_180_7"></td>
								    <td><input type="number" class="form-control catWiseIncorrectAmt t4b_7" id="t4b_185_365_7" name="<portlet:namespace />t4b_185_365_7"></td>
								    <td><input type="number" class="form-control catWiseIncorrectAmt t4b_8" id="t4b_366_and_above_7" name="<portlet:namespace />t4b_366_and_above_7"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Incorrect Processing of Subscriber Details</td>
	                                <td><input type="number" class="form-control catWiseSubscriberDetails t4b_1" id="t4b_referrals_8" name="<portlet:namespace />t4b_referrals_8"></td>
									<td><input type="number" class="form-control catWiseSubscriberDetails t4b_2" id="t4b_0_7_8" name="<portlet:namespace />t4b_0_7_8"></td>
								    <td><input type="number" class="form-control catWiseSubscriberDetails t4b_3" id="t4b_8_15_8" name="<portlet:namespace />t4b_8_15_8"></td>
								    <td><input type="number" class="form-control catWiseSubscriberDetails t4b_4" id="t4b_16_31_8" name="<portlet:namespace />t4b_16_31_8"></td>
								    <td><input type="number" class="form-control catWiseSubscriberDetails t4b_5" id="t4b_32_90_8" name="<portlet:namespace />t4b_32_90_8"></td>
								    <td><input type="number" class="form-control catWiseSubscriberDetails t4b_6" id="t4b_91_180_8" name="<portlet:namespace />t4b_91_180_8"></td>
								    <td><input type="number" class="form-control catWiseSubscriberDetails t4b_7" id="t4b_185_365_8" name="<portlet:namespace />t4b_185_365_8"></td>
								    <td><input type="number" class="form-control catWiseSubscriberDetails t4b_8" id="t4b_366_and_above_8" name="<portlet:namespace />t4b_366_and_above_8"></td>
	                            </tr>
	
	                            <tr>
	                                <td>I-PIN, T-PIN Related - Others</td>
	                                <td><input type="number" class="form-control catWiseIpin t4b_1" id="t4b_referrals_9" name="<portlet:namespace />t4b_referrals_9"></td>
									<td><input type="number" class="form-control catWiseIpin t4b_2" id="t4b_0_7_9" name="<portlet:namespace />t4b_0_7_9"></td>
								    <td><input type="number" class="form-control catWiseIpin t4b_3" id="t4b_8_15_9" name="<portlet:namespace />t4b_8_15_9"></td>
								    <td><input type="number" class="form-control catWiseIpin t4b_4" id="t4b_16_31_9" name="<portlet:namespace />t4b_16_31_9"></td>
								    <td><input type="number" class="form-control catWiseIpin t4b_5" id="t4b_32_90_9" name="<portlet:namespace />t4b_32_90_9"></td>
								    <td><input type="number" class="form-control catWiseIpin t4b_6" id="t4b_91_180_9" name="<portlet:namespace />t4b_91_180_9"></td>
								    <td><input type="number" class="form-control catWiseIpin t4b_7" id="t4b_185_365_9" name="<portlet:namespace />t4b_185_365_9"></td>
								    <td><input type="number" class="form-control catWiseIpin t4b_8" id="t4b_366_and_above_9" name="<portlet:namespace />t4b_366_and_above_9"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Not Processed/Delay in Processing Subscriber Changes Request</td>
	                                <td><input type="number" class="form-control catWiseSubscriberChanges t4b_1" id="t4b_referrals_10" name="<portlet:namespace />t4b_referrals_10"></td>
									<td><input type="number" class="form-control catWiseSubscriberChanges t4b_2" id="t4b_0_7_10" name="<portlet:namespace />t4b_0_7_10"></td>
								    <td><input type="number" class="form-control catWiseSubscriberChanges t4b_3" id="t4b_8_15_10" name="<portlet:namespace />t4b_8_15_10"></td>
								    <td><input type="number" class="form-control catWiseSubscriberChanges t4b_4" id="t4b_16_31_10" name="<portlet:namespace />t4b_16_31_10"></td>
								    <td><input type="number" class="form-control catWiseSubscriberChanges t4b_5" id="t4b_32_90_10" name="<portlet:namespace />t4b_32_90_10"></td>
								    <td><input type="number" class="form-control catWiseSubscriberChanges t4b_6" id="t4b_91_180_10" name="<portlet:namespace />t4b_91_180_10"></td>
								    <td><input type="number" class="form-control catWiseSubscriberChanges t4b_7" id="t4b_185_365_10" name="<portlet:namespace />t4b_185_365_10"></td>
								    <td><input type="number" class="form-control catWiseSubscriberChanges t4b_8" id="t4b_366_and_above_10" name="<portlet:namespace />t4b_366_and_above_10"></td>
	                            </tr>
	
	                            <tr>
	                                <td>PRAN Related-Error in Details</td>
	                                <td><input type="number" class="form-control catWiseDetailsError t4b_1" id="t4b_referrals_11" name="<portlet:namespace />t4b_referrals_11"></td>
									<td><input type="number" class="form-control catWiseDetailsError t4b_2" id="t4b_0_7_11" name="<portlet:namespace />t4b_0_7_11"></td>
								    <td><input type="number" class="form-control catWiseDetailsError t4b_3" id="t4b_8_15_11" name="<portlet:namespace />t4b_8_15_11"></td>
								    <td><input type="number" class="form-control catWiseDetailsError t4b_4" id="t4b_16_31_11" name="<portlet:namespace />t4b_16_31_11"></td>
								    <td><input type="number" class="form-control catWiseDetailsError t4b_5" id="t4b_32_90_11" name="<portlet:namespace />t4b_32_90_11"></td>
								    <td><input type="number" class="form-control catWiseDetailsError t4b_6" id="t4b_91_180_11" name="<portlet:namespace />t4b_91_180_11"></td>
								    <td><input type="number" class="form-control catWiseDetailsError t4b_7" id="t4b_185_365_11" name="<portlet:namespace />t4b_185_365_11"></td>
								    <td><input type="number" class="form-control catWiseDetailsError t4b_8" id="t4b_366_and_above_11" name="<portlet:namespace />t4b_366_and_above_11"></td>
	                            </tr>
	
	                            <tr>
	                                <td>PRAN Card Related-PRAN card not received</td>
	                                <td><input type="number" class="form-control catWiseCardNotReceived t4b_1" id="t4b_referrals_12" name="<portlet:namespace />t4b_referrals_12"></td>
									<td><input type="number" class="form-control catWiseCardNotReceived t4b_2" id="t4b_0_7_12" name="<portlet:namespace />t4b_0_7_12"></td>
								    <td><input type="number" class="form-control catWiseCardNotReceived t4b_3" id="t4b_8_15_12" name="<portlet:namespace />t4b_8_15_12"></td>
								    <td><input type="number" class="form-control catWiseCardNotReceived t4b_4" id="t4b_16_31_12" name="<portlet:namespace />t4b_16_31_12"></td>
								    <td><input type="number" class="form-control catWiseCardNotReceived t4b_5" id="t4b_32_90_12" name="<portlet:namespace />t4b_32_90_12"></td>
								    <td><input type="number" class="form-control catWiseCardNotReceived t4b_6" id="t4b_91_180_12" name="<portlet:namespace />t4b_91_180_12"></td>
								    <td><input type="number" class="form-control catWiseCardNotReceived t4b_7" id="t4b_185_365_12" name="<portlet:namespace />t4b_185_365_12"></td>
								    <td><input type="number" class="form-control catWiseCardNotReceived t4b_8" id="t4b_366_and_above_12" name="<portlet:namespace />t4b_366_and_above_12"></td>
	                            </tr>
	
	                            <tr>
	                                <td>PRAN Related-Account Related(Tier I & Tier II)</td>
	                                <td><input type="number" class="form-control catWiseAccount t4b_1" id="t4b_referrals_13" name="<portlet:namespace />t4b_referrals_13"></td>
									<td><input type="number" class="form-control catWiseAccount t4b_2" id="t4b_0_7_13" name="<portlet:namespace />t4b_0_7_13"></td>
								    <td><input type="number" class="form-control catWiseAccount t4b_3" id="t4b_8_15_13" name="<portlet:namespace />t4b_8_15_13"></td>
								    <td><input type="number" class="form-control catWiseAccount t4b_4" id="t4b_16_31_13" name="<portlet:namespace />t4b_16_31_13"></td>
								    <td><input type="number" class="form-control catWiseAccount t4b_5" id="t4b_32_90_13" name="<portlet:namespace />t4b_32_90_13"></td>
								    <td><input type="number" class="form-control catWiseAccount t4b_6" id="t4b_91_180_13" name="<portlet:namespace />t4b_91_180_13"></td>
								    <td><input type="number" class="form-control catWiseAccount t4b_7" id="t4b_185_365_13" name="<portlet:namespace />t4b_185_365_13"></td>
								    <td><input type="number" class="form-control catWiseAccount t4b_8" id="t4b_366_and_above_13" name="<portlet:namespace />t4b_366_and_above_13"></td>
	                            </tr>
	
	                            <tr>
	                                <td>PRAN Card Related - Other</td>
	                                <td><input type="number" class="form-control catWiseOther t4b_1" id="t4b_referrals_14" name="<portlet:namespace />t4b_referrals_14"></td>
									<td><input type="number" class="form-control catWiseOther t4b_2" id="t4b_0_7_14" name="<portlet:namespace />t4b_0_7_14"></td>
								    <td><input type="number" class="form-control catWiseOther t4b_3" id="t4b_8_15_14" name="<portlet:namespace />t4b_8_15_14"></td>
								    <td><input type="number" class="form-control catWiseOther t4b_4" id="t4b_16_31_14" name="<portlet:namespace />t4b_16_31_14"></td>
								    <td><input type="number" class="form-control catWiseOther t4b_5" id="t4b_32_90_14" name="<portlet:namespace />t4b_32_90_14"></td>
								    <td><input type="number" class="form-control catWiseOther t4b_6" id="t4b_91_180_14" name="<portlet:namespace />t4b_91_180_14"></td>
								    <td><input type="number" class="form-control catWiseOther t4b_7" id="t4b_185_365_14" name="<portlet:namespace />t4b_185_365_14"></td>
								    <td><input type="number" class="form-control catWiseOther t4b_8" id="t4b_366_and_above_14" name="<portlet:namespace />t4b_366_and_above_14"></td>
	                            </tr>
	
	                            <tr>
	                                <td>SOT Related</td>
	                                <td><input type="number" class="form-control catWiseSOT t4b_1" id="t4b_referrals_15" name="<portlet:namespace />t4b_referrals_15"></td>
									<td><input type="number" class="form-control catWiseSOT t4b_2" id="t4b_0_7_15" name="<portlet:namespace />t4b_0_7_15"></td>
								    <td><input type="number" class="form-control catWiseSOT t4b_3" id="t4b_8_15_15" name="<portlet:namespace />t4b_8_15_15"></td>
								    <td><input type="number" class="form-control catWiseSOT t4b_4" id="t4b_16_31_15" name="<portlet:namespace />t4b_16_31_15"></td>
								    <td><input type="number" class="form-control catWiseSOT t4b_5" id="t4b_32_90_15" name="<portlet:namespace />t4b_32_90_15"></td>
								    <td><input type="number" class="form-control catWiseSOT t4b_6" id="t4b_91_180_15" name="<portlet:namespace />t4b_91_180_15"></td>
								    <td><input type="number" class="form-control catWiseSOT t4b_7" id="t4b_185_365_15" name="<portlet:namespace />t4b_185_365_15"></td>
								    <td><input type="number" class="form-control catWiseSOT t4b_8" id="t4b_366_and_above_15" name="<portlet:namespace />t4b_366_and_above_15"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Tier II related</td>
	                                <td><input type="number" class="form-control catWiseTierTwo t4b_1" id="t4b_referrals_16" name="<portlet:namespace />t4b_referrals_16"></td>
									<td><input type="number" class="form-control catWiseTierTwo t4b_2" id="t4b_0_7_16" name="<portlet:namespace />t4b_0_7_16"></td>
								    <td><input type="number" class="form-control catWiseTierTwo t4b_3" id="t4b_8_15_16" name="<portlet:namespace />t4b_8_15_16"></td>
								    <td><input type="number" class="form-control catWiseTierTwo t4b_4" id="t4b_16_31_16" name="<portlet:namespace />t4b_16_31_16"></td>
								    <td><input type="number" class="form-control catWiseTierTwo t4b_5" id="t4b_32_90_16" name="<portlet:namespace />t4b_32_90_16"></td>
								    <td><input type="number" class="form-control catWiseTierTwo t4b_6" id="t4b_91_180_16" name="<portlet:namespace />t4b_91_180_16"></td>
								    <td><input type="number" class="form-control catWiseTierTwo t4b_7" id="t4b_185_365_16" name="<portlet:namespace />t4b_185_365_16"></td>
								    <td><input type="number" class="form-control catWiseTierTwo t4b_8" id="t4b_366_and_above_16" name="<portlet:namespace />t4b_366_and_above_16"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Withdrawal Related</td>
	                               	<td><input type="number" class="form-control catWiseWithdrawal t4b_1" id="t4b_referrals_17" name="<portlet:namespace />t4b_referrals_17"></td>
									<td><input type="number" class="form-control catWiseWithdrawal t4b_2" id="t4b_0_7_17" name="<portlet:namespace />t4b_0_7_17"></td>
								    <td><input type="number" class="form-control catWiseWithdrawal t4b_3" id="t4b_8_15_17" name="<portlet:namespace />t4b_8_15_17"></td>
								    <td><input type="number" class="form-control catWiseWithdrawal t4b_4" id="t4b_16_31_17" name="<portlet:namespace />t4b_16_31_17"></td>
								    <td><input type="number" class="form-control catWiseWithdrawal t4b_5" id="t4b_32_90_17" name="<portlet:namespace />t4b_32_90_17"></td>
								    <td><input type="number" class="form-control catWiseWithdrawal t4b_6" id="t4b_91_180_17" name="<portlet:namespace />t4b_91_180_17"></td>
								    <td><input type="number" class="form-control catWiseWithdrawal t4b_7" id="t4b_185_365_17" name="<portlet:namespace />t4b_185_365_17"></td>
								    <td><input type="number" class="form-control catWiseWithdrawal t4b_8" id="t4b_366_and_above_17" name="<portlet:namespace />t4b_366_and_above_17"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Death withdrawal not initiated / not authorised / amount not received</td>
	                                <td><input type="number" class="form-control catWiseDeathWithdrawal t4b_1" id="t4b_referrals_18" name="<portlet:namespace />t4b_referrals_18"></td>
									<td><input type="number" class="form-control catWiseDeathWithdrawal t4b_2" id="t4b_0_7_18" name="<portlet:namespace />t4b_0_7_18"></td>
								    <td><input type="number" class="form-control catWiseDeathWithdrawal t4b_3" id="t4b_8_15_18" name="<portlet:namespace />t4b_8_15_18"></td>
								    <td><input type="number" class="form-control catWiseDeathWithdrawal t4b_4" id="t4b_16_31_18" name="<portlet:namespace />t4b_16_31_18"></td>
								    <td><input type="number" class="form-control catWiseDeathWithdrawal t4b_5" id="t4b_32_90_18" name="<portlet:namespace />t4b_32_90_18"></td>
								    <td><input type="number" class="form-control catWiseDeathWithdrawal t4b_6" id="t4b_91_180_18" name="<portlet:namespace />t4b_91_180_18"></td>
								    <td><input type="number" class="form-control catWiseDeathWithdrawal t4b_7" id="t4b_185_365_18" name="<portlet:namespace />t4b_185_365_18"></td>
								    <td><input type="number" class="form-control catWiseDeathWithdrawal t4b_8" id="t4b_366_and_above_18" name="<portlet:namespace />t4b_366_and_above_18"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Exit not initiated / not authorised / amount not received</td>
	                                <td><input type="number" class="form-control catWiseExit t4b_1" id="t4b_referrals_19" name="<portlet:namespace />t4b_referrals_19"></td>
									<td><input type="number" class="form-control catWiseExit t4b_2" id="t4b_0_7_19" name="<portlet:namespace />t4b_0_7_19"></td>
								    <td><input type="number" class="form-control catWiseExit t4b_3" id="t4b_8_15_19" name="<portlet:namespace />t4b_8_15_19"></td>
								    <td><input type="number" class="form-control catWiseExit t4b_4" id="t4b_16_31_19" name="<portlet:namespace />t4b_16_31_19"></td>
								    <td><input type="number" class="form-control catWiseExit t4b_5" id="t4b_32_90_19" name="<portlet:namespace />t4b_32_90_19"></td>
								    <td><input type="number" class="form-control catWiseExit t4b_6" id="t4b_91_180_19" name="<portlet:namespace />t4b_91_180_19"></td>
								    <td><input type="number" class="form-control catWiseExit t4b_7" id="t4b_185_365_19" name="<portlet:namespace />t4b_185_365_19"></td>
								    <td><input type="number" class="form-control catWiseExit t4b_8" id="t4b_366_and_above_19" name="<portlet:namespace />t4b_366_and_above_19"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Partial withdrawal not initiated / not authorised / amount not received</td>
	                                <td><input type="number" class="form-control catWisePartialWithdrawal t4b_1" id="t4b_referrals_20" name="<portlet:namespace />t4b_referrals_20"></td>
									<td><input type="number" class="form-control catWisePartialWithdrawal t4b_2" id="t4b_0_7_20" name="<portlet:namespace />t4b_0_7_20"></td>
								    <td><input type="number" class="form-control catWisePartialWithdrawal t4b_3" id="t4b_8_15_20" name="<portlet:namespace />t4b_8_15_20"></td>
								    <td><input type="number" class="form-control catWisePartialWithdrawal t4b_4" id="t4b_16_31_20" name="<portlet:namespace />t4b_16_31_20"></td>
								    <td><input type="number" class="form-control catWisePartialWithdrawal t4b_5" id="t4b_32_90_20" name="<portlet:namespace />t4b_32_90_20"></td>
								    <td><input type="number" class="form-control catWisePartialWithdrawal t4b_6" id="t4b_91_180_20" name="<portlet:namespace />t4b_91_180_20"></td>
								    <td><input type="number" class="form-control catWisePartialWithdrawal t4b_7" id="t4b_185_365_20" name="<portlet:namespace />t4b_185_365_20"></td>
								    <td><input type="number" class="form-control catWisePartialWithdrawal t4b_8" id="t4b_366_and_above_20" name="<portlet:namespace />t4b_366_and_above_20"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Pre-mature withdrawal not initiated / not authorised / amount not received</td>
	                                <td><input type="number" class="form-control catWisePreMatureWithdrawal t4b_1" id="t4b_referrals_21" name="<portlet:namespace />t4b_referrals_21"></td>
									<td><input type="number" class="form-control catWisePreMatureWithdrawal t4b_2" id="t4b_0_7_21" name="<portlet:namespace />t4b_0_7_21"></td>
								    <td><input type="number" class="form-control catWisePreMatureWithdrawal t4b_3" id="t4b_8_15_21" name="<portlet:namespace />t4b_8_15_21"></td>
								    <td><input type="number" class="form-control catWisePreMatureWithdrawal t4b_4" id="t4b_16_31_21" name="<portlet:namespace />t4b_16_31_21"></td>
								    <td><input type="number" class="form-control catWisePreMatureWithdrawal t4b_5" id="t4b_32_90_21" name="<portlet:namespace />t4b_32_90_21"></td>
								    <td><input type="number" class="form-control catWisePreMatureWithdrawal t4b_6" id="t4b_91_180_21" name="<portlet:namespace />t4b_91_180_21"></td>
								    <td><input type="number" class="form-control catWisePreMatureWithdrawal t4b_7" id="t4b_185_365_21" name="<portlet:namespace />t4b_185_365_21"></td>
								    <td><input type="number" class="form-control catWisePreMatureWithdrawal t4b_8" id="t4b_366_and_above_21" name="<portlet:namespace />t4b_366_and_above_21"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Other grievances</td>
	                                <td><input type="number" class="form-control catWiseOtherGrievances t4b_1" id="t4b_referrals_22" name="<portlet:namespace />t4b_referrals_22"></td>
									<td><input type="number" class="form-control catWiseOtherGrievances t4b_2" id="t4b_0_7_22" name="<portlet:namespace />t4b_0_7_22"></td>
								    <td><input type="number" class="form-control catWiseOtherGrievances t4b_3" id="t4b_8_15_22" name="<portlet:namespace />t4b_8_15_22"></td>
								    <td><input type="number" class="form-control catWiseOtherGrievances t4b_4" id="t4b_16_31_22" name="<portlet:namespace />t4b_16_31_22"></td>
								    <td><input type="number" class="form-control catWiseOtherGrievances t4b_5" id="t4b_32_90_22" name="<portlet:namespace />t4b_32_90_22"></td>
								    <td><input type="number" class="form-control catWiseOtherGrievances t4b_6" id="t4b_91_180_22" name="<portlet:namespace />t4b_91_180_22"></td>
								    <td><input type="number" class="form-control catWiseOtherGrievances t4b_7" id="t4b_185_365_22" name="<portlet:namespace />t4b_185_365_22"></td>
								    <td><input type="number" class="form-control catWiseOtherGrievances t4b_8" id="t4b_366_and_above_22" name="<portlet:namespace />t4b_366_and_above_22"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Change Request- Others</td>
	                                <td><input type="number" class="form-control catWiseChangeRequestOthers t4b_1" id="t4b_referrals_23" name="<portlet:namespace />t4b_referrals_23"></td>
									<td><input type="number" class="form-control catWiseChangeRequestOthers t4b_2" id="t4b_0_7_23" name="<portlet:namespace />t4b_0_7_23"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestOthers t4b_3" id="t4b_8_15_23" name="<portlet:namespace />t4b_8_15_23"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestOthers t4b_4" id="t4b_16_31_23" name="<portlet:namespace />t4b_16_31_23"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestOthers t4b_5" id="t4b_32_90_23" name="<portlet:namespace />t4b_32_90_23"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestOthers t4b_6" id="t4b_91_180_23" name="<portlet:namespace />t4b_91_180_23"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestOthers t4b_7" id="t4b_185_365_23" name="<portlet:namespace />t4b_185_365_23"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestOthers t4b_8" id="t4b_366_and_above_23" name="<portlet:namespace />t4b_366_and_above_23"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Change Request- Processed Incorrectly</td>
	                                <td><input type="number" class="form-control catWiseChangeRequestProcessed t4b_1" id="t4b_referrals_24" name="<portlet:namespace />t4b_referrals_24"></td>
									<td><input type="number" class="form-control catWiseChangeRequestProcessed t4b_2" id="t4b_0_7_24" name="<portlet:namespace />t4b_0_7_24"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestProcessed t4b_3" id="t4b_8_15_24" name="<portlet:namespace />t4b_8_15_24"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestProcessed t4b_4" id="t4b_16_31_24" name="<portlet:namespace />t4b_16_31_24"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestProcessed t4b_5" id="t4b_32_90_24" name="<portlet:namespace />t4b_32_90_24"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestProcessed t4b_6" id="t4b_91_180_24" name="<portlet:namespace />t4b_91_180_24"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestProcessed t4b_7" id="t4b_185_365_24" name="<portlet:namespace />t4b_185_365_24"></td>
								    <td><input type="number" class="form-control catWiseChangeRequestProcessed t4b_8" id="t4b_366_and_above_24" name="<portlet:namespace />t4b_366_and_above_24"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Contribution-others</td>
	                                <td><input type="number" class="form-control catWiseContributionOthers t4b_1" id="t4b_referrals_25" name="<portlet:namespace />t4b_referrals_25"></td>
									<td><input type="number" class="form-control catWiseContributionOthers t4b_2" id="t4b_0_7_25" name="<portlet:namespace />t4b_0_7_25"></td>
								    <td><input type="number" class="form-control catWiseContributionOthers t4b_3" id="t4b_8_15_25" name="<portlet:namespace />t4b_8_15_25"></td>
								    <td><input type="number" class="form-control catWiseContributionOthers t4b_4" id="t4b_16_31_25" name="<portlet:namespace />t4b_16_31_25"></td>
								    <td><input type="number" class="form-control catWiseContributionOthers t4b_5" id="t4b_32_90_25" name="<portlet:namespace />t4b_32_90_25"></td>
								    <td><input type="number" class="form-control catWiseContributionOthers t4b_6" id="t4b_91_180_25" name="<portlet:namespace />t4b_91_180_25"></td>
								    <td><input type="number" class="form-control catWiseContributionOthers t4b_7" id="t4b_185_365_25" name="<portlet:namespace />t4b_185_365_25"></td>
								    <td><input type="number" class="form-control catWiseContributionOthers t4b_8" id="t4b_366_and_above_25" name="<portlet:namespace />t4b_366_and_above_25"></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>GRAND TOTAL</strong></td>
	                                <td><input type="text" class="form-control grandTotal" id="t4b_referrals_26" name="<portlet:namespace />t4b_referrals_26" readonly></td>
									<td><input type="text" class="form-control grandTotal" id="t4b_0_7_26" name="<portlet:namespace />t4b_0_7_26" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4b_8_15_26" name="<portlet:namespace />t4b_8_15_26" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4b_16_31_26" name="<portlet:namespace />t4b_16_31_26" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4b_32_90_26" name="<portlet:namespace />t4b_32_90_26" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4b_91_180_26" name="<portlet:namespace />t4b_91_180_26" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4b_185_365_26" name="<portlet:namespace />t4b_185_365_26" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4b_366_and_above_26" name="<portlet:namespace />t4b_366_and_above_26" readonly></td>
	                            </tr>
	
	
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <!-- TABLE 4(b) : Ageing of category-wise grievances at the end of Quarter X (FY XXXXX) (NPS) -->
	
	            <div class="table_fourc mt-5">
	                <div class="row">
	                    <div class="col-12">
	                        <table class="mt-5 table table-bordered w-100" id="table9">
	                            <tr>
	                                <td class="text-center" colspan=9>
	                                    <strong>TABLE 4(c) : Ageing of entity-wise outstanding grievances at the end of Quarter x (FY <span class="fy">xxxxx</span>) (APY/NPS-Lite)</strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td><strong>Entity</strong></td>
	                                <td><strong>Referrals at the end of Quarter x (FY <span class="fy">xxxxx</span>)</strong></td>
	                                <td><strong>0-7</strong></td>
	                                <td><strong>8-15</strong></td>
	                                <td><strong>16-31</strong></td>
	                                <td><strong>32-90</strong></td>
	                                <td><strong>91-180</strong></td>
	                                <td><strong>181-365</strong></td>
	                                <td><strong>366 & above</strong></td>
	                            </tr>
	                            <tr>
	                                <td>CRA</td>
	                                <td><input type="number" class="form-control table4c_cra t4c_cra" id="t4c_referrals_0" name="<portlet:namespace />t4c_referrals_0"></td>
								    <td><input type="number" class="form-control table4c_cra t4c_nloo" id="t4c_0_7_0" name="<portlet:namespace />t4c_0_7_0"></td>
								    <td><input type="number" class="form-control table4c_cra t4c_nlao" id="t4c_8_15_0" name="<portlet:namespace />t4c_8_15_0"></td>
								    <td><input type="number" class="form-control table4c_cra t4c_apy" id="t4c_16_31_0" name="<portlet:namespace />t4c_16_31_0"></td>
								    <td><input type="number" class="form-control table4c_cra t4c_nlcc" id="t4c_32_90_0" name="<portlet:namespace />t4c_32_90_0"></td>
								    <td><input type="number" class="form-control table4c_cra t4c_tb" id="t4c_91_180_0" name="<portlet:namespace />t4c_91_180_0"></td>
								    <td><input type="number" class="form-control table4c_cra t4c_nps" id="t4c_185_365_0" name="<portlet:namespace />t4c_185_365_0"></td>
								    <td><input type="number" class="form-control table4c_cra t4c_others" id="t4c_366_and_above_0" name="<portlet:namespace />t4c_366_and_above_0"></td>
	                            </tr>
	                            <tr>
	                                <td>NLOO</td>
	                                <td><input type="number" class="form-control nloo t4c_cra" id="t4c_referrals_1" name="<portlet:namespace />t4c_referrals_1"></td>
								    <td><input type="number" class="form-control nloo t4c_nloo" id="t4c_0_7_1" name="<portlet:namespace />t4c_0_7_1"></td>
								    <td><input type="number" class="form-control nloo t4c_nlao" id="t4c_8_15_1" name="<portlet:namespace />t4c_8_15_1"></td>
								    <td><input type="number" class="form-control nloo t4c_apy" id="t4c_16_31_1" name="<portlet:namespace />t4c_16_31_1"></td>
								    <td><input type="number" class="form-control nloo t4c_nlcc" id="t4c_32_90_1" name="<portlet:namespace />t4c_32_90_1"></td>
								    <td><input type="number" class="form-control nloo t4c_tb" id="t4c_91_180_1" name="<portlet:namespace />t4c_91_180_1"></td>
								    <td><input type="number" class="form-control nloo t4c_nps" id="t4c_185_365_1" name="<portlet:namespace />t4c_185_365_1"></td>
								    <td><input type="number" class="form-control nloo t4c_others" id="t4c_366_and_above_1" name="<portlet:namespace />t4c_366_and_above_1"></td>
	                            </tr>
	                            <tr>
	                                <td>NLAO</td>
	                                <td><input type="number" class="form-control nlao t4c_cra" id="t4c_referrals_2" name="<portlet:namespace />t4c_referrals_2"></td>
								    <td><input type="number" class="form-control nlao t4c_nloo" id="t4c_0_7_2" name="<portlet:namespace />t4c_0_7_2"></td>
								    <td><input type="number" class="form-control nlao t4c_nlao" id="t4c_8_15_2" name="<portlet:namespace />t4c_8_15_2"></td>
								    <td><input type="number" class="form-control nlao t4c_apy" id="t4c_16_31_2" name="<portlet:namespace />t4c_16_31_2"></td>
								    <td><input type="number" class="form-control nlao t4c_nlcc" id="t4c_32_90_2" name="<portlet:namespace />t4c_32_90_2"></td>
								    <td><input type="number" class="form-control nlao t4c_tb" id="t4c_91_180_2" name="<portlet:namespace />t4c_91_180_2"></td>
								    <td><input type="number" class="form-control nlao t4c_nps" id="t4c_185_365_2" name="<portlet:namespace />t4c_185_365_2"></td>
								    <td><input type="number" class="form-control nlao t4c_others" id="t4c_366_and_above_2" name="<portlet:namespace />t4c_366_and_above_2"></td>
	                            </tr>
	                            <tr>
	                                <td>APY-SP</td>
	                                <td><input type="number" class="form-control sp t4c_cra" id="t4c_referrals_3" name="<portlet:namespace />t4c_referrals_3"></td>
								    <td><input type="number" class="form-control sp t4c_nloo" id="t4c_0_7_3" name="<portlet:namespace />t4c_0_7_3"></td>
								    <td><input type="number" class="form-control sp t4c_nlao" id="t4c_8_15_3" name="<portlet:namespace />t4c_8_15_3"></td>
								    <td><input type="number" class="form-control sp t4c_apy" id="t4c_16_31_3" name="<portlet:namespace />t4c_16_31_3"></td>
								    <td><input type="number" class="form-control sp t4c_nlcc" id="t4c_32_90_3" name="<portlet:namespace />t4c_32_90_3"></td>
								    <td><input type="number" class="form-control sp t4c_tb" id="t4c_91_180_3" name="<portlet:namespace />t4c_91_180_3"></td>
								    <td><input type="number" class="form-control sp t4c_nps" id="t4c_185_365_3" name="<portlet:namespace />t4c_185_365_3"></td>
								    <td><input type="number" class="form-control sp t4c_others" id="t4c_366_and_above_3" name="<portlet:namespace />t4c_366_and_above_3"></td>
	                            </tr>
	
	                            <tr>
	                                <td>NLCC</td>
	                                <td><input type="number" class="form-control nlcc t4c_cra" id="t4c_referrals_4" name="<portlet:namespace />t4c_referrals_4"></td>
								    <td><input type="number" class="form-control nlcc t4c_nloo" id="t4c_0_7_4" name="<portlet:namespace />t4c_0_7_4"></td>
								    <td><input type="number" class="form-control nlcc t4c_nlao" id="t4c_8_15_4" name="<portlet:namespace />t4c_8_15_4"></td>
								    <td><input type="number" class="form-control nlcc t4c_apy" id="t4c_16_31_4" name="<portlet:namespace />t4c_16_31_4"></td>
								    <td><input type="number" class="form-control nlcc t4c_nlcc" id="t4c_32_90_4" name="<portlet:namespace />t4c_32_90_4"></td>
								    <td><input type="number" class="form-control nlcc t4c_tb" id="t4c_91_180_4" name="<portlet:namespace />t4c_91_180_4"></td>
								    <td><input type="number" class="form-control nlcc t4c_nps" id="t4c_185_365_4" name="<portlet:namespace />t4c_185_365_4"></td>
								    <td><input type="number" class="form-control nlcc t4c_others" id="t4c_366_and_above_4" name="<portlet:namespace />t4c_366_and_above_4"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Trustee Bank</td>
	                                <td><input type="number" class="form-control table4c_tb t4c_cra" id="t4c_referrals_5" name="<portlet:namespace />t4c_referrals_5"></td>
								    <td><input type="number" class="form-control table4c_tb t4c_nloo" id="t4c_0_7_5" name="<portlet:namespace />t4c_0_7_5"></td>
								    <td><input type="number" class="form-control table4c_tb t4c_nlao" id="t4c_8_15_5" name="<portlet:namespace />t4c_8_15_5"></td>
								    <td><input type="number" class="form-control table4c_tb t4c_apy" id="t4c_16_31_5" name="<portlet:namespace />t4c_16_31_5"></td>
								    <td><input type="number" class="form-control table4c_tb t4c_nlcc" id="t4c_32_90_5" name="<portlet:namespace />t4c_32_90_5"></td>
								    <td><input type="number" class="form-control table4c_tb t4c_tb" id="t4c_91_180_5" name="<portlet:namespace />t4c_91_180_5"></td>
								    <td><input type="number" class="form-control table4c_tb t4c_nps" id="t4c_185_365_5" name="<portlet:namespace />t4c_185_365_5"></td>
								    <td><input type="number" class="form-control table4c_tb t4c_others" id="t4c_366_and_above_5" name="<portlet:namespace />t4c_366_and_above_5"></td>
	                            </tr>
	
	                            <tr>
	                                <td>NPS Trust</td>
	                                <td><input type="number" class="form-control table4c_nps t4c_cra" id="t4c_referrals_6" name="<portlet:namespace />t4c_referrals_6"></td>
								    <td><input type="number" class="form-control table4c_nps t4c_nloo" id="t4c_0_7_6" name="<portlet:namespace />t4c_0_7_6"></td>
								    <td><input type="number" class="form-control table4c_nps t4c_nlao" id="t4c_8_15_6" name="<portlet:namespace />t4c_8_15_6"></td>
								    <td><input type="number" class="form-control table4c_nps t4c_apy" id="t4c_16_31_6" name="<portlet:namespace />t4c_16_31_6"></td>
								    <td><input type="number" class="form-control table4c_nps t4c_nlcc" id="t4c_32_90_6" name="<portlet:namespace />t4c_32_90_6"></td>
								    <td><input type="number" class="form-control table4c_nps t4c_tb" id="t4c_91_180_6" name="<portlet:namespace />t4c_91_180_6"></td>
								    <td><input type="number" class="form-control table4c_nps t4c_nps" id="t4c_185_365_6" name="<portlet:namespace />t4c_185_365_6"></td>
								    <td><input type="number" class="form-control table4c_nps t4c_others" id="t4c_366_and_above_6" name="<portlet:namespace />t4c_366_and_above_6"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Others</td>
	                                <td><input type="number" class="form-control table4c_others t4c_cra" id="t4c_referrals_7" name="<portlet:namespace />t4c_referrals_7"></td>
								    <td><input type="number" class="form-control table4c_others t4c_nloo" id="t4c_0_7_7" name="<portlet:namespace />t4c_0_7_7"></td>
								    <td><input type="number" class="form-control table4c_others t4c_nlao" id="t4c_8_15_7" name="<portlet:namespace />t4c_8_15_7"></td>
								    <td><input type="number" class="form-control table4c_others t4c_apy" id="t4c_16_31_7" name="<portlet:namespace />t4c_16_31_7"></td>
								    <td><input type="number" class="form-control table4c_others t4c_nlcc" id="t4c_32_90_7" name="<portlet:namespace />t4c_32_90_7"></td>
								    <td><input type="number" class="form-control table4c_others t4c_tb" id="t4c_91_180_7" name="<portlet:namespace />t4c_91_180_7"></td>
								    <td><input type="number" class="form-control table4c_others t4c_nps" id="t4c_185_365_7" name="<portlet:namespace />t4c_185_365_7"></td>
								    <td><input type="number" class="form-control table4c_others t4c_others" id="t4c_366_and_above_7" name="<portlet:namespace />t4c_366_and_above_7"></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>GRAND TOTAL</strong></td>
	                                <td><input type="text" class="form-control grandTotal" id="t4c_referrals_8" name="<portlet:namespace />t4c_referrals_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4c_0_7_8" name="<portlet:namespace />t4c_0_7_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4c_8_15_8" name="<portlet:namespace />t4c_8_15_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4c_16_31_8" name="<portlet:namespace />t4c_16_31_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4c_32_90_8" name="<portlet:namespace />t4c_32_90_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4c_91_180_8" name="<portlet:namespace />t4c_91_180_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4c_185_365_8" name="<portlet:namespace />t4c_185_365_8" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4c_366_and_above_8" name="<portlet:namespace />t4c_366_and_above_8" readonly></td>
	                            </tr>
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <!-- TABLE 4(c) : Ageing of entity-wise outstanding grievances at the end of Quarter x (FY xxxxx) (APY/NPS-Lite) -->
	
	            <div class="table_fourd mt-5">
	                <div class="row">
	                    <div class="col-12">
	                        <table class="mt-5 table table-bordered w-100" id="table10">
	                            <tr>
	                                <td class="text-center" colspan=9>
	                                    <strong>TABLE 4(d) : Ageing of category-wise outstanding grievances at the end of Quarter x (FY <span class="fy">xxxxx</span>) (APY/NPS-Lite)</strong>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td><strong>Enquiries and Grievances (Referrals) Category</strong></td>
	                                <td><strong>Referrals as at the end of Quarter x (FY <span class="fy">xxxxx</span>)</strong></td>
	                                <td><strong>0-7</strong></td>
	                                <td><strong>8-15</strong></td>
	                                <td><strong>16-31</strong></td>
	                                <td><strong>32-90</strong></td>
	                                <td><strong>91-180</strong></td>
	                                <td><strong>181-365</strong></td>
	                                <td><strong>366 & above</strong></td>
	                            </tr>
	                            <tr>
	                                <td>Against NPS Trust</td>
	                                <td><input type="number" class="form-control table4d_againstNPS t4d_1" id="t4d_referrals_0" name="<portlet:namespace />t4d_referrals_0"></td>
								    <td><input type="number" class="form-control table4d_againstNPS t4d_2" id="t4d_0_7_0" name="<portlet:namespace />t4d_0_7_0"></td>
								    <td><input type="number" class="form-control table4d_againstNPS t4d_3" id="t4d_8_15_0" name="<portlet:namespace />t4d_8_15_0"></td>
								    <td><input type="number" class="form-control table4d_againstNPS t4d_4" id="t4d_16_31_0" name="<portlet:namespace />t4d_16_31_0"></td>
								    <td><input type="number" class="form-control table4d_againstNPS t4d_5" id="t4d_32_90_0" name="<portlet:namespace />t4d_32_90_0"></td>
								    <td><input type="number" class="form-control table4d_againstNPS t4d_6" id="t4d_91_180_0" name="<portlet:namespace />t4d_91_180_0"></td>
								    <td><input type="number" class="form-control table4d_againstNPS t4d_7" id="t4d_185_365_0" name="<portlet:namespace />t4d_185_365_0"></td>
								    <td><input type="number" class="form-control table4d_againstNPS t4d_8" id="t4d_366_and_above_0" name="<portlet:namespace />t4d_366_and_above_0"></td>
	                            </tr>
	                            <tr>
	                                <td>Contribution amount not reflected in account</td>
	                                <td><input type="number" class="form-control table4d_AmtNotReflected t4d_1" id="t4d_referrals_1" name="<portlet:namespace />t4d_referrals_1"></td>
								    <td><input type="number" class="form-control table4d_AmtNotReflected t4d_2" id="t4d_0_7_1" name="<portlet:namespace />t4d_0_7_1"></td>
								    <td><input type="number" class="form-control table4d_AmtNotReflected t4d_3" id="t4d_8_15_1" name="<portlet:namespace />t4d_8_15_1"></td>
								    <td><input type="number" class="form-control table4d_AmtNotReflected t4d_4" id="t4d_16_31_1" name="<portlet:namespace />t4d_16_31_1"></td>
								    <td><input type="number" class="form-control table4d_AmtNotReflected t4d_5" id="t4d_32_90_1" name="<portlet:namespace />t4d_32_90_1"></td>
								    <td><input type="number" class="form-control table4d_AmtNotReflected t4d_6" id="t4d_91_180_1" name="<portlet:namespace />t4d_91_180_1"></td>
								    <td><input type="number" class="form-control table4d_AmtNotReflected t4d_7" id="t4d_185_365_1" name="<portlet:namespace />t4d_185_365_1"></td>
								    <td><input type="number" class="form-control table4d_AmtNotReflected t4d_8" id="t4d_366_and_above_1" name="<portlet:namespace />t4d_366_and_above_1"></td>
	                            </tr>
	                            <tr>
	                                <td>General Query</td>
	                                <td><input type="number" class="form-control table4d_GeneralQuery t4d_1" id="t4d_referrals_2" name="<portlet:namespace />t4d_referrals_2"></td>
								    <td><input type="number" class="form-control table4d_GeneralQuery t4d_2" id="t4d_0_7_2" name="<portlet:namespace />t4d_0_7_2"></td>
								    <td><input type="number" class="form-control table4d_GeneralQuery t4d_3" id="t4d_8_15_2" name="<portlet:namespace />t4d_8_15_2"></td>
								    <td><input type="number" class="form-control table4d_GeneralQuery t4d_4" id="t4d_16_31_2" name="<portlet:namespace />t4d_16_31_2"></td>
								    <td><input type="number" class="form-control table4d_GeneralQuery t4d_5" id="t4d_32_90_2" name="<portlet:namespace />t4d_32_90_2"></td>
								    <td><input type="number" class="form-control table4d_GeneralQuery t4d_6" id="t4d_91_180_2" name="<portlet:namespace />t4d_91_180_2"></td>
								    <td><input type="number" class="form-control table4d_GeneralQuery t4d_7" id="t4d_185_365_2" name="<portlet:namespace />t4d_185_365_2"></td>
								    <td><input type="number" class="form-control table4d_GeneralQuery t4d_8" id="t4d_366_and_above_2" name="<portlet:namespace />t4d_366_and_above_2"></td>
	                            </tr>
	                            <tr>
	                                <td>Incorrect Contribution Amount Reflected</td>
	                                <td><input type="number" class="form-control table4d_IncorrectAmt t4d_1" id="t4d_referrals_3" name="<portlet:namespace />t4d_referrals_3"></td>
								    <td><input type="number" class="form-control table4d_IncorrectAmt t4d_2" id="t4d_0_7_3" name="<portlet:namespace />t4d_0_7_3"></td>
								    <td><input type="number" class="form-control table4d_IncorrectAmt t4d_3" id="t4d_8_15_3" name="<portlet:namespace />t4d_8_15_3"></td>
								    <td><input type="number" class="form-control table4d_IncorrectAmt t4d_4" id="t4d_16_31_3" name="<portlet:namespace />t4d_16_31_3"></td>
								    <td><input type="number" class="form-control table4d_IncorrectAmt t4d_5" id="t4d_32_90_3" name="<portlet:namespace />t4d_32_90_3"></td>
								    <td><input type="number" class="form-control table4d_IncorrectAmt t4d_6" id="t4d_91_180_3" name="<portlet:namespace />t4d_91_180_3"></td>
								    <td><input type="number" class="form-control table4d_IncorrectAmt t4d_7" id="t4d_185_365_3" name="<portlet:namespace />t4d_185_365_3"></td>
								    <td><input type="number" class="form-control table4d_IncorrectAmt t4d_8" id="t4d_366_and_above_3" name="<portlet:namespace />t4d_366_and_above_3"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Incorrect Processing of Subscriber Details</td>
	                                <td><input type="number" class="form-control table4d_SubscriberDetails t4d_1" id="t4d_referrals_4" name="<portlet:namespace />t4d_referrals_4"></td>
								    <td><input type="number" class="form-control table4d_SubscriberDetails t4d_2" id="t4d_0_7_4" name="<portlet:namespace />t4d_0_7_4"></td>
								    <td><input type="number" class="form-control table4d_SubscriberDetails t4d_3" id="t4d_8_15_4" name="<portlet:namespace />t4d_8_15_4"></td>
								    <td><input type="number" class="form-control table4d_SubscriberDetails t4d_4" id="t4d_16_31_4" name="<portlet:namespace />t4d_16_31_4"></td>
								    <td><input type="number" class="form-control table4d_SubscriberDetails t4d_5" id="t4d_32_90_4" name="<portlet:namespace />t4d_32_90_4"></td>
								    <td><input type="number" class="form-control table4d_SubscriberDetails t4d_6" id="t4d_91_180_4" name="<portlet:namespace />t4d_91_180_4"></td>
								    <td><input type="number" class="form-control table4d_SubscriberDetails t4d_7" id="t4d_185_365_4" name="<portlet:namespace />t4d_185_365_4"></td>
								    <td><input type="number" class="form-control table4d_SubscriberDetails t4d_8" id="t4d_366_and_above_4" name="<portlet:namespace />t4d_366_and_above_4"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Other Grievances</td>
	                                <td><input type="number" class="form-control table4d_OtherGrievances t4d_1" id="t4d_referrals_5" name="<portlet:namespace />t4d_referrals_5"></td>
								    <td><input type="number" class="form-control table4d_OtherGrievances t4d_2" id="t4d_0_7_5" name="<portlet:namespace />t4d_0_7_5"></td>
								    <td><input type="number" class="form-control table4d_OtherGrievances t4d_3" id="t4d_8_15_5" name="<portlet:namespace />t4d_8_15_5"></td>
								    <td><input type="number" class="form-control table4d_OtherGrievances t4d_4" id="t4d_16_31_5" name="<portlet:namespace />t4d_16_31_5"></td>
								    <td><input type="number" class="form-control table4d_OtherGrievances t4d_5" id="t4d_32_90_5" name="<portlet:namespace />t4d_32_90_5"></td>
								    <td><input type="number" class="form-control table4d_OtherGrievances t4d_6" id="t4d_91_180_5" name="<portlet:namespace />t4d_91_180_5"></td>
								    <td><input type="number" class="form-control table4d_OtherGrievances t4d_7" id="t4d_185_365_5" name="<portlet:namespace />t4d_185_365_5"></td>
								    <td><input type="number" class="form-control table4d_OtherGrievances t4d_8" id="t4d_366_and_above_5" name="<portlet:namespace />t4d_366_and_above_5"></td>
	                            </tr>
	
	                            <tr>
	                                <td>PRAN Card Related</td>
	                                <td><input type="number" class="form-control table4d_pran t4d_1" id="t4d_referrals_6" name="<portlet:namespace />t4d_referrals_6"></td>
								    <td><input type="number" class="form-control table4d_pran t4d_2" id="t4d_0_7_6" name="<portlet:namespace />t4d_0_7_6"></td>
								    <td><input type="number" class="form-control table4d_pran t4d_3" id="t4d_8_15_6" name="<portlet:namespace />t4d_8_15_6"></td>
								    <td><input type="number" class="form-control table4d_pran t4d_4" id="t4d_16_31_6" name="<portlet:namespace />t4d_16_31_6"></td>
								    <td><input type="number" class="form-control table4d_pran t4d_5" id="t4d_32_90_6" name="<portlet:namespace />t4d_32_90_6"></td>
								    <td><input type="number" class="form-control table4d_pran t4d_6" id="t4d_91_180_6" name="<portlet:namespace />t4d_91_180_6"></td>
								    <td><input type="number" class="form-control table4d_pran t4d_7" id="t4d_185_365_6" name="<portlet:namespace />t4d_185_365_6"></td>
								    <td><input type="number" class="form-control table4d_pran t4d_8" id="t4d_366_and_above_6" name="<portlet:namespace />t4d_366_and_above_6"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Processing of change request by Nodal Office</td>
	                                <td><input type="number" class="form-control table4d_ChangeRequestProcessed t4d_1" id="t4d_referrals_7" name="<portlet:namespace />t4d_referrals_7"></td>
								    <td><input type="number" class="form-control table4d_ChangeRequestProcessed t4d_2" id="t4d_0_7_7" name="<portlet:namespace />t4d_0_7_7"></td>
								    <td><input type="number" class="form-control table4d_ChangeRequestProcessed t4d_3" id="t4d_8_15_7" name="<portlet:namespace />t4d_8_15_7"></td>
								    <td><input type="number" class="form-control table4d_ChangeRequestProcessed t4d_4" id="t4d_16_31_7" name="<portlet:namespace />t4d_16_31_7"></td>
								    <td><input type="number" class="form-control table4d_ChangeRequestProcessed t4d_5" id="t4d_32_90_7" name="<portlet:namespace />t4d_32_90_7"></td>
								    <td><input type="number" class="form-control table4d_ChangeRequestProcessed t4d_6" id="t4d_91_180_7" name="<portlet:namespace />t4d_91_180_7"></td>
								    <td><input type="number" class="form-control table4d_ChangeRequestProcessed t4d_7" id="t4d_185_365_7" name="<portlet:namespace />t4d_185_365_7"></td>
								    <td><input type="number" class="form-control table4d_ChangeRequestProcessed t4d_8" id="t4d_366_and_above_7" name="<portlet:namespace />t4d_366_and_above_7"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Service not received</td>
	                                <td><input type="number" class="form-control table4d_service t4d_1" id="t4d_referrals_8" name="<portlet:namespace />t4d_referrals_8"></td>
								    <td><input type="number" class="form-control table4d_service t4d_2" id="t4d_0_7_8" name="<portlet:namespace />t4d_0_7_8"></td>
								    <td><input type="number" class="form-control table4d_service t4d_3" id="t4d_8_15_8" name="<portlet:namespace />t4d_8_15_8"></td>
								    <td><input type="number" class="form-control table4d_service t4d_4" id="t4d_16_31_8" name="<portlet:namespace />t4d_16_31_8"></td>
								    <td><input type="number" class="form-control table4d_service t4d_5" id="t4d_32_90_8" name="<portlet:namespace />t4d_32_90_8"></td>
								    <td><input type="number" class="form-control table4d_service t4d_6" id="t4d_91_180_8" name="<portlet:namespace />t4d_91_180_8"></td>
								    <td><input type="number" class="form-control table4d_service t4d_7" id="t4d_185_365_8" name="<portlet:namespace />t4d_185_365_8"></td>
								    <td><input type="number" class="form-control table4d_service t4d_8" id="t4d_366_and_above_8" name="<portlet:namespace />t4d_366_and_above_8"></td>
	                            </tr>
	
	                            <tr>
	                                <td>SOT Related</td>
	                                <td><input type="number" class="form-control table4d_sot t4d_1" id="t4d_referrals_9" name="<portlet:namespace />t4d_referrals_9"></td>
								    <td><input type="number" class="form-control table4d_sot t4d_2" id="t4d_0_7_9" name="<portlet:namespace />t4d_0_7_9"></td>
								    <td><input type="number" class="form-control table4d_sot t4d_3" id="t4d_8_15_9" name="<portlet:namespace />t4d_8_15_9"></td>
								    <td><input type="number" class="form-control table4d_sot t4d_4" id="t4d_16_31_9" name="<portlet:namespace />t4d_16_31_9"></td>
								    <td><input type="number" class="form-control table4d_sot t4d_5" id="t4d_32_90_9" name="<portlet:namespace />t4d_32_90_9"></td>
								    <td><input type="number" class="form-control table4d_sot t4d_6" id="t4d_91_180_9" name="<portlet:namespace />t4d_91_180_9"></td>
								    <td><input type="number" class="form-control table4d_sot t4d_7" id="t4d_185_365_9" name="<portlet:namespace />t4d_185_365_9"></td>
								    <td><input type="number" class="form-control table4d_sot t4d_8" id="t4d_366_and_above_9" name="<portlet:namespace />t4d_366_and_above_9"></td>
	                            </tr>
	
	                            <tr>
	                                <td>Withdrawal Related</td>
	                                <td><input type="number" class="form-control table4d_Withdrawal t4d_1" id="t4d_referrals_10" name="<portlet:namespace />t4d_referrals_10"></td>
								    <td><input type="number" class="form-control table4d_Withdrawal t4d_2" id="t4d_0_7_10" name="<portlet:namespace />t4d_0_7_10"></td>
								    <td><input type="number" class="form-control table4d_Withdrawal t4d_3" id="t4d_8_15_10" name="<portlet:namespace />t4d_8_15_10"></td>
								    <td><input type="number" class="form-control table4d_Withdrawal t4d_4" id="t4d_16_31_10" name="<portlet:namespace />t4d_16_31_10"></td>
								    <td><input type="number" class="form-control table4d_Withdrawal t4d_5" id="t4d_32_90_10" name="<portlet:namespace />t4d_32_90_10"></td>
								    <td><input type="number" class="form-control table4d_Withdrawal t4d_6" id="t4d_91_180_10" name="<portlet:namespace />t4d_91_180_10"></td>
								    <td><input type="number" class="form-control table4d_Withdrawal t4d_7" id="t4d_185_365_10" name="<portlet:namespace />t4d_185_365_10"></td>
								    <td><input type="number" class="form-control table4d_Withdrawal t4d_8" id="t4d_366_and_above_10" name="<portlet:namespace />t4d_366_and_above_10"></td>
	                            </tr>
	
	                            <tr>
	                                <td><strong>GRAND TOTAL</strong></td>
	                                <td><input type="text" class="form-control grandTotal" id="t4d_referrals_11" name="<portlet:namespace />t4d_referrals_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4d_0_7_11" name="<portlet:namespace />t4d_0_7_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4d_8_15_11" name="<portlet:namespace />t4d_8_15_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4d_16_31_11" name="<portlet:namespace />t4d_16_31_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4d_32_90_11" name="<portlet:namespace />t4d_32_90_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4d_91_180_11" name="<portlet:namespace />t4d_91_180_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4d_185_365_11" name="<portlet:namespace />t4d_185_365_11" readonly></td>
								    <td><input type="text" class="form-control grandTotal" id="t4d_366_and_above_11" name="<portlet:namespace />t4d_366_and_above_11" readonly></td>
	                            </tr>
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <!-- TABLE 4(d) : Ageing of category-wise outstanding grievances at the end of Quarter x (FY xxxx) (APY/NPS-Lite) -->
	
	            <div class="form-inline sub_points py-5 mb-4 px-2">
	                <div class="row">
	                    <div class="col-12">
	                        <p class="mb-3">
	                            <strong>2. Analysis of data for quarter ended <input class="p-1 rounded border" type="text" id="date_5" name="<portlet:namespace />date_5"> 2021 is as follows:</strong>
	                        </p>
	
	                        <ul>
	                            <li class="m-4">
	                                Major number of grievances are outstanding against 
	                                <input class="form-control grievances" type="text" id="numberOfGriev_1" name="<portlet:namespace />numberOfGriev_1"> 
	                                ( <input class="form-control grievances" type="text" id="numberOfGriev_2" name="<portlet:namespace />numberOfGriev_2"> ) 
	                                followed by <input class="form-control grievances" type="text" id="numberOfGriev_3" name="<portlet:namespace />numberOfGriev_3">
	                                ( <input class="form-control grievances" type="text" id="numberOfGriev_4" name="<portlet:namespace />numberOfGriev_4"> ), 
	                                <input class="form-control grievances" type="text" id="numberOfGriev_5" name="<portlet:namespace />numberOfGriev_5">
	                                ( <input class="form-control grievances" type="text" id="numberOfGriev_6" name="<portlet:namespace />numberOfGriev_6"> ).
	                            </li>
	
	                            <li class="m-4">
	                                Out of total <input class="form-control grievances" type="text" id="grievances" name="<portlet:namespace />grievances"> 
	                                outstanding grievances, <input class="form-control grievances" type="text" id="grievances_2" name="<portlet:namespace />grievances_2"> 
	                                ( <input class="form-control grievances" type="text" id="grievances_3" name="<portlet:namespace />grievances_3"> )
	                                grievances of the total outstanding grievances are pending for more than 90 days.
	                            </li>
	
	                            <li class="m-4">
	                                Out of <input class="form-control grievances" type="text" id="grievances_4" name="<portlet:namespace />grievances_4">  
	                                grievances outstanding against <input class="form-control grievances" type="text" id="grievances_5" name="<portlet:namespace />grievances_5"> , 
	                                <input class="form-control grievances" type="text" id="grievances_6" name="<portlet:namespace />grievances_6">  
	                                grievances are pending for more than 90 days. Out of the total referrals received, 
	                                general query are the highest followed by PRAN card related queries.Ministry of 
	                                <input class="form-control grievances" type="text" id="grievances_7" name="<portlet:namespace />grievances_7">  
	                                has the highest number of outstanding grievances against them 
	                                ( <input class="form-control grievances" type="text" id="grievances_8" name="<portlet:namespace />grievances_8"> ) 
	                                followed by Ministry of <input class="form-control grievances" type="text" id="grievances_9" name="<portlet:namespace />grievances_9">
	                                ( <input class="form-control grievances" type="text" id="grievances_10" name="<portlet:namespace />grievances_10"> ).
	                            </li>
	
	                            <li class="m-4">
	                                Out of <input class="form-control grievances" type="text" id="grievances_11" name="<portlet:namespace />grievances_11">  
	                                grievances outstanding against <input class="form-control grievances" type="text" id="grievances_12" name="<portlet:namespace />grievances_12">, 
	                                <input class="form-control grievances" type="text" id="grievances_13" name="<portlet:namespace />grievances_13">
	                                ( <input class="form-control grievances" type="text" id="grievances_14" name="<portlet:namespace />grievances_14"> )
	                                are pending for more than 90 days. 
	                                Among the ministeries, Ministry of Defense has the highest number of outstanding grievances against them 
	                                <input class="form-control grievances" type="text" id="grievances_15" name="<portlet:namespace />grievances_15"> followed by Ministry of 
	                                <input class="form-control grievances" type="text" id="grievances_16" name="<portlet:namespace />grievances_16">
	                                ( <input class="form-control grievances" type="text" id="grievances_17" name="<portlet:namespace />grievances_17"> ).
	                            </li>
	
	                            <li class="m-4">
	                                Out of <input class="form-control grievances" type="text" id="grievances_18" name="<portlet:namespace />grievances_18">
	                                grievances outstanding against 
	                                <input class="form-control grievances" type="text" id="grievances_19" name="<portlet:namespace />grievances_19"> 
	                                ( <input class="form-control grievances" type="text" id="grievances_20" name="<portlet:namespace />grievances_20"> )
	                                are pending for more than 90 days. Amongst the states, 
	                                Government of <input class="form-control grievances" type="text" id="grievances_21" name="<portlet:namespace />grievances_21"> 
	                                is having highest outstanding grievances
	                                ( <input class="form-control grievances" type="text" id="grievances_22" name="<portlet:namespace />grievances_22"> ) followed by 
	                                <input class="form-control grievances" type="text" id="grievances_23" name="<portlet:namespace />grievances_23">
	                                ( <input class="form-control grievances" type="text" id="grievances_24" name="<portlet:namespace />grievances_24"> ).
	                            </li>
	
	                            <li class="m-4">
	                                CRAs have received <input class="form-control grievances" type="text" id="grievances_25" name="<portlet:namespace />grievances_25">
	                                grievances during the period, <input class="form-control grievances" type="text" id="grievances_26" name="<portlet:namespace />grievances_26"> 
	                                grievances have been resolved & 
	                                <input class="form-control grievances" type="text" id="grievances_27" name="<portlet:namespace />grievances_27"> 
	                                complaints are outstanding against them at the end of the quarter.
	                            </li>
	
	                            <li class="m-4">
	                                Out of <input class="form-control grievances" type="text" id="grievances_28" name="<portlet:namespace />grievances_28">
	                                grievances received by CRA during the period, 
	                                <input class="form-control grievances" type="text" id="grievances_29" name="<portlet:namespace />grievances_29"> 
	                                grievances( <input class="form-control grievances" type="text" id="grievances_30" name="<portlet:namespace />grievances_30"> ) 
	                                were raised by the subscribers in the nature of General Queries.
	                            </li>
	                        </ul>
	
	
	                        <p class="mb-3">
	                            <strong>3. Summary of activities at NPS Trust in compliance with the PFRDA 
	                            (Redressal of Subscriber Grievances) Regulation, 2015 and PFRDA policy for transferring 
	                            grievances from NPS Trust to PFRDA during the quarter ended 
	                            <input class="form-control" type="text" id="activity" name="<portlet:namespace />activity"> 2021</strong>
	                        </p>
	
	                        <ul>
	                            <li class="m-4">
	                                <input class="form-control grievances" type="text" id="grievances_31" name="<portlet:namespace />grievances_31">
	                                grievances raised against NPS Trust were attended to by the Trust and the grievances were suitably resolved or retagged against the concerned intermediary with an advisory to resolve the grievance.
	                            </li>
	
	                            <li class="m-4">
	                                <input class="form-control grievances" type="text" id="grievances_32" name="<portlet:namespace />grievances_32">
	                                escalated grievances, where the subscriber was not satisfied with the 
	                                resolution provided by the intermediary, were attended to by the Trust. 
	                                Based on the nature of the escalated grievance, the following actions were initiated:
	
	                                <ol type="a">
	                                    <li class="m-4">
	                                        When the grievance involves monetary loss to the subscriber, 
	                                        communication by way of physical letters was addressed to the 
	                                        intermediaries advising them to resolve the grievances within 
	                                        stipulated timelines. The applicable clauses of regulations for seeking
	                                        compensation by the subscriber in case the grievance was not resolved 
	                                        within the stipulated timeline to the satisfaction of the subscriber 
	                                        was also brought to the notice of the concerned intermediary.
	                                    </li>
	
	                                    <li class="m-4">
	                                        In case the grievance involved only service requests and 
	                                        were not serious in nature, such grievances were suitably 
	                                        retagged to the concerned intermediary with an advisory from 
	                                        NPS Trust to resolve the grievance to the satisfaction 
	                                        of the subscribers.
	                                    </li>
	
	                                    <li class="m-4">
	                                        Wherever possible, the Trust provides direct resolution 
	                                        to the subscriber and closes the escalated grievance in CGMS.
	                                    </li>
	
	                                    <li class="m-4">
	                                        Policy related issues were referred to 
	                                        PFRDA for issuing suitable clarifications/guidelines.
	                                    </li>
	                                </ol>
	                            </li>
	
	                            <li class="m-4">
	                                In compliance with PFRDA policy for transferring of grievances from the Trust to PFRDA, 
	                                <input class="form-control" type="text" id="pfrdaPolicy" name="<portlet:namespace />pfrdaPolicy">
	                                physical letters were issued to the Nodal Offices/PoPs/APY-SPs during the 
	                                <input class="form-control" type="text" id="date_6" name="<portlet:namespace />date_6">
	                                2021 quarter where the grievances have crossed 60 days and still pending for
	                                resolution.
	                            </li>
	                        </ul>
	                    </div>
	                </div>
	            </div>
	
	
	            <div class="row mt-5">
	                <div class="col-12">
	                    <label>Submitted for kind information and further instructions, if any.</label>
	                </div>
	            </div>
	
	            <div class="form-inline row">
	                <div class="col-md-12">
	                    <p class="bold-text">Sashi Krishnan</p>
	                    <p class="bold-text">मुख्य कार्यकारी अधिकारी </p>
	                    <p class="bold-text">Chief Executive Officer </p>
	                    <p>दिनांक / Date:<input type="date" class="form-control date_7" id="date_7" name="<portlet:namespace />date_7"><br></p>
	                </div>
	            </div>
	            
	            <div class="row text-center">
		            <div class="col-md-12">
		                <input type="submit" class="nps-btn" id="btn-submit" value="Submit">
		            </div>
	        	</div>
            
            </form>
            
        </div>
  
</div>     

<script type="text/javascript">
//var table = null;

$(document).ready(function() {
	//table = $("#table1").DataTable();
});
</script>

<style type="text/css">
#myTable_length, #myTable_filter, #myTable_info, #myTable_paginate {
	display: none;
} 

 /* #table1_length, #table1_filter, #table1_info, #table1_paginate {
	display: none;
}  */

label.error {
	display: none !important;
}

input.error {
	border-color: red;
}

/* .page_title {
	font-size: 18px;
} */

#myTable label.error {
	display: block !important;
}
</style>

<script type="text/javascript">

$(document).ready(function() {
	var t1col1 = 0;	
	 $(".column_input_a").change(function() {
		console.log("Called when Input field Changed");
		var total_column_a = 0;
		$(".column_input_a").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				total_column_a += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", total_column_a);
		t1col1 = $("#opening_balance_10").val(total_column_a); 
	});
	console.log(t1col1);
	 
	var t1col2 = 0;
	 $(".column_input_b").change(function() {
			console.log("Called when Input field Changed");
			var total_column_b = 0;
			$(".column_input_b").each(function() {
				var currentVal = parseFloat($(this).val());
				if(!isNaN(currentVal)) {
					total_column_b += currentVal; 
				}
			});
			console.log("Total Sum ::::: ", total_column_b);
			t1col2 = $("#escalated_to_npst_10").val(total_column_b);
		});  
	 
	 var t1col3 = 0;
	 $(".column_input_c").change(function() {
			console.log("Called when Input field Changed");
			var total_column_c = 0;
			$(".column_input_c").each(function() {
				var currentVal = parseFloat($(this).val());
				if(!isNaN(currentVal)) {
					total_column_c += currentVal; 
				}
			});
			console.log("Total Sum ::::: ", total_column_c);
			t1col3 = $("#grievances_received_10").val(total_column_c);
		});  
	 
	 var t1col4 = 0;	
	 $(".column_input_d").change(function() {
			console.log("Called when Input field Changed");
			var total_column_d = 0;
			$(".column_input_d").each(function() {
				var currentVal = parseFloat($(this).val());
				if(!isNaN(currentVal)) {
					total_column_d += currentVal; 
				}
			});
			console.log("Total Sum ::::: ", total_column_d);
			t1col4 = $("#grievances_assigned_10").val(total_column_d);
		});
		
	 	var t1col5 = 0;	
		$(".column_input_e").change(function() {
			console.log("Called when Input field Changed");
			var total_column_e = 0;
			$(".column_input_e").each(function() {
				var currentVal = parseFloat($(this).val());
				if(!isNaN(currentVal)) {
					total_column_e += currentVal; 
				}
			});
			console.log("Total Sum ::::: ", total_column_e);
			t1col5 = $("#grievances_resolved_10").val(total_column_e);
		});
		
		var t1col6 = 0;	
		$(".column_input_f").change(function() {
			console.log("Called when Input field Changed");
			var total_column_f = 0;
			$(".column_input_f").each(function() {
				var currentVal = parseFloat($(this).val());
				if(!isNaN(currentVal)) {
					total_column_f += currentVal; 
				}
			});
			console.log("Total Sum ::::: ", total_column_f);
			t1col6 = $("#outstanding_grievances_10").val(total_column_f);
		});
		
		//table scheme wise
	
		var t2row1 = 0;	
		 $(".openbal_col").change(function() {
			var op = 0;
			$(".openbal_col").each(function() {
				var currentVal = parseFloat($(this).val());
				if(!isNaN(currentVal)) {
					op += currentVal; 
				}
			});
			console.log("Total Sum ::::: ", op);
			t2row1 = $("#total_0").val(op); 
		});
		 
		 var t2row2 = 0;	
		 $(".received").change(function() {
			var received = 0;
			$(".received").each(function() {
				var currentVal = parseFloat($(this).val());
				if(!isNaN(currentVal)) {
					received += currentVal; 
				}
			});
			console.log("Total Sum ::::: ", received);
			t2row2 = $("#total_1").val(received); 
		});
		 
		 var t2row3 = 0;	
		 $(".resolved").change(function() {
			var resolved = 0;
			$(".resolved").each(function() {
				var currentVal = parseFloat($(this).val());
				if(!isNaN(currentVal)) {
					resolved += currentVal; 
				}
			});
			console.log("Total Sum ::::: ", resolved);
			t2row3 = $("#total_2").val(resolved); 
		});
		 
		 var t2row4 = 0;	
		 $(".outstanding").change(function() {
			var outstanding = 0;
			$(".outstanding").each(function() {
				var currentVal = parseFloat($(this).val());
				if(!isNaN(currentVal)) {
					outstanding += currentVal; 
				}
			});
			console.log("Total Sum ::::: ", outstanding);
			t2row4 = $("#total_3").val(outstanding); 
		});
		
	//table 3a
	var t3acol1 = 0;	
	 $(".t3a_q1").change(function() {
		var t3a_1 = 0;
		$(".t3a_q1").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t3a_1 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t3a_1);
		t3acol1 = $("#cause_wise_q1_23").val(t3a_1); 
	});
	 
	 var t3acol2 = 0;	
	 $(".t3a_q2").change(function() {
		var t3a_2 = 0;
		$(".t3a_q2").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t3a_2 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t3a_2);
		t3acol2 = $("#cause_wise_q2_23").val(t3a_2); 
	});
	 
	 var t3acol3 = 0;	
	 $(".t3a_q3").change(function() {
		var t3a_3 = 0;
		$(".t3a_q3").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t3a_3 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t3a_3);
		t3acol3 = $("#cause_wise_q3_23").val(t3a_3); 
	});
	 
	 var t3acol4 = 0;	
	 $(".t3a_q4").change(function() {
		var t3a_4 = 0;
		$(".t3a_q4").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t3a_4 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t3a_4);
		t3acol4 = $("#cause_wise_q4_23").val(t3a_4); 
	});
	 
	 //table 3b
	 
	 var t3bcol1 = 0;	
	 $(".t3b_q1").change(function() {
		var t3b_1 = 0;
		$(".t3b_q1").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t3b_1 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t3b_1);
		t3bcol1 = $("#grievances_3b_q1_11").val(t3b_1); 
	});
	 
	 var t3bcol2 = 0;	
	 $(".t3b_q2").change(function() {
		var t3b_2 = 0;
		$(".t3b_q2").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t3b_2 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t3b_2);
		t3bcol2 = $("#grievances_3b_q2_11").val(t3b_2); 
	});
	 
	 var t3bcol3 = 0;	
	 $(".t3b_q3").change(function() {
		var t3b_3 = 0;
		$(".t3b_q3").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t3b_3 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t3b_3);
		t3bcol3 = $("#grievances_3b_q3_11").val(t3b_3); 
	});
	 
	 var t3bcol4 = 0;	
	 $(".t3b_q4").change(function() {
		var t3b_4 = 0;
		$(".t3b_q4").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t3b_4 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t3b_4);
		t3bcol1 = $("#grievances_3b_q4_11").val(t3b_4); 
	});
	 
	 var t3bcol5 = 0;	
	 $(".t3b_qOne").change(function() {
		var t3b_5 = 0;
		$(".t3b_qOne").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t3b_5 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t3b_5);
		t3bcol5 = $("#grievances_3b_q1One_11").val(t3b_5); 
	});
	
	 
	 var t3bcol6 = 0;	
	 $(".t3b_qTwo").change(function() {
		var t3b_6 = 0;
		$(".t3b_qTwo").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t3b_6 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t3b_6);
		t3bcol6 = $("#grievances_3b_q2One_11").val(t3b_6); 
	});
	 
	 //table 4
	 
	 var t4col1 = 0;	
	 $(".t4_q1").change(function() {
		var t4_1 = 0;
		$(".t4_q1").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4_1 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4_1);
		t4col1 = $("#entity_wise_outstanding_q1_10").val(t4_1); 
	});
	 
	 var t4col2 = 0;	
	 $(".t4_q2").change(function() {
		var t4_2 = 0;
		$(".t4_q2").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4_2 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4_2);
		t4col2 = $("#entity_wise_outstanding_q2_10").val(t4_2); 
	});
	 
	 var t4col3 = 0;	
	 $(".t4_q3").change(function() {
		var t4_3 = 0;
		$(".t4_q3").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4_3 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4_3);
		t4col3 = $("#entity_wise_outstanding_q3_10").val(t4_3); 
	});
	 
	 var t4col4 = 0;	
	 $(".t4_q4").change(function() {
		var t4_4 = 0;
		$(".t4_q4").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4_4 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4_4);
		t4col1 = $("#entity_wise_outstanding_q4_10").val(t4_4); 
	});
	 
	 var t4col5 = 0;	
	 $(".t4_qOne").change(function() {
		var t4_5 = 0;
		$(".t4_qOne").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4_5 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4_5);
		t4col5 = $("#entity_wise_outstanding_q1One_10").val(t4_5); 
	});
	
	 var t4col6 = 0;	
	 $(".t4_qTwo").change(function() {
		var t4_6 = 0;
		$(".t4_qTwo").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4_6 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4_6);
		t4col6 = $("#entity_wise_outstanding_q2One_10").val(t4_6); 
	});
	 
	//table 4a
	
	var t4acol1 = 0;	
	 $(".t4a_cra").change(function() {
		var t4a_1 = 0;
		$(".t4a_cra").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4a_1 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4a_1);
		t4acol1 = $("#t4a_referrals_8").val(t4a_1); 
	});
	 
	 var t4acol2 = 0;	
	 $(".t4a_cg").change(function() {
		var t4a_2 = 0;
		$(".t4a_cg").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4a_2 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4a_2);
		t4acol2 = $("#t4a_0_7_8").val(t4a_2); 
	});
	 
	 var t4acol3 = 0;	
	 $(".t4a_sg").change(function() {
		var t4a_3 = 0;
		$(".t4a_sg").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4a_3 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4a_3);
		t4acol3 = $("#t4a_8_15_8").val(t4a_3); 
	});
	 
	 var t4acol4 = 0;	
	 $(".t4a_pop").change(function() {
		var t4a_4 = 0;
		$(".t4a_pop").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4a_4 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4a_4);
		t4acol4 = $("#t4a_16_31_8").val(t4a_4); 
	});
	 
	 var t4acol5 = 0;	
	 $(".t4a_corp").change(function() {
		var t4a_5 = 0;
		$(".t4a_corp").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4a_5 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4a_5);
		t4acol5 = $("#t4a_32_90_8").val(t4a_5); 
	});
	 
	 var t4acol6 = 0;	
	 $(".t4a_tb").change(function() {
		var t4a_6 = 0;
		$(".t4a_tb").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4a_6 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4a_6);
		t4acol6 = $("#t4a_91_180_8").val(t4a_6); 
	});
	
	 var t4acol7 = 0;	
	 $(".t4a_nps").change(function() {
		var t4a_7 = 0;
		$(".t4a_nps").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4a_7 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4a_7);
		t4acol7 = $("#t4a_185_365_8").val(t4a_7); 
	});
	 
	 var t4acol8 = 0;	
	 $(".t4a_others").change(function() {
		var t4a_8 = 0;
		$(".t4a_others").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4a_8 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4a_8);
		t4acol8 = $("#t4a_366_and_above_8").val(t4a_8); 
	});
	 
	 //table 4b
	 
	 var t4bcol1 = 0;	
	 $(".t4b_1").change(function() {
		var t4b_1 = 0;
		$(".t4b_1").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4b_1 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4b_1);
		t4bcol1 = $("#t4b_referrals_26").val(t4b_1); 
	});
	 
	 var t4bcol2 = 0;	
	 $(".t4b_2").change(function() {
		var t4b_2 = 0;
		$(".t4b_2").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4b_2 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4b_2);
		t4bcol2 = $("#t4b_0_7_26").val(t4b_2); 
	}); 
	 
	 var t4bcol3 = 0;	
	 $(".t4b_3").change(function() {
		var t4b_3 = 0;
		$(".t4b_3").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4b_3 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4b_3);
		t4bcol3 = $("#t4b_8_15_26").val(t4b_3); 
	});
	 
	 var t4bcol4 = 0;	
	 $(".t4b_4").change(function() {
		var t4b_4 = 0;
		$(".t4b_4").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4b_4 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4b_4);
		t4bcol4 = $("#t4b_16_31_26").val(t4b_4); 
	});
	 
	 var t4bcol5 = 0;	
	 $(".t4b_5").change(function() {
		var t4b_5 = 0;
		$(".t4b_5").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4b_5 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4b_5);
		t4bcol5 = $("#t4b_32_90_26").val(t4b_5); 
	});
	 
	 var t4bcol6 = 0;	
	 $(".t4b_6").change(function() {
		var t4b_6 = 0;
		$(".t4b_6").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4b_6 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4b_6);
		t4bcol6 = $("#t4b_91_180_26").val(t4b_6); 
	});
	 
	 var t4bcol7 = 0;	
	 $(".t4b_7").change(function() {
		var t4b_7 = 0;
		$(".t4b_7").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4b_7 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4b_7);
		t4bcol7 = $("#t4b_185_365_26").val(t4b_7); 
	});
	 
	 var t4bcol8 = 0;	
	 $(".t4b_8").change(function() {
		var t4b_8 = 0;
		$(".t4b_8").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4b_8 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4b_8);
		t4bcol8 = $("#t4b_366_and_above_26").val(t4b_8); 
	});
	 
	 //table 4c
	 
	 var t4ccol1 = 0;	
	 $(".t4c_cra").change(function() {
		var t4c_1 = 0;
		$(".t4c_cra").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4c_1 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4c_1);
		t4ccol1 = $("#t4c_referrals_8").val(t4c_1); 
	});
	 
	 var t4ccol2 = 0;	
	 $(".t4c_nloo").change(function() {
		var t4c_2 = 0;
		$(".t4c_nloo").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4c_2 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4c_2);
		t4ccol2 = $("#t4c_0_7_8").val(t4c_2); 
	}); 
	 
	 var t4ccol3 = 0;	
	 $(".t4c_nlao").change(function() {
		var t4c_3 = 0;
		$(".t4c_nlao").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4c_3 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4c_3);
		t4ccol3 = $("#t4c_8_15_8").val(t4c_3); 
	});
	 
	 var t4ccol4 = 0;	
	 $(".t4c_apy").change(function() {
		var t4c_4 = 0;
		$(".t4c_apy").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4c_4 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4c_4);
		t4ccol4 = $("#t4c_16_31_8").val(t4c_4); 
	});
	 
	 var t4ccol5 = 0;	
	 $(".t4c_nlcc").change(function() {
		var t4c_5 = 0;
		$(".t4c_nlcc").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4c_5 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4c_5);
		t4ccol5 = $("#t4c_32_90_8").val(t4c_5); 
	});
	 
	 var t4ccol6 = 0;	
	 $(".t4c_tb").change(function() {
		var t4c_6 = 0;
		$(".t4c_tb").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4c_6 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4c_6);
		t4ccol6 = $("#t4c_91_180_8").val(t4c_6); 
	});
	 
	 var t4ccol7 = 0;	
	 $(".t4c_nps").change(function() {
		var t4c_7 = 0;
		$(".t4c_nps").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4c_7 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4c_7);
		t4ccol7 = $("#t4c_185_365_8").val(t4c_7); 
	});
	 
	 var t4ccol8 = 0;	
	 $(".t4c_others").change(function() {
		var t4c_8 = 0;
		$(".t4c_others").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4c_8 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4c_8);
		t4ccol8 = $("#t4c_366_and_above_8").val(t4c_8); 
	});
	
	 //table 4d
	 
	 var t4dcol1 = 0;	
	 $(".t4d_1").change(function() {
		var t4d_1 = 0;
		$(".t4d_1").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4d_1 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4d_1);
		t4dcol1 = $("#t4d_referrals_11").val(t4d_1); 
	});
	 
	 var t4dcol2 = 0;	
	 $(".t4d_2").change(function() {
		var t4d_2 = 0;
		$(".t4d_2").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4d_2 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4d_2);
		t4dcol2 = $("#t4d_0_7_11").val(t4d_2); 
	}); 
	 
	 var t4dcol3 = 0;	
	 $(".t4d_3").change(function() {
		var t4d_3 = 0;
		$(".t4d_3").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4d_3 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4d_3);
		t4dcol3 = $("#t4d_8_15_11").val(t4d_3); 
	});
	 
	 var t4dcol4 = 0;	
	 $(".t4d_4").change(function() {
		var t4d_4 = 0;
		$(".t4d_4").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4d_4 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4d_4);
		t4dcol4 = $("#t4d_16_31_11").val(t4d_4); 
	});
	 
	 var t4dcol5 = 0;	
	 $(".t4d_5").change(function() {
		var t4d_5 = 0;
		$(".t4d_5").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4d_5 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4d_5);
		t4dcol5 = $("#t4d_32_90_11").val(t4d_5); 
	});
	 
	 var t4dcol6 = 0;	
	 $(".t4d_6").change(function() {
		var t4d_6 = 0;
		$(".t4d_6").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4d_6 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4d_6);
		t4dcol6 = $("#t4d_91_180_11").val(t4d_6); 
	});
	 
	 var t4dcol7 = 0;	
	 $(".t4d_7").change(function() {
		var t4d_7 = 0;
		$(".t4d_7").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4d_7 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4d_7);
		t4dcol7 = $("#t4d_185_365_11").val(t4d_7); 
	});
	 
	 var t4dcol8 = 0;	
	 $(".t4d_8").change(function() {
		var t4d_8 = 0;
		$(".t4d_8").each(function() {
			var currentVal = parseFloat($(this).val());
			if(!isNaN(currentVal)) {
				t4d_8 += currentVal; 
			}
		});
		console.log("Total Sum ::::: ", t4d_8);
		t4dcol8 = $("#t4d_366_and_above_11").val(t4d_8); 
	});
	 
	
	toastr.options = {
	  "debug": false,
	  "positionClass": "toast-bottom-right",
	  "onclick": null,
	  "fadeIn": 300,
	  "fadeOut": 1000,
	  "timeOut": 6000,
	  "extendedTimeOut": 1000
	}
	
	$('#date_1').datepicker({
		format: "yyyy",
        viewMode: "years", 
        minViewMode: "years",
        language: 'en',
        autoclose:true
        
    });
	
	$('#date_2').datepicker({
		format: "M",
        viewMode: "months", 
        minViewMode: "months",
        language: 'en',
        autoclose:true
        
    });
	
	$('#date_3').datepicker({
		format: "M",
        viewMode: "months", 
        minViewMode: "months",
        language: 'en',
        autoclose:true
        
    });
	
	$('#date_4').datepicker({
		format: "yyyy",
        viewMode: "years", 
        minViewMode: "years",
        language: 'en',
        autoclose:true
        
    });
	
	$( ".date_4" ).on( "change", function() {
	  	
		var setdate = $('#date_4').val();
	  	console.log(setdate);
	  	$( ".fy" ).text('');
		$( ".fy" ).text(setdate);
	});
	
	$('#date_5').datepicker({
		format: "yyyy",
        viewMode: "years", 
        minViewMode: "years",
        language: 'en',
        autoclose:true
        
    });
	
	$('#date_6').datepicker({
		format: "yyyy",
        viewMode: "years", 
        minViewMode: "years",
        language: 'en',
        autoclose:true
        
    });
	
	  $("#bmcgmsAgenda").validate({
		 
	}); 
	  
	  $('#table1 tbody tr td input[type=number]').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	  
	  $('#table2 tbody tr td input[type=number]').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	  
	  $('#table3 tbody tr td input[type=number]').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	  
	  $('#table4 tbody tr td input[type=number]').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	  
	  $('#table5 tbody tr td input[type=number]').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	  
	  $('#table6 tbody tr td input[type=number]').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	  
	  $('#table7 tbody tr td input[type=number]').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	  
	  $('#table8 tbody tr td input[type=number]').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	  
	  $('#table9 tbody tr td input[type=number]').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	  
	  $('#table10 tbody tr td input[type=number]').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	  
	  $('input[type="text"]:not([readonly])').each(function() {
		  $(this).rules("add", 
		            {
		        		required: true
		            }
		        );
      }); 
	 
	 /* $('input:not([readonly])').each(function() {
	        $(this).rules("add", 
	            {
	        		required: true
	            }
	        );
	    });    */

	$('#success-message').hide();
	$('#error-message').hide();

    $("#bmcgmsAgenda").on('submit', (function(e) {
    	//$("#btn-submit").click(function() {	
    		e.preventDefault();
    		
    		if($( "#bmcgmsAgenda" ).valid()){

    			console.log("Inside button click");
    			
    			//if($("form.form").valid()){
    				var formData = new FormData($("form.form")[0]);
    				//var formData = new FormData();
    				
    				$(".animaion").show();
    				$.ajax({
    		            type: "POST",
    		            enctype: 'multipart/form-data',
    		            processData: false,
    		            contentType: false,
    		            cache: false,
    		            url: '${cgmsAgendaResourceURL}',
    		            data: formData,
    		            success: function(data) {
    		            	console.log("Inside Success");
    		            	//$('#success-message').show();
    		            	//$("#success-message").html("<i class='fa fa-check-circle mr-2'></i>Form has been submitted successfully!");
    		            	$(".animaion").hide();
    		            	toastr.success('Form has been submitted successfully!');
    		            	//$("form.form")[0].reset();
    		            	$("#bmcgmsAgenda")[0].reset();
    		            },
    		            error: function() {
    		            	//$('#error-message').show();
    		            	//$("#error-message").html("<i class='fa fa-times-circle mr-2'></i>Something went wrong please try again!");
    		            	$(".animaion").hide();
    		            	toastr.error('An error occured while submitting the form. Please try again');
    		            	console.log("Error Occured in ajax call");
    		            },
    		            complete: function(){
    		            	$(".animaion").hide();
    						setTimeout(function() { location.reload(true); }, 5000);
    			        }
    		
    		        });
    			//} 
    			 /* else {
    	    		toastr.error('Please fill the required field(s).');
    	   		}  */
    			
    			
    		}

    	}));
    
});

</script>




   