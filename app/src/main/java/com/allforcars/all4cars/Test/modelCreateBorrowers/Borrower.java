
package com.allforcars.all4cars.Test.modelCreateBorrowers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Borrower {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("account_application_id")
    @Expose
    private Integer accountApplicationId;
    @SerializedName("loan_product_id")
    @Expose
    private Object loanProductId;
    @SerializedName("m_no")
    @Expose
    private String mNo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("aadhar_no")
    @Expose
    private Integer aadharNo;
    @SerializedName("pan_no")
    @Expose
    private String panNo;
    @SerializedName("landholding")
    @Expose
    private String landholding;
    @SerializedName("crops_grown")
    @Expose
    private String cropsGrown;
    @SerializedName("credit_limit")
    @Expose
    private Object creditLimit;
    @SerializedName("borrower_status")
    @Expose
    private String borrowerStatus;
    @SerializedName("loan_amount")
    @Expose
    private Object loanAmount;
    @SerializedName("existing_credit_facility")
    @Expose
    private String existingCreditFacility;
    @SerializedName("total_existing_loan_outstanding_amount")
    @Expose
    private Integer totalExistingLoanOutstandingAmount;
    @SerializedName("total_current_emi_amount")
    @Expose
    private Integer totalCurrentEmiAmount;
    @SerializedName("total_monthly_expenses")
    @Expose
    private Integer totalMonthlyExpenses;
    @SerializedName("additional_income_source")
    @Expose
    private String additionalIncomeSource;
    @SerializedName("additional_monthly_income")
    @Expose
    private Integer additionalMonthlyIncome;
    @SerializedName("farmer_image")
    @Expose
    private String farmerImage;
    @SerializedName("e_stamp_status")
    @Expose
    private String eStampStatus;
    @SerializedName("e_sign_status")
    @Expose
    private String eSignStatus;
    @SerializedName("e_mandate_status")
    @Expose
    private String eMandateStatus;
    @SerializedName("pan_front")
    @Expose
    private Object panFront;
    @SerializedName("pan_back")
    @Expose
    private Object panBack;
    @SerializedName("aadhaar_front")
    @Expose
    private Object aadhaarFront;
    @SerializedName("aadhaar_back")
    @Expose
    private Object aadhaarBack;
    @SerializedName("others_front")
    @Expose
    private Object othersFront;
    @SerializedName("others_back")
    @Expose
    private Object othersBack;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccountApplicationId() {
        return accountApplicationId;
    }

    public void setAccountApplicationId(Integer accountApplicationId) {
        this.accountApplicationId = accountApplicationId;
    }

    public Object getLoanProductId() {
        return loanProductId;
    }

    public void setLoanProductId(Object loanProductId) {
        this.loanProductId = loanProductId;
    }

    public String getMNo() {
        return mNo;
    }

    public void setMNo(String mNo) {
        this.mNo = mNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Integer getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(Integer aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getLandholding() {
        return landholding;
    }

    public void setLandholding(String landholding) {
        this.landholding = landholding;
    }

    public String getCropsGrown() {
        return cropsGrown;
    }

    public void setCropsGrown(String cropsGrown) {
        this.cropsGrown = cropsGrown;
    }

    public Object getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Object creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getBorrowerStatus() {
        return borrowerStatus;
    }

    public void setBorrowerStatus(String borrowerStatus) {
        this.borrowerStatus = borrowerStatus;
    }

    public Object getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Object loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getExistingCreditFacility() {
        return existingCreditFacility;
    }

    public void setExistingCreditFacility(String existingCreditFacility) {
        this.existingCreditFacility = existingCreditFacility;
    }

    public Integer getTotalExistingLoanOutstandingAmount() {
        return totalExistingLoanOutstandingAmount;
    }

    public void setTotalExistingLoanOutstandingAmount(Integer totalExistingLoanOutstandingAmount) {
        this.totalExistingLoanOutstandingAmount = totalExistingLoanOutstandingAmount;
    }

    public Integer getTotalCurrentEmiAmount() {
        return totalCurrentEmiAmount;
    }

    public void setTotalCurrentEmiAmount(Integer totalCurrentEmiAmount) {
        this.totalCurrentEmiAmount = totalCurrentEmiAmount;
    }

    public Integer getTotalMonthlyExpenses() {
        return totalMonthlyExpenses;
    }

    public void setTotalMonthlyExpenses(Integer totalMonthlyExpenses) {
        this.totalMonthlyExpenses = totalMonthlyExpenses;
    }

    public String getAdditionalIncomeSource() {
        return additionalIncomeSource;
    }

    public void setAdditionalIncomeSource(String additionalIncomeSource) {
        this.additionalIncomeSource = additionalIncomeSource;
    }

    public Integer getAdditionalMonthlyIncome() {
        return additionalMonthlyIncome;
    }

    public void setAdditionalMonthlyIncome(Integer additionalMonthlyIncome) {
        this.additionalMonthlyIncome = additionalMonthlyIncome;
    }

    public String getFarmerImage() {
        return farmerImage;
    }

    public void setFarmerImage(String farmerImage) {
        this.farmerImage = farmerImage;
    }

    public String getEStampStatus() {
        return eStampStatus;
    }

    public void setEStampStatus(String eStampStatus) {
        this.eStampStatus = eStampStatus;
    }

    public String getESignStatus() {
        return eSignStatus;
    }

    public void setESignStatus(String eSignStatus) {
        this.eSignStatus = eSignStatus;
    }

    public String getEMandateStatus() {
        return eMandateStatus;
    }

    public void setEMandateStatus(String eMandateStatus) {
        this.eMandateStatus = eMandateStatus;
    }

    public Object getPanFront() {
        return panFront;
    }

    public void setPanFront(Object panFront) {
        this.panFront = panFront;
    }

    public Object getPanBack() {
        return panBack;
    }

    public void setPanBack(Object panBack) {
        this.panBack = panBack;
    }

    public Object getAadhaarFront() {
        return aadhaarFront;
    }

    public void setAadhaarFront(Object aadhaarFront) {
        this.aadhaarFront = aadhaarFront;
    }

    public Object getAadhaarBack() {
        return aadhaarBack;
    }

    public void setAadhaarBack(Object aadhaarBack) {
        this.aadhaarBack = aadhaarBack;
    }

    public Object getOthersFront() {
        return othersFront;
    }

    public void setOthersFront(Object othersFront) {
        this.othersFront = othersFront;
    }

    public Object getOthersBack() {
        return othersBack;
    }

    public void setOthersBack(Object othersBack) {
        this.othersBack = othersBack;
    }

}
