import { useEffect } from "react"
import {fetchDataGetRet, userEncoded} from "../../../../../utils"
import { useVarCau } from "./useVarCau"

export const useHandleDataModif = () => {
    
    const {
        loadingDataTable,
        triggerDataTable,
        dataTable,
        empresa,
        numRegistros,
        posicion,
        search,
        loadingInfoCau,
        triggerInfoCau,
        folio,
        infoCau,
        setLoadingDataTable,
        setTriggerDataTable,
        setDataTable,
        setEmpresa,
        setNumRegistros,
        setPosicion,
        setSearch,
        setLoadingInfoCau,
        setTriggerInfoCau,
        setFolio,
        setInfoCau
    } = useVarCau()

    useEffect(() => {
        const getDataCau = async () => {
            try {
                if(triggerDataTable && search === "") {
                    setLoadingDataTable(true)
                    const response = await fetchDataGetRet(
                        '/cau/modificados/consulta-info',
                        " al consultar datos cau modificados",
                        {
                            n_emp: empresa
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
                        '/cau/modificados/consulta-info-id',
                        " al consultar info cau abierto",
                        {
                            s_user: userEncoded(),
                            n_folio: folio
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
        numRegistros,
        posicion,
        search,
        infoCau,
        loadingInfoCau,
        setEmpresa,
        setTriggerDataTable,
        setNumRegistros,
        setPosicion,
        setSearch,
        setFolio,
        setTriggerInfoCau,
        setInfoCau
    }
}