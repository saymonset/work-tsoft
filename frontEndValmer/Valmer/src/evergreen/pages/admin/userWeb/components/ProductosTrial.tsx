import React, { useEffect, useState } from "react"
import { MenuProducts } from "./MenuProducts"
import { DataHistoricoTrial, FvContratos, ItemProduct } from "../../../../../model"
import { fetchDataGetRet, fetchDataPostRet, showAlert } from "../../../../../utils"
import { BarLoader } from "react-spinners"
import { ButtonContent } from "../../../../../shared"

interface ProductosTrialProps {
    n_nombre: number
    triggerProduct: boolean
    setTriggerProduct: React.Dispatch<React.SetStateAction<boolean>>
    setDataTable: React.Dispatch<React.SetStateAction<DataHistoricoTrial[]>>
    setTriggerDataTable: React.Dispatch<React.SetStateAction<boolean>>
}

export const ProductosTrial: React.FC<ProductosTrialProps> = ({n_nombre,
                                                                  triggerProduct,
                                                                  setTriggerProduct,
                                                                  setDataTable,
                                                                  setTriggerDataTable}) => {

    const [loading, setLoading] = useState<boolean>(false)
    const [products, setProducts] = useState<ItemProduct>({} as ItemProduct)
    const [loadingPermisos, setLoadingPermisos] = useState<boolean>(false)
    const [triggerPermisos, setTriggerPermisos] = useState<boolean>(false)
    const [permisos, setPermisos] = useState<FvContratos>({} as FvContratos)

    useEffect(() => {
        const getProduct = async () => {
            if (triggerProduct) {
                setLoading(true)
                const response = await fetchDataGetRet(
                    "/admin-user-web/productos-trial",
                    " al consultar productos trial",
                    {n_nombre}
                )
                setProducts(response.body)
                setLoading(false)
                setTriggerProduct(false)
            }
        }
        getProduct().catch(() => {})
    }, [triggerProduct])

    useEffect(() => {
        const getPermisos = async () => {
            if (triggerPermisos) {
                setLoadingPermisos(true)
                const response = await fetchDataPostRet(
                    "/admin-user-web/genera-permisos-trial",
                    " al generar permisos trial",
                    permisos,
                    {n_nombre}
                )
                setDataTable(response.body)
                setLoadingPermisos(false)
                setTriggerPermisos(false)
                setTriggerDataTable(true)
            }
        }
        getPermisos().catch(() => {})
    }, [triggerPermisos])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target
        const permisosValue = {...permisos, [name]: value}
        setPermisos(permisosValue)
    }

    const handleGeneraPermisos = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        if (permisos.d_fecha_trial) { 
            setTriggerPermisos(true)
        } else {
            showAlert('info', "Campo Requerido", "Debe completar Fecha Fin Trial")
        }
    }

    return (
        <div className="form-cols-1 row-span-1">
            <span className="form-title mb-3">Productos Trial</span>
            {loading ? (
                <BarLoader className="mt-4" color="#059669" width={300} />
            ):(
                <>
                    <div className="form-cols-2">
                        <div className="form-date form-date-my">
                            <input 
                                type="date"
                                id="d_fecha_trial"
                                name="d_fecha_trial"
                                value={permisos.d_fecha_trial}
                                onChange={handleChange}
                            />
                            <label htmlFor="d_fecha_trial">Fecha Fin Trial</label>
                        </div>
                        <div className="flex items-center ml-2.5">
                            <button
                                className="btn w-9/12"
                                onClick={handleGeneraPermisos}
                            >
                                <ButtonContent name="Genera Permisos" loading={loadingPermisos} />
                            </button>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <MenuProducts 
                            data={products}
                            permisos={permisos}
                            setPermisos={setPermisos}
                        />
                    </div>
                </>
            )}
        </div>
    )
}