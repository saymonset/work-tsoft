import {HeadCauClient} from "./header/HeadCauClient";
import {generateUUID} from "../../../../../../../utils";
import React from "react";
import {DataForm} from "./form/DataForm";
import {ServiceLevelsCau} from "./form/ServiceLevelsCau";


interface Registro {
    id: string;
    [key: string]: string;
}

interface Column {
    name: string;
    type: string;
}

interface Props {
    nameCatalog: string;
    setShowTable: (show: boolean) => void;
    setSelectedOption: (show: string) => void;
    columns: Column[];
}

export const EditCauClient : React.FC<Props> = ({nameCatalog, setShowTable,
                                                    setSelectedOption, columns}) => {

    const registros: { id: number | string }[] =
        Array.from({ length: 50 }, (_, index) => ({
            id: index + 1,
            ...columns.reduce<Partial<Registro>>(
                (acc, column) => {
                    acc[column.name] = `Dato ${index + 1}`;
                    return acc;
                },
                {}
            ),
        }));

    const goBack = () => {
        setShowTable(true)
        setSelectedOption('')
    }

    return (
        <>
            <div className="form-cols-2 mb-12">
                <div className="flex justify-start pr-2">
                    <button className="btn" onClick={() => goBack()}>
                        <i className="mr-2 fa-solid fa-arrow-left"></i>
                        <span>Regresar</span>
                    </button>
                </div>

                <div className="flex justify-end pr-2">
                    <button className="btn">
                        <span>Grabar</span>
                    </button>
                    <button className="btn">
                        <span>Nuevo</span>
                    </button>
                    <button className="btn">
                        <span>Borrar</span>
                    </button>
                </div>
            </div>

            <HeadCauClient/>

            <div className="flex mb-8">
                <div className="flex-1 mt-8 ml-8 text-center text-cyan-700 text-2xl font-semibold">
                    Catalogo {nameCatalog}
                </div>
            </div>

            <div className="flex flex-col">
                <div className="overflow-x-auto">
                    <div style={{ maxHeight: "500px", overflowY: "scroll" }}>
                        <table className="min-w-full">
                            <thead>
                            <tr>
                                {columns.map((column) => (
                                    <th key={generateUUID()} className="bg-cyan-700 text-white px-4 py-2">
                                        {column.name}
                                    </th>
                                ))}
                            </tr>
                            </thead>
                            <tbody>
                            {registros.map((registro) => (
                                <tr key={registro.id}>
                                    {columns.map((column) => (
                                        <td key={generateUUID()} className="border px-4 py-2">
                                            {(registro as Registro)[column.name]}
                                        </td>
                                    ))}
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                <DataForm/>

                <ServiceLevelsCau/>
            </div>
        </>
    )
}