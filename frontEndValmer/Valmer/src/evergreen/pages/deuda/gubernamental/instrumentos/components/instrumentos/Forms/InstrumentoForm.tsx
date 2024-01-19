import {MoonLoader} from 'react-spinners';
import React from "react";
import {useHandleData, useTvEmiSerie} from "./hooks";
import {ButtonContent, TvEmiSerieOptions} from "../../../../../../../../shared";
import {getCatalogs} from "../../../../../../../../utils";
import { useBigInput } from '../../../../../tasas/components/forms/hooks/useBigInput';

export const InstrumentoForm = ({requeridos}: any) => {

    //  Achica o agranda el input del form cuando obtiene o deja el focus
    const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
    const {
        triggerErase,
        isNewGubForm,
        isNewSerieGub,
        catalog,
        loading,
        tv,
        loadingTv,
        selectedTv,
        selectedEmisora,
        selectedSerie,
        requiredTv,
        requiredEmisora,
        requiredSerie,
        loadingEmisoras,
        loadingSerie,
        loadingConsultaData,
        emisora,
        serie,
        consultaData,
        handleClickTv,
        handleEmisora,
        handleSerie,
    } = useTvEmiSerie()


    const {
        loadingSubmitInstRW,
        isFieldModified,
        formRef,
        fieldRequired,
        inputValueEval,
        checkboxValueEval,
        handleChange,
        handleNumericChange,
        handleTvNewForm,
        handleCheckboxChange,
        handleSubmitRW } = useHandleData({requeridos});

    const isESG = checkboxValueEval("b_esg", consultaData);

    if (loading || loadingTv || !catalog.length) {
        return (
            <div className="flex justify-center items-center h-full">
                {loading || loadingTv ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    if (triggerErase) {
        return (
            <div className="flex justify-center items-center h-full"></div>
        );
    }

    return (
        <>
            <form ref={formRef}>
                <div className="px-1">
                    <TvEmiSerieOptions isNewFormInst={isNewGubForm}
                                       isNewSerie={isNewSerieGub}
                                       loadingConsultaData={loadingConsultaData}
                                       loadingEmisoras={loadingEmisoras}
                                       loadingSerie={loadingSerie}
                                       selectedTv={selectedTv}
                                       selectedEmisora={selectedEmisora}
                                       selectedSerie={selectedSerie}
                                       requiredTv={requiredTv}
                                       requiredEmisora={requiredEmisora}
                                       requiredSerie={requiredSerie}
                                       tv={tv}
                                       emisora={emisora}
                                       serie={serie}
                                       isFieldModified={isFieldModified}
                                       consultaData={consultaData}
                                       requeridos={requeridos}
                                       inputValueEval={inputValueEval}
                                       handleChange={handleChange}
                                       handleTvNewForm={handleTvNewForm}
                                       handleClickTv={handleClickTv}
                                       handleEmisora={handleEmisora}
                                       handleSerie={handleSerie}/>
                    <div className="form-cols-6">
                        <div className="form-select">
                            <select
                                name="n_tipo_instrumento"
                                value = {consultaData.body?.n_tipo_instrumento || ''}
                                onChange={(e) => handleChange(e)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'TIPO_INSTRUMENTO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_tipo_instrumento'>
                                Tipo Instrumento
                            </label>
                            {fieldRequired.n_tipo_instrumento && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_tipo_instrumento</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_familia_instrumento"
                                value = {consultaData.body?.n_familia_instrumento || ''}
                                onChange={(e) => handleChange(e)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'FAMILIA_INSTRUMENTO-GUBER').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_familia_instrumento'>
                                Familia Instrumento
                            </label>
                            {fieldRequired.n_familia_instrumento && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_familia_instrumento</span>
                            )}
                        </div>
                 
                        <div className="form-select">
                            <select
                                name="n_tipo_mercado"
                                value = {consultaData.body?.n_tipo_mercado || ''}
                                onChange={(e) => handleChange(e)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'TIPO_MERCADO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_tipo_mercado'>
                                Tipo Mercado
                            </label>
                            {fieldRequired.n_tipo_mercado && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_tipo_mercado</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_pais"
                                value = {consultaData.body?.n_pais || ''}
                                onChange={(e) => handleChange(e)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'PAIS').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_pais'>
                                País
                            </label>
                            {fieldRequired.n_pais && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_pais</span>
                            )}
                        </div>
                    
                        <div className="form-select">
                            <select
                                name="n_moneda"
                                value = {consultaData.body?.n_moneda || ''}
                                onChange={(e) => handleChange(e)}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'MONEDA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_moneda'>
                                Moneda
                            </label>
                            {fieldRequired.n_moneda && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_moneda</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_isin"
                                value={consultaData.body?.s_isin || ''}
                                onChange={handleChange}
                                placeholder=""
                                maxLength={12}
                                required
                                onFocus={() => handleFocus('s_isin')}
                                onBlur={handleBlur}
                                style={sendStyle('s_isin')}
                            />
                            <label htmlFor='s_isin'>
                                ISIN
                            </label>
                        </div>
                        <div className="form-select">
                            <select id='n_pais_riesgo'
                                    name="n_pais_riesgo"
                                    value = {consultaData.body?.n_pais_riesgo || ''}
                                    onChange={handleChange}
                                    required
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'PAIS').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="n_pais_riesgo">
                                País de Riesgo
                            </label>
                            {fieldRequired.n_pais_riesgo && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_pais_riesgo</span>
                            )}
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                id='b_rnv'
                                name="b_rnv"
                                checked={checkboxValueEval("b_rnv", consultaData)}
                                onChange={(e) => handleCheckboxChange(e)}
                            />
                            <label htmlFor='b_rnv'>
                                RNV
                            </label>
                        </div>
                    </div>
                </div>

                <div className="px-3">
                    <div className="bg-cyan-700 p-1 text-slate-50">
                        <span>Características</span>
                    </div>
                    <div className="form-cols-6">
                        <div className="form-select">
                            <select
                                name="n_emisor"
                                value = {consultaData.body?.n_emisor || ''}
                                onChange={handleChange}
                                required>
                                <option value="default">...</option>
                                {getCatalogs(catalog,'EMISOR').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_emisor'>
                                Emisor
                            </label>
                            {fieldRequired.n_emisor && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_emisor</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_crv_descuento"
                                value = {consultaData.body?.n_crv_descuento || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'CRV_DESCUENTO').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_crv_descuento'>
                                Curva Descuento
                            </label>
                            {fieldRequired.n_crv_descuento && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_crv_descuento</span>
                            )}
                        </div>
                  
                        <div className="form-select">
                            <select
                                name="n_representante_comun"
                                value = {consultaData.body?.n_representante_comun || ''}
                                onChange={handleChange}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'REPRESENTANTE_COMUN').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_representante_comun'>
                                Representante Común
                            </label>
                            {fieldRequired.n_representante_comun && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_representante_comun</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_agente_colocador"
                                value = {consultaData.body?.n_agente_colocador || ''}
                                onChange={handleChange}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'AGENTE_COLOCADOR').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_agente_colocador'>
                                Agente Colocador
                            </label>
                            {fieldRequired.n_agente_colocador && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_agente_colocador</span>
                            )}
                        </div>
                  
                        <div className="form-select">
                            <select
                                name="n_tasa_referencia"
                                value = {consultaData.body?.n_tasa_referencia || ''}
                                onChange={handleChange}
                                required
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'CRV_REFERENCIA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_tasa_referencia'>
                                Tasa Referencia
                            </label>
                            {fieldRequired.n_tasa_referencia && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_tasa_referencia</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_valor_nominal"
                                placeholder=''
                                value={consultaData.body?.n_valor_nominal || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                onFocus={() => handleFocus('n_valor_nominal')}
                                onBlur={handleBlur}
                                style={sendStyle('n_valor_nominal')}
                            />
                            <label htmlFor='n_valor_nominal'>
                                Valor Nominal
                            </label>
                            {fieldRequired.n_valor_nominal && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_valor_nominal</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_convencion_dias"
                                value = {consultaData.body?.n_convencion_dias || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'CONVENCION_DIAS').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_convencion_dias'>
                                Convención Dias
                            </label>
                            {fieldRequired.n_convencion_dias && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_convencion_dias</span>
                            )}
                        </div>
                   
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_emision"
                                value = {consultaData.body?.d_fecha_emision || ''}
                                onChange={handleChange}
                            />
                            <label htmlFor='d_fecha_emision'>
                                Fecha Emisión
                            </label>
                            {fieldRequired.d_fecha_emision && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido d_fecha_emision</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                placeholder=""
                                name="n_plazo"
                                value={consultaData.body?.n_plazo || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                required
                                onFocus={() => handleFocus('n_plazo')}
                                onBlur={handleBlur}
                                style={sendStyle('n_plazo')}
                            />
                            <label htmlFor='n_plazo'>
                                Plazo
                            </label>
                            {fieldRequired.n_plazo && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_plazo</span>
                            )}
                        </div>
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_fecha_vto"
                                value = {consultaData.body?.d_fecha_vto || ''}
                                onChange={handleChange}
                            />
                            <label htmlFor='d_fecha_vto'>
                                Fecha Vencimiento
                            </label>
                            {fieldRequired.d_fecha_vto && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido d_fecha_vto</span>
                            )}
                        </div>
                   
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_titulos_circulacion"
                                value={consultaData.body?.n_titulos_circulacion || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                placeholder=""
                                required
                                onFocus={() => handleFocus('n_titulos_circulacion')}
                                onBlur={handleBlur}
                                style={sendStyle('n_titulos_circulacion')}
                            />
                            <label htmlFor='n_titulos_circulacion'>
                                Títulos
                            </label>
                            {fieldRequired.n_titulos_circulacion && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_titulos_circulacion</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_monto_emi"
                                value={consultaData.body?.n_monto_emi || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                placeholder=""
                                required
                                onFocus={() => handleFocus('n_monto_emi')}
                                onBlur={handleBlur}
                                style={sendStyle('n_monto_emi')}
                            />
                            <label htmlFor='n_monto_emi'>
                                Monto Emi.
                            </label>
                            {fieldRequired.n_monto_emi && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_monto_emi</span>
                            )}
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_monto_circ"
                                value={consultaData.body?.n_monto_circ || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                placeholder=""
                                required
                                onFocus={() => handleFocus('n_monto_circ')}
                                onBlur={handleBlur}
                                style={sendStyle('n_monto_circ')}
                            />
                            <label htmlFor="n_monto_circ">
                                Monto Cir.
                            </label>
                            {fieldRequired.n_monto_circ && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_monto_circ</span>
                            )}
                        </div>
                  
                        <div className="form-select">
                            <select
                                name="n_tipo_tasa"
                                value = {consultaData.body?.n_tipo_tasa || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'TIPO_TASA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_tipo_tasa'>
                                Tipo Tasa
                            </label>
                            {fieldRequired.n_tipo_tasa && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_tipo_tasa</span>
                            )}
                        </div>
                        <div className="form-select">
                            <select
                                name="n_nomb_tasa"
                                value = {consultaData.body?.n_nomb_tasa || consultaData.body?.s_tasa || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'TIPO_INSTRUMENTO-NOMBRE_TASA').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_nomb_tasa'>
                                Nomb. Tasa
                            </label>
                        </div>
                  
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_prima"
                                value={consultaData.body?.n_prima || ''}
                                onChange={handleChange}
                                placeholder=""
                                required
                                onFocus={() => handleFocus('n_prima')}
                                onBlur={handleBlur}
                                style={sendStyle('n_prima')}
                            />
                            <label htmlFor='n_prima'>
                                Prima
                            </label>
                        </div>
                        <div className="form-check">
                            <input
                                type="checkbox"
                                id='b_proteccion_inflacionaria'
                                name="b_proteccion_inflacionaria"
                                checked={checkboxValueEval(
                                    "b_proteccion_inflacionaria", consultaData)}
                                onChange={(e) => handleCheckboxChange(e)}
                            />
                            <label htmlFor='b_proteccion_inflacionaria'>
                                Protección Inflacionaria
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="n_composicion_yield"
                                value = {consultaData.body?.n_composicion_yield || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'COMPOSICION_YIELD').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_composicion_yield'>
                                Composición Yield
                            </label>
                            {fieldRequired.n_composicion_yield && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_composicion_yield</span>
                            )}
                        </div>
                    </div>
                    <div className="form-cols-4">
                        <div className="form-select">
                            <select
                                name="n_forma_cotizacion"
                                value = {consultaData.body?.n_forma_cotizacion || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'FORMA_COTIZACION').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_forma_cotizacion'>
                                Forma Cotización
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_cusip"
                                value={consultaData.body?.s_cusip || ''}
                                onChange={handleChange}
                                placeholder=""
                                maxLength={9}
                                required
                                onFocus={() => handleFocus('s_cusip')}
                                onBlur={handleBlur}
                                style={sendStyle('s_cusip')}
                            />
                            <label htmlFor='s_cusip'>
                                CUSIP
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_sedol"
                                value={consultaData.body?.s_sedol || ''}
                                onChange={handleChange}
                                placeholder=""
                                maxLength={7}
                                required
                                onFocus={() => handleFocus('s_sedol')}
                                onBlur={handleBlur}
                                style={sendStyle('s_sedol')}
                            />
                            <label htmlFor='s_sedol'>
                                SEDOL
                            </label>
                        </div>
                   
                        <div className="form-select">
                            <select
                                name="n_status"
                                value = {consultaData.body?.n_status || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'STATUS').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_status'>
                                Status
                            </label>
                            {fieldRequired.n_status && (
                                <span className="fontError animate__animated animate__fadeIn">Campo requerido n_status</span>
                            )}
                        </div>
                  
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_last_reset_rate"
                                placeholder=''
                                value={consultaData.body?.n_last_reset_rate || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                required
                                onFocus={() => handleFocus('n_last_reset_rate')}
                                onBlur={handleBlur}
                                style={sendStyle('n_last_reset_rate')}
                            />
                            <label htmlFor='n_last_reset_rate'>
                                Last Reset Rate
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_last_reset_rate_24h"
                                value={consultaData.body?.n_last_reset_rate_24h || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                placeholder=""
                                required
                                onFocus={() => handleFocus('n_last_reset_rate_24h')}
                                onBlur={handleBlur}
                                style={sendStyle('n_last_reset_rate_24h')}
                            />
                            <label htmlFor='n_last_reset_rate_24h'>
                                Last Reset Rate 24
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_tasa_mercado"
                                value={consultaData.body?.n_tasa_mercado || ''}
                                onChange={handleChange}
                                placeholder=""
                                required
                                onFocus={() => handleFocus('n_tasa_mercado')}
                                onBlur={handleBlur}
                                style={sendStyle('n_tasa_mercado')}
                            />
                            <label htmlFor='n_tasa_mercado'>
                                Tasa de Mercado
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_rendimiento"
                                value={consultaData.body?.n_rendimiento || ''}
                                onChange={handleChange}
                                placeholder=""
                                required
                                onFocus={() => handleFocus('n_rendimiento')}
                                onBlur={handleBlur}
                                style={sendStyle('n_rendimiento')}
                            />
                            <label htmlFor="n_rendimiento">
                                Tasa de Rendimiento
                            </label>
                        </div>
                 
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_precio_mercado"
                                value={consultaData.body?.n_precio_mercado || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                placeholder=""
                                required
                                onFocus={() => handleFocus('n_precio_mercado')}
                                onBlur={handleBlur}
                                style={sendStyle('n_precio_mercado')}
                            />
                            <label htmlFor='n_precio_mercado'>
                                Precio Mercado
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_precio_mercado_24h"
                                value={consultaData.body?.n_precio_mercado_24h || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                placeholder=""
                                required
                                onFocus={() => handleFocus('n_precio_mercado_24h')}
                                onBlur={handleBlur}
                                style={sendStyle('n_precio_mercado_24h')}
                            />
                            <label htmlFor='n_precio_mercado_24h'>
                                Precio Mercado 24
                            </label>
                        </div>
                     
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_intereses_md"
                                value={consultaData.body?.n_intereses_md || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                placeholder=""
                                onFocus={() => handleFocus('n_intereses_md')}
                                onBlur={handleBlur}
                                style={sendStyle('n_intereses_md')}
                            />
                            <label htmlFor='n_intereses_md'>
                                Intereses MD
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_intereses_24h"
                                placeholder=''
                                value={consultaData.body?.n_intereses_24h || ''}
                                onChange={(e) => handleNumericChange(e, handleChange)}
                                onFocus={() => handleFocus('n_intereses_24h')}
                                onBlur={handleBlur}
                                style={sendStyle('n_intereses_24h')}
                            />
                            <label htmlFor='n_intereses_24h'>
                                Intereses 24
                            </label>
                        </div>
                   
                        <div className="form-check">
                            <input
                                type="checkbox"
                                id='b_esg'
                                name="b_esg"
                                checked={isESG}
                                onChange={(e) => handleCheckboxChange(e)}
                            />
                            <label htmlFor='b_esg'>
                                ESG
                            </label>
                        </div>

                        {isESG && (
                            <div className="form-select">
                                <select
                                    name="n_familia_esg"
                                    value = {consultaData.body?.n_familia_esg || ''}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog,'FAMILIA_ESG').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor='n_familia_esg'>
                                    Familia ESG
                                </label>
                            </div>
                        )}
                    </div>

                    {consultaData?.body && consultaData?.body?.s_serie !== '' && !isNewGubForm && (
                            <div className="mt-0 px-3">
                                <div className="bg-cyan-700 p-1 text-slate-50">
                                    <span>Instrumento Deuda</span>
                                </div>

                                <div className="form-cols-3">
                                    <div className="form-input">
                                        <input
                                            type="text"
                                            name="n_sobretasa"
                                            value={consultaData.body?.n_sobretasa || ''}
                                            onChange={(e) => handleNumericChange(e, handleChange)}
                                            placeholder=""
                                            onFocus={() => handleFocus('n_sobretasa')}
                                            onBlur={handleBlur}
                                            style={sendStyle('n_sobretasa')}
                                        />
                                        <label htmlFor='n_sobretasa'>
                                            Sobretasa de Mercado
                                        </label>
                                    </div>


                                    <div className="form-select">
                                        <select
                                            name="n_clasificacion_sectorial"
                                            value = {consultaData.body?.n_clasificacion_sectorial || ''}
                                            onChange={handleChange}
                                        >
                                            <option value="default">...</option>
                                            {getCatalogs(catalog,'CLASIFICACION_SECTORIAL').map((column) => (
                                                <option key={column[0]} value={column[0]}>
                                                    {column[1]}
                                                </option>
                                            ))}
                                        </select>
                                        <label htmlFor='n_clasificacion_sectorial'>
                                            Clas Sectorial
                                        </label>
                                    </div>
                                </div>
                            </div>
                        )
                    }

                    <div className="form-cols-1">
                        <div className="line"></div>
                    </div>
                </div>
            </form>

            <form onSubmit={handleSubmitRW}>
                <div className="px-3">
                    <div className="bg-cyan-700 p-1 text-slate-50">
                        <span>RW</span>
                    </div>
                    <div className="form-cols-6">
                        <div className="form-select">
                            <select
                                name="n_theo_model"
                                value = {consultaData.body?.n_theo_model || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'THEO_MODEL').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_theo_model'>
                                Theorical Model
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="n_market_model"
                                value = {consultaData.body?.n_market_model || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'MARKET_MODEL').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_market_model'>
                                Market Model
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="n_curve_index"
                                value = {consultaData.body?.n_curve_index || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'CURVE_INDEX').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_curve_index'>
                                Curve Index
                            </label>
                        </div>
                    
                        <div className="form-select">
                            <select
                                name="n_reset_rule"
                                value = {consultaData.body?.n_reset_rule || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'BUSINESS_RESET_RULE').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_reset_rule'>
                                Reset Rule
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="n_busdayrule"
                                value = {consultaData.body?.n_busdayrule || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                {getCatalogs(catalog,'BUSINESS_RESET_RULE').map((column) => (
                                    <option key={column[0]} value={column[0]}>
                                        {column[1]}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor='n_busdayrule'>
                                Business Day Rule
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="n_fixed_coupon_date"
                                value={consultaData.body?.n_fixed_coupon_date || ''}
                                onChange={handleChange}
                                placeholder=""
                                onFocus={() => handleFocus('n_fixed_coupon_date')}
                                onBlur={handleBlur}
                                style={sendStyle('n_fixed_coupon_date')}
                            />
                            <label htmlFor='n_fixed_coupon_date'>
                                Fixed Coupon Date
                            </label>
                        </div>
                   
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_credit_spread_curve"
                                value={consultaData.body?.s_credit_spread_curve || ''}
                                onChange={handleChange}
                                placeholder=""
                                onFocus={() => handleFocus('s_credit_spread_curve')}
                                onBlur={handleBlur}
                                style={sendStyle('s_credit_spread_curve')}
                            />
                            <label htmlFor='s_credit_spread_curve'>
                                Credit Spread Curve
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_oddfirstcoupon"
                                value={consultaData.body?.s_oddfirstcoupon || ''}
                                onChange={handleChange}
                                placeholder=""
                                onFocus={() => handleFocus('s_oddfirstcoupon')}
                                onBlur={handleBlur}
                                style={sendStyle('s_oddfirstcoupon')}
                            />
                            <label htmlFor='s_oddfirstcoupon'>
                                Odd First Coupon
                            </label>
                        </div>
                        <div className="form-input">
                            <input
                                type="text"
                                name='s_oddlastcoupon'
                                value={consultaData.body?.s_oddlastcoupon || ''}
                                onChange={handleChange}
                                placeholder=""
                                onFocus={() => handleFocus('s_oddlastcoupon')}
                                onBlur={handleBlur}
                                style={sendStyle('s_oddlastcoupon')}
                            />
                            <label htmlFor='s_oddlastcoupon'>
                                Odd Last Coupon
                            </label>
                        </div>
                  
                        <div className="form-date">
                            <input
                                type="date"
                                name="d_lastregcou"
                                value={consultaData.body?.d_lastregcou || ''}
                                onChange={handleChange}
                            />
                            <label htmlFor='d_lastregcou'>
                                Last Reg Coupon
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="b_runscfphase"
                                value = {consultaData.body?.b_runscfphase || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                <option value="0"> Falso </option>
                                <option value="1"> Verdadero </option>
                            </select>
                            <label htmlFor='b_runscfphase'>
                                Runscfphase
                            </label>
                        </div>
                        <div className="form-select">
                            <select
                                name="b_couponpro"
                                value = {consultaData.body?.b_couponpro || ''}
                                onChange={handleChange}
                            >
                                <option value="default">...</option>
                                <option value="0"> Falso </option>
                                <option value="1"> Verdadero </option>
                            </select>
                            <label htmlFor='b_couponpro'>
                                Coupon Prorated
                            </label>
                        </div>
                    </div>
                    <div className="form-cols-1">
                        <div className="form-input">
                            <input
                                type="text"
                                name="s_coupgenmthd"
                                value={consultaData.body?.s_coupgenmthd || ''}
                                onChange={handleChange}
                                placeholder=""
                                onFocus={() => handleFocus('s_coupgenmthd')}
                                onBlur={handleBlur}
                                style={sendStyle('s_coupgenmthd')}
                            />
                            <label htmlFor='s_coupgenmthd'>
                                Coupon Generation Method
                            </label>
                        </div>
                    </div>
                </div>
                <div className='mt-0 flex justify-end px-3'>
                    <button className='bg-cyan-700 hover:bg-cyan-800 text-white py-1 px-3 rounded-md'>
                        <ButtonContent name={"Guardar RW"} loading={loadingSubmitInstRW}></ButtonContent>
                    </button>
                </div>
            </form>
        </>
    );
}