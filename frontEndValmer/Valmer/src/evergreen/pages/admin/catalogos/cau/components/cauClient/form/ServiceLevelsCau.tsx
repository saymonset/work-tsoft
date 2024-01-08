import React from "react";
import {DataCauLevels, generateUUID} from "../../../../../../../../utils";
import {
    BodyEditCauClient,
    Catalogo,
    ConsultaDataEditCauClient,
    NivelesServicioEditCau
} from "../../../../../../../../model";

interface ServiceLevelsCauProps {
    Data: ConsultaDataEditCauClient;
    handleChange: <T extends HTMLInputElement | HTMLSelectElement>(e: React.ChangeEvent<T>) => void;
}

export const ServiceLevelsCau = ({Data, handleChange}: ServiceLevelsCauProps) => {

    const getValue = (levelValue: string) => {
        if (Data && levelValue in Data) {
            return (Data as any)[levelValue];
        }
        return "2";
    };


    return (
        <>
            <div className="bg-cyan-700 h-8 w-full">
                <div className="flex items-center justify-center h-full">
                    <span className="text-white text-lg font-bold">NIVELES DE SERVICIO</span>
                </div>
            </div>

            <div className="mt-8 mb-14 grid grid-cols-4 gap-4">
                {DataCauLevels.map((level) => (
                    <div key={generateUUID()} className="relative z-0 my-3">
                        <select value={getValue(level.value)}
                                name={level.name}
                                className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                                onChange={handleChange}
                        >
                            <option value="0">...</option>
                            <option value="2">O</option>
                            <option value="3">T</option>
                            <option value="1">X</option>
                        </select>
                        <label
                            htmlFor={level.name}
                            className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                        >
                            {level.name}
                        </label>
                    </div>
                ))}
            </div>
        </>
    )
}