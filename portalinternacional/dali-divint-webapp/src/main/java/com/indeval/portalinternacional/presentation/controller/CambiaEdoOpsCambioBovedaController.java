/*
 * Copyright (c) 2020 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraOperacionesSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.vo.HistorialBitacoraOperacionesSICDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.UsuarioVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la ejecucion de Cambio de Estado de las Operaciones de Cambio de Boveda SIC.
 */
@SuppressWarnings("unchecked")
public class CambiaEdoOpsCambioBovedaController extends ControllerBase {

    
	private static final Logger LOG = LoggerFactory.getLogger(CambiaEdoOpsCambioBovedaController.class);

	/**
	 * Cadena para almacenar el o los folios control para la busqueda.
	 */
	private String folioControlParam;

	/**
	 * Listado de folios control.
	 */
	private List<BigInteger> listaFoliosControl;

	/**
	 * Listado de folios control no encontrados.
	 */
	private List<BigInteger> listaFoliosNoEncontrados;	

	/**
	 * String folios control no encontrados.
	 */
	private String foliosNoEncontrados;

	/**
	 * Indica si la consulta ya fue ejecutada
	 */
	private boolean consultaEjecutada = false;

    /**
     * Servicio de division internacional.
     */
    private DivisionInternacionalService divisionInternacionalService;

    private UsuarioVO usuario;

    /** Establece el n&uacte;mero de registros encontrados */
    private String numResultados;

    /** variable que indica si se va a renderear el resultado */
    private Boolean renderResult = new Boolean(false);

    /** id mensaje */
    private String idMsg;

    /** mensaje */
    private String mensaje;

    private List<BitacoraOperacionesSic> bitacorasInsert;    
	private List<BitacoraOperacionesSic> bitacorasUpdate;	
	private boolean menuOperador = false;
	private boolean menuAutorizador = false;
	private boolean menuHistorial = false;
	private boolean busquedaPrincipal = false;	
	private PaginaVO resultados;
	private String flujo;
	private String motivoCambio;
	
	private Date fechaInicialAut;
	private Date fechaFinalAut;
	
	private Date fechaInicialOp;
	private Date fechaFinalOp;
	
	private String custodio;
	private String tipoOperacion;
	private String operacion;
	private String foliosConcatComa;
	private boolean consultaEjecutadaHistorial = false;
	private List listaCustodios;
	private String estatusAnterior;
	private String estatusNuevo;

    private List<OperacionSic> registrosNoCambioBoveda = null;

	/**
	 * Constructor.
	 */
	public CambiaEdoOpsCambioBovedaController() {
		inicializarParams();
	}	

	/**
	 * Inicializacion.
	 * @return
	 */
	public String getInit() {
		return null;
	}

	/**
	 * Inicializacion de parametros.
	 */
	public void inicializarParams() {
		this.folioControlParam = new String();
		this.foliosNoEncontrados = new String();
		this.listaFoliosControl = new ArrayList<BigInteger>();
		this.listaFoliosNoEncontrados = new ArrayList<BigInteger>();
        this.consultaEjecutada = false;    
        this.resultados = new PaginaVO();  
        this.motivoCambio = new String();
	}

	/**
	 * Limpia los resultados de la consulta y los criterios.
	 * @param e
	 */
	public void limpiar(ActionEvent e) {
	    this.consultaEjecutada = false;
	    this.paginaVO = new PaginaVO();
	    this.paginaVO.setRegistrosXPag(50);
        inicializarParams();
	}
	
	/**
	 * Metodo para obtener fecha y hora 
	 * @return la fecha actual
	 */
	public Date fechaHora(String format,Date dateFormat) {
		Date date = null;
        DateFormat formatoFecha = new SimpleDateFormat(format);
        String fecha = formatoFecha.format(dateFormat);
        try {
			date = formatoFecha.parse(fecha);
		} catch (ParseException e) {
			LOG.error("Error al parse fecha"+e);           
			e.printStackTrace();
		}
		return date;        
	}

	/**
	 * Ejecuta por primera vez la consulta de busqueda.
	 * Se agrego metodo obtenerFoliosNoEncontrados 
	 * @param event
	 */
	public void obtenerOperaciones(ActionEvent event) {
	    LOG.debug("\n\n####### Entrando a obtenerOperaciones()...");
	    this.definePrecondicionesBusqueda(event);
	    if (StringUtils.isEmpty(folioControlParam) && menuAutorizador) {
	    	this.ejecutarConsulta();
	    }
	    else if (this.validaCampoFolio()) {	    	
            try {
            	this.obtenerListadoDeFoliosControl();
            	if (this.listaFoliosControl.size() > 1000) {
            		this.addErrorMessage("No se permite mas de 1000 folios en la busqueda");
            	}
            	else {
		            this.ejecutarConsulta();
		            if (this.busquedaPrincipal) {
	            	    this.obtenerFoliosNoEncontrados();
		            }
            	}
			} catch (Exception e) {
				LOG.debug("En formato folio control: "+e.getMessage());
				this.addMessageFromProperties("msgErrorExpresionRegularSic", FacesMessage.SEVERITY_ERROR);
			}       
	    }			
	}
	
	/**
	 * Define las precondiciones para la busqueda principal
	 */
	private void definePrecondicionesBusqueda(ActionEvent event){
		this.defineOpcionMenu(event);
	    this.defineOpcionBusqueda(event);
	    this.paginaVO.setOffset(0);
        this.paginaVO.setRegistros(null);
	}
	
