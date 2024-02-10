package com.indeval.portalinternacional.middleware.servicios.modelo;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
@Entity
@Table(name = "T_FILETRANSFER_DIVISAS_INT")
@SequenceGenerator(name = "SEQ_FILETRANSFER_DIVISAS_INT", sequenceName = "SEQ_FILETRANSFER_DIVISAS_INT", allocationSize = 1, initialValue = 1)
public class FileTransferDivisas implements Serializable {
    private static final long serialVersionUID = 1L;
    public Long idFileTransferDivisasInt;
    public Date fechaRegistro;
    public BigInteger registrosTotal;
    public BigInteger registrosExitosos;
    public BigInteger registrosError;
    public String usuarioRegistro;
    public Date fechaAutoriza;
    public String usuarioAutoriza;
    public EstatusDivisas estatusDivisas;
    public String comentarios;
    FileTransferDetalleDivisas fileTransferDetalleDivisa;
    public String usuario;
    @Id
    @Column(name = "ID_FILETRANSFER_DIVISAS_INT", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FILETRANSFER_DIVISAS_INT")
    public Long getIdFileTransferDivisasInt() {
       return idFileTransferDivisasInt;
    }
    public void setIdFileTransferDivisasInt(Long idFileTransferDivisasInt) {
       this.idFileTransferDivisasInt = idFileTransferDivisasInt;
    }
    @Column(name = "FECHA_REGISTRO", unique = false, nullable = false)
    public Date getFechaRegistro() {
       return fechaRegistro;
    }
    public void setFechaRegistro(Date fechaRegistro) {
       this.fechaRegistro = fechaRegistro;
    }
    @Column(name = "REGISTROS_TOTAL", unique = false, nullable = false)
    public BigInteger getRegistrosTotal() {
       return registrosTotal;
    }
    public void setRegistrosTotal(BigInteger registrosTotal) {
       this.registrosTotal = registrosTotal;
    }
    @Column(name = "REGISTROS_EXITOSOS", unique = false, nullable = false)
    public BigInteger getRegistrosExitosos() {
       return registrosExitosos;
    }
    public void setRegistrosExitosos(BigInteger registrosExitosos) {
       this.registrosExitosos = registrosExitosos;
    }
    @Column(name = "REGISTROS_ERROR", unique = false, nullable = false)
    public BigInteger getRegistrosError() {
       return registrosError;
    }
    public void setRegistrosError(BigInteger registrosError) {
       this.registrosError = registrosError;
    }
    @Column(name = "USUARIO_REGISTRO")
    public String getUsuarioRegistro() {
       return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
       this.usuarioRegistro = usuarioRegistro;
    }
    @Column(name = "FECHA_AUTORIZA", unique = false, nullable = false)
    public Date getFechaAutoriza() {
       return fechaAutoriza;
    }
    public void setFechaAutoriza(Date fechaAutoriza) {
       this.fechaAutoriza = fechaAutoriza;
    }
    @Column(name = "USUARIO_AUTORIZA")
    public String getUsuarioAutoriza() {
       return usuarioAutoriza;
    }
    public void setUsuarioAutoriza(String usuarioAutoriza) {
       this.usuarioAutoriza = usuarioAutoriza;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ESTATUS", nullable = false)
    public EstatusDivisas getEstatusDivisas() {
       return estatusDivisas;
    }
    public void setEstatusDivisas(EstatusDivisas estatusDivisas) {
       this.estatusDivisas = estatusDivisas;
    }
    @Column(name = "COMENTARIOS", unique = false, nullable = true)
    public String getComentarios() {
       return comentarios;
    }
    public void setComentarios(String comentarios) {
       this.comentarios = comentarios;
    }
    @OneToOne(mappedBy = "fileTransferDivisas")
    public FileTransferDetalleDivisas getFileTransferDetalleDivisa() {
       return fileTransferDetalleDivisa;
    }
    public void setFileTransferDetalleDivisa(FileTransferDetalleDivisas fileTransferDetalleDivisa) {
       this.fileTransferDetalleDivisa = fileTransferDetalleDivisa;
    }
    
}
