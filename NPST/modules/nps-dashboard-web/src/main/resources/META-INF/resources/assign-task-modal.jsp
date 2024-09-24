<%@ include file="init.jsp"%>

<div class="modal fade" id="assignTaskModal" tabindex="-1"
	aria-labelledby="success_ticLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<h4>Assign task</h4>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div class="alert alert-danger hide" id="error-msg"><p>Something went wrong. Please try again.</p></div>
				<div class="form-group">
					<label class="form-label">Select User</label>
					<select name="assignedUser" class="form-control assignedUser">
						<c:forEach items="${assignees }" var="assigne">
							<option value="${assigne.userId }">${assigne.fullName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label class="form-label">Comment</label>
					<textarea rows="5" cols="6" placeholder="Please add your comments here." class="form-control comment" name="comment"></textarea>
				</div>
			</div>
			<div class="modal-footer text-right" style="display: block;">
				<button type="button" class="btn btn-primary" id="save-btn">Save</button>
			</div>
		</div>
	</div>
</div>

<script>
$("#save-btn").click(function(){
	$("#error-msg").addClass("hide");
	const workflowContext = ${workflowContext};
	$.ajax({
		url:"${assignTaskToUserResourceCommandURL}",
		data: {
			<portlet:namespace/>workflowTaskId: ${workflowTaskId},
			<portlet:namespace/>workflowContext: JSON.stringify(workflowContext),
			<portlet:namespace/>comment: $(".comment").val(),
			<portlet:namespace/>assignedUser: $(".assignedUser").val(),
		},
		type: "post",
		dataType : "json",
		success: function(data){
			console.log('success function called',data);
			if(data.status){
				$("#assignTaskModal").modal('hide');
				$(".head").html("<h6>Task assigned successfully.</h6>");
				$('#success_tic').modal('show');
				
				window.setTimeout( function() {
					window.location.reload(true);
					}, 1000);
				
			}else{
				$("#error-msg").removeClass("hide");
			}
			
		},
		 error: function (xhr, ajaxOptions, thrownError) {
		        console.log('xhr status::',xhr.status);
		        console.log('thrown error::',thrownError);
		      }
	});	
});

</script>