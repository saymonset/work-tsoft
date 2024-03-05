package com.indeval.portaldali.presentation.jsf.pdf.itextExtension;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
//import com.lowagie.text.Watermark;

public interface IDocument {
    public boolean add( Element element) throws DocumentException;
   // public boolean add( Watermark watermark);
}
