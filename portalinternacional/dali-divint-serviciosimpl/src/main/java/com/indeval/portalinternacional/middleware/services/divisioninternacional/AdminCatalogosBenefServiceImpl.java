package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExcepcionEmisionBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.vo.SicEmisionVO;
import com.indeval.portalinternacional.persistence.dao.EmisionDao;
import com.indeval.portalinternacional.persistence.dao.ExcepcionEmisionBenefDao;
import com.indeval.portalinternacional.persistence.dao.HorarioBeneficiarioDao;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;

public class AdminCatalogosBenefServiceImpl implements AdminCatalogosBenefService{
	
	private ExcepcionEmisionBenefDao excepcionEmisionBenefDao;
	private HorarioBeneficiarioDao horarioBeneficiarioDao;
	private EmisionDao emisionDao;
	private SicEmisionDao sicEmisionDao;

	public List<ExcepcionEmisionBenef> getEmisionesSinBenef() {		
		return excepcionEmisionBenefDao.findExecepcionesEmision();
	}

	public List<HorarioBeneficiario> getHorariosCustodios() {		
		return horarioBeneficiarioDao.findHorariosDerecho();
	}

	public void eliminarEmisionSinBenef(ExcepcionEmisionBenef emisionBenef) {
		ExcepcionEmisionBenef emisionBenefTmp = null;
		if(emisionBenef != null && emisionBenef.getIdExcepcionEmision() != null){
			emisionBenefTmp = (ExcepcionEmisionBenef)excepcionEmisionBenefDao.getByPk(ExcepcionEmisionBenef.class, emisionBenef.getIdExcepcionEmision());
			emisionBenefTmp.setEliminado(Boolean.TRUE);
			emisionBenefTmp.setFechaEliminacion(emisionBenef.getFechaEliminacion());
			excepcionEmisionBenefDao.update(emisionBenefTmp);
		}
	}

	public void eliminarHorario(HorarioBeneficiario horarioBeneficiario) {
		HorarioBeneficiario horarioBeneficiarioTmp = null;
		if(horarioBeneficiario != null && horarioBeneficiario.getIdHorario() != null){
			horarioBeneficiarioTmp = (HorarioBeneficiario)horarioBeneficiarioDao.getByPk(HorarioBeneficiario.class, horarioBeneficiario.getIdHorario());
			horarioBeneficiarioTmp.setEliminado(Boolean.TRUE);
			horarioBeneficiarioTmp.setFechaEliminacion(horarioBeneficiario.getFechaEliminacion());
			horarioBeneficiarioDao.update(horarioBeneficiarioTmp);
		}
	}

	public void agregarEmisionSinBenef(ExcepcionEmisionBenef emisionBenef)throws BusinessException {
		String error = null;		
		if(emisionBenef != null){
			emisionBenef.setEliminado(Boolean.FALSE);			
			error = validaEmision(emisionBenef);
			if(StringUtils.isBlank(error)){
				if(excepcionEmisionBenefDao.findExecepcionesEmision(emisionBenef) == null){
					excepcionEmisionBenefDao.save(emisionBenef);
				}else{
					error = "La emision ya existe.";
				}
			}
			
			if(StringUtils.isNotBlank(error)){
				throw new BusinessException(error);
			}
		}
	}

	public void agregarHorario(HorarioBeneficiario horarioBeneficiario)throws BusinessException {
		String error = null;
		if(horarioBeneficiario != null){
			horarioBeneficiario.setEliminado(Boolean.FALSE);
			error = validaHorario(horarioBeneficiario);			
			if( StringUtils.isBlank(error)){
				if(horarioBeneficiarioDao.findHorario(horarioBeneficiario) == null){
					horarioBeneficiarioDao.save(horarioBeneficiario);
				}else{
					error = "El horario ya existe.";
				}
			}			
			if(StringUtils.isNotBlank(error)){
				throw new BusinessException(error);
			}
		}		
	}

