import React, {useEffect, useState} from "react";
import {Catalogo, DataActPrecio, RespQueryValuation} from "../../../../../../model";
import {fetchDataGetRet, fetchDataPostRetNoMsj, getCurrentDate} from "../../../../../../utils";

export const useValuacion = (mercado: string) => {
    const [isSelectDate, setIsSelectDate] = useState(false)
    const [isSelectOption, setIsSelectOption] = useState(false)
    const [isChecked, setIsChecked] = useState(false)
    const [loadingInst, setLoadingInst] = useState(false)
    const [loadingQuery, setLoadingQuery] = useState(false)
    const [isOpenModal, setIsOpenModal] = useState<boolean>(false)
    const [loadingConsPrecio, setLoadingConstPrecio] = useState<boolean>(false)
    const [triggerConsPrecio, setTriggerConsPrecio] = useState<boolean>(false)
    const [loadingActPrecio, setLoadingActPrecio] = useState<boolean>(false)
    const [triggerActPrecio, setTriggerActPrecio] = useState<boolean>(false)

    const [selectOption, setSelectOption] = useState("")
    const [selectDate, setSelectDate] = useState(getCurrentDate);
    const [catalog, setCatalog] = useState({} as Catalogo)
    const [query, setQuery] = useState({} as RespQueryValuation)
    const [checkedItems, setCheckedItems]
        = useState<Record<string, boolean>>({});
    const [selectedInstr, setSelectedInstr] = useState<string>("")
    const [dataActPrecio, setDataActPrecio] = useState<DataActPrecio>({} as DataActPrecio)


    useEffect(() => {
        const getTipoInstrumento = async () => {
            setLoadingInst(true);
            const response = await fetchDataGetRet(
                "/deuda/catalogo",
                "al obtener catalogo TIPO_INSTRUMENTO",
                {table: "TIPO_INSTRUMENTO", field: "S_DESCRIPCION"}
            )
            setCatalog(response.body)

            setLoadingInst(false);
        }

        getTipoInstrumento().then()
    }, []);

    useEffect(() => {
        const getPrecio = async () => {
            if (triggerConsPrecio) {
                setLoadingConstPrecio(true)
                const response = await fetchDataGetRet(
                    "/instrumentos/eurobonos/eurobonos-val/consulta-precio-euro",
                    " al consultar precio",
                    {s_instrumento: selectedInstr}
                )

                setDataActPrecio(response.body)
                setLoadingConstPrecio(false)
                setTriggerConsPrecio(false)
            }
        }

        getPrecio().then()
    }, [triggerConsPrecio])

    useEffect(() => {
        const getActPrecio = async () => {
            if (triggerActPrecio) {
                setLoadingActPrecio(true)
                const response = await fetchDataPostRetNoMsj(
                    "/instrumentos/eurobonos/eurobonos-val/actualiza-precio-euro",
                    "Actualizado",
                    "Precio actualizado correctamente",
                    " al actualizar precio",
                    [],
                    {n_precio: dataActPrecio.n_precio, s_instrumento: selectedInstr}
                )
                const title = response.title_log
                const  msg = response.msg_log
                const data = {...dataActPrecio, ["title_log"]: title, ["msg_log"]: msg}
                setDataActPrecio(data)
                setLoadingActPrecio(false)
                setTriggerActPrecio(false)
            }
        }

        getActPrecio().then()
    }, [triggerActPrecio])

    const handleChangeOption = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setIsSelectOption(false)
        setSelectOption(e.target.value)
    };

    const handleDate = (e: React.ChangeEvent<HTMLInputElement>) => {
        setIsSelectDate(false)
        setSelectDate(e.target.value)
    }

    const handleDel = (property: string, e: React.ChangeEvent<HTMLInputElement>) => {
        setIsChecked(false)
        setCheckedItems(prev => ({ ...prev, [property]: e.target.checked }));
    }

    const handleConsulta = async () => {
        setCheckedItems({});
        if (!validateInput()) {
            return;
        }

        setIsChecked(false)
        setLoadingQuery(true);

        const response = await fetchDataGetRet("/instrumentos/" + mercado + "/" + mercado + "-val/carga-val-corp",
            "Consulta",
            {
                n_tipo_instr: selectOption,
                d_fecha_val: selectDate
            });

        if(response)
        {
            setQuery(response);
        }
    
        setLoadingQuery(false);
    };

    const handleSave = async () => {
        if (!validateInput()) {
            return;
        }

        if (Object.keys(query).length > 0) {
            const checkedLength = Object.keys(checkedItems).length
            const checkedFalses = Object.values(checkedItems).every(value => value === false)
            if(checkedLength === 0 || checkedFalses)
            {
                setIsChecked(true);
                return;
            }
        }
        else
        {
            return
        }

        let checkedField = Object.keys(checkedItems).filter(key => checkedItems[key]).join(',');
        setIsChecked(false)
        setLoadingQuery(true)
        const response = await fetchDataGetRet(
            "/instrumentos/" + mercado + "/" + mercado + "-val/borra-instr-val",
            " error al borrar instr val",
            {checked_instr: checkedField, d_fecha_val: selectDate, n_tipo_instr: selectOption})

        if(response)
        {
            setCheckedItems({})
            setQuery({} as RespQueryValuation)
            setQuery(response)
        }

        setLoadingQuery(false)
    }

    const validateInput = () => {
        if (!selectDate) {
            setIsSelectDate(true);
            return false;
        }

        if (!selectOption || selectOption === "default") {
            setIsSelectOption(true);
            return false;
        }

        return true;
    }

    const handleCloseModal = () => {
        setIsOpenModal(false)
        setDataActPrecio({} as DataActPrecio)
    }

    const handleOpenModal = (instrumento: string) => {
        setSelectedInstr(instrumento)
        setIsOpenModal(true)
    }

    const handleChangePrecio = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target
        const data = {...dataActPrecio, [name]: value}
        setDataActPrecio(data)
    }

    const handleConsultarPrecio = () => {
        setTriggerConsPrecio(true)
    }

    const handleActualizarPrecio = () => {
        setTriggerActPrecio(true)
    }

    return {
        isChecked,
        isSelectDate,
        isSelectOption,
        selectDate,
        loadingQuery,
        loadingInst,
        checkedItems,
        catalog,
        query,
        isOpenModal,
        selectedInstr,
        dataActPrecio,
        loadingConsPrecio,
        loadingActPrecio,
        handleConsulta,
        handleSave,
        handleDate,
        handleDel,
        handleChangeOption,
        handleOpenModal,
        handleCloseModal,
        handleChangePrecio,
        handleConsultarPrecio,
        handleActualizarPrecio
    }
}