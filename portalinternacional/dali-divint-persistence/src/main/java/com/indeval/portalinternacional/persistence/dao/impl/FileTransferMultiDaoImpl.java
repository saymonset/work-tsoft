/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransfer;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDetalleDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDivisas;
import com.indeval.portalinternacional.persistence.dao.FileTransferMultiDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class FileTransferMultiDaoImpl extends BaseDaoHibernateImpl implements
		FileTransferMultiDao {
	
    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FileTransferMultiDaoImpl.class);

	private static final String ID_FILE_TRANSFER = "\\[ID_FILE_TRANSFER\\]";
	/**
	 * Query setea T_FILETRANSFER_DETALLE_DIVISAS_INT.ID_STATUS=12 (error) cuando TSALDO_NOMBRADA.SALDO_DISPONIBLE
	 * es menor a la suma de los montos netos negativos (retiros), la verificacion se hace en saldo nombrada usando
	 * como filtro:
	 * TSALDO_NOMBRADA.ID_CUENTA, TSALDO_NOMBRADA.ID_DIVISA, TSALDO_NOMBRADA.ID_BOVEDA
	 * El agrupamiento T_FILETRANSFER_DETALLE_DIVISAS_INT es por cuentanombrada, boveda, divisa donde los montosnetos sean menores a cero
	 */
	static final String QUERY_VALIDA_SALDOS_ACTUALIZA_ESTADO=" UPDATE\n" +
			" T_FILETRANSFER_DETALLE_DIVISAS_INT tft\n" +
			" SET\n" +
			" tft.id_estatus = 12,\n" +
			" tft.ERROR = 'Saldo disponible insuficiente en: Cuenta,Boveda,Divisa'\n" +
			" WHERE\n" +
			" tft.ID_FILETRANSFER_DETALLE_DIVISAS_INT IN(\n" +
			" SELECT \n" +
			" ID_FILETRANSFER_DETALLE_DIVISAS_INT\n" +
			" FROM\n" +
			" T_FILETRANSFER_DETALLE_DIVISAS_INT tfd\n" +
			" INNER JOIN (\n" +
			" SELECT\n" +
			" 	TFDDI.ID_INSTITUCION ,\n" +
			"	ccn.ID_CUENTA_NOMBRADA ,\n" +
			" 	TFDDI.CLAVE_TIPO_INSTITUCION || TFDDI.FOLIO_INSTITUCION clave ,\n" +
			"	TFDDI.ID_BOVEDA,\n" +
			"	TFDDI.ID_DIVISA,\n" +
			"	sum(\n" +
			" 		CASE \n" +
			"			WHEN TFDDI.MONTO_NETO <0 THEN TFDDI.MONTO_NETO *-1 ELSE 0\t\n" +
			"		END\n" +
			" 	   ) sumaretiros,TSN.SALDO_DISPONIBLE\n" +
			"	 FROM T_FILETRANSFER_DETALLE_DIVISAS_INT tfddi\n" +
			"		INNER JOIN C_CUENTA_NOMBRADA ccn ON CCN.ID_INSTITUCION = TFDDI.ID_INSTITUCION AND CCN.ID_TIPO_CUENTA = 16\n" +
			"		INNER JOIN T_SALDO_NOMBRADA tsn ON TSN.ID_CUENTA = CCN.ID_CUENTA_NOMBRADA\n" +
			"       AND tsn.ID_BOVEDA = TFDDI.ID_BOVEDA AND tsn.ID_DIVISA = tfddi.ID_DIVISA\n" +
			"	  WHERE tfddi.ID_FILETRANSFER_DIVISAS_INT = [ID_FILE_TRANSFER] AND tfddi.ID_ESTATUS=2 AND TFDDI.MONTO_NETO<0\n" +
			"	GROUP BY TFDDI.CLAVE_TIPO_INSTITUCION || TFDDI.FOLIO_INSTITUCION , TFDDI.ID_BOVEDA, TFDDI.ID_DIVISA,\n" +
			" 			 TSN.SALDO_DISPONIBLE, ccn.ID_CUENTA_NOMBRADA,TFDDI.ID_INSTITUCION\n" +
			" 	HAVING\n" +
			" 			SUM(\n" +
			" 				CASE \n" +
			"					WHEN TFDDI.MONTO_NETO <0 THEN TFDDI.MONTO_NETO *-1 ELSE 0 \n" +
			"				END\n" +
			"				)<> 0\n" +
			"	) X ON TFD.ID_INSTITUCION = x.id_institucion AND TFD.ID_BOVEDA = x.id_boveda AND TFD.ID_DIVISA = x.id_divisa\n" +
			" 	WHERE TFD.ID_FILETRANSFER_DIVISAS_INT = [ID_FILE_TRANSFER] AND x.saldo_disponible<sumaretiros AND tfd.MONTO_NETO <0\n" +
			")";

	/**
	 *
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

	@Override
	public List<FileTransferDivisas> findFileTransferDivIntByUsuario(final String cveUsuario) {

		log.info(String.format("Entrando a FileTransferDaoImpl.findFileTransferDivIntByUsuario(%s)",cveUsuario));
		List listaFileTransfer = new ArrayList();
		 listaFileTransfer = (List) this.getHibernateTemplate()
				.execute(new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session.createCriteria(FileTransferDivisas.class);

						criteria.add(Restrictions.eq("usuarioRegistro", cveUsuario));;
						criteria.addOrder(Order.asc("idFileTransferDivisasInt"));
						return criteria.list();
					}
				});

		return listaFileTransfer;
	}

	@Override
	public List<FileTransferDetalleDivisas> findFileTransferDetalleByIdFileTransfer(final Long idFileTransfer) {
		List<FileTransferDetalleDivisas> lista = new ArrayList();

		log.info(String.format("findFileTransferDetalleByIdFileTransfer(%s)",String.valueOf(idFileTransfer)));
		lista = (List<FileTransferDetalleDivisas>) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(FileTransferDetalleDivisas.class);

				criteria.add(Restrictions.eq("idFileTransferDivisas",idFileTransfer));
				return criteria.list();
			}
		});
		return lista;
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FileTransferMultiDao#findFileTransferByIdFileTransfer(Long) 
	 * @param idFileTransfer
	 * @return
	 */
	public FileTransferDivisas findFileTransferByIdFileTransfer(final Long idFileTransfer){
		FileTransferDivisas file = null;
		log.debug(String.format("findFileTransferByIdFileTransfer(%s)",idFileTransfer.toString()));
		file = (FileTransferDivisas) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				Criteria criteria = session.createCriteria(FileTransferDivisas.class);
				criteria.add(Restrictions.eq("idFileTransferDivisasInt",idFileTransfer));
				return criteria.uniqueResult();
			}
		});

		return file;
	}

	@Override
	public boolean validaSaldoDisponiblePorParticipanteEnFT(Long idFileTransfer) {
		String Query = getQueryValidaSaldosActualizaEstado(idFileTransfer);
		log.debug("Ejecutar :: \n" + Query);
		Session session = getSession();
		SQLQuery sqlQuery = session.createSQLQuery(Query);
		log.trace(sqlQuery.getQueryString());
		int update = sqlQuery.executeUpdate();
		return update > 0;
	}

	public static final String getQueryValidaSaldosActualizaEstado(Long idFileTransfer) {
		return QUERY_VALIDA_SALDOS_ACTUALIZA_ESTADO.replaceAll(ID_FILE_TRANSFER, idFileTransfer.toString());
	}

}
