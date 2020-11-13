package com.lti.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "claim_tbl")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","policy"})
public class Claim {
	@Id
	@GeneratedValue
	long claimNo;

	double amount;
	String status;
	String reason;
	LocalDate claimDate;
	@NaturalId
	String accountNo;
	String documentImageUrl;
	
	String remark;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "policyNo")
	Policy policy;
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(long claimNo) {
		this.claimNo = claimNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public LocalDate getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(LocalDate claimDate) {
		this.claimDate = claimDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getDocumentImageUrl() {
		return documentImageUrl;
	}

	public void setDocumentImageUrl(String documentImageUrl) {
		this.documentImageUrl = documentImageUrl;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

}
