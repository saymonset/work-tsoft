/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ArchivosAdjuntosEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AtributosEmbebidosEvcoDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraXMLEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesEventoCorporativo;



/**
 * 
 * @author
 *
 */
public class CompararObjetosEventosCorporativos {

	private final static Logger log = LoggerFactory.getLogger(CompararObjetosEventosCorporativos.class);
	private static final int PRIMER_REGISTRO_EVCO = 0;
	
	/**
	 * 
	 * @param bitacoraListParam
	 * @param evcoUnico
	 * @return
	 */
	public List<AtributosEmbebidosEvcoDto> getDiferenciasBitacoraEvco(List<BitacoraXMLEvco> bitacoraListParam, AtributosEmbebidosEvcoDto evcoUnico){
		log.info("Ingreso al metodo que implementa el algoritmo de obtencion de diferencias de bitacora");
		List<AtributosEmbebidosEvcoDto> cuerpoVarianteListEvco = new ArrayList<AtributosEmbebidosEvcoDto>();

		for (int i = 0; i < bitacoraListParam.size(); i++){
			BitacoraXMLEvco bitacoraBean = bitacoraListParam.get(i);
			AtributosEmbebidosEvcoDto bitacoraVariante = new AtributosEmbebidosEvcoDto();	
			if(PRIMER_REGISTRO_EVCO == i){
				bitacoraVariante= evcoUnico;
			}else{
				BitacoraXMLEvco bitacoraBeanAnt = bitacoraListParam.get(i - 1);
				bitacoraVariante.setFechaActualizacion(bitacoraBean.getFechaActualizacion());
				if(validarHashCodeEvco(bitacoraBeanAnt.getHashCodeCuerpo(), bitacoraBean.getHashCodeCuerpo()) == false){
					CuerpoEventoCorporativo cuerpoCorporativo =(CuerpoEventoCorporativo)getDiferenciasCuerpo(bitacoraBeanAnt,bitacoraBean);
					bitacoraVariante.setCuerpoEventoHtml(cuerpoCorporativo.getCuerpo());
					bitacoraVariante.setPiePaginaHtml(cuerpoCorporativo.getPiePagina());
				}

				if(validarHashCodeEvco(bitacoraBeanAnt.getHashCodeNotas(), bitacoraBean.getHashCodeNotas()) == false){
					List<String> listNotas=getDiferenciasNotas(bitacoraBeanAnt,bitacoraBean);
					bitacoraVariante.setNotasList(listNotas);					
				}

				if(validarHashCodeEvco(bitacoraBeanAnt.getHashCodeOpciones(), bitacoraBean.getHashCodeOpciones()) == false){ 
					List<OpcionesEventoCorporativo> listOpciones = getDiferenciasOpciones(bitacoraBeanAnt,bitacoraBean);					
					bitacoraVariante.setOpcionesList(listOpciones);
				}

				if(validarHashCodeEvco(bitacoraBeanAnt.getHashCodeArchivo(), bitacoraBean.getHashCodeArchivo()) == false){ 
					List<ArchivosAdjuntosEvcoDTO>  listArchivos = getDiferenciasAdjuntos(bitacoraBeanAnt,bitacoraBean);					
					bitacoraVariante.setArchivosAdjuntos(listArchivos);
				}
			}
			cuerpoVarianteListEvco.add(bitacoraVariante);
		}
		Collections.reverse(cuerpoVarianteListEvco);
		return cuerpoVarianteListEvco;
	}
	
