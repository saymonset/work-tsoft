package com.indeval.dali.constans;

public enum TipoEmision {

	DEUDA_ESTRUCTURADA_DE_ENTIDADES_FINANCIERAS("99"),
	CERTIFICADOS_BURSATILES_BANCARIOS("94"),
	TITULOS_OPCIONALES_SOBRE_ACCIONES("WA");
	
	String cve;
	
	private TipoEmision(String cve) {
		this.cve=cve;
	}
	
	public String getCve() {
		return cve;
	}
}
