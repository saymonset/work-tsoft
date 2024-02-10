package com.indeval.portalinternacional.middleware.services.validador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.util.ValidarCamposEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesDTO;

public class ValidarCamposEventosCorporativosImp implements	ValidarCamposEventoCorporativo {
	private MessageResolver errorResolver;
	private final String MENSAJE_GENERAL= "Revise los campos requeridos por favor.";
	private static final Logger log = LoggerFactory.getLogger(ValidarCamposEventosCorporativosImp.class);
	public void validaCamposGenerales(EventoCorporativo evco) throws BusinessException {
		// TODO Auto-generated method stub

		
		String msnError="";
		/*
		if (evco.getFechaCreacion()== null )
		{	
			throw new BusinessException("Debe insertar una fecha de Registro."+ MENSAJE_GENERAL);
		}
		else
		{
			if ( evco.getFechaCreacion().after(new Date()))
			{
				throw new BusinessException("La Fecha de Registro debe ser menor a la fecha actual." +MENSAJE_GENERAL);
			}
			
		}
*/
		if (evco.getTipoValor() == null||evco.getTipoValor().trim().equals(""))
		{
			throw new BusinessException(errorResolver.getMessage("J0117")+" "+MENSAJE_GENERAL);
		}

		if (evco.getEmisora() == null||evco.getEmisora().trim().equals(""))
		{
			throw new BusinessException(errorResolver.getMessage("J0118")+" "+MENSAJE_GENERAL);
		}
		if (evco.getSerie() == null||evco.getSerie().trim().equals(""))
		{
			throw new BusinessException(errorResolver.getMessage("J0119")+" "+MENSAJE_GENERAL);
		}
		if (evco.getIsin() == null||evco.getIsin().trim().equals(""))
		{
			throw new BusinessException(errorResolver.getMessage("J0120")+" "+MENSAJE_GENERAL);
		}
		if (evco.getTipoEvento() == null||evco.getTipoEvento().equals("-1"))
		{
			throw new BusinessException(errorResolver.getMessage("J0121")+" "+MENSAJE_GENERAL);
		}
		if (evco.getCustodio() == null)
		{
			throw new BusinessException(errorResolver.getMessage("J0122")+" "+MENSAJE_GENERAL);
		}
		if (evco.getTipoMercado() == null)
		{
			throw new BusinessException("Debe seleccionar un Tipo Mercado. "+MENSAJE_GENERAL);
		}
		
		List<Date> fechas= new ArrayList<Date>();		
		List<String> fechasNombre= new ArrayList<String>();

//		if (evco.getFechaPago() != null)
//		{
//			fechas.add(evco.getFechaPago());	
//			fechasNombre.add("Fecha Pago");
//		}
		if (evco.getFechaIndeval() != null)
		{
			fechas.add(evco.getFechaIndeval());
			fechasNombre.add("Fecha l\u00EDmite Indeval");
		}
		if (evco.getFechaCliente() != null)
		{
			fechas.add(evco.getFechaCliente());
			fechasNombre.add("Fecha l\u00EDmite Participante");
		}
		
		
		String msnValidacion=	validarFechas(fechas,fechasNombre);
		
		if(!msnValidacion.equals(""))
		{			
			msnError=(msnValidacion+MENSAJE_GENERAL);	
			throw new BusinessException(msnError);
		}
		
	
	
	}
	
