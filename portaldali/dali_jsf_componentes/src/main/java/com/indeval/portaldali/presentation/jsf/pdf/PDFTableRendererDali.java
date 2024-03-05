package com.indeval.portaldali.presentation.jsf.pdf;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.presentation.util.PDFConstantes;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class PDFTableRendererDali extends Renderer {
    
	private Logger logger = LoggerFactory.getLogger(PDFTableRendererDali.class);

    public PDFTableRendererDali() {
        super();
    }

    private int maxCols = 0;

    private boolean renderHeader = false;

    private boolean renderFooter = false;
    

    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
        maxCols = 0;
        renderHeader = false;
        renderFooter = false;
        PDFTableComponentDali tableComponent = (PDFTableComponentDali) component;
        List<UIComponent> rowChildren = tableComponent.getChildren();
        
       final Integer MAX_COLS = tableComponent.getMaxColums();            

        if (tableComponent.getChildCount() > 0) {

            for (int i = 0; i < rowChildren.size(); i++) {
                PDFRowComponentDali rowComponent = (PDFRowComponentDali) rowChildren
                        .get(i);
                if( rowComponent.isRendered() ) {
                    maxCols = Math.min(MAX_COLS, rowComponent.getRealNumCols());
                    renderHeader = renderHeader || rowComponent.hasHeaders();
                    renderFooter = renderFooter || rowComponent.hasFooters();
                }
            }

            for (int i = 0; i < rowChildren.size(); i++) {
                PDFRowComponentDali rowComponent = (PDFRowComponentDali) rowChildren
                        .get(i);
                if( rowComponent.isRendered() ) {
                    rowComponent.setMaxCols(maxCols);
                }
            }           
            
            tableComponent.setTable(new PdfPTable(maxCols));
            int align = ((Integer)PDFConstantes.alignments.get( tableComponent.getAlign() )).intValue();
            tableComponent.getTable().setHorizontalAlignment(align);
            tableComponent.getTable().setWidthPercentage(tableComponent.getWidthPercentage().intValue());
            tableComponent.getTable().setSpacingBefore(2f);
            tableComponent.getTable().setSpacingAfter(2f);
            tableComponent.getTable().setSplitRows(true);            
            int headerRows = 0;
            if (renderHeader) {
                for (int i = 0; i < rowChildren.size(); i++) {
                    PDFRowComponentDali rowComponent = (PDFRowComponentDali) rowChildren.get(i);
                    if( rowComponent.isRendered() ) {
                        List<UIComponent> columnChildren = rowComponent.getChildren();
                        int j = 0;
                        for (j = 0; j < rowComponent.getChildCount(); j++) {
                            PDFColumnComponentDali columnComponent = (PDFColumnComponentDali) columnChildren.get(j);
                            if( columnComponent.isRendered() ) {
                                String valor = " ";
                                if( columnComponent.getHeader() != null ) {
                                    valor = columnComponent.getHeader().toString();
                                }
                                logger.trace("Render Header, value = " + valor);
                                Paragraph par = new Paragraph(valor,
                                        PDFConstantes.TABLE_HEADER_FONT);
                                par.setAlignment(Paragraph.ALIGN_CENTER);
                                par.setAlignment(Paragraph.ALIGN_CENTER);
                                PdfPCell cell = new PdfPCell(par);
                                cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                                cell.setVerticalAlignment(Paragraph.ALIGN_MIDDLE);
                                cell.setColspan(columnComponent.getCollspan().intValue());
                                
                                if( columnComponent.getHeader() == null ) {
                                    cell.setBackgroundColor(PDFConstantes.TABLE_HEADER_EMPTY_BG_COLOR);
                                    cell.setBorderColor(PDFConstantes.TABLE_HEADER_EMPTY_BORDER_COLOR);
                                } else {
                                    cell.setBackgroundColor(PDFConstantes.TABLE_HEADER_BG_COLOR);
                                    cell.setBorderColor(PDFConstantes.TABLE_HEADER_BORDER_COLOR);
                                }
                                cell.setPadding(2);
                                cell.setUseBorderPadding(true);
                                cell.setBorderWidth(1f);
                                tableComponent.getTable().addCell(cell);
                            }
    
                        }
                        final Integer extraCollspan = getExtraCollspan(maxCols, rowComponent.getRealNumCols());                        
	                        if (extraCollspan > 0) {
	                        	//con esto se completa la linea
	                            PdfPCell cell = new PdfPCell(new Paragraph(" "));
	                            cell.setBackgroundColor(PDFConstantes.TABLE_HEADER_EMPTY_BG_COLOR);
	                            cell.setBorderColor(PDFConstantes.TABLE_HEADER_EMPTY_BORDER_COLOR);
	                            cell.setPadding(2);
	                            cell.setUseBorderPadding(true);
	                            cell.setBorderWidthBottom(1.0f);
	                            cell.setBorderWidthTop(0.5f);
	                            cell.setBorderWidthLeft(0);
	                            cell.setBorderWidthRight(0);
	                            cell.setColspan(maxCols-extraCollspan);
	                            tableComponent.getTable().addCell(cell);
	                        }	                      
	                        
                        if(headerRows == 0) {
                            headerRows = getHeaderRows(maxCols, rowComponent.getRealNumCols());
                        }
                    }
                }
                tableComponent.getTable().setHeaderRows(headerRows);
            }
        }
    }

    private Integer getHeaderRows(final Integer maxCols,
            final Integer realNumCols) {
    	
        logger.trace("getHeaderRows MaxCols: " + maxCols+ " getHeaderRows RealNumCols: " + realNumCols);
        
        int numHeaderRows=realNumCols / maxCols;        
        if(realNumCols % maxCols  != 0) {
        	numHeaderRows++;
        } 
        return numHeaderRows;
    }

    private Integer getExtraCollspan(int maxCols, int realNumCols) {
        logger.trace("MaxCols: " + maxCols);
        logger.trace("RealNumCols: " + realNumCols);
        final Integer extraCollspan= (realNumCols %  maxCols);        
        logger.trace("extraCollspan: " + extraCollspan);
        return extraCollspan;
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
        try {
            PDFTableComponentDali tableComponent = (PDFTableComponentDali) component;
            List<UIComponent> rowChildren = tableComponent.getChildren();
            if (tableComponent.getChildCount() > 0 && renderFooter) {
                for (int i = 0; i < rowChildren.size(); i++) {
                    PDFRowComponentDali rowComponent = (PDFRowComponentDali) rowChildren.get(i);
                    if( rowComponent.isRendered() ) {
                        rowComponent.setMaxCols(maxCols);
                        List<UIComponent> columnChildren = rowComponent.getChildren();
                        int j = 0;
                        for (j = 0; j < rowComponent.getChildCount(); j++) {
                            PDFColumnComponentDali columnComponent = (PDFColumnComponentDali) columnChildren.get(j);
                            if( columnComponent.isRendered() ) {
                                Integer columnAlign = (Integer) PDFConstantes.alignments.get(columnComponent.getAlign());
                                String valor = " ";
                                if( columnComponent.getFooter() != null ){
                                    if( columnComponent.getConverter() != null ){
                                        try {
                                            valor = columnComponent.getConverter().getAsString(facesContext, columnComponent, columnComponent.getFooter());
                                        }catch(Exception e) {
                                            valor = columnComponent.getFooter().toString();
                                        }
                                    }else {
                                        valor = columnComponent.getFooter().toString();
                                    }
                                }
                                Paragraph par = new Paragraph(valor,PDFConstantes.TABLE_FOOTER_FONT);
                                par.setAlignment(columnAlign.intValue());
                                PdfPCell cell = new PdfPCell(par);
                                cell.setHorizontalAlignment(columnAlign.intValue());
                                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                                cell.setColspan(columnComponent.getCollspan().intValue());
                                cell.setBackgroundColor(PDFConstantes.TABLE_FOOTER_BG_COLOR);
                                cell.setBorderColor(PDFConstantes.TABLE_FOOTER_BORDER_COLOR);
                                cell.setPadding(2);
                                cell.setUseBorderPadding(true);
                                cell.setBorderWidthBottom(0);
                                cell.setBorderWidthTop(0.5f);
                                cell.setBorderWidthLeft(0);
                                cell.setBorderWidthRight(0);
                                tableComponent.getTable().addCell(cell);
                            }
                        }
                          
                        int extraCollspan = getExtraCollspan(maxCols, rowComponent.getRealNumCols());
                        
                        if (extraCollspan > 0) {
                            PdfPCell cell = new PdfPCell(new Paragraph(" "));
                            cell.setBackgroundColor(PDFConstantes.TABLE_FOOTER_BG_COLOR);
                            cell.setBorderColor(PDFConstantes.TABLE_FOOTER_BORDER_COLOR);
                            cell.setPadding(2);
                            cell.setUseBorderPadding(true);
                            cell.setBorderWidthBottom(0);
                            cell.setBorderWidthTop(0.5f);
                            cell.setBorderWidthLeft(0);
                            cell.setBorderWidthRight(0);
                            cell.setColspan(maxCols-extraCollspan);
                            tableComponent.getTable().addCell(cell);
                        }
                    }
                }

            }
            
            tableComponent.getDocument().add(tableComponent.getTable());
            maxCols = 0;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }

    public boolean getRendersChildren() {
        return true;
    }

    @SuppressWarnings("rawtypes")
    public void encodeChildren(FacesContext context, UIComponent component)
            throws java.io.IOException {
        PDFTableComponentDali tableComponent = (PDFTableComponentDali) component;
        if (tableComponent.getChildCount() > 0) {
            List<UIComponent> rowChildren = tableComponent.getChildren();
            Object[] datos = null;

            if (tableComponent.getValue().getClass().isArray()) {
                datos = (Object[]) tableComponent.getValue();
            } else {
                Class[] intesfaces = tableComponent.getValue().getClass()
                        .getInterfaces();
                for (int i = 0; i < intesfaces.length; i++) {
                    if (intesfaces[i].equals(List.class)) {
                        datos = ((List) tableComponent.getValue()).toArray();
                        break;
                    } else if (intesfaces[i].equals(Collection.class)) {
                        datos = ((Collection) tableComponent.getValue())
                                .toArray();
                        break;
                    } else if (intesfaces[i].equals(Map.class)) {
                        datos = ((Map) tableComponent.getValue()).values()
                                .toArray();
                        break;
                    }
                }
            }
            if (datos == null) {
                datos = new Object[1];
                datos[0] = tableComponent.getValue();
            }

            if (datos != null ) {
                Map<String, Object>  reqMap = context.getExternalContext().getRequestMap();
                for (int i = 0; i < datos.length; i++) {
                    reqMap.put(tableComponent.getCurrentBean(), datos[i]);
                    for (int j = 0; j < rowChildren.size(); j++) {
                        PDFRowComponentDali row = (PDFRowComponentDali) rowChildren
                                .get(j);
                        row.setOddRow(new Boolean(i % 2 == 0));
                        row.encodeBegin(context);
                        row.encodeChildren(context);
                        row.encodeEnd(context);
                    }
                }
                reqMap.remove(tableComponent.getCurrentBean());
            }
        }
    }

}
