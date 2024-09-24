package nps.email.service.constants;

import java.util.HashMap;
import java.util.Map;

import nps.email.service.model.ErrorAPIDetails;

public class ErrorAPIDetailsMap {
	public static Map<String, ErrorAPIDetails> errorAPIDetailsMap = new HashMap<String, ErrorAPIDetails>();
	
	static  {
		/**
		 * Annex 10
		 */
		
		errorAPIDetailsMap.put("Griev_against_TB", new ErrorAPIDetails("Griev_against_TB", "griev_against_TBResponse/griev_against_TBResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Monthly_Avg_Bal", new ErrorAPIDetails("Monthly_Avg_Bal", "monthly_Avg_BalResponse/monthly_Avg_BalResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NCRA_Remittance_Report", new ErrorAPIDetails("NCRA_Remittance_Report", "nCRA_Remittance_ReportResponse/nCRA_Remittance_ReportResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NPST_Acc_Amt", new ErrorAPIDetails("NPST_Acc_Amt", "nPST_Acc_AmtResponse/nPST_Acc_AmtResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Annexure_4C", new ErrorAPIDetails("Annexure_4C", "annexure_4CResponse/annexure_4CResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Annexure_4D", new ErrorAPIDetails("Annexure_4D", "annexure_4DResponse/annexure_4DResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("TB_Paid_Monetary_Damage", new ErrorAPIDetails("TB_Paid_Monetary_Damage", "tB_Paid_Monetary_DamageResponse/tB_Paid_Monetary_DamageResult/Streams/out_xml/Value/TABLE"));
		/**
		 * Annex 10 end
		 */
		/**
		 * Annex 7 start
		 */
		errorAPIDetailsMap.put("Collection_Summary", new ErrorAPIDetails("Collection_Summary", "collection_SummaryResponse/collection_SummaryResult/Streams/out_xml/Value/TABLE"));
		
		/*All_Electronic_Accepted stored procedure will be used for following 5 sheets as all of them are similar*/
		errorAPIDetailsMap.put("All_Chq_Accepted", new ErrorAPIDetails("All_Electronic_Accepted", "all_Electronic_AcceptedResponse/all_Electronic_AcceptedResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("All_ENPS_Accepted", new ErrorAPIDetails("All_Electronic_Accepted", "all_Electronic_AcceptedResponse/all_Electronic_AcceptedResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("All_Dremit_Accepted", new ErrorAPIDetails("All_Electronic_Accepted", "all_Electronic_AcceptedResponse/all_Electronic_AcceptedResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("All_Electronic_Accepted", new ErrorAPIDetails("All_Electronic_Accepted", "all_Electronic_AcceptedResponse/all_Electronic_AcceptedResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Consolidated_Collection_Report", new ErrorAPIDetails("All_Electronic_Accepted", "all_Electronic_AcceptedResponse/all_Electronic_AcceptedResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("All_Electronic_Rejects", new ErrorAPIDetails("All_Electronic_Rejects", "all_Electronic_RejectsResponse/all_Electronic_RejectsResult/Streams/out_xml/Value/TABLE"));
		/* Rejection_Reasons stored procedure will be used for following 5 sheets as all of them are similar */
		errorAPIDetailsMap.put("Rejection_Reasons", new ErrorAPIDetails("Rejection_Reasons", "rejection_ReasonsResponse/rejection_ReasonsResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Invalid_Acc_No", new ErrorAPIDetails("Invalid_Acc_No", "rejection_ReasonsResponse/rejection_ReasonsResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Amount_Mismatch", new ErrorAPIDetails("Amount_Mismatch", "rejection_ReasonsResponse/rejection_ReasonsResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Invalid_Tran_ID", new ErrorAPIDetails("Invalid_Tran_ID", "rejection_ReasonsResponse/rejection_ReasonsResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Expired_TID", new ErrorAPIDetails("Expired_TID", "rejection_ReasonsResponse/rejection_ReasonsResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("No_TranID_inward_msg", new ErrorAPIDetails("No_TranID_inward_msg", "rejection_ReasonsResponse/rejection_ReasonsResult/Streams/out_xml/Value/TABLE"));
		
		/*All_Chq_Failed_TID_Expired, Chq_Clearance_TAT, Chq_Rejected_JAN2022, Electronic_Rejects_Summary, PAO_Repeated_Mistakes, PAO_Submitting_Chq stored procedures are not required, hence not created. */
		
		/* new api added 18/10/2022 */
		errorAPIDetailsMap.put("All_Chq_Failed_TID_Expired", new ErrorAPIDetails("All_Chq_failed_tid", "all_Chq_failed_tidResponse/all_Chq_failed_tidResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Chq_Clearance_TAT", new ErrorAPIDetails("chq_clearance_tat", "chq_clearance_tatResponse/chq_clearance_tatResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Chq_Rejected", new ErrorAPIDetails("chq_rejected", "chq_rejectedResponse/chq_rejectedResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Collections_TID_ToBe_Enabled", new ErrorAPIDetails("coll_tid_tobe_enabled", "coll_tid_tobe_enabledResponse/coll_tid_tobe_enabledResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Electronic_Rejects_Summary", new ErrorAPIDetails("electronic_rejects_summ", "electronic_rejects_summResponse/electronic_rejects_summResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("PAO_Repeated_mistakes", new ErrorAPIDetails("pao_repeated_mistakes", "pao_repeated_mistakesResponse/pao_repeated_mistakesResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("PAO_Submitting_Chq", new ErrorAPIDetails("pao_submitting_chq", "pao_submitting_chqResponse/pao_submitting_chqResult/Streams/out_xml/Value/TABLE"));
		/* new api added 18/10/2022 */
		/**
		 * Annex 7 end
		 */
		
		/**
		 * Annex 10b start
		 */
		errorAPIDetailsMap.put("Email_Complaint_Tracker", new ErrorAPIDetails("ANX_10B", "aNX_10BResponse/aNX_10BResult/Streams/out_xml/Value/TABLE"));
		/**
		 * Annex 10b start
		 */
		/**
		 * Annex 10a start
		 */
		errorAPIDetailsMap.put("NPST_Withdrawal_Summary", new ErrorAPIDetails("AnX10A_Wthdrwl", "anX10A_WthdrwlResponse/anX10A_WthdrwlResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("CRA_Hold_Amt_Ageing", new ErrorAPIDetails("AnX10A_Ageing", "anX10A_AgeingResponse/anX10A_AgeingResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Top_Returned_Reasons", new ErrorAPIDetails("AnX10A_RTRN_RSN", "anX10A_RTRN_RSNResponse/anX10A_RTRN_RSNResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Returned_Remittances", new ErrorAPIDetails("AnX10A_Ret_Remit", "anX10A_Ret_RemitResponse/anX10A_Ret_RemitResult/Streams/out_xml/Value/TABLE"));
		
		
		errorAPIDetailsMap.put("Returned_Remittances_Ageing", new ErrorAPIDetails("AnX10A_Ageing", "anX10A_AgeingResponse/anX10A_AgeingResult/Streams/out_xml/Value/TABLE"));
		/**
		 * Annex 10a end
		 */
		
		/**
		 * Annex 5 start
		 */
		errorAPIDetailsMap.put("Sheet1", new ErrorAPIDetails("Annexure_5", "annexure_5Response/annexure_5Result/Streams/out_xml/Value/TABLE"));
		/**
		 * Annex 5 end
		 */
		
		/**
		 * Annex 5a start
		 */
		errorAPIDetailsMap.put("Transaction Return MIS", new ErrorAPIDetails("Annexure_5A", "annexure_5AResponse/annexure_5AResult/Streams/out_xml/Value/TABLE"));
		/**
		 * Annex 5a end
		 */
		
		/**
		 * Annex 3 start
		 */
		errorAPIDetailsMap.put("CL BAL", new ErrorAPIDetails("Annexure_3", "annexure_3Response/annexure_3Result/Streams/out_xml/Value/TABLE"));
		/**
		 * Annex 3 end
		 */
		
		/**
		 * Annex 7a start
		 */
		errorAPIDetailsMap.put("NSDL", new ErrorAPIDetails("Anx_7A_NSDL", "anx_7A_NSDLResponse/anx_7A_NSDLResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("KFINTECH", new ErrorAPIDetails("Anx_7A_KFT", "anx_7A_KFTResponse/anx_7A_KFTResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Annex 7a end
		 */
		
		/**
		 * Annex 11 start
		 */
		
		errorAPIDetailsMap.put("Anx_IV", new ErrorAPIDetails("Anx11_Quarterly_Withdrawl_Delay", "anx11_Quarterly_Withdrawl_DelayResponse/anx11_Quarterly_Withdrawl_DelayResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Anx_IVA", new ErrorAPIDetails("Anx11_Quarterly_Withdrawl_Delay", "anx11_Quarterly_Withdrawl_DelayResponse/anx11_Quarterly_Withdrawl_DelayResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Anx_IVB", new ErrorAPIDetails("Anx11_Quarterly_Withdrawl_Delay", "anx11_Quarterly_Withdrawl_DelayResponse/anx11_Quarterly_Withdrawl_DelayResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Anx_IVC", new ErrorAPIDetails("Anx11_Quarterly_Withdrawl_Delay", "anx11_Quarterly_Withdrawl_DelayResponse/anx11_Quarterly_Withdrawl_DelayResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Anx_IVD", new ErrorAPIDetails("Anx11_Quarterly_Withdrawl_Delay", "anx11_Quarterly_Withdrawl_DelayResponse/anx11_Quarterly_Withdrawl_DelayResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("NCRA_Avg_Amt", new ErrorAPIDetails("NCRA_Avg_Amt", "nCRA_Avg_AmtResponse/nCRA_Avg_AmtResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("KCRA_Avg_Amt", new ErrorAPIDetails("NCRA_Avg_Amt", "nCRA_Avg_AmtResponse/nCRA_Avg_AmtResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("CAMS_Avg_Amt", new ErrorAPIDetails("NCRA_Avg_Amt", "nCRA_Avg_AmtResponse/nCRA_Avg_AmtResult/Streams/out_xml/Value/TABLE"));
		
		
		errorAPIDetailsMap.put("KCRA_Acc_List", new ErrorAPIDetails("KCRA_Acc_List", "kCRA_Acc_ListResponse/kCRA_Acc_ListResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("KCRA_Acc_List", new ErrorAPIDetails("KCRA_Acc_List", "kCRA_Acc_ListResponse/kCRA_Acc_ListResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("KCRA_Closing_Bal", new ErrorAPIDetails("KCRA_Closing_Bal", "kCRA_Closing_BalResponse/kCRA_Closing_BalResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NCRA_Closing_Bal", new ErrorAPIDetails("KCRA_Closing_Bal", "kCRA_Closing_BalResponse/kCRA_Closing_BalResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("CAMS_Closing_Bal", new ErrorAPIDetails("KCRA_Closing_Bal", "kCRA_Closing_BalResponse/kCRA_Closing_BalResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("KCRA_ENPS_Serv_Charges", new ErrorAPIDetails("KCRA_ENPS_Serv_Charges", "kCRA_ENPS_Serv_ChargesResponse/kCRA_ENPS_Serv_ChargesResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NCRA_ENPS_Serv_Charges", new ErrorAPIDetails("KCRA_ENPS_Serv_Charges", "kCRA_ENPS_Serv_ChargesResponse/kCRA_ENPS_Serv_ChargesResult/Streams/out_xml/Value/TABLE"));
		
		
		errorAPIDetailsMap.put("KCRA_Acc_Total_Amt", new ErrorAPIDetails("KCRA_Acc_Total_Amt", "kCRA_Acc_Total_AmtResponse/kCRA_Acc_Total_AmtResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NCRA_Acc_Total_Amt", new ErrorAPIDetails("KCRA_Acc_Total_Amt", "kCRA_Acc_Total_AmtResponse/kCRA_Acc_Total_AmtResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("NCRA_Acc_Total_Amt", new ErrorAPIDetails("KCRA_Acc_Total_Amt", "kCRA_Acc_Total_AmtResponse/kCRA_Acc_Total_AmtResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NCRA_Acc_Total_Amt", new ErrorAPIDetails("KCRA_Acc_Total_Amt", "kCRA_Acc_Total_AmtResponse/kCRA_Acc_Total_AmtResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NCRA_Acc_Total_Amt", new ErrorAPIDetails("KCRA_Acc_Total_Amt", "kCRA_Acc_Total_AmtResponse/kCRA_Acc_Total_AmtResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NCRA_Acc_Total_Amt", new ErrorAPIDetails("KCRA_Acc_Total_Amt", "kCRA_Acc_Total_AmtResponse/kCRA_Acc_Total_AmtResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NCRA_Acc_Total_Amt", new ErrorAPIDetails("KCRA_Acc_Total_Amt", "kCRA_Acc_Total_AmtResponse/kCRA_Acc_Total_AmtResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NCRA_Acc_Total_Amt", new ErrorAPIDetails("KCRA_Acc_Total_Amt", "kCRA_Acc_Total_AmtResponse/kCRA_Acc_Total_AmtResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Annex 11 end
		 */
		
		/**
		 * Annex 6 Start
		 */
		errorAPIDetailsMap.put("All Citizen_UOS", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Andhra Pradesh", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Arunachal Pradesh", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Assam", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Atal Pension Yojana_APY", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Bihar", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("CGAB", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Central Govt", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Chandigarh", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Chhatisgarh", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Corporate", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("D-Remit", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Damodar_kolkata", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("ENPS", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Goa", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Gujarat", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Haryana Govt", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Himachal Pradesh", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Jammu_Kashmir", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Jharkhand", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Karnataka", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Kerala", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Madhya Pradesh", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Maharashtra", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Manipur", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Meghalaya", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Mizoram", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NACH-NSDL", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NPS-Lite", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Nagaland", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Odisha", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Omni Bus", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Puducherry", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Punjab", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Rajasthan", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Rejection Parking", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Sikkim", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		//errorAPIDetailsMap.put("Summary_consolidate", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Tamil Nadu", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Telangana", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Tripura", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Uttar Pradesh", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Uttarakhand", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("West Bengal", new ErrorAPIDetails("Anx_6", "anx_6Response/anx_6Result/Streams/out_xml/Value/TABLE"));
		/**
		 * Annex 6 end
		 */
		
		/**
		 * Annex 4A start
		 */
		errorAPIDetailsMap.put("Report", new ErrorAPIDetails("Anx_4A", "anx_4AResponse/anx_4AResult/Streams/out_xml/Value/TABLE"));
		/**
		 * Annex 4A end
		 */
		
		/**
		 * Annex 4 start
		 */
		errorAPIDetailsMap.put("weekly_average_balance_report", new ErrorAPIDetails("Anx_4", "anx_4Response/anx_4Result/Streams/out_xml/Value/TABLE"));
		/**
		 * Annex 4 end
		 */
		
		/**
		 * PFM Weekly_NAV_KCRA start
		 */
		errorAPIDetailsMap.put("Weekly_NAV_KCRA", new ErrorAPIDetails("PFM/Weekly_NAV_KCRA", "weekly_NAV_KCRAResponse/weekly_NAV_KCRAResult/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Weekly_NAV_KCRA end
		 */
		/**
		 * PFM Weekly_NAV_NSDL start
		 */
		errorAPIDetailsMap.put("Weekly_NAV", new ErrorAPIDetails("PFM/Weekly_NAV_NCRA", "weekly_NAV_NCRAResponse/weekly_NAV_NCRAResult/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Weekly_NAV_NSDL end
		 */
		/**
		 * PFM Mn_Form1 start
		 */
		errorAPIDetailsMap.put("Form 1", new ErrorAPIDetails("PFM/Mn_Form1", "mn_Form1Response/mn_Form1Result/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form1 end
		 */
		/**
		 * PFM Mn_Form1 start
		 */
		errorAPIDetailsMap.put("Form 2", new ErrorAPIDetails("PFM/Mn_Form2", "mn_Form2Response/mn_Form2Result/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form2 end
		 */
		/**
		 * PFM Mn_Form3 start
		 */
		errorAPIDetailsMap.put("Form 3", new ErrorAPIDetails("PFM/Mn_Form3", "mn_Form3Response/mn_Form3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 3-1", new ErrorAPIDetails("PFM/Mn_Form3", "mn_Form3Response/mn_Form3Result/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form3 end
		 */
		/**
		 * PFM Mn_Form4 start
		 */
		errorAPIDetailsMap.put("Form 4 ATAL PENSION YOJANA", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 4 CORP CG", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 4 NPS LITE", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 4 CG", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 4 SG", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form4 end
		 */
		
		/**
		 * PFM Mn_Form5 start
		 */
		errorAPIDetailsMap.put("Form 5 C-I", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 5 C-II", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form5 end
		 */
		
		/**
		 * PFM Mn_Form6 start
		 */
		errorAPIDetailsMap.put("Form 6 E-I", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 6 E-II", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form6 end
		 */
		
		/**
		 * PFM Mn_Form7 start
		 */
		errorAPIDetailsMap.put("Form 7 G-I", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 7 G-II", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 7A A-T I", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form7B TTS", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form7 end
		 */
		
		/**
		 * PFM Mn_Form8 start
		 */
		errorAPIDetailsMap.put("Form 8_as_on_date", new ErrorAPIDetails("PFM/Mn_Form8", "mn_Form8Response/mn_Form8Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 8_during_the_month", new ErrorAPIDetails("PFM/Mn_Form8", "mn_Form8Response/mn_Form8Result/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form8 end
		 */
		
		/**
		 * PFM Mn_Form9 start
		 */
		errorAPIDetailsMap.put("Form 9", new ErrorAPIDetails("PFM/Mn_Form9", "mn_Form9Response/mn_Form9Result/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form9 end
		 */
		
		/**
		 * PFM Mn_Form10 start
		 */
		errorAPIDetailsMap.put("10 Industry", new ErrorAPIDetails("PFM/Mn_Form10", "mn_Form10Response/mn_Form10Result/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form10 end
		 */
		
		/**
		 * PFM Mn_Form11 start
		 */
		errorAPIDetailsMap.put("Form_11_Sponsor", new ErrorAPIDetails("PFM/Mn_Form11", "mn_Form11Response/mn_Form11Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form_11_Non_Sponsored", new ErrorAPIDetails("PFM/Mn_Form11", "mn_Form11Response/mn_Form11Result/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form11 end
		 */
		
		/**
		 * PFM Mn_Form12 start
		 */
		errorAPIDetailsMap.put("12 issuer", new ErrorAPIDetails("PFM/Mn_Form12", "mn_Form12Response/mn_Form12Result/Streams/out_xml/Value/TABLE"));
		/**
		 * PFM Mn_Form12 end
		 */
		
		/**
		 * PFM Mn_Form13,14 start
		 */
		errorAPIDetailsMap.put("13 Portfolio Data", new ErrorAPIDetails("PFM/Mn_Form13", "mn_Form13Response/mn_Form13Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("14 Transaction Data", new ErrorAPIDetails("PFM/Mn_Form14", "mn_Form14Response/mn_Form14Result/Streams/out_xml/Value/TABLE"));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**
		 * PFM Mn_Form 1-14 api changes start
		 */
		errorAPIDetailsMap.put("Form_4", new ErrorAPIDetails("PFM/Mn_Form2", "mn_Form2Response/mn_Form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2A", new ErrorAPIDetails("PFM/Mn_Form3", "mn_Form3Response/mn_Form3Result/Streams/out_xml/Value/TABLE"));
		//errorAPIDetailsMap.put("Form 3-1", new ErrorAPIDetails("PFM/Mn_Form3", "mn_Form3Response/mn_Form3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 APY", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 Corp CG", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 NPS Lite", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 CG", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 SG", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 C-I", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 C-II", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 E-I", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 E-II", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 G-I", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 G-II", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 A-T I", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 Other schemes", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		//errorAPIDetailsMap.put("Form 2 TTS", new ErrorAPIDetails("PFM/Mn_Form4CG", "mn_Form4CGResponse/mn_Form4CGResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 6_as_on_date", new ErrorAPIDetails("PFM/Mn_Form8", "mn_Form8Response/mn_Form8Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 6_during_the_month", new ErrorAPIDetails("PFM/Mn_Form8", "mn_Form8Response/mn_Form8Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 7", new ErrorAPIDetails("PFM/Mn_Form9", "mn_Form9Response/mn_Form9Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 8_Industry", new ErrorAPIDetails("PFM/Mn_Form10", "mn_Form10Response/mn_Form10Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 8_Sponsor", new ErrorAPIDetails("PFM/Mn_Form11", "mn_Form11Response/mn_Form11Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 8_Non_Sponsored", new ErrorAPIDetails("PFM/Mn_Form11", "mn_Form11Response/mn_Form11Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 8_issuer", new ErrorAPIDetails("PFM/Mn_Form12", "mn_Form12Response/mn_Form12Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 2 Portfolio Data", new ErrorAPIDetails("PFM/Mn_Form13", "mn_Form13Response/mn_Form13Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 3 Transactions Data", new ErrorAPIDetails("PFM/Mn_Form14", "mn_Form14Response/mn_Form14Result/Streams/out_xml/Value/TABLE"));
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**
		 * PFM Mn_Form13,14 end
		 */
		
		/**
		 * SBI_Scheme_Data start
		 */
		errorAPIDetailsMap.put("Scheme_Data", new ErrorAPIDetails("PFM/Mn_Scheme", "mn_SchemeResponse/mn_SchemeResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * SBI_Scheme_Data end
		 */
		/**
		 * IIAS Recommendation start
		 */
		errorAPIDetailsMap.put("IIAS_Recommendation", new ErrorAPIDetails("PFM/Mn_IIASRecom", "mn_IIASRecomResponse/mn_IIASRecomResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * IIAS Recommendation end
		 */
		/**
		 * SES Vote Matrix start
		 */
		errorAPIDetailsMap.put("Monthly Vote Matrix Results", new ErrorAPIDetails("PFM/Mn_SESVote", "mn_SESVoteResponse/mn_SESVoteResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * SES Vote Matrix end
		 */
		/**
		 * Monthly Website Data start
		 */
		errorAPIDetailsMap.put("website_data", new ErrorAPIDetails("PFM/Mn_Websitedata", "mn_WebsitedataResponse/mn_WebsitedataResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Monthly Website Data end
		 */
		/**
		 * Non unanimous Report start
		 */
		//errorAPIDetailsMap.put("mn_non_unanimous_voting", new ErrorAPIDetails("Mn_Non_Unanimous_Voting", "mn_NonUnanimousResponse/mn_NonUnanimousResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("mn_non_unanimous_voting", new ErrorAPIDetails("PFM/Mn_Non_Unanimous_Voting", "mn_Non_Unanimous_VotingResponse/mn_Non_Unanimous_VotingResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Non unanimous Report end
		 */
		
		/**
		 * Quarterly Report start
		 */
		errorAPIDetailsMap.put("Quarterly_report", new ErrorAPIDetails("PFM/QUARTERLY_REPORT", "qUARTERLY_REPORTResponse/qUARTERLY_REPORTResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Quarterly Report end
		 */
		
		/**
		 * Majority Vote start
		 */
		errorAPIDetailsMap.put("Majority Vote NPST", new ErrorAPIDetails("PFM/Majority_Vote", "majority_VoteResponse/majority_VoteResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Majority Vote end
		 */
		
		/**
		 * Vote Report start
		 */
		errorAPIDetailsMap.put("Consolidated Voting Report", new ErrorAPIDetails("PFM/ConsolidatedVotingReport", "consolidatedVotingReportResponse/consolidatedVotingReportResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("No. of Resolutions", new ErrorAPIDetails("PFM/No_Of_Resolutions", "no_Of_ResolutionsResponse/no_Of_ResolutionsResult/Streams/out_xml/Value/TABLE"));
		//errorAPIDetailsMap.put("SES Vote Matrix", new ErrorAPIDetails("PFM/SES_Vote_Matrix", "sES_Vote_MatrixResponse/sES_Vote_MatrixResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("SES Vote Matrix", new ErrorAPIDetails("PFM/SES_Vote_Matrix", "sES_Vote_MatrixResponse/sES_Vote_MatrixResult/Streams/out_xml/Value/TABLE"));
		
		
		errorAPIDetailsMap.put("Quarterly Vote Matrix Results", new ErrorAPIDetails("PFM/Vote_Matrix", "vote_MatrixResponse/vote_MatrixResult/quarterly_EvotingResult/Streams/out_xml/Value/TABLE"));
		/**
		 * Vote Report end
		 */
		
		/**
		 * Quarterly Evoting Summary start
		 */
		errorAPIDetailsMap.put("Quarterly Evoting", new ErrorAPIDetails("PFM/Quarterly_Evoting", "quarterly_EvotingResponse/quarterly_EvotingResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Quarterly Evoting Summary end
		 */
		
		/**
		 * Quarterly_Non_Unanimous start
		 */
		
		errorAPIDetailsMap.put("Quarterly_Non_Unanimous", new ErrorAPIDetails("PFM/Quarterly_Non_Unanimous", "quarterly_Non_UnanimousResponse/quarterly_Non_UnanimousResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Quarterly_Non_Unanimous end
		 */
		
		
		/**
		 * IMF Scheme start
		 */
		errorAPIDetailsMap.put("IMF Scheme C I", new ErrorAPIDetails("PFM/IMF_SCHEME_C_I", "iMF_SCHEME_C_IResponse/iMF_SCHEME_C_IResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("IMF Scheme C II", new ErrorAPIDetails("PFM/IMF_SCHEME_C_II", "iMF_SCHEME_C_IIResponse/iMF_SCHEME_C_IIResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("IMF Scheme Tax T II", new ErrorAPIDetails("PFM/IMF_SCHEME_TAX_T_II", "iMF_SCHEME_TAX_T_IIResponse/iMF_SCHEME_TAX_T_IIResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("IMF Scheme Summary", new ErrorAPIDetails("PFM/SUMMARY", "sUMMARYResponse/sUMMARYResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("MGMT RATE", new ErrorAPIDetails("PFM/MGMT_RATE", "mGMT_RATEResponse/mGMT_RATEResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("IMF Scheme Quarterly Report", new ErrorAPIDetails("PFM/QUARTERLY_REPORT", "qUARTERLY_REPORTResponse/qUARTERLY_REPORTResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("IMF Scheme E I", new ErrorAPIDetails("PFM/IMF_SCHEME_E_I", "iMF_SCHEME_E_IResponse/iMF_SCHEME_E_IResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("IMF Scheme E II", new ErrorAPIDetails("PFM/IMF_SCHEME_E_II", "iMF_SCHEME_E_IIResponse/iMF_SCHEME_E_IIResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("IMF Scheme G I", new ErrorAPIDetails("PFM/IMF_SCHEME_G_I", "iMF_SCHEME_G_IResponse/iMF_SCHEME_G_IResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("IMF Scheme G II", new ErrorAPIDetails("PFM/IMF_SCHEME_G_II", "iMF_SCHEME_G_IIResponse/iMF_SCHEME_G_IIResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("IMF Scheme A I", new ErrorAPIDetails("PFM/IMF_SCHEME_A_I", "iMF_SCHEME_A_IResponse/iMF_SCHEME_A_IResult/Streams/out_xml/Value/TABLE"));	
		/**
		 * IMF Scheme end
		 */
		
		/**
		 * Final Intimation_APY start
		 */
		errorAPIDetailsMap.put("APY", new ErrorAPIDetails("GRIEVANCE/APY", "aPYResponse/aPYResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("APY_Grievance", new ErrorAPIDetails("GRIEVANCE/APY_Grievance", "apy_GrievanceResponse/apy_GrievanceResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Final Intimation_APY end
		 */
		
		/**
		 * Final Intimation_NPS start
		 */
		errorAPIDetailsMap.put("PAO_Grievance", new ErrorAPIDetails("GRIEVANCE/PAO_Grievance", "pAO_GrievanceResponse/pAO_GrievanceResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Reg_Lite", new ErrorAPIDetails("GRIEVANCE/Reg_Lite", "reg_LiteResponse/reg_LiteResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Final Intimation_NPS end
		 */
		
		/**
		 * Month 2021- Griev pending start
		 */
		errorAPIDetailsMap.put("Summary", new ErrorAPIDetails("GRIEVANCE/Summary", "summaryResponse/summaryResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("NPS Nodal Office", new ErrorAPIDetails("GRIEVANCE/NPS_Nodal_Office", "nPS_Nodal_OfficeResponse/nPS_Nodal_OfficeResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("APY and NPS Lite", new ErrorAPIDetails("GRIEVANCE/APY_and_NPS_Lite_Copy", "aPY_and_NPS_Lite_CopyResponse/aPY_and_NPS_Lite_CopyResult/Streams/out_xml/Value/TABLE"));

		/**
		 * Month 2021- Griev pending end
		 */
		
		/**
		 * Status 45 days start
		 */
		errorAPIDetailsMap.put("Griev Pending for resolution", new ErrorAPIDetails("GRIEVANCE/Griev_Pending_for_resolution", "griev_Pending_for_resolutionResponse/griev_Pending_for_resolutionResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Pending Data_NPS", new ErrorAPIDetails("GRIEVANCE/Pending_Data_NPS", "pending_Data_NPSResponse/pending_Data_NPSResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Pending Data_APY", new ErrorAPIDetails("GRIEVANCE/Pending_Data_APY", "pending_Data_APYResponse/pending_Data_APYResult/Streams/out_xml/Value/TABLE"));

		/**
		 * Status 45 days end
		 */
		
		/**
		 * Top 5 data Monthly_NPS start
		 */
		errorAPIDetailsMap.put("Ministry wise pending", new ErrorAPIDetails("GRIEVANCE/Ministry_wise_pending", "ministry_wise_pendingResponse/ministry_wise_pendingResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("State wise pending", new ErrorAPIDetails("GRIEVANCE/State_wise_pending", "state_wise_pendingResponse/state_wise_pendingResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("POP wise pending", new ErrorAPIDetails("GRIEVANCE/POP_wise_pending", "pOP_wise_pendingResponse/pOP_wise_pendingResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("CHO wise pending", new ErrorAPIDetails("GRIEVANCE/CHO_wise_pending", "cHO_wise_pendingResponse/cHO_wise_pendingResult/Streams/out_xml/Value/TABLE"));
		
		//checked are applied on Liferay for Grievance details
		//errorAPIDetailsMap.put("Grievance Details", new ErrorAPIDetails("GRIEVANCE/Grievance_Details_copy", "grievance_Details_copyResponse/grievance_Details_copyResult/Streams/out_xml/Value/TABLE"));
		

		/**
		 * Top 5 data Monthly_NPS end
		 */
		
		/**
		 * Monthly MIS Report start
		 */
		errorAPIDetailsMap.put("Grievances_Outstanding", new ErrorAPIDetails("GRIEVANCE/Grievances_Outstanding", "grievances_OutstandingResponse/grievances_OutstandingResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Total_Grievance_Received_Resolv", new ErrorAPIDetails("GRIEVANCE/Total_Grievance_Received_Resolve", "total_Grievance_Received_ResolveResponse/total_Grievance_Received_ResolveResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("NPS_Scheme_Month_Analysis", new ErrorAPIDetails("GRIEVANCE/NPS_Scheme_Month_Analysis", "nPS_Scheme_Month_AnalysisResponse/nPS_Scheme_Month_AnalysisResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("NPSLite_Scheme_Month_Analysis", new ErrorAPIDetails("GRIEVANCE/NPSLite_Scheme_Month_Analysis", "nPSLite_Scheme_Month_AnalysisResponse/nPSLite_Scheme_Month_AnalysisResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Resolution Speed Analysis", new ErrorAPIDetails("GRIEVANCE/Resolution_Speed_Analysis", "resolution_Speed_AnalysisResponse/resolution_Speed_AnalysisResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Entity wise ageing Analysis", new ErrorAPIDetails("GRIEVANCE/Entity_wise_ageing_Analysis", "entity_wise_ageing_AnalysisResponse/entity_wise_ageing_AnalysisResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("NPS Entity wise ageing", new ErrorAPIDetails("GRIEVANCE/NPS_Entity_wise_ageing_copy", "nPS_Entity_wise_ageing_copyResponse/nPS_Entity_wise_ageing_copyResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("NPS Category wise ageing", new ErrorAPIDetails("GRIEVANCE/NPS_Category_wise_ageing_copy", "nPS_Category_wise_ageing_copyResponse/nPS_Category_wise_ageing_copyResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("NPSLite Entity wise ageing", new ErrorAPIDetails("GRIEVANCE/NPSLite_Entity_wise_ageing", "nPSLite_Entity_wise_ageingResponse/nPSLite_Entity_wise_ageingResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("NPSLite Category wise ageing", new ErrorAPIDetails("GRIEVANCE/NPSLite_Category_wise_ageing", "nPSLite_Category_wise_ageingResponse/nPSLite_Category_wise_ageingResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("EntityWise Outstanding Analysis", new ErrorAPIDetails("GRIEVANCE/EntityWise_Outstanding_Analysis", "entityWise_Outstanding_AnalysisResponse/entityWise_Outstanding_AnalysisResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("NPS Category wise grievance", new ErrorAPIDetails("GRIEVANCE/NPS_Category_wise_grievance", "nPS_Category_wise_grievanceResponse/nPS_Category_wise_grievanceResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("NPSLite Category wise grievance", new ErrorAPIDetails("GRIEVANCE/NPSLite_Category_wise_grievance", "nPSLite_Category_wise_grievanceResponse/nPSLite_Category_wise_grievanceResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Referral Handled at NPS", new ErrorAPIDetails("GRIEVANCE/Referral_Handled_at_NPS", "referral_Handled_at_NPSResponse/referral_Handled_at_NPSResult/Streams/out_xml/Value/TABLE"));

		/**
		 * Monthly MIS Report end
		 */
		
		/**
		 * Compliance report_NPST Trust start
		 */
		errorAPIDetailsMap.put("Status of Withdrawals reported", new ErrorAPIDetails("CRA/Status_of_Withdrawals_reported", "status_of_Withdrawals_reportedResponse/status_of_Withdrawals_reportedResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Reported Wthdrwl Aging Analysis", new ErrorAPIDetails("CRA/Reported_Wthdrwl_Aging_Analysis", "reported_Wthdrwl_Aging_AnalysisResponse/reported_Wthdrwl_Aging_AnalysisResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Special provision on Wthdrwl", new ErrorAPIDetails("CRA/Special_provision_on_Wthdrwl", "special_provision_on_WthdrwlResponse/special_provision_on_WthdrwlResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Subs Annuity Purchase", new ErrorAPIDetails("CRA/Subs_Annuity_Purchase", "subs_Annuity_PurchaseResponse/subs_Annuity_PurchaseResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Withdrawal Related Subs Griev", new ErrorAPIDetails("CRA/Withdrawal_Related_Subs_Griev", "withdrawal_Related_Subs_GrievResponse/withdrawal_Related_Subs_GrievResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Griev Wthdrwl Aging Analysis",  new ErrorAPIDetails("CRA/Griev_Wthdrwl_Aging_Analysis", "griev_Wthdrwl_Aging_AnalysisResponse/griev_Wthdrwl_Aging_AnalysisResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Compliance report_NPST Trust end
		 */
		
		/**
		 * CRA Quarter Data start
		 */
		errorAPIDetailsMap.put("Exit and Withdrawal", new ErrorAPIDetails("CRA/Exit_and_Withdrawal", "exit_and_WithdrawalResponse/exit_and_WithdrawalResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Sectoral Analysis", new ErrorAPIDetails("CRA/Sectoral_Analysis", "sectoral_AnalysisResponse/sectoral_AnalysisResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Cause-wise Pattern Analysis", new ErrorAPIDetails("CRA/Cause_wise_Pattern_Analysis", "cause_wise_Pattern_AnalysisResponse/cause_wise_Pattern_AnalysisResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Sector-wise Ageing analysis", new ErrorAPIDetails("CRA/Sector_wise_Ageing_analysis", "sector_wise_Ageing_analysisResponse/sector_wise_Ageing_analysisResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Cause-wise Ageing analysis", new ErrorAPIDetails("CRA/Cause_wise_Ageing_analysis", "cause_wise_Ageing_analysisResponse/cause_wise_Ageing_analysisResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Annuity Request", new ErrorAPIDetails("CRA/Annuity_Request", "annuity_RequestResponse/annuity_RequestResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Annuity request ageing", new ErrorAPIDetails("CRA/Annuity_request_ageing", "annuity_request_ageingResponse/annuity_request_ageingResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Exercised Exit Option", new ErrorAPIDetails("CRA/Exercised_Exit_Option", "exercised_Exit_OptionResponse/exercised_Exit_OptionResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Gap Analysis", new ErrorAPIDetails("CRA/Gap_Analysis", "gap_AnalysisResponse/gap_AnalysisResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * CRA Quarter Data end
		 */
		
		/**
		 * Annexure I - PFM AUC Details start
		 */
		errorAPIDetailsMap.put("AUC SUMMARY", new ErrorAPIDetails("Custodian/AUC_SUMMARY", "aUC_SUMMARYResponse/aUC_SUMMARYResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Asset Not Under Custody", new ErrorAPIDetails("Custodian/Asset_Not_Under_Custody", "asset_Not_Under_CustodyResponse/asset_Not_Under_CustodyResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Collateral Details", new ErrorAPIDetails("Custodian/COLL", "cOLLResponse/cOLLResult/Streams/out_xml/Value/TABLE"));
		/**
		 * Annexure I - PFM AUC Details end
		 */
		
		/**
		 * Annexure-I-AUC-Details start
		 */
		errorAPIDetailsMap.put("NPS FINAL SUMMARY", new ErrorAPIDetails("Custodian/NPS_FINAL_SUMMARY", "nPS_FINAL_SUMMARYResponse/nPS_FINAL_SUMMARYResult/Streams/out_xml/Value/TABLE"));

		/**
		 * Annexure-I-AUC-Details end
		 */
		
		/**
		 * Annexure IV - PFM start
		 */
	//	errorAPIDetailsMap.put("Custodian Charges by PFM", new ErrorAPIDetails("Custodian/HDFC", "hDFCResponse/hDFCResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Custodian Charges by PFM", new ErrorAPIDetails("Custodian/Anx4_PFM", "anx4_PFMResponse/anx4_PFMResult/Streams/out_xml/Value/TABLE"));

		/**
		 * Annexure IV - PFM end
		 */
		
		/**
		 * Annexure-IV-Custodian start
		 */
		/*errorAPIDetailsMap.put("HDFC", new ErrorAPIDetails("Custodian/HDFC", "hDFCResponse/hDFCResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("ICICI", new ErrorAPIDetails("Custodian/HDFC", "hDFCResponse/hDFCResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Kotak", new ErrorAPIDetails("Custodian/HDFC", "hDFCResponse/hDFCResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("SBI", new ErrorAPIDetails("Custodian/HDFC", "hDFCResponse/hDFCResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("UTI", new ErrorAPIDetails("Custodian/HDFC", "hDFCResponse/hDFCResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("LIC 1", new ErrorAPIDetails("Custodian/HDFC", "hDFCResponse/hDFCResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Birla1", new ErrorAPIDetails("Custodian/HDFC", "hDFCResponse/hDFCResult/Streams/out_xml/Value/TABLE"));*/
		errorAPIDetailsMap.put("Custodian Charges", new ErrorAPIDetails("Custodian/HDFC", "hDFCResponse/hDFCResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Annexure-IV-Custodian end
		 */
		
		/**
		 * Annual report on proxy voting start
		 */
		errorAPIDetailsMap.put("Summary Annual Proxy Voting", new ErrorAPIDetails("PFM/Summary_Annual_Proxy_Voting", "summary_Annual_Proxy_VotingResponse/summary_Annual_Proxy_VotingResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Consensus vote summary", new ErrorAPIDetails("PFM/Consensus_vote_summary", "consensus_vote_summaryResponse/consensus_vote_summaryResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Annual Proxy Voting Report", new ErrorAPIDetails("PFM/Annual_Proxy_Voting_Report_copy", "annual_Proxy_Voting_Report_copyResponse/annual_Proxy_Voting_Report_copyResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Annual report on proxy voting end
		 */
		
		/**
		 * Annual voting Count start
		 */
		//errorAPIDetailsMap.put("Final Annual Vote Count", new ErrorAPIDetails("PFM/Final_Annual_Vote_Count", "final_Annual_Vote_CountResponse/final_Annual_Vote_CountResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Ann Final Majority Vote Count", new ErrorAPIDetails("PFM/Final_Annual_Vote_Count", "final_Annual_Vote_CountResponse/final_Annual_Vote_CountResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Annual PF Voting Recomm", new ErrorAPIDetails("PFM/Annual_PF_Voting_Recomm", "annual_PF_Voting_RecommResponse/annual_PF_Voting_RecommResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Annual voting Count end
		 */
		
		/**
		 * Quarterly voting Count start
		 */
		//errorAPIDetailsMap.put("Final Vote Count", new ErrorAPIDetails("PFM/Final_Vote_Count", "final_Vote_CountResponse/final_Vote_CountResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Final Majority Vote Count", new ErrorAPIDetails("PFM/Final_Vote_Count", "final_Vote_CountResponse/final_Vote_CountResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("PF Voting Recomm Count", new ErrorAPIDetails("PFM/PF_Voting_Recomm_Count", "pF_Voting_Recomm_CountResponse/pF_Voting_Recomm_CountResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Quarterly voting Count end
		 */
		
		/**
		 * website_data start
		 */
		errorAPIDetailsMap.put("website_data", new ErrorAPIDetails("PFM/website", "websiteResponse/websiteResult/Streams/out_xml/Value/TABLE"));

		/**
		 * website_data end
		 */
		
		/**
		 * NPST TRUST FEE PFM start
		 */
		errorAPIDetailsMap.put("NPST_TRUST_FEE", new ErrorAPIDetails("PFM/NTF", "nTFResponse/nTFResult/Streams/out_xml/Value/TABLE"));

		/**
		 * NPST TRUST FEE PFM end
		 */
		
		/**
		 * Monthly PFM TDS Report start
		 */
		errorAPIDetailsMap.put("Challan Detail", new ErrorAPIDetails("PFM/PTC", "pTCResponse/pTCResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Deductee Detail", new ErrorAPIDetails("PFM/PDD", "pDDResponse/pDDResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Monthly PFM TDS Report end
		 */
		
		/**
		 * QUATERLY COMPLIANCE FORM start
		 */
		/*errorAPIDetailsMap.put("FORM_1A_1B", new ErrorAPIDetails("PFM/FORM_1A_1B", "fORM_1A_1BResponse/fORM_1A_1BResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("FORM 2 -Scheme -TAX-T2", new ErrorAPIDetails("PFM/FORM2", "fORM2Response/fORM2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 2 - Scheme - A - Tier- I", new ErrorAPIDetails("PFM/FORM2", "fORM2Response/fORM2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 2 - Scheme - E - Tier - I", new ErrorAPIDetails("PFM/FORM2", "fORM2Response/fORM2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 2 - Scheme - E - Tier - II", new ErrorAPIDetails("PFM/FORM2", "fORM2Response/fORM2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 2 - Scheme - C - Tier - I", new ErrorAPIDetails("PFM/FORM2", "fORM2Response/fORM2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 2 - Scheme - C - Tier - II", new ErrorAPIDetails("PFM/FORM2", "fORM2Response/fORM2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 2 - Scheme - G - Tier - I", new ErrorAPIDetails("PFM/FORM2", "fORM2Response/fORM2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 2 - Scheme - G - Tier - II", new ErrorAPIDetails("PFM/FORM2", "fORM2Response/fORM2Result/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("FORM 3 Scheme - A - Tier -  I", new ErrorAPIDetails("PFM/FORM3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme -TAX-T2", new ErrorAPIDetails("PFM/FORM3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - E - Tier - I", new ErrorAPIDetails("PFM/FORM3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - E - Tier - II", new ErrorAPIDetails("PFM/FORM3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - C - Tier - I", new ErrorAPIDetails("PFM/FORM3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - C - Tier - II", new ErrorAPIDetails("PFM/FORM3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - G - Tier - I", new ErrorAPIDetails("PFM/FORM3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - G - Tier - II", new ErrorAPIDetails("PFM/FORM3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("FORM 4", new ErrorAPIDetails("PFM/FORM4", "fORM4Response/fORM4Result/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("FORM 5", new ErrorAPIDetails("PFM/FORM5", "fORM5Response/fORM5Result/Streams/out_xml/Value/TABLE"));
		*/
		/**
		 * QUATERLY COMPLIANCE FORM end
		 */
		
		/**
		 * QUATERLY COMPLIANCE FORM start
		 */
		/*errorAPIDetailsMap.put("FORM_1A_1B", new ErrorAPIDetails("PFM/FORM_1A_1B", "fORM_1A_1BResponse/fORM_1A_1BResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("FORM2", new ErrorAPIDetails("PFM/FORM2", "fORM2Response/fORM2Result/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("FORM3", new ErrorAPIDetails("PFM/FORM3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("FORM4", new ErrorAPIDetails("PFM/FORM4", "fORM4Response/fORM4Result/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("FORM5", new ErrorAPIDetails("PFM/FORM5", "fORM5Response/fORM5Result/Streams/out_xml/Value/TABLE"));
		*/
		/**
		 * QUATERLY COMPLIANCE FORM end
		 */
		
		/**
		 * GROWTH DATA start
		 */
		//errorAPIDetailsMap.put("AUM", new ErrorAPIDetails("PFM/AUM", "aUMResponse/aUMResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("PFM AUM Growth", new ErrorAPIDetails("PFM/PFMGROWTH", "pFMGROWTHResponse/pFMGROWTHResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Schemewise Growth", new ErrorAPIDetails("PFM/SCHEMEGROWTH", "sCHEMEGROWTHResponse/sCHEMEGROWTHResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Sector wise Scheme Growth", new ErrorAPIDetails("PFM/SECTORGROWTH", "sECTORGROWTHResponse/sECTORGROWTHResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("PFM wise Scheme Growth", new ErrorAPIDetails("PFM/PFMSCHEMEGROWTH", "pFMSCHEMEGROWTHResponse/pFMSCHEMEGROWTHResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * GROWTH DATA end
		 * 
		 */
		
		/**
		 * mn_consensus_voting start
		 */
		errorAPIDetailsMap.put("mn_consensus_voting", new ErrorAPIDetails("PFM/mn_consensus_voting", "mn_consensus_votingResponse/mn_consensus_votingResult/Streams/out_xml/Value/TABLE"));

		/**
		 * mn_consensus_voting end
		 */
		
		

		/**
		 * weekly_valuation_rep_tb end
		 */
		/**
		 * Category Wise Ageing start
		 */
		errorAPIDetailsMap.put("Escalated to NPS Trust", new ErrorAPIDetails("Grievance/Escalated_to_NPS_Trust", "escalated_to_NPS_TrustResponse/escalated_to_NPS_TrustResult/Streams/out_xml/Value/TABLE"));

		
		errorAPIDetailsMap.put("Raised against NPS Trust", new ErrorAPIDetails("Grievance/Raised_Against_NPS_Trust", "raised_Against_NPS_TrustResponse/raised_Against_NPS_TrustResult/Streams/out_xml/Value/TABLE"));

		/**
		 *Category Wise Ageing end
		 */
		
		
		
		/**
		 * Weekly All Sector Report start
		 */
		errorAPIDetailsMap.put("Status of Atal Pension Scheme", new ErrorAPIDetails("PFM/SOAPS", "sOAPSResponse/sOAPSResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Overall Status of State Govt", new ErrorAPIDetails("PFM/OSOSG", "oSOSGResponse/oSOSGResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Overall Status Sector wise", new ErrorAPIDetails("PFM/OSSW", "oSSWResponse/oSSWResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Status of Individual Subscriber", new ErrorAPIDetails("PFM/SOIS", "sOISResponse/sOISResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Status of Central Government", new ErrorAPIDetails("PFM/SOCG", "sOCGResponse/sOCGResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Status of Corporate sector", new ErrorAPIDetails("PFM/SOCS", "sOCSResponse/sOCSResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Status of NPS Lite", new ErrorAPIDetails("PFM/SONL", "sONLResponse/sONLResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Weekly All Sector Report end
		 * 
		 */
		
		
		/**
		 * Yearly- Consolidated Financial and key stats_STD start
		 */
		errorAPIDetailsMap.put("Balance Sheet", new ErrorAPIDetails("PFM/BALINCST", "bALINCSTResponse/bALINCSTResult/Streams/out_xml/Value/TABLE"));
		//errorAPIDetailsMap.put("Income Statement", new ErrorAPIDetails("PFM/BALINCST", "bALINCSTResponse/bALINCSTResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Income Statement", new ErrorAPIDetails("PFM/REVENUE", "rEVENUEResponse/rEVENUEResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Key Statistics", new ErrorAPIDetails("PFM/NOTEKEY", "nOTEKEYResponse/nOTEKEYResult/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("Consolidated Balance Sheet", new ErrorAPIDetails("PFM/cIncStat", "cIncStatResponse/cIncStatResult/Streams/out_xml/Value/TABLE"));
		//errorAPIDetailsMap.put("Notes of Combined Balance Sheet", new ErrorAPIDetails("PFM/cIncStat", "cIncStatResponse/cIncStatResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Notes of Combined Balance Sheet", new ErrorAPIDetails("PFM/NOTES", "nOTESResponse/nOTESResult/Streams/out_xml/Value/TABLE"));
		//errorAPIDetailsMap.put("Combined Income Statement", new ErrorAPIDetails("PFM/cIncStat", "cIncStatResponse/cIncStatResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Combined Income Statement", new ErrorAPIDetails("PFM/REVACC", "rEVACCResponse/rEVACCResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Note of Balance Sheet", new ErrorAPIDetails("PFM/Balsheet", "balsheetResponse/balsheetResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Yearly- Consolidated Financial and key stats_STD end
		 * 
		 */
		/**
		 * Quarterly Compliance Forms
		 */
		
		
		errorAPIDetailsMap.put("Form_2", new ErrorAPIDetails("PFM/FORM1B", "fORM1BResponse/fORM1BResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 3_ Other_schemes", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));

		//errorAPIDetailsMap.put("FORM_3 -Scheme -TAX-T2", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme - A - Tier- I", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme - E - Tier - I", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme - E - Tier - II", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme - C - Tier - I", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme - C - Tier - II", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme - G - Tier - I", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme - G - Tier - II", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme - CG", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme - SG", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme -Corp- CG", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme -APY", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM_3 - Scheme - NPS Lite", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
		
		
		
//		errorAPIDetailsMap.put("Form 1B", new ErrorAPIDetails("PFM/FORM1B", "fORM1BResponse/fORM1BResult/Streams/out_xml/Value/TABLE"));
//
//		errorAPIDetailsMap.put("FORM 2 -Scheme -TAX-T2", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme - A - Tier- I", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme - E - Tier - I", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme - E - Tier - II", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme - C - Tier - I", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme - C - Tier - II", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme - G - Tier - I", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme - G - Tier - II", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme - CG", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme - SG", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme -Corp- CG", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme -APY", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));
//		errorAPIDetailsMap.put("FORM 2 - Scheme - NPS Lite", new ErrorAPIDetails("PFM/qtr_form2", "qtr_form2Response/qtr_form2Result/Streams/out_xml/Value/TABLE"));

		errorAPIDetailsMap.put("FORM 3 Scheme -TAX-T2", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - A - Tier -  I", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - E - Tier - I", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - E - Tier - II", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - C - Tier - I", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - C - Tier - II", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - G - Tier - I", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - G - Tier - II", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - CG", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - SG", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - Corp CG", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - APY", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("FORM 3 Scheme - NPS Lite", new ErrorAPIDetails("PFM/form3", "fORM3Response/fORM3Result/Streams/out_xml/Value/TABLE"));

		
		
		errorAPIDetailsMap.put("Form 4", new ErrorAPIDetails("PFM/form4", "fORM4Response/fORM4Result/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Form 5", new ErrorAPIDetails("PFM/form5", "form5Response/form5Result/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Cashflow", new ErrorAPIDetails("PFM/Cashflow", "cashflowResponse/cashflowResult/Streams/out_xml/Value/TABLE"));
		
		
		errorAPIDetailsMap.put("Form_1", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Form 1A", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 1A_2", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 1A_3", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 1A_4", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 1A_5", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 1A_6", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 1A_7", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 1A_8", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 1A_9", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Form 1A_10", new ErrorAPIDetails("PFM/FORM1A", "fORM1AResponse/fORM1AResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Quarterly Compliance Forms
		 */
		
		/**
		 * Benchmark return
		 */
		errorAPIDetailsMap.put("Benchmark Return", new ErrorAPIDetails("PFM/weekly_benchmark_return", "weekly_benchmark_returnResponse/weekly_benchmark_returnResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Benchmark Return Date", new ErrorAPIDetails("PFM/weekly_benchmark_return_date", "weekly_benchmark_return_dateResponse/weekly_benchmark_return_dateResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * Benchmark return
		 */
		
		/**
		 * Invest Management Fee
		 */
		errorAPIDetailsMap.put("Fee Summary", new ErrorAPIDetails("PFM/SUMMARY", "sUMMARYResponse/sUMMARYResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Management_Fee", new ErrorAPIDetails("PFM/IMF_SCHEME_A_I", "iMF_SCHEME_A_IResponse/iMF_SCHEME_A_IResult/Streams/out_xml/Value/TABLE"));
		/**
		 * Invest Management Fee
		 */
		
		/**
		 * NPST FEE
		 */
		
		errorAPIDetailsMap.put("nps_trust_fee", new ErrorAPIDetails("PFM/NTF", "nTFResponse/nTFResult/Streams/out_xml/Value/TABLE"));
		/**
		 * NPST FEE
		 */
		
		/**
		 * Monthly ASP
		 */
		errorAPIDetailsMap.put("ASP Report", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Edelweiss Tokio", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Bajaj Allianz", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Canara HSBC", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("HDFC Life", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("ICICI Prudential", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Indian Life", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Kotak Life", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("LIC OF India", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Max Life", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("SBI Life", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("Star Union", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("TATA AIA", new ErrorAPIDetails("CRA/Monthly_ASP", "monthly_ASPResponse/monthly_ASPResult/Streams/out_xml/Value/TABLE"));
		/**
		 * Monthly ASP
		 */
		/**
		 * 
		 */
		errorAPIDetailsMap.put("Investment During the Period", new ErrorAPIDetails("PFM/QTRFORM3_Dir_KP", "qTRFORM3_Dir_KPResponse/qTRFORM3_Dir_KPResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("Self Dealing or Front Running", new ErrorAPIDetails("PFM/QTRFORM3_Dir_KP", "qTRFORM3_Dir_KPResponse/qTRFORM3_Dir_KPResult/Streams/out_xml/Value/TABLE"));
		
		/**
		 * weekly_valuation_rep_tb start
		 */
		errorAPIDetailsMap.put("weekly_valuation_rep_tb", new ErrorAPIDetails("PFM/weekly_valuation_rep_tb", "weekly_valuation_rep_tbResponse/weekly_valuation_rep_tbResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("valuation_sg", new ErrorAPIDetails("PFM/weekly_valuation_rep_tb", "weekly_valuation_rep_tbResponse/weekly_valuation_rep_tbResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("weekly_valuation_rep_cb", new ErrorAPIDetails("PFM/weekly_valuation_rep_tb", "weekly_valuation_rep_tbResponse/weekly_valuation_rep_tbResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("valuation_cg", new ErrorAPIDetails("PFM/weekly_valuation_rep_tb", "weekly_valuation_rep_tbResponse/weekly_valuation_rep_tbResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("NONEQ", new ErrorAPIDetails("PFM/weekly_valuation_noneq", "weekly_valuation_noneqResponse/weekly_valuation_noneqResult/Streams/out_xml/Value/TABLE"));
		
		//errorAPIDetailsMap.put("repo_eq", new ErrorAPIDetails("PFM/weekly_valuation_eq", "qTRFORM3_Dir_KPResponse/qTRFORM3_Dir_KPResult/Streams/out_xml/Value/TABLE"));
		
		errorAPIDetailsMap.put("repo_eq", new ErrorAPIDetails("PFM/weekly_valuation_eq", "weekly_valuation_eqResponse/weekly_valuation_eqResult/Streams/out_xml/Value/TABLE"));
		errorAPIDetailsMap.put("repo_debt", new ErrorAPIDetails("PFM/weekly_valuation_eq", "weekly_valuation_eqResponse/weekly_valuation_eqResult/Streams/out_xml/Value/TABLE"));
		
			
		/**
		 * 
		 */
		
		
		
	}
	
	/*
	 * public static Map<String, ErrorAPIDetails> getErrorAPIDetailsMap() {
	 * 
	 * }
	 */
}
