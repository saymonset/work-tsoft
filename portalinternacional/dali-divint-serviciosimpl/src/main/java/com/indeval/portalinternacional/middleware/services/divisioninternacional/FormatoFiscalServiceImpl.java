package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.TipoInstitucion;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorFormatoFiscalTinService;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoFiscal;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.FormatoFiscalDao;
import com.indeval.portalinternacional.persistence.dao.HistoricoBeneficiarioDao;

public class FormatoFiscalServiceImpl implements FormatoFiscalService {
	
	/** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(FormatoFiscalServiceImpl.class);
	
    private FormatoFiscalDao formatoFiscalDao;
    private BeneficiarioDao beneficiarioDao;
    private HistoricoBeneficiarioDao historicoBeneficiarioDao;
    private CatBicDao catBicDao;
    
    private ValidatorFormatoFiscalTinService validatorFormatoFiscalTinService;
    
	/* Constantes para estatus de Beneficiarios */
	long STATUS_BENEFICIARIO_REGISTRADO = 1;
	long STATUS_BENEFICIARIO_AUTORIZADO = 2;
	long STATUS_BENEFICIARIO_VENCIDO = 3;
	long STATUS_BENEFICIARIO_ACTUALIZADO = 4;
	long STATUS_BENEFICIARIO_CANCELADO = 5;
	long STATUS_BENEFICIARIO_ELIMINADO = 6;
	
    /**
     * Metodo encargado de generar una lista de bean formatosFiscales
     * @param beneficiario
     * @return List<FormatoFiscal>
     */
    public List<FormatoFiscal> prepareFormatoFiscalTin(Beneficiario beneficiario){
    	this.log.info("Entrando a prepareFormatoFiscalTin");
    	List<FormatoFiscal> listFormatoFiscal = new ArrayList<FormatoFiscal>();
    	
        // Iterate for institucion
        Set<Institucion> setInstitucion = beneficiario.getInstitucion();
        this.log.info("setInstitucion :: " + setInstitucion.size());
        if(setInstitucion.size() > 0){
            for (Institucion institucion : setInstitucion) {
//            	if(this.validatorFormatoFiscalTinService.validateFormatIsAuthorized(beneficiario)){
                    FormatoFiscal formatoFiscalBean = new FormatoFiscal();
                    formatoFiscalBean.setInstitucionID(institucion.getIdInstitucion());
                    
                    TipoInstitucion tipoInstitucion = institucion.getTipoInstitucion();
                    formatoFiscalBean.setTipoInstitucionID(tipoInstitucion.getIdTipoInstitucion());
                    formatoFiscalBean.setFolioInstitucion(institucion.getFolioInstitucion());

                    formatoFiscalBean.setBeneficiarioID(beneficiario.getIdBeneficiario());
                    formatoFiscalBean.setServicioID(123L);
                    formatoFiscalBean.setTipoFormato(beneficiario.getTipoFormato());
                    formatoFiscalBean.setTipoBeneficiario(beneficiario.getTipoBeneficiario().getIdTipoBeneficiario());
                    formatoFiscalBean.setStatusFormatoID(beneficiario.getStatusBenef().getIdStatusBenef());
                    formatoFiscalBean.setFechaIngreso(beneficiario.getFechaRegistro());
                    formatoFiscalBean.setFechaAutorizacion(beneficiario.getFechaAutorizacion());
                    formatoFiscalBean.setEliminado(beneficiario.getEliminado());
                    
                    formatoFiscalBean.setNombreBeneficiario(beneficiario.getNombres());
                    formatoFiscalBean.setApellidoPaternoBeneficiario(beneficiario.getApellidoPaterno());
                    formatoFiscalBean.setApellidoMaternoBeneficiario(beneficiario.getApellidoMaterno());
                    formatoFiscalBean.setDescriptionTipoBeneficiario(beneficiario.getTipoBeneficiario().getDescTipoBeneficiario());
                    formatoFiscalBean.setRazonSocialBeneficiario(beneficiario.getRazonSocial());
                    formatoFiscalBean.setEstado(beneficiario.getActivo());
                    formatoFiscalBean.setUoi(beneficiario.getUoiNumber());
                    formatoFiscalBean.setCuentaNombrada(beneficiario.getIdCuentaNombrada());
					Map<Long,String> resultadoConsulta = catBicDao.findCustodioCuentaNombrada(beneficiario.getIdCuentaNombrada());
            		String custodio = null;
					for( Map.Entry<Long, String> entry : resultadoConsulta.entrySet() ) {
						custodio = entry.getValue();
					}
					formatoFiscalBean.setCustodio(custodio);

					listFormatoFiscal.add(this.validatorFormatoFiscalTinService.obtenerRFCFormato(formatoFiscalBean, beneficiario));
//            	}
    		}	
        }
    	return listFormatoFiscal;
    }
    
