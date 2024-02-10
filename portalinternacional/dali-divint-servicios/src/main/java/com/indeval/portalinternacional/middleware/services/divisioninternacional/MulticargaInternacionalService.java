package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMulticarga;
import com.indeval.portalinternacional.middleware.servicios.modelo.RegistroMulticarga;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoOperacionMulticarga;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaResumenExcelVO;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaVO;

public interface MulticargaInternacionalService {
	
	/**Servicio para guardar un archivo en MulticargaInternacional-Adp
	 * @param MulticargaVO
	 * @return MulticargaResumenExcelVO
	 * @throws BusinessException
	 * */
	public MulticargaResumenExcelVO guardaRegistrosMulticargaAdp(MulticargaVO multicargaVO) throws BusinessException;
	
	/**Servicio para guardar un archivo en MulticargaInternacional-Multiempresa
	 * @param MulticargaVO
	 * @return MulticargaResumenExcelVO
	 * @throws BusinessException
	 * */
	public MulticargaResumenExcelVO guardaRegistrosMulticargaMultiempresa(MulticargaVO multicargaVO) throws BusinessException;
	
	/**Servicio para validar que el nombre del archivo a guardar no exista en la tabla
	 * @param String nombre del archivo
	 * @return true si ya existe este nombre, false si no existe el nombre
	 * @throws BusinessException
	 * */	
	public boolean nombreArchivoValido(String nombreArchivo)throws BusinessException;
	
	/**
	 * Servicio para obtener el catalogo de los estados de los registros Multicarga
	 * @return List<EstadoMulticarga> Lista con los estados
	 * @throws BusinessException
	 * */	
	public List<EstadoMulticarga> obtieneCatalogoEstadoMulticarga() throws BusinessException;
	
	/**
	 * Servicio para obtener el catalogo de los tipos de Operacion Multicarga
	 * @return  List<TipoOperacionMulticarga> Lista con los Tipos de Operacion
	 * @throws BusinessException
	 * */	
	public List<TipoOperacionMulticarga> obtieneCatalogoTipoOperacionMulticarga() throws BusinessException;
	
	/**
	 * Servicio para consultar las operaciones Multicarga que cumplan con los criterios de busqueda
	 * @param MulticargaVO con los criterios de busqueda deseados,PaginaVO para los datos de la paginaci&oacute;n
	 * @return PaginaVO con la paginaci&oacute;n y registros encontrados
	 * @throws BusinessException 
	 * */
	public PaginaVO consultaOperacionesMulticarga(MulticargaVO multicargaVO,PaginaVO paginaVO) throws BusinessException;
		
	/**
	 * Servicio para consultar un Registro Multicarga respecto al idMulticarga 
	 * @param Long idMulticarga
	 * @return RegistroMulticarga
	 * @throws BusinessException 
	 * */
	public RegistroMulticarga consultaRegistrosByIdMulticarga(Long idMulticarga)throws BusinessException;
	
	/**
	 * Servicio para borrar el registro multicarga(tabla T_MULTICARGA_INTERNACIONAL y T_REGISTROS_MULTICARGA),
	 * dependiendo del estado del Archivo, este servicio lo borra permanentemente o lo deja en estado borrado
	 * @param Long idMulticarga, String nombreUsuario
	 * @throws BusinessException
	 * */
	public void borrarRegistroMulticarga(Long idMulticarga, String nombreUsuario) throws BusinessException;
	
	/**
	 * Servicio para autorizar el Archivo Multicarga y asignar los valores dados por los registros
	 * @param Long idMulticarga, String nombreUsuario
	 * @return List lista con los registros que no pudo asignar(registros con error)
	 * @throws BusinessException
	 * */
	public List autorizarRegistroMulticarga(Long idMulticarga, String nombreUsuario) throws BusinessException; 
		

}
