import React, { useState } from "react";
import { PanelButtonNav } from "./PanelButtonNav";
import { CateRvForm, ActualizaBdForm, ProcesoRvForm, SalidasForm, ConsultasForm } from "../components";

interface PanelProps {
    currentDate: string;
    isProcessSelected: boolean;
}

export const Panel: React.FC<PanelProps> = ({ currentDate, isProcessSelected }) => {
    const [activePanel, setActivePanel] = useState("caterv");

    const handleButtonClickPanel = (buttonId: string) => {
        setActivePanel(buttonId === activePanel ? "" : buttonId);
    };

    return (
        <>
            <div className="panel">

                <div className="panel-header">
                    <PanelButtonNav
                        activePanel={activePanel}
                        handleButtonClickPanel={handleButtonClickPanel} />
                </div>

                <div className="panel-body">
                    {
                        activePanel === "caterv" && (
                            <CateRvForm currentDate={currentDate} isProcessSelected={isProcessSelected} />
                        )
                    }
                    {
                        activePanel === "actualizabd" && (
                            <ActualizaBdForm />
                        )
                    }

                    {activePanel === "procesorv" && (
                        <ProcesoRvForm />
                    )
                    }

                    {activePanel === "salidas" && (
                        <SalidasForm />
                    )
                    }

                    {activePanel === "consultas" && (
                        <ConsultasForm />
                    )
                    }
                </div>
            </div>
        </>
    );
};
