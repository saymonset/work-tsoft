package com.indeval.portalinternacional.middleware.services.util;


import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.*;

public class XmlUtils {
	public static XStream getXStreamCData(final String... camposCData){
		XStream xstream = new XStream(new XppDriver() {
				    public HierarchicalStreamWriter createWriter(Writer out) {
					return new PrettyPrintWriter(out) {
					    boolean cdata = false;
					    String[] campos=camposCData;
					    public void startNode(String name, Class clazz){
							super.startNode(name);
							for(String campo : campos){
								if(name.equals(campo)){
									cdata=true;
									break;
								}else{
									cdata=false;
								}
							}					
						
					    }
					    protected void writeText(QuickWriter writer, String text) {
						if(cdata) {
						    writer.write("<![CDATA[");
						    writer.write(text);
						    writer.write("]]>");
						} else {
						    writer.write(text);
						}
					    }
					};
				    }
				}
			);
		return xstream;
	}
	
	public static void setAtributosXStream(XStream xstream, Class clase){
		Annotations.configureAliases(xstream, clase);
	}
	
	public static void setAtributosXStream(XStream xstream, Class... clase){
		for(Class cl : clase){
			setAtributosXStream(xstream, cl);
		}
	}
}

