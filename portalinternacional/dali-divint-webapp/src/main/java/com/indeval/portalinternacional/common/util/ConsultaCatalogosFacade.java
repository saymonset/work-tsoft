/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.common.util;

import com.indeval.portaldali.persistence.util.constants.DaliConstants;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.*;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.TipoCustodiaConstants;
import com.indeval.portalinternacional.middleware.servicios.dto.*;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaCorresponsal;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaCorresponsal103;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaCorresponsal202;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * Clase que permite el acceso a la consulta de los catálogos utilizados en los
 * criterios y permite la construcción de los elementos que se mostrarn en los
 * combo - box
 * 
 * @author Erik Vera Montoya
 * @version 1.0
 */
public class ConsultaCatalogosFacade {

	// Cambio Multidivisas
	private InstitucionesService institucionesService;
	private ConsultaDivisaService consultaDivisaService;
	private MovimientoEfectivoInternacionalService internacionalService;
	private ConsultaCuentasService consultaCuentaService;
	private ConsultaSaldosEfectivoService consultaSaldoEfectivoService;
	private BovedaService consultaBovedaService;
	private RetiroEfectivoIntPendientesService retiroEfectivoIntPendientesService;
	private List<String> divisasPrincipales = null;

	public List<SelectItem> getSelectItemsTipoDivisa() {
		List<SelectItem> opcionesSelect = new ArrayList<SelectItem>();

		SelectItem item = null;
		for (DivisaDTO divisa : consultaDivisaService.consultarDivisas(null)) {
			opcionesSelect.add(new SelectItem("" + divisa.getId(), divisa.getClaveAlfabetica()));
		}
		return opcionesSelect;
	}

	public ConsultaCuentasService getConsultaCuentaService() {
		return consultaCuentaService;
	}

	public InstitucionWebDTO buscarInstitucionPorIdFolio(String idFolio) {
		InstitucionWebDTO institucion = null;
		if (!StringUtils.isEmpty(idFolio)) {
			institucion = institucionesService.buscarInstitucionPorClaveYFolio(idFolio);
		}

		return institucion;
	}

	public List<SelectItem> obtenerDivisasPorInstitucion(long idInstitucion) {
		final List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();
		List<DivisaDTO> divisas = new ArrayList<>();
		divisas = consultaDivisaService.findIdDivisasByIdInstitucion(idInstitucion);

		for (DivisaDTO divisa : divisas) {

			opcionesCombo.add(new SelectItem("" + divisa.getId(), divisa.getClaveAlfabetica()));
		}

		return opcionesCombo;
	}

	public Long findCatBicEnBaseABovedaEfectivoParticipante(Long idBovedaEfectivo, Long idInstitucion) {
		return this.consultaBovedaService.findCatBicEnBaseABovedaEfectivoParticipante(idBovedaEfectivo, idInstitucion);
	}

	public CuentaCorresponsalDTO getCuentasCorresponsalesByDivisaAndInstitucion(Long idDivisa, Long idInstitucion) {
		final List<CuentaCorresponsalDTO> corresponsalDTOs = new ArrayList<CuentaCorresponsalDTO>();
		final List<CuentaCorresponsal> corresponsales = consultaDivisaService
				.findCuentaCorresponsalByIdDivisaAndIdInstitucion(idDivisa, idInstitucion);
		for (CuentaCorresponsal cuentaCorresponsal : corresponsales) {
			corresponsalDTOs.add(cuentaCorresponsalDTOByCuentaCorresponsa(cuentaCorresponsal));
		}
		if (corresponsalDTOs != null && !corresponsalDTOs.isEmpty()) {
			return corresponsalDTOs.get(0);
		} else {
			return null;
		}
	}

	private CuentaCorresponsalDTO cuentaCorresponsalDTOByCuentaCorresponsa(CuentaCorresponsal cuentaCorresponsal) {
		final CuentaCorresponsalDTO dto = new CuentaCorresponsalDTO();

		dto.setFechaAlta(cuentaCorresponsal.getFechaAlta());
		dto.setFechaUltModificacion(cuentaCorresponsal.getFechaUltModificacion());
		dto.setIdCuentaCorresponsal(cuentaCorresponsal.getIdCuentaCorresponsal());
		dto.setIdDivisa(cuentaCorresponsal.getIdDivisa());
		dto.setIdInstitucion(cuentaCorresponsal.getIdInstitucion());

		if (cuentaCorresponsal.getCuentaCorresponsal103() != null) {
			dto.setMensaje(Constantes.MT_103);
			cuentaCorresponsalDTOByCuentaCorresponsa103(dto, cuentaCorresponsal.getCuentaCorresponsal103());
		} else if (cuentaCorresponsal.getCuentaCorresponsal202() != null) {
			dto.setMensaje(Constantes.MT_202);
			cuentaCorresponsalDTOByCuentaCorresponsa202(dto, cuentaCorresponsal.getCuentaCorresponsal202());
		}

		return dto;
	}

