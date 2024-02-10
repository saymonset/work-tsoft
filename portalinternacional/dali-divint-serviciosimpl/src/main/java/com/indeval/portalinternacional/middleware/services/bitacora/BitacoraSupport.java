// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.bitacora;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.sidv.bitacoraauditoria.exception.BitacoraException;
import com.indeval.sidv.bitacoraauditoria.middleware.service.BitacoraService;
import com.indeval.sidv.bitacoraauditoria.persistence.model.vo.ObjetoTransaccionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BitacoraSupport {

	/**Logger*/
	private static final Logger LOG = LoggerFactory.getLogger(BitacoraSupport.class);
	
    /**
     * Metodo que registra en bitacora de auditoria operaciones realizadas en el portal.  
     * @param usuarioP Usuario que realiza la operacion a bitacorar. 
     * @param oldObj Objeto antes de la operacion realizada. 
     * @param newObj Objeto despues de la operacion realizada. 
     * @param service Servicio de bitacoreo. 
     * @param idModuloTransaccion El id del modulo al que pertenece la operacion realizada. 
     * @param idOperacionTransaccion El id de la transaccion realizada.
     */
    public static final boolean doRegistrarBitacora(String usuarioP, Object oldObj, Object newObj, BitacoraService service, Integer idModuloTransaccion, Integer idOperacionTransaccion){
        String usuario = usuarioP;
        ObjetoTransaccionVo vo = new ObjetoTransaccionVo(oldObj, newObj);

        try {
            if (usuario == null || usuario.trim().length() == 0) {
                usuario = Constantes.USUARIO_DECONOCIDO;
            }
            service.doInsertaBitacora(idModuloTransaccion, idOperacionTransaccion, usuario, vo);
            return true;
        } catch (BitacoraException e) {
            LOG.debug("Ha ocurrido un error al insertar el registro de bitacora realizada por el usuario " + usuario);
            LOG.error(e.getMessage(), e);
            return false;
        } catch (Throwable t) {
            LOG.debug("Ha ocurrido un error al insertar el registro de bitacora realizada por el usuario " + usuario);
            LOG.error(t.getMessage(), t);
            return false;
        }
    }

}
