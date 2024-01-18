import React from "react";
import { RevisarForm } from "./components";
import {useInsumos} from "./hooks";
import {ButtonContent, TextAreaLog} from "../../../../../../shared";
import {MoonLoader} from "react-spinners";

export const InsumosForm = () => {

    const {
        showRevisarForm,
        isShowLogBox,
        log,
        loading,
        loadingLogCsv,
        loadingLogBox,
        loadingRevisar1,
        isCheckboxChecked,
        handleChange,
        handleLogCsv,
        handleInsumos,
        handleRevisar1
    } = useInsumos()

    const renderLogContent = () => {
        if (log.length > 0) {
            return (
                <div className="flex flex-col items-center">
                    <div className="w-1/8 mb-4">
                        <button
                            className="w-44 bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3"
                            onClick={handleLogCsv}>
                            <ButtonContent name="Obtener Log CSV" loading={loadingLogCsv}/>
                        </button>
                    </div>
                    <TextAreaLog log={log}/>
                </div>
            );
        }
        else
        {
            return <p>No hay registros de log disponibles.</p>;
        }
    };

    return (
        <table className="tableEuro mt-12">
            <tbody>
            <tr>
                <td valign="top">
                    <table>
                        <tbody>
                        <tr>
                            <td height="35" align="left">
                                <div className="form-check">
                                    <label htmlFor="cbx_carga_insumos">
                                        1
                                    </label>
                                    <input
                                        type="checkbox"
                                        name="cbx_carga_insumos"
                                        checked={isCheckboxChecked.cbx_carga_insumos}
                                        onClick={() => handleChange("cbx_carga_insumos", isCheckboxChecked.cbx_carga_insumos)}
                                    />
                                    <button
                                        className="w-44 bg-cyan-700 hover:bg-green-700
                                            text-white py-1 rounded-md px-3 mx-1"
                                        onClick={handleInsumos}
                                    >
                                        <ButtonContent name="Carga Insumos" loading={loading}/>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td height="35" align="left">
                                <div className="form-check">
                                    <label className="!ml-0" htmlFor="cbx_Revisar1">
                                        2
                                    </label>
                                    <input
                                        type="checkbox"
                                        name="cbx_Revisar1"
                                        checked={isCheckboxChecked.cbx_Revisar1}
                                        onClick={() => handleChange("cbx_Revisar1", isCheckboxChecked.cbx_Revisar1)}
                                    />
                                    <button
                                        className="w-44 bg-cyan-700 hover:bg-green-700
                                            text-white py-1 rounded-md px-3 mx-1"
                                        onClick={handleRevisar1}
                                    >
                                        <ButtonContent name="Revisar 1" loading={loadingRevisar1}/>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
                <td>
                    {isShowLogBox && (
                        <div className="flex justify-center items-center ">
                            {loadingLogBox ? (
                                <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80}/>
                            ) : renderLogContent()}
                        </div>
                    )}
                </td>
                <td align="center"></td>
            </tr>
            <tr>
                <td>{showRevisarForm && <RevisarForm/>}</td>
            </tr>
            </tbody>
        </table>
    )
}