import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {Precalculados, ShowPrecalc} from "../../../../../../model";
import React, {useState} from "react";
import Sweet from "sweetalert2";
import { fetchDataPostAct } from "../../../../../../utils";
import { updatePrecalculados, updateTriggerPrecalc } from "../../../../../../redux";

export const useInstrumentosPreCalc = () => {

    const tv = useSelector(
        (state: RootState<any, any, any>) => state.selectedTvAcc
    ) as unknown as string;

    const emisora = useSelector(
        (state: RootState<any, any, any>) => state.selectedEmisoraAcc
    ) as unknown as string;

    const serie = useSelector(
        (state: RootState<any, any, any>) => state.selectedSerieAcc
    ) as unknown as string;

    const precalculados = useSelector(
        (state: RootState<any, any, any>) => state.precalculados
    ) as unknown as Precalculados;

    const [showState, setShowState] = useState<ShowPrecalc>({
        vinculado: false,
        adr: false,
        suspendido: false,
        fijo: false,
        derCorp: false
    })

    const dispatch = useDispatch()

    const handleSweetAlert = (
        currentShowState: boolean,
        newCheckedValue: ShowPrecalc,
        setStateFunction: React.Dispatch<React.SetStateAction<ShowPrecalc>>,
        name: string
    ) => {
        if (!currentShowState) {
            Sweet.fire({
                icon: "warning",
                title: "¿Está seguro?",
                html: `¿Desea eliminar esta característica? <br/><br/> <b style="color: #0e7490;">${name.toUpperCase()}</b>`,
                showCancelButton: true,
                confirmButtonColor: '#166534',
                confirmButtonText: '<span style="color: white;">Aceptar</span>',
                cancelButtonText: `Cancelar`,
            }).then((r) => {
                if (r.isConfirmed) {
                    setStateFunction(newCheckedValue);
                    fetchDataPostAct(
                        "/acciones/instrumentos/elimina-precalc",
                        "Actualizado",
                        " al eliminar precalculado",
                        [],
                        {
                            s_tv: tv,
                            s_emisora: emisora,
                            s_serie: serie,
                            "value_check-precal": name
                        }
                    )

                    setTimeout(() => {
                        dispatch(updatePrecalculados({} as Precalculados))
                        dispatch(updateTriggerPrecalc(true))
                    }, 1000)
                }
            }).catch(() => {})
        } else {
            setStateFunction(newCheckedValue);
        }
    }

    const handleShowVinculado = (e: React.MouseEvent<HTMLButtonElement>, name: keyof ShowPrecalc) => {
        e.preventDefault();
        
        setShowState(prevState => {
            const updatedState: ShowPrecalc = Object.keys(prevState).reduce(
                (acc, key) => {
                    acc[key as keyof ShowPrecalc] = key === name;
                    return acc; 
                },
                {} as ShowPrecalc
            );

            return updatedState;
        })
    }

    const checkboxValue = (namePrecalc: any, name: string): boolean => {
        const existPrecalc = precalculados?.[namePrecalc]?.[0]

        if (existPrecalc) {
            return existPrecalc[name] == "checked"
        }

        return false
    }

    const deletedPrecalc = (e: React.ChangeEvent<HTMLInputElement>) => {
        const checked = e.target.checked
        const name = e.target.name

        const updatedState = {...showState, [name]: false}

        handleSweetAlert(
            checked,
            updatedState,
            setShowState,
            name
        )
    }

    return {
        showState,
        precalculados,
        handleShowVinculado,
        checkboxValue,
        deletedPrecalc,
        setShowState
    };
}