	private void cuentaCorresponsalDTOByCuentaCorresponsa103(CuentaCorresponsalDTO dto,
			CuentaCorresponsal103 cuentaCorresponsal) {
		dto.setActivoCorresponsalBackup(cuentaCorresponsal.getActivoCorresponsalBackup());
		dto.setActivoCorresponsalPrincipal(cuentaCorresponsal.getActivoCorresponsalPrincipal());
		dto.setAccountLocationB(cuentaCorresponsal.getAccountLocationB());
		dto.setAccountLocationP(cuentaCorresponsal.getAccountLocationP());
		dto.setAccountNameAddressB(cuentaCorresponsal.getAccountNameAddressB());
		dto.setAccountNameAddressP(cuentaCorresponsal.getAccountNameAddressP());
		dto.setAccountOptionB(cuentaCorresponsal.getAccountOptionB());
		dto.setAccountOptionP(cuentaCorresponsal.getAccountOptionP());
		dto.setAccountValueB(cuentaCorresponsal.getAccountPartyIdB());
		dto.setAccountValueP(cuentaCorresponsal.getAccountPartyIdP());
		dto.setBeneficiaryNameAddressB(cuentaCorresponsal.getBeneficiaryNameAddressB());
		dto.setBeneficiaryNameAddressP(cuentaCorresponsal.getBeneficiaryNameAddressP());
		dto.setBeneficiaryNumberNameB(cuentaCorresponsal.getBeneficiaryNumberNameB());
		dto.setBeneficiaryNumberNameP(cuentaCorresponsal.getBeneficiaryNumberNameP());
		dto.setBeneficiaryOptionB(cuentaCorresponsal.getBeneficiaryOptionB());
		dto.setBeneficiaryOptionP(cuentaCorresponsal.getBeneficiaryOptionP());
		dto.setBeneficiaryValueB(cuentaCorresponsal.getBeneficiaryAccountB());
		dto.setBeneficiaryValueP(cuentaCorresponsal.getBeneficiaryAccountP());
		dto.setIntermediaryNameAddressB(cuentaCorresponsal.getIntermediaryNameAddressB());
		dto.setIntermediaryNameAddressP(cuentaCorresponsal.getIntermediaryNameAddressP());
		dto.setIntermediaryOptionB(cuentaCorresponsal.getIntermediaryOptionB());
		dto.setIntermediaryOptionP(cuentaCorresponsal.getIntermediaryOptionP());
		dto.setIntermediaryValueB(cuentaCorresponsal.getIntermediaryPartyIdB());
		dto.setIntermediaryValueP(cuentaCorresponsal.getIntermediaryPartyIdP());
		dto.setAccountBicP(cuentaCorresponsal.getAccountBicP());
		dto.setAccountBicB(cuentaCorresponsal.getAccountBicB());
		dto.setIntermediaryBicP(cuentaCorresponsal.getIntermediaryBicP());
		dto.setIntermediaryBicB(cuentaCorresponsal.getIntermediaryBicB());
		dto.setBeneficiaryBicP(cuentaCorresponsal.getBeneficiaryBicP());
		dto.setBeneficiaryBicB(cuentaCorresponsal.getBeneficiaryBicB());
	}

