<%@ include file="/init.jsp" %>

<portlet:resourceURL id="manpower/form" var="manpowerResourceURL" />

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
            <h4>ManPowerForm1</h4>
            <form class="form" id="manpower" action="#" method="post">
               <div class="statement-wrapper">
              	  <input type="hidden" value="${reportUploadLogId }" name="<portlet:namespace/>reportUploadLogId" class="reportUploadLogId" />
		 		  <input type="hidden" value="${reportMasterId }" name="<portlet:namespace/>reportMasterId" class="reportMasterId"/>
                  <input type="hidden" id="rowCountFT" name="<portlet:namespace />rowCountFT">
                  
               	  <div id="table" class="table-editable">

                     <div class="table-cont">
                        <table id="myTable_1" class="table css-serial w-100 table-responsive">
                           <thead>
                           <tr>
							<td colspan=13 class="text-center"><strong>Information Of Change In Key Personal Of The Pension Found Section 1</strong></td>
							</tr>
                              <tr>
                                 <th>Sr.No</th>
                                 <th>Destination</th>
                                 <th>Name</th>
                                 <th>Date Of Appoinment</th>
                                 <th>Contact No</th>
                                 <th>Email</th>
                                 <th>Highest Qualification</th>
                                 <th>Total Prof Exp</th>
                                 <th>Deputation Yes/No</th>
                                 <th>Linkedin ID</th>
                                 <th>Position</th>
                                 <th>Date Of Board Meeting Approving Appoinment</th>
                                 <th>Biodata</th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>
                                 	1
                                 </td>
                                 <td>
                                    <input type="text" class="destination" id="destination" name="<portlet:namespace />destination0_i[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name" name="<portlet:namespace />name0_i[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateOfAppoinment" id="dateOfAppoinment" name="<portlet:namespace/>dateofappoinment0_i[]">
                                 </td>
                                 <td>
                                    <input type="number" class="contactno" id="contactno" name="<portlet:namespace />contactno0_i[]">
                                 </td>
                                 <td>
                                    <input type="email" class="email" id="email" name="<portlet:namespace />email0_i[]">
                                 </td>
                                 <td>
                                    <input type="text" class="highestQualification" id="highestQualification" name="<portlet:namespace />highestqualification0_i[]">
                                 </td>
                                 <td>
                                    <input type="number" class="totalProfExp" id="totalProfExp" name="<portlet:namespace />totalprofexp0_i[]">
                                 </td>
                                 <td>
                                    <input type="text" class="deputation" id="deputation" name="<portlet:namespace />deputation0_i[]">
                                 </td>
                                 <td>
                                    <input type="text" class="linkedinID" id="linkedinID" name="<portlet:namespace />linkedinid0_i[]">
                                 </td>
                                 <td>
                                    <input type="text" class="position" id="position" name="<portlet:namespace />position0_i[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateOfBoardMeetngApprove" id="dateOfBoardMeetngApprove" name="<portlet:namespace />dateOfBoardMeetngApprove0_i[]">
                                 </td>
                                 <td>
                                    <input type="file" class="biodata" id="biodata" name="biodata_1" accept=".pdf">
                                 </td>

                              </tr>
                              
                              <tr>
                                 <td>
                                 	2
                                 </td>
                                 <td>
                                    <input type="text" class="destination" id="destination1_i" name="<portlet:namespace />destination1_i[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name1_i" name="<portlet:namespace />name1_i[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateOfAppoinment" id="dateOfAppoinment1" name="<portlet:namespace/>dateofappoinment1_i[]">
                                 </td>
                                 <td>
                                    <input type="number" class="contactno" id="contactno1" name="<portlet:namespace />contactno1_i[]">
                                 </td>
                                 <td>
                                    <input type="email" class="email" id="email1" name="<portlet:namespace />email1_i[]">
                                 </td>
                                 <td>
                                    <input type="text" class="highestQualification" id="highestQualification1" name="<portlet:namespace />highestqualification1_i[]">
                                 </td>
                                 <td>
                                    <input type="number" class="totalProfExp" id="totalProfExp1" name="<portlet:namespace />totalprofexp1_i[]">
                                 </td>
                                 <td>
                                    <input type="text" class="deputation" id="deputation1" name="<portlet:namespace />deputation1_i[]">
                                 </td>
                                 <td>
                                    <input type="text" class="linkedinID" id="linkedinID1" name="<portlet:namespace />linkedinid1_i[]">
                                 </td>
                                 <td>
                                    <input type="text" class="position" id="position1" name="<portlet:namespace />position1_i[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateOfBoardMeetngApprove" id="dateOfBoardMeetngApprove1" name="<portlet:namespace />dateOfBoardMeetngApprove1_i[]">
                                 </td>
                                 <td>
                                    <input type="file" class="biodata" id="biodata1" name="biodata_2" accept=".pdf">
                                 </td>

                              </tr>
                           </tbody>
                        </table>

                        <br><br>
                        
                        <table id="myTable_1a" class="table css-serial w-100 table-responsive">
                           <thead>
                           <tr>
							<td colspan=4 class="text-center"><strong>Investment and risk management committee compositior section1a</strong></td>
							</tr>
                              <tr>
                                 <th>Sr.No</th>
                                 <th>Type</th>
                                 <th>Status</th>
                                 <th> </th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>
                                 	1
                                 </td>
                                 <td>
                                    Investment Committee Composition
                                 </td>
                                 <td>
                                    <input type="radio" class="status" id="status1" name="<portlet:namespace />iccstatus" value="Change" checked>Change 
                                 </td>
                                 <td>
                                    <input type="radio" class="status" id="status1a" name="<portlet:namespace />iccstatus"value="No Change">NoChange
                                 </td>
                      
                              </tr>
                              <tr>
                                 <td>
                                 	2
                                 </td>
                                 <td>
                                    Risk Management committee Composition
                                 </td>
                                 <td>
                                    <input type="radio" class="status" id="status2" name="<portlet:namespace />rmccstatus" value="Change"checked>Change
                                 </td>
                                 <td>
                                    <input type="radio" class="status" id="status2a" name="<portlet:namespace />rmccstatus"value="No Change">No Change
                                 </td>
                      
                      
                              </tr>
                           </tbody>
                        </table>
                        
                         <br><br>
                        
                        <table id="myTable_1b" class="table css-serial w-100 table-responsive">
                           <thead>
                           <tr>
							<td colspan=5 class="text-center"><strong>Composition of investment committee section 1b</strong></td>
							</tr>
                              <tr>
                                 <th>Sr.No</th>
                                 <th>Designation</th>
                                 <th>Name</th>
                                 <th>Chairperson Member</th>
                                  <th>Date Of Appointment In The Committee</th>
                                   
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>
                                 	1
                                 </td>
                                 <td>
                                   <input type="text" class="designation" id="designation" name="<portlet:namespace />designation0_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="namee" name="<portlet:namespace />name0_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="chairperson_member" name="<portlet:namespace />chairpersonmember0_ib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee_ib" name="<portlet:namespace />dateofappointmentinthecommittee0_ib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	2
                                 </td>
                                 <td>
                                   <input type="text" class="designation" id="designation1" name="<portlet:namespace />designation1_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name1" name="<portlet:namespace />name1_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="chairperson_member1" name="<portlet:namespace />chairpersonmember1_ib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee1_ib" name="<portlet:namespace />dateofappointmentinthecommittee1_ib[]">
                                 </td>
                              </tr>
                             <tr>
                                 <td>
                                 	3
                                 </td>
                                 <td>
                                   <input type="text" class="designation" id="designation2" name="<portlet:namespace />designation2_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name2" name="<portlet:namespace />name2_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="chairperson_member2" name="<portlet:namespace />chairpersonmember2_ib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee2_ib" name="<portlet:namespace />dateofappointmentinthecommittee2_ib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	4
                                 </td>
                                 <td>
                                   <input type="text" class="designation" id="designation3" name="<portlet:namespace />designation3_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name3" name="<portlet:namespace />name3_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="chairperson_member3" name="<portlet:namespace />chairpersonmember3_ib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee3_ib" name="<portlet:namespace />dateofappointmentinthecommittee3_ib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	5
                                 </td>
                                 <td>
                                   <input type="text" class="designation" id="designation4" name="<portlet:namespace />designation4_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name4" name="<portlet:namespace />name4_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="chairperson_member4" name="<portlet:namespace />chairpersonmember4_ib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee4_ib" name="<portlet:namespace />dateofappointmentinthecommittee4_ib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	6
                                 </td>
                                 <td>
                                   <input type="text" class="designation" id="designation5" name="<portlet:namespace />designation5_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name5" name="<portlet:namespace />name5_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="chairperson_member5" name="<portlet:namespace />chairpersonmember5_ib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee5_ib" name="<portlet:namespace />dateofappointmentinthecommittee5_ib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	7
                                 </td>
                                 <td>
                                   <input type="text" class="designation" id="designation6" name="<portlet:namespace />designation6_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name6" name="<portlet:namespace />name6_ib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="chairperson_member6" name="<portlet:namespace />chairpersonmember6_ib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee6_ib" name="<portlet:namespace />dateofappointmentinthecommittee6_ib[]">
                                 </td>
                              </tr>
                           </tbody>
                        </table>
                        
                         <br><br>
                         
                         <table id="myTable_1c" class="table css-serial w-100 table-responsive">
                           <thead>
                           <tr>
							<td colspan=5 class="text-center"><strong>Composition of risk management committee section 1c</strong></td>
							</tr>
                              <tr>
                                 <th>Sr.No</th>
                                 <th>Designation</th>
                                 <th>Name</th>
                                 <th>Chairperson Member</th>
                                 <th>Date Of Appointment In The Committee</th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>
                                 	1
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation0" name="<portlet:namespace />designation1_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name0" name="<portlet:namespace />name1_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairpersonmember" id="chairpersonmember" name="<portlet:namespace />chairpersonmember1_ic[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee_ic" name="<portlet:namespace />dateofappointmentinthecommittee1_ic[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	2
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation-1" name="<portlet:namespace />designation2_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name-1" name="<portlet:namespace />name2_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairpersonmember" id="chairpersonmem" name="<portlet:namespace />chairpersonmember2_ic[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee1_ic" name="<portlet:namespace />dateofappointmentinthecommittee2_ic[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	3
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation-2" name="<portlet:namespace />designation3_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name-2" name="<portlet:namespace />name3_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairpersonmember" id="chairpersonmember2_ic" name="<portlet:namespace />chairpersonmember3_ic[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee2_ic" name="<portlet:namespace />dateofappointmentinthecommittee3_ic[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	4
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation-3" name="<portlet:namespace />designation4_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name-3" name="<portlet:namespace />name4_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairpersonmember" id="chairpersonmember3_ic" name="<portlet:namespace />chairpersonmember4_ic[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee3_ic" name="<portlet:namespace />dateofappointmentinthecommittee4_ic[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	5
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation-5" name="<portlet:namespace />designation5_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name-5" name="<portlet:namespace />name5_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairpersonmember" id="chairpersonmember5_ic" name="<portlet:namespace />chairpersonmember5_ic[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee5_ic" name="<portlet:namespace />dateofappointmentinthecommittee5_ic[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	6
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation-6" name="<portlet:namespace />designation6_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name-6" name="<portlet:namespace />name6_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairpersonmember" id="chairpersonmember6_ic" name="<portlet:namespace />chairpersonmember6_ic[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee6_ic" name="<portlet:namespace />dateofappointmentinthecommittee6_ic[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	7
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation-7" name="<portlet:namespace />designation7_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name-7" name="<portlet:namespace />name7_ic[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairpersonmember" id="chairpersonmember7_ic" name="<portlet:namespace />chairpersonmember7_ic[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee7_ic" name="<portlet:namespace />dateofappointmentinthecommittee7_ic[]">
                                 </td>
                              </tr>
                           </tbody>
                        </table>
                        
                        <br><br>
                        
                        <table id="myTable_2" class="table css-serial w-100 table-responsive">
                           <thead>
                           <tr>
							<td colspan=12 class="text-center"><strong>Information of change in directors of the pension fund section2</strong></td>
							</tr>
                              <tr>
                                 <th>Sr.No</th>
                                 <th>Name</th>
                                 <th>Type_of_Director</th>
                                 <th>Chairperson Member</th>
                                 <th>Independent_Non-Independent</th>
                                 <th>Director Identification Number (DIN)</th>
                                 <th>Date Of Appointment</th>
                                 <th>Highest Qualification</th>
                                  <th>Total professional Experience In Finance/Financial Services (in number of years)</th>
                                  <th>Resume/Biodata</th>
                                  <th>Form MBP-1 Of The Director (Details of interests of the Director in other companies)</th>
                                   
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>
                                 	1
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name00" name="<portlet:namespace />name_ii">
                                 </td>
                                 <td>
                                    <input type="text" class="typeofdirector" id="typeofdirector" name="<portlet:namespace />typeofdirector_ii">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="chairperson--member" name="<portlet:namespace />Chairpersonmember_ii">
                                 </td>
                                  <td>
                                    <input type="text" class="Independent_nonindependent" id="Independent_nonindependent" name="<portlet:namespace />Independent_Nonindependent_ii">
                                 </td>
                                 <td>
                                    <input type="text" class="directoridentificationnumber" id="directoridentificationnumber" name="<portlet:namespace />directorIdentificationNumber_ii">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointment" id="dateofappointment" name="<portlet:namespace />dateofappointment_ii">
                                 </td>
                                 <td>
                                 	<input type="text" class="highestqualification" id="highestqualification" name="<portlet:namespace />highestQualification_ii" >
                                 </td>
                                 <td>
                                    <input type="number" class="totalprofessionalexperience" id="totalprofessionalexperience" name="<portlet:namespace />totalprofessionalexperience_ii">
                                 </td>
                                 <td>
                                    <input type="file" class="resume_biodata" id="resume_biodata" name="resumeBiodata_ii" accept=".pdf">
                                 </td>
                                 <td>
                                    <input type="text" class="formMBP1ofthedirector" id="formMBP1ofthedirector" name="<portlet:namespace />formMBP1ofthedirector_ii">
                                 </td>
                              </tr>
                           </tbody>
                        </table>
                         
                         <br><br>
                         
                         <table id="myTable_2" class="table css-serial w-100 table-responsive">
                           <thead>
                           <tr>
							<td colspan=5 class="text-center"><strong>Section 2a and DB table</strong></td>
							</tr>
                              <tr>
                                 <th>Sr.No</th>
                                 <th>Names Of The Companies/Bodies Corporate/Firms/Association Of Individuals</th>
                                 <th>Nature Of Interest</th>
                                 <th>Shareholding</th>
                                  <th>Date On Which Interest Or Concern Arose</th>
                                   
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>
                                 	1
                                 </td>
                                 <td>
                                    <input type="text" class="namesofthecompanies" id="namesofthecompanies" name="<portlet:namespace />namesofthecompanies0_iia[]">
                                 </td>
                                 <td>
                                    <input type="text" class="natureofinterest" id="natureofinterest" name="<portlet:namespace />natureofinterest0_iia[]">
                                 </td>
                                 <td>
                                    <input type="text" class="shareholding" id="shareholding" name="<portlet:namespace />shareholding0_iia[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateonwhichinterestorconcernarose" id="dateonwhichinterestorconcernarose" name="<portlet:namespace />dateoninterestorconcernarose0_iia[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	2
                                 </td>
                                 <td>
                                    <input type="text" class="namesofthecompanies" id="namesofthecompanies1" name="<portlet:namespace />namesofthecompanies1_iia[]">
                                 </td>
                                 <td>
                                    <input type="text" class="natureofinterest" id="natureofinterest1" name="<portlet:namespace />natureofinterest1_iia[]">
                                 </td>
                                 <td>
                                    <input type="text" class="shareholding" id="shareholding1" name="<portlet:namespace />shareholding1_iia[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateonwhichinterestorconcernarose" id="dateonwhichinterestorconcernarose1" name="<portlet:namespace />dateoninterestorconcernarose1_iia[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	3
                                 </td>
                                 <td>
                                    <input type="text" class="namesofthecompanies" id="namesofthecompanies2" name="<portlet:namespace />namesofthecompanies2_iia[]">
                                 </td>
                                 <td>
                                    <input type="text" class="natureofinterest" id="natureofinterest2" name="<portlet:namespace />natureofinterest2_iia[]">
                                 </td>
                                 <td>
                                    <input type="text" class="shareholding" id="shareholding2" name="<portlet:namespace />shareholding2_iia[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateonwhichinterestorconcernarose" id="dateonwhichinterestorconcernarose2" name="<portlet:namespace />dateoninterestorconcernarose2_iia[]">
                                 </td>
                              </tr>
                           </tbody>
                        </table>
                        
                    <br><br>
                          
                    <table id="myTable_2a" class="table css-serial w-100 table-responsive">
                           <thead>
                           <tr>
							<td colspan=4 class="text-center"><strong>Investment and risk management committee composition section2a</strong></td>
							</tr>
                              <tr>
                                 <th>Sr.No</th>
                                 <th>Type</th>
                                 <th>Status</th>
                                 <th> </th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>
                                 	1
                                 </td>
                                 <td>
                                 Investment Committee Composition
                                 </td>
                                 <td>
                                    <input type="radio" class="status" id="status2b" name="<portlet:namespace />iccstatusi"checked>change
                                 </td>
                                 <td>
                                    <input type="radio" class="status" id="status2bb" name="<portlet:namespace />iccstatusi">nochange
                                 </td>
                      
                              </tr>
                              <tr>
                                 <td>
                                 	2
                                 </td>
                                 <td>
                                    Risk Management Committee Composition
                                 </td>
                                 <td>
                                    <input type="radio" class="status" id="status2c" name="<portlet:namespace />rmccstatusi"checked>Change
                                 </td>
                                 <td>
                                    <input type="radio" class="status" id="status2cc" name="<portlet:namespace />rmccstatusi">NoChange
                                 </td>
                      
                              </tr>
                           </tbody>
                        </table>
                        
                    <br><br>
                        
                    <table id="myTable_2b" class="table css-serial w-100 table-responsive">
                           <thead>
                           	<tr>
								<td colspan=5 class="text-center"><strong>Composition of investment committee section 2b</strong></td>
							</tr>
                            <tr>
                               <th>Sr.No</th>
                               <th>Designation</th>
                               <th>Name</th>
                               <th>Chairperson/Member</th>
                               <th>Date Of Appointment In The Committee</th>
                            </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>
                                 	1
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation9" name="<portlet:namespace />designation1_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name9" name="<portlet:namespace />name1_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="Chairperson/member" name="<portlet:namespace />chairpersonmember1_iib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee_iib" name="<portlet:namespace />dateofappointmentinthecommittee1_iib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	2
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation/2" name="<portlet:namespace />designation2_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name/2" name="<portlet:namespace />name2_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="Chairperson/member2" name="<portlet:namespace />chairpersonmember2_iib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee2_iib" name="<portlet:namespace />dateofappointmentinthecommittee2_iib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	3
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation/3" name="<portlet:namespace />designation3_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name/3" name="<portlet:namespace />name3_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="Chairperson/member3" name="<portlet:namespace />chairpersonmember3_iib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee3_iib" name="<portlet:namespace />dateofappointmentinthecommittee3_iib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	4
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation/4" name="<portlet:namespace />designation4_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name/4" name="<portlet:namespace />name4_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="Chairperson/member4" name="<portlet:namespace />chairpersonmember4_iib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee4_iib" name="<portlet:namespace />dateofappointmentinthecommittee4_iib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	5
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation/5" name="<portlet:namespace />designation5_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name/5" name="<portlet:namespace />name5_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="Chairperson/member5" name="<portlet:namespace />chairpersonmember5_iib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee5_iib" name="<portlet:namespace />dateofappointmentinthecommittee5_iib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	6
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation/6" name="<portlet:namespace />designation6_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name/6" name="<portlet:namespace />name6_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="Chairperson/member6" name="<portlet:namespace />chairpersonmember6_iib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee6_iib" name="<portlet:namespace />dateofappointmentinthecommittee6_iib[]">
                                 </td>
                              </tr>
                              <tr>
                                 <td>
                                 	7
                                 </td>
                                 <td>
                                    <input type="text" class="designation" id="designation/7" name="<portlet:namespace />designation7_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="name" id="name/7" name="<portlet:namespace />name7_iib[]">
                                 </td>
                                 <td>
                                    <input type="text" class="chairperson_member" id="Chairperson/member7" name="<portlet:namespace />chairpersonmember7_iib[]">
                                 </td>
                                 <td>
                                    <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee7_iib" name="<portlet:namespace />dateofappointmentinthecommittee7_iib[]">
                                 </td>
                              </tr>
                           </tbody>
                        </table>
                        
                    <br><br>
                         
                    <table id="myTable_2c" class="table css-serial w-100 table-responsive">
                          <thead>
	                          <tr>
								<td colspan=5 class="text-center"><strong>Composition of risk management committee section 2c</strong></td>
							  </tr>
	                          <tr>
	                             <th>Sr.No</th>
	                             <th>Designation</th>
	                             <th>Name</th>
	                             <th>Chairperson/Member</th>
	                             <th>Date Of Appointment In The Committee</th>
	                          </tr>
                          </thead>
                          <tbody>
                             <tr>
                                <td>
                                	1
                                </td>
                                <td>
                                   <input type="text" class="designation" id="designation/" name="<portlet:namespace />designation0_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="name" id="name_iic" name="<portlet:namespace />name0_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="chairperson_member" id="chairpersonmember_iic" name="<portlet:namespace />chairpersonmember0_iic[]">
                                </td>
                                <td>
                                   <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee_iic" name="<portlet:namespace />dateofappointmentinthecommittee0_iic[]">
                                </td>
                             </tr>
                             <tr>
                                <td>
                                	2
                                </td>
                                <td>
                                   <input type="text" class="designation" id="designation/1" name="<portlet:namespace />designation1_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="name" id="name/1" name="<portlet:namespace />name1_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="chairperson_member" id="chairperson/member1" name="<portlet:namespace />chairpersonmember1_iic[]">
                                </td>
                                <td>
                                   <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee1_iic" name="<portlet:namespace />dateofappointmentinthecommittee1_iic[]">
                                </td>
                             </tr>
                             <tr>
                                <td>
                                	3
                                </td>
                                <td>
                                   <input type="text" class="designation" id="designation12" name="<portlet:namespace />designation2_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="name" id="name12" name="<portlet:namespace />name2_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="chairperson_member" id="chairperson/member2" name="<portlet:namespace />chairpersonmember2_iic[]">
                                </td>
                                <td>
                                   <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee2_iic" name="<portlet:namespace />dateofappointmentinthecommittee2_iic[]">
                                </td>
                             </tr>
                             <tr>
                                <td>
                                	4
                                </td>
                                <td>
                                   <input type="text" class="designation" id="designation13" name="<portlet:namespace />designation3_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="name" id="name13" name="<portlet:namespace />name3_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="chairperson_member" id="chairperson/member3" name="<portlet:namespace />chairpersonmember3_iic[]">
                                </td>
                                <td>
                                   <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee3_iic" name="<portlet:namespace />dateofappointmentinthecommittee3_iic[]">
                                </td>
                             </tr>
                             <tr>
                                <td>
                                	5
                                </td>
                                <td>
                                   <input type="text" class="designation" id="designation14" name="<portlet:namespace />designation4_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="name" id="name14" name="<portlet:namespace />name4_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="chairperson_member" id="chairperson/member4" name="<portlet:namespace />chairpersonmember4_iic[]">
                                </td>
                                <td>
                                   <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee4_iic" name="<portlet:namespace />dateofappointmentinthecommittee4_iic[]">
                                </td>
                             </tr>
                             <tr>
                                <td>
                                	6
                                </td>
                                <td>
                                   <input type="text" class="designation" id="designation15" name="<portlet:namespace />designation5_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="name" id="name15" name="<portlet:namespace />name5_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="chairperson_member" id="chairperson/member5" name="<portlet:namespace />chairpersonmember5_iic[]">
                                </td>
                                <td>
                                   <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee5_iic" name="<portlet:namespace />dateofappointmentinthecommittee5_iic[]">
                                </td>
                             </tr>
                             <tr>
                                <td>
                                	7
                                </td>
                                <td>
                                   <input type="text" class="designation" id="designation16" name="<portlet:namespace />designation6_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="name" id="name16" name="<portlet:namespace />name6_iic[]">
                                </td>
                                <td>
                                   <input type="text" class="chairperson_member" id="chairperson/member6" name="<portlet:namespace />chairpersonmember6_iic[]">
                                </td>
                                <td>
                                   <input type="date" class="dateofappointmentinthecommittee" id="dateofappointmentinthecommittee6_iic" name="<portlet:namespace />dateofappointmentinthecommittee6_iic[]">
                                </td>
                             </tr>
                          </tbody>
                       </table>
                    <br><br> 
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
                        
  <style>
	#myTable_1_length, #myTable_1_filter, #myTable_1_info, #myTable_1_paginate {
		display: none;
	}
	
	input.error {
		border-color: red;
	}
	
	select {
	    background-color: #E9F3FC;
	    color: #111111;
	    border-radius: 5px;
	    padding: 5px 20px;
	}
	.modal {
    display: none;
	}
	
	table.table thead th, .nps-body-main .nps-page-main.nps-statement-wrapper .table-cont table tbody input {
	    padding: 10px !important; 
	}
	
	.css-serial strong {
		color: #fff;
	}
	
	.table-cont {
	       overflow-x: hidden !important;
	}

	/* label.error {
		display: none !important;
	} */
</style>
	
<script type="text/javascript">
$(document).ready(function() {
	
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	if(dd<10){
	        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    }
    today = yyyy+'-'+mm+'-'+dd;
    $("input[type='date']").attr('max', today);
	
	 //$("#myTable_1b").hide();
	 $("#status1").click(function(){
	 	//$("#myTable_1b").show();
	 	$("#myTable_1b").removeClass("hide");
	 });

	 $("#status1a").click(function(){
	 	//$("#myTable_1b").hide();
		 $("#myTable_1b").addClass("hide");
	 });
	 
	 
	 //$("#myTable_1c").hide();
	 $("#status2").click(function(){
	 	//$("#myTable_1c").show();
		 $("#myTable_1c").removeClass("hide");
	 });

	 $("#status2a").click(function(){
	 	//$("#myTable_1c").hide();
	 	$("#myTable_1c").addClass("hide");
	 });
	 
	 
	 
	 
	// $("#myTable_2b").hide();
	 $("#status2b").click(function(){
	 	//$("#myTable_2b").show();
		 $("#myTable_2b").removeClass("hide");
	 });

	 $("#status2bb").click(function(){
	 	//$("#myTable_2b").hide();
		 $("#myTable_2b").addClass("hide");
	 });
	 
	 
	 //$("#myTable_2c").hide();
	 $("#status2c").click(function(){
	 	//$("#myTable_2c").show();
		 $("#myTable_2c").removeClass("hide"); 
	 });

	 $("#status2cc").click(function(){
	 	//$("#myTable_2c").hide();
		 $("#myTable_2c").addClass("hide");
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
	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-zA-Z\s]+$/i.test(value);
		}, "Please enter characters only");
	
	$("#manpower").validate({
	  	rules: {
	  		
	  		 "<portlet:namespace/>destination0_i[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>destination1_i[]": {
				lettersonly: true,
			    required: true
			},
		 	
			"<portlet:namespace/>name0_i[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>dateofappoinment0_i[]": {
			    required: true
			},
			"<portlet:namespace/>contactno0_i[]": {
			    required: true
			},
			"<portlet:namespace/>email0_i[]": {
			    required: true
			},
			"<portlet:namespace/>highestqualification0_i[]": {
			    required: true
			},
			"<portlet:namespace/>totalprofexp0_i[]": {
			    required: true
			},
			"<portlet:namespace/>deputation0_i[]": {
			    required: true
			},
			"<portlet:namespace/>position0_i[]": {
			    required: true
			},
			"<portlet:namespace/>dateOfBoardMeetngApprove0_i[]": {
			    required: true
			},
			"<portlet:namespace/>name0_ii[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>designation0_iic[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>designation1_iic[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>designation2_iic[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>designation3_iic[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>designation4_iic[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>designation5_iic[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>designation6_iic[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>name0_iic[]": {
				lettersonly: true,
			    required: true
			},
			"<portlet:namespace/>chairpersonmember0_iic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommitte_iic[]": {
			    required: true
			},
			"<portlet:namespace/>designation1_iib[]": {
			    required: true
			},
			"<portlet:namespace/>designation2_iib[]": {
			    required: true
			},
			"<portlet:namespace/>designation3_iib[]": {
			    required: true
			},
			"<portlet:namespace/>designation4_iib[]": {
			    required: true
			},
			"<portlet:namespace/>designation5_iib[]": {
			    required: true
			},
			"<portlet:namespace/>designation6_iib[]": {
			    required: true
			},
			"<portlet:namespace/>designation7_iib[]": {
			    required: true
			},
			"<portlet:namespace/>name1_iib[]": {
			    required: true
			},
			"<portlet:namespace/>chairpersonmember1_iib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommitte1_iib[]": {
			    required: true
			},
			"<portlet:namespace/>namesofthecompanies0_iia[]": {
			    required: true
			},
			"<portlet:namespace/>natureofinterest0_iia[]": {
			    required: true
			},
			"<portlet:namespace/>shareholding0_iia[]": {
			    required: true
			},
			"<portlet:namespace/>dateoninterestorconcernarose0_iia[]": {
			    required: true
			},
			"<portlet:namespace/>name_ii": {
			    required: true
			},
			"<portlet:namespace/>typeofdirector_ii": {
			    required: true
			},
			"<portlet:namespace/>Chairpersonmember_ii": {
			    required: true
			},
			
			"<portlet:namespace/>Independent_Nonindependent_ii": {
			    required: true
			},
			"<portlet:namespace/>directorIdentificationNumber_ii": {
			    required: true
			},
			"<portlet:namespace/>dateofappointment_ii": {
			    required: true
			},
			"<portlet:namespace/>highestQualification_ii": {
			    required: true
			},
			"<portlet:namespace/>totalprofessionalexperience_ii": {
			    required: true
			},
			"<portlet:namespace/>resumeBiodata_ii": {
			    required: true
			},
			"<portlet:namespace/>formMBP1ofthedirector_ii": {
			    required: true
			},
			"<portlet:namespace/>designation1_ic[]": {
			    required: true
			},
			"<portlet:namespace/>designation2_ic[]": {
			    required: true
			},
			"<portlet:namespace/>designation3_ic[]": {
			    required: true
			},
			"<portlet:namespace/>designation4_ic[]": {
			    required: true
			},
			"<portlet:namespace/>designation5_ic[]": {
			    required: true
			},
			"<portlet:namespace/>designation6_ic[]": {
			    required: true
			},
			"<portlet:namespace/>designation7_ic[]": {
			    required: true
			},
			"<portlet:namespace/>name1_ic[]": {
			    required: true
			},
			"<portlet:namespace/>name1_ic[]": {
			    required: true
			},
			"<portlet:namespace/>chairpersonmember1_ic[]": {
			    required: true
			},
			"<portlet:namespace/>chairpersonmember2_ic[]": {
			    required: true
			},
			"<portlet:namespace/>chairpersonmember3_ic[]": {
			    required: true
			},
			"<portlet:namespace/>chairpersonmember4_ic[]": {
			    required: true
			},
			"<portlet:namespace/>chairpersonmember5_ic[]": {
			    required: true
			},
			"<portlet:namespace/>chairpersonmember6_ic[]": {
			    required: true
			},
			"<portlet:namespace/>chairpersonmember7_ic[]": {
			    required: true
			},
			"<portlet:namespace/>dateOfBoardMeetngApprove_ic[]": {
			    required: true
			},
			"<portlet:namespace/>designation0_ib[]": {
			    required: true
			},
			"<portlet:namespace/>designation1_ib[]": {
			    required: true
			},
			"<portlet:namespace/>designation2_ib[]": {
			    required: true
			},
			"<portlet:namespace/>designation3_ib[]": {
			    required: true
			},
			"<portlet:namespace/>designation4_ib[]": {
			    required: true
			},
			"<portlet:namespace/>designation5_ib[]": {
			    required: true
			},
			"<portlet:namespace/>designation6_ib[]": {
			    required: true
			},
			"<portlet:namespace/>name_ib[]": {
			    required: true
			},
			"<portlet:namespace/>chairpersonmember0_ib[]": {
			    required: true
			},
			"<portlet:namespace/>dateOfBoardMeetngApprove0_ib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee0_ib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee1_ib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee2_ib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee3_ib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee4_ib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee5_ib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee6_ib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee1_ic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee2_ic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee3_ic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee4_ic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee5_ic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee6_ic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee7_ic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee6_iic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee5_iic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee4_iic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee3_iic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee2_iic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee1_iic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee0_iic[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee_iib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee1_iib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee2_iib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee3_iib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee4_iib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee5_iib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee6_iib[]": {
			    required: true
			},
			"<portlet:namespace/>dateofappointmentinthecommittee7_iib[]": {
			    required: true
			},
	  	}

	});
	
	
	 $('.destination').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                lettersonly: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            }
	        );
	    });
	 
	 $('.name').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
		
	 $('.dateOfAppoinment').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 
	 $('.contactno').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 
	 $('.email').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 
	 $('.highestQualification').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 
	 $('.totalProfExp').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.deputation').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.linkedinID').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.position').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.dateOfBoardMeetngApprove').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.biodata').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.type').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.status').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.chairperson_member').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.typeofdirector').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.directoridentificationnumber').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.dateofappointment').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.highestqualification').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.totalprofessionalexperience').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
	 $('.resume_biodata').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	 		});
	  $('.formMBP1ofthedirector').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	        });
	    $('.namesofthecompanies').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });
	    });
     $('.natureofinterest').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });	

     });
    $('.shareholding').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });	
    });
	  $('.dateonwhichinterestorconcernarose').each(function() {
	        $(this).rules("add", 
	            {
	                required: true,
	                messages: {
	                    required: "This field is required.",
	                }
	            });	
		
				
	    });
	 
	 
	 
	 
	 
	 	$("#manpower").on('submit', function(e) {
	 		e.preventDefault();
	 		if($("form.form").valid()){
	 			
	 			var formData = new FormData(document.getElementById("manpower"));
	 			
	 			var htmlWidth = $("#canvasD").width();
				var htmlHeight = $("#canvasD").height();
				var pdfWidth = htmlWidth + (15 * 2);
				var pdfHeight = (pdfWidth * 1.5) + (15 * 2);
				var doc = new jsPDF('p', 'pt', [pdfWidth, pdfHeight]);

				var pageCount = Math.ceil(htmlHeight / pdfHeight) - 1;

				html2canvas($("#canvasD")[0], {imageTimeout: 5000, allowTaint: true }).then(function(canvas) {
					canvas.getContext('2d');
	
					var image = canvas.toDataURL("image/jpeg", 1.0);
					doc.addImage(image, 'JPEG', 15, 15, htmlWidth, htmlHeight);
	
					for (var i = 1; i <= pageCount; i++) {
						doc.addPage(pdfWidth, pdfHeight);
						doc.addImage(image, 'JPEG', 15, -(pdfHeight * i)+15, htmlWidth, htmlHeight);
					}
	
					console.log("doc.output('bloburl') ::: ", doc.output('arraybuffer'));
	/* 				doc.save("split-to-multi-page.pdf");
				     window.open(doc.output('bloburl')); */
				     
					var file = new Blob([new Uint8Array(doc.output('arraybuffer'))], {type: 'application/pdf'});
				     
					console.log("file::: ", file);
					
					formData.append("Manpower_Form_1_File", file);
		 			
		 			$(".content").hide();
					$(".animaion").show();
					$.ajax({
			            type: "POST",
			            enctype: 'multipart/form-data',
			            processData: false,
			            contentType: false,
			            cache: false,
			            url: '${manpowerResourceURL}',
			            data: formData,
			            success: function(data) {
			            	$(".content").show();
			            	$(".animaion").hide();
			            	if(data == "Success") {
			            		$('#success_tic').modal('show');
			            		$("#manpower")[0].reset();
		  		             	$('#success_tic').on('hidden.bs.modal', function (e) {
		  		             		location.reload(); 
		  		           		});
			            	} else {
			            		console.log("Error Occured in ajax call");
			            		$('#output').html('An error occured while submitting the form. Please try again');
			            		$('#errorExcel').modal('show');
			            	}
			            	
			            },
			            error: function() {
			            	$(".animaion").hide();
		        			$('#output').html('An error occured while submitting the form. Please try again');
		        			$('#errorExcel').modal('show');
			            	console.log("Error Occured in ajax call");
			            },
			            complete: function(){
			            	$(".animaion").hide();
				        },
			
			        });
				});
		 	}else{
	    		toastr.error('Please fill the required field(s).');
	    		//$('#output').html("Please fill the required field.");	
	   		}
	 
	 });
	 
});// ready function end here
	 
		
	</script> 