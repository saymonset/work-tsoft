import {TitleDate, LogBoxText} from "../../../../shared";
import {Panel} from "./Panel";
import {HocRestricted} from "../../restrictedAccess";
import React, { useEffect } from "react";
import {ProcesoHeader} from "./components";
import { updateStateCheckbox } from "../../../../redux";
import { stateCheckbox } from "../../../../model";
import { useDispatch } from "react-redux";

export const Eurobonos = () => {

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(updateStateCheckbox({} as stateCheckbox))
    })

    const title : string = "Proceso Eurobonos";

    return (
        <HocRestricted title={title} view={title}>
            <TitleDate title={title}/>

            <ProcesoHeader/>

            <div className="mt-4">
                <Panel/>
            </div>

            <div className="mt-8 mb-8">
                <LogBoxText logName={"log_proceso_eurobonos"}/>
            </div>
        </HocRestricted>
    )
}