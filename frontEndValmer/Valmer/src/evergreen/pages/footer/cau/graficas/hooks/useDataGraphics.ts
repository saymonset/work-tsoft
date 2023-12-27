import { useEffect, useState } from "react"
import { AreaGraphics, HomeGraphics, InstGraphics } from "../../../../../../model"
import { fetchDataGetRet, getCurrentDate, showAlert } from "../../../../../../utils"
import dayjs from "dayjs"

export const useDataGraphics = () => {

    const [loading, setLoading] = useState<boolean>(false)
    const [data, setData] = useState<HomeGraphics[]>([])
    const [loadingDataInst, setLoadingDataInst] = useState<boolean>(false)
    const [instGraphics, setInstGraphics] = useState<InstGraphics[]>([])
    const [loadingCliente, setLoadingCliente] = useState<boolean>(false)
    const [loadingServicio, setLoadingServicio] = useState<boolean>(false)
    const [loadingMesArea, setLoadingMesArea] = useState<boolean>(false)
    const [triggerMesArea, setTriggerMesArea] = useState<boolean>(true)
    const [dataMesArea, setDataMesArea] = useState<AreaGraphics[]>([])
    const [dataServicio, setDataServicio] = useState<AreaGraphics[]>([])
    const [triggerServicio, setTriggerServicio] = useState<boolean>(true)
    const [dataCliente, setDataCliente] = useState<AreaGraphics[]>([])
    const [triggerCliente, setTriggerCliente] = useState<boolean>(true)
    const [fechaInicio, setFechaInicio] = useState<string>(dayjs().subtract(7,'day').format('YYYY-MM-DD'))
    const [fechaFin, setFechaFin] = useState<string>(getCurrentDate())

    useEffect(() => {
        const getHomeGraphics = async () => {
            if (!data || data.length === 0) {
                setLoading(true)
                try {
                    const response = await fetchDataGetRet(
                        "/cau/graficas/grafica-home",
                        " al consultar datos para Grafica Home",
                        {}
                    )
                    setData(response.body)
                    setLoading(false)
                } catch (error) {
                    await showAlert("error", "Se produjo el siguiente error", "Error: " + error)
                }
            }
        }
        getHomeGraphics().then()
    }, [data])

    useEffect(() => {
        const getInstGraphics = async () => {
            if (!instGraphics || instGraphics.length === 0) {
                setLoadingDataInst(true)
                try {
                    const response = await fetchDataGetRet(
                        "/cau/graficas/grafica-inst",
                        " al consultar datos para Grafica Insttución",
                        {}
                    )
                    setInstGraphics(response.body)
                    setLoadingDataInst(false)
                } catch (error) {
                    setLoadingDataInst(false)
                    await showAlert("error", "Se produjo el siguiente error", "Error: " + error)
                }
            }
        }
        getInstGraphics().then()
    }, [])

    useEffect(() => {
        const getMesAreaGraphics = async () => {
            if (triggerMesArea) {
                setLoadingMesArea(true)
                try {
                    const response = await fetchDataGetRet(
                        "/cau/graficas/grafica-cau-mes-area",
                        " al consultar datos para Grafica Mes Area",
                        {}
                    )
                    setDataMesArea(response.body)
                    setLoadingMesArea(false)
                    setTriggerMesArea(false)
                } catch (error) {
                    setLoadingDataInst(false)
                    setTriggerMesArea(false)
                    await showAlert("error", "Se produjo el siguiente error", "Error: " + error)
                }
            }
        }
        getMesAreaGraphics().then()
    }, [])

    useEffect(() => {
        const getServicioGraphics = async () => {
            if (triggerServicio) {
                setLoadingServicio(true)
                try {
                    const response = await fetchDataGetRet(
                        "/cau/graficas/grafica-servicio",
                        " al consultar datos para Grafica Servicios",
                        {
                            d_fecha_fin: fechaFin,
                            d_fecha_inicio: fechaInicio
                        }
                    )
                    setDataServicio(response.body)
                    setLoadingServicio(false)
                    setTriggerServicio(false)
                } catch (error) {
                    setLoadingServicio(false)
                    setTriggerServicio(false)
                    await showAlert("error", "Se produjo el siguiente error", "Error: " + error)
                }
            }
        }
        getServicioGraphics().then()
    }, [triggerServicio])

    useEffect(() => {
        const getClienteGraphics = async () => {
            if (triggerCliente) {
                setLoadingCliente(true)
                try {
                    const response = await fetchDataGetRet(
                        "/cau/graficas/grafica-cliente",
                        " al consultar datos para Grafica Cliente",
                        {
                            d_fecha_fin: fechaFin,
                            d_fecha_inicio: fechaInicio
                        }
                    )
                    setDataCliente(response.body)
                    setLoadingCliente(false)
                    setTriggerCliente(false)
                } catch (error) {
                    setLoadingCliente(false)
                    setTriggerCliente(false)
                    await showAlert("error", "Se produjo el siguiente error", "Error: " + error)
                }
            }
        }
        getClienteGraphics().then()
    }, [triggerCliente])

    return {
        data,
        loading,
        loadingDataInst,
        instGraphics,
        loadingCliente,
        loadingServicio,
        loadingMesArea,
        fechaInicio,
        fechaFin,
        dataMesArea,
        dataServicio,
        dataCliente,
        setFechaInicio,
        setFechaFin,
        setTriggerCliente,
        setTriggerServicio
    }
}