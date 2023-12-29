import React from "react";
import {useCambBonRefTable} from "./hooks";
import {ResponseBonosRef} from "../../../../../../../model";
import {generateUUID} from "../../../../../../../utils";
import {ButtonContent} from "../../../../../../../shared";
export const CambBonRefTable = ({data, date}: {data: ResponseBonosRef, date: string}) => {

    const {
        dataNew,
        loading,
        shouldDisableCheckbox,
        handleSave,
        handleChangeChk} = useCambBonRefTable({data, date})

    if (!dataNew?.body) {
        return <p className='mt-16 text-center'>No hay datos para mostrar.</p>
    }

    return (
        <>
            <table className='mt-8 table'>
                <thead className='thead'>
                <tr>
                    <th>Instrumento</th>
                    <th>BONO REF.AYER</th>
                    <th>BONO REF.HOY</th>
                    <th>DURACIÓN MAC.INSTR</th>
                    <th>DURACIÓN MAC. BONO REF (AYER)</th>
                    <th>DURACIÓN MAC BONO REF (HOY)</th>
                    <th>SOBRETASA (AYER)</th>
                    <th>ACTUALIZAR</th>
                </tr>
                </thead>
                <tbody className='tbody'>
                {Array.isArray(dataNew.body) && dataNew.body.length > 0 ? (
                        dataNew.body.map((item, index) => {
                            const isDisabled = shouldDisableCheckbox(item);

                            return (
                                <tr
                                    key={generateUUID()}
                                    className='tr'
                                >
                                    <td>{item.property}</td>
                                    <td>{item.data.BONO_AYER}</td>
                                    <td>{item.data.BONO_HOY}</td>
                                    <td>{item.data.DUR_INSTR}</td>
                                    <td>{item.data.DUR_BONO_AYER}</td>
                                    <td>{item.data.DUR_BONO_HOY}</td>
                                    <td>{item.data.SOBRETASA}</td>
                                    <td>
                                        <input
                                            type="checkbox"
                                            className="w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded focus:ring-cyan-700
                                    dark:focus:ring-cyan-700 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700
                                    dark:border-gray-600"
                                            name="b_rnv"
                                            checked={item.data.CAMBIO === "1"}
                                            onChange={(e) => handleChangeChk(e, item, index)}
                                            disabled={isDisabled}
                                        />
                                    </td>
                                </tr>
                            );
                        })
                    ) : (
                        <tr>
                            <td colSpan={8} className="text-center">No se encontraron registros</td>
                        </tr>
                    )
                }
                </tbody>
            </table>
            <button className="mt-16 btn" onClick={handleSave}>
                <ButtonContent name={"Guardar"} loading={loading}/>
            </button>
        </>
    )
}