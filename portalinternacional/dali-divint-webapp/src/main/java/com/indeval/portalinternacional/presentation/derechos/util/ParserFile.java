package com.indeval.portalinternacional.presentation.derechos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.presentation.controller.derechos.FileTransBeneficiarioVO;

public class ParserFile {

	private static final Logger LOG = LoggerFactory.getLogger(ParserFile.class);
	private List<String> posiciciones;
	private Map<String, Integer> sizes;
	private static final int SIZE_OPERATION = 107;
	private static final String TV = "tv";
	private static final String EMISORA = "emisora";
	private static final String SERIE = "serie";
	private static final String CUPON = "cupon";
	private static final String TIPO = "tipo";
	private static final String SUB_TIPO = "subtipo";
	private static final String CANTIDAD_TITULOS = "cantidadTitulos";
	private static final String UOI = "uoi";
	private static final String FECHA_CORTE = "fechaCorte";
	private static final String CUENTA = "cuenta";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private static final String NEW_LINE = "\n";
	private static final String PATRON_ALFANUMERICO = "[A-Za-z_0-9Ññ~ ]*";
	private static final String PATRON_NUMERICO = "[0-9]*";
	private static final String ERROR_LONGITUD = "Longitud incorrecta del beneficiario (Deben de ser 107 posiciones en total)";

