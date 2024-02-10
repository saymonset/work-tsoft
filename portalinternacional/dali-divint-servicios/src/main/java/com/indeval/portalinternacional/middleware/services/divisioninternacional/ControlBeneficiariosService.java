package com.indeval.portalinternacional.middleware.services.divisioninternacional;


import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptPayeeW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptionFatcaW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W9;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILACodigoDepartamento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILASectorEconomico;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoDocumento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoEmpresa;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatusBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.BeneficiariosPaginacionVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CatBicVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam;

/**
 * Interface del servicio de control de beneficiarios
 * @author ribarra
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public interface ControlBeneficiariosService {

	/**
	 * Inserta un objeto de tipo Beneficiario en la base de datos.
	 * @param beneficiario Beneficiario a insertar
	 * @param idInstitucion	Institucion seleccionada
	 * @throws BusinessException En caso de error de validacion
	 */
	public Long insertaBeneficiario(Beneficiario beneficiario, Long idInstitucion) throws BusinessException;

	/**
	 * Elimina un beneficiario
	 * @param idBeneficiario Beneficiario a eliminar
	 * @throws BusinessException En caso de error de validacion
	 */
	public void eliminaBeneficiario(Long idBeneficiario) throws BusinessException;
	
	/**
	 * Agrega un beneficiairo a una institucion
	 * @param idBeneficiario Id del beneficiario
	 * @param idInstitucion Id de la institucion
	 * @throws BusinessException En caso de error de validacion
	 */
	public void agregaBeneficiarioInstitucion(Long idBeneficiario, Long idInstitucion) throws BusinessException;
	
	/**
	 * Obtiene de un beneficiairo la institucion
	 * @param idBeneficiario Id del beneficiario
	 * @param idInstitucion
	 * @return 
	 * @throws BusinessException En caso de error
	 */
	public BeneficiarioInstitucion getBeneficiarioInstitucion(Long idBeneficiario, Long idInstitucion) throws BusinessException;
	
	/**
	 * Calcula el codigo UOI del beneficiario en base a se datos
	 * @param beneficiario Beneficario seleccionado
	 * @return Codigo del beneficiario UOI del beneficiario
	 * @throws BusinessException En caso de error de validacion
	 */
	public String obtieneCodigoBeneficiario(Beneficiario beneficiario) throws BusinessException;
	/**
	 * Pre autoriza un beneficiario
	 * @param idBeneficiario Id del Beneficiario
	 * @throws BusinessException En caso de error de validacion
	 */
	public void preAutorizaBeneficiario(Long idBeneficiario) throws BusinessException;
	/**
	 * Activa un beneficiario
	 * @param idBeneficiario Id del Beneficiario
	 * @throws BusinessException En caso de error de validacion
	 */
	public void activarBeneficiario(Long idBeneficiario) throws BusinessException;

	/**
	 * Cancela un beneficiario
	 * @param idBeneficiario Id del Beneficiario
	 * @throws BusinessException En caso de error de validacion
	 */
	public void cancelaBeneficiario(Long idBeneficiario) throws BusinessException;
	
	/**
	 * Realiza la consulta de los Beneficiarios en la base de datos.con el nuevo metodo
	 * @param param Parametros de la consulta
	 * @param paginaVO Estado de la Paginacion
	 * @param Boolean isPopup
	 * @return PaginaVO Consulta Paginada
	 * @throws BusinessException En caso de error de validacion
	 */
	public PaginaVO consultaBeneficiarios(ConsultaBeneficiariosParam param,PaginaVO paginaVO, Boolean isPopup) throws BusinessException;

	/**
	 * Realiza la consulta de los Beneficiarios Historicos en la base de datos.
	 * @param param Parametros de la consulta
	 * @param paginaVO Estado de la Paginacion
	 * @return PaginaVO Consulta Paginada
	 * @throws BusinessException En caso de error de validacion
	 */
	public PaginaVO consultaBeneficiariosHistorico(ConsultaHistoricoBeneficiariosParam param,PaginaVO paginaVO) throws BusinessException;
	
	/**
	 * Obtiene un Beneficiario por ID
	 * 
	 * @param idBeneficiario Id del beneficiario a buscar
	 * @return Beneficiario Beneficiario encontrado
	 * @throws BusinessException En caso de error de validacion
	 */
	public Beneficiario consultaBeneficiarioById(Long idBeneficiario) throws BusinessException;
	
	/**
	 * Obtiene un Beneficiario por uoi
	 * 
	 * @param uoiBeneficiario del beneficiario a buscar
	 * @return Beneficiario Beneficiario encontrado
	 * @throws BusinessException En caso de error de validacion
	 */
	public Beneficiario consultaBeneficiarioByUoi(String uoiBeneficiario) throws BusinessException;
	
	/**
	 * Actualiza los datos de un Beneficiario
	 * @param beneficiario Beneficiario a actualizar
	 * @throws BusinessException En caso de error de validacion
	 */
	public void actualizaBeneficiario(Beneficiario beneficiario) throws BusinessException;

	/**
	 * Devuelve una lista de todos los custodios (catbic)
	 * @return CatBic[]
	 * @throws BusinessException
	 */
	public List<Object[]> obtieneCatBic() throws BusinessException;
	
	/**
	 * Devuelve una lista de todos los StatusBeneficiario
	 * @return StatusBeneficiario[]
	 * @throws BusinessException
	 */
	public StatusBeneficiario[] obtieneStatusBeneficiario() throws BusinessException;
	
	/**
	 * Devuelve un arreglo con los tipos de beneficiario que corresponden
	 * a un custodio determinado.
	 * @param idCuentaNombrada Id del custodio
	 * @return List Tipos de Beneficiarios encontrados
	 */
	public List obtieneTiposBeneficiario(Long idCuentaNombrada);
	
	/**
	 * Devuelve un arreglo con los tipos de beneficiario.
	 * @return TipoBeneficiario[] Tipos de Beneficiario
	 */
	public List obtieneTiposBeneficiario();
	
	/**
	 * Devuelve un arreglo con los tipos de beneficiario que corresponden
	 * a un custodio determinado.
	 * @param catBic Id del custodio
	 * @return TipoBeneficiario[] Tipos de Beneficiario
	 */
	public TipoBeneficiario[] obtieneTiposBeneficiario(String catBic);
	
	
	/**
	 * Devuelve el formato de acuerdo al tipo de beneficirio y a la
	 * cuenta nombrada
	 * @param idCuentaNombrada Id del custodio
	 * @param idTipoBeneficiario Tipo de Beneficiario
	 * @return String formato Formato Correspondiente
	 */
	public String obtieneFormato( Long idCuentaNombrada, Long idTipoBeneficiario );
	
	/**
	 * Obtiene la lista de campos para el formato
	 * @return List<Field3W8BEN> lista de Campos
	 */
	public List<Field3W8BEN> getField3W8BEN();
	
	/**
	 * Obtiene la lista de campos para el formato
	 * @return List<Field3W8IMY> lista de Campos
	 */
	public List<Field3W8IMY> getField3W8IMY();
	
	/**
	 * Obtiene la lista de campos para el formato
	 * @return List<Field3W9> lista de Campos
	 */
	public List<Field3W9> getField3W9();
	
	/**
	 * Obtiene la lista completa de CustodioTipoBenef
	 * @return Lista de CustodioTipoBenef
	 */
	public List<CustodioTipoBenef> getListaCustodioTipoBenef();

	/**
	 * Regresa la fecha valida para los formatos
	 * @return fecha valida
	 */
	public Date obtieneFechaValida();

	/**
	 * Asigna un adp a un beneficiario existente
	 * @param idBeneficiario Id del beneficiario a acutalizar
	 * @param adp Numero de Adp como cadena
	 */
	public void asignaAdpBeneficiario(Long idBeneficiario, String adp);

	/**
	 * Elimina una institucion a un beneficiario
	 * @param idBeneficiario Id del beneficiario
	 * @param idInstitucion Id de la institucion
	 */
	public void eliminaInstitucionBeneficiario(Long idBeneficiario, Long idInstitucion);
	 
	/**
	 * Consulta un beneficiario por UOI y que tiene asignada la institucin
	 * 
	 * @param uoi
	 * @param idEstausBeneficiario
	 * @param idTipoInstitucion
	 * @param folioInstitucion
	 * @return
	 * @throws BusinessException
	 */
	Beneficiario consultaBeneficiarioUoiInst(String uoi,Long idEstausBeneficiario,Long idTipoInstitucion,String folioInstitucion) throws BusinessException;
	
	/**
	 * Consulta todos los custodios
	 * @return
	 * @throws BusinessException
	 */
	List<Object[]> obtieneAllCatBic() throws BusinessException;
	
	List<BeneficiariosPaginacionVO> consultaPaginasBeneficiarios(ConsultaBeneficiariosParam param, Boolean isPopup) throws BusinessException;

	/**
	 * Realiza la depuracion de los formatos fiscales de los beneficiarios.
	 * @throws BusinessException En caso de error.
	 */
	void depurarFormatosFiscalesBeneficiarios() throws BusinessException;
	
	/**
	 * Asigna un nuevo valor al porcentaje de retencion del beneficairio
	 * @param idBeneficiario Id del beneficiario
	 * @param nuevoPorcentajeRetencion el valor del nuevo porcentaje de Retencion
	 * */
	public void modificaPorcentajeRetencion(Long idBeneficiario, Double nuevoPorcentajeRetencion)  throws BusinessException;
	
	/**
	 * Elimina el valor del Adp asignado por multicarga, le asigna el valor nulo
	 @param String Uoi 
	 * */
	public void asignaValorAdpNuloBeneficiario(String uoi);
	
	/**
	 * Elimina una institucion a un beneficiario
	 * @param idBeneficiario Id del beneficiario
	 * @param idInstitucion Id de la institucion
	 */
	public void eliminaInstitucionBeneficiarioMulticarga(Long idBeneficiario, Long idInstitucion);	
	
	/**
	 * Consulta el catalogo de Exempt Payee para W9
	 * @return
	 */
	public List<ExemptPayeeW9> consultaCatExemptPayeeW9();
	
	/**
	 * Consulta catalogo de Exemption Fatca para W9
	 * @return
	 */
	public List<ExemptionFatcaW9> consultaCatExemptionFatcaW9();
	/**
	 * Consulta beneficiarios aunque se encuentren eliminados
	 * @return
	 */
	public Beneficiario consultaBeneficiarioByIdEliminados(Long idBeneficiario) throws BusinessException;

	
	/**
	 * Consulta del catalago de tipo de sectores economicos para
	 * los formatos MILA
	 * 
	 * @return Lista con los valores del catalogo
	 */
	List<MILASectorEconomico> consultaCatMilaSectorEconomico();
	
	/**
	 * Consulta del catalogo de tipo de documentos para 
	 * los formatos MILA
	 * 
	 * @return Lista con los valores del catalogo
	 */
	List<MILATipoDocumento> consultaCatMilaTipoDocumento();
	
	/**
	 * Consulta del catalogo de tipo de empresas para
	 * los formatos MILA
	 * 
	 * @return Lista con los valores del catalogo
	 */
	List<MILATipoEmpresa> consultaCatMilaTipoEmpresa();
	
	/**
	 * Consulta de entidades federativas para los
	 * formato MILA
	 * @return Lista con los valores del catalogo
	 */
	List<MILACodigoDepartamento> consultaCatMilaEstados();
	
	/**
	 * Metodo para actualizar estatus de un beneficiario
	 * @param Beneficiario
	 * @param statusActualizado
	 * @param descStatusActualizad
	 */
	public void actualizaStatusBeneficiario(Beneficiario beneficiario, long statusActualizado, String descStatusActualizad);
	
	/**
	 * 
	 * @param beneficiario
	 * @param statusActualizado
	 * @param descStatusActualizado
	 * @return
	 */
	public void cambiaStatusBeneficiario(Beneficiario beneficiario, long statusActualizado, String descStatusActualizado);
	
	/**
	 * Devuelve una lista de todos los custodios (catbic) activos
	 * @return CatBic[]
	 * @throws BusinessException
	 */
	public List<Object[]> obtieneCatBicActivo() throws BusinessException;
	
	
	/**
	 * Consulta si un custodio esta activo
	 * @param idCuenta
	 */
	public boolean esCatBicActivo(Long idCuentaNombrada);
	
	/**
	 * Consulta la lista de entidades custodio
	 */
	public List<CatBicVO> obtieneCatBicEntities();
	
	/**
	 * Metodo para modificar atributos de un objeto CatBicVO
	 * @param custodiosBeneficiarios
	 */
	void modificarCustodios(CatBicVO custodiosBeneficiarios);
	
	/**
	 * Valida si la abreviacion de un custodio existe.
	 * @param abreviacionCustodio
	 * @return Boolean
	 */
	CatBic findAbreviacionByCustodio(String abreviacionCustodio);
	
}
