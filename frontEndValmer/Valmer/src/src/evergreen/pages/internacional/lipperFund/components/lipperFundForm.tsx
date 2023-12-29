import { useDataHandle } from "../hooks";
import {ConsultaLipper, FormValuesLipper} from "../../../../../model";
import { ButtonContent, Modal } from "../../../../../shared";
import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {generateUUID} from "../../../../../utils";

export const LipperFundForm = () => {

    const consultaLipper = useSelector((state: RootState<any, any, any>) =>
        state.consultaDataLipper) as unknown as ConsultaLipper

    const {
        loadingSubmit,
        isOpen,
        selectedValues,
        checkboxValueEval,
        inputValueEval,
        handleValidaBBVAChange,
        handlePrecioMercadoChange,
        handleUpdateModal,
        handleCloseModal,
        handleUpdateSubmit
    } = useDataHandle()

    return (
        <div className="flex flex-col text-sm mt-5 mb-5 ml-5 mr-5 animate__animated animate__fadeIn">
            <table className='table'>
                <thead className='thead'>
                    <tr>
                        <th>CONT</th>
                        <th>INSTRUMENTO</th>
                        <th>ISIN</th>
                        <th>RIC</th>
                        <th>STATUS</th>
                        <th>VALIDA BBVA</th>
                        <th>MONEDA</th>
                        <th>PRECIO MERCADO</th>
                        <th>GUARDAR</th>
                    </tr>
                </thead>
                <tbody className='tbody'>
                    {consultaLipper.body.map((item: FormValuesLipper) => (
                        <tr key={generateUUID()} className='tr'>
                            <td>{item.rowNum}</td>
                            <td>{item.instrumento}</td>
                            <td>{item.isin}</td>
                            <td>{item.ric}</td>
                            <td>{item.status}</td>
                            <td>
                                <input
                                    type="checkbox"
                                    className="w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded focus:ring-cyan-700
                                    dark:focus:ring-cyan-700 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700
                                    dark:border-gray-600"
                                    id={`b_valida_bbva_${item.instrumento}`}
                                    name={`b_valida_bbva_${item.instrumento}`}
                                    checked = {checkboxValueEval('b_valida_bbva_'+item.instrumento, item.valida_bbva)}
                                    onChange={handleValidaBBVAChange}
                                />
                            </td>
                            <td>{item.moneda}</td>
                            <td>
                                <input
                                    type="text"
                                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2
                                    border-gray-300 appearance-none dark:text-slate-900 dark:border-gray-600
                                    dark:focus:border-cyan-700 focus:outline-none focus:ring-0 focus:border-cyan-700 peer"
                                    name={`n_precio_mercado_${item.instrumento}`}
                                    id={`n_precio_mercado_${item.instrumento}`}
                                    value={inputValueEval('n_precio_mercado_'+item.instrumento, item.precio_mercado)}
                                    onChange={handlePrecioMercadoChange}
                                />
                            </td>
                            <td>
                                <button
                                    className="btn"
                                    onClick={() => handleUpdateModal(item.instrumento, item.valida_bbva, item.precio_mercado)}
                                >
                                    <span className="fa-solid fa-floppy-disk"></span>
                                </button>
                            </td>
                        </tr>
                    ))}
                    <Modal isOpen={isOpen} onClose={handleCloseModal} title="¿Desea actualizar esta información?" modalSize="small">
                        <ul>
                            <li>Instrumento: {selectedValues.s_instrumento}</li>
                            <li>Precio: {selectedValues.n_precio_mercado}</li>
                            <li>Valida BBVA: {selectedValues.b_valida_bbva}</li>
                        </ul>
                        <div className="line"></div>
                        <button 
                            className="btn"
                            onClick={handleUpdateSubmit}
                        >
                            <ButtonContent name="Continuar" loading={loadingSubmit}></ButtonContent>
                        </button>
                        <button 
                            className="btn"
                            onClick={handleCloseModal}
                        >
                            <span>Cancelar</span>
                        </button>
                    </Modal>
                </tbody>
            </table>
        </div>
    )
}