/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigInteger;
import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.BovedaDao;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.servicios.dto.BovedaDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;

/**
 * Implementacion del servicio de negocio BovedaService.
 */
public class BovedaServiceImpl implements BovedaService {

    /** Logger */
	private static final Logger LOG = LoggerFactory.getLogger(BovedaServiceImpl.class);
    
    /** Dao para las consultas de boveda */
    private BovedaDao bovedaDao;
    
    /** Dao para las consultas de boveda */
    private com.indeval.portalinternacional.persistence.dao.BovedaDao bovedaIntDao;
    
    /** Dao para las consultas de Cat Bic */
    private CatBicDao catBicDao;

	/*
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService#getBovedaById(java.lang.Integer)
	 */
	public BovedaDto getBovedaById(Integer idBoveda) {
	    Boveda bovedaObj = this.bovedaDao.findBovedaById(idBoveda.longValue());
	    BovedaDto bovedaDto = null;
	    if (bovedaObj != null) {
	        bovedaDto = new BovedaDto(bovedaObj.getIdBoveda().intValue(), bovedaObj.getNombreCorto(), bovedaObj.getDescripcion(), 1);
	    }
	    return bovedaDto;
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService#obtenerBoveda(java.lang.Integer)
	 */
	public BovedaDto obtenerBoveda(Integer idBoveda) throws BusinessException {
        LOG.info("####### Entrando a BovedaServiceImpl.obtenerBoveda()...");
        if (idBoveda == null) {
            throw new BusinessException("La Emisi\u00F3n no tiene una Boveda asignada!");
        }
        BovedaDto boveda = this.getBovedaById(idBoveda);
        if (boveda == null) {
            throw new BusinessException("La Boveda no existe!");
        }

        return boveda;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService#obtenerBovedaByNombreCorto(String)
     */
	public Bovedas obtenerBovedaByNombreCorto(String nombreCorto) {
        return bovedaIntDao.findBovedaByNombreCorto(nombreCorto);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService#findAll()
	 */
    @SuppressWarnings("unchecked")
    public List<Bovedas> findAll() throws BusinessException {
	    try {
	        return (List<Bovedas>) this.bovedaIntDao.findAll(Bovedas.class.getName());
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new BusinessException(e.getMessage());
	    }
	}

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService#findAllBovedasValores()
     */
    public List<Bovedas> findAllBovedasValores() throws BusinessException {
        try {
            return (List<Bovedas>) this.bovedaIntDao.findAllBovedasValores();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService#getBovedaByIdCuentaBoveda(Integer)
     */
    public Bovedas getBovedaByIdCuentaBoveda(Integer idCuentaBoveda) throws BusinessException {
        try {
            return this.bovedaIntDao.getBovedaByIdCuentaBoveda(idCuentaBoveda);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService#obtenerCatBic(Bovedas)
     */
    public String obtenerCatBic(Bovedas boveda) throws BusinessException {
        try {
            int catbics = this.catBicDao.findCatBicByCuentaNombrada(boveda.getIdCuentaBoveda().longValue());
            if (catbics > 1) {
                throw new Exception("Existe m\u00E1s de un CatBic para la B\u00F3veda!");
            }
            String cb = this.catBicDao.findCatBicEntityByCuentaNombrada(boveda.getIdCuentaBoveda().longValue());
            if (cb != null) {
                return cb;
            }
            throw new Exception("No existe CatBic para la B\u00F3veda " + boveda.getNombreCorto() + "!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    // Cambio Multidivisas
    @Override
    public List<BigInteger> obtenerBovedasPorDivisa(DivisaDTO divisaDTO) {
        return bovedaIntDao.obtenerBovedasPorDivisa(divisaDTO);
    }

    @Override
    public List<BovedaDto> buscarBovedasPorTipoCustodia(BovedaDto criterio, List<Long> idsBoveda, EstadoPaginacionDTO estadoPaginacion) {
        return bovedaIntDao.buscarBovedasPorTipoCustodia(criterio, estadoPaginacion);
    }

    @Override
    public Long findCatBicEnBaseABovedaEfectivoParticipante(Long idBovedaEfectivo, Long idInstitucion) {
        return this.catBicDao.findCatBicEnBaseABovedaEfectivoParticipante(idBovedaEfectivo, idInstitucion);
    }

    /**
     * Multidivisas
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService#findAllBovedasEfectivo()
     * */
    @Override
    public List<Bovedas> findAllBovedasEfectivo() {
        return this.bovedaIntDao.findAllBovedasEfectivo();
    }


// Fin Cambio Multidivisas
    public void setBovedaDao(BovedaDao bovedaDao) {
        this.bovedaDao = bovedaDao;
    }

    public void setBovedaIntDao(com.indeval.portalinternacional.persistence.dao.BovedaDao bovedaIntDao) {
        this.bovedaIntDao = bovedaIntDao;
    }

    public void setCatBicDao(CatBicDao catBicDao) {
        this.catBicDao = catBicDao;
    }

}
