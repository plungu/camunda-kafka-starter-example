package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;
import org.springframework.context.annotation.Profile;

@Profile("integration")
public class ServiceRequest {
    private long id;
    private String serviceId;
    private String serviceCategory;
    private String serviceDescription;
    private String serviceOwner;
    private String serviceOwnerMSID;
    private String sourcingManager;
    private String sourcingManagerMSID;
    private String acquiringDivision;
    private String buContractingService;
    private String leContractingServiceCode;
    private String additionalReviewer;
    private String additionalReviewerMSID;
    private String additionalReviewerNotes;
    private String sourcingComments;
    private String applicationName;
    private Integer eonId;
    private String estimatedAnnualSpend;
    private String serviceDetailsComments;
    private boolean started;
    private boolean approved;
    private boolean rejected;

    public ServiceRequest(){}

    public ServiceRequest(ServiceRequestEntity entity){
        this.id = entity.getId();
        this.serviceId = entity.getServiceId();
        this.serviceCategory = entity.getServiceCategory();
        this.acquiringDivision = entity.getAcquiringDivision();
        this.additionalReviewer = entity.getAdditionalReviewer();
        this.additionalReviewerNotes = entity.getAdditionalReviewerNotes();
        this.additionalReviewerMSID = entity.getAdditionalReviewerMSID();
        this.applicationName = entity.getApplicationName();
        this.buContractingService = entity.getBuContractingService();
        this.eonId = entity.getEonId();
        this.estimatedAnnualSpend = entity.getEstimatedAnnualSpend();
        this.leContractingServiceCode = entity.getLeContractingServiceCode();
        this.serviceDescription = entity.getServiceDescription();
        this.serviceDetailsComments = entity.getServiceDetailsComments();
        this.serviceOwnerMSID = entity.getServiceOwnerMSID();
        this.sourcingComments = entity.getSourcingComments();
        this.sourcingManager = entity.getSourcingManager();
        this.started = entity.getStarted();
        this.approved = entity.isApproved();
        this.rejected = entity.isRejected();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean getStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceOwner() {
        return serviceOwner;
    }

    public void setServiceOwner(String serviceOwner) {
        this.serviceOwner = serviceOwner;
    }

    public String getServiceOwnerMSID() {
        return serviceOwnerMSID;
    }

    public void setServiceOwnerMSID(String serviceOwnerMSID) {
        this.serviceOwnerMSID = serviceOwnerMSID;
    }

    public String getSourcingManager() {
        return sourcingManager;
    }

    public void setSourcingManager(String sourcingManager) {
        this.sourcingManager = sourcingManager;
    }

    public String getSourcingManagerMSID() {
        return sourcingManagerMSID;
    }

    public void setSourcingManagerMSID(String sourcingManagerMSID) {
        this.sourcingManagerMSID = sourcingManagerMSID;
    }

    public String getAcquiringDivision() {
        return acquiringDivision;
    }

    public void setAcquiringDivision(String acquiringDivision) {
        this.acquiringDivision = acquiringDivision;
    }

    public String getBuContractingService() {
        return buContractingService;
    }

    public void setBuContractingService(String buContractingService) {
        this.buContractingService = buContractingService;
    }

    public String getLeContractingServiceCode() {
        return leContractingServiceCode;
    }

    public void setLeContractingServiceCode(String leContractingServiceCode) {
        this.leContractingServiceCode = leContractingServiceCode;
    }

    public String getAdditionalReviewer() {
        return additionalReviewer;
    }

    public void setAdditionalReviewer(String additionalReviewer) {
        this.additionalReviewer = additionalReviewer;
    }

    public String getAdditionalReviewerMSID() {
        return additionalReviewerMSID;
    }

    public void setAdditionalReviewerMSID(String additionalReviewerMSID) {
        this.additionalReviewerMSID = additionalReviewerMSID;
    }

    public String getAdditionalReviewerNotes() {
        return additionalReviewerNotes;
    }

    public void setAdditionalReviewerNotes(String additionalReviewerNotes) {
        this.additionalReviewerNotes = additionalReviewerNotes;
    }

    public String getSourcingComments() {
        return sourcingComments;
    }

    public void setSourcingComments(String sourcingComments) {
        this.sourcingComments = sourcingComments;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Integer getEonId() {
        return eonId;
    }

    public void setEonId(Integer eonId) {
        this.eonId = eonId;
    }

    public String getEstimatedAnnualSpend() {
        return estimatedAnnualSpend;
    }

    public void setEstimatedAnnualSpend(String estimatedAnnualSpend) {
        this.estimatedAnnualSpend = estimatedAnnualSpend;
    }

    public String getServiceDetailsComments() {
        return serviceDetailsComments;
    }

    public void setServiceDetailsComments(String serviceDetailsComments) {
        this.serviceDetailsComments = serviceDetailsComments;
    }
}
