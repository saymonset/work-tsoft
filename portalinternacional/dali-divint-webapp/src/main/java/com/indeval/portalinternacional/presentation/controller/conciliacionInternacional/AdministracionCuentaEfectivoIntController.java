/**
 * 
 */
package com.indeval.portalinternacional.presentation.controller.conciliacionInternacional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.vo.CuentaEfectivoIntDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * @author César Hernández
 * 
 */
public class AdministracionCuentaEfectivoIntController extends ControllerBase {
	private static final Logger logger = LoggerFactory.getLogger(AdministracionCuentaEfectivoIntController.class);

	private ConciliacionEfectivoIntService conciliacionEfectivoIntService;

	private int totalPaginas;
	private boolean consultaEjecutada = false;

	private String bovedaDali;
	private String bicCode;
	private String cuenta;
	private String cuenta950;
	private String nombre;
	private String divisa;
	private String pais;
	private String cuentaNegocio = "INT";
	private String horaInicio;
	private String minInicio;
	private String horaFin;
	private String minFin;
	private int tipoCuenta = 2;
	private Double saldoInicial;
	private boolean activo;
	private boolean envioTesoreria;

	private List<SelectItem> listaBoveda;
	private List<SelectItem> listaDivisa;
	private List<SelectItem> listaPaises;

	private CuentaEfectivoInt cei;

	private Long idCuenta;

	/** Filtros */
	private List<String> listBicCodes;
	private List<String> listDivisas;
	private List<String> listCuentas;

	private Set<String> bicCodeSelected;
	private Set<String> divisaSelected;
	private Set<String> cuentaSelected;

	private String selectTipoCuenta = "-1";
	private String selectCuentaNegocio = "TODAS";

	private CuentaEfectivoIntDTO dto;

	private List<Divisa> divisas;

	private PaginaVO paginaReportes;

	private int totalRegistros = 0;

	public String getInit() {
		// ejecutarConsulta();

		List<String> bicCodes = conciliacionEfectivoIntService
				.consultaBicCodes();
		listBicCodes = new ArrayList<String>();
		listBicCodes.add("TODOS");
		listBicCodes.addAll(bicCodes);
		bicCodeSelected = new HashSet<String>();
		bicCodeSelected.add("TODOS");

		divisas = conciliacionEfectivoIntService
				.consultaDivisas(bicCodeSelected);
		listDivisas = new ArrayList<String>();
		listDivisas.add("TODAS");

		for (Divisa d : divisas) {
			listDivisas.add(d.getClaveAlfabetica());
		}

		divisaSelected = new HashSet<String>();
		divisaSelected.add("TODAS");

		List<String> cuentas = conciliacionEfectivoIntService.consultaCuentas(
				bicCodeSelected, divisaSelected);
		listCuentas = new ArrayList<String>();
		listCuentas.add("TODAS");
		listCuentas.addAll(cuentas);
		cuentaSelected = new HashSet<String>();
		cuentaSelected.add("TODAS");

		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);

