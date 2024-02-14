// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.horariosCustodios;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaHorariosCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.HorariosCustodiosVO;

import java.text.SimpleDateFormat;
import java.util.*;

public class Util {

    static Random random = new Random();
    public static String usuario = "indevaldrp";

    public static List<Integer> estados = obtenerEstados();

    public static List<Custodio> custodios = obtenerCustodios();
    public static List<Divisa> divisas = obtenerDivisas();

    public static List<String[]> horariosIniciales = obtenerListaHorasDelDia(true);
    public static List<String[]> horariosFinales = obtenerListaHorasDelDia(false);

    public static List<HorariosCustodiosVO> allHorariosCustodiosEsperados = getAllHorariosCustodiosEsperados();

    public static HorariosCustodiosDto horarioCustodioActializadoEsperado(Integer idHorarioCustodio, Integer cambioEstado, String usuarioChecker) {
        HorariosCustodiosVO horariosCustodiosVO = new HorariosCustodiosVO();
        for (HorariosCustodiosVO vo : allHorariosCustodiosEsperados) {
            if (idHorarioCustodio.intValue() == vo.getIdHorariosCustodios().intValue()) {
                horariosCustodiosVO = vo;
                break;
            }
        }

        HorariosCustodiosDto resultadoDto = new HorariosCustodiosDto();
        resultadoDto.setIdHorariosCustodios(horariosCustodiosVO.getIdHorariosCustodios());
        resultadoDto.setIdCustodio(horariosCustodiosVO.getIdCustodio());
        resultadoDto.setNombreCustodio(horariosCustodiosVO.getCustodioNombre());
        resultadoDto.setIdDivisa(horariosCustodiosVO.getIdDivisa());
        resultadoDto.setNombreDivisa(horariosCustodiosVO.getDivisaClave());
        resultadoDto.setHorarioInicial(horariosCustodiosVO.getHorarioInicial());
        resultadoDto.setHorarioFinal(horariosCustodiosVO.getHorarioFinal());
        resultadoDto.setFechaCreacion(horariosCustodiosVO.getFechaCreacion());
        resultadoDto.setCreador(horariosCustodiosVO.getCreador());
        resultadoDto.setUsuarioChecker(usuarioChecker);
        resultadoDto.setEstatus(cambioEstado);
        resultadoDto.setFechaUltModificacion(new Date());

        return resultadoDto;
    }

    public static HorariosCustodiosDto horarioCustodioActializadoEsperado(HorariosCustodiosDto resultadoDto) {
        resultadoDto.setIdHorariosCustodios(new Random().nextInt(50));
        resultadoDto.setFechaUltModificacion(new Date());
        resultadoDto.setUsuarioChecker(usuario);
        Custodio custodio = obtenerCustodioByID(resultadoDto.getIdCustodio());
        if (custodio != null) {
            resultadoDto.setNombreCustodio(custodio.getNombreCorto());
        }
        Divisa divisa = obtenerDivisaByID(resultadoDto.getIdDivisa());
        if (divisa != null) {
            resultadoDto.setNombreDivisa(divisa.getClaveAlfabetica());
        }
        return resultadoDto;
    }

    public static PaginaVO paginaVOconsultaEsperada(CriteriosConsultaHorariosCustodiosVO criterios, PaginaVO paginaVO) {
        paginaVO.setRegistros(horariosCustodiosCriterios(criterios));
        paginaVO.setTotalRegistros(paginaVO.getRegistros().size());
        return paginaVO;
    }

    public static CriteriosConsultaHorariosCustodiosVO cargaCriterios(
            boolean agregarDivisa, boolean agregarCustodio,
            boolean agregarEstado, boolean agregarFecha) {
        CriteriosConsultaHorariosCustodiosVO criterios =
                new CriteriosConsultaHorariosCustodiosVO();
        HorariosCustodiosVO horariosCustodiosVO = obtenerAlgunHorarioCustodio();
        if (agregarDivisa)
            criterios.setIdDivisa(horariosCustodiosVO.getIdDivisa());
        if (agregarCustodio)
            criterios.setIdCustodio(horariosCustodiosVO.getIdCustodio());
        if (agregarEstado)
            criterios.setIdEstatus(horariosCustodiosVO.getEstatus());
        if (agregarFecha)
            criterios.setFechaCreacion(horariosCustodiosVO.getFechaCreacion());
        return criterios;
    }

    public static HorariosCustodiosVO obtenerAlgunHorarioCustodio() {
        return allHorariosCustodiosEsperados.get(random.nextInt(allHorariosCustodiosEsperados.size() - 1));
    }

