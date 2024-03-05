/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Jan 14, 2008
 *
 */
package com.indeval.portaldali.middleware.services.estadocuenta.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.BeansException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.constants.ColumnasOrdenadasConstants;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService;
import com.indeval.portaldali.middleware.services.util.test.DTOUtil;


/**
 * Prueba unitaria para el servicio ConsultaPosicionService
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class ConsultaPosicionServiceImplTest extends BaseTestCase {

	/**
	 * Servicio sobre el cual se ejecutaran las pruebas unitarias
	 */
	private ConsultaPosicionService consultaPosicionService = null;
	
	/** 
	 * Constante que define el dataset para la prueba unitaria
	 */
	private static String TEST_DATA_FILE = "/com/indeval/portaldali/datasets/dataSet_Posiciones.xml";	
	

	
	@Override
	public  void  onSetUp()  {
		try {
			super.onSetUp();
		consultaPosicionService = (ConsultaPosicionService) this.applicationContext.getBean("consultaPosicionService");
			DataSource ds = null;//this.jdbcTemplate.getDataSource();
		Connection conn = ds.getConnection();
		try {
			IDatabaseConnection connection = new DatabaseConnection(conn, "LIQVAL_USER");
			
		    DatabaseConfig config = connection.getConfig();
		    config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());
			
			DatabaseOperation.INSERT.execute(
					connection, 
					new FlatXmlDataSet(this.getClass().getResourceAsStream(TEST_DATA_FILE)));
		}
		finally {
			DataSourceUtils.releaseConnection(conn, ds);
		}
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseUnitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onTearDown() throws Exception {
		super.onTearDown();
		//endTransaction();
		DataSource ds = null;//this.jdbcTemplate.getDataSource();
		Connection conn = ds.getConnection();
		try {
			IDatabaseConnection connection = new DatabaseConnection(conn, "LIQVAL_USER");
			
		    DatabaseConfig config = connection.getConfig();
		    config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());
			
		    DatabaseOperation.DELETE.execute(				
			    	connection, 
					new FlatXmlDataSet(this.getClass().getResourceAsStream(TEST_DATA_FILE)));
		}
		finally {
			DataSourceUtils.releaseConnection(conn, ds);
		}
	}
	
