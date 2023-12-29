import React, {useEffect, useState} from "react";
import {
    ActiveInstrumentos,
    ActiveCuponesTasas,
    ActiveAmortizaciones,
    ActiveInformacionFinanciera} from "../components";
import {PanelButtonNav} from "./PanelButtonNav";
import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";

export const Panel = ({requeridos}: any) => {

    const isSelectPanelCorp = useSelector((state: RootState<any, any, any>) =>
        state.isSelectPanelCorp) as unknown as boolean;

    const [activePanel, setActivePanel] = useState("instrumentos");
    const [activeForm, setActiveForm] = useState("instrumento");

    useEffect(() => {
        if(isSelectPanelCorp) {setActivePanel("cuponesTasas");}
    }, [isSelectPanelCorp]);

    const handleButtonClickPanel = (buttonId: string) => {
        setActivePanel(buttonId === activePanel ? "" : buttonId);
    };

    const handleButtonClickForm = (buttonId: string) => {
        setActiveForm(buttonId === activeForm ? "" : buttonId);
    };

    return (
        <div className="panel">
            <div className="panel-header">
                <PanelButtonNav
                    activePanel={activePanel}
                    handleButtonClickPanel={handleButtonClickPanel} />
            </div>

            <div className="panel-body">
                {
                    activePanel === "instrumentos" && (
                        <ActiveInstrumentos
                            requeridos={requeridos}
                            activePanel={activePanel}
                            activeForm={activeForm}
                            handleButtonClickForm={handleButtonClickForm}/>
                    )}

                {
                    activePanel === "cuponesTasas" && (
                        <ActiveCuponesTasas
                            requeridos={requeridos}
                            activePanel={activePanel}
                            activeForm={activeForm}
                            handleButtonClickForm={handleButtonClickForm}/>
                    )}

                {activePanel === "amortizaciones" && (
                    <ActiveAmortizaciones
                        activePanel={activePanel}
                    />
                )}

                {activePanel === "infoFinanciera" && (
                    <ActiveInformacionFinanciera
                        activePanel={activePanel}
                    />
                )}
            </div>
        </div>
    );
};
