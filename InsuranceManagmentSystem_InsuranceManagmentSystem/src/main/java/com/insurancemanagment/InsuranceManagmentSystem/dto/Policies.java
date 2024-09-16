package com.insurancemanagment.InsuranceManagmentSystem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Policies {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int policyId;
    private String policyName;
    private String policyType;
    private double premiumAmount;
	@ManyToOne
	@JoinColumn(name="admin_id")
	@JsonIgnore
	private Admin admin;
	@OneToMany(mappedBy = "policies")
	private List<PolicyBooking> policybooking;
	
	
	
	public Policies() {
		
	}
	
	public Policies(int policyId, String policyName, String policyType, double premiumAmount, String policyHolderName) {
		super();
		this.policyId = policyId;
		this.policyName = policyName;
		this.policyType = policyType;
		this.premiumAmount = premiumAmount;
	}
	public int getPolicyId() {
		return policyId;
	}
	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public double getPremiumAmount() {
		return premiumAmount;
	}
	public void setPremiumAmount(double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public List<PolicyBooking> getPolicybooking() {
		return policybooking;
	}
	public void setPolicybooking(List<PolicyBooking> policybooking) {
		this.policybooking = policybooking;
	}
	
	
	
	
	
}


