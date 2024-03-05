package com.indeval.dali.constans;

public enum Divisa {

	MXN(1),
	UDI(2),
	USD(3),
	EUR(4);
	
	int divisa;
	
	private Divisa(int divisa) {
		this.divisa=divisa;
	}

	public int getDivisa() {
		return divisa;
	}
	
}
