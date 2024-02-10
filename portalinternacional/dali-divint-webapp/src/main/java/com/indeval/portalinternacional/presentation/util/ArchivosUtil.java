/**
 * Portal Internacional
 * Copyrigth (c) 2022 Indeval. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.PersistenceException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;

/**
 * Clase de utileria para el manejo de archivos.
 * 
 * @author Pablo Balderas
 */
public class ArchivosUtil {
	
	/**
	 * Metodo que lista todos los archivos de un directorio.
	 * @param ruta Directorio a explorar.
	 * @return Lista con los nombres de los archivos.
	 * @throws BusinessException En caso de ocurrir un error.
	 */
	public static List<String> obtenerListaArchivosDirectorio(String ruta) throws BusinessException {
		List<String> listaArchivos = new ArrayList<String>();
		try {
			File directorio = new File(ruta);
			List<File> files = (List<File>) FileUtils.listFiles(directorio, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
			for (File file : files) {
				listaArchivos.add(file.getName());
			}
		}
		catch (Exception e) {
			throw new BusinessException("Ocurrio un error al explorar el directorio " + ruta, e);
		}
		return listaArchivos;
	}
	

	/**
	 * Metodo que valida si un archivo o directorio existe
	 * @param ruta Ruta del archivo o directorio a validar
	 * @return true si existe, false en caso contrario
	 */
	public static boolean existeArchivoDirectorio(String ruta) {
		Path path = Paths.get(ruta);
		return Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
	}
	
	/**
	 * Metodo que crea un directorio  
	 * @param ruta Ruta del directorio a crear
	 */
	public static void crearDirectorio(String ruta) throws BusinessException {
		try {
		    Files.createDirectory(Paths.get(ruta));
		}
		catch(FileAlreadyExistsException e){
			throw new BusinessException("Ya existe el directorio " + ruta, e);
		}
		catch (IOException e) {
			throw new BusinessException("Ocurrio un error al crear el directorio " + ruta, e);
		}
	}
	
	/**
	 * Metodo que elimina un directorio
	 * @param ruta Ruta del directorio a eliminar
	 * @throws FileSystemErrorException En caso de ocurrir un error
	 */
	public static void eliminarDirectorio(String ruta) throws BusinessException {
		try {
			Files.delete(Paths.get(ruta));
		}
		catch (IOException e) {
			throw new BusinessException("Ocurrio un error al eliminar el directorio " + ruta, e);
		}
	}
	
	/**
	 * Elimina un directorio junto con su contenido.
	 * @param ruta Ruta del directorio a eliminar.
	 * @throws BusinessException En caso de ocurrir un error.
	 */
	public static void eliminarDirectorioConContenido(String ruta) throws BusinessException {
		try {
			FileUtils.deleteDirectory(new File(ruta));
		}
		catch (IOException e) {
			throw new BusinessException("Ocurrio un error al eliminar el directorio " + ruta, e);
		}
	}
	
	
	/**
	 * Metodo que guarda un archivo
	 * @param inputStream Flujo de datos a guardar en disco
	 * @param ruta Ruta donde se guardara el archivo
	 * @throws FileSystemErrorException En caso de ocurrir un error
	 */
	public static void guardarArchivo(InputStream inputStream, String ruta) throws BusinessException {
		try {
			Files.copy(inputStream, Paths.get(ruta));
		}
		catch (IOException e) {
			throw new BusinessException("Ocurrrió un error al escribir el archivo " + ruta, e);
		}
	}
	
	/**
	 * Metodo que guarda un archivo en disco.
	 * @param bytes arreglo de bytes que representan el archivo.
	 * @param ruta Ruta donde se guardara el archivo
	 */
	public static void guardarArchivo(byte[] bytes, String ruta) throws BusinessException {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(ruta);
			fos.write(bytes);
			fos.close();
		} 
		catch (IOException e) {
			throw new BusinessException("Ocurrrió un error al escribir el archivo " + ruta, e);
		}
	}
	
	
	/**
	 * Metodo que guarda la cadena del argumento en un archivo en disco.
	 * @param cadenaGuardar Cadena a guardar en archivo.
	 * @param ruta Ruta donde se guardara el archivo.
	 * @throws BusinessException En caso de ocurrir un error.
	 */
	public static void guardarCadenaEnArchivo(String cadenaGuardar, String ruta) throws BusinessException {
		try {
			FileUtils.writeStringToFile(new File(ruta), cadenaGuardar, StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			throw new BusinessException("Ocurrrió un error al escribir el archivo " + ruta, e);
		}
	}
	
	/**
	 * Metodo que escribe un arreglo de bytes en un archivo.
	 * @param contenido Arreglo de bytes a guardar.
	 * @param rutaArchivo Ruta donde se guarda el archivo.
	 * @throws IOException 
	 */
	public static void escribirArregloBytesArchivo(byte[] contenido, String rutaArchivo) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(rutaArchivo);
			fos.write(contenido);
			fos.close();
		}
		catch (IOException e) {
			throw new BusinessException("Ocurrrió un error al escribir el archivo " + rutaArchivo, e);
		}
	}
	
	/**
	 * Metodo que elimina un archivo
	 * @param ruta Ruta donde esta el archivo a eliminar
	 * @throws PersistenceException En caso de ocurrir un error
	 */
	public static void eliminarArchivo(String ruta) throws BusinessException {
		try {
			Files.delete(Paths.get(ruta));
		}
		catch (IOException e) {
			throw new BusinessException("Ocurrrió un error al eliminar el archivo " + ruta, e);
		}
	}
	
	/**
	 * Metodo que mueve un archivo.
	 * @param rutaOrigen Ruta origen
	 * @param rutaDestino Ruta destino
	 * @throws FileSystemErrorException En caso de ocurrir un error
	 */
	public static void moverArchivo(String rutaOrigen, String rutaDestino) throws Exception {
		try {
			Path origen = Paths.get(rutaOrigen);
			Path destino = Paths.get(rutaDestino);
			Files.move(origen, destino); 
		}
		catch (IOException e) {
			throw new Exception("Ocurrrió un error al mover el archivo de " + rutaOrigen + " a " + rutaDestino, e);
		}
	}
	
	/**
	 * Metodo que copia un archivo.
	 * @param rutaOrigen Ruta origen
	 * @param rutaDestino Ruta destino
	 * @throws FileSystemErrorException En caso de ocurrir un error
	 */
	public static void copiarArchivo(String rutaOrigen, String rutaDestino) throws BusinessException {
		try {
			Path origen = Paths.get(rutaOrigen);
			Path destino = Paths.get(rutaDestino);
			Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			throw new BusinessException("Ocurrrió un error al copiar el archivo de " + rutaOrigen + " a " + rutaDestino, e);
		}
	}
	
	/**
	 * Metodo que lee un archivo.
	 * @param ruta Ruta donde esta el archivo a leer
	 * @return Arreglo de bytes con el contenido del archivo
	 * @throws FileSystemErrorException En caso de ocurrir un error
	 */
	public static byte[] leerArchivo(String ruta) throws BusinessException {
		try {
			byte[] archivoEnBytes = Files.readAllBytes(Paths.get(ruta));
			return archivoEnBytes;
		}
		catch (IOException e) {
			throw new BusinessException("Ocurrrió un error al leer el archivo " + ruta, e);
		}
	}
	
	
	/**
	 * Comprime un directorio y lo devuelve como un array de bytes.
	 * @param rutaDirectorio Ruta a comprimir.
	 * @return Array de bytes que representa el archivo zip.
	 * @throws BusinessException En caso de ocurrir un error.
	 */
	public static ByteArrayOutputStream comprimirDirectorioAsByteArrayOutputStream(final Path rutaDirectorio) throws BusinessException {
		try {
			//Crea un objeto ByteArrayOutputStream
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			//Crea un objeto ZipOutputStream con el objeto anterior como base
	        final ZipOutputStream zos = new ZipOutputStream(baos);
	        zos.setLevel(Deflater.BEST_COMPRESSION);

            Files.walkFileTree(rutaDirectorio, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    zos.putNextEntry(new ZipEntry(rutaDirectorio.relativize(file).toString()));
                    Files.copy(file, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
            zos.close();
			
            return baos;
		}
		catch (Exception e) {
			throw new BusinessException("Ocurrrió un error al generar el archivo ZIP ", e);
		}
	}
	
	
	

}