	/**
	 * Define si viene de la busqueda principal o si viene del paginador
	 */
	private void defineOpcionBusqueda(ActionEvent event) {
		String opcionBusqueda = (String) event.getComponent().getAttributes().get("tipo_busqueda");
		busquedaPrincipal = false;
		if (opcionBusqueda != null && opcionBusqueda.equals("principal"))
			busquedaPrincipal = true;
	}
	
	/**
	 * Define el tipo de rol con el que se accedio al menu
	 */
	private void defineOpcionMenu(ActionEvent event) {
		String menu = (String) event.getComponent().getAttributes().get("tipo_operador_menu");
		menuOperador = false;
		menuAutorizador = false;
		menuHistorial = false;
		if (menu.equals("Operador"))
			menuOperador=true;
		if (menu.equals("Autorizador"))
			menuAutorizador=true;
		if (menu.equals("Historial"))
			menuHistorial=true;
	}
	
	/**
	 * Metodo para realizar la validacion del campo folio control
	 */
	private boolean validaCampoFolio() {
		boolean valido = true;
		if (StringUtils.isEmpty(folioControlParam)) {		
		    this.addMessageFromProperties("msgErrorCampoObligatorio", new Object[]{"Folio Control"}, FacesMessage.SEVERITY_ERROR);
			valido = false;			
		}
		else if (!Pattern.matches(Constantes.EXPRESION_REGULAR_CAMBIO_OPERACION_SIC, folioControlParam.trim().replace("\n", ""))) {
		    this.addMessageFromProperties("msgErrorExpresionRegularSic", FacesMessage.SEVERITY_ERROR);
			valido = false;
		}
		return valido;			
	}

	/**
	 * Obtiene los folios no encontrados en la busqueda
	 */
	private void obtenerFoliosNoEncontrados() {
		List<OperacionSic> operaciones = (List<OperacionSic>) this.resultados.getRegistros();
		if (this.consultaEjecutada && operaciones.size() != 0)	{
			List<BigInteger> listaFoliosEncontrados = new ArrayList<BigInteger>();
			this.listaFoliosNoEncontrados = new ArrayList<BigInteger>();			
			for (OperacionSic opSic : operaciones) {
				listaFoliosEncontrados.add(opSic.getFolioControl());
			}
			for (BigInteger folio : this.listaFoliosControl) {
				if (!listaFoliosEncontrados.contains(folio)) {
					this.listaFoliosNoEncontrados.add(folio);
				}
			}
			if (!this.listaFoliosNoEncontrados.isEmpty()) {
				this.muestraMensajesFoliosNoEncontrados();
			}
		}	
	}
	
	/**
	 * Muestra los folios no encontrados en la busqueda
	 */
	private void muestraMensajesFoliosNoEncontrados() {
	    // Folios de operaciones que NO son Cambio de Boveda, no se toman en cuenta como folios no encontrados.
	    List<BigInteger> rcb = new ArrayList<BigInteger>();
	    for (OperacionSic os : this.registrosNoCambioBoveda) {
            rcb.add(os.getFolioControl());
        }

		for (BigInteger folio : this.listaFoliosNoEncontrados) {
		    if (!rcb.contains(folio)) {
			    this.addWarnMessage("Folio no encontrado: "+folio.toString());
		    }
		}
	}

	/**
	 * Se obtiene el listado de folios control a consultar.
	 */
	public void obtenerListadoDeFoliosControl() throws Exception {
        LOG.debug("\n\n####### Entrando a obtenerListadoDeFoliosControl()...");

		String[] folios = this.folioControlParam.split("\n");
		foliosConcatComa = this.folioControlParam.replace("\n", ",");
		ArrayList<ArrayList<String>> arreglos = this.obtenerDuplicadosYEliminarlos(folios);
		ArrayList<String> arregloDeDuplicados = arreglos.get(0);
		ArrayList<String> arregloSinDuplicados = arreglos.get(1);
		this.listaFoliosControl = new ArrayList<BigInteger>();
		for (String folio : arregloSinDuplicados) {
			this.listaFoliosControl.add(new BigInteger(folio));
		}		
	}	

	/**
	 * Se obtienen 2 listados, uno con los folios no duplicados y otro con los folios que est��n duplicados. 
	 * @param folios Todos los folios
	 * @return Un arreglo con los dos listados.
	 */
	private ArrayList<ArrayList<String>> obtenerDuplicadosYEliminarlos(String[] folios) {
        LOG.debug("\n\n####### Entrando a obtenerDuplicadosYEliminarlos()...");
        ArrayList<String> listaAOperar = new ArrayList<String>((List<String>) Arrays.asList(folios));
	    ArrayList<String> listaDuplicados = new ArrayList<String>();
	    ArrayList<String> listaSinDuplicados = new ArrayList<String>();
        Iterator<String> it = listaAOperar.iterator();
        LOG.debug("\n\n####### Tamanio de Folios=[" + listaAOperar.size() + "]");
        String folio = new String();
        while (it.hasNext()) {
            folio = (String) it.next();
            LOG.debug("\n\n####### Folio a revisar=[" + folio + "]");
            if (!listaSinDuplicados.contains(folio)) {
                listaSinDuplicados.add(folio);
            }
            else {
                if (!listaDuplicados.contains(folio)) {
                    listaDuplicados.add(folio);
                }
            }
        }
        LOG.debug("\n\n####### Tamanio Folios Duplicados=[" + listaDuplicados.size() + "]");
        for (String fol : listaDuplicados) {
            LOG.debug("\n\n####### Folio Duplicado=[" + fol + "]");
        }
        LOG.debug("\n\n####### Tamanio Folios Sin Duplicar=[" + listaSinDuplicados.size() + "]");
        for (String foli : listaSinDuplicados) {
            LOG.debug("\n\n####### Folio Sin Duplicar=[" + foli + "]");
        }
        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
        ret.add(listaDuplicados);
        ret.add(listaSinDuplicados);
        return ret;
	}

