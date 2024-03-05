package com.indeval.portaldali.presentation.jsf.xls;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.indeval.portaldali.presentation.util.UtilsXLS;

public class XLSColumnRenderer extends Renderer {

	private static final String BGCOLOR_NONE = "none";
	private Logger logger = LoggerFactory.getLogger(XLSColumnRenderer.class);

	public XLSColumnRenderer() {
		super();

	}

	public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {
		UtilsXLS utils = UtilsXLS.getInstance();
		try {
			XLSColumnComponent xlsComponent = (XLSColumnComponent) component;
			if (!xlsComponent.isRendered()) {
				return;
			}
			HSSFRow row = ((XLSRowComponent) xlsComponent.getParent()).getRow();
			HSSFCell cell = row.createCell(xlsComponent.getCellNumber().shortValue());
			Object valor = xlsComponent.getValue();
			utils.setCellValue(cell, valor);
			int cellType = utils.getXLSValueType(valor);
			cell.setCellType(cellType);
			HSSFCellStyle style;
			if (xlsComponent.isOddRow().booleanValue()) {
				style = utils.getOddCellStyle(xlsComponent);
			} else {
				style = utils.getPairCellStyle(xlsComponent);
			}
			
			String cellBgColor = null;
			if(xlsComponent.getValueBinding("bgcolor") != null) {
				cellBgColor = (String) xlsComponent.getValueBinding("bgcolor").getValue(facesContext);
			}
			
			if (cellBgColor != null && cellBgColor.length() > 0 && !cellBgColor.equalsIgnoreCase(BGCOLOR_NONE)
					&& cellBgColor.length() == 6) {
				style = utils.getRedCellStyle(xlsComponent);
			}

			if (xlsComponent.getPattern() != null && cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				HSSFDataFormat format = xlsComponent.getDataFormat();
				style.setDataFormat(format.getFormat(xlsComponent.getPattern()));
			}
            logger.trace("rendering Column. Value: " + valor);
			cell.setCellStyle(style);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
