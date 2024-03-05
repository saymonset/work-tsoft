/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero.util;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.indeval.portaldali.middleware.services.mercadodinero.RegistroEstatusOperacionesVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.AgentePersistence;
import com.indeval.portaldali.persistence.vo.EmisionPK;
import com.indeval.portaldali.persistence.vo.EmisionPersistence;
import com.indeval.portaldali.persistence.vo.Mdintran;

/**
 * Clase utilitaria que contiene metodos para el manejo o manipulacion de
 * los VOs utilizados por MercadoDineroService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class UtilsVOMercadoDinero {
    
    /** Log de Clase. */
    private static final Logger logger = LoggerFactory.getLogger(UtilsVOMercadoDinero.class);
    
    /**
     * Construye una instancia de Mdintran
     *
     * @return Mdintran
     */
    public static  Mdintran getInstanceMdintran() {

        logger.info("Entrando a UtilsVOMercadoDinero.getInstanceMdintran()");
        
        /* Se construye el objeto Mdintran */
        Mdintran mdintran = new Mdintran();

        /* Se contruye el agente y su PK y se settean */
        AgentePersistence agentePersistence = new AgentePersistence();
        AgentePK agentePK = new AgentePK();
        agentePersistence.setAgentePK(agentePK);
        mdintran.setAgente(agentePersistence);

        /* Se contruye la emision y su PK y se settean */
        EmisionPersistence emisionPersistence = new EmisionPersistence();
        EmisionPK emisionPK = new EmisionPK();
        emisionPersistence.setEmisionPk(emisionPK);
        mdintran.setEmision(emisionPersistence);

        /* Se settea la baja logica */
        mdintran.setBajaLogica(Constantes.EN_FIRME);

        /* Se retorna el objeto creado */
        return mdintran;

    }
    
    /**
     * Construye una instancia de Mdintran
     *
     * @return Mdintran
     */
    public static  Mdintran getInstanceMdintran(PosicionNombrada tPosicionNombrada) {

        logger.info("Entrando a UtilsVOMercadoDinero.getInstanceMdintran()");
        
        /* Se construye el objeto Mdintran */
        Mdintran mdintran = new Mdintran();

        /* Se contruye el agente y su PK y se settean */
        AgentePersistence agentePersistence = new AgentePersistence();
        AgentePK agentePK = new AgentePK();
        agentePersistence.setAgentePK(agentePK);
        mdintran.setAgente(agentePersistence);

        /* Se contruye la emision y su PK y se settean */
        EmisionPersistence emisionPersistence = new EmisionPersistence();
        EmisionPK emisionPK = new EmisionPK();
        emisionPersistence.setEmisionPk(emisionPK);
        mdintran.setEmision(emisionPersistence);        
        
        
        /* Se settea la baja logica */
        mdintran.setBajaLogica(Constantes.EN_FIRME);

        /* Se retorna el objeto creado */
        return mdintran;

    }
    
