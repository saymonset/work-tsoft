import {generateUUID} from "../../../../../../utils";
import {BarLoader} from "react-spinners";
import React from "react";
import {CostaRicaHeaderProps} from "../../../../../../model";
import {ButtonContent} from "../../../../../../shared";

export const CostaRicaFormHeader = (data: CostaRicaHeaderProps) => {
    return (
        <div className="form mt-4 animate__animated animate__fadeIn">
            <form>
                <div className="form-cols-1">
                    <div className="form-cols-1">
                        <div className="flex justify-end pr-2">
                            <button
                                className="btn"
                                type='button'
                                onClick={data.handleNuevo}
                            >
                                <span>Nuevo</span>
                            </button>
                            <button
                                className="btn"
                                onClick={data.handleSave}
                            >
                                <ButtonContent name={"Guardar"} loading={data.loadingSave}/>
                            </button>
                            <button
                                className="btn"
                                onClick={data.handleErase}
                            >
                                <span>Limpiar</span>
                            </button>
                            {data.activeNuevo && (
                                <button
                                    className="btn animate__animated animate__fadeIn"
                                    type='button'
                                    onClick={data.handleCancel}
                                >
                                    <span>Cancelar</span>
                                </button>
                            )}
                        </div>
                    </div>
                    <span className="form-title">Instrumento</span>
                    {data.activeNuevo && (
                        <div className="form-cols-4 -my-3 animate__animated animate__fadeIn">
                            <div className="form-input">
                                <input
                                    type="text"
                                    name="emisor"
                                    value={data.selectedEmisor}
                                    onChange={(e) => {
                                        data.setSelectedEmisor(e.target.value)
                                    }}
                                />
                                <label htmlFor="emisor">EMISOR</label>
                            </div>
                            <div className="form-input">
                                <input
                                    type="text"
                                    name="nemoInstrumento"
                                    value={data.selectedNemo}
                                    onChange={(e) => {
                                        data.setSelectedNemo(e.target.value)
                                    }}
                                />
                                <label htmlFor="nemoInstrumento">NEMO INSTRUMENTO</label>
                            </div>
                            <div className="form-input">
                                <input
                                    type="text"
                                    name="serie"
                                    value={data.selectedSerie}
                                    onChange={(e) => {
                                        data.setSelectedSerie(e.target.value)
                                    }}
                                />
                                <label htmlFor="serie">SERIE</label>
                            </div>
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="inactivas"
                                    value={data.checkboxValue}
                                    onChange={data.handleCheckboxChange}
                                />
                                <label htmlFor="inactivas">Inactivas</label>
                            </div>
                        </div>
                    )}
                    {!data.activeNuevo && (
                        <div className="form-cols-4 -my-3 animate__animated animate__fadeIn">

                            <div className="form-select">
                                <select
                                    name="s_emisor"
                                    value={data.selectedEmisor}
                                    onChange={data.handleEmisor}
                                >
                                    <option value="">...</option>
                                    {data.emisor?.map((item: any) => (
                                        <option key={generateUUID()} value={item}>
                                            {item}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="emisor">EMISOR</label>
                            </div>

                            <div className="form-select">
                                <select
                                    name="s_nemo_instr"
                                    value={data.selectedNemo}
                                    onChange={data.handleNemo}
                                >
                                    <option value="">...</option>
                                    {data.nemoInstrumento?.map((item) => (
                                        <option key={generateUUID()} value={item}>
                                            {item}
                                        </option>
                                    ))}
                                </select>
                                {data.loadingNemoInst && <BarLoader className="mt-2" color="#059669" width={200}/>}
                                <label htmlFor="nemoInstrumento">NEMO INSTRUMENTO</label>
                            </div>

                            <div className="form-select">
                                <select
                                    name="s_serie"
                                    value={data.selectedSerie}
                                    onChange={data.handleSerie}
                                >
                                    <option value="">...</option>
                                    {data.serie?.map((item) => (
                                        <option key={generateUUID()} value={item}>
                                            {item}
                                        </option>
                                    ))}
                                </select>
                                {data.loadingSerie && <BarLoader className="mt-2" color="#059669" width={200}/>}
                                <label htmlFor="serie">SERIE</label>
                            </div>
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="inactivas"
                                    value={data.checkboxValue}
                                    onChange={data.handleCheckboxChange}
                                />
                                <label htmlFor="inactivas">Inactivas</label>
                            </div>
                        </div>
                    )}
                </div>
            </form>
            <div className="line"></div>
        </div>
    )
}