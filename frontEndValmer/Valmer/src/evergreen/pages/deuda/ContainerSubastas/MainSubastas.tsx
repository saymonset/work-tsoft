import {ButtonContent, TitleDate} from "../../../../shared";
import React from "react";
import { HocRestricted } from "../../restrictedAccess";
import {useSubastas} from "./hooks";
import {generateUUID} from "../../../../utils";
import {SubastasProps} from "../../../../model";

const LazySubastasForm = React.lazy(() =>
    import('./subastas').then(module => ({ default: module.SubastasForm}))
);
const LazySubastasFlotantesForm = React.lazy(() =>
    import('./subastasFlotantes').then(module => ({ default: module.SubastasFlotantesForm}))
);
export const MainSubastas = (sub: SubastasProps) => {

    const {
        loadingConsulta,
        loadingActualizar,
        handleSubmit,
        handleConsulta,
        handleErase} = useSubastas(sub)

    const buttonConfig = [
        {name: "Actualizar", loading: loadingActualizar, handler: handleSubmit},
        {name: "Consultar", loading: loadingConsulta, handler: handleConsulta},
        {name: "Limpiar", loading: false, handler: handleErase}
    ];

    return (
        <HocRestricted title={sub.title} view={sub.title}>
            <TitleDate title={sub.title}/>
            <div className="flex justify-end pr-2">
                {buttonConfig.map(({name, loading, handler}) =>
                    <button
                        key={generateUUID()}
                        className="bg-cyan-700 hover:bg-green-700 text-white py-0 rounded-md px-3 mx-1"
                        onClick={handler}>
                        <ButtonContent name={name} loading={loading}></ButtonContent>
                    </button>
                )}
            </div>
            {sub.title === "Subastas" ? <LazySubastasForm isSub={sub.isSub}/> : <LazySubastasFlotantesForm isSub={sub.isSub}/>}
        </HocRestricted>
    )
}