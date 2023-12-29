import React from "react";
import {PanelButtonNav} from "./PanelButtonNav";
import {useActivePanelForm} from "./hooks";
import {ActiveInstrumentos, ActiveCuponesTasas} from "../components";

export const Panel = ({requeridos}: any) => {

   const {activePanel, activeForm, handleButtonClickPanel, handleButtonClickForm} = useActivePanelForm()

    return (
        <div className="panel">
            <div className="panel-header">
                <PanelButtonNav
                    activePanel={activePanel}
                    handleButtonClickPanel={handleButtonClickPanel} />
            </div>

            <div className="panel-body">
                {activePanel === "instrumentos" &&(
                    <ActiveInstrumentos
                        requeridos={requeridos}
                        activePanel={activePanel}
                        activeForm={activeForm}
                        handleButtonClickForm={handleButtonClickForm}/>
                )}

                {activePanel === "cuponesTasas" &&(
                    <ActiveCuponesTasas
                        requeridos={requeridos}
                        activePanel={activePanel}
                        activeForm={activeForm}
                        handleButtonClickForm={handleButtonClickForm}/>
                )}
            </div>

        </div>
    );
};
