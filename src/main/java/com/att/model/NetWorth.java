package com.att.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NetWorth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer netWorthID;
	private Float value;

	public Integer getNetWorthID() {
		return netWorthID;
	}

	public Float getValue() {
		return value;
	}

	public void setNetWorthID(Integer netWorthID) {
		this.netWorthID = netWorthID;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public void updateFrom(NetWorth networth) {
		if (networth.getValue() != null) {
			value = networth.getValue();
		}
	}
}
