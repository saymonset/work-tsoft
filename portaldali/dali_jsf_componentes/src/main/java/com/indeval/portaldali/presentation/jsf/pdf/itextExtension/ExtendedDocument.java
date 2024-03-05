package com.indeval.portaldali.presentation.jsf.pdf.itextExtension;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
//import com.lowagie.text.Watermark;

public class ExtendedDocument extends Document implements IDocument {
    public ExtendedDocument() {
        super();
    }
    public ExtendedDocument( Rectangle pageSize, float borderLeft, float borderRight, float borderTop, float borderBottom ) {
        super( pageSize, borderLeft, borderRight, borderTop, borderBottom );
    }
    
    public boolean add( Element element) throws DocumentException{
        return super.add(element);
    }
//    public boolean add( Watermark watermark){
//        return super.add(watermark);
//    }
//    
}
