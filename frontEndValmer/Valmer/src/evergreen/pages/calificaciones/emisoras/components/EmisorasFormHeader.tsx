import React from "react";
import {getCatalogs} from "../../../../../utils";
import {MoonLoader} from "react-spinners";
import {useEmisorasHeader} from "./hooks";
import {ButtonContent} from "../../../../../shared";
import { useBigInput } from "../../../deuda/tasas/components/forms/hooks/useBigInput";

export const EmisorasFormHeader = () => {

    //  Achica o agranda el input del form cuando obtiene o deja el focus
    const {  handleFocus,
        handleBlur,
        sendStyle} = useBigInput();
    const {
        loadingSave,
        loading,
        catalog,
        isFieldReqCalifEmi,
        handleSave,
        handleChange} = useEmisorasHeader()

    if (loading || !catalog.length) {
        return (
            <div className="flex justify-center items-center h-full">
                {loading ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <div className="ml-4 form-cols-2">
            <div className="form-select">
                <select defaultValue="default"
                        name="n_emisor"
                        onChange={handleChange}
                >
                    <option value="default">...</option>
                    {getCatalogs(catalog, 'EMISOR').map((column) => (
                        <option key={column[0]} value={column[0]}>
                            {column[1]}
                        </option>
                    ))}
                </select>
                <label htmlFor="n_emisor">
                    Emisora
                </label>
                {isFieldReqCalifEmi.n_emisor && (
                    <span className="fontError animate__animated animate__fadeIn">
                        Campo requerido Emisora
                    </span>
                )}
            </div>
            <div className="form-select">
                <select defaultValue="default"
                        name="n_pais"
                        onChange={handleChange}
                >
                    <option value="default">...</option>
                    {getCatalogs(catalog, 'PAIS').map((column) => (
                        <option key={column[0]} value={column[0]}>
                            {column[1]}
                        </option>
                    ))}
                </select>
                <label htmlFor="n_pais">
                    País
                </label>
                {isFieldReqCalifEmi.n_pais && (
                    <span className="fontError animate__animated animate__fadeIn">
                        Campo requerido País
                    </span>
                )}
            </div>
            <div className="form-input form-my0">
                <input
                    type="text"
                    name="s_entidad"
                    placeholder=""
                    onChange={handleChange}
                    required
                    onFocus={() => handleFocus('s_entidad')}
                                onBlur={handleBlur}
                                style={sendStyle('s_entidad')}
                />
                <label htmlFor="s_entidad">
                    Entidad
                </label>
            </div>

            <div className="flex justify-end items-center">
                <button className="btn" onClick={handleSave}>
                    <ButtonContent name="Guardar" loading={loadingSave}/>
                </button>
            </div>
        </div>
    )
}