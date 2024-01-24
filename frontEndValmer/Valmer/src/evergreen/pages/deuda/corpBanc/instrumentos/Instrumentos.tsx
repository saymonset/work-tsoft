import {ButtonContent, LogBoxText, TitleDate} from "../../../../../shared";
import React from "react";
import {Panel} from "./Panel";
import {HocRestricted} from "../../../restrictedAccess";
import {useCalCorp, useEraseData} from './hooks';
import {Link} from "react-router-dom";
import {ModalCalCorp} from "./components";

export const Instrumentos = () => {

    const title : string = "Instrumentos Corp-Banc"
    let concatenatedValues = "";

    const {
        requeridosCorp,
        loadingSubmit,
        handleSubmit,
        handleNuevoClick,
        handleNuevaSerieClick,
        handleLimpiarClick
    } = useEraseData()

    const {
        instrument,
        isDivVisible,
        isAnimatingOut,
        loadingSaveCalculator,
        loadingCalPrecios,
        loadingCalTasas,
        isSelectDate,
        isModalOpenCalTasas,
        isModalOpenCalPrecio,
        selectDate,
        precios,
        tasasInt,
        handleModalCloseTasas,
        handleModalClosePrecio,
        handleDate,
        handleCalTasasInt,
        handleCalPrecio,
        handleUpdateCalculator,
        toggleDivVisibility,
        selectedValues
    } = useCalCorp()

    if (selectedValues.selectedEmisora ==="...") selectedValues.selectedEmisora = "0";
    if (selectedValues.selectedSerie ==="...") selectedValues.selectedSerie = "0";
    if (selectedValues.selectedTv !== "" || selectedValues.selectedEmisora !== "" || selectedValues.selectedSerie !== "") {
        concatenatedValues = `${selectedValues.selectedTv}_${selectedValues.selectedEmisora}_${selectedValues.selectedSerie}`;
    }

    return (
        <HocRestricted title={title} view={title}>
            <TitleDate title={title}/>
            <div>
                <li>
                    <strong style={{ color: '#2980b9' }}>{concatenatedValues}</strong>
                </li>
            </div>
            <div className="flex justify-end pr-2">
                <button
                    className="btn"
                    onClick={handleNuevoClick}
                >
                    <span>Nuevo</span>
                </button>
                <button
                    className="btn"
                    onClick={handleSubmit}
                >
                    <ButtonContent name="Guardar" loading={loadingSubmit}/>
                </button>
                <button
                    className="btn"
                    onClick={handleNuevaSerieClick}
                >
                    <span>Nueva Serie</span>
                </button>
                <Link to="/deuda/genera/si" className="btn">
                    <span>S.I.</span>
                </Link>
                <Link to="/deuda/corpBanc/cortes/cupon" className="btn">
                    <span>Corte Cupón</span>
                </Link>
                <Link to="/deuda/corpBanc/bonos/referencia" className="btn">
                    <span>Cambios Bonos Ref.</span>
                </Link>
                <Link to="/deuda/corpBanc/valuacion/instrumentos" className="btn">
                    <span>Valuación</span>
                </Link>
                <Link to="/deuda/corpBanc/carga/instr" className="btn">
                    <span>Carga Instr</span>
                </Link>
                <Link to="/deuda/corpBanc/carga/bullet/bond" className="btn">
                    <span>Carga BulletBond</span>
                </Link>
                <div>|</div>
                <button
                    className="btn"
                    onClick={handleLimpiarClick}
                >
                    <span>Limpiar</span>
                </button>
                <div>|</div>
                <button className={`ml-2 bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3`}
                        onClick={toggleDivVisibility}>
                    <i className="fa fa-calculator"></i>
                </button>
                <div>|</div>
            </div>

            {isDivVisible && (
                <div className={`mt-4 basis-4/6 text-right animate__animated ${isAnimatingOut ?
                    'animate__fadeOutRight' : 'animate__fadeInRight'}`}>
                    <div className="flex gap-3 items-center justify-end">
                        <div className="">
                            <button className="btn"
                                    onClick={handleCalTasasInt}>
                                <i className="mr-2 fa fa-calculator"></i>
                                <ButtonContent name="Cal.Tasas-Int." loading={loadingCalTasas}/>
                            </button>
                        </div>
                        <div className="">
                            <button className="btn"
                                    onClick={handleCalPrecio}>
                                <i className="mr-2 fa fa-calculator"></i>
                                <ButtonContent name="Cal.Precio." loading={loadingCalPrecios}/>
                            </button>
                        </div>
                        <div className="form-date w-64">
                            <input
                                type="date"
                                value={selectDate}
                                onChange={handleDate}
                                required
                            />
                            {isSelectDate && (
                                <span className="fontError animate__animated animate__fadeIn">
                                    Fecha Cal.Precio es requerido</span>
                            )}
                        </div>
                    </div>
                </div>
            )}

            <Panel requeridos={requeridosCorp} />

            <ModalCalCorp instrument={instrument}
                          isModalOpenCalTasas={isModalOpenCalTasas}
                          handleModalCloseTasas={handleModalCloseTasas}
                          isModalOpenCalPrecio={isModalOpenCalPrecio}
                          handleModalClosePrecio={handleModalClosePrecio}
                          handleUpdateCalculator={handleUpdateCalculator}
                          loadingSaveCalculator={loadingSaveCalculator}
                          precios={precios}
                          tasasInt={tasasInt}
            />

            <div className="my-8">
                <LogBoxText logName={"log_corpo"}/>
            </div>
        </HocRestricted>
    );
};