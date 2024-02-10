/**
 * Bursatec - Portal Internacional Copyright (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2017;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;

/**
 * Utileria para el formato W8 IMY 2015
 * 
 * @author Pablo Balderas
 */
public class W8IMY2017Util {

    public static Map<String, String> w8ImyParteI4Campos = new HashMap<String, String>();
    public static Map<String, String> w8ImyParteI4Valores = new HashMap<String, String>();
    public static Map<String, String> w8ImyParteI5Campos = new HashMap<String, String>();
    public static Map<String, String> w8ImyParteI5Valores = new HashMap<String, String>();

    static {
        W8IMY2017Util.w8ImyParteI4Campos.put("partIcmp4a", "1");
        W8IMY2017Util.w8ImyParteI4Campos.put("partIcmp4b", "2");
        W8IMY2017Util.w8ImyParteI4Campos.put("partIcmp4c", "9");
        W8IMY2017Util.w8ImyParteI4Campos.put("partIcmp4d", "3");
        W8IMY2017Util.w8ImyParteI4Campos.put("partIcmp4e", "4");
        W8IMY2017Util.w8ImyParteI4Campos.put("partIcmp4f", "5");
        W8IMY2017Util.w8ImyParteI4Campos.put("partIcmp4g", "6");
        W8IMY2017Util.w8ImyParteI4Campos.put("partIcmp4h", "7");
        W8IMY2017Util.w8ImyParteI4Campos.put("partIcmp4i", "8");

        W8IMY2017Util.w8ImyParteI4Valores.put("partIcmp4a",
                "topmostSubform[0].Page1[0].Line4[0].c1_001[0]");
        W8IMY2017Util.w8ImyParteI4Valores.put("partIcmp4b",
                "topmostSubform[0].Page1[0].Line4[0].c1_002[0]");
        W8IMY2017Util.w8ImyParteI4Valores.put("partIcmp4c",
                "topmostSubform[0].Page1[0].Line4[0].c1_003[0]");
        W8IMY2017Util.w8ImyParteI4Valores.put("partIcmp4d",
                "topmostSubform[0].Page1[0].Line4[0].c1_004[0]");
        W8IMY2017Util.w8ImyParteI4Valores.put("partIcmp4e",
                "topmostSubform[0].Page1[0].Line4[0].c1_005[0]");
        W8IMY2017Util.w8ImyParteI4Valores.put("partIcmp4f",
                "topmostSubform[0].Page1[0].Line4[0].c1_006[0]");
        W8IMY2017Util.w8ImyParteI4Valores.put("partIcmp4g",
                "topmostSubform[0].Page1[0].Line4[0].c1_007[0]");
        W8IMY2017Util.w8ImyParteI4Valores.put("partIcmp4h",
                "topmostSubform[0].Page1[0].Line4[0].c1_008[0]");
        W8IMY2017Util.w8ImyParteI4Valores.put("partIcmp4i",
                "topmostSubform[0].Page1[0].Line4[0].c1_009[0]");

        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5b", "2"); // 11
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5c", "3"); // 12
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5d", "4"); // 13
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5f", "6"); // 15
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5i", "9"); // 18
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5l", "8"); // 21
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5m", "12"); // 22
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5n", "12"); // 23
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5o", "13"); // 24
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5p", "14"); // 25
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5q", "15"); // 26
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5r", "16"); // 27
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5u", "16"); // 30
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5v", "18"); // 31
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5w", "19"); // 32
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5x", "19"); // 33
        W8IMY2017Util.w8ImyParteI5Campos.put("partIcmp5y", "19"); // 34

        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5b", "topmostSubform[0].Page1[0].c1_011[0]"); // 11
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5c", "topmostSubform[0].Page1[0].c1_012[0]"); // 12
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5d", "topmostSubform[0].Page1[0].c1_013[0]"); // 13
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5f", "topmostSubform[0].Page1[0].c1_015[0]"); // 15
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5i", "topmostSubform[0].Page1[0].c1_018[0]"); // 18
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5l", "topmostSubform[0].Page1[0].c1_021[0]"); // 21
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5m", "topmostSubform[0].Page1[0].c1_022[0]"); // 22
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5n", "topmostSubform[0].Page1[0].c1_023[0]"); // 23
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5o", "topmostSubform[0].Page1[0].c1_024[0]"); // 24
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5p", "topmostSubform[0].Page1[0].c1_025[0]"); // 25
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5q", "topmostSubform[0].Page1[0].c1_026[0]"); // 26
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5r", "topmostSubform[0].Page1[0].c1_027[0]"); // 27
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5u", "topmostSubform[0].Page1[0].c1_030[0]"); // 30
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5v", "topmostSubform[0].Page1[0].c1_031[0]"); // 31
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5w", "topmostSubform[0].Page1[0].c1_032[0]"); // 32
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5x", "topmostSubform[0].Page1[0].c1_033[0]"); // 33
        W8IMY2017Util.w8ImyParteI5Valores.put("partIcmp5y", "topmostSubform[0].Page1[0].c1_034[0]"); // 34
    }

