package com.indeval.portaldali.middleware.services.util;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.Map;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ReenvioOperacionesConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return AbstractList.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		AbstractList list = (AbstractList) source;
		for(Object mapObj : list) {
	        AbstractMap map = (AbstractMap) mapObj;
            writer.startNode("operacion");
	        for (Object obj : map.entrySet()) {
	            Map.Entry entry = (Map.Entry) obj;
	            writer.startNode(entry.getKey().toString());
	            Object val = entry.getValue();
	            if ( null != val ) {
	                writer.setValue(val.toString());
	            }
	            writer.endNode();
	        }
            writer.endNode();
		}
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
