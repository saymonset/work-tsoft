/**
 * 2H Software
 * Bursatec - Seguridad
 */
package com.bursatec.seguridad.middleware.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.vo.InstitucionVO;
import com.bursatec.seguridad.vo.RespuestaVO;
import com.bursatec.seguridad.vo.SistemaVO;
import com.bursatec.seguridad.vo.TicketInfoVO;
/**
 * Interfaz para el acceso al servicio web de seguridad.
 * @author Emigdio Hernández
 *
 */
 interface WebServiceSeguridadRemote extends Remote {

	  void activarTokenUsuario(String s, String s1)
     throws SeguridadException, RemoteException;

  String login(String s, String s1, String s2, String s3)
     throws SeguridadException, RemoteException;

  Object[] getFacultadesSimples(String s)
     throws SeguridadException, RemoteException;

  RespuestaVO autorizaCuenta(String s, String s1, String s2, long l)
     throws SeguridadException, RemoteException;

  UsuarioDTO getUsuario(String s)
     throws SeguridadException, RemoteException;

  TicketInfoVO getTicketInfo(String s)
     throws SeguridadException, RemoteException;

  Object[] getInstitucionesDisponibles(String s, String s1, String s2, String s3, String s4)
     throws SeguridadException, RemoteException;

  RespuestaVO autorizaCuenta0(String s, String s1, String s2, String s3, String s4)
     throws SeguridadException, RemoteException;

  Object[] findAllSistemas()
     throws RemoteException;

  String generaFirmaDigitalPorSistema(String s, String s1)
     throws SeguridadException, RemoteException;

  void cambiarPassword(String s, String s1, String s2, String s3)
     throws SeguridadException, RemoteException;

  Object[] findSistemasAuthWeb()
     throws RemoteException;

  Object[] getCuentasListaNegra(String s)
     throws SeguridadException, RemoteException;

  InstitucionVO getInstitucionPrimaria(String s)
     throws SeguridadException, RemoteException;

  SistemaVO getSistemaWithAuthWebByNombre(String s)
     throws SeguridadException, RemoteException;

  String iniciaAutentificacion(String s, String s1, String s2)
     throws SeguridadException, RemoteException;

  String getEmail(String s)
     throws SeguridadException, RemoteException;

  Object[] getInstitucionesAsignadas(String s, String s1)
     throws SeguridadException, RemoteException;

  void logout(String s)
     throws RemoteException;

  String generaFirmaDigital(String s)
     throws SeguridadException, RemoteException;

  boolean verificaFirmaDigital(String s, String s1, String s2, String s3)
     throws SeguridadException, RemoteException;

  Object[] getFacultadesPorPerfil(String s)
     throws SeguridadException, RemoteException;

  RespuestaVO autorizaRol(String s, String s1, String s2)
     throws SeguridadException, RemoteException;

  String autentificarFase2(String s, String s1)
     throws SeguridadException, RemoteException;

  String generaFirmaDigitalPorSistemaAlgoritmo(String s, String s1, String s2)
     throws SeguridadException, RemoteException;

  void cambiaInfoUsuario(String s, String s1, String s2)
     throws SeguridadException, RemoteException;

  String autentificacion(String s)
     throws SeguridadException, RemoteException;

  Object[] getFacultadesListaNegra(String s)
     throws SeguridadException, RemoteException;

  String autentificarFase1(String s, String s1, String s2)
     throws SeguridadException, RemoteException;

  boolean isTicketValido(String s)
     throws SeguridadException, RemoteException;

  void validaEstadoTicket(String s)
     throws SeguridadException, RemoteException;

  InstitucionVO getInstitucionPorPerfil(String s)
     throws SeguridadException, RemoteException;

  boolean verificaUsuario(String s, String s1, String s2)
     throws SeguridadException, RemoteException;

  String loginToken(String s, String s1, String s2, String s3, String s4)
     throws SeguridadException, RemoteException;

  Object[] getFacultadesCuentaPorPerfil(String s)
     throws SeguridadException, RemoteException;

  Object[] getSistemasPorUsuario(String s)
     throws RemoteException;

  Object[] getNombresRolesPorPerfil(String s)
     throws SeguridadException, RemoteException;

  RespuestaVO autorizaFacultad(String s, String s1, String s2)
     throws SeguridadException, RemoteException;

  SistemaVO getSistemaPorPerfil(String s)
     throws SeguridadException, RemoteException;

  RespuestaVO autorizaRoles(String s, String s1, Object aobj[])
     throws SeguridadException, RemoteException;

  RespuestaVO autorizaFacultadCuenta(String s, String s1, String s2, String s3, long l)
     throws SeguridadException, RemoteException;

	

     
	
}
