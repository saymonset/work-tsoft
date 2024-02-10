// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.RetiroEfectivoIntPendientes;
import com.indeval.portalinternacional.persistence.dao.RetiroEfectivoIntPendientesDao;

public class RetiroEfectivoIntPendientesDaoImpl extends BaseDaoHibernateImpl implements RetiroEfectivoIntPendientesDao {
    @Override
    public void saveRetiroEfectivoIntPendientes(RetiroEfectivoIntPendientes retiroEfectivoIntPendientes) {
        // logger.debug("Entrando a saveRetiroEfectivoIntPendientes");
        System.out.println("Entrando a saveRetiroEfectivoIntPendientes");
        try{
//			this.getHibernateTemplate().setAllowCreate(true);
//			this.getHibernateTemplate().setCheckWriteOperations(false);
            this.getHibernateTemplate().save(retiroEfectivoIntPendientes);
            this.getHibernateTemplate().flush();
        } catch(Exception ex){
            //logger.error("Error en saveRetiroEfectivoIntPendientes", ex);
            System.out.println("Error en saveRetiroEfectivoIntPendientes");
            System.out.println(ex.getMessage());
        }
    }
}
