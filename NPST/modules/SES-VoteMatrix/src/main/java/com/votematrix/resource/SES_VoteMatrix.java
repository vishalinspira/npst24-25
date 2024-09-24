package com.votematrix.resource;

import com.daily.average.service.service.ReportUploadLogPFMAMLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import SES.VoteMatrix.constants.SESVoteMatrixPortletKeys;
import compliance.service.model.VoteMatrix;
import compliance.service.service.VoteMatrixLocalService;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(property = { 
		"javax.portlet.name=" + SESVoteMatrixPortletKeys.SESVOTEMATRIX,
		"mvc.command.name=" + SESVoteMatrixPortletKeys.vote, 
		}, 
service = MVCResourceCommand.class)

public class SES_VoteMatrix implements MVCResourceCommand {
	
	private static Log _log = LogFactoryUtil.getLog(SES_VoteMatrix.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONObject resultJsonObject = fileUpload(themeDisplay, resourceRequest);
		try {
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(resultJsonObject.toString());
		} catch (IOException e) {
			 _log.error(e);
		}
		
		return false;
	}
	
	public JSONObject fileUpload(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Long userId = themeDisplay.getUserId();
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String statusByUserName = themeDisplay.getUser().getFullName();
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(resourceRequest);
		} catch (PortalException e) {
			 _log.error(e);
		}
		
		String reportUploadLogIdString = uploadPortletRequest.getParameter("reportUploadLogId");
		_log.info("reportUploadLogIdString" + reportUploadLogIdString);
		Long reportUploadLogId = Long.parseLong(reportUploadLogIdString);
		_log.info("reportUploadLogId" + reportUploadLogId);
		
		String fileName = uploadPortletRequest.getFileName("voteFile");

		File file = uploadPortletRequest.getFile("voteFile");
		//excelValidationAn7Api.validateExcelFile(file);

		String mimeType = uploadPortletRequest.getContentType("voteFile");

		String title = fileName;

		JSONArray voteMatrixJsonArray = JSONFactoryUtil.createJSONArray();
		List<VoteMatrix> voteMatrixList = new ArrayList<VoteMatrix>();
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
		XSSFWorkbook workbook = null;
		
		voteMatrixLocalService.deleteVoteMatrixByReportUploadLogId(reportUploadLogId);

		resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
		if(resultJsonObject.getBoolean("status")) {
			
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#,##0.0#";
			DecimalFormat decimalFormat = new DecimalFormat(pattern,symbols);
			decimalFormat.setParseBigDecimal(true);
			
			/**
			 * Create error excel
			 */
			XSSFWorkbook errorExcel = new XSSFWorkbook();
			XSSFSheet xx = (XSSFSheet) errorExcel.createSheet();
			XSSFRow xxx = xx.createRow(1);
			XSSFCell rowNumCel = xxx.createCell(1);
			rowNumCel.setCellValue("RowNum");
			XSSFCell fileNamecell = xxx.createCell(2);
			fileNamecell.setCellValue("Meeting Date");

			boolean isexcelhaserror = false;
			try {
				if (file != null) {
					OPCPackage pkg = OPCPackage.open(file);
					workbook = new XSSFWorkbook(pkg);
					
					//sheet validate
					List<String> errorlist = ValidateSheetName.ValidateExcelSheetName(file, workbook);
					
					if (errorlist.size() > 0) {
						resultJsonObject.put("status", false);
						resultJsonObject.put("sheeterror",true);
						JSONArray arrayList = JSONFactoryUtil.createJSONArray(errorlist);
						resultJsonObject.put("errorSheetNameList", arrayList);
						try {
							errorExcel.close();
						} catch (IOException e) {
							 _log.error(e);
						}
						// return
						return resultJsonObject;
					}else {
						
						XSSFSheet sheet = workbook.getSheetAt(0);
						String sheetname = sheet.getSheetName();
						Iterator<Row> rows = sheet.rowIterator();
						int rowCount = 1;
						int errorRowCount = 2;
						
						rowloop:
						while (rows.hasNext()) {
							
							VoteMatrix voteMatrix = (VoteMatrix) voteMatrixLocalService.
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
								
								if (cell != null) {
		
									DataFormatter formatter = new DataFormatter();
									
									String val = formatter.formatCellValue(cell);
									if (val != null)
										val = val.trim();
									if(rowCount > 1) {
										_log.debug("Val: " + val);
										if (i == 0) {
											if (val != null && Validator.isNotNull(val) && val.length() > 0) {
												//Long srno=Long.parseLong(val);
											//	Date date_1=null;
												try {
												//	date_1 = CommonParser.parseDate(val, cell);
													voteMatrix.setMeeting_date(cell.getDateCellValue());
												} catch (Exception e) {
													_log.info("error parsing date--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg);
													return resultJsonObject;
												}
												//voteMatrix.setMeeting_date(date_1);
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
											try {
												BigDecimal res_no = CommonParser.parseBigDecimal(val);
												voteMatrix.setResolution_no(res_no);
											} catch (Exception e) {
												_log.info("error parsing big decimal--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										}
										else if (i == 5) {
											voteMatrix.setResolution_category(val);
										} 
										else if (i == 6) {
											voteMatrix.setResol_dtls(val);
										}
										else if (i == 7) {
											voteMatrix.setResol_type(val);
										}
										else if (i == 8) {
											voteMatrix.setLic_vote(val);
										}
										else if (i == 9) {
											voteMatrix.setLic_rationale_vote_recomm(val);
										} 
										else if (i == 10) {
											try {
												long lic_shares = CommonParser.parseLong(val);
												voteMatrix.setLic_shares_held(lic_shares);
											} catch (Exception e) {
												_log.info("error parsing long--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										} 
										else if (i == 11) {
											voteMatrix.setUti_vote(val);
										} 
										else if (i == 12) {
											voteMatrix.setUti_rationale_vote_recomm(val);
										} 
										else if (i == 13) {
											try {
												long uti_shares = Long.parseLong(val);
												voteMatrix.setUti_shares_held(uti_shares);
											} catch (Exception e) {
												 _log.error(e);
											}
										} 
										else if (i == 14) {
											voteMatrix.setSbi_vote(val);
										} 
										else if (i == 15) {
											voteMatrix.setSbi_rationale_vote_recomm(val);
										} 
										else if (i == 16) {
											try {
												long sbi_shares = CommonParser.parseLong(val);
												voteMatrix.setSbi_shares_held(sbi_shares);
											} catch (Exception e) {
												_log.info("error parsing long--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										} 
										else if (i == 17) {
											voteMatrix.setHdfc_vote(val);
										} 
										else if (i == 18) {
											voteMatrix.setHdfc_rationale_vote_recomm(val);
										} 
										else if (i == 19) {
											try {
												long hdfc_shares = CommonParser.parseLong(val);
												voteMatrix.setHdfc_shares_held(hdfc_shares);
											} catch (Exception e) {
												_log.info("error parsing long--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										} 
										else if (i == 20) {
											voteMatrix.setIcici_vote(val);
										} 
										else if (i == 21) {
											voteMatrix.setIcici_rationale_vote_recomm(val);
										}
										else if (i == 22) {
											try {
												long icici_shares = CommonParser.parseLong(val);
												voteMatrix.setIcici_shares_held(icici_shares);
											} catch (Exception e) {
												_log.info("error parsing long--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										}
										else if (i == 23) {
											voteMatrix.setKotak_vote(val);
										}
										else if (i == 24) {
											voteMatrix.setKotak_rationale_vote_recomm(val);
										}
										else if (i == 25) {
											try {
												long kotak_shares = CommonParser.parseLong(val);
												voteMatrix.setKotak_shares_held(kotak_shares);
											} catch (Exception e) {
												_log.info("error parsing long--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										}
										else if (i == 26) {
											voteMatrix.setBirla_vote(val);
										}
										else if (i == 27) {
											voteMatrix.setBirla_rationale_vote_recomm(val);
										}
										else if (i == 28) {
											try {
												long birla_shares = CommonParser.parseLong(val);
												voteMatrix.setBirla_shares_held(birla_shares);
											} catch (Exception e) {
												_log.info("error parsing long--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										}
										
										
										else if (i == 29) {
											voteMatrix.setAxis_vote(val);
										}
										else if (i == 30) {
											voteMatrix.setAxis_rationale_vote_recomm(val);
										}
										else if (i == 31) {
											try {
												long birla_shares = CommonParser.parseLong(val);
												voteMatrix.setAxis_shares_held(birla_shares);
											} catch (Exception e) {
												_log.info("error parsing long--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										}
										
										else if (i == 32) {
											voteMatrix.setTata_vote(val);
										}
										else if (i == 33) {
											voteMatrix.setTata_rationale_vote_recomm(val);
										}
										else if (i == 34) {
											try {
												long birla_shares = CommonParser.parseLong(val);
												voteMatrix.setTata_shares_held(birla_shares);
											} catch (Exception e) {
												_log.info("error parsing long--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										}
										
										else if (i == 35) {
											voteMatrix.setMax_vote(val);
										}
										else if (i == 36) {
											voteMatrix.setMax_rationale_vote_recomm(val);
										}
										else if (i == 37) {
											try {
												long birla_shares = CommonParser.parseLong(val);
												voteMatrix.setMax_shares_held(birla_shares);
											} catch (Exception e) {
												_log.info("error parsing long--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										}
									
										else if (i == 38) {
											voteMatrix.setDsp_vote(val);
										}
										else if (i == 39) {
											voteMatrix.setDsp_rationale_vote_recomm(val);
										}
										else if (i == 40) {
											try {
												long birla_shares = CommonParser.parseLong(val);
												voteMatrix.setDsp_shares_held(birla_shares);
											} catch (Exception e) {
												_log.info("error parsing long--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										}
										
										else if (i == 41) {
											try {
												BigDecimal bg = CommonParser.parseBigDecimal(val);
												voteMatrix.setTotal_sharehldng_nps_comb(bg.stripTrailingZeros());
											} catch (Exception e) {
												_log.info("error parsing big decimal--->"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
												return resultJsonObject;
											}
										}
										else if (i == 42) {
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
											voteMatrix.setPrcnt_total_shares_held_comb(val);
										}
										else if (i == 43) {
											voteMatrix.setMajority_vote_pfm_comb(val);
										}
										else if (i == 44) {
											voteMatrix.setSes_vote_recomm(val);
										}
										else if (i == 45) {
											voteMatrix.setSes_rationale_vote_recomm(val);
										}
										else if (i == 46) {
											//_log.info("cell " + cell.getCellType());
											String str1 = val.replace("%", "");
											//_log.info("str1 " + str1);
											//double promoShares = Double.parseDouble(str1);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str1);
											voteMatrix.setPrcnt_tot_shares_held_promoter(str1);
										}
										else if (i == 47) {
											//_log.info("cell " + cell.getCellType());
											String str2 = val.replace("%", "");
											//double promoPolled = Double.parseDouble(str2);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str2);
											voteMatrix.setVotes_polled_promoter(str2);
										}
										else if (i == 48) {
											//_log.info("cell " + cell.getCellType());
											String str3 = val.replace("%", "");
											//double promoFavor = Double.parseDouble(str3);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str3);
											voteMatrix.setVotes_in_favor_promoter(str3);
										}
										else if (i == 49) {
											//_log.info("cell " + cell.getCellType());
											String str4 = val.replace("%", "");
											//double promoAgainst = Double.parseDouble(str4);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str4);
											voteMatrix.setVotes_in_against_promoter(str4);
										}
										else if (i == 50) {
											//_log.info("cell " + cell.getCellType());
											String str5 = val.replace("%", "");
											//double instituteShares = Double.parseDouble(str5);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str5);
											voteMatrix.setPrcnt_tot_shares_held_institut(str5);
										}
										else if (i == 51) {
											//_log.info("cell " + cell.getCellType());
											String str6 = val.replace("%", "");
											//double institutePolled = Double.parseDouble(str6);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str6);
											voteMatrix.setVotes_polled_institution(str6);
										}
										else if (i == 52) {
											//_log.info("cell " + cell.getCellType());
											String str7 = val.replace("%", "");
											//double instituteFavor = Double.parseDouble(str7);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str7);
											voteMatrix.setVotes_in_favor_insttitution(str7);
										}
										else if (i == 53) {
											//_log.info("cell " + cell.getCellType());
											String str8 = val.replace("%", "");
											//double instituteAgainst = Double.parseDouble(str8);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str8);
											voteMatrix.setVotes_in_against_institution(str8);
										}
										else if (i == 54) {
											//_log.info("cell " + cell.getCellType());
											String str9 = val.replace("%", "");
											//double retailShares = Double.parseDouble(str9);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str9);
											voteMatrix.setPrcnt_total_shares_held_retail(str9);
										}
										else if (i == 55) {
											//_log.info("cell " + cell.getCellType());
											String str10 = val.replace("%", "");
											//double retailPolled = Double.parseDouble(str10);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str10);
											voteMatrix.setVotes_polled_retail(str10);
										}
										else if (i == 56) {
											//_log.info("cell " + cell.getCellType());
											String str11 = val.replace("%", "");
											//double retailFavor = Double.parseDouble(str11);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str11);
											voteMatrix.setVotes_in_favor_retail(str11);
										}
										else if (i == 57) {
											//_log.info("cell " + cell.getCellType());
											String str12 = val.replace("%", "");
											//double retailAgainst = Double.parseDouble(str12);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str12);
											voteMatrix.setVotes_in_against_retail(str12);
										}
										else if (i == 58) {
											//_log.info("cell " + cell.getCellType());
											String str13 = val.replace("%", "");
											//double outcomePolled = Double.parseDouble(str13);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str13);
											voteMatrix.setVotes_polled_voting_outcome(str13);
										}
										else if (i == 59) {
											//_log.info("cell " + cell.getCellType());
											String str14 = val.replace("%", "");
											//double outcomeFor = Double.parseDouble(str14);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str14);
											voteMatrix.setVoting_for(str14);
										}
										else if (i == 60) {
											//_log.info("cell " + cell.getCellType());
											String str15 = val.replace("%", "");
											//double outcomeAgainst = Double.parseDouble(str15);
											//BigDecimal bg = (BigDecimal) decimalFormat.parse(str15);
											voteMatrix.setVoting_against(str15);
										}
										else if (i == 61) {
											voteMatrix.setOutcome(val);
										}
										else if (i == 62) {
											//_log.info("cell " + cell.getCellType());
											voteMatrix.setSes_report(val);
										}
										 else if (i == 63) {
											 voteMatrix.setSes_id(val);
											}
			                                else if (i == 64) {
			                                	voteMatrix.setSes_res_id(val);
											}
										
										else if (i==65) {
		                                	try {
												Date date_1 = cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
												voteMatrix.setReportingDate(date_1);
											} catch (Exception e) {
												_log.info("error parsing date"+val);
												resultJsonObject.put("status", false);
												resultJsonObject.put("msg", CommonParser.dateExceptionMsg);
												return resultJsonObject;
											}
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
									//_log.info("vote matrix: " + voteMatrix.toString());
									//JSONObject voteMatrixsonObject = JSONFactoryUtil.createJSONObject(voteMatrix.toString());
									//voteMatrixJsonArray.put(voteMatrixsonObject);
									voteMatrixList.add(voteMatrix);
								}
								rowCount++;
							}
						_log.info("voteMatrix" +" rowcount"+rowCount);
					}
					
						
					}
				}catch (Exception e) {
					 _log.error(e);
				}
				//_log.info(voteMatrixJsonArray.toString());
				
				if (!isexcelhaserror) {
					voteMatrixLocalService.addVoteMatrix(voteMatrixList);
					_log.info("voteMatrix" +" datacount"+voteMatrixList.size());
					
					Long fileEntryId = 0l;
					fileEntryId = uploadFILETOFOLDER(themeDisplay, resourceRequest);
					try {
						errorExcel.close();
						String remarks = ParamUtil.getString(uploadPortletRequest, "remarks");
						updateReportLog ( userId, fileEntryId, true, reportUploadLogId,  WorkflowConstants.STATUS_PENDING, statusByUserName, serviceContext,remarks);
						//resultJsonObject.put("pdfURL",  pdfTable(eTierIList, themeDisplay, resourceRequest));
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("status", true);
				} else {
					String downloadUrl = null;
					try {
	
						OutputStream stream = null;
	
						File errortempfile = File.createTempFile("error", "xlsx");
						stream = new FileOutputStream(errortempfile);
	
						errorExcel.write(stream);
	
						String filePath = errortempfile.getPath();
						String filename = errortempfile.getName();
						stream.close();
						errorExcel.close();
						downloadUrl = fileUpload(themeDisplay, filePath, filename, resourceRequest);
					} catch (Exception e) {
						 _log.error(e);
					}
					resultJsonObject.put("downloadUrl", downloadUrl);
					resultJsonObject.put("status", false);
				}
			}
			return resultJsonObject;
		
		}
	
	@SuppressWarnings("deprecation")
	public String fileUpload(ThemeDisplay themeDisplay, String filepath, String filename,
			ResourceRequest resourceRequest) {

		File file = new File(filepath);
		String mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		String title = filename + ".xlsx";
		String description = "This file is added via programatically";
		long repositoryId = themeDisplay.getScopeGroupId();
		String downloadUrl = StringPool.BLANK;
		try {

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);
			FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId, 0, title, mimeType, title, description, "",
					file, serviceContext);
			FileVersion fileVersion = (FileVersion) entry.getLatestFileVersion();
			downloadUrl = DLURLHelperUtil.getDownloadURL(entry, fileVersion, themeDisplay, StringPool.BLANK);
		} catch (Exception e) {
			_log.info(e.getMessage());
			 _log.error(e);
		}
		return downloadUrl;
	}
	
	@SuppressWarnings("deprecation")
	public long uploadFILETOFOLDER(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) {
		Date date =new Date();
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);

		String fileName = date.getTime() + uploadPortletRequest.getFileName("voteFile");

		File file = uploadPortletRequest.getFile("voteFile");

		String mimeType = uploadPortletRequest.getContentType("voteFile");

		String title = fileName;

		String description = "SES - Vote Matrix";

		long repositoryId = themeDisplay.getScopeGroupId();
	

		try {

			Folder folder = getFolder(themeDisplay);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(),
					resourceRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getPrimaryKey();

		} catch (Exception e) {

			_log.info(e.getMessage());

			 _log.error(e);
		}
		return 0;
	}
	
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	
	public Folder getFolder(ThemeDisplay themeDisplay) {

		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Monthly");
			_log.info(folder);

		} catch (Exception e) {

			_log.info(e.getMessage());
		}
		return folder;

	}
	
	public void updateReportLog( long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, String statusByUserName, ServiceContext serviceContext, String remarks) {
		Date createDate = new Date();
		ReportUploadLogPFMAMLocalServiceUtil.updateReportUploadLog(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId, status, createdBy, statusByUserName, createDate, serviceContext,remarks);//(createDate, createdBy, fileEntryId, uploaded, reportUploadLogId);
	}
	
	@Reference
	VoteMatrixLocalService voteMatrixLocalService;
	
//	@Reference
//	ExcelValidationAn7Api excelValidationAn7Api;
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;

}
