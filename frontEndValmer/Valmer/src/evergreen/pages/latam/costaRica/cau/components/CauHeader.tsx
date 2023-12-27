import {getCatalogs} from "../../../../../../utils";
import React from "react";
import {CauHeaderProps} from "../../../../../../model";


export const CauHeader = ({
                              catalog,
                              selectedEnterprise,
                              eraseFilters,
                              handleEnterprise,
                              handleStatus}: CauHeaderProps) => {
    return (
        <form>
            <div className="flex justify-center">
                <div className="form-select w-1/4 m-1">
                    <select name="n_emp"
                            id="n_emp"
                            value={selectedEnterprise}
                            onChange={handleEnterprise}>
                        <option value="">...</option>
                        {getCatalogs(catalog, 'INTRANET.CR_SEG_INSTITUCION').map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label htmlFor="n_emp">
                        Empresa
                    </label>
                </div>
                <div className="content-center grid m-1">
                    <button className="btn" type="button" onClick={eraseFilters}>
                        Limpiar Filtros
                    </button>
                </div>
                <div className="content-center grid m-1">
                    <button
                        className="btn"
                        type="button"
                        onClick={() => handleStatus("Abiertos")}
                    >
                        Abiertos
                    </button>
                </div>
                <div className="content-center grid m-1">
                    <button
                        className="btn"
                        type="button"
                        onClick={() => handleStatus("Cerrados")}
                    >
                        Cerrados
                    </button>
                </div>
                <div className="content-center grid m-1">
                    <button
                        className="btn"
                        type="button"
                        onClick={() => handleStatus("Modificación")}
                    >
                        Modificación
                    </button>
                </div>
            </div>
        </form>
    );
};