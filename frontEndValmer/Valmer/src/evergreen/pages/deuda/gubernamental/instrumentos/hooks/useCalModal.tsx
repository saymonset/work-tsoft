import React, { useState } from 'react'
import { CalProps } from '../../../../../../model'
import { getCurrentDate } from '../../../../../../utils'

export const useCalModal  = (c: CalProps) => {
    const [selectDate, setSelectDate] = useState(getCurrentDate)
    // const [nPrecioRend, setNPrecioRend]
    //     = useState(c.calPrecios?.body?.n_precio_rend || 0)
    // const [nPrecioMercadoSt, setNPrecioMercadoSt]
    //     = useState(c.calPrecios?.body?.n_precio_mercado_st || 0
    // )
    // const [nPrecioMercadoR, setNPrecioMercadoR]
    //     = useState(c.calPrecios?.body?.n_precio_mercado_r || 0)

    const [nPrecioRendResp, setNPrecioRendResp] = useState()
    const [nPrecioMercadoStResp, setNPrecioMercadoStResp] = useState()
    const [nPrecioMercadoRResp, setNPrecioMercadoRResp] = useState()

    const [loadingPrecioRend, setLoadingPrecioRend] = useState(false);
    const [loadingPrecioMercadoSt, setLoadingPrecioMercadoSt] = useState(false);
    const [loadingMercadoRResp, setLoadingMercadoRResp] = useState(false);

     
    const handleCalcularPrecioRendimiento = async () => {
        // await handleCalculo(
        //     "/instrumentos/eurobonos/calcular-precio-rendimiento",
        //     " al obtener calculo de precio rendimiento",
        //     'n_precio_rend',
        //     nPrecioRend,
        //     setNPrecioRendResp,
        //     setLoadingPrecioRend,
        //     'n_precio'
        // );
    };

    const handleCalcularSobretasa = async () => {
        // await handleCalculo(
        //     "/instrumentos/eurobonos/calcular-sobretasa",
        //     " al obtener calculo de sobretasa",
        //     'n_precio_mercado_st',
        //     nPrecioMercadoSt,
        //     setNPrecioMercadoStResp,
        //     setLoadingPrecioMercadoSt,
        //     'n_st'
        // );
    };

    const handleCalcularRendimiento = async () => {
        // await handleCalculo(
        //     "/instrumentos/eurobonos/calcular-rendimiento",
        //     " al obtener calculo de rendimiento",
        //     'n_precio_mercado_r',
        //     nPrecioMercadoR,
        //     setNPrecioMercadoRResp,
        //     setLoadingMercadoRResp,
        //     'n_rendimiento'
        // );
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