    private static List<HorariosCustodiosVO> horariosCustodiosCriterios(CriteriosConsultaHorariosCustodiosVO criterios) {
        List<HorariosCustodiosVO> resultados = allHorariosCustodiosEsperados;


        if (criterios.getIdDivisa() != null) {
            resultados = filtrarPorDivisa(resultados, criterios.getIdDivisa());
        }

        if (criterios.getIdCustodio() != null) {
            resultados = filtrarPorCustodio(resultados, criterios.getIdCustodio());
        }

        if (criterios.getIdEstatus() != null) {
            resultados = filtrarPorEstado(resultados, criterios.getIdEstatus());
        }

        if (criterios.getFechaCreacion() != null) {
            resultados = filtrarPorFecha(resultados, criterios.getFechaCreacion());
        }

        return resultados;
    }

    private static List<HorariosCustodiosVO> filtrarPorDivisa(
            List<HorariosCustodiosVO> horariosCustodios, int idDivisa) {
        List<HorariosCustodiosVO> filtro = new ArrayList<>();
        for (HorariosCustodiosVO horariosCustodiosVO : horariosCustodios) {
            if (horariosCustodiosVO.getIdDivisa().intValue() == idDivisa) {
                filtro.add(horariosCustodiosVO);
            }
        }
        return filtro;
    }

    private static List<HorariosCustodiosVO> filtrarPorCustodio(
            List<HorariosCustodiosVO> horariosCustodios, int idCustodio) {
        List<HorariosCustodiosVO> filtro = new ArrayList<>();
        for (HorariosCustodiosVO horariosCustodiosVO : horariosCustodios) {
            if (horariosCustodiosVO.getIdCustodio().intValue() == idCustodio) {
                filtro.add(horariosCustodiosVO);
            }
        }
        return filtro;
    }

    private static List<HorariosCustodiosVO> filtrarPorEstado(
            List<HorariosCustodiosVO> horariosCustodios, int idEstado) {
        List<HorariosCustodiosVO> filtro = new ArrayList<>();
        for (HorariosCustodiosVO horariosCustodiosVO : horariosCustodios) {
            if (horariosCustodiosVO.getEstatus().intValue() == idEstado) {
                filtro.add(horariosCustodiosVO);
            }
        }
        return filtro;
    }

    private static List<HorariosCustodiosVO> filtrarPorFecha(
            List<HorariosCustodiosVO> horariosCustodios, Date fecha) {
        List<HorariosCustodiosVO> filtro = new ArrayList<>();
        for (HorariosCustodiosVO horariosCustodiosVO : horariosCustodios) {
            if (isSameDay(horariosCustodiosVO.getFechaCreacion(), fecha)) {
                filtro.add(horariosCustodiosVO);
            }
        }
        return filtro;
    }