//	/**
//	 * Prueba unitaria para el método consultarPosiciones
//	 */
//	public void testConsultarPosiciones() {
//		CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO();
//		orden.setColumna(ColumnasOrdenadasConstants.POR_CUENTA);
//		
//		//Para nombradas
//		PosicionDTO criterio = this.crearPosicionNombrada();
//		EstadoPaginacionDTO estadoPaginacionDTO = DTOUtil.crearEstadoPaginacionDTO();
//		estadoPaginacionDTO.setTotalResultados(
//				consultaPosicionService.obtenerProyeccionDeConsultaDePosiciones(criterio,true));
//		List<PosicionDTO> resultados =  
//			consultaPosicionService.consultarPosiciones(criterio, estadoPaginacionDTO,orden);
//		assertEquals(resultados.size(), 1);
//		PosicionDTO posicionDTO = resultados.get(0);
//		
//		assertNotNull(posicionDTO.getPosicion());
//		assertNotNull(posicionDTO.getPosicionDisponible());
//		assertNotNull(posicionDTO.getPosicionNoDisponible());
//		assertEquals("010050513", posicionDTO.getCuenta().getNumeroCuenta());
//		assertEquals(1, posicionDTO.getCuenta().getTipoTenencia().getIdTipoCuenta());
//		assertEquals("US9130171096", posicionDTO.getEmision().getIsin());
//		assertEquals("*", posicionDTO.getEmision().getSerie().getSerie());
//		assertEquals(1793, posicionDTO.getEmision().getEmisora().getId());
//		assertEquals(106, posicionDTO.getEmision().getTipoValor().getId());
//		assertEquals(1, posicionDTO.getEmision().getTipoValor().getMercado().getId());		
//		
//		//Para controladas
//		criterio = this.crearPosicionControlada();
//		estadoPaginacionDTO = DTOUtil.crearEstadoPaginacionDTO();
//		estadoPaginacionDTO.setTotalResultados(
//				consultaPosicionService.obtenerProyeccionDeConsultaDePosiciones(criterio,true));
//		resultados =  
//			consultaPosicionService.consultarPosiciones(criterio, estadoPaginacionDTO,orden);
//		assertEquals(resultados.size(), 1);
//		posicionDTO = resultados.get(0);		
//		assertNotNull(posicionDTO.getPosicion());
//		assertNotNull(posicionDTO.getPosicionDisponible());
//		assertNotNull(posicionDTO.getPosicionNoDisponible());
//		assertEquals("010059002", posicionDTO.getCuenta().getNumeroCuenta());
//		assertEquals(10, posicionDTO.getCuenta().getTipoTenencia().getIdTipoCuenta());
//		assertEquals("US9130171096", posicionDTO.getEmision().getIsin());
//		assertEquals("*", posicionDTO.getEmision().getSerie().getSerie());
//		assertEquals(1793, posicionDTO.getEmision().getEmisora().getId());
//		assertEquals(106, posicionDTO.getEmision().getTipoValor().getId());
//		assertEquals(1, posicionDTO.getEmision().getTipoValor().getMercado().getId());		
//	}
	

	/**
	 * Prueba unitaria para el método obtenerIdentificadoresValidosParaEmision
	 */
	public void testObtenerIdentificadoresValidosParaEmision() {
		//Para nombradas
		PosicionDTO criterio =  this.crearPosicionNombrada();
		List<Long> identificadoresValidos = consultaPosicionService.obtenerIdentificadoresValidosParaEmision(criterio);
		assertEquals(identificadoresValidos.size(), 1);
		assertEquals((Long)identificadoresValidos.get(0), new Long(1523));		
		
		//Para controladas
		criterio = this.crearPosicionControlada();
		identificadoresValidos = consultaPosicionService.obtenerIdentificadoresValidosParaEmision(criterio);
		assertEquals(identificadoresValidos.size(), 1);
		assertEquals((Long)identificadoresValidos.get(0), new Long(1523));			
	}
	
	/**
	 * Prueba unitaria para el método obtenerIdentificadoresValidosParaBoveda
	 */
	public void testObtenerIdentificadoresValidosParaBoveda() {
		//Para nombradas
		PosicionDTO criterio = this.crearPosicionNombrada();
		List<Long> identificadoresValidos = consultaPosicionService.obtenerIdentificadoresValidosParaBoveda(criterio);
		assertEquals(identificadoresValidos.size(), 1);
		assertEquals((Long)identificadoresValidos.get(0), new Long(7));
		
		//Para controladas
		criterio = this.crearPosicionControlada();
		identificadoresValidos = consultaPosicionService.obtenerIdentificadoresValidosParaBoveda(criterio);
		assertEquals(identificadoresValidos.size(), 1);
		assertEquals((Long)identificadoresValidos.get(0), new Long(7));		
	}

	
	/**
	 * Prueba unitaria para el método obtenerIdentificadoresValidosParaCuenta
	 */
	public void testObtenerIdentificadoresValidosParaCuenta() {
		//Para nombradas		
		PosicionDTO criterio = this.crearPosicionNombrada();
		List<Long> identificadoresValidos = consultaPosicionService.obtenerIdentificadoresValidosParaCuenta(criterio);
		assertEquals((Long) identificadoresValidos.get(0) , new Long(1993));
		
		//Para controladas
		criterio = this.crearPosicionControlada();
		identificadoresValidos = consultaPosicionService.obtenerIdentificadoresValidosParaCuenta(criterio);
		assertEquals(identificadoresValidos.size(), 1);
		assertEquals((Long)identificadoresValidos.get(0), new Long(881));		
	}
	
	
	/**
	 * Crea un DTO de posicion nombrada con datos para realizar la prueba unitaria
	 * @return DTO con los datos necesarios para ejecutar la prueba unitaria
	 */
	private PosicionDTO crearPosicionNombrada() {
		PosicionDTO criterio = DTOUtil.crearPosicionDTO("N", "P");
		criterio.setFechaInicio(FormatUtil.stringToDate("08/01/2008"));
		criterio.setFechaFinal(FormatUtil.stringToDate("08/01/2008"));
		
		//Colocamos la cuenta
		criterio.getCuenta().setNumeroCuenta("010050513");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(1);
		
		//Colocamos la boveda
		criterio.getBoveda().setId(7);
		
		//Colocamos la emision
		criterio.getEmision().setIsin("US9130171096");
		criterio.getEmision().getSerie().setSerie("*");
		criterio.getEmision().getEmisora().setId(1793);
		criterio.getEmision().getTipoValor().setId(106);
		criterio.getEmision().getTipoValor().getMercado().setId(1);		
		return criterio;
	}
	
	/**
	 * Crea un DTO de posicion controlada con datos para realizar la prueba unitaria
	 * @return DTO con los datos necesarios para ejecutar la prueba unitaria
	 */
	private PosicionDTO crearPosicionControlada() {
		PosicionDTO criterio = DTOUtil.crearPosicionDTO("C", "P");
		criterio.setFechaInicio(FormatUtil.stringToDate("08/01/2008"));
		criterio.setFechaFinal(FormatUtil.stringToDate("08/01/2008"));
		
		//Colocamos la cuenta
		criterio.getCuenta().setNumeroCuenta("010059002");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(10);
		
		//Colocamos la boveda
		criterio.getBoveda().setId(7);
		
		//Colocamos la emision
		criterio.getEmision().setIsin("US9130171096");
		criterio.getEmision().getSerie().setSerie("*");
		criterio.getEmision().getEmisora().setId(1793);
		criterio.getEmision().getTipoValor().setId(106);
		criterio.getEmision().getTipoValor().getMercado().setId(1);		
		return criterio;
	}
	
	 /**
     * metodo para obtener el nombre de los applicationContext a utilizar.
     *
     * @return String[]
     */
    protected String[] getConfigLocations() {
    	return BaseTestCase.archivosContext;
	}
}