//    /**
//     * Construye una instancia de com.indeval.persistence.portallegado.capital.modelo.Valor
//     *
//     * @return com.indeval.persistence.portallegado.capital.modelo.Valor
//     */
//    public static  com.indeval.persistence.portallegado.capital.modelo.Valor getInstanceValorCapital(
//    		PosicionNombrada tPosicionNombrada) {
//    	
//    	log.info("Entrando a UtilsVOMercadoDinero.getInstanceValorCapital");
//    	
//    	/* Se construye el objeto com.indeval.persistence.portallegado.capital.modelo.Valor */
//    	
//    	com.indeval.persistence.portallegado.capital.modelo.Valor valor = 
//    		new com.indeval.persistence.portallegado.capital.modelo.Valor();
//    	
//    	return valor;
//    }
//    
//    /**
//     * Construye una instancia de com.indeval.persistence.portallegado.dinero.modelo.Valor
//     *
//     * @return com.indeval.persistence.portallegado.dinero.modelo.Valor
//     */
//    public static  com.indeval.persistence.portallegado.dinero.modelo.Valor getInstanceValorDinero(
//    		PosicionNombrada tPosicionNombrada) {
//    	
//    	log.info("Entrando a UtilsVOMercadoDinero.getInstanceValorDinero");
//    	
//    	/* Se construye el objeto com.indeval.persistence.portallegado.dinero.modelo.Valor */
//    	com.indeval.persistence.portallegado.dinero.modelo.Valor valor = 
//    		new com.indeval.persistence.portallegado.dinero.modelo.Valor();
//    	
//    	return valor;
//    }
    
    /**
     * Este m&eacute;todo verifica que la cuenta del agente recibido 
     * sea de emisi&oacute;n '5000', '5001'
     * 
     * @param agenteVO
     * @return boolean
     */
    public static boolean esCuentaEmision(AgenteVO agenteVO) {

        logger.info("Entrando a UtilsVOMercadoDinero.esCuentaEmision()");
        
        boolean esCuentaEmision = false;
        
        if(agenteVO != null && StringUtils.isNotBlank(agenteVO.getCuenta())){
        	esCuentaEmision = esCuentaEmision(agenteVO.getCuenta().trim());
        }
        
        return esCuentaEmision;

    }
    
    /**
     * Este m&eacute;todo verifica que una cuenta sea de emisi&oacute;n '5000', '5001'
     *
     * @param cuenta
     *
     * @return boolean
     */
    public static boolean esCuentaEmision(String cuenta) {

        logger.info("Entrando a UtilsVOMercadoDinero.esCuentaEmision()");
        
        return (StringUtils.isNotBlank(cuenta) &&
               (cuenta.equalsIgnoreCase(Constantes.CUENTA_EMISION_5000) ||
               cuenta.equalsIgnoreCase(Constantes.CUENTA_EMISION_5001))) ? true : false;

    }
    
    /**
     * TODO este metodo deberia estar en el objeto Mdintran
     * 
     * Construye una copia del objeto Mdintran recibido 
     *
     * @param mdintranExample
     *
     * @return Mdintran
     */
    public static Mdintran clonaMdintran(Mdintran mdintranExample) {

        logger.info("Entrando a UtilsVOMercadoDinero.clonaMdintran()");
        
        Mdintran mdintranExampleCopia = new Mdintran();

//        mdintranExampleCopia.setAgente(mdintranExample.getAgente());
//        mdintranExampleCopia.setAgenteRecep(mdintranExample.getAgenteRecep());
//        mdintranExampleCopia.setAplicacion(mdintranExample.getAplicacion());
//        mdintranExampleCopia.setAreaTrabajo(mdintranExample.getAreaTrabajo());
//        mdintranExampleCopia.setBajaLogica(mdintranExample.getBajaLogica());
//        mdintranExampleCopia.setCantidadOperada(mdintranExample.getCantidadOperada());
//        mdintranExampleCopia.setClaveReporto(mdintranExample.getClaveReporto());
//        mdintranExampleCopia.setCorreccion(mdintranExample.getCorreccion());
//        mdintranExampleCopia.setCuentaOrigen(mdintranExample.getCuentaOrigen());
//        mdintranExampleCopia.setCuentaOrigenRecep(mdintranExample.getCuentaOrigenRecep());
//        mdintranExampleCopia.setDiasPlazo(mdintranExample.getDiasPlazo());
//        mdintranExampleCopia.setDivisa(mdintranExample.getDivisa());
//        mdintranExampleCopia.setEmision(mdintranExample.getEmision());
//        mdintranExampleCopia.setError(mdintranExample.getError());
//        mdintranExampleCopia.setFechaConcer(mdintranExample.getFechaConcer());
//        mdintranExampleCopia.setFechaHora(mdintranExample.getFechaHora());
//        mdintranExampleCopia.setFechaLiquidacion(mdintranExample.getFechaLiquidacion());
//        mdintranExampleCopia.setFechaReporto(mdintranExample.getFechaReporto());
//        mdintranExampleCopia.setFolioControl(mdintranExample.getFolioControl());
//        mdintranExampleCopia.setFolioDescripcion(mdintranExample.getFolioDescripcion());
//        mdintranExampleCopia.setFolioTransmision(mdintranExample.getFolioTransmision());
//        mdintranExampleCopia.setImporteParcial(mdintranExample.getImporteParcial());
//        mdintranExampleCopia.setIpAddress(mdintranExample.getIpAddress());
//        mdintranExampleCopia.setLiquidacion(mdintranExample.getLiquidacion());
//        mdintranExampleCopia.setMDinero(mdintranExample.getMDinero());
//        mdintranExampleCopia.setMdintranPk(mdintranExample.getMdintranPk());
//        mdintranExampleCopia.setMensajeJava(mdintranExample.getMensajeJava());
//        mdintranExampleCopia.setMercado(mdintranExample.getMercado());
//        mdintranExampleCopia.setNombreUsuario(mdintranExample.getNombreUsuario());
//        mdintranExampleCopia.setOrigen(mdintranExample.getOrigen());
//        mdintranExampleCopia.setOrigenAplicacion(mdintranExample.getOrigenAplicacion());
//        mdintranExampleCopia.setPosicAct(mdintranExample.getPosicAct());
//        mdintranExampleCopia.setPrecioPorTitulo(mdintranExample.getPrecioPorTitulo());
//        mdintranExampleCopia.setPTasaRef(mdintranExample.getPTasaRef());
//        mdintranExampleCopia.setSneComp(mdintranExample.getSneComp());
//        mdintranExampleCopia.setSociedadSerie(mdintranExample.getSociedadSerie());
//        mdintranExampleCopia.setTasaDescuento(mdintranExample.getTasaDescuento());
//        mdintranExampleCopia.setTasaPremio(mdintranExample.getTasaPremio());
//        mdintranExampleCopia.setTasaRendimiento(mdintranExample.getTasaRendimiento());
//        mdintranExampleCopia.setTransmision(mdintranExample.getTransmision());
//        mdintranExampleCopia.setUsuario(mdintranExample.getUsuario());
//        mdintranExampleCopia.setUVersion(mdintranExample.getUVersion());
//        mdintranExampleCopia.setValorCarteraComp(mdintranExample.getValorCarteraComp());
//        mdintranExampleCopia.setValorCarteraVend(mdintranExample.getValorCarteraVend());
//        mdintranExampleCopia.setBajaLogica(mdintranExample.getBajaLogica());

        BeanUtils.copyProperties(mdintranExample, mdintranExampleCopia);
        
        return mdintranExampleCopia;

    }
    
