/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 28, 2007
 *
 */
package com.indeval.portaldali.persistence.dao.estadocuenta.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.DetalleMovimientoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.estadocuenta.DetalleMovimientoEfectivoDAO;
import com.indeval.portaldali.persistence.model.RegContEfecNombrada;
import com.indeval.portaldali.persistence.model.RegContEfecNombradaHistorico;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
import com.indeval.portaldali.persistence.model.RegContValNombradaHistorico;

/**
 * Implementacion de la interface de acceso a la base de datos para las
 * consultas de los detalles de movimientos de efectivo
 * 
 * @author Pablo Julian Balderas Mendez
 * 
 */
public class DetalleMovimientoEfectivoDAOImpl extends HibernateDaoSupport implements DetalleMovimientoEfectivoDAO {

	private Logger logger = LoggerFactory.getLogger(DetalleMovimientoEfectivoDAOImpl.class);

	/**
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoEfectivoDAO#consultarDetalleMovimientoEfectivo(long,
	 *      java.lang.String,boolean)
	 */
	public DetalleMovimientoEfectivoDTO consultarDetalleMovimientoEfectivo(long idRegistroContable, final boolean isHistorico) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		String nombreTabla = (isHistorico) ? RegContEfecNombradaHistorico.class.getName() : RegContEfecNombrada.class.getName();

		query.append("SELECT registro, registro.operacion.instruccion.instruccionEfectivo.tipoRetiro.nombreCorto, ");
		query.append(" registro.operacion.instruccion.instruccionEfectivo.idTipoPago ");
		query.append(" FROM " + nombreTabla + " registro ");
		query.append(" join fetch registro.operacion ");
		query.append(" join fetch registro.operacion.tipoOperacion "); 
		query.append(" join fetch registro.operacion.instruccion ");
		query.append(" join fetch registro.operacion.instruccion.tipoInstruccion "); 
		query.append(" left outer join registro.operacion.instruccion.instruccionEfectivo ");
		query.append(" left join registro.operacion.instruccion.instruccionEfectivo.tipoRetiro ");
		
		if(isHistorico) {
			query.append(" join fetch registro.saldoNombradaHistorico ");
			query.append(" join fetch registro.saldoNombradaHistorico.cuentaNombrada ");
			query.append(" join fetch registro.saldoNombradaHistorico.cuentaNombrada.institucion ");
			query.append(" join fetch registro.saldoNombradaHistorico.cuentaNombrada.institucion.tipoInstitucion ");
			query.append(" join fetch registro.saldoNombradaHistorico.cuentaNombrada.tipoCuenta ");
			query.append(" join fetch registro.saldoNombradaHistorico.boveda ");
			query.append(" join fetch registro.saldoNombradaHistorico.divisa ");
		}
		else {			
			query.append(" join fetch registro.saldoNombrada ");
			query.append(" join fetch registro.saldoNombrada.cuentaNombrada ");
			query.append(" join fetch registro.saldoNombrada.cuentaNombrada.institucion ");
			query.append(" join fetch registro.saldoNombrada.cuentaNombrada.institucion.tipoInstitucion ");
			query.append(" join fetch registro.saldoNombrada.cuentaNombrada.tipoCuenta ");
			query.append(" join fetch registro.saldoNombrada.boveda ");
			query.append(" join fetch registro.saldoNombrada.divisa ");
		}
		
		query.append(" WHERE ");

		query.append(" registro.idRegistroContable = ? ");
		params.add(new BigInteger(String.valueOf(idRegistroContable)));
		tipos.add(new BigIntegerType());
		
		if( isHistorico ){
			query.append(" AND registro.fechaCreacion = registro.operacion.fechaCreacion ");
		}

		return (DetalleMovimientoEfectivoDTO) getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("rawtypes")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				DetalleMovimientoEfectivoDTO detalleMovimientoEfectivoDTO = null;

				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List listaResultados = hQuery.list();
				
				if (listaResultados != null && listaResultados.size() > 0) {
					Object[] array = (Object[])listaResultados.get(0);
					if(isHistorico) {
						logger.info("Termino el query: [" + array[0] + "-" + array[1] + "-" + array[2] + "]");
						detalleMovimientoEfectivoDTO = DTOAssembler.crearDetalleMovimientoEfectivoDTO(
								(RegContEfecNombradaHistorico) array[0],
								(String) array[1],
								(Integer) array[2]);
					} else {
						detalleMovimientoEfectivoDTO = DTOAssembler.crearDetalleMovimientoEfectivoDTO(
								(RegContEfecNombrada) array[0],
								(String) array[1],
								(Integer) array[2]);
					}
				}

				return detalleMovimientoEfectivoDTO;
			}
		});
	}

	/**
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoEfectivoDAO#buscarIdRegistroContableValorNombradoDeOperacionDVP(long,
	 *      long,boolean)
	 */
	@SuppressWarnings("unchecked")
	public long buscarIdRegistroContableValorNombradoDeOperacionDVP(long idRegistroContable, long idInstitucion, final boolean isHistorico) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		String selectFromTabla1 = (isHistorico) 
					? "	SELECT reg.idRegistroContableHistorico FROM " + RegContValNombradaHistorico.class.getName() 
							: "	SELECT reg.idRegistroContable FROM " + RegContValNombrada.class.getName();
		String nombreTabla2 = (isHistorico) ? RegContEfecNombradaHistorico.class.getName() : RegContEfecNombrada.class.getName();

		query.append("SELECT (");
		query.append(selectFromTabla1 + " reg ");
		query.append("	WHERE reg.idOperacion = regc.idOperacion ");
		if( isHistorico ) {
			query.append("	and reg.fechaCreacion = regc.fechaCreacion ");
			query.append("	and reg.posicion.cuentaNombrada.idInstitucion = regc.saldoNombradaHistorico.cuentaNombrada.idInstitucion ");
		}
		else {
			query.append("	and reg.posicion.cuentaNombrada.idInstitucion = regc.saldoNombrada.cuentaNombrada.idInstitucion ");
		}
		query.append("	and rownum<2) from " + nombreTabla2 + " regc ");
		query.append(" where regc.idRegistroContable = ?");

		params.add(new BigInteger(String.valueOf(idRegistroContable)));
		tipos.add(new BigIntegerType());
		
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				long idRegistro = 0;
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<BigInteger> listaResultados = hQuery.list();
				if (listaResultados != null && listaResultados.size() > 0) {
					idRegistro = listaResultados.get(0) != null ? listaResultados.get(0).longValue() : 0;
				}

				return idRegistro;
			}
		});
	}

}
