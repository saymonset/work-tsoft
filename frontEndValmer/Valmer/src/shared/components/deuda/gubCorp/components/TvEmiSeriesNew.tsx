import {BarLoader} from "react-spinners";
import React from "react";
import {TvEmiSerieProps} from "../../../../../model";
// @ts-ignore
import Select, {ActionMeta, InputActionMeta, ValueType} from 'react-select';
import {customStyles} from "../../../../../utils";
import { useBigInput } from "../../../../../utils/useBigInput";

interface IOption {
    value: string;
    label: string;
}

export const TvEmiSeriesNew = (propsDef: TvEmiSerieProps) => {

    
    //  Achica o agranda el input del form cuando obtiene o deja el focus
    const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();

    const tvOptions = propsDef.tv?.map((item) => ({value: item, label: item}));

    const handleInputChange = (inputValue: string, actionMeta: InputActionMeta) => {
        if (actionMeta.action === 'input-change')
        {
            propsDef.handleTvNewForm(inputValue)
            propsDef.handleClickTv(inputValue)
        }
    };

    const handleOptionSelect = async (selectedOption: ValueType<IOption>, actionMeta: ActionMeta<IOption>) => {
        if (actionMeta.action === 'select-option') {
            propsDef.handleTvNewForm((selectedOption as IOption).value)
            propsDef.handleClickTv((selectedOption as IOption).value)
        }
    };

    const handleEmi = (event: any) => {
        propsDef.handleEmisora(event)
        propsDef.handleChange(event)
    }

    const handleSerie = (event: any) => {
        propsDef.handleSerie(event)
        propsDef.handleChange(event)
    }


    return (
        <>
            {propsDef.loadingConsultaData && <BarLoader className="w-full mt-2 mb-2" color="#059669" width={500}/>}
            <div className="bg-cyan-700 p-1 text-slate-50">
                <span>Instrumento</span>
            </div>
            <div className="form-cols-3">
                <div className="form-input">
                    <Select
                        className="mt-0"
                        name="s_tv"
                        value={tvOptions.find(option =>
                            option.value === propsDef.selectedTv) ??
                            { value: propsDef.selectedTv, label: propsDef.selectedTv }}
                        onChange={handleOptionSelect}
                        onInputChange={handleInputChange}
                        options={tvOptions}
                        isSearchable
                        required
                        styles={customStyles}
                        

                    />
                    <label htmlFor="s_tv">TV</label>
                    {propsDef.requiredTv && (
                        <span className="fontError animate__animated animate__fadeIn">Campo requerido sTv</span>
                    )}
                </div>

                

                <div className="form-input">
                    <input
                        type="text"
                        name="s_emisora"
                        value={propsDef.consultaData?.body?.s_emisora ?? ''}
                        onChange={handleEmi}
                        onFocus={() => handleFocus('s_emisora')}
                        onBlur={handleBlur}
                        style={sendStyle('s_emisora')}
                    />
                    <label htmlFor="s_emisora">
                        EMISORA
                    </label>
                    {propsDef.requiredEmisora && (
                        <span className="fontError animate__animated animate__fadeIn">Campo requerido s_emisora</span>
                    )}
                </div>
                <div className="form-input">
                    <input
                        type="text"
                        name="s_serie"
                        value={propsDef.consultaData?.body?.s_serie ?? ''}
                        onChange={handleSerie}
                        onFocus={() => handleFocus('s_serie')}
                        onBlur={handleBlur}
                        style={sendStyle('s_emisora')}
                    />
                    <label htmlFor="s_serie">
                        SERIE
                    </label>
                    {propsDef.requiredSerie && (
                        <span className="fontError animate__animated animate__fadeIn">Campo requerido s_serie</span>
                    )}
                </div>
            </div>
        </>
    )
}