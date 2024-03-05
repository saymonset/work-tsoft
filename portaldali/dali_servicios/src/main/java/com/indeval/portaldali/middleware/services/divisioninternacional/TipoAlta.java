/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TipoAlta implements Serializable {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private String id;

    private String descripcion;

    /** Define TODOS_VALUE */
    public static final String TODOS_VALUE = "Todos";

    /** Define CPOS_VALUE */
    public static final String CPOS_VALUE = "CPOS";

    /** Define ADCP_VALUE */
    public static final String ADCP_VALUE = "ADCP";

    /** Define IADC_VALUE */
    public static final String IADC_VALUE = "IADC";

    /** Define BADC_VALUE */
    public static final String BADC_VALUE = "BADC";

    /** Define VIVI_VALUE */
    public static final String VIVI_VALUE = "VIVI";

    /** Define GADC_VALUE */
    public static final String GADC_VALUE = "GADC";

    /** Define TODOS */
    public static final TipoAlta TODOS = new TipoAlta(TODOS_VALUE, TODOS_VALUE);

    /** Define CPOS */
    public static final TipoAlta CPOS = new TipoAlta(CPOS_VALUE, CPOS_VALUE);

    /** Define ADCP */
    public static final TipoAlta ADCP = new TipoAlta(ADCP_VALUE, ADCP_VALUE);

    /** Define IADC */
    public static final TipoAlta IADC = new TipoAlta(IADC_VALUE, IADC_VALUE);

    /** Define BADC */
    public static final TipoAlta BADC = new TipoAlta(BADC_VALUE, BADC_VALUE);

    /** Define VIVI */
    public static final TipoAlta VIVI = new TipoAlta(VIVI_VALUE, VIVI_VALUE);

    /** Define GADC */
    public static final TipoAlta GADC = new TipoAlta(GADC_VALUE, GADC_VALUE);

    private static Map<String, TipoAlta> tiposAlta;
    static {
        tiposAlta = new HashMap<String, TipoAlta>();
        tiposAlta.put(TODOS_VALUE, TODOS);
        tiposAlta.put(ADCP_VALUE, ADCP);
        tiposAlta.put(BADC_VALUE, BADC);
        tiposAlta.put(CPOS_VALUE, CPOS);
        tiposAlta.put(GADC_VALUE, GADC);
        tiposAlta.put(IADC_VALUE, IADC);
        tiposAlta.put(VIVI_VALUE, VIVI);

    }

    /**
     * @param idTipoAlta
     * @return String
     */
    public static String getCondicionBusqueda(String idTipoAlta) {
        StringBuffer result = new StringBuffer();

        if (idTipoAlta.equals(TipoAlta.TODOS_VALUE)) {
            Set idsTiposAlta = TipoAlta.getTiposAlta().keySet();
            if (idsTiposAlta.contains(TipoAlta.TODOS_VALUE)) {
                idsTiposAlta.remove(TipoAlta.TODOS_VALUE);
            }
            Object[] ids = idsTiposAlta.toArray();

            for (int i = 0; i < ids.length; i++) {
                String id = (String) ids[i];

                // if(!id.equals(TipoAlta.TODOS_VALUE)) {
                result.append(" '");
                result.append(id);
                result.append("'");

                if (i != (ids.length - 1)) {
                    result.append(",");
                }
                // }

            }

        }
        else {
            result.append("'");
            result.append(idTipoAlta);
            result.append("'");
        }

        return result.toString();
    }

    /**
     * @return Map
     */
    public static Map getTiposAlta() {
        return tiposAlta;
    }

    /**
     * @param idTipoAlta
     * @return TipoAlta
     */
    public static TipoAlta getTipoAltaById(String idTipoAlta) {
        TipoAlta result = TipoAlta.TODOS;

        // Assert
        if (TipoAlta.getTiposAlta().containsKey(idTipoAlta)) {
            result = (TipoAlta) TipoAlta.getTiposAlta().get(idTipoAlta);
        }
        else {
            throw new IllegalArgumentException("El id de tipo de alta no es valido.");
        }

        return result;
    }

    /**
     * 
     */
    private TipoAlta() {
        super();
    }

    /**
     * @param id
     * @param descripcion
     */
    private TipoAlta(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
