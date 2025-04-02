package com.nps.manpower.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.nps.manpower.constants.ManpowerStatusConstant;
import com.nps.manpower.model.ManpowerEmployee;
import com.nps.manpower.service.ManpowerEmployeeLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
@Component(immediate = true, service = ManpowerUtil.class)
public class ManpowerUtil {
	
	/**
	 * 
	 * @param status
	 * @return
	 */
//	public static List<ManpowerEmployee> getInvestmentCommitteEmployee(int status){
//		
//		List<ManpowerEmployee> manpowerEmployees=new ArrayList<ManpowerEmployee>();
//		
//		List<ManpowerEmployee> bothCommitteeEmployees =ManpowerEmployeeLocalServiceUtil.getAllByStatusAndCommitteeMembershipType(status, Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_BOTH));
//		List<ManpowerEmployee> investCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.getAllByStatusAndCommitteeMembershipType(status, Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_INVESTMENT));
//		if(!bothCommitteeEmployees.isEmpty() && bothCommitteeEmployees!=null) {
//			manpowerEmployees.addAll(bothCommitteeEmployees);
//		}
//		if(!investCommitteeEmployees.isEmpty() && investCommitteeEmployees!=null) {
//			manpowerEmployees.addAll(investCommitteeEmployees);
//		}
//		return manpowerEmployees;
//	}
	
	/**
	 * 
	 * @param status
	 * @param committeeMembershipType
	 * @return
	 */
//	public static List<ManpowerEmployee> getFilterInvestmentCommitteeEmployee(int status,String committeeMembershipType) {
//		List<ManpowerEmployee> manpowerEmployees=new ArrayList<ManpowerEmployee>();
//		if(status==Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE)) {
//			List<ManpowerEmployee> bothCommitteeEmployees =ManpowerEmployeeLocalServiceUtil.getAllByCommitteeMembershipType(Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_BOTH));
//			List<ManpowerEmployee> riskCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.getAllByCommitteeMembershipType(Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_RISK));
//			if(!bothCommitteeEmployees.isEmpty() && bothCommitteeEmployees!=null) {
//				manpowerEmployees.addAll(bothCommitteeEmployees);
//			}
//			if(!riskCommitteeEmployees.isEmpty() && riskCommitteeEmployees!=null) {
//				manpowerEmployees.addAll(riskCommitteeEmployees);
//			}
//		}else {
//			List<ManpowerEmployee> bothCommitteeEmployees =ManpowerEmployeeLocalServiceUtil.getAllByStatusAndCommitteeMembershipType(status, Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_BOTH));
//			List<ManpowerEmployee> riskCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.getAllByStatusAndCommitteeMembershipType(status, Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_RISK));
//			if(!bothCommitteeEmployees.isEmpty() && bothCommitteeEmployees!=null) {
//				manpowerEmployees.addAll(bothCommitteeEmployees);
//			}
//			if(!riskCommitteeEmployees.isEmpty() && riskCommitteeEmployees!=null) {
//				manpowerEmployees.addAll(riskCommitteeEmployees);
//			}
//		}
//		return manpowerEmployees;
//	}
	
