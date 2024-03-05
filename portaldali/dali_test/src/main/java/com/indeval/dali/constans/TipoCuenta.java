package com.indeval.dali.constans;

public enum TipoCuenta {

	PROP_NOM(1),
	TERC_NOM(2),
	EMI_NOM_PROP(3),
	EMI_NOM_TERC(4);
	
	int id_tipo_cuenta;
	
	private TipoCuenta(int id_tipo_cuenta) {
		this.id_tipo_cuenta=id_tipo_cuenta;
	}

	public int getId_tipo_cuenta() {
		return id_tipo_cuenta;
	}
}
