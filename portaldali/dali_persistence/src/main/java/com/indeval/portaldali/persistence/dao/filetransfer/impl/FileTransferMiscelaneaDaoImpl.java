/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
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
import com.indeval.portaldali.persistence.dao.filetransfer.FileTransferMiscelaneaDao;
import com.indeval.portaldali.persistence.model.FileTransferMiscelanea;

/**
 * Implementación de la interface FileTransferMiscelaneaDao.
 * 
 * @author Pablo Balderas
 */
public class FileTransferMiscelaneaDaoImpl extends BaseDaoHibernateImpl implements FileTransferMiscelaneaDao {

	/** Log de la clase */
	private static final Logger log = LoggerFactory.getLogger(FileTransferMiscelaneaDaoImpl.class);
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.filetransfer.FileTransferMiscelaneaDao#getByIdFolioTipo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<FileTransferMiscelanea> getByIdFolioTipo(final String idInst, final String folioInst, final String tipoProceso) {
		log.info("Entrando a FileTransferMiscelaneaDaoImpl.getByIdFolioTipo()");
		//Valida los parametros de la consulta
        Assert.notNull(idInst, "Falta el ID de la institucion");
        Assert.notNull(folioInst, "Falta el FOLIO de la institucion");
        Assert.notNull(tipoProceso, "Falta el TIPO DE PROCESO");
        //Ejecuta la consulta
        HibernateCallback hcb = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session.createCriteria(FileTransferMiscelanea.class).add(
                        Expression.eq("fileTransferPK.idInst", idInst)).add(
                        Expression.eq("fileTransferPK.folioInst", folioInst)).add(
                        Expression.eq("fileTransferPK.tipoReg", tipoProceso)).addOrder(
                        Order.asc("fileTransferPK.consec")).list();
            }
        };
        return (List<FileTransferMiscelanea>) getHibernateTemplate().execute(hcb);
	}
	
}
