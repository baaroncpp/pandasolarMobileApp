package com.panda.solar.Model.constants;

public enum ApprovalTypes {
	
	EMPLOYEE(1),
	EXPENSE(2),
	AGENT(3),
	EQUIPMENT_DISPATCH(4),
	CAPEX(5),
	LEASE_SALE(6),
	OPEX(7);

	private final int index;
	ApprovalTypes(int index) {
		// TODO Auto-generated constructor stub
		this.index = index;
	}
	
	public int getValue() {
		return index;
	}

}
