import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import { useEffect, useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import { Catalogo, ResponseDataCorp } from "../../../../../model"
import { valmerApi } from "../../../../../api"
import { showAlert } from "../../../../../utils"
import { updateCatalogCau } from "../../../../../redux/Cau/actions"

export const useGetCatalogs = () => {

    const catalog = useSelector(
        (state: RootState <any, any, any>) => state.catalogCau
    ) as unknown as Catalogo[]

    const [loadingCatalog, setLoadingCatalog] = useState<boolean>(false)

    const dispatch = useDispatch()

    useEffect(() => {
        if (!catalog || catalog.length === 0) {
            setLoadingCatalog(true)
            valmerApi
                .get<ResponseDataCorp>('/cau/catalogos')
                .then((response) => {
                    dispatch(updateCatalogCau(response.data.body.catalogos))
                    setLoadingCatalog(false)
                })
                .catch(async (error) => {
                    setLoadingCatalog(false);
                    if (error.message.includes('Network Error')) {
                      await showAlert('error', 'Error', 'No hay conexión con el servidor')
                    } else {
                      await showAlert('error', 'Error', error.message);
                    }
                })
        }
    }, [catalog])

    return {
        catalog,
        loadingCatalog
    }
}

