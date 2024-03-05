package com.indeval.portaldali.middleware.services.efectivo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.util.Constantes;
import com.indeval.portaldali.middleware.util.OperacionEfectivoBuilder;
import com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO;
import com.indeval.portaldali.persistence.model.EstadoInstruccion;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;

public class MovimientosEfectivoServiceImpl implements MovimientosEfectivoService{

	private final Logger logger = LoggerFactory.getLogger(MovimientosEfectivoServiceImpl.class);

	private RetiroEfectivoService retiroEfectivoService;
	
	private InstitucionDaliDAO institucionDao;
	private DivisaDaliDAO divisaDao;
	private BovedaDaliDAO bovedaDao;
	
	public RetiroEfectivoDTO findById(long idOperacionEfectivo) {
		return retiroEfectivoService.findById(idOperacionEfectivo);
	}
	
	@Transactional
    public String guardarOperacion(TraspasoEfectivoVO traspaso, Map<String, String> datosFirma){
    	logger.debug("guardaOperacion");
    	
    	String cuentaEmisor = traspaso.getCuentaOrdenante();
    	String cuentaReceptor = traspaso.getCuentaBeneficiaria();
    	String divisa =  traspaso.getDivisa();
    	String boveda = traspaso.getBoveda();
    	
    	String claveFolioEmisor = obtenerClaveFolioInstitucionPorCuenta(cuentaEmisor);
    	String claveFolioReceptor = obtenerClaveFolioInstitucionPorCuenta(cuentaReceptor);
    	
    	InstitucionDTO institucionEmisor = obtenerInstitucionPorClaveFolio(claveFolioEmisor);
    	InstitucionDTO institucionReceptor = obtenerInstitucionPorClaveFolio(claveFolioReceptor);
    	DivisaDTO divisaDto = obtenerDivisaPorClave(divisa);
    	BovedaDTO bovedaDto = obtenerBovedaPorNombreCorto(boveda);
    	
    	OperacionEfectivoBuilder builder = OperacionEfectivoBuilder.newInstance(Constantes.TIPO_OPERACION__TRANSFERENCIA_EFECTIVO)
    	.withOrigen(Constantes.ORIGEN__PORTAL_DALI)
    	.withIdTipoInstruccion(Constantes.ID_TIPO_INSTRUCCION__TREF)
    	.withCuentaEmisor(cuentaEmisor)
    	.withCuentaReceptor(cuentaReceptor)
    	.withImporte(traspaso.getMonto())
    	.withReferenciaMensaje(traspaso.getReferenciaMensaje())
    	.withReferenciaOperacion(traspaso.getReferenciaOperacion())
    	.withIdDivisa(divisaDto.getId())
    	.withIdBoveda(bovedaDto.getId())
    	.withIdEstado(EstadoInstruccion.REGISTRADA_VALUE)
    	.withFechaRegistro(traspaso.getFechaRegistro())
    	.withUsuarioRegistro(datosFirma.get(Constantes.DATOS_FIRMA__USUARIO))
    	.withSerieRegistro(datosFirma.get(Constantes.DATOS_FIRMA__SERIE))
    	.withFirmaRegistro(datosFirma.get(Constantes.DATOS_FIRMA__ISO_FIRMADO))
    		
    	.withIdInstitucionEmisor((int)institucionEmisor.getId())
    	.withIdInstitucionReceptor((int) institucionReceptor.getId());
    	
        HashMap<String, String> datosAdicionales = new HashMap<String, String>();
		datosAdicionales.put("ordenante", traspaso.getOrdenante());
        datosAdicionales.put("beneficiario", traspaso.getBeneficiario());
        datosAdicionales.put("usrCredencial", datosFirma.get(Constantes.DATOS_FIRMA__TICKET));
        
        RetiroEfectivoDTO operacionEfectivo = builder.build();
		
		retiroEfectivoService.registra(operacionEfectivo);
		
		return operacionEfectivo.getReferenciaOperacion();
    }
    
