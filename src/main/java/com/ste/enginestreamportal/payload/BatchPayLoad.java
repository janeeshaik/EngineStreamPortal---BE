package com.ste.enginestreamportal.payload;

import com.ste.enginestreamportal.model.Batch;

public class BatchPayLoad extends Batch{
	private String materialDescription;
	
	public String getMaterialDescription() {
		return materialDescription;
	}
	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}
	
	
}