	public void actualizarEmisionSinBenef(ExcepcionEmisionBenef emisionBenef)throws BusinessException {
		ExcepcionEmisionBenef emisionBenefTmp = null;
		String error = null;
		
		if(emisionBenef != null && emisionBenef.getIdExcepcionEmision() != null){			
			error = validaEmision(emisionBenef);
			if(StringUtils.isBlank(error)){
				if(excepcionEmisionBenefDao.findExecepcionesEmision(emisionBenef) == null){
					emisionBenefTmp = (ExcepcionEmisionBenef)excepcionEmisionBenefDao.getByPk(ExcepcionEmisionBenef.class, emisionBenef.getIdExcepcionEmision());
					emisionBenefTmp.setEmisora(emisionBenef.getEmisora());
					emisionBenefTmp.setIsin(emisionBenef.getIsin());
					emisionBenefTmp.setSerie(emisionBenef.getSerie());
					emisionBenefTmp.setTv(emisionBenef.getTv());
					excepcionEmisionBenefDao.update(emisionBenefTmp);
				}else{
					error = "La emision ya existe.";
				}				
			}
		}
		if(StringUtils.isNotBlank(error)){
			throw new BusinessException(error);
		}
	}

	public void actualizarHorario(HorarioBeneficiario horarioBeneficiario)throws BusinessException {
		HorarioBeneficiario horarioBeneficiarioTmp = null;
		String error = null;
		if(horarioBeneficiario != null && horarioBeneficiario.getIdHorario() != null){
			error = validaHorario(horarioBeneficiario);
			if(StringUtils.isBlank(error)){				
				/*if(!validaEmisionHorario){
					horarioBeneficiarioTmp = (HorarioBeneficiario)horarioBeneficiarioDao.getByPk(HorarioBeneficiario.class, horarioBeneficiario.getIdHorario());
					horarioBeneficiarioTmp.setDias(horarioBeneficiario.getDias());					
					horarioBeneficiarioTmp.setEsDespuesFechaCorte(horarioBeneficiario.getEsDespuesFechaCorte());					
					horarioBeneficiarioTmp.setHora(horarioBeneficiario.getHora());					
					horarioBeneficiarioTmp.setMinuto(horarioBeneficiario.getMinuto());
					horarioBeneficiarioTmp.setPorcentajeRet(horarioBeneficiario.getPorcentajeRet());
					horarioBeneficiarioDao.update(horarioBeneficiarioTmp);
				}else*/if(horarioBeneficiarioDao.findHorario(horarioBeneficiario) == null){
					horarioBeneficiarioTmp = (HorarioBeneficiario)horarioBeneficiarioDao.getByPk(HorarioBeneficiario.class, horarioBeneficiario.getIdHorario());
					horarioBeneficiarioTmp.setDias(horarioBeneficiario.getDias());
					horarioBeneficiarioTmp.setEmisora(horarioBeneficiario.getEmisora());
					horarioBeneficiarioTmp.setEsDespuesFechaCorte(horarioBeneficiario.getEsDespuesFechaCorte());
					horarioBeneficiarioTmp.setFolioInstitucion(horarioBeneficiario.getFolioInstitucion());
					horarioBeneficiarioTmp.setHora(horarioBeneficiario.getHora());
					horarioBeneficiarioTmp.setIdInstitucion(horarioBeneficiario.getIdInstitucion());
					horarioBeneficiarioTmp.setMinuto(horarioBeneficiario.getMinuto());
					horarioBeneficiarioTmp.setPorcentajeRet(horarioBeneficiario.getPorcentajeRet());
					horarioBeneficiarioTmp.setSerie(horarioBeneficiario.getSerie());
					horarioBeneficiarioTmp.setTv(horarioBeneficiario.getTv());			
					horarioBeneficiarioDao.update(horarioBeneficiarioTmp);
				}else{
					error = "El horario ya existe.";
				}				
			}
		}
		if(StringUtils.isNotBlank(error)){
			throw new BusinessException(error);
		}
	}
	
