/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;
import java.util.Map;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptPayeeW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptionFatcaW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILACodigoDepartamento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILASectorEconomico;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoDocumento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoEmpresa;
import com.indeval.portalinternacional.middleware.servicios.vo.BeneficiariosPaginacionVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaNombreBeneficiarioParam;
import com.indeval.portalinternacional.middleware.servicios.vo.NombreBeneficiario;

/**
 * 
 * @author Oscar Garcia
 * 
 */
public interface BeneficiarioDao extends BaseDao {

    /**
     * Consulta los beneficiarios
     * 
     * @param ConsultaBeneficiariosParam param
     * @param PaginaVO paginaVO
     * @param Boolean isPopup
     * @return PaginaVO
     */
    PaginaVO findBeneficiarios(ConsultaBeneficiariosParam param, PaginaVO paginaVO, Boolean isPopup);

    /**
     * Consulta los beneficiarios, para obtener las letras iniciales
     * 
     * @param ConsultaBeneficiariosParam param
     * @param PaginaVO paginaVO
     * @return PaginaVO
     */
    PaginaVO findBeneficiariosLetras(ConsultaBeneficiariosParam param, PaginaVO paginaVO);

    /**
     * Consulta los beneficiarios por nombre para ver si existen y evitar que se capturen mas de una
     * vez
     * 
     * @param ConsultaBeneficiariosParam param
     * @param PaginaVO paginaVO
     * @return PaginaVO
     */
    PaginaVO findBeneficiariosByName(ConsultaNombreBeneficiarioParam param);

    /**
     * Obtiene el Beneficiario a partir de su ID
     * 
     * @param Long idBeneficiario
     * @return Beneficiario
     */
    Beneficiario findBeneficiarioById(Long idBeneficiario);
    
    /**
     * Obtiene el Beneficiario a partir de su uoi
     * 
     * @param  String uoiNumber
     * @return Beneficiario
     */
    public Beneficiario findBeneficiarioByUoiNumber(final String uoiNumber);

    /**
     * Obtiene el Beneficiario a partir de su ID aunque esten eliminados
     * 
     * @param Long idBeneficiario
     * @return Beneficiario
     */
    Beneficiario findBeneficiarioByIdEliminados(Long idBeneficiario);

    /**
     * Obtiene el Beneficiario a partir de su UOI
     * 
     * @param String uoiNumber
     * @return Long
     */
    Long findBeneficiarioByUOI(String uoiNumber);

    /**
     * Obtiene los nombres de los beneficiairos
     * 
     * @return Lista de todos los beneficiarios y sus nombres
     */
    List<NombreBeneficiario> getNombresBeneficiarios(ConsultaNombreBeneficiarioParam param);

    /**
     * Obtiene los nombres de los beneficiairos
     * 
     * @return Lista de todos los beneficiarios y sus nombres
     */
    List<NombreBeneficiario> getNombresBeneficiarios(ConsultaBeneficiariosParam param);

    /**
     * Obtiene el registro de la tabla de beneficiarios - institucion
     * 
     * @param idBeneficiario Id del beneficiario
     * @param idInstitucion Id de la institucion
     * @return El registro de beneficiario institucion
     */
    BeneficiarioInstitucion getBeneficiarioInstitucion(Long idBeneficiario, Long idInstitucion);


    /**
     * Obtiene el numero maximo de registros de beneficiarios
     * 
     * @return EL numero maximo de registros de beneficiarios
     */
    Long numeroMaximoRegistros();


    Beneficiario getBeneficiarioByUoiInstitucion(String uoi, Long estatusBeneficiario,
            Long idTipoInstitucion, String folioInstitucion);


    List<BeneficiariosPaginacionVO> paginasConsultaBeneficiarios(ConsultaBeneficiariosParam params);

    Integer depurarFormatosFiscalesBeneficiarios(Integer anioDepuracion, List<String> tiposFormato);

    List<Beneficiario> getBeneficiariosDepuracion(Integer anioDepuracion, List<String> tiposFormato);

    /**
     * Regresa un Beneficiario por su valor Uoi
     * 
     * @param uoiNumber
     * @return Beneficiario
     * */
    Beneficiario consultaBeneficiarioByUoiForAdp(String uoi);

    /**
     * Regresa un Beneficiario por su valor Uoi
     * 
     * @param uoiNumber
     * @return Beneficiario
     * */
    Beneficiario consultaBeneficiarioByUoiForInstituciones(String uoi);

    /**
     * Consulta el catalogo de Exempt Payee para W9
     * 
     * @return
     */
    List<ExemptPayeeW9> consultaCatExemptPayeeW9();

    /**
     * Consulta catalogo de Exemption Fatca para W9
     * 
     * @return
     */
    List<ExemptionFatcaW9> consultaCatExemptionFatcaW9();

    /**
     * Regresa un mapa del catalogo ExemptionFatcaW9, el valor de la llave es el fatca code, el
     * valor es el id asiganado
     * 
     * @return Map con el valor de la llave es el fatca code, el valor es el id asiganado
     */
    Map<Integer, Long> getCatalogoExemptPayeeW9();

    /**
     * Regresa un mapa del catalogo ExemptionFatcaW9, el valor de la llave es el fatca code, el
     * valor es el id asiganado
     * 
     * @return Map con el valor de la llave es el fatca code, el valor es el id asiganado
     */
    Map<String, Long> getCatalogoExemptionFatcaW9();

    /**
     * Consulta del catalago de tipo de sectores economicos para los formatos MILA
     * 
     * @return Lista con los valores del catalogo
     */
    List<MILASectorEconomico> consultaCatMilaSectorEconomico();

    /**
     * Consulta del catalogo de tipo de documentos para los formatos MILA
     * 
     * @return Lista con los valores del catalogo
     */
    List<MILATipoDocumento> consultaCatMilaTipoDocumento();

    /**
     * Consulta del catalogo de tipo de empresas para los formatos MILA
     * 
     * @return Lista con los valores del catalogo
     */
    List<MILATipoEmpresa> consultaCatMilaTipoEmpresa();

    /**
     * Consulta de entidades federativas para los formato MILA
     * 
     * @return Lista con los valores del catalogo
     */
    List<MILACodigoDepartamento> consultaCatMilaEstados();

    /**
     * Consulta para obtener el valor maximo de beneficiarios que tiene una institucion
     * 
     * @param idCuentaNombrada
     * @return
     */
    Long findMaxBeneficiarioMilaByCustodio(long idCuentaNombrada);

    Long findUOIMaxConsecutivo(final String uoiNumber);

}