//    /**
//     * Mapea un objeto Prestamo a un objeto PrestamoVO
//     *
//     * @param prestamo
//     * @return PrestamoVO[]
//     * @throws BusinessException
//     */
//    public static PrestamoVO prestamo2VO(Prestamo prestamo) throws BusinessException {
//        
//        PrestamoVO prestamoVO = null;
//        BigDecimal PRECIO_VECTOR = UtilsDaliVO.BIG_DECIMAL_ZERO;
//        
//        if (prestamo != null) {
//
//            prestamoVO = new PrestamoVO();
//
//            prestamoVO.setCantidad(prestamo.getCantidad());
//            prestamoVO.setFaltanteSobrante(prestamo.getExedenteFaltante());
//            prestamoVO.setFechaConcertacion(prestamo.getFechaConcertacion());
//            prestamoVO.setFechaVencimiento(prestamo.getFechaVencimiento());
//            prestamoVO.setFolio(prestamo.getFolioPrestamo());
//            prestamoVO.setNoProrroga(prestamo.getNoProrroga());
//            prestamoVO.setPremio(prestamo.getPremio());
//            prestamoVO.setSobreTasa(prestamo.getSobretasa());
//            prestamoVO.setTasaP(prestamo.getTasaP());
//            prestamoVO.setValuacion(prestamo.getValuacion());
//            prestamoVO.setStatus(prestamo.getStatus().toString());
//            prestamoVO.setStatusDescripcion(
//                prestamo.getCatalogoEstadosPrestamos().getDescripcion());
//
//            prestamoVO.setAforo(prestamo.getAforo());
//            prestamoVO.setAforado(prestamo.getAforado());
//
//            try {
//
//                // calculamos el faltante - sobrante
//                // aforado - aforo
//                if ((prestamo.getAforo() != null) && (prestamo.getAforado() != null)) {
//
//                    prestamoVO.setFaltanteSobrante(
//                        prestamo.getAforado().subtract(prestamo.getAforo()));
//
//                }
//
//                // valuacion/cantidad as PRECIO_VECTOR
//                if ((prestamo.getValuacion() != null) && (prestamo.getCantidad() != null)) {
//
//                    PRECIO_VECTOR = prestamo.getValuacion().divide(
//                            new BigDecimal(prestamo.getCantidad()), 8,
//                            BigDecimal.ROUND_HALF_EVEN);
//                    prestamoVO.setPrecio(PRECIO_VECTOR);
//
//                }
//
//                // Se obtienen las garantias
//                prestamoVO.setGarantias(UtilsVOMercadoDinero.obtenerGarantias(prestamo));
//
//                // XXX Esto no se utiliza
//                // (cantidad * PRECIO_VECTOR + premio * aforo) as GARANTIAS
//                // NECESARIAS ???
//                if ((prestamo.getPremio() != null) && (prestamo.getAforo() != null)) {
//
//                    BigDecimal operando1 = new BigDecimal(prestamo.getCantidad()).multiply(
//                            PRECIO_VECTOR);
//                    BigDecimal operando2 = prestamo.getPremio().multiply(prestamo.getAforo());
//                    prestamoVO.setGarantiasNecesarias(operando1.add(operando2));
//
//                }
//
//                // XXX Esto no se utiliza
//                // (cantidad * PRECIO_VECTOR) as GARANTIAS CUBIERTAS, ???
//                if (prestamo.getCantidad() != null) {
//
//                    prestamoVO.setGarantiasCubiertas(
//                        new BigDecimal(prestamo.getCantidad()).multiply(PRECIO_VECTOR));
//
//                }
//
//            } catch (NumberFormatException e) {
//
//                e.getMessage();
//
//            }
//
//            if (
//                (prestamo.getEmision() != null) &&
//                    (prestamo.getEmision().getEmisionPk() != null)) {
//
//                EmisionVO emisionVO = new EmisionVO();
//                emisionVO.setCupon(prestamo.getEmision().getEmisionPk().getCupon());
//                emisionVO.setEmisora(prestamo.getEmision().getEmisionPk().getEmisora());
//                emisionVO.setIdTipoValor(prestamo.getEmision().getEmisionPk().getTv());
//                emisionVO.setSerie(prestamo.getEmision().getEmisionPk().getSerie());
//                prestamoVO.setEmision(emisionVO);
//
//            }
//
//            // prestamoVO.setStatusPrima(prestamo.getStatus().toString());
//            prestamoVO.setTasa(prestamo.getTasaT());
//
//            if (prestamo.getFolioInstitucion() != null) {
//
//                prestamoVO.setFolioInstitucion(new Integer(prestamo.getFolioInstitucion()));
//
//            }
//
//            if (prestamo.getFolioOriginal() != null) {
//
//                prestamoVO.setFolioOriginal(
//                    new Integer(prestamo.getFolioOriginal().toString()));
//
//            }
//
//            if (prestamo.getIdInstitucion() != null) {
//
//                prestamoVO.setIdInstitucion(new Integer(prestamo.getIdInstitucion()));
//
//            }
//
//        }
//        
//        return prestamoVO;
//    }
//
//    /**
//     * Construye un PrestamoVO[] a partir de un List de objetos Prestamo 
//     *
//     * @param prestamos
//     * @return PrestamoVO[]
//     * @throws BusinessException
//     */
//    public static PrestamoVO[] prestamo2VO(List prestamos) throws BusinessException {
//
//        log.info("Entrando al metodo privado MercadoDineroServiceImpl.prestamo2VO()");
//
//        PrestamoVO[] results = null;
//
//        if(prestamos != null) {
//
//            results = new PrestamoVO[prestamos.size()];
//            Iterator it = prestamos.iterator();
//            int i = 0;
//
//            while (it.hasNext()) {
//
//                Prestamo prestamo = (Prestamo) it.next();
//
//                if (prestamo != null) {
//
//                    results[i] = prestamo2VO(prestamo);
//                    i++;
//
//                }
//
//            }
//
//
//        }
//
//        return results;
//
//    }
    
