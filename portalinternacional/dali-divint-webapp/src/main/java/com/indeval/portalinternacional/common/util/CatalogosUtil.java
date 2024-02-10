/**
 * Bursatec - Portal Internacional Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.indeval.portaldali.persistence.modelo.BancoCorresponsal;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlCatalogosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.CatalogosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.ExportacionConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

/**
 * Clase utilitaria para obtener los catálogos más comunes de la aplicación.
 * 
 * @author Pablo Balderas
 */
public class CatalogosUtil {

    /** Servicio para obtener catálogos. */
    private ControlCatalogosService catalogosService;

    /** Servicio para obtener catálogos. */
    private DivisionInternacionalService divisionInternacionalService;

    /** Indica el estatus activo */
    private final String ESTATUS_ACTIVO = "1";

    /**
     * Obtiene el catálogo de divisas.
     * 
     * @return Lista con el catálogo.
     */
    public List<SelectItem> getCatalogoDivisas() {
        List<SelectItem> catalogoDivisas = new ArrayList<SelectItem>();
        Divisa[] divisas = this.catalogosService.getDivisas();
        if (divisas != null && divisas.length > 0) {
            for (Divisa divisa : divisas) {
                catalogoDivisas.add(new SelectItem(divisa.getIdDivisa(), divisa
                        .getClaveAlfabetica()));
            }
        }
        return catalogoDivisas;
    }

    /**
     * Obtiene el catalogo de divisas para visualizar la descripcion en vez de la clave alfabetica.
     * 
     * @return Lista con el catalogo.
     */
    public List<SelectItem> getCatalogoDivisasDescripcion() {
        List<SelectItem> catalogoDivisas = new ArrayList<SelectItem>();
        List<Divisa> divisas = this.catalogosService.getDivisasById();
        if (divisas != null && !divisas.isEmpty()) {
            for (Divisa divisa : divisas) {
                catalogoDivisas.add(new SelectItem(divisa.getIdDivisa(), divisa.getDescripcion()));
            }
        }
        return catalogoDivisas;
    }

    /**
     * Obtiene el catálogo de bovedas.
     * 
     * @return Lista con el catálogo.
     */
    public List<SelectItem> getCatalogoBovedas() {
        List<SelectItem> catalogoBovedas = new ArrayList<SelectItem>();
        Boveda[] bovedas = this.catalogosService.getBovedas();
        if (bovedas != null && bovedas.length > 0) {
            for (Boveda boveda : bovedas) {
                catalogoBovedas.add(new SelectItem(boveda.getIdBoveda(), boveda.getNombreCorto()));
            }
        }
        return catalogoBovedas;
    }

    /**
     * Obtiene el catálogo de estatus de derecho.
     * 
     * @return Lista con el catálogo.
     */
    public List<SelectItem> getCatalogoEstatusDerecho() {
        List<SelectItem> catalogoEstatusDerecho = new ArrayList<SelectItem>();
        List<EstatusDerecho> estatusDerechos = this.catalogosService.getEstatusDerecho();
        if (estatusDerechos != null && !estatusDerechos.isEmpty()) {
            for (EstatusDerecho estatusDerecho : estatusDerechos) {
                catalogoEstatusDerecho.add(new SelectItem(estatusDerecho.getIdEstatusDerecho()
                        .longValue(), estatusDerecho.getDescEstatusDerecho()));
            }
        }
        return catalogoEstatusDerecho;
    }

    /**
     * Obtiene el catálogo de estatus de derechos con solo las opciones pagado y cortado.
     * 
     * @return Lista con el catálogo.
     */
    public List<SelectItem> getCatalogoEstatusDerechosPagadoCortado() {
        List<SelectItem> catalogoEstatusDerecho = new ArrayList<SelectItem>();
        catalogoEstatusDerecho.add(new SelectItem(CatalogosConstantes.ID_ESTATUS_DERECHO_CORTADO,
                CatalogosConstantes.ESTATUS_DERECHO_CORTADO));
        catalogoEstatusDerecho.add(new SelectItem(CatalogosConstantes.ID_ESTATUS_DERECHO_PAGADO,
                CatalogosConstantes.ESTATUS_DERECHO_PAGADO));
        return catalogoEstatusDerecho;
    }


