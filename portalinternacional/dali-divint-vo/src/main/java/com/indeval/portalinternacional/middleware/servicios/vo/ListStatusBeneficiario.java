package com.indeval.portalinternacional.middleware.servicios.vo;

public enum ListStatusBeneficiario {
	
	REGISTRADO("REGISTRADO"),
	REGISTRADO_CORTO("REG"),
	AUTORIZADO("AUTORIZADO"),
	AUTORIZADO_CORTO("AUT"),
	VENCIDO("VENCIDO"),
	VENCIDO_CORTO("VEN"),
	ACTUALIZADO("ACTUALIZADO"),
	ACTUALIZADO_CORTO("ACT"),
	CANCELADO("CANCELADO"),
	CANCELADO_CORTO("CAN"),
	ELIMINADO("ELIMINADO"),
	ELIMINADO_CORTO("ELI"),
	PRE_AUTORIZADO("PRE AUTORIZADO"),
	PRE_AUTORIZADO_CORTO("PAT"),
	NONE("NONE");


    private final String statusBeneficiario;

    private ListStatusBeneficiario(final String statusBeneficiario) {
        this.statusBeneficiario = statusBeneficiario;
    }

    public String getStatusBeneficiario() {
        return this.statusBeneficiario;
    }

    public static ListStatusBeneficiario obtenerInstancia(final String statusBeneficiario) {
        for (ListStatusBeneficiario sb : ListStatusBeneficiario.values()) {
            if (sb.getStatusBeneficiario().trim().equalsIgnoreCase(statusBeneficiario.trim())) {
                return sb;
            }
        }
        return ListStatusBeneficiario.NONE;
    }

}