	private void cuentaCorresponsalDTOByCuentaCorresponsa202(CuentaCorresponsalDTO dto,
			CuentaCorresponsal202 cuentaCorresponsal) {
		dto.setActivoCorresponsalBackup(cuentaCorresponsal.getActivoCorresponsalBackup());
		dto.setActivoCorresponsalPrincipal(cuentaCorresponsal.getActivoCorresponsalPrincipal());
		dto.setAccountLocationB(cuentaCorresponsal.getAccountLocationB());
		dto.setAccountLocationP(cuentaCorresponsal.getAccountLocationP());
		dto.setAccountNameAddressB(cuentaCorresponsal.getAccountNameAddressB());
		dto.setAccountNameAddressP(cuentaCorresponsal.getAccountNameAddressP());
		dto.setAccountOptionB(cuentaCorresponsal.getAccountOptionB());
		dto.setAccountOptionP(cuentaCorresponsal.getAccountOptionP());
		dto.setAccountValueB(cuentaCorresponsal.getAccountPartyIdB());
		dto.setAccountValueP(cuentaCorresponsal.getAccountPartyIdP());
		dto.setBeneficiaryNameAddressB(cuentaCorresponsal.getBeneficiaryNameAddressB());
		dto.setBeneficiaryNameAddressP(cuentaCorresponsal.getBeneficiaryNameAddressP());
		dto.setBeneficiaryOptionB(cuentaCorresponsal.getBeneficiaryOptionB());
		dto.setBeneficiaryOptionP(cuentaCorresponsal.getBeneficiaryOptionP());
		dto.setBeneficiaryValueB(cuentaCorresponsal.getBeneficiaryPartyIdB());
		dto.setBeneficiaryValueP(cuentaCorresponsal.getBeneficiaryPartyIdP());
		dto.setIntermediaryNameAddressB(cuentaCorresponsal.getIntermediaryNameAddressB());
		dto.setIntermediaryNameAddressP(cuentaCorresponsal.getIntermediaryNameAddressP());
		dto.setIntermediaryOptionB(cuentaCorresponsal.getIntermediaryOptionB());
		dto.setIntermediaryOptionP(cuentaCorresponsal.getIntermediaryOptionP());
		dto.setIntermediaryValueB(cuentaCorresponsal.getIntermediaryPartyIdB());
		dto.setIntermediaryValueP(cuentaCorresponsal.getIntermediaryPartyIdP());
		dto.setAccountBicP(cuentaCorresponsal.getAccountBicP());
		dto.setAccountBicB(cuentaCorresponsal.getAccountBicB());
		dto.setIntermediaryBicP(cuentaCorresponsal.getIntermediaryBicP());
		dto.setIntermediaryBicB(cuentaCorresponsal.getIntermediaryBicB());
		dto.setBeneficiaryBicP(cuentaCorresponsal.getBeneficiaryBicP());
		dto.setBeneficiaryBicB(cuentaCorresponsal.getBeneficiaryBicB());
	}

	/**
	 * Obtiene una lista con los elementos del cat�logo de tipos de Bovedas y
	 * filtra solo las de tipo valor preparados en elementos del tipo
	 * {@link SelectItem} para desplegar las opciones en un selectOneMenu
	 *
	 * @return Lista con las opciones a presentar
	 */
	public List<SelectItem> getSelectItemsBovedasEfectivoPorDivisa(DivisaDTO divisaDTO) {
		List<SelectItem> opcionesSelect = new ArrayList<SelectItem>();

		BovedaDto dto = new BovedaDto();
		dto.setIdBoveda(DaliConstants.VALOR_COMBO_TODOS);
		dto.setDescripcion("Selecciona una Boveda");
		BovedaDto criterio = new BovedaDto();
		TipoBovedaDto tipoBoveda = new TipoBovedaDto();
		tipoBoveda.setClave(TipoCustodiaConstants.EFECTIVO);
		criterio.setTipoBoveda(tipoBoveda);

		opcionesSelect.add(new SelectItem("-1", dto.getDescripcion()));

		// Obtiene los ID de las bovedas relacionadas a una divisa
		List<BigInteger> idsBovDiv = consultaBovedaService.obtenerBovedasPorDivisa(divisaDTO);
		// if (idsBovDiv != null) {
		if (!idsBovDiv.isEmpty()) {
			List<Long> idsBovedas = new ArrayList<Long>();
			for (BigInteger idBov : idsBovDiv) {
				idsBovedas.add(idBov.longValue());
			}

			// Obtiene las Bovedas
			for (BovedaDto boveda : consultaBovedaService.buscarBovedasPorTipoCustodia(criterio, idsBovedas, null)) {
				opcionesSelect.add(new SelectItem("" + boveda.getIdBoveda(), boveda.getNombreCorto()));
			}
		}
		return opcionesSelect;
	}

	/**
	 * Obtiene el saldo disponible del participante de su cuenta controlada de
	 * efectivo
	 *
	 * @param idFolioParticipante
	 * @param idBoveda
	 * @return Saldo del participante, null en caso de no existir el participante
	 */
	public Double getSaldoNetoEfectivoPorBovedaDivisa(String idFolioParticipante, Long idBoveda, Long idDivisa) {
		List<SaldoEfectivoDTO> resultados = getSaldoEfectivo(idFolioParticipante, idBoveda, idDivisa);
		Double saldo;
		if (resultados.size() > 0) {
			saldo = resultados.get(0).getSaldo();
		} else {
			saldo = 0.0;
		}
		return saldo;
	}

	public Double getSaldoDisponibleEfectivoPorBovedaDivisa(String idFolioParticipante, Long idBoveda, Long idDivisa) {
		List<SaldoEfectivoDTO> resultados = getSaldoEfectivo(idFolioParticipante, idBoveda, idDivisa);
		Double saldoDisponible;
		if (resultados.size() > 0) {
			saldoDisponible = resultados.get(0).getSaldoDisponible();
		} else {
			saldoDisponible = 0.0;
		}
		return saldoDisponible;
	}

