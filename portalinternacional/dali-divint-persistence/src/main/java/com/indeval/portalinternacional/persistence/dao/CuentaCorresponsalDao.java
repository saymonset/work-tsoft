package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaCorresponsal;

public interface CuentaCorresponsalDao {
	/** Busca cuentas corresponsales por el id de una institucion. */
	List<CuentaCorresponsal> findCuentaCorresponsalByIdInstitucion(Long idInstitucion);

	/** Busca cuentas corresponsales por el id de una divisa */
	List<CuentaCorresponsal> findCuentaCorresponsalByIdDivisa(Long idDivisa);

	/** Busca cuentas corresponsales por el id de una divisa */
	List<CuentaCorresponsal> findCuentaCorresponsalByIdDivisaAndIdInstitucion(Long idDivisa, Long idInstitucion);

	/** Busca id de las divisas relacionas con la cuenta corresponsal */
	List<Long> findIdDivisasByIdInstitucion(Long idInstitucion);

}
