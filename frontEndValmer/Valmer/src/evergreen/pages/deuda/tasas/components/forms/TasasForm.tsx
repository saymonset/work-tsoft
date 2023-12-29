import React, { useState } from "react";
import {useTasasForm, useTasasFormData} from "./hooks";
import {ButtonContent, Modal} from "../../../../../../shared";
import { useBigInput } from "./hooks/useBigInput";



export const TasasForm = ({d_fecha}: any) => {

    const {
        textUdi,
        isOpenUdi,
        loadingUdi,
        isTextArea,
        setTextUdi,
        handleCloseUdi,
        handleOpenUdi,
        handleChargeUdi} = useTasasFormData()

    const {forms, loadingSave, handleSubmit, handleChange} = useTasasForm();

    const {  handleFocus,
             handleBlur,
             sendStyle} = useBigInput();

    const [focusedInput, setFocusedInput] = useState<string>("");

    
    return (
        <div className={`p-0     w-full border border-slate-300 mb-1 animate__animated animate__fadeIn`}>

            <div className="px-3">
                <div className="form-cols-4">

                    <form onSubmit={(e) => handleSubmit(e, 'formTasHistPriCet', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasas Hist Primaria Cetes</span>
                            <div className="form-input" >
                                <input 
                                    type="text"
                                    name="n_cetes28"
                                    id="n_cetes28"
                                    value={forms['formTasHistPriCet']?.['n_cetes28'] || ''}
                                    onChange={handleChange('formTasHistPriCet')}
                                    required
                                    onFocus={() => handleFocus('n_cetes28')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_cetes28')}
                                />
                                <label 
                                    htmlFor="n_cetes28">
                                    CETES 28
                                </label>
                                </div>
                            <div className="form-input">
                                <input
                                    type="text"
                                    name="n_cetes91"
                                    id="n_cetes91"
                                    value={forms['formTasHistPriCet']?.['n_cetes91'] || ''}
                                    onChange={handleChange('formTasHistPriCet')}
                                    required
                                    onFocus={() => handleFocus('n_cetes91')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_cetes91')}
                                />
                                <label   htmlFor="n_cetes91">
                                    CETES 91
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_cetes182"
                                    id="n_cetes182"
                                    value={forms['formTasHistPriCet']?.['n_cetes182'] || ''}
                                    onChange={handleChange('formTasHistPriCet')}
                                    required
                                    onFocus={() => handleFocus('n_cetes182')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_cetes182')}
                                />
                                <label    htmlFor="n_cetes182">
                                    CETES 182
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_cetes364"
                                    id="n_cetes364"
                                    value={forms['formTasHistPriCet']?.['n_cetes364'] || ''}
                                    onChange={handleChange('formTasHistPriCet')}
                                    required
                                    onFocus={() => handleFocus('n_cetes364')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_cetes364')}
                                />
                                <label   htmlFor="n_cetes364">
                                    CETES 364
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>

                    <form onSubmit={(e) => handleSubmit(e, 'formTaHiFoGu', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasas Hist Fondeo Guber</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_fguber28"
                                    id="n_fguber28"
                                    value={forms['formTaHiFoGu']?.['n_fguber28'] || ''}
                                    onChange={handleChange('formTaHiFoGu')}
                                    required
                                    onFocus={() => handleFocus('n_fguber28')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_fguber28')}
                                />
                                <label  htmlFor="n_fguber28">
                                    Max Fondeo Vs Cetes 28
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_fguber91"
                                    id="n_fguber91"
                                    value={forms['formTaHiFoGu']?.['n_fguber91'] || ''}
                                    onChange={handleChange('formTaHiFoGu')}
                                    required
                                    onFocus={() => handleFocus('n_fguber91')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_fguber91')}
                                    
                                />
                                <label  style={{ paddingTop: '0rem', paddingBottom: '0rem' }}   htmlFor="n_fguber91">
                                    Max Fondeo Vs Cetes 91
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <form onSubmit={(e) => handleSubmit(e, 'formTasaTIIE', d_fecha)}>
                        <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasas TIIE</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tiie28"
                                    id="n_tiie28"
                                    value={forms['formTasaTIIE']?.['n_tiie28'] || ''}
                                    onChange={handleChange('formTasaTIIE')}
                                    required
                                    onFocus={() => handleFocus('n_tiie28')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tiie28')}
                                />
                                <label  htmlFor="n_tiie28">
                                    TIIE28
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tiie91"
                                    id="n_tiie91"
                                    value={forms['formTasaTIIE']?.['n_tiie91'] || ''}
                                    onChange={handleChange('formTasaTIIE')}
                                    required
                                    onFocus={() => handleFocus('n_tiie91')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tiie91')}
                                />
                                <label htmlFor="n_tiie91">
                                    TIIE91 
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tiie182"
                                    id="n_tiie182"
                                    value={forms['formTasaTIIE']?.['n_tiie182'] || ''}
                                    onChange={handleChange('formTasaTIIE')}
                                    required
                                    onFocus={() => handleFocus('n_tiie182')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tiie182')}
                                />
                                <label  htmlFor="n_tiie182">
                                    TIIE182
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>

                    <form onSubmit={(e) => handleSubmit(e, 'formTaTreYi5y', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasa Treasury Yield 5Y</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_treasury_5y"
                                    id="n_treasury_5y"
                                    value={forms['formTaTreYi5y']?.['n_treasury_5y'] || ''}
                                    onChange={handleChange('formTaTreYi5y')}
                                    required
                                    onFocus={() => handleFocus('n_treasury_5y')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_treasury_5y')}
                                />
                                <label  style={{ paddingTop: '0rem', paddingBottom: '0rem' }}   htmlFor="n_treasury_5y">
                                    TREASURY 5Y
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <form onSubmit={(e) => handleSubmit(e, 'formTaFoGu', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasas Fondeo Guber</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_fguber28d"
                                    id="n_fguber28d"
                                    value={forms['formTaFoGu']?.['n_fguber28d'] || ''}
                                    onChange={handleChange('formTaFoGu')}
                                    required
                                    onFocus={() => handleFocus('n_fguber28d')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_fguber28d')}
                                />
                                <label   htmlFor="n_fguber28d">
                                    Fondo Guber 28
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_fguber91d"
                                    id="n_fguber91d"
                                    value={forms['formTaFoGu']?.['n_fguber91d'] || ''}
                                    onChange={handleChange('formTaFoGu')}
                                    required
                                    onFocus={() => handleFocus('n_fguber91d')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_fguber91d')}
                                />
                                <label  htmlFor="n_fguber91d">
                                    Fondo Guber 91
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_fguber"
                                    id="n_fguber"
                                    placeholder=""
                                    value={forms['formTaFoGu']?.['n_fguber'] || ''}
                                    onChange={handleChange('formTaFoGu')}
                                    required
                                    onFocus={() => handleFocus('n_fguber')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_fguber')}
                                />
                                <label htmlFor="n_fguber">
                                    Fondo Guber
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <form onSubmit={(e) => handleSubmit(e, 'formFonGuBanx', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Fondeo Guber Banxico</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_apertura_guber"
                                    id="n_apertura_guber"
                                    value={forms['formFonGuBanx']?.['n_apertura_guber'] || ''}
                                    onChange={handleChange('formFonGuBanx')}
                                    required
                                    onFocus={() => handleFocus('n_apertura_guber')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_apertura_guber')}
                                />
                                <label htmlFor="n_apertura_guber">
                                    Apertura
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_min_guber"
                                    id="n_min_guber"
                                    value={forms['formFonGuBanx']?.['n_min_guber'] || ''}
                                    onChange={handleChange('formFonGuBanx')}
                                    required
                                    onFocus={() => handleFocus('n_min_guber')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_min_guber')}
                                />
                                <label  htmlFor="n_min_guber">
                                    Minimo
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_max_guber"
                                    id="n_max_guber"
                                    value={forms['formFonGuBanx']?.['n_max_guber'] || ''}
                                    onChange={handleChange('formFonGuBanx')}
                                    required
                                    onFocus={() => handleFocus('n_max_guber')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_max_guber')}
                                />
                                <label htmlFor="n_max_guber">
                                    Maximo
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_cierre_guber"
                                    id="n_cierre_guber"
                                    value={forms['formFonGuBanx']?.['n_cierre_guber'] || ''}
                                    onChange={handleChange('formFonGuBanx')}
                                    required
                                    onFocus={() => handleFocus('n_cierre_guber')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_cierre_guber')}
                                />
                                <label htmlFor="n_cierre_guber">
                                    Cierre
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_pond_guber"
                                    id="n_pond_guber"
                                    value={forms['formFonGuBanx']?.['n_pond_guber'] || ''}
                                    onChange={handleChange('formFonGuBanx')}
                                    required
                                    onFocus={() => handleFocus('n_pond_guber')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_pond_guber')}
                                />
                                <label htmlFor="n_pond_guber">
                                    Ponderado
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <form onSubmit={(e) => handleSubmit(e, 'formEuribor', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasas Euribor</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_euribor"
                                    id="n_euribor"
                                    value={forms['formEuribor']?.['n_euribor'] || ''}
                                    onChange={handleChange('formEuribor')}
                                    required
                                    onFocus={() => handleFocus('n_euribor')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_euribor')}
                                />
                                <label  htmlFor="n_euribor">
                                    EURIBOR 3M
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_euribor6m"
                                    id="n_euribor6m"
                                    value={forms['formEuribor']?.['n_euribor6m'] || ''}
                                    onChange={handleChange('formEuribor')}
                                    required
                                    onFocus={() => handleFocus('n_euribor6m')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_euribor6m')}
                                />
                                <label  htmlFor="n_euribor6m">
                                    EURIBOR 6M
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <form onSubmit={(e) => handleSubmit(e, 'formTaSwa5Y6M3M', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasa Swap 5Y 6M 3M</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_swap_5y_6m_3m"
                                    id="n_swap_5y_6m_3m"
                                    value={forms['formTaSwa5Y6M3M']?.['n_swap_5y_6m_3m'] || ''}
                                    onChange={handleChange('formTaSwa5Y6M3M')}
                                    required
                                    onFocus={() => handleFocus('n_swap_5y_6m_3m')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_swap_5y_6m_3m')}
                                />
                                <label htmlFor="n_swap_5y_6m_3m">
                                    Swap 5Y 6M 3M
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <form onSubmit={(e) => handleSubmit(e, 'formTaFoBa', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasa Fondeo Bancario</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_fbanc"
                                    id="n_fbanc"
                                    value={forms['formTaFoBa']?.['n_fbanc'] || ''}
                                    onChange={handleChange('formTaFoBa')}
                                    required
                                    onFocus={() => handleFocus('n_fbanc')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_fbanc')}
                                />
                                <label htmlFor="n_fbanc">
                                    Fondeo Bancario
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <form onSubmit={(e) => handleSubmit(e, 'formBancBanx', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Fondeo Bancario Banxico</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_apertura_banc"
                                    id="n_apertura_banc"
                                    value={forms['formBancBanx']?.['n_apertura_banc'] || ''}
                                    onChange={handleChange('formBancBanx')}
                                    required
                                    onFocus={() => handleFocus('n_apertura_banc')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_apertura_banc')}
                                />
                                <label  htmlFor="n_apertura_banc">
                                    Apertura
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_min_banc"
                                    id="n_min_banc"
                                    value={forms['formBancBanx']?.['n_min_banc'] || ''}
                                    onChange={handleChange('formBancBanx')}
                                    required
                                    onFocus={() => handleFocus('n_min_banc')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_min_banc')}
                                    />
                                <label  htmlFor="n_min_banc">
                                    Minimo
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_max_banc"
                                    id="n_max_banc"
                                    value={forms['formBancBanx']?.['n_max_banc'] || ''}
                                    onChange={handleChange('formBancBanx')}
                                    required
                                    onFocus={() => handleFocus('n_max_banc')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_max_banc')}
                                />
                                <label  htmlFor="n_max_banc">
                                    Maximo
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_cierre_banc"
                                    id="n_cierre_banc"
                                    value={forms['formBancBanx']?.['n_cierre_banc'] || ''}
                                    onChange={handleChange('formBancBanx')}
                                    required
                                    onFocus={() => handleFocus('n_cierre_banc')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_cierre_banc')}
                                />
                                <label  htmlFor="n_cierre_banc">
                                    Cierre
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_pond_banc"
                                    id="n_pond_banc"
                                    value={forms['formBancBanx']?.['n_pond_banc'] || ''}
                                    onChange={handleChange('formBancBanx')}
                                    required
                                    onFocus={() => handleFocus('n_pond_banc')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_pond_banc')}
                                />
                                <label htmlFor="n_pond_banc">
                                    Ponderado
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <div className="grid grid-rows-6 grid-cols-1">
                        <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasas UDIS</span>
                        <div className="text-center text-xs text-cyan-700">
                            <button className='bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md'
                                    onClick={handleOpenUdi}>
                                <span>Carga UDIS</span>
                            </button>
                        </div>
                    </div>

                    <form onSubmit={(e) => handleSubmit(e, 'formTaIruFeFu', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasa IRUSD Fed Funds</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_irusd_fed_funds"
                                    id="n_irusd_fed_funds"
                                    value={forms['formTaIruFeFu']?.['n_irusd_fed_funds'] || ''}
                                    onChange={handleChange('formTaIruFeFu')}
                                    required
                                    onFocus={() => handleFocus('n_irusd_fed_funds')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_irusd_fed_funds')}
                                />
                                <label htmlFor="n_irusd_fed_funds">
                                    IRUSD_FED_FUNDS
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <form onSubmit={(e) => handleSubmit(e, 'formTermSofr', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Term SOFR</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tsofr1M"
                                    id="n_tsofr1M"
                                    value={forms['formTermSofr']?.['n_tsofr1M'] || ''}
                                    onChange={handleChange('formTermSofr')}
                                    required
                                    onFocus={() => handleFocus('n_tsofr1M')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tsofr1M')}
                                />
                                <label htmlFor="n_tsofr1M">
                                    1M
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tsofr3M"
                                    id="n_tsofr3M"
                                    value={forms['formTermSofr']?.['n_tsofr3M'] || ''}
                                    onChange={handleChange('formTermSofr')}
                                    required
                                    onFocus={() => handleFocus('n_tsofr3M')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tsofr3M')}
                                />
                                <label  htmlFor="n_tsofr3M">
                                    3M
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tsofr6M"
                                    id="n_tsofr6M"
                                    value={forms['formTermSofr']?.['n_tsofr6M'] || ''}
                                    onChange={handleChange('formTermSofr')}
                                    required
                                    onFocus={() => handleFocus('n_tsofr6M')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tsofr6M')}
                                />
                                <label  htmlFor="n_tsofr6M">
                                    6M
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tsofr12M"
                                    id="n_tsofr12M"
                                    value={forms['formTermSofr']?.['n_tsofr12M'] || ''}
                                    onChange={handleChange('formTermSofr')}
                                    required
                                    onFocus={() => handleFocus('n_tsofr12M')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tsofr12M')}
                                />
                                <label  htmlFor="n_tsofr12M">
                                    12M
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <form onSubmit={(e) => handleSubmit(e, 'formTiieFon1d', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasa Tiie Fondeo 1D</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tiie_fondeo_1d"
                                    id="n_tiie_fondeo_1d"
                                    value={forms['formTiieFon1d']?.['n_tiie_fondeo_1d'] || ''}
                                    onChange={handleChange('formTiieFon1d')}
                                    required
                                    onFocus={() => handleFocus('n_tiie_fondeo_1d')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tiie_fondeo_1d')}
                                />
                                <label htmlFor="n_tiie_fondeo_1d">
                                    Tiie Fondeo 1D
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>


                    <form onSubmit={(e) => handleSubmit(e, 'formUsdSofr', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Tasa USD SOFR</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_sofr"
                                    id="n_sofr"
                                    value={forms['formUsdSofr']?.['n_sofr'] || ''}
                                    onChange={handleChange('formUsdSofr')}
                                    required
                                    onFocus={() => handleFocus('n_sofr')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_sofr')}
                                />
                                <label  htmlFor="n_sofr">
                                    SOFR 1D
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>

                    <form onSubmit={(e) => handleSubmit(e, 'formIpcMexbol', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">IPC MEXBOL</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_ipc_mexbol"
                                    id="n_ipc_mexbol"
                                    value={forms['formIpcMexbol']?.['n_ipc_mexbol'] || ''}
                                    onChange={handleChange('formIpcMexbol')}
                                    required
                                    onFocus={() => handleFocus('n_ipc_mexbol')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_ipc_mexbol')}
                                />
                                <label htmlFor="n_ipc_mexbol">
                                    IPC MEXBOL
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>

                    <form onSubmit={(e) => handleSubmit(e, 'formTIIEF', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">TIIE de Fondeo Compuesto</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_ind_tiief_nat"
                                    id="n_ind_tiief_nat"
                                    value={forms['formTIIEF']?.['n_ind_tiief_nat'] || ''}
                                    onChange={handleChange('formTIIEF')}
                                    required
                                    onFocus={() => handleFocus('n_ind_tiief_nat')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_ind_tiief_nat')}
                                />
                                <label  htmlFor="n_ind_tiief_nat">
                                    IND TIIEF NAT
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_ind_tiief_banc"
                                    id="n_ind_tiief_banc"
                                    value={forms['formTIIEF']?.['n_ind_tiief_banc'] || ''}
                                    onChange={handleChange('formTIIEF')}
                                    required
                                    onFocus={() => handleFocus('n_ind_tiief_banc')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_ind_tiief_banc')}
                                />
                                <label  htmlFor="n_ind_tiief_banc">
                                    IND TIIEF BANC
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tiief_28_acum"
                                    id="n_tiief_28_acum"
                                    value={forms['formTIIEF']?.['n_tiief_28_acum'] || ''}
                                    onChange={handleChange('formTIIEF')}
                                    required
                                    onFocus={() => handleFocus('n_tiief_28_acum')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tiief_28_acum')}
                                />
                                <label  htmlFor="n_tiief_28_acum">
                                    TIIEF 28 ACUM
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tiief_91_acum"
                                    id="n_tiief_91_acum"
                                    value={forms['formTIIEF']?.['n_tiief_91_acum'] || ''}
                                    onChange={handleChange('formTIIEF')}
                                    required
                                    onFocus={() => handleFocus('n_tiief_91_acum')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tiief_91_acum')}
                                />
                                <label  htmlFor="n_tiief_91_acum">
                                    TIIEF 91 ACUM
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_tiief_182_acum"
                                    id="n_tiief_182_acum"
                                    value={forms['formTIIEF']?.['n_tiief_182_acum'] || ''}
                                    onChange={handleChange('formTIIEF')}
                                    required
                                    onFocus={() => handleFocus('n_tiief_182_acum')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_tiief_182_acum')}
                                />
                                <label  htmlFor="n_tiief_182_acum">
                                    TIIEF 182 ACUM
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Guardar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>

                    <form onSubmit={(e) => handleSubmit(e, 'formAjustSofr', d_fecha)}>
                            <span className="bg-cyan-700 text-slate-50 px-1 h-7">Ajustadas de SOFR</span>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_adjtsofr1W"
                                    id="n_adjtsofr1W"
                                    value={forms['formAjustSofr']?.['n_adjtsofr1W'] || ''}
                                    onChange={handleChange('formAjustSofr')}
                                    disabled
                                    required
                                    onFocus={() => handleFocus('n_adjtsofr1W')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_adjtsofr1W')}
                                />
                                <label  htmlFor="n_adjtsofr1W">
                                    ADJ SOFR 1W
                                </label>
                            </div>
                            <div className="form-input">
                                <input
                                    type="text"
                                    name="n_adjtsofr1M"
                                    id="n_adjtsofr1M"
                                    value={forms['formAjustSofr']?.['n_adjtsofr1M'] || ''}
                                    onChange={handleChange('formAjustSofr')}
                                    disabled
                                    required
                                    onFocus={() => handleFocus('n_adjtsofr1M')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_adjtsofr1M')}
                                />
                                <label htmlFor="n_adjtsofr1M">
                                    ADJ SOFR 1M
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_adjtsofr2M"
                                    id="n_adjtsofr2M"
                                    value={forms['formAjustSofr']?.['n_adjtsofr2M'] || ''}
                                    onChange={handleChange('formAjustSofr')}
                                    disabled
                                    required
                                    onFocus={() => handleFocus('n_adjtsofr2M')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_adjtsofr2M')}
                                />
                                <label htmlFor="n_adjtsofr2M">
                                    ADJ SOFR 2M
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_adjtsofr3M"
                                    id="n_adjtsofr3M"
                                    value={forms['formAjustSofr']?.['n_adjtsofr3M'] || ''}
                                    onChange={handleChange('formAjustSofr')}
                                    disabled
                                    required
                                    onFocus={() => handleFocus('n_adjtsofr3M')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_adjtsofr3M')}
                                />
                                <label htmlFor="n_adjtsofr3M">
                                    ADJ SOFR 3M
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_adjtsofr6M"
                                    id="n_adjtsofr6M"
                                    value={forms['formAjustSofr']?.['n_adjtsofr6M'] || ''}
                                    onChange={handleChange('formAjustSofr')}
                                    disabled
                                    required
                                    onFocus={() => handleFocus('n_adjtsofr6M')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_adjtsofr6M')}
                                />
                                <label htmlFor="n_adjtsofr6M">
                                    ADJ SOFR 3M
                                </label>
                            </div>
                            <div className="form-input">
                                <input 
                                    type="text"
                                    name="n_adjtsofr12M"
                                    id="n_adjtsofr12M"
                                    value={forms['formAjustSofr']?.['n_adjtsofr12M'] || ''}
                                    onChange={handleChange('formAjustSofr')}
                                    disabled
                                    required
                                    onFocus={() => handleFocus('n_adjtsofr12M')}
                                    onBlur={handleBlur}
                                    style={sendStyle('n_adjtsofr12M')}
                                />
                                <label  htmlFor="n_adjtsofr12M">
                                    ADJ SOFR 12M
                                </label>
                            </div>
                            <div className="text-center text-xs text-cyan-700 my-0">
                                <button type="submit"
                                        className={`bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md 
                                        ${loadingSave ? 'opacity-50 pointer-events-none' : ''}`}>
                                    <ButtonContent name='Actualizar' loading={loadingSave}/>
                                </button>
                            </div>
                    </form>

                    <Modal isOpen={isOpenUdi} onClose={handleCloseUdi} title="">
                        <div className="grid grid-cols-1 mt-1">
                            <div className="p-1 bg-cyan-700 text-white text-center">
                                <span>Actualiza UDIS</span>
                            </div>
                            <div className="grid grid-cols-2 gap-4">
                                <div className="relative z-0 w-full my-3">
                                    <textarea
                                        className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-300 appearance-none dark:text-slate-900
                                        dark:border-gray-600 dark:focus:border-cyan-700 focus:outline-none focus:ring-0
                                        focus:border-cyan-700 peer"
                                        onChange={e => setTextUdi(e.target.value)}
                                        value={textUdi}
                                    />
                                    {isTextArea && (<span className="fontError animate__animated animate__fadeIn">
                                        Ingrese un valor</span>)}
                                </div>
                                <div className="relative z-0 w-full my-3">
                                    <div className="text-center text-lg text-cyan-700 my-3">
                                        <button className='bg-cyan-700 hover:bg-green-700 text-white py-1 px-3 rounded-md'
                                                onClick={handleChargeUdi}>
                                            <ButtonContent name="Cargar" loading={loadingUdi}></ButtonContent>
                                        </button>

                                        <div className="mt-14 text-center text-xs text-cyan-700 my-3">
                                            Formato Fecha (YYYY-MM-DD)
                                        </div>

                                        <div className="text-center text-xs text-cyan-700 my-0">
                                            Layout (FECHA -tab- VALOR UDI)
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Modal>
                </div>
            </div>
        </div>
    )
}