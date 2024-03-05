package com.indeval.portaldali.presentation.jsf.pdf.itextExtension;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
//import com.lowagie.text.Watermark;
import com.lowagie.text.pdf.PdfPCell;

public class PDFExtendedCell extends PdfPCell implements IDocument {

    public boolean add(Element element) throws DocumentException {
        addElement(element);
        return true;
    }
//    public boolean add(Watermark watermark){
//        return true;
//    }
//  

}
