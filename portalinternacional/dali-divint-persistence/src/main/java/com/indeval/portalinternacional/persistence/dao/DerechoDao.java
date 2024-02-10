package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Derecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.DerechoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistorico;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistoricoVo;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosParam;

public interface DerechoDao  extends BaseDao{

	/**
	 * Recupera todos los derechos de acuerdo a los filtros de param
	 * @param param
	 * @param paginaVO
	 * @return
	 */
	PaginaVO findDerechos(ConsultaDerechosParam param,PaginaVO paginaVO);

    /**
     * Recupera todos los derechos de acuerdo a los filtros de params y los asigna al parametro paginaVO.
     * @param params Los parametros para realizar la busqueda.
     * @param paginaVO El objeto a asignar el resultado.
     * @param all Discierne si se van a obtener todos los objetos (como para reportes y limitados por un maximo) o se esta 
     *            haciendo una consulta desde la vista.
     * @return El objeto paginaVO lleno.
     */
    PaginaVO findDerechosUsandoVista(ConsultaDerechosParam params, PaginaVO paginaVO, boolean all);
	
	/**
	 * Consulta los beneficiarios pertenecientes a las cuentas y a la institucion
	 * 
	 * @param idDerecho
	 * @param idCuentas
	 * @param tipoInstitucion
	 * @param folioInstitucion
	 * @return
	 */
	List<DerechoBeneficiario> findBenefDerechoXCuenta(Long idDerecho,Long[] idCuentas,Integer tipoInstitucion,String folioInstitucion);

	List<Object> getPosicionyPorcentajeDerechoXCuentaUsandoVista(final Long idDerecho, final Long[] idCuentas, final Integer tipoInstitucion, 
            final String folioInstitucion);
	
	
    /**
     * Consulta los beneficiarios pertenecientes a las cuentas y a la institucion, pero en este se usa una vista (sin usar entities
     * que tenian fetch tipo eager y se lanzaban demasiadas consultas adicionales), en esta se acumulan todos los campos necesarios 
     * para las consultas.
     * 
     * @param idDerecho
     * @param idCuentas
     * @param tipoInstitucion
     * @param folioInstitucion
     * @return
     */
    List<Object> findBenefDerechoXCuentaUsandoVista(Long idDerecho, Long[] idCuentas, Integer tipoInstitucion, String folioInstitucion);
	
	/**
	 * Recupera el beneficiario de acuerdo a su id
	 * @param idBeneficiarioDerecho
	 * @return
	 */
	DerechoBeneficiario getBeneficiarioDerecho(Long idBeneficiarioDerecho);
	
	/**
	 * Recupera el id de la cuenta nombrada del beneficiario en base al UOI y cuenta
	 * 
	 * @param idDerecho
	 * @param cuenta
	 * @param institucion
	 * @param idBeneficiario
	 * @return
	 */
	Long getCuentaDerechoByUoi(Long idDerecho,String cuenta,Institucion institucion,Long idBeneficiario);
	
	/**
	 * Recupera el beneficiario del derecho asociado a la cuenta
	 * @param idBeneficiario
	 * @param idCuentaNombrada
	 * @param idDerechoCapital
	 * @return
	 */
	DerechoBeneficiario getBeneficiarioDerecho(Long idBeneficiario,Long idCuentaNombrada,Long idDerechoCapital);
	
	/**
	 * Recupera las cuentas asociadas al derecho por institucion
	 * @param idDerechoCapital
	 * @param tipoInstitucion
	 * @param folioInstitucion
	 * @param paginaVO
	 * @return
	 */
	PaginaVO findCuentasDerecho(Long idDerechoCapital,Integer tipoInstitucion,String folioInstitucion,PaginaVO paginaVO);

	/**
	 * Recupera las cuentas asociadas al derecho por institucion sin usar entidades de hibernate.
	 * @param idDerechoCapital
	 * @param tipoInstitucion
	 * @param folioInstitucion
	 * @param paginaVO
	 * @return
	 */
	PaginaVO findCuentasDerechoWithoutEntities(Long idDerechoCapital, Integer tipoInstitucion, String folioInstitucion, PaginaVO paginaVO);
	
	/**
	 * Recupera el beneficiario por uoi y cuenta nombrada
	 * 
	 * @param uoi
	 * @param idCuentaNombrada
	 * @return
	 */
	Beneficiario findBeneficiarioCuentaByUoi(String uoi,Long idCuentaNombrada);
	
	/**
	 * Recupera los beneficiarios del derecho, ya sea procesados o no
	 * 
	 * @param idDerecho
	 * @param procesados
	 * @return
	 */
	List<DerechoBeneficiario> findBeneficiariosDerecho(Long idDerecho,Boolean procesados);
	
	/**
	 * Recupera el beneficiario del derecho si es que existe en internacional
	 * 
	 * @param idDerecho
	 * @return
	 */
	DerechoBeneficiario getDerechoNoCortado(Long idDerecho); 
	
	/**
	 * Recupera el beneficiario del derecho de default
	 * 
	 * @param idDerechoCapital
	 * @return
	 */
	DerechoBeneficiario getBenefDefaultDerecho(Long idDerechoCapital);
	
	/**
	 * Recupera el derecho de acuerdo al tipo y subtipo
	 * 
	 * @param param
	 * @param tipo
	 * @param subtipo
	 * @return
	 */
	Derecho findDerecho(ConsultaDerechosParam param, int tipo,int subtipo);
	
	/**
	 * Recupera la cuenta nombrada asociada a la institucion
	 * 
	 * @param cuenta
	 * @param idTipoInstitucion
	 * @param folioInstitucion
	 * @return
	 */
	CuentaNombrada findCuenta(String cuenta,Integer idTipoInstitucion,String folioInstitucion);
	
	/**
	 * Metodo para obtener Derecho Capital Historico
	 * @param idDerechoCapital
	 * @return
	 */
	List<DerechoCapitalHistoricoVo> consultaDerechoCapitalHistorico(Long idDerechoCapital);
	
	/**
	 * Metodo para eliminar Derecho Historico Tabla
	 * @param idDerechoCapital
	 * @return
	 */
	public Integer deleteDerechoHistoricoTabla(final Long idDerechoCapital);
	
	/**
	 * Metodo para persistir Derecho Capital Historico
	 * @param derechoCapitalHistotico
	 * @return
	 */
	public void saveDerechoCapitalHistorico(DerechoCapitalHistorico derechoCapitalHistotico);
}
