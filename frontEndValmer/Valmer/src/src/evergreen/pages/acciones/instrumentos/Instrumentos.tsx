import {ButtonContent, TitleDate} from "../../../../shared";
import React from "react";
import {InstrumentosForm, ModalCaracTvForm} from "./components";
import {useInstrumentosAcc} from "./hooks";

export const Instrumentos = () => {

    const {
        isOpenModal,
        loading,
        showCarRvAcc,
        requeridosAcc,
        handleOpenCaracCv,
        handleCloseCaracCv,
        handleErase,
        handleNewForm,
        handleSave
    } = useInstrumentosAcc()


    return (
        <>
            <TitleDate title='Instrumentos Acciones'/>

            <div className="form-cols-2">
                <div className="flex justify-start pr-2">
                    <button className="btn" onClick={handleSave}>
                        <ButtonContent name="Guardar" loading={loading}/>
                    </button>
                </div>

                <div className="flex justify-end pr-2">
                    {showCarRvAcc && (
                        <button className="btn" onClick={handleOpenCaracCv}>
                            <span>Carac.RV</span>
                        </button>
                    )}
                    <button className="btn" onClick={handleNewForm}>
                        <span>Nuevo</span>
                    </button>
                    <button className="btn" onClick={handleErase}>
                        <span>Limpiar</span>
                    </button>
                </div>
            </div>

            <div className={`mt-4 container-flex animate__animated animate__fadeIn`}>
                <div className="container-form">
                    <InstrumentosForm requeridosAcc={requeridosAcc} />
                </div>
            </div>

            <ModalCaracTvForm isOpenModal={isOpenModal} handleCloseCaracCv={handleCloseCaracCv}/>
        </>
    )
}