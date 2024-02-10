package com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato;

import java.text.DecimalFormat;

public class FileDocumentoUpload {

	private String Name;
    private String mime;
    private long length;
    private byte[] data;
    private boolean preview = true; 
    
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
        int extDot = name.lastIndexOf('.');
        if(extDot > 0){
            String extension = name.substring(extDot +1);
            if("bmp".equals(extension)){
                mime="image/bmp";
            } else if("jpg".equals(extension)){
                mime="image/jpeg";
            } else if("gif".equals(extension)){
                mime="image/gif";
            } else if("png".equals(extension)){
                mime="image/png";
            } else if("pdf".equals(extension)){
                mime="application/pdf";
                preview = false;
            }else {
                mime = "image/unknown";
                preview = false;
            }
        }
    }
    public long getLength() {
        return length;
    }
    
    public String getSizeString(){
    	double unidades = ((double)length)/1000.00d;
    	String medida = " KB";
    	
    	if(unidades>1000.00d){
    		unidades = unidades /1000.00d;
    		medida = " MB";
    	}
    	DecimalFormat df = new DecimalFormat("0.00##");
    	String result = df.format(unidades)+medida;
    	return result;
    }
    public void setLength(long length) {
        this.length = length;
    }
    
    public String getMime(){
        return mime;
    }
	public boolean isPreview() {
		return preview;
	}
}
