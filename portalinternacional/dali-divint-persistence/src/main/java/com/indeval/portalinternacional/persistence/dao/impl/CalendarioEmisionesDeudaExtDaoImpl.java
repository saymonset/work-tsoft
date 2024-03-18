/**
 * 
 */
package com.indeval.portalinternacional.persistence.dao.impl;



import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.indeval.portalinternacional.middleware.servicios.modelo.*;
import com.indeval.portalinternacional.middleware.servicios.vo.CalendarioDerechosVO;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.CalendarioEmisionesDeudaExtDTO;
import com.indeval.portalinternacional.persistence.dao.CalendarioEmisionesDeudaExtDao;

/**
 * @author lmunoz
 *
 */
public class CalendarioEmisionesDeudaExtDaoImpl extends BaseDaoHibernateImpl implements
		CalendarioEmisionesDeudaExtDao {

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(CalendarioEmisionesDeudaExtDaoImpl.class);    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private CalendarioEmisionesDeudaExtHelper helper;
    

	public List<EstadoDerechoInt> getCatalogoEstadosDerechoInt() {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + EstadoDerechoInt.class.getName() + " edi ");
		sb.append(" ORDER BY edi.id ");
		@SuppressWarnings("unchecked")
		List<EstadoDerechoInt> retorno = (List<EstadoDerechoInt>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;
	}

	
	
	public List<TipoPagoInt> getCatalogoTiposPagoInt(final Boolean isCAMV) {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + TipoPagoInt.class.getName() + " tpi ");
		if(isCAMV != null){
			if(isCAMV)
				sb.append(" WHERE tpi.caev = 1 ");
			else
				sb.append(" WHERE tpi.caev = 0 ");
		}
		sb.append(" ORDER BY tpi.id ");
		
		@SuppressWarnings("unchecked")
		List<TipoPagoInt> retorno = (List<TipoPagoInt>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;
	}

	public List<Custodio> getCatalogoCustodios() {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + Custodio.class.getName() + " cus ");
		sb.append(" ORDER BY cus.id ");
		@SuppressWarnings("unchecked")
		List<Custodio> retorno = (List<Custodio>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;
	}
	
	
	
	public PaginaVO consultaCalendarioDerechos(CalendarioEmisionesDeudaExtDTO params, PaginaVO pagina) {
		
		final CalendarioInternacionalQuery queryhelper = helper.createCalendarioIntSQL(params);		
		
		if(pagina == null){
			pagina = new PaginaVO();
		}
				
		@SuppressWarnings("unchecked")
		Long totales = (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryhelper.getCountQuery());
				query.setParameters(queryhelper.getParamsSQL().toArray(new Object[0]), queryhelper.getTipos().toArray(new Type[0]));
				
				return query.uniqueResult();
			}
		});
		if(totales != null){
			pagina.setTotalRegistros(totales.intValue());
		}else{
			pagina.setTotalRegistros(0);
		}
	
		final Integer offset = pagina.getOffset() != null ? pagina.getOffset():null;
		
		final Integer regxpag = pagina.getRegistrosXPag() != null ? pagina.getRegistrosXPag():null;
		@SuppressWarnings("unchecked")
		List<CalendarioDerechos> lista = (List<CalendarioDerechos>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				log.info(queryhelper.getQuery());
				Query query = session.createQuery(queryhelper.getQuery());
				query.setParameters(queryhelper.getParamsSQL().toArray(new Object[0]), queryhelper.getTipos().toArray(new Type[0]));
				if ( offset != null && regxpag !=null && regxpag != PaginaVO.TODOS) {
					query.setMaxResults(regxpag);
					query.setFetchSize(regxpag);						
					query.setFirstResult(offset);
				}
				return query.list();
			}
		});
		if(lista != null && lista.size() > 0){
			List<CalendarioDerechosVO> listaVO=calculaMontos(lista);
			pagina.setRegistros(listaVO);
		}
		
		return pagina;
	}


	public List<CalendarioDerechosVO> calculaMontos(List<CalendarioDerechos> lista){
		List<CalendarioDerechosVO> calendarioCalculadosVO= new ArrayList<>();
//		#Sacamos todos los id_calendar para preparar un in de sql
		StringBuilder stringBuilder = new StringBuilder();
		for (CalendarioDerechos calendario : lista) {
			stringBuilder.append(calendario.getIdCalendario()).append(",");
		}

// Remove the trailing comma if needed
		if (stringBuilder.length() > 0) {
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}

		String sqlIn = stringBuilder.toString();

		//Buscamos n bd
		List<BitacoraMensajeSwiftImporte> bmsiLst = getBitacoraMensajeSwiftImportebyId(sqlIn.toString());
		if (bmsiLst == null){
			bmsiLst = new ArrayList<>();
		}

		//agrupamos en un map los CalendarId
		Map<Long, List<BitacoraMensajeSwiftImporte>> mapa = new HashMap<Long, List<BitacoraMensajeSwiftImporte>>();
		for (BitacoraMensajeSwiftImporte bmsi : bmsiLst) {
			Long idCalendario = bmsi.getIdCalendario();
			if (!mapa.containsKey(idCalendario)) {
				mapa.put(idCalendario, new ArrayList<BitacoraMensajeSwiftImporte>());
			}
			mapa.get(idCalendario).add(bmsi);
		}


		for (CalendarioDerechos calendario : lista) {

			//Mapeamos todos los valores del entity calendario a VO CalendarioVO
			CalendarioDerechosVO calendarioDerechosVO = new CalendarioDerechosVO(calendario);
			Long idCalendario = calendarioDerechosVO.getIdCalendario();


			//	Solo para los custodios EUROCLE = 2 se busca que fecha valor y fecha de pago sean iguales para encntrar un MT567 que debe tener como obligatorio si las fechas son iguales
			if (calendarioDerechosVO.getCustodio() != null && calendarioDerechosVO.getCustodio().getId() == 2){
				if (calendarioDerechosVO.getFechaPago() != null &&
						calendarioDerechosVO.getFechaValor()!=null &&
						calendarioDerechosVO.getFechaValor().equals(calendarioDerechosVO.getFechaPago())){
					calendarioDerechosVO.setEuroclearAndFechaPagoValor(true);
				}
			}

			boolean isCitiBank = false;
			//CITIBANCK
			if (calendarioDerechosVO.getCustodio() != null && calendarioDerechosVO.getCustodio().getId() == 13){
				isCitiBank = true;
				calendarioDerechosVO.setCitiBank(isCitiBank);
			}

			BigDecimal monto566 = new BigDecimal(0.0);
			//retiro
			BigDecimal monto900 = new BigDecimal(0.0);
			// deposito
			BigDecimal monto910 = new BigDecimal(0.0);

			List<BitacoraMensajeSwiftImporte> lista0 = mapa.containsKey(idCalendario) ? mapa.get(idCalendario) : new ArrayList<BitacoraMensajeSwiftImporte>();
			for (BitacoraMensajeSwiftImporte mensajeSwift : lista0) {
				//Segun la regla, si es euroclear y tiene fecha de pago y fecha valor, debe tener un MT 567 para poder conctinuar
				if (calendarioDerechosVO.getEuroclearAndFechaPagoValor()){
					if ("567".equalsIgnoreCase(mensajeSwift.getTipoMensaje().toString())) {
						calendarioDerechosVO.setHasM567(true);
					}
				}

//				Solo para los custodios CITIBANK = 13 se busca los mt900 y mt 910
				if (isCitiBank){
					if ("900".equalsIgnoreCase(mensajeSwift.getTipoMensaje().toString())) {
						monto900.add(mensajeSwift.getImporte() != null ?  new BigDecimal(mensajeSwift.getImporte()) : new BigDecimal(0.0));
					}
					if ("910".equalsIgnoreCase(mensajeSwift.getTipoMensaje().toString())) {
						monto910.add(mensajeSwift.getImporte() != null ?  new BigDecimal(mensajeSwift.getImporte()) : new BigDecimal(0.0));
					}
				}

				if ("566".equalsIgnoreCase(mensajeSwift.getTipoMensaje().toString())) {
					monto566.add(mensajeSwift.getImporte() != null ?  new BigDecimal(mensajeSwift.getImporte()) : new BigDecimal(0.0));
				}


			}

			BigDecimal montoConfir = new BigDecimal(0.0);
			if (isCitiBank){
				montoConfir = monto566.subtract(monto910.add(monto900));
			}else{
				montoConfir = monto566;
			}

			BigDecimal importe = calendarioDerechosVO.getImporte();
			boolean isPuedePagar = importe!=null? montoConfir.subtract(importe).compareTo(BigDecimal.ZERO) >= 0 : true;

			calendarioDerechosVO.setPuedePagar(isPuedePagar);
			calendarioDerechosVO.setMontoConfirmado(montoConfir);
			calendarioCalculadosVO.add(calendarioDerechosVO);
		}

		return calendarioCalculadosVO;
	}

	public Integer actualizarEstadosDerechoInt(final Set<Long> ids, 	final Integer nuevoEstado) {			
		final StringBuilder sb = new StringBuilder();
		Integer retorno = 0;
		if(nuevoEstado == Constantes.CALENDARIO_DERECHOS_CORREGIDO){
			retorno = remueveReversal(ids,nuevoEstado);
		}
		sb.append(" update  " + CalendarioDerechos.class.getName() + " cal ");
		sb.append(" set cal.estado.id = :nuevoEstado  ");
		sb.append(" where  cal.idCalendario in (:ids ) ");
	

		retorno+= (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setInteger("nuevoEstado", nuevoEstado);
				query.setParameterList("ids", ids);				
				return query.executeUpdate();
			}
		});			
		
		return retorno;
	}	

	private Integer remueveReversal(final Set<Long> ids,final Integer nuevoEstado){	
		Integer retorno=0;
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + CalendarioDerechos.class.getName() + " cal ");
		sb.append(" where cal.idCalendario in (:ids ) ");		
		final CalendarioDerechos reversal = (CalendarioDerechos) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setParameterList("ids", ids);
				return query.uniqueResult();
			}
		});		
		//quitamos la prioridad
		final StringBuilder sb1 = new StringBuilder();		
		sb1.append(" update  " + CalendarioDerechos.class.getName() + " cal ");
		sb1.append(" set  cal.prioridad=0 ");
		sb1.append(" where  cal.custodio.id = :idcust  ");
		sb1.append(" and cal.referencia = :ref ");

		retorno = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb1.toString());				
				query.setLong("idcust", reversal.getCustodio().getId());	
				query.setString("ref", reversal.getReferencia());	
				return query.executeUpdate();
			}
		});
		
		return retorno;
	}

	//Modificado para Multidivisas
	public List<BitacoraMensajeSwift>  getBitacoraMensajeSwiftbyId(final Long id) {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM BitacoraMensajeSwift bms");
		sb.append(" where bms.idCalendario = :id");
		sb.append(" ORDER BY bms.fecha desc, bms.idCalendario desc");
		List<BitacoraMensajeSwift> retorno = null;
		try {
			retorno = (List<BitacoraMensajeSwift>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(sb.toString());
					query.setLong("id", id);
					return query.list();
				}
			});
		}catch (Exception e) {
			System.out.println("e.toString() = " + e.toString());
		}

		return retorno;
	}


	/*	Metodo creado para proyecto Multidivisas
	 *	Calcula el Importe de los mensajes
	 */

	public List<BitacoraMensajeSwiftImporte>  getBitacoraMensajeSwiftImportebyId(final String sqlIn) {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM BitacoraMensajeSwiftImporte bsi");
		sb.append(" WHERE bsi.idCalendario IN (:ids)");
		sb.append(" ORDER BY bsi.fecha desc, bsi.idCalendario desc ");
		List<BitacoraMensajeSwiftImporte> retorno = null;
		try {
			retorno = (List<BitacoraMensajeSwiftImporte>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(sb.toString());
					List<Long> idList = new ArrayList<Long>();
					String[] idArray = sqlIn.split(",");
					for (String id : idArray) {
						idList.add(Long.parseLong(id.trim()));
					}
					query.setParameterList("ids", idList);
					return query.list();
				}
			});
		}catch (Exception e) {
			System.out.println("e.toString() = " + e.toString());
		}
		return retorno;
	}
	/*	Metodo creado para proyecto Multidivisas
	 *	Calcula el Importe de los mensajes
	 */public List<BitacoraMensajeSwiftImporte>  getBitacoraMensajeSwiftImportebyId(final Long id) {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM BitacoraMensajeSwiftImporte bsi");
		sb.append(" where bsi.idCalendario =:id ");
		sb.append(" ORDER BY bsi.fecha desc, bsi.idCalendario desc ");
		List<BitacoraMensajeSwiftImporte> retorno = null;
		try {
			retorno = (List<BitacoraMensajeSwiftImporte>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(sb.toString());
					query.setLong("id", id);
					return query.list();
				}
			});
		}catch (Exception e) {
			System.out.println("e.toString() = " + e.toString());
		}

		return retorno;
	}




	/**
	 * @param helper the helper to set
	 */
	public void setHelper(CalendarioEmisionesDeudaExtHelper helper) {
		this.helper = helper;
	}


	/**
	 * obtiene el calendario de derechos por id
	 */
	public List<CalendarioDerechos> consultaCalendarioDerechosByIds(final Set<Long> ids) {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + CalendarioDerechos.class.getName() + " cal ");
		sb.append(" where cal.idCalendario in(:ids)  ");	
		@SuppressWarnings("unchecked")
		List<CalendarioDerechos> retorno = (List<CalendarioDerechos>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setParameterList("ids", ids);
				return query.list();
			}
		});
		return retorno;
	}

	/**
	 * 
	 */
	public List<Boveda> consultaBovedas(final Integer tipoBoveda) {		
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + Boveda.class.getName() + " bov ");
		if(tipoBoveda != null){
			sb.append(" where bov.tipoBoveda.idTipoBoveda = :id ");			
		}
		
		@SuppressWarnings("unchecked")
		List<Boveda> retorno = (List<Boveda>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				if(tipoBoveda != null){
					query.setInteger("id", tipoBoveda);
				}
				return query.list();
			}
		});
		return retorno;
	}



	public Boveda consultaBoveda(final Long idBoveda) {
		final StringBuilder sb = new StringBuilder();
		if(idBoveda == null){
			return null;		
		}
		sb.append(" FROM " + Boveda.class.getName() + " bov ");
		sb.append(" where bov.idBoveda = :id ");		
		
		@SuppressWarnings("unchecked")
		Boveda retorno = (Boveda) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setLong("id", idBoveda);
				return query.uniqueResult();
			}
		});
		return retorno;
	}

	/**
	 * 
	 */
	public List<Control> obtieneEstadosMensajeria(String idEstado)
			throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		if(idEstado == null){
			return null;		
		}
		final Long id=Long.valueOf(idEstado);
		sb.append(" FROM " + Control.class.getName() + " cont ");
		sb.append(" where cont.idEstado = :id ");		
		
		@SuppressWarnings("unchecked")
		List<Control> retorno = (List<Control>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setLong("id", id);
				return query.list();
			}
		});
		return retorno;
	}
	
	public List<BitacoraMensajeSwift>  getBitacoraMensajeSwiftbyIdHist(final Long id) {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + BitacoraMensajeSwift.class.getName() + " bit ");
		sb.append(" where bit.id = :id ");
		sb.append(" ORDER BY bit.fecha desc, bit.id desc ");
		@SuppressWarnings("unchecked")
		List<BitacoraMensajeSwift> retorno = (List<BitacoraMensajeSwift>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setLong("id", id);
				return query.list();
			}
		});
		return retorno;
	}
		
}
