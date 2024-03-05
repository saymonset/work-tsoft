/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * Apr 15, 2008
 */
package mock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.print.attribute.standard.Severity;

/**
 * Clase utilitari para simular el contexto de la aplicacion y poder obtener un resource Bundle para los mensajes necesarios en
 * los componentes de validacion
 * 
 * @author Erik Vera Montoya
 * 
 */
public class FacesContextMock extends FacesContext {
    // Atributos necesarios para simular el contexto de la aplicacion

    private Map<String, Object> map = new HashMap<String, Object>();
    private Application application;
    private Iterator<String> clientIdWithMessages;
    private ExternalContext externalContext;
    private javax.faces.application.FacesMessage.Severity maximumSeverity;
    private Iterator<FacesMessage> messages;
    private RenderKit renderKit;
    private boolean renderResponse;
    private boolean responseComplete;
    private ResponseStream responseStream;
    private ResponseWriter responseWriter;
    private UIViewRoot viewRoot;

    private boolean releaseCalled = false;
    private boolean renderResponseCalled = false;
    private boolean responseCompleteCalled = false;

    public static void setCurrentInstance(FacesContext fctx) {
        FacesContext.setCurrentInstance(fctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#addMessage(java.lang.String, javax.faces.application.FacesMessage)
     */
    @Override
    public void addMessage(String arg0, FacesMessage arg1) {
        map.put(arg0, arg1);

    }

    public FacesMessage retrieveMessage(String msg) {
        FacesMessage result = (FacesMessage) map.get(msg);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getApplication()
     */
    @Override
    public Application getApplication() {
        return application;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getClientIdsWithMessages()
     */
    @Override
    public Iterator<String> getClientIdsWithMessages() {
        return clientIdWithMessages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getExternalContext()
     */
    @Override
    public ExternalContext getExternalContext() {
        return externalContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getMaximumSeverity()
     */
    @Override
    public javax.faces.application.FacesMessage.Severity getMaximumSeverity() {
        return maximumSeverity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getMessages()
     */
    @Override
    public Iterator<FacesMessage> getMessages() {
        return messages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getMessages(java.lang.String)
     */
    @Override
    public Iterator<FacesMessage> getMessages(String arg0) {
        return messages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getRenderKit()
     */
    @Override
    public RenderKit getRenderKit() {
        return renderKit;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getRenderResponse()
     */
    @Override
    public boolean getRenderResponse() {
        return renderResponse;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getResponseComplete()
     */
    @Override
    public boolean getResponseComplete() {
        return responseComplete;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getResponseStream()
     */
    @Override
    public ResponseStream getResponseStream() {
        return responseStream;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getResponseWriter()
     */
    @Override
    public ResponseWriter getResponseWriter() {
        return responseWriter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#getViewRoot()
     */
    @Override
    public UIViewRoot getViewRoot() {
        return viewRoot;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#release()
     */
    @Override
    public void release() {
        releaseCalled = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#renderResponse()
     */
    @Override
    public void renderResponse() {
        renderResponseCalled = true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#responseComplete()
     */
    @Override
    public void responseComplete() {
        responseCompleteCalled = true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#setResponseStream(javax.faces.context.ResponseStream)
     */
    @Override
    public void setResponseStream(ResponseStream rs) {
        responseStream = rs;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#setResponseWriter(javax.faces.context.ResponseWriter)
     */
    @Override
    public void setResponseWriter(ResponseWriter rw) {
        responseWriter = rw;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.context.FacesContext#setViewRoot(javax.faces.component.UIViewRoot)
     */
    @Override
    public void setViewRoot(UIViewRoot vr) {
        viewRoot = vr;

    }

    /**
     * Obtiene el campo descripcion externalContext
     * 
     * @param externalContext el valor de externalContext a asignar
     */
    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    /**
     * Asigna el valor del campo releaseCalled
     * 
     * @return the releaseCalled
     */
    public boolean isReleaseCalled() {
        return releaseCalled;
    }

    /**
     * Asigna el valor del campo renderResponseCalled
     * 
     * @return the renderResponseCalled
     */
    public boolean isRenderResponseCalled() {
        return renderResponseCalled;
    }

    /**
     * Asigna el valor del campo responseCompleteCalled
     * 
     * @return the responseCompleteCalled
     */
    public boolean isResponseCompleteCalled() {
        return responseCompleteCalled;
    }

}
