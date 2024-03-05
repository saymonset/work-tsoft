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
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.constants.ColumnasOrdenadasConstants;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaSaldosEfectivoService;
import com.indeval.portaldali.middleware.services.util.test.DTOUtil;


/**
 * Prueba unitaria del servicio de negocio ConsultaSaldosEfectivoService
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class ConsultaSaldosEfectivoServiceImplTest extends BaseTestCase {
	
	/** 
	 * Constante que define el dataset para la prueba unitaria
	 */
	private static String TEST_DATA_FILE = "/com/indeval/estadocuenta/core/dataSets/dataSet_Saldos.xml";

	/**
	 * Servicio de negocio sobre el cual se efectuaran las pruebas unitarias
	 */
	private ConsultaSaldosEfectivoService consultaSaldosEfectivoService = null;
	
	
	@Override
	public void onSetUp()  {
		try {
			super.onSetUp();		
		consultaSaldosEfectivoService = 
			(ConsultaSaldosEfectivoService)this.applicationContext.getBean("consultaSaldosEfectivoService");
			DataSource ds = null;//this.jdbcTemplate.getDataSource();
		Connection conn = ds.getConnection();
		try {
			IDatabaseConnection connection = new DatabaseConnection(conn, "LIQVAL_USER3");
			
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
	public void onTearDown() {
		try {
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
	
	/**
	 * Prueba unitaria para el método consultarSaldosEfectivo
	 */
	public void testConsultarSaldosEfectivo() {
		EstadoPaginacionDTO estadoPaginacion = DTOUtil.crearEstadoPaginacionDTO();
		CriterioOrdenamientoDTO criterioOrdenamiento = new CriterioOrdenamientoDTO();
		criterioOrdenamiento.setColumna(ColumnasOrdenadasConstants.POR_SALDO);
		
		//Para nombradas
		SaldoEfectivoDTO criterio = crearSaldoEfectivoDTONombrada();
		estadoPaginacion.setTotalResultados(
				consultaSaldosEfectivoService.obtenerProyeccionConsultaSaldosEfectivo(criterio,false));
		List<SaldoEfectivoDTO> saldosNombrados = 
			consultaSaldosEfectivoService.consultarSaldosEfectivo(criterio, estadoPaginacion, criterioOrdenamiento);
		for (SaldoEfectivoDTO saldoEfectivoDTO : saldosNombrados) {
			assertEquals(saldoEfectivoDTO.getCuenta().getNumeroCuenta(), "010012001");
			assertEquals(saldoEfectivoDTO.getBoveda().getId(), 18);
			assertEquals(saldoEfectivoDTO.getDivisa().getId(), 1);
		}
		
		//Para controladas
		criterio = crearSaldoEfectivoDTOControlada();
		estadoPaginacion.setTotalResultados(
				consultaSaldosEfectivoService.obtenerProyeccionConsultaSaldosEfectivo(criterio,false));
		criterioOrdenamiento.setColumna(ColumnasOrdenadasConstants.POR_SALDO);
		List<SaldoEfectivoDTO> saldosControladas = 
			consultaSaldosEfectivoService.consultarSaldosEfectivo(criterio, estadoPaginacion, criterioOrdenamiento);
		for (SaldoEfectivoDTO saldoEfectivoDTO : saldosControladas) {
			assertEquals(saldoEfectivoDTO.getCuenta().getNumeroCuenta(), "010019005");
			assertEquals(saldoEfectivoDTO.getBoveda().getId(), 17);
			assertEquals(saldoEfectivoDTO.getDivisa().getId(), 2);
		}		
	}
	
	/**
	 * Prueba unitaria para el método obtenerIdentificadoresValidosParaDivisa
	 */
	public void testObtenerIdentificadoresValidosParaDivisa() {
		//Para nombradas
		List<Long> identificadoresValidos = 
			consultaSaldosEfectivoService.obtenerIdentificadoresValidosParaDivisa(crearSaldoEfectivoDTONombrada());
		assertEquals(1, identificadoresValidos.size());
		assertEquals((Long) identificadoresValidos.get(0), Long.valueOf(1));
		//Para controladas
		identificadoresValidos = 
			consultaSaldosEfectivoService.obtenerIdentificadoresValidosParaDivisa(crearSaldoEfectivoDTOControlada());
		assertEquals(1, identificadoresValidos.size());
		assertEquals((Long) identificadoresValidos.get(0), Long.valueOf(2));
	}
	
	/**
	 * Prueba unitaria para el método obtenerIdentificadoresValidosParaBoveda
	 */
	public void testObtenerIdentificadoresValidosParaBoveda() {
		//Para nombradas
		List<Long> identificadoresValidos = 
			consultaSaldosEfectivoService.obtenerIdentificadoresValidosParaBoveda(crearSaldoEfectivoDTONombrada());
		assertEquals(1, identificadoresValidos.size());
		assertEquals((Long) identificadoresValidos.get(0), Long.valueOf(18));
		//Para controladas
		identificadoresValidos = 
			consultaSaldosEfectivoService.obtenerIdentificadoresValidosParaBoveda(crearSaldoEfectivoDTOControlada());
		assertEquals(1, identificadoresValidos.size());
		assertEquals((Long) identificadoresValidos.get(0), Long.valueOf(17));		
	}
	
	/**
	 * Prueba unitaria para el método obtenerIdentificadoresValidosParaCuenta
	 */
	public void testObtenerIdentificadoresValidosParaCuenta() {
		//Para nombradas
		List<Long> identificadoresValidos = 
			consultaSaldosEfectivoService.obtenerIdentificadoresValidosParaCuenta(crearSaldoEfectivoDTONombrada());
		assertEquals(1, identificadoresValidos.size());
		assertEquals((Long) identificadoresValidos.get(0), Long.valueOf(1953));
		//Para controladas
		identificadoresValidos = 
			consultaSaldosEfectivoService.obtenerIdentificadoresValidosParaCuenta(crearSaldoEfectivoDTOControlada());
		assertEquals(1, identificadoresValidos.size());
		assertEquals((Long) identificadoresValidos.get(0), Long.valueOf(863));			
	}
	
	/**
	 * Crea un DTO de {@link SaldoEfectivoDTO} para ejecutar las pruebas sobre saldos nombradas
	 * @return DTO con los datos necesarios para ejecutar las pruebas 
	 */
	private SaldoEfectivoDTO crearSaldoEfectivoDTONombrada() {
		SaldoEfectivoDTO criterio = DTOUtil.crearSaldoEfectivoDTO("N", "P");		
		//Colocamos la fecha
		criterio.setFecha(FormatUtil.stringToDate("01/02/2008"));
		//Colocamos la institucion
		criterio.getCuenta().getInstitucion().setId(1);		
		//Colocamos el número de cuenta
		criterio.getCuenta().setNumeroCuenta("010012001");
		//Colocamos la divisa
		criterio.getDivisa().setId(1);
		//Colocamos la boveda
		criterio.getBoveda().setId(18);
		return criterio;
	}
	
	/**
	 * Crea un DTO de {@link SaldoEfectivoDTO} para ejecutar las pruebas sobre saldos controladas
	 * @return DTO con los datos necesarios para ejecutar las pruebas 
	 */	
	private SaldoEfectivoDTO crearSaldoEfectivoDTOControlada() {
		SaldoEfectivoDTO criterio = DTOUtil.crearSaldoEfectivoDTO("C", "P");
		//Colocamos la fecha
		criterio.setFecha(FormatUtil.stringToDate("01/02/2008"));
		//Colocamos la institucion
		criterio.getCuenta().getInstitucion().setId(1);		
		//Colocamos el número de cuenta
		criterio.getCuenta().setNumeroCuenta("010019005");
		//Colocamos la divisa
		criterio.getDivisa().setId(2);
		//Colocamos la boveda
		criterio.getBoveda().setId(17);
		return criterio;
	}	
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
	 */
	protected String[] getConfigLocations() {
        return new String[] { "classpath:com/indeval/portaldali/conf/applicationContext.xml",
        					  "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-saldos.xml"	};
    }	
	
}
