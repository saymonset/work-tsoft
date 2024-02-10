/**
 * Bursatec - Portal Internacional Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.formatosw.FormaW8BENE;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;

/**
 * Utileria para el formato W8BEN-E
 * 
 * @author Pablo Balderas
 */
public class W8BENEUtil {

    public static Map<String, String> w8BeneParteI4Campos = new HashMap<String, String>();
    public static Map<String, String> w8BeneParteI4Valores = new HashMap<String, String>();
    public static Map<String, String> w8BeneParteI5Campos = new HashMap<String, String>();
    public static Map<String, String> w8BeneParteI5Valores = new HashMap<String, String>();

    static {

        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4a", "1");
        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4b", "2");
        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4c", "3");
        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4d", "4");
        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4e", "5");
        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4f", "6");
        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4g", "7");
        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4h", "8");
        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4i", "10");
        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4j", "11");
        W8BENEUtil.w8BeneParteI4Campos.put("partIcmp4k", "12");

        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4a", "topmostSubform[0].Page1[0].c1_001[0]");
        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4b", "topmostSubform[0].Page1[0].c1_001[1]");
        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4c", "topmostSubform[0].Page1[0].c1_001[2]");
        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4d", "topmostSubform[0].Page1[0].c1_001[3]");
        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4e", "topmostSubform[0].Page1[0].c1_001[4]");
        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4f", "topmostSubform[0].Page1[0].c1_001[5]");
        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4g", "topmostSubform[0].Page1[0].c1_001[6]");
        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4h", "topmostSubform[0].Page1[0].c1_001[7]");
        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4i", "topmostSubform[0].Page1[0].c1_001[8]");
        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4j", "topmostSubform[0].Page1[0].c1_001[9]");
        W8BENEUtil.w8BeneParteI4Valores.put("partIcmp4k", "topmostSubform[0].Page1[0].c1_001[10]");

        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5a", "13");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5b", "14");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5c", "15");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5d", "16");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5e", "17");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5f", "18");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5g", "19");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5h", "20");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5i", "21");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5j", "22");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5k", "23");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5l", "24");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5m", "25");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5n", "26");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5o", "27");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5p", "28");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5q", "29");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5r", "30");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5s", "31");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5t", "32");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5u", "33");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5v", "34");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5w", "35");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5x", "36");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5y", "37");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5z", "38");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5aa", "39");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5ab", "40");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5ac", "41");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5ad", "42");
        W8BENEUtil.w8BeneParteI5Campos.put("partIcmp5ae", "43");

        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5a",
                "topmostSubform[0].Page1[0].column1[0].c1_003[0]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5b",
                "topmostSubform[0].Page1[0].column1[0].c1_003[1]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5c",
                "topmostSubform[0].Page1[0].column1[0].c1_003[2]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5d",
                "topmostSubform[0].Page1[0].column1[0].c1_003[3]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5e",
                "topmostSubform[0].Page1[0].column1[0].c1_003[4]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5f",
                "topmostSubform[0].Page1[0].column1[0].c1_003[5]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5g",
                "topmostSubform[0].Page1[0].column1[0].c1_003[6]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5h",
                "topmostSubform[0].Page1[0].column1[0].c1_003[7]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5i",
                "topmostSubform[0].Page1[0].column1[0].c1_003[8]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5j",
                "topmostSubform[0].Page1[0].column1[0].c1_003[9]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5k",
                "topmostSubform[0].Page1[0].column1[0].c1_003[10]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5l",
                "topmostSubform[0].Page1[0].column1[0].c1_003[11]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5m",
                "topmostSubform[0].Page1[0].column1[0].c1_003[12]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5n",
                "topmostSubform[0].Page1[0].column2[0].c1_003[0]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5o",
                "topmostSubform[0].Page1[0].column2[0].c1_003[1]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5p",
                "topmostSubform[0].Page1[0].column2[0].c1_003[2]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5q",
                "topmostSubform[0].Page1[0].column2[0].c1_003[3]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5r",
                "topmostSubform[0].Page1[0].column2[0].c1_003[4]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5s",
                "topmostSubform[0].Page1[0].column2[0].c1_003[5]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5t",
                "topmostSubform[0].Page1[0].column2[0].c1_003[6]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5u",
                "topmostSubform[0].Page1[0].column2[0].c1_003[7]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5v",
                "topmostSubform[0].Page1[0].column2[0].c1_003[8]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5w",
                "topmostSubform[0].Page1[0].column2[0].c1_003[9]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5x",
                "topmostSubform[0].Page1[0].column2[0].c1_003[10]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5y",
                "topmostSubform[0].Page1[0].column2[0].c1_003[11]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5z",
                "topmostSubform[0].Page1[0].column2[0].c1_003[12]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5aa",
                "topmostSubform[0].Page1[0].column2[0].c1_003[13]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5ab",
                "topmostSubform[0].Page1[0].column2[0].c1_003[14]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5ac",
                "topmostSubform[0].Page1[0].column2[0].c1_003[15]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5ad",
                "topmostSubform[0].Page1[0].column2[0].c1_003[16]");
        W8BENEUtil.w8BeneParteI5Valores.put("partIcmp5ae",
                "topmostSubform[0].Page1[0].column2[0].c1_003[17]");

    }

    /**
     * Indica que apndice es el que debe de mostrar
     * 
     * @param apendice Apendice
     * @param formaW8BENE Forma
     */
    public static String obtenerApendice(final FormaW8BENE formaW8BENE) {
        String apendice = new String();
        if (formaW8BENE.isPartIV()) {
            apendice = ".partIV";
        } else if (formaW8BENE.isPartV()) {
            apendice = ".partV";
        } else if (formaW8BENE.isPartVI()) {
            apendice = ".partVI";
        } else if (formaW8BENE.isPartVII()) {
            apendice = ".partVII";
        } else if (formaW8BENE.isPartVIII()) {
            apendice = ".partVIII";
        } else if (formaW8BENE.isPartIX()) {
            apendice = ".partIX";
        } else if (formaW8BENE.isPartX()) {
            apendice = ".partX";
        } else if (formaW8BENE.isPartXI()) {
            apendice = ".partXI";
        } else if (formaW8BENE.isPartXII()) {
            apendice = ".partXII";
        } else if (formaW8BENE.isPartXIII()) {
            apendice = ".partXIII";
        } else if (formaW8BENE.isPartXIV()) {
            apendice = ".partXIV";
        } else if (formaW8BENE.isPartXV()) {
            apendice = ".partXV";
        } else if (formaW8BENE.isPartXVI()) {
            apendice = ".partXVI";
        } else if (formaW8BENE.isPartXVII()) {
            apendice = ".partXVII";
        } else if (formaW8BENE.isPartXVIII()) {
            apendice = ".partXVIII";
        } else if (formaW8BENE.isPartXIX()) {
            apendice = ".partXIX";
        } else if (formaW8BENE.isPartXX()) {
            apendice = ".partXX";
        } else if (formaW8BENE.isPartXXI()) {
            apendice = ".partXXI";
        } else if (formaW8BENE.isPartXXII()) {
            apendice = ".partXXII";
        } else if (formaW8BENE.isPartXXIII()) {
            apendice = ".partXXIII";
        } else if (formaW8BENE.isPartXXIV()) {
            apendice = ".partXXIV";
        } else if (formaW8BENE.isPartXXV()) {
            apendice = ".partXXV";
        } else if (formaW8BENE.isPartXXVI()) {
            apendice = ".partXXVI";
        } else if (formaW8BENE.isPartXXVII()) {
            apendice = ".partXXVII";
        } else if (formaW8BENE.isPartXXVIII()) {
            apendice = ".partXXVIII";
        } else if (formaW8BENE.isPartXXIX()) {
            apendice = ".partXXIX";
        }
        return apendice;
    }

    /**
     * Método que pasa a mayusculas todos los campos String de la forma.
     */
    public static void formaAMayusculas(final FormaW8BENE forma) {
        forma.setPartIcmp1(StringUtils.upperCase(forma.getPartIcmp1()));
        forma.setPartIcmp2(StringUtils.upperCase(forma.getPartIcmp2()));
        forma.setPartIcmp3(StringUtils.upperCase(forma.getPartIcmp3()));
        forma.setPartIcmp6a(StringUtils.upperCase(forma.getPartIcmp6a()));
        forma.setPartIcmp6b(StringUtils.upperCase(forma.getPartIcmp6b()));
        forma.setPartIcmp6c(StringUtils.upperCase(forma.getPartIcmp6c()));
        forma.setPartIcmp7a(StringUtils.upperCase(forma.getPartIcmp7a()));
        forma.setPartIcmp7b(StringUtils.upperCase(forma.getPartIcmp7b()));
        forma.setPartIcmp7c(StringUtils.upperCase(forma.getPartIcmp7c()));
        forma.setPartIcmp8(StringUtils.upperCase(forma.getPartIcmp8()));
        forma.setPartIcmp9a2(StringUtils.upperCase(forma.getPartIcmp9a2()));
        forma.setPartIcmp9b2(StringUtils.upperCase(forma.getPartIcmp9b2()));
        forma.setPartIcmp10(StringUtils.upperCase(forma.getPartIcmp10()));
        forma.setPartIIcmp12a(StringUtils.upperCase(forma.getPartIIcmp12a()));
        forma.setPartIIcmp12b(StringUtils.upperCase(forma.getPartIIcmp12b()));
        forma.setPartIIcmp12c(StringUtils.upperCase(forma.getPartIIcmp12c()));
        forma.setPartIIcmp13(StringUtils.upperCase(forma.getPartIIcmp13()));
        forma.setPartIIIcmp14a1(StringUtils.upperCase(forma.getPartIIIcmp14a1()));
        forma.setPartIIIcmp15a(StringUtils.upperCase(forma.getPartIIIcmp15a()));
        forma.setPartIIIcmp15b(StringUtils.upperCase(forma.getPartIIIcmp15b()));
        forma.setPartIIIcmp15c(StringUtils.upperCase(forma.getPartIIIcmp15c()));
        forma.setPartIVcmp16(StringUtils.upperCase(forma.getPartIVcmp16()));
        forma.setPartVIIcmp20(StringUtils.upperCase(forma.getPartVIIcmp20()));
        forma.setPartXIIcmp26b(StringUtils.upperCase(forma.getPartXIIcmp26b()));
        forma.setPartXIIcmp26c(StringUtils.upperCase(forma.getPartXIIcmp26c()));
        forma.setPartXIIcmp26d(StringUtils.upperCase(forma.getPartXIIcmp26d()));
        forma.setPartXIXcmp33b(StringUtils.upperCase(forma.getPartXIXcmp33b()));
        forma.setPartXXcmp34b(StringUtils.upperCase(forma.getPartXXcmp34b()));
        forma.setPartXXIcmp35b(StringUtils.upperCase(forma.getPartXXIcmp35b()));
        forma.setPartXXIIIcmp37b(StringUtils.upperCase(forma.getPartXXIIIcmp37b()));
        forma.setPartXXIIIcmp37d(StringUtils.upperCase(forma.getPartXXIIIcmp37d()));
        forma.setPartXXIIIcmp37e(StringUtils.upperCase(forma.getPartXXIIIcmp37e()));
        forma.setPartXXVIIIcmp42(StringUtils.upperCase(forma.getPartXXVIIIcmp42()));
        forma.setPaisResidencial(StringUtils.upperCase(forma.getPaisResidencial()));
        forma.setPrintName(StringUtils.upperCase(forma.getPrintName()));

    }

    /**
     * Obtiene el tipo de beneficiario.
     * 
     * @param formaW8BENE
     */
    public static void obtenerTipoBeneficiario(final FormaW8BENE formaW8BENE) {
        String tipoBeneficiario = null;
        if (formaW8BENE.isPartIcmp4a()) {
            tipoBeneficiario = "Corporation";
        } else if (formaW8BENE.isPartIcmp4b()) {
            tipoBeneficiario = "Disregarded entity";
        } else if (formaW8BENE.isPartIcmp4c()) {
            tipoBeneficiario = "Partnership";
        } else if (formaW8BENE.isPartIcmp4d()) {
            tipoBeneficiario = "Simple trust";
        } else if (formaW8BENE.isPartIcmp4e()) {
            tipoBeneficiario = "Grantor trust";
        } else if (formaW8BENE.isPartIcmp4f()) {
            tipoBeneficiario = "Complex trust";
        } else if (formaW8BENE.isPartIcmp4g()) {
            tipoBeneficiario = "Estate";
        } else if (formaW8BENE.isPartIcmp4h()) {
            tipoBeneficiario = "Government";
        } else if (formaW8BENE.isPartIcmp4i()) {
            tipoBeneficiario = "Central Bank of Issue";
        } else if (formaW8BENE.isPartIcmp4j()) {
            tipoBeneficiario = "Tax-exempt organization";
        } else if (formaW8BENE.isPartIcmp4k()) {
            tipoBeneficiario = "Private foundation";
        } else if (formaW8BENE.isPartIcmp4n()) {
            tipoBeneficiario = "Private foundation";
        }
        formaW8BENE.setDescTipoBeneficiario(tipoBeneficiario);
    }


    /**
     * Copia el campo 2 al campo 14a1
     * 
     * @param formaW8BENE Forma
     */
    public static void copiarPais(final FormaW8BENE formaW8BENE) {
        formaW8BENE.setPartIIIcmp14a1(formaW8BENE.getPartIcmp2());
    }

    /**
     * Completa el campo 15
     * 
     * @param formaW8BENE Forma
     */
    public static void completarCampo15(final FormaW8BENE formaW8BENE) {
        if (StringUtils.isNotBlank(formaW8BENE.getPartIIIcmp15a())) {
            if (BeneficiariosConstantes.VALOR_DIVIDENDS.equals(formaW8BENE.getPartIIIcmp15a()
                    .trim())) {
                formaW8BENE.setPartIIIcmp15c(BeneficiariosConstantes.DIVIDENDS);
                formaW8BENE.setReadOnlyCmp15c(true);
            } else if (BeneficiariosConstantes.VALOR_INTEREST.equals(formaW8BENE.getPartIIIcmp15a()
                    .trim())) {
                formaW8BENE.setPartIIIcmp15c(BeneficiariosConstantes.INTEREST);
                formaW8BENE.setReadOnlyCmp15c(true);
            } else {
                formaW8BENE.setPartIIIcmp15c(null);
                formaW8BENE.setReadOnlyCmp15c(false);
            }
        }
    }

    /**
     * Inhabilita el campo 7 y limpia los campos
     * 
     * @param formaW8BENE Forma
     */
    public static void deshabilitarDomicilioCorreo(final boolean deshabilitaDomicilioCorreo,
            final FormaW8BENE formaW8BENE) {
        if (deshabilitaDomicilioCorreo) {
            formaW8BENE.setPartIcmp7a(null);
            formaW8BENE.setPartIcmp7b(null);
            formaW8BENE.setPartIcmp7c(null);
        }
        formaW8BENE.setInhabilitarPartIcmp7(deshabilitaDomicilioCorreo);
    }

    /**
     * Habilita o deshabilita las partes de la forma.
     * 
     * @param formaW8BENE Formato
     */
    public static void habilitarDeshabilitarPartesFormato(final FormaW8BENE formaW8BENE) {
        formaW8BENE.setPartIV(formaW8BENE.isPartIcmp5f());
        formaW8BENE.setPartV(formaW8BENE.isPartIcmp5g());
        formaW8BENE.setPartVI(formaW8BENE.isPartIcmp5h());
        formaW8BENE.setPartVII(formaW8BENE.isPartIcmp5i());
        formaW8BENE.setPartVIII(formaW8BENE.isPartIcmp5j());
        formaW8BENE.setPartIX(formaW8BENE.isPartIcmp5k());
        formaW8BENE.setPartX(formaW8BENE.isPartIcmp5l());
        formaW8BENE.setPartXI(formaW8BENE.isPartIcmp5m());
        formaW8BENE.setPartXII(formaW8BENE.isPartIcmp5n() || formaW8BENE.isPartIcmp5af());
        formaW8BENE.setPartXIII(formaW8BENE.isPartIcmp5o());
        formaW8BENE.setPartXIV(formaW8BENE.isPartIcmp5p());
        formaW8BENE.setPartXV(formaW8BENE.isPartIcmp5q());
        formaW8BENE.setPartXVI(formaW8BENE.isPartIcmp5r());
        formaW8BENE.setPartXVII(formaW8BENE.isPartIcmp5s());
        formaW8BENE.setPartXVIII(formaW8BENE.isPartIcmp5t());
        formaW8BENE.setPartXIX(formaW8BENE.isPartIcmp5u());
        formaW8BENE.setPartXX(formaW8BENE.isPartIcmp5v());
        formaW8BENE.setPartXXI(formaW8BENE.isPartIcmp5w());
        formaW8BENE.setPartXXII(formaW8BENE.isPartIcmp5x());
        formaW8BENE.setPartXXIII(formaW8BENE.isPartIcmp5y());
        formaW8BENE.setPartXXIV(formaW8BENE.isPartIcmp5z());
        formaW8BENE.setPartXXV(formaW8BENE.isPartIcmp5aa());
        formaW8BENE.setPartXXVI(formaW8BENE.isPartIcmp5ab());
        formaW8BENE.setPartXXVII(formaW8BENE.isPartIcmp5ac());
        formaW8BENE.setPartXXVIII(formaW8BENE.isPartIcmp5ae());
        formaW8BENE.setPartXXIX(formaW8BENE.isPartXXVIcmp40c() && formaW8BENE.isPartXXVI());

        if (!(formaW8BENE.isPartXXVIcmp40c() && formaW8BENE.isPartXXVI())) {
            formaW8BENE.setPartXXIXadra(null);
            formaW8BENE.setPartXXIXadrb(null);
            formaW8BENE.setPartXXIXadrc(null);
            formaW8BENE.setPartXXIXadrd(null);
            formaW8BENE.setPartXXIXadre(null);
            formaW8BENE.setPartXXIXadrf(null);
            formaW8BENE.setPartXXIXadrg(null);
            formaW8BENE.setPartXXIXadrh(null);
            formaW8BENE.setPartXXIXadri(null);

            formaW8BENE.setPartXXIXnoma(null);
            formaW8BENE.setPartXXIXnomb(null);
            formaW8BENE.setPartXXIXnomc(null);
            formaW8BENE.setPartXXIXnomd(null);
            formaW8BENE.setPartXXIXnome(null);
            formaW8BENE.setPartXXIXnomf(null);
            formaW8BENE.setPartXXIXnomg(null);
            formaW8BENE.setPartXXIXnomh(null);
            formaW8BENE.setPartXXIXnomi(null);

            formaW8BENE.setPartXXIXtina(null);
            formaW8BENE.setPartXXIXtinb(null);
            formaW8BENE.setPartXXIXtinc(null);
            formaW8BENE.setPartXXIXtind(null);
            formaW8BENE.setPartXXIXtine(null);
            formaW8BENE.setPartXXIXtinf(null);
            formaW8BENE.setPartXXIXting(null);
            formaW8BENE.setPartXXIXtinh(null);
            formaW8BENE.setPartXXIXtini(null);
        }
        formaW8BENE.setPartXXX(true); // check certify
    }

    /**
     * Valida los domicilios del beneficiario.
     * 
     * @param beneficiario Beneficiario
     */
    public static void validarDomiciliosBeneficiario(final Beneficiario beneficiario) {
        if (beneficiario.getDomicilioW8Normal() != null) {
            beneficiario.getDomicilioW8Normal().domicilioAMayusculas();
            beneficiario.getDomicilioW8Normal().setOuterNumber(new String());
            beneficiario.getDomicilioW8Normal().setInteriorNumber(new String());
            beneficiario.getDomicilioW8Normal().setPostalCode(new String());
            beneficiario.getDomicilioW8Normal().setStateProvince(new String());
        }
        if (beneficiario.getDomicilioW8Correo() != null) {
            beneficiario.getDomicilioW8Correo().domicilioAMayusculas();
            beneficiario.getDomicilioW8Correo().setOuterNumber(new String());
            beneficiario.getDomicilioW8Correo().setInteriorNumber(new String());
            beneficiario.getDomicilioW8Correo().setPostalCode(new String());
            beneficiario.getDomicilioW8Correo().setStateProvince(new String());
        }
    }


    /**
     * Verifica las partes del formato para colocar a null o false los campos de las partes no
     * seleccionadas.
     * 
     * @param formaW8BENE Forma sobre la cual se realiza la verificación.
     */
    public static void verificarPartesFormato(final FormaW8BENE formaW8BENE) {
        if (!formaW8BENE.isPartIV()) {
            formaW8BENE.setPartIVcmp16(null);
            formaW8BENE.setPartIVcmp17a(false);
            formaW8BENE.setPartIVcmp17b(false);
        }
        if (!formaW8BENE.isPartV()) {
            formaW8BENE.setPartVcmp18(false);
        }
        if (!formaW8BENE.isPartVI()) {
            formaW8BENE.setPartVIcmp19(false);
        }
        if (!formaW8BENE.isPartVII()) {
            formaW8BENE.setPartVIIcmp20(null);
            formaW8BENE.setPartVIIcmp21(false);
        }
        if (!formaW8BENE.isPartVIII()) {
            formaW8BENE.setPartVIIIcmp22(false);
        }
        if (!formaW8BENE.isPartIX()) {
            formaW8BENE.setPartIXcmp23(false);
        }
        if (!formaW8BENE.isPartX()) {
            formaW8BENE.setPartXcmp24a(false);
            formaW8BENE.setPartXcmp24b(false);
            formaW8BENE.setPartXcmp24c(false);
            formaW8BENE.setPartXcmp24d(false);
        }
        if (!formaW8BENE.isPartXI()) {
            formaW8BENE.setPartXIcmp25a(false);
            formaW8BENE.setPartXIcmp25b(false);
            formaW8BENE.setPartXIcmp25c(false);
        }
        if (!formaW8BENE.isPartXII()) {
            formaW8BENE.setPartXIIcmp26a(false);
            formaW8BENE.setPartXIIcmp26b(null);
            formaW8BENE.setPartXIIcmp26c(null);
            formaW8BENE.setPartXIIcmp26d(null);
        }
        if (!formaW8BENE.isPartXIII()) {
            formaW8BENE.setPartXIIIcmp27(false);
        }
        if (!formaW8BENE.isPartXIV()) {
            formaW8BENE.setPartXIVcmp28a(false);
            formaW8BENE.setPartXIVcmp28b(false);
        }
        if (!formaW8BENE.isPartXV()) {
            formaW8BENE.setPartXVcmp29a(false);
            formaW8BENE.setPartXVcmp29b(false);
            formaW8BENE.setPartXVcmp29c(false);
            formaW8BENE.setPartXVcmp29d(false);
            formaW8BENE.setPartXVcmp29e(false);
            formaW8BENE.setPartXVcmp29f(false);
        }
        if (!formaW8BENE.isPartXVI()) {
            formaW8BENE.setPartXVIcmp30(false);
        }
        if (!formaW8BENE.isPartXVII()) {
            formaW8BENE.setPartXVIIcmp31(false);
        }
        if (!formaW8BENE.isPartXVIII()) {
            formaW8BENE.setPartXVIIIcmp32(false);
        }
        if (!formaW8BENE.isPartXIX()) {
            formaW8BENE.setPartXIXcmp33a(false);
            formaW8BENE.setPartXIXcmp33b(null);
        }
        if (!formaW8BENE.isPartXX()) {
            formaW8BENE.setPartXXcmp34a(false);
            formaW8BENE.setPartXXcmp34b(null);
        }
        if (!formaW8BENE.isPartXXI()) {
            formaW8BENE.setPartXXIcmp35a(false);
            formaW8BENE.setPartXXIcmp35b(null);
        }
        if (!formaW8BENE.isPartXXII()) {
            formaW8BENE.setPartXXIIcmp36(false);
        }
        if (!formaW8BENE.isPartXXIII()) {
            formaW8BENE.setPartXXIIIcmp37a(false);
            formaW8BENE.setPartXXIIIcmp37b(null);
            formaW8BENE.setPartXXIIIcmp37c(false);
            formaW8BENE.setPartXXIIIcmp37d(null);
            formaW8BENE.setPartXXIIIcmp37e(null);
        }
        if (!formaW8BENE.isPartXXIV()) {
            formaW8BENE.setPartXXIVcmp38(false);
        }
        if (!formaW8BENE.isPartXXV()) {
            formaW8BENE.setPartXXVcmp39(false);
        }
        if (!formaW8BENE.isPartXXVI()) {
            formaW8BENE.setPartXXVIcmp40a(false);
            formaW8BENE.setPartXXVIcmp40b(false);
            formaW8BENE.setPartXXVIcmp40c(false);
            formaW8BENE.setPartXXIX(false);
        }
        if (!formaW8BENE.isPartXXVII()) {
            formaW8BENE.setPartXXVIIcmp41(false);
        }
        if (!formaW8BENE.isPartXXVIII()) {
            formaW8BENE.setPartXXVIIIcmp42(null);
            formaW8BENE.setPartXXVIIIcmp43(false);
        }
        if (!formaW8BENE.isPartIIIcmp14bj()) {
            formaW8BENE.setPartIIIcmp14bk(null);
        }
        // parte 30 siempre checada
        formaW8BENE.setPartXXX(true);

        if (!formaW8BENE.isPartXXVIcmp40c()) {// formaW8BENE.isPartXXVIcmp40c()
            formaW8BENE.setPartXXIXadra(null);
            formaW8BENE.setPartXXIXadrb(null);
            formaW8BENE.setPartXXIXadrc(null);
            formaW8BENE.setPartXXIXadrd(null);
            formaW8BENE.setPartXXIXadre(null);
            formaW8BENE.setPartXXIXadrf(null);
            formaW8BENE.setPartXXIXadrg(null);
            formaW8BENE.setPartXXIXadrh(null);
            formaW8BENE.setPartXXIXadri(null);

            formaW8BENE.setPartXXIXnoma(null);
            formaW8BENE.setPartXXIXnomb(null);
            formaW8BENE.setPartXXIXnomc(null);
            formaW8BENE.setPartXXIXnomd(null);
            formaW8BENE.setPartXXIXnome(null);
            formaW8BENE.setPartXXIXnomf(null);
            formaW8BENE.setPartXXIXnomg(null);
            formaW8BENE.setPartXXIXnomh(null);
            formaW8BENE.setPartXXIXnomi(null);

            formaW8BENE.setPartXXIXtina(null);
            formaW8BENE.setPartXXIXtinb(null);
            formaW8BENE.setPartXXIXtinc(null);
            formaW8BENE.setPartXXIXtind(null);
            formaW8BENE.setPartXXIXtine(null);
            formaW8BENE.setPartXXIXtinf(null);
            formaW8BENE.setPartXXIXting(null);
            formaW8BENE.setPartXXIXtinh(null);
            formaW8BENE.setPartXXIXtini(null);
        }
    }


}
