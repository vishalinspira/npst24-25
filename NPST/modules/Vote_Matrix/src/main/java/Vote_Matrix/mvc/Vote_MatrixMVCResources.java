package Vote_Matrix.mvc;

import com.daily.average.service.model.VMatrix;
import com.daily.average.service.service.ReportUploadLogPFMAMLocalServiceUtil;
import com.daily.average.service.service.VMatrixLocalService;
import com.daily.average.service.service.VMatrixLocalServiceUtil;
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

import Vote_Matrix.constants.Vote_MatrixPortletKeys;
import nps.common.service.util.CommonParser;
import nps.email.api.api.ExcelValidationAn10Api;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" +  Vote_MatrixPortletKeys.VOTE_MATRIX,
				"mvc.command.name=/saveVotematrixfileURL/data"
		},
		service = MVCResourceCommand.class
)

public class Vote_MatrixMVCResources implements MVCResourceCommand{

	private static Log _log = LogFactoryUtil.getLog(Vote_MatrixMVCResources.class);
	 
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		_log.info("Serve Resource method");
		
		_log.info("welcome to Vote_MatrixMVCResources:::::::::::::::::::::");
		
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
		_log.info("Inside fileUpload");
		
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
		
		String fileName = uploadPortletRequest.getFileName("votematrixfile");

		File file = uploadPortletRequest.getFile("votematrixfile");
		JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		
		//resultJsonObject.put("status",true);
		

		String mimeType = uploadPortletRequest.getContentType("votematrixfile");

		String title = fileName;

		JSONArray vmArray = JSONFactoryUtil.createJSONArray();
		List<VMatrix> vmList = new ArrayList<VMatrix>();
		//JSONObject resultJsonObject = JSONFactoryUtil.createJSONObject();
		JSONArray errorArray = JSONFactoryUtil.createJSONArray();
		XSSFWorkbook workbook = null;
		
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
		XSSFCell customerIdcell = xxx.createCell(2);
		customerIdcell.setCellValue("Payment Id");
		XSSFCell NPSNumber = xxx.createCell(3);
		NPSNumber.setCellValue("Ret. Ref. No.");
		
