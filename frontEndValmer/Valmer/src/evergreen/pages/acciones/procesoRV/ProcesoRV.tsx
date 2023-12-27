import { TitleDate } from "../../../../shared";
import React, { useState } from "react";
import { Panel } from "./Panel";
import { ProcesoHeader } from "./components";
import { HocRestricted } from "../../restrictedAccess";

export const ProcesoRV = () => {

    const title = "Proceso Renta Variable"
    const [currentDate, setCurrentDate] = useState("");
    const [isProcessSelected, setIsProcessSelected] = useState(false);

    const handleCurrentDateChange = (newDate: string) => {
        setCurrentDate(newDate);
    };

    const handleProcessSelectionChange = (isSelected: boolean) => {
        setIsProcessSelected(isSelected);
    };

    return (
        <HocRestricted title={title} view={title}>
            <TitleDate title={title} />

            <ProcesoHeader
                onCurrentDateChange={handleCurrentDateChange}
                onProcessSelectionChange={handleProcessSelectionChange}
            />

            <div className="mt-4">
                <Panel currentDate={currentDate} isProcessSelected={isProcessSelected} />
            </div>
        </HocRestricted>
    )
}