    /**
     * Obtiene el catálogo de custodios.
     * 
     * @return Lista con el catálogo.
     */
    public List<SelectItem> getCatalogoCustodios() {
        List<SelectItem> catalogoCustodios = new ArrayList<SelectItem>();
        List<Custodio> custodios = this.divisionInternacionalService.obtieneCatalogoCustodios();
        if (custodios != null && !custodios.isEmpty()) {
            for (Custodio custodio : custodios) {
                catalogoCustodios.add(new SelectItem(custodio.getId().longValue(), custodio
                        .getDescripcion()));
            }
        }
        return catalogoCustodios;
    }

    /**
     * Obtiene el catálogo de custodios.
     * 
     * @return Lista con el catálogo.
     */
    public List<SelectItem> getCatalogoCustodiosCatBic() {
        List<SelectItem> catalogoCustodios = new ArrayList<SelectItem>();
        List<Custodio> custodios = this.divisionInternacionalService.obtieneCatalogoCustodios();
        if (custodios != null && !custodios.isEmpty()) {
            for (Custodio custodio : custodios) {
                catalogoCustodios.add(new SelectItem(custodio.getIdCatBic().getIdCatbic()
                        .longValue(), custodio.getDescripcion()));
            }
        }
        return catalogoCustodios;
    }

    /**
     * Obtiene el catalogo de tipos de beneficiario.
     * 
     * @return Lista con el catalogo.
     */
    public List<SelectItem> getCatalogoTiposBeneficiario() {
        List<SelectItem> catalogoTipoBeneficiarios = new ArrayList<SelectItem>();
        List<TipoBeneficiario> tiposBeneficiarios = this.catalogosService.getTipoBeneficiario();
        if (tiposBeneficiarios != null && !tiposBeneficiarios.isEmpty()) {
            for (TipoBeneficiario tipoBeneficiario : tiposBeneficiarios) {
                if (this.ESTATUS_ACTIVO.equalsIgnoreCase(tipoBeneficiario.getStatusTipoBenef())) {
                    catalogoTipoBeneficiarios.add(new SelectItem(tipoBeneficiario
                            .getIdTipoBeneficiario(), tipoBeneficiario.getDescTipoBeneficiario()));
                }
            }
        }
        return catalogoTipoBeneficiarios;
    }

