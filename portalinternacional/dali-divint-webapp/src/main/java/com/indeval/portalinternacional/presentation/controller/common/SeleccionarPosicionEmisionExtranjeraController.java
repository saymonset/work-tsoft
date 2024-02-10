/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Emisora;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.Instrumento;
import com.indeval.portaldali.persistence.modelo.PosicionNombrada;
import com.indeval.portaldali.persistence.modelo.TipoCuenta;
import com.indeval.portaldali.persistence.util.constants.Constantes;
import com.indeval.portaldali.persistence.util.constants.DaliConstants;
import com.indeval.portalinternacional.presentation.converter.FormatoNumeroEnteroConverter;

/**
 * Controller que atiende las solicitudes de los componentes autocomplete de Cuentas, participantes, emisiones y bovedas con Emision Extranjera
 * 
 * @author Erik Vera
 * 
 */
@SuppressWarnings({"unchecked"})
public class SeleccionarPosicionEmisionExtranjeraController extends ControllerBase{
	private AgenteVO agente = null;

    private EmisionVO emision = null;

    private DateUtilService dateUtilService = null;
    /**
     * Identificadores de mercado v&aacute;lidos para esta consulta
     */
    private List<Long> idMercado = null;
    /**
     * Acceso a la consulta de catálogos
     * 
     */
    private ConsultaCatalogoService consultaCatalogoService = null;
    Map<String, String> params = null;
    /**
     * método que atiende las solicitudes de autocomplete del catálogo de tipos de valor.
     * 
     * @param prefijo Criterio de búsqueda
     * @return Lista con los tipos de valor encontrados
     */
    public List<Instrumento> buscarTiposValorPorPrefijo(Object prefijo) {

        return consultaCatalogoService.buscarTiposDeValoresPorMercados(idMercado.toArray(new Long[] {}), prefijo.toString());

    }

    /**
     * método que atiende las solicitudes de autocomplete del catálogo de emisoras.
     * 
     * @param prefijo Criterio de búsqueda
     * @return Lista con las emisoras encontradas
     */
    public List<Emisora> buscarEmisorasPorPrefijo(Object prefijo) {
        String prefijoAjustado = "";
        if (prefijo != null) {
            prefijoAjustado = String.valueOf(prefijo.toString().trim()).replace('*', '%');
        }
        return consultaCatalogoService.buscarEmisorasPorPrefijo(prefijoAjustado);

    }

    /**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de elección de bóveda de valor
     * 
     * @return
     */
    public List<SelectItem> getBovedasValor() {

        List<Boveda> bovedas = consultaCatalogoService.buscarBovedasDeValor();
        List<SelectItem> listaSelectBovedas = new ArrayList<SelectItem>();

        for (Boveda boveda : bovedas) {
            listaSelectBovedas.add(new SelectItem(boveda.getIdBoveda().toString(), boveda.getDescripcion()));
        }
        return listaSelectBovedas;
    }

