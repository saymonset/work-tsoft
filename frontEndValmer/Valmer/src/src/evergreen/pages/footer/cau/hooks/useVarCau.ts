import { useState } from "react"
import { CatUser, DataTableCau, InfoCauId, PassExpInfo } from "../../../../../model"

export const useVarCau = () => {
    const [loadingDataTable, setLoadingDataTable] = useState<boolean>(false)
    const [triggerDataTable, setTriggerDataTable] = useState<boolean>(true)
    const [dataTable, setDataTable] = useState<DataTableCau>({} as DataTableCau)
    const [empresa, setEmpresa] = useState<string>("")
    const [area, setArea] = useState<string>("")
    const [servicio, setServicio] = useState<string>("")
    const [numRegistros, setNumRegistros] = useState<number>(25)
    const [posicion, setPosicion] = useState<number>(0)
    const [search, setSearch] = useState<string>("")
    const [loadingInfoCau, setLoadingInfoCau] = useState<boolean>(false)
    const [triggerInfoCau, setTriggerInfoCau] = useState<boolean>(false)
    const [folio, setFolio] = useState<number>(0)
    const [infoCau, setInfoCau] = useState<InfoCauId>({} as InfoCauId)
    const [validaUser, setValidaUser] = useState<boolean>(false)
    const [triggerValidaUser, setTriggerValidaUser] = useState<boolean>(true)
    const [loadingValidaUser, setLoadingValidaUser] = useState<boolean>(false)
    const [dataPassExp, setDataPassExp] = useState<PassExpInfo[]>([])
    const [triggerPassExp, setTriggerPassExp] = useState<boolean>(false)
    const [loadingPassExp, setLoadingPassExp] = useState<boolean>(false)
    const [loadingCatUsr, setLoadingCatUsr] = useState<boolean>(false)
    const [catUsr, setCatUsr] = useState<CatUser[]>([])
    const [triggerCatUsr, setTriggerCatUsr] = useState<boolean>(false)

    return {
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
        validaUser,
        triggerValidaUser,
        loadingValidaUser,
        dataPassExp,
        triggerPassExp,
        loadingPassExp,
        area,
        servicio,
        catUsr,
        loadingCatUsr,
        triggerCatUsr,
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
        setInfoCau,
        setValidaUser,
        setTriggerValidaUser,
        setLoadingValidaUser,
        setDataPassExp,
        setTriggerPassExp,
        setLoadingPassExp,
        setArea,
        setServicio,
        setTriggerCatUsr,
        setLoadingCatUsr,
        setCatUsr
    }
}