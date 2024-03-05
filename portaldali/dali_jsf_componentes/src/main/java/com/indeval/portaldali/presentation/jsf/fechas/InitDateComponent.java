package com.indeval.portaldali.presentation.jsf.fechas;

public class InitDateComponent extends DateComponent {

	public InitDateComponent (){
		this.setRendererType("indeval.InitDate");
	}

	public String getFamily() {
		return "indeval.InitDate";
	}
	
    public String getType() {
        return "indeval.InitDate";
    }
}
