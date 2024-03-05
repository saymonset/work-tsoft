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
import com.indeval.portaldali.persistence.dao.filetransfer.FileTransferOperacionesDao;
import com.indeval.portaldali.persistence.model.FileTransferOperaciones;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class FileTransferOperacionesDaoImpl extends BaseDaoHibernateImpl implements
        FileTransferOperacionesDao {

    private static final Logger logger = LoggerFactory.getLogger(FileTransferOperacionesDaoImpl.class);

	@SuppressWarnings("unchecked")
	public List getByIdFolioTipo(final String idInst, final String folioInst,
			final String tipoProceso) {
		logger.info("Entrando a FileTransferOperacionesDaoImpl.getByIdFolioTipo()");
        
        Assert.notNull(idInst, "Falta el ID de la institucion");
        Assert.notNull(folioInst, "Falta el FOLIO de la institucion");
        Assert.notNull(tipoProceso, "Falta el TIPO DE PROCESO");
        
        HibernateCallback hcb = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session.createCriteria(FileTransferOperaciones.class).add(
                        Expression.eq("fileTransferPK.idInst", idInst)).add(
                        Expression.eq("fileTransferPK.folioInst", folioInst)).add(
                        Expression.eq("fileTransferPK.tipoReg", tipoProceso)).addOrder(
                        Order.asc("fileTransferPK.consec")).list();
            }
        };
        return (List) getHibernateTemplate().execute(hcb);
	}


}
