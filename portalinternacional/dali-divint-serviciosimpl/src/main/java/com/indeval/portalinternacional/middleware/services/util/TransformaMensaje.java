/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.util;

import java.io.IOException;
import java.io.StringReader;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.ValidationException;
import biz.c24.io.api.presentation.DefaultSource;
import biz.c24.io.api.presentation.JavaClassSink;

import com.indeval.protocolofinanciero.api.modelo.mensajeindeval.MensajeIndeval;
import com.indeval.protocolofinanciero.api.modelo.mensajeindeval.MensajeIndevalElement;
import com.indeval.protocolofinanciero.api.transformacion.entradaapi.TransformacionEntradaAPITransform;
import com.indeval.protocolofinanciero.api.vo.AcuseVO;
import com.indeval.protocolofinanciero.api.vo.ConfirmacionLiquidacionVO;
import com.indeval.protocolofinanciero.api.vo.DerechosPatrimonialesAmortizacionVO;
import com.indeval.protocolofinanciero.api.vo.DerechosPatrimonialesInteresesVO;
import com.indeval.protocolofinanciero.api.vo.ErrorVO;
import com.indeval.protocolofinanciero.api.vo.EventoVO;
import com.indeval.protocolofinanciero.api.vo.ListaPosicionVO;
import com.indeval.protocolofinanciero.api.vo.StatusVO;
import com.indeval.protocolofinanciero.api.vo.SufVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;

/**
 * @author javiles
 *
 */
public class TransformaMensaje {
    
    public Object generaVO(TextMessage tm) {
        ComplexDataObject complexDataObject;
        try {
            complexDataObject = (ComplexDataObject) transformMessage(tm.getText());
            JavaClassSink snk = new JavaClassSink();
            if (complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.acuse.AcuseVO) {
                snk.setRootClass(AcuseVO.class);
                return (AcuseVO) snk.convertObject(complexDataObject);
            }
            else if (complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.status.StatusVO) {
                snk.setRootClass(StatusVO.class);
                return (StatusVO) snk.convertObject(complexDataObject);
            }
            else if (complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.error.ErrorVO) {
                snk.setRootClass(ErrorVO.class);
                return (ErrorVO) snk.convertObject(complexDataObject);
            }
            else if (complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.confliqui.ConfirmacionLiquidacionVO) {
                snk.setRootClass(ConfirmacionLiquidacionVO.class);
                return (ConfirmacionLiquidacionVO) snk.convertObject(complexDataObject);
            }
            else if (complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.evento.EventoVO) {
                snk.setRootClass(EventoVO.class);
                return (EventoVO) snk.convertObject(complexDataObject);
            }
            else if (complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.suf.SufVO) {
                snk.setRootClass(SufVO.class);
                return (SufVO) snk.convertObject(complexDataObject);
            }
            else if (complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.consultaposicion.ListaPosicionVO) {
                snk.setRootClass(ListaPosicionVO.class);
                return (ListaPosicionVO) snk.convertObject(complexDataObject);
            }
            else if (complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.traspasocontrapago.TraspasoContraPagoVO) {
                snk.setRootClass(TraspasoContraPagoVO.class);
                return (TraspasoContraPagoVO) snk.convertObject(complexDataObject);
            }
            else if (complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.derechosamortizacionvo.DerechosPatrimonialesAmortizacionVO) {
                snk.setRootClass(DerechosPatrimonialesAmortizacionVO.class);
                return (DerechosPatrimonialesAmortizacionVO) snk.convertObject(complexDataObject);
            }
            else if (complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.derechosinteresvo.DerechosPatrimonialesInteresesVO) {
                snk.setRootClass(DerechosPatrimonialesInteresesVO.class);
                return (DerechosPatrimonialesInteresesVO) snk.convertObject(complexDataObject);
            }
            else if(complexDataObject instanceof com.indeval.protocolofinanciero.api.modelo.traspasoefectivo.TraspasoEfectivoVO) {
                snk.setRootClass(TraspasoEfectivoVO.class);
                return (TraspasoEfectivoVO) snk.convertObject(complexDataObject);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ValidationException e) {
            e.printStackTrace();
        }
        catch (JMSException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Transforma los mensajes recibidos de formato ISO15022 a objeto
     * ComplexDataObject.
     * 
     * @param isoMessage
     * @return returns complexDataObject
     * @throws IOException
     * @throws ValidationException
     */
    /*private ComplexDataObject transformMessage(String isoMessage) throws IOException,
            ValidationException {
        ComplexDataObject complexDataObject = null;
        Element element = MensajeIndevalElement.getInstance();
        isoMessage = isoMessage.replaceAll("\\r\\n", "\r").replaceAll("\\n", "\r").replaceAll(
                "\\r", "\r\n"); // asegura que al final de la linea exista \r\n
        isoMessage = isoMessage.replaceAll(" \\r\\n", "\r\n"); // quita los
                                                                // espacios en
                                                                // blanco al
                                                                // final de la
                                                                // linea
        isoMessage = isoMessage.replaceAll("\\r\\n\\r\\n", "\r\n"); // quita las
                                                                    // lineas en
                                                                    // blanco
        isoMessage = isoMessage.replaceAll("\\t", ""); // quita los tabs al
                                                        // final de la linea
        MensajeIndeval mensajeIndeval = (MensajeIndeval) new DefaultSource(new StringReader(
                isoMessage)).readObject(element);
        TransformacionEntradaAPITransform model = new TransformacionEntradaAPITransform();
        ComplexDataObject[][] output = model.transform(new ComplexDataObject[][] {
            {
                mensajeIndeval } });
        for (int i = 0; i < output.length; i++) {
            if (output[i].length > 0) {
                complexDataObject = output[i][0];
                break;
            }
        }
        return complexDataObject;
    }*/
    
    
    private ComplexDataObject transformMessage(String isoMessage)
    throws IOException, ValidationException
    {
        ComplexDataObject complexDataObject = null;
        biz.c24.io.api.data.Element element = MensajeIndevalElement.getInstance();
        isoMessage = isoMessage.replaceAll("\\r\\n", "\r").replaceAll("\\n", "\r").replaceAll("\\r", "\r\n");
        isoMessage = isoMessage.replaceAll(" \\r\\n", "\r\n");
        isoMessage = isoMessage.replaceAll("\\r\\n\\r\\n", "\r\n");
        isoMessage = isoMessage.replaceAll("\\t", "");
        MensajeIndeval mensajeIndeval = (MensajeIndeval)(new DefaultSource(new StringReader(isoMessage))).readObject(element);
        TransformacionEntradaAPITransform model = new TransformacionEntradaAPITransform();
        ComplexDataObject output[][] = model.transform(new ComplexDataObject[][] {
            new ComplexDataObject[] {
                mensajeIndeval
            }
        });
        int i = 0;
        do
        {
            if(i >= output.length)
                break;
            if(output[i].length > 0)
            {
                complexDataObject = output[i][0];
                break;
            }
            i++;
        } while(true);
        return complexDataObject;
    }
    
}
