import { ButtonContent, Modal } from "../../../../../../shared"
import { useModalPegaVtos } from "./hooks"
import {ModalPegaVtosProps} from "../../../../../../model";

export const ModalPegaVtos = (props: ModalPegaVtosProps) => {

    const {
        textArea,
        loadingCarga,
        handleChangeTextArea,
        handleLimpiar,
        handleCarga
    } = useModalPegaVtos(props)

    return (
        <Modal isOpen={props.isOpen} onClose={props.onClose} title="Vencimientos">
            <div className="grid grid-rows-3 grid-flow-col gap-1">
                <div className="row-span-3 col-span-6 gird grid-rows-6 gap-1">
                    <div className="form-title text-center">FORMATO</div>
                        {props.tipoTv === "F" ? (
                            <div className="grid grid-cols-2 gap-1 -mt-3">
                                <div className="form-title">INSTRUMENTO</div>
                                <div className="form-title">FECHA(AAAAMMDD)</div>
                            </div>
                        ) : (
                            <div className="grid grid-cols-3 gap-1">
                                <div className="form-title">CLAVE</div>
                                <div className="form-title">FECHA1(AAAAMMDD)</div>
                                <div className="form-title">FECHA2(AAAAMMDD)</div>
                            </div>
                        )}
                    <div className="mt-1 w-full">
                        <textarea 
                            className="border border-solid border-gray-900 w-full max-h-64"
                            name="TextAreaVtos" 
                            id="TextAreaVtos"
                            rows={7}
                            value={textArea || ""}
                            onChange={(e) => handleChangeTextArea(e)}
                        />
                    </div>
                </div>
                <div className="grid grid-rows-3 gap-1">
                    <div className="form-title text-center">CARGA</div>
                    <button 
                        className="btn"
                        onClick={handleCarga}
                    >
                        <ButtonContent name="CARGA" loading={loadingCarga} />
                    </button>
                    <button 
                        className="btn"
                        onClick={handleLimpiar}
                    >
                        LIMPIAR
                    </button>
                </div>
            </div>
        </Modal>
    )
}