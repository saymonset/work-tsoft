/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */

package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.InstitucionesService;
import com.indeval.portalinternacional.middleware.servicios.dto.InstitucionWebDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Instituciones;
import com.indeval.portalinternacional.middleware.servicios.vo.InstitucionesVo;
import com.indeval.portalinternacional.persistence.dao.InstitucionesDao;

/**
 * Implementaci&oacute;n de la interfaz de servicio InstitucionesService
 * 
 * @author IDS Comercial S.A de C.V
 * @version 1.0
 */
public class InstitucionesServiceImpl implements InstitucionesService {

	private static final long serialVersionUID = 1L;

    /** DAO de instituciones */
	private InstitucionesDao institucionesDao;

    /** 
     * @see com.indeval.sidv.emisiones.middleware.service.InstitucionesService#getInstitucionByTipoYFolio(java.lang.Integer, java.lang.String)
     */
    public InstitucionesVo getInstitucionByTipoYFolio(Integer idtipoInstitucion, String folio) {
        InstitucionesVo itVo = null;   
        String tipoInstitucion =  idtipoInstitucion.toString().length() < 2 ? "0" + idtipoInstitucion : idtipoInstitucion.toString();

        Instituciones it = institucionesDao.getInstitucionByTipoYFolio(tipoInstitucion, folio);
        if (it != null) {
            itVo = this.creaInstitucionVo(it);
        }

        return itVo;
    }

    // Cambio Multidivisas
    @Override
    public InstitucionWebDTO buscarInstitucionPorClaveYFolio(String idFolio) {
    	return institucionesDao.buscarInstitucionPorClaveYFolio(idFolio);
    }
    // Fin Cambio Multidivisas
    /**
     * Construte un vo de instituciones a partir de un objeto Instituciones.
     * El objeto emision debe tener los datos correspondientes al tipo de institucion.     
     * @param institucion
     * @return  InstitucionesVo
     */    
    public InstitucionesVo creaInstitucionVo(Instituciones institucion){
        InstitucionesVo itVo = new InstitucionesVo();

        if (null!= institucion){
            itVo.setId(institucion.getIdInstitucion());
            itVo.setFolio(institucion.getFolio());
            itVo.setClaveCasfim(institucion.getClaveCasfim());
            itVo.setRazonSocial(institucion.getRazonSocial());
            itVo.setDomicilioFiscal(institucion.getDomicilioFiscal());
            itVo.setRfc(institucion.getRfc());
            itVo.setNombre(institucion.getNombre());
            itVo.setCodigoPostal(institucion.getCodigoPostal());
            itVo.setOperaTituloGlobal(institucion.isOpTituloGlobal());
            itVo.setFechaAlta(institucion.getFechaAlta());
            itVo.setFechaBaja(institucion.getFechaBaja());
            itVo.setIdEstadoInstitucion(institucion.getIdEstadoInstitucion());
            itVo.setBic(institucion.getBic());
            itVo.setInterna(institucion.getInterna() != null ? institucion.getInterna().booleanValue() : false);
            itVo.setClaveSpei(institucion.getClaveSpei());
            itVo.setClaveSpeiBeneficiario(institucion.getClaveSpeiBeneficiario());
            itVo.setRfcBeneficiario(institucion.getRfcBeneficiario());
            itVo.setNombreBeneficiario(institucion.getNombreBeneficiario());
            itVo.setClabeBeneficiario(institucion.getClabeBeneficiario());
            itVo.setOperaSiac(institucion.getOperaSiac());
            itVo.setNacionalidad(institucion.getNacionalidad());
        }

        return itVo;
    }

    public void setInstitucionesDao(InstitucionesDao institucionesDao) {
        this.institucionesDao = institucionesDao;
    }

}
