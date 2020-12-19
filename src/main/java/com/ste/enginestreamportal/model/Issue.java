package com.ste.enginestreamportal.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Issue",uniqueConstraints={@UniqueConstraint(columnNames={"IssueNumber"})})
public class Issue extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "State")
	private State state;
	
	@Column(name = "IssueNumber")
	private String issueNumber;
	
	@Column(name = "StartDate")
	private Date startDate;
	
    @Column(name = "EndDate")
    private Date endDate;
    
    @Column(name = "Description")
    private String description;
    
    @Column(name = "EngineSerialNumber")
    private String engineSerialNumber;
    
    @OneToOne
    @JoinColumn(name = "CustomerId")
    private User customerId;
    
    @OneToOne
    @JoinColumn(name = "IssueCategoryId")
    private IssueCategory issueCategoryId;
    
    @OneToOne
    @JoinColumn(name = "IssueSubCategoryId")
    private IssueSubCategory issueSubCategoryId;
    
    @Column(name = "Subject")
    private String subject;
    
    @OneToOne
    @JoinColumn(name = "RaisedBy")
    private User raisedBy;
    
    @OneToOne
    @JoinColumn(name = "RaisedFor")
    private User raisedFor;
    
    @Column(name = "RaisedDate")
    private Date raisedDate;
    
    @Column(name = "ResponseRequiredDate")
    private Date responseRequiredDate;
    
    @OneToOne
    @JoinColumn(name = "RaisedForDepartmentId")
    private Department raisedForDepartmentId;
    
    @Column(name = "IssueEndDate")
    private Date issueEndDate;
    
    @Column(name = "ExcusableDelay")
    private Integer excusableDelay;

    @OneToMany(mappedBy = "issueId")
    private List<Responses> responses;
    
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEngineSerialNumber() {
		return engineSerialNumber;
	}

	public void setEngineSerialNumber(String engineSerialNumber) {
		this.engineSerialNumber = engineSerialNumber;
	}

	public User getCustomerId() {
		return customerId;
	}

	public void setCustomerId(User customerId) {
		this.customerId = customerId;
	}

	public IssueCategory getIssueCategoryId() {
		return issueCategoryId;
	}

	public void setIssueCategoryId(IssueCategory issueCategoryId) {
		this.issueCategoryId = issueCategoryId;
	}

	public IssueSubCategory getIssueSubCategoryId() {
		return issueSubCategoryId;
	}

	public void setIssueSubCategoryId(IssueSubCategory issueSubCategoryId) {
		this.issueSubCategoryId = issueSubCategoryId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public User getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(User raisedBy) {
		this.raisedBy = raisedBy;
	}

	public User getRaisedFor() {
		return raisedFor;
	}

	public void setRaisedFor(User raisedFor) {
		this.raisedFor = raisedFor;
	}

	public Date getRaisedDate() {
		return raisedDate;
	}

	public void setRaisedDate(Date raisedDate) {
		this.raisedDate = raisedDate;
	}

	public Date getResponseRequiredDate() {
		return responseRequiredDate;
	}

	public void setResponseRequiredDate(Date responseRequiredDate) {
		this.responseRequiredDate = responseRequiredDate;
	}

	public Department getRaisedForDepartmentId() {
		return raisedForDepartmentId;
	}

	public void setRaisedForDepartmentId(Department raisedForDepartmentId) {
		this.raisedForDepartmentId = raisedForDepartmentId;
	}

	public Date getIssueEndDate() {
		return issueEndDate;
	}

	public void setIssueEndDate(Date issueEndDate) {
		this.issueEndDate = issueEndDate;
	}

	public Integer getExcusableDelay() {
		return excusableDelay;
	}

	public void setExcusableDelay(Integer excusableDelay) {
		this.excusableDelay = excusableDelay;
	}

	public List<Responses> getResponses() {
		return responses;
	}

	public void setResponses(List<Responses> responses) {
		this.responses = responses;
	}
}
