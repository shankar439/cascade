package com.HMPackage.baseResponse;

import lombok.Builder;

@Builder
public class BaseResponse<T> {
    @Builder.Default
    private String StatusCode="200";
    @Builder.Default
    private String StatusMessage="Status Success";
    private T Data;
    
    
	public String getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}
	public String getStatusMessage() {
		return StatusMessage;
	}
	public void setStatusMsg(String statusMessage) {
		StatusMessage = statusMessage;
	}
	public T getData() {
		return Data;
	}
	public void setData(T data) {
		Data = data;
	}
}
