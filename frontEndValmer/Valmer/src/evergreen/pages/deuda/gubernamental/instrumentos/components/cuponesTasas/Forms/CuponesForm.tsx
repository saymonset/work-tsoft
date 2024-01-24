import {getCatalogs} from "../../../../../../../../utils";
import React from "react";
import {useDataCupones} from "./hooks";
import { useBigInput } from "../../../../../../../../utils";

export const CuponesForm = ({requeridos}: any) => {

     //  Achica o agranda el input del form cuando obtiene o deja el focus
     const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
    const {
        consultaData,
        catalog,
        fieldRequired,
        handleChange,
        handleNumericChange
    } = useDataCupones({requeridos})

    return (
        <form>
            <div className="px-3">
                <div className="form-cols-3">
                    <div className="form-select">
                        <select name="n_frecuencia_cupon"
                                value={consultaData?.body?.n_frecuencia_cupon || ''}
                                onChange={handleChange}
                        >
                            <option value="default">...</option>
                            {getCatalogs(catalog, 'FRECUENCIA_CUPON-GUBER').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_frecuencia_cupon">Forma de Pago</label>
                        {fieldRequired.n_frecuencia_cupon && (
                            <span className="fontError animate__animated animate__fadeIn">Campo requerido n_frecuencia_cupon</span>
                        )}
                    </div>
                    <div className=" form-date form-date-my">
                        <input
                            type="date"
                            name="d_fecha_ini_cupon"
                            value={consultaData?.body?.d_fecha_ini_cupon || ''}
                            onChange={handleChange}
                        />
                        <label htmlFor="d_fecha_ini_cupon">Fecha Inicio Cupón</label>
                    </div>
                    <div className=" form-date form-date-my">
                        <input
                            type="date"
                            name="d_fecha_fin_cupon"
                            value={consultaData?.body?.d_fecha_fin_cupon || ''}
                            onChange={handleChange}
                        />
                        <label htmlFor="d_fecha_fin_cupon">Fecha Fin Cupón</label>
                    </div>
                </div>
                <div className="form-cols-3">
                    <div className="form-input">
                        <input type="text"
                               placeholder=""
                               name="n_num_cupones"
                               value={consultaData?.body?.n_num_cupones || ''}
                               onChange={(e) => handleNumericChange(e, handleChange)}
                               onFocus={() => handleFocus('n_num_cupones')}
                               onBlur={handleBlur}
                               style={sendStyle('n_num_cupones')}
                        />
                        <label htmlFor="n_num_cupones">Cupones</label>
                    </div>
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_tasa"
                            value={consultaData?.body?.n_tasa || ''}
                            onChange={handleChange}
                            placeholder=""
                            onFocus={() => handleFocus('n_tasa')}
                            onBlur={handleBlur}
                            style={sendStyle('n_tasa')}
                        />
                        <label htmlFor="n_tasa">Tasa</label>
                        {fieldRequired.n_tasa && (
                            <span className="fontError animate__animated animate__fadeIn">Campo requerido n_tasa</span>
                        )}
                    </div>
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_tasa_24h"
                            value={consultaData?.body?.n_tasa_24h || ''}
                            onChange={handleChange}
                            placeholder=""
                            onFocus={() => handleFocus('n_tasa_24h')}
                            onBlur={handleBlur}
                            style={sendStyle('n_tasa_24h')}
                        />
                        <label htmlFor="n_tasa_24h">Tasa 24h</label>
                        {fieldRequired.n_tasa_24h && (
                            <span
                                className="fontError animate__animated animate__fadeIn">Campo requerido n_tasa_24h</span>
                        )}
                    </div>
                </div>
                <div className="form-cols-3">
                    <div className="form-select">
                        <select name="n_tipo_promedio"
                                value={consultaData?.body?.n_tipo_promedio || ''}
                                onChange={handleChange}
                        >
                            <option value="default">...</option>
                            {getCatalogs(catalog, 'TIPO_PROMEDIO').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="n_tipo_promedio">Tipo Promedio</label>
                        {fieldRequired.n_tipo_promedio && (
                            <span className="fontError animate__animated animate__fadeIn">Campo requerido n_tipo_promedio</span>
                        )}
                    </div>
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_periodo_cupon"
                            value={consultaData?.body?.n_periodo_cupon || ''}
                            onChange={handleChange}
                            placeholder=""
                            onFocus={() => handleFocus('n_periodo_cupon')}
                            onBlur={handleBlur}
                            style={sendStyle('n_periodo_cupon')}
                        />
                        <label htmlFor="n_periodo_cupon">Período Cupón</label>
                    </div>
                    <div className="form-input">
                        <input
                            type="text"
                            name="n_periodo_cupon_v"
                            value={consultaData?.body?.n_periodo_cupon_v || ''}
                            onChange={handleChange}
                            placeholder=""
                            onFocus={() => handleFocus('n_periodo_cupon_v')}
                            onBlur={handleBlur}
                            style={sendStyle('n_periodo_cupon_v')}
                        />
                        <label htmlFor="n_periodo_cupon_v">Período Cupón Vigente</label>
                    </div>
                </div>
            </div>
        </form>
    )
}