package com.indeval.portalinternacional.middleware.services.divisioninternacional;


import java.util.List;

import javax.ejb.Remote;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Derecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.DerechoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FactorSimulacionMav;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.VConsultaBeneficiarioDerechosCuenta;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosParam;
import com.indeval.sidv.ejercicios.derechos.patrimoniales.dto.SimulacionDerechoInternacionalDto;
@Remote
public interface ControlDerechosService {

	/**
	 * Metodo que realiza la consulta de los derechos usando los parametros contenidos en param, 
	 * si este es vacio consulta todos los derechos.
	 * 
	 * @param params Los parametros para realizar la busqueda.
     * @param paginaVO El objeto a asignar el resultado.
     * @param all Discierne si se van a obtener todos los objetos (como para reportes y limitados por un maximo) o se esta 
     *            haciendo una consulta desde la vista.
	 * @return
	 * @throws BusinessException
	 */
	PaginaVO getDerechosUsandoVista(ConsultaDerechosParam params, PaginaVO paginaVO, boolean all) throws BusinessException;

    /**
     * Metodo que realiza la consulta de los derechos contenidos en param, si este es vacio consulta todos los derechos
     * 
     * @param param
     * @param paginaVO
     * @return
     * @throws BusinessException
     */
    PaginaVO getDerechos(ConsultaDerechosParam param, PaginaVO paginaVO)throws BusinessException;
	
	/**
	 * Consulta los beneficiarios pertenecientes a las cuentas
	 * @param idDerecho
	 * @param idCuentas
	 * @return
	 * @throws BusinessException
	 */
	List<DerechoBeneficiario> getBenefDerechoXCuenta(Long idDerecho,Long[] idCuentas)throws BusinessException;
    
	
	List<VConsultaBeneficiarioDerechosCuenta> getBenefDerechoXCuentaOptimizadoSimplificado(final Long idDerecho, final Long[] idCuentas,Integer idTipoInstitucion,String folioInstitucion)
    		throws BusinessException;
	
	
	
    /**
     * Consulta los beneficiarios pertenecientes a las cuentas, este servicio usara metodos de persistencia sin usar
     * entidades de hibernate que tengan relaciones fetch tipo EAGER y generen demasiadas consultas a la BD.
     * @param idDerecho
     * @param idCuentas
     * @return
     * @throws BusinessException
     */
	List<VConsultaBeneficiarioDerechosCuenta> getBenefDerechoXCuentaOptimizado(Long idDerecho, Long[] idCuentas) throws BusinessException;
	
	/**
	 * Consulta los beneficiarios pertenecientes a las cuentas y por institucion
	 * @param idDerecho
	 * @param idCuentas
	 * @param idTipoInstitucion
	 * @param folioInstitucion
	 * @return
	 * @throws BusinessException
	 */
	List<DerechoBeneficiario> getBenefDerechoXCuenta(Long idDerecho,Long[] idCuentas,Integer idTipoInstitucion,String folioInstitucion)throws BusinessException;
    
    /**
     * Consulta los beneficiarios pertenecientes a las cuentas y por institucion, este servicio usara metodos de persistencia sin usar
     * entidades de hibernate que tengan relaciones fetch tipo EAGER y generen demasiadas consultas a la BD.
     * @param idDerecho
     * @param idCuentas
     * @param idTipoInstitucion
     * @param folioInstitucion
     * @return
     * @throws BusinessException
     */
	List<VConsultaBeneficiarioDerechosCuenta> getBenefDerechoXCuentaOptimizado(Long idDerecho, Long[] idCuentas, 
                                                                               Integer idTipoInstitucion, String folioInstitucion)
                                              throws BusinessException;
	
	/**
	 * Actualiza todos los beneficiarios del derecho a eliminados
	 * @param idBeneficiarioDerecho
	 * @throws BusinessException
	 */
	void deleteBeneficiarioDerecho(Long idBeneficiarioDerecho)throws BusinessException;
	
	/**
	 * Valida si el beneficiario ya fue agregado al derecho y cuenta
	 * @param idBeneficiario
	 * @param idCuentaNombrada
	 * @param idDerechoCapital
	 * @return
	 * @throws BusinessException
	 */
	boolean existeBeneficiarioDerecho(Long idBeneficiario,Long idCuentaNombrada,Long idDerechoCapital)throws BusinessException;
	
	/**
	 * Recupera el id de la cuenta nombrada del beneficiario en base al UOI y cuenta
	 * 
	 * @param idDerecho
	 * @param cuenta
	 * @param institucion
	 * @param idBeneficiario
	 * @return
	 * @throws BusinessException
	 */
	Long getCuentaDerechoByUoi(Long idDerecho,String cuenta,Institucion institucion,Long idBeneficiario)throws BusinessException;
	
	/**
	 * Agrega el beneficiario al derecho
	 * @param derechoBeneficiario
	 * @throws BusinessException
	 */
	void agregaBeneficiarioDerecho(DerechoBeneficiario derechoBeneficiario)throws BusinessException;
	
