package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransBeneficiario;

public interface FileTransferBenefDao  extends BaseDao {

	PaginaVO findCargaExistente(Integer idInstitucion,String folioInstitucion,PaginaVO paginaVO);
	
	void saveCarga(List<FileTransBeneficiario> beneficiarios);
	
	void deleteCarga(Integer idInstitucion,String folioInstitucion, Boolean deleteOnlyWithError);
	
	long getNumRegistrosConError(Integer idInstitucion,String folioInstitucion);
	
	List<FileTransBeneficiario> consultaBeneficiariosConError(Integer idInstitucion,String folioInstitucion);
	
	long getNumRegistrosSinError(Integer idInstitucion,String folioInstitucion);
	
	List<FileTransBeneficiario> findCargaExistenteSinError(Integer idInstitucion,String folioInstitucion);
}
