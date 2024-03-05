/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.AgentePersistence;
import com.indeval.portaldali.persistence.vo.EmisionPK;
import com.indeval.portaldali.persistence.vo.EmisionPersistence;
import com.indeval.portaldali.persistence.vo.InstrumentoVO;
import com.indeval.portaldali.persistence.vo.PageVO;

/**
 * Clase utilitaria que contiene metodos para el manejo o manipulacion de
 * algunos VOs
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class UtilsDaliVO implements Constantes {

    /** Log de Clase. */
    private static final Logger logger = LoggerFactory.getLogger(UtilsDaliVO.class);

    /**
     * Metodo utilitario que construye y retorna un AgentePersistence 
     * a partir de los valores contenidos en un AgenteVO
     * @param agenteVO
     * @return AgentePersistence
     */
    public static AgentePersistence getAgente(AgenteVO agenteVO) {

        logger.info("Entrando a UtilsDaliVO.getAgente()");

        AgentePersistence agente = null;

        if (agenteVO != null) {
            agente = new AgentePersistence();
            agente.setAgentePK(getAgentePK(agenteVO));
        }
        else {
            logger.debug("Se recibio un agenteVO nulo");
        }

        return agente;
    }

    /**
     * Metodo utilitario que construye y retorna un AgentePersistence a partir de los
     * valores contenidos en un AgenteVO
     * 
     * @param agenteVO
     * @return AgentePersistence creado si el agenteVO no estaba nulo, si estab vacio se
     *         retorna un agente vacio.
     */
    public static AgentePersistence parseAgenteVO2Agente(AgenteVO agenteVO) {

        logger.info("Entrando a UtilsDaliVO.parseAgenteVO2Agente()");

        AgentePersistence agente = null;

        if (agenteVO != null) {
            agente = new AgentePersistence();
            agente.setAgentePK(parseAgenteVO2AgentePK(agenteVO));
        }
        else {
            logger.debug("Se recibio un agenteVO nulo");
        }

        return agente;
    }

    /**
     * Metodo utilitario que construye y retorna un AgenteVO a partir de los
     * valores contenidos en un AgentePersistence.
     * 
     * @param agente
     * @return AgenteVO recien creado. Si el agenteVO recibido es null, el
     *         metodo retorna null.
     */
    public static AgenteVO getAgenteVO(AgentePersistence agente) {

        logger.info("Entrando a UtilsDaliVO.getAgenteVO()");

        AgenteVO agenteVO = null;

        if (agente != null) {

            agenteVO = new AgenteVO();

            // Se settean los atributos
            if (agente.getAgentePK() != null) {
                agenteVO.setId(agente.getAgentePK().getIdInst());
                agenteVO.setFolio(agente.getAgentePK().getFolioInst());
                agenteVO.setCuenta(agente.getAgentePK().getCuenta());
            }
            agenteVO.setNombreCorto(agente.getNombreCorto());
            agenteVO.setNombreCuenta(agente.getRazonSocial());
            agenteVO.setTipo(agente.getTipoDepositante()); // TODO verificar
        }
        else {
            logger.debug("Se recibio un agente nulo");
        }

        return agenteVO;
    }

    /**
     * Crea una AgentePK a partir de un AgenteVO
     * 
     * @param agenteVO
     *            con los valores base.
     * @return AgentePK recien creado, solo devuelve null si el AgenteVO se
     *         recibe NULL. Si los atributos del AgenteVO recibido estan en
     *         blanco, el objeto AgentePK devuelto los tendra NULL.
     */
    public static AgentePK getAgentePK(AgenteVO agenteVO) {

        logger.info("Entrando a UtilsDaliVO.getAgentePK()");

        AgentePK agentePK = null;

        if (agenteVO != null) {
            agentePK = new AgentePK();
            if (StringUtils.isNotBlank(agenteVO.getId())) {
                agentePK.setIdInst(agenteVO.getId().trim());
            }
            if (StringUtils.isNotBlank(agenteVO.getFolio())) {
                agentePK.setFolioInst(agenteVO.getFolio().trim());
            }
            if (StringUtils.isNotBlank(agenteVO.getCuenta())) {
                agentePK.setCuenta(agenteVO.getCuenta().trim());
            }
        }

        return agentePK;
    }

    /**
     * Crea una AgentePK a partir de un AgenteVO
     * 
     * @param agenteVO
     *            con los valores base.
     * @return AgentePK recien creado, devuelve null si el AgenteVO se recibe
     *         null o si todos los atributos estan en blanco o null. Si los
     *         atributos del AgenteVO recibido estan en blanco, el objeto
     *         AgentePK devuelto los tendra NULL.
     * 
     */
    public static AgentePK parseAgenteVO2AgentePK(AgenteVO agenteVO) {

        logger.info("Entrando a UtilsDaliVO.parseAgenteVO2AgentePK()");

        AgentePK agentePK = null;

        if (agenteVO != null) {

            AgentePK agentePKTransfer = new AgentePK();
            boolean required = false;

            if (StringUtils.isNotBlank(agenteVO.getId())) {
                agentePKTransfer.setIdInst(agenteVO.getId().trim());
                required = true;
            }
            if (StringUtils.isNotBlank(agenteVO.getFolio())) {
                agentePKTransfer.setFolioInst(agenteVO.getFolio().trim());
                required = true;
            }
            if (StringUtils.isNotBlank(agenteVO.getCuenta())) {
                agentePKTransfer.setCuenta(agenteVO.getCuenta().trim());
                required = true;
            }
            if (required) {
                agentePK = agentePKTransfer;
            }
        }

        return agentePK;
    }

    /**
     * Metodo utilitario que construye y retorna una EmisionPersistence a partir de los
     * valores contenidos en una EmisionVO.
     * 
     * @param emisionVO
     *            EmisionVO
     * @return EmisionPersistence objeto creado a partir de la emisionVO. Si la emisionVO
     *         recibida es null, el metodo retorna null. Si los atributos de la
     *         EmisionVO recibido estan en blanco, el objeto EmisionPersistence devuelto
     *         los tendra NULL.
     */
    public static EmisionPersistence getEmision(EmisionVO emisionVO) {

        logger.info("Entrando a UtilsDaliVO.getEmision()");

        EmisionPersistence emision = null;

        if (emisionVO != null) {
            emision = new EmisionPersistence();
            emision.setEmisionPk(getEmisionPK(emisionVO));
            emision.setInstrumento(new InstrumentoVO());
            if (StringUtils.isNotBlank(emisionVO.getIsin())) {
                emision.setIsin(emisionVO.getIsin().trim());
            }
            if (StringUtils.isNotBlank(emisionVO.getAlta())) {
                emision.setEmisionExtra(emisionVO.getAlta().trim());
            }
            if (StringUtils.isNotBlank(emisionVO.getCuponCortado())) {
                emision.setCuponCortado(emisionVO.getCuponCortado().trim());
            }
        }

        return emision;
    }

    /**
     * Metodo utilitario que construye y retorna una EmisionPersistence a partir de los
     * valores contenidos en una EmisionVO.
     * 
     * @param emisionVO
     *            EmisionVO
     * @return EmisionPersistence recien creada a partir de la emisionVO. devuelve NULL si
     *         la emisionVO esta NULL
     */
    public static EmisionPersistence parseEmisionVO2Emision(EmisionVO emisionVO) {

        logger.info("Entrando a UtilsDaliVO.parseEmisionVO2Emision()");

        EmisionPersistence emision = null;

        if (emisionVO != null) {
            emision = new EmisionPersistence();
            emision.setEmisionPk(parseEmisionVO2EmisionPK(emisionVO));
            emision.setInstrumento(new InstrumentoVO());
            emision.getInstrumento().setTv(emisionVO.getIdTipoValor());
            if (StringUtils.isNotBlank(emisionVO.getIsin())) {
                emision.setIsin(emisionVO.getIsin().trim());
            }
            if (StringUtils.isNotBlank(emisionVO.getAlta())) {
                emision.setEmisionExtra(emisionVO.getAlta().trim());
            }
            if (StringUtils.isNotBlank(emisionVO.getCuponCortado())) {
                emision.setCuponCortado(emisionVO.getCuponCortado().trim());
            }
            if (StringUtils.isNotBlank(emisionVO.getMercado())){
            	emision.getInstrumento().setMercado(emisionVO.getMercado());
            }
            if (emisionVO.getFechaVencimiento()!=null){
            	emision.setFechaVencimiento(emisionVO.getFechaVencimiento());
            }
        }

        return emision;
    }

    /**
     * Metodo utilitario que construye y retorna una EmisionVO a partir de los
     * valores contenidos en una EmisionPersistence sin considerar su saldo disponible.
     * 
     * @param emision
     *            EmisionPersistence
     * @return EmisionVO emisionVO construida a partir de la emision recibida
     */
    public static EmisionVO getEmisionVO(EmisionPersistence emision) {

        logger.info("Entrando a UtilsDaliVO.getEmisionVO()");

        // Si la emision recibida es null, el metodo retorna null
        if (emision == null || emision.getEmisionPk() == null) {
            logger.debug("La emision recibida, o su PK, esta NULL");
            return null;
        }

        // Se construye la EmisionVO
        EmisionVO emisionVO = new EmisionVO();

        // Se settean los atributos
        emisionVO.setIdTipoValor(emision.getEmisionPk().getTv());
        emisionVO.setEmisora(emision.getEmisionPk().getEmisora());
        emisionVO.setSerie(emision.getEmisionPk().getSerie());
        emisionVO.setCupon(emision.getEmisionPk().getCupon());
        emisionVO.setCuponCortado(emision.getCuponCortado());
        emisionVO.setAlta(emision.getEmisionExtra());
        emisionVO.setIsin(emision.getIsin());
        emisionVO.setFechaVencimiento(emision.getFechaVencimiento());

        if (emision.getInstrumento() != null) {
            emisionVO.setMercado(emision.getInstrumento().getMercado());
        }

        emisionVO.setPrecioVector(emision.getPrecioVector());

        return emisionVO;
    }

    /**
     * Crea una EmisionPK a partir de una EmisionVO
     * 
     * @param emisionVO
     *            con los valores base
     * @return EmisionPK creada siempre.
     */
    public static EmisionPK getEmisionPK(EmisionVO emisionVO) {

        logger.info("Entrando a UtilsDaliVO.getEmisionPK()");

        EmisionPK emisionPK = new EmisionPK();

        if (emisionVO != null) {
            if (StringUtils.isNotBlank(emisionVO.getIdTipoValor())) {
                emisionPK.setTv(emisionVO.getIdTipoValor().trim());
            }
            if (StringUtils.isNotBlank(emisionVO.getEmisora())) {
                emisionPK.setEmisora(emisionVO.getEmisora().trim());
            }
            if (StringUtils.isNotBlank(emisionVO.getSerie())) {
                emisionPK.setSerie(emisionVO.getSerie().trim());
            }
            if (StringUtils.isNotBlank(emisionVO.getCupon())) {
                emisionPK.setCupon(emisionVO.getCupon().trim());
            }

        }

        return emisionPK;
    }

    /**
     * Crea una EmisionPK a partir de una EmisionVO
     * 
     * @param emisionVO
     *            con los valores base
     * @return EmisionPK creada solo si la emisionVO no estaba nula ni vacia.
     */
    public static EmisionPK parseEmisionVO2EmisionPK(EmisionVO emisionVO) {

        logger.info("Entrando a UtilsDaliVO.parseEmisionVO2EmisionPK()");

        EmisionPK emisionPK = null;

        if (emisionVO != null) {

            EmisionPK emisionPKTransfer = new EmisionPK();
            boolean required = false;
            if (StringUtils.isNotBlank(emisionVO.getIdTipoValor())) {
                emisionPKTransfer.setTv(emisionVO.getIdTipoValor().trim());
                required = true;
            }
            if (StringUtils.isNotBlank(emisionVO.getEmisora())) {
                emisionPKTransfer.setEmisora(emisionVO.getEmisora().trim());
                required = true;
            }
            if (StringUtils.isNotBlank(emisionVO.getSerie())) {
                emisionPKTransfer.setSerie(emisionVO.getSerie().trim());
                required = true;
            }
            if (StringUtils.isNotBlank(emisionVO.getCupon())) {
                emisionPKTransfer.setCupon(emisionVO.getCupon().trim());
                required = true;
            }
            if (required) {
                emisionPK = emisionPKTransfer;
            }
        }
        return emisionPK;
    }

    /**
     * Metodo utilitario que construye un arreglo de EmisionVO a partir de los
     * valores contenidos en un arreglo de EmisionPersistence
     * 
     * @param emision
     *            EmisionPersistence[] arreglo de emisiones
     * @return EmisionVO[] arreglo de emisionesVO construidas a partir de las
     *         emisiones contenidas en el arreglo recibido
     */
    public static EmisionVO[] getEmisionesVO(EmisionPersistence[] emision) {

        logger.info("Entrando a UtilsDaliVO.getEmisionesVO()");

        // Si el arreglo de emision recibido es null, el metodo retorna null
        if (emision == null) {
            logger.debug("Se recibio un arreglo de emisiones nulo");
            return null;
        }
        logger.debug("El arreglo de emisiones recibido tiene [ " + emision.length + " ] elementos");

        List<EmisionVO> listaEmisionesVO = new ArrayList<EmisionVO>(); // Lista general de
        // EmisionesVO

        // Se extrae cada emision del arreglo recibido,
        // se construye la emisionVO y se coloca en la Lista general de
        // EmisionesVO
        for (int i = 0; i < emision.length; i++) {

            EmisionPersistence elementoEmision = emision[i];

            if (elementoEmision != null && elementoEmision.getEmisionPk() != null) {

                // Se construye la EmisionVO
                EmisionVO emisionVO = new EmisionVO();

                // Se settean los valores de la EmisionVO a partir de la EmisionPersistence
                emisionVO.setIdTipoValor(elementoEmision.getEmisionPk().getTv());
                emisionVO.setEmisora(elementoEmision.getEmisionPk().getEmisora());
                emisionVO.setSerie(elementoEmision.getEmisionPk().getSerie());
                emisionVO.setCupon(elementoEmision.getEmisionPk().getCupon());
                emisionVO.setIsin(elementoEmision.getIsin());
                emisionVO.setFechaVencimiento(elementoEmision.getFechaVencimiento());
                emisionVO.setPrecioVector(elementoEmision.getPrecioVector());

                if (elementoEmision.getInstrumento() != null) {
                    emisionVO.setMercado(elementoEmision.getInstrumento().getMercado());
                }

                // Se recorre el set de valores
                Set setValores = elementoEmision.getValores();

                /*
                 * log.debug("El objeto elementoEmision[ " + i +" ] tiene [ " +
                 * setValores.size() + " ] valores");
                 */

                if (setValores != null) {
                    for (Iterator iter = setValores.iterator(); iter.hasNext();) {

                        // Se obtiene el valor y se setea el saldodisponible
//                        Valor valor = (Valor) iter.next();
//                        emisionVO.setSaldoDisponible(valor.getSaldoDisponible());
//
//                        // Se agrega la emisionVO a la lista
//                        listaEmisionesVO.add(emisionVO);
                    }
                }
                else {
                    // Se agrega la emisionVO a la lista
                    listaEmisionesVO.add(emisionVO);
                }

            }

        }

        // Se construye el arreglo de emisionesVO a partir de la lista
        EmisionVO[] emisionesVO = (EmisionVO[]) listaEmisionesVO
                .toArray(new EmisionVO[listaEmisionesVO.size()]); // Es un
        // arreglo
        // de
        // EmisionesVO

        logger.debug("Se retorna el arreglo EmisionesVO [" + emisionesVO.length + "]");

        return emisionesVO;

    }

    /**
     * Regresa un objeto PageVO, construido a partir del objeto PaginaVO que
     * recibe y verifica.
     * 
     * @param paginaVO -
     *            Objeto PaginaVO.
     * @return PageVO - Objeto PageVO inicializado con los valores de offset y
     *         registrosXPag que se reciben de la pagina de entrada.
     */
    public static PageVO getPageVO(PaginaVO paginaVO) {

        logger.info("Entrando a UtilsDaliVO.getPageVO()");

        paginaVO = getPaginaNotBlank(paginaVO);

        // Se construye la pagina de tipo PageVO
        PageVO pageVO = new PageVO();
        pageVO.setOffset(paginaVO.getOffset());
        pageVO.setRegistrosXPag(paginaVO.getRegistrosXPag());
        pageVO.setValores(paginaVO.getValores());

        return pageVO;
    }

    /**
     * Recibe un objeto de tipo PageVO y verifica que no sea nulo, construye y
     * regresa un objeto de tipo PaginaVO con los datos contenidos en el objeto
     * recibido.
     * 
     * @param pageVO -
     *            Objeto de tipo PageVO recibido
     * @return PaginaVO - Objeto de tipo PaginaVO a retornar
     * @throws BusinessException
     */
    public static PaginaVO getPaginaVO(PageVO pageVO) throws BusinessException {

        logger.info("Entrando a UtilsDaliVO.getPaginaVO()");

        if (pageVO == null) {
            logger.debug("ERROR: El PageVO se recibio NULL");
            throw new BusinessException("No se han encontrado registros coincidentes " +
                    "para los criterios de b\u00fasqueda seleccionados.");
        }

        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(pageVO.getOffset());
        paginaVO.setRegistros(pageVO.getRegistros());
        paginaVO.setRegistrosXPag(pageVO.getRegistrosXPag());
        paginaVO.setTotalRegistros(pageVO.getTotalRegistros());
        paginaVO.setValores(pageVO.getValores());

        return paginaVO;
    }

    /**
     * Retorna un objeto PaginaVO valido (offset >=0 y registrosXPag >= 0)
     * 
     * @param paginaVO
     * @return PaginaVO pagina valida
     */
    public static PaginaVO getPaginaNotBlank(PaginaVO paginaVO) {

        logger.info("Entrando a UtilsDaliVO.getPaginaNotBlank()");

        if (paginaVO == null) {
            paginaVO = new PaginaVO();
        }
        if (paginaVO.getOffset() == null || paginaVO.getOffset().intValue() < 0) {
            paginaVO.setOffset(INTEGER_ZERO);
        }
        if (paginaVO.getRegistrosXPag() == null || paginaVO.getRegistrosXPag().intValue() < 0) {
            paginaVO.setRegistrosXPag(PaginaVO.TODOS);
        }
        return paginaVO;
    }

    /**
     * Valida que la pagina no sea nula y que la misma tenga registros
     * @param pageVO
     * @throws BusinessException Excepcio&oacute;n arrojada si no hay registros. 
     *                           Utiliza el mensaje predeterminado: "No se han encontrado registros 
     *                           coincidentes para los criterios de b&uacute;squeda seleccionados."
     */
    public static void validaPage(PageVO pageVO) throws BusinessException {
        
        logger.info("Entrando a CatalogoServiceImpl.validaPage()");
        
        UtilsDaliVO.validaPage(pageVO, null);
        
    }
    
    /**
     * Valida que la pagina no sea nula y que la misma tenga registros
     * @param pageVO
     * @param mensaje Mensaje personalizado de la excepci&oacute;n. Si es null o vacio, 
     *                se utiliza el mensaje predeterminado: "No se han encontrado registros 
     *                coincidentes para los criterios de b\u00fasqueda seleccionados."
     * @throws BusinessException Excepcio&oacute;n arrojada si no hay registros
     */
    public static void validaPage(PageVO pageVO, String mensaje) throws BusinessException {
        
        logger.info("Entrando a CatalogoServiceImpl.validaPage()");

        if(pageVO == null || pageVO.getRegistros() == null || pageVO.getRegistros().isEmpty()){

            if(StringUtils.isBlank(mensaje)) {
                throw new BusinessException("No se han encontrado registros coincidentes " +
                        "para los criterios de b\u00fasqueda seleccionados.");
            }
            else {
                throw new BusinessException(mensaje);
            }

        }
        
    }
    