//    /**
//     * Retorna un Arreglo de GarantiaVigenteVO a partir del set de Garantias del Prestamo
//     * recibido.
//     *
//     * @param prestamo
//     * @return List
//     */
//    private static GarantiaVigenteVO[] obtenerGarantias(Prestamo prestamo) {
//
//        log.info("Entrando a MercadoDineroServiceImpl.obtenerGarantias()");
//
//        List<GarantiaVigenteVO> listGarantiasVO = new ArrayList<GarantiaVigenteVO>();
//
//        Set setGarantias = prestamo.getGarantias();
//
//        // Lista de Garantias del Prestamo
//        for (Iterator iter = setGarantias.iterator(); iter.hasNext();) {
//
//            Garantia garantia = (Garantia) iter.next();
//            GarantiaVigenteVO garantV = new GarantiaVigenteVO();
//
//            EmisionVO emisionG = new EmisionVO();
//            emisionG.setIdTipoValor(garantia.getGarantiaPk().getTv());
//            emisionG.setCupon(garantia.getGarantiaPk().getCupon());
//            emisionG.setEmisora(garantia.getGarantiaPk().getEmisora());
//            emisionG.setSerie(garantia.getGarantiaPk().getSerie());
//
//            garantV.setEmision(emisionG);
//            garantV.setCuenta(garantia.getCuentaOrigen());
//            garantV.setValuacion(garantia.getValuacion());
//            garantV.setTitulos(BigDecimal.valueOf(garantia.getCantidad().longValue()));
//
//            listGarantiasVO.add(garantV);
//
//        }
//
//        GarantiaVigenteVO[] prestamosGarantias = new GarantiaVigenteVO[listGarantiasVO.size()];
//        prestamosGarantias = (GarantiaVigenteVO[]) listGarantiasVO.toArray(prestamosGarantias);
//
//        return prestamosGarantias;
//
//    }
    
    /**
     * Este metodo recibe una lista de Elementos de tipo RegistroEstatusOperacionesVO y los
     * ordena por su campo foliocontrol
     *
     * @param listaElementosTraspasante
     *
     * @return TreeMap
     */
    public static TreeMap sortListRegistroEstatusOperacionesVO(List listaElementosTraspasante) {

        logger.info("Entrando a UtilsVOMercadoDinero.sortListRegistroEstatusOperacionesVO()");

        TreeMap map = new TreeMap();

        if (listaElementosTraspasante != null) {

            Iterator iter = listaElementosTraspasante.iterator();

            while (iter.hasNext()) {

                RegistroEstatusOperacionesVO element = (RegistroEstatusOperacionesVO) iter.next();
                // no se valida la nulidad de los elementos que conforman la
                // llave del mapa
                // pues estos pertenecen a la llave primaria de la entidad
                map.put(
                    element.getAgenteFirmado().getClave() + element.getAgenteFirmado().getCuenta() +
                    element.getLlaveFolio().toString() +
                    ((element.getFolioControl() != null) ? element.getFolioControl().toString() : "") +
                    element.getContraparte().getClave() + element.getContraparte().getCuenta(),
                    element);

            }

        }

        return map;

    }
    
}
