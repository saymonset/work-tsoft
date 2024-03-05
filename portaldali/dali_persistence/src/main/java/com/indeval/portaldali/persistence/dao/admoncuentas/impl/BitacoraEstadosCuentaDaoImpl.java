/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admoncuentas.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.admoncuentas.BitacoraEstadosCuentaDao;
import com.indeval.portaldali.persistence.model.BitacoraEstadosCuentaRetiro;

/**
 * Implementacion de la interface BitacoraEstadosCuentaDao, contiene los metodos 
 * para las acciones sobre la bitacora de cambios de estado.  
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class BitacoraEstadosCuentaDaoImpl  extends BaseDaoHibernateImpl implements BitacoraEstadosCuentaDao {
	
    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(BitacoraEstadosCuentaDaoImpl.class);
	
	/** 
	 * Crea un registro en la bitacora 
	 * @param cuenta objeto CuentaRetiro
	 */
	public void save(BitacoraEstadosCuentaRetiro bitacora){
		logger.info("salvando una BitacoraEstadosCuentaRetiro");
		super.save(bitacora);
	}
	
	/**
	 * Consulta la bitacora por id 
	 * @param id objeto Long que identifica una bitacora
	 */
	public BitacoraEstadosCuentaRetiro getBitacora(Long id){
		logger.debug("Encontrando... BitacoraEstadosCuentaRetiro con id : " + id);
		return (BitacoraEstadosCuentaRetiro)getHibernateTemplate().get(BitacoraEstadosCuentaRetiro.class, id);
	} 
	
	/**
	 * Actualiza bitacora 
	 * @param cuenta objeto CuentaRetiro
	 */
	public void saveOrUpdate(BitacoraEstadosCuentaRetiro bitacora){
		logger.debug("saveOrUpdate, BitacoraEstadosCuentaRetiro");
		getHibernateTemplate().saveOrUpdate(bitacora);
	}
	
	/**
	 * Elimina cuenta  
	 * @param cuenta objeto CuentaRetiro
	 */
	public void delete(BitacoraEstadosCuentaRetiro bitacora){
		logger.debug("eliminando CuentaRetiroNacional cuentaId:"+bitacora.getIdBitacoraEdosCuenta());
		getHibernateTemplate().delete(bitacora);
	}
	
	/**
	 * Busca la bitacora de los estados por un id de cuenta
	 * @param idcuenta id de la cuenta retiro 
	 */
	public BitacoraEstadosCuentaRetiro getBitacoraPorCuenta(Long idCuenta){
		logger.debug("buscando bitacora para la cuenta retiro id:"+idCuenta);
		return (BitacoraEstadosCuentaRetiro)getHibernateTemplate()
					.find("from "+BitacoraEstadosCuentaRetiro.class.getName() + " bcr " +
						  "where bcr.cuentaRetiro.idCuentaRetiro= "+idCuenta)
				    .get(0);
	}
	
	/**
	 * Busca las bitacoras correspondientes a los id de las cuentas (CuentaRetiro) 
	 * @param idCuentas id de las cuentas (CuentaRetiro) separadas por coma
	 */
	@SuppressWarnings("unchecked")
	public List getBitacoraXIdsCuentaRetiro(String idCuentas){
		logger.debug("buscando bitacoras para las CuentaRetiro id:"+idCuentas);
		List resultados = getHibernateTemplate()
							.find("from "+BitacoraEstadosCuentaRetiro.class.getName() + " bcr " +
								  "		join fetch bcr.cuentaRetiro "+
								  "		join fetch bcr.cuentaRetiro.estado "+
								  "where bcr.cuentaRetiro.idCuentaRetiro in ("+idCuentas+")");
		return resultados==null? new ArrayList(0):resultados;
	}
}
