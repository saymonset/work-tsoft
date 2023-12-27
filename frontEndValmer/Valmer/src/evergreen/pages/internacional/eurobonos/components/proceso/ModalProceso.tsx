import { BarLoader } from "react-spinners";
import { ButtonContent, Modal, TitleDate } from "../../../../../../shared";
import { useGetCatalogoEuroCurvas } from "./hooks";
import { useEffect } from "react";
import { useModalProceso } from "./hooks/useModalProceso";
import {generateUUID} from "../../../../../../utils";

interface ModalProcesoProps {
    close: () => void;
    isModalOpenProcesos: boolean;
}

export const ModalProceso = ({ close, isModalOpenProcesos }: ModalProcesoProps) => {
    const title = "Catalogo EURO_CURVAS";

    const { catalogoEuroCurva, loading, fetchCatalogoEuroCurvas, updateCatalogo } = useGetCatalogoEuroCurvas();
    const { handleClick,
        handleLimpiar,
        handleInstrumentoChange,
        handleCurvaChange,
        handleGuardar,
        selectedNID,
        selectedInstrumento,
        selectedCurva,
        handleNuevo,
        loadingButtonNuevo,
        loadingButtonGuardar,
        handleBorrar,
        loadingButtonBorrar } = useModalProceso(updateCatalogo);

    useEffect(() => {
        if (!catalogoEuroCurva) {
            fetchCatalogoEuroCurvas().then();
        }
    }, [catalogoEuroCurva, fetchCatalogoEuroCurvas]);

    return (
        <Modal isOpen={isModalOpenProcesos} onClose={close} title="" isEuroCurvas={true}>
            <TitleDate title={title} />
            <button className="btn" onClick={handleNuevo}>
                <ButtonContent name="Nuevo" loading={loadingButtonNuevo} />
            </button>
            <button className="btn" onClick={handleGuardar}>
                <ButtonContent name="Guardar" loading={loadingButtonGuardar} />
            </button>
            <button className="btn" onClick={handleBorrar}>
                <ButtonContent name="Borrar" loading={loadingButtonBorrar} />
            </button>
            <button className="btn" onClick={handleLimpiar}>
                <span>Limpiar</span>
            </button>
            <div className="text-white bg-cyan-700 flex justify-center mt-10">EURO_CURVAS</div>
            {loading && (
                <BarLoader
                    className="ml-2 w-full mt-2 mb-2"
                    color="#059669"
                    width={500}
                />
            )}
            <div className="max-h-64 overflow-y-scroll">
                <table className="table w-full">
                    <thead className="thead">
                        <tr>
                            <th>N_ID</th>
                            <th>S_INSTRUMENTO</th>
                            <th>S_CURVA</th>
                            <th>PRECIO</th>
                            <th>DXV</th>
                            <th>VTO</th>
                            <th>INSUMO</th>
                        </tr>
                    </thead>
                    <tbody className="tbody">
                        {catalogoEuroCurva?.body?.registros.map((registro) => (
                            <tr key={generateUUID()}>
                                <td className="btn-link">
                                    <button
                                        onClick={(e) =>
                                            handleClick(e, registro.n_id, registro.s_instrumento, registro.s_curva)}>
                                        {registro.n_id}
                                    </button>
                                </td>
                                <td>{registro.s_instrumento}</td>
                                <td>{registro.s_curva}</td>
                                <td>{registro.n_precio}</td>
                                <td>{registro.n_dxv}</td>
                                <td>{registro.d_vto}</td>
                                <td>{registro.n_insumo}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className="form-cols-2 w-full">
                <div className="form-cols-1">
                    <span className="form-title">DATOS</span>
                    <div className="form-cols-1 -my-3">
                        <div className="form-input">
                            <label htmlFor="idCliente">N_ID</label>
                            <input
                                type="text"
                                value={selectedNID ?? ""}
                            />
                        </div>
                        <div className="form-input">
                            <label htmlFor="idCliente">S_INSTRUMENTO</label>
                            <input
                                type="text"
                                value={selectedInstrumento}
                                onChange={handleInstrumentoChange}
                            />
                        </div>
                        <div className="form-input">
                            <label htmlFor="idCliente">S_CURVA</label>
                            <input
                                type="text"
                                value={selectedCurva}
                                onChange={handleCurvaChange}
                            />
                        </div>
                    </div>
                </div>
            </div>
            <div className="flex flex-row justify-center mt-9">
                <button className="btn" onClick={close}>
                    <span>Cerrar</span>
                </button>
            </div>
        </Modal>
    );
}