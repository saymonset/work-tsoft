/**
 * 
 */
package com.indeval.portaldali.middleware.services.advice;

import java.math.BigInteger;
import java.util.Date;

import javax.jms.TextMessage;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.dto.BitacoraIngresosConsulta;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.ingresos.IngresosDao;
import com.indeval.portaldali.persistence.model.ConsultaIngresos;
import com.indeval.portaldali.persistence.model.LogConsultaIngresos;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Implementacion del servicio que consume los mensajes de 
 * la bitacora de consultas
 * 
 * @author Rafael Ibarra
 *
 */
public class ReceiverBitacoraConsultaServiceImpl implements
		ReceiverBitacoraConsultaService {
	
	/** Logger de la clase */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** DAO para utlidades de los ingresos */
	private IngresosDao ingresosDao;

	/**
	 * @see com.indeval.portaldali.middleware.services.advice.ReceiverBitacoraConsultaService#recibeMensajes(javax.jms.TextMessage)
	 */
	public void recibeMensajes(TextMessage textMessage) {
		logger.debug("Entrando a ReceiverBitacoraConsultaServiceImpl.recibeMensajes");
		try {
			// Parsea el mensaje desde XML
			XStream parser = new XStream(new DomDriver());
			parser.alias("bitacoraIngresos", BitacoraIngresosConsulta.class);
			BitacoraIngresosConsulta bitacora = (BitacoraIngresosConsulta)parser.fromXML(textMessage.getText());
			logger.debug("Mensaje de texto: [" + ToStringBuilder.reflectionToString(bitacora,ToStringStyle.MULTI_LINE_STYLE) + "]");
			
			if( bitacora != null ) {
			
				// Si la institucion primaria debe de dejar consulta
				if( ingresosDao.debeDejarBitacoraInstitucion(bitacora.getInstitucionPrimaria()) ) {
			
					LogConsultaIngresos logConsultaIngresos = new LogConsultaIngresos();
					
					ConsultaIngresos consultaIngresos = ingresosDao.getConsultaIngresosPorNombre(bitacora.getNombreConsulta());
					if( consultaIngresos == null ) {
						throw new BusinessException( "Consulta no encontrada." );
					}
					
					if( consultaIngresos.getBitacorable() ) {
						logConsultaIngresos.setInstitucion(ingresosDao.getInstitucionPorIdFolio(bitacora.getInstitucion()));
						logConsultaIngresos.setConsultaIngresos(consultaIngresos);
						logConsultaIngresos.setTicket(bitacora.getTicket());
						logConsultaIngresos.setNumeroRegistros( BigInteger.valueOf(bitacora.getNumeroRegistros()) );
						logConsultaIngresos.setIdTipoConsulta(bitacora.isRepresentaTotal()?1:2);
						logConsultaIngresos.setFechaHoraRegistro(new Date());
						ingresosDao.save(logConsultaIngresos);
					} else {
						logger.info("Consulta no bitacorable");
					}
			
				} else {
					logger.info("La institucion no debe de dejar mensaje: [" + bitacora.getInstitucionPrimaria() + "]");
				}
			} else {
				throw new BusinessException( "El objeto no se pudo convertir o es nulo." );
			}
		} catch (Exception e) {
			logger.error("Error al procesar el mensaje: [" + textMessage.toString() + "]", e);
		}
	}

	/**
	 * @param ingresosDao the ingresosDao to set
	 */
	public void setIngresosDao(IngresosDao ingresosDao) {
		this.ingresosDao = ingresosDao;
	}
	
}
