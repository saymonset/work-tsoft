package com.indeval.portalinternacional.presentation.controller.custodiosDepositantes;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.EmisionesCustodiosDepositantesService;
import com.indeval.portalinternacional.middleware.servicios.dto.CustodiosDepositantesDto;
import com.indeval.portalinternacional.middleware.servicios.vo.CustodioVO;
import com.indeval.portalinternacional.middleware.servicios.vo.DepositanteVO;
import com.indeval.portalinternacional.middleware.servicios.vo.EmisionVO;
import com.indeval.portalinternacional.middleware.servicios.vo.MensajeSecuenciaVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;

/**
 * Clase controller para custodios depositantes
 * @author arivera
 * @version 1.0
 */
public class ConsultaCustodiosDepositantesController extends ControllerBase {
	
	/** variable para el Log de la clase. */
	private static final Logger log = LoggerFactory.getLogger(ConsultaCustodiosDepositantesController.class);
	
	/** variable que almacena los datos de la pantalla. */
	private CustodiosDepositantesDto custodiosDto = new CustodiosDepositantesDto();
	
	/** variable para indicar cuando se realiza la consulta */
	private boolean consultaEjecutada;
	
	/** servicio para custodios depositantes */
	private EmisionesCustodiosDepositantesService emisionesCustodiosDepositantesService;
	
	
    /**
     * Constructor
     */
    public ConsultaCustodiosDepositantesController() {
        super();
    }
	
	
	/**
	 * Inicializa el bean
	 * @return
	 */
	public String getInit()
	{
		
		if(custodiosDto == null)
			custodiosDto = new CustodiosDepositantesDto();
		
		if(paginaVO == null)
		   paginaVO = new PaginaVO();
		
		if(custodiosDto.getTv() == null)
			custodiosDto.setTv(StringUtils.EMPTY);
		
		if(custodiosDto.getEmisora() == null)
			custodiosDto.setEmisora(StringUtils.EMPTY);
		
		if(custodiosDto.getBicProd() == null)
			custodiosDto.setBicProd(StringUtils.EMPTY);
		
		if(custodiosDto.getCupon() == null)
			custodiosDto.setCupon(StringUtils.EMPTY);
		
		if(custodiosDto.getDetalleCustodio() == null)
			custodiosDto.setDetalleCustodio(StringUtils.EMPTY);
		
		if(custodiosDto.getEstatusRegistro() == null)
			custodiosDto.setEstatusRegistro(StringUtils.EMPTY);
		
		if(custodiosDto.getIsin() == null)
			custodiosDto.setIsin(StringUtils.EMPTY);
		
		if(custodiosDto.getSerie() == null)
			custodiosDto.setSerie(StringUtils.EMPTY);
		
		paginaVO.setRegistrosXPag(50);
		//this.paginaVO.setOffset(0);
		
		log.debug("### INICIANDO CONSULTA CUSTODIOS DEPOSITANTES");
		return null;
	}
	
	
	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{
		log.debug("### Custodios Depositantes - limpiar");
		custodiosDto.setTv(StringUtils.EMPTY);
		custodiosDto.setEmisora(StringUtils.EMPTY);
		custodiosDto.setSerie(StringUtils.EMPTY);
		custodiosDto.setIsin(StringUtils.EMPTY);
		custodiosDto.setBicProd(StringUtils.EMPTY);
		custodiosDto.setDetalleCustodio(StringUtils.EMPTY);
		
		if (paginaVO != null) {
            if (paginaVO.getRegistros() != null) {
                paginaVO.getRegistros().clear();
                paginaVO.setRegistros(null);
            }
            paginaVO.setOffset(0);
            paginaVO.setTotalRegistros(0);
            paginaVO.setRegistrosXPag(50);
        }
					
		setConsultaEjecutada(false);		
	}
	
	public void ejecutarConsultaPorRegistros() {
		getPaginaVO().setRegistros(null);
		paginaVO.setOffset(0);
        ejecutarConsulta();
	}
	
	
	/**
	 * Buscar informacion
	 * @param evt
	 */
	public void buscar(ActionEvent evt)
	{
		log.debug("### Custodios Depositantes - buscar");
		paginaVO.setRegistrosXPag(50);
		
		paginaVO.setOffset(0);
		getPaginaVO().setRegistros(null);
		ejecutarConsulta();

	}
	
	/**
	 * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
	 */
	public String ejecutarConsulta() {
		if (paginaVO != null) {
	            paginaVO.limpiaResultados();
	    }		 
		log.debug("### Custodios Depositantes - ejecutarConsulta");
			
		setPaginaVO(emisionesCustodiosDepositantesService.consultarEmisionesCustodiosDepositantes(custodiosDto, paginaVO));

		consultaEjecutada = true;
		return null;
	}
	
	/**
	 * Ejecuta la consulta para generar el reporte xml.
	 */
	public void generarReporteXml(ActionEvent evt)
	{	

		PaginaVO resultados = new PaginaVO();
		resultados.setOffset(0);
		resultados.setRegistrosXPag(PaginaVO.TODOS);
		List<MensajeSecuenciaVO> resultado = null;
		try {
			resultado = emisionesCustodiosDepositantesService.consultarEmisionesCustodiosDepositantesXml(custodiosDto, resultados);
					
		String xml = "";
		 if (resultado != null) {
	            for (Iterator iter = resultado.iterator(); iter.hasNext();) {
	            	xml = (String) objectSalidaToXML(iter.next());
	            }
	        }
		
		 downloadFile(xml);
		
		} catch (Exception e) {
			log.error(e.toString());
			
		}

	}
	
	/**
	 * Metodo para generar el XML  COMENTO ESTO
	 */
	public Object objectSalidaToXML(Object vo) throws Exception {
		XStream xstream = new XStream();
        Annotations.configureAliases(xstream, EmisionVO.class);
        Annotations.configureAliases(xstream, MensajeSecuenciaVO.class); 
        Annotations.configureAliases(xstream, CustodioVO.class); 
        Annotations.configureAliases(xstream, DepositanteVO.class); 
        return xstream.toXML(vo);
	 }
	
	
	/**
	 * Metodo para descargar el reporte XML
	 */
	public String downloadFile(String xml) {
	    File file = new File("DepositantesCustodios.xml");
	    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse(); 
	    response.setContentType("application/xml");
	    response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
	    writeOutContent(response, xml, file.getName());
	    FacesContext.getCurrentInstance().responseComplete();
	    return null;
	}
	
	/**
	 * Metodo para escribir el reporte XML
	 */
	private void writeOutContent(final HttpServletResponse res, String xml, final String theFilename) {
	    if (xml == null) {
	        return;
	    }
	    try {    	
	    	ServletOutputStream out = res.getOutputStream();
	        DataOutputStream data = new DataOutputStream(out);
	        data.write(xml.getBytes());
	        out.flush();
	        out.close();	        
	    } catch (final IOException ex) {
	    }
	}

	
	/** Getters and Setters **/
	
	public CustodiosDepositantesDto getCustodiosDto() {
		return custodiosDto;
	}

	public void setCustodiosDto(CustodiosDepositantesDto custodiosDto) {
		this.custodiosDto = custodiosDto;
	}


	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	public void setEmisionesCustodiosDepositantesService(
			EmisionesCustodiosDepositantesService emisionesCustodiosDepositantesService) {
		this.emisionesCustodiosDepositantesService = emisionesCustodiosDepositantesService;
	}

}
