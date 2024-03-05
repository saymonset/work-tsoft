/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TotalesProcesoFileTrasnferVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private PaginaVO pagina;

    private Integer totalRegistrosACargar;

    private Integer totalRegistrosConError;

    /**
     * @return Returns the pagina.
     */
    public PaginaVO getPagina() {
        return pagina;
    }

    /**
     * @param pagina
     *            The pagina to set.
     */
    public void setPagina(PaginaVO pagina) {
        this.pagina = pagina;
    }

    /**
     * @return Returns the totalRegistrosACargar.
     */
    public Integer getTotalRegistrosACargar() {
        return totalRegistrosACargar;
    }

    /**
     * @param totalRegistrosACargar
     *            The totalRegistrosACargar to set.
     */
    public void setTotalRegistrosACargar(Integer totalRegistrosACargar) {
        this.totalRegistrosACargar = totalRegistrosACargar;
    }

    /**
     * @return Returns the totalRegistrosConError.
     */
    public Integer getTotalRegistrosConError() {
        return totalRegistrosConError;
    }

    /**
     * @param totalRegistrosConError
     *            The totalRegistrosConError to set.
     */
    public void setTotalRegistrosConError(Integer totalRegistrosConError) {
        this.totalRegistrosConError = totalRegistrosConError;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
