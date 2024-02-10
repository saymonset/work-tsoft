/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.calendarioEmisionesDeudaExt;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.CalendarioDerechos;
import com.indeval.portalinternacional.middleware.servicios.modelo.NotificacionAccion;
import com.indeval.portalinternacional.middleware.servicios.vo.CalendarioEmisionesDeudaExtDTO;
import com.indeval.portalinternacional.persistence.dao.CalendarioEmisionesDeudaExtDao;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;

/**
 * 
 * @author Luis Roberto Munoz
 * 
 */
@SuppressWarnings( { "unchecked" })
public class ITestCalendario extends BaseDaoTestCase {
	private static final Integer AUTORIZADO=2;
	private static final Integer PREVIO=3;
	private static final Integer CONFIRMADO=4;
	private static final Integer PRELIQUIDADO=5;
	private static final Integer LIQUIDADO=6;
	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestCalendario.class);

	/**
	 * Dao que se va a probar
	 */
	private CalendarioEmisionesDeudaExtDao calendarioEmisionesDeudaExtDao;

	/**
	  * 
	  */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		calendarioEmisionesDeudaExtDao = (CalendarioEmisionesDeudaExtDao) getBean("calendarioEmisionesDeudaExtDao");
	}

	/**
	 * @throws ParseException 
	  * 

	 *
	  */
	
	/*public void testFindAllCalendario() throws ParseException {

		assertNotNull(calendarioEmisionesDeudaExtDao);

		// Objeto de prueba 
		CalendarioEmisionesDeudaExtDTO params = new CalendarioEmisionesDeudaExtDTO();
		// Prueba de consulta 
		params.init();
		PaginaVO cons=calendarioEmisionesDeudaExtDao.consultaCalendarioDerechos( params,null);
		assertNotNull(cons.getRegistros());
		assertNotSame(new Integer(0), cons.getRegistros().size());
		for(CalendarioDerechos reg : (List<CalendarioDerechos>)cons.getRegistros()){			
			log.debug("reg =\n"+ObjectToString(reg));
			System.out.println(ObjectToString(reg));
		}
		
	}
	
	public void testFindCalendarioByParams() throws ParseException {

		assertNotNull(calendarioEmisionesDeudaExtDao);

		// Objeto de prueba 
		CalendarioEmisionesDeudaExtDTO params = new CalendarioEmisionesDeudaExtDTO();
		params.init();
		// Campos de la consulta 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 params.setRefCustodio("123");
		 	 
		 params.setTipoValor("1");
		 params.setEmisora("JPM");
		 params.setSerie("1-09");
		 params.setCupon("0001");
		 params.setIsin("US172967FQ94");
		 params.setFechaCorte(sdf.parse("30/06/2011"));		
		 params.setTipoPagoCAMV(-1);
		// Prueba de consulta 
		
		 PaginaVO cons=calendarioEmisionesDeudaExtDao.consultaCalendarioDerechos( params,null);
			assertNotNull(cons.getRegistros());
			assertNotSame(new Integer(0), cons.getRegistros().size());
			for(CalendarioDerechos reg : (List<CalendarioDerechos>)cons.getRegistros()){			
				log.debug("reg =\n"+ObjectToString(reg));
				System.out.println(ObjectToString(reg));
			}
		
	}
	
	public void testUpdateCalendario() throws ParseException {

		assertNotNull(calendarioEmisionesDeudaExtDao);

		// Objeto de prueba 
		CalendarioEmisionesDeudaExtDTO params = new CalendarioEmisionesDeudaExtDTO();
		params.init();
		// Campos de la consulta 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 params.setRefCustodio("123");
		
		 //params.setEstado(1);
		 params.setTipoValor("D2");
		 params.setEmisora("CQ94");
		 params.setSerie("10414");
		 params.setCupon("0001");
		 params.setIsin("US172967FQ94");
		 params.setFechaCorte(sdf.parse("30/06/2011"));
		 params.setFechaPago(null);
		
		 params.setDivisa("USD");
		 
		 params.setCustodio(1);
		 params.setTipoPagoCAEV(17);
		 params.setTipoPagoCAMV(-1);
		// Prueba de consulta 
		params.init();
		PaginaVO cons=calendarioEmisionesDeudaExtDao.consultaCalendarioDerechos( params,null);
		assertNotNull(cons.getRegistros());
		CalendarioDerechos reg = (CalendarioDerechos)cons.getRegistros().get(0);
		reg.getEstado().setId(2);
		calendarioEmisionesDeudaExtDao.update(reg);	
		cons=calendarioEmisionesDeudaExtDao.consultaCalendarioDerechos( params,null);
		assertNotNull(cons.getRegistros());
		assertEquals(new Integer(2), (Integer)((CalendarioDerechos)cons.getRegistros().get(0)).getEstado().getId());
		reg = (CalendarioDerechos)cons.getRegistros().get(0);
		reg.getEstado().setId(1);
		calendarioEmisionesDeudaExtDao.update(reg);		
	}
	
	public void testEstados() throws ParseException {
		assertNotNull(calendarioEmisionesDeudaExtDao);	
		// Prueba de consulta 		
		List<EstadoDerechoInt> cons=calendarioEmisionesDeudaExtDao.getCatalogoEstadosDerechoInt();
		assertNotNull(cons);
		System.out.println("==================ESTADOS===================================\n");
		for(EstadoDerechoInt reg : cons){			
			log.debug("reg =\n"+ObjectToString(reg));
			System.out.println(ObjectToString(reg));
		}		
	}
	
	public void testTipoPagoIntCAMV() throws ParseException {
		assertNotNull(calendarioEmisionesDeudaExtDao);	
		// Prueba de consulta 		
		List<TipoPagoInt> cons=calendarioEmisionesDeudaExtDao.getCatalogoTiposPagoInt(false);
		assertNotNull(cons);
		for(TipoPagoInt reg : cons){			
			log.debug("reg =\n"+ObjectToString(reg));
			System.out.println("==================CAMV===================================\n"+ObjectToString(reg));
		}		
	}
	
	public void testTipoPagoIntCAEV() throws ParseException {
		assertNotNull(calendarioEmisionesDeudaExtDao);	
		// Prueba de consulta 		
		List<TipoPagoInt> cons=calendarioEmisionesDeudaExtDao.getCatalogoTiposPagoInt(true);
		assertNotNull(cons);
		for(TipoPagoInt reg : cons){			
			log.debug("reg =\n"+ObjectToString(reg));
			System.out.println("==================CAEV===================================\n"+ObjectToString(reg));
		}		
	}
	
	public void testCustodios() throws ParseException {
		assertNotNull(calendarioEmisionesDeudaExtDao);	
		// Prueba de consulta 		
		List<Custodio> cons=calendarioEmisionesDeudaExtDao.getCatalogoCustodios();
		assertNotNull(cons);
		for(Custodio reg : cons){			
			log.debug("reg =\n"+ObjectToString(reg));
			System.out.println("==================CUSTODIOS===================================\n"+ObjectToString(reg));
		}		
	}
	
	public void testActualizaEstadoCalendario(){
		//OJO: modifica el estado de los primeros 3 registros
		assertNotNull(calendarioEmisionesDeudaExtDao);
		
		Set<Long> lista = new HashSet<Long>();
		lista.add(1l);
		lista.add(2l);
		lista.add(3l);
		Integer cons=0;
		//cons=calendarioEmisionesDeudaExtDao.actualizarEstadosDerechoInt(lista, AUTORIZADO);
		cons=calendarioEmisionesDeudaExtDao.actualizarEstadosDerechoInt(lista, PREVIO);
		assertNotNull(cons);
		assertEquals((Integer)3,cons);				
	}
	
	public void testBitacoraCalendario(){
		//OJO: modifica el estado de los primeros 3 registros
		assertNotNull(calendarioEmisionesDeudaExtDao);
		
		List<BitacoraMensajeSwift>cons=calendarioEmisionesDeudaExtDao.getBitacoraMensajeSwiftbyId(1l);
		assertNotNull(cons);
		
		for(BitacoraMensajeSwift reg : cons){			
			log.debug("reg =\n"+ObjectToString(reg));			
			System.out.println("==================BitacoraMensajeSwift===================================\n"+ObjectToString(reg));
		}
	}
	
	
	
*/	

	
	public void testFindCalendarioIndevalByParams() throws ParseException {

		assertNotNull(calendarioEmisionesDeudaExtDao);

		// Objeto de prueba 
		CalendarioEmisionesDeudaExtDTO params = new CalendarioEmisionesDeudaExtDTO();
		params.init();
		// Campos de la consulta 
		params.setCalendarioIndeval(false);
		params.setHojaDeTrabajo(false);
		//params.setCustodio(1); 
		//params.setIdCalendario(2048l);
		
		
		// Prueba de consulta 
		PaginaVO pvo=new PaginaVO();
		pvo.setRegistrosXPag(2);
		pvo.setOffset(0);
		 PaginaVO cons=calendarioEmisionesDeudaExtDao.consultaCalendarioDerechos( params,pvo);
			
			for(CalendarioDerechos reg : (List<CalendarioDerechos>)cons.getRegistros()){			
				log.debug("reg =\n"+ObjectToString(reg));
				System.out.println(ObjectToString(reg));
			}
			System.out.println("TOTAL REGISTROS "+cons.getTotalRegistros());
			
			assertTrue(true);
		
	}
	
	
	public void tesstNotificacionAccionesPortal(){
		XStream xstream = new XStream();
		NotificacionAccion msg = new NotificacionAccion();
		
		msg.setAccion(NotificacionAccion.TipoAccion.CAMBIO_ESTADO);		
		msg.setEstado(NotificacionAccion.TipoEstado.AUTORIZADO);
		//msg.getIds().add("uno");
		//msg.getIds().add("dos");
		//msg.getIds().add("tres");
		
		Annotations.configureAliases(xstream, NotificacionAccion.class);
		String xmlst=xstream.toXML(msg);
		System.out.println(xmlst);		
		
		
		NotificacionAccion msgrcv = (NotificacionAccion)xstream.fromXML(xmlst);
		System.out.println(ObjectToString(msgrcv));
		assertTrue(true);
	
	}
	
	 
	private String ObjectToString(Object o){
		StringBuffer buff = new StringBuffer();
	
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field f:fields)	{
			try{
				
				Method m = o.getClass().getMethod("get"+StringUtils.capitalize(f.getName()), null);
				buff.append(f.getName());
				buff.append("\t=>\t");
				Object o2 =m.invoke(o, null);
				if(o2!=null && o2.getClass().getPackage().getName().contains("com.indeval")){
					//buff.append("\n== "+o2.getClass().getName()+"==\n");
					//buff.append(ObjectToString(o2));
					//buff.append("\n== fin "+o2.getClass().getName()+"==\n");
				}else{
					if(o2 != null)
					buff.append(o2);
				}
				buff.append("\n");		
			
			}
			catch (Exception ex){
				//ex.printStackTrace(System.out);
			}
		}
		return buff.toString();
	}


}
