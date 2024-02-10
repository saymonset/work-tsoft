package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Emisora;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.Instrumento;

public interface EmisionDao   extends BaseDao{

	Emision findEmisionByIsin(String isin);
	Emisora findEmisoraByCvePizarra(String clavePizarra);
	Instrumento findInstrumentoByTv(String tv);
	Institucion findInstitucionByIdFolio(Long id,String folio);
}
