import React from "react";
import {MoonLoader} from "react-spinners";
import {generateUUID} from "../../../../../../utils";
import {CauTableProps} from "../../../../../../model";

export const CauTable = ({
    status,
    loadingOpened,
    loadingClosed,
    loadingModifies,
    tableOpened,
    handleClickFolio,
    handleClickFolioCerrados
}: CauTableProps) => {

    const isCerrados = status === "Cerrados";
    const isModificacion = status === "Modificación";

    return (
        <div>
            {loadingOpened || loadingClosed || loadingModifies ? (
                <div className="mt-8 flex justify-center items-center h-full">
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60}/>
                </div>
            ) : (
                <div className="flex justify-center mt-3">
                    <div className="w-2/3 border border-slate-400 overflow-y-auto" style={{maxHeight: '300px'}}>
                        <table className="table w-full">
                            <thead className="thead">
                                <tr>
                                    <th>FOLIO</th>
                                    {!isModificacion &&
                                    <>                               
                                    <th>AREA</th>
                                    <th>SERVICIO</th>
                                    </> 
                                    }
                                    {!isModificacion &&
                                    <th>EMPRESA</th>
                                    }
                                    {!isModificacion &&
                                    <th>STATUS</th>
                                    }
                                    {!isCerrados ?
                                    <>
                                    <th>SECTOR</th>
                                    {isModificacion &&
                                    <>
                                    <th>SERVICIO</th>
                                    <th>EMPRESA</th>
                                    <th>STATUS</th>
                                    </>
                                    }
                                    <th>FECHA</th>
                                    </>
                                    :
                                    <>
                                    <th>APERTURA</th>
                                    <th>CIERRRE</th>
                                    </>
                                    }
                                </tr>
                            </thead>
                            <tbody className="tbody">
                                {tableOpened && tableOpened.length > 0 &&
                                    tableOpened.map((data) => {
                                        return (
                                            <tr key={generateUUID()} className="tr">
                                                <td>
                                                    <button
                                                        className="text-blue-700"
                                                        onClick={(e) => {
                                                            e.preventDefault();
                                                            if (!isCerrados) {
                                                                handleClickFolio(data.n_folio).then();
                                                            } else {
                                                                handleClickFolioCerrados(data.n_folio).then();
                                                            }
                                                        }}
                                                    >
                                                        {data.n_folio}
                                                    </button>
                                                </td>
                                                <td>{data.nombre}</td>
                                                <td>{data.s_servicio}</td>
                                                <td>{data.compania}</td>
                                                <td>{data.s_status}</td>
                                                {!isCerrados ?
                                                    <>
                                                            {!isModificacion &&
                                                            <td>-</td>
                                                        }
                                                        <td>{data.d_fecha}</td>
                                                    </>
                                                    :
                                                    <>
                                                        <td>{data.d_fecha}</td>
                                                        <td>{data.d_fecha_cierre}</td>
                                                    </>
                                                }
                                            </tr>
                                        )
                                    })
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            )}
        </div>
    )
}