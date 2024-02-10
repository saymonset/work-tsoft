package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import javax.ejb.Remote;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransBeneficiario;

@Remote
public interface FileTransferBeneficiariosService {

	PaginaVO consultaCargaExistente(Integer idInstitucion,String folioInstitucion,PaginaVO paginaVO);
	
	void guardaCarga(List<FileTransBeneficiario> beneficiarios);
	
	void eliminaCargaExistente(Integer idInstitucion,String folioInstitucion, Boolean deleteOnlyWithError);
	
	long countBeneficiariosConError(Integer idInstitucion,String folioInstitucion);
	
	List<FileTransBeneficiario> consultaBeneficiariosConError(Integer idInstitucion,String folioInstitucion);
	
	long countBeneficiariosSinError(Integer idInstitucion,String folioInstitucion);
	
	List<FileTransBeneficiario> consultaCargaExistenteSinError(Integer idInstitucion,String folioInstitucion);
}
