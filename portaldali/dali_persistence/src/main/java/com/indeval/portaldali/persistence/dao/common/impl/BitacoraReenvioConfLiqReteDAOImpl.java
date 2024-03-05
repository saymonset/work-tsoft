package com.indeval.portaldali.persistence.dao.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.modelo.to.commons.BitacoraReenvioConfLiqReteDTO;
import com.indeval.portaldali.persistence.dao.common.BitacoraReenvioConfLiqReteDAO;
import com.indeval.portaldali.persistence.model.BitacoraReenvioConfLiqRete;

public class BitacoraReenvioConfLiqReteDAOImpl extends BaseDaoHibernateImpl implements BitacoraReenvioConfLiqReteDAO {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,readOnly = false,rollbackFor={Exception.class})
	public void saveBitacora(List<BitacoraReenvioConfLiqReteDTO> retes) {
		List<BitacoraReenvioConfLiqRete> entities = new ArrayList<BitacoraReenvioConfLiqRete>();
		for (BitacoraReenvioConfLiqReteDTO dto : retes) {
			BitacoraReenvioConfLiqRete brl = new BitacoraReenvioConfLiqRete();
			brl.setBoveda(dto.getBoveda());
			brl.setCasfimInstitucionReceptora(dto.getCasfimInstitucionReceptora());
			brl.setCasfimInstitucionTraspasante(dto.getCasfimInstitucionTraspasante());
			brl.setClabe(dto.getClabe());
			brl.setClaveRastreo(dto.getClaveRastreo());
			brl.setConcepto(dto.getConcepto());
			brl.setCuentaReceptora(dto.getCuentaReceptora());
			brl.setCuentaTraspasante(dto.getCuentaTraspasante());
			brl.setDescErrorReenvio(dto.getDescErrorReenvio());
			brl.setDescripcionEstado(dto.getDescripcionEstado());
			brl.setDivisa(dto.getDivisa());
			brl.setEstadoInstruccion(dto.getEstadoInstruccion());
			brl.setFechaLiquidacion(dto.getFechaLiquidacion());
			brl.setFechaReenvio(dto.getFechaReenvio());
			brl.setFechaRegistro(dto.getFechaRegistro());
			brl.setFolioInstLiquidacion(dto.getFolioInstLiquidacion());
			brl.setFolioInstruccion(dto.getFolioInstruccion());
			brl.setFolioOrigen(dto.getFolioOrigen());
			brl.setIdInstEfec(dto.getIdInstEfec());
			brl.setIdUsuario(dto.getIdUsuario());
			brl.setInstitucionOrigen(dto.getInstitucionOrigen());
			brl.setInstitucionReceptora(dto.getInstitucionReceptora());
			brl.setInstitucionTraspasante(dto.getInstitucionTraspasante());
			brl.setIp(dto.getIp());
			brl.setLiqSpei(dto.getLiqSpei());
			brl.setMonto(dto.getMonto());
			brl.setReferenciaNumerica(dto.getReferenciaNumerica());
			brl.setReferenciaOperacion(dto.getReferenciaOperacion());
			brl.setReferenciaParticipante(dto.getReferenciaParticipante());
			brl.setResultadoReenvio(dto.getResultadoReenvio());
			brl.setTipoInstruccion(dto.getTipoInstruccion());
			brl.setTipoMensaje(dto.getTipoMensaje());
			brl.setTipoRetiro(dto.getTipoRetiro());
			entities.add(brl);
		}

		getHibernateTemplate().saveOrUpdateAll(entities);
	}

}
