package com.indeval.portaldali.presentation.util;

import java.awt.Color;
import java.util.HashMap;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;

public class PDFConstantes {
    public static final HashMap<String, Integer> alignments = new HashMap<String, Integer>();
    public static final HashMap<String, Integer> valignments = new HashMap<String, Integer>();
    
    public static final float DOC_BORDER_LEFT = 50;
    public static final float DOC_BORDER_RIGHT = 30;
    public static final float DOC_BORDER_TOP = 65;
    public static final float DOC_BORDER_BOTTOM = 30;
    
    public static final Font DOC_HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD,12.0f);
    public static final Font DOC_FOOTER_FONT = FontFactory.getFont(FontFactory.HELVETICA,6.5f);
    public static final Font TABLE_HEADER_FONT  = FontFactory.getFont(FontFactory.HELVETICA_BOLD,6.0f);;
    public static final Font TABLE_FOOTER_FONT  = FontFactory.getFont(FontFactory.HELVETICA,5.0f);;
    public static final Font TEXT_FONT = FontFactory.getFont(FontFactory.HELVETICA,5.0f);
    public static final Font SUBTITLE_TEXT_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD,9.0f);
    public static final Font CEL_VALUE_FONT = FontFactory.getFont(FontFactory.HELVETICA,5.0f);
    public static final Font FILTER_LABEL_FONT = FontFactory.getFont(FontFactory.HELVETICA,5.0f);
    public static final Font FILTER_VALUE_FONT = FontFactory.getFont(FontFactory.HELVETICA,5.0f);
    
    public static final Color DOC_COLOR_INDEVAL = new Color( 0 ,1 ,83 );
    public static final Color TABLE_HEADER_COLOR = new Color(0, 64, 128);
    public static final Color TABLE_HEADER_BG_COLOR = new Color(153, 179, 204);
    public static final Color TABLE_HEADER_BORDER_COLOR = Color.WHITE;
    public static final Color TABLE_HEADER_EMPTY_BG_COLOR = Color.WHITE;
    public static final Color TABLE_HEADER_EMPTY_BORDER_COLOR = Color.WHITE;
    public static final Color TABLE_FOOTER_COLOR = Color.BLACK;
    public static final Color TABLE_FOOTER_BG_COLOR = Color.WHITE;
    public static final Color TABLE_FOOTER_BORDER_COLOR = new Color(153, 179, 204);
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Color SUBTITLE_TEXT_COLOR = new Color(43,51,153);
    public static final Color EMPTY_CELL_BG_PAIR_COLOR = new Color(235, 235, 235);
    public static final Color EMPTY_CELL_BG_ODD_COLOR = Color.WHITE;
    public static final Color EMPTY_CELL_BORDER_COLOR = Color.WHITE;
    public static final Color CEL_COLOR_VALUE = Color.BLACK;
    public static final Color CEL_PAIR_COLOR = new Color(235, 235, 235);
    public static final Color CEL_ODD_COLOR = Color.WHITE;
    public static final Color CEL_BORDER_COLOR = Color.WHITE;
    public static final Color FILTER_COLOR_LABEL= Color.BLACK;
    public static final Color FILTER_COLOR_VALUE= Color.BLACK;
    
    public static final Color GRID_BORDER_COLOR= new Color(153, 179, 204);

    
    static{
        TEXT_FONT.setColor(TEXT_COLOR);
        alignments.put("CENTER", new Integer(Paragraph.ALIGN_CENTER));
        alignments.put("LEFT", new Integer(Paragraph.ALIGN_LEFT));
        alignments.put("RIGHT", new Integer(Paragraph.ALIGN_RIGHT));
        
        valignments.put("MIDDLE", new Integer(Paragraph.ALIGN_MIDDLE));
        valignments.put("TOP", new Integer(Paragraph.ALIGN_TOP));
        valignments.put("BOTTOM", new Integer(Paragraph.ALIGN_BOTTOM));
        
        TABLE_HEADER_FONT.setColor(TABLE_HEADER_COLOR);
        TABLE_HEADER_FONT.setStyle(Font.BOLD);
        TABLE_FOOTER_FONT.setColor(TABLE_FOOTER_COLOR );
        TABLE_FOOTER_FONT.setStyle(Font.BOLD);
        SUBTITLE_TEXT_FONT.setColor(SUBTITLE_TEXT_COLOR);
        FILTER_LABEL_FONT.setColor(FILTER_COLOR_LABEL);
        FILTER_VALUE_FONT.setColor(FILTER_COLOR_VALUE);
        DOC_HEADER_FONT.setColor(DOC_COLOR_INDEVAL);
        DOC_FOOTER_FONT.setColor(DOC_COLOR_INDEVAL);
        CEL_VALUE_FONT.setColor(CEL_COLOR_VALUE);
    }
}
