package com.proxyvotingdashboard.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import compliance.service.model.VoteMatrix;
import compliance.service.service.VoteMatrixLocalServiceUtil;

public class SesVoteMatrix {
	private static Log _log = LogFactoryUtil.getLog(SesVoteMatrix.class);
	
	public static void saveSesVoteMatrix(File file, XSSFWorkbook workbook, long userId, JSONArray errorArray, XSSFSheet xx, boolean isexcelhaserror, JSONArray SesVoteMatrixJsonArray, List<VoteMatrix> SesVoteMatrixList, DecimalFormat decimalFormat,long reportUploadLogId) {
		_log.info("Inside SesVoteMatrix util class");

		VoteMatrixLocalServiceUtil.deleteVoteMatrixByReportUploadLogId(reportUploadLogId);
		_log.info("b4 try block");
		try {
			_log.info("inside try block");
			if (file != null) {
				_log.info("Inside file != null");
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheetAt(0);
			
				Iterator<Row> rows = sheet.rowIterator();
				int rowCount = 1;
				int errorRowCount = 2;
				
				rowloop:
				while (rows.hasNext()) {
					VoteMatrix voteMatrix = (VoteMatrix) VoteMatrixLocalServiceUtil.
							createVoteMatrix(CounterLocalServiceUtil.increment(VoteMatrix.class.getName()));
					
					voteMatrix.setReportUploadLogId(reportUploadLogId);
					voteMatrix.setCreatedby(userId);
					
					JSONObject errorObj = JSONFactoryUtil.createJSONObject();
					errorObj.put("rownum", rowCount);
					boolean isError = false;
					XSSFRow row = (XSSFRow) rows.next();
					XSSFRow errorRow = null;
					
					int lastColumn = Math.max(row.getLastCellNum(), 0);
					
					for (int i = 0; i < lastColumn; i++) {
						XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						DataFormatter formatter = new DataFormatter();
						
						String val = formatter.formatCellValue(cell);
						if (cell != null) {

							if (val != null)
								val = val.trim();
							if(rowCount > 1) {
								_log.info("Val: " + val);
								if (i == 0) {
									if (val != null && Validator.isNotNull(val) && val.length() > 0) {
										Date date_1 = new SimpleDateFormat("dd-MM-yyyy").parse(val);
										voteMatrix.setMeeting_date(date_1);
									}
									 else {
											errorObj.put("cellno", 2);
											errorObj.put("msg", "It is not a number");
											
											isError = true;
										}
								}
								else if (i == 1) {
									voteMatrix.setIsin(val);
								}
								else if (i == 2) {
									voteMatrix.setCompany_name(val);
								} 
								else if (i == 3) {
									voteMatrix.setMeeting_type(val);
								} 
								else if (i == 4) {
									voteMatrix.setResolution_category(val);
								} 
								else if (i == 5) {
									voteMatrix.setResol_dtls(val);
								}
								else if (i == 6) {
									voteMatrix.setResol_type(val);
								}
								else if (i == 7) {
									voteMatrix.setLic_vote(val);
								}
								else if (i == 8) {
									voteMatrix.setLic_rationale_vote_recomm(val);
								} 
								else if (i == 9) {
									try {
										long lic_shares = Long.parseLong(val);
										voteMatrix.setLic_shares_held(lic_shares);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 10) {
									voteMatrix.setUti_vote(val);
								} 
								else if (i == 11) {
									voteMatrix.setUti_rationale_vote_recomm(val);
								} 
								else if (i == 12) {
									try {
										long uti_shares = Long.parseLong(val);
										voteMatrix.setUti_shares_held(uti_shares);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 13) {
									voteMatrix.setSbi_vote(val);
								} 
								else if (i == 14) {
									voteMatrix.setSbi_rationale_vote_recomm(val);
								} 
								else if (i == 15) {
									try {
										long sbi_shares = Long.parseLong(val);
										voteMatrix.setSbi_shares_held(sbi_shares);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 16) {
									voteMatrix.setHdfc_vote(val);
								} 
								else if (i == 17) {
									voteMatrix.setHdfc_rationale_vote_recomm(val);
								} 
								else if (i == 18) {
									try {
										long hdfc_shares = Long.parseLong(val);
										voteMatrix.setHdfc_shares_held(hdfc_shares);
									} catch (Exception e) {
										 _log.error(e);
									}
								} 
								else if (i == 19) {
									voteMatrix.setIcici_vote(val);
								} 
								else if (i == 20) {
									voteMatrix.setIcici_rationale_vote_recomm(val);
								}
								else if (i == 21) {
									try {
										long icici_shares = Long.parseLong(val);
										voteMatrix.setIcici_shares_held(icici_shares);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 22) {
									voteMatrix.setKotak_vote(val);
								}
								else if (i == 23) {
									voteMatrix.setKotak_rationale_vote_recomm(val);
								}
								else if (i == 24) {
									try {
										long kotak_shares = Long.parseLong(val);
										voteMatrix.setKotak_shares_held(kotak_shares);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 25) {
									voteMatrix.setBirla_vote(val);
								}
								else if (i == 26) {
									voteMatrix.setBirla_rationale_vote_recomm(val);
								}
								else if (i == 27) {
									try {
										long birla_shares = Long.parseLong(val);
										voteMatrix.setBirla_shares_held(birla_shares);
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 28) {
									try {
										BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
										voteMatrix.setTotal_sharehldng_nps_comb(bg.stripTrailingZeros());
									} catch (Exception e) {
										 _log.error(e);
									}
								}
								else if (i == 29) {
									voteMatrix.setPrcnt_total_shares_held_comb(val);
								}
								else if (i == 30) {
									voteMatrix.setMajority_vote_pfm_comb(val);
								}
								else if (i == 31) {
									voteMatrix.setSes_vote_recomm(val);
								}
								else if (i == 32) {
									voteMatrix.setSes_rationale_vote_recomm(val);
								}
								else if (i == 33) {
									String str1 = val.replace("%", "");
									voteMatrix.setPrcnt_tot_shares_held_promoter(str1);
								}
								else if (i == 34) {
									String str2 = val.replace("%", "");
									voteMatrix.setVotes_polled_promoter(str2);
								}
								else if (i == 35) {
									
									String str3 = val.replace("%", "");
									voteMatrix.setVotes_in_favor_promoter(str3);
								}
								else if (i == 36) {
									String str4 = val.replace("%", "");
									voteMatrix.setVotes_in_against_promoter(str4);
								}
								else if (i == 37) {
									String str5 = val.replace("%", "");
									voteMatrix.setPrcnt_tot_shares_held_institut(str5);
								}
								else if (i == 38) {
									String str6 = val.replace("%", "");
									voteMatrix.setVotes_polled_institution(str6);
								}
								else if (i == 39) {
									String str7 = val.replace("%", "");
									voteMatrix.setVotes_in_favor_insttitution(str7);
								}
								else if (i == 40) {
									String str8 = val.replace("%", "");
									voteMatrix.setVotes_in_against_institution(str8);
								}
								else if (i == 41) {
									String str9 = val.replace("%", "");
									voteMatrix.setPrcnt_total_shares_held_retail(str9);
								}
								else if (i == 42) {
									String str10 = val.replace("%", "");
									voteMatrix.setVotes_polled_retail(str10);
								}
								else if (i == 43) {
									String str11 = val.replace("%", "");
									voteMatrix.setVotes_in_favor_retail(str11);
								}
								else if (i == 44) {
									String str12 = val.replace("%", "");
									voteMatrix.setVotes_in_against_retail(str12);
								}
								else if (i == 45) {
									String str13 = val.replace("%", "");
									voteMatrix.setVotes_polled_voting_outcome(str13);
								}
								else if (i == 46) {
									String str14 = val.replace("%", "");
									voteMatrix.setVoting_for(str14);
								}
								else if (i == 47) {
									String str15 = val.replace("%", "");
									voteMatrix.setVoting_against(str15);
								}
								else if (i == 48) {
									voteMatrix.setOutcome(val);
								}
								else if (i == 49) {
									voteMatrix.setSes_report(val);
								}
								
							}
						} else if(i == 0 && rowCount > 1) {
							break rowloop;
						}
					}
					voteMatrix.setCreatedon(new Date());
						
						if (isError && rowCount > 1) {
							errorArray.put(errorObj);
							errorRow = xx.createRow(errorRowCount);
							errorRowCount++;
							XSSFCell rowCountCel = errorRow.createCell(1);
							rowCountCel.setCellValue(rowCount);
							XSSFCell cellError = errorRow.createCell(errorObj.getInt("cellno"));
							cellError.setCellValue(errorObj.getString("msg"));
							isexcelhaserror = true;
						} else if (rowCount > 1) {
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject(voteMatrix.toString());
							SesVoteMatrixJsonArray.put(jsonObject);
							SesVoteMatrixList.add(voteMatrix);
						}
						rowCount++;
					}
				}
			else {
				_log.info("file==null   >>>>>>>>>>");
			}
			}catch (Exception e) {
				 _log.error(e);
			}_log.info("after try block");
	}
	
	

}