	@Transactional
    public String guardarOperacion(RetiroEfectivoVO retiro, Map<String, String> datosFirma){
    	logger.debug("guardaOperacion");
    	
    	String conceptoPago = retiro.getConceptoPago(); //SPEI | SIAC
    	String cuentaEmisor = retiro.getCuentaOrdenante();
    	String cuentaReceptor = retiro.getCuentaBeneficiaria();
    	String divisa = retiro.getDivisa();
    	String boveda = retiro.getBoveda();
    	
    	String claveFolioEmisor = obtenerClaveFolioInstitucionPorCuenta(cuentaEmisor);
    	String claveFolioReceptor = obtenerClaveFolioInstitucionPorCuenta(cuentaReceptor);
    	
    	InstitucionDTO institucionEmisor = obtenerInstitucionPorClaveFolio(claveFolioEmisor);
    	InstitucionDTO institucionReceptor = obtenerInstitucionPorClaveFolio(claveFolioReceptor);
    	DivisaDTO divisaDto = obtenerDivisaPorClave(divisa);
    	BovedaDTO bovedaDto = obtenerBovedaPorNombreCorto(boveda);
    	
    	OperacionEfectivoBuilder builder = OperacionEfectivoBuilder.newInstance(conceptoPago)
    	.withOrigen(Constantes.ORIGEN__PORTAL_DALI)
    	.withIdTipoInstruccion(Constantes.ID_TIPO_INSTRUCCION__RETE)
    	.withImporte(retiro.getMonto())
    	.withCuentaEmisor(cuentaEmisor)
    	.withCuentaReceptor(cuentaReceptor)
    	.withReferenciaMensaje(retiro.getReferenciaMensaje())
    	.withReferenciaOperacion(retiro.getReferenciaOperacion())
    	.withReferenciaNumerica(retiro.getReferenciaNumericaSPEI())
    	.withConceptoPago(conceptoPago)
    	.withIdDivisa(divisaDto.getId())
    	.withIdBoveda(bovedaDto.getId())
    	.withIdEstado(EstadoInstruccion.REGISTRADA_VALUE)
    	.withFechaRegistro(retiro.getFechaRegistro())
    	.withUsuarioRegistro(datosFirma.get(Constantes.DATOS_FIRMA__USUARIO))
    	.withSerieRegistro(datosFirma.get(Constantes.DATOS_FIRMA__SERIE))
    	.withFirmaRegistro(datosFirma.get(Constantes.DATOS_FIRMA__ISO_FIRMADO))
    	
    	.withIdInstitucionEmisor(institucionEmisor.getId())
    	.withIdInstitucionReceptor(institucionReceptor.getId());
		
    	        
        
        HashMap<String, String> datosAdicionales = new HashMap<String, String>();
		datosAdicionales.put("ordenante", retiro.getOrdenante());
        datosAdicionales.put("beneficiario", retiro.getBeneficiario());
        datosAdicionales.put("tipoRetiro", retiro.getConceptoPago());
		datosAdicionales.put("referenciaNumericaSPEI", retiro.getReferenciaNumericaSPEI());
		// EL TICKET
		//datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL, (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SeguridadConstants.TICKET_SESION));		
        
		RetiroEfectivoDTO operacionEfectivo = builder.build();
		
		
		retiroEfectivoService.registra(operacionEfectivo);
		
		return operacionEfectivo.getReferenciaOperacion();
    }
    
