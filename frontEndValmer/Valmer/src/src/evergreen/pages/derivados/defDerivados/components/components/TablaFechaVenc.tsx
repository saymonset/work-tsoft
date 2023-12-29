import { BarLoader } from "react-spinners"
import { TableDefVenc } from "../../../../../../model"
import { generateUUID } from "../../../../../../utils"
import { ButtonContent, Modal } from "../../../../../../shared"

interface TablaFechaVencProps {
    isHelp: boolean
    loading: boolean
    isModal: boolean
    loadingDeleted: boolean
    data: TableDefVenc[]
    tipoTv: string
    handleModalConfirm: (sInstrumento: string) => void
    closeModal: () => void
    deletedVenc: () => void
}

export const TablaFechaVenc = (props: TablaFechaVencProps) => {
    return (
        <>
            <div className={`w-full h-96 animate__animated ${!props.isHelp ? "animate__fadeIn" : "animate__fadeOut hidden"}`}>
                <div 
                    className="bg-cyan-700 text-white px-1 mt-2 mx-1"
                >
                    Fechas Vto
                </div>
                {props.loading && <BarLoader className="w-full mt-2 mb-2" color="#059669" width={300} />}
                <div className="max-h-full overflow-y-auto">
                    <table className="table w-full">
                        <thead className="thead">
                            <tr>
                                <th>Instrumento</th>
                                {props.tipoTv === "F" ? (
                                    <th>Fecha</th>
                                ) : (
                                    <>
                                        <th>Fecha 1</th>
                                        <th>Fecha 2</th>
                                    </>
                                )}
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody className="tbody">
                            {props?.data?.map((item) => (
                                <tr className="tr" key={generateUUID()}>
                                    <td>
                                        {item.s_instrumentoq}
                                    </td>
                                    {props.tipoTv === "F" ? (
                                        <td>{item.s_fechaq}</td>
                                    ) : (
                                        <>
                                            <td>{item.s_fechaq0}</td>
                                            <td>{item.s_fechaq1}</td>
                                        </>
                                    )}
                                    <td>
                                        <button 
                                            className="fa fa-remove text-red-500 text-xl"
                                            onClick={() => props.handleModalConfirm(item.s_instrumentoq)}
                                        />
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
            <Modal 
                title="Eliminar Vencimiento" 
                isOpen={props.isModal} 
                onClose={props.closeModal} 
                modalSize="medium"
            >
                <div className="text-center">
                    <p className="mb-2">Realmente desea eliminar esto?</p>
                    <button 
                        className="btn"
                        onClick={props.deletedVenc}
                    >
                        <ButtonContent name="Aceptar" loading={props.loadingDeleted} />
                    </button>
                    <button 
                        className="btn"
                        onClick={props.closeModal}
                    >
                        Cancelar
                    </button>
                </div>
            </Modal>
        </>
    )
}