	/**
	 * 
	 * @param reg1Hash
	 * @param reg2Hash
	 * @return
	 */
	public boolean validarHashCodeEvco(Long reg1Hash,Long reg2Hash){
		if(reg1Hash == null || reg2Hash == null){ //no hay comparacion y por lo tanto es lo mismo
			return true;
		}
		if(reg1Hash.equals(reg2Hash)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param registroAnt
	 * @param registroAct
	 * @return
	 */
	public CuerpoEventoCorporativo getDiferenciasCuerpo(BitacoraXMLEvco registroAnt, BitacoraXMLEvco registroAct){
		CuerpoEventoCorporativo cuerpoCorporativo = new CuerpoEventoCorporativo();

		if(!registroAnt.getCuerpoEvento().getCuerpo().equals(registroAct.getCuerpoEvento().getCuerpo())){
			cuerpoCorporativo.setCuerpo(registroAct.getCuerpoEvento().getCuerpo());
		}
		
		if(!registroAnt.getCuerpoEvento().getPiePagina().equals(registroAct.getCuerpoEvento().getPiePagina())){
			cuerpoCorporativo.setPiePagina(registroAct.getCuerpoEvento().getPiePagina());
		}		
		return cuerpoCorporativo;
	}
	
	/**
	 * 
	 * @param registroAnt
	 * @param registroAct
	 * @return
	 */
	public List<String> getDiferenciasNotas(BitacoraXMLEvco registroAnt, BitacoraXMLEvco registroAct){
		List<String> listNotas= new ArrayList<String>();
		if(registroAct.getNotasVec() !=  null){
			for (int ii = 0; ii < registroAct.getNotasVec().size(); ii++){
				NotasDTO notasAct = registroAct.getNotasVec().get(ii);
				boolean notaEncontrada = false;
				int idNotasActVar = Integer.parseInt(notasAct.getId()+"");
				bucleFullInterior:
					if(registroAnt !=null && registroAnt.getNotasVec()!=null){
						for (int i = 0; i < registroAnt.getNotasVec().size(); i++){
							NotasDTO notasAnt = registroAnt.getNotasVec().get(i);
							int idNotasAntVar = Integer.parseInt(notasAnt.getId()+"");
							if(idNotasActVar == idNotasAntVar){
								if(notasAnt.getDescripcion().equals(notasAct.getDescripcion()) == false){
									listNotas.add(notasAct.getDescripcion());
								}	
								notaEncontrada = true;
								break bucleFullInterior;
							}
						}
					}
				if(notaEncontrada == false){
					listNotas.add(notasAct.getDescripcion());
				}
			}
		}
		return listNotas;
	}
	
	/**
	 * 
	 * @param registroAnt
	 * @param registroAct
	 * @return
	 */
	public List<OpcionesEventoCorporativo> getDiferenciasOpciones(BitacoraXMLEvco registroAnt, BitacoraXMLEvco registroAct){
		List<OpcionesEventoCorporativo> listaOpciones = new ArrayList<OpcionesEventoCorporativo>();

		if(registroAct.getOpcionesVec() !=  null){
			bucleFullExterior:
				for (int ii = 0; ii < registroAct.getOpcionesVec().size(); ii++){
					OpcionesDTO opcionesAct = registroAct.getOpcionesVec().get(ii);
					//revisar si esto es valido
					if(opcionesAct == null){
						continue;
					}
					boolean opcionExistente = false;
					int idOpcionesActVar = Integer.parseInt(opcionesAct.getIdOpcion()+"");
					bucleFullInterior:
						if(registroAnt.getOpcionesVec() != null){
							for (int i = 0; i < registroAnt.getOpcionesVec().size(); i++){
								OpcionesDTO opcionesAnt = registroAnt.getOpcionesVec().get(i);
								int idOpcionesAntVar = Integer.parseInt(opcionesAnt.getIdOpcion()+"");
								if(idOpcionesActVar == idOpcionesAntVar){	
									if(opcionesAct.toString().hashCode() != opcionesAnt.toString().hashCode()){	
										OpcionesEventoCorporativo opcionesEvco= new OpcionesEventoCorporativo();
										opcionesEvco.setIdOpcion(opcionesAnt.getIdOpcion());
										opcionesEvco.setCuerpo(opcionesAnt.getCuerpo());
										opcionesEvco.setFechaCliente(BitacoraEventosCorporativosUtil.getDateOfString(opcionesAnt.getFechaCliente()));
										opcionesEvco.setFechaIndeval(BitacoraEventosCorporativosUtil.getDateOfString(opcionesAnt.getFechaIndeval()));
										opcionesEvco.setFechaPago(BitacoraEventosCorporativosUtil.getDateOfString(opcionesAnt.getFechaPago()));
	
										if(opcionesAnt.getCuerpo().equals(opcionesAct.getCuerpo()) == false){
											opcionesEvco.setCuerpo(opcionesAct.getCuerpo());
										}
										
										if(opcionesAnt.getFechaCliente().equals(opcionesAct.getFechaCliente()) == false){
											opcionesEvco.setFechaCliente(BitacoraEventosCorporativosUtil.getDateOfString(opcionesAct.getFechaCliente()));
										}
										
										if(opcionesAnt.getFechaIndeval().equals(opcionesAct.getFechaIndeval()) == false){
											opcionesEvco.setFechaIndeval(BitacoraEventosCorporativosUtil.getDateOfString(opcionesAct.getFechaIndeval()));
										}
										
										if(opcionesAnt.getFechaPago().equals(opcionesAct.getFechaPago()) == false){
											opcionesEvco.setFechaPago(BitacoraEventosCorporativosUtil.getDateOfString(opcionesAct.getFechaPago()));
										}
										listaOpciones.add(opcionesEvco);	
									}
									opcionExistente = true;
									break bucleFullInterior;
								}else{
									boolean opcionBorrada = false;
									bucleBorrado:
										if(registroAct !=null && registroAct.getOpcionesVec()!=null){
											for (int j = 0; j < registroAct.getOpcionesVec().size(); j++){
												OpcionesDTO opcionesBorradas = registroAct.getOpcionesVec().get(j);
												int idOpcioneBorrada = Integer.parseInt(opcionesBorradas.getIdOpcion()+"");
												if(idOpcionesAntVar == idOpcioneBorrada){
													opcionBorrada=true;
													break bucleBorrado;
												}
											}
										}
									if(opcionBorrada == false){
										listaOpciones.clear();
										if(registroAct !=null &&  registroAct.getOpcionesVec() != null){
											for (OpcionesDTO opcionesDTOEvcoAct : registroAct.getOpcionesVec()){		
												OpcionesEventoCorporativo opcionesEvco = new OpcionesEventoCorporativo();
												opcionesEvco.setIdOpcion(opcionesDTOEvcoAct.getIdOpcion());
												opcionesEvco.setCuerpo(opcionesDTOEvcoAct.getCuerpo());
												opcionesEvco.setFechaCliente(BitacoraEventosCorporativosUtil.getDateOfString(opcionesDTOEvcoAct.getFechaCliente()));
												opcionesEvco.setFechaIndeval(BitacoraEventosCorporativosUtil.getDateOfString(opcionesDTOEvcoAct.getFechaIndeval()));
												opcionesEvco.setFechaPago(BitacoraEventosCorporativosUtil.getDateOfString(opcionesDTOEvcoAct.getFechaPago()));
												listaOpciones.add(opcionesEvco);							
											}
										}
										break bucleFullExterior;
									}else{
										opcionExistente=false;								
									}
								}
							}
						}
					if(opcionExistente == false){
						OpcionesEventoCorporativo opcionesEvco= new OpcionesEventoCorporativo();
						opcionesEvco.setIdOpcion(opcionesAct.getIdOpcion());
						opcionesEvco.setCuerpo(opcionesAct.getCuerpo());
						opcionesEvco.setFechaCliente(BitacoraEventosCorporativosUtil.getDateOfString(opcionesAct.getFechaCliente()));
						opcionesEvco.setFechaIndeval(BitacoraEventosCorporativosUtil.getDateOfString(opcionesAct.getFechaIndeval()));
						opcionesEvco.setFechaPago(BitacoraEventosCorporativosUtil.getDateOfString(opcionesAct.getFechaPago()));
						listaOpciones.add(opcionesEvco);
					}
				}
		}
		return listaOpciones;
	}
	
	/**
	 * 
	 * @param registroAnt
	 * @param registroAct
	 * @return
	 */
	public List<ArchivosAdjuntosEvcoDTO>  getDiferenciasAdjuntos(BitacoraXMLEvco registroAnt, BitacoraXMLEvco registroAct){
		List<ArchivosAdjuntosEvcoDTO> listaArchivos = new ArrayList<ArchivosAdjuntosEvcoDTO>();

		if(registroAct.getArchivosVec() !=  null){
			for (int ii = 0; ii < registroAct.getArchivosVec().size(); ii++){
				ArchivosAdjuntosEvcoDTO archivoAct = registroAct.getArchivosVec().get(ii);
				boolean archivoEncontrada = false;
				int idArchivoActVar = Integer.parseInt(archivoAct.getIdAdjuntos()+"");
				bucleFullInterior:
					if(registroAnt !=null && registroAnt.getArchivosVec() !=null){
						for (int i = 0; i < registroAnt.getArchivosVec().size(); i++){
							ArchivosAdjuntosEvcoDTO archivoAnt = registroAnt.getArchivosVec().get(i);
							int idArchivoAntVar=Integer.parseInt(archivoAnt.getIdAdjuntos()+"");
							if(idArchivoActVar == idArchivoAntVar){
								if(archivoAnt.getDescripcion().equals(archivoAct.getDescripcion()) == false){
									listaArchivos.add(archivoAct);
								}	
								archivoEncontrada = true;
								break bucleFullInterior;
							}
						}
					}
				if(archivoEncontrada == false){
					listaArchivos.add(archivoAct);
				}
			}
		}
		return listaArchivos;
	}
}