    /**
     * Ejecuta la consulta.
     */
    public String ejecutarConsulta() {
        try {        				
            if (this.menuOperador) {
                this.paginaVO = this.divisionInternacionalService.consultaOperacionesPorFoliosControl(this.listaFoliosControl, this.paginaVO);
                this.consultaEjecutada = true;  
                this.filtraOperacionesNOCambioBoveda();
                this.defineDatosExportacion();
            }
            if (this.menuAutorizador) {
                this.paginaVO = this.divisionInternacionalService.consultaOperacionesPorFoliosControlAutorizador(this.listaFoliosControl, this.paginaVO);        		
                this.consultaEjecutada = true;
                this.filtraOperacionesNOCambioBoveda();
                this.defineDatosExportacion();
            }
        	if (this.menuHistorial) {
        	    boolean seAplicaCambioDeBoveda = true;
        		this.paginaVO = divisionInternacionalService.obtieneHistorialBitacoraSIC(defineParametrosBusquedaHistorial(), this.paginaVO, seAplicaCambioDeBoveda);
    			this.consultaEjecutada = true;
    			this.defineDatosExportacion();
        	}            
        } catch (BusinessException e) {
            LOG.error("Ocurrio un error:",e);
            this.agregarMensaje(e);
        } catch (Exception e) {
            LOG.error("Ocurrio un error:",e);
            this.agregarInfoMensaje(e.getMessage());
        } catch (Throwable e) {
            LOG.error("Ocurrio un error:",e);
            this.agregarMensaje(e);
        }

        return null;
    }

    /**
     * Filtra las operaciones que no son de cambio de boveda, puesto que estas no se van a permitir que 
     * cambien de estado en esta pantalla.
     */
    private void filtraOperacionesNOCambioBoveda() {
        if (this.paginaVO != null && this.paginaVO.getRegistros() != null && this.paginaVO.getRegistros().size() > 0) {
            List<OperacionSic> regs = this.paginaVO.getRegistros();
            List<OperacionSic> regsValidos = new ArrayList<OperacionSic>();
            List<OperacionSic> regsInvalidos = new ArrayList<OperacionSic>();
            for (OperacionSic opsic : regs) {
                if (opsic.getCambioBoveda().equals(BigInteger.ONE)) {
                    regsValidos.add(opsic);
                }
                else {
                    regsInvalidos.add(opsic);
                }
            }
            if (!regsInvalidos.isEmpty()) {
                this.registrosNoCambioBoveda = regsInvalidos;
                this.paginaVO.setRegistros(regsValidos);
                this.paginaVO.setTotalRegistros(regsValidos.size());
                Map valores = this.paginaVO.getValores();
                List<OperacionSic> vals = (List<OperacionSic>) valores.get("totalRegistros");
                regsValidos = null;
                regsInvalidos = null;
                regsValidos = new ArrayList<OperacionSic>();
                regsInvalidos = new ArrayList<OperacionSic>();
                for (OperacionSic os : vals) {
                    if (os.getCambioBoveda().equals(BigInteger.ONE)) {
                        regsValidos.add(os);
                    }
                    else {
                        regsInvalidos.add(os);
                    }
                }
                this.paginaVO.getValores().put("totalRegistros", regsValidos);

                //Se agrega el mensaje con los folios que NO SON de Cambio de Boveda
                StringBuffer mensaje = new StringBuffer();
                mensaje.append("Folios no permitidos para cambio de estado de Cambios de B\u00F3veda: ");
                for (OperacionSic operacionSic : this.registrosNoCambioBoveda) {
                    mensaje.append(operacionSic.getFolioControl()).append(", ");
                }
                mensaje.replace(mensaje.length()-2, mensaje.length(), "");
                this.addWarnMessage(mensaje.toString());
            }
        }
    }

    /**
	 * Define la variable resultados solo cuando es accedido por el boton de buscar
	 */
    private void defineDatosExportacion() {
		if (busquedaPrincipal) {
			resultados = new PaginaVO();
			resultados.setRegistros(new ArrayList());
		    if (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0) {
				resultados.setRegistros((List) paginaVO.getValores().get("totalRegistros"));
				resultados.setTotalRegistros(paginaVO.getTotalRegistros());
		    }
		}
	}

	/**
     * Realiza la aplicacion de cambio de estados de las operaciones mostradas en pantalla.
     * Valida que los estatus sean correctos antes de invocar el servicio
     * @param ev 
     */
    public void aplicarCambios(ActionEvent ev) {
        LOG.debug("\n\n####### Entrando a aplicarCambios()...");
        try {        	 
        	 if (!this.esMotivoActualizadoVacio()) {
            	 if (this.validaEstadosDeFolios()) {
	        		 List<String> foliosCorrectos = this.divisionInternacionalService.insertaCambioEstatusOperador(
	        		                                                                  bitacorasInsert, bitacorasUpdate);
	        		 for (String folio : foliosCorrectos) {
						this.addMessage("Folio control: " + folio + " actualizado correctamente.");
	        		 }
	        		 this.ejecutarConsulta();
        		 }
        	 }
        	 else {
        		 this.addWarnMessage("El motivo del cambio no puede estar vac\u00EDo.");
        	 }       	        	
        } catch (BusinessException e) {
            LOG.error("Ocurrio un error:",e);
            this.agregarMensaje(e);
        } catch (Exception e) {
            LOG.error("Ocurrio un error:",e);
            this.agregarInfoMensaje(e.getMessage());
        } catch (Throwable e) {
            LOG.error("Ocurrio un error:",e);
            this.agregarMensaje(e);
        }
    }  
    
