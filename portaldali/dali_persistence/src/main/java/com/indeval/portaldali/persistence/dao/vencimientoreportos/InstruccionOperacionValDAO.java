package com.indeval.portaldali.persistence.dao.vencimientoreportos;

import com.indeval.portaldali.persistence.model.InstruccionOperacionVal;

public interface InstruccionOperacionValDAO {
	InstruccionOperacionVal findById(long id);

	void actualizaPlazo(long idInstruccion, int nuevoPlazo);
}
