package com.indeval.portalinternacional.middleware.services.divisioninternacional.beneficiarios;

import java.sql.Date;
import java.util.List;

public interface ExportacionBeneficiariosService {
	
	/**
	 * Metodo que obtiene una lista de listas de cadenas con los datos de los beneficiarios separados por comas.
	 * @param formato Formato del beneficiario
	 * @param fechaInicio Fecha inicio de formato
	 * @param fechaFin Fecha fin de formato
	 * @return lista de listas de cadenas con los datos de los beneficiarios separados por comas.
	 */
	List<String> obtenerRegistrosLayauts(String formato, Date fechaInicio, Date fechaFin);
	
}