	/**
	 * Agrega una lista de beneficiarios al derecho
	 * @param lstBeneficiarios
	 * @throws BusinessException
	 */
	void agregaBeneficiariosDerecho(List<DerechoBeneficiario> lstBeneficiarios)throws BusinessException;
	
	/**
	 * Modifica la posicion asiganda a un beneficiario
	 * @param idBeneficiarioDerecho
	 * @param posicion
	 * @throws BusinessException
	 */
	void actualizaPosicionBeneficiario(Long idBeneficiarioDerecho,Long posicion)throws BusinessException;
	
	/**
	 * Consulta todas las cuentas asociadas a un derecho
	 * @param idDerecho
	 * @param paginaVO
	 * @return
	 * @throws BusinessException
	 */
	PaginaVO getCuentasDerecho(Long idDerecho,PaginaVO paginaVO)throws BusinessException;

	/**
	 * Consulta todas las cuentas asociadas a un derecho, este servicio usara metodos de persistencia sin usar
	 * entidades de hibernate.
	 * @param idDerecho
	 * @param paginaVO
	 * @return
	 * @throws BusinessException
	 */
	PaginaVO getCuentasDerechoOptimizado(Long idDerecho, PaginaVO paginaVO) throws BusinessException;
	
	/**
	 * Consulta todas las cuentas asociadas a un derecho y una institucion
	 * @param idDerecho
	 * @param idTipoInstitucion
	 * @param folioInstitucion
	 * @param paginaVO
	 * @return
	 * @throws BusinessException
	 */
	PaginaVO getCuentasDerecho(Long idDerecho,Integer idTipoInstitucion,String folioInstitucion,PaginaVO paginaVO)throws BusinessException;

	/**
	 * Consulta todas las cuentas asociadas a un derecho y una institucion, este servicio usara metodos de persistencia 
	 * sin usar entidades de hibernate.
	 * @param idDerecho
	 * @param idTipoInstitucion
	 * @param folioInstitucion
	 * @param paginaVO
	 * @return
	 * @throws BusinessException
	 */
	PaginaVO getCuentasDerechoOptimizado(Long idDerecho, Integer idTipoInstitucion, String folioInstitucion, PaginaVO paginaVO) 
	         throws BusinessException;
	
	/**
	 * Obtiene un beneficiario asociado al UOI y la cuenta nombrada
	 * @param uoi
	 * @param idCuentaNombrada
	 * @return
	 * @throws BusinessException
	 */
	Beneficiario getBeneficiarioCuentaByUoi(String uoi,Long idCuentaNombrada)throws BusinessException;
	
	/**
	 * Recupera el id de la cuenta nombrada del custodio de la emision
	 * @param idEmision
	 * @return
	 */
	Long getCuentaNombradaOfCustodio(Long idEmision);	

	/**
	 * Envia la simulacion del derecho a MAV.
	 * @param dto
	 * @throws BusinessException
	 */
	void enviarSimulacionDerechoCapitalInter(SimulacionDerechoInternacionalDto dto) throws BusinessException;

	/**
	 * Realiza una copia de los beneficiarios del derecho, actualiza es estado del derecho a PAGADO y actualiza el
	 * campo de PROCESADO a true
	 * 
	 * @param idDerecho
	 * @throws BusinessException
	 */
	void pagarDerecho(Long idDerecho)throws BusinessException;
	
	/**
	 * Actualiza el estado del derecho al estado pasado como parametro, esto es a los beneficiarios procesados o no procesados
	 * @param idDerecho
	 * @param idEstatusDerecho
	 * @param procesado
	 * @throws BusinessException
	 */
	void actualizarEstatusDerecho(Long idDerecho,Integer idEstatusDerecho,Boolean procesado)throws BusinessException;
	
	/**
	 * Verifica si al derecho se le fueron agregados beneficiarios, para no buscarlos en la tabla de T_DERECHO_CAPITAL
	 * @param idDerecho
	 * @return
	 * @throws BusinessException
	 */
	boolean existeDerechoNoCortado(Long idDerecho)throws BusinessException;
	
	/**
	 * Recupera el beneficiario del derecho agregado por default
	 * @param idDerecho
	 * @return
	 * @throws BusinessException
	 */
	DerechoBeneficiario getDerechoNoCortado(Long idDerecho)throws BusinessException;
	
	/**
	 * Valida si la emision requiere beneficiarios
	 * @param emisionVO
	 * @param idEmisionDerecho
	 * @return
	 */
	boolean requireBeneficiariosDerecho(EmisionVO emisionVO,Long idEmisionDerecho);

