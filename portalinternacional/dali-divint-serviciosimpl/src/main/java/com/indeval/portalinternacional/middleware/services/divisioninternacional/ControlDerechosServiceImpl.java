package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.util.DTOAssemblerCapitales;
import com.indeval.portalinternacional.middleware.services.util.UtilDivIntService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Derecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.DerechoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExcepcionEmisionBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.FactorSimulacionMav;
import com.indeval.portalinternacional.middleware.servicios.modelo.GrupoRetencion;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.modelo.VConsultaBeneficiarioDerechosCuenta;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CamposW;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistorico;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistoricoVo;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NotificacionAccionCapitales;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NotificacionAccionCapitales.TipoAccion;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.SicEmisionVO;
import com.indeval.portalinternacional.middleware.servicios.vo.VConsultaDerechosCapitalVO;
import com.indeval.portalinternacional.persistence.dao.DerechoDao;
import com.indeval.portalinternacional.persistence.dao.ExcepcionEmisionBenefDao;
import com.indeval.portalinternacional.persistence.dao.FormatoW8Dao;
import com.indeval.portalinternacional.persistence.dao.GrupoRetencionDao;
import com.indeval.portalinternacional.persistence.dao.HorarioBeneficiarioDao;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;
import com.indeval.sidv.ejercicios.derechos.patrimoniales.dto.SimulacionDerechoInternacionalDto;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;

public class ControlDerechosServiceImpl implements ControlDerechosService {

	private static final Logger LOG = LoggerFactory.getLogger(ControlDerechosServiceImpl.class);

