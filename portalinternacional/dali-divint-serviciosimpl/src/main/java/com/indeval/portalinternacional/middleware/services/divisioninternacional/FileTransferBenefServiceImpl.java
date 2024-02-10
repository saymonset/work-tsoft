package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransBeneficiario;
import com.indeval.portalinternacional.persistence.dao.FileTransferBenefDao;

public class FileTransferBenefServiceImpl implements FileTransferBeneficiariosService{

	private FileTransferBenefDao fileTransferBenefDao;

	public PaginaVO consultaCargaExistente(Integer idInstitucion, String folioInstitucion, PaginaVO paginaVO) {		
		return fileTransferBenefDao.findCargaExistente(idInstitucion, folioInstitucion,paginaVO);
	}

	public void guardaCarga(List<FileTransBeneficiario> beneficiarios) {
		if(beneficiarios != null && !beneficiarios.isEmpty()){
			fileTransferBenefDao.deleteCarga(beneficiarios.get(0).getIdInstitucion(), beneficiarios.get(0).getFolioInstitucion(), Boolean.FALSE);
			fileTransferBenefDao.saveCarga(beneficiarios);
		}				
	}

	public void eliminaCargaExistente(Integer idInstitucion,String folioInstitucion, Boolean deleteOnlyWithError) {
		fileTransferBenefDao.deleteCarga(idInstitucion, folioInstitucion, deleteOnlyWithError);		
	}
	
	public void setFileTransferBenefDao(FileTransferBenefDao fileTransferBenefDao) {
		this.fileTransferBenefDao = fileTransferBenefDao;
	}

	public long countBeneficiariosConError(Integer idInstitucion,String folioInstitucion) {
		return fileTransferBenefDao.getNumRegistrosConError(idInstitucion, folioInstitucion);
	}
	
	public List<FileTransBeneficiario> consultaBeneficiariosConError(Integer idInstitucion,String folioInstitucion) {
		return fileTransferBenefDao.consultaBeneficiariosConError(idInstitucion, folioInstitucion);
	}

	public long countBeneficiariosSinError(Integer idInstitucion,String folioInstitucion) {
		return fileTransferBenefDao.getNumRegistrosSinError(idInstitucion, folioInstitucion);
	}

	public List<FileTransBeneficiario> consultaCargaExistenteSinError(Integer idInstitucion, String folioInstitucion) {
		return fileTransferBenefDao.findCargaExistenteSinError(idInstitucion,folioInstitucion);
	}
}
