import { CatalogoRegInv, RespRegInvData } from "../../../../../model/RegimenInversion";
import { useGetCatalogs } from "../hooks/useGetCatalogs";
import React from "react";

interface CaracteristicasProps {
    consultaDataRegInv: RespRegInvData;
    handleChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
    catalog: CatalogoRegInv[];
    handleCheckbox: (e: React.ChangeEvent<HTMLInputElement>) => void;
    spanEnabled: boolean;
}

export const Caracteristicas = (
{
    consultaDataRegInv,
    handleChange,
    catalog,
    handleCheckbox,
    spanEnabled
}: CaracteristicasProps) => {

    const classSelect = `block py-2.5 px-0 w-1/2 text-sm text-gray-900
                        bg-transparent border-0 border-b-2 appearance-none
                        border-gray-600 focus:border-cyan-700 focus:outline-none focus:ring-0`;
    const bCambioValue = consultaDataRegInv?.body?.B_CAMBIO === '' || consultaDataRegInv?.body?.B_CAMBIO === '0' ? '0' : '1';
    const checkedComponRv = consultaDataRegInv?.body?.COMPON_RV === '1' || consultaDataRegInv?.body?.COMPON_RV === 'Componente de Renta Variable';
    const componRvValue =
        (consultaDataRegInv?.body?.COMPON_RV === "1" || consultaDataRegInv?.body?.COMPON_RV === "Componente de Renta Variable")
        ? 'Componente de Renta Variable'
        : '';
    const checkedValoresExt = consultaDataRegInv?.body?.VALORES_EXT === '1' || consultaDataRegInv?.body?.VALORES_EXT === 'Valores Extranjeros';
    const valoresExtValue = (consultaDataRegInv?.body?.VALORES_EXT === "1" || consultaDataRegInv?.body?.VALORES_EXT === "Valores Extranjeros")
        ? 'Valores Extranjeros'
        : '';


    return (
        <>
        <div className="form-title">
            <span>Caracteristicas</span>
        </div>
        <div>
            <div className="form-cols-2 h-[56px]">
                <div className="form-cols-2 items-center">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="B_CAMBIO"
                            checked={consultaDataRegInv?.body?.B_CAMBIO === "1"}
                            onChange={handleCheckbox}
                        />
                        <label htmlFor="B_CAMBIO">CAMBIOS SOLO EN FRONT:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                    {spanEnabled && (<span>{bCambioValue}</span>)}
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="calidad_crediticia_nacional">Calidad Crediticia Nacional:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.CALIDAD_C}</span>
                    </div>
                </div>
            </div>
            <div className="form-cols-2 bg-gray-200 border border-x-0 border-gray-300 h-[56px]">
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="calificacion_regimen">Calificacion Regimen:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.CALIF_REG}</span>
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="Clasificacion Consar">Clasificacion Consar:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <select 
                            className={classSelect}
                            name="CLAS_CONSAR"
                            value={consultaDataRegInv?.body?.CLAS_CONSAR ?? "default"}
                            onChange={handleChange}
                        >
                            <option value="default">...</option>
                            {useGetCatalogs(catalog, 'clasificacion_consar').map((column) => (
                                <option key={column[0]} value={column[1]}>
                                    {column[1]}
                                </option>
                                ))}
                        </select>
                    </div>
                </div>
            </div>
            <div className="form-cols-2 h-[56px]">
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="clasificacion_consar">Clasificacion Consar TF:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                    <select 
                        className={classSelect}
                        name="CLAS_CONSAR_TF"
                        value={consultaDataRegInv?.body?.CLAS_CONSAR_TF ?? "default"}
                        onChange={handleChange}
                    >
                        <option value="default">...</option>
                        {useGetCatalogs(catalog, 'clasificacion_consar').map((column) => (
                            <option key={column[0]} value={column[1]}>
                                {column[1]}
                            </option>
                            ))}
                    </select>
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="COMPON_RV"
                            checked={checkedComponRv}
                            onChange={handleCheckbox}
                        />
                        <label htmlFor="COMPON_RV">Componente de Renta Variable:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        {spanEnabled && <span>{componRvValue}</span>}
                    </div>
                </div>
            </div>
            <div className="form-cols-2 bg-gray-200 border border-x-0 border-gray-300 h-[56px]">
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="divisas">Divisas:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.DIVISAS}</span>
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="emisor">Emisor:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.EMISOR}</span>
                    </div>
                </div>
            </div>
            <div className="form-cols-2 h-[56px]">
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="entidades_relacionadas">Entidades Relacionadas:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <select 
                            className={classSelect}
                            name="ENTIDADES_R"
                            value={consultaDataRegInv?.body?.ENTIDADES_R ?? "default"}
                            onChange={handleChange}
                        >
                            <option value="default">...</option>
                            {useGetCatalogs(catalog, 'entidades_relacionadas').map((column) => (
                                <option key={column[0]} value={column[1]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="nexo_patrimonial">Nexo patrimonial:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.NEXO_PATRI}</span>
                    </div>
                </div>
            </div>
            <div className="form-cols-2 bg-gray-200 border border-x-0 border-gray-300 h-[56px]">
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="proteccion_inflacionaria">Proteccion Inflacionaria:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.PROTEC_INFL}</span>
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="riesgo_concentracion">Riesgo de Concentracion:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.RIESGO_CONC}</span>
                    </div>
                </div>
            </div>
            <div className="form-cols-2 h-[56px]">
                <div className="form-cols-2">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="VALORES_EXT"
                            checked={checkedValoresExt}
                            onChange={handleCheckbox}
                        />
                        <label htmlFor="valores_extranjeros">Valores Extranjeros:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        {spanEnabled && <span>{valoresExtValue}</span>}
                    </div>
                </div>
                <div className="form-cols-2"></div>
            </div>
            <div className="form-cols-2 h-[56px]">
                <div className="form-cols-2">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="emisor_regimen">Emisor Regimen:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <select className="block py-2.5 px-0 w-3/4 text-sm text-gray-900 
                                        bg-transparent border-0 border-b-2 appearance-none 
                                        border-gray-600 focus:border-cyan-700 focus:outline-none focus:ring-0;"
                                name="EMISOR_REG"
                                value={consultaDataRegInv?.body?.EMISOR_REG ?? "default"}
                                onChange={handleChange}
                                >
                            <option value="default">...</option>
                            {useGetCatalogs(catalog, 'EMISOR').map((column) => (
                                <option key={column[0]} value={column[0]}>
                                    {column[1]}
                                </option>
                            ))}
                        </select>
                    </div>
                </div>
                <div className="form-cols-2"></div>
            </div>
        </div>
        </>
    );
}