	public String parseBeneficiario(String operation, FileTransBeneficiarioVO obj) {
		Map<String, String> fields = null;
		String error = null;
		LOG.debug("## operation.length() == 107 :: " + operation.length());
		if (StringUtils.isBlank(operation) || operation.length() != SIZE_OPERATION) {
			LOG.debug("## parseBeneficiario Longitud invalida del beneficiario ");
			error = ERROR_LONGITUD;
		}

		if (StringUtils.isBlank(error)) {
			try {
				fields = getFields(operation);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("INCORRECTO : GetFields() :: " + e.getMessage() + " - " + e.getCause());
			}

			try {
				error = validFields(fields);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("INCORRECTO : validFields() :: " + e.getMessage() + " - " + e.getCause());
			}

			if (StringUtils.isBlank(error)) {
				obj = obj == null ? new FileTransBeneficiarioVO() : obj;
				obj.setLinea(operation);
				obj.setTv(fields.get(TV).trim());
				obj.setEmisora(fields.get(EMISORA).trim());
				obj.setSerie(fields.get(SERIE).trim());
				obj.setCupon(fields.get(CUPON).trim());
				obj.setTipoDerecho(Integer.valueOf(fields.get(TIPO).trim()));
				if (obj.getTipoDerecho().intValue() == Constantes.ID_DERECHO_DISTRIBUCION) {
					obj.setSubtipoDerecho(Integer.valueOf(fields.get(SUB_TIPO).trim()));
				}
				obj.setCantidadTitulos(Long.valueOf(fields.get(CANTIDAD_TITULOS).trim()));
				obj.setUoi(fields.get(UOI).trim());
				obj.setCuenta(fields.get(CUENTA).trim());
				try {
					obj.setFechaCorteStr(fields.get(FECHA_CORTE));
					obj.setFechaCorte(DATE_FORMAT.parse(fields.get(FECHA_CORTE)));
				} catch (ParseException e) {
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
			}

		}
		return error;
	}

	private List<String> getPositions() {

		if (posiciciones == null) {
			posiciciones = new ArrayList<String>();
			posiciciones.add(TV);
			posiciciones.add(EMISORA);
			posiciciones.add(SERIE);
			posiciciones.add(CUPON);
			posiciciones.add(TIPO);
			posiciciones.add(SUB_TIPO);
			posiciciones.add(CANTIDAD_TITULOS);
			posiciciones.add(UOI);
			posiciciones.add(FECHA_CORTE);
			posiciciones.add(CUENTA);
		}

		return posiciciones;
	}

	private Map<String, Integer> getSizes() {
		if (sizes == null) {
			sizes = new HashMap<String, Integer>();
			sizes.put(TV, Integer.valueOf(4));
			sizes.put(EMISORA, Integer.valueOf(7));
			sizes.put(SERIE, Integer.valueOf(6));
			sizes.put(CUPON, Integer.valueOf(4));
			sizes.put(TIPO, Integer.valueOf(2));
			sizes.put(SUB_TIPO, Integer.valueOf(2));
			sizes.put(CANTIDAD_TITULOS, Integer.valueOf(18));
			sizes.put(UOI, Integer.valueOf(50));
			sizes.put(FECHA_CORTE, Integer.valueOf(10));
			sizes.put(CUENTA, Integer.valueOf(4));
		}
		return sizes;
	}

	private Map<String, String> getFields(String operation) {
		Map<String, String> fields = null;
		String field = null;
		String valueField = null;
		int offset = 0;
		int fieldSize = 0;

		fields = new HashMap<String, String>();
		for (int i = 0; i < getPositions().size(); i++) {
			try {
				field = getPositions().get(i);
			} catch (Exception e) {
				LOG.info("\n INCORRECTO : GetPositions() \n");
				e.printStackTrace();
			}

			try {
				fieldSize = getSizes().get(field).intValue();
			} catch (Exception e) {
				LOG.info("\n INCORRECTO : GetSizes() \n");
				e.printStackTrace();
			}

			valueField = operation.substring(offset, (offset + fieldSize));
			fields.put(field, valueField);
			offset += fieldSize;

		}
		return fields;
	}

	private String validFields(Map<String, String> fields) {
		StringBuffer error = new StringBuffer();
		long monto = 0;

		// Validaciones para tv
		if (StringUtils.isBlank(fields.get(TV))) {
			error.append("El campo tv es requerido. Posicion 1 [max long 4]. ").append(NEW_LINE);
			return error.toString();
		} else if (!fields.get(TV).trim().matches(PATRON_ALFANUMERICO)) {
			error.append("El campo tv debe ser alfanumerico. ").append(NEW_LINE);
			return error.toString();
		}

		// Validaciones para emisora
		if (StringUtils.isBlank(fields.get(EMISORA))) {
			error.append("El campo de emisora es requerido. Posicion 5 [max long 7]. ").append(NEW_LINE);
			return error.toString();
		}

		// Validaciones serie
		if (StringUtils.isBlank(fields.get(SERIE))) {
			error.append("El campo de serie es requerido. Posicion 12 [max long 6]. ").append(NEW_LINE);
			return error.toString();
		}

		// Validaciones cupon
		if (StringUtils.isBlank(fields.get(CUPON))) {
			error.append("El campo de cupon es requerido. Posicion 18 [max long 4]. ").append(NEW_LINE);
			return error.toString();
		} else if (!fields.get(CUPON).trim().matches(PATRON_NUMERICO)) {
			error.append("El campo de cupon debe ser numerico").append(NEW_LINE);
			return error.toString();
		}

		// Validaciones tipo
		if (StringUtils.isBlank(fields.get(TIPO))) {
			error.append("El campo tipo es requerido. Posicion 22 [max long 2]. ").append(NEW_LINE);
			return error.toString();
		} else if (!fields.get(TIPO).trim().matches(PATRON_NUMERICO)) {
			error.append("El campo de tipo deben ser digitos[1 o 18]").append(NEW_LINE);
			return error.toString();
		}

		// Validaciones sub tipo
		try{
			Integer tipo = Integer.parseInt(fields.get(TIPO).trim());
			if (tipo == Constantes.ID_DERECHO_DISTRIBUCION) {
				if (StringUtils.isBlank(fields.get(SUB_TIPO).trim())) {
					error.append("El campo subtipo es requerido. Posicion 24 [max long 2]. ").append(NEW_LINE);
					return error.toString();
				} else if (!fields.get(SUB_TIPO).trim().matches(PATRON_NUMERICO)) {
					error.append("El campo de subtipo deben ser digitos del 1 al 7").append(NEW_LINE);
					return error.toString();
				}
			}
		} catch(Exception ex){
			ex.printStackTrace();
			LOG.error(ex.getMessage() + " - " + ex.getCause());
			error.append("El campo subtipo es requerido. Posicion 24 [max long 2]. Deben ser digitos del 1 al 7 ").append(NEW_LINE);
			return error.toString();
		}

		// Validaciones cantidad de titulos
		if (StringUtils.isBlank(fields.get(CANTIDAD_TITULOS))) {
			error.append("El campo de cantidad de titulos es requerido. Posicion 26 [max long 18]. ").append(NEW_LINE);
			return error.toString();
		} else if (!fields.get(CANTIDAD_TITULOS).trim().matches(PATRON_NUMERICO)) {
			error.append("El campo de cantidad titulos deben ser digitos").append(NEW_LINE);
			return error.toString();
		} else {
			monto = Long.valueOf(fields.get(CANTIDAD_TITULOS).trim());
			if (monto < 0) {
				error.append("El campo de cantidad titulos debe ser mayor a 0").append(NEW_LINE);
				return error.toString();
			}
		}

		// Validaciones uoi
		if (StringUtils.isBlank(fields.get(UOI))) {
			error.append("El campo de UOI es requerido. Posicion 44 [max long 50]. ").append(NEW_LINE);
			return error.toString();
		}

		// Validaciones fecha de corte
		if (StringUtils.isBlank(fields.get(FECHA_CORTE))) {
			error.append("El campo de fecha de corte es requerido. Posicion 94 [max long 10]. ").append(NEW_LINE);
			return error.toString();
		} else {
			try {
				DATE_FORMAT.parse(fields.get(FECHA_CORTE));
			} catch (ParseException e) {
				error.append("El campo de fecha de corte debe tener el formato [dd/MM/yyyy]").append(NEW_LINE);
				return error.toString();
			}
		}

		// validaciones cuenta
		if (StringUtils.isBlank(fields.get(CUENTA).trim())) {
			error.append("El campo de cuenta es requerido. Posicion 104 [max long 4]. ").append(NEW_LINE);
			return error.toString();
		} else if (!fields.get(CUENTA).trim().matches(PATRON_NUMERICO)) {
			error.append("El campo de cuenta deben ser digitos").append(NEW_LINE);
			return error.toString();
		}

		return error.length() == 0 ? null : error.toString();
	}
}
