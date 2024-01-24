import React from "react";
import {ButtonContent, Modal} from "../../../../../shared";
import {getCatalogs} from "../../../../../utils";
import {useModalCaracTvForm} from "./hooks";

interface ModalProps {
    isOpenModal: boolean
    handleCloseCaracCv: () => void
}
export const ModalCaracTvForm = ({isOpenModal, handleCloseCaracCv}: ModalProps) => {

    const {
        loading,
        catalog,
        consultaDataAccInst,
        handleSave,
        handleChange
    } = useModalCaracTvForm()

    return (
            <Modal isOpen={isOpenModal} onClose={handleCloseCaracCv} title="" modalSize="large">
                <div className="form-title">
                    <span>Caracteristicas</span>
                </div>

                <form onSubmit={handleSave}>
                    <div className="form-dialog">
                        <div className="form-cols-2">
                            <div className="form-cols-22">
                                <label htmlFor="n_actividad">
                                    Actividad
                                </label>
                                <select
                                    className="w-5"
                                    name="n_actividad"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_actividad ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_ACTIVIDAD').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_categoria">
                                    Categoría
                                </label>
                                <select
                                    className="w-5"
                                    name="n_categoria"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_categoria ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_CATEGORIA').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>

                        <div className="mt-2 form-cols-2">
                            <div className="form-cols-22">
                                <label htmlFor="n_subramo">
                                    Subramo
                                </label>
                                <select
                                    className="w-5"
                                    name="n_subramo"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_subramo ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_CATEGORIA').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>

                        <div className="mt-2 form-cols-2">
                            <div className="form-cols-22">
                                <label htmlFor="n_ramo">
                                    Ramo
                                </label>
                                <select
                                    className="w-5"
                                    name="n_ramo"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_ramo ?? 'default'}
                                    disabled={true}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'RAMO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>

                        <div className="mt-2 form-cols-2">
                            <div className="form-cols-22">
                                <label htmlFor="n_sector">
                                    Sector
                                </label>
                                <select
                                    className="w-5"
                                    name="n_sector"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_sector ?? 'default'}
                                    disabled={true}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'SECTOR').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>


                        <div className="mt-2 form-cols-2">
                            <div className="form-cols-22">
                                <label htmlFor="n_subsector">
                                    Subsector
                                </label>
                                <select
                                    className="w-5"
                                    name="n_subsector"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_subsector ?? 'default'}
                                    disabled={true}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'SUBSECTOR').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>


                        <div className="mt-2 form-cols-2">
                            <div className="form-cols-22">
                                <label htmlFor="n_emisor_bacc">
                                    Emisor
                                </label>
                                <select
                                    className="w-5"
                                    name="n_emisor_bacc"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_emisor_bacc ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_EMISOR').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="mt-2 form-cols-22">
                                <label htmlFor="s_bursatilidad">
                                    Bursatilidad
                                </label>
                                <select
                                    className="w-5"
                                    name="s_bursatilidad"
                                    value={consultaDataAccInst?.body?.accionesAdd?.s_bursatilidad ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'bursatilidad').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="mt-2 form-cols-22">
                                <label htmlFor="n_foco_geo">
                                    Foco Geográfico
                                </label>
                                <select
                                    className="w-5"
                                    name="n_foco_geo"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_foco_geo ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_FOCO_GEOGRAFICO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_frec_dvd">
                                    Frecuencia Dvd
                                </label>
                                <select
                                    className="w-5"
                                    name="n_frec_dvd"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_frec_dvd ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_FRECUENCIA_DVD').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_mdo_cto">
                                    Mercado Corto
                                </label>
                                <select
                                    className="w-5"
                                    name="n_mdo_cto"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_mdo_cto ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_MDO_CORTO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_mdo_lgo">
                                    Mercado Largo
                                </label>
                                <select
                                    className="w-5"
                                    name="n_mdo_lgo"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_mdo_lgo ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_MDO_LARGO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>

                        <div className="mt-2 form-cols-2">
                            <div className="form-cols-22">
                                <label htmlFor="n_subramo_ing">
                                    Subramo Ing
                                </label>
                                <select
                                    className="w-5"
                                    name="n_subramo_ing"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_subramo_ing ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_SUBRAMO_ING').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>

                        <div className="form-cols-2">
                            <div className="form-cols-22">
                                <label htmlFor="n_tipo_derecho">
                                    Tipo Derecho
                                </label>
                                <select
                                    className="w-5"
                                    name="n_tipo_derecho"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_tipo_derecho ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_TIPO_DERECHO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <div className="form-cols-22">
                                <label htmlFor="n_tipo_dvd">
                                    Tipo Dividendo
                                </label>
                                <select
                                    className="w-5"
                                    name="n_tipo_dvd"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_tipo_dvd ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_TIPO_DIVIDENDO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_tipo_fondo">
                                    Tipo Fondo
                                </label>
                                <select
                                    className="w-5"
                                    name="n_tipo_fondo"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_tipo_fondo ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_TIPO_FONDO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_tipo_instr_bacc">
                                    Tipo Instr
                                </label>
                                <select
                                    className="w-5"
                                    name="n_tipo_instr_bacc"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_tipo_instr_bacc ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_TIPO_INSTRUMENTO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_tipo_pago">
                                    Tipo Pago
                                </label>
                                <select
                                    className="w-5"
                                    name="n_tipo_pago"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_tipo_pago ?? 'default'}
                                    onChange={handleChange}
                                >
                                    <option value="default">...</option>
                                    {getCatalogs(catalog, 'BACC_TIPO_PAGO').map((column) => (
                                        <option key={column[0]} value={column[0]}>
                                            {column[1]}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_acc_inscritas">
                                    Acciones Instruidas
                                </label>
                                <input
                                    className="w-5"
                                    name="n_acc_inscritas"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_acc_inscritas ?? ''}
                                    onChange={handleChange}
                                    type="text"/>

                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_volumen">
                                    Volumen
                                </label>
                                <input
                                    className="w-5"
                                    name="n_volumen"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_volumen ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_movimientos">
                                    Movimientos
                                </label>
                                <input
                                    className="w-5"
                                    name="n_movimientos"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_movimientos ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_vol_bid">
                                    Volumen Bid
                                </label>
                                <input
                                    className="w-5"
                                    name="n_vol_bid"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_vol_bid ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_vol_ask">
                                    Volumen Ask
                                </label>
                                <input
                                    className="w-5"
                                    name="n_vol_ask"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_vol_bid ?? ''}
                                    type="text"
                                    onChange={handleChange}/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_hu">
                                    UH
                                </label>
                                <input
                                    className="w-5"
                                    name="n_hu"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_hu ?? ''}
                                    type="text"
                                    onChange={handleChange}/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_precio_bid">
                                    Precio Bid
                                </label>
                                <input
                                    className="w-5"
                                    name="n_precio_bid"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_precio_bid ?? ''}
                                    type="text"
                                    onChange={handleChange}/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_precio_ask">
                                    Precio Ask
                                </label>
                                <input
                                    className="w-5"
                                    type="text"
                                    name="n_precio_ask"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_precio_ask ?? ''}
                                    onChange={handleChange}/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="d_fecha_uh">
                                    Fecha UH
                                </label>
                                <input
                                    className="w-5"
                                    type="date"
                                    name="d_fecha_uh"
                                    value={consultaDataAccInst?.body?.accionesAdd?.d_fecha_uh ?? ''}
                                    onChange={handleChange}/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_cambio_procentual_1d">
                                    Cambio Porcentual 1D
                                </label>
                                <input
                                    className="w-5"
                                    type="text"
                                    name="n_cambio_procentual_1d"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_cambio_procentual_1d ?? ''}
                                    onChange={handleChange}/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_cambios_puntos_1d">
                                    Cambio Puntos 1D
                                </label>
                                <input
                                    className="w-5"
                                    name="n_cambios_puntos_1d"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_cambios_puntos_1d ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_cambio_procentual_1a">
                                    Cambio Porcentual 1A
                                </label>
                                <input
                                    className="w-5"
                                    name="n_cambio_procentual_1a"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_cambio_procentual_1a ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_cambios_puntos_1a">
                                    Cambio Puntos 1A
                                </label>
                                <input
                                    className="w-5"
                                    name="n_cambios_puntos_1a"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_cambios_puntos_1a ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_precio_mercado">
                                    Precio Mercado
                                </label>
                                <input
                                    className="w-5"
                                    name="n_precio_mercado"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_precio_mercado ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="d_fecha_cierre">
                                    Fecha Cierre
                                </label>
                                <input 
                                    className="w-5"
                                    name="d_fecha_cierre"
                                    value={consultaDataAccInst?.body?.accionesAdd?.d_fecha_cierre ?? ''}
                                    onChange={handleChange}
                                    type="date"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_valor_libros">
                                    Valor en Libros
                                </label>
                                <input
                                    className="w-5"
                                    name="n_valor_libros"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_valor_libros ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_dividendo">
                                    Dividendo
                                </label>
                                <input
                                    className="w-5"
                                    name="n_dividendo"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_dividendo ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="d_fecha_anuncio">
                                    Fecha Anuncio
                                </label>
                                <input
                                    className="w-5"
                                    name="d_fecha_anuncio"
                                    value={consultaDataAccInst?.body?.accionesAdd?.d_fecha_anuncio ?? ''}
                                    onChange={handleChange}
                                    type="date"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="d_fecha_de_aplicaion">
                                    Fecha Aplicacion
                                </label>
                                <input
                                    className="w-5"
                                    name="d_fecha_de_aplicaion"
                                    value={consultaDataAccInst?.body?.accionesAdd?.d_fecha_de_aplicaion ?? ''}
                                    onChange={handleChange}
                                    type="date"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="d_fecha_de_pago">
                                    Fecha de Pago
                                </label>
                                <input
                                    className="w-5"
                                    name="d_fecha_de_pago"
                                    value={consultaDataAccInst?.body?.accionesAdd?.d_fecha_de_pago ?? ''}
                                    onChange={handleChange}
                                    type="date"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="d_fecha_de_anuncio">
                                    Fecha de Anuncio
                                </label>
                                <input
                                    className="w-5"
                                    name="d_fecha_de_anuncio"
                                    value={consultaDataAccInst?.body?.accionesAdd?.d_fecha_de_anuncio ?? ''}
                                    onChange={handleChange}
                                    type="date"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="d_fecha_de_aplicaion">
                                    Fecha de Aplicacion
                                </label>
                                <input
                                    className="w-5"
                                    type="date"
                                    name="d_fecha_de_aplicaion"
                                    value={consultaDataAccInst?.body?.accionesAdd?.d_fecha_de_aplicaion ?? ''}
                                    onChange={handleChange}/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="d_fecha_de_liquidacion">
                                    Fecha de Liquidacion
                                </label>
                                <input
                                    className="w-5"
                                    name="d_fecha_de_liquidacion"
                                    value={consultaDataAccInst?.body?.accionesAdd?.d_fecha_de_liquidacion ?? ''}
                                    onChange={handleChange}
                                    type="date"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_factor_de_ajuste">
                                    Factor de Ajuste
                                </label>
                                <input
                                    className="w-5"
                                    name="n_factor_de_ajuste"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_factor_de_ajuste ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_acciones_anteriores">
                                    Acciones Anteriores
                                </label>
                                <input
                                    className="w-5"
                                    name="n_acciones_anteriores"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_acciones_anteriores ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_acciones_nuevas">
                                    Acciones Nuevas
                                </label>
                                <input
                                    className="w-5"
                                    name="n_acciones_nuevas"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_acciones_nuevas ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_proporcion">
                                    Proporcion
                                </label>
                                <input
                                    className="w-5"
                                    name="n_proporcion"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_proporcion ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_indice_principal">
                                    Indice Principal
                                </label>
                                <input
                                    className="w-5"
                                    name="n_indice_principal"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_indice_principal ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                            <div className="form-cols-22">
                                <label htmlFor="n_acciones_circulacion">
                                    Acciones en Circulacion
                                </label>
                                <input
                                    className="w-5"
                                    name="n_acciones_circulacion"
                                    value={consultaDataAccInst?.body?.accionesAdd?.n_acciones_circulacion ?? ''}
                                    onChange={handleChange}
                                    type="text"/>
                            </div>
                        </div>
                        <div className="line"/>

                        <div className="flex justify-center">
                            <button type="submit"
                                    className="btn">
                                <ButtonContent name="Guardar" loading={loading}></ButtonContent>
                            </button>
                        </div>
                    </div>
                </form>
            </Modal>
    );
};