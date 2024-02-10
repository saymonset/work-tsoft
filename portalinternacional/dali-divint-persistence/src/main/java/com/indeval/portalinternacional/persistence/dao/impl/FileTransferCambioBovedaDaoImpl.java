/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.modelo.BitacoraOperaciones;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransfer;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferCambioBoveda;
import com.indeval.portalinternacional.persistence.dao.FileTransferCambioBovedaDao;

/**
 * DAO de implementaci&oacute;n de FileTransferCambioBovedaDao.
 */

public class FileTransferCambioBovedaDaoImpl extends BaseDaoHibernateImpl implements FileTransferCambioBovedaDao {

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.FileTransferDao#save(FileTransfer)
     */
    public Integer save(FileTransferCambioBoveda fileTransfer) {
        getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
        Integer idFileTransfer = (Integer) getHibernateTemplate().save(fileTransfer);
        getHibernateTemplate().flush();
        return idFileTransfer;
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.FileTransferDao#update(FileTransfer)
     */
    public void update(FileTransferCambioBoveda fileTransfer) {
        getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
        getHibernateTemplate().update(fileTransfer);
        getHibernateTemplate().flush();
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.FileTransferDao#getByIdFolioYEstadoCreado(String)
     */
    @SuppressWarnings("unchecked")
    public FileTransferCambioBoveda getByIdFolioYEstadoCreado(String idFolio) {
        FileTransferCambioBoveda ft = null;

        StringBuffer query = new StringBuffer();        
        query.append(" from " + FileTransferCambioBoveda.class.getName() + " f " );
        query.append(" where f.idFolio = ? ");
        query.append(" and f.estado = ? ");
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(idFolio);
        params.add(Constantes.ESTATUS_CREADO);
        ArrayList<Type> tipos = new ArrayList<Type>();
        tipos.add(new StringType());
        tipos.add(new StringType());
        Query q = getSession().createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
        List<FileTransferCambioBoveda> res = q.list();
        
        if (res != null && !res.isEmpty()) {
            ft = (FileTransferCambioBoveda) res.get(0);
        }

        return ft;
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.FileTransferDao#salvarBitacoraOperaciones(BitacoraOperaciones)
     */
    public Integer salvarBitacoraOperaciones(BitacoraOperaciones bitacoraOperaciones) {
        getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
        Integer idBitacoraOperaciones = new Integer( ((Long) getHibernateTemplate().save(bitacoraOperaciones)).toString() );
        getHibernateTemplate().flush();
        return idBitacoraOperaciones;
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.FileTransferDao#flush()
     */
    public void flush() {
        getHibernateTemplate().flush();
    }

}