    /**
     * método que atiende las solicitudes de autocomplete del catálogo de cuentas del campo traspasante.
     * 
     * @param prefijo Criterio de búsqueda
     * @return Lista con las cuentas
     */
    public List<CuentaNombrada> buscarCuentaTraspasante(Object prefijo) {
        CuentaNombrada criterio = new CuentaNombrada();
        criterio.setTipoCuenta(new TipoCuenta());
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        List<CuentaNombrada> resultados = new ArrayList<CuentaNombrada>();
        Institucion institucion = null;
        criterio.getTipoCuenta().setTipoCustodia(Constantes.TIPO_CUSTODIA_VALORES);
        criterio.getTipoCuenta().setNaturalezaContable(Constantes.NATURALEZA_PASIVO);
        criterio.getTipoCuenta().setNaturalezaProcesoLiquidacion(Constantes.CUENTA_NOMBRADA);
        if (!StringUtils.isEmpty(params.get("nombre_id_folio_traspasante"))
                && !StringUtils.isEmpty(params.get(params.get("nombre_id_folio_traspasante")))) {
            institucion = consultaCatalogoService.findInstitucionByClaveFolio(params.get(params.get("nombre_id_folio_traspasante")));
        }
        if (institucion != null) {
            criterio.setInstitucion(institucion);
            criterio.setCuenta(prefijo.toString().replace('*', '%'));
            resultados = consultaCatalogoService.findCuentasByNumeroCuenta(criterio);
        } else {
            CuentaNombrada sinOpciones = new CuentaNombrada();
            sinOpciones.setNombreCuenta("Debe seleccionar primero una institución receptora");
            sinOpciones.setCuenta(" ");
            resultados.add(sinOpciones);
        }
        if (resultados.size() == 0) {
            CuentaNombrada sinOpciones = new CuentaNombrada();
            sinOpciones.setNombreCuenta("Sin coincidencias");
            sinOpciones.setCuenta(" ");
            resultados.add(sinOpciones);
        }

        return resultados;
    }

    /**
     * método que atiende las solicitudes de autocomplete del catálogo de cuentas del campo receptor.
     * 
     * @param prefijo Criterio de búsqueda
     * @return Lista con las cuentas
     */
    public List<CuentaNombrada> buscarCuentaReceptor(Object prefijo) {
        CuentaNombrada criterio = new CuentaNombrada();
        criterio.setTipoCuenta(new TipoCuenta());
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        List<CuentaNombrada> resultados = new ArrayList<CuentaNombrada>();
        Institucion institucion = null;
        criterio.getTipoCuenta().setTipoCustodia(Constantes.TIPO_CUSTODIA_VALORES);
        criterio.getTipoCuenta().setNaturalezaContable(Constantes.NATURALEZA_PASIVO);
        criterio.getTipoCuenta().setNaturalezaProcesoLiquidacion(Constantes.CUENTA_NOMBRADA);
        if (!StringUtils.isEmpty(params.get("nombre_id_folio_receptor"))
                && !StringUtils.isEmpty(params.get(params.get("nombre_id_folio_receptor")))) {
            institucion = consultaCatalogoService.findInstitucionByClaveFolio(params.get(params.get("nombre_id_folio_receptor")));
        }
        if (institucion != null) {
            criterio.setInstitucion(institucion);
            criterio.setCuenta(prefijo.toString().replace('*', '%'));
            resultados = consultaCatalogoService.findCuentasByNumeroCuenta(criterio);
        } else {
            CuentaNombrada sinOpciones = new CuentaNombrada();
            sinOpciones.setNombreCuenta("Debe seleccionar primero una institución receptora");
            sinOpciones.setCuenta(" ");
            resultados.add(sinOpciones);
        }
        if (resultados.size() == 0) {
            CuentaNombrada sinOpciones = new CuentaNombrada();
            sinOpciones.setNombreCuenta("Sin coincidencias");
            sinOpciones.setCuenta(" ");
            resultados.add(sinOpciones);
        }

        return resultados;
    }

    /**
     * Busca una lista de participantes cuyo id-folio comience con la cadena de criterio recibido
     * 
     * @param prefijo Cadena de criterio id-folio
     * @return Lista de instituciones recuperadas
     */
    public List<Institucion> buscarParticipante(Object prefijo) {
        return consultaCatalogoService.findInstitucionesByClaveFolio(prefijo.toString());
    }