    private DerechoDao derechoDao;
    private DateUtilService dateUtilService;
    private UtilDivIntService utilDivIntService;
    private SicEmisionDao sicEmisionDao;
    private ExcepcionEmisionBenefDao excepcionEmisionBenefDao;
    private HorarioBeneficiarioDao horarioBeneficiarioDao;
    private GrupoRetencionDao grupoRetencionDao;
    private JmsTemplate jmsTemplatePagados;
    private FormatoW8Dao formatoW8Dao;

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService#getDerechosUsandoVista(ConsultaDerechosParam,
     *      PaginaVO, boolean)
     */
    public PaginaVO getDerechosUsandoVista(final ConsultaDerechosParam param, PaginaVO paginaVO,
            final boolean all) throws BusinessException {
        LOG.debug("####### En getDerechosUsandoVista()...");
        paginaVO = this.derechoDao.findDerechosUsandoVista(param, paginaVO, all);
        @SuppressWarnings("unchecked")
        List<Object> derechos = paginaVO.getRegistros();
        Iterator<Object> it = derechos.iterator();
        List<VConsultaDerechosCapitalVO> lstDer = new ArrayList<VConsultaDerechosCapitalVO>();
        while (it.hasNext()) {
            Object[] ob = (Object[]) it.next();
            VConsultaDerechosCapitalVO derecho = new VConsultaDerechosCapitalVO();
            derecho.setIdDerechoCapital((Long) ob[0]);
            derecho.setIdTipoDerecho((Integer) ob[1]);
            derecho.setDescTipoDerecho((String) ob[2]);
            derecho.setIdSubtipoDerecho((Long) ob[3]);
            derecho.setDescSubtipoDerecho((String) ob[4]);
            derecho.setClaveTipoValor((String) ob[5]);
            derecho.setClavePizarra((String) ob[6]);
            derecho.setSerie((String) ob[7]);
            derecho.setClaveCupon((String) ob[8]);
            derecho.setIsin((String) ob[9]);
            java.sql.Timestamp tmstmp = (java.sql.Timestamp) ob[10];
            Calendar fec = Calendar.getInstance();
            fec.setTimeInMillis(tmstmp.getTime());
            derecho.setFechaCorte(tmstmp != null ? fec.getTime() : null);
            tmstmp = (java.sql.Timestamp) ob[11];
            fec.setTimeInMillis(tmstmp.getTime());
            derecho.setFechaPago(tmstmp != null ? fec.getTime() : null);
            derecho.setProporcion((Float) ob[12]);
            derecho.setDivisa((String) ob[13]);
            derecho.setBovedaCustodio((String) ob[14]);
            derecho.setDescEstatusDerecho((String) ob[15]);
            derecho.setIdEmision((Long) ob[16]);
            derecho.setFee((Float) ob[17]);
            derecho.setIdEstatusDerecho((Long) ob[18]);
            derecho.setBovedaValoresDerecho((String) ob[19]);
            lstDer.add(derecho);
            derecho = null;
        }
        paginaVO.setRegistros(lstDer);

        return paginaVO;
    }

    public PaginaVO getDerechos(final ConsultaDerechosParam param, final PaginaVO paginaVO)
            throws BusinessException {
        return this.derechoDao.findDerechos(param, paginaVO);
    }

    public List<DerechoBeneficiario> getBenefDerechoXCuenta(final Long idDerecho,
            final Long[] idCuentas) throws BusinessException {
        return this.derechoDao.findBenefDerechoXCuenta(idDerecho, idCuentas, null, null);
    }

    
    public List<VConsultaBeneficiarioDerechosCuenta> getBenefDerechoXCuentaOptimizadoSimplificado(final Long idDerecho, final Long[] idCuentas,final Integer idTipoInstitucion, final String folioInstitucion)
    		throws BusinessException{
    	LOG.info("\n\n####### Entrando a getBenefDerechoXCuentaOptimizado("+idDerecho+",["+Arrays.toString(idCuentas)+"])");
    	List<VConsultaBeneficiarioDerechosCuenta> listadoBeneficiarios = null;
    	Long tiempoInicioConsulta=System.currentTimeMillis();
    	
    	List<Object> beneficiarios = this.derechoDao.getPosicionyPorcentajeDerechoXCuentaUsandoVista(idDerecho,idCuentas,idTipoInstitucion,folioInstitucion);
    	 LOG.info("Tiempo de consulta :"+(System.currentTimeMillis()-tiempoInicioConsulta)+" registros:"+beneficiarios.size());
    	if (beneficiarios != null && beneficiarios.size() > 0) {
    		listadoBeneficiarios = new ArrayList<VConsultaBeneficiarioDerechosCuenta>();
    		VConsultaBeneficiarioDerechosCuenta beneficiario =null;
    		Iterator<Object> it = beneficiarios.iterator();
            tiempoInicioConsulta=System.currentTimeMillis();
            while (it.hasNext()) {
                Object[] ob = (Object[]) it.next();
                beneficiario = new VConsultaBeneficiarioDerechosCuenta();
                beneficiario.setPosicion((Long) ob[0]);
                beneficiario.setPorcentajeRetencion((Double) ob[1]);
                beneficiario.setPorcentajeRetencionCustodio((Double) ob[2]);
                beneficiario.setIdCuentaNombrada((Long) ob[3]);
                if (beneficiario.getPorcentajeRetencion() == null || beneficiario.getPorcentajeRetencion() < 0) {
                    beneficiario.setPorcentajeRetencion(beneficiario.getPorcentajeRetencionCustodio());
                }
                listadoBeneficiarios.add(beneficiario);
                beneficiario = null;                
            }
    		
            LOG.info("Tiempo de transformacion de objetos :"+(System.currentTimeMillis()-tiempoInicioConsulta)+" registros:"+listadoBeneficiarios.size());
    		
    	}
    	
    	return listadoBeneficiarios;
    }
    
    
    
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService.getBenefDerechoXCuentaOptimizado(Long,Long[])
     */
    public List<VConsultaBeneficiarioDerechosCuenta> getBenefDerechoXCuentaOptimizado(final Long idDerecho, final Long[] idCuentas) 
            throws BusinessException {
        LOG.info("\n\n####### Entrando a getBenefDerechoXCuentaOptimizado("+idDerecho+",["+Arrays.toString(idCuentas)+"])");
        VConsultaBeneficiarioDerechosCuenta beneficiario = null;
        List<VConsultaBeneficiarioDerechosCuenta> listadoBeneficiarios = new ArrayList<VConsultaBeneficiarioDerechosCuenta>();
        Long tiempoInicioConsulta=System.currentTimeMillis();
        List<Object> beneficiarios = this.derechoDao.findBenefDerechoXCuentaUsandoVista(idDerecho, idCuentas, null, null);
        LOG.info("Tiempo de consulta :"+(System.currentTimeMillis()-tiempoInicioConsulta)+" registros:"+beneficiarios.size());
       
        if (beneficiarios != null && beneficiarios.size() > 0) {
            Iterator<Object> it = beneficiarios.iterator();
            tiempoInicioConsulta=System.currentTimeMillis();
            while (it.hasNext()) {
                Object[] ob = (Object[]) it.next();
                beneficiario = new VConsultaBeneficiarioDerechosCuenta();
                beneficiario.setCuenta((String) ob[0]);
                beneficiario.setIdCuentaNombrada((Long) ob[1]);
                beneficiario.setFolioInstitucion((String) ob[2]);
                beneficiario.setIdDerechoBeneficiario((Long) ob[3]);
                beneficiario.setIdDerechoCapital((Long) ob[4]);
                beneficiario.setNombreCompleto((String) ob[5]);
                beneficiario.setPorcentajeRetencion((Double) ob[6]);
                beneficiario.setPosicion((Long) ob[7]);
                beneficiario.setUoi((String) ob[8]);
                beneficiario.setAdpNumber((String) ob[9]);
                beneficiario.setTipoFormato((String) ob[10]);
                beneficiario.setDescTipoBeneficiario((String) ob[11]);
                beneficiario.setIdTipoInstitucion((Long) ob[12]);
                beneficiario.setIdCamposFormatoW8BEN((Long) ob[13]);
                beneficiario.setRfcFW8BEN((String) ob[14]);
                beneficiario.setIdCamposFormatoW8BENE((Long) ob[15]);
                beneficiario.setRfcFW8BENE((String) ob[16]);
                beneficiario.setIdDomicilioW8Normal((Long) ob[17]);
                beneficiario.setContryDomW8NORMAL((String) ob[18]);
                beneficiario.setStreetDomW8NORMAL((String) ob[19]);
                beneficiario.setOuternumberDomW8NORMAL((String) ob[20]);
                beneficiario.setInteriornumberDomW8NORMAL((String) ob[21]);
                beneficiario.setPostalcodeDomW8NORMAL((String) ob[22]);
                beneficiario.setCitytownDomW8NORMAL((String) ob[23]);
                beneficiario.setStateprovinceDomW8NORMAL((String) ob[24]);
                beneficiario.setIdCamposFormatoW9((Long) ob[25]);
                beneficiario.setRfcFW9((String) ob[26]);
                beneficiario.setIdDomicilioW9((Long) ob[27]);
                beneficiario.setContryDomW9((String) ob[28]);
                beneficiario.setStreetDomW9((String) ob[29]);
                beneficiario.setOuternumberDomW9((String) ob[30]);
                beneficiario.setInteriornumberDomW9((String) ob[31]);
                beneficiario.setPostalcodeDomW9((String) ob[32]);
                beneficiario.setCitytownDomW9((String) ob[33]);
                beneficiario.setStateprovinceDomW9((String) ob[34]);
                beneficiario.setIdCamposFormatoW8IMY((Long) ob[35]);
                beneficiario.setRfcFW8IMY((String) ob[36]);
                beneficiario.setIdCamposFormatoW8IMY2015((Long) ob[37]);
                beneficiario.setRfcFW8IMY2015((String) ob[38]);
                beneficiario.setContryDomW8NORMALDEW8IMY((String) ob[39]);
                beneficiario.setStreetDomW8NORMALDEW8IMY((String) ob[40]);
                beneficiario.setOutnumDomW8NORMALDEW8IMY((String) ob[41]);
                beneficiario.setIntnumDomW8NORMALDEW8IMY((String) ob[42]);
                beneficiario.setPoscodDomW8NORMALDEW8IMY((String) ob[43]);
                beneficiario.setCittowDomW8NORMALDEW8IMY((String) ob[44]);
                beneficiario.setStaproDomW8NORMALDEW8IMY((String) ob[45]);
                beneficiario.setIdCamposFormatoMILA((Long) ob[46]);
                beneficiario.setRfcFMILA((String) ob[47]);
                beneficiario.setRfcFMILANumeroDocumento((String) ob[48]);
                beneficiario.setIdDomicilioMILA((Long) ob[49]);
                beneficiario.setContryDomMILA((String) ob[50]);
                beneficiario.setStreetDomMILA((String) ob[51]);
                beneficiario.setOutnumberDomMILA((String) ob[52]);
                beneficiario.setInteriornumberDomMILA((String) ob[53]);
                beneficiario.setPostalcodeDomMILA((String) ob[54]);
                beneficiario.setCitytownDomMILA((String) ob[55]);
                beneficiario.setStateprovinceDomMILA((String) ob[56]);
                beneficiario.setPorcentajeRetencionCustodio((Double) ob[57]);
//                LOG.info("\n\n####### Cuenta de Beneficiario=[" + beneficiario.getIdCuentaNombrada() + "|" + beneficiario.getCuenta() + "]\n\n");
                if (beneficiario.getPorcentajeRetencion() == null || beneficiario.getPorcentajeRetencion() < 0) {
                    beneficiario.setPorcentajeRetencion(beneficiario.getPorcentajeRetencionCustodio());
                }
                listadoBeneficiarios.add(beneficiario);
                beneficiario = null;
            }
            
            LOG.info("Tiempo de transformacion de objetos :"+(System.currentTimeMillis()-tiempoInicioConsulta)+" registros:"+listadoBeneficiarios.size());
            
        }

        return listadoBeneficiarios;
    }

    public void setDerechoDao(final DerechoDao derechoDao) {
        this.derechoDao = derechoDao;
    }
    
	/**
	 * @param formatoW8Dao the formatoW8Dao to set
	 */
	public void setFormatoW8Dao(FormatoW8Dao formatoW8Dao) {
		this.formatoW8Dao = formatoW8Dao;
	}

    public List<DerechoBeneficiario> getBenefDerechoXCuenta(final Long idDerecho,
            final Long[] idCuentas, final Integer idTipoInstitucion, final String folioInstitucion)
            throws BusinessException {
        return this.derechoDao.findBenefDerechoXCuenta(idDerecho, idCuentas, idTipoInstitucion,
                folioInstitucion);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService.getBenefDerechoXCuentaOptimizado(Long,Long[],Integer,String)
     */
    public List<VConsultaBeneficiarioDerechosCuenta> getBenefDerechoXCuentaOptimizado(final Long idDerecho, final Long[] idCuentas, 
                                                                                      final Integer idTipoInstitucion, final String folioInstitucion)
            throws BusinessException {
        LOG.info("\n\n####### Entrando a getBenefDerechoXCuentaOptimizado()...\n\n");
        VConsultaBeneficiarioDerechosCuenta beneficiario = null;
        List<VConsultaBeneficiarioDerechosCuenta> listadoBeneficiarios = new ArrayList<VConsultaBeneficiarioDerechosCuenta>();

        List<Object> beneficiarios = this.derechoDao.findBenefDerechoXCuentaUsandoVista(idDerecho, idCuentas, idTipoInstitucion, folioInstitucion);
        if (beneficiarios != null && beneficiarios.size() > 0) {
            Iterator<Object> it = beneficiarios.iterator();
            while (it.hasNext()) {
                Object[] ob = (Object[]) it.next();
                beneficiario = new VConsultaBeneficiarioDerechosCuenta();
                beneficiario.setCuenta((String) ob[0]);
                beneficiario.setIdCuentaNombrada((Long) ob[1]);
                beneficiario.setFolioInstitucion((String) ob[2]);
                beneficiario.setIdDerechoBeneficiario((Long) ob[3]);
                beneficiario.setIdDerechoCapital((Long) ob[4]);
                beneficiario.setNombreCompleto((String) ob[5]);
                beneficiario.setPorcentajeRetencion((Double) ob[6]);
                beneficiario.setPosicion((Long) ob[7]);
                beneficiario.setUoi((String) ob[8]);
                beneficiario.setAdpNumber((String) ob[9]);
                beneficiario.setTipoFormato((String) ob[10]);
                beneficiario.setDescTipoBeneficiario((String) ob[11]);
                beneficiario.setIdTipoInstitucion((Long) ob[12]);
                beneficiario.setIdCamposFormatoW8BEN((Long) ob[13]);
                beneficiario.setRfcFW8BEN((String) ob[14]);
                beneficiario.setIdCamposFormatoW8BENE((Long) ob[15]);
                beneficiario.setRfcFW8BENE((String) ob[16]);
                beneficiario.setIdDomicilioW8Normal((Long) ob[17]);
                beneficiario.setContryDomW8NORMAL((String) ob[18]);
                beneficiario.setStreetDomW8NORMAL((String) ob[19]);
                beneficiario.setOuternumberDomW8NORMAL((String) ob[20]);
                beneficiario.setInteriornumberDomW8NORMAL((String) ob[21]);
                beneficiario.setPostalcodeDomW8NORMAL((String) ob[22]);
                beneficiario.setCitytownDomW8NORMAL((String) ob[23]);
                beneficiario.setStateprovinceDomW8NORMAL((String) ob[24]);
                beneficiario.setIdCamposFormatoW9((Long) ob[25]);
                beneficiario.setRfcFW9((String) ob[26]);
                beneficiario.setIdDomicilioW9((Long) ob[27]);
                beneficiario.setContryDomW9((String) ob[28]);
                beneficiario.setStreetDomW9((String) ob[29]);
                beneficiario.setOuternumberDomW9((String) ob[30]);
                beneficiario.setInteriornumberDomW9((String) ob[31]);
                beneficiario.setPostalcodeDomW9((String) ob[32]);
                beneficiario.setCitytownDomW9((String) ob[33]);
                beneficiario.setStateprovinceDomW9((String) ob[34]);
                beneficiario.setIdCamposFormatoW8IMY((Long) ob[35]);
                beneficiario.setRfcFW8IMY((String) ob[36]);
                beneficiario.setIdCamposFormatoW8IMY2015((Long) ob[37]);
                beneficiario.setRfcFW8IMY2015((String) ob[38]);
                beneficiario.setContryDomW8NORMALDEW8IMY((String) ob[39]);
                beneficiario.setStreetDomW8NORMALDEW8IMY((String) ob[40]);
                beneficiario.setOutnumDomW8NORMALDEW8IMY((String) ob[41]);
                beneficiario.setIntnumDomW8NORMALDEW8IMY((String) ob[42]);
                beneficiario.setPoscodDomW8NORMALDEW8IMY((String) ob[43]);
                beneficiario.setCittowDomW8NORMALDEW8IMY((String) ob[44]);
                beneficiario.setStaproDomW8NORMALDEW8IMY((String) ob[45]);
                beneficiario.setIdCamposFormatoMILA((Long) ob[46]);
                beneficiario.setRfcFMILA((String) ob[47]);
                beneficiario.setRfcFMILANumeroDocumento((String) ob[48]);
                beneficiario.setIdDomicilioMILA((Long) ob[49]);
                beneficiario.setContryDomMILA((String) ob[50]);
                beneficiario.setStreetDomMILA((String) ob[51]);
                beneficiario.setOutnumberDomMILA((String) ob[52]);
                beneficiario.setInteriornumberDomMILA((String) ob[53]);
                beneficiario.setPostalcodeDomMILA((String) ob[54]);
                beneficiario.setCitytownDomMILA((String) ob[55]);
                beneficiario.setStateprovinceDomMILA((String) ob[56]);
                beneficiario.setPorcentajeRetencionCustodio((Double) ob[57]);
                if (beneficiario.getPorcentajeRetencion() == null || beneficiario.getPorcentajeRetencion() < 0) {
                    beneficiario.setPorcentajeRetencion(beneficiario.getPorcentajeRetencionCustodio());
                }
                listadoBeneficiarios.add(beneficiario);
                beneficiario = null;
            }
        }

        return listadoBeneficiarios;
    }

    public void deleteBeneficiarioDerecho(final Long idBeneficiarioDerecho)
            throws BusinessException {
        LOG.debug("buscando el beneficiario a borrar logicamente...");
        DerechoBeneficiario derechoBeneficiario =
                this.derechoDao.getBeneficiarioDerecho(idBeneficiarioDerecho);

        if (derechoBeneficiario != null) {
            LOG.debug("beneficiario recuperado [" + derechoBeneficiario.getIdDerechoBeneficiario()
                    + "]");
            derechoBeneficiario.setEliminado(Boolean.TRUE);
            derechoBeneficiario.setFechaEliminacion(this.dateUtilService.getCurrentDate());
            LOG.info("Actualizando beneficiario....");
            this.derechoDao.update(derechoBeneficiario);
            LOG.info("Beneficiario actualizado");
        }
    }

    public void setDateUtilService(final DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

    public boolean existeBeneficiarioDerecho(final Long idBeneficiario,
            final Long idCuentaNombrada, final Long idDerechoCapital) throws BusinessException {
        DerechoBeneficiario derechoBeneficiario = null;
        boolean existeBeneficiario = false;
        derechoBeneficiario =
                this.derechoDao.getBeneficiarioDerecho(idBeneficiario, idCuentaNombrada,
                        idDerechoCapital);
        if (derechoBeneficiario != null) {
            existeBeneficiario = true;
        }
        return existeBeneficiario;
    }

    public Long getCuentaDerechoByUoi(final Long idDerecho, final String cuenta,
            final Institucion institucion, final Long idBeneficiario) throws BusinessException {
        return this.derechoDao
                .getCuentaDerechoByUoi(idDerecho, cuenta, institucion, idBeneficiario);
    }

    public void agregaBeneficiarioDerecho(final DerechoBeneficiario derechoBeneficiario)
            throws BusinessException {
        DerechoBeneficiario benefExistente = null;
        if (derechoBeneficiario.getIdDerechoBeneficiario() == null) {
            derechoBeneficiario.setEliminado(Boolean.FALSE);
            this.derechoDao.save(derechoBeneficiario);
        } else {
            benefExistente = (DerechoBeneficiario) this.derechoDao.getByPk(DerechoBeneficiario.class, derechoBeneficiario.getIdDerechoBeneficiario());
            if (benefExistente.getAsignacion().longValue() != derechoBeneficiario.getAsignacion().longValue()) {
                benefExistente.setAsignacion(derechoBeneficiario.getAsignacion());
                this.derechoDao.update(benefExistente);
            }
        }
    }

    public void actualizaPosicionBeneficiario(final Long idBeneficiarioDerecho, final Long posicion)
            throws BusinessException {

        DerechoBeneficiario beneficiario =
                (DerechoBeneficiario) this.derechoDao.getByPk(DerechoBeneficiario.class,
                        idBeneficiarioDerecho);
        if (beneficiario != null) {
            beneficiario.setAsignacion(posicion);
            this.derechoDao.update(beneficiario);
        }

    }

    public PaginaVO getCuentasDerecho(final Long idDerecho, final PaginaVO paginaVO)
            throws BusinessException {
        return this.derechoDao.findCuentasDerecho(idDerecho, null, null, paginaVO);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService.getCuentasDerechoOptimizado(Long,PaginaVO)
     */
    public PaginaVO getCuentasDerechoOptimizado(final Long idDerecho, final PaginaVO paginaVO) throws BusinessException {
        return this.derechoDao.findCuentasDerechoWithoutEntities(idDerecho, null, null, paginaVO);
    }

    public PaginaVO getCuentasDerecho(final Long idDerecho, final Integer idTipoInstitucion,
            final String folioInstitucion, final PaginaVO paginaVO) throws BusinessException {
        return this.derechoDao.findCuentasDerecho(idDerecho, idTipoInstitucion, folioInstitucion,
                paginaVO);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService.getCuentasDerechoOptimizado(Long,Integer,String,PaginaVO)
     */
    public PaginaVO getCuentasDerechoOptimizado(final Long idDerecho, final Integer idTipoInstitucion, final String folioInstitucion, 
                                                final PaginaVO paginaVO) throws BusinessException {
        return this.derechoDao.findCuentasDerechoWithoutEntities(idDerecho, idTipoInstitucion, folioInstitucion, paginaVO);
    }

    public Beneficiario getBeneficiarioCuentaByUoi(final String uoi, final Long idCuentaNombrada)
            throws BusinessException {
        return this.derechoDao.findBeneficiarioCuentaByUoi(uoi, idCuentaNombrada);
    }

    public void agregaBeneficiariosDerecho(final List<DerechoBeneficiario> lstBeneficiarios)
            throws BusinessException {
        if (lstBeneficiarios != null && !lstBeneficiarios.isEmpty()) {
            for (DerechoBeneficiario derechoBeneficiario : lstBeneficiarios) {
                this.agregaBeneficiarioDerecho(derechoBeneficiario);
            }
        }

    }

    public Long getCuentaNombradaOfCustodio(final Long idEmision) {
        return this.utilDivIntService.getCuentaNombradaOfCustodio(idEmision);
    }

    public void setUtilDivIntService(final UtilDivIntService utilDivIntService) {
        this.utilDivIntService = utilDivIntService;
    }

    public void enviarSimulacionDerechoCapitalInter(SimulacionDerechoInternacionalDto dto) throws BusinessException {
        //Sin implementacion pues del controller ConsultaTablaRetencionController se llama directo al ejb 
        //ControlDerechosServiceBean
    }

    /**
     * Actualizar estado.
     * Al actualizar el estado se manda un mensaje al moi-capitales apra que genere el registro
     * en historico de capitales
     */
    public void actualizarEstatusDerecho(final Long idDerecho, final Integer idEstatusDerecho,
            final Boolean procesado) throws BusinessException {

        this.actualizarEstatusDerechoBeneficiario(idDerecho, idEstatusDerecho, procesado);
        this.derechoDao.flush();
/*        if(Constantes.ID_ESTATUS_DERECHO_CONFIRMADO.equals(idEstatusDerecho)
                || Constantes.ID_ESTATUS_DERECHO_AUTORIZADO.equals(idEstatusDerecho)
                || Constantes.ID_ESTATUS_DERECHO_PREVIO.equals(idEstatusDerecho)){
//            this.generaMensajeHistoricoCapitales(idDerecho);
        } */
    }
    
    /**
     * cambia el estado del derecho
     * @param idDerecho
     * @param idEstatusDerecho
     * @param procesado
     * @throws BusinessException
     */
    public void actualizarEstatusDerechoBeneficiario(final Long idDerecho, final Integer idEstatusDerecho,
            final Boolean procesado) {
        List<DerechoBeneficiario> lstBeneficiarios = null;
        EstatusDerecho estatusDerecho = null;

        lstBeneficiarios = this.derechoDao.findBeneficiariosDerecho(idDerecho, procesado);
        if (lstBeneficiarios != null && !lstBeneficiarios.isEmpty()) {
            estatusDerecho =
                    (EstatusDerecho) this.derechoDao
                            .getByPk(EstatusDerecho.class, idEstatusDerecho);
            for (DerechoBeneficiario beneficiario : lstBeneficiarios) {
                beneficiario.setEstatusDerecho(estatusDerecho);
                this.derechoDao.update(beneficiario);            		
            }
        }
        this.derechoDao.flush();
    }

    /**
     * Pagar Derecho Capital a sus beneficiarios, al final manda un mensaje al moi para que genere
     * el historico de capitales
     */
    public void pagarDerecho(final Long idDerecho) {
    	LOG.debug("Entrando a pagarDerecho");
        List<DerechoBeneficiario> lstBeneficiarios = null;

        lstBeneficiarios = this.derechoDao.findBeneficiariosDerecho(idDerecho, Boolean.FALSE);
        if (lstBeneficiarios != null && !lstBeneficiarios.isEmpty()) {
        	/** Agrego los beneficiario **/
        	this.pagarBeneficiariosDerecho(lstBeneficiarios);
        	this.procesaBeneficiariosDerecho(lstBeneficiarios);
        	this.derechoDao.flush();
//        	this.generaMensajeHistoricoCapitales(idDerecho);
        }
    }
    
    /**
     * @param lstBeneficiarios
     */
    public void pagarBeneficiariosDerecho(final List<DerechoBeneficiario> lstBeneficiarios) {
    	LOG.debug("Entrando a pagarBeneficiariosDerecho");
        EstatusDerecho estatusDerecho = new EstatusDerecho();
        estatusDerecho.setIdEstatusDerecho(Constantes.ID_ESTATUS_DERECHO_PAGADO);
        estatusDerecho.setDescEstatusDerecho(Constantes.ESTATUS_DERECHO_PAGADO);
        for (DerechoBeneficiario beneficiario : lstBeneficiarios) {
            try {
            	DerechoBeneficiario beneficiarioSave = new DerechoBeneficiario();
            	beneficiarioSave.setIdDerechoCapital(beneficiario.getIdDerechoCapital());
            	beneficiarioSave.setCuentaNombrada(beneficiario.getCuentaNombrada());
            	beneficiarioSave.setAsignacion(beneficiario.getAsignacion());
            	beneficiarioSave.setEstatusDerecho(estatusDerecho);            	
            	beneficiarioSave.setFechaEliminacion(beneficiario.getFechaEliminacion());
            	beneficiarioSave.setEliminado(beneficiario.getEliminado());
            	beneficiarioSave.setBeneficiario(beneficiario.getBeneficiario());
            	beneficiarioSave.setProcesado(Boolean.TRUE);
                this.derechoDao.save(beneficiarioSave);
                LOG.debug("## Derecho save success!!");    
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }
        this.derechoDao.flush();
    }
    
    /**
     * @param lstBeneficiarios
     */
    public void procesaBeneficiariosDerecho(final List<DerechoBeneficiario> lstBeneficiarios) {
    	LOG.debug("Entrando a procesaBeneficiariosDerecho");
        EstatusDerecho estatusDerecho = new EstatusDerecho();
        estatusDerecho.setIdEstatusDerecho(Constantes.ID_ESTATUS_DERECHO_PAGADO);
        estatusDerecho.setDescEstatusDerecho(Constantes.ESTATUS_DERECHO_PAGADO);
        for (DerechoBeneficiario beneficiario : lstBeneficiarios) {
            try {
            	if(beneficiario.getIdDerechoBeneficiario() != null){
                    beneficiario.setEstatusDerecho(estatusDerecho);
                    this.derechoDao.update(beneficiario);
            		LOG.debug("Se actualiza DerechoBeneficiario");
            	} else {
                	DerechoBeneficiario beneficiarioSave = new DerechoBeneficiario();
                	beneficiarioSave.setIdDerechoCapital(beneficiario.getIdDerechoCapital());
                	beneficiarioSave.setCuentaNombrada(beneficiario.getCuentaNombrada());
                	beneficiarioSave.setAsignacion(beneficiario.getAsignacion());
                	beneficiarioSave.setEstatusDerecho(estatusDerecho);            	
                	beneficiarioSave.setFechaEliminacion(beneficiario.getFechaEliminacion());
                	beneficiarioSave.setEliminado(beneficiario.getEliminado());
                	beneficiarioSave.setBeneficiario(beneficiario.getBeneficiario());
                	beneficiarioSave.setProcesado(Boolean.FALSE);
            		this.derechoDao.save(beneficiario);
            		LOG.debug("Se almacena DerechoBeneficiario");            		
            	}
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }
        this.derechoDao.flush();
    }

    /**
     * @param idDerecho
     */
    public void generaMensajeHistoricoCapitales(final Long idDerecho) {
        // ya termino de pagar, manda mensaje al moi para generar historico
        try {
            LOG.debug("=================Mandando Mensaje de Generacion de Capital Historico al MOI ============");
//          this.generarHistoricoCapitalesPagados(idDerecho);
          this.generaDetalleCapitales(idDerecho);
        } catch (Exception e) {
            LOG.error(
                    "NO SE PUDO MANDAR el mensaje AL MOI DERECHO CAPITALES PAGADOS con idDerechoCapital: "
                            + idDerecho, e);
        }
    }

    private void generarHistoricoCapitalesPagados(final Long idDerecho) {
        XStream xstream = new XStream();
        Annotations.configureAliases(xstream, NotificacionAccionCapitales.class);

        NotificacionAccionCapitales der = new NotificacionAccionCapitales();
        der.setAccion(TipoAccion.DERECHO_CAPITAL_PAGADO);
        der.setIdDerechoCapital(idDerecho);

        final String msgCapitalPagadoXml = xstream.toXML(der);

        LOG.debug("XMLCapitalPagado====>" + msgCapitalPagadoXml);

        this.jmsTemplatePagados.send(new MessageCreator() {
            public Message createMessage(final Session session) throws JMSException {
                final Message msg = session.createTextMessage(msgCapitalPagadoXml);
                return msg;
            }
        });
    }

    public boolean existeDerechoNoCortado(final Long idDerecho) throws BusinessException {
        boolean existeDerechoNoCortado = false;

        if (this.derechoDao.getDerechoNoCortado(idDerecho) != null) {
            existeDerechoNoCortado = true;
        }

        return existeDerechoNoCortado;
    }

    public boolean requireBeneficiariosDerecho(final EmisionVO emisionVO,
            final Long idEmisionDerecho) {
        int camposValidos = 0;
        boolean requiereBeneficiarios = true;
        SicEmision emision = null;
        List<ExcepcionEmisionBenef> lstExcepciones = null;
        int camposValidar = 4;

        if (emisionVO != null && idEmisionDerecho != null) {
            emision = this.sicEmisionDao.findSicEmisionByIdEmision(idEmisionDerecho);
            if (emision != null && emision.getCuentaNombrada() != null
                    && emision.getCuentaNombrada().getIdCuentaNombrada() != null) {
                lstExcepciones =
                        this.excepcionEmisionBenefDao.findExecepcionesEmision(emision
                                .getCuentaNombrada().getIdCuentaNombrada());
            }
            if (lstExcepciones != null && !lstExcepciones.isEmpty()) {
                for (ExcepcionEmisionBenef emisionBenef : lstExcepciones) {
                    if (StringUtils.isBlank(emisionBenef.getTv())
                            && StringUtils.isBlank(emisionBenef.getEmisora())
                            && StringUtils.isBlank(emisionBenef.getSerie())
                            && StringUtils.isBlank(emisionBenef.getIsin())) {
                        continue;
                    }
                    if (StringUtils.isBlank(emisionBenef.getTv())
                            || emisionBenef.getTv().equals(emisionVO.getTv())) {
                        camposValidos++;
                    }
                    if (StringUtils.isBlank(emisionBenef.getEmisora())
                            || emisionBenef.getEmisora().equals(emisionVO.getEmisora())) {
                        camposValidos++;
                    }
                    if (StringUtils.isBlank(emisionBenef.getSerie())
                            || emisionBenef.getSerie().equals(emisionVO.getSerie())) {
                        camposValidos++;
                    }
                    if (StringUtils.isBlank(emisionBenef.getIsin())
                            || emisionBenef.getIsin().equals(emisionVO.getIsin())) {
                        camposValidos++;
                    }
                    if (camposValidos == camposValidar) {
                        requiereBeneficiarios = false;
                        break;
                    } else {
                        camposValidos = 0;
                    }
                }
            }
        }

        return requiereBeneficiarios;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService.requireBeneficiariosDerechoOptimizado(EmisionVO, Long)
     */
    public boolean requireBeneficiariosDerechoOptimizado(final EmisionVO emisionVO, final Long idEmisionDerecho) {
        LOG.info("\n\n####### Entrando a requireBeneficiariosDerechoOptimizado()...\n\n");
        int camposValidos = 0;
        boolean requiereBeneficiarios = true;
        SicEmisionVO sicEmisionVO = null;
        List<ExcepcionEmisionBenef> lstExcepciones = null;
        int camposValidar = 4;

        if (emisionVO != null && idEmisionDerecho != null) {
            sicEmisionVO = this.sicEmisionDao.findSicEmisionByIdEmisionWithoutEntities(idEmisionDerecho);
            if (sicEmisionVO != null && sicEmisionVO.getIdCuentaNombrada() != null) {
                lstExcepciones = this.excepcionEmisionBenefDao.findExecepcionesEmision(sicEmisionVO.getIdCuentaNombrada().longValue());
            }
            if (lstExcepciones != null && !lstExcepciones.isEmpty()) {
                for (ExcepcionEmisionBenef emisionBenef : lstExcepciones) {
                    if (StringUtils.isBlank(emisionBenef.getTv()) && 
                        StringUtils.isBlank(emisionBenef.getEmisora()) && 
                        StringUtils.isBlank(emisionBenef.getSerie()) && 
                        StringUtils.isBlank(emisionBenef.getIsin())) {
                        continue;
                    }
                    if (StringUtils.isBlank(emisionBenef.getTv()) || emisionBenef.getTv().equals(emisionVO.getTv())) {
                        camposValidos++;
                    }
                    if (StringUtils.isBlank(emisionBenef.getEmisora()) || emisionBenef.getEmisora().equals(emisionVO.getEmisora())) {
                        camposValidos++;
                    }
                    if (StringUtils.isBlank(emisionBenef.getSerie()) || emisionBenef.getSerie().equals(emisionVO.getSerie())) {
                        camposValidos++;
                    }
                    if (StringUtils.isBlank(emisionBenef.getIsin()) || emisionBenef.getIsin().equals(emisionVO.getIsin())) {
                        camposValidos++;
                    }
                    if (camposValidos == camposValidar) {
                        requiereBeneficiarios = false;
                        break;
                    }
                    else {
                        camposValidos = 0;
                    }
                }
            }
        }

        return requiereBeneficiarios;
    }

    public HorarioBeneficiario getHorarioBenefDerecho(final EmisionVO emisionVO,
            final Long idEmisionDerecho) {
        SicEmision emision = null;
        int camposValidos = 0;
        int camposValidar = 3;
        HorarioBeneficiario horarioBeneficiarioTmp = null;
        HorarioBeneficiario horarioDefault = null;
        List<HorarioBeneficiario> horarios = null;

        if (emisionVO != null && idEmisionDerecho != null) {
            emision = this.sicEmisionDao.findSicEmisionByIdEmision(idEmisionDerecho);
            if (emision != null && emision.getCuentaNombrada() != null
                    && emision.getCuentaNombrada().getIdCuentaNombrada() != null) {
                horarios =
                        this.horarioBeneficiarioDao.findHorariosDerecho(emision.getCuentaNombrada()
                                .getIdCuentaNombrada());
                if (horarios != null && !horarios.isEmpty()) {
                    for (HorarioBeneficiario horarioBeneficiario : horarios) {
                        if (horarioBeneficiario.getPorcentajeRet() != null
                                && StringUtils.isBlank(horarioBeneficiario.getTv())
                                && StringUtils.isBlank(horarioBeneficiario.getEmisora())
                                && StringUtils.isBlank(horarioBeneficiario.getSerie())) {
                            horarioDefault = horarioBeneficiario;
                            continue;
                        }
                        if (StringUtils.isBlank(horarioBeneficiario.getTv())
                                || horarioBeneficiario.getTv().equals(emisionVO.getTv())) {
                            camposValidos++;
                        }
                        if (StringUtils.isBlank(horarioBeneficiario.getEmisora())
                                || horarioBeneficiario.getEmisora().equals(emisionVO.getEmisora())) {
                            camposValidos++;
                        }
                        if (StringUtils.isBlank(horarioBeneficiario.getSerie())
                                || horarioBeneficiario.getSerie().equals(emisionVO.getSerie())) {
                            camposValidos++;
                        }

                        if (camposValidos == camposValidar) {
                            horarioBeneficiarioTmp = horarioBeneficiario;
                            break;
                        } else {
                            camposValidos = 0;
                        }
                    }
                    if (horarioBeneficiarioTmp == null) {
                        horarioBeneficiarioTmp = horarioDefault;
                    }
                }
            }
        }

        return horarioBeneficiarioTmp;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService.getHorarioBenefDerechoOptimizado(EmisionVO, Long)
     */
    public HorarioBeneficiario getHorarioBenefDerechoOptimizado(final EmisionVO emisionVO, final Long idEmisionDerecho) {
        LOG.info("\n\n####### Entrando a getHorarioBenefDerechoOptimizado()...\n\n");
        SicEmisionVO sicEmisionVO = null;
        int camposValidos = 0;
        int camposValidar = 3;
        HorarioBeneficiario horarioBeneficiarioTmp = null;
        HorarioBeneficiario horarioDefault = null;
        List<HorarioBeneficiario> horarios = null;

        if (emisionVO != null && idEmisionDerecho != null) {
            sicEmisionVO = this.sicEmisionDao.findSicEmisionByIdEmisionWithoutEntities(idEmisionDerecho);
            if (sicEmisionVO != null && sicEmisionVO.getIdCuentaNombrada() != null) {
                horarios = this.horarioBeneficiarioDao.findHorariosDerecho(sicEmisionVO.getIdCuentaNombrada().longValue());
                if (horarios != null && !horarios.isEmpty()) {
                    for (HorarioBeneficiario horarioBeneficiario : horarios) {
                        if (horarioBeneficiario.getPorcentajeRet() != null && 
                            StringUtils.isBlank(horarioBeneficiario.getTv()) && 
                            StringUtils.isBlank(horarioBeneficiario.getEmisora()) && 
                            StringUtils.isBlank(horarioBeneficiario.getSerie())) {
                            horarioDefault = horarioBeneficiario;
                            continue;
                        }
                        if (StringUtils.isBlank(horarioBeneficiario.getTv()) || 
                            horarioBeneficiario.getTv().equals(emisionVO.getTv())) {
                            camposValidos++;
                        }
                        if (StringUtils.isBlank(horarioBeneficiario.getEmisora()) || 
                            horarioBeneficiario.getEmisora().equals(emisionVO.getEmisora())) {
                            camposValidos++;
                        }
                        if (StringUtils.isBlank(horarioBeneficiario.getSerie()) || 
                            horarioBeneficiario.getSerie().equals(emisionVO.getSerie())) {
                            camposValidos++;
                        }

                        if (camposValidos == camposValidar) {
                            horarioBeneficiarioTmp = horarioBeneficiario;
                            break;
                        } 
                        else {
                            camposValidos = 0;
                        }
                    }
                    if (horarioBeneficiarioTmp == null) {
                        horarioBeneficiarioTmp = horarioDefault;
                    }
                }
            }
        }

        return horarioBeneficiarioTmp;
    }


    public HorarioBeneficiario getHorarioInstitucion(final EmisionVO emisionVO,
            final Long idEmisionDerecho, final Integer idInst, final String folioInst) {
        SicEmision emision = null;
        HorarioBeneficiario horarioInst = null;
        if (emisionVO != null && idEmisionDerecho != null && idInst != null
                && !StringUtils.isBlank(folioInst)) {
            emision = this.sicEmisionDao.findSicEmisionByIdEmision(idEmisionDerecho);
            if (emision != null && emision.getCuentaNombrada() != null
                    && emision.getCuentaNombrada().getIdCuentaNombrada() != null) {
                horarioInst =
                        this.horarioBeneficiarioDao.findHorarioInstitucion(emision
                                .getCuentaNombrada().getIdCuentaNombrada(), idInst, folioInst);
            }
        }

        return horarioInst;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService.getHorarioInstitucionOptimizado(EmisionVO,Long,Integer,String)
     */
    public HorarioBeneficiario getHorarioInstitucionOptimizado(final EmisionVO emisionVO, final Long idEmisionDerecho, 
                                                               final Integer idInst, final String folioInst) {
        LOG.info("\n\n####### Entrando a getHorarioInstitucionOptimizado()...\n\n");
        SicEmisionVO sicEmisionVO = null;
        HorarioBeneficiario horarioInst = null;
        if (emisionVO != null && idEmisionDerecho != null && idInst != null && !StringUtils.isBlank(folioInst)) {
            sicEmisionVO = this.sicEmisionDao.findSicEmisionByIdEmisionWithoutEntities(idEmisionDerecho);
            if (sicEmisionVO != null && sicEmisionVO.getIdCuentaNombrada() != null) {
                horarioInst = this.horarioBeneficiarioDao.findHorarioInstitucion(sicEmisionVO.getIdCuentaNombrada(), 
                                                                                 idInst, folioInst);
            }
        }

        return horarioInst;
    }

    public Float getPorcentajeRetDefault(final EmisionVO emisionVO, final Long idEmisionDerecho) {
        SicEmision emision = null;
        List<HorarioBeneficiario> horarios = null;
        Float porcentajeRetDefault = null;

        if (emisionVO != null && idEmisionDerecho != null) {
            emision = this.sicEmisionDao.findSicEmisionByIdEmision(idEmisionDerecho);
            if (emision != null && emision.getCuentaNombrada() != null
                    && emision.getCuentaNombrada().getIdCuentaNombrada() != null) {
                horarios =
                        this.horarioBeneficiarioDao.findHorariosDerecho(emision.getCuentaNombrada()
                                .getIdCuentaNombrada());
                if (horarios != null && !horarios.isEmpty()) {
                    for (HorarioBeneficiario horarioBeneficiario : horarios) {
                        if (horarioBeneficiario.getPorcentajeRet() != null
                                && StringUtils.isBlank(horarioBeneficiario.getTv())
                                && StringUtils.isBlank(horarioBeneficiario.getEmisora())
                                && StringUtils.isBlank(horarioBeneficiario.getSerie())) {
                            porcentajeRetDefault = horarioBeneficiario.getPorcentajeRet();
                            break;
                        }
                    }
                }
            }
        }

        return porcentajeRetDefault;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService.getPorcentajeRetDefaultOptimizado(EmisionVO, Long)
     */
    public Float getPorcentajeRetDefaultOptimizado(final EmisionVO emisionVO, final Long idEmisionDerecho) {
        LOG.info("\n\n####### Entrando a getPorcentajeRetDefaultOptimizado()...\n\n");
        SicEmisionVO sicEmisionVO = null;
        List<HorarioBeneficiario> horarios = null;
        Float porcentajeRetDefault = null;

        if (emisionVO != null && idEmisionDerecho != null) {
            sicEmisionVO = this.sicEmisionDao.findSicEmisionByIdEmisionWithoutEntities(idEmisionDerecho);
            if (sicEmisionVO != null && sicEmisionVO.getIdCuentaNombrada() != null) {
                horarios = this.horarioBeneficiarioDao.findHorariosDerecho(sicEmisionVO.getIdCuentaNombrada());
                if (horarios != null && !horarios.isEmpty()) {
                    for (HorarioBeneficiario horarioBeneficiario : horarios) {
                        if (horarioBeneficiario.getPorcentajeRet() != null && 
                            StringUtils.isBlank(horarioBeneficiario.getTv()) && 
                            StringUtils.isBlank(horarioBeneficiario.getEmisora()) && 
                            StringUtils.isBlank(horarioBeneficiario.getSerie())) {
//                            LOG.info("\n\n####### porcentajeRetDefault de horario=[" + porcentajeRetDefault + "]\n\n");
                            porcentajeRetDefault = horarioBeneficiario.getPorcentajeRet();
                            break;
                        }
                    }
                }
            }
        }

        return porcentajeRetDefault;
    }


    public void agregaBeneficiarioDefault(final Long idDerecho, final Integer idEstado)
            throws BusinessException {
        DerechoBeneficiario derechoBeneficiario = new DerechoBeneficiario();
        EstatusDerecho estatusDerecho = new EstatusDerecho();
        derechoBeneficiario.setEliminado(Boolean.FALSE);
        estatusDerecho.setIdEstatusDerecho(idEstado);
        derechoBeneficiario.setEstatusDerecho(estatusDerecho);
        derechoBeneficiario.setIdDerechoCapital(idDerecho);
        derechoBeneficiario.setProcesado(Boolean.FALSE);
        this.derechoDao.save(derechoBeneficiario);
        this.derechoDao.flush();
    }

    public boolean existeBeneficiarioDefault(final Long idDerecho) {
        boolean existeBeneficiario = false;
        DerechoBeneficiario beneficiario = this.derechoDao.getBenefDefaultDerecho(idDerecho);

        if (beneficiario != null) {
            existeBeneficiario = true;
        }
        return existeBeneficiario;
    }

    public Float getPorcentajeRetFromDerecho(final Long idDerechoCapital) {
        List<GrupoRetencion> lstGruposRetencion = null;
        Float porcentaje = null;

        lstGruposRetencion = this.grupoRetencionDao.getGruposRetencionByIdDerecho(idDerechoCapital);
        if (lstGruposRetencion != null && !lstGruposRetencion.isEmpty()) {
            porcentaje = lstGruposRetencion.get(0).getPorcentajeRetencion();
        } else {
            porcentaje = new Float(0);
        }

        return porcentaje;
    }


    public Derecho getDerecho(final ConsultaDerechosParam param, final int tipo, final int subtipo)
            throws BusinessException {
        return this.derechoDao.findDerecho(param, tipo, subtipo);
    }

    public void setSicEmisionDao(final SicEmisionDao sicEmisionDao) {
        this.sicEmisionDao = sicEmisionDao;
    }

    public void setExcepcionEmisionBenefDao(final ExcepcionEmisionBenefDao excepcionEmisionBenefDao) {
        this.excepcionEmisionBenefDao = excepcionEmisionBenefDao;
    }

    public void setHorarioBeneficiarioDao(final HorarioBeneficiarioDao horarioBeneficiarioDao) {
        this.horarioBeneficiarioDao = horarioBeneficiarioDao;
    }

    public void setGrupoRetencionDao(final GrupoRetencionDao grupoRetencionDao) {
        this.grupoRetencionDao = grupoRetencionDao;
    }

    public CuentaNombrada getCuenta(final String cuenta, final Integer idTipoInst,
            final String folioInstitucion) {
        return this.derechoDao.findCuenta(cuenta, idTipoInst, folioInstitucion);
    }

    public DerechoBeneficiario getDerechoNoCortado(final Long idDerecho) throws BusinessException {
        return this.derechoDao.getDerechoNoCortado(idDerecho);
    }

    public void guardaFactorSimulacion(final FactorSimulacionMav simulacionMav) {
        FactorSimulacionMav mavTmp = null;

        mavTmp =
                (FactorSimulacionMav) this.derechoDao.getByPk(FactorSimulacionMav.class,
                        simulacionMav.getIdDerechoCapital());
        if (mavTmp != null) {
            mavTmp.setFee(simulacionMav.getFee());
            mavTmp.setPorcentajeRetencion(simulacionMav.getPorcentajeRetencion());
            mavTmp.setProporcion(simulacionMav.getProporcion());
            this.derechoDao.update(mavTmp);
        } else {
            this.derechoDao.save(simulacionMav);
        }

    }

    public FactorSimulacionMav getFactorSimulacion(final Long idDerechoCapital) {
        return (FactorSimulacionMav) this.derechoDao.getByPk(FactorSimulacionMav.class,
                idDerechoCapital);
    }

    /** @param jmsTemplatePagados to be set in this.jmsTemplatePagados */
    public void setJmsTemplatePagados(final JmsTemplate jmsTemplatePagados) {
        this.jmsTemplatePagados = jmsTemplatePagados;
    }
    
	public void generaDetalleCapitales(Long idDerechoCapital) throws Exception {
	    LOG.debug("####### Generando detalle de capitales, idDerechoCapital=[" + idDerechoCapital + "]");
		List <DerechoCapitalHistoricoVo> derecho = derechoDao.consultaDerechoCapitalHistorico(idDerechoCapital);
		LOG.debug("Derechos encontrados :: " + derecho);
		//obtengo un derecho capital para realizar las consultas de default
		if(derecho != null && derecho.size() >0){
			DerechoCapitalHistoricoVo derechotmp= derecho.get(0);
			LOG.debug("derechotmp toString :: " + derechotmp.toString());
			LOG.debug("Se clasifican cuentas del derecho");
			//Se clasifican sus cuentas
			Map<String,List<DerechoCapitalHistoricoVo>> cuentas = clasificaCuentas(derecho);
			//se obtiene su porcentaje de retencion por default
			LOG.debug("Se obtiene porcentajeRetencionDefault");
			Float porcentajeRetencionDefault = calculaPorcentajeRetencionDefault(derechotmp);		
			LOG.debug("porcentajeRetencionDefault :: " + porcentajeRetencionDefault);
			LOG.debug("Calcula  la resta de los titulos no asignados y los porcentajes aplicados, asi como su rfc");
			calculaInformacionFaltante(cuentas, porcentajeRetencionDefault);											
			
			LOG.debug("Borra si el registro existe");
			derechoDao.deleteDerechoHistoricoTabla(derechotmp.getIdDerechoCapital());
			
			LOG.debug("## Guardar en base el DerechoCapitalHistorico");
			for(String cuenta : cuentas.keySet()){
				for(DerechoCapitalHistoricoVo der : cuentas.get(cuenta)){
					DerechoCapitalHistorico derTabla = DTOAssemblerCapitales.getDerechoCapitalEntity(der);
					try{
//						der = null;
						derechoDao.saveDerechoCapitalHistorico(derTabla);
						LOG.debug("Se guardo el derecho exitosamente");			
					}catch(Exception e){
						LOG.error("Fallo la insercion en la tabla del derecho capital: " + derTabla.getIdDerechoCapital(),e);
					}
				}
			}
			LOG.debug("terminado derecho: "+derechotmp.getIdDerechoCapital());
			derechotmp=null;
			cuentas=null;
			derechoDao.flush();
		}else{
			LOG.debug("==================No se puede generar el derecho: "+idDerechoCapital);
			throw new Exception(" No hay datos para generar los datos historicos de Capitales del idDerecho capital: "+idDerechoCapital);
		}
	}
	
	
	/**
	 * Metodo para clasificar cuentas
	 * @param derecho
	 * @return
	 */
	private Map<String,List<DerechoCapitalHistoricoVo>> clasificaCuentas(List<DerechoCapitalHistoricoVo> derecho) {
		LOG.debug("Entrando a clasificaCuentas");
		Map<String,List<DerechoCapitalHistoricoVo>> cuentas = new HashMap<String,List<DerechoCapitalHistoricoVo>>();
		for(DerechoCapitalHistoricoVo der : derecho){
			//Pregunto por la cuenta a ver si existe, si no la creo junto con su lista
			if (!cuentas.containsKey(der.getIdFolio()+":"+der.getCuenta())){
				cuentas.put(der.getIdFolio()+":"+der.getCuenta(), new ArrayList<DerechoCapitalHistoricoVo>());				
			}
			cuentas.get(der.getIdFolio()+":"+der.getCuenta()).add(der);
		}
		return cuentas;		
	}
	
	/**
	 * Metodo para Calcular Porcentaje Retencion Default
	 * @param derechotmp
	 * @return
	 */
	private Float calculaPorcentajeRetencionDefault(DerechoCapitalHistoricoVo derechotmp) {
		LOG.debug("Entrando a calculaPorcentajeRetencionDefault");
		Float porcentajeRetdefault = null;
		LOG.debug("Verificando si la emision requiere beneficiarios....");
		boolean requiereBeneficiarios = requireBeneficiariosDerecho(derechotmp);
		LOG.debug("Requiere beneficiarios ["+requiereBeneficiarios+"]");
		
		if(requiereBeneficiarios){
			porcentajeRetdefault = getPorcentajeRetDefault(derechotmp.getIdCuentaNombradaBeneficiario());
			LOG.debug("requiereBeneficiarios porcentajeRetdefault :: " + porcentajeRetdefault);
			if(porcentajeRetdefault == null){
				porcentajeRetdefault = 0F;
			}
		}else{
			if(derechotmp.getPorcentajeMav() !=null){
				porcentajeRetdefault = new Float(derechotmp.getPorcentajeMav().toString());
			}else{
				porcentajeRetdefault = 0F;
			}
		}
		return porcentajeRetdefault;
	}
	
	private boolean requireBeneficiariosDerecho(DerechoCapitalHistoricoVo derechotmp) {
		LOG.debug("Entrando a requireBeneficiariosDerecho");
		int camposValidos = 0;
		boolean requiereBeneficiarios = true;		
		
		List<ExcepcionEmisionBenef> lstExcepciones = null;
		int camposValidar = 4;
		
		lstExcepciones = excepcionEmisionBenefDao.findExecepcionesEmision(derechotmp.getIdCuentaNombradaBeneficiario());
			
			if(lstExcepciones != null && !lstExcepciones.isEmpty()){
				for(ExcepcionEmisionBenef emisionBenef : lstExcepciones){
					if(StringUtils.isBlank(emisionBenef.getTv()) && StringUtils.isBlank(emisionBenef.getEmisora())
							&& StringUtils.isBlank(emisionBenef.getSerie()) && StringUtils.isBlank(emisionBenef.getIsin())){
						continue;
					}
					if(StringUtils.isBlank(emisionBenef.getTv()) || emisionBenef.getTv().equals(derechotmp.getClaveTipoValor())){
						camposValidos++;		
					}
					if(StringUtils.isBlank(emisionBenef.getEmisora()) || emisionBenef.getEmisora().equals(derechotmp.getClavePizarra())){
						camposValidos++;		
					}
					if(StringUtils.isBlank(emisionBenef.getSerie()) || emisionBenef.getSerie().equals(derechotmp.getSerie())){
						camposValidos++;		
					}
					if(StringUtils.isBlank(emisionBenef.getIsin()) || emisionBenef.getIsin().equals(derechotmp.getIsin())){
						camposValidos++;		
					}
					if(camposValidos == camposValidar){
						requiereBeneficiarios = false;
						break;
					}else{
						camposValidos = 0;
					}
				}
			}
		
		
		return requiereBeneficiarios;
	}
	
	/**
	 * Obtiene Porcentaje de retencion default
	 * @param idDerechoCapitalBenef
	 * @return
	 */
	private Float getPorcentajeRetDefault(Long IdCuentaNombradaBeneficiario) {
		LOG.debug("Entrando a getPorcentajeRetDefault");
		List<HorarioBeneficiario> horarios = null;
		Float porcentajeRetDefault = null;
		horarios = horarioBeneficiarioDao.findHorariosDerecho(IdCuentaNombradaBeneficiario);
		LOG.debug("horarios :: " + horarios);
				if(horarios != null && !horarios.isEmpty()){
					for(HorarioBeneficiario horarioBeneficiario : horarios){
						if(horarioBeneficiario.getPorcentajeRet() != null && StringUtils.isBlank(horarioBeneficiario.getTv())
								&& StringUtils.isBlank(horarioBeneficiario.getEmisora()) && StringUtils.isBlank(horarioBeneficiario.getSerie())){							
							porcentajeRetDefault = horarioBeneficiario.getPorcentajeRet();
							break;
						}						
					}					
				}		
		return porcentajeRetDefault;
	}
	
	private void calculaInformacionFaltante(Map<String, List<DerechoCapitalHistoricoVo>> cuentas, Float porcentajeRetencionDefault) throws Exception {
		LOG.debug("Entrando a calculaInformacionFaltante");
		//se asigna el porcentaje de retencion real 				
		for(String cuenta : cuentas.keySet()){
			Long titulosAcumulados = 0l; //para calcular los titulos no asignados
			DerechoCapitalHistoricoVo derNoAsignado=null;
			for(DerechoCapitalHistoricoVo der : cuentas.get(cuenta)){
				//Calcula Porcentaje de retencion
				calculaPorcentajeRetencionDerecho(der,porcentajeRetencionDefault);
				//calculaProporcionDerecho(der,porcentajeRetencionDefault); //la proporcion se recalcula con el fee
				if(der.getIdBeneficiario() != null){//titulos asignados
					titulosAcumulados += der.getAsignacion();
					calculaMontos(der);
					completaCamposBeneficiario(der);					
					
				}else{// es el de los titulos no asignados
					derNoAsignado=der;
				}
			}
			// calculo de titulos no asignados
			Long titulos = derNoAsignado.getAsignacion() - titulosAcumulados;
			derNoAsignado.setAsignacion(titulos);
			calculaMontos(derNoAsignado);
		}		
	}
	
	private void calculaPorcentajeRetencionDerecho(DerechoCapitalHistoricoVo derecho, Float porcentajeRetencionDefault) {
		LOG.debug("Entrando a calculaPorcentajeRetencionDerecho");
		/** Por beneficiario se saca el rfc se obtiene el porcentaje de retencion para cada beneficiario */
		
		if(derecho.getIdBeneficiario()!= null){ //es un derecho con beneficiario
			//TODO:sacar constantes
			LOG.debug("Es un derecho con beneficiario");
			if( new Long(18l).equals(derecho.getIdTipoDerecho()) && 
					excepcionEmisionBenefDao.findEmisionPorcentajeCero(derecho.getIdCuentaNombradaBeneficiario()) != null){
				// Es distribucion y va en cero
				derecho.setPorcentajeRetencionReal(0d);
			}else{
				if(derecho.getPorcentajeBen() !=null ){
					derecho.setPorcentajeRetencionReal(new Double(derecho.getPorcentajeBen().toString()));
				}else if(derecho.getPorcentajeRetencionCTB() != null){
					derecho.setPorcentajeRetencionReal(new Double(derecho.getPorcentajeRetencionCTB().toString()));
				}else if(derecho.getPorcentajeHBDefault() != null){
					derecho.setPorcentajeRetencionReal(new Double(derecho.getPorcentajeHBDefault().toString()));
				}else{
					derecho.setPorcentajeRetencionReal(new Double(porcentajeRetencionDefault.toString()));
				}
			}
			
		}else{ //es el derecho con titulos no asignados
			LOG.debug("El derecho con titulos no asignados");
			if(porcentajeRetencionDefault != null){
				derecho.setPorcentajeRetencionReal(new Double(porcentajeRetencionDefault.toString()));
			}else{
				derecho.setPorcentajeRetencionReal(0d);
			}
		}
	}

	private void calculaMontos(DerechoCapitalHistoricoVo der) throws Exception {
		LOG.debug("Entrando a calculaMontos");
	    BigDecimal montoNeto = null;
	    BigDecimal porcentajeRetTmp = null;
	    BigDecimal montoFee = null;

	    LOG.debug("calculaMontos :: Obtiene la posicion");
	    BigDecimal posicionTmp = null;//new BigDecimal(der.getAsignacion());
	    if(der.getAsignacion() == null){
	        der.setAsignacion(0l);
	        posicionTmp = BigDecimal.ZERO;
	    } else {
	    	posicionTmp = new BigDecimal(der.getAsignacion());
	    }

	    LOG.debug("calculaMontos :: Se obtiene la proporcion");
        Float proporcion = 0f;
        if (der.getFee() != null) {
            proporcion = this.getProporcionPrima(der);
        }
        else {
        	if(der.getProporcion() != null){
                proporcion = der.getProporcion().floatValue();        		
        	}
        }
        BigDecimal proporcionTmp = new BigDecimal(proporcion.toString());

        LOG.debug("calculaMontos :: Se obtiene el monto bruto");
        BigDecimal montoBruto = BigDecimal.ZERO;
        BigDecimal montoBrutoF = BigDecimal.ZERO;
        if (der.getFee() != null) {
        	if(der.getProporcion() != null){
                montoBruto = posicionTmp.multiply(new BigDecimal(der.getProporcion().floatValue()));        		
        	}
            montoBrutoF = posicionTmp.multiply(proporcionTmp);
        }
        else {
            montoBruto = posicionTmp.multiply(proporcionTmp);
        }

        LOG.debug("calculaMontos :: Se obtiene el impuesto retenido");
        BigDecimal impuestoRetenido = BigDecimal.ZERO;
        if(der.getPorcentajeRetencionReal() != null){
            porcentajeRetTmp = (new BigDecimal(der.getPorcentajeRetencionReal().floatValue())).movePointLeft(2);        	
        }
        if (der.getFee() != null) {
        	BigDecimal montoSinFee = BigDecimal.ZERO;
        	if(der.getProporcion() != null){
                montoSinFee = posicionTmp.multiply(new BigDecimal(der.getProporcion().floatValue()));        		
        	}

            if (der.getPorcentajeRetencionReal() != null && der.getPorcentajeRetencionReal() > 0) {
                BigDecimal porcRetDeRet = new BigDecimal(der.getPorcentajeRetencionReal().toString());
                porcentajeRetTmp = porcRetDeRet.movePointLeft(2);
            }
            impuestoRetenido = montoSinFee.multiply(porcentajeRetTmp);
        }
        else {
            impuestoRetenido = montoBruto.multiply(porcentajeRetTmp);
        }

        LOG.debug("calculaMontos :: Se obtiene el monto neto");
        BigDecimal porcentajeRetDif = new BigDecimal(1).subtract(porcentajeRetTmp);
        if (der.getFee() != null) {
            montoNeto = porcentajeRetDif.multiply(montoBrutoF);
        }
        else {
            montoNeto = porcentajeRetDif.multiply(montoBruto);
        }

        LOG.debug("calculaMontos :: Se obtiene el fee");
        if (der.getFee()!= null) {
            BigDecimal fee = new BigDecimal(der.getFee().floatValue());
            montoFee = fee.multiply(posicionTmp).setScale(2, RoundingMode.HALF_UP);
            fee = null;
        }
        else {
            montoFee = BigDecimal.ZERO;
        }

        if (montoBruto != null && montoBruto.doubleValue() > 0d) {
            der.setMontoBruto(montoBruto.setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        else {
            der.setMontoBruto(0d);
        }
        if (impuestoRetenido != null && impuestoRetenido.doubleValue() > 0d) {
            der.setImpuestoRetenido(impuestoRetenido.setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        else {
            der.setImpuestoRetenido(0d);
        }
        if (montoFee != null && montoFee.doubleValue() > 0d) {
            der.setMontoFee(montoFee.doubleValue());
        }
        else {
            der.setMontoFee(0d);
        }
        if (montoNeto != null && montoNeto.doubleValue() > 0d) {
            der.setMontoNeto(montoNeto.setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        else {
            der.setMontoNeto(0d);
        }

        montoBruto = null;
        montoBrutoF = null;
	}
	
	/**
	 * Realiza el calculo de la porporcion prima.
	 * @param der El derecho para tomar datos
	 * @return El valor de la proporcion.
	 * @throws Exception 
	 */
	public Float getProporcionPrima(DerechoCapitalHistoricoVo der) throws Exception {
		LOG.debug("Entrando a getProporcionPrima");
        BigDecimal numerador = null;
        BigDecimal divisor = null;
        Float proporcionCalculada = null;
        BigDecimal proporcion = null;
        BigDecimal porcentajeRet = null;
        BigDecimal fee = null;
        BigDecimal proporcionRes = null;
        if (der.getFee() == null) {
        	if( der.getProporcion() != null){
                proporcionCalculada = der.getProporcion().floatValue();        		
        	}
        }
        else {
        	if( der.getProporcion() != null){
                proporcion = new BigDecimal(der.getProporcion().floatValue());        		
        	}
        	if(der.getPorcentajeRetencionReal() != null){
                porcentajeRet = new BigDecimal(der.getPorcentajeRetencionReal().floatValue() / 100);        		
        	}
        	if (der.getFee() != null) {
                fee = new BigDecimal(der.getFee().floatValue()); 
        	}
        	
        	if( proporcion != null && porcentajeRet != null && fee != null){
                numerador = (proporcion.subtract((proporcion.multiply(porcentajeRet)))).subtract(fee);
                divisor = new BigDecimal(1).subtract(porcentajeRet);           
                proporcionRes = numerador.divide(divisor, 13, BigDecimal.ROUND_UP);
                proporcionCalculada = Float.valueOf(proporcionRes.floatValue());
        	} else {
        		throw new Exception(" No hay datos suficientes (Proporcion, PorcentajeRetencion, free) para generar los datos historicos del Derecho");
        	}
        	
        }
        return proporcionCalculada;
    }
	
	private void completaCamposBeneficiario(DerechoCapitalHistoricoVo der) {
		LOG.debug("Entrando a completaCamposBeneficiario");
		CamposW camposW = formatoW8Dao.findCamposFormatoW(der.getIdBeneficiario(), der.getTipoFormato());
		if(camposW != null){
			der.setExemptionFromFatcaRepCode(camposW.getExemptionFromFatcaRepCode());
			der.setTiin(camposW.getTiin());
			der.setSsn(camposW.getSsn());
			der.setForeigntiin(camposW.getForeigntiin());
			der.setReferenceNumber(camposW.getReferenceNumber());
			der.setGiin(camposW.getGiin());
			der.setExemptPayeeCode(camposW.getExemptPayeeCode());
			der.setFechaNacimiento(camposW.getFechaNacimiento());
			der.setRfc(camposW.getRfc());
		}
	}

}
