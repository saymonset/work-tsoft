package com.indeval.portaldali.presentation.jsf.text;

import java.util.List;

import javax.faces.component.UIOutput;

public class TextRowComponent extends UIOutput {


    public TextRowComponent(){
        this.setRendererType("indeval.TextRow");
    }
    
    public String getFamily() {
        return "indeval.TextRow";
    }
    
    public boolean hasHeaders() {
        List cols = this.getChildren();
        if( cols != null ) {
            for( int i = 0; i < cols.size(); i++ ) {
                TextColumnComponent column = (TextColumnComponent)cols.get(i);
                if( column.getHeader() != null && !column.getHeader().equals("") ) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean hasFooters() {
        List cols = this.getChildren();
        if( cols != null ) {
            for( int i = 0; i < cols.size(); i++ ) {
                TextColumnComponent column = (TextColumnComponent)cols.get(i);
                if( column.getFooter() != null && !column.getFooter().equals("") ) {
                    return true;
                }
            }
        }
        return false;
    }
}
