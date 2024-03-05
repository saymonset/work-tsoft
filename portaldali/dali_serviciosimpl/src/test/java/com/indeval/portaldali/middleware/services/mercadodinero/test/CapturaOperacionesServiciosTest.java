package com.indeval.portaldali.middleware.services.mercadodinero.test;


import java.math.BigDecimal;
import java.util.Date;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;

/**
 * Clase de prueba unitaria para verificar la integracicn del servicio de envo
 * de operaciones
 * 
 * @author Juan Carlos Huizar Moreno.
 * 
 */
public class CapturaOperacionesServiciosTest extends
		BaseTestCase{

	

	public void testColocacionPrimaria() {
		/** Representa el detalle del usuario logueado */
		
		MercadoDineroService servicio = (MercadoDineroService)getBean("mercadoDineroService");
		EnvioOperacionesService envioService = (EnvioOperacionesService)getBean("envioOperacionesService");
		
		RegistraOperacionParams params = obtenerRegistraOperacionParams();
		
		try {
			
			String res = servicio.validaTVMD("94");
			System.out.println(res);
			res = servicio.validaGetColocacionPrimariaRecompraBusinessRules(params);
			System.out.println(res);
		} catch (BusinessException e) {

			e.printStackTrace();

		}
		
		try {
			
			Date resDate = servicio.agregarDiasHabiles(new Date(), 15);
			System.out.println(resDate);
		} catch (BusinessException e) {

			e.printStackTrace();

		}
		
		
		

	}

	
	public void testFondeoCuentaPropia(){
		MercadoDineroService servicio = (MercadoDineroService)getBean("mercadoDineroService");
		EnvioOperacionesService envioService = (EnvioOperacionesService)getBean("envioOperacionesService");
		String res = null;
		
		RegistraOperacionParams params = obtenerRegistraOperacionParams();
		params.setPlazoLiquidacion(Integer.valueOf("0"));
		
		//params.getReceptor().setId(params.getTraspasante().getId());
		//params.getReceptor().setFolio(params.getTraspasante().getFolio());
		params.setClaveReporto("F");
		params.getTraspasante().setCuenta("0030");
		//params.getReceptor().setCuenta("0030");
		params.setFechaConcertacion(new Date());
		try {
			
			res = servicio.validaTVMD("94");
			System.out.println(res);
			
		} catch (BusinessException e) {

			e.printStackTrace();

		}
		try {
			
			res = servicio.validaRegistraOperacionBusinessRules(params);
			System.out.println(res);
			
			TraspasoContraPagoVO tp = inicializaTraspasoContrapagoVO(params);
			
			envioService.grabaOperacion(tp, res, false, null, null, null);
			
		} catch (BusinessException e) {

			e.printStackTrace();

		}
		
	}
	
	
	public void testReportoNominal(){
		MercadoDineroService servicio = (MercadoDineroService)getBean("mercadoDineroService");
		EnvioOperacionesService envioService = (EnvioOperacionesService)getBean("envioOperacionesService");
		String res = null;
		
		RegistraOperacionParams params = obtenerRegistraOperacionParams();
		params.setPlazoLiquidacion(Integer.valueOf("1"));
		params.setPlazoReporto(Integer.valueOf("1"));
		params.setTasaPremio(new BigDecimal("0.5"));
		params.setClaveReporto("R");
		params.getTraspasante().setCuenta("0030");
		
		params.setFechaConcertacion(new Date());
		
		params.getEmision().setIdTipoValor("1I");
		params.getEmision().setSerie("*");
		params.getEmision().setIsin("US4642874659");
		params.getEmision().setEmisora("EFA");
		params.setFechaRegreso(new Date());
		try {
			
			res = servicio.validaTVMD("94");
			System.out.println(res);
			
		} catch (BusinessException e) {

			e.printStackTrace();

		}
		try {
			
			//zzres = servicio.validaRegistraOperacionBusinessRules(params);
			System.out.println(res);
			
			TraspasoContraPagoVO vo = inicializaTraspasoContrapagoVO(params);
			vo.setTasaNegociada(new Double(params.getTasaPremio().doubleValue()));
            vo.setTasaReferencia(new Double(params.getTasaPremio().doubleValue()));
            System.out.println("TASA PREMIO["+vo.getTasaNegociada()+"]");
            vo.setTasaFija(true);
            vo.setFechaVencimiento(params.getFechaRegreso());
			envioService.grabaOperacion(vo, res, false, null, null, null);
			
		} catch (BusinessException e) {

			e.printStackTrace();

		}
	}
	
	public void testTraspasoLibreDePago(){
		MercadoDineroService servicio = (MercadoDineroService)getBean("mercadoDineroService");
		EnvioOperacionesService envioService = (EnvioOperacionesService)getBean("envioOperacionesService");
		String res = null;
		
		RegistraOperacionParams params = obtenerRegistraOperacionParams();
		params.setPlazoLiquidacion(Integer.valueOf("1"));
		params.setPlazoReporto(Integer.valueOf("1"));
		params.setTasaPremio(new BigDecimal("0.5"));
		params.setClaveReporto("T");
		params.getTraspasante().setCuenta("0030");
		
		params.setFechaConcertacion(new Date());
		
		
		try {
			
			res = servicio.validaTVMD("94");
			System.out.println(res);
			
		} catch (BusinessException e) {

			e.printStackTrace();

		}
		try {
			
			//res = servicio.validaRegistraOperacionBusinessRules(params);
			System.out.println(res);
			
			TraspasoContraPagoVO vo = inicializaTraspasoContrapagoVO(params);
			vo.setReferenciaOperacion(res);                    
            vo.setTipoInstruccion("T");
            //TODO Cotejar con el usuario si las 2 siguientes tasas a persistir son realmente la tasa premio proveniente de la pantalla
            vo.setTasaNegociada(new Double(params.getTasaPremio().doubleValue()));
            vo.setTasaReferencia(new Double(params.getTasaPremio().doubleValue()));
            vo.setTasaFija(true);
            
            vo.setFechaRegistro(new Date());
            //Fecha de Liquidacion =  Fecha de liquidacion + plazo (24,48); convertir a dias habiles
            
            vo.setFechaRegistro(new Date());
            //Fecha de Vencimiento  =  Fecha de liquidacion + plazo en # dias naturales (int)
            vo.setFechaVencimiento(new Date());
            vo.setPrecio(new BigDecimal( "1" ));
            vo.setISIN(params.getEmision().getIsin());
            
            //pesos PE, DL dlares
            vo.setDivisa("MXN");
			
			envioService.grabaOperacion(vo, res, false, null, null, null);
			
		} catch (BusinessException e) {

			e.printStackTrace();

		}
	}
	
	
	
	public void testVenta(){
		MercadoDineroService servicio = (MercadoDineroService)getBean("mercadoDineroService");
		EnvioOperacionesService envioService = (EnvioOperacionesService)getBean("envioOperacionesService");
		String res = null;
		
		RegistraOperacionParams params = obtenerRegistraOperacionParams();
		params.setPlazoLiquidacion(Integer.valueOf("1"));
		params.setPlazoReporto(Integer.valueOf("1"));
		params.setTasaPremio(new BigDecimal("0.5"));
		params.setClaveReporto("T");
		params.getTraspasante().setCuenta("0030");
		
		params.setFechaConcertacion(new Date());
		
		
		try {
			
			res = servicio.validaTVMD("94");
			System.out.println(res);
			
		} catch (BusinessException e) {

			e.printStackTrace();

		}
		try {
			
			//res = servicio.validaRegistraOperacionBusinessRules(params);
			System.out.println(res);
			
			TraspasoContraPagoVO vo = inicializaTraspasoContrapagoVO(params);
			vo.setReferenciaOperacion(res);                    
            vo.setTipoInstruccion("T");
           
			envioService.grabaOperacion(vo, res, false, null, null, null);
			
		} catch (BusinessException e) {

			e.printStackTrace();

		}
	}
	/**
	 * Crea un objeto de parámetros que se puede utilizar con algunos datos ya cargados
	 * @return Objeto de parámetros
	 */
	public RegistraOperacionParams obtenerRegistraOperacionParams(){
		AgenteVO agente = new AgenteVO();
		agente.setId("01");
		agente.setFolio("003");
		RegistraOperacionParams  params = new RegistraOperacionParams();
		params.setTraspasante(new AgenteVO());
		params.setReceptor(new AgenteVO());
		params.getTraspasante().setCuenta("5000");
		params.getTraspasante().setId("01");
		params.getTraspasante().setFolio("001");
		params.getReceptor().setCuenta("0307");
		params.getReceptor().setId("01");
		params.getReceptor().setFolio("003");
		params.setClaveReporto("J");
		params.setFechaRegreso(new Date());
		params.setRol("TRASPASANTE");
		params.setEmision(new EmisionVO());
		params.getEmision().setIsin("MX91CE020060");
		params.getEmision().setEmisora("CEMEX");
		params.getEmision().setIdTipoValor("91");
		params.getEmision().setSerie("02-4");
		params.getEmision().setCupon("0011");
		params.getEmision().setMercado("PB");
		params.setCantidadOperada(new BigDecimal("15"));
		params.setPrecioTitulo(new BigDecimal(1));
		return params;
	}


	/**
     * Crea un objeto TraspasoContraPagoVO y lo llena con los valores del objeto registraOperacionParams.
     * Este m&eacute;todo tiene como objetivo eliminar la redundancia de codigo que puede existir al momento de incializar
     * el objeto vo en cada una de los tipos de operaciones 
     *  
     * @param registraOperacionParams Objeto de donde se tomara la informacion
     * @return vo con los campos inicializados en base al objeto proporcionado como parametros
     */
    private com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO inicializaTraspasoContrapagoVO(RegistraOperacionParams registraOperacionParams) {
        
    	TraspasoContraPagoVO vo = new TraspasoContraPagoVO();
    	
		vo.setTipoValor(registraOperacionParams.getEmision().getIdTipoValor().trim());
        vo.setEmisora(registraOperacionParams.getEmision().getEmisora().trim());
        vo.setSerie(registraOperacionParams.getEmision().getSerie().trim());
        vo.setCupon(registraOperacionParams.getEmision().getCupon().trim());
        vo.setCantidadTitulos(new Long(registraOperacionParams.getCantidadOperada().longValue()));
        vo.setIdFolioCtaReceptora(   registraOperacionParams.getReceptor().getId()+ 
                registraOperacionParams.getReceptor().getFolio() + 
                registraOperacionParams.getReceptor().getCuenta());
        vo.setIdFolioCtaTraspasante( registraOperacionParams.getTraspasante().getId() + 
                registraOperacionParams.getTraspasante().getFolio() +
                registraOperacionParams.getTraspasante().getCuenta());
        vo.setFechaLiquidacion(registraOperacionParams.getFechaLiq());
        vo.setFechaConcertacion(registraOperacionParams.getFechaConcertacion());// <-Extra
        vo.setFechaRegistro( registraOperacionParams.getFechaConcertacion() );
        vo.setMonto(registraOperacionParams.getImporte());

		return vo;
	}
	

}
