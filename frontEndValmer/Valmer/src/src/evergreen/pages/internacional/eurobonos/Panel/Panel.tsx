import React, {useState} from "react";
import {PanelButtonNav} from "./PanelButtonNav";
import {InsumosForm, ContribuidoresForm, ProcesoForm, AdmonCarterasForm} from "../components";

export const Panel = () => {

    const [activePanel, setActivePanel] = useState("insumos");

    const handleButtonClickPanel = (buttonId: string) => {
        setActivePanel(buttonId === activePanel ? "" : buttonId);
    };

    return (
        <div className="panel">

            <div className="panel-header">
                <PanelButtonNav
                    activePanel={activePanel}
                    handleButtonClickPanel={handleButtonClickPanel}/>
            </div>

            <div className="panel-body">
                {
                    activePanel === "insumos" && (
                        <InsumosForm/>
                    )
                }
                {
                    activePanel === "contribuidores" && (
                        <ContribuidoresForm/>
                    )
                }

                {activePanel === "proceso" && (
                    <ProcesoForm/>
                )
                }

                {activePanel === "admonCarteras" && (
                    <AdmonCarterasForm/>
                )
                }
            </div>
        </div>
    );
};
