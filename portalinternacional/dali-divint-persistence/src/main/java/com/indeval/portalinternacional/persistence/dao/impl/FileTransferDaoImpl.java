/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransfer;
import com.indeval.portalinternacional.persistence.dao.FileTransferDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.List;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class FileTransferDaoImpl extends BaseDaoHibernateImpl implements
        FileTransferDao {
	
    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FileTransferDaoImpl.class);

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FileTransferDao#getFileTransferDivIntByIdFolioTipoReg(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<FileTransfer> findFileTransferDivIntByIdFolioTipoReg(final String id, final String folio, final String tipoReg) {
		
		log.info("Entrando a FileTransferDaoImpl.findFileTransferDivIntByIdFolioTipoReg()");
		
		/* Se realiza la consulta */
		List listaFileTransfer = (List) this.getHibernateTemplate()
				.execute(new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session.createCriteria(FileTransfer.class);
						criteria.add(Restrictions.eq("idInst", id));
						criteria.add(Restrictions.eq("folioInst", folio));
						criteria.add(Restrictions.eq("tipoReg", tipoReg));
						criteria.addOrder(Order.asc("idFiletransferDivint"));
						return criteria.list();

					}
				});
		
		return listaFileTransfer;
	}

}
