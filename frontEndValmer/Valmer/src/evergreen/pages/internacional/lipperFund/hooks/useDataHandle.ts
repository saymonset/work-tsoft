import React, { useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import {ConsultaLipper, FormValuesLipper, ResponseDataTableHead} from "../../../../../model"
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import {updateConsultaLipper, updateDataTableHead, updateFormValuesLipper} from "../../../../../redux"
import {fetchDataGetSave, fetchDataPost, showAlert} from "../../../../../utils"
import {AxiosResponse} from "axios";
import {valmerApi} from "../../../../../api";

export const useDataHandle = () => {

    const formValuesLipper = useSelector((state: RootState<any, any, any>) => 
        state.formValuesLipper) as unknown as FormValuesLipper

    const dispatch = useDispatch()

    const formValuesKeys = Object.keys(formValuesLipper)

    const [isOpen, setIsOpen] = useState(false)
    const [selectedValues, setSelectedValues] = useState<FormValuesLipper>({} as FormValuesLipper)
    const [loadingSubmit, setLoadingSubmit] = useState(false)
    const [isTxtArea, setIsTxtArea] = useState(false)
    const [txtAreaLipper, setTxtAreaLipper] = useState("")
    const [selectedSalidas, setSelectedSalidas] = useState(false)
    const [selectedReuters, setSelectedReuters] = useState(false)
    const [valuesCheckbox, setValuesCheckbox] = useState({})
    const [loadingGeneraArchivo, setLoadingGeneraArchivo] = useState(false)

    const inputValueEval = (name: string, data: string): string => {
        if (formValuesKeys.includes(name)) 
        {
            return formValuesLipper[name]
        } 
        else 
        {
            return data
        }
    }

    const checkboxValueEval = (name: string, data: string) : boolean => {
        if(formValuesKeys.includes(name))
        {
            return formValuesLipper[name] === '1'
        } else {
            return data === '1'
        }
    }

    const handleValidaBBVAChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, checked} = e.target
        const updatedValues = {...formValuesLipper, [name]: checked ? '1' : '0'}
        dispatch(updateFormValuesLipper(updatedValues))
    }

    const handlePrecioMercadoChange = <T extends HTMLInputElement>(e: React.ChangeEvent<T>) => {
        const {name, value} = e.target
        const updatedValues = {...formValuesLipper, [name]: value}
        dispatch(updateFormValuesLipper(updatedValues))
    }

    const handleUpdateModal = (instrumento: string, valida_bbva: string, precio_mercado: string) => {
        const idValidaBBVA = "b_valida_bbva_" + instrumento
        const idPrecioMercado = "n_precio_mercado_" + instrumento
        const datos = {["s_instrumento"]: instrumento, 
                       ["n_precio_mercado"]: precio_mercado, 
                       ["b_valida_bbva"]: valida_bbva}
        
        if(formValuesKeys.includes(idValidaBBVA) && formValuesKeys.includes(idPrecioMercado)) 
        {
            const values = {...datos, 
                            ["n_precio_mercado"]: formValuesLipper[idPrecioMercado], 
                            ["b_valida_bbva"]: formValuesLipper[idValidaBBVA] }

            setSelectedValues(values)
        } 
        else if (formValuesKeys.includes(idValidaBBVA)) 
        {
            const values = {...datos, ["b_valida_bbva"]: formValuesLipper[idValidaBBVA]}

            setSelectedValues(values)
        } 
        else if (formValuesKeys.includes(idPrecioMercado)) 
        {
            const values = {...datos, ["n_precio_mercado"]: formValuesLipper[idPrecioMercado]}

            setSelectedValues(values)
        } 
        else if (valida_bbva === "")
        {
            const values = {...datos, ["b_valida_bbva"]: "0"}
            setSelectedValues(values)
        }
        else 
        {
            const values = {...datos}
            setSelectedValues(values)
        }
        setIsOpen(true)
    }

    const handleCloseModal = () => {
        setIsOpen(false)
    }

    const handleUpdateSubmit = async () => {
        setLoadingSubmit(true)
        await fetchDataPost("internacional/lipper-fund/update-instrumento", 
                            "al guardar los datos", 
                            selectedValues)
        setLoadingSubmit(false)
        setIsOpen(false)
    }

    const handleOpenModal = () => {
        setIsOpen(true)
    }

    const handleActualizar = async () => {
        if (txtAreaLipper.trim() != "")
        {
            setIsTxtArea(false)
            setLoadingSubmit(true)

            try {
                const parts = txtAreaLipper.split(/,|\s+/).map(part => part.trim());

                if (parts.length === 2 && !isNaN(parseFloat(parts[1]))) {
                    const object = {
                        s_instrumento: parts[0],
                        b_valida_bbva: 0,
                        n_precio_mercado: parseFloat(parts[1])
                    };

                    const response: AxiosResponse<any> = await valmerApi.post(
                        "/internacional/lipper-fund/update-instrumento",
                        object,
                        {});

                    await showAlert("success", "Guardado",
                        response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);

                    dispatch(updateConsultaLipper({} as ConsultaLipper))
                    dispatch(updateDataTableHead({} as ResponseDataTableHead))
                }
                else
                {
                    await showAlert('error', 'Error',
                        "Formato inválido. Asegúrate de ingresar un instrumento y un valor numérico.");
                }
            } catch (error: any) {
                if (error.message.includes('Network Error')) {
                    await showAlert('error', 'Error', 'No hay conexión con el servidor');
                } else {
                    await showAlert('error', `Error al actualizar los datos`, error.message);
                }
            }
            finally {
                setIsOpen(false)
                setLoadingSubmit(false)
            }
        }
        else
        {
            setIsTxtArea(true)
        }
    }

    const handleChangeCheckbox = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, checked} = e.target
        if (name === 'chk_solo_salidas')
        {
            setSelectedSalidas(!selectedSalidas)
            const values = {...valuesCheckbox, [name]: checked ? '1' : '0'}
            setValuesCheckbox(values)
        }
        else
        {
            setSelectedReuters(!selectedReuters)
            const values = {...valuesCheckbox, [name]: checked ? '1' : '0'}
            setValuesCheckbox(values)
        }
    }

    const handleGeneraArchivos = async () => {
        setLoadingGeneraArchivo(true)
        await fetchDataGetSave("/internacional/lipper-fund/genera-archivos-lipper", " al generar archivos", valuesCheckbox)
        setLoadingGeneraArchivo(false)
    }

    return {
        loadingSubmit,
        isOpen,
        selectedValues,
        txtAreaLipper,
        isTxtArea,
        selectedSalidas,
        selectedReuters,
        loadingGeneraArchivo,
        checkboxValueEval,
        inputValueEval,
        handleValidaBBVAChange,
        handlePrecioMercadoChange,
        handleUpdateModal,
        handleCloseModal,
        handleUpdateSubmit,
        handleOpenModal,
        setTxtAreaLipper,
        handleActualizar,
        handleChangeCheckbox,
        handleGeneraArchivos
    }
}