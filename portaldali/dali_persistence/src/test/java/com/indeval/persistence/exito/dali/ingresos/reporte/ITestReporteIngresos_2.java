/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.ingresos.reporte;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Rafael Ibarra
 */
public class ITestReporteIngresos_2 extends TestCase {

    /**
     * Objeto de loggeo
     */
    private static final Logger log = LoggerFactory.getLogger(ITestReporteIngresos_2.class);

    public static String tab = "\t";

    public static String linea = "\n";

    public void testGetReporte() throws Exception {
        List<Bitacora> pivote = cargaArchivo("o:\\RepDic\\RepDic01-04.txt");
        List<List<Bitacora>> lista = new ArrayList<List<Bitacora>>();
        lista.add(cargaArchivo("o:\\RepDic\\RepDic07-11.txt"));
        lista.add(cargaArchivo("o:\\RepDic\\RepDic14-18.txt"));
        lista.add(cargaArchivo("o:\\RepDic\\RepDic21-24.txt"));
        lista.add(cargaArchivo("o:\\RepDic\\RepDic28-31.txt"));
        log.info("Total Registros: [" + pivote.size() + "]");

        for (List<Bitacora> semamas : lista) {
            log.info("Total Registros: [" + semamas.size() + "]");
            for (Bitacora bit : semamas) {
                int indice = pivote.indexOf(bit);
                log.info("Indice: [" + indice + "]");
                if (indice >= 0) {
                    Bitacora existente = pivote.get(indice);
                    assertNotNull("Bitacora inexistente", existente);
                    assertNotNull("Bitacora inexistente", bit);
                    existente.setTotalConsultas(existente.getTotalConsultas() + bit.getTotalConsultas());
                    existente.setTotalRegistros(existente.getTotalRegistros() + bit.getTotalRegistros());
                    existente.setGratis(existente.getGratis() + bit.getGratis());
                } else {
                    pivote.add(bit);
                }
            }
        }

        log.info("Total Registros: [" + pivote.size() + "]");

        FileOutputStream fos = new FileOutputStream("o:\\RepDic\\RepDicCompleto.txt");

        for (Bitacora bit : pivote) {
            fos.write(bit.toString().getBytes());
            fos.write(linea.getBytes());
        }
        fos.close();

    }

    private List<Bitacora> cargaArchivo(String archivo) throws Exception {
        List<Bitacora> retorno = new ArrayList<Bitacora>();
        FileInputStream fstream = new FileInputStream(archivo);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;

        strLine = br.readLine();
        System.out.println(strLine);

        Bitacora bit = null;

        while ((strLine = br.readLine()) != null) {
//			System.out.println(strLine);
            bit = new Bitacora();
            StringTokenizer st = new StringTokenizer(strLine);

            bit.setIdInstitucion(new Long(st.nextToken()));
            bit.setIdFolio(st.nextToken());
            bit.setNombreInstitucion(st.nextToken());
            bit.setIdConsulta(new Long(st.nextToken()));
            bit.setNombreConsulta(st.nextToken());
            bit.setTotalConsultas(new Long(st.nextToken()).intValue());
            bit.setTotalRegistros(new Long(st.nextToken()).intValue());
            bit.setGratis(new Long(st.nextToken()).intValue());
            retorno.add(bit);
        }

        br.close();
        in.close();
        fstream.close();
        return retorno;
    }

}
