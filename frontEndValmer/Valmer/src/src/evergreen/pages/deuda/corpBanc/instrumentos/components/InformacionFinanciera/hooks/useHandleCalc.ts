import { ChangeEvent, useEffect, useState } from "react"

export const useHandleCalc = () => {
    
    const [factorVal, setFactorVal] = useState<number>(0)
    const [derCobro, setDerCobro] = useState<number>(0)
    const [activoNC, setActivoNC] = useState<number>(0)
    const [efectivo, setEfectivo] = useState<number>(0)
    const [garantias, setGarantias] = useState<number>(0)
    const [montoCirc, setMontoCirc] = useState<number>(0)
    const [vnaAct, setVnaAct] = useState<number>(0)

    const [der35Porcent, setDer35Porcent] = useState<number>(0)
    const [recAnc, setRecAnc] = useState<number>(0)
    const [ac, setAc] = useState<number>(0)
    const [acRec, setAcRec] = useState<number>(0)
    const [recGarantias, setRecGarantias] = useState<number>(0)
    const [porcentVNA, setPorcentVNA] = useState<number>(0)
    const [precioNuevo, setPrecioNuevo] = useState<number>(0)

    useEffect(() => {
        const der35PorcentVal = factorVal * derCobro;
        setDer35Porcent(der35PorcentVal);

        const recAncVal = factorVal * activoNC;
        setRecAnc(recAncVal);

        const acVal = efectivo + der35PorcentVal;
        setAc(acVal);

        const acRecVal = recAncVal + acVal;
        setAcRec(acRecVal);

        const recGarantiasVal = factorVal * garantias;
        setRecGarantias(recGarantiasVal);

        const porcentVNAVal = acRecVal / montoCirc;
        setPorcentVNA(porcentVNAVal);

        const precioNuevoVal = vnaAct * porcentVNAVal;
        setPrecioNuevo(precioNuevoVal);
    }, [factorVal, derCobro, activoNC, efectivo, garantias, montoCirc, vnaAct]);

    const handleInputChange = (
        event: ChangeEvent<HTMLInputElement>,
        stateUpdater: React.Dispatch<React.SetStateAction<number>>,
    ) => {
        const value = parseFloat(event.target.value);
        stateUpdater(isNaN(value) ? 0 : value);
    }

    return {
        factorVal,
        derCobro,
        activoNC,
        efectivo,
        garantias,
        montoCirc,
        vnaAct,
        der35Porcent,
        recAnc,
        ac,
        acRec,
        recGarantias,
        porcentVNA,
        precioNuevo,
        setFactorVal,
        setDerCobro,
        setActivoNC,
        setEfectivo,
        setGarantias,
        setMontoCirc,
        setVnaAct,
        handleInputChange,
    }
}