    /**
     * Metodo encargado de generar un bean formatosFiscales para la institucion actual 
     * @param beneficiario
     * @return List<FormatoFiscal>
     */
    public FormatoFiscal prepareTinCobroInstitucion(Beneficiario beneficiario, Institucion institucionAsignada){
        this.log.info("Entrando a prepareFormatoFiscalTin");
        FormatoFiscal formatoFiscalBean = new FormatoFiscal();
    	
        // Iterate for institucion
        Set<Institucion> setInstitucion = beneficiario.getInstitucion();
        if(setInstitucion.size() > 0){
            for (Institucion institucion : setInstitucion) {            	
        		if(institucion.getIdInstitucion().equals(institucionAsignada.getIdInstitucion()) && institucion.getFolioInstitucion().equals(institucionAsignada.getFolioInstitucion())){
                    formatoFiscalBean.setInstitucionID(institucion.getIdInstitucion());
                    TipoInstitucion tipoInstitucion = institucion.getTipoInstitucion();
                    formatoFiscalBean.setTipoInstitucionID(tipoInstitucion.getIdTipoInstitucion());
                    formatoFiscalBean.setFolioInstitucion(institucion.getFolioInstitucion());

                    formatoFiscalBean.setBeneficiarioID(beneficiario.getIdBeneficiario());
                    formatoFiscalBean.setServicioID(123L);
                    formatoFiscalBean.setTipoFormato(beneficiario.getTipoFormato());
                    formatoFiscalBean.setTipoBeneficiario(beneficiario.getTipoBeneficiario().getIdTipoBeneficiario());
                    formatoFiscalBean.setStatusFormatoID(beneficiario.getStatusBenef().getIdStatusBenef());
                    formatoFiscalBean.setFechaIngreso(beneficiario.getFechaRegistro());
                    formatoFiscalBean.setFechaAutorizacion(beneficiario.getFechaAutorizacion());
                    formatoFiscalBean.setEliminado(beneficiario.getEliminado());
                    
                    formatoFiscalBean.setNombreBeneficiario(beneficiario.getNombres());
                    formatoFiscalBean.setApellidoPaternoBeneficiario(beneficiario.getApellidoPaterno());
                    formatoFiscalBean.setApellidoMaternoBeneficiario(beneficiario.getApellidoMaterno());
                    formatoFiscalBean.setDescriptionTipoBeneficiario(beneficiario.getTipoBeneficiario().getDescTipoBeneficiario());
                    formatoFiscalBean.setRazonSocialBeneficiario(beneficiario.getRazonSocial());
                    formatoFiscalBean.setEstado(beneficiario.getActivo());
                    formatoFiscalBean.setUoi(beneficiario.getUoiNumber());
                    formatoFiscalBean.setCuentaNombrada(beneficiario.getIdCuentaNombrada());
					Map<Long,String> resultadoConsulta = catBicDao.findCustodioCuentaNombrada(beneficiario.getIdCuentaNombrada());
            		String custodio = null;
					for( Map.Entry<Long, String> entry : resultadoConsulta.entrySet() ) {
						custodio = entry.getValue();
					}
					formatoFiscalBean.setCustodio(custodio);

					formatoFiscalBean = this.validatorFormatoFiscalTinService.obtenerRFCFormato(formatoFiscalBean, beneficiario);
        		}
    		}
        }
    	return formatoFiscalBean;
    }
    
