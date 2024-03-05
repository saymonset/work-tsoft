package com.indeval.portaldali.persistence.dao.conciliacionModulos;

import java.util.List;

import com.indeval.portaldali.persistence.model.BitacoraEstadoReenvio;
import com.indeval.portaldali.persistence.model.BitacoraReenvio;

public interface BitacoraReenvioDAO {

	void save(BitacoraReenvio bitacoraReenvio);
	
	void save(BitacoraEstadoReenvio bitacoraEstadoReenvio);
	
	void save(List<BitacoraEstadoReenvio> lstBitacoraEstadoReenvio);
	
	BitacoraEstadoReenvio getEstadoReenvioByPk(String folioOperacion);
	
	List<BitacoraEstadoReenvio> getEstadosReenvioByPk(List<String> folioOperacion);
	
	void updateEstadoReenvio(String folioOperacion, boolean isProcesado);
	
	void updateEstadosReenvio(List<String> foliosOperacion, boolean isProcesado);

}
