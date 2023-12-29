import { useRef, useState } from "react"
import { IsFieldModifiedFvDerivados, RequeridosDefDerivados, RespDerivadosDef } from "../../../../../model"
import { fetchDataPost, userEncoded, validateFormDerivadosFields } from "../../../../../utils"
import { useDispatch, useSelector } from "react-redux"
import { RootState } from "@reduxjs/toolkit/dist/query/core/apiState"

export const useHandleButtons = () => {

    const requeridosDefDer: RequeridosDefDerivados = {
        s_tv: useRef<HTMLInputElement | HTMLSelectElement | null>(null),
        s_emisora: useRef<HTMLInputElement | HTMLSelectElement |  null>(null),
        n_moneda: useRef<HTMLSelectElement | null>(null)
    }

    const fieldRequiredDefDerivados = useSelector(
        (state: RootState<any,any,any>) => state.fieldRequiredDerivados
    ) as unknown as IsFieldModifiedFvDerivados

    const requiredTv = useSelector(
        (state: RootState<any,any,any>) => state.requiredTvDer
    ) as unknown as boolean

    const requiredEmisora = useSelector(
        (state: RootState<any,any,any>) => state.requiredEmisoraDer
    ) as unknown as boolean

    const [consultaDataDerDef, setConsultaDataDerDef]
        = useState<RespDerivadosDef>({} as RespDerivadosDef)

    const [selectedTv, setSelectedTv] = useState<string>("")
    const [selectedEmisora, setSelectedEmisora] = useState<string>("")

    const [isNew, setIsNew] = useState<boolean>(false)

    const [loadingGuardaDer, setLoadingGuardaDer] = useState(false)
    const [loadingActCaract, setLoadingActCaract] = useState(false)

    const [isTablaCatalog, setIsTableCatalog] = useState(false)

    const [isButtonVenc, setIsButtonVenc] = useState(false)
    const [isModalVenc, setIsModalVenc] = useState(false)

    const [triggerTablaVenc, setTriggerTablaVenc] = useState(false)

    const dispatch = useDispatch()

    const handleNew = () => {
        setIsNew(true)
        setConsultaDataDerDef({} as RespDerivadosDef)
        setSelectedTv("")
        setSelectedEmisora("")
        setIsButtonVenc(false)
    }

    const handleGuardaDerivados = async () => {
        if (await validateFormDerivadosFields(consultaDataDerDef.body, dispatch, requeridosDefDer, false, false, undefined, fieldRequiredDefDerivados)) {
            const data = {
                ...consultaDataDerDef,
                body: {
                    ...consultaDataDerDef.body,
                    s_user: userEncoded()
                }
            }
            setLoadingGuardaDer(true)
            await fetchDataPost(
                "/derivados/def/actualiza-info",
                " error al guardar derivados def",
                data.body
            )
            setLoadingGuardaDer(false)
        }
    }

    const handleActualizaCaract = async () => {
        const data = {
            ...consultaDataDerDef,
            body: {
                ...consultaDataDerDef.body,
                s_user: userEncoded()
            }
        }
        setLoadingActCaract(true)
        await fetchDataPost(
            "/derivados/def/actualiza-info-tablas",
            " error al actualizar características",
            data.body
        )
        setLoadingActCaract(false)
    }

    const handleTablaCatalog = () => {
        setIsTableCatalog(!isTablaCatalog)
    }

    const handleVenc = () => {
        setTriggerTablaVenc(true)
        setIsModalVenc(true)
    }

    const closeModalVenc = () => {
        setIsModalVenc(false)
    }

    return {
        isNew,
        consultaDataDerDef,
        selectedTv,
        selectedEmisora,
        loadingGuardaDer,
        loadingActCaract,
        isTablaCatalog,
        isButtonVenc,
        isModalVenc,
        triggerTablaVenc,
        fieldRequiredDefDerivados,
        requeridosDefDer,
        requiredTv,
        requiredEmisora,
        setTriggerTablaVenc,
        setSelectedTv,
        setSelectedEmisora,
        setConsultaDataDerDef,
        setIsButtonVenc,
        handleNew,
        handleGuardaDerivados,
        handleActualizaCaract,
        handleTablaCatalog,
        handleVenc,
        closeModalVenc
    }
}