    /**
     * Obtiene el catalogo de tipos de formatos W.
     * 
     * @return Lista con el catalogo
     */
    public List<SelectItem> getCatalogoTiposFormatosW() {
        List<SelectItem> catalogoTiposFormatosW = new ArrayList<SelectItem>();
        catalogoTiposFormatosW.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN,
                BeneficiariosConstantes.FORMATO_W8_BEN));
        catalogoTiposFormatosW.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN2014,
                BeneficiariosConstantes.FORMATO_W8_BEN2014));
        catalogoTiposFormatosW.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN2017,
                BeneficiariosConstantes.FORMATO_W8_BEN2017));
        catalogoTiposFormatosW.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_IMY,
                BeneficiariosConstantes.FORMATO_W8_IMY));
        catalogoTiposFormatosW.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_IMY2015,
                BeneficiariosConstantes.FORMATO_W8_IMY2015));
        catalogoTiposFormatosW.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_IMY2017,
                BeneficiariosConstantes.FORMATO_W8_IMY2016));
        catalogoTiposFormatosW.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN_E,
                BeneficiariosConstantes.FORMATO_W8_BEN_E));
        catalogoTiposFormatosW.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN_E_2016,
                BeneficiariosConstantes.FORMATO_W8_BEN_E_2016));
        catalogoTiposFormatosW.add(new SelectItem(BeneficiariosConstantes.FORMATO_W9,
                BeneficiariosConstantes.FORMATO_W9));
        return catalogoTiposFormatosW;
    }

    /**
     * Obtiene el catalog de estados de formatos W
     * 
     * @return Lista con el catalogo
     */
    public List<SelectItem> getCatalogoEstadosFormatosW() {
        List<SelectItem> catalogoEstadosFormatoW = new ArrayList<SelectItem>();
        catalogoEstadosFormatoW.add(new SelectItem(new String(), ExportacionConstantes.TODOS));
        catalogoEstadosFormatoW.add(new SelectItem(BeneficiariosConstantes.ESTADO_ACTIVO,
                BeneficiariosConstantes.ESTADO_ACTIVO));
        catalogoEstadosFormatoW.add(new SelectItem(BeneficiariosConstantes.ESTADO_INACTIVO,
                BeneficiariosConstantes.ESTADO_INACTIVO));
        return catalogoEstadosFormatoW;
    }

    /**
     * Obtiene el catalog de estados de estados derecho
     * 
     * @return Lista con el catalogo
     */
    public List<SelectItem> getCatalogoEstadosDerechoCapital() {
        List<SelectItem> catalogoEstadoDerecho = new ArrayList<SelectItem>();
        catalogoEstadoDerecho.add(new SelectItem(new String(), ExportacionConstantes.TODOS));
        catalogoEstadoDerecho.add(new SelectItem(BeneficiariosConstantes.ESTADO_DERECHO_PREVIO,
                BeneficiariosConstantes.ESTADO_DERECHO_PREVIO_LABEL));
        catalogoEstadoDerecho.add(new SelectItem(BeneficiariosConstantes.ESTADO_DERECHO_AUTORIZADO,
                BeneficiariosConstantes.ESTADO_DERECHO_AUTORIZADO_LABEL));
        catalogoEstadoDerecho.add(new SelectItem(BeneficiariosConstantes.ESTADO_DERECHO_CONFIRMADO,
                BeneficiariosConstantes.ESTADO_DERECHO_CONFIRMADO_LABEL));
        catalogoEstadoDerecho.add(new SelectItem(BeneficiariosConstantes.ESTADO_DERECHO_PAGADO,
                BeneficiariosConstantes.ESTADO_DERECHO_PAGADO_LABEL));
        return catalogoEstadoDerecho;
    }

    /**
     * Obtiene el catalog de estados de estados derecho
     * 
     * @return Lista con el catalogo
     */
    public List<SelectItem> getCatalogoTipoDerecho() {
        List<SelectItem> catalogoEstadoDerecho = new ArrayList<SelectItem>();
        catalogoEstadoDerecho.add(new SelectItem(new String(), ExportacionConstantes.TODOS));
        catalogoEstadoDerecho.add(new SelectItem(
                BeneficiariosConstantes.TIPO_DERECHO_DIVIDENDO_EFECTIVO,
                BeneficiariosConstantes.TIPO_DERECHO_DIVIDENDO_EFECTIVO_LABEL));
        catalogoEstadoDerecho.add(new SelectItem(BeneficiariosConstantes.TIPO_DERECHO_DISTRIBUCION,
                BeneficiariosConstantes.TIPO_DERECHO_DIVIDENDO_EFECTIVO_LABEL));

        return catalogoEstadoDerecho;
    }


    /**
     * Método para establecer el atributo catalogosService
     * 
     * @param catalogosService El valor del atributo catalogosService a establecer.
     */
    public void setCatalogosService(final ControlCatalogosService catalogosService) {
        this.catalogosService = catalogosService;
    }

    /**
     * Método para establecer el atributo divisionInternacionalService
     * 
     * @param divisionInternacionalService El valor del atributo divisionInternacionalService a
     *        establecer.
     */
    public void setDivisionInternacionalService(
            final DivisionInternacionalService divisionInternacionalService) {
        this.divisionInternacionalService = divisionInternacionalService;
    }
    
	public List<BancoCorresponsal> getBancoCorreponsal() {
		return catalogosService.buscaBancoCorresponsal();
	}
	
    public List<SelectItem> getCatalogoDivisasXInstitucion(Long idInstitucion) {
        List<SelectItem> catalogoDivisas = new ArrayList<SelectItem>();
        Divisa[] divisas = this.catalogosService.obtenerDivisaPorInstitucion(idInstitucion);
        if (divisas != null && divisas.length > 0) {
        	System.out.println("getCatalogoDivisasXInstitucion devolvera "+divisas.length);
            for (Divisa divisa : divisas) {
                catalogoDivisas.add(new SelectItem(divisa.getIdDivisa(), divisa.getDescripcion()));
            }
        }else {
        	System.out.println("getCatalogoDivisasXInstitucion devolvio null en las institucio");
        }
        return catalogoDivisas;
    }
}
