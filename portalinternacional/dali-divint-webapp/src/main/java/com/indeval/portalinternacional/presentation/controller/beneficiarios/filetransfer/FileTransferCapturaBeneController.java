package com.indeval.portalinternacional.presentation.controller.beneficiarios.filetransfer;

import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.convertInputStreamToHSSFWorkbook;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCapturaBenefService;
import com.indeval.portalinternacional.middleware.services.util.FileUploadService;
import com.indeval.portalinternacional.middleware.services.util.UtilDivintBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.util.vo.ProcessInfoVO;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.FileTransferBenefConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoFormatoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferForma;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.exception.MulticargaException;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferCapturaBenefVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenCargaFilebenefVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class FileTransferCapturaBeneController extends ControllerBase {

	/** Servicio para el bloqueo y cordinaci&oacute;n del servicio */
	private FileUploadService fileUploadService = null;

	/** Servicio para llenar los tipos de Formatos */
	private UtilDivintBeneficiariosService utilDivintBeneficiariosService;

	private Map<String, FileTransferCapturaBenefService<FileTransferForma>> mapfileTransfersCapturaBenefServices;

	/** Servicio fileTransferCapturaBenefService */
	private FileTransferCapturaBenefService<FileTransferForma> fileTransferCapturaBenefService;

	/** Informacion del proceso de carga actual */
	private ProcessInfoVO processInfo;

	/** VO para guardar el archivo a cargar */
	private FileTransferCapturaBenefVO fileTransferCapturaBenefVO;

	/** VO con el resumen de la carga temporal */
	private ResumenCargaFilebenefVO resumenCargaFilebenefVO;

	/** VO con los datos del agente firmado */
	private AgenteVO agenteVO;

	/** idFolio de la instituci&oacute;n */
	private String idFolioInstitucion;

	/** Archivo subido por el usuario */
	private UploadedFile archivo;

	/** Registros cargados */
	private Integer registrosCargados;

	/** Registros con Error */
	private Integer registrosConError;

	/** Total de Registros */
	private Integer totalRegistros;

	/** nombre corto del Usuario responsable */
	private String usuarioResponsable;

	private Date fechaFormato;

	/** bandera para indicar el incio de carga de la pantalla */
	private boolean banderaInicio = false;

	/** bandera para mostrar la seccion de carga del archivo */
	private boolean mostrarSeccionArchivo = false;

	/**
	 * bandera para permitir o bloquear la selecci&oacute;n de forma a cargar
	 */
	private boolean opcionForma = false;

	/** bandera para mostrar la tabla de informaci&oacute;n */
	private boolean mostrarTablaInfo = false;

	/** bandera para mostrar el resumen de errores */
	private boolean muestraResumenErrores = false;

	/** ID de la pagina principal */
	private String navHome;

	/** Nombre del proceso arrancado,BE */
	private String process;

	/** tipo formato */
	private String tipoFormatoCadena;

	/** formato seleccionado */
	private String formato;

	/** lista con los tipos de formatos */
	private List<SelectItem> listaTipoFormato;

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FileTransferCapturaBeneController.class);

	/** lista con el detalle de la carga calificada */
	private List<FileTransferForma> detalleCarga;

	/** lista para guardar el total de registros con Error */
	private List<FileTransferForma> listaRegistrosError;

	private String urlDescargaFormato;

	/**
	 * Recupera la informaci&oacute;n del proceso que est&aacute; actualmente
	 * corriendo para mostrar la pantalla por primera vez.
	 * 
	 * @return null
	 */
	public String getInit() {
		if (!this.banderaInicio) {
			this.setRegistrosXPag(5);
			this.processInfo = null;
			this.resumenCargaFilebenefVO = null;
			this.fileTransferCapturaBenefVO = null;
			this.listaRegistrosError = null;
			this.detalleCarga = null;
			this.tipoFormatoCadena = "-1";
			this.agenteVO = this.getAgenteFirmado();
			this.usuarioResponsable = this.agenteVO.getNombreCorto();
			this.idFolioInstitucion = this.agenteVO.getId() + this.agenteVO.getFolio();
			this.fechaFormato = this.utilDivintBeneficiariosService.obtieneFechaActual();
			this.inicializaTipoFormato();
			this.banderaInicio = true;
			this.getDetalleProceso();
			if (this.processInfo != null) {
				this.getResumenPrevioCarga();
			} else {
				this.setRegistrosCargados(0);
				this.setRegistrosConError(0);
				this.setTotalRegistros(0);
			}
		}
		return null;
	}

	/**
	 * Consulta el resumen de la validaciones aplicadas y el resultado de estas
	 * a la &uacute;ltima carga del archivo del usuario
	 * 
	 * @return
	 */
	public String getResumenPrevioCarga() {
		try {

			this.resumenCargaFilebenefVO = this.mapfileTransfersCapturaBenefServices
					.get(this.mapfileTransfersCapturaBenefServices.keySet().iterator().next())
					.consultaCargaExistente(this.idFolioInstitucion);
			if (this.resumenCargaFilebenefVO != null) {
				this.listaRegistrosError = null;
				this.setRegistrosCargados(this.resumenCargaFilebenefVO.getRegistrosACargar());
				this.setRegistrosConError(this.resumenCargaFilebenefVO.getRegistrosConError());
				this.setTotalRegistros(this.resumenCargaFilebenefVO.getTotalRegistros());
				this.tipoFormatoCadena = this.resumenCargaFilebenefVO.getTipoFormato().toString();

				this.fileTransferCapturaBenefService = this.mapfileTransfersCapturaBenefServices
						.get(this.tipoFormatoCadena);
				this.listaRegistrosError = this.fileTransferCapturaBenefService
						.obtenerRegistrosError(this.resumenCargaFilebenefVO.getXmlRegistrosConError());

				// si tiene registros con error se paginan
				if (this.listaRegistrosError != null && !this.listaRegistrosError.isEmpty()) {
					this.paginaVO.setTotalRegistros(this.listaRegistrosError.size());
					this.paginaVO.setOffset(0);
					this.paginaVO.extraerSublist(this.listaRegistrosError);
				}
				this.mostrarSeccionArchivo = false;
				this.mostrarTablaInfo = false;

				this.opcionForma = true;
				this.muestraResumenErrores = true;

			} else {
				this.restauraValoresInicio();
			}
			return this.navHome;
		} catch (Exception e) {
			log.error("Error", e);
			this.eliminaPreviaCarga();
			this.restauraValoresInicio();
			e.printStackTrace();
			this.addErrorMessage("Error al obtener el resumen");
			return this.navHome;
		}
	}

	/**
	 * metodo que tomo el evento del boton procesar, toma los registros
	 * correctos y los guarda en t_beneficiarios, elimina el regitro temporal y
	 * quita el candado del usuario
	 * 
	 * @param event
	 */
	public void procesar(final ActionEvent event) {
		String mensaje = null;
		int registrosGuardados = 0;
		try {
			this.processInfo.setUsuario(this.idFolioInstitucion);
			this.processInfo.setIdProceso(this.idFolioInstitucion + this.process);
			this.processInfo = this.fileUploadService.getProcessInfo(this.processInfo);
			if (this.processInfo != null && this.processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO)) {
				this.processInfo.setStatus(ProcessInfoVO.GUARDANDO);
				this.processInfo.setPorcentajeTerminado(new BigDecimal(50));
				this.fileUploadService.updateProcessInfo(this.processInfo);

				Boolean cuentaConRegistrosCorrectos = Boolean.FALSE;
				Long tipoFormato = this.resumenCargaFilebenefVO.getTipoFormato();
				String claveUsuario = this.resumenCargaFilebenefVO.getUsuario();

				if (this.getRegistrosCargados() > 0) {
					registrosGuardados = this.fileTransferCapturaBenefService.guardaBeneficiarios(claveUsuario,
							tipoFormato);
					cuentaConRegistrosCorrectos = Boolean.TRUE;
				} else {
					this.fileTransferCapturaBenefService.eliminaCargaExistente(claveUsuario,
							cuentaConRegistrosCorrectos);
				}
				if (registrosGuardados > 0) {
					mensaje = "Beneficiarios Guardados de manera Exitosa";
					this.addMessage(mensaje);
				}
				this.processInfo.setStatus(ProcessInfoVO.TERMINADO);
				this.processInfo.setPorcentajeTerminado(new BigDecimal(100));
				this.fileUploadService.updateProcessInfo(this.processInfo);
			} else {
				if (this.processInfo != null) {
					mensaje = "Error: Los registros ya fueron procesados";
				} else {
					mensaje = "Error: No existe proceso iniciado";
				}
				throw new MulticargaException(mensaje);
			}
		} catch (MulticargaException mx) {
			this.addErrorMessage(mx.getMessage());
			mx.printStackTrace();
			this.eliminaPreviaCarga();
		} catch (BusinessException be) {
			this.eliminaPreviaCarga();
			be.printStackTrace();
			mensaje = "Error al guardar los Beneficiarios";
			this.addErrorMessage(mensaje);
		} finally {
			this.setRegistrosCargados(registrosGuardados);
			this.setRegistrosConError(this.getTotalRegistros() - this.getRegistrosCargados());
			this.restauraValoresInicio();
		}
	}

	/**
	 * toma el evento del boton cancelar, elimina la precarga y regresa los
	 * valores para una nueva carga
	 */
	public void cancelar(final ActionEvent event) {
		String mensaje = null;
		try {
			this.processInfo.setUsuario(this.idFolioInstitucion);
			this.processInfo.setIdProceso(this.idFolioInstitucion + this.process);
			this.processInfo = this.fileUploadService.getProcessInfo(this.processInfo);

			if (this.processInfo != null && this.processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO)) {
				this.processInfo.setStatus(ProcessInfoVO.TERMINADO);
				this.processInfo.setPorcentajeTerminado(new BigDecimal(100));
				this.fileUploadService.updateProcessInfo(this.processInfo);

				Boolean cuentaConRegistrosCorrectos = Boolean.FALSE;
				if (this.resumenCargaFilebenefVO.getRegistrosACargar() != null
						&& this.resumenCargaFilebenefVO.getRegistrosACargar() > 0) {
					cuentaConRegistrosCorrectos = Boolean.TRUE;
				}

				this.fileTransferCapturaBenefService.eliminaCargaExistente(this.resumenCargaFilebenefVO.getUsuario(),
						cuentaConRegistrosCorrectos);
			} else {
				if (this.processInfo != null) {
					mensaje = "Error: Los registros ya fueron procesados";
				} else {
					mensaje = "Error: No existe proceso iniciado";
				}
				throw new MulticargaException(mensaje);
			}
		} catch (MulticargaException mx) {
			this.addErrorMessage(mx.getMessage());
			mx.printStackTrace();
			this.eliminaPreviaCarga();
		} catch (BusinessException be) {
			this.eliminaPreviaCarga();
			be.printStackTrace();
			mensaje = "Error al cancelar la operaci�n";
			this.addErrorMessage(mensaje);
		} finally {
			this.restauraValoresInicio();
			this.setRegistrosCargados(0);
			this.setRegistrosConError(0);
			this.setTotalRegistros(0);
		}
	}

	/**
	 * m&eacute;todo para limpiar las propiedades, cambiar las banderas para una
	 * nueva carga y elimina las precargas del usuario
	 */
	private void restauraValoresInicio() {
		if (this.paginaVO.getRegistros() != null && !this.paginaVO.getRegistros().isEmpty()) {
			this.paginaVO.getRegistros().clear();
		}
		this.processInfo = null;
		this.resumenCargaFilebenefVO = null;
		this.fileTransferCapturaBenefVO = null;
		this.listaRegistrosError = null;
		this.detalleCarga = null;
		this.mostrarSeccionArchivo = true;
		this.mostrarTablaInfo = true;
		this.opcionForma = false;
		this.muestraResumenErrores = false;

		if (this.tipoFormatoCadena != null) {
			this.formato = this.getFormado(this.tipoFormatoCadena).getLabel();
		}
		this.release();
	}

	/**
	 * m&eacute;todo para manipular la paginaci&oacute;n
	 */
	@Override
	public String ejecutarConsulta() {
		this.paginaVO.extraerSublist(this.listaRegistrosError);
		return null;
	}

	/**
	 * toma el evento de iniciar carga de archivo, genera un proceso para el
	 * usuario en turno, verifica y guarda el archivo ingresado y muestra la
	 * informaci&oacute;n de este
	 */
	public void uploadFile(final ActionEvent event) {
		String mensaje = null;
		this.fileTransferCapturaBenefVO = null;
		try {
			this.validaFormatoArchivo();
			this.fileTransferCapturaBenefVO = this.generaVO();

			if (this.processInfo == null) {
				this.processInfo = new ProcessInfoVO();
			}
			this.processInfo.setUsuario(this.idFolioInstitucion);
			this.processInfo.setIdProceso(this.idFolioInstitucion + this.process);
			this.processInfo.setAbort('B');// F
			this.processInfo.setPorcentajeTerminado(new BigDecimal(0));
			this.processInfo.setStatus(ProcessInfoVO.CARGANDO);

			Boolean candado = this.fileUploadService.obtainLock(this.processInfo);
			if (!candado.booleanValue()) {
				mensaje = "Ya hay un proceso de carga iniciado para este tipo de papel";
				this.addErrorMessage(mensaje);
				return;
			}

			this.fileTransferCapturaBenefService.guardaCargaTemporal(this.fileTransferCapturaBenefVO,
					this.idFolioInstitucion);

			this.getResumenPrevioCarga();

			this.processInfo.setStatus(ProcessInfoVO.PROCESANDO);
			this.processInfo.setPorcentajeTerminado(new BigDecimal(50));
			this.fileUploadService.updateProcessInfo(this.processInfo);

		} catch (MulticargaException mx) {
			this.addErrorMessage(mx.getMessage());
			mx.printStackTrace();
		} catch (BusinessException bex) {
			this.eliminaPreviaCarga();
			this.restauraValoresInicio();
			this.addErrorMessage(bex.getMessage());
			bex.printStackTrace();
		} catch (Exception e) {
			this.eliminaPreviaCarga();
			this.restauraValoresInicio();
			e.printStackTrace();
			this.addErrorMessage("Error al realizar la carga");
		}

	}

	/** m&eacute;todo para obtener el resumen de la precarga */
	// private ResumenCargaFilebenefVO obtieneResumenCargaPrevia(){
	// return
	// mapfileTransfersCapturaBenefServices.get(tipoFormatoCadena).consultaCargaExistente(idFolioInstitucion);
	// }

	/** Metodo que valida el formato del archivo */
	private void validaFormatoArchivo() throws MulticargaException {
		log.debug("**Validando Formato Archivo**");
		String mensajeError = null;
		if (this.archivo == null) {
			mensajeError = "El archivo de operaciones es un dato requerido";
			throw new MulticargaException(mensajeError);
		}
		String nombreArchivo = StringUtils.upperCase(StringUtils.trimToEmpty(this.archivo.getName()));

		if (nombreArchivo.endsWith("XLSX")) {
			mensajeError = "Cambiar formato, guardar con extensi\u00F3n .xls";
			throw new MulticargaException(mensajeError);
		}
		if (!nombreArchivo.endsWith("XLS")) {
			mensajeError = "Formato incorrecto, s\u00F3lo se acepta formato .xls";
			throw new MulticargaException(mensajeError);
		}

		if (StringUtils.isBlank(this.agenteVO.getId()) || StringUtils.isBlank(this.agenteVO.getFolio())
				|| StringUtils.isBlank(this.idFolioInstitucion)) {
			mensajeError = "Se necesita un usuario con sesi\u00F3n iniciada";
			throw new MulticargaException(mensajeError);
		}
		if (StringUtils.isBlank(this.tipoFormatoCadena) || Long.valueOf(this.tipoFormatoCadena) < 0) {
			mensajeError = "Seleccione el Tipo Formato a cargar";
			throw new MulticargaException(mensajeError);
		}

	}

	/**
	 * Metodo que genera el FileTransferCapturaBenefVO tomando los datos
	 * ingresados por el usuario
	 */
	private FileTransferCapturaBenefVO generaVO() throws MulticargaException {
		log.debug("**Generando el MulticargaVO**");
		FileTransferCapturaBenefVO fileTransferCapturaBenefVO = null;
		HSSFWorkbook Wb = null;
		try {
			Wb = convertInputStreamToHSSFWorkbook(this.archivo.getInputStream());
			fileTransferCapturaBenefVO = new FileTransferCapturaBenefVO();
			fileTransferCapturaBenefVO.setArchivo(Wb);
			fileTransferCapturaBenefVO.setId(this.agenteVO.getId());
			fileTransferCapturaBenefVO.setFolio(this.agenteVO.getFolio());
			fileTransferCapturaBenefVO.setClaveUsuario(this.idFolioInstitucion);
			fileTransferCapturaBenefVO.setTipoFormato(Long.valueOf(this.tipoFormatoCadena));
			fileTransferCapturaBenefVO.setUsuarioIndeval(this.isInstitucionIndeval());
		} catch (IOException ioex) {
			throw new MulticargaException("Error al leer el archivo");
		}
		return fileTransferCapturaBenefVO;
	}

	/**
	 * Verifica si existe un proceso corriendo
	 */
	public Boolean getIsProcessRunning() {
		this.processInfo.setUsuario(this.agenteVO.getClave() + this.agenteVO.getFolio());
		this.processInfo.setIdProceso(this.agenteVO.getClave() + this.agenteVO.getFolio() + this.process);
		return this.fileUploadService.isProcessRunning(this.processInfo);
	}

	/**
	 * Inicializa lista de tipos de carga
	 */
	private void inicializaTipoFormato() {

		List<TipoFormatoBeneficiario> tipoFormatoBeneficiario = this.utilDivintBeneficiariosService
				.obtieneCatalogoTipoOperacionMulticarga();

		if (tipoFormatoBeneficiario != null) {
			if (this.listaTipoFormato == null) {
				this.listaTipoFormato = new ArrayList<SelectItem>();
			} else {
				this.listaTipoFormato.clear();
			}
			for (TipoFormatoBeneficiario tipos : tipoFormatoBeneficiario) {
				// TEMPORAL: Se desactiva formato w8bene hasta que salga la
				// version 2016
				// if (!tipos.getIdTipoFormatoBene().equals(3l)
				// && !tipos.getIdTipoFormatoBene().equals(4l)) {
				this.listaTipoFormato.add(
						new SelectItem(tipos.getIdTipoFormatoBene().toString(), tipos.getDescripcionTipoFormato()));
				// }
			}

		}

	}

	/**
	 * retorna el valor del tipo formato seleccionado
	 */
	// public String getSelectedTipo(){
	// String resultado = null;
	// resultado = getSelected(getTipoFormatoCadena() ,this.listaTipoFormato);
	// if(resultado != null){
	// return resultado;
	// }
	//
	// return resultado;
	// }

	/**
	 * m&eacute;todo de ayuda para tomar el valor de las listas tipoOperacion y
	 * estadoRegistro
	 */
	// private String getSelected(String key, List<SelectItem> lista){
	// String resultado = null;
	// for(SelectItem item : lista){
	// if(key != null && item.getValue().equals(key))
	// resultado=item.getLabel();
	// }
	// return resultado;
	// }

	/**
	 * Recupera el detalle de la informaci&oacute;n que se esta cargando
	 * actualmente o que espera confirmaci&oacute;n.
	 * 
	 * @return
	 */
	public void getDetalleProceso() {
		try {
			if (this.processInfo == null) {
				this.processInfo = new ProcessInfoVO();
			}
			this.processInfo.setUsuario(this.idFolioInstitucion);
			this.processInfo.setIdProceso(this.idFolioInstitucion + this.process);
			this.processInfo = this.fileUploadService.getProcessInfo(this.processInfo);

		} catch (BusinessException ex) {
			log.error("Error al obtenber el estatus del proceso de carga", ex);
			this.release();
		}
	}

	/**
	 * toma el evento y actualiza la paginaci&oacute;n
	 */
	public void actualizarPaginacionRegistro(final ActionEvent event) {
		this.paginaVO.extraerSublist(this.listaRegistrosError);
	}

	/** toma el evento para validar la selecci&oacute;n de algun formato */
	public void valorTipoFormato(final ValueChangeEvent event) {
		this.tipoFormatoCadena = (String) event.getNewValue();
		if (Long.valueOf(this.tipoFormatoCadena) > 0) {
			this.formato = this.getFormado(this.tipoFormatoCadena).getLabel();
			this.mostrarSeccionArchivo = true;
			this.mostrarTablaInfo = true;
			this.fileTransferCapturaBenefService = this.mapfileTransfersCapturaBenefServices
					.get(this.tipoFormatoCadena);
			log.info("Recuperando el FileTransfer(" + this.tipoFormatoCadena + ")="
					+ this.fileTransferCapturaBenefService);
		} else {
			this.mostrarSeccionArchivo = false;
			this.mostrarTablaInfo = false;
		}
	}

	public SelectItem getFormado(final String value) {
		for (SelectItem item : this.listaTipoFormato) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Quita el lock de la base y resetea las banderas
	 */
	private void release() {
		if (this.processInfo == null) {
			this.processInfo = new ProcessInfoVO();
		}
		this.processInfo.setUsuario(this.idFolioInstitucion);
		this.processInfo.setIdProceso(this.idFolioInstitucion + this.process);
		this.fileUploadService.releaseLock(this.processInfo);
	}

	/**
	 * si ocurre una Exception elimina los registros precargados, para que el
	 * usuario pueda volver a cargar el archivo
	 */
	private void eliminaPreviaCarga() {
		this.fileTransferCapturaBenefService.eliminaCargaExistente(this.idFolioInstitucion, null);
	}

	/**
	 * regresa la lista que contiene los registros cargados, son los registros
	 * para el llenado del archivo excel
	 */
	public void detalleCargaBeneficiario() {
		log.debug("obteniendo el detalle con formato :" + this.resumenCargaFilebenefVO.getTipoFormato());
		// De acuerdo al tipo de file transfer, coloca el nombre del archivo
		if (Constantes.FORMATO_W9 == this.resumenCargaFilebenefVO.getTipoFormato().longValue()) {
			this.urlDescargaFormato = FileTransferBenefConstantes.ARCHIVO_RESUMEN_W9;
		} else if (Constantes.FORMATO_W8BEN2014 == this.resumenCargaFilebenefVO.getTipoFormato().longValue()) {
			this.urlDescargaFormato = FileTransferBenefConstantes.ARCHIVO_RESUMEN_W8BEN;
		} else if (Constantes.FORMATO_W8BENE == this.resumenCargaFilebenefVO.getTipoFormato().longValue()) {
			this.urlDescargaFormato = FileTransferBenefConstantes.ARCHIVO_RESUMEN_W8BENE;
		} else if (Constantes.FORMATO_W8IMY == this.resumenCargaFilebenefVO.getTipoFormato().longValue()) {
			this.urlDescargaFormato = FileTransferBenefConstantes.ARCHIVO_RESUMEN_W8IMY;
		}

		if (this.detalleCarga != null) {
			this.detalleCarga.clear();
		} else {
			this.detalleCarga = new ArrayList<FileTransferForma>();
		}
		if (this.resumenCargaFilebenefVO.getRegistrosConError().intValue() > 0) {
			if (this.listaRegistrosError != null && !this.listaRegistrosError.isEmpty()) {
				this.detalleCarga.addAll(this.listaRegistrosError);
			}
		}
		if (this.resumenCargaFilebenefVO.getRegistrosACargar().intValue() > 0) {
			List<FileTransferForma> listaCorrectosTemp = this.fileTransferCapturaBenefService
					.consultaRegistrosCorrectos(this.idFolioInstitucion);
			if (listaCorrectosTemp != null && !listaCorrectosTemp.isEmpty()) {
				this.detalleCarga.addAll(listaCorrectosTemp);
			}
		}
		Collections.sort(this.detalleCarga);
	}

	public boolean isBanderaInicio() {
		return this.banderaInicio;
	}

	public void setBanderaInicio(final boolean banderaInicio) {
		this.banderaInicio = banderaInicio;
	}

	public boolean isMostrarSeccionArchivo() {
		return this.mostrarSeccionArchivo;
	}

	public void setMostrarSeccionArchivo(final boolean mostrarSeccionArchivo) {
		this.mostrarSeccionArchivo = mostrarSeccionArchivo;
	}

	public boolean isMostrarTablaInfo() {
		return this.mostrarTablaInfo;
	}

	public void setMostrarTablaInfo(final boolean mostrarTablaInfo) {
		this.mostrarTablaInfo = mostrarTablaInfo;
	}

	public boolean isMuestraResumenErrores() {
		return this.muestraResumenErrores;
	}

	public void setMuestraResumenErrores(final boolean muestraResumenErrores) {
		this.muestraResumenErrores = muestraResumenErrores;
	}

	public boolean isOpcionForma() {
		return this.opcionForma;
	}

	public void setOpcionForma(final boolean opcionForma) {
		this.opcionForma = opcionForma;
	}

	public ProcessInfoVO getProcessInfo() {
		return this.processInfo;
	}

	public void setProcessInfo(final ProcessInfoVO processInfo) {
		this.processInfo = processInfo;
	}

	public String getNavHome() {
		return this.navHome;
	}

	public void setNavHome(final String navHome) {
		this.navHome = navHome;
	}

	public String getProcess() {
		return this.process;
	}

	public void setProcess(final String process) {
		this.process = process;
	}

	public UploadedFile getArchivo() {
		return this.archivo;
	}

	public void setArchivo(final UploadedFile archivo) {
		this.archivo = archivo;
	}

	public Integer getRegistrosCargados() {
		return this.registrosCargados;
	}

	public void setRegistrosCargados(final Integer registrosCargados) {
		this.registrosCargados = registrosCargados;
	}

	public Integer getRegistrosConError() {
		return this.registrosConError;
	}

	public void setRegistrosConError(final Integer registrosConError) {
		this.registrosConError = registrosConError;
	}

	public Integer getTotalRegistros() {
		return this.totalRegistros;
	}

	public void setTotalRegistros(final Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public String getUsuarioResponsable() {
		return this.usuarioResponsable;
	}

	public void setUsuarioResponsable(final String usuarioResponsable) {
		this.usuarioResponsable = usuarioResponsable;
	}

	public FileTransferCapturaBenefVO getFileTransferCapturaBenefVO() {
		return this.fileTransferCapturaBenefVO;
	}

	public Date getFechaFormato() {
		return this.fechaFormato;
	}

	public void setFechaFormato(final Date fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	public void setFileTransferCapturaBenefVO(final FileTransferCapturaBenefVO fileTransferCapturaBenefVO) {
		this.fileTransferCapturaBenefVO = fileTransferCapturaBenefVO;
	}

	public ResumenCargaFilebenefVO getResumenCargaFilebenefVO() {
		return this.resumenCargaFilebenefVO;
	}

	public void setResumenCargaFilebenefVO(final ResumenCargaFilebenefVO resumenCargaFilebenefVO) {
		this.resumenCargaFilebenefVO = resumenCargaFilebenefVO;
	}

	public AgenteVO getAgenteVO() {
		return this.agenteVO;
	}

	public void setAgenteVO(final AgenteVO agenteVO) {
		this.agenteVO = agenteVO;
	}

	public String getIdFolioInstitucion() {
		return this.idFolioInstitucion;
	}

	public void setIdFolioInstitucion(final String idFolioInstitucion) {
		this.idFolioInstitucion = idFolioInstitucion;
	}

	public String getTipoFormatoCadena() {
		return this.tipoFormatoCadena;
	}

	public void setTipoFormatoCadena(final String tipoFormatoCadena) {
		this.tipoFormatoCadena = tipoFormatoCadena;
	}

	public List<SelectItem> getListaTipoFormato() {
		return this.listaTipoFormato;
	}

	public List<FileTransferForma> getListaRegistrosError() {
		return this.listaRegistrosError;
	}

	public void setListaRegistrosError(final List<FileTransferForma> listaRegistrosError) {
		this.listaRegistrosError = listaRegistrosError;
	}

	/**
	 * @return the detalleCarga
	 */
	public List<FileTransferForma> getDetalleCarga() {
		return this.detalleCarga;
	}

	// se inyectan las dependencias
	public void setFileUploadService(final FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public void setUtilDivintBeneficiariosService(final UtilDivintBeneficiariosService utilDivintBeneficiariosService) {
		this.utilDivintBeneficiariosService = utilDivintBeneficiariosService;
	}

	public Map<String, FileTransferCapturaBenefService<FileTransferForma>> getMapfileTransfersCapturaBenefServices() {
		return this.mapfileTransfersCapturaBenefServices;
	}

	public void setMapfileTransfersCapturaBenefServices(
			final Map<String, FileTransferCapturaBenefService<FileTransferForma>> mapfileTransfersCapturaBenefServices) {
		this.mapfileTransfersCapturaBenefServices = mapfileTransfersCapturaBenefServices;
	}

	public String getUrlDescargaFormato() {
		this.detalleCargaBeneficiario();
		return this.urlDescargaFormato;
	}

	public void setUrlDescargaFormato(final String urlDescargaFormato) {
		this.urlDescargaFormato = urlDescargaFormato;
	}

	public String getFormato() {
		return this.formato;
	}

}
