package com.indeval.portaldali.vo;

import java.io.Serializable;

public class CaptOpsFieldConfigVO implements Serializable {

    private static final long serialVersionUID = 8054257614213954487L;
    private String  field;
    private Boolean readonly = Boolean.FALSE;
    private Boolean rendered = Boolean.TRUE;;
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public Boolean getReadonly() {
        return readonly;
    }
    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }
    public Boolean getRendered() {
        return rendered;
    }
    public void setRendered(Boolean rendered) {
        this.rendered = rendered;
    }
}
