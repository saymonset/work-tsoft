package com.indeval.portaldali.persistence.dao.vencimientoreportos;

import java.util.List;

import com.indeval.portaldali.persistence.model.VencimientoAnticipado;

public interface VencimientoAnticipadoDAO {
	
	public void save(VencimientoAnticipado vencimiento);
	public void update(VencimientoAnticipado vencimiento);
	
	public VencimientoAnticipado findById(long id);
	public List<VencimientoAnticipado> findByInstruccionAndStatus(long idInstruccion, String status);
}

