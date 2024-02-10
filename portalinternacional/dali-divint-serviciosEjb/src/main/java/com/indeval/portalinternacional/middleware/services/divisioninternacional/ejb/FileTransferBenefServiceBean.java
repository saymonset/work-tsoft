package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransBeneficiario;

@Stateless(name = "ejb.fileTransfBenefService", mappedName = "ejb.fileTransfBenefService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(FileTransferBeneficiariosService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class FileTransferBenefServiceBean implements FileTransferBeneficiariosService{

	@Autowired
	private FileTransferBeneficiariosService fileTransferBenefService;
	
	public PaginaVO consultaCargaExistente(Integer idInstitucion, String folioInstitucion,PaginaVO paginaVO) {
		return fileTransferBenefService.consultaCargaExistente(idInstitucion, folioInstitucion, paginaVO);
	}

	public void guardaCarga(List<FileTransBeneficiario> beneficiarios) {
		fileTransferBenefService.guardaCarga(beneficiarios);		
	}

	public void eliminaCargaExistente(Integer idInstitucion,String folioInstitucion, Boolean deleteOnlyWithError) {
		fileTransferBenefService.eliminaCargaExistente(idInstitucion, folioInstitucion, deleteOnlyWithError);		
	}

	public long countBeneficiariosConError(Integer idInstitucion,String folioInstitucion) {		
		return fileTransferBenefService.countBeneficiariosConError(idInstitucion, folioInstitucion);
	}
	
	public List<FileTransBeneficiario> consultaBeneficiariosConError(Integer idInstitucion, String folioInstitucion) {
		return fileTransferBenefService.consultaBeneficiariosConError(idInstitucion, folioInstitucion);
	}

	public long countBeneficiariosSinError(Integer idInstitucion,String folioInstitucion) {		
		return fileTransferBenefService.countBeneficiariosSinError(idInstitucion, folioInstitucion);
	}

	public List<FileTransBeneficiario> consultaCargaExistenteSinError(Integer idInstitucion, String folioInstitucion) {
		return fileTransferBenefService.consultaCargaExistenteSinError(idInstitucion, folioInstitucion);
	}

}
