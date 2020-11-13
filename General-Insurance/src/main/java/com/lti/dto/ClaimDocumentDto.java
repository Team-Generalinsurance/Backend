package com.lti.dto;

import javax.mail.Multipart;

import org.springframework.web.multipart.MultipartFile;

public class ClaimDocumentDto {
	long policyNo;
    private MultipartFile documentImage;
	
    
    public long getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(long policyNo) {
		this.policyNo = policyNo;
	}
	public MultipartFile getDocumentImage() {
		return documentImage;
	}
	public void setDocumentImage(MultipartFile documentImage) {
		this.documentImage = documentImage;
	}
	
	
}
