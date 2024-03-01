package com.indeval.portalinternacional.presentation.controller.diasInhabilesDivisas;

import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;

import java.util.List;

public class ObjetosParaProcesar {
    private String nombreArchivo;
    private List<FileTransferVO> objetosParaProcesar;
    private Integer totalRegistros;
    private Integer totalParaProcesar;
    private Integer totalErrores;

    private String estadoCarga;

    private List<String> tiposErrores;

    public ObjetosParaProcesar() {
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public List<FileTransferVO> getObjetosParaProcesar() {
        return objetosParaProcesar;
    }

    public void setObjetosParaProcesar(List<FileTransferVO> objetosParaProcesar) {
        this.objetosParaProcesar = objetosParaProcesar;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Integer getTotalParaProcesar() {
        return totalParaProcesar;
    }

    public void setTotalParaProcesar(Integer totalParaProcesar) {
        this.totalParaProcesar = totalParaProcesar;
    }

    public Integer getTotalErrores() {
        return totalErrores;
    }

    public void setTotalErrores(Integer totalErrores) {
        this.totalErrores = totalErrores;
    }

    public String getEstadoCarga() {
        return estadoCarga;
    }

    public void setEstadoCarga(String estadoCarga) {
        this.estadoCarga = estadoCarga;
    }

    public List<String> getTiposErrores() {
        return tiposErrores;
    }

    public void setTiposErrores(List<String> tiposErrores) {
        this.tiposErrores = tiposErrores;
    }

    @Override
    public String toString() {
        return "ObjetosParaProcesar{" +
                "nombreArchivo='" + nombreArchivo + '\'' +
                ", objetosParaProcesar=" + (objetosParaProcesar == null ? "NULL" : objetosParaProcesar.size()) +
                ", totalRegistros=" + totalRegistros +
                ", totalParaProcesar=" + totalParaProcesar +
                ", totalErrores=" + totalErrores +
                ", estadoCarga='" + estadoCarga + '\'' +
                '}';
    }
}
