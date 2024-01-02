import React from "react";
import {ButtonContent, Modal} from "../../../../../../shared";
import {useProceso} from "./hooks";
import {MoonLoader} from "react-spinners";
import { ModalProceso } from "./ModalProceso";
import {generateUUID} from "../../../../../../utils";

export const ProcesoForm = () => {

    const {
        log,
        logModal,
        loadingProd,
        loadingLogCsv,
        loadingLogBox,
        loadingLogBoxModal,
        isModalOpenProcesos,
        isShowLogBoxProc,
        isShowLogBoxProcModal,
        isOpenEdit,
        isOutputs,
        loadingPrices,
        loadingCalculateRates,
        loadingCalculatePrices,
        loadingOutputs,
        tableOutputs,
        isCheckboxChecked,
        handleOpenLogModal,
        handleOpenCarga,
        handleCloseEdit,
        handleChange,
        handleLogCsv,
        handleUpdatePrices,
        handleCalculateRates,
        handleCalculatePrices,
        handleOutputs,
        handleOpenModal,
        handleCloseModal,
    } = useProceso()

    const renderLogContent = () => {
        if (log.length > 0) {
            return (
                <div className="mt-8 flex flex-col items-center w-full">
                    <div className="w-1/8 mb-4">
                        <button
                            className="w-44 bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3"
                            onClick={handleLogCsv}>
                            <ButtonContent name="Obtener Log CSV" loading={loadingLogCsv}/>
                        </button>
                    </div>
                    <div
                        className="bg-gray-900 text-green-500 p-2 w-3/4 resize-y overflow-auto max-h-[30rem]"
                        dangerouslySetInnerHTML={{ __html: log }}
                        style={{ minHeight: '30rem' }}
                    />

                    {isOutputs && (<div className="mt-12 w-1/8">
                        <button
                            className="w-44 bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3"
                            onClick={handleOpenCarga}>
                            <ButtonContent name="Produccion" loading={loadingProd}/>
                        </button>
                    </div>)}
                </div>
            );

        } else {
            return <p>No hay registros de log disponibles.</p>;
        }
    };

    const renderLogContentModal = () => {
        if (logModal.length > 0) {
            return (
                <div className="mt-8 flex flex-col items-center w-full">
                    <div
                        className="bg-gray-900 text-green-500 p-2 w-full resize-y overflow-auto"
                        dangerouslySetInnerHTML={{ __html: logModal }}
                        style={{ minHeight: '20rem', maxHeight: '30rem', height: '30rem' }}
                    />
                </div>
            );
        }
        else
        {
            return <p>No hay registros de log disponibles.</p>;
        }
    };


    return (
        <>
        <table className="tableEuro mt-12">
            <tbody>
            <tr>
                <td valign="top">
                    <table>
                        <tbody>
                        <tr>
                            <td height="35" align="left">
                                <div className="form-check">
                                    <span>
                                        9
                                    </span>
                                    <input
                                        type="checkbox"
                                        name="cbx_Act_Precios"
                                        checked={isCheckboxChecked.cbx_Act_Precios}
                                        onClick={() => handleChange("cbx_Act_Precios",
                                            isCheckboxChecked.cbx_Act_Precios)}
                                    />
                                    <button
                                        className="w-44 bg-cyan-700 hover:bg-green-700
                                            text-white py-1 rounded-md px-3 mx-1"
                                        onClick={handleUpdatePrices}>
                                        <ButtonContent name="Act Precios" loading={loadingPrices}/>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td height="35" align="left">
                                <div className="form-check">
                                    <span className="!ml-0">
                                        9.1
                                    </span>
                                    <input
                                        type="checkbox"
                                        name="cbx_Calcular_Tasas"
                                        checked={isCheckboxChecked.cbx_Calcular_Tasas}
                                        onClick={() => handleChange("cbx_Calcular_Tasas",
                                            isCheckboxChecked.cbx_Calcular_Tasas)}
                                    />
                                    <button
                                        className="w-44 bg-cyan-700 hover:bg-green-700
                                            text-white py-1 rounded-md px-3 mx-1"
                                            onClick={handleCalculateRates}>
                                            <ButtonContent name="Calcular Tasas" loading={loadingCalculateRates}/>
                                        </button>
                                        <button 
                                            className={`ml-2 bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3`}
                                            onClick={handleOpenModal}
                                            >
                                            <i className="fa fa-calculator"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td height="35" align="left">
                                    <div className="form-check">
                                        <span>
                                            10
                                        </span>
                                        <input
                                            type="checkbox"
                                            name="cbx_Calcula_Precios"
                                            checked={isCheckboxChecked.cbx_Calcula_Precios}
                                            onClick={() => handleChange("cbx_Calcula_Precios",
                                                isCheckboxChecked.cbx_Calcula_Precios)}
                                        />
                                        <button
                                            className="w-44 bg-cyan-700 hover:bg-green-700
                                            text-white py-1 rounded-md px-3 mx-1"
                                        onClick={handleCalculatePrices}>
                                        <ButtonContent name="Calcula Precios" loading={loadingCalculatePrices}/>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td height="35" align="left">
                                <div className="form-check">
                                    <span>
                                        11
                                    </span>
                                    <input
                                        type="checkbox"
                                        name="cbx_Salidas"
                                        checked={isCheckboxChecked.cbx_Salidas}
                                        onClick={()=> handleChange("cbx_Salidas", isCheckboxChecked.cbx_Salidas)}
                                    />
                                    <button
                                        className="w-44 bg-cyan-700 hover:bg-green-700
                                            text-white py-1 rounded-md px-3 mx-1"
                                        onClick={handleOutputs}>
                                        <ButtonContent name="Salidas" loading={loadingOutputs}/>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
                <td>
                    {isShowLogBoxProc && (
                        <div className="flex mb-44 mr-44 justify-center items-center ">
                            {loadingLogBox ? (
                                <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                            ) : renderLogContent()}
                        </div>
                    )}
                </td>
                <td align="center">
                </td>
            </tr>
            </tbody>
        </table>

        <ModalProceso isModalOpenProcesos= {isModalOpenProcesos} close={handleCloseModal}/>
            <Modal isOpen={isOpenEdit} onClose={handleCloseEdit} title="Valida SALIDAS_EURO">

                {isShowLogBoxProcModal ? (
                    renderLogContentModal()
                ) : (
                    <>
                        <table className="table w-full">
                            <thead className="thead">
                            <tr>
                                <th rowSpan={tableOutputs?.length ? tableOutputs.length + 1 : 1}>Curva</th>
                                <th>Archivo</th>
                                <th>Fecha</th>
                                <th>Hora</th>
                            </tr>
                            </thead>
                            <tbody className="tbody">
                            {tableOutputs?.map((salida, index) => (
                                <tr key={generateUUID()}>
                                    {index === 0 && <td rowSpan={tableOutputs.length || 1}>SALIDAS_EURO</td>}
                                    <td>{salida.archivo}</td>
                                    <td>{salida.fecha}</td>
                                    <td>{salida.hora}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>

                        <div className="flex justify-center mt-14">
                            <button
                                className="w-44 bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3"
                                onClick={handleOpenLogModal}>
                                <ButtonContent name="Produccion" loading={loadingLogBoxModal}/>
                            </button>
                        </div>
                    </>
                )}

            </Modal>
        </>
    )
}