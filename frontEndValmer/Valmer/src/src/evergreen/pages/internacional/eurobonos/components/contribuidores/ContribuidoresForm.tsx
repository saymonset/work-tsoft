import React from "react";
import {ButtonContent} from "../../../../../../shared";
import {ChargeClientWallet, Revisar2Form} from "./components";
import {useContribuidores} from "./hooks";
import {MoonLoader} from "react-spinners";

export const ContribuidoresForm = () => {

    const {
        showFileContributor,
        isShowLogBoxCont,
        log,
        loading,
        loadingLogCsv,
        loadingLogBox,
        loadingRevisar2,
        showContribuidoresForm,
        flechaArriba,
        isCheckboxChecked,
        handleLogCsv,
        handleGetWallet,
        handleRevisar2,
        handleShowFileContributor,
        handleChange
    } = useContribuidores()

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
                        className="bg-gray-900 text-green-500 p-2 w-3/4 resize-y overflow-auto"
                        dangerouslySetInnerHTML={{ __html: log }}
                        style={{ minHeight: '30rem' }}
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
        <table className="tableEuro mt-12">
            <tbody>
            <tr>
                <td valign="top">
                    <table>
                        <tbody>
                        <tr>
                            <td height="35" align="left">
                                <div className="form-check">
                                    <label htmlFor="cbx_obtener_carteras">
                                        5
                                    </label>
                                    <input
                                        type="checkbox"
                                        name="cbx_obtener_carteras"
                                        checked={isCheckboxChecked.cbx_obtener_carteras}
                                        onClick={()=>handleChange("cbx_obtener_carteras", isCheckboxChecked.cbx_obtener_carteras)}
                                    />
                                    <button
                                        className="w-44 bg-cyan-700 hover:bg-green-700
                                            text-white py-1 rounded-md px-3 mx-1"
                                        onClick={handleGetWallet}>
                                        <ButtonContent name="Obtener Carteras" loading={loading}/>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td height="35" align="left">
                                <div className="form-check">
                                    <label htmlFor="cbx_revisar2">
                                        6
                                    </label>
                                    <input
                                        type="checkbox"
                                        name="cbx_revisar2"
                                        checked={isCheckboxChecked.cbx_revisar2}
                                        onClick={()=>handleChange("cbx_revisar2", isCheckboxChecked.cbx_revisar2)}
                                    />
                                    <button
                                        className="w-44 bg-cyan-700 hover:bg-green-700
                                            text-white py-1 rounded-md px-3 mx-1"
                                        onClick={handleRevisar2}>
                                        <ButtonContent name="Revisar 2" loading={loadingRevisar2}/>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td height="35" align="left">
                                <div className="form-check">
                                    <button
                                        className="w-60 bg-cyan-700 hover:bg-green-700
                                        text-white py-1 rounded-md px-3 mx-1 flex items-center justify-center flex-row"
                                        onClick={handleShowFileContributor}
                                    >
                                        Cargar Cartera Clientes
                                        <svg className={`ml-1 h-4 w-4 fill-current 
                                             transform ${flechaArriba ? 'rotate-180' : ''}`}
                                            xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                                            <path d="M10 2a1 1 0 0 1 1 1v12.586l2.293-2.293a1 1 0 1 1 1.414 1.414l-4
                                            4a1 1 0 0 1-1.414 0l-4-4a1 1 0 0 1 1.414-1.414L9 15.586V3a1 1 0 0 1 1-1z"/>
                                        </svg>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div className="border-b w-full border-cyan-700	"></div>
                </td>
                <td align="center"></td>
            </tr>
            <tr>
                <td>
                    {isShowLogBoxCont && (
                        <div className="flex mb-44 justify-center items-center h-full">
                            {loadingLogBox ? (
                                <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                            ) : renderLogContent()}
                        </div>
                    )}
                </td>
            </tr>
            <tr>
                {showFileContributor ? (<ChargeClientWallet/>) : null}
            </tr>
            <tr>
                <td>{showContribuidoresForm && <Revisar2Form/>}</td>
            </tr>
            </tbody>
        </table>
    )
}