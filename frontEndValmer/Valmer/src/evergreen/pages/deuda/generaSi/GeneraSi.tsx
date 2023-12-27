import {ButtonContent, TitleDate} from "../../../../shared"
import React from "react";
import {useGeneraSI} from "./hooks";

export const GeneraSi = () => {

    const {
        todos,
        gubernamental,
        corpBanc,
        eurobonos,
        loading,
        loadingGenSalidasCorp,
        loadingGenSalidasEuro,
        loadingEnviaMov,
        setGubernamental,
        setCorpBanc,
        setEurobonos,
        setLoadingGenSalidasCorp,
        setLoadingGenSalidasEuro,
        setLoadingEnviaMov,
        handleTodosChange,
        handleCheckboxChange,
        handleCalculo,
        handleSI} = useGeneraSI()

    const buttonIcon = (loadingOption: boolean) => {
        if(loadingOption)
        {
            return <></>
        }
        else
        {
            return <i className="fa fa-file"/>
        }
    }

    return (
        <>
            <TitleDate title="SI" />

            <div className="flex justify-center pr-2 w-full">
                <div className="card w-96">
                    <div className="body">
                        <div className="grid grid-cols-2">
                            <div className="row-span-1">
                                <button className="btn-icon"
                                        onClick={handleCalculo}>
                                    <div className="font-bold">
                                        {loading ? <i></i> : <i className="fa fa-calculator"></i>}
                                        <ButtonContent name={" Calculo Interes"} loading={loading}/>
                                    </div>
                                </button>
                            </div>
                            <div className="text-center px-4">
                                <span className="font-bold">Familia</span>
                                <div className="form-cols-1">
                                    <div className="form-check">
                                        <input
                                            type="checkbox"
                                            checked={todos}
                                            onChange={handleTodosChange}
                                            name="chk_i_todos"/>
                                        <label htmlFor="chk_i_todos">Todos</label>
                                    </div>
                                    <div className="form-check">
                                        <input
                                            type="checkbox"
                                            checked={gubernamental}
                                            onChange={() => handleCheckboxChange(gubernamental, setGubernamental)}
                                            name="chk_i_gubernamental"/>
                                        <label htmlFor="chk_i_gubernamental">Gubernamental</label>
                                    </div>
                                    <div className="form-check">
                                        <input
                                            type="checkbox"
                                            checked={corpBanc}
                                            onChange={() => handleCheckboxChange(corpBanc, setCorpBanc)}
                                            name="chk_i_corp-banc"/>
                                        <label htmlFor="chk_i_corp-banc">Corp-Banc</label>
                                    </div>
                                    <div className="form-check">
                                        <input
                                            type="checkbox"
                                            checked={eurobonos}
                                            onChange={() => handleCheckboxChange(eurobonos, setEurobonos)}
                                            name="chk_i_eurobonos"/>
                                        <label htmlFor="chk_i_eurobonos">Eurobonos</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="line"/>
                        <div className="grid grid-cols-1 gap-4">
                            <button className="btn-icon" onClick={() => handleSI(3, setLoadingGenSalidasCorp)}>
                                <div className="font-bold">
                                    {buttonIcon(loadingGenSalidasCorp)}
                                    <ButtonContent name={" Genera Salidas CORP|GUB|BANC"} loading={loadingGenSalidasCorp}/>
                                </div>
                            </button>
                            <button className="btn-icon" onClick={() => handleSI(4, setLoadingGenSalidasEuro)}>
                                <div className="font-bold">
                                    {buttonIcon(loadingGenSalidasEuro)}
                                    <ButtonContent name={" Genera Salidas EURO"} loading={loadingGenSalidasEuro}/>
                                </div>
                            </button>
                            <button className="btn-icon" onClick={() => handleSI(5, setLoadingEnviaMov)}>
                                <div className="font-bold">
                                    {buttonIcon(loadingEnviaMov)}
                                    <ButtonContent name={" Envía Resumen Movimientos"} loading={loadingEnviaMov}/>
                                </div>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}