		VMatrixLocalServiceUtil.deleteVMatrixByReportUploadLogId(reportUploadLogId);
		
		
		boolean isexcelhaserror = false;
		try {
			if (file != null) {
				OPCPackage pkg = OPCPackage.open(file);
				workbook = new XSSFWorkbook(pkg);
				XSSFSheet sheet = workbook.getSheet("Quarterly Vote Matrix Results");
				if (sheet == null) {
					resultJsonObject.put("status", false);
					resultJsonObject.put("sheeterror",true);
					JSONArray  errorSheetNameList = JSONFactoryUtil.createJSONArray();
					//errorSheetNameList.put("Vote Matrix");
					errorSheetNameList.put("Quarterly Vote Matrix Results");
					resultJsonObject.put("errorSheetNameList", errorSheetNameList);
					return resultJsonObject;
					}else {
						resultJsonObject = excelValidationAn10Api.validateExcelFile(file, resourceRequest);
						if(resultJsonObject.getBoolean("status")) {
							Iterator<Row> rows = sheet.rowIterator();
							int rowCount = 1;
							int errorRowCount = 2;
							
							rowLoop:
							while (rows.hasNext()) {
								VMatrix vm = (VMatrix) vMatrixLocalService.
										createVMatrix(CounterLocalServiceUtil.increment(VMatrix.class.getName()));
								vm.setCreatedby(userId);
								vm.setReportUploadLogId(reportUploadLogId);
								
								JSONObject errorObj = JSONFactoryUtil.createJSONObject();
								errorObj.put("rownum", rowCount);
								boolean isError = false;
								XSSFRow row = (XSSFRow) rows.next();
								XSSFRow errorRow = null;
								
								for (int i = 0; i < 63; i++) {
									XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
									if (cell != null) {
		
										DataFormatter formatter = new DataFormatter();
			
										String val = formatter.formatCellValue(cell);
										if (val != null)
											val = val.trim();
										if(rowCount > 1) {
										//_log.info("Val: " + val);
											if (i == 0) {
												//Date date_1=null;
												try {
													vm.setMeeting_date(cell.getDateCellValue());
													//date_1 = CommonParser.parseDate(val, cell);
												} catch (Exception e) {
													_log.info("error parsing date--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg);
													return resultJsonObject;
												}
												
											} 
											else if (i == 1) {
												vm.setIsin(val);
											} 
											else if (i == 2) {
												vm.setCompany_name(val);
											} 
											else if (i == 3) {
												vm.setMeeting_type(val);
											}
											else if (i == 4) {
												vm.setResolution_category(val);
											} 
											else if (i == 5) {
												vm.setResolution_dtls(val);
											} 
											else if (i == 6) {
												vm.setResolution_type(val);
											} 
											else if (i == 7) {
												vm.setLic_vote(val);
											} 
											else if (i == 8) {
												vm.setLic_rationale_vote_recomm(val);
											}
											else if (i == 9) {
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(val);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setLic_shares_held(bg);
											}
											else if (i == 10) {
												vm.setUti_vote(val);
											}
											else if (i == 11) {
												vm.setUti_rationale_vote_recomm(val);
											}
											else if (i == 12) {
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(val);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setUti_shares_held(bg);
											}
											else if (i == 13) {
												vm.setSbi_vote(val);
											}
											else if (i == 14) {
												vm.setSbi_rationale_vote_recomm(val);
											}
											else if (i == 15) {
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(val);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setSbi_shares_held(bg);
											}
											else if (i == 16) {
												vm.setHdfc_vote(val);
											}
											else if (i == 17) {
												vm.setHdfc_rationale_vote_recomm(val);
											}
											else if (i == 18) {
												BigDecimal bg;
												try {
													bg = CommonParser.parseBigDecimal(val);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setHdfc_shares_held(bg);
											}
											else if (i == 19) {
												vm.setIcici_vote(val);
											}
											else if (i == 20) {
												vm.setIcici_rationale_vote_recomm(val);
											}
											else if (i == 21) {
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(val);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val+"   i: "+i);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setIcici_shares_held(bg);
											}
											else if (i == 22) {
												vm.setKotak_vote(val);
											}
											else if (i == 23) {
												vm.setKotak_rationale_vote_recomm(val);
											}
											else if (i == 24) {
												BigDecimal bg = (BigDecimal) decimalFormat.parse(val);
												vm.setKotak_shares_held(bg);
											}
											else if (i == 25) {
												vm.setBirla_vote(val);
											}
											else if (i == 26) {
												vm.setBirla_rationale_vote_recomm(val);
											}
											else if (i == 27) {
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(val);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val+"   i: "+i);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setBirla_shares_held(bg);
											}
											
											
											
											else if (i == 28) {
												vm.setAxis_vote(val);
											}
											else if (i == 29) {
												vm.setAxis_rationale_vote_recomm(val);
											}
											else if (i == 30) {
												try {
													long birla_shares = CommonParser.parseLong(val);
													vm.setAxis_shares_held(birla_shares);
												} catch (Exception e) {
													_log.info("error parsing long--->"+val+"   i: "+i);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
											}
											
											else if (i == 31) {
												vm.setTata_vote(val);
											}
											else if (i == 32) {
												vm.setTata_rationale_vote_recomm(val);
											}
											else if (i == 33) {
												try {
													long birla_shares = CommonParser.parseLong(val);
													vm.setTata_shares_held(birla_shares);
												} catch (Exception e) {
													_log.info("error parsing long--->"+val+"   i: "+i);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
											}
											
											else if (i == 34) {
												vm.setMax_vote(val);
											}
											else if (i == 35) {
												vm.setMax_rationale_vote_recomm(val);
											}
											else if (i == 36) {
												try {
													long birla_shares = CommonParser.parseLong(val);
													vm.setMax_shares_held(birla_shares);
												} catch (Exception e) {
													_log.info("error parsing long--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
											}
										
											else if (i == 37) {
												vm.setDsp_vote(val);
											}
											else if (i == 38) {
												vm.setDsp_rationale_vote_recomm(val);
											}
											else if (i == 39) {
												try {
													long birla_shares = CommonParser.parseLong(val);
													vm.setDsp_shares_held(birla_shares);
												} catch (Exception e) {
													_log.info("error parsing long--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
											}
											
											
											else if (i == 40) {
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(val);
												} catch (Exception e) {
													_log.info("error in parsing big decimal--->"+val);
													 resultJsonObject.put("status", false);
													 resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													 return resultJsonObject;
												}
												vm.setTotal_shareholding_nps_comb(bg);
											}
											else if (i == 41) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error in parsing big decimal--->"+val);
													 resultJsonObject.put("status", false);
													 resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													 return resultJsonObject;
												}
												vm.setPrcnt_total_shares_held_comb(bg.stripTrailingZeros());
											}
											else if (i == 42) {
												vm.setMajority_vote_pfm_combined(val);
											}
											else if (i == 43) {
												vm.setSes_vote_recomm(val);
											}
											else if (i == 44) {
												vm.setSes_rationale_vote_recomm(val);
											}
											else if (i == 45) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setPr_of_total_shares_held_prom(bg.stripTrailingZeros());
											}
											else if (i == 46) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+str2);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setVotes_polled_promoter(bg.stripTrailingZeros());
											}
											else if (i == 47) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setVotes_in_favor_promoter(bg.stripTrailingZeros());
											}
											else if (i == 48) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setVotes_in_against_promoter(bg.stripTrailingZeros());
											}
											else if (i == 49) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setPrcnt_total_shares_held_inst(bg.stripTrailingZeros());
											}
											else if (i == 50) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setVotes_polled_inst(bg.stripTrailingZeros());
											}
											else if (i == 51) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg);
													return resultJsonObject;
												}
												vm.setVotes_in_favor_inst(bg.stripTrailingZeros());
											}
											else if (i == 52) {
												String str2 = val.replace("%", "");
												BigDecimal bg;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setVotes_in_against_inst(bg.stripTrailingZeros());
											}
											else if (i == 53) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setPrcnt_total_shares_held_retail(bg.stripTrailingZeros());
											}
											else if (i == 54) {
												String str2 = val.replace("%", "");
												BigDecimal bg;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg);
													return resultJsonObject;
												}
												vm.setVotes_polled_retail(bg.stripTrailingZeros());
											}
											else if (i == 55) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setVotes_in_favor_retail(bg.stripTrailingZeros());
											}
											else if (i == 56) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setVotes_in_against_retail(bg.stripTrailingZeros());
											}
											else if (i == 57) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setVotes_polled(bg.stripTrailingZeros());
											}
											else if (i == 58) {
												String str2 = val.replace("%", "");
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(str2);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.numberExceptionMsg);
													return resultJsonObject;
												}
												vm.setFor_(bg.stripTrailingZeros());
											}
											else if (i == 59) {
												BigDecimal bg=null;
												try {
													bg = CommonParser.parseBigDecimal(val);
												} catch (Exception e) {
													_log.info("error parsing big decimal--->"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.fileExceptionMsg);
													return resultJsonObject;
												}
											    vm.setAgainst(bg);
											}
											else if (i == 60) {
												vm.setOutcome(val);
											}
											else if (i == 61) {
												vm.setSes_report(val);
											}
											else if (i==62) {
			                                	try {
													Date date_1 = cell.getDateCellValue();// new SimpleDateFormat("dd-MM-yyyy").parse(val);
													vm.setReportingDate(date_1);
												} catch (Exception e) {
													_log.info("error parsing date"+val);
													resultJsonObject.put("status", false);
													resultJsonObject.put("msg", CommonParser.dateExceptionMsg);
													return resultJsonObject;
												}
											}
										 
										}
									}else if(i==0 && rowCount > 1) {
										break rowLoop;
									}
								}
							
								vm.setCreatedate(new Date());
								
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
									JSONObject valuationRepSgJsonObject = JSONFactoryUtil.createJSONObject(vm.toString());
									vmArray.put(valuationRepSgJsonObject);
									vmList.add(vm);
								}
								rowCount++;
							}
							_log.info("vm" +" rowcount"+rowCount);
						}else{
							resultJsonObject.put("status", false);
							resultJsonObject.put("sheeterror",true);
							resultJsonObject.put("msg", "Error while file validation.");
						}
					}
				}
			}catch (Exception e) {
				 _log.error(e);
			} 
			//_log.info(valuationsgArray.toString());
			//_log.info(errorArray);
			
			//_log.info("isexcelhaserror" + isexcelhaserror);
			if (!isexcelhaserror) {
				vMatrixLocalService.addVMatrix(vmList);
				_log.info("vm" +" datacount"+vmList.size());
				
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

		String fileName =date.getTime()+ uploadPortletRequest.getFileName("votematrixfile");

		File file = uploadPortletRequest.getFile("votematrixfile");

		String mimeType = uploadPortletRequest.getContentType("votematrixfile");

		String title = fileName;

		String description = "votematrixfile";

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
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, "Weekly");
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
	VMatrixLocalService vMatrixLocalService;
	
	@Reference
	ExcelValidationAn10Api excelValidationAn10Api;
}
