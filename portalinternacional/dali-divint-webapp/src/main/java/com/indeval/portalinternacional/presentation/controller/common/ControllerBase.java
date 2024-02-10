/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.common;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.middleware.ejb.SeguridadServiceLocator;
import com.bursatec.seguridad.middleware.service.SeguridadException;
import com.bursatec.seguridad.middleware.service.SeguridadService;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.bursatec.seguridad.vo.CertificadoVO;
import com.bursatec.seguridad.vo.InstitucionVO;
import com.indeval.commons.cache.services.DistCacheService;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.presentation.jsf.constantes.ReportesConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.presentation.advice.ResultadoInvocacionServicioUtil;

/**
 * Clase base para los backing bean de DIVISION INTERNACIONAL
 *
 * @author Erik Vera Montoya - 2H Software
 * @version 1.0 - Jun 21, 2008
 */
@SuppressWarnings("unchecked")
public abstract class ControllerBase {

    private static final Logger logger = LoggerFactory.getLogger(ControllerBase.class);

    /**
     * Objeto que representa la pagina a consultar
     */
    public PaginaVO paginaVO = new PaginaVO();

    /**
     * Objeto para representar las parcialidades
     **/
    public PaginaVO paginaParcialidadesVO = new PaginaVO();

    /**
     * El n&uacute;mero de p&aacute;gina actual de la consulta
     */
    private int numeroPagina = -1;

    /**
     * La cantidad de registros que ser&aacute;n presentados en una p&aacute;gina de resultados
     */
    private int registrosPorPagina = -1;

    /**
     * El total de p&aacute;ginas de resultados productos de la consulta
     */
    private int totalPaginas = -1;

    /**
     * Indica el estado de la peticion de un reporte
     */
    private boolean peticionReporteCompleta = false;

    private transient DistCacheService indevalCacheService;

    /**
     * Método que reinicia las variables para monitorear el estado de la
     * petición de un reporte.
     */
    public void reiniciarEstadoPeticion() {
        peticionReporteCompleta = false;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.removeAttribute(ReportesConstantes.ESTADO_PETICION_REPORTES);
    }

