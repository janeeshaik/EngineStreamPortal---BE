package com.ste.enginestreamportal.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Batch")
public class Batch extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "BIZUnit")
	private String bizUnit;

	@Column(name = "BIZUnitDescription")
	private String bizUnitDescription;
	
	@Column(name = "BatchNo")
	private Long batchNo;
	
	@Column(name = "QIBatchNo")
	private Long qiBatchNo;
	
	@Column(name = "StorageLocation")
	private String storageLocation;
	
	@Column(name = "StorageBin")
	private String storageBin;
	
	@Column(name = "LastReceiptDate")
	private Date lastReceiptDate;
	
	@Column(name = "AgeByDay")
	private Long ageByDay;
	
	@Column(name = "AgeByMonth")
	private Long ageByMonth;
	
	@Column(name = "Quantity")
	private Long quantity;
	
	@Column(name = "UOM")
	private String uom;
	
	@Column(name = "VendorName")
	private String vendorName;
	
	@Column(name = "ReasonPurchaseDescription")
	private String reasonPurchaseDescription;
	
	@Column(name = "ValueInUSD")
	private BigDecimal valueInUSD;
	
	@Column(name = "NBVInUSD")
	private BigDecimal nbvInUSD;
	
	@Column(name = "TotalNBVUSD")
	private BigDecimal totalNBVUSD;
	
	@Column(name = "TSN")
	private Long tsn;
	
	@Column(name = "CSN")
	private Long csn;
	
	@Column(name = "Condition")
	private String condition;
	
	@Column(name = "MaterialSerialNumber")
	private String materialSerialNumber;
	
	@Column(name = "MaterialCharacteristic")
	private String materialCharacteristic;

	@ManyToOne
	@JoinColumn(name = "Material", referencedColumnName = "id")
	@JsonBackReference
	private Material material;

	@Column(name = "SurplusFlag")
	private boolean surplusFlag;
	
	@OneToMany(mappedBy = "batch")
	private List<SurplusFlagHistory> surplusFlagHistoryList;
	
	public String getBizUnit() {
		return bizUnit;
	}

	public void setBizUnit(String bizUnit) {
		this.bizUnit = bizUnit;
	}

	public String getBizUnitDescription() {
		return bizUnitDescription;
	}

	public void setBizUnitDescription(String bizUnitDescription) {
		this.bizUnitDescription = bizUnitDescription;
	}

	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	public Long getQiBatchNo() {
		return qiBatchNo;
	}

	public void setQiBatchNo(Long qiBatchNo) {
		this.qiBatchNo = qiBatchNo;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public String getStorageBin() {
		return storageBin;
	}

	public void setStorageBin(String storageBin) {
		this.storageBin = storageBin;
	}

	public Date getLastReceiptDate() {
		return lastReceiptDate;
	}

	public void setLastReceiptDate(Date lastReceiptDate) {
		this.lastReceiptDate = lastReceiptDate;
	}

	public Long getAgeByDay() {
		return ageByDay;
	}

	public void setAgeByDay(Long ageByDay) {
		this.ageByDay = ageByDay;
	}

	public Long getAgeByMonth() {
		return ageByMonth;
	}

	public void setAgeByMonth(Long ageByMonth) {
		this.ageByMonth = ageByMonth;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getReasonPurchaseDescription() {
		return reasonPurchaseDescription;
	}

	public void setReasonPurchaseDescription(String reasonPurchaseDescription) {
		this.reasonPurchaseDescription = reasonPurchaseDescription;
	}

	public BigDecimal getValueInUSD() {
		return valueInUSD;
	}

	public void setValueInUSD(BigDecimal valueInUSD) {
		this.valueInUSD = valueInUSD;
	}

	public BigDecimal getNbvInUSD() {
		return nbvInUSD;
	}

	public void setNbvInUSD(BigDecimal nbvInUSD) {
		this.nbvInUSD = nbvInUSD;
	}

	public BigDecimal getTotalNBVUSD() {
		return totalNBVUSD;
	}

	public void setTotalNBVUSD(BigDecimal totalNBVUSD) {
		this.totalNBVUSD = totalNBVUSD;
	}

	public Long getTsn() {
		return tsn;
	}

	public void setTsn(Long tsn) {
		this.tsn = tsn;
	}

	public Long getCsn() {
		return csn;
	}

	public void setCsn(Long csn) {
		this.csn = csn;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getMaterialSerialNumber() {
		return materialSerialNumber;
	}

	public void setMaterialSerialNumber(String materialSerialNumber) {
		this.materialSerialNumber = materialSerialNumber;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public String getMaterialCharacteristic() {
		return materialCharacteristic;
	}

	public void setMaterialCharacteristic(String materialCharacteristic) {
		this.materialCharacteristic = materialCharacteristic;
	}

	public boolean isSurplusFlag() {
		return surplusFlag;
	}

	public void setSurplusFlag(boolean surplusFlag) {
		this.surplusFlag = surplusFlag;
	}

	public List<SurplusFlagHistory> getSurplusFlagHistoryList() {
		return surplusFlagHistoryList;
	}

	public void setSurplusFlagHistoryList(List<SurplusFlagHistory> surplusFlagHistoryList) {
		this.surplusFlagHistoryList = surplusFlagHistoryList;
	}
}
