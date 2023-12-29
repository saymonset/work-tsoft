import React from "react";
import {BarLoader} from "react-spinners";
import {generateUUID, getCatalogs} from "../../../../../utils";
import {PanamaHeaderProps} from "../../../../../model";
import {ButtonContent} from "../../../../../shared";
import { Link } from "react-router-dom";

export const PanamaHeader = (data: PanamaHeaderProps) => {
    return (
        <div className="form mt-4 animate__animated animate__fadeIn">
            <form>
                <div className="form-cols-3">
                    <div className="form-cols-1 col-span-2">
                        <div className="form-cols-1">
                            <div className="flex justify-end pr-2">
                                <button
                                    className="btn"
                                    type='button'
                                    onClick={data.handleNuevo}
                                >
                                    <span>Nuevo</span>
                                </button>
                                <button className="btn" onClick={data.handleSave}>
                                    <ButtonContent name="Guardar" loading={data.loadingSave}/>
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
                        {data.loadingConsultaData && <BarLoader className="w-full" color="#059669" width={500} />}
                        <div className="form-cols-2 -my-3">
                            {data.activeNuevo && (
                                <div className="form-input animate__animated animate__fadeIn">
                                    <input
                                        type="text"
                                        name="nemotecnico"
                                        id="nemotecnico"
                                        placeholder=''
                                        value={data.selectedNemoTecnico}
                                        onChange={(e) =>
                                        {data.setSelectedNemoTecnico(e.target.value)}}
                                    />
                                    <label htmlFor="nemotecnico">NEMOTECNICO</label>
                                </div>
                            )}
                            {!data.activeNuevo && (
                                <div className="form-select animate__animated animate__fadeIn">
                                    <select
                                        name="nemotecnico"
                                        id="nemotecnico"
                                        value={data.selectedNemoTecnico}
                                        onChange={data.handleSelectNemo}
                                    >
                                        <option value="">...</option>
                                        {data.nemoTecnico?.map((item) => (
                                            <option key={generateUUID()} value={item}>
                                                {item}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="nemotecnico">NEMOTECNICO</label>
                                </div>
                            )}
                            <div className="form-select">
                                <select
                                    name="n_tipo_instrumento"
                                    value={data.consultaData?.body?.info_bd?.n_tipo_instrumento || "default"}
                                    onChange={data.handleChange}
                                >
                                    <option value="">...</option>
                                    {getCatalogs(data.catalog, 'TIPO_INSTRUMENTO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_tipo_instrumento">TIPO INSTRUMENTO</label>
                            </div>
                        </div>
                        {!data.activeNuevo && (
                            <div className="form-cols-2 animate__animated animate__fadeIn">
                                <div className="form-check">
                                    <input
                                        type="checkbox"
                                        name="inactivas"
                                        checked={data.checkboxValues.inactivas === 1}
                                        onChange={data.handleCheckboxChange}
                                    />
                                    <label htmlFor="inactivas">Inactivas</label>
                                </div>
                                <div className="form-check">
                                    <input
                                        type="checkbox"
                                        name="amortAnticipadas"
                                        checked={data.checkboxValues.amortAnticipadas === 1}
                                        onChange={data.handleCheckboxChange}
                                    />
                                    <label htmlFor="amortAnticipadas">Amort. Anticipadas</label>
                                </div>
                            </div>
                        )}
                    </div>
                    <Link to="/latam/panama/historico">
                    <div className="form-cols-1">
                        <a className="card-img">
                            <img
                                src="/img/hist_animado.gif"
                                alt="históricos"
                                className="card-img"
                            />
                        </a>
                    </div>
                    </Link>
                </div>
            </form>
            <div className="line"></div>
        </div>
    );
};