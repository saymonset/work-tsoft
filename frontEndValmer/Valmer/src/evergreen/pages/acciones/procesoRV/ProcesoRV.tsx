import { TitleDate } from "../../../../shared";
import React, { useState } from "react";
import { Panel } from "./Panel";
import { ProcesoHeader } from "./components";
import { HocRestricted } from "../../restrictedAccess";
import { getCurrentDate } from "../../../../utils";

export const ProcesoRV = () => {

    const title = "Proceso Renta Variable"
    const [currentDate, setCurrentDate] = useState(getCurrentDate);
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
                currentDate={currentDate}
                onCurrentDateChange={handleCurrentDateChange}
                onProcessSelectionChange={handleProcessSelectionChange}
            />

            <div className="mt-4">
                <Panel currentDate={currentDate} isProcessSelected={isProcessSelected} />
            </div>
        </HocRestricted>
    )
}