	private String validarFechas(List<Date> fechas, List<String> nombreDeFechas)
	{

		int cont=0;
		String validacionCorrecta="";
		
		if(fechas.size() > 1)
		{
			for (int i = 0; i < fechas.size(); i++)
			{	cont++;	
			
				if(cont < fechas.size() )
				{
					if(!fechas.get(i).after(fechas.get(cont)) || fechas.get(i).equals(fechas.get(cont)))
					{
						validacionCorrecta="La  "+nombreDeFechas.get(i) +" debe ser mayor a  "+nombreDeFechas.get(cont)+" ";
					}
				}		
			}
		}	

		return validacionCorrecta;
	}

	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}



	public void validaTamanioListaOpcionesEvco(EventoCorporativo params,
			Map<String, OpcionesDTO> mapOpciones )
			throws BusinessException {

		if( params.getTipoEvento().getIdTipoEvento().equals(new Long(2)) && (mapOpciones == null || mapOpciones.size() == 0) )
		{
			throw new BusinessException("El Evento Corporativo debe tener al menos una opci\u00F3n. ");
		}
		int opcactivas = 0;
		if(mapOpciones != null && mapOpciones.size()>0){
			for(OpcionesDTO opc : mapOpciones.values()){			
				if(!opc.isBorradoLogico()){
					opcactivas++;
				}
			}
			if(opcactivas == 0){
				throw new BusinessException("El Evento Corporativo debe tener al menos una opci\u00F3n. ");
			}
		}
	}

	
	public void validaTamanioListaOpcionesEvco(EventoCorporativo params,
			List listaOpciones) throws BusinessException {
		if( params.getTipoEvento().getIdTipoEvento().equals(new Long(2)) && (listaOpciones == null || listaOpciones.size() == 0 ))
		{
			throw new BusinessException("El Evento Corporativo debe tener al menos una opci\u00F3n. ");
		}
		int opcactivas = 0;
		if(listaOpciones != null && listaOpciones.size()>0){
			for(Object obj : listaOpciones){
				OpcionesDTO opc = (OpcionesDTO)obj;
				if(!opc.isBorradoLogico()){
					opcactivas++;
				}
			}
			if(opcactivas == 0){
				throw new BusinessException("El Evento Corporativo debe tener al menos una opci\u00F3n. ");
			}
		}
	}
	
	public void validaNotasEvco(String params) throws BusinessException {


		
		if (!params.trim().equals(""))
		{
			String html=params;
			String target = html.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", "").trim();
			
			if(target.equals(""))
			{
				throw new BusinessException("Debe insertar la descripci\u00F3n de la nota. " + MENSAJE_GENERAL);
			}
		}
		else
		{
			throw new BusinessException("Debe insertar la descripci\u00F3n de la nota. " + MENSAJE_GENERAL);
		}
		
	}

	

	public void validaOpcionesEvco(String params, Date fechaTiPa, Date fechaIn,	Date fechaCli) throws BusinessException {
		if (!params.trim().equals(""))
		{
			String html=params;
			String target = html.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", "").trim();
			
			if(target.equals(""))
			{
				throw new BusinessException("Debe insertar la descripci\u00F3n de la opci\u00F3n. " + MENSAJE_GENERAL);
			}
		}
		else
		{
			throw new BusinessException("Debe insertar la descripci\u00F3n de la opci\u00F3n. " + MENSAJE_GENERAL);
		}
		
		
		List<Date> fechas= new ArrayList<Date>();		
		List<String> fechasNombre= new ArrayList<String>();

//		if (fechaTiPa != null)
//		{
//			fechas.add(fechaTiPa);	
//			fechasNombre.add("Fecha Pago");
//		}
		if (fechaIn != null)
		{
			fechas.add(fechaIn);
			fechasNombre.add("Fecha l\u00EDmite Indeval");
		}
		if (fechaCli != null)
		{
			fechas.add(fechaCli);
			fechasNombre.add("Fecha l\u00EDmite Participante");
		}
		String msnValidacion=	validarFechas(fechas,fechasNombre);
		
		if(!msnValidacion.equals(""))
		{			
			throw new BusinessException(msnValidacion+MENSAJE_GENERAL);
		}
		
	}

	public void validaCuerpoEvco(CuerpoEventoCorporativo params)
			throws BusinessException {
		
		if (!params.getCuerpo().trim().equals(""))
		{
			String html=params.getCuerpo();
			String target = html.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", "").trim();
			
			if(target.equals(""))
			{
				throw new BusinessException("Debe insertar una descripci\u00F3n en el cuerpo del evento. " + MENSAJE_GENERAL);
			}
		}
		else
		{
			throw new BusinessException("Debe insertar una descripci\u00F3n en el cuerpo del evento. " + MENSAJE_GENERAL);
		}
		
	}
	public void validaNotificacionesEvco(String params,String destinatario ,Date fechaIni,
			Date fechaFin) throws BusinessException {
		
		
		log.info("VALIDANDO NOTIFICACIONES::: ");
		List<Date> fechas= new ArrayList<Date>();		
		List<String> fechasNombre= new ArrayList<String>();

		
		if (destinatario.equals("-1"))
		{
			throw new BusinessException("Debe seleccionar un destinatario. "+MENSAJE_GENERAL);
		}
		if (params == null||params.trim().equals(""))
		{
			throw new BusinessException("Debe insertar el Texto Notificaci\u00F3n. "+MENSAJE_GENERAL);
		}
		if (fechaIni != null)
		{
			fechas.add(fechaFin );	
			fechasNombre.add("Fecha Final ");
		}
		else
		{		log.info("FECHAS::: EROR1");
			throw new BusinessException("Debe insertar una Fecha Inicial. "+MENSAJE_GENERAL);
		}
		if (fechaFin != null)
		{		log.info("FECHAS::: EROR2");
			fechas.add(fechaIni);
			fechasNombre.add("Fecha Inicio");
		}
		else
		{
			throw new BusinessException("Debe insertar una Fecha Final. "+MENSAJE_GENERAL);
		}
		
		String msnValidacion=	validarFechas(fechas,fechasNombre);
		
		if(!msnValidacion.equals(""))
		{			
			throw new BusinessException(msnValidacion+MENSAJE_GENERAL);
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Calendar inst = Calendar.getInstance();
		inst.setTimeZone(TimeZone.getTimeZone("Mexico/General"));
		
		String snow= format.format(inst.getTime());

		long now = 0l;
		try {
			now = format.parse(snow).getTime();
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		if(fechaFin.getTime() < (now-21600000)){
			throw new BusinessException("La fecha fin de la Notificaci\u00F3n debe estar en un futuro. "+MENSAJE_GENERAL);
		}
		if(fechaIni.getTime() >= fechaFin.getTime()){
			throw new BusinessException("la fecha Inicio debe ser mayor a la fecha fin. "+MENSAJE_GENERAL);
		}
	}

	public void validarValidacionesEvco(String validacion, String operador,
			String valor) throws BusinessException {
		
		if (validacion.trim().equals("-1"))
		{
			throw new BusinessException("Debe seleccionar un  concepto. "+MENSAJE_GENERAL);
		}
		
		if (operador.trim().equals("-1"))
		{
			throw new BusinessException("Debe seleccionar un  operador. "+MENSAJE_GENERAL);
		}		
		if (StringUtils.isBlank(valor))
		{
			throw new BusinessException("Debe insertar un valor. "+MENSAJE_GENERAL);
		}
		if(!StringUtils.isNumeric(valor)){
			throw new BusinessException("El campo valor debe ser un num\u00E9rico. "+MENSAJE_GENERAL);
		}
		if(StringUtils.isNumeric(valor) && valor.trim().length() > 18){
			throw new BusinessException("El campo valor debe tener una longitud m\u00E1xima de 18 d\u00EDgitos. "+MENSAJE_GENERAL);
		}
		
	}

	public void validaOpcionesDefault(List<OpcionesDTO> listOpciones) throws BusinessException {
		int numOpcionDefault = 0;
		if(listOpciones != null && listOpciones.size() > 0){
			for(OpcionesDTO dto : listOpciones){
				if(!dto.isBorradoLogico() && (StringUtils.isNotEmpty(dto.getValorDefault()) || dto.isDefault())){
					numOpcionDefault++;
				}
			}
			if(numOpcionDefault == 0 ){
				throw new BusinessException(" Debe existir una opci\u00F3n por default. ");
			}
			if(numOpcionDefault > 1){
				throw new BusinessException("No pueden existir m\u00E1s de una opci\u00F3n por default. ");
			}
		}		
	}

	public void validaAdjuntos(List<AdjuntosEventoCorporativo> listAdjuntos)
			throws BusinessException {
		if(listAdjuntos != null && listAdjuntos.size() >0){
			for(AdjuntosEventoCorporativo adj  : listAdjuntos){
				if (adj.getDescripcion().length() > 400){
					throw new BusinessException("Nombre de archivo adjunto superior a 400 caracteres. ");
				}
			}
		
		}
	}
	
}

