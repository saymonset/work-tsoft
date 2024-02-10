/**
 * Portal Internacional
 * Copyrigth (c) 2022 Indeval. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;

/**
 * @author Pablo Balderas
 *
 */
public class ArchivosCSVUtil {

	/**
	 * Crea un nombre de directorio concatenando el prefijo con la fecha actual.
	 * @param prefijo Prefijo del directorio.
	 * @return Nombre del directorio.
	 */
	public static String crearNombreDirectorio(String prefijo) {
		return prefijo.concat(Long.valueOf(System.currentTimeMillis()).toString());
	}
	
	/**
	 * Crea un archivo CSV a partir de la lista de cadenas en el directorio especificado.
	 * @param directorio Directorio donde se deposita el archivo.
	 * @param registros Lista con los registros a guardar en el archivo.
	 * @throws IOException 
	 */
	public static void crearArchivoCSV(String directorio, List<String> registros, String headers) throws BusinessException {
		try {
	        Path path = Paths.get(directorio);
	        Path temp = Files.createTempFile(path, null, ".csv");
	        registros.add(0, headers);
	        FileUtils.writeLines(temp.toFile(), StandardCharsets.ISO_8859_1.name(), registros);
		}
		catch (IOException e) {
			throw new BusinessException("Ocurrio un error al crear el archivo CSV", e);
		}
	}
		
}