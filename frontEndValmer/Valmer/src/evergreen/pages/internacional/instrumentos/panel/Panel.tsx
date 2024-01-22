import React, {useState} from "react";
import {
    ActiveInstrumentos,
    ActiveCuponesTasas
    } from "../components";
import {PanelButtonNav} from "./PanelButtonNav";

export const Panel = ({requeridos}: any) => {

    const [activePanel, setActivePanel] = useState("instrumentos");
    const [activeForm, setActiveForm] = useState("instrumento");

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
                {activePanel === "instrumentos" && (
                    <ActiveInstrumentos
                        requeridos={requeridos}
                        activePanel={activePanel}
                    />
                )}

                {activePanel === "cuponesTasas" && (
                    <ActiveCuponesTasas
                        activePanel={activePanel}
                        activeForm={activeForm}
                        handleButtonClickForm={handleButtonClickForm}
                    />
                )}
            </div>
        </div>
    );
};
