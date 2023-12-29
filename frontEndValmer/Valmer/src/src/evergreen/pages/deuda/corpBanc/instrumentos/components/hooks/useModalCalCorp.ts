import React, {useEffect, useState} from "react";
import {BodyCalTasInt, CalTasasInt, CalTasasIntereses, CarCalTasas} from "../../../../../../../model";

export const useModalCalCorp = (tasasInt: CalTasasInt) => {

    const [characteristics, setCharacteristics]
        = useState<CarCalTasas | null>(null);
    const [interests, setInterests]
        = useState<CalTasasIntereses | null>(null);

    useEffect(() => {
        if(tasasInt?.body) {
            tasasInt.body.forEach((item: BodyCalTasInt) => {
                if (item.property === 'caracteristicas') {
                    setCharacteristics(item.data as CarCalTasas);
                } else if (item.property === 'intereses') {
                    setInterests(item.data as CalTasasIntereses);
                }
            });
        }
    }, [tasasInt]);

    const handleDateInit = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (characteristics) {
            setCharacteristics({
                ...characteristics,
                d_fecha_ini_cupon: e.target.value
            });
        }
    }

    const handleDateEnd = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (characteristics) {
            setCharacteristics({
                ...characteristics,
                d_fecha_fin_cupon: e.target.value
            });
        }
    }

    const handleValue = (e: React.ChangeEvent<HTMLInputElement>) => {
        //Empty Method
    }

    return {interests, characteristics, handleDateInit, handleDateEnd, handleValue}
}