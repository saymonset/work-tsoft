import { useEffect, useState } from "react"
import { RespDataTableDef } from "../../../../../../model"
import { fetchDataGetRet } from "../../../../../../utils"

export const useDataTableDef = () => {
    const [dataTableDef, setDataTableDef] = useState<RespDataTableDef>({} as RespDataTableDef)
    const [loadingDataTable, setLoadingDataTable] = useState(false)

    useEffect(() => {
        if (!dataTableDef.body || dataTableDef.body.length === 0) {
            const getDataTable = async () => {
                setLoadingDataTable(true)
                const response = await fetchDataGetRet(
                    "/derivados/def/tabla-catalogo",
                    " al obtener data tabla catalogo",
                    {}
                )
                setDataTableDef(response)
                setLoadingDataTable(false)
            }
            getDataTable().then()
        }
    }, [dataTableDef]);

    return { dataTableDef, loadingDataTable };
}