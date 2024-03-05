/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.filetransfer.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.filetransfer.FileTransferDao;
import com.indeval.portaldali.persistence.model.FileTransfer;
import com.indeval.portaldali.persistencia.fileTransfer.ArchivosFileTransfer;

/**
 * Implementacion de la interfaz de datos FileTransferDao
 * 
 * @author Jose Aviles
 * @author Pablo Balderas
 */
public class FileTransferDaoImpl extends BaseDaoHibernateImpl implements FileTransferDao {

    private static final Logger logger = LoggerFactory.getLogger(FileTransferDaoImpl.class);

    /**
     * @see com.indeval.portaldali.persistence.dao.filetransfer.FileTransferDao#getByIdFolioTipo(java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
	public List<FileTransfer> getByIdFolioTipo(final String idInst, final String folioInst, final String tipoProceso) {
        
        logger.info("Entrando a FileTransferDaoImpl.getByIdFolioTipo()");
        
        Assert.notNull(idInst, "Falta el ID de la institucion");
        Assert.notNull(folioInst, "Falta el FOLIO de la institucion");
        Assert.notNull(tipoProceso, "Falta el TIPO DE PROCESO");
        
        HibernateCallback hcb = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session.createCriteria(FileTransfer.class).add(
                        Expression.eq("fileTransferPK.idInst", idInst)).add(
                        Expression.eq("fileTransferPK.folioInst", folioInst)).add(
                        Expression.eq("fileTransferPK.tipoReg", tipoProceso)).addOrder(
                        Order.asc("fileTransferPK.consec")).list();
            }
        };
        return (List<FileTransfer>) getHibernateTemplate().execute(hcb);
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portaldali.persistence.dao.filetransfer.FileTransferDao#guardarArchivoFileTransfer(com.indeval.portaldali.persistencia.posicion.ArchivosFileTransfer)
     */
    public void guardarArchivoFileTransfer(ArchivosFileTransfer archivosFileTransfer) throws BusinessException {
    	try {
    		getHibernateTemplate().save(archivosFileTransfer);
    	}
    	catch (Exception e) {
    		throw new BusinessException(e.getMessage(), e);
		}
    }
}
