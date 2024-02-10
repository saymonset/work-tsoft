package com.indeval.portalinternacional.persistence.dao;

import java.util.List;
import java.util.Map;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferCapturaBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.RegistrosBeneficiarios;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoFormatoBeneficiario;

public interface FileTransferCapturaBenefDao extends BaseDao{

	/**
	 * Servicio que regresa una carga temporal del archivo de beneficiarios, buscando por la clave del usuario
	 * @param String claveUsuario
	 * @return FileTransferCapturaBeneficiario	
	 * */
	public FileTransferCapturaBeneficiario findFileTransferCapturaBeneByIdFolioTipoReg(final String claveUsuario);
	
	/**
	 * Servicio que regresa la lista de formatos de beneficiarios
	 * @return List<TipoFormatoBeneficiario>
	 * */
	public List<TipoFormatoBeneficiario> getFormatoBeneficiario();
	
	/**
	 * Servicio que regresa los registros correctos de la pre-carga de beneficiarios
	 * @param String claveUsuario
	 * @return RegistrosBeneficiarios 
	 * */
	public RegistrosBeneficiarios consultaRegistrosBeneByIdFileCarga(final String claveUsuario);
	
	/**
	 * Servicio para eliminar los registros con error y datos de la carga temporal de beneficiarios
	 * @param String claveUsuario
	 * @return Integer n&uacute;mero de registros eliminados 
	 * */
	public Integer deleteArchivoTempFileTransferBeneficiario(final String claveUsuario);
	
	/**
	 * Servicio para eliminar los registros correctos de la carga temporal de beneficiarios
	 * @param String claveUsuario
	 * @return Integer n&uacute;mero de registros eliminados 
	 * */
	public Integer deleteArchivoTempRegistrosCorrectos(final String claveUsuario);
		
}
