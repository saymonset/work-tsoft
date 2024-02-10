/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.eventosCorporativos;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Estado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoConsultaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoEvCo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoEvento;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoMercado;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ListaDistribucionDao;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.PersonaDao;

/**
 * 
 * @author Luis Roberto Munoz
 * 
 */
@SuppressWarnings( { "unchecked" })
public class ITestEventoCorporativo extends BaseDaoTestCase {
	
	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestEventoCorporativo.class);

	/**
	 * Dao que se va a probar
	 */
	private ConsultaEventosCorporativosDao dao;

	/*
	 * 
	 */
	private ListaDistribucionDao listaDistribucionDao;

	/**
	 * 
	 */
	

	/**
	 * 
	 */
	private PersonaDao personaDao; 
	
	/**
	  * 
	  */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		dao = (ConsultaEventosCorporativosDao) getBean("consultaEventosCorporativosDao");
		listaDistribucionDao = (ListaDistribucionDao) getBean("listaDistribucionDao");
		
		personaDao = (PersonaDao) getBean("personaDao");
	}

	/**
	 * @throws ParseException 
	  * 

	 *
	  */
	
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
					buff.append("\n== "+o2.getClass().getName()+"==\n");
					buff.append(ObjectToString(o2));
					buff.append("\n== fin "+o2.getClass().getName()+"==\n");
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

	
	public void tesstFindEventoCorpByParams() throws ParseException {

		//assertNotNull(conciliacionIntDao);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// Objeto de prueba 
		EventoCorporativoConsultaDTO params = new EventoCorporativoConsultaDTO();
		
		// Campos de la consulta 
		params.setIdEventoCorporativo("1");	
		params.setCustodio("3");
		params.setTipoDerechoML("1");
		params.setTipoDerechoMO("1");
		params.setTipoValor("1A");
		params.setEmisora("EMI");
		params.setSerie("*");
		
		params.setEstado("1");
		
		params.setTipoEvento("1");
		params.setTipoMercado("1");
		
		params.setFechaIndevalInicio(sdf.parse("10/09/2014"));
		params.setFechaIndevalFin(sdf.parse("10/09/2014"));
		params.setFechaClienteInicio(sdf.parse("09/09/2014"));
		params.setFechaClienteFin(sdf.parse("09/09/2014"));
		params.setFechaPagoInicio(sdf.parse("12/09/2014"));
		params.setFechaPagoFin(sdf.parse("12/09/2014"));
		
		params.setFechaCreacionInicio(sdf.parse("08/09/2014"));
		params.setFechaCreacionFin(sdf.parse("08/09/2014"));
		params.setFechaRegistroInicio(sdf.parse("07/09/2014"));
		params.setFechaRegistroFin(sdf.parse("07/09/2014"));
		
		
		
		// Prueba de consulta 
		PaginaVO pvo=new PaginaVO();
		pvo.setRegistrosXPag(PaginaVO.TODOS);
		pvo.setOffset(0);
		 PaginaVO cons=dao.getEventosCorporativos(params, pvo, false);
		 log.info("CONSULTA EVENTOS : "+cons.getRegistros());
			for(EventoCorporativo reg : (List<EventoCorporativo>)cons.getRegistros()){			
				log.info("reg =\n"+ObjectToString(reg));
				//System.out.println(ObjectToString(reg));
				log.info(reg.getIdEventoCorporativo().toString());
			}
			log.info("TOTAL REGISTROS "+cons.getTotalRegistros());
			
			assertTrue(true);
			
			List<Estado> edos = dao.getEstadosEventoCorporativo();
			for(Estado obj:edos){
				log.info("reg =\n"+ObjectToString(obj));
			}
			assertNotNull(edos);
			
			List<TipoDerechoEvCo> objs = dao.getTiposDerechoML();
			for(TipoDerechoEvCo obj:objs){
				log.info("reg =\n"+ObjectToString(obj));
			}
			assertNotNull(objs);
			
			List<TipoDerechoEvCo> objs2 = dao.getTiposDerechoMO();
			for(TipoDerechoEvCo obj:objs2){
				log.info("reg =\n"+ObjectToString(obj));
			}
			assertNotNull(objs2);
			
			List<TipoEvento> objs3 = dao.getTiposEvento();
			for(TipoEvento obj:objs3){
				log.info("reg =\n"+ObjectToString(obj));
			}
			assertNotNull(objs3);
			
			List<TipoMercado> objs4 = dao.getTiposMercado();
			for(TipoMercado obj:objs4){
				log.info("reg =\n"+ObjectToString(obj));
			}
			assertNotNull(objs4);
	}
	
	/*public void tesstOperacionesConciliacionXML(){
		XStream xstream = new XStream();
		Annotations.configureAliases(xstream, OperacionesConciliacionInt.class);
		
		OperacionesConciliacionInt op = new OperacionesConciliacionInt();
		
		op.setOperacion(TipoOperacionConciliacion.CONCILIA);
		op.setIdConciliacion(1l);
		
		System.out.println("xml = "+ xstream.toXML(op));
	}*/

	
	public void ttestListaDistribucionDao(){

		log.debug("=========================== entrando a TEST =====================================");
		
		log.debug("========================= Lista Distribucion DAO ================================");
		
		ListaDistribucion lista = (ListaDistribucion) listaDistribucionDao.obtenerListaDistribucionPorId(26l);
		assertNotNull(lista);
		log.debug("Lista: " + lista.getIdLista() + "," + lista.getNombre() + "," + lista.getInactivo());
		/*for(Grupo obj : lista.getGrupos()){
			log.debug("contiene: " + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo());	
		}*/
		/*
		List<ListaDistribucion> listaActivas = listaDistribucionDao.obtenerListasDistribucionActivas();
		assertTrue(listaActivas.size()>0);
		log.debug(listaActivas.size());
		for(ListaDistribucion obj : listaActivas){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}
		
		log.debug("============================Grupo DAO======================================");
		Long idGrupo = 30l;
		Grupo grupo = grupoDao.obtenerGrupoPorId(idGrupo);
		assertNotNull(grupo);
		log.debug("Grupo: " + grupo.getIdGrupo() + "," + grupo.getNombre() + "," + grupo.getInactivo());
		
		Long idListaDistribucion = 26l;
		List<Grupo> listaGruposIdLista = grupoDao.obtenerGruposPorListaDistribucion(idListaDistribucion);
		assertTrue(listaGruposIdLista.size()>0);
		log.debug(listaGruposIdLista.size());
		for(Grupo obj : listaGruposIdLista){
			log.debug("Grupo: " + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo());	
		}

		List<Grupo> listaGruposNotIdLista = grupoDao.obtenerGruposNoPertenecientesListaDistribucion(idListaDistribucion);
		assertTrue(listaGruposNotIdLista.size()>0);
		log.debug(listaGruposNotIdLista.size());
		for(Grupo obj : listaGruposNotIdLista){
			log.debug("Grupo: " + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo());	
		}

		
		log.debug("============================Persona DAO======================================");
		Long idPersona = 1l;
		Persona persona = personaDao.obtenerPersonaPorId(idPersona);
		assertNotNull(persona);
		log.debug("Persona: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());

		idGrupo = 1l;
		List<Persona> listaPersonasGrupo = personaDao.obtenerPersonasPorGrupo(idGrupo);
		assertTrue(listaPersonasGrupo.size()>0);
		log.debug(listaPersonasGrupo.size());
		for(Persona obj : listaPersonasGrupo){
			log.debug("Persona: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}
		 */
		
	}

	public void testPersonaDao(){

		log.debug("=========================== entrando a TEST =====================================");
		
		log.debug("============================Persona DAO======================================");

		
		
//		Long idPersona = 1l;
//		Persona persona = personaDao.obtenerPersonaPorId(idPersona);
//		assertNotNull(persona);
//		log.debug("Persona: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());

//		idGrupo = 1l;
//		List<Persona> listaPersonasGrupo = personaDao.obtenerPersonasPorGrupo(idGrupo);
//		assertTrue(listaPersonasGrupo.size()>0);
//		log.debug(listaPersonasGrupo.size());
//		for(Persona obj : listaPersonasGrupo){
//			log.debug("Persona: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
//		}

		
	}
	
	
}
