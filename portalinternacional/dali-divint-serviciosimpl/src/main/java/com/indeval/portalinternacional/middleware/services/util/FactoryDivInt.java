/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransfer;
import com.indeval.portalinternacional.middleware.servicios.vo.CampoArchivoVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class FactoryDivInt implements Constantes {

	private static final int MIN_SIZE_DATE = 10;

	/** Boveda predeterminada */
    public static final Long BOVEDA_PREDETERMINADA = Long.valueOf(1);
    
	/** Para el registro de log de esta clase */
    private final static Logger log = LoggerFactory.getLogger(FactoryDivInt.class);
    
	public static enum MES {JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC};
	public static enum MES_ES {ENE, FEB, MAR, ABR, MAY, JUN, JUL, AGO, SEP, OCT, NOV, DIC};

    /**
     * Obtiene el tipo de mensaje (540, 543, etc)
     * 
     * @param tipoOperacion
     * @param tipoTraspaso
     * @return String
     */
    public static String obtieneTipoMensaje(String tipoOperacion, String tipoTraspaso) {
        
        log.debug("Entrando a obtieneTipoMensaje()");
        
        if (StringUtils.isBlank(tipoOperacion) || StringUtils.isBlank(tipoTraspaso)) {
            return null;
        }
        if (TIPO_MOVTO_R.equalsIgnoreCase(tipoOperacion.trim())) {
            if (TRASPASO_CONTRA.equalsIgnoreCase(tipoTraspaso.trim())) {
                return TCP_RECEPCION;
            } else if ((TRASPASO_LIBRE.equalsIgnoreCase(tipoTraspaso.trim()))) {
                return TLP_RECEPCION;
            } else {
                return null;
            }
        } else if (TIPO_MOVTO_E.equalsIgnoreCase(tipoOperacion.trim())) {
            if (TRASPASO_CONTRA.equalsIgnoreCase(tipoTraspaso.trim())) {
                return TCP_ENTREGA;
            } else if (TRASPASO_LIBRE.equalsIgnoreCase(tipoTraspaso.trim())) {
                return TLP_ENTREGA;
            } else {
                return null;
            }
        } else {
            return null;
        }
        
    }
    
    public static String[] obtieneTipoOperTipoTrasp(String tipoMensaje) {
        
        log.debug("Entrando a obtieneTipoMensaje()");
        
        if (StringUtils.isBlank(tipoMensaje)) {
            return null;
        }
        
        String[] salida = new String[2];
        if (TCP_RECEPCION.equals(tipoMensaje)) {
            salida[0] = TIPO_MOVTO_R;
            salida[1] = TRASPASO_CONTRA;
        }
        else if (TLP_RECEPCION.equals(tipoMensaje)) {
            salida[0] = TIPO_MOVTO_R;
            salida[1] = TRASPASO_LIBRE;
        }
        else if (TCP_ENTREGA.equals(tipoMensaje)) {
            salida[0] = TIPO_MOVTO_E;
            salida[1] = TRASPASO_CONTRA;
        }
        else if (TLP_ENTREGA.equals(tipoMensaje)) {
            salida[0] = TIPO_MOVTO_E;
            salida[1] = TRASPASO_LIBRE;
        }
        
        return salida;
        
    }

    /**
     * Convierte el formato de fecha del archivo plano DE "dd-MMM-yyyy" A "MM/dd/yyyy"
     * 
     * @param fechaArchivo
     * @return String
     */
    public static String fechaArchivo2fechaFileTransfer(String fechaArchivo) {

        log.debug("Entrando a FactoryDivInt.fechaArchivo2fechaFileTransfer()");
        String fecha = "";
        
        if (StringUtils.isNotBlank(fechaArchivo) && fechaArchivo.length() >= MIN_SIZE_DATE ) {
        	
        	log.debug("fechaArchivo a convertir = [" + fechaArchivo + "]");
        	
            Map meses = new HashMap();
            meses.put(MES.JAN.toString(), "01");
            meses.put(MES.FEB.toString(), "02");
            meses.put(MES.MAR.toString(), "03");
            meses.put(MES.APR.toString(), "04");
            meses.put(MES.MAY.toString(), "05");
            meses.put(MES.JUN.toString(), "06");
            meses.put(MES.JUL.toString(), "07");
            meses.put(MES.AUG.toString(), "08");
            meses.put(MES.SEP.toString(), "09");
            meses.put(MES.OCT.toString(), "10");
            meses.put(MES.NOV.toString(), "11");
            meses.put(MES.DEC.toString(), "12");
            
            Map mesesEsp = new HashMap();
            mesesEsp.put(MES_ES.ENE.toString(), "01");
            mesesEsp.put(MES_ES.ABR.toString(), "04");
            mesesEsp.put(MES_ES.AGO.toString(), "08");
            mesesEsp.put(MES_ES.DIC.toString(), "12");
            
            try {
	            List elementosFecha = new ArrayList();
	            elementosFecha.add(fechaArchivo.substring(0, 2));
	            elementosFecha.add(meses.get(fechaArchivo.substring(3, 6).toUpperCase()) != null ? 
	            		meses.get(fechaArchivo.substring(3, 6).toUpperCase()) : 
	            		mesesEsp.get(fechaArchivo.substring(3, 6).toUpperCase()));
	            elementosFecha.add(fechaArchivo.substring(7));
	            
	            fecha = elementosFecha.get(1) + "/" + elementosFecha.get(0) + "/"
	            	+ elementosFecha.get(2);
            } catch(IndexOutOfBoundsException e) {
            	e.printStackTrace();
            }
        }
        
    	return fecha;
    }
    
    /**
     * Genera el mapa de indices (true) o de nombres (false) correspondientes a
     * traspasos de divisi&oacute;n internacional
     * 
     * @param obtieneIndices
     * @return Map
     * @throws BusinessException
     */
    public static Map<Object, Object> indicesNombresTraspasosDivInt(boolean obtieneIndices)
    throws BusinessException {
        Map<Object, Object> mapa = new TreeMap<Object, Object>();
        if (obtieneIndices) {
            mapa.put(ID_INST_VEND, new Integer(0));
            mapa.put(FOLIO_INST_VEND, new Integer(1));
            mapa.put(CUENTA_VEND, new Integer(2));
            mapa.put(TV, new Integer(3));
            mapa.put(EMISORA, new Integer(4));
            mapa.put(SERIE, new Integer(5));
            mapa.put(CUPON, new Integer(6));
            mapa.put(ISIN, new Integer(7));
            mapa.put(ID_BOVEDA, new Integer(8));
            mapa.put(CANTIDAD, new Integer(9));
            mapa.put(TIPO_OPER, new Integer(10));
            mapa.put(TIPO_MOV, new Integer(11));
            mapa.put(FECHA_NOT, new Integer(12));
            mapa.put(FECHA_LIQ, new Integer(13));
            mapa.put(FECHA_OPER, new Integer(14));
            mapa.put(CUSTODIO, new Integer(15));
            mapa.put(CUENTA_CONTRA, new Integer(16));
            mapa.put(CONTRAPARTE, new Integer(17));
            mapa.put(DEPOSITANTE, new Integer(18));
            mapa.put(NOMBRE_CUENTA_BEN, new Integer(19));
            mapa.put(NUMERO_CUENTA_BEN, new Integer(20));
            mapa.put(IMPORTE, new Integer(21));
            mapa.put(DIVISA, new Integer(22));
            mapa.put(INST_ESP, new Integer(23));
        }
        else {
            mapa.put(new Integer(0), ID_INST_VEND);
            mapa.put(new Integer(1), FOLIO_INST_VEND);
            mapa.put(new Integer(2), CUENTA_VEND);
            mapa.put(new Integer(3), TV);
            mapa.put(new Integer(4), EMISORA);
            mapa.put(new Integer(5), SERIE);
            mapa.put(new Integer(6), CUPON);
            mapa.put(new Integer(7), ISIN);
            mapa.put(new Integer(8), ID_BOVEDA);
            mapa.put(new Integer(9), CANTIDAD);
            mapa.put(new Integer(10), TIPO_OPER);
            mapa.put(new Integer(11), TIPO_MOV);
            mapa.put(new Integer(12), FECHA_NOT);
            mapa.put(new Integer(13), FECHA_LIQ);
            mapa.put(new Integer(14), FECHA_OPER);
            mapa.put(new Integer(15), CUSTODIO);
            mapa.put(new Integer(16), CUENTA_CONTRA);
            mapa.put(new Integer(17), CONTRAPARTE);
            mapa.put(new Integer(18), DEPOSITANTE);
            mapa.put(new Integer(19), NOMBRE_CUENTA_BEN);
            mapa.put(new Integer(20), NUMERO_CUENTA_BEN);
            mapa.put(new Integer(21), IMPORTE);
            mapa.put(new Integer(22), DIVISA);
            mapa.put(new Integer(23), INST_ESP);
        }
        return mapa;
    }

    /**
     * 
     * @param fileTransfer
     * @param indice
     * @param campos
     * @param tam
     * @param agenteVO
     * @param emisionVO
     * @param cuentaRecep 
     * @throws BusinessException
     */
    public static FileTransfer llenaFileTransferDivInt(FileTransfer fileTransfer,
    		Map<Object, Object> indice, String[] campos, int tam, AgenteVO agenteVO,
    		EmisionVO emisionVO, String cuentaRecep) throws BusinessException {

    	log.debug("Entrando a FactoryDivIntVO.llenaFileTransferDivInt()");

    	fileTransfer.setIdInstTrasp(obtieneCampo(indice, campos, ID_INST_VEND));
    	fileTransfer.setFolioInstTrasp(obtieneCampo(indice, campos, FOLIO_INST_VEND));
    	fileTransfer.setCuentaTrasp(obtieneCampo(indice, campos, CUENTA_VEND));
    	
    	fileTransfer.setIdInstRecep(agenteVO.getId());
    	fileTransfer.setFolioInstRecep(agenteVO.getFolio());
    	fileTransfer.setCuentaRecep(cuentaRecep);
    	
    	fileTransfer.setTv(obtieneCampo(indice, campos, TV));
    	fileTransfer.setEmisora(obtieneCampo(indice, campos, EMISORA));
    	fileTransfer.setSerie(obtieneCampo(indice, campos, SERIE));
    	fileTransfer.setCupon(obtieneCampo(indice, campos, CUPON));
    	fileTransfer.setBajaLogica(obtieneCampo(indice, campos, CUPON_CORTADO));
    	
    	fileTransfer.setMercado(emisionVO != null ? emisionVO.getMercado() : BLANK);
    	
    	fileTransfer.setIsin(obtieneCampo(indice, campos, ISIN));
    	
    	fileTransfer.setIdBoveda(StringUtils.isNotBlank(obtieneCampo(indice, campos, ID_BOVEDA)) ? 
    			new Long(obtieneCampo(indice, campos, ID_BOVEDA)) : BOVEDA_PREDETERMINADA);
    	
    	fileTransfer.setCantidadTitulos(obtieneCampo(indice, campos, CANTIDAD));

    	String tipoOper = obtieneCampo(indice, campos, TIPO_OPER);
    	String tipoMov = obtieneCampo(indice, campos, TIPO_MOV);

    	fileTransfer.setTipoOperacion(tipoOper);
    	fileTransfer.setTipoMovto(tipoMov);

    	if (tipoOper.equalsIgnoreCase(TRASPASO_LIBRE)) {
    		if (tipoMov.equalsIgnoreCase(TIPO_MOVTO_E)) {
    			fileTransfer.setTipoMensaje(TMSJ_542);
    			fileTransfer.setEstatusOper("N");
    		}
    		else if (tipoMov.equalsIgnoreCase(TIPO_MOVTO_R)) {
    			fileTransfer.setTipoMensaje(TMSJ_540);
    			fileTransfer.setEstatusOper("N");
    		}
    	}
    	else if (tipoOper.equalsIgnoreCase(TRASPASO_CONTRA)) {
    		if (tipoMov.equalsIgnoreCase(TIPO_MOVTO_E)) {
    			fileTransfer.setTipoMensaje(TMSJ_543);
    			fileTransfer.setEstatusOper("N");
    		}
    		else if (tipoMov.equalsIgnoreCase(TIPO_MOVTO_R)) {
    			fileTransfer.setTipoMensaje(TMSJ_541);
    			fileTransfer.setEstatusOper("X");
    		}
    	}

    	fileTransfer.setFechaNotificacion(obtieneCampo(indice, campos, FECHA_NOT));
    	fileTransfer.setFechaLiquidacion(obtieneCampo(indice, campos, FECHA_LIQ));
    	fileTransfer.setFechaOperacion(obtieneCampo(indice, campos, FECHA_OPER));
    	fileTransfer.setDescCustodio(obtieneCampo(indice, campos, CUSTODIO));
    	fileTransfer.setCuentaContraparte(obtieneCampo(indice, campos, CUENTA_CONTRA));
    	fileTransfer.setDescContraparte(obtieneCampo(indice, campos, CONTRAPARTE));
    	fileTransfer.setNombreDepositante(obtieneCampo(indice, campos, DEPOSITANTE));
    	fileTransfer.setNombreCuentaBeneficiario(obtieneCampo(indice, campos, NOMBRE_CUENTA_BEN));
    	fileTransfer.setNumeroCuentaBeneficiario(obtieneCampo(indice, campos, NUMERO_CUENTA_BEN));

    	if (tam > LONGITUD_TRASP_DIV_INT_MIN) {
    		fileTransfer.setImporte(obtieneCampo(indice, campos, IMPORTE));
    		fileTransfer.setDivisa(obtieneCampo(indice, campos, DIVISA));
    	}

    	if (tam > LONGITUD_TRASP_DIV_INT_INTER) {
    		fileTransfer.setInstruccEspeciales(obtieneCampo(indice, campos, INST_ESP));
    	}

    	return fileTransfer;

    }
    
    
    /**
     * Encapsula la obtenci&oacute;n del valor del campo
     * aplicando un trim() cuando se recupera el valor
     * @param campo
     * @return valor
     */
    public static String obtieneCampo(Map<Object, Object> indice, String[] campos, String campo){
    	
    	log.debug("Entrando a FactoryDivInt.obtieneCampo()");

    	String valor = null;
    	if(campos != null && campos.length > 0 && indice != null && !indice.isEmpty()) {
    		Integer i = (Integer) indice.get(campo);
    		if(i!=null){
    			valor = campos[i.intValue()];
    			valor = StringUtils.isNotBlank(valor) ? valor.trim() : valor;
    		}	
    	}
    	return valor;

    }
    
    /**
     * Genera un {@link CampoArchivoVO}
     * @param campo
     * @param nombre
     * @param tieneError
     * @param valor
     * @param formato
     * @return CampoArchivoVO
     */
    public static CampoArchivoVO generaArchivoVO(String campo, String nombre,
            boolean tieneError, Object valor, String formato) {
    	
    	log.debug("Entrando a FactoryDivInt.generaArchivoVO()");
    	
    	int longitudCampo = StringUtils.isNotBlank(campo) ? campo.length() : 0;
    	
    	return FactoryDivInt.generaArchivoVO(longitudCampo, nombre, tieneError, valor, formato);
    	
    }
    
    /**
     * Genera un {@link CampoArchivoVO}
     * @param campo
     * @param nombre
     * @param tieneError
     * @param valor
     * @param formato
     * @return CampoArchivoVO
     */
    public static CampoArchivoVO generaArchivoVO(int longitud, String nombre,
            boolean tieneError, Object valor, String formato) {
    	
    	log.debug("Entrando a FactoryDivInt.generaArchivoVO()");
    	
        CampoArchivoVO campoArchivoVO = new CampoArchivoVO();
        campoArchivoVO.setLongitud(longitud);
        campoArchivoVO.setNombre(nombre);
        campoArchivoVO.setTieneError(tieneError);
        campoArchivoVO.setValor(valor);
        campoArchivoVO.setFormato(formato);
        return campoArchivoVO;
    }

    
}