    /**
  	 * Valida que el estatus no sea el mismo y que sea diferente de vacio y llena los objetos a insertar o actualizar
  	 * @return true si encontro al menos un objeto bitacora valido
  	 */
    private boolean validaEstadosDeFolios() {
    	this.bitacorasInsert = new ArrayList<BitacoraOperacionesSic>();
    	this.bitacorasUpdate = new ArrayList<BitacoraOperacionesSic>();
        List<OperacionSic> operaciones = (List<OperacionSic>) this.paginaVO.getRegistros();
    	Boolean ejecutaConsulta = false;
		for (OperacionSic operacionSic : operaciones) {
			BitacoraOperacionesSic bitacora = operacionSic.getBitacoraOperacionesSic();
			if (bitacora != null) {
				if (operacionSic.getEstado().equals(bitacora.getIdEstatusOperacionNuevo().toString())) {
					this.addWarnMessage("Folio control: " + operacionSic.getFolioControl() + 
					                    " no ser\u00e1 aplicado, el estatus nuevo ya se encuentra aplicado en la bitacora");
				}
				else {
					bitacora.setIdEstatusOperacionNuevo(Long.parseLong(operacionSic.getEstado()));
					this.bitacorasUpdate.add(bitacora);
				}
			}
			else {
			    String mensajeErrorEstadoDeseado = "";
			    if (operacionSic.getTipoMensaje().equals(Constantes.TLP_ENTREGA)) {
			        LOG.debug("\n\n####### Es TLP Entrega...");
			        mensajeErrorEstadoDeseado = this.validaEstadoDeseadoDeEntrega(operacionSic);
			        if (StringUtils.isEmpty(mensajeErrorEstadoDeseado)) {
	                    LOG.debug("\n\n####### Se permite cambio de estado...");
			            this.bitacorasInsert.add(this.generaObjetoBitacoraOperacion(operacionSic));
			        }
			        else {
	                    this.addWarnMessage("Folio control: " + operacionSic.getFolioControl() + 
	                                        mensajeErrorEstadoDeseado);
			        }
			    }
			    else if (operacionSic.getTipoMensaje().equals(Constantes.TLP_RECEPCION)) {
                    LOG.debug("\n\n####### Es TLP Recepcion...");
			        mensajeErrorEstadoDeseado = this.validaEstadoDeseadoDeRecepcion(operacionSic);
                    if (StringUtils.isEmpty(mensajeErrorEstadoDeseado)) {
                        LOG.debug("\n\n####### Se permite cambio de estado...");
                        this.bitacorasInsert.add(this.generaObjetoBitacoraOperacion(operacionSic));
                    }
                    else {
                        this.addWarnMessage("Folio control: " + operacionSic.getFolioControl() + 
                                            mensajeErrorEstadoDeseado);
                    }
			    }
			}					
		}

		if (!this.bitacorasInsert.isEmpty() || !this.bitacorasUpdate.isEmpty()) {
			ejecutaConsulta = true;
		}

		operaciones = null;

		return ejecutaConsulta;
	}

    /**
     * Valida el estado deseado de la operacion de Entrega.
     * @param operacionSic La operacion a validar el estado
     * @return La cadena de error a mostrar o null en caso contrario y para permitir el cambio de estado.
     */
    private String validaEstadoDeseadoDeEntrega(OperacionSic operacionSic) {
        String estadoDeseado = operacionSic.getEstado();
        Long estadoActual = operacionSic.getEstatusOperacion().getIdEstatusOperacion();
        
        if (estadoDeseado.equals("-1")) {
            return " no ser\u00e1 aplicado, el estatus nuevo no debe estar vac\u00edo.";
        }
        else if (estadoDeseado.equals(estadoActual.toString())) {
            return " no ser\u00e1 aplicado, el estado nuevo es igual que el actual.";
        }
        //Validacion si el estado deseado es RETENIDO
        else if (estadoDeseado.equals((Long.valueOf(Constantes.ST_OPER_RETENIDA)).toString())) {
            if (estadoActual.equals(Constantes.ST_OPER_HABILITADA) || 
                estadoActual.equals(Constantes.ST_OPER_NOTIFICADA) || 
                estadoActual.equals(Constantes.ST_OPER_ENVIADA) || 
                estadoActual.equals(Constantes.ST_OPER_PENDIENTE) || 
                estadoActual.equals(Constantes.ST_OPER_CONFIRMADA)) {
                Long idOperacionSic = operacionSic.getIdOperacionesSic();
                Long idBoveda = operacionSic.getBoveda().getIdBoveda();
                Long idEmision = operacionSic.getEmision().getIdEmision();
                if (this.divisionInternacionalService.existePosicionEnCuenta5001Indeval(idOperacionSic, idBoveda, idEmision)) {
                    return null;
                }
                return " no ser\u00e1 aplicado, el cambio de estado en la operaci\u00F3n no es posible llevarlo a cabo.";
            }
            return " no ser\u00e1 aplicado, el cambio de estado en la operaci\u00F3n no est\u00E1 permitido.";
        }

        return null;
    }

