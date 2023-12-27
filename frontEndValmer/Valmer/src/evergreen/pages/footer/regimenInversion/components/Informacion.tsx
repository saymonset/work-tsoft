import { CatalogoRegInv, RespRegInvData } from "../../../../../model/RegimenInversion";
import { useGetCatalogs } from "../hooks/useGetCatalogs";

interface InformacionProps {
    consultaDataRegInv: RespRegInvData;
    handleChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
    catalog: CatalogoRegInv[];
    handleCheckbox: (e: React.ChangeEvent<HTMLInputElement>) => void;
    spanEnabled: boolean;
}

export const Informacion = (
{
    consultaDataRegInv,
    handleChange,
    catalog,
    handleCheckbox,
    spanEnabled
}: InformacionProps) => {
    return (
        <>
        <div className="form-cols-1">
            <span className="form-title">Información</span>
        </div>
        <div>
            <div className="form-cols-2 h-[56px]">
                <div className="form-cols-2 items-center">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="BURSA"
                            checked={consultaDataRegInv?.body?.BURSA === '1' || consultaDataRegInv?.body?.BURSA === 'Instrumentos Bursatilizados'}
                            onChange={handleCheckbox}
                        />
                        <label htmlFor="BURSA">Bursatilizados:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        {spanEnabled && 
                            <span>
                                {consultaDataRegInv?.body?.BURSA === "1" || consultaDataRegInv?.body?.BURSA === "Instrumentos Bursatilizados"
                                    ? 'Instrumentos Bursatilizados'
                                    : ''}
                            </span>
                        }
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="Corpotrac">Corpotrac:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.CORPO}</span>
                    </div>
                </div>
            </div>
            <div className="form-cols-2 h-[56px] bg-gray-200 border border-x-0 border-gray-300">
                <div className="form-cols-2 items-center">
                <div className="flex flex-col justify-end">
                        <label htmlFor="Derivado">Derivado:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span></span>
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                <div className="form-check">
                        <input
                            type="checkbox"
                            name="ESTRUCT_Deuda"
                            checked={consultaDataRegInv?.body?.ESTRUCT_Deuda === '1' || consultaDataRegInv?.body?.ESTRUCT_Deuda === 'Instrumentos Estructurados'}
                            onChange={handleCheckbox}
                        />
                        <label htmlFor="Estructurado">Estructurado:</label>
                    </div>
                </div>
            </div>
            <div className="form-cols-2 h-[56px]">
                <div className="form-cols-2 items-center">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="MCIRC_Deuda"
                            checked={consultaDataRegInv?.body?.MCIRC_Deuda === '1' || consultaDataRegInv?.body?.MCIRC_Deuda === 'MC'}
                            onChange={handleCheckbox}
                        />
                        <label htmlFor="monto_circulacion">Monto en Circulacion:</label>
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="composicion_corpotrac">% Composición Corpotrac:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.N_CORPO}</span>
                    </div>
                </div>
            </div>
            <div className="form-cols-2 h-[56px] bg-gray-200 border border-x-0 border-gray-300">
                <div className="form-cols-2 items-center">
                <div className="flex flex-col justify-end">
                        <label htmlFor="valor_subyacente">Valor Subyacente:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.N_SUBY}</span>
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                <div className="flex flex-col justify-end">
                        <label htmlFor="Subyacente">Subyacente:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        <span>{consultaDataRegInv?.body?.SUBY}</span>
                    </div>
                </div>
            </div>
            <div className="form-cols-2 h-[56px]">
                <div className="form-cols-2 items-center">
                    <div className="form-check">
                        <input
                            type="checkbox"
                            name="S_FIBRAS"
                            checked={consultaDataRegInv?.body?.S_FIBRAS === '1'}
                            onChange={handleCheckbox}
                        />
                        <label htmlFor="S_FIBRAS">FIBRAS:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                        {spanEnabled && 
                            <span>
                                {(consultaDataRegInv?.body?.S_FIBRAS === "1") ?  'FIBRAS' : ''}
                            </span>
                        }
                    </div>
                </div>
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="tipo_derivado">Tipo Derivado:</label>
                    </div>
                </div>
            </div>
            <div className="form-cols-2 h-[56px] bg-gray-200 border border-x-0 border-gray-300">
                <div className="form-cols-2 items-center">
                    <div className="flex flex-col justify-end">
                        <label htmlFor="tipo_estructurado">Tipo Estructurado:</label>
                    </div>
                    <div className="flex flex-col justify-end">
                    <select className="block py-2.5 px-0 w-full text-sm text-gray-900 
                                        bg-transparent border-0 border-b-2 appearance-none 
                                        border-gray-600 focus:border-cyan-700 focus:outline-none focus:ring-0;"
                            name="TIPO_ESTRUCT_Deuda"
                            value={consultaDataRegInv?.body?.TIPO_ESTRUCT_Deuda ?? "0"}
                            onChange={handleChange}>
                            <option value="0">...</option>
                            {useGetCatalogs(catalog, 'tipo_estructurado').map((column) => (
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