	/**
     * Metodo repetido con requireBeneficiariosDerecho(EmisionVO, Long), solo que este metodo obtiene un objeto 
     * de la tabla C_SIC_EMISIONES sin usar la entidad de hibernate SicEmision debido a que esta tiene varias 
     * relaciones con fetch type EAGER.
     * @param emisionVO 
     * @param idEmisionDerecho
     * @return
     */
	boolean requireBeneficiariosDerechoOptimizado(EmisionVO emisionVO, Long idEmisionDerecho);

	/**
	 * Recupera el horario para agregar beneficiarios a esta emision
	 * 
	 * @param emisionVO
	 * @param idEmisionDerecho
	 * @return
	 */
	HorarioBeneficiario getHorarioBenefDerecho(EmisionVO emisionVO,Long idEmisionDerecho);

	/**
	 * Metodo repetido con getHorarioBenefDerecho(EmisionVO, Long), solo que este metodo obtiene un objeto 
     * de la tabla C_SIC_EMISIONES sin usar la entidad de hibernate SicEmision debido a que esta tiene varias 
     * relaciones con fetch type EAGER.
	 * @param emisionVO
	 * @param idEmisionDerecho
	 * @return
	 */
	HorarioBeneficiario getHorarioBenefDerechoOptimizado(EmisionVO emisionVO, Long idEmisionDerecho);
	
	/**
	 * Recupera el horario especial de la emision para la institucion
	 * @param emisionVO
	 * @param idEmisionDerecho
	 * @param idInst
	 * @param folioInst
	 * @return
	 */
	HorarioBeneficiario getHorarioInstitucion(EmisionVO emisionVO,Long idEmisionDerecho,Integer idInst,String folioInst);

	/**
	 * Metodo repetido con getHorarioInstitucion(EmisionVO, Long), solo que este metodo obtiene un objeto 
     * de la tabla C_SIC_EMISIONES sin usar la entidad de hibernate SicEmision debido a que esta tiene varias 
     * relaciones con fetch type EAGER.
	 * @param emisionVO
	 * @param idEmisionDerecho
	 * @param idInst
	 * @param folioInst
	 * @return
	 */
	HorarioBeneficiario getHorarioInstitucionOptimizado(final EmisionVO emisionVO, final Long idEmisionDerecho, 
            final Integer idInst, final String folioInst);
	
	/**
	 * Recupera el porcentaje de retencion de default para la emision
	 * 
	 * @param emisionVO
	 * @param idEmisionDerecho
	 * @return
	 */
	Float getPorcentajeRetDefault(EmisionVO emisionVO,Long idEmisionDerecho);	

	/**
	 * Metodo repetido con getPorcentajeRetDefault(EmisionVO, Long), solo que este metodo obtiene un objeto 
     * de la tabla C_SIC_EMISIONES sin usar la entidad de hibernate SicEmision debido a que esta tiene varias 
     * relaciones con fetch type EAGER.
	 * @param emisionVO
	 * @param idEmisionDerecho
	 * @return
	 */
	Float getPorcentajeRetDefaultOptimizado(EmisionVO emisionVO, Long idEmisionDerecho);
	
	/**
	 * Inserta el beneficiario de default para el derecho
	 * 
	 * @param idDerecho
	 * @param idEstado
	 * @throws BusinessException
	 */
	void agregaBeneficiarioDefault(Long idDerecho,Integer idEstado)throws BusinessException;
	
	/**
	 * Valida si existe el beneficiario de default
	 * 
	 * @param idDerecho
	 * @return
	 */
	boolean existeBeneficiarioDefault(Long idDerecho);
	
	/**
	 * Obtiene el porcentaje de retencion del derecho
	 * 
	 * @param idDerechoCapital
	 * @return
	 */
	Float getPorcentajeRetFromDerecho(Long idDerechoCapital);	
	
	/**
	 * Recupera un derecho en base al tipo y subtipo de este
	 * 
	 * @param param
	 * @param tipo
	 * @param subtipo
	 * @return
	 * @throws BusinessException
	 */
	Derecho getDerecho(ConsultaDerechosParam param,int tipo,int subtipo)throws BusinessException;
	
	/**
	 * Recupera la cuenta nombrada perteneciente a la institucion
	 * 
	 * @param cuenta
	 * @param idTipoInst
	 * @param folioInstitucion
	 * @return
	 */
	CuentaNombrada getCuenta(String cuenta,Integer idTipoInst,String folioInstitucion);
	
	/**
	 * Guarda los valore porporcionados al derecho (proporcion,porcentaje de retencion y fee)
	 * 
	 * @param simulacionMav
	 */
	void guardaFactorSimulacion(FactorSimulacionMav simulacionMav);
	
	/**
	 * Recupera los valores guardados en el metodo de guardaFactorSimulacion
	 * 
	 * @param idDerechoCapital
	 * @return
	 */
	FactorSimulacionMav getFactorSimulacion(Long idDerechoCapital);

	/**
	 * Metodo para generar historico de capitales
	 * @param idDerecho
	 */
	public void generaMensajeHistoricoCapitales(Long idDerecho);
}
