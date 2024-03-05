package com.indeval.portaldali.middleware.services.mercadodinero.test;

import java.math.BigInteger;
import java.util.ArrayList;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.filetransfer.FileTransferService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;

/**
 * Clase de prueba unitaria para verificar la integracicn del servicio de
 * file transfer
 * 
 * @author Emigdio Hernández
 * 
 */
public class FileTransferServiceImplTest extends BaseTestCase {

	public void testValidarBusinessRules() {
		/** Representa el detalle del usuario logueado */

		FileTransferService service = (FileTransferService) applicationContext
				.getBean("fileTransferService");

		AgenteVO agente = new AgenteVO();
		agente.setId("01");
		agente.setFolio("028");

		ArrayList<String> archivo = new ArrayList<String>();

		archivo
				.add("23-Oct-20080102833010102833011   ICA    *     0001T 720000000000000000561000000000000000000000");
		archivo
				.add("23-Oct-20080102833010101519171   TVAZTCACPO   0000V 240000000000000000170000000001140310000000");

		service.almacenaInformacion(agente, "TC", archivo, 0, "01028");
		service.validaInformacion(agente, "TC", BigInteger.ONE, 0);

	}

}
