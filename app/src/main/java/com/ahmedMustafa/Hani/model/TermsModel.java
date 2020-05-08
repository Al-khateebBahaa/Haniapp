package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("terms_of_use")
    @Expose
    private String termsOfUse;
    @SerializedName("privacy_policy")
    @Expose
    private String privacyPolicy;
    @SerializedName("delivery_policy_break")
    @Expose
    private String deliveryPolicyBreak;
    @SerializedName("ideas_rights")
    @Expose
    private String ideasRights;
    @SerializedName("payment")
    @Expose
    private String payment;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getDeliveryPolicyBreak() {
        return deliveryPolicyBreak;
    }

    public void setDeliveryPolicyBreak(String deliveryPolicyBreak) {
        this.deliveryPolicyBreak = deliveryPolicyBreak;
    }

    public String getIdeasRights() {
        return ideasRights;
    }

    public void setIdeasRights(String ideasRights) {
        this.ideasRights = ideasRights;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
