import { useEffect } from "react"
import { useVarCau } from "./useVarCau"
import { fetchDataGetRet, userEncoded } from "../../../../../utils"

export const useHandleDataCerrados = () => {
    const {
        triggerDataTable,
        loadingDataTable,
        dataTable,
        search,
        empresa,
        servicio,
        area,
        numRegistros,
        posicion,
        infoCau,
        loadingInfoCau,
        triggerInfoCau,
        folio,
        setNumRegistros,
        setPosicion,
        setSearch,
        setFolio,
        setTriggerInfoCau,
        setLoadingDataTable,
        setDataTable,
        setTriggerDataTable,
        setEmpresa,
        setServicio,
        setArea,
        setLoadingInfoCau,
        setInfoCau
    } = useVarCau()

    useEffect(() => {
        const getDataCau = async () => {
            try {
                if(triggerDataTable && search === "") {
                    setLoadingDataTable(true)
                    const response = await fetchDataGetRet(
                        '/cau/cerrados/consulta-info',
                        " al consultar datos cau cerrados",
                        {
                            n_area_cat: area,
                            n_emp: empresa,
                            n_serv: servicio,
                            num_registros: 3000
                        }
                    )
                    setDataTable(response.body)
                    setLoadingDataTable(false)
                    setTriggerDataTable(false)
                }
            } catch (error) {
                setLoadingDataTable(false)
                setTriggerDataTable(false)
                console.log("Se produjo el siguiente error: " + error)
            }
        }
        getDataCau().then()
    })

    useEffect(() => {
        const getInfoCau = async () => {
            try {
                if(triggerInfoCau) {
                    setLoadingInfoCau(true)
                    const response = await fetchDataGetRet(
                        '/cau/cerrados/consulta-info-id',
                        " al consultar info cau abierto",
                        {
                            n_folio: folio,
                            s_user: userEncoded()
                        }
                    )
                    setInfoCau(response.body[0])
                    setLoadingInfoCau(false)
                    setTriggerInfoCau(false)
                }
            } catch (error) {
                setLoadingInfoCau(false)
                setTriggerInfoCau(false)
                console.log("Se produjo el siguiente error: " + error)
            }
        }
        getInfoCau().then()
    })

    return {
        loadingDataTable,
        dataTable,
        empresa,
        servicio,
        area,
        numRegistros,
        posicion,
        search,
        infoCau,
        loadingInfoCau,
        setNumRegistros,
        setPosicion,
        setSearch,
        setFolio,
        setTriggerInfoCau,
        setEmpresa,
        setServicio,
        setArea,
        setTriggerDataTable,
        setInfoCau
    }
}