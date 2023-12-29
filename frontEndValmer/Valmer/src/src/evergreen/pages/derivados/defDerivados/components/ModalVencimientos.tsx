import { Modal } from "../../../../../shared"
import { FormVenc, FormatArch, TablaFechaVenc } from "./components"
import { ModalPegaVtos } from "./components/ModalPegaVtos"
import { useDataModal } from "./hooks"
import {ModalVencProps} from "../../../../../model";

export const ModalVencimientos = (props: ModalVencProps) => {

    const {
        isHelp,
        loadingDataTable,
        loadingDeleted,
        dataTableVenc,
        isModalConfirm,
        modalCargaArc,
        modalCargaArcOpen,
        modalCargaArcClose,
        setIsHelp,
        setDataTableVenc,
        confirmDeleteVenc,
        closeModalConfirm,
        deletedVenc
    } = useDataModal(props)

    return (
        <Modal isOpen={props.isOpen} onClose={props.onClose} title="Vencimientos" modalSize="large">
            <div className="flex">

                <div className={`${props.tipoTv === "otro" ? "w-full" : "w-3/5"}`}>
                    <div className="panel">
                        <div className="panel-header">

                            <div
                                className={`panel-button
                                    ${props.tipoTv === "F"
                                            ? "panel-button-active"
                                            : "bg-cyan-700 text-white opacity-40"
                                    }`}
                            >
                                Futuros
                            </div>

                            <div
                                className={`panel-button 
                                    ${props.tipoTv === "O"
                                            ? "panel-button-active"
                                            : "bg-cyan-700 text-white opacity-40"
                                    }`}
                            >
                                Opciones
                            </div>
                        </div>

                        <div className="panel-body">
                            <FormVenc
                                tipoTv={props.tipoTv}
                                tv={props.tv}
                                emisora={props.emisora}
                                serieOp={props.serieOp}
                                setIsHelp={setIsHelp}
                                setDataTable={setDataTableVenc}
                                openModal={modalCargaArcOpen}
                            />
                        </div>

                        <div className="panel-body">
                            {props.tipoTv === "otro" && (
                                <div></div>
                            )}
                        </div>

                    </div>
                </div>
                {props.tipoTv !== "otro" && (
                    <div className="block w-2/5">
                        <TablaFechaVenc
                            isHelp={isHelp}
                            loading={loadingDataTable}
                            data={dataTableVenc}
                            isModal={isModalConfirm}
                            loadingDeleted={loadingDeleted}
                            tipoTv={props.tipoTv}
                            handleModalConfirm={confirmDeleteVenc}
                            closeModal={closeModalConfirm}
                            deletedVenc={deletedVenc}
                        />
                        <FormatArch
                            isHelp={isHelp}
                        />
                    </div>
                )}
            </div>
            <ModalPegaVtos
                isOpen={modalCargaArc}
                onClose={modalCargaArcClose}
                tipoTv={props.tipoTv}
            />
        </Modal>
    )
}