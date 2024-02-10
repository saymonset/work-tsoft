package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.List;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.modelo.BancoCorresponsal;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.persistence.dao.DivisasInterDao;

public class DivisasInterDaoImpl extends BaseDaoHibernateImpl  implements DivisasInterDao {
	
//	@SuppressWarnings("unchecked")
//	public Divisa[] findDivisasPorInstitucion(Long idInstitucion){
//		
//		System.out.println("Dentro de com.indeval.portalinternacional.persistence.dao.impl.findDivisasPorInstitucion");
//		
//		final StringBuilder sb = new StringBuilder();
//		sb.append(" FROM " + Divisa.class.getName() + " d ");
//		sb.append(" WHERE d.idDivisa IN (SELECT bc.idDivisa FROM  "+BancoCorresponsal.class.getName()+" bc WHERE bc.idInstitucion = "+idInstitucion);
//		sb.append(")");
//		
//		System.out.println("Query generado, listo para ejecutar");
//		
//		 return (Divisa[])  getHibernateTemplate().execute(new HibernateCallback() {
//	            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//	            	System.out.println("Iniciando ejecucion");
//	                Query query = session.createQuery(sb.toString());
//	                System.out.println("Query creado");
//	                 return query.list().toArray(new Divisa[] {});
//	            }
//	        });
//	}
	
	@SuppressWarnings("unchecked")
	public Divisa[] findDivisasPorInstitucion(Long idInstitucion) {
			System.out.println("Recibiendo institucion "+idInstitucion);
		return (Divisa[]) ((List<Divisa>) getHibernateTemplate()
				.find(" FROM " + Divisa.class.getName() + " d  WHERE d.idDivisa IN (SELECT bc.idDivisa FROM  "+BancoCorresponsal.class.getName()+" bc WHERE bc.idInstitucion = "+idInstitucion+")")).toArray(new Divisa[] {});
	}
	
	@SuppressWarnings("unchecked")
	public Divisa[] buscaDivisas() {

		return (Divisa[]) ((List<Divisa>) getHibernateTemplate()
				.find("FROM " + Divisa.class.getName() + " d order by d.descripcion")).toArray(new Divisa[] {});
	}
	
}