    private static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }


    private static List<HorariosCustodiosVO> getAllHorariosCustodiosEsperados() {
        List<HorariosCustodiosVO> horariosCustodios = new ArrayList<>();


        int cantidadHorarios = 5 + random.nextInt(15);

        for (int i = 0; i < cantidadHorarios; i++) {
            HorariosCustodiosVO horarioCustodio = new HorariosCustodiosVO();
            horarioCustodio.setIdHorariosCustodios(i + 1);
            horarioCustodio.setCreador(usuario);

            horarioCustodio.setEstatus(estados.get(random.nextInt(estados.size() - 1)));

            Custodio custodio = custodios.get(random.nextInt(custodios.size() - 1));
            horarioCustodio.setIdCustodio(custodio.getId());
            horarioCustodio.setCustodioNombre(custodio.getNombreCorto());
            horarioCustodio.setCustodioDescripcion(custodio.getDescripcion());

            Divisa divisa = divisas.get(random.nextInt(divisas.size() - 1));
            horarioCustodio.setIdDivisa(divisa.getIdDivisa().intValue());
            horarioCustodio.setDivisaClave(divisa.getClaveAlfabetica());
            horarioCustodio.setDivisaDescripcion(divisa.getDescripcion());

            int indiceHorarios = random.nextInt(horariosIniciales.size() - 1);
            horarioCustodio.setHorarioInicial(horariosIniciales.get(indiceHorarios)[1]);
            horarioCustodio.setHorarioFinal(horariosFinales.get(indiceHorarios)[1]);

            horarioCustodio.setFechaCreacion(generarFechaAleatoria());
            horariosCustodios.add(horarioCustodio);
        }
        return horariosCustodios;
    }


    private static Date generarFechaAleatoria() {
        Calendar fechaInicio = new GregorianCalendar(2022, Calendar.JANUARY, 1);
        Calendar fechaFin = new GregorianCalendar(2024, Calendar.DECEMBER, 31);

        long diferencia = fechaFin.getTime().getTime() - fechaInicio.getTime().getTime();
        Random random = new Random();
        long tiempoAleatorio = fechaInicio.getTime().getTime() + (long) (random.nextDouble() * diferencia);
        return new Date(tiempoAleatorio);
    }


    private static Divisa obtenerDivisaByID(int id) {
        for (Divisa divisa : divisas) {
            if (divisa.getIdDivisa().intValue() == id)
                return divisa;
        }
        return null;
    }

    private static Custodio obtenerCustodioByID(int id) {
        for (Custodio custodio : custodios) {
            if (custodio.getId().intValue() == id)
                return custodio;
        }
        return null;
    }

    private static List<String[]> obtenerListaHorasDelDia(boolean inicio) {
        List<String[]> listaHoras = new ArrayList<>();
        SimpleDateFormat formatoPresentacion = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatoCarga = new SimpleDateFormat("HH:mm:ss");

        for (int hora = 0; hora < 24; hora++) {
            Date horaActual = new Date();
            horaActual.setHours(hora);
            int valor = (inicio ? 0 : 59);
            horaActual.setMinutes(valor);
            horaActual.setSeconds(valor);
            String horaPresentacion = formatoPresentacion.format(horaActual);
            String horaCarga = formatoCarga.format(horaActual);
            String[] rango = {horaCarga, horaPresentacion};
            listaHoras.add(rango);
        }

        return listaHoras;
    }

    private static List<Custodio> obtenerCustodios() {
        List<Custodio> custodios = new ArrayList<>();
        String[] nombresCortos = {"CLEARST", "EUROCLE", "DEUTSCHE", "SANTANDER", "BBVA", "BONY", "JPMORGAN", "BOFAMERI", "DCVCHILE", "DECEVAL", "CAVALI", "MARKIT", "CITIBANK", "BNPPARIBAS"};
        String[] descripciones = {"CLEARSTREAM BANKING", "EUROCLEAR BANK", "DEUTSCHE BANK AG, NY", "SANTANDER INVESTMENT SERVICES, S.A.", "BANCO BILBAO VIZCAYA ARGENTARIA SA", "THE BANK OF NEW YORK", "JP MORGAN", "BANK OF AMERICA MERILL LYNCH", "DEPOSITO CENTRAL DE VALORES S.A.", "DECEVAL S.Field52aOrderInstLocal.A.", "CAVALI S.A. I.C.L.V.", "MARKIT", "CITIBANK, N.A.", "BNP PARIBAS"};


        for (int i = 0; i < nombresCortos.length; i++) {
            Custodio custodio = new Custodio();
            custodio.setId(i + 1);
            custodio.setNombreCorto(nombresCortos[i]);
            custodio.setDescripcion(descripciones[i]);
            custodios.add(custodio);
        }
        return custodios;
    }

    private static List<Divisa> obtenerDivisas() {
        List<Divisa> divisas = new ArrayList<>();
        String[] nombresCortos = {"MXN", "UDI", "USD", "EUR", "ARS", "AUD", "BRL", "CAD", "CHF", "CLP", "CNY", "COP", "DKK", "GBP", "HKD", "ILS", "ISK"};
        String[] descripciones = {"Mexican Peso", "Mexican Unidad de Inversion", "USA DOLLAR", "Euro", "Argentine Peso", "Australian Dollar", "Brazilian Real", "Canadian Dollar", "Swiss Franc", "Chilean Peso", "Yuan Renminbi", "Colombian Peso", "Danish Krone", "United Kingdom Pound", "Hong Kong Dollar", "New Israeli SheqelISK", "Iceland Krona"};
        for (int i = 0; i < nombresCortos.length; i++) {
            Divisa divisa = new Divisa();
            divisa.setIdDivisa(new Long(i + 1));
            divisa.setClaveAlfabetica(nombresCortos[i]);
            divisa.setDescripcion(descripciones[i]);
            divisas.add(divisa);
        }
        return divisas;
    }

    private static List<Integer> obtenerEstados() {
        List<Integer> estados = new ArrayList<>();
        estados.add(1);//"REGISTRADO"
        estados.add(2);//"AUTORIZADO"
        estados.add(3);//"CANCELADO"
        return estados;
    }


}