    /**
     * Método que valida si la petición que genera un reporte ha terminado.
     *
     * @param actionEvent Evento generado por faces.
     */
    public void actualizarEstadoPeticion(ActionEvent actionEvent) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Integer estado = (Integer) session.getAttribute(ReportesConstantes.ESTADO_PETICION_REPORTES);
        if (estado != null && ReportesConstantes.ESTADO_PETICION_REPORTES_COMPLETO == estado) {
            peticionReporteCompleta = true;
        }
    }

    public void setRegistrosXPag(Integer regs) {
        paginaVO.setRegistrosXPag(regs);
    }


    /**
     * Método que valida la vigencia del certificado electrónico.
     *
     * @return true si el certificado es vigente; false en caso contrario.
     * @throws BusinessException en caso de ocurrir un error
     */
    public void validarVigenciaCertificado() throws BusinessException {
        try {
            if (isUsuarioConFacultadFirmar()) {
                SeguridadService seguridadExposedService = SeguridadServiceLocator.obtenerSeguridadExposedService();
                if (seguridadExposedService == null) {
                    throw new BusinessException(obtenerMensajePropiedades("msgErrorSeguridad"));
                }
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(SeguridadConstants.USUARIO_SESION);
                if (usuario != null && StringUtils.isNotBlank(usuario.getNumeroSerieCertificado())) {
                    //Indica si deben validar o no los certificados dependiendo la bandera en c_propiedades_dali
                    boolean validarCertificado = seguridadExposedService.isValidaCertificados();
                    //Obtiene el certificado
                    CertificadoVO certificado = seguridadExposedService.getCertificadoInfo(usuario.getNumeroSerieCertificado());
                    if (validarCertificado &&
                            !((certificado.getStatus() != null && SeguridadConstants.CERTIFICADO_VALIDO_VALUE == certificado.getStatus()) &&
                                    validarFechaVencimientoCertificado(certificado))) {
                        throw new BusinessException(obtenerMensajePropiedades("msgErrorCertificadoNoVigente"));
                    }
                } else {
                    throw new BusinessException(obtenerMensajePropiedades("msgErrorSinCertificado"));
                }
            }
        } catch (SeguridadException exception) {
            throw new BusinessException(obtenerMensajePropiedades("msgErrorSeguridad"), exception);
        } catch (Exception exception) {
            throw new BusinessException(obtenerMensajePropiedades("msgErrorSeguridad"), exception);
        }
    }

    /**
     * Valida que la fecha de vencimiento del certificado sea menor o igual a la fecha actual.
     *
     * @param certificado Objeto con el certificado.
     * @return true si la fecha de vencimiento es valida; false en caso contrario.
     */
    private boolean validarFechaVencimientoCertificado(CertificadoVO certificado) {
        boolean valido = false;
        if (certificado != null && certificado.getFechaVencimiento() != null) {
            valido = new Date().compareTo(certificado.getFechaVencimiento()) <= 0;
        }
        return valido;
    }

    /**
     * Método que obtiene un mensajes del archivo properties.
     *
     * @param idMensaje Id del mensaje en el archivo properties.
     * @return Cadena con el mensaje.
     */
    protected String obtenerMensajePropiedades(String idMensaje) {
        return (String) getValue("#{mensajes." + idMensaje + "}");
    }

    /**
     * Método que obtiene un mensaje del archivo properties.
     *
     * @param idMensaje  Id del mensaje
     * @param parametros Parámetros del mensaje
     * @return Mensaje formateado
     */
    public String obtenerMensajePropiedades(String idMensaje, final Object[] parametros) {
        String mensaje = (String) getValue("#{mensajes." + idMensaje + "}");
        if (null != parametros && parametros.length > 0) {
            mensaje = (new MessageFormat(mensaje)).format(parametros, new StringBuffer(), null).toString();
        }
        return mensaje;
    }

    /**
     * Metodo de utileria para obtener el valor de una expresion EL de faces
     *
     * @param obj: La expresion a evaluar
     * @return El valor de la expresion
     */
    public Object getValue(final String obj) {
        final ExpressionFactory expr =
                getFacesContext().getApplication().getExpressionFactory();
        final ValueExpression valExp =
                expr.createValueExpression(getELContext(), obj, Object.class);
        return valExp.getValue(getELContext());
    }

    /**
     * Para obtener el mapa de parametros del request
     *
     * @return Mapa de parametros del request
     */
    public Map<String, String> getParameterMap() {
        return getContext().getExternalContext().getRequestParameterMap();
    }

    /**
     * Para obtener el contexto de Faces
     *
     * @return Objeto de contexto de Faces
     */
    public FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }


    /**
     * Metodo de utileria para simplificar el acceso al contexto de faces
     *
     * @return El objeto FacesContext
     */
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Metodo de utileria para simplificar el acceso al contexto de EL
     *
     * @return El objeto ELContext
     */
    public ELContext getELContext() {
        return getFacesContext().getELContext();
    }


    /**
     * Verifica si existe un error en la &uacute;ltima invocaci&oacute;n de un servicio de negocio remoto
     *
     * @return
     */
    public boolean existeErrorEnInvocacion() {
        return ResultadoInvocacionServicioUtil.existeError();
    }

    /**
     * Agrega un mensaje proveniente de una excepci&oacute;n de negocio producto de una llamada a un EJB
     *
     * @param ex
     */
    public void agregarMensaje(Throwable ex) {

        if (ex instanceof EJBException) {

            EJBException ejbException = (EJBException) ex;

            if (ejbException.getCause() != null && ejbException.getCause() instanceof BusinessException) {
                BusinessException bex = (BusinessException) ejbException.getCause();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getMessage(), bex.getMessage()));
                ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
            } else {
                if (ejbException.getCausedByException() != null
                        && ejbException.getCausedByException() instanceof BusinessException) {
                    BusinessException bex = (BusinessException) ejbException.getCausedByException();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getMessage(), bex.getMessage()));
                    ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
                } else {
                    if (ejbException.getCausedByException() != null
                            && ejbException.getCausedByException() instanceof InfrastructureException) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, ejbException.getMessage(), ejbException.getMessage()));
                        ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
                        throw (InfrastructureException) ejbException.getCausedByException();
                    }
                    /*
                     * se agrega validacion para controlar los mensajes de las excepciones de tipo IllegalArgumentException
                     */
                    if (ejbException.getCausedByException() != null && ejbException.getCausedByException() instanceof IllegalArgumentException) {
                        IllegalArgumentException ilegalException = (IllegalArgumentException) ejbException.getCausedByException();
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, ilegalException.getMessage(), ilegalException.getMessage()));
                        ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
                        throw new InfrastructureException(ex);
                    }
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
            throw new InfrastructureException(ex);
        }

    }

    /**
     * Agrega un mensaje proveniente de una excepci&oacute;n de negocio producto de una llamada a un servicio de negocio
     *
     * @param bex
     */
    public void agregarMensaje(BusinessException bex) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getMessage(), bex.getMessage()));
        ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
    }

    /**
     * Agrega un mensaje informativo
     *
     * @param mensaje
     */
    public void agregarMensajeInfo(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
    }
    /**
     * Agrega un mensaje informativo
     *
     * @param mensaje
     */
    public void agregarMensajeWarn(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, null));
    }

    /**
     * Verifica si el usuario en sesi&oacute;n pertenece a la instituci&oacute;n INDEVAL.
     *
     * @return <code>true</code> si el usuario en sesi&oacute;n pertenece a la
     * instituci&oacute;n INDEVAL. <code>false</code> en cualquier otro caso.
     */
    public boolean isInstitucionIndeval() {
        AgenteVO inst = getAgenteFirmado();
        boolean res = false;
        if ("12".equals(inst.getId())) {
            res = true;
        }
        return res;
    }

    /**
     * Indica si la operaci&oacute;n se realiza con certificado digital o no
     *
     * @return
     */
    public boolean conFirma() {
        return isUsuarioConFacultadFirmar();
    }

    /**
     * M&eacute;todo base para poder ejecutar la consulta
     */
    public String ejecutarConsulta() {
        return null;
    }

    /**
     * Actualiza la informaci&oacute;n de la paginaci&oacute;n al avanzar el n&uacute;mero de p&aacute;ginas solicitadas.
     *
     * @param paginas el n&uacute;mero de p&aacute;ginas a avanzar.
     */
    public void avanzar(int paginas) {
        int numeropPag = getNumeroPagina();
        if (paginaVO.getTotalRegistros() > 0) {
            if (numeropPag + paginas > getTotalPaginas()) {
                numeropPag = getTotalPaginas();
            } else {
                numeropPag += paginas;
            }
            paginaVO.setOffset((numeropPag - 1) * paginaVO.getRegistrosXPag() + 0);
        }
        ejecutarConsulta();

    }

    /**
     * Actualiza la informaci&oacute;n de la paginaci&oacute;n al retroceder el numero de p&aacute;ginas solicitadas.
     *
     * @param paginas el n&uacute;mero p&aacute;ginas a retroceder.
     */
    public void retroceder(int paginas) {
        int numeropPag = getNumeroPagina();
        if (paginaVO.getTotalRegistros() > 0) {
            if ((numeropPag - paginas) < 1) {
                numeropPag = 1;
            } else {
                numeropPag -= paginas;
            }
        }
        paginaVO.setOffset(((numeropPag - 1) * paginaVO.getRegistrosXPag()));
        ejecutarConsulta();
    }

    /**
     * Actualiza la informaci&oacute;n de la paginaci&oacute;n al establecer la primera p&aacute;gina de resultados como la p&aacute;gina actual.
     */
    public String irPrimeraPagina() {

        if (paginaVO.getTotalRegistros() > 0) {
            numeroPagina = 1;
        }
        paginaVO.setOffset(0);
        ejecutarConsulta();
        return null;
    }

    /**
     * Actualiza la informaci&oacute;n de la paginaci&oacute;n al establecer la &uacute;ltima p&aacute;gina de resultados como la p&aacute;gina actual.
     */
    public String irUltimaPagina() {
        getNumeroPagina();
        if (paginaVO.getTotalRegistros() > 0) {
            paginaVO.setOffset((getTotalPaginas() - 1) * paginaVO.getRegistrosXPag() + 0);
        }
        ejecutarConsulta();
        return null;
    }

    public boolean isIrAlPrimero() {

        boolean resultado = false;

        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() > 1) {
                resultado = true;
            }
        }

        return resultado;
    }

    public boolean isIrAlUltimo() {
        boolean resultado = false;

        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() < getTotalPaginas()) {
                resultado = true;
            }
        }

        return resultado;
    }

    public boolean isAvanzar() {
        boolean resultado = false;

        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() < getTotalPaginas()) {
                resultado = true;
            }
        }

        return resultado;
    }

    public boolean isAvanzarRapido() {
        boolean resultado = false;

        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() < getTotalPaginas()) {
                resultado = true;
            }
        }

        return resultado;
    }

    public boolean isRetroceder() {
        boolean resultado = false;

        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() > 1) {
                resultado = true;
            }
        }

        return resultado;
    }

    public boolean isRetrocederRapido() {
        boolean resultado = false;

        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() > 1) {
                resultado = true;
            }
        }

        return resultado;
    }

    /**
     * Metodo para refrescar pagina actual
     */

    public String irPaginaActual() {
        int numeroPag = getNumeroPagina();
        if (paginaVO.getTotalRegistros() > 0) {
            paginaVO.setOffset((numeroPag - 1) * paginaVO.getRegistrosXPag() + 0);
        }
        ejecutarConsulta();
        return null;
    }

    public String avanzarPagina() {
        avanzar(1);
        return null;
    }

    public String avanzarPaginasRapido() {
        avanzar(3);
        return null;
    }

    public String retrocederPaginasRapido() {
        retroceder(3);
        return null;
    }

    public String retrocederPagina() {
        retroceder(1);
        return null;
    }

    /**
     * M&eacute;todo que obtiene la fecha actual para los reportes
     *
     * @return la fecha del d&iacute;a actual.
     */
    public Date getFechaActual() {
        return new Date();
    }

    /**
     * @return the paginaVO
     */
    public PaginaVO getPaginaVO() {
        return paginaVO;
    }

    /**
     * @param paginaVO the paginaVO to set
     */
    public void setPaginaVO(PaginaVO paginaVO) {
        this.paginaVO = paginaVO;
    }

    /**
     * @return the paginaParcialidadesVO
     */
    public PaginaVO getPaginaParcialidadesVO() {
        return paginaParcialidadesVO;
    }

    /**
     * @param paginaParcialidadesVO the paginaParcialidadesVO to set
     */
    public void setPaginaParcialidadesVO(PaginaVO paginaParcialidadesVO) {
        this.paginaParcialidadesVO = paginaParcialidadesVO;
    }

    /**
     * @return the registrosPorPagina
     */
    public int getRegistrosPorPagina() {
        if (paginaVO != null && paginaVO.getRegistrosXPag() > 0) {
            registrosPorPagina = paginaVO.getRegistrosXPag();
        }
        return registrosPorPagina;
    }

    /**
     * @param registrosPorPagina the registrosPorPagina to set
     */
    public void setRegistrosPorPagina(int registrosPorPagina) {
        this.registrosPorPagina = registrosPorPagina;
    }

    /**
     * @return the numeroPagina
     */
    public int getNumeroPagina() {
        if (paginaVO != null && paginaVO.getRegistrosXPag() > 0) {

            numeroPagina = (int) Math.ceil(paginaVO.getOffset().doubleValue() / paginaVO.getRegistrosXPag().doubleValue()) + 1;
            int total = getTotalPaginas();
            if (numeroPagina > total) {
                numeroPagina = total;
            }

        }
        return numeroPagina;
    }

    /**
     * @param numeroPagina the numeroPagina to set
     */
    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    /**
     * @return the totalPaginas
     */
    public int getTotalPaginas() {
        if (paginaVO != null && paginaVO.getTotalRegistros() > 0) {
            totalPaginas = (int) Math.ceil((paginaVO.getTotalRegistros().doubleValue() / paginaVO.getRegistrosXPag()
                    .doubleValue()));
        }
        return totalPaginas;
    }

    /**
     * @param totalPaginas the totalPaginas to set
     */
    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    /**
     * Obtiene de la sesi&oacute;n Web del usuario la instituci&oacute;n actual del participante.
     *
     * @return DTO con los datos de la instituci&oacute;n.
     */
    public AgenteVO getAgenteFirmado() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Object obj = ((HttpSession) ctx.getExternalContext().getSession(false)).getAttribute(SeguridadConstants.INSTITUCION_ACTUAL);

        AgenteVO agente = null;
        InstitucionDTO institucionDTO = null;

        if (obj instanceof InstitucionDTO) {
            institucionDTO = (InstitucionDTO) obj;
            if (institucionDTO != null) {
                agente = new AgenteVO();
                agente.setId(institucionDTO.getClaveTipoInstitucion());
                agente.setFolio(institucionDTO.getFolioInstitucion());
                agente.setFirmado(true);
                agente.setNombreCorto(institucionDTO.getNombreCorto());
                agente.setRazon(institucionDTO.getRazonSocial());

            }
        } else if (obj instanceof AgenteVO) {
            agente = (AgenteVO) obj;
        }
        return agente;
    }

    /**
     * Agrega un mensaje de severidad info
     *
     * @param mensaje
     */
    public void agregarInfoMensaje(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        mensaje, mensaje));
    }

    /**
     * Agrega un mensaje al contexto y de esta forma, se pueda presentar al usuario
     *
     * @param idMensaje ID del mensaje en el archivo de propiedades
     * @param severidad La severidad con la que se manda el mensaje
     */
    protected void addMessageFromProperties(final String idMensaje, final Severity severidad) {
        final String mensaje = (String) getValue("#{mensajes." + idMensaje + "}");
        final FacesMessage facesMessage = new FacesMessage(severidad, mensaje, mensaje);
        getFacesContext().addMessage(null, facesMessage);
    }

    /**
     * Agrega un mensaje al contexto y de esta forma, se pueda presentar al usuario
     *
     * @param idMensaje  Id del mensaje en el archivo de propiedades
     * @param parametros Parametros del mensaje
     * @param severidad  La severidad con la que se manda el mensaje
     */
    protected void addMessageFromProperties(final String idMensaje, final Object[] parametros, final Severity severidad) {
        String mensaje = (String) getValue("#{mensajes." + idMensaje + "}");
        if (null != parametros && parametros.length > 0) {
            mensaje = (new MessageFormat(mensaje)).format(parametros, new StringBuffer(), null).toString();
        }
        final FacesMessage facesMessage = new FacesMessage(severidad, mensaje, mensaje);
        getFacesContext().addMessage(null, facesMessage);
    }


    /**
     * M&eacute;todo que agrega mensaje el faces context
     *
     * @param mensaje
     */
    public void addMessage(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
    }

    /**
     * M&eacute;todo que agrega mensaje el faces context
     *
     * @param mensaje
     */
    public void addErrorMessage(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                mensaje, mensaje));
    }

    public void addWarnMessage(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                mensaje, mensaje));
    }


    /**
     * M&eacute;todo que indica el usuario actual debe firmar operaciones
     *
     * @return true en caso de que deba firmar, false en caso contrario
     */
    protected boolean isUsuarioConFacultadFirmar() {
        boolean debeFirmar = false;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        if (session != null) {

            HashMap<String, String> facultadesUsuario = (HashMap<String, String>) session.getAttribute(SeguridadConstants.FACULTADES_SESION);

            if (facultadesUsuario.get(SeguridadConstants.FACULTAD_FIRMA_OPERACION) != null) {
                debeFirmar = true;
            }

        }

        return debeFirmar;
    }

    /**
     * M&eacute;todo que indica el usuario actual debe firmar operaciones
     *
     * @return true en caso de que deba firmar, false en caso contrario
     */
    public boolean isUsuarioRolIndeval() {
        boolean rolIndeval = false;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        rolIndeval = (Boolean) session.getAttribute("ROLINDEVAL");
        return rolIndeval;
    }

    public void trataExcepcion(EJBException ejbException) throws InfrastructureException {
        if (ejbException.getCause() != null && ejbException.getCause() instanceof BusinessException) {
            BusinessException bex = (BusinessException) ejbException.getCause();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getMessage(), bex.getMessage()));
            ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
        } else {
            if (ejbException.getCausedByException() != null && ejbException.getCausedByException() instanceof BusinessException) {
                BusinessException bex = (BusinessException) ejbException.getCausedByException();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getMessage(), bex.getMessage()));
                ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
            } else {
                if (ejbException.getCausedByException() != null && ejbException.getCausedByException() instanceof InfrastructureException) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ejbException.getMessage(), ejbException.getMessage()));
                    ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
                    throw (InfrastructureException) ejbException.getCausedByException();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ejbException.getMessage(), ejbException.getMessage()));
                    ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
                    throw new InfrastructureException(ejbException.getCausedByException());
                }
            }
        }
    }

    /**
     * Obtiene un parametro del request
     *
     * @param paramName Nombre del parametro a obtener
     * @return El parametro o null si no existe
     */
    public String getParamFromRequest(String paramName) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> mapaParametros = facesContext.getExternalContext().getRequestParameterMap();
        if (mapaParametros != null) {
            if (mapaParametros.containsKey(paramName)) {
                return mapaParametros.get(paramName);
            }
        }
        return null;
    }

    /**
     * Obtiene un parametro del request y lo convierte a Integer si puede
     *
     * @param paramName Nombre del parametro a obtener
     * @return El parametro o null si no existe
     */
    public Long getLongParamFromRequest(String paramName) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> mapaParametros = facesContext.getExternalContext().getRequestParameterMap();
        if (mapaParametros != null) {
            if (mapaParametros.containsKey(paramName)) {
                String paramCadena = mapaParametros.get(paramName);
                if (StringUtils.isNotBlank(paramCadena)) {
                    Long param = null;
                    try {
                        param = Long.valueOf(paramCadena);
                    } catch (NumberFormatException numberFormatException) {
                        logger.error("Error al convertir el parametro: [" + paramName + "]", numberFormatException);
                        param = null;
                    }
                    return param;
                }
            }
        }
        return null;
    }

    /**
     * Obtiene un bean del contexto
     *
     * @param beanName Nombre del bean a obtener
     * @return El bean encontrado o null sino lo encuentra
     */
    public Object getBean(String beanName) {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        ExpressionFactory expressionFactory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
        Object bean = expressionFactory.createValueExpression(context, "#{" + beanName + "}", Object.class).getValue(context);
        return bean;
    }

    public Object getActionAttribute(ActionEvent event, String name) {
        return event.getComponent().getAttributes().get(name);
    }

    public boolean isUserInRoll(String rol) {
        boolean isUserInRol = false;
        Map<String, String> roles = null;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null && session.getAttribute(SeguridadConstants.ROLES_SESION) != null) {
            roles = (Map<String, String>) session.getAttribute(SeguridadConstants.ROLES_SESION);
            if (roles.containsKey(rol)) {
                isUserInRol = true;
            }
        }
        return isUserInRol;
    }

    public boolean isAdminSicMILA() {
        return isUserInRoll(Constantes.ROL_INT_SIC_MILA_ADMIN);
    }

    /**
     * metodo para tomar el valor de sesion del usuario logueado
     *
     * @return String el nombre del usuario
     */
    public String getNombreUsuarioSesion() {
        String nombreUsuario = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        Object obj = ((HttpSession) ctx.getExternalContext().getSession(false)).getAttribute(SeguridadConstants.USUARIO_SESION);
        if (obj instanceof UsuarioDTO) {
            UsuarioDTO usuarioDTO = (UsuarioDTO) obj;
            if (usuarioDTO != null && usuarioDTO.getNombre() != null) {
                nombreUsuario = usuarioDTO.getNombre();
            }
        }
        return nombreUsuario;
    }


    /**
     * @return String el nombre del usuario
     * @author Marco Edgar Valencia Arana, KODE
     * metodo para tomar la clave de sesion del usuario logueado
     */
    public String getCveUsuarioSesion() {
        String cveUsuario = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        Object obj = ((HttpSession) ctx.getExternalContext().getSession(false)).getAttribute(SeguridadConstants.USUARIO_SESION);
        if (obj instanceof UsuarioDTO) {
            UsuarioDTO usuarioDTO = (UsuarioDTO) obj;
            if (usuarioDTO != null && usuarioDTO.getNombre() != null) {
                cveUsuario = usuarioDTO.getClaveUsuario();
            }
        }
        return cveUsuario;
    }


    /**
     * Metodo para obtener el ticket de seguridad de la sesion
     *
     * @return String el ticket de seguridad de la sesion
     */
    public String getTicketSesion() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        return (String) ((HttpSession) ctx.getExternalContext().getSession(false)).getAttribute(SeguridadConstants.TICKET_SESION);
    }

    /**
     * Método para obtener el atributo peticionReporteCompleta
     *
     * @return El atributo peticionReporteCompleta
     */
    public boolean isPeticionReporteCompleta() {
        return peticionReporteCompleta;
    }


    /**
     * Método para establecer el atributo peticionReporteCompleta
     *
     * @param peticionReporteCompleta El valor del atributo peticionReporteCompleta a establecer.
     */
    public void setPeticionReporteCompleta(boolean peticionReporteCompleta) {
        this.peticionReporteCompleta = peticionReporteCompleta;
    }


    public void setIndevalCacheService(DistCacheService indevalCacheService) {
        this.indevalCacheService = indevalCacheService;
    }

    public DistCacheService getIndevalCacheService() {
        return indevalCacheService;
    }


    public void addToCache(String id, String key, String value, Long timeValue, TimeUnit timeUnit) {
        indevalCacheService.addToCache(id.concat("|").concat(key), value, -1l, TimeUnit.SECONDS, timeValue, timeUnit);
    }

    public String getFromCache(String id, String key) {
        return indevalCacheService.getFromCache(id.concat("|").concat(key));
    }

    public void removeFromCache(String id, String key) {
        indevalCacheService.removeFromCache(id.concat("|").concat(key));
    }

    public void removeFromCache(String key) {
        indevalCacheService.removeFromCache(key);
    }

	/**
	 * Obtiene institucion actual del usuario logueado
	 * 
	 * @author genner.cardenas
	 * @since 20/06/2023
	 * @return
	 */
	public InstitucionVO getInstitucionVigente() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		Object obj = ((HttpSession) ctx.getExternalContext().getSession(false))
				.getAttribute(SeguridadConstants.INSTITUCION_VO_ACTUAL);

		InstitucionVO institucionVO = null;
		if (obj instanceof InstitucionVO) {
			institucionVO = (InstitucionVO) obj;
		} else {
			institucionVO = new InstitucionVO();
		}

		return institucionVO;
	}

}