	@Transactional
    public String guardarOperacion(RetiroEfectivoDTO retiroDTO, Map<String, String> datosFirma) {
    	OperacionEfectivoBuilder builder = OperacionEfectivoBuilder.newInstance(Constantes.TIPO_OPERACION__RETIRO_CCS)
		.withOrigen(Constantes.ORIGEN__PORTAL_DALI)
		.withIdTipoInstruccion(Constantes.ID_TIPO_INSTRUCCION__RETE)
		.withIdBoveda(retiroDTO.getBoveda().getId())
		.withReferenciaNumerica(retiroDTO.getReferencia())
		.withConceptoPago(retiroDTO.getConceptoPago())
		
		.withIdInstitucionEmisor(retiroDTO.getInstitucion().getId())
		.withIdInstitucionReceptor(retiroDTO.getIdInstReceptor().getId())
		.withCuentaEmisor(retiroDTO.getInstitucion().getCuentaClabe())
		.withCuentaReceptor(retiroDTO.getCuentaBeneficiario())
		// .withNombreReceptor(retiroDTO.getNombreBeneficiario())
		.withIdDivisa(retiroDTO.getDivisa().getId())
		.withImporte(retiroDTO.getImporteTraspaso())
		.withReferenciaOperacion(retiroDTO.getReferenciaOperacion())
		.withReferenciaMensaje(retiroDTO.getReferenciaMensaje())
		.withIdEstado(EstadoInstruccion.REGISTRADA_VALUE)
		.withFechaRegistro(new Date())
    	.withUsuarioRegistro(datosFirma.get(Constantes.DATOS_FIRMA__USUARIO))
    	.withSerieRegistro(datosFirma.get(Constantes.DATOS_FIRMA__SERIE))
    	.withFirmaRegistro(datosFirma.get(Constantes.DATOS_FIRMA__ISO_FIRMADO));
		
		RetiroEfectivoDTO operacionEfectivo = builder.build();
		
		retiroEfectivoService.registra(operacionEfectivo);
		
		return operacionEfectivo.getReferenciaOperacion();
    }
    
	
	private InstitucionDTO obtenerInstitucionPorClaveFolio(String claveFolio) {
		InstitucionDTO result = institucionDao.buscarInstitucionPorClaveYFolio(claveFolio);
		
		if(result == null)throw new BusinessException(String.format("No se encontro la institucion con clave folio []", claveFolio));
		
		return result;
	}
	
	private DivisaDTO obtenerDivisaPorClave(String clave) {
		DivisaDTO result = divisaDao.obtenerDivisaPorClaveAlfavetica(clave);
		
		if(result == null)throw new BusinessException(String.format("No se encontro la boveda con la clave [%s]", clave));
		
		return result;
	}
	
	private BovedaDTO obtenerBovedaPorNombreCorto(String nombreCorto) {
		BovedaDTO  bovedaDto = new BovedaDTO();
		bovedaDto.setNombreCorto(nombreCorto);
		
		BovedaDTO result = bovedaDao.buscarBovedaPorTipoCustodia(bovedaDto);
		
		if(result == null) throw new BusinessException(String.format("No se encontro la boveda con nombre [%s]", nombreCorto));
		
		return result;
	}
    private String obtenerClaveFolioInstitucionPorCuenta(String cuenta) {
    	String claveFolio = null;
    	
    	if (StringUtils.isNotBlank(cuenta)) {	
			if (cuenta.length() == 7) {
				claveFolio = cuenta.substring(0, 5);
			} else if (cuenta.length() == 18) {
				claveFolio = cuenta.substring(8, 13);
			}
			
			logger.debug("claveFolio: " + claveFolio );
			
		}
    	
    	if(StringUtils.isBlank(claveFolio)) throw new BusinessException("No se pudo obtener la clave folio a partir de la cuenta [%s]", cuenta);
    	
    	return claveFolio;
    }

	public void setRetiroEfectivoService(RetiroEfectivoService retiroEfectivoService) {
		this.retiroEfectivoService = retiroEfectivoService;
	}

	public void setInstitucionDao(InstitucionDaliDAO institucionDao) {
		this.institucionDao = institucionDao;
	}

	public void setDivisaDao(DivisaDaliDAO divisaDao) {
		this.divisaDao = divisaDao;
	}

	public void setBovedaDao(BovedaDaliDAO bovedaDao) {
		this.bovedaDao = bovedaDao;
	}
}