    /**
     * Metodo encargado de generar un bean formatosFiscales para la institucion actual 
     * @param beneficiario
     * @return List<FormatoFiscal>
     */
    public FormatoFiscal prepareTinCobroByBeneficiarioIdInstitucion(Beneficiario beneficiario, Long idInstitucion){
        this.log.info("Entrando a prepareFormatoFiscalTin");
        FormatoFiscal formatoFiscalBean = new FormatoFiscal();
    	
        // Iterate for institucion
        Set<Institucion> setInstitucion = beneficiario.getInstitucion();
        if(setInstitucion.size() > 0){
            for (Institucion institucion : setInstitucion) {      	
        		if(institucion.getIdInstitucion().equals(idInstitucion)){
                    formatoFiscalBean.setInstitucionID(institucion.getIdInstitucion());
                    TipoInstitucion tipoInstitucion = institucion.getTipoInstitucion();
                    formatoFiscalBean.setTipoInstitucionID(tipoInstitucion.getIdTipoInstitucion());
                    formatoFiscalBean.setFolioInstitucion(institucion.getFolioInstitucion());

                    formatoFiscalBean.setBeneficiarioID(beneficiario.getIdBeneficiario());
                    formatoFiscalBean.setServicioID(123L);
                    formatoFiscalBean.setTipoFormato(beneficiario.getTipoFormato());
                    formatoFiscalBean.setTipoBeneficiario(beneficiario.getTipoBeneficiario().getIdTipoBeneficiario());
                    formatoFiscalBean.setStatusFormatoID(beneficiario.getStatusBenef().getIdStatusBenef());
                    formatoFiscalBean.setFechaIngreso(beneficiario.getFechaRegistro());
                    formatoFiscalBean.setFechaAutorizacion(beneficiario.getFechaAutorizacion());
                    formatoFiscalBean.setEliminado(beneficiario.getEliminado());
                    
                    formatoFiscalBean.setNombreBeneficiario(beneficiario.getNombres());
                    formatoFiscalBean.setApellidoPaternoBeneficiario(beneficiario.getApellidoPaterno());
                    formatoFiscalBean.setApellidoMaternoBeneficiario(beneficiario.getApellidoMaterno());
                    formatoFiscalBean.setDescriptionTipoBeneficiario(beneficiario.getTipoBeneficiario().getDescTipoBeneficiario());
                    formatoFiscalBean.setRazonSocialBeneficiario(beneficiario.getRazonSocial());
                    formatoFiscalBean.setEstado(beneficiario.getActivo());
                    formatoFiscalBean.setUoi(beneficiario.getUoiNumber());
                    formatoFiscalBean.setCuentaNombrada(beneficiario.getIdCuentaNombrada());
					Map<Long,String> resultadoConsulta = catBicDao.findCustodioCuentaNombrada(beneficiario.getIdCuentaNombrada());
            		String custodio = null;
					for( Map.Entry<Long, String> entry : resultadoConsulta.entrySet() ) {
						custodio = entry.getValue();
					}
					formatoFiscalBean.setCustodio(custodio);

					formatoFiscalBean = this.validatorFormatoFiscalTinService.obtenerRFCFormato(formatoFiscalBean, beneficiario);
        		}
    		}
        }
    	return formatoFiscalBean;
    }
    
    /**
     * Valida tipos de formatos validos
     */
    public List<FormatoFiscal> validateFormatosFiscales(List<FormatoFiscal> listFormatoFiscal){
    	this.log.info("Entrando a validateFormatosFiscales");
    	
    	List<FormatoFiscal> responseFormatoFiscales = new ArrayList<FormatoFiscal>();
    	
    	for (FormatoFiscal formatoFiscal : listFormatoFiscal) {
    		
            if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(formatoFiscal.getTipoFormato()) 
            		|| BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(formatoFiscal.getTipoFormato()) 
            		|| BeneficiariosConstantes.FORMATO_W8_BEN2017.equals(formatoFiscal.getTipoFormato())
            		|| BeneficiariosConstantes.FORMATO_W9.equals(formatoFiscal.getTipoFormato())
					|| BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(formatoFiscal.getTipoFormato()) 
            		|| BeneficiariosConstantes.FORMATO_W8_BEN_E_2016.equals(formatoFiscal.getTipoFormato())
            		|| BeneficiariosConstantes.FORMATO_W8_IMY.equals(formatoFiscal.getTipoFormato())
            		|| BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(formatoFiscal.getTipoFormato()) 
            		|| BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(formatoFiscal.getTipoFormato())
            		|| BeneficiariosConstantes.FORMATO_W8_IMY2016.equals(formatoFiscal.getTipoFormato()))
            	
            	responseFormatoFiscales.add(formatoFiscal);

		}
    	