//    /***************************************************************************
//     * Metodo utilitario que construye y retorna una ParticipanteVO a partir de
//     * los valores contenidos en otro objeto de tipo CParticipante
//     * 
//     * @param participante
//     * @return ParticipanteVO recien creado, devuelve null si el participante
//     *         esta NULL o su clave esta NULL o BLANK
//     */
//    public static ParticipanteVO getParticipanteVO(CParticipante participante) {
//
//        log.info("Entrando a UtilsDaliVO.getParticipanteVO()");
//
//        // Si el participante recibido es null, el metodo retorna null
//        if (participante == null || StringUtils.isBlank(participante.getClaveParticipante())) {
//            log.debug("El participante recibido es nulo, o su PK, esta NULL");
//            return null;
//        }
//
//        // Se construye la ParticipanteVO
//        ParticipanteVO participanteVO = new ParticipanteVO();
//
//        // Se settean los atributos
//        participanteVO.setClave(participante.getClaveParticipante());
//        participanteVO.setNombreCorto(
//                StringUtils.isNotBlank(participante.getNombreCorto()) ? 
//                        participante.getNombreCorto().trim() : null);
//        participanteVO.setNombreCompleto(
//                StringUtils.isNotBlank(participante.getNombreParticipante()) ? 
//                        participante.getNombreParticipante().trim() : null);
//
//        return participanteVO;
//
//    }

