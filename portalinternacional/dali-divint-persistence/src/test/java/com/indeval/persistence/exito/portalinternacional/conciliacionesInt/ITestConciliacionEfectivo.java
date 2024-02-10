package com.indeval.persistence.exito.portalinternacional.conciliacionesInt;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.ComentarioEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivoDao;

/**
 * 
 * @author Fernando Pineda
 * 
 */
public class ITestConciliacionEfectivo extends BaseDaoTestCase {
	
	/**
	 * Dao que se va a probar
	 */
	private ConciliacionEfectivoDao conciliacionEfectivoDao;
	private static final Logger log = LoggerFactory.getLogger(ITestConciliacionEfectivo.class);

	protected void onSetUp() throws Exception {
		super.onSetUp();
		conciliacionEfectivoDao = (ConciliacionEfectivoDao) getBean("conciliacionEfectivoDao");
	}
	
	public void tesstConsultaConciliacionEfectivoInt() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		ConciliacionEfectivoIntDTO params = new ConciliacionEfectivoIntDTO();
		Set<String> bicCodes = new HashSet<String>();
		//bicCodes.add("DEUTUS33CUS");
		//bicCodes.add("CEDELULLXXX");
		Set<String> divisas = new HashSet<String>();
		//divisas.add("USD");
		Set<String> cuentas = new HashSet<String>();
		//cuentas.add("153-02198-1-3");
		//cuentas.add("23310");
		
		params.setBicCodes(bicCodes);
		params.setDivisas(divisas);
		params.setCuentas(cuentas);
		params.setCuentaCustodia(true);
		params.setCuentaComercial(false);
		//params.setFolioConciliacion(2270L);
		//params.setStatementNumber(13074);
		//params.setFechaBalanceInicio(sdf.parse("15/03/2013"));
		//params.setFechaBalanceFin(sdf.parse("15/03/2013"));
		//params.setFechaEmisionInicio(sdf.parse("19/03/2013 11:00"));
		//params.setFechaEmisionFin(sdf.parse("19/03/2013 12:00"));
		
		PaginaVO pagina = new PaginaVO();
		pagina = conciliacionEfectivoDao.consultaConciliacionEfectivoInt(params, pagina);
		System.out.println("pagina:" + pagina.getTotalRegistros());
//		assertNotNull(pagina);
	}
	
	public void testConsultaDetalleConciliacionEfectivoInt() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfh = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		DetalleConciliacionEfectivoIntDTO params = new DetalleConciliacionEfectivoIntDTO();
		Set<String> bicCodes = new HashSet<String>();
		bicCodes.add("DEUTUS33CUS");
		//bicCodes.add("CEDELULLXXX");
		Set<String> divisas = new HashSet<String>();
		divisas.add("USD");
		Set<String> cuentas = new HashSet<String>();
		cuentas.add("153-02198-1-3");
		//cuentas.add("23310");
		
//		params.setBicCodes(bicCodes);
//		params.setDivisas(divisas);
//		params.setCuentas(cuentas);
		params.setCuentaCustodia(false);
		params.setCuentaComercial(false);
		params.setComentarios(true);
//		params.setFolioConciliacion(2270L);
//		params.setReferenciaMensaje("124130315A000036");
//		params.setFechaBalanceInicio(sdf.parse("15/03/2013"));
//		params.setFechaBalanceFin(sdf.parse("15/03/2013"));
//		params.setFechaEmisionInicio(sdfh.parse("19/03/2013 8:00"));
//		params.setFechaEmisionFin(sdfh.parse("19/03/2013 9:00"));
//		params.setFechaCreditoDebitoInicio(sdf.parse("15/03/2013"));
//		params.setFechaCreditoDebitoFin(sdf.parse("15/03/2013"));
		PaginaVO pagina = new PaginaVO();
		pagina = conciliacionEfectivoDao.consultaDetalleConciliacionEfectivoInt(params, pagina);
		System.out.println("pagina:" + pagina.getTotalRegistros());
//		assertNotNull(pagina);
	}
	
	public void tesstConsultaBicCodes(){
		List<String> listaBicCodes = conciliacionEfectivoDao.consultaBicCodes();
		System.out.println(listaBicCodes.toString());
		//assertNotNull(listaBicCodes);
	}
	
	public void tesstConsultaDivisas(){
		Set<String> bicCodes = new HashSet<String>();
		//bicCodes.add("DEUTUS33CUS");
		bicCodes.add("SABNESMMSSS");
		//bicCodes.add("TODOS");
		List<Divisa> listaDivisas = conciliacionEfectivoDao.consultaDivisas(bicCodes);
		
		for(Divisa divisa : listaDivisas){
			System.out.println("divisa:" + divisa.getClaveAlfabetica());
		}
		//assertNotNull(listaDivisas);
	}
	
	public void tesstConsultaCuentas(){
		Set<String> bicCodes = new HashSet<String>();
		//bicCodes.add("DEUTUS33CUS");
		//bicCodes.add("CEDELULLXXX");
		bicCodes.add("TODOS");
		
		Set<String> divisas = new HashSet<String>();
		//divisas.add("USD");
		//divisas.add("EUR");
		divisas.add("TODAS");
		
		List<String> listaCuentas = conciliacionEfectivoDao.consultaCuentas(bicCodes, divisas);
		System.out.println("listaCuentas:" + listaCuentas);
		//assertNotNull(listaCuentas);
	}
	
	public void tesstConsultaBitacora(){
		List<BitacoraConciliacionEfectivoInt> lista = conciliacionEfectivoDao.getBitacoraConciliacionEfectivoInt(2346l);
		for(BitacoraConciliacionEfectivoInt bit : lista){
			System.out.println(bit.getBicCode());
			System.out.println(bit.getChk());
			System.out.println(bit.getMensaje());
		}
		//assertNotNull(lista);
	}
	
	public void tesstfindComentariosByIdDetalle(){
		Long idDetalle = 32710L;
		List<ComentarioEfectivoInt> comentarios = conciliacionEfectivoDao.findComentariosByIdDetalle(idDetalle);
		
		for(ComentarioEfectivoInt comentario : comentarios){
			log.info("comentario:" + comentario.getComentario());
		}
	}
	
	public void tesstConsultaSaldoInicialCuenta(){
		Set<String> bicCodes = new HashSet<String>();
		bicCodes.add("DEUTUS33CUS");
		bicCodes.add("CEDELULLXXX");
		
		Set<String> cuentas = new HashSet<String>();
		cuentas.add("153-02198-13");
		cuentas.add("75986");
		
		String divisa = "MXN";
		
		BigDecimal saldo = conciliacionEfectivoDao.consultaSaldoInicialCuenta(bicCodes, divisa, cuentas);
		System.out.println("saldo:" + saldo);
	}
	
	public void tesstConsultaSaldoAcumuladoHistorico() throws Exception{
		Set<String> bicCodes = new HashSet<String>();
		bicCodes.add("DEUTUS33CUS");
		bicCodes.add("CEDELULLXXX");
		
		Set<String> cuentas = new HashSet<String>();
		cuentas.add("153-02198-1-3");
		cuentas.add("75986");
		
		String divisa = "USD";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = sdf.parse("26/08/2013");
		
		BigDecimal saldo = conciliacionEfectivoDao.consultaSaldoAcumuladoHistorico(bicCodes, divisa, cuentas, fecha);
		System.out.println("saldo:" + saldo);
	}
}
