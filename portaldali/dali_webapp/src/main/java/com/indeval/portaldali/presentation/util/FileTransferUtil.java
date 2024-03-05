/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * Clase de utileria para los procesos de file transfer.
 * 
 * @author Pablo Balderas
 */
public class FileTransferUtil {

	/** Log del sistema */
	private static final Logger logger = LoggerFactory.getLogger(FileTransferUtil.class);
	
	/**
	 * Lee un archivo y retorna una lista de cadenas, una por fila del archivo.
	 * @param archivo Archivo a leer.
	 * @return Lista con la información del archivo.
	 */
	public static List<String> leerArchivo(UploadedFile archivo) throws BusinessException {
		List<String> contenidoArchivo = new ArrayList<String>();		
        InputStreamReader isr = null;
        BufferedReader br = null;
        String linea = null;
        try {
            isr = new InputStreamReader(archivo.getInputStream());
            br = new BufferedReader(isr);
            linea = br.readLine();
            while(linea != null) {
                if(StringUtils.isNotBlank(linea.trim())) {
                	linea = linea.toUpperCase();
                	contenidoArchivo.add(linea); 
                }
                linea = br.readLine();
            }
            return contenidoArchivo;
        }
        catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
        finally {
            if ( br != null ) {
                try {
                    br.close();
                } 
                catch (IOException e) {
                    logger.warn(e.getMessage(), e);
                }
            }
            if ( isr != null ) {
                try {
                    isr.close();
                }
                catch (IOException e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }
		
	}
	
	
}
