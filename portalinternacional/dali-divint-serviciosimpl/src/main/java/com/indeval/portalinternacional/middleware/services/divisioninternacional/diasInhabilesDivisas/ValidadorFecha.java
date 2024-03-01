/*
 * Multidivisas: Dás inhábiles por Divisa : Validador de campos del archivo de carga
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional.diasInhabilesDivisas;

import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.ErrorDiaInhabilDivisa;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.FechaDiaInhabilDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.ErrorDiaInhabilDivisaTipo.*;

public class ValidadorFecha {

    public static FechaDiaInhabilDTO obtenerFechaValida(String linea) {
        FechaDiaInhabilDTO validadorFecha = new FechaDiaInhabilDTO();
        validadorFecha.setCampos(esLineaValida(linea));
        if (validadorFecha.getCampos() != null) {
            validadorFecha.setDivisa(validadorFecha.getCampos()[0]);
            validadorFecha.setAnio(esAnioValido(validadorFecha.getCampos()[3]));
            if (validadorFecha.getAnio() != null) {
                validadorFecha.setMes(esMesValido(validadorFecha.getCampos()[2]));
                if (validadorFecha.getMes() != null) {
                    validadorFecha.setDia(esDiaValido(validadorFecha.getCampos()[1], validadorFecha.getMes(), validadorFecha.getAnio()));
                    if (validadorFecha.getDia() != null) {
                        validadorFecha.setFecha(cargaFecha(
                                validadorFecha.getDia(),
                                validadorFecha.getMes(),
                                validadorFecha.getAnio()));
                        validadorFecha.setValida(true);
                    } else {
                        validadorFecha.setError(new ErrorDiaInhabilDivisa(ERROR_FECHA_DIA, validadorFecha.getCampos()[1]));
                    }
                } else {
                    validadorFecha.setError(new ErrorDiaInhabilDivisa(ERROR_FECHA_MES, validadorFecha.getCampos()[2]));
                }
            } else {
                validadorFecha.setError(new ErrorDiaInhabilDivisa(ERROR_FECHA_ANIO, validadorFecha.getCampos()[3]));
            }
        } else {
            validadorFecha.setError(new ErrorDiaInhabilDivisa(ERROR_FORMATO, linea));
        }
        return validadorFecha;
    }

    private static Date cargaFecha(int dia, int mes, int anio) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, anio);
        calendar.set(Calendar.MONTH, mes - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dia);
        return calendar.getTime();
    }

    private static String[] esLineaValida(String linea) {
        String[] campos = linea.split(",");
        if (campos.length > 3) {
            return campos;
        } else {
            return null;
        }
    }

    private static boolean esNumero(String texto) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(texto);
        return matcher.matches();
    }

    private static boolean esAlfanumerico(String texto) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(texto);
        return matcher.matches();
    }

    private static Integer esAnioValido(String anio) {
        if (esNumero(anio)) {
            int anioNumero = Integer.parseInt(anio);
            Calendar calendario = Calendar.getInstance();
            int actual = calendario.get(Calendar.YEAR);
            if (anioNumero >= actual) {
                return anioNumero;
            }
        }
        return null;
    }

    private static Integer esMesValido(String mes) {
        if (esNumero(mes)) {
            int mesNumero = Integer.parseInt(mes);
            if (mesNumero > 0 && mesNumero < 13) {
                return mesNumero;
            }
        }
        return null;
    }

    private static Integer esDiaValido(String dia, int mes, int anio) {
        if (esNumero(dia)) {
            int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (esBisiesto(anio)) {
                diasPorMes[2] = 29;
            }
            int diaNumero = Integer.parseInt(dia);
            if (diaNumero >= 1 && diaNumero <= diasPorMes[mes]) {
                return diaNumero;
            }
        }
        return null;
    }

    private static boolean esBisiesto(int anio) {
        return (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
    }
}

