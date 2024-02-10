/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.common;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.persistence.modelo.DiaInhabil;

/**
 * 
 * @author Erik Vera Montoya - 2H Software
 * @version 1.0 - Jul 8, 2008
 */
@SuppressWarnings({"unchecked"})
public class DiasInhabilesController {

    private DateUtilService dateUtilService;

    int diasAnterioresConsulta = 0;

    public Date getHabilDate() {
        Date hoy = dateUtilService.getCurrentDate();
        Calendar calhoy = Calendar.getInstance();
        Calendar calInhabil = Calendar.getInstance();
        calhoy.setTime(hoy);
        boolean banderaDiaInhabil = true;
        List diasInhabiles = null;

        while (banderaDiaInhabil) {
            banderaDiaInhabil = false;
            if (diasInhabiles == null) {
                try {
                    diasInhabiles = dateUtilService.consultarDiasInhabilesByMonth(calhoy.get(Calendar.MONTH), calhoy
                            .get(Calendar.YEAR));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (diasInhabiles != null) {
                for (int i = 0; i < diasInhabiles.size(); i++) {
                    calInhabil.setTime(((DiaInhabil) diasInhabiles.get(i)).getDiaInhabil());
                    if (calInhabil.get(Calendar.DAY_OF_MONTH) == calhoy.get(Calendar.DAY_OF_MONTH)) {
                        banderaDiaInhabil = true;
                        int mes = calhoy.get(Calendar.MONTH);
                        calhoy.setTime(addDays(calhoy.getTime(), -1));
                        if (mes != calhoy.get(Calendar.MONTH)) {
                            diasInhabiles = null;
                        }
                        break;
                    }
                }
            }
        }

        return calhoy.getTime();
    }

    public Date[] getShowDates() {
        return new Date[] { getCurrentDate(), getHabilDate() };
    }

    /**
     * Suma una cantidad de dias a la fecha proporcionada. Notese que la cantidad de dias puede ser negativa o positiva.
     */
    public static Date addDays(Date fecha, int dias) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(fecha);
        cal.add(Calendar.DAY_OF_MONTH, dias);

        return cal.getTime();
    }

    /**
     * Metdo que obtiene la fecha actual
     */

    public Date getCurrentDate() {
        Date hoy = dateUtilService.getCurrentDate();
        return hoy;
    }

    public Date[] getDiasInhabiles(int anio, int mes) {
        int month = mes - 1;
        int year = anio;
        List diasInhabiles;
        try {
            diasInhabiles = dateUtilService.consultarDiasInhabilesByMonth(month, year);
            Date[] fechas = new Date[diasInhabiles.size()];
            for (int i = 0; i < diasInhabiles.size(); i++) {

                fechas[i] = ((DiaInhabil) diasInhabiles.get(i)).getDiaInhabil();
            }
            return fechas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     */
    public Date getLimiteInferior() {

        return dateUtilService.agregarDiasHabiles(dateUtilService.getCurrentDate(), -1 * diasAnterioresConsulta);

    }

    /**
     * @param dateutilService the dateutilService to set
     */
    public void setDateUtilService(DateUtilService dateutilService) {
        this.dateUtilService = dateutilService;
    }

    /**
     * @return the diasAnterioresConsulta
     */
    public int getDiasAnterioresConsulta() {
        return diasAnterioresConsulta;
    }

    /**
     * @param diasAnterioresConsulta the diasAnterioresConsulta to set
     */
    public void setDiasAnterioresConsulta(int diasAnterioresConsulta) {
        this.diasAnterioresConsulta = diasAnterioresConsulta;
    }

}