	private String validaHorario(HorarioBeneficiario horarioBeneficiario){
		String error = null;
		if(StringUtils.isNotBlank(horarioBeneficiario.getTv()) && emisionDao.findInstrumentoByTv(horarioBeneficiario.getTv()) == null){
			error = "El T.V. "+horarioBeneficiario.getTv()+" no existe";
		}
		
		if(StringUtils.isBlank(error) && StringUtils.isNotBlank(horarioBeneficiario.getEmisora()) && emisionDao.findEmisoraByCvePizarra(horarioBeneficiario.getEmisora()) == null){
			error = "La Emisora "+horarioBeneficiario.getEmisora()+" no existe";
		}
		
		if(StringUtils.isBlank(error) && horarioBeneficiario.getIdInstitucion() != null && emisionDao.findInstitucionByIdFolio(Long.valueOf(horarioBeneficiario.getIdInstitucion().longValue()), horarioBeneficiario.getFolioInstitucion()) == null){
			error = "La institucion no existe.";
		}					
		return error;
	}
	
	private String validaEmision(ExcepcionEmisionBenef emisionBenef){
		String error = null;
		if(StringUtils.isNotBlank(emisionBenef.getTv()) && emisionDao.findInstrumentoByTv(emisionBenef.getTv()) == null){
			error = "El T.V. "+emisionBenef.getTv()+" no existe";
		}
		
		if(StringUtils.isBlank(error) && StringUtils.isNotBlank(emisionBenef.getEmisora()) && emisionDao.findEmisoraByCvePizarra(emisionBenef.getEmisora()) == null){
			error = "La Emisora "+emisionBenef.getEmisora()+" no existe";
		}

		if(StringUtils.isBlank(error) && StringUtils.isNotBlank(emisionBenef.getIsin()) && emisionDao.findEmisionByIsin(emisionBenef.getIsin()) == null){
			error = "El ISIN "+emisionBenef.getIsin()+" no existe";
		}
		return error;
	}
	
	public boolean tieneEmisionPorcentajeCero(Long idEmisionDerecho) {
		SicEmision emision = null;
		boolean tienePorcentajeCero = false;
		emision = sicEmisionDao.findSicEmisionByIdEmision(idEmisionDerecho);
		if(excepcionEmisionBenefDao.findEmisionPorcentajeCero(emision.getCuentaNombrada().getIdCuentaNombrada()) != null){
			tienePorcentajeCero = true;
		}
		
		return tienePorcentajeCero;
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.AdminCatalogosBenefService.tieneEmisionPorcentajeCeroOptimizado(Long)
	 */
    public boolean tieneEmisionPorcentajeCeroOptimizado(Long idEmisionDerecho) {
        SicEmisionVO sicEmisionVO = null;
        boolean tienePorcentajeCero = false;
        sicEmisionVO = sicEmisionDao.findSicEmisionByIdEmisionWithoutEntities(idEmisionDerecho);
        if (excepcionEmisionBenefDao.findEmisionPorcentajeCero(sicEmisionVO.getIdCuentaNombrada().longValue()) != null) {
            tienePorcentajeCero = true;
        }

        return tienePorcentajeCero;
    }

	public void setExcepcionEmisionBenefDao(
			ExcepcionEmisionBenefDao excepcionEmisionBenefDao) {
		this.excepcionEmisionBenefDao = excepcionEmisionBenefDao;
	}

	public void setHorarioBeneficiarioDao(
			HorarioBeneficiarioDao horarioBeneficiarioDao) {
		this.horarioBeneficiarioDao = horarioBeneficiarioDao;
	}
	
	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}

	public void setSicEmisionDao(SicEmisionDao sicEmisionDao) {
		this.sicEmisionDao = sicEmisionDao;
	}
}