//    /**
//     * Convierte una lista de EmisionVOPersistence en otra de EmisionVO
//     * 
//     * @param listaEmisionesVOPersistence
//     * @return List
//     */
//    public static List emisionesVOPersistence2EmisionesVO(List listaEmisionesVOPersistence) {
//
//        log.info("Entrando a DivisionInternacionalServiceImpl.emisionesVOPersistence2EmisionesVO()");
//
//        Assert.notNull(listaEmisionesVOPersistence, "La lista recibida esta NULL");
//        Assert.isTrue(!listaEmisionesVOPersistence.isEmpty());
//
//        List<EmisionVO> listaEmisionesVO = new ArrayList<EmisionVO>();
//
//        for (Iterator iter = listaEmisionesVOPersistence.iterator(); iter.hasNext();) {
//            EmisionVOPersistence elementoEmisionPersistence = (EmisionVOPersistence) iter.next();            
//            EmisionVO emisionVO = emisionVOPersistence2EmisionVO(elementoEmisionPersistence);
//            listaEmisionesVO.add(emisionVO);
//        }
//
//        return listaEmisionesVO;
//    }
    
//    /**
//     * Convierte una instancia de EmisionVOPersistence en otra de EmisionVO
//     * @param elementoEmisionPersistence
//     * @return
//     */
//    public static EmisionVO emisionVOPersistence2EmisionVO(
//    		EmisionVOPersistence elementoEmisionPersistence){
//    	
//    	log.info("Entrando a UtilsDaliVO.emisionVOPersistence2EmisionVO");
//    	
//        EmisionVO emisionVO = new EmisionVO();
//        emisionVO.setIdTipoValor(elementoEmisionPersistence.getIdTipoValor());
//        emisionVO.setEmisora(elementoEmisionPersistence.getEmisora());
//        emisionVO.setSerie(elementoEmisionPersistence.getSerie());
//        emisionVO.setCupon(elementoEmisionPersistence.getCupon());
//        emisionVO.setSaldoDisponible(elementoEmisionPersistence.getSaldoDisponible());
//        emisionVO.setFechaVencimiento(elementoEmisionPersistence.getFechaVencimiento());
//        if (emisionVO.getFechaVencimiento() != null) {
//            emisionVO.setDiasVigentes(elementoEmisionPersistence.getDiasVigentes());
//        }
//        emisionVO.setIsin(elementoEmisionPersistence.getIsin());
//        emisionVO.setAlta(elementoEmisionPersistence.getAlta());
//        emisionVO.setMercado(elementoEmisionPersistence.getMercado());
//        emisionVO.setPrecioVector(elementoEmisionPersistence.getPrecioVector());
//        
//        return emisionVO;
//    	
//    }

}