	private List<SaldoEfectivoDTO> getSaldoEfectivo(String idFolioParticipante, Long idBoveda, Long idDivisa) {
		Double saldo = null;
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		// crear objeto con el id de la boveda
		BovedaDto bovedaDTO = new BovedaDto();
		bovedaDTO.setIdBoveda(idBoveda.intValue());
		criterio.setBoveda(bovedaDTO);
		// crear objeto con el id de la divisa
		DivisaDTO divisaDTO = new DivisaDTO();
		divisaDTO.setId(idDivisa);
		criterio.setDivisa(divisaDTO);
		criterio.setCuenta(new CuentaEfectivoDTO());
		criterio.getCuenta().setInstitucion(buscarInstitucionPorIdFolio(idFolioParticipante));
		criterio.getCuenta().setNumeroCuenta("-1");
		criterio.getCuenta().setTipoCustodia("E");
		criterio.getCuenta().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, null));
		criterio.getCuenta().setTipoCuenta(new TipoCuentaDTO(TipoCuentaDTO.CUENTA_NOMBRADA, null));
		criterio.setFecha(new Date());
		List<SaldoEfectivoDTO> resultados = consultaSaldoEfectivoService.consultarSaldosEfectivo(criterio, null, null);
		return resultados;
	}

	/**
	 * Obtiene el saldo disponible del participante de su cuenta controlada de
	 * efectivo
	 *
	 * @param idFolioParticipante
	 * @return Saldo del participante, null en caso de no existir el participante
	 */
	public Double getSaldoNetoEfectivo(String idFolioParticipante) {
		Double saldo = null;

		BovedaDto boveda = new BovedaDto();
		boveda.setIdBoveda(BovedaDto.BOVEDA_BANXICO);
		DivisaDTO divisa = new DivisaDTO();
		divisa.setId(DivisaDTO.DIVISA_PESO_MX);

		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		criterio.setCuenta(new CuentaEfectivoDTO());
		criterio.getCuenta().setInstitucion(buscarInstitucionPorIdFolio(idFolioParticipante));
		criterio.getCuenta().setNumeroCuenta("-1");
		criterio.getCuenta().setTipoCustodia("E");
		criterio.getCuenta().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, null));
		criterio.getCuenta().setTipoCuenta(new TipoCuentaDTO(TipoCuentaDTO.CUENTA_CONTROLADA, null));
		criterio.setFecha(new Date());
		criterio.setBoveda(boveda);
		criterio.setDivisa(divisa);

		List<SaldoEfectivoDTO> resultados = consultaSaldoEfectivoService.consultarSaldosEfectivo(criterio, null, null);
		if (resultados.size() > 0) {
			saldo = resultados.get(0).getSaldo();
		} else {
			saldo = 0.0;
		}
		return saldo;
	}

	public MovimientoEfectivoInternacionalService getInternacionalService() {
		return internacionalService;
	}

	public void setConsultaDivisaService(ConsultaDivisaService consultaDivisaService) {
		this.consultaDivisaService = consultaDivisaService;
	}

	public ConsultaDivisaService getConsultaDivisaService() {
		return consultaDivisaService;
	}

	public void setInstitucionesService(InstitucionesService institucionesService) {
		this.institucionesService = institucionesService;
	}

	public InstitucionesService getInstitucionesService() {
		return institucionesService;
	}

	public void setConsultaBovedaService(BovedaService consultaBovedaService) {
		this.consultaBovedaService = consultaBovedaService;
	}

	public BovedaService getConsultaBovedaService() {
		return consultaBovedaService;
	}

	public void setConsultaCuentaService(ConsultaCuentasService consultaCuentaService) {
		this.consultaCuentaService = consultaCuentaService;
	}

	public void setConsultaSaldoEfectivoService(ConsultaSaldosEfectivoService consultaSaldoEfectivoService) {
		this.consultaSaldoEfectivoService = consultaSaldoEfectivoService;
	}

	public ConsultaSaldosEfectivoService getConsultaSaldoEfectivoService() {
		return consultaSaldoEfectivoService;
	}

	public void setInternacionalService(MovimientoEfectivoInternacionalService internacionalService) {
		this.internacionalService = internacionalService;
	}

	public RetiroEfectivoIntPendientesService getRetiroEfectivoIntPendientesService() {
		return retiroEfectivoIntPendientesService;
	}

	public void setRetiroEfectivoIntPendientesService(RetiroEfectivoIntPendientesService retiroEfectivoIntPendientesService) {
		this.retiroEfectivoIntPendientesService = retiroEfectivoIntPendientesService;
	}
	// Fin Cambio Multidivisas
}
