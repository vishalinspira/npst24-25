<%@ include file="/init.jsp" %>
<portlet:resourceURL id="reportStatus" var="reportStatusResourceURL" />
<p>
	<b><liferay-ui:message key="reportstatus.caption"/></b>
</p>
<input type="submit" id="btn-submit" value="Submit">

<script type="text/javascript">
		$(document).ready(function() {
			toastr.options = {
					  "debug": false,
					  "positionClass": "toast-bottom-right",
					  "onclick": null,
					  "fadeIn": 300,
					  "fadeOut": 1000,
					  "timeOut": 9000,
					  "extendedTimeOut": 1000
					}
		});
		
		$(function(){
		    $('#btn-submit').on('click', function(){

		        var fd = new FormData();

		        $.ajax({
		            url: '<%=reportStatusResourceURL %>',  
		            type: 'POST',
		            data: fd,
		            success:function(data){
					alert(data);
		            },
		            error: function() {
		           		console.log("Error Occured in ajax call");
		            },
		            complete: function(){
						console.log("Complete");
			        },
		            cache: false,
		            contentType: false,
		            processData: false
		        });
		    });
		});
		</script>
