package com.indeval.portalinternacional.common.util;

import java.util.Comparator;

import javax.faces.model.SelectItem;

public class SelectItemComparator implements Comparator<SelectItem> {

	private boolean comparaId;
	
	public SelectItemComparator(boolean comparaId){
		this.comparaId  = comparaId;
	}	
	public int compare(SelectItem o1, SelectItem o2) {
		Object value1;
		Object value2;
		
		if(comparaId){
			value1 = o1.getValue();
			value2 = o2.getValue();
		}
		else{
			value1 = o1.getLabel();
			value2 = o2.getLabel();
		}
		if(value1.getClass().equals(value2.getClass())){
			if(value1 instanceof Long){
				return ((Long)value1).compareTo((Long)value2);
			}
			else if(value1 instanceof Integer){
				return ((Integer)value1).compareTo((Integer)value2);
			}
		}
		return value1.toString().compareTo(value2.toString());
	}

}