		return null;
	}

	/**
	 * Consultas las divisas a partir de un BIC Code seleccionado
	 */
	public void obtieneDivisas() {
		listDivisas.clear();
		listCuentas.clear();

		divisaSelected.clear();
		divisaSelected.add("TODAS");

		divisas = conciliacionEfectivoIntService
				.consultaDivisas(bicCodeSelected);

		listDivisas.add("TODAS");

		for (Divisa d : divisas) {
			listDivisas.add(d.getClaveAlfabetica());
		}
		obtieneCuentas();
	}

	/**
	 * Obtiene las cuentas ligadas a una divisa seleccionada
	 */
	public void obtieneCuentas() {
		listCuentas.clear();
		cuentaSelected.clear();
		cuentaSelected.add("TODAS");

		List<String> cuentas = conciliacionEfectivoIntService.consultaCuentas(
				bicCodeSelected, divisaSelected);
		listCuentas.add("TODAS");
		listCuentas.addAll(cuentas);
	}

	public String ejecutarConsulta() {
		creaObjetoCriterio();

		paginaVO = conciliacionEfectivoIntService
				.consultaCuentas(paginaVO, dto);

		totalPaginas = paginaVO.getTotalRegistros()
				/ paginaVO.getRegistrosXPag();

		if (paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
			totalPaginas++;
		totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;

		consultaEjecutada = true;

		return null;
	}

	private void creaObjetoCriterio() {
		if (dto == null) {
			dto = new CuentaEfectivoIntDTO();
		}

		List<String> bics = null;
		Iterator<String> iter = bicCodeSelected.iterator();
		while (iter.hasNext()) {
			if (bics == null) {
				bics = new ArrayList<String>();
			}

			bics.add(iter.next());
		}

		List<Long> _divisas = null;
		iter = divisaSelected.iterator();
		while (iter.hasNext()) {
			if (_divisas == null) {
				_divisas = new ArrayList<Long>();
			}

			String claveAlfabetica = iter.next();

			for (Divisa d : divisas) {
				if (claveAlfabetica.equals(d.getClaveAlfabetica())) {
					_divisas.add(d.getIdDivisa());
					break;
				}
			}
		}

		List<String> cuentas = null;
		iter = cuentaSelected.iterator();
		while (iter.hasNext()) {
			if (cuentas == null) {
				cuentas = new ArrayList<String>();
			}

			cuentas.add(iter.next());
		}

		dto.setBicCodes(bics);
		dto.setCuentas(cuentas);
		dto.setDivisas(_divisas);
		dto.setTipoCuenta(Integer.valueOf(selectTipoCuenta));
		dto.setCuentaNegocio(selectCuentaNegocio);
	}

	/**
	 * Limpia todos los campos
	 * 
	 * @param evt
	 */
	public void limpiar(ActionEvent evt) {
		if (paginaVO.getRegistros() != null) {
			paginaVO.getRegistros().clear();
		}

		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);

		totalPaginas = 0;

		bicCodeSelected.clear();
		divisaSelected.clear();
		cuentaSelected.clear();

		listBicCodes.clear();
		listDivisas.clear();
		listCuentas.clear();

		selectTipoCuenta = "-1";
		selectCuentaNegocio = "TODAS";

		tipoCuenta = 1;
		cuentaNegocio = "NAC";

		getInit();

		setConsultaEjecutada(false);
	}

	/**
	 * Inicia el popup para agregar o editar una cuenta
	 * 
	 * @return
	 */
	public String getInitNuevaEditaCuenta() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			idCuenta = Long.valueOf(facesContext.getExternalContext()
					.getRequestParameterMap().get("idCuenta"));

			if (idCuenta.longValue() > 0) {
				cei = conciliacionEfectivoIntService
						.consultaCuentaEfectivoIntPorPk(idCuenta);

				bicCode = cei.getBicCode();
				divisa = (cei.getDivisa() != null) ? String.valueOf(cei
						.getDivisa().getIdDivisa()) : "-1";
				cuenta = cei.getCuenta();
				bovedaDali = (cei.getBoveda() != null) ? String.valueOf(cei
						.getBoveda().getIdBoveda()) : "-1";
				pais = (cei.getPais() != null) ? String.valueOf(cei.getPais()
						.getIdPais()) : "-1";
				cuenta950 = cei.getCuenta950();
				envioTesoreria = cei.getEnvioTesoreria().booleanValue();
				activo = cei.getActivo().booleanValue();
				horaInicio = (cei.getHorarioInicio() != null) ? cei
						.getHorarioInicio().substring(0, 2) : "";
				minInicio = (cei.getHorarioInicio() != null) ? cei
						.getHorarioInicio().substring(3, 5) : "";
				horaFin = (cei.getHorarioFin() != null) ? cei.getHorarioFin()
						.substring(0, 2) : "";
				minFin = (cei.getHorarioFin() != null) ? cei.getHorarioFin()
						.substring(3, 5) : "";
				tipoCuenta = cei.getTipoCuenta().intValue();
				cuentaNegocio = cei.getCuentaNegocio();
				saldoInicial = (cei.getSaldo() != null) ? cei.getSaldo()
						.doubleValue() : 0.0f;
				nombre = cei.getNombre();
			} else {
				cei = null;
			}
		} catch (Exception ex) {
			addErrorMessage("Error al recibir los parámetros");
		}

		return null;
	}

	/**
	 * Buscar las confirmaciones
	 * 
	 * @param evt
	 */
	public void buscarCuentas(ActionEvent evt) {
		paginaVO.setOffset(0);

		ejecutarConsulta();

		consultaEjecutada = true;
	}

	/**
	 * Guarda la cuenta en el catalogo
	 * 
	 * @param evt
	 */
	public void guardarCuenta(ActionEvent evt) {
		if(validaDatos()){
			Divisa d = conciliacionEfectivoIntService.consultaDivisaPorPk(Long.valueOf(divisa));
			Boveda b = conciliacionEfectivoIntService.consultaBovedaPorPk(Long.valueOf(bovedaDali));
			PaisInt p = conciliacionEfectivoIntService.consultaPaisPorPk(Integer.valueOf(pais));
			
			if(cei == null){
				cei = new CuentaEfectivoInt();
			}

			cei.setBicCode(bicCode.toUpperCase());
			cei.setDivisa(d);
			cei.setCuenta(cuenta.toUpperCase());
			cei.setBoveda(b);
			cei.setPais(p);
			cei.setCuenta950(cuenta950.toUpperCase());
			cei.setEnvioTesoreria(envioTesoreria);
			cei.setActivo(activo);

			if (!horaInicio.equals("") && !minInicio.equals("")) {
				if (Integer.parseInt(horaInicio) < 10) {
					horaInicio = "0" + Integer.parseInt(horaInicio);
				}
				if (Integer.parseInt(minInicio) < 10) {
					minInicio = "0" + Integer.parseInt(minInicio);
				}
				cei.setHorarioInicio(horaInicio + ":" + minInicio);
			} else {
				cei.setHorarioInicio(null);
			}

			if (!horaFin.equals("") && !minFin.equals("")) {
				if (Integer.parseInt(horaFin) < 10) {
					horaFin = "0" + Integer.parseInt(horaFin);
				}
				if (Integer.parseInt(minFin) < 10) {
					minFin = "0" + Integer.parseInt(minFin);
				}
				cei.setHorarioFin(horaFin + ":" + minFin);
			} else {
				cei.setHorarioFin(null);
			}

			cei.setTipoCuenta(tipoCuenta);
			cei.setCuentaNegocio(cuentaNegocio);
			cei.setSaldo(BigDecimal.valueOf(saldoInicial));
			cei.setNombre(nombre.toUpperCase());

			if (idCuenta.longValue() == 0) {
				Boolean existeCuenta = Boolean.FALSE;
				if (conciliacionEfectivoIntService.existeCuenta(cei.getBicCode(), cei.getDivisa().getIdDivisa(), cei.getCuenta(), idCuenta)) {
					existeCuenta = Boolean.TRUE;
					addErrorMessage("Ya existe una cuenta con el BIC Code, Divisa y Cuenta seleccionadas.");
				} else if (conciliacionEfectivoIntService.existeCuenta950(cei.getBicCode(), cei.getDivisa().getIdDivisa(), cei.getCuenta950(), idCuenta)) {
					existeCuenta = Boolean.TRUE;
					addErrorMessage("Ya existe una cuenta con el BIC Code, Divisa y Cuenta 950 seleccionadas.");
				}
				
				if (!existeCuenta) {
					conciliacionEfectivoIntService.guardaCuentaEfectivoInt(cei);
					addMessage("La cuenta se guardó satisfactoriamente.");
				}
				
			} else {
				Boolean existeCuenta = Boolean.FALSE;
				if (conciliacionEfectivoIntService.existeCuenta(cei.getBicCode(), cei.getDivisa().getIdDivisa(), cei.getCuenta(), idCuenta)) {
					existeCuenta = Boolean.TRUE;
					addErrorMessage("Ya existe una cuenta con el BIC Code, Divisa y Cuenta seleccionadas.");	
				} else if (conciliacionEfectivoIntService.existeCuenta950(cei.getBicCode(), cei.getDivisa().getIdDivisa(), cei.getCuenta950(), idCuenta)) {
					existeCuenta = Boolean.TRUE;
					addErrorMessage("Ya existe una cuenta con el BIC Code, Divisa y Cuenta 950 seleccionadas.");
				}
				
				if (!existeCuenta) {
					conciliacionEfectivoIntService.actualizaCuentaEfectivoInt(cei);
					addMessage("La cuenta se actualizó satisfactoriamente.");
				}
			}
		}
	}

	private boolean validaDatos() {
		String camposRequeridos = "Revise los campos requeridos por favor.";

		if (bicCode.equals("")) {
			addErrorMessage("Debe escribir un BIC Code. " + camposRequeridos);
			return false;
		}

		if (Integer.valueOf(divisa) == -1) {
			addErrorMessage("Debe seleccionar una Divisa. " + camposRequeridos);
			return false;
		}

		if (nombre.equals("")) {
			addErrorMessage("Debe escribir un Nombre. " + camposRequeridos);
			return false;
		}

		if (cuenta.equals("")) {
			addErrorMessage("Debe escribir una Cuenta. " + camposRequeridos);
			return false;
		}

		if (cuenta950.equals("")) {
			addErrorMessage("Debe escribir una Cuenta 950. " + camposRequeridos);
			return false;
		}

		if (Long.valueOf(pais) == -1) {
			addErrorMessage("Debe seleccionar un país. " + camposRequeridos);
			return false;
		}

		if (saldoInicial == null) {
			addErrorMessage("Debe escribir un Saldo Inicial. "
					+ camposRequeridos);
			return false;
		}

		if (!horaInicio.equals("")) {
			if (Integer.valueOf(horaInicio).intValue() > 23) {
				addErrorMessage("La hora de inicio no debe ser mayor a 23. ");
				return false;
			}

			if (minInicio.equals("") || horaFin.equals("") || minFin.equals("")) {
				addErrorMessage("Debe escribir el horario completo. ");
				return false;
			}
		}

		if (!minInicio.equals("")) {
			if (Integer.valueOf(minInicio).intValue() > 59) {
				addErrorMessage("Los minutos de la hora de inicio no pueden ser mayor a 59. ");
				return false;
			}

			if (horaInicio.equals("") || horaFin.equals("")
					|| minFin.equals("")) {
				addErrorMessage("Debe escribir el horario completo. ");
				return false;
			}
		}

		if (!horaFin.equals("")) {
			if (Integer.valueOf(horaFin).intValue() > 23) {
				addErrorMessage("La hora final no debe ser mayor a 23. ");
				return false;
			}

			if (horaInicio.equals("") || minInicio.equals("")
					|| minFin.equals("")) {
				addErrorMessage("Debe escribir el horario completo. ");
				return false;
			}
		}

		if (!minFin.equals("")) {
			if (Integer.valueOf(minFin).intValue() > 59) {
				addErrorMessage("Los minutos de la hora final no pueden ser mayor a 59. ");
				return false;
			}

			if (horaInicio.equals("") || horaFin.equals("")
					|| horaFin.equals("")) {
				addErrorMessage("Debe escribir el horario completo. ");
				return false;
			}
		}
		
		if(!horaInicio.equals("") && !minInicio.equals("") && !horaFin.equals("") && !minFin.equals("")){
			if (Integer.valueOf(horaInicio).intValue() == Integer.valueOf(horaFin).intValue()) {
				if (Integer.valueOf(minInicio).intValue() >= Integer.valueOf(minFin).intValue()) {
					addErrorMessage("La Hora Inicio debe ser menor a la Hora Final.");
					return false;
				}
			}
			if (Integer.valueOf(horaInicio).intValue() > Integer.valueOf(horaFin).intValue()) {
				addErrorMessage("La Hora Inicio debe ser menor a la Hora Final.");
				return false;
			}
		}

		return true;
	}

	/**
	 * Obtiene la consulta de Bovedas
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getBovedas() {
		if (this.listaBoveda != null && this.listaBoveda.size() > 0) {
			return this.listaBoveda;
		}
		List<Boveda> bovedas = conciliacionEfectivoIntService.consultaBovedasEfectivo();
		List<SelectItem> listaBoveda = new ArrayList<SelectItem>();
		if (bovedas != null) {
			for (Boveda boveda : bovedas) {
				listaBoveda.add(new SelectItem(boveda.getIdBoveda().toString(),
						boveda.getDescripcion()));
			}
		} else {
			listaBoveda.add(new SelectItem("-2", "VACIO"));
		}
		this.listaBoveda = listaBoveda;
		return listaBoveda;
	}

	@SuppressWarnings("unchecked")
	public List<SelectItem> getDivisas() {
		if (this.listaDivisa != null && this.listaDivisa.size() > 0) {
			return this.listaDivisa;
		}
		
		List<SelectItem> listaDivisa = conciliacionEfectivoIntService.findDivisas();		

		this.listaDivisa = listaDivisa;
		return listaDivisa;
	}

	@SuppressWarnings("unchecked")
	public List<SelectItem> getPaises() {
		if (this.listaPaises != null && this.listaPaises.size() > 0) {
			return this.listaPaises;
		}
		List<PaisInt> paises = conciliacionEfectivoIntService.consultaPaises();

		List <SelectItem> listaPaises = new ArrayList<SelectItem>();
		if(paises != null){    		
			for (PaisInt pais : paises){
				if(!pais.getNombrePais().equals("EXTRANJERO"))
					listaPaises.add(new SelectItem(pais.getIdPais().toString(), pais.getNombrePais()));	
			}
		}else{
			listaPaises.add(new SelectItem("-2","VACIO"));
		}
		
		this.listaPaises = listaPaises;
		return listaPaises;
	}

	public void generarReportes(ActionEvent evt)
	{	
		reiniciarEstadoPeticion();

		creaObjetoCriterio();

		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
		paginaReportes = conciliacionEfectivoIntService.consultaCuentas(
				paginaReportes, dto);
		this.totalRegistros = paginaReportes.getTotalRegistros();
	}

	/**
	 * @return the conciliacionEfectivoIntService
	 */
	public ConciliacionEfectivoIntService getConciliacionEfectivoIntService() {
		return conciliacionEfectivoIntService;
	}

	/**
	 * @param conciliacionEfectivoIntService
	 *            the conciliacionEfectivoIntService to set
	 */
	public void setConciliacionEfectivoIntService(
			ConciliacionEfectivoIntService conciliacionEfectivoIntService) {
		this.conciliacionEfectivoIntService = conciliacionEfectivoIntService;
	}

	/**
	 * @return the consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * @param consultaEjecutada
	 *            the consultaEjecutada to set
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * @return the bicCode
	 */
	public String getBicCode() {
		return bicCode;
	}

	/**
	 * @param bicCode
	 *            the bicCode to set
	 */
	public void setBicCode(String bicCode) {
		this.bicCode = bicCode;
	}

	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta
	 *            the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the tipoCuenta
	 */
	public int getTipoCuenta() {
		return tipoCuenta;
	}

	/**
	 * @param tipoCuenta
	 *            the tipoCuenta to set
	 */
	public void setTipoCuenta(int tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	/**
	 * @return the saldoInicial
	 */
	public Double getSaldoInicial() {
		return saldoInicial;
	}

	/**
	 * @param saldoInicial
	 *            the saldoInicial to set
	 */
	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the envioTesoreria
	 */
	public boolean isEnvioTesoreria() {
		return envioTesoreria;
	}

	/**
	 * @param envioTesoreria
	 *            the envioTesoreria to set
	 */
	public void setEnvioTesoreria(boolean envioTesoreria) {
		this.envioTesoreria = envioTesoreria;
	}

	/**
	 * @return the bovedaDali
	 */
	public String getBovedaDali() {
		return bovedaDali;
	}

	/**
	 * @param bovedaDali
	 *            the bovedaDali to set
	 */
	public void setBovedaDali(String bovedaDali) {
		this.bovedaDali = bovedaDali;
	}

	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa
	 *            the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return the cuenta950
	 */
	public String getCuenta950() {
		return cuenta950;
	}

	/**
	 * @param cuenta950
	 *            the cuenta950 to set
	 */
	public void setCuenta950(String cuenta950) {
		this.cuenta950 = cuenta950;
	}

	/**
	 * @return the cuentaNegocio
	 */
	public String getCuentaNegocio() {
		return cuentaNegocio;
	}

	/**
	 * @param cuentaNegocio
	 *            the cuentaNegocio to set
	 */
	public void setCuentaNegocio(String cuentaNegocio) {
		this.cuentaNegocio = cuentaNegocio;
	}

	/**
	 * @return the horaInicio
	 */
	public String getHoraInicio() {
		return horaInicio;
	}

	/**
	 * @param horaInicio
	 *            the horaInicio to set
	 */
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * @return the minInicio
	 */
	public String getMinInicio() {
		return minInicio;
	}

	/**
	 * @param minInicio
	 *            the minInicio to set
	 */
	public void setMinInicio(String minInicio) {
		this.minInicio = minInicio;
	}

	/**
	 * @return the horaFin
	 */
	public String getHoraFin() {
		return horaFin;
	}

	/**
	 * @param horaFin
	 *            the horaFin to set
	 */
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	/**
	 * @return the minFin
	 */
	public String getMinFin() {
		return minFin;
	}

	/**
	 * @param minFin
	 *            the minFin to set
	 */
	public void setMinFin(String minFin) {
		this.minFin = minFin;
	}

	/**
	 * @return the listBicCodes
	 */
	public List<String> getListBicCodes() {
		return listBicCodes;
	}

	/**
	 * @param listBicCodes
	 *            the listBicCodes to set
	 */
	public void setListBicCodes(List<String> listBicCodes) {
		this.listBicCodes = listBicCodes;
	}

	/**
	 * @return the listDivisas
	 */
	public List<String> getListDivisas() {
		return listDivisas;
	}

	/**
	 * @param listDivisas
	 *            the listDivisas to set
	 */
	public void setListDivisas(List<String> listDivisas) {
		this.listDivisas = listDivisas;
	}

	/**
	 * @return the listCuentas
	 */
	public List<String> getListCuentas() {
		return listCuentas;
	}

	/**
	 * @param listCuentas
	 *            the listCuentas to set
	 */
	public void setListCuentas(List<String> listCuentas) {
		this.listCuentas = listCuentas;
	}

	/**
	 * @return the bicCodeSelected
	 */
	public Set<String> getBicCodeSelected() {
		return bicCodeSelected;
	}

	/**
	 * @param bicCodeSelected
	 *            the bicCodeSelected to set
	 */
	public void setBicCodeSelected(Set<String> bicCodeSelected) {
		this.bicCodeSelected = bicCodeSelected;
	}

	/**
	 * @return the divisaSelected
	 */
	public Set<String> getDivisaSelected() {
		return divisaSelected;
	}

	/**
	 * @param divisaSelected
	 *            the divisaSelected to set
	 */
	public void setDivisaSelected(Set<String> divisaSelected) {
		this.divisaSelected = divisaSelected;
	}

	/**
	 * @return the cuentaSelected
	 */
	public Set<String> getCuentaSelected() {
		return cuentaSelected;
	}

	/**
	 * @param cuentaSelected
	 *            the cuentaSelected to set
	 */
	public void setCuentaSelected(Set<String> cuentaSelected) {
		this.cuentaSelected = cuentaSelected;
	}

	/**
	 * @return the selectTipoCuenta
	 */
	public String getSelectTipoCuenta() {
		return selectTipoCuenta;
	}

	/**
	 * @param selectTipoCuenta
	 *            the selectTipoCuenta to set
	 */
	public void setSelectTipoCuenta(String selectTipoCuenta) {
		this.selectTipoCuenta = selectTipoCuenta;
	}

	/**
	 * @return the selectCuentaNegocio
	 */
	public String getSelectCuentaNegocio() {
		return selectCuentaNegocio;
	}

	/**
	 * @param selectCuentaNegocio
	 *            the selectCuentaNegocio to set
	 */
	public void setSelectCuentaNegocio(String selectCuentaNegocio) {
		this.selectCuentaNegocio = selectCuentaNegocio;
	}

	/**
	 * @return the paginaReportes
	 */
	public PaginaVO getPaginaReportes() {
		return paginaReportes;
	}

	/**
	 * @param paginaReportes
	 *            the paginaReportes to set
	 */
	public void setPaginaReportes(PaginaVO paginaReportes) {
		this.paginaReportes = paginaReportes;
	}

	/**
	 * @return the totalRegistros
	 */
	public int getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros
	 *            the totalRegistros to set
	 */
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
}