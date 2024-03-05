package com.indeval.dali.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.indeval.dali.constans.Divisa;
import com.indeval.dali.constans.TipoCuenta;
import com.indeval.dali.constans.TipoEmision;
import com.indeval.dali.dao.PosicionNombradaDao;
import com.indeval.dali.vo.EmisionVO;


public class PosicionNombradaDaoImpl  extends JdbcDaoSupport implements PosicionNombradaDao{

	public List<EmisionVO> findEmisionesConPosicionMD(TipoCuenta tipoCuenta,int idInstitucion,Divisa divisa, int maxRows){
		return findEmisionesConPosicion(tipoCuenta,idInstitucion, "2,3",divisa,maxRows);
	}
	
	public List<EmisionVO> findEmisionesConPosicionMC(TipoCuenta tipoCuenta,int idInstitucion,Divisa divisa,int maxRows){
		return findEmisionesConPosicion(tipoCuenta,idInstitucion, "1",divisa,maxRows);
	}
	
	
	private List<EmisionVO> findEmisionesConPosicion(TipoCuenta tipoCuenta,int idInstitucion, String tipoMercado, Divisa divisa,int maxRows){
		final StringBuffer query=new StringBuffer();
		query.append("SELECT CN.CUENTA,TCN.CLAVE_TIPO_CUENTA,CN.ID_TIPO_CUENTA, PN.ID_EMISION, "); 
		query.append("CI.CLAVE_TIPO_VALOR, CA.CLAVE_PIZARRA, CE.SERIE, CU.CLAVE_CUPON, PN.POSICION_DISPONIBLE, B.DESCRIPCION ");
		query.append("FROM ADMIN_SEGU.T_POSICION_NOMBRADA PN ");
		query.append("INNER JOIN ADMIN_SEGU.C_EMISION CE ON PN.ID_EMISION = CE.ID_EMISION "); 
		query.append("INNER JOIN ADMIN_SEGU.C_EMISORA CA ON CE.ID_EMISORA = CA.ID_EMISORA  ");
		query.append("INNER JOIN ADMIN_SEGU.C_INSTRUMENTO CI ON CE.ID_INSTRUMENTO = CI.ID_INSTRUMENTO "); 
		query.append("INNER JOIN ADMIN_SEGU.C_CUPON CU ON CU.ID_CUPON = PN.ID_CUPON  ");
		query.append("INNER JOIN ADMIN_SEGU.C_CUENTA_NOMBRADA CN ON CN.ID_CUENTA_NOMBRADA = PN.ID_CUENTA "); 
		query.append("INNER JOIN ADMIN_SEGU.C_TIPO_CUENTA TCN ON TCN.ID_TIPO_CUENTA = CN.ID_TIPO_CUENTA ");
		query.append("INNER JOIN ADMIN_SEGU.C_INSTITUCION IC ON CN.ID_INSTITUCION = IC.ID_INSTITUCION  ");
		query.append("INNER JOIN ADMIN_SEGU.C_BOVEDA B ON B.ID_BOVEDA=PN.ID_BOVEDA ");
		query.append(" WHERE (CE.FECHA_VENCIMIENTO - 2 > SYSDATE or CE.FECHA_VENCIMIENTO is null)  ");
		query.append(" AND IC.ID_INSTITUCION = "+idInstitucion);
		query.append(" AND TCN.ID_TIPO_CUENTA = "+tipoCuenta.getId_tipo_cuenta());		
		query.append(" AND PN.POSICION_DISPONIBLE > 999");
		query.append(" AND CI.ID_MERCADO IN ("+tipoMercado+")");
		query.append(" AND CE.ID_DIVISA_VALOR_NOMINAL = "+divisa.getDivisa());
		query.append(" AND B.ID_BOVEDA=1");
		
		System.out.println("SQL:"+query.toString());
		this.getJdbcTemplate().setMaxRows(maxRows);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final List<EmisionVO> emisiones = this.getJdbcTemplate().query(
				query.toString(),
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						final String tipoValor=rs.getString("CLAVE_TIPO_VALOR");
						final String emisora=rs.getString("CLAVE_PIZARRA");
						final String serie=rs.getString("SERIE");
						final String cupon=rs.getString("CLAVE_CUPON");
						final int idEmision=rs.getInt("ID_EMISION");
						final String cuenta=rs.getString("CUENTA");
						final BigDecimal posicionDisponible=rs.getBigDecimal("POSICION_DISPONIBLE");
						final String boveda=rs.getString("DESCRIPCION");
						final EmisionVO emision = new EmisionVO();
						emision.setTv(tipoValor);
						emision.setEmisora(emisora);
						emision.setSerie(serie);
						emision.setCupon(cupon);
						emision.setIdEmision(idEmision);
						emision.setCuenta(cuenta);
						emision.setPosicionDisponible(posicionDisponible);
						emision.setBoveda(boveda);
						return emision;
					}
				}
		);
		
		return emisiones;
	}
	
	
	
	
	
}
