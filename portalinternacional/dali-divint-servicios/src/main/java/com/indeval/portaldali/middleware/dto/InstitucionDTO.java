
/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Data Transfer Object que representa un elemento del catálogo de instituciones.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class InstitucionDTO implements Serializable{

        private static final long serialVersionUID = 1L;
        
        /** El identificador de la institución */
        private long id = 0;
        
        /** El nombre corto asociado a la institución */
        private String nombreCorto = null;
        /**
         * La clave del tipo de institucion
         */
        private String claveTipoInstitucion = null;
        /**
         * El folio de la institución
         */
        private String folioInstitucion = null;
	/** La razón social de la institución */
	private String razonSocial = null;

        /**
         * método para obtener el atributo id
         * 
         * @return the id
         */
        public long getId() {
                return id;
        }
        /**
         * método para establecer el atributo id
         * 
         * @param id the id to set
         */
        public void setId(long id) {
                this.id = id;
        }
        /**
         * método para obtener el atributo nombreCorto
         * 
         * @return the nombreCorto
         */
        public String getNombreCorto() {
                return nombreCorto;
        }
        /**
         * método para establecer el atributo nombreCorto
         * 
         * @param nombreCorto the nombreCorto to set
         */
        public void setNombreCorto(String nombreCorto) {
                this.nombreCorto = nombreCorto;
        }
        /**
         * Obtiene el campo claveTipoInstitucion
         * @return  claveTipoInstitucion
         */
        public String getClaveTipoInstitucion() {
                return claveTipoInstitucion;
        }
        /**
         * Asigna el valor del campo claveTipoInstitucion
         * @param claveTipoInstitucion el valor de claveTipoInstitucion a asignar
         */
        public void setClaveTipoInstitucion(String claveTipoInstitucion) {
                this.claveTipoInstitucion = claveTipoInstitucion;
        }
        /**
         * Obtiene el campo folioInstitucion
         * @return  folioInstitucion
         */
        public String getFolioInstitucion() {
                return folioInstitucion;
        }
	/**
	 * Obtiene el valor del atributo razonSocial
	 * 
	 * @return el valor del atributo razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Establece el valor del atributo razonSocial
	 * 
	 * @param razonSocial
	 *            el valor del atributo razonSocial a establecer
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

        /**
         * Asigna el valor del campo folioInstitucion
         * @param folioInstitucion el valor de folioInstitucion a asignar
         */
        public void setFolioInstitucion(String folioInstitucion) {
                this.folioInstitucion = folioInstitucion;
        }
}
