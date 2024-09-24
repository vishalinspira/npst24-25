package nps.email.service.model;

import nps.email.service.constants.ErrorAPIKeys;

public class ErrorAPIDetails {
	String apiEndPoint;
	String apiNodeTree;
	
	public ErrorAPIDetails() {
		
	}
	
	public ErrorAPIDetails(String apiEndPoint, String apiNodeTree) {
		super();
		this.apiEndPoint = ErrorAPIKeys.ERROR_API_DOMAIN+apiEndPoint;
		this.apiNodeTree = apiNodeTree;
	}
	public String getApiEndPoint() {
		return apiEndPoint;
	}
	public void setApiEndPoint(String apiEndPoint) {
		this.apiEndPoint = ErrorAPIKeys.ERROR_API_DOMAIN+apiEndPoint;
	}
	public String getApiNodeTree() {
		return apiNodeTree;
	}
	public void setApiNodeTree(String apiNodeTree) {
		this.apiNodeTree = apiNodeTree;
	}
	
	@Override
	public String toString() {
		return "{\"apiEndPoint\":\""+apiEndPoint+"\", \"apiNodeTree\":\""+apiNodeTree+"\"}";
	}
}