    /**
     * Valida el estado deseado de la operacion de Recepcion.
     * @param operacionSic La operacion a validar el estado
     * @return La cadena de error a mostrar o null en caso contrario.
     */
    private String validaEstadoDeseadoDeRecepcion(OperacionSic operacionSic) {
        String estadoDeseado = operacionSic.getEstado();
        Long estadoActual = operacionSic.getEstatusOperacion().getIdEstatusOperacion();
        
        if (estadoDeseado.equals("-1")) {
            return " no ser\u00e1 aplicado, el estatus nuevo no debe estar vac\u00EDo.";
        }
        //Validacion de mismo estado
        else if (estadoDeseado.equals(estadoActual.toString())) {
            return " no ser\u00e1 aplicado, el estado nuevo es igual que el actual.";
        }
        //Validacion si el estado deseado es LIBERADO
        else if (estadoDeseado.equals((Long.valueOf(Constantes.ST_OPER_LIBERADA)).toString())) {
            if (estadoActual.equals(Constantes.ST_OPER_PENDIENTE_LIBERAR)) {
                return null;
            }
            return " no ser\u00e1 aplicado, el cambio de estado en la operaci\u00F3n no est\u00E1 permitido.";
        }
        //Validacion si el estado actual es liberado y el estado deseado no es liberado
        else if (estadoActual.equals(Constantes.ST_OPER_PENDIENTE_LIBERAR)) {
            if (!estadoDeseado.equals((Long.valueOf(Constantes.ST_OPER_LIBERADA)).toString())) {
                boolean bovedaNuevaCambiada = this.getDivisionInternacionalService().validarNuevaBovedaEnEmision(
                                                   operacionSic.getEmision().getIdEmision(), 
                                                   operacionSic.getBoveda().getIdBoveda());
                if (bovedaNuevaCambiada) {
                    return " no ser\u00e1 aplicado, la operaci\u00F3n ya tiene la Nueva B\u00F3veda asignada.";
                }
                return null;
            }
        }
        else if (estadoActual.equals(Constantes.ST_OPER_LIBERADA)) {
            return " no ser\u00e1 aplicado, la operaci\u00F3n ya est\u00E1 Liberada.";
        }

        return null;
    }

	/**
  	 * Genera un objeto BitacoraOperacionesSic
  	 * @param operacion
  	 * @return el objeto BitacoraOperacionesSic
  	 */
	 private BitacoraOperacionesSic generaObjetoBitacoraOperacion(OperacionSic operacion) {		 
		 BitacoraOperacionesSic bitacora = new BitacoraOperacionesSic();
		 bitacora.setFolioControl(operacion.getFolioControl());
		 bitacora.setIdEstatusOperacionAnterior(operacion.getEstatusOperacion().getIdEstatusOperacion());
		 bitacora.setIdEstatusOperacionNuevo(Long.parseLong(operacion.getEstado()));
		 bitacora.setCveUsuarioOperador(getCveUsuarioSesion());
	  	 bitacora.setFechaSolicitudOperador(fechaHora("dd/MM/yyyy HH:mm:ss", new Date()));
	  	 bitacora.setIdEstatusBitacora(new Long(1));
	  	 bitacora.setMotivoCambio(this.motivoCambio);
	  	 return bitacora;
	  }  
    
	/**
	 * Invoca el servicio para cancelar es registro en t bitacora
	 * @param ev
	 */
    public void cancelarCambios(ActionEvent ev) {
    	LOG.debug("\n\n####### Entrando a cancelarCambios()...");   
    	List<OperacionSic> operaciones = (List<OperacionSic>) this.paginaVO.getRegistros();
    	List<BitacoraOperacionesSic> bitacorasCancela = new ArrayList<BitacoraOperacionesSic>();
    	boolean existenCancelados = false;
    	boolean errorFolio = false;
    	for (OperacionSic operacionSic : operaciones) 
			if (operacionSic.isCancela()) {
				existenCancelados=true;
				BitacoraOperacionesSic bitacora = operacionSic.getBitacoraOperacionesSic();				
				if (bitacora!=null) {
					bitacora.setIdEstatusBitacora(new Long(2));
					bitacorasCancela.add(bitacora);
				}
				else {
					this.addWarnMessage("Error al intentar cancelar folio "+operacionSic.getFolioControl().toString());
					errorFolio = true;
				}									
			}
    	
    	if (existenCancelados && !errorFolio) {
    		this.actualizaFoliosCancelados(bitacorasCancela);
    	}
    	else {
    		this.addWarnMessage("No hay registros seleccionados para cancelar");
    	}
    }    

    /**
	 * Invoca el servicio para cancelar 
	 * @param bitacorasCancela Una lista con los objetos bitacora a cancelar
	 */
    private void actualizaFoliosCancelados(List<BitacoraOperacionesSic> bitacorasCancela) {
        try {
            List<String> foliosCanceladosCorrectamente = 
                this.divisionInternacionalService.cancelaSolicitudBitacoraSic(bitacorasCancela);
            for (String folio : foliosCanceladosCorrectamente) {
                this.addMessage("Folio: "+folio+" cancelado correctamente.");
            }
            this.ejecutarConsulta();       	
        } catch (BusinessException e) {
           LOG.error("Ocurrio un error:",e);
           this.agregarMensaje(e);
       } catch (Exception e) {
           LOG.error("Ocurrio un error:",e);
           this.agregarInfoMensaje(e.getMessage());
       } catch (Throwable e) {
           LOG.error("Ocurrio un error:",e);
           this.agregarMensaje(e);
       }		
	}
    
