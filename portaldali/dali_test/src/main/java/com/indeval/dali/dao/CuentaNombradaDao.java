package com.indeval.dali.dao;

import java.util.List;

import com.indeval.dali.constans.TipoCuenta;

public interface CuentaNombradaDao {
	 List<String> findCuentas(TipoCuenta tipoCuenta,Integer idInstitucion);
}
