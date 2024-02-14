package com.indeval.portalinternacional.presentation.controller.cuentasTransitoriasEfectivo;

import com.indeval.portalinternacional.middleware.services.util.FileUploadMultiDivService;
import com.indeval.portalinternacional.unittest.BaseDaoTestCase;

public class FileTransferCuentasTransitoriasEfectivoControllerTest extends BaseDaoTestCase {

    FileTransferCuentasTransitoriasEfectivoController controller;
    private FileUploadMultiDivService fileUploadService = null;
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        System.out.println("Dentro de onSetup");
//        service = (CuentasTransitoriasEfectivoService) applicationContext.getBean("cuentasTransitoriasEfectivoService");
        controller = new FileTransferCuentasTransitoriasEfectivoController();
        System.out.println("Controler creado "+controller);

        if (fileUploadService == null) {
            fileUploadService = (FileUploadMultiDivService) applicationContext.getBean("fileUploadService");
        }
        System.out.println("FileUploadService "+fileUploadService);
    }

    public void testParaTest() {

        System.out.println("Iniciando test");


        controller.paraTest("C:\\traspaso\\archivo.xls",fileUploadService);
        //controller.uploadFile(null);
        controller.iniciaMapaConversion();

        try {
            controller.readFileXLS(null,null);
        } catch (Exception e) {
            System.out.println("Error "+e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("Fin test");

    }

}