    /**
	 * Actualiza el motivo para los folios seleccionados
	 * @param ev
	 */
    public void actualizarMotivos(ActionEvent ev) {
    	LOG.debug("\n\n####### Entrando a actualizarMotivos()...");
    	if (esMotivoActualizadoVacio()) {
    	    this.addWarnMessage("El motivo del cambio no puede estar vac\u00EDo.");	
    	}
    	else {
    		List<OperacionSic> operaciones = (List<OperacionSic>) this.paginaVO.getRegistros();
        	List<BitacoraOperacionesSic> actualizaMotivoList = new ArrayList<BitacoraOperacionesSic>(); 
        	boolean existeChecksSeleccionados = false;
        	for (OperacionSic operacionSic : operaciones) {
        		if (operacionSic.isActualizaCambio()) {
        			existeChecksSeleccionados = true;
        			actualizaMotivoList.add(agregaMotivoActualizado(operacionSic.getBitacoraOperacionesSic()));
        		}
        	}
        	if (existeChecksSeleccionados) {
        	    this.actualizarMotivosService(actualizaMotivoList);
        	}
        	else { 
        	    this.addWarnMessage("No hay registros seleccionados para actualizar motivo.");
        	}
    	} 	
    }

    /**
	 * Realiza la llamada al metodo de servicio
	 * @param la lista de objetos bitacora a actualizar
	 */
    private void actualizarMotivosService(List<BitacoraOperacionesSic> actualizaMotivoList) {
    	try {        	 
          	 List<String> foliosActualizadosMotivo = 
          	     this.divisionInternacionalService.actualizaMotivoCambioBitacoraSic(actualizaMotivoList);
          	 for (String folio : foliosActualizadosMotivo) { 
          		 this.addMessage("Folio: "+folio+" actualizado correctamente.");
          	 }
          } catch (BusinessException e) {
              LOG.error("Ocurrio un error:",e);
              this.agregarMensaje(e);
          } catch (Exception e) {
              LOG.error("Ocurrio un error:",e);
              this.agregarInfoMensaje(e.getMessage());
          } catch (Throwable e) {
              LOG.error("Ocurrio un error:",e);
              this.agregarMensaje(e);
          }		
	}

    /**
 	 * Revisa si el motivo esta vacio
 	 */
	private boolean esMotivoActualizadoVacio() {
		if (this.motivoCambio.length() == 0) {
			return true;
		}
		else {			
			return false;
		}
	}

	/**
	 * agrega el motivo al objeto de bitacora
	 * @param el objeto bitacora a agregar el motivo
	 */	
	private BitacoraOperacionesSic agregaMotivoActualizado(BitacoraOperacionesSic bitacoraOperacionesSic) {
    	bitacoraOperacionesSic.setMotivoCambio(motivoCambio);
    	return bitacoraOperacionesSic;		
	}

	/**
  	 * Realiza la validacion si existen folios seleccionados por autorizar si se encuentran invoca el servicio
  	 * @param ev
  	 */
    public void autorizarCambios(ActionEvent ev) {
        LOG.debug("\n\n####### Entrando a autorizarCambios()...");
        List<OperacionSic> operaciones = (List<OperacionSic>) paginaVO.getRegistros();
        List<OperacionSic> listaOperacionesAutorizar = new ArrayList<OperacionSic>(); 

        for (OperacionSic operacionSic : operaciones) {
            if (operacionSic.isAutorizo()) {
                listaOperacionesAutorizar.add(operacionSic);
            }
        }

        if (!listaOperacionesAutorizar.isEmpty()) {
            this.autorizaFolios(listaOperacionesAutorizar);
        }
        else {
            this.addWarnMessage("No existen folios seleccionados por autorizar");
        }
    }    

    /**
  	 * Invoca el servicio para autorizar los cambios de estado
  	 * @param listaOperacionesAutorizar la lista d operacionesSic
  	 */
    private void autorizaFolios(List<OperacionSic> listaOperacionesAutorizar) {
        try {
            this.divisionInternacionalService.actualizaOperacionSICCambioBovedaAutorizador(listaOperacionesAutorizar, 
                                                                                           this.getCveUsuarioSesion()); 	
            this.addMessage("Operaciones actualizadas correctamente.");
            this.ejecutarConsulta();   
        } catch (BusinessException e) {
            LOG.error("Ocurrio un error:",e);
            this.agregarMensaje(e);
        } catch (Exception e) {
            LOG.error("Ocurrio un error:",e);
            this.agregarInfoMensaje(e.getMessage());
        } catch (Throwable e) {
            LOG.error("Ocurrio un error:",e);
            this.agregarMensaje(e);
        }		
    }
    
  	/**
	 * Genera los reportes en formato PDF o Excel
	 * @param e ActionListener que lo invoca
	 */
	public void generarReportesXls(ActionEvent e) {
		if (menuOperador) {
			flujo = "cambiaEdoCambioBovXls";
		}
		if (menuAutorizador) {
			flujo = "cambiaEdoCambioBovAutXls";
		}		
	}
	
	/**
	 * Define el flujo al cual sera generado el reporte
	 * @return el string con el flujo
	 */
	public String defineFlujo() {
		return flujo;
	}
	
	/********************************   SECCION PARA GENERAR HISTORIAL DE CONSULTA  ******************************/
	
	public void limpiarHistorialForma(ActionEvent e) {
		this.fechaInicialAut = null;
		this.folioControlParam = new String();
		this.fechaFinalAut = null;	
		this.fechaInicialOp = null;
		this.fechaFinalOp = null;
		this.custodio = "-1";
		this.tipoOperacion = "-1";
		this.operacion = "-1";
		this.estatusAnterior = "-1";
		this.estatusNuevo = "-1";
		this.consultaEjecutada = false;
	    this.paginaVO = new PaginaVO();
	    this.paginaVO.setRegistrosXPag(50);
	}	
	
	public void obtenerHistorial(ActionEvent e) {
	    LOG.debug("\n\n####### Entrando a obtenerHistorial()...");
	    this.defineOpcionBusqueda(e);
	    this.defineOpcionMenu(e);
	    if (busquedaPrincipal) {
	        if (this.validaCamposVaciosHistorial() && this.validaRangosCamposFecha()) {
	            this.ejecutarConsulta();
	        }
	    }
	    else {
	        this.ejecutarConsulta();
	    }
	}
	
	private HistorialBitacoraOperacionesSICDTO defineParametrosBusquedaHistorial() {
		HistorialBitacoraOperacionesSICDTO historialDto = new HistorialBitacoraOperacionesSICDTO();
		historialDto.setFolioControl(folioControlParam.length()==0?null:new BigDecimal(folioControlParam));
		historialDto.setFechaInAut(fechaInicialAut);
		historialDto.setFechafinAut(fechaFinalAut==null?null:formatUltimaHora(fechaFinalAut));
		historialDto.setFechaInOp(fechaInicialOp);
		historialDto.setFechaFinOp(fechaFinalOp==null?null:formatUltimaHora(fechaFinalOp));
		historialDto.setDetalleCustodio(custodio);
		historialDto.setOperacion(operacion);
		historialDto.setTipoOperacion(tipoOperacion);
		historialDto.setEstatusAnterior(estatusAnterior);
		historialDto.setEstatusNuevo(estatusNuevo);
		return historialDto;
	}
	
	private Date formatUltimaHora(Date miDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(miDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	private boolean validaRangosCamposFecha() {
	    boolean validado = true;
	    if (fechaInicialOp != null && fechaFinalOp == null)
	        fechaFinalOp = fechaInicialOp;
	    if (fechaInicialOp == null && fechaFinalOp != null)
	        fechaInicialOp = fechaFinalOp;
	    if (fechaInicialAut != null && fechaFinalAut == null)
	        fechaFinalAut = fechaInicialAut;
	    if (fechaInicialAut == null && fechaFinalAut != null)
	        fechaInicialAut = fechaFinalAut;
	    if (fechaInicialOp != null && fechaFinalOp != null) {
	        if (fechaFinalOp.before(fechaInicialOp)) {
	            validado = false;
	            this.addErrorMessage("La fecha final no puede ser menor a la inicial");
	        }
	    }
	    if (fechaFinalAut != null && fechaInicialAut != null) {
	        if (fechaFinalAut.before(fechaInicialAut)) {
	            validado = false;
	            this.addErrorMessage("La fecha final no puede ser menor a la inicial");
	        }
	    }
	    return validado;
	}

	private boolean validaCamposVaciosHistorial() {
	    boolean validado = true;
	    if (folioControlParam.length() == 0 && fechaInicialOp == null && fechaFinalOp == null && fechaInicialAut == null && 
	        fechaFinalAut == null && custodio == "-1" && tipoOperacion == "-1" && operacion == "-1") {
	        this.addErrorMessage("Al menos un campo debe ser proporcionado");
	        validado = false;
	    }
	    else if (folioControlParam.length() != 0) {
	        if (!Pattern.matches(Constantes.EXPRESION_REGULAR_CAMBIO_OPERACION_SIC, folioControlParam)) {
	            this.addMessageFromProperties("msgErrorExpresionRegularSic", FacesMessage.SEVERITY_ERROR);
	            validado = false;
	        }	
	    }
	    return validado;
	}

	@SuppressWarnings("rawtypes")
	public List getListaCustodios() {
		if (listaCustodios==null) {
			listaCustodios = new ArrayList();
	    	List<String> custodios = divisionInternacionalService.listaCustodios();
	    	if (custodios != null  && !custodios.isEmpty()) {
	    		for (String c: custodios) {	    			
	    			 if (!(c == null  )) {						
						 listaCustodios.add(new SelectItem(c, c));
					 }
	        	}	
	    	}
		}	
    	return listaCustodios;
	}
	
	/**
	 * Metodo que retorna la descripcion de la divisa selecccionado en la consulta
	 * @return la descripcion de la divisa de la operacion
	 */
	public String getOperacionSeleccionada() {
		String descripcion = "";
		
		if ("-1".equals(operacion)) {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
		else if (Constantes.TIPO_MOVTO_E.equals(operacion)) {
			descripcion = Constantes.MOVTO_ENTREGA;
		} 
		else {
			descripcion = Constantes.MOVTO_RECIBE;
		}		
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la descripcion de la divisa selecccionado en la consulta
	 * @return la descripcion de la divisa de la operacion
	 */
	public String getTipoOperacionSeleccionada() {
		String descripcion = "";
		
		if ("-1".equals(tipoOperacion)) {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
		else if (Constantes.TRASPASO_CONTRA.equals(tipoOperacion)) {
			descripcion = Constantes.DESC_TRASPASO_CONTRA;
		}
		else {
			descripcion = Constantes.DESC_TRASPASO_LIBRE;
		}
				
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la descripcion del estatus selecccionado en la consulta
	 * @return la descripcion del estatus de la operacion
	 */
	public String getEstatusSeleccionadoAnterior() {
		String descripcion = "";		
		
		if ("-1".equals(estatusAnterior)) {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		else {
			EstatusOperacion[] estados = divisionInternacionalService.obtieneEstatusOperacion();
			if (estados != null && estados.length > 0) {				
				int i = 0;				
				while (i <= estados.length -1  ) {					
					if (estados[i].getIdEstatusOperacion().equals(Long.valueOf(estatusAnterior))) {					
						descripcion = estados[i].getDescEstatusOperacion();
						break;
					}
					i++;
				}
			}
		}
		return descripcion;
	}

	/**
	 * Metodo que retorna la descripcion del estatus selecccionado en la consulta
	 * @return la descripcion del estatus de la operacion
	 */
	public String getEstatusSeleccionadoNuevo() {
		String descripcion = "";		
		
		if ("-1".equals(estatusNuevo)) {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		else {
			EstatusOperacion[] estados = divisionInternacionalService.obtieneEstatusOperacion();
			if (estados != null && estados.length > 0) {				
				int i = 0;				
				while (i <= estados.length -1  ) {					
					if (estados[i].getIdEstatusOperacion().equals(Long.valueOf(estatusNuevo))) {					
						descripcion = estados[i].getDescEstatusOperacion();
						break;
					}
					i++;
				}
			}
		}
		return descripcion;
	}
	
	
	/**********************************  GETERS Y SETERS **********************************************************/	
    
    /**
	 * @return the folioControlParam
	 */
	public String getFolioControlParam() {
		return this.folioControlParam;
	}

	/**
	 * @param folioControlParam the folioControlParam to set
	 */
	public void setFolioControlParam(String folioControlParam) {
		this.folioControlParam = folioControlParam;
	}

	/**
	 * @return the consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return this.consultaEjecutada;
	}

	/**
	 * @param consultaEjecutada the consultaEjecutada to set
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	
	/**
	 * @return the divisionInternacionalService
	 */
	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	public String getFlujo() {
		return this.flujo;
	}

	public void setFlujo(String flujo) {
		this.flujo = flujo;
	}

	/**
	 * @param divisionInternacionalService
	 *            the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	/**
	 * @return the usuario
	 */
	public UsuarioVO getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the numResultados
	 */
	public String getNumResultados() {
		return numResultados;
	}

	public PaginaVO getResultados() {
		return resultados;
	}

	public void setResultados(PaginaVO resultados) {
		this.resultados = resultados;
	}

	/**
	 * @param numResultados
	 *            the numResultados to set
	 */
	public void setNumResultados(String numResultados) {
		this.numResultados = numResultados;
	}

	/**
	 * @return the foliosNoEncontrados
	 */
	public String getFoliosNoEncontrados() {
		return foliosNoEncontrados;
	}

	/**
	 * @param foliosNoEncontrados the foliosNoEncontrados to set
	 */
	public void setFoliosNoEncontrados(String foliosNoEncontrados) {
		this.foliosNoEncontrados = foliosNoEncontrados;
	}

	/**
	 * @return the listaFoliosNoEncontrados
	 */
	public List<BigInteger> getListaFoliosNoEncontrados() {
		return listaFoliosNoEncontrados;
	}

	/**
	 * @param listaFoliosNoEncontrados the listaFoliosNoEncontrados to set
	 */
	public void setListaFoliosNoEncontrados(
			List<BigInteger> listaFoliosNoEncontrados) {
		this.listaFoliosNoEncontrados = listaFoliosNoEncontrados;
	}

	/**
	 * @return the renderResult
	 */
	public Boolean getRenderResult() {
		return renderResult;
	}

	/**
	 * @param renderResult
	 *            the renderResult to set
	 */
	public void setRenderResult(Boolean renderResult) {
		this.renderResult = renderResult;
	}

	/**
	 * @return the idMsg
	 */
	public String getIdMsg() {
		return idMsg;
	}

	/**
	 * @param idMsg
	 *            the idMsg to set
	 */
	public void setIdMsg(String idMsg) {
		this.idMsg = idMsg;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the motivoCambio
	 */
	public String getMotivoCambio() {
		return motivoCambio;
	}

	/**
	 * @param motivoCambio the motivoCambio to set
	 */
	public void setMotivoCambio(String motivoCambio) {
		this.motivoCambio = motivoCambio;
	}
	
    public Date getFechaInicialAut() {
		return fechaInicialAut;
	}

	public void setFechaInicialAut(Date fechaInicialAut) {
		this.fechaInicialAut = fechaInicialAut;
	}

	public Date getFechaFinalAut() {
		return fechaFinalAut;
	}

	public void setFechaFinalAut(Date fechaFinalAut) {
		this.fechaFinalAut = fechaFinalAut;
	}

	public Date getFechaInicialOp() {
		return fechaInicialOp;
	}

	public void setFechaInicialOp(Date fechaInicialOp) {
		this.fechaInicialOp = fechaInicialOp;
	}

	public String getFoliosConcatComa() {
		return foliosConcatComa;
	}

	public void setFoliosConcatComa(String foliosConcatComa) {
		this.foliosConcatComa = foliosConcatComa;
	}

	public Date getFechaFinalOp() {
		return fechaFinalOp;
	}

	public void setFechaFinalOp(Date fechaFinalOp) {
		this.fechaFinalOp = fechaFinalOp;
	}

	public String getCustodio() {
		return custodio;
	}

	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public boolean isConsultaEjecutadaHistorial() {
		return consultaEjecutadaHistorial;
	}

	public void setConsultaEjecutadaHistorial(boolean consultaEjecutadaHistorial) {
		this.consultaEjecutadaHistorial = consultaEjecutadaHistorial;
	}
	
	public void setListaCustodios(List listaCustodios) {
		this.listaCustodios = listaCustodios;
	}

	/**
	 * @return the estatusAnterior
	 */
	public String getEstatusAnterior() {
		return estatusAnterior;
	}

	/**
	 * @param estatusAnterior the estatusAnterior to set
	 */
	public void setEstatusAnterior(String estatusAnterior) {
		this.estatusAnterior = estatusAnterior;
	}

	/**
	 * @return the estatusNuevo
	 */
	public String getEstatusNuevo() {
		return estatusNuevo;
	}

	/**
	 * @param estatusNuevo the estatusNuevo to set
	 */
	public void setEstatusNuevo(String estatusNuevo) {
		this.estatusNuevo = estatusNuevo;
	}

}
