package com.camunda.poc.starter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;

@Profile("triage")
@Entity(name="policy")
public class PolicyEntity {

    private static final long serialVersionUID = -209110667815280386L;

    private @Version
    @JsonIgnore
    Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="co_policy_no", nullable=true)
    private String coPolicyNo;

    @Column(name="co_policy_term", nullable=true)
    private String coPolicyTerm;

    @Column(name="co_insured_nm", nullable=true)
    private String coInsuredNm;

    @Column(name="co_quote_no", nullable=true)
    private String coQuoteNo;

    @Column(name="co_ins_co_nm", nullable=true)
    private String coInsCoNm;

    @Column(name="credit_check_started", nullable=true)
    private boolean creditCheckStarted;

    public long getId() {
        return id;
    }

    public String getCoPolicyNo() {
        return coPolicyNo;
    }

    public void setCoPolicyNo(String coPolicyNo) {
        this.coPolicyNo = coPolicyNo;
    }

    public String getCoPolicyTerm() {
        return coPolicyTerm;
    }

    public void setCoPolicyTerm(String coPolicyTerm) {
        this.coPolicyTerm = coPolicyTerm;
    }

    public String getCoInsuredNm() {
        return coInsuredNm;
    }

    public void setCoInsuredNm(String coInsuredNm) {
        this.coInsuredNm = coInsuredNm;
    }

    public String getCoQuoteNo() {
        return coQuoteNo;
    }

    public void setCoQuoteNo(String coQuoteNo) {
        this.coQuoteNo = coQuoteNo;
    }

    public String getCoInsCoNm() {
        return coInsCoNm;
    }

    public void setCoInsCoNm(String coInsCoNm) {
        this.coInsCoNm = coInsCoNm;
    }

    public boolean isCreditCheckStarted() {
        return creditCheckStarted;
    }

    public void setCreditCheckStarted(boolean creditCheckStarted) {
        this.creditCheckStarted = creditCheckStarted;
    }

}
