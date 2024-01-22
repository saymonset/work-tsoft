import React from "react";
import {BarLoader, MoonLoader} from "react-spinners";
import {generateUUID, getCatalogs} from "../../../../../utils";
import {useAccInsHandleData} from "./hooks";
import {InstrumentosPreCalc, InstrumentosTable} from "./components";
import { RequeridosAcc } from "../../../../../model";
import { useBigInput } from "../../../../../utils/useBigInput";


interface InstrumentosFormProps {
    requeridosAcc: RequeridosAcc
}

export const InstrumentosForm: React.FC<InstrumentosFormProps> = ({ requeridosAcc }) => {

    //  Achica o agranda el input del form cuando obtiene o deja el focus
    const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
    const {
        isNewFormInst,
        showPrecalc,
        tvAccInst,
        loadingTv,
        catalog,
        loading,
        triggerErase,
        consultaDataAccInst,
        emisorasAccInst,
        serieAccInst,
        dividendosTable,
        loadingDividendos,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        loadingEmisoras,
        loadingSerie,
        loadingConsultaData,
        fieldRequiredAccInst,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        loadingPrecalc,
        handleChange,
        handleCheckbox,
        handleClickTv,
        handleEmisora,
        handleSerie
    } = useAccInsHandleData()


    if (triggerErase) {
        return (
            <div className="flex justify-center items-center h-full"></div>
        );
    }

    if (loading || loadingTv || !catalog.length) {
        return (
            <div className="flex justify-center items-center h-full">
                {loading || loadingTv ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <form>
            <div>
                {loadingConsultaData && <BarLoader className="ml-2 w-full mt-2 mb-2" color="#059669" width={500}/>}

                <div className={`${showPrecalc ? 'animate__animated animate__fadeIn' : ''}`}>
                    <div className="form">
                        <div className="form-title">
                            <span>Instrumento</span>
                        </div>
                        <div className="mt-0 form-cols-4">
                            {isNewFormInst ? (
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_tv"
                                        value={selectedTv}
                                        onChange={handleClickTv}
                                        required
                                        onFocus={() => handleFocus('s_tv')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_tv')}
                                    />
                                    <label htmlFor="s_tv">
                                        TV
                                    </label>
                                        {requiredTv && (
                                            <span className="fontError animate__animated animate__fadeIn">
                                                Campo requerido TV
                                            </span>
                                        )}
                                </div>
                            ) : (
                                <div className="form-select">
                                    <select
                                        name="s_tv"
                                        value={selectedTv}
                                        onChange={handleClickTv}
                                    >
                                        <option value="default">...</option>
                                        {tvAccInst?.map((item) => (
                                            <option key={generateUUID()} value={item}>
                                                {item}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="s_tv">
                                        TV {requiredTv}
                                    </label>
                                        {requiredTv && (
                                            <span className="fontError animate__animated animate__fadeIn">
                                                Campo requerido TV
                                            </span>
                                        )}
                                </div>
                            )}
                            {isNewFormInst ? (
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_emisora"
                                        value={selectedEmisora}
                                        onChange={handleEmisora}
                                        required
                                        onFocus={() => handleFocus('s_emisora')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_emisora')}
                                    />
                                    <label htmlFor="s_emisora">
                                        Emisora
                                    </label>
                                        {requiredEmisora && (
                                            <span className="fontError animate__animated animate__fadeIn">
                                                Campo requerido Emisora
                                            </span>
                                        )}
                                </div>
                            ) : (
                                <div className="form-select">
                                    <select
                                        name="s_emisora"
                                        value={selectedEmisora}
                                        onChange={handleEmisora}
                                    >
                                        <option value="default">...</option>
                                        {emisorasAccInst?.map((item) => (
                                            <option key={generateUUID()} value={item}>
                                                {item}
                                            </option>
                                        ))}
                                    </select>

                                    {loadingEmisoras && <BarLoader className="mt-2" color="#059669" width={150}/>}
                                    <label htmlFor="s_emisora">
                                        Emisora
                                    </label>
                                        {requiredEmisora && (
                                            <span className="fontError animate__animated animate__fadeIn">
                                                Campo requerido Emisora
                                            </span>
                                        )}
                                </div>
                            )}
                            {isNewFormInst ? (
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_serie"
                                        value={selectedSerie}
                                        onChange={handleSerie}
                                        required
                                        onFocus={() => handleFocus('s_serie')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_serie')}
                                    />
                                    <label htmlFor="s_serie">
                                        SERIE
                                    </label>
                                        {requiredSerie && (
                                            <span className="fontError animate__animated animate__fadeIn">
                                                Campo requerido Serie
                                            </span>
                                        )}
                                </div>
                            ) : (
                                <div className="form-select">
                                    <select
                                        name="s_serie"
                                        value={selectedSerie}
                                        onChange={handleSerie}
                                    >
                                        <option value="default">...</option>
                                        {serieAccInst?.map((item) => (
                                            <option key={generateUUID()} value={item}>
                                                {item}
                                            </option>
                                        ))}
                                    </select>

                                        {loadingSerie && <BarLoader className="mt-2" color="#059669" width={150}/>}
                                        <label htmlFor="s_serie">
                                            SERIE
                                        </label>
                                        {requiredSerie && (
                                            <span className="fontError animate__animated animate__fadeIn">
                                                Campo requerido Serie
                                            </span>
                                        )}
                                    </div>
                                )}



                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="n_mat_mc"
                                    checked={consultaDataAccInst?.body?.acciones?.n_mat_mc === '1'}
                                    onChange={handleCheckbox}
                                />
                                <label htmlFor="n_mat_mc">
                                    Matriz MC
                                </label>
                            </div>
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="b_sic"
                                    checked={consultaDataAccInst?.body?.acciones.b_sic === '1'}
                                    onChange={handleCheckbox}
                                />
                                <label htmlFor="b_sic">SIC</label>
                            </div>
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="b_tipo_crisis_shock"
                                    checked={consultaDataAccInst?.body?.acciones.b_tipo_crisis_shock === '1'}
                                    onChange={handleCheckbox}
                                />
                                <label htmlFor="b_tipo_crisis_shock">Crisis Shock</label>
                            </div>
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="b_valida_bbva"
                                    checked={consultaDataAccInst?.body?.acciones.b_valida_bbva === '1'}
                                    onChange={handleCheckbox}
                                />
                                <label htmlFor="b_valida_bbva">Valida BBVA(56)</label>
                            </div>
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="b_precio_cierre"
                                    checked={consultaDataAccInst?.body?.acciones.b_precio_cierre === '1'}
                                    onChange={handleCheckbox}
                                />
                                <label htmlFor="b_precio_cierre">Precio Cierre</label>
                            </div>
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="b_rnv"
                                    checked={consultaDataAccInst?.body?.acciones.b_rnv === '1'}
                                    onChange={handleCheckbox}
                                />
                                <label htmlFor="b_rnv">RNV</label>
                            </div>
                        </div>
                        <div className="mt-1 form-cols-4">
                            <div className="form-select">
                                <select
                                    name="n_tipo_instrumento"
                                    value={consultaDataAccInst?.body?.acciones?.n_tipo_instrumento ?? 'default'}
                                    onChange={handleChange}
                                    ref={requeridosAcc.n_tipo_instrumento}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'TIPO_INSTRUMENTO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="n_tipo_instrumento">
                                    Tipo Instrumento
                                </label>
                                {fieldRequiredAccInst.n_tipo_instrumento && (
                                    <span className="fontError animate__animated animate__fadeIn">
                                        Campo requerido Tipo Instrumento
                                    </span>
                                )}
                            </div>

                                <div className="form-select">
                                    <select name="n_tipo_mercado"
                                            value={consultaDataAccInst?.body?.acciones?.n_tipo_mercado ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_tipo_mercado}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'TIPO_MERCADO').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_tipo_mercado">
                                        Tipo Mercado
                                    </label>
                                    {fieldRequiredAccInst.n_tipo_mercado && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Tipo Mercado
                                        </span>
                                    )}
                                </div>
                            {/* </div>
                            <div className="mt-0 form-cols-2"> */}
                                <div className="form-select">
                                    <select name="n_pais"
                                            value={consultaDataAccInst?.body?.acciones.n_pais ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_pais}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'PAIS').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_pais">
                                        Pais
                                    </label>
                                    {fieldRequiredAccInst.n_pais && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Pais
                                        </span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select name="n_fam_instrumento"
                                            value={consultaDataAccInst?.body?.acciones.n_fam_instrumento ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_fam_instrumento}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'FAMILIA_INSTRUMENTO').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_fam_instrumento">
                                        Familia Instrumento
                                    </label>
                                    {fieldRequiredAccInst.n_fam_instrumento && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Familia Instrumento
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-title">
                                <span>Caracteristicas</span>
                            </div>
                            <div className="mt-1 form-cols-2">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_ticker"
                                        value={consultaDataAccInst?.body?.acciones.s_ticker ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        required
                                        onFocus={() => handleFocus('s_ticker')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_ticker')}
                                    />
                                    <label htmlFor="s_ticker">
                                        Ticker Bloomberg
                                    </label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_ric"
                                        value={consultaDataAccInst?.body?.acciones.s_ric ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        required
                                        onFocus={() => handleFocus('s_ric')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_ric')}
                                    />
                                    <label htmlFor="s_ric">
                                        Ric Reuters
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2">
                                <div className="form-input">
                                    <input
                                        type="number"
                                        name="n_precio"
                                        value={consultaDataAccInst?.body?.acciones.n_precio ?? "0.000000"}
                                        onChange={handleChange}
                                        required
                                        ref={requeridosAcc.n_precio}
                                        onFocus={() => handleFocus('n_precio')}
                                        onBlur={handleBlur}
                                        style={sendStyle('n_precio')}
                                    />
                                    <label htmlFor="n_precio">
                                        Precio
                                    </label>
                                    {fieldRequiredAccInst.n_precio && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Precio
                                        </span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select name="n_moneda"
                                            value={consultaDataAccInst?.body?.acciones.n_moneda ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_moneda}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'MONEDA').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_moneda">
                                        Moneda
                                    </label>
                                    {fieldRequiredAccInst.n_moneda && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Moneda
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-2">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_isin"
                                        value={consultaDataAccInst?.body?.acciones.s_isin ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        required
                                        ref={requeridosAcc.s_isin}
                                        onFocus={() => handleFocus('s_isin')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_isin')}
                                    />
                                    <label htmlFor="s_isin">
                                        ISIN
                                    </label>
                                    {fieldRequiredAccInst.s_isin && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido ISIN
                                        </span>
                                    )}
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_sedol"
                                        value={consultaDataAccInst?.body?.acciones.s_sedol ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        required
                                        onFocus={() => handleFocus('s_sedol')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_sedol')}
                                    />
                                    <label htmlFor="s_sedol">
                                        SEDOL
                                    </label>
                                </div>
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_cusip"
                                        value={consultaDataAccInst?.body?.acciones.s_cusip ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        required
                                        onFocus={() => handleFocus('s_cusip')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_cusip')}
                                    />
                                    <label htmlFor="s_cusip">
                                        CUSIP
                                    </label>
                                </div>
                            </div>
                            <div className="form-cols-2">
                                <div className="form-select">
                                    <select name="n_status"
                                            value={consultaDataAccInst?.body?.acciones.n_status ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_status}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'STATUS').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_status">
                                        Status
                                    </label>
                                    {fieldRequiredAccInst.n_status && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Status
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-2">
                                <div className="form-select">
                                    <select name="n_crv_desc"
                                            value={consultaDataAccInst?.body?.acciones.n_crv_desc ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_crv_desc}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'CRV_DESCUENTO').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_crv_desc">
                                        Curva Descuento
                                    </label>
                                    {fieldRequiredAccInst.n_crv_desc && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Curva Descuento
                                        </span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select name="n_crv_ref"
                                            value={consultaDataAccInst?.body?.acciones.n_crv_ref ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_crv_ref}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'CRV_REFERENCIA').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_crv_ref">
                                        Curva Referencia
                                    </label>
                                    {fieldRequiredAccInst.n_crv_ref && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Curva Referencia
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-2">
                                <div className="form-input">
                                    <input
                                        type="text"
                                        name="s_dividendo"
                                        value={consultaDataAccInst?.body?.acciones.s_dividendo ?? ""}
                                        onChange={handleChange}
                                        placeholder=""
                                        required
                                        ref={requeridosAcc.s_dividendo}
                                        onFocus={() => handleFocus('s_dividendo')}
                                        onBlur={handleBlur}
                                        style={sendStyle('s_dividendo')}
                                    />
                                    <label htmlFor="s_dividendo">
                                        Dividendo
                                    </label>
                                    {fieldRequiredAccInst.s_dividendo && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Dividendo
                                        </span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select name="n_tipo_rv"
                                            value={consultaDataAccInst?.body?.acciones.n_tipo_rv ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_tipo_rv}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'TIPO_RVARIABLE').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_tipo_rv">
                                        Tipo Renta Variable
                                    </label>
                                    {fieldRequiredAccInst.n_tipo_rv && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Tipo Renta Variable
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-2">
                                <div className="form-select">
                                    <select name="n_equ_sec"
                                            value={consultaDataAccInst?.body?.acciones.n_equ_sec ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_equ_sec}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'EQUITY_SECTOR').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_equ_sec">
                                        Equity Sector
                                    </label>
                                    {fieldRequiredAccInst.n_equ_sec && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Equity Sector
                                        </span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select name="n_pais_o"
                                            value={consultaDataAccInst?.body?.acciones.n_pais_o ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_pais_o}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'PAIS').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_pais_o">
                                        Pais Operacion
                                    </label>
                                    {fieldRequiredAccInst.n_pais_o && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Pais Operación
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-2">
                                <div className="form-select">
                                    <select name="n_theo_model"
                                            value={consultaDataAccInst?.body?.acciones.n_theo_model ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_theo_model}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'THEO_MODEL').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_theo_model">
                                        Theo Model
                                    </label>
                                    {fieldRequiredAccInst.n_theo_model && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Theo Model
                                        </span>
                                    )}
                                </div>
                                <div className="form-select">
                                    <select name="n_mkt_model"
                                            value={consultaDataAccInst?.body?.acciones.n_mkt_model ?? 'default'}
                                            onChange={handleChange}
                                            ref={requeridosAcc.n_mkt_model}
                                    >
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'MARKET_MODEL').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_mkt_model">
                                        Mkt Model
                                    </label>
                                    {fieldRequiredAccInst.n_mkt_model && (
                                        <span className="fontError animate__animated animate__fadeIn">
                                            Campo requerido Mkt Model
                                        </span>
                                    )}
                                </div>
                            </div>
                            <div className="form-cols-1">
                                <div className="form-select">
                                    <select name="n_emisor"
                                            value={consultaDataAccInst?.body?.acciones.n_emisor ?? 'default'}
                                            onChange={handleChange}>
                                        <option value="default">...</option>
                                        {getCatalogs(catalog, 'EMISOR').map((column) => (
                                            <option key={column[0]} value={column[0]}>
                                                {column[1]}
                                            </option>
                                        ))}
                                    </select>
                                    <label htmlFor="n_emisor">
                                        Emisor
                                    </label>
                                </div>
                            </div>
                    </div>

                    <div className="form-cols-2">

                        <div className="mb-14">
                            {loadingDividendos &&
                                <BarLoader
                                    className="w-full mt-2 mb-2"
                                    color="#059669"
                                    width={500}
                                />
                            }
                            <InstrumentosTable data={dividendosTable}/>
                        </div>

                        {showPrecalc &&
                            <div className="animate__animated animate__fadeIn">
                                <div className="form-cols-1">
                                    <InstrumentosPreCalc loadingPrecalc={loadingPrecalc} />
                                </div>
                            </div>
                        }
                    </div>

                </div>
            </div>
        </form>
    )
}