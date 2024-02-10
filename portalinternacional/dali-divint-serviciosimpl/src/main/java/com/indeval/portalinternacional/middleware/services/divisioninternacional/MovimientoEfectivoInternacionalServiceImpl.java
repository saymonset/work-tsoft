// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoDepositoEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoRetiroEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaMovEfeDivExtVO;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;
import com.indeval.portalinternacional.persistence.dao.MovimientoEfectivoInternacionalDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovimientoEfectivoInternacionalServiceImpl implements MovimientoEfectivoInternacionalService {
	
	private static final String DEPOSITO = "0";
	
	private static final String RETIRO = "1";
	
	private MovimientoEfectivoInternacionalDao movimientoEfectivoInternacionalDao;
	
	@Override
	public MovimientoRetiroEfectivoInternacional getMovimientoRetiroEfectivoInternacionalByPk(Long idMovimientoRetiroEfectivoInternacional) {
		return (MovimientoRetiroEfectivoInternacional)this.movimientoEfectivoInternacionalDao.getByPk(MovimientoRetiroEfectivoInternacional.class, idMovimientoRetiroEfectivoInternacional);
	}
	
	@Override
	public MovimientoDepositoEfectivoInternacional getMovimientoDepositoEfectivoInternacionalByPk(Long idMovimientoDepositoEfectivoInternacional) {
		return (MovimientoDepositoEfectivoInternacional)this.movimientoEfectivoInternacionalDao.getByPk(MovimientoDepositoEfectivoInternacional.class, idMovimientoDepositoEfectivoInternacional);
	}

	@Override
	public void saveMovimientoEfectivoInternacional(MovimientoEfectivoInternacionalVO efectivoInternacionalVO) {
		if(RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento())) {
			final MovimientoRetiroEfectivoInternacional retiro = getMovimientoRetiroEfectivoIntByDTO(efectivoInternacionalVO);
			// retiro.setFechaAlta(new Date());
			retiro.setEstadoMovEfectivoInt(Constantes.ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO);
			retiro.setEstadoLiqIndeval(Constantes.ESTADO_LIQ_INDEVAL_REGISTRADO);
			movimientoEfectivoInternacionalDao.saveMovimientoRetiroEfectivoInternacional(retiro);

			efectivoInternacionalVO.setIdMovimiento(retiro.getIdMovimientoEfectivoInt());
			efectivoInternacionalVO.setFechaAlta(retiro.getFechaAlta());
			efectivoInternacionalVO.setFechaUltimaModificacion(retiro.getFechaUltimaModificacion());
			efectivoInternacionalVO.setEstadoMovimiento(retiro.getEstadoMovEfectivoInt());
			efectivoInternacionalVO.setEstadoLiqIndeval(retiro.getEstadoLiqIndeval());
		}else if(DEPOSITO.equals(efectivoInternacionalVO.getTipoMovimiento())) {
			final MovimientoDepositoEfectivoInternacional deposito = getMovimientoDepositoEfectivoIntByDTO(efectivoInternacionalVO);
			// deposito.setFechaAlta(new Date());
			deposito.setEstadoMovEfectivoInt(Constantes.ID_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO);
			deposito.setEstadoLiqIndeval(Constantes.ESTADO_LIQ_INDEVAL_REGISTRADO);
			movimientoEfectivoInternacionalDao.saveMovimientoDepositoEfectivoInternacional(deposito);

			efectivoInternacionalVO.setIdMovimiento(deposito.getIdMovimientoEfectivoInt());
			efectivoInternacionalVO.setFechaAlta(deposito.getFechaAlta());
			efectivoInternacionalVO.setFechaUltimaModificacion(deposito.getFechaUltimaModificacion());
			efectivoInternacionalVO.setEstadoMovimiento(deposito.getEstadoMovEfectivoInt());
			efectivoInternacionalVO.setEstadoLiqIndeval(deposito.getEstadoLiqIndeval());
		}
	}

	@Override
	public void deleteMovimientoEfectivoInternacional(MovimientoEfectivoInternacionalVO efectivoInternacionalVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMovimientoEfectivoInternacional(MovimientoEfectivoInternacionalVO efectivoInternacionalVO) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateMovimientoRetiroEfectivoInternacional(MovimientoRetiroEfectivoInternacional movimientoRetiroEfectivoInternacional) {
		this.movimientoEfectivoInternacionalDao.update(movimientoRetiroEfectivoInternacional);
	}
	
	@Override
	public void updateMovimientoDepositoEfectivoInternacional(MovimientoDepositoEfectivoInternacional movimientoDepositoEfectivoInternacional) {
		this.movimientoEfectivoInternacionalDao.update(movimientoDepositoEfectivoInternacional);
	}
	
	@Override
	public void updateEstatusMovimientosEfectivoInternacional(List<String> idsMovimientosEfectivo, Long estadoMovEfectivoIntAsignar) {
		List<Long> idsRetiros = new ArrayList<>();
		List<Long> idsDepositos = new ArrayList<>();
		
		if(idsMovimientosEfectivo != null && idsMovimientosEfectivo.size() > 0) {
			for(String idMovimientoEfectivo : idsMovimientosEfectivo) {
				if(Constantes.PREFIJO_ID_RETIRO.equals(idMovimientoEfectivo.substring(0, 2))) {
					idsRetiros.add(Long.valueOf(idMovimientoEfectivo.substring(2, idMovimientoEfectivo.length())));
				}
				
				if(Constantes.PREFIJO_ID_DEPOSITO.equals(idMovimientoEfectivo.substring(0, 2))) {
					idsDepositos.add(Long.valueOf(idMovimientoEfectivo.substring(2, idMovimientoEfectivo.length())));
				}
			}
		}
		
		if(idsRetiros.size() > 0) {
			this.movimientoEfectivoInternacionalDao.updateEstatusRetirosEfectivoInternacional(idsRetiros, estadoMovEfectivoIntAsignar);
		}
		
		if(idsDepositos.size() > 0) {
			this.movimientoEfectivoInternacionalDao.updateEstatusDepositosEfectivoInternacional(idsDepositos, estadoMovEfectivoIntAsignar);
		}
	}
	
	@Override
	public PaginaVO getMovimientosEfectivoInternacional(CriteriosConsultaMovEfeDivExtVO criteriosConsulta, PaginaVO paginaVO) {
		return this.movimientoEfectivoInternacionalDao.getMovimientosEfectivoInternacional(criteriosConsulta, paginaVO);
	}
	
	private MovimientoRetiroEfectivoInternacional getMovimientoRetiroEfectivoIntByDTO(MovimientoEfectivoInternacionalVO efectivoInternacionalVO) {
		final MovimientoRetiroEfectivoInternacional movEfecInt = new MovimientoRetiroEfectivoInternacional();
		movEfecInt.setEstadoLiqIndeval(1L);
		movEfecInt.setEstadoMovEfectivoInt(1L);
		movEfecInt.setFechaAlta(efectivoInternacionalVO.getFechaAlta());
		movEfecInt.setFechaUltimaModificacion(efectivoInternacionalVO.getFechaAlta());
		movEfecInt.setFechaLiquidacion(efectivoInternacionalVO.getFechaLiquidacion());
		movEfecInt.setTipoMensajee(efectivoInternacionalVO.getTipoMensaje());
		movEfecInt.setIdCuenta(efectivoInternacionalVO.getIdCuenta());
		movEfecInt.setIdBoveda(efectivoInternacionalVO.getBoveda().getIdBoveda().longValue());
		movEfecInt.setIdDivisa(efectivoInternacionalVO.getDivisa().getId());
		movEfecInt.setSaldoDisponible(efectivoInternacionalVO.getSaldoDisponible());
		movEfecInt.setImporteTraspasar(efectivoInternacionalVO.getImporteTraspasar());
		movEfecInt.setSaldoEfectivo(efectivoInternacionalVO.getSaldoEfectivo());
		movEfecInt.setNotasComentarios(efectivoInternacionalVO.getNotasComentarios());
		movEfecInt.setReferenciaNumerica(efectivoInternacionalVO.getReferenciaNumerica());
		movEfecInt.setReferenciaRelacionada(efectivoInternacionalVO.getReferenciaRelacionada());
		movEfecInt.setIntermediaryOption(efectivoInternacionalVO.getIntermediaryOption());
		movEfecInt.setIntermediaryPartyId(efectivoInternacionalVO.getIntermediaryValue());
		movEfecInt.setIntermediaryNameAddress(efectivoInternacionalVO.getIntermediaryNameAddress());
		movEfecInt.setIntermediaryBic(efectivoInternacionalVO.getIntermediaryBic());
		movEfecInt.setAccountOption(efectivoInternacionalVO.getAccountOption());
		movEfecInt.setAccountPartyId(efectivoInternacionalVO.getAccountValue());
		movEfecInt.setAccountNameAddress(efectivoInternacionalVO.getAccountNameAddress());
		movEfecInt.setAccountBic(efectivoInternacionalVO.getAccountBic());
		movEfecInt.setAccountLocation(efectivoInternacionalVO.getAccountLocation());
		movEfecInt.setBeneficiaryOption(efectivoInternacionalVO.getBeneficiaryOption());
		movEfecInt.setBeneficiaryPartyId(Constantes.MT_202.equals(efectivoInternacionalVO.getTipoMensaje()) ? efectivoInternacionalVO.getBeneficiaryValue() : null);
		movEfecInt.setBeneficiaryAccount(Constantes.MT_103.equals(efectivoInternacionalVO.getTipoMensaje()) ? efectivoInternacionalVO.getBeneficiaryValue() : null);
		movEfecInt.setBeneficiaryNameAddress(efectivoInternacionalVO.getBeneficiaryNameAddress());
		movEfecInt.setBeneficiaryNumberName(efectivoInternacionalVO.getBeneficiaryNumberName());
		movEfecInt.setBeneficiaryBic(efectivoInternacionalVO.getBeneficiaryBic());
		movEfecInt.setFolioControl(efectivoInternacionalVO.getFolioControl());
		movEfecInt.setIdCatbic(efectivoInternacionalVO.getIdCatbic());
		return movEfecInt;
	}
	
	private MovimientoDepositoEfectivoInternacional getMovimientoDepositoEfectivoIntByDTO(MovimientoEfectivoInternacionalVO efectivoInternacionalVO) {
		final MovimientoDepositoEfectivoInternacional movEfecInt = new MovimientoDepositoEfectivoInternacional();
		movEfecInt.setEstadoLiqIndeval(1L);
		movEfecInt.setEstadoMovEfectivoInt(1L);
		movEfecInt.setFechaAlta(efectivoInternacionalVO.getFechaAlta());
		movEfecInt.setFechaUltimaModificacion(efectivoInternacionalVO.getFechaAlta());
		movEfecInt.setFechaLiquidacion(efectivoInternacionalVO.getFechaLiquidacion());
		movEfecInt.setIdCuenta(efectivoInternacionalVO.getIdCuenta());
		movEfecInt.setIdBoveda(efectivoInternacionalVO.getBoveda().getIdBoveda().longValue());
		movEfecInt.setIdDivisa(efectivoInternacionalVO.getDivisa().getId());
		movEfecInt.setSaldoDisponible(efectivoInternacionalVO.getSaldoDisponible());
		movEfecInt.setImporteTraspasar(efectivoInternacionalVO.getImporteTraspasar());
		movEfecInt.setSaldoEfectivo(efectivoInternacionalVO.getSaldoEfectivo());
		movEfecInt.setFolioControl(efectivoInternacionalVO.getFolioControl());
		movEfecInt.setIdCatbic(efectivoInternacionalVO.getIdCatbic());
		movEfecInt.setReferenciaRelacionada(efectivoInternacionalVO.getReferenciaRelacionada());
		return movEfecInt;
	}

	public MovimientoEfectivoInternacionalDao getMovimientoEfectivoInternacionalDao() {
		return movimientoEfectivoInternacionalDao;
	}

	public void setMovimientoEfectivoInternacionalDao(
			MovimientoEfectivoInternacionalDao movimientoEfectivoInternacionalDao) {
		this.movimientoEfectivoInternacionalDao = movimientoEfectivoInternacionalDao;
	}

	@Override
	public Long getFolioControl() {
		return this.movimientoEfectivoInternacionalDao.getFolioControl();
	}
	
	@Override
	public boolean esUsuarioPermitidoAutorizar(String usuario, Long folioControl) {
		List<Integer> idsOperacionTransaccion = new ArrayList<Integer>();
		idsOperacionTransaccion.add(Constantes.ID_OP_TR_REGISTRO_MOV_EFE_DIV_EXT);
		
		return this.movimientoEfectivoInternacionalDao.esUsuarioPermitidoTransaccion(usuario, idsOperacionTransaccion, folioControl, true);
	}
	
	@Override
	public boolean esUsuarioPermitidoLiberar(String usuario, Long folioControl) {
		List<Integer> idsOperacionTransaccion = new ArrayList<Integer>();
		idsOperacionTransaccion.add(Constantes.ID_OP_TR_REGISTRO_MOV_EFE_DIV_EXT);
		idsOperacionTransaccion.add(Constantes.ID_OP_TR_AUTORIZACION_MOV_EFE_DIV_EXT);
		
		return this.movimientoEfectivoInternacionalDao.esUsuarioPermitidoTransaccion(usuario, idsOperacionTransaccion, folioControl, true);
	}
	
	@Override
	public boolean esUsuarioPermitidoCancelarAutorizado(String usuario, Long folioControl) {
		List<Integer> idsOperacionTransaccion = new ArrayList<Integer>();
		idsOperacionTransaccion.add(Constantes.ID_OP_TR_AUTORIZACION_MOV_EFE_DIV_EXT);
		
		return this.movimientoEfectivoInternacionalDao.esUsuarioPermitidoTransaccion(usuario, idsOperacionTransaccion, folioControl, false);
	}
}