	public static List<ManpowerEmployee> getCommitteEmployee(int status,String pfmType,int committeeType){
		List<ManpowerEmployee> manpowerEmployees=new ArrayList<ManpowerEmployee>();
		List<ManpowerEmployee> bothCommitteeEmployees =null;
		List<ManpowerEmployee> indivisualCommitteeEmployees=null;
		if(status==Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE) && pfmType.equalsIgnoreCase(ManpowerStatusConstant.ALL_PFM)) {
			logger.info("status : "+status + "  pfm type   : "+pfmType +"    committe type   "+committeeType);
			indivisualCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.getAllByCommitteeMembershipType(committeeType);
			bothCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.getAllByCommitteeMembershipType(Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_BOTH));
		}else if(status!=Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE) && pfmType.equalsIgnoreCase(ManpowerStatusConstant.ALL_PFM)) {
			logger.info("status : "+status + "  pfm type   : "+pfmType +"    committe type   "+committeeType);
			indivisualCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.getAllByStatusAndCommitteeMembershipType(status,committeeType);
			bothCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.getAllByStatusAndCommitteeMembershipType(status,Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_BOTH));
		}else if(status==Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE) && !pfmType.equalsIgnoreCase(ManpowerStatusConstant.ALL_PFM)) {
			logger.info("status : "+status + "  pfm type   : "+pfmType +"    committe type   "+committeeType);
			indivisualCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.findByPfmAndCommitteeMembershipType(committeeType, pfmType);
			bothCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.findByPfmAndCommitteeMembershipType(Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_BOTH),pfmType);
		}else {
			logger.info("status : "+status + "  pfm type   : "+pfmType +"    committe type   "+committeeType);
			indivisualCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.findByPfmAndCommitteeMembershipTypeAndStatus(committeeType, pfmType, status);
			bothCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.findByPfmAndCommitteeMembershipTypeAndStatus(Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_BOTH), pfmType, status);
		}
		
		if(!bothCommitteeEmployees.isEmpty() && bothCommitteeEmployees!=null) {
			manpowerEmployees.addAll(bothCommitteeEmployees);
		}
		if(!indivisualCommitteeEmployees.isEmpty() && indivisualCommitteeEmployees!=null) {
			manpowerEmployees.addAll(indivisualCommitteeEmployees);
		}
		return manpowerEmployees;
		
	}
	
	
	/**
	 * 
	 * @param status
	 * @return
	 */
	public static List<ManpowerEmployee> getRiskManagementCommitteEmployee(int status,String pfmType){
		List<ManpowerEmployee> manpowerEmployees=new ArrayList<ManpowerEmployee>();
		
		List<ManpowerEmployee> bothCommitteeEmployees =ManpowerEmployeeLocalServiceUtil.getAllByStatusAndCommitteeMembershipType(status, Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_BOTH));
		List<ManpowerEmployee> riskCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.getAllByStatusAndCommitteeMembershipType(status, Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_RISK));
		
		if(status==Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE) && pfmType.equalsIgnoreCase(ManpowerStatusConstant.ALL_PFM)) {
			
		}else if(status!=Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE) && pfmType.equalsIgnoreCase(ManpowerStatusConstant.ALL_PFM)) {
			
		}else if(status==Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE) && !pfmType.equalsIgnoreCase(ManpowerStatusConstant.ALL_PFM)) {
			
		}else {
			
		}
		
		if(!bothCommitteeEmployees.isEmpty() && bothCommitteeEmployees!=null) {
			manpowerEmployees.addAll(bothCommitteeEmployees);
		}
		if(!riskCommitteeEmployees.isEmpty() && riskCommitteeEmployees!=null) {
			manpowerEmployees.addAll(riskCommitteeEmployees);
		}
		return manpowerEmployees;
	}
	
	/**
	 * 
	 * @param status
	 * @param isDirector
	 * @return
	 */
	public static List<ManpowerEmployee> getFilterEmployee(int status,int isDirector,String pfmName) {
		logger.info("filterType: "+status+"    isDirector:  "+isDirector+"   pfmName:  "+pfmName);
		if(status==Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE) && pfmName.equalsIgnoreCase(ManpowerStatusConstant.ALL_PFM)) {
			return ManpowerEmployeeLocalServiceUtil.getAllByIsDirector(isDirector);
		}else if(status==Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE) && !pfmName.equalsIgnoreCase(ManpowerStatusConstant.ALL_PFM)) {
			return ManpowerEmployeeLocalServiceUtil.findByIsDirectorAndPfmName(isDirector, pfmName);
		}else if(status!=Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE) && pfmName.equalsIgnoreCase(ManpowerStatusConstant.ALL_PFM)) {
			return ManpowerEmployeeLocalServiceUtil.getAllByIsDirectorAndStatus(isDirector, status);
		}else {
			return ManpowerEmployeeLocalServiceUtil.findByIsDirectorAndPfmNameAndStatus(isDirector, pfmName, status);
		}
	}
	
	/**
	 * 
	 * @param status
	 * @param committeeMembershipType
	 * @return
	 */
	public static List<ManpowerEmployee> getFilterRiskMangementCommitteeEmployee(int status,String committeeMembershipType) {
		List<ManpowerEmployee> manpowerEmployees=new ArrayList<ManpowerEmployee>();
		if(status==Integer.parseInt(ManpowerStatusConstant.ALL_EMPLOYEE)) {
			List<ManpowerEmployee> bothCommitteeEmployees =ManpowerEmployeeLocalServiceUtil.getAllByCommitteeMembershipType(Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_BOTH));
			List<ManpowerEmployee> riskCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.getAllByCommitteeMembershipType(Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_RISK));
			if(!bothCommitteeEmployees.isEmpty() && bothCommitteeEmployees!=null) {
				manpowerEmployees.addAll(bothCommitteeEmployees);
			}
			if(!riskCommitteeEmployees.isEmpty() && riskCommitteeEmployees!=null) {
				manpowerEmployees.addAll(riskCommitteeEmployees);
			}
		}else {
			List<ManpowerEmployee> bothCommitteeEmployees =ManpowerEmployeeLocalServiceUtil.getAllByStatusAndCommitteeMembershipType(status, Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_BOTH));
			List<ManpowerEmployee> riskCommitteeEmployees=ManpowerEmployeeLocalServiceUtil.getAllByStatusAndCommitteeMembershipType(status, Integer.parseInt(ManpowerStatusConstant.COMMITTEE_MEMBERSHIP_TYPE_RISK));
			if(!bothCommitteeEmployees.isEmpty() && bothCommitteeEmployees!=null) {
				manpowerEmployees.addAll(bothCommitteeEmployees);
			}
			if(!riskCommitteeEmployees.isEmpty() && riskCommitteeEmployees!=null) {
				manpowerEmployees.addAll(riskCommitteeEmployees);
			}
		}
		
		return manpowerEmployees;
	}
	
	/**
	 * 
	 * @param experience
	 * @return
	 */
	public static String experienceFormat(String experience){
		String exp="";
		try {
			String ex[]=experience.split(StringPool.COLON);
			exp=ex[0]+" Year ";
			exp=exp+ex[1]+" Month";
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return exp;
	}
	
	private static Log logger = LogFactoryUtil.getLog(ManpowerUtil.class);
}
 