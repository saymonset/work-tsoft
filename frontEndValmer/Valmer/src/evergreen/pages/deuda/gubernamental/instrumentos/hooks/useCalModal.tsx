import React, { useState } from 'react'
import { CalProps } from '../../../../../../model'
import { getCurrentDate } from '../../../../../../utils'

export const useCalModal  = (c: CalProps) => {
    const [selectDate, setSelectDate] = useState(getCurrentDate)
     

    const [nPrecioRendResp, setNPrecioRendResp] = useState()
    const [nPrecioMercadoStResp, setNPrecioMercadoStResp] = useState()
    const [nPrecioMercadoRResp, setNPrecioMercadoRResp] = useState()

    const [loadingPrecioRend, setLoadingPrecioRend] = useState(false);
    const [loadingPrecioMercadoSt, setLoadingPrecioMercadoSt] = useState(false);
    const [loadingMercadoRResp, setLoadingMercadoRResp] = useState(false);

     
    const handleCalcularPrecioRendimiento = async () => {
    
    };

    const handleCalcularSobretasa = async () => {
    
    };

    const handleCalcularRendimiento = async () => {
     
    };

    const handleCalculo = async (
        endpoint: string,
        errorMessage: string,
        precioKey: string,
        precioValue: string | number,
        setResponse: React.Dispatch<React.SetStateAction<undefined>>,
        setLoading: React.Dispatch<React.SetStateAction<boolean>>,
        keyResponse: string
    ) => {
        let data: { [key: string]: any } = {
            dFechaVal: selectDate,
            sEmisora: c.selectedEmisora,
            sSerie: c.selectedSerie,
            sTv: c.selectedTv,
            [precioKey]: precioValue
        };

        setLoading(true);
        setLoading(false);
    };

    return{
        selectDate,
        nPrecioRendResp,
        nPrecioMercadoStResp,
        nPrecioMercadoRResp,
        loadingPrecioRend,
        loadingPrecioMercadoSt,
        loadingMercadoRResp,
        setSelectDate,
        handleCalcularPrecioRendimiento,
        handleCalcularSobretasa,
        handleCalcularRendimiento
    }
}
