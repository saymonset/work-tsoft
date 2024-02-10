package com.indeval.portalinternacional.presentation.advice;


import java.util.Map;

import javax.faces.context.FacesContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.bursatec.seguridad.vo.InstitucionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * Implementación de un aspecto interceptor para los servicios de consulta que dejan bitacora.
 * 
 * @author Rafael Ibarra
 * @version 1.0
 * 
 */
@Aspect
public class AfterConsultaServiceAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Indica el mapeo de los nombres de los metodos de consulta de proyeccion con el nombre de la
     * consulta
     */
    private Map<String, String> mapaProyecciones;

    /**
     * JmsTemplate para el envio de mensajes de la bitacora de Consultas
     */
    private JmsTemplate jmsTemplate;


    /**
     * Intercepta los servicios para guardar la bitacora de las consultas
     * 
     * @param returnValue
     */
    @AfterReturning(
            pointcut = "(execution(* com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalServiceImpl.consultaOperaciones(..)) || "
                    + "execution(* com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalServiceImpl.findSicEmisionesByEmisionAndCustodio(..)) || "
                    + "execution(* com.indeval.portalinternacional.middleware.services.divisioninternacional.DerechosCapitalesServiceImpl.consultaDetalleDerechosCapital(..)) || "
                    + "execution(* com.indeval.portalinternacional.middleware.services.divisioninternacional.DerechosCapitalesServiceImpl.consultaDetalleDerechosCapitalCuenta(..))"
                    + ") && " + "args(..,debeDejarLog)",
                    returning = "returnValue")
    public void afterReturningProyeccionServices(final JoinPoint joinPoint,
            final Object returnValue, final Boolean debeDejarLog) {
        this.log.debug("Entrando al metodo AfterThrowingWebAspect.afterReturningProyeccionServices");
        BitacoraIngresosConsulta bitacora = null;
        /* Generacion y Envio del mensaje */
        try {
            if (debeDejarLog != null && debeDejarLog) {
                bitacora = this.procesaDatosUsuario(FacesContext.getCurrentInstance());
                this.procesaDatosProyeccionServicio(bitacora, joinPoint.getStaticPart(),
                        returnValue);
                this.enviaMensajes(bitacora);
            }
        } catch (Exception ex) {
            if (bitacora != null) {
                this.log.error(
                        "ERROR AL MANDAR EL MENSAJE: ["
                                + ToStringBuilder.reflectionToString(bitacora,
                                        ToStringStyle.MULTI_LINE_STYLE) + "]", ex);
            } else {
                this.log.error("ERROR AL MANDAR EL MENSAJE", ex);
            }
        }

    }



    /**
     * Metodo que toma el contexto Faces y el StaticPart del PointCut para formar el objeto y
     * obtener el ticket y la institucion actual
     * 
     * @param FacesContext facesContext
     * @return BitacoraIngresosConsulta
     */
    private BitacoraIngresosConsulta procesaDatosUsuario(final FacesContext facesContext)
            throws Exception {
        BitacoraIngresosConsulta bitacora = new BitacoraIngresosConsulta();

        HttpServletRequest request =
                (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        /* Obtiene el ticket de la sesion */
        String ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
        if (StringUtils.isNotBlank(ticket)) {
            bitacora.setTicket(ticket);
        } else {
            throw new Exception("No se puede obtener el ticket");
        }
        /* Obtiene la institucion primaria */
        UsuarioDTO user = (UsuarioDTO) session.getAttribute(SeguridadConstants.USUARIO_SESION);
        if (user != null && user.getInstitucionPrimaria() != null
                && StringUtils.isNotBlank(user.getInstitucionPrimaria().getClave())
                && StringUtils.isNotBlank(user.getInstitucionPrimaria().getFolio())) {
            bitacora.setInstitucionPrimaria(user.getInstitucionPrimaria().getClave()
                    + user.getInstitucionPrimaria().getFolio());
        } else {
            throw new Exception("No se puede obtener la institucion primaria");
        }
        /* Obtiene la institucion seleccionada */
        InstitucionVO institucionVO =
                (InstitucionVO) session.getAttribute(SeguridadConstants.INSTITUCION_VO_ACTUAL);
        if (institucionVO != null) {
            bitacora.setInstitucion(institucionVO.getClave() + institucionVO.getFolio());
        } else {
            throw new Exception("No se puede obtener la institucion seleccionada");
        }

        return bitacora;
    }

    /**
     * Metodo que toma el contexto Faces y el StaticPart del PointCut para formar el objeto y
     * obtener el ticket y la institucion actual
     * 
     * @param BitacoraIngresosConsulta bitacora
     * @param JoinPoint.StaticPart staticPart
     * @param Object returnValue
     */
    private void procesaDatosProyeccionServicio(final BitacoraIngresosConsulta bitacora,
            final JoinPoint.StaticPart staticPart, final Object returnValue) throws Exception {

        /* Se obtienen los datos dependiendo del tipo de consulta */
        Signature signature = staticPart.getSignature();
        String firmaMetodo = signature.getName();

        if (StringUtils.isNotBlank(firmaMetodo)) {
            firmaMetodo = firmaMetodo.trim();
            bitacora.setRepresentaTotal(true);

            String nombreConsulta = this.mapaProyecciones.get(firmaMetodo);

            if (StringUtils.isNotBlank(nombreConsulta)) {
                bitacora.setNombreConsulta(nombreConsulta);
            } else {
                throw new Exception("No hay ninguna coincidencia del metodo");
            }

            /* Se obtienen los numeros de registros */
            Number numeroRegistros = null;
            PaginaVO pagina = (PaginaVO) returnValue;
            if (pagina != null && pagina.getTotalRegistros() != null) {
                numeroRegistros = new Long(pagina.getTotalRegistros().longValue());
            }
            if (numeroRegistros != null) {
                bitacora.setNumeroRegistros(numeroRegistros.longValue());
            } else {
                throw new Exception("No se pudo obtener el numero de registros");
            }

        } else {
            throw new Exception("No se puede obtener la firma del metodo");
        }

    }


    /**
     * Metodo que envia los mensajes a la cola de salida
     * 
     * @param bitacora
     */
    private void enviaMensajes(final BitacoraIngresosConsulta bitacora) {
        XStream parser = new XStream(new DomDriver());
        parser.alias("bitacoraIngresos", BitacoraIngresosConsulta.class);
        final String mensaje = parser.toXML(bitacora);

        this.jmsTemplate.send(new MessageCreator() {
            public Message createMessage(final Session session) throws JMSException {
                TextMessage message = session.createTextMessage();
                message.setText(mensaje);
                return message;
            }
        });
    }

    /**
     * @param mapaProyecciones the mapaProyecciones to set
     */
    public void setMapaProyecciones(final Map<String, String> mapaProyecciones) {
        this.mapaProyecciones = mapaProyecciones;
    }

    /**
     * @param jmsTemplate the jmsTemplate to set
     */
    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

   

}
