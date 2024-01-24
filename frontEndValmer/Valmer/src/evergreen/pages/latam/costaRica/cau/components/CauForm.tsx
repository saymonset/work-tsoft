import React from "react";
import {MoonLoader} from "react-spinners";
import {ButtonContent} from "../../../../../../shared";
import {CauFormsProps} from "../../../../../../model";
import {Link} from "react-router-dom";
import {getCatalogs, useBigInput} from "../../../../../../utils";
export const CauForm = ({
                            loadingSave,
                            loadingFolio,
                            isEdit,
                            catalog,
                            queryFolio,
                            handleChange,
                            handleSave,
                            status
                        }: CauFormsProps) => {

    //  Achica o agranda el input del form cuando obtiene o deja el focus
    const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();                            

    const isCerrados = status === "Cerrados";
    const isAbiertos = status === "Abiertos";

    return (
        <div>
            {loadingFolio ? (
                <div className="mt-8 flex justify-center items-center h-full">
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60}/>
                </div>
            ) : (
                <div className="flex justify-center mt-3">
                    <div className="w-1/2">
                        <form>
                            <div className="form-cols-2">
                                <span className="form-title col-span-2">DATOS</span>
                                <div className="mt-1 -my-3">
                                    <div className="form-input">
                                        <input type="text"
                                               name="n_folio"
                                               value={queryFolio.n_folio ?? ""}
                                               readOnly
                                               onFocus={() => handleFocus('n_folio')}
                                               onBlur={handleBlur}
                                               style={sendStyle('n_folio')}
                                               />
                                        <label htmlFor="n_folio">
                                            FOLIO
                                        </label>
                                    </div>
                                </div>
                                <div className="mt-1 -my-3">
                                    <div className="form-input">
                                        <input type="text"
                                               name="n_area"
                                               value={queryFolio.n_area ?? ""}
                                               readOnly/>
                                        <label htmlFor="n_area">AREA</label>
                                    </div>
                                </div>
                                {isEdit ? (
                                    <div className="mt-2 -my-3 animate__animated animate__fadeIn">
                                        <div className="form-select">
                                            <select name="n_servicio"
                                                    value={queryFolio.n_servicio}
                                                    onChange={handleChange}>
                                                <option value="">...</option>
                                                {getCatalogs(catalog, 'INTRANET.CR_CAU_SERVICIOS').map((column) => (
                                                    <option key={column[0]} value={column[1]}>
                                                        {column[1]}
                                                    </option>
                                                ))}
                                            </select>
                                            <label htmlFor="servicio">SERVICIO</label>
                                        </div>
                                    </div>
                                ) : (
                                    <div className="mt-2 -my-3 animate__animated animate__fadeIn">
                                        <div className="form-input">
                                            <input type="text"
                                                   name="n_servicio"
                                                   value={queryFolio.n_servicio || ""}
                                                   readOnly
                                                   onFocus={() => handleFocus('n_servicio')}
                                                   onBlur={handleBlur}
                                                   style={sendStyle('n_servicio')}/>
                                            <label htmlFor="servicio">SERVICIO</label>
                                        </div>
                                    </div>
                                )}
                                <div className="mt-2 -my-3">
                                    <div className="form-input">
                                        <input type="text"
                                               name="n_empresa"
                                               value={queryFolio.n_empresa ?? ""}
                                               readOnly
                                               onFocus={() => handleFocus('n_empresa')}
                                               onBlur={handleBlur}
                                               style={sendStyle('n_empresa')}/>
                                        <label htmlFor="n_empresa">EMPRESA</label>
                                    </div>
                                </div>
                                <div className="mt-2 -my-3">
                                    <div className="form-input">
                                        <input type="text"
                                               name="s_nombre"
                                               value={queryFolio.s_nombre ?? ""}
                                               readOnly
                                               onFocus={() => handleFocus('s_nombre')}
                                               onBlur={handleBlur}
                                               style={sendStyle('s_nombre')}
                                               />
                                        <label htmlFor="s_nombre">NOMBRE</label>
                                    </div>
                                </div>
                                <div className="mt-2 -my-3">
                                    <div className="form-input">
                                        <input type="text"
                                               name="s_correo"
                                               value={queryFolio.s_correo ?? ""}
                                               readOnly
                                               onFocus={() => handleFocus('s_correo')}
                                               onBlur={handleBlur}
                                               style={sendStyle('s_correo')}
                                               />
                                        <label htmlFor="s_correo">CORREO</label>
                                    </div>
                                </div>
                                {!isCerrados &&
                                    <div className="mt-2 -my-3">
                                        <div className="form-input">
                                            <input type="text"
                                                   name="s_telefono"
                                                   value={queryFolio.s_telefono ?? ""}
                                                   readOnly
                                                   onFocus={() => handleFocus('s_telefono')}
                                                    onBlur={handleBlur}
                                                    style={sendStyle('s_telefono')}
                                                   />
                                            <label htmlFor="s_telefono">TELEFONO</label>
                                        </div>
                                    </div>
                                }
                                {isAbiertos &&
                                    <div className="mt-2 -my-3">
                                        <div className="form-input">
                                            <input type="text"
                                                   name="s_sector"
                                                   value={queryFolio.s_sector ?? ""}
                                                   readOnly
                                                   onFocus={() => handleFocus('s_sector')}
                                                   onBlur={handleBlur}
                                                   style={sendStyle('s_sector')}/>
                                            <label htmlFor="s_telefono">SECTOR</label>
                                        </div>
                                    </div>
                                }
                                <div className="mt-2 -my-3">
                                    <div className="form-text-area">
                                    <textarea name="s_descripcion"
                                              value={queryFolio.s_descripcion ?? ""}
                                              readOnly>
                                    </textarea>
                                        <label htmlFor="s_descripcion">DESCRIPCION</label>
                                    </div>
                                </div>

                                {!queryFolio.s_archivo || queryFolio.s_archivo != "" &&
                                    (
                                        <div className="mt-2 -my-3">
                                            <div className="form-input flex">
                                                <Link to={"http://www.valmercostarica.com/CR/CAU/" + queryFolio.s_archivo }
                                                      className="text-blue-500 hover:text-blue-700">
                                                    <span>{queryFolio.s_archivo}</span>
                                                </Link>
                                                <label htmlFor="s_archivo">ARCHIVO ADJUNTO</label>
                                            </div>
                                        </div>
                                    )
                                }
                                {isCerrados &&
                                    <div className="mt-2 -my-3">
                                        <div className="form-input">
                                            <input type="text"
                                                   name="s_atendio"
                                                   value={queryFolio.s_atendio ?? ""}
                                                   readOnly
                                                   onFocus={() => handleFocus('s_atendio')}
                                                   onBlur={handleBlur}
                                                   style={sendStyle('s_atendio')}/>
                                            <label htmlFor="s_atendio">ATENDIO</label>
                                        </div>
                                    </div>
                                }
                                <div className="mt-2 -my-3">
                                    <div className="form-select">
                                        <select name="n_status"
                                                value={queryFolio.n_status}
                                                onChange={handleChange}>
                                            <option value="default">...</option>
                                            {getCatalogs(catalog, 'CAU_STATUS').map((column) => (
                                                <option key={column[0]} value={column[0]}>
                                                    {column[1]}
                                                </option>
                                            ))}
                                        </select>
                                        <label htmlFor="n_status">STATUS</label>
                                    </div>
                                </div>
                                {queryFolio.n_status === "3" && (
                                    <>
                                        <div className="mt-2 -my-3">
                                            <div className="form-date">
                                                <input
                                                    type="date"
                                                    name="d_fecha_estimada"
                                                    value={queryFolio.d_fecha_estimada}
                                                    onChange={handleChange}
                                                />
                                                <label htmlFor="d_fecha_estimada">
                                                    Fecha Estimada
                                                </label>
                                            </div>
                                        </div>
                                        <div className="mt-2 -my-3">
                                            <div className="form-text-area">
                                    <textarea name="s_observaciones"
                                              value={queryFolio.s_observaciones ?? ""}
                                              onChange={handleChange}>
                                    </textarea>
                                                <label htmlFor="s_observaciones">Observaciones</label>
                                            </div>
                                        </div>
                                    </>
                                )}

                            </div>
                            {(status == "Modificación" || (isEdit && queryFolio.muestra_boton_grabar)) && (
                                <div className="mt-5 animate__animated animate__fadeIn">
                                    <button type="button"
                                            className="btn"
                                            onClick={handleSave}>
                                        <ButtonContent name="Grabar" loading={loadingSave}/>
                                    </button>
                                </div>
                            )}
                        </form>
                    </div>
                </div>
            )}
        </div>
    )
}