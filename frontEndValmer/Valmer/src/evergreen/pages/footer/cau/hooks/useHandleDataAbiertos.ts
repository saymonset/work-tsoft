import { useEffect } from "react"
import { fetchDataGetRet, userEncoded } from "../../../../../utils"
import { useVarCau } from "./useVarCau"

export const useHandleDataAbiertos = () => {

    const {
        loadingDataTable, triggerDataTable, dataTable,
        empresa, numRegistros, posicion,
        search, loadingInfoCau, triggerInfoCau,
        folio, infoCau, validaUser,
        triggerValidaUser, loadingValidaUser, dataPassExp,
        triggerPassExp, loadingPassExp, catUsr,
        loadingCatUsr, triggerCatUsr,
        setLoadingDataTable, setTriggerDataTable,
        setDataTable, setEmpresa, setNumRegistros,
        setPosicion, setSearch, setLoadingInfoCau,
        setTriggerInfoCau, setFolio,
        setInfoCau, setValidaUser,
        setTriggerValidaUser, setLoadingValidaUser,
        setDataPassExp, setTriggerPassExp,
        setLoadingPassExp, setTriggerCatUsr,
        setLoadingCatUsr, setCatUsr
    } = useVarCau()

    useEffect(() => {
        const getDataCau = async () => {
            try {
                if(triggerDataTable && search === "") {
                    setLoadingDataTable(true)
                    const response = await fetchDataGetRet(
                        '/cau/abiertos/consulta-info',
                        " al consultar datos cau abiertos",
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
                        '/cau/abiertos/consulta-info-id',
                        " al consultar info cau abierto",
                        {
                            s_user: userEncoded(),
                            n_folio: folio
                        }
                    )
                    setInfoCau(response.body[0])
                    setLoadingInfoCau(false)
                    setTriggerInfoCau(false)
                    setTriggerCatUsr(true)
                }
            } catch (error) {
                setLoadingInfoCau(false)
                setTriggerInfoCau(false)
                console.log("Se produjo el siguiente error: " + error)
            }
        }
        getInfoCau().then()
    })

    useEffect(() => {
        const getValidaUser = async () => {
            try {
                if(triggerValidaUser) {
                    setLoadingValidaUser(true)
                    const response = await fetchDataGetRet(
                        '/cau/pass-expires/valida-usuario',
                        " al validar usuario",
                        {
                            s_user: userEncoded()
                        }
                    )
                    setValidaUser(response.body)
                    setLoadingValidaUser(false)
                    setTriggerValidaUser(false)
                    if (response.body) {
                        setTriggerPassExp(true)
                    }
                }
            } catch (error) {
                setLoadingValidaUser(false)
                setTriggerValidaUser(false)
                console.log("Se produjo el siguiente error: " + error)
            }
        }

        if(!dataPassExp || dataPassExp.length === 0) {
            getValidaUser().then()
        }
    })

    useEffect(() => {
        const getPassExp = async () => {
            try {
                if(triggerPassExp) {
                    setLoadingPassExp(true)
                    const response = await fetchDataGetRet(
                        '/cau/pass-expires/consulta-info',
                        " al obtener datos de pass expires",
                        {}
                    )
                    setTriggerPassExp(false)
                    setLoadingPassExp(false)
                    setDataPassExp(response.body)
                }
            } catch (error) {
                setLoadingPassExp(false)
                setTriggerPassExp(false)
                console.log("Se produjo el siguiente error: " + error)
            }
        }
        getPassExp().then()
    })

    useEffect(() => {
        const getCatUsr = async () => {
            try {
                if (triggerCatUsr) {
                    setLoadingCatUsr(true)
                    const response = await fetchDataGetRet(
                        "/cau/abiertos/consulta-usuarios-servicios",
                        " al consultar catálogo de usuarios",
                        {n_servicio_aux: infoCau.n_serv_aux}
                    )
                    setCatUsr(response.body)
                    setLoadingCatUsr(false)
                    setTriggerCatUsr(false)
                }
            } catch (error) {
                setLoadingCatUsr(false)
                setTriggerCatUsr(false)
                console.log("Se produjo el siguiente error: " + error)
            }
        }
        getCatUsr().then()
    }, [catUsr, triggerCatUsr])

    return {
        loadingDataTable,
        dataTable,
        empresa,
        numRegistros,
        posicion,
        search,
        infoCau,
        loadingInfoCau,
        validaUser,
        loadingValidaUser,
        dataPassExp,
        loadingPassExp,
        catUsr,
        loadingCatUsr,
        setEmpresa,
        setTriggerDataTable,
        setNumRegistros,
        setPosicion,
        setSearch,
        setFolio,
        setTriggerInfoCau,
        setTriggerPassExp,
        setInfoCau
    }
}