    /**
     * Prende o apaga los booleanos correspondientes a los apendices de la forma W8IMY.
     * 
     * @param w8imy Forma W8IMY sobre la cual se prenden o apagan los booleanos.
     */
    public static void checarApendicesCampo4(final FormaW8IMY2017 w8imy) {
        // w8imy.setPartIII(w8imy.isPartIcmp4a());
        // w8imy.setPartIV(w8imy.isPartIcmp4b());
        // w8imy.setPartV(w8imy.isPartIcmp4c() || w8imy.isPartIcmp5f() ? true : false);
        // w8imy.setPartVI(w8imy.isPartIcmp4d());
        // w8imy.setPartVII(w8imy.isPartIcmp4e() || w8imy.isPartIcmp4f() ? true : false);
        // w8imy.setPartVIII(w8imy.isPartIcmp4g() || w8imy.isPartIcmp4h() || w8imy.isPartIcmp4i() ?
        // true
        // : false);
    }

    /**
     * Prende o apaga los booleanos correspondientes a los apendices de la forma W8IMY.
     * 
     * @param w8imy Forma W8IMY sobre la cual se prenden o apagan los booleanos.
     */
    public static void checarApendicesCampo5(final FormaW8IMY2017 w8imy) {
        // w8imy.setPartIX(w8imy.isPartIcmp5a());
        // w8imy.setPartV(w8imy.isPartIcmp4c() || w8imy.isPartIcmp5f() ? true : false);
        // w8imy.setPartX(w8imy.isPartIcmp5g());
        // w8imy.setPartXII(w8imy.isPartIcmp5h());
        // w8imy.setPartXIII(w8imy.isPartIcmp5i());
        // w8imy.setPartXIV(w8imy.isPartIcmp5j());
        // w8imy.setPartXV(w8imy.isPartIcmp5k());
        // w8imy.setPartXI(w8imy.isPartIcmp5l());
        // w8imy.setPartXVI(w8imy.isPartIcmp5m());
        // w8imy.setPartXVII(w8imy.isPartIcmp5n());
        // w8imy.setPartXVIII(w8imy.isPartIcmp5o());
        // w8imy.setPartXIX(w8imy.isPartIcmp5p());
        // w8imy.setPartXX(w8imy.isPartIcmp5q());
        // w8imy.setPartXXI(w8imy.isPartIcmp5r());
        // w8imy.setPartXXII(w8imy.isPartIcmp5s());
        // w8imy.setPartXXIII(w8imy.isPartIcmp5t());
        // w8imy.setPartXXIV(w8imy.isPartIcmp5u());
        // w8imy.setPartXXV(w8imy.isPartIcmp5v());
        // w8imy.setPartXXVI(w8imy.isPartIcmp5w());
        // w8imy.setPartXXVII(w8imy.isPartIcmp5y());
    }

    /**
     * Obtiene el apendice a mostrar de acuerdo a la opción del campo 4.
     * 
     * @param w8imy Formato
     * @return Apendice
     */
    public static String obtenerApendiceCampo4(final FormaW8IMY2017 w8imy) {
        String apendice4 = new String();
        if (w8imy.isPartIcmp4a()) {
            apendice4 = ".partIII";
        } else if (w8imy.isPartIcmp4b()) {
            apendice4 = ".partIV";
        } else if (w8imy.isPartIcmp4c()) {
            apendice4 = ".partV";
        } else if (w8imy.isPartIcmp4d()) {
            apendice4 = ".partVI";
        } else if (w8imy.isPartIcmp4e() || w8imy.isPartIcmp4f()) {
            apendice4 = ".partVII";
        } else if (w8imy.isPartIcmp4g() || w8imy.isPartIcmp4h() || w8imy.isPartIcmp4i()) {
            apendice4 = ".partVIII";
        }
        return apendice4;
    }

    /**
     * Obtiene el apendice a mostrar de acuerdo a la opción del campo 5.
     * 
     * @param w8imy Formato
     * @return Apendice
     */
    public static String obtenerApendiceCampo5(final FormaW8IMY2017 w8imy) {
        String apendice5 = new String();

        if (w8imy.isPartIcmp5a()) {
            apendice5 = ".partIX";
        } else if (w8imy.isPartIcmp5f()) {
            apendice5 = ".partV";
        } else if (w8imy.isPartIcmp5g()) {
            apendice5 = ".partX";
        } else if (w8imy.isPartIcmp5h()) {
            apendice5 = ".partXII";
        } else if (w8imy.isPartIcmp5i()) {
            apendice5 = ".partXIII";
        } else if (w8imy.isPartIcmp5j()) {
            apendice5 = ".partXIV";
        } else if (w8imy.isPartIcmp5k()) {
            apendice5 = ".partXV";
        } else if (w8imy.isPartIcmp5l()) {
            apendice5 = ".partXI";
        } else if (w8imy.isPartIcmp5m()) {
            apendice5 = ".partXVI";
        } else if (w8imy.isPartIcmp5n()) {
            apendice5 = ".partXVII";
        } else if (w8imy.isPartIcmp5o()) {
            apendice5 = ".partXVIII";
        } else if (w8imy.isPartIcmp5p()) {
            apendice5 = ".partXIX";
        } else if (w8imy.isPartIcmp5q()) {
            apendice5 = ".partXX";
        } else if (w8imy.isPartIcmp5r()) {
            apendice5 = ".partXXI";
        } else if (w8imy.isPartIcmp5s()) {
            apendice5 = ".partXXII";
        } else if (w8imy.isPartIcmp5t()) {
            apendice5 = ".partXXIII";
        } else if (w8imy.isPartIcmp5u()) {
            apendice5 = ".partXXIV";
        } else if (w8imy.isPartIcmp5v()) {
            apendice5 = ".partXXV";
        } else if (w8imy.isPartIcmp5w()) {
            apendice5 = ".partXXVI";
        } else if (w8imy.isPartIcmp5y()) {
            apendice5 = ".partXXVII";
        }
        return apendice5;
    }


    /**
     * Inhabilita el campo 7 y limpia los campos
     * 
     * @param formaW8BENE Forma
     */
    public static void deshabilitarDomicilioCorreo(final boolean deshabilitaDomicilioCorreo,
            final FormaW8IMY2017 w8imy) {
        if (deshabilitaDomicilioCorreo) {
            w8imy.setPartIcmp7a(null);
            w8imy.setPartIcmp7b(null);
            w8imy.setPartIcmp7c(null);
        }
        w8imy.setReadOnlyCmp7(deshabilitaDomicilioCorreo);
    }

    /**
     * Verifica las partes del formato para colocar a null o false los campos de las partes no
     * seleccionadas.
     * 
     * @param w8imy Forma sobre la cual se realiza la verificación.
     */
    public static void validarPartesFormato(final FormaW8IMY2017 w8imy) {
        // if (!w8imy.isPartIII()) {
        // w8imy.setPartIIIcmp14a(false);
        // w8imy.setPartIIIcmp14b(false);
        // w8imy.setPartIIIcmp14c(false);
        // w8imy.setPartIIIcmp14c1(null);
        // w8imy.setPartIIIcmp14d(false);
        // w8imy.setPartIIIcmp14d1(null);
        // w8imy.setPartIIIcmp14e(false);
        // w8imy.setPartIIIcmp14e1(null);
        // w8imy.setPartIIIcmp14ei(false);
        // w8imy.setPartIIIcmp14eii(false);
        // w8imy.setPartIIIcmp14f(false);
        // }
        // if (!w8imy.isPartIV()) {
        // w8imy.setPartIVcmp15a(false);
        // w8imy.setPartIVcmp15b(false);
        // w8imy.setPartIVcmp15c(false);
        // w8imy.setPartIVcmp15d(false);
        // }
        // if (!w8imy.isPartV()) {
        // w8imy.setPartVcmp16a(false);
        // w8imy.setPartVcmp16b(false);
        // w8imy.setPartVcmp16c(false);
        // }
        // if (!w8imy.isPartVI()) {
        // w8imy.setPartVIcmp17a(false);
        // w8imy.setPartVIcmp17b(false);
        // w8imy.setPartVIcmp17c(false);
        // }
        // if (!w8imy.isPartVII()) {
        // w8imy.setPartVIIcmp18(false);
        // }
        // if (!w8imy.isPartVIII()) {
        // w8imy.setPartVIIIcmp19(false);
        // }
        // if (!w8imy.isPartIX()) {
        // w8imy.setPartIXcmp20(false);
        // }
        // if (!w8imy.isPartX()) {
        // w8imy.setPartXcmp21a(null);
        // w8imy.setPartXcmp21b(false);
        // w8imy.setPartXcmp21c(false);
        // }
        // if (!w8imy.isPartXI()) {
        // w8imy.setPartXIcmp22a(false);
        // w8imy.setPartXIcmp22b(false);
        // w8imy.setPartXIcmp22c(false);
        // }
        // if (!w8imy.isPartXII()) {
        // w8imy.setPartXIIcmp23(false);
        // }
        // if (!w8imy.isPartXIII()) {
        // w8imy.setPartXIIIcmp24(false);
        // }
        // if (!w8imy.isPartXIV()) {
        // w8imy.setPartXIVcmp25a(null);
        // w8imy.setPartXIVcmp25b(false);
        // }
        // if (!w8imy.isPartXV()) {
        // w8imy.setPartXVcmp26(false);
        // }
        // if (!w8imy.isPartXVI()) {
        // w8imy.setPartXVIcmp27a(false);
        // w8imy.setPartXVIcmp27b(false);
        // w8imy.setPartXVIcmp27c(false);
        // }
        // if (!w8imy.isPartXVII()) {
        // w8imy.setPartXVIIcmp28(false);
        // }
        // if (!w8imy.isPartXVIII()) {
        // w8imy.setPartXVIIIcmp29a(false);
        // w8imy.setPartXVIIIcmp29b(null);
        // w8imy.setPartXVIIIcmp29c(null);
        // w8imy.setPartXVIIIcmp29d(null);
        // }
        // if (!w8imy.isPartXIX()) {
        // w8imy.setPartXIXcmp30a(false);
        // w8imy.setPartXIXcmp30b(false);
        // w8imy.setPartXIXcmp30c(false);
        // w8imy.setPartXIXcmp30d(false);
        // w8imy.setPartXIXcmp30e(false);
        // w8imy.setPartXIXcmp30f(false);
        // }
        // if (!w8imy.isPartXX()) {
        // w8imy.setPartXXcmp31(false);
        // }
        // if (!w8imy.isPartXXI()) {
        // w8imy.setPartXXIcmp32a(false);
        // w8imy.setPartXXIcmp32b(null);
        // }
        // if (!w8imy.isPartXXII()) {
        // w8imy.setPartXXIIcmp33a(false);
        // w8imy.setPartXXIIcmp33b(null);
        // }
        // if (!w8imy.isPartXXIII()) {
        // w8imy.setPartXXIIIcmp34a(false);
        // w8imy.setPartXXIIIcmp34a1(null);
        // w8imy.setPartXXIIIcmp34b(false);
        // w8imy.setPartXXIIIcmp34b1(null);
        // w8imy.setPartXXIIIcmp34b2(null);
        // }
        // if (!w8imy.isPartXXIV()) {
        // w8imy.setPartXXIVcmp35(false);
        // }
        // if (!w8imy.isPartXXV()) {
        // w8imy.setPartXXVcmp36(false);
        // }
        // if (!w8imy.isPartXXVI()) {
        // w8imy.setPartXXVIcmp37(false);
        // }
        // if (!w8imy.isPartXXVII()) {
        // w8imy.setPartXXVIIcmp38(null);
        // w8imy.setPartXXVIIcmp39(false);
        // }
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
     * Obtiene el tipo de beneficiario.
     * 
     * @param w8imy
     */
    public static void obtenerTipoBeneficiario(final FormaW8IMY2017 w8imy) {
        String tipoBeneficiario = null;
        if (w8imy.isPartIcmp4a()) {
            tipoBeneficiario = "Qualified intermediary";
        } else if (w8imy.isPartIcmp4b()) {
            tipoBeneficiario = "Nonqualified intermediary";
        } else if (w8imy.isPartIcmp4c()) {
            tipoBeneficiario = "Territory financial institution";
        } else if (w8imy.isPartIcmp4d()) {
            tipoBeneficiario = "U.S. branch";
        } else if (w8imy.isPartIcmp4e()) {
            tipoBeneficiario = "Withholding foreign partnership";
        } else if (w8imy.isPartIcmp4f()) {
            tipoBeneficiario = "Withholding foreign trust";
        } else if (w8imy.isPartIcmp4g()) {
            tipoBeneficiario = "Nonwithholding foreign partnership";
        } else if (w8imy.isPartIcmp4h()) {
            tipoBeneficiario = "Nonwithholding foreign simple trust";
        } else if (w8imy.isPartIcmp4i()) {
            tipoBeneficiario = "Nonwithholding foreign grantor trust";
        }
        w8imy.setDescTipoBeneficiario(tipoBeneficiario);
    }

    /**
     * Método que pasa a mayusculas todos los campos String de la forma.
     */
    public static void formaAMayusculas(final FormaW8IMY2017 w8imy) {
        w8imy.setPartIcmp1(StringUtils.upperCase(w8imy.getPartIcmp1()));
        w8imy.setPartIcmp2(StringUtils.upperCase(w8imy.getPartIcmp2()));
        w8imy.setPartIcmp3(StringUtils.upperCase(w8imy.getPartIcmp3()));
        w8imy.setPartIcmp8(StringUtils.upperCase(w8imy.getPartIcmp8()));
        w8imy.setPartIcmp9(StringUtils.upperCase(w8imy.getPartIcmp9()));
        w8imy.setPartIcmp10(StringUtils.upperCase(w8imy.getPartIcmp10()));
        w8imy.setPartIIcmp12a(StringUtils.upperCase(w8imy.getPartIIcmp12a()));
        w8imy.setPartIIcmp12b(StringUtils.upperCase(w8imy.getPartIIcmp12b()));
        w8imy.setPartIIcmp12c(StringUtils.upperCase(w8imy.getPartIIcmp12c()));
        w8imy.setPartIIcmp13(StringUtils.upperCase(w8imy.getPartIIcmp13()));
        // w8imy.setPartIIIcmp14c1(StringUtils.upperCase(w8imy.getPartIIIcmp14c1()));
        // w8imy.setPartIIIcmp14d1(StringUtils.upperCase(w8imy.getPartIIIcmp14d1()));
        // w8imy.setPartIIIcmp14e1(StringUtils.upperCase(w8imy.getPartIIIcmp14e1()));
        // w8imy.setPartXcmp21a(StringUtils.upperCase(w8imy.getPartXcmp21a()));
        // w8imy.setPartXIVcmp25a(StringUtils.upperCase(w8imy.getPartXIVcmp25a()));
        // w8imy.setPartXVIIIcmp29b(StringUtils.upperCase(w8imy.getPartXVIIIcmp29b()));
        // w8imy.setPartXVIIIcmp29c(StringUtils.upperCase(w8imy.getPartXVIIIcmp29c()));
        // w8imy.setPartXVIIIcmp29d(StringUtils.upperCase(w8imy.getPartXVIIIcmp29d()));
        // w8imy.setPartXXIcmp32b(StringUtils.upperCase(w8imy.getPartXXIcmp32b()));
        // w8imy.setPartXXIIcmp33b(StringUtils.upperCase(w8imy.getPartXXIIcmp33b()));
        // w8imy.setPartXXIIIcmp34a1(StringUtils.upperCase(w8imy.getPartXXIIIcmp34a1()));
        // w8imy.setPartXXIIIcmp34b1(StringUtils.upperCase(w8imy.getPartXXIIIcmp34b1()));
        // w8imy.setPartXXIIIcmp34b2(StringUtils.upperCase(w8imy.getPartXXIIIcmp34b2()));
        // w8imy.setPartXXVIIcmp38(StringUtils.upperCase(w8imy.getPartXXVIIcmp38()));
        w8imy.setPrintName(StringUtils.upperCase(w8imy.getPrintName()));
    }
}
