/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.bursatec.seguridad.middleware.service;

import java.rmi.RemoteException;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.vo.InstitucionVO;
import com.bursatec.seguridad.vo.RespuestaVO;
import com.bursatec.seguridad.vo.SistemaVO;
import com.bursatec.seguridad.vo.TicketInfoVO;


/**
 * Interfaz para el acceso al servicio Web de Seguridad de Bursatec.
 * @author Emigdio Hernández
 *
 */
public interface WebServiceSeguridadService {
	
	void activarTokenUsuario(String s, String s1)
    throws  RemoteException;

 String login(String s, String s1, String s2, String s3)
    throws  RemoteException;

 Object[] getFacultadesSimples(String s)
    throws  RemoteException;

 RespuestaVO autorizaCuenta(String s, String s1, String s2, long l)
    throws  RemoteException;

 UsuarioDTO getUsuario(String s)
    throws  RemoteException;

 TicketInfoVO getTicketInfo(String s)
    throws  RemoteException;

 Object[] getInstitucionesDisponibles(String s, String s1, String s2, String s3, String s4)
    throws  RemoteException;

 RespuestaVO autorizaCuenta0(String s, String s1, String s2, String s3, String s4)
    throws  RemoteException;

 Object[] findAllSistemas()
    throws RemoteException;

 String generaFirmaDigitalPorSistema(String s, String s1)
    throws  RemoteException;

 void cambiarPassword(String s, String s1, String s2, String s3)
    throws  RemoteException;

 Object[] findSistemasAuthWeb()
    throws RemoteException;

 Object[] getCuentasListaNegra(String s)
    throws  RemoteException;

 InstitucionVO getInstitucionPrimaria(String s)
    throws  RemoteException;

 SistemaVO getSistemaWithAuthWebByNombre(String s)
    throws  RemoteException;

 String iniciaAutentificacion(String s, String s1, String s2)
    throws  RemoteException;

 String getEmail(String s)
    throws  RemoteException;

 Object[] getInstitucionesAsignadas(String s, String s1)
    throws  RemoteException;

 void logout(String s)
    throws RemoteException;

 String generaFirmaDigital(String s)
    throws  RemoteException;

 boolean verificaFirmaDigital(String s, String s1, String s2, String s3)
    throws  RemoteException;

 Object[] getFacultadesPorPerfil(String s)
    throws  RemoteException;

 RespuestaVO autorizaRol(String s, String s1, String s2)
    throws  RemoteException;

 String autentificarFase2(String s, String s1)
    throws  RemoteException;

 String generaFirmaDigitalPorSistemaAlgoritmo(String s, String s1, String s2)
    throws  RemoteException;

 void cambiaInfoUsuario(String s, String s1, String s2)
    throws  RemoteException;

 String autentificacion(String s)
    throws  RemoteException;

 Object[] getFacultadesListaNegra(String s)
    throws  RemoteException;

 String autentificarFase1(String s, String s1, String s2)
    throws  RemoteException;

 boolean isTicketValido(String s)
    throws  RemoteException;

 void validaEstadoTicket(String s)
    throws  RemoteException;

 InstitucionVO getInstitucionPorPerfil(String s)
    throws  RemoteException;

 boolean verificaUsuario(String s, String s1, String s2)
    throws  RemoteException;

 String loginToken(String s, String s1, String s2, String s3, String s4)
    throws  RemoteException;

 Object[] getFacultadesCuentaPorPerfil(String s)
    throws  RemoteException;

 Object[] getSistemasPorUsuario(String s)
    throws RemoteException;

 Object[] getNombresRolesPorPerfil(String s)
    throws  RemoteException;

 RespuestaVO autorizaFacultad(String s, String s1, String s2)
    throws  RemoteException;

 SistemaVO getSistemaPorPerfil(String s)
    throws  RemoteException;

 RespuestaVO autorizaRoles(String s, String s1, Object aobj[])
    throws  RemoteException;

 RespuestaVO autorizaFacultadCuenta(String s, String s1, String s2, String s3, long l)
    throws  RemoteException;

	
	
}