    	return responseFormatoFiscales;
        
    }
    
    /**
     * Metodo para almacenar datos del beneficiario en tabla: T_FORMATOS_FISCALES_ING
     * @param List<FormatoFiscal> listFormatoFiscalTin
     * @param Beneficiario beneficiario
     */
    public void saveFormatoFiscalTin(List<FormatoFiscal> listFormatoFiscalTin, Beneficiario beneficiario){
        this.log.info("Entrando a saveFormatoFiscalTin");
        for (FormatoFiscal formatoFiscal : listFormatoFiscalTin) {
    		this.log.info("TipoFormato :: " + formatoFiscal.getTipoFormato()
    		+ " :: Nombre :: " + formatoFiscal.getNombreBeneficiario()
    		+ " :: ApellidoPaterno :: " + formatoFiscal.getApellidoPaternoBeneficiario()
    		+ " :: ApellidoMaterno :: " + formatoFiscal.getApellidoMaternoBeneficiario()
    		+ " :: RazonSocial :: " + formatoFiscal.getRazonSocialBeneficiario()
    		+ " :: FechaAutorizacion :: " + formatoFiscal.getFechaAutorizacion());

    		Boolean isSave = this.validateExistsFormatoFiscalTin(formatoFiscal);
    		this.log.info("Boolean isSave :: " + isSave);
    		
    		//Se busca la instritucion asignada
//            BeneficiarioInstitucion beneficiarioInstitucion = this.beneficiarioDao.getBeneficiarioInstitucion(formatoFiscal.getBeneficiarioID(), formatoFiscal.getInstitucionID());
        	if(isSave){
        		this.log.info("## beneficiario.getStatusBenef().getIdStatusBenef() :: " + beneficiario.getStatusBenef().getIdStatusBenef());
				Boolean generaTIN = true;        		
        		if(beneficiario.getStatusBenef().getIdStatusBenef() == STATUS_BENEFICIARIO_CANCELADO){
        			generaTIN = false;
                	break;
        		}else{
            		Boolean fechaMayor = this.validatorFormatoFiscalTinService.getDiffYears(new Date(), formatoFiscal.getFechaIngreso());
            		if(fechaMayor){
            			formatoFiscal.setExtraer(false);
                		Long idFormatoFiscal = (Long) this.formatoFiscalDao.save(formatoFiscal);
                		this.formatoFiscalDao.flush();
                		this.log.info("ID Generado :: " + idFormatoFiscal);
                		
//                		if(beneficiarioInstitucion != null){
//                    		beneficiarioInstitucion.setGeneraCobro(Boolean.FALSE);
//                    		this.beneficiarioDao.update(beneficiarioInstitucion);
//                		}
            		}else{
                		try{
                			// Valido el historico: CANCELADO, AUTORIZADO ==> NO GENERA COBRO
/*
                			List<HistoricoBeneficiario> history = historicoBeneficiarioDao.findHistoricoBeneficiario(formatoFiscal.getBeneficiarioID(), formatoFiscal.getTipoFormato().trim());
                			for (HistoricoBeneficiario historicoBeneficiario : history) {
        						if(historicoBeneficiario.getStatusAnterior().getIdStatusBenef() == STATUS_BENEFICIARIO_CANCELADO
        								|| historicoBeneficiario.getStatusAnterior().getIdStatusBenef() == STATUS_BENEFICIARIO_ACTUALIZADO
        								|| historicoBeneficiario.getStatusAnterior().getIdStatusBenef() == STATUS_BENEFICIARIO_AUTORIZADO){
        							generaTIN = false;
        			                break;
        						}
        					}
*/
                			if(generaTIN){
                				formatoFiscal.setExtraer(true);
                        		Long idFormatoFiscal = (Long) this.formatoFiscalDao.save(formatoFiscal);
                        		this.formatoFiscalDao.flush();
                        		this.log.info("ID Generado :: " + idFormatoFiscal);	
                        
//                        		if(beneficiarioInstitucion != null){
//    	                    		beneficiarioInstitucion.setGeneraCobro(Boolean.TRUE);
//    	                    		this.beneficiarioDao.update(beneficiarioInstitucion);
//                        		}
                			}
                		} catch (Exception ex){
                			ex.printStackTrace();
                    		// Si hay un error en la inserción, se envia mail
//                        	this.mailService.sendMail("message", "destino", "subject", "from", "UTF-8");
                		}
            		}
        		}
        	} /*else{
        		if(!(this.validatorFormatoFiscalTinService.getDiffYears(new Date(), formatoFiscal.getFechaIngreso()))){
        			formatoFiscal.setExtraer(false);
            		Long idFormatoFiscal = (Long) this.formatoFiscalDao.save(formatoFiscal);
            		this.formatoFiscalDao.flush();
            		this.this.log.info("ID Generado :: " + idFormatoFiscal);	
        		}
        	}*/
		}
        
    }
    
    /**
     * Metodo para almacenar datos del beneficiario en tabla: T_FORMATOS_FISCALES_ING
     * @param List<FormatoFiscal> listFormatoFiscalTin
     */
    public void guardaTinFormatoFiscal(FormatoFiscal formatoFiscalTin){
        this.log.info("Entrando a guardaTinFormatoFiscal");
	     // Si es true, significa que el formato fiscal no existe aun en la tabla de TIN
	     FormatoFiscal formatoFiscalFind = this.formatoFiscalDao.getTinByBeneficiciarioInstitutionTin(formatoFiscalTin);
	     if(formatoFiscalFind == null){
	    	 formatoFiscalTin.setExtraer(Boolean.FALSE);
	     	Long idFormatoFiscal = (Long) this.formatoFiscalDao.save(formatoFiscalTin);
	     	this.log.info("ID Generado :: " + idFormatoFiscal);	
	     }
    }
    
    /**
     * Metodo para actualizar datos del beneficiario en tabla: T_FORMATOS_FISCALES_ING
     * @param FormatoFiscal formatoFiscalTin
     */
    public void updateTinFormato(FormatoFiscal formatoFiscalTin){
        this.log.info("Entrando a updateTinFormato");
		FormatoFiscal formatoFiscalFind = this.formatoFiscalDao.getTinByBeneficiciarioInstitutionTin(formatoFiscalTin);
		formatoFiscalFind.setExtraer(Boolean.FALSE); 
		this.formatoFiscalDao.update(formatoFiscalFind);
		this.formatoFiscalDao.flush();
    }
    
    /**
     * Metodo para actualizar datos del beneficiario en tabla: T_FORMATOS_FISCALES_ING
     * @param FormatoFiscal formatoFiscalTin
     */
    public void actualizaFormatoFiscal(FormatoFiscal formatoFiscalTin){
		FormatoFiscal formatoFiscalFind = this.formatoFiscalDao.getTinByBeneficiciarioInstitutionTin(formatoFiscalTin);
		if(formatoFiscalFind != null){
			formatoFiscalFind.setExtraer(Boolean.FALSE);
			this.formatoFiscalDao.update(formatoFiscalFind);
			this.formatoFiscalDao.flush();
		}
    }
    
    /**
     * Metodo para actualizar cobro en tabla: T_BENEFICIARIOS_INSTITUCION
     * @param formatoFiscalTin
     */
	public void actualizaBeneficiarioInstitucion(FormatoFiscal formatoFiscalTin) {
		BeneficiarioInstitucion beneficiarioInstitucion = this.beneficiarioDao.getBeneficiarioInstitucion(formatoFiscalTin.getBeneficiarioID(), formatoFiscalTin.getInstitucionID());
		if(beneficiarioInstitucion != null){
    		this.beneficiarioDao.update(beneficiarioInstitucion);
    		this.beneficiarioDao.flush();
		}
	}
    
    /**
     * Metodo para almacenar datos del beneficiario en tabla: T_FORMATOS_FISCALES_ING
     * cuando al asignar una institucion se selecciona un check para generar TIN de cobro
     * @param List<FormatoFiscal> listFormatoFiscalTin
     */
	public void generarTinCobro(FormatoFiscal formatoFiscalBean, Boolean isCobro){
        this.log.info("Entrando a generarTinCobro :: isCobro :: " + isCobro);
        this.log.info("TipoFormato :: " + formatoFiscalBean.getTipoFormato()
		+ " :: Nombre :: " + formatoFiscalBean.getNombreBeneficiario()
		+ " :: ApellidoPaterno :: " + formatoFiscalBean.getApellidoPaternoBeneficiario()
		+ " :: ApellidoMaterno :: " + formatoFiscalBean.getApellidoMaternoBeneficiario()
		+ " :: RazonSocial :: " + formatoFiscalBean.getRazonSocialBeneficiario()
		+ " :: TAXID :: " + formatoFiscalBean.getTaxID()
		+ " :: FechaAutorizacion :: " + formatoFiscalBean.getFechaAutorizacion()
		+ " :: isCobro :: " + isCobro);

        // Si es true, significa que el formato fiscal no existe aun en la tabla de TIN
		FormatoFiscal formatoFiscalFind = this.formatoFiscalDao.getTinByBeneficiciarioInstitutionTin(formatoFiscalBean);
    	if(formatoFiscalFind == null){
    		formatoFiscalBean.setExtraer(isCobro);
    		Long idFormatoFiscal = (Long) this.formatoFiscalDao.save(formatoFiscalBean);
    		this.log.info("ID Generado :: " + idFormatoFiscal);	
    	} else{
    		formatoFiscalFind.setExtraer(isCobro); 
    		this.formatoFiscalDao.update(formatoFiscalFind);
    		this.formatoFiscalDao.flush();
    	}
        
    }
	
	/**
	 * Metodo para obtener las instituciones asignadas a un beneficiario
	 */
	public BeneficiarioInstitucion getBeneficiarioInstitucion(Long idBeneficiario, Long idInstitucion){
		BeneficiarioInstitucion beneficiario = this.formatoFiscalDao.getBeneficiarioInstitucion(idBeneficiario, idInstitucion);
		return beneficiario;
	}
    
    /**
     * Metodo para validar si el formato fiscal Tin ya fue insertado
     * @param formatoFiscalBean
     * @return Boolean
     */
    public Boolean validateExistsFormatoFiscalTin(FormatoFiscal formatoFiscalBean){
        Long isProcess = this.formatoFiscalDao.validateExistsFormatoFiscalTin(formatoFiscalBean);
        if(isProcess > 0){
        	return false;
        } else{
        	return true;
        }
    }

	/**
	 * @param formatoFiscalDao the formatoFiscalDao to set
	 */
	public void setFormatoFiscalDao(FormatoFiscalDao formatoFiscalDao) {
		this.formatoFiscalDao = formatoFiscalDao;
	}

	/**
	 * @param historicoBeneficiarioDao the historicoBeneficiarioDao to set
	 */
	public void setHistoricoBeneficiarioDao(HistoricoBeneficiarioDao historicoBeneficiarioDao) {
		this.historicoBeneficiarioDao = historicoBeneficiarioDao;
	}

	/**
	 * @param validatorFormatoFiscalTinService the validatorFormatoFiscalTinService to set
	 */
	public void setValidatorFormatoFiscalTinService(ValidatorFormatoFiscalTinService validatorFormatoFiscalTinService) {
		this.validatorFormatoFiscalTinService = validatorFormatoFiscalTinService;
	}

	/**
	 * @param catBicDao the catBicDao to set
	 */
	public void setCatBicDao(CatBicDao catBicDao) {
		this.catBicDao = catBicDao;
	}
	
	/**
	 * @param beneficiarioDao the beneficiarioDao to set
	 */
	public void setBeneficiarioDao(BeneficiarioDao beneficiarioDao) {
		this.beneficiarioDao = beneficiarioDao;
	}

}
