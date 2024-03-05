package com.indeval.dali.dao;

import java.util.List;

import com.indeval.dali.constans.Divisa;
import com.indeval.dali.constans.TipoCuenta;
import com.indeval.dali.constans.TipoEmision;
import com.indeval.dali.vo.EmisionVO;

public interface PosicionNombradaDao {
	List<EmisionVO> findEmisionesConPosicionMD(TipoCuenta tipoCuenta,int idInstitucion,Divisa divisa,int maxRows);
	List<EmisionVO> findEmisionesConPosicionMC(TipoCuenta tipoCuenta,int idInstitucion,Divisa divisa,int maxRows);
}
