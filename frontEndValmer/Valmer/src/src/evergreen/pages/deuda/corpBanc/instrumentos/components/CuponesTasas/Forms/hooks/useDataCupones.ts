import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"
import { useSelector } from "react-redux"
import {Catalogo, FlujosCorp, IsFieldModifiedFvDdGobIns, IsFlujosCorp, ResponseConsultaDataCorp} from "../../../../../../../../../model"
import { useHandleDataCorp } from "../../../Instrumentos"
import { useEffect, useState } from "react"
import { fetchDataPostRetNoMsj, userEncoded } from "../../../../../../../../../utils"
import { updateConsultaDataCorp } from "../../../../../../../../../redux"

export const useDataCupones = () => {
    const catalog = useSelector((state: RootState<any, any, any>) => 
        state.catalog) as unknown as Catalogo[]

    const consultaData = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataCorp) as unknown as ResponseConsultaDataCorp

    const fieldRequiredCorp = useSelector((state: RootState<any, any, any>) =>
        state.fieldRequiredCorp) as unknown as IsFieldModifiedFvDdGobIns;

    const isFrecuenciaBimestral = useSelector((state: RootState<any, any, any>) =>
        state.isFrecuenciaBimestral) as unknown as boolean;

    const [editableRow, setEditableRow] = useState<IsFlujosCorp>({} as IsFlujosCorp)

    const [isAct, setIsAct] = useState<boolean>(false)

    const [dataCup, setDataCup] = useState<FlujosCorp[]>([])

    const [loadingActCup, setLoadingActCup] = useState<boolean>(false)
    const [triggerUpdatedCup, setTriggerUpdatedCup] = useState<boolean>(false)

    useEffect(() => {
        setDataCup(consultaData?.body?.h_flujos_hist)
    }, [])

    useEffect(() => {
        const updatedDataCup = async () => {
            if (triggerUpdatedCup) {
                setLoadingActCup(true)
                const response = await fetchDataPostRetNoMsj(
                    "/instrumentos/corporativos/actualiza-info-cupones",
                    "Actualizado",
                    "Registros actualizados correctamente",
                    " al actualizar cupones",
                    dataCup,
                    {
                        sEmisora: consultaData.body.s_emisora,
                        sSerie: consultaData.body.s_serie,
                        sTv: consultaData.body.s_tv,
                        sUser: userEncoded()
                    }
                )
                updateConsultaDataCorp(response)
                setEditableRow({})
                setIsAct(false)
                setLoadingActCup(false)
                setTriggerUpdatedCup(false)
            }
        }
        updatedDataCup().then()
    }, [triggerUpdatedCup])

    const {
        isFieldModified,
        inputValueEval,
        handleChange,
        handleCheckboxChange,
        handleNumericChange,
        checkboxValueEval,
    } = useHandleDataCorp()

    const getDateInfo = (data: string): string => {
        if (data == 'undef') {
            return ''
        } else {
            return data
        }
    }

    const enableEditing = (idRow: string) => {
        const value = {...editableRow, [idRow]: true}
        setEditableRow(value)

        if (!isAct) {
            setIsAct(true)
        }
    }

    const disableEditing = (idRow: string) => {
        const value = {...editableRow, [idRow]: false}
        setEditableRow(value)
        
        const allValues = Object.values(editableRow).filter(value => value === true)

        if (allValues.length === 1) {
            setIsAct(false)
        }
    }

    const handleChangeCup = (e: React.ChangeEvent<HTMLInputElement>, idRow: string) => {
        const {name, value} = e.target
        const updatedDataCup = [...dataCup]

        for (let i = 0; i < updatedDataCup.length; i++) {
            const flujos = updatedDataCup[i]
            if (flujos.property === idRow && flujos.data && name in flujos.data) {
                const newData = {...flujos.data, [name]: value}
                updatedDataCup[i] = {...flujos, data: newData}
                setDataCup(updatedDataCup)
            }
        }
    }

    const handleUpdatedCupon = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        setTriggerUpdatedCup(true)
    }

    return {
        isFrecuenciaBimestral,
        fieldRequiredCorp,
        catalog,
        consultaData,
        isFieldModified,
        editableRow,
        isAct,
        dataCup,
        loadingActCup,
        inputValueEval,
        handleChange,
        handleCheckboxChange,
        handleNumericChange,
        checkboxValueEval,
        getDateInfo,
        enableEditing,
        disableEditing,
        handleChangeCup,
        handleUpdatedCupon
    }
}