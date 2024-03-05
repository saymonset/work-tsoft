/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ServicesCapturaOperacionViewHelper.java
 * Apr 23, 2008
 */
package com.indeval.portaldali.presentation.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoParams;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.util.FechasUtilService;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.util.UtilFecha;
import com.indeval.portaldali.presentation.dto.mercadodinero.TraspasoLibrePagoDTO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Clase Helper para el cálculo de fechas y cantidades que utilizan servicios de negocio para realizar operaciones.
 *  @author Emigdio Hernández
 *
 *
 */
public class ServicesCapturaOperacionViewHelper {
	
	/**Dao para lA consulta de días Inhábiles*/
	private DiaInhabilDaliDao diaInhabilDaliDao=null;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * Servicio para el acceso al cálculo de fechas
	 */
	private FechasUtilService fechasUtilService = null;
	/**
	 * Servicio de mercado de dinero
	 */
	private MercadoDineroService mercadoDineroService = null;
	
	
	public boolean isDiaHabil(Date fecha){
			if (diaInhabilDaliDao.fechaInhabil(fecha)> 0){
				return false;
			}	
			return true;	
		
	}
	
	public Date calculaFechaLiquidacion(Integer plazo){
        try{
            Date today = fechasUtilService.getCurrentDate();
           
            if( plazo == null  ){
                return today;
            }
            int dias = plazo.intValue()/24;                       
            Date fechLiq = mercadoDineroService.agregarDiasHabiles(today, dias);
            return fechLiq;
        }catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * Obtiene el campo fechasUtilService
	 * @return  fechasUtilService
	 */
	public FechasUtilService getFechasUtilService() {
		return fechasUtilService;
	}

	/**
	 * Asigna el campo fechasUtilService
	 * @param fechasUtilService el valor de fechasUtilService a asignar
	 */
	public void setFechasUtilService(FechasUtilService fechasUtilService) {
		this.fechasUtilService = fechasUtilService;
	}

	/**
	 * Obtiene el campo mercadoDineroService
	 * @return  mercadoDineroService
	 */
	public MercadoDineroService getMercadoDineroService() {
		return mercadoDineroService;
	}

	/**
	 * Asigna el campo mercadoDineroService
	 * @param mercadoDineroService el valor de mercadoDineroService a asignar
	 */
	public void setMercadoDineroService(MercadoDineroService mercadoDineroService) {
		this.mercadoDineroService = mercadoDineroService;
	}
	
	/**
     * Crea un objeto TraspasoContraPagoVO y lo llena con los valores del objeto registraOperacionParams.
     * Este m&eacute;todo tiene como objetivo eliminar la redundancia de codigo que puede existir al momento de incializar
     * el objeto vo en cada una de los tipos de operaciones 
     *  
     * @param registraOperacionParams Objeto de donde se tomara la informacion
     * @return vo con los campos inicializados en base al objeto proporcionado como parametros
     */
    public TraspasoContraPagoVO inicializaTraspasoContrapagoVO(RegistraOperacionParams registraOperacionParams) {
        
    	TraspasoContraPagoVO vo = new TraspasoContraPagoVO();
    	
		vo.setTipoValor(registraOperacionParams.getEmision().getIdTipoValor().trim());
        vo.setEmisora(registraOperacionParams.getEmision().getEmisora().trim());
        vo.setSerie(registraOperacionParams.getEmision().getSerie().trim());
        vo.setCupon(registraOperacionParams.getEmision().getCupon().trim());
        vo.setCantidadTitulos(Long.valueOf(registraOperacionParams.getCantidadOperada().longValue()));
        vo.setIdFolioCtaReceptora(   registraOperacionParams.getReceptor().getId()+ 
                registraOperacionParams.getReceptor().getFolio() + 
                registraOperacionParams.getReceptor().getCuenta());
        vo.setIdFolioCtaTraspasante( registraOperacionParams.getTraspasante().getId() + 
                registraOperacionParams.getTraspasante().getFolio() +
                registraOperacionParams.getTraspasante().getCuenta());
        vo.setFechaLiquidacion(registraOperacionParams.getFechaLiq());
        vo.setFechaConcertacion(registraOperacionParams.getFechaConcertacion());// <-Extra
        vo.setFechaRegistro( registraOperacionParams.getFechaConcertacion() );
        vo.setFechaHoraCierreOper(registraOperacionParams.getFechaHoraCierreOper());
        vo.setMonto(registraOperacionParams.getImporte());

		return vo;
	}
	
	/**
     * Crea un objeto TraspasoLibrePagoVO y lo llena con los valores del objeto registraOperacionParams.
     * Este m&eacute;todo tiene como objetivo eliminar la redundancia de codigo que puede existir al momento de incializar
     * el objeto vo en cada una de los tipos de operaciones 
     *  
     * @param registraOperacionParams Objeto de donde se tomara la informacion
     * @return vo con los campos inicializados en base al objeto proporcionado como parametros
     */
    public TraspasoLibrePagoVO inicializaTraspasoLibrePagoVO(RegistraOperacionParams registraOperacionParams) {
        
    	TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();
    	
		vo.setTipoValor(registraOperacionParams.getEmision().getIdTipoValor().trim());
        vo.setEmisora(registraOperacionParams.getEmision().getEmisora().trim());
        vo.setSerie(registraOperacionParams.getEmision().getSerie().trim());
        vo.setCupon(registraOperacionParams.getEmision().getCupon().trim());
        vo.setCantidadTitulos(Long.valueOf(registraOperacionParams.getCantidadOperada().longValue()));
        vo.setIdFolioCtaReceptora(   registraOperacionParams.getReceptor().getId()+ 
                registraOperacionParams.getReceptor().getFolio() + 
                registraOperacionParams.getReceptor().getCuenta());
        vo.setIdFolioCtaTraspasante( registraOperacionParams.getTraspasante().getId() + 
                registraOperacionParams.getTraspasante().getFolio() +
                registraOperacionParams.getTraspasante().getCuenta());
        vo.setFechaLiquidacion(registraOperacionParams.getFechaLiq());
        vo.setFechaRegistro( registraOperacionParams.getFechaConcertacion() );
        vo.setFechaHoraCierreOper(registraOperacionParams.getFechaHoraCierreOper());

		return vo;
	}
    
    /**
     * Establece a la fecha con el formato de dd/MM/yyyy absolutamente (Sin horas, minutos, segundos).
     * @param fechaParam
     * @return
     */
    public Date parseFechaAbsoluta(Date fechaParam){
        Calendar c = Calendar.getInstance();
        c.setTime(fechaParam);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        
        Date fechaReg = new Date();
        SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fechaReg = formateadorFecha.parse(dayOfMonth+"/"+month+"/"+year);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaReg;
    }
    
    /**
     * 
     * @param traspasoLibrePago
     * @return
     */
    public GetCapturaTraspasoParams construyeYCalculaOperacionCapitales(TraspasoLibrePagoDTO traspasoLibrePago){
        GetCapturaTraspasoParams registraOperacionParams = new GetCapturaTraspasoParams();

        // --- AQUI SE LLEVA A CABO LA INSERCION DE LA OPERACION

        registraOperacionParams.setFechaLiquidacion(traspasoLibrePago.getFechaConcertacion());
        registraOperacionParams.setFechaHoraCierreOper(traspasoLibrePago.getFechaHoraCierreOper());
        // --- TIPO DE OPERACION, TRASPASO LIBRE DE PAGO ("T")
        registraOperacionParams.setCveReporto("T");

        // --- AGENTE TRASPASANTE
        registraOperacionParams
                .setTraspasante(DTOAssembler.crearAgenteVO(traspasoLibrePago.getPosicionTraspasante().getCuenta()));

        // --- AGENTE DEL RECEPTOR
        registraOperacionParams.setReceptor(DTOAssembler.crearAgenteVO(traspasoLibrePago.getCuentaReceptor()));
        registraOperacionParams.setEmision(new EmisionVO());
        registraOperacionParams.setCantidad(new BigDecimal(traspasoLibrePago.getCantidadOperada()));

        // --- EMISION
        if (traspasoLibrePago.getPrecioVector() != null) {
            registraOperacionParams.getEmision().setPrecioVector(new BigDecimal(traspasoLibrePago.getPrecioVector()));
        }
        // --- SALDO DISPONIBLE
        if (traspasoLibrePago.getSaldoDisponible() != null) {
            registraOperacionParams.getEmision().setSaldoDisponible(
                    new BigDecimal(traspasoLibrePago.getSaldoDisponible().doubleValue()));
        }
        registraOperacionParams.getEmision().setIdTipoValor(
                traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
        registraOperacionParams.getEmision().setEmisora(
                traspasoLibrePago.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
        registraOperacionParams.getEmision().setSerie(
                traspasoLibrePago.getPosicionTraspasante().getEmision().getSerie().getSerie());
        registraOperacionParams.getEmision().setCupon(traspasoLibrePago.getPosicionTraspasante().getEmision().getCupon());
        registraOperacionParams.getEmision().setMercado(traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado());

        CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();
        calculos.setTipoOperacion(registraOperacionParams.getCveReporto());
        calculos.setCantidadOperadaLeida(traspasoLibrePago.getCantidadOperada() != null ? traspasoLibrePago.getCantidadOperada()
                .toString() : "0");
        calculos.setSaldoDisponibleLeido(traspasoLibrePago.getSaldoDisponible() != null ? traspasoLibrePago.getSaldoDisponible()
                .toString() : "0");
        calculos.setPlazoRepDiasInhabilitadoLeido("true");
        calculos.setPlazoLiquidacionHorasInhabilitadoLeido("true");
        calculos.setPrecioTituloInhabilitadoLeido("true");
        calculos.setImporteInhabilitadoLeido("true");
        calculos.setTasaPremioInhabilitadoLeido("true");
        calculos.setTasaPremioLeido(null);

        realizarCalculos(calculos);
        
        // --- ENCONTRO UN ERROR AL CALCULAR EL SIMULADO
        if (calculos != null && calculos.getMensajeSimulado() != null
                && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
        	registraOperacionParams = null;
        }
        // --- ENCONTRO UN ERROR AL CALCULAR EL SIMULADO
        if (calculos != null && calculos.getMensajeSimulado() != null
                && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
        	registraOperacionParams = null;
        }

        return registraOperacionParams;
    }

    /**
     * 
     * @param registraOperacionParams
     * @return
     */
	public TraspasoLibrePagoVO inicializaTraspasoContrapagoVO(GetCapturaTraspasoParams registraOperacionParams) {
        
    	TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();
    	
		vo.setTipoValor(registraOperacionParams.getEmision().getIdTipoValor().trim());
        vo.setEmisora(registraOperacionParams.getEmision().getEmisora().trim());
        vo.setSerie(registraOperacionParams.getEmision().getSerie().trim());
        vo.setCupon(registraOperacionParams.getEmision().getCupon().trim());
        vo.setCantidadTitulos(Long.valueOf(registraOperacionParams.getCantidad().longValue()));
        vo.setIdFolioCtaReceptora(   registraOperacionParams.getReceptor().getId()+ 
                registraOperacionParams.getReceptor().getFolio() + 
                registraOperacionParams.getReceptor().getCuenta());
        vo.setIdFolioCtaTraspasante( registraOperacionParams.getTraspasante().getId() + 
                registraOperacionParams.getTraspasante().getFolio() +
                registraOperacionParams.getTraspasante().getCuenta());
        vo.setFechaLiquidacion(registraOperacionParams.getFechaLiquidacion());
        vo.setFechaRegistro( registraOperacionParams.getFechaLiquidacion() );
        vo.setFechaHoraCierreOper(registraOperacionParams.getFechaHoraCierreOper());

		return vo;
	}

    
	public RegistraOperacionParams construyeYCalculaOperacionDinero (TraspasoLibrePagoDTO traspasoLibrePago) {
	       boolean encontroError = false;
	        RegistraOperacionParams registraOperacionParams = new RegistraOperacionParams();

	        // --- AQUI SE LLEVA A CABO LA INSERCION DE LA OPERACION

	        // --- Se deja fijo el ROL DEL AGENTE FIRMADO de Traspasante (T)
	        registraOperacionParams.setRol("T");

	        registraOperacionParams.setFechaConcertacion(traspasoLibrePago.getFechaConcertacion());
	        registraOperacionParams.setFechaHoraCierreOper(traspasoLibrePago.getFechaHoraCierreOper());
	        // --- TIPO DE OPERACION, TRASPASO LIBRE DE PAGO ("T")
	        registraOperacionParams.setClaveReporto("T");
	        
	        // --- AGENTE TRASPASANTE
	        registraOperacionParams
	                .setTraspasante(DTOAssembler.crearAgenteVO(traspasoLibrePago.getPosicionTraspasante().getCuenta()));

	        // --- AGENTE DEL RECEPTOR
	        registraOperacionParams.setReceptor(DTOAssembler.crearAgenteVO(traspasoLibrePago.getCuentaReceptor()));
	        registraOperacionParams.setEmision(new EmisionVO());

	        // --- EMISION
	        if (traspasoLibrePago.getPrecioVector() != null) {
	            registraOperacionParams.getEmision().setPrecioVector(new BigDecimal(traspasoLibrePago.getPrecioVector()));
	        }
	        // --- SALDO DISPONIBLE
	        if (traspasoLibrePago.getSaldoDisponible() != null) {
	            registraOperacionParams.getEmision().setSaldoDisponible(
	                    new BigDecimal(traspasoLibrePago.getSaldoDisponible().doubleValue()));
	        }
	        registraOperacionParams.getEmision().setIdTipoValor(
	                traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
	        registraOperacionParams.getEmision().setEmisora(
	                traspasoLibrePago.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
	        registraOperacionParams.getEmision().setSerie(
	                traspasoLibrePago.getPosicionTraspasante().getEmision().getSerie().getSerie());
	        registraOperacionParams.getEmision().setCupon(traspasoLibrePago.getPosicionTraspasante().getEmision().getCupon());
	        registraOperacionParams.getEmision().setMercado(traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado());

	        // --- DIAS VIGENTES
	        if (traspasoLibrePago.getDiasVigentes() != null) {
	            registraOperacionParams.setDiasVigentes(traspasoLibrePago.getDiasVigentes());
	        }

	        // --- SIMULADO
	        if (traspasoLibrePago.getSimulado() != null) {
	            registraOperacionParams.setSimulado(new BigInteger(traspasoLibrePago.getSimulado().toString()));
	        }

	        // --- NETO EFECTIVO
	        if (traspasoLibrePago.getNetoEfectivo() != null) {
	            registraOperacionParams.setNetoEfectivo(new BigDecimal(traspasoLibrePago.getNetoEfectivo()));
	        }

	        registraOperacionParams.setPlazoReporto(Integer.valueOf("0"));

	        CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();
	        calculos.setTipoOperacion(registraOperacionParams.getClaveReporto());
	        calculos.setCantidadOperadaLeida(traspasoLibrePago.getCantidadOperada() != null ? traspasoLibrePago.getCantidadOperada()
	                .toString() : "0");
	        calculos.setSaldoDisponibleLeido(traspasoLibrePago.getSaldoDisponible() != null ? traspasoLibrePago.getSaldoDisponible()
	                .toString() : "0");
	        calculos.setPlazoRepDiasLeido(registraOperacionParams.getPlazoReporto().toString());
	        calculos.setPlazoRepDiasInhabilitadoLeido("true");
	        calculos.setPlazoLiquidacionHorasInhabilitadoLeido("true");
	        calculos.setPrecioTituloInhabilitadoLeido("true");
	        calculos.setImporteInhabilitadoLeido("true");
	        calculos.setTasaPremioInhabilitadoLeido("true");
	        calculos.setTasaPremioLeido(null);

	        realizarCalculos(calculos);

	        // --- ENCONTRO UN ERROR AL CALCULAR EL SIMULADO
	        if (calculos != null && calculos.getMensajeSimulado() != null
	                && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
	            encontroError = true;
	        }
	        // --- ENCONTRO UN ERROR AL CALCULAR EL SIMULADO
	        if (calculos != null && calculos.getMensajeSimulado() != null
	                && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
	            encontroError = true;
	        }

	        if (encontroError) {
	        } else {
	            // --- CANTIDAD OPERADA
	            if (traspasoLibrePago.getCantidadOperada() != null) {
	                registraOperacionParams.setCantidadOperada(new BigDecimal(traspasoLibrePago.getCantidadOperada()));
	            } else {
	                registraOperacionParams.setCantidadOperada(new BigDecimal(0));
	            }
	            // --- VALOR EN
	            if (traspasoLibrePago.getDivisa() != null) {
	                registraOperacionParams.setDivisa(traspasoLibrePago.getDivisa().getClaveAlfabetica());
	            }
	        }
	        registraOperacionParams.setFechaLiq(calculaFechaLiquidacion(registraOperacionParams.getPlazoLiquidacion()));
	        registraOperacionParams.setFechaRegreso(calculos.getFechaRegreso());
	        
	        if (registraOperacionParams.getFechaRegreso() == null) {
	            registraOperacionParams.setFechaRegreso(getFechasUtilService().getCurrentDate());
	        }
	        
	        return registraOperacionParams;
	}

	
    /** 
     * Realizar los c&aacute;lculos de los siguientes campos: Simulado, Fecha Regreso, Importe y Premio
     * El resultado se almacena en campos de este objeto
     */
	public void realizarCalculos(CalculoCapturaOperacionViewHelper calculo){
		 	this.calcularFechaRegreso(calculo);
	        this.calcularImporte(calculo);
	        this.calcularSimulado(calculo);
	        //this.verificarSignoTasaPremio();
	        this.calcularPremio(calculo);

	        this.verificarRangoPrecioTitulo(calculo);
	}
	
	
	/**
     * 
     * Calcular la Fecha Regreso
     *  
     */
    private void calcularFechaRegreso(CalculoCapturaOperacionViewHelper calculo){
    	calculo.setFechaRegreso(null);
    	calculo.setMensajeFechaRegreso("");
    	
        Boolean pRepDiasAux = null;
        Boolean pLiqHorasAux = null;
        
        if (calculo.getPlazoRepDiasInhabilitadoLeido() != null){
            pRepDiasAux = Boolean.valueOf(calculo.getPlazoRepDiasInhabilitadoLeido());
        }
        
        if (calculo.getPlazoLiquidacionHorasInhabilitadoLeido() != null){
            pLiqHorasAux = Boolean.valueOf(calculo.getPlazoLiquidacionHorasInhabilitadoLeido());
        }
        
        if (pLiqHorasAux != null && !pLiqHorasAux.booleanValue()) {
            //--- AHORA VERIFICO EN CASO DE ESTAR HABILITADO Y CON DATOS
            //--- LOS CAMPOS PLAZO REP.(DIAS) Y PLAZO LIQUIDACION (HORAS)
            //--- QUE LA SUMA DE AMBOS ME DE UN DIA HABIL
            
            verificarSumaPlazoRepDiasConPlazoLiquidacionHoras(calculo,pRepDiasAux,pLiqHorasAux);
        }
        else {
            
            if (pRepDiasAux != null && !pRepDiasAux.booleanValue()){
                //--- CALCULAR LA FECHA DE REGRESO A PARTIR DEL PLAZO REP.(DIAS)
                calcularFechaRegreso2(calculo);
            }
        }
    }
    /**
     * Realiza el c&aacute;lculo del Importe 
     * 
     * @return void 
     * 
     */     
    public void calcularImporte(CalculoCapturaOperacionViewHelper calculo) {

        Boolean pTitAux = null;
        
        calculo.setImporte(null);
        calculo.setMensajeImporte("");

        if (calculo.getPrecioTituloInhabilitadoLeido() != null){
            pTitAux = Boolean.valueOf(calculo.getPrecioTituloInhabilitadoLeido());
        }
        
        if (pTitAux != null && !pTitAux.booleanValue()){
            
            if ((calculo.getCantidadOperada() != null) && (calculo.getPrecioTitulo() != null)) {
                try {
                    
                    
                    
                    BigDecimal importe = (calculo.getPrecioTitulo().multiply(calculo.getCantidadOperada())).setScale(2,BigDecimal.ROUND_HALF_UP);
                    calculo.setImporte(importe);
                    
                }
                catch(NumberFormatException nfe){
                	FacesContext.getCurrentInstance().addMessage("importe",new FacesMessage("El(Los) campo(s) Cantidad Operada y/o Precio T\u00edtulo debe(n) ser \u00e9rico(s)."));
                    
                    calculo.setMensajeImporte("El(Los) campo(s) Cantidad Operada y/o Precio T\u00edtulo debe(n) ser num\u00e9rico(s).");
                }
            }
        }
    }
    /**
     * Calcular Simulado
     * 
     */
    public void calcularSimulado(CalculoCapturaOperacionViewHelper calculo){
        
        calculo.setSimulado(null);
        calculo.setMensajeSimulado("");
        
        if ( calculo.getSaldoDisponibleLeido() != null && calculo.getCantidadOperadaLeida() != null ){
            if ( !calculo.getSaldoDisponibleLeido().trim().equalsIgnoreCase("")) {
                if ( !calculo.getCantidadOperadaLeida().trim().equalsIgnoreCase("") ) {
                    BigDecimal saldoDisponibleAux = new BigDecimal(eliminarFormatoSeparadorMiles(calculo.getSaldoDisponibleLeido().trim()));
                    BigDecimal cantidadOperadaAux = new BigDecimal(eliminarFormatoSeparadorMiles(calculo.getCantidadOperadaLeida().trim()));
                    BigDecimal resultadoAux = saldoDisponibleAux.subtract(cantidadOperadaAux);
                                        
                    calculo.setSimulado(resultadoAux);
                    
                }
            }
        }        
    }
    /**
     * Realiza el c&aacute;lculo del Premio 
     * 
     * @return void 
     * 
     */
    public void calcularPremio(CalculoCapturaOperacionViewHelper calculo) {

        Boolean pTitAux = null;
        Boolean impAux = null;
        Boolean tpAux = null;
        Boolean prdAux = null;
        
        calculo.setPremio(null);
        calculo.setMensajePremio("");
        
        if (calculo.getPrecioTituloInhabilitadoLeido() != null){
            pTitAux = Boolean.valueOf(calculo.getPrecioTituloInhabilitadoLeido());
        }
        
        if (calculo.getImporteInhabilitadoLeido() != null){
            impAux = Boolean.valueOf(calculo.getImporteInhabilitadoLeido());
        }
        
        if (calculo.getTasaPremioInhabilitadoLeido() != null){
            tpAux = Boolean.valueOf(calculo.getTasaPremioInhabilitadoLeido());
        }
        
        if (calculo.getPlazoRepDiasInhabilitadoLeido() != null){
            prdAux = Boolean.valueOf(calculo.getPlazoRepDiasInhabilitadoLeido());
        }
        
        if ( pTitAux != null && 
                impAux != null &&
                tpAux != null &&
                prdAux != null &&
                !pTitAux.booleanValue() &&
                !impAux.booleanValue() &&
                !tpAux.booleanValue() &&
                !prdAux.booleanValue() ){
                
            //--- PRIMERO SE CALCULA EL IMPORTE
            calcularImporte(calculo);
            
            //--- TOMA EL IMPORTE CALCULADO EN LA LINEA ANTERIOR, ES DECIR NO TOMA EL IMPORTE LEIDO DEL JSF
            //--- PORQUE PUEDE SER QUE NO ESTE ACTUALIZADO.
            
           
            
            if ((calculo.getImporte() != null) && (calculo.getTasaPremioLeido() != null) && (calculo.getPlazoRepDiasLeido() != null) &&
                     !calculo.getTasaPremioLeido().trim().equalsIgnoreCase("") &&
                    !calculo.getPlazoRepDiasLeido().trim().equalsIgnoreCase("") ) {
                try {
                    
                    
                    BigDecimal tasaPremioNumerico = new BigDecimal(calculo.getTasaPremioLeido().trim());
                    BigDecimal plazoReportoNumerico = new BigDecimal(calculo.getPlazoRepDiasLeido().trim());
                    
                    /*
                    BigDecimal calculo1 = (importeNumerico.multiply(tasaPremioNumerico)).setScale(2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal dato1 = new BigDecimal(36000.00).setScale(2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal calculo2 = (calculo1.divide(dato1,BigDecimal.ROUND_HALF_UP)).setScale(2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal premio = (calculo2.multiply(plazoReportoNumerico)).setScale(2,BigDecimal.ROUND_HALF_UP);
                    */
                    BigDecimal calculo1 = calculo.getImporte().multiply(tasaPremioNumerico);
                    BigDecimal dato1 = new BigDecimal(36000.00);
                    BigDecimal calculo2 = calculo1.divide(dato1,BigDecimal.ROUND_HALF_UP);
                    BigDecimal premio = (calculo2.multiply(plazoReportoNumerico)).setScale(2,BigDecimal.ROUND_HALF_UP);

                    calculo.setPremio(premio);
                    
                }
                catch(NumberFormatException nfe){
                    calculo.setMensajePremio("El(Los) campo(s) Importe y/o Tasa Premio y/o Plazo Rep.(d\u00edas) debe(n) ser num\u00e9rico(s).");
                    FacesContext.getCurrentInstance().addMessage("premio", new FacesMessage("El(Los) campo(s) Importe y/o Tasa Premio y/o Plazo Rep.(d\u00edas) debe(n) ser num\u00e9rico(s)."));
                    
                }
            }
        }
    }
    /**
     * Verifica que el Precio T&iacute;tulo este entre el rango de Precio m&iacute;nimo
     * y el precio m&aacute;ximo especificado por el tipo valor de la Emisin.
     * Esta validaci&oacute;n se realiza siempre y cuando el Tipo de Operaci&oacute;n
     * sea diferente de Traspaso y que el Precio m&aacute;ximo sea mayor a cero.
     * En caso de ser un Traspaso la &uacute;nica validaci&oacute;n que se realiza es
     * que el Precio T&iacute;tulo sea mayor que cero.
     *
     */
    public void verificarRangoPrecioTitulo(CalculoCapturaOperacionViewHelper calculo){
        
    	calculo.setMensajePrecioTitulo("");
        
        if ( calculo.getPrecioTitulo() != null  &&
        		calculo.getTipoOperacion() != null && !calculo.getTipoOperacion().trim().equals("") &&
             !calculo.getTipoOperacion().trim().equalsIgnoreCase("-1") && calculo.getEmision() != null && 
             calculo.getEmision().getTipoValor() != null && calculo.getEmision().getTipoValor().getClaveTipoValor() != null &&
             !calculo.getEmision().getTipoValor().getClaveTipoValor().trim().equalsIgnoreCase("")){
            
            //--- Verifico el Tipo de Operacion
            if (calculo.getTipoOperacion().equalsIgnoreCase("T")){
                //--- EL TIPO DE OPERACION ES TRASPASO
                //--- POR LO TANTO SOLO VALIDO QUE EL PRECIO TITULO SEA MAYOR QUE CERO
                
               
                
                if ( !(calculo.getPrecioTitulo().compareTo(new BigDecimal("0")) == 1) ){
                    
                    calculo.setMensajePrecioTitulo("El Precio \u00edtulo no puede ser cero.");
                    FacesContext.getCurrentInstance().addMessage("precioTitulo", new FacesMessage("El Precio T\u00edtulo no puede ser cero."));
                    
                }
            }
            else {
                //--- PARA LOS TIPOS DE OPERACION DIFERENTES A TRASPASO REALIZO
                //--- VALIDACION DE RANGO
//                com.indeval.persistence.vo.PrecioTituloVO rangoPrecioTitulo = precioTituloDao.recuperarRangoPrecio(this.getEmision().getIdTipoValor());
//                
//                if (rangoPrecioTitulo != null){
//                    //--- SI EL PRECIO MAXIMO ES MAYOR A CERO SE APLICA LA VALIDACION
//                    //--- EN CASO CONTRARIO NO SE VALIDA EL PRECIO TITULO
//                    
//                    if (rangoPrecioTitulo.getPrecioMaximo().compareTo(new BigDecimal("0")) == 1){
//                        
//                        BigDecimal precioTituloAux = new BigDecimal(this.getPrecioTitulo());
//                        
//                        if( !((precioTituloAux.compareTo(rangoPrecioTitulo.getPrecioMaximo()) == 0) | (precioTituloAux.compareTo(rangoPrecioTitulo.getPrecioMinimo()) == 0))){
//                            
//                            if ( !((precioTituloAux.compareTo(rangoPrecioTitulo.getPrecioMaximo()) == -1) &&
//                                 (precioTituloAux.compareTo(rangoPrecioTitulo.getPrecioMinimo()) == 1))){
//                                
//                                calculo.setMensajePrecioTitulo("El Precio T\u00edtulo esta fuera de rango.");
//                                FacesContext.getCurrentInstance().addMessage("precioTitulo", new FacesMessage("El Precio T\u00edtulo esta fuera de rango."));
//                               
//                            }
//                        }
//                        
//                    }
//                }
            }
        }
    }
    /**
     * Verifica que la suma de Plazo Rep.(D&iacute;as) y de Plazo Liquidaci&oacute;n (Horas) de un 
     * d&iacute;a h&aacute;bil.
     * 
     * @return void 
     * 
     */
    
    public void verificarSumaPlazoRepDiasConPlazoLiquidacionHoras(CalculoCapturaOperacionViewHelper calculo,Boolean plazoRepDiasInhabilitadoLeido,
                                               Boolean plazoLiquidacionHorasInhabilitadoLeido) {
        String plazoRepDiasAux = "";
        String plazoLiquidacionHorasAux = "";
        
        if (plazoRepDiasInhabilitadoLeido != null && plazoLiquidacionHorasInhabilitadoLeido != null &&
                !plazoRepDiasInhabilitadoLeido.booleanValue() && 
                !plazoLiquidacionHorasInhabilitadoLeido.booleanValue() ){
            
            plazoRepDiasAux = calculo.getPlazoRepDiasLeido();
            plazoLiquidacionHorasAux = calculo.getPlazoLiquidacionHorasLeido();
            
            //--- COLOCO ESTE IF PORQUE PUEDE SER QUE AL HACER SUBMIT, LA PANTALLA
            //--- ESTUVIERA EN UN TIPO DE OPERACION QUE HAYA INHABILITADO EL CAMPO
            //--- "PLAZO LIQUIDACION(HORAS)" Y CUANDO PASE A UN TIPO DE OPERACION QUE
            //--- LO HABILITE ESTE TEDRA UN NULO COMO VALOR ENTONCES YO LO COLOCO
            //--- A "24 HRS." PARA QUE HAGA EL CALCULO DE FECHA DE REGRESO DE FORMA CORRECTA
            //--- Y CON ESE VALOR DE "24 HRS."
            
            if (plazoLiquidacionHorasAux == null && calculo.getPlazoLiquidacionHorasInhabilitado().booleanValue() == Boolean.FALSE){
                calculo.setPlazoLiquidacionHoras(0);
                plazoLiquidacionHorasAux = "0";
            }
            
            if ((plazoRepDiasAux != null) && (plazoLiquidacionHorasAux != null) &&
                    !plazoRepDiasAux.trim().equalsIgnoreCase("") && !plazoLiquidacionHorasAux.trim().equalsIgnoreCase("") ) {
                
                try {
                    double numeroDias = new Double(plazoLiquidacionHorasAux.trim()).doubleValue() / 24.0;
                    long plazoRepDiasAux2 = Long.valueOf(plazoRepDiasAux.trim()).longValue();
                    
                    if ( plazoRepDiasAux2 < 0 ){
                        calculo.setFechaRegreso(null);
                        
                        calculo.setMensajeFechaRegreso("El Plazo Rep.(d\u00edas) debe ser mayor o igual a 0.");
                        FacesContext.getCurrentInstance().addMessage("plazoReporto", new FacesMessage("El Plazo Rep.(d\u00edas) debe ser mayor o igual a 0."));
                        
                    }
                    else {
                        try {
                            
                            Calendar c = Calendar.getInstance();
                            int dia = c.get( Calendar.DAY_OF_MONTH );
                            int mes = c.get( Calendar.MONTH );
                            int anio = c.get( Calendar.YEAR );
                            GregorianCalendar f2 = new GregorianCalendar(0,0,0);
                            f2.set(anio,mes,dia,0,0,0);
                            
                            Date fechaSumadaConPlazoLiquidacion = mercadoDineroService.agregarDiasHabiles(f2.getTime(),(int)numeroDias);
                            Date fechaSumadaConPlazoReporto = UtilFecha.addDays(fechaSumadaConPlazoLiquidacion, (int) plazoRepDiasAux2);
                                                       
                            
                            if ( diaInhabilDaliDao.esInhabil(fechaSumadaConPlazoReporto) ) {
                                calculo.setFechaRegreso(null);
                                calculo.setMensajeFechaRegreso("La suma de Plazo Rep.(D\u00edas) y de Plazo Liquidaci\u00f3n (Horas) no da un d\u00eda h\u00e1bil.");
                                calculo.setMensajeFechaRegreso("El Plazo Rep.(d\u00edas) debe ser mayor o igual a 0.");
                                FacesContext.getCurrentInstance().addMessage("plazoReporto", new FacesMessage("La suma de Plazo Rep.(D\u00edas) y de Plazo Liquidaci\u00f3n (Horas) no da un d\u00eda h\u00e1bil."));

                            }
                            else {
                                calculo.setFechaRegreso(fechaSumadaConPlazoReporto);
                                calculo.setMensajeFechaRegreso("");
                            }
                        }
                        catch (BusinessException be){
                        	calculo.setFechaRegreso(null);
                            
                        	calculo.setMensajeFechaRegreso("Existe un problema con el servicio de MD.");
                            StringBuffer mensaje = new StringBuffer();
                            mensaje.append(be.toString());
                            
                            be.printStackTrace();
                            logger.error(mensaje.toString());
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje.toString()));
                            FacesContext.getCurrentInstance().addMessage("plazoReporto",new FacesMessage("Existe un problema con el servicio de MD."));
                          
                        }
                    }
                }
                catch(NumberFormatException nfe){
                	calculo.setFechaRegreso(null);
                	calculo.setMensajeFechaRegreso("El Plazo Rep.(d\u00edas) debe ser num\u00e9rico.");
                	FacesContext.getCurrentInstance().addMessage("plazoReporto",new FacesMessage("El Plazo Rep.(d\u00edas) debe ser num\u00e9rico."));
                   
                }
            }
        }
    }
	
    /**
     * Realiza el c&aacute;lculo de la Fecha de Regreso  
     * 
     * @return void 
     * 
     */
    public void calcularFechaRegreso2(CalculoCapturaOperacionViewHelper calculo) {
        
        if (calculo.getPlazoRepDiasLeido() != null && !calculo.getPlazoRepDiasLeido().trim().equalsIgnoreCase("") ){
            Calendar c = Calendar.getInstance();
            int dia = c.get( Calendar.DAY_OF_MONTH );
            int mes = c.get( Calendar.MONTH );
            int anio = c.get( Calendar.YEAR );
            GregorianCalendar f2 = new GregorianCalendar(0,0,0);
            f2.set(anio,mes,dia,0,0,0);
            Date nuevaFechaRegresoCalculada = UtilFecha.addDays(f2.getTime(), Integer.parseInt(calculo.getPlazoRepDiasLeido().trim()));
            
            calculo.setFechaRegreso(nuevaFechaRegresoCalculada);
            
            calculo.setMensajeFechaRegreso("");
            if ( diaInhabilDaliDao.esInhabil(nuevaFechaRegresoCalculada) ) {
            	calculo.setMensajeFechaRegreso("La fecha es d\u00eda inh\u00e1bil");
            	FacesContext.getCurrentInstance().addMessage("fechaRegreso",new FacesMessage("La fecha es d\u00eda inh\u00e1bil"));
            }
        }
    }
    /**
     * Eliminar formato de separador de miles (,)
     * 
     * @return String Monto sin separador de miles (,)
     * 
     */
    public String eliminarFormatoSeparadorMiles(String montoFormateado){
        String cadena = montoFormateado;
        String resultado = "";
        if (cadena != null && ( cadena.trim().length() > 0 )){
            resultado = cadena.replaceAll(",","");
        }
        else {
            resultado = null;
        }
        
        return resultado;
    }

	public DiaInhabilDaliDao getDiaInhabilDao() {
		return diaInhabilDaliDao;
	}

	public void setDiaInhabilDao(DiaInhabilDaliDao diaInhabilDaliDao) {
		this.diaInhabilDaliDao = diaInhabilDaliDao;
	}
}
