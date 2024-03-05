/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.modelo.util.UtilsVOCatalogo;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;

/**
 * Implementacion de los servicios de BusinessRulesCatalogo
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BusinessRulesCatalogoServiceImpl implements BusinessRulesCatalogoService {

    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(BusinessRulesCatalogoServiceImpl.class);

    /** Bean para acceso al properties de mensaje de errores */
    private MessageResolver errorResolver;

    /** Bean de acceso a UtilService */
    private UtilServices utilService;    

//    /** Bean de acceso a valorDao */
//    private ValorDao dineroValorDao;
    
    /** Bean de acceso a instrumentoDaliDao */
    private InstrumentoDaliDao instrumentoDaliDao;
    
//    /**Bean de acceso a valorDao */
//    private com.indeval.persistence.portallegado.capital.dao.ValorDao valorDao;
    
	/*
     * NOTAS DE DESARROLLO:
     * exec catalogo..UP_libera_city @id1 @fol1 @cta1 @id2
     * @fol2 @cta2 @tv @emis @serie @cupon @cant @fol_transm @m_dine @usuario
     * @fecha_liq @mercado @origen @origen_aplicac @nom_us @llave_folio
     * @nom_area @fol_cont @entrega @nombre_cuenta @no_cuenta @mt
     * @desc_contrapar @cuenta_contrap @fecha_ejec @fecha_oper @dep_liq
     * @custodio
     */
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.BusinessRulesCatalogoService#liberaOperaciones(LiberaOperacionesParams)
     */
    public boolean liberaOperaciones(LiberaOperacionesParams params) throws BusinessException {

        logger.info("Entrando a BusinessRulesCatalogoServiceImpl.liberaOperaciones");
        
        utilService.validaDTONoNulo(params, "");
        
        return true;
    }

    /*
     * NOTAS DE DESARROLLO:
     * exec catalogo..UP_traslp @id1 @fol1 @cta1 @t_cta1
     * @llave @id2 @fol2 @cta2 @t_cta2 @tv @emis @serie @cupon @cant_op
     * @clave_reporto @precio_titulo @fecha_liq @liq @mercado @origen
     * @origen_apl @folio_desc @nom_area @nom_us @divisa @usu_real
     * @fecha_adquisic @precio_adquisi @cliente @curp_rfc @sociedad_serie
     * @nombre_usuario
     */
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.BusinessRulesCatalogoService#registraTraspasosYCompensaValores(RegistraTraspasosYCompensaValoresParams)
     */
    public boolean registraTraspasosYCompensaValores(RegistraTraspasosYCompensaValoresParams params)
            throws BusinessException {

        logger.info("Entrando a BusinessRulesCatalogoServiceImpl.registraTraspasosYCompensaValores");
        
        utilService.validaDTONoNulo(params, "");
        
        /*
         * if (@divisa is null) select @divisa = 'PE'
         */
        boolean asignaDivisaPE = false;
        if(params.getDivisa() == null) {
        	//hay que asignar la divisa a PE
        	asignaDivisaPE = true;
        }
        /*
         * select @base=uso from instrumentos where tv=@tv
         */
        com.indeval.portaldali.persistence.model.Instrumento cinstrumento = instrumentoDaliDao.getInstrumento(params.getTv());
        com.indeval.portaldali.persistence.vo.InstrumentoVO instrumento = UtilsVOCatalogo.getInstanceInstrumento(cinstrumento);
        
//        /*
//         * if (@base ='dine')
//         *  begin  BDDINERO 
//         * 
//         */
//        if(Constantes.DINE.equalsIgnoreCase(instrumento.getUso())) {
//
//        	ValorPK valorPK = new ValorPK();
//        	valorPK.setIdInst(params.getId1());
//        	valorPK.setFolioInst(params.getFol1());
//        	valorPK.setCuenta(params.getCta1());
//        	valorPK.setTv(params.getTv());
//        	valorPK.setEmisora(params.getEmis());
//        	valorPK.setSerie(params.getSerie());
//        	valorPK.setCupon(params.getCupon());
//
//        	Valor valor = dineroValorDao.getValor(valorPK);
//        	
//        	/*
//        	 * OBTIENE SALDO_DISP DEL TRASPASANTE DESPUES DE AFECTARLO
//        	 * select @saldis_d=saldo_disponible from bddinero..valores
//        	 * where id_inst=@id1 and folio_inst=@fol1 and cuenta=@cta1
//        	 * and tv=@tv and emisora=@emis and serie=@serie and cupon=@cupon
//        	 * OBTIENE SALDO_DISP DEL TRASPASANTE ANTES DE AFECTARLO
//        	 * select @saldis_a=(@saldis_d + @cant_op) 
//        	 * if ((@saldis_a - @cant_op)!=@saldis_d)
//        	 * begin
//        	 * rollback transaction
//        	 * print 'Error al actualizar el saldo del traspasante'
//        	 * select @status= -202  /* Traspaso no efectuado error no identificado
//        	 * select @status
//        	 * return
//        	 * end
//        	 * 
//        	 */
//
//        	BigDecimal saldoDisA = valor.getSaldoDisponible().add(params.getCantOp());
//
//
//        	if(!(saldoDisA.subtract(params.getCantOp()).compareTo(valor.getSaldoDisponible()) == 0)) {
//        		throw new BusinessException(errorResolver.getMessage("JBR-202"), "JBR-202");
//        	}
//        	/*
//        	 *  select @pos_act=saldo_disponible from bddinero..valores 
//        	 *  where id_inst=@id1 and folio_inst=@fol1 and cuenta=@cta1 and
//        	 *  tv=@tv and emisora=@emis and serie=@serie and cupon=@cupon
//        	 *  select @pos_ant=(@pos_act + @cant_op)
//        	 *  if (@pos_act < 0)
//        	 *  begin
//        	 *  rollback transaction
//        	 *  print 'Error saldo_disponible < cantidad_operada'
//        	 *  select @status -181
//        	 *  select @status
//        	 *  return
//        	 *  end
//        	 * 
//        	 */
//        	if(valor != null && valor.getSaldoDisponible().doubleValue() < params.getCantOp().doubleValue() ) {
//        		throw new BusinessException(errorResolver.getMessage("JBR-181"), "JBR-181");
//        	}
//
//        	valorPK.setIdInst(params.getId2());
//        	valorPK.setFolioInst(params.getFol2());
//        	valorPK.setCuenta(params.getCta2());
//        	/*valorPK.setTv(params.getTv());
//        	valorPK.setEmisora(params.getEmis());
//        	valorPK.setSerie(params.getSerie());
//        	valorPK.setCupon(params.getCupon());*/
//
//
//        	Valor valor2 = dineroValorDao.getValor(valorPK);
//        	
//        	/*
//        	 * OBTIENE SALDO_DISP DEL RECEPTOR ANTES DE AFECTARLO
//        	 * select @saldis_a=(@saldis_d - @cant_op)
//        	 * if ((@saldis_a + @cant_op)!=@saldis_d)
//        	 * begin
//        	 * rollback transaction
//        	 * print 'Error al actualizar el saldo del receptor'
//        	 * select @status= -203  /* Traspaso no efectuado error no identificado
//        	 * select @status
//        	 * returnend
//        	 * 
//        	 */
//        	
//        	saldoDisA = valor2.getSaldoDisponible().subtract(params.getCantOp());
//
//        	if(!(saldoDisA.add(params.getCantOp()).compareTo(valor2.getSaldoDisponible()) == 0)) {
//        		throw new BusinessException(errorResolver.getMessage("JBR-203"), "JBR-203");
//        	}
//        }
//        else {
//        	/* BDCAPTAL */
//        	com.indeval.persistence.portallegado.capital.modelo.ValorPK valorPK = new com.indeval.persistence.portallegado.capital.modelo.ValorPK();
//        	valorPK.setIdInst(params.getId1());
//        	valorPK.setFolioInst(params.getFol1());
//        	valorPK.setCuenta(params.getCta1());
//        	valorPK.setTv(params.getTv());
//        	valorPK.setEmisora(params.getEmis());
//        	valorPK.setSerie(params.getSerie());
//        	valorPK.setCupon(params.getCupon());
//        	
//        	/*
//        	 * OBTIENE SALDO_DISP DEL TRASPASANTE DESPUES DE AFECTARLO 
//        	 * select @saldis_d=saldo_disponible from bdcaptal..valores
//        	 * where id_inst=@id1 and folio_inst=@fol1 and cuenta=@cta1
//        	 * and tv=@tv and emisora=@emis and serie=@serie and cupon=@cupon
//        	 * OBTIENE SALDO_DISP DEL TRASPASANTE ANTES DE AFECTARLO 
//        	 * select @saldis_a=(@saldis_d + @cant_op) 
//        	 * if ((@saldis_a - @cant_op)!=@saldis_d)
//        	 * begin
//        	 * rollback transaction
//        	 * print 'Error al actualizar el saldo del traspasante'
//        	 * select @status= -202  /* Traspaso no efectuado error no identificado
//        	 * select @status
//        	 * return
//        	 * end
//        	 * 
//        	 */
//        	com.indeval.persistence.portallegado.capital.modelo.Valor valor = valorDao.getByPK(valorPK);
//        	
//        	BigDecimal saldoDispA = valor.getSaldoDisponible().add(params.getCantOp());
//        	
//        	if(!(saldoDispA.subtract(params.getCantOp()).compareTo(valor.getSaldoDisponible()) == 0)) {
//        		throw new BusinessException(errorResolver.getMessage("JBR-202"), "JBR-202");
//        	}
//        	
//        	/*
//        	 *  select @pos_act=saldo_disponible from bdcaptal..valores 
//        	 *  where id_inst=@id1 and folio_inst=@fol1 and cuenta=@cta1 and
//        	 *  tv=@tv and emisora=@emis and serie=@serie and cupon=@cupon
//        	 *  select @pos_ant=(@pos_act + @cant_op)
//        	 *  if (@pos_act < 0)
//        	 *  begin
//        	 *  rollback transaction
//        	 *  print 'Error saldo_disponible < cantidad_operada'
//        	 *  select @status= -181
//        	 *  select @status
//        	 *  return
//        	 *  end
//        	 */
//        	if(valor != null && valor.getSaldoDisponible().doubleValue() < params.getCantOp().doubleValue()){
//        		throw new BusinessException(errorResolver.getMessage("JBR-181"), "JBR-181");
//        		
//        	}
//        	
//        	valorPK.setIdInst(params.getId2());
//        	valorPK.setFolioInst(params.getFol2());
//        	valorPK.setCuenta(params.getCta2());
//        	
//        	com.indeval.persistence.portallegado.capital.modelo.Valor valor2 = valorDao.getByPK(valorPK);
//        	
//        	/*
//        	 * OBTIENE SALDO_DISP DEL RECEPTOR DESPUES DE AFECTARLO
//        	 * select @saldis_d=saldo_disponible from bdcaptal..valores
//        	 * where id_inst=@id2 and folio_inst=@fol2 and cuenta=@cta2 
//        	 * and tv=@tv and emisora=@emis and serie=@serie and cupon=@cupon
//        	 * OBTIENE SALDO_DISP DEL RECEPTOR ANTES DE AFECTARLO 
//        	 * select @saldis_a=(@saldis_d - @cant_op)
//        	 * if ((@saldis_a + @cant_op)!=@saldis_d)
//        	 * begin
//        	 * rollback transaction
//        	 * print 'Error al actualizar el saldo del receptor'
//        	 * select @status -203  /* Traspaso no efectuado error no identificado
//        	 * select @status
//        	 * return
//        	 * end
//        	 */
//        	
//        	saldoDispA = valor2.getSaldoDisponible().subtract(params.getCantOp());
//        	
//        	if(!(saldoDispA.add(params.getCantOp()).compareTo(valor2.getSaldoDisponible()) == 0)) {
//        		throw new BusinessException(errorResolver.getMessage("JBR-203"), "JBR-203");
//        	}
//        	
//        }
        
        return asignaDivisaPE;
    }

    /**
     * Inyeccion del bean errorResolver
     *
     * @param errorResolver
     */
    public void setErrorResolver(MessageResolver errorResolver) {
        this.errorResolver = errorResolver;
    }
    
    /**
     * Inyeccion del bean utilService
     *
     * @param utilService
     */
    public void setUtilService(UtilServices utilService) {
        this.utilService = utilService;
    }

//	/**
//	 * @param dineroValorDao the dineroValorDao to set
//	 */
//	public void setDineroValorDao(ValorDao dineroValorDao) {
//		this.dineroValorDao = dineroValorDao;
//	}

    /**
     * @param instrumentoDaliDao the instrumentoDaliDao to set
     */
    public void setInstrumentoDao(InstrumentoDaliDao instrumentoDaliDao) {
        this.instrumentoDaliDao = instrumentoDaliDao;
    }

	public MessageResolver getErrorResolver() {
		return errorResolver;
	}

	public UtilServices getUtilService() {
		return utilService;
	}

	public InstrumentoDaliDao getInstrumentoDao() {
		return instrumentoDaliDao;
	}

//    /**
//	 * @param valorDao the valorDao to set
//	 */
//	public void setValorDao(com.indeval.persistence.portallegado.capital.dao.ValorDao valorDao) {
//		this.valorDao = valorDao;
//	}

}