    /**
     * Inicializa los resultados de la consulta de posiciones del participante.
     * 
     * @return null
     */
    public String getInit() {
        params = new HashMap<String, String>();
        
        Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
        
        for (String key : keys) {
            params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        }
        
        
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        ejecutarConsulta();
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portalinternacional.presentation.controller.common.ControllerBase#ejecutarConsulta()
     */
    @Override
    public String ejecutarConsulta() {
        

        agente = new AgenteVO();
        emision = new EmisionVO();
        Institucion institucion = null;
        if (params.get("idFolioInstitucion") != null) {
            institucion = consultaCatalogoService.findInstitucionByClaveFolio(params.get("idFolioInstitucion"));
        }
        if (institucion != null) {

            agente.setId(institucion.getTipoInstitucion().getClaveTipoInstitucion());
            agente.setFolio(institucion.getFolioInstitucion());
            agente.setCuenta(params.get("cuenta"));

            emision.setTv(StringUtils.isEmpty(params.get("tv")) ? null : params.get("tv"));

            emision.setEmisora(StringUtils.isEmpty(params.get("emisora")) ? null : params.get("emisora"));

            emision.setSerie(StringUtils.isEmpty(params.get("serie")) ? null : params.get("serie"));

            emision.setIsin(StringUtils.isEmpty(params.get("isin")) ? null : params.get("isin"));

            emision.setCupon(StringUtils.isEmpty(params.get("cupon")) ? null : params.get("cupon"));

            long idBoveda = NumberUtils.toLong(params.get("idBoveda"), DaliConstants.VALOR_COMBO_TODOS);

            emision.setIdBoveda(idBoveda > 0 ? idBoveda : null);

            
            PaginaVO pgTmp = consultaCatalogoService.buscarListaPosicionesNombradasDeParticipanteInvExt(agente, emision, paginaVO);
            
            if (pgTmp != null) {
                paginaVO = pgTmp;
                List<Object[]> registros = new ArrayList<Object[]>();
                Object[] resultado = null;
                for (PosicionNombrada posicion : (List<PosicionNombrada>)paginaVO.getRegistros()) {
                    resultado = new Object[4];
                    resultado[0] = posicion;
                    resultado[1] = posicion.getCupon();
                    resultado[2] = getDiasDiff(posicion.getCupon().getEmision().getFechaVencimiento());
                    resultado[3] = FormatoNumeroEnteroConverter.FORMATO_ENTERO.format(posicion.getPosicionDisponible().doubleValue());
                    registros.add(resultado);
                }
                paginaVO.setRegistros(registros);
               
            } else {
                paginaVO.setRegistros(null);
                paginaVO.setTotalRegistros(0);
            }

        } else {

        }

        return null;
    }
    
    public String getDiasDiff(Date fecha){
        String res = null;
        
        
        if(fecha != null){
            res = String.valueOf((int)(fecha.getTime() - dateUtilService.getCurrentDate().getTime()));
        }
             
        return res;
    }

    /**
     * @return the idMercado
     */
    public List<Long> getIdMercado() {
        return idMercado;
    }

    /**
     * @param idMercado the idMercado to set
     */
    public void setIdMercado(List<Long> idMercado) {
        this.idMercado = idMercado;
    }

    /**
     * @return the consultaCatalogoService
     */
    public ConsultaCatalogoService getConsultaCatalogoService() {
        return consultaCatalogoService;
    }

    /**
     * @param consultaCatalogoService the consultaCatalogoService to set
     */
    public void setConsultaCatalogoService(ConsultaCatalogoService consultaCatalogoService) {
        this.consultaCatalogoService = consultaCatalogoService;
    }

    /**
     * @return the agente
     */
    public AgenteVO getAgente() {
        return agente;
    }

    /**
     * @param agente the agente to set
     */
    public void setAgente(AgenteVO agente) {
        this.agente = agente;
    }

    /**
     * @return the emision
     */
    public EmisionVO getEmision() {
        return emision;
    }

    /**
     * @param emision the emision to set
     */
    public void setEmision(EmisionVO emision) {
        this.emision = emision;
    }

    /**
     * @return the dateUtilService
     */
    public DateUtilService getDateUtilService() {
        return dateUtilService;
    }

    /**
     * @param dateUtilService the dateUtilService to set
     */
    public void setDateUtilService(DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

    /**
     * @return the params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
