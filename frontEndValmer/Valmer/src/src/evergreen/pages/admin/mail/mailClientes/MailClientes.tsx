import React, { useState } from "react";
import { TitleDate } from "../../../../../shared";
import { MailClientesTable } from './components/MailClientesTable';
import { MailClientesForm } from './components/MailClientesForm';
import {HocRestricted} from "../../../restrictedAccess";
import { useGetCatalogoEmpresas } from "./hooks/useGetCatalogoEmpresas";
import { getCatalogs } from "../../../../../utils";
import { MoonLoader } from "react-spinners";

export const MailClientes = () => {
    const title = "Envios Mail Valmer"
    const { catalogo, loading } = useGetCatalogoEmpresas();
    const [selectedEmpresa, setSelectedEmpresa] = useState<number | null>(null);
    const [selectedNCliente, setSelectedNCliente] = useState<number | null>(null);

    const handleEmpresaChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const value = event.target.value;
        setSelectedEmpresa(value ? parseInt(value, 10) : null);
        setSelectedNCliente(null);
    };

    if (loading || !catalogo.length) {
        return (
            <div className="flex justify-center items-center h-full">
              {loading ? (
                  <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
              ) : (
                  <div>No hay información</div>
              )}
            </div>
        );
    }

    return (
        <HocRestricted title={title} view={title}>
                <TitleDate title={title}/>

                <div className="flex justify-center animate__animated animate__fadeIn">
                    <div className="form-select w-1/2 m-1">
                        <select name="empresa"
                                id="empresa" 
                                onChange={handleEmpresaChange}
                                value={selectedEmpresa !== null ? selectedEmpresa.toString() : ""}>
                            <option value="">...</option>
                            {getCatalogs(catalogo, 'CAU_EMPRESA').map((empresa) => (
                                <option key={empresa[0]} value={empresa[0]}>
                                    {empresa[1]}
                                </option>
                            ))}
                        </select>
                        <label htmlFor="empresa">EMPRESA</label>
                    </div>
                </div>

                <MailClientesTable
                    selectedEmpresa={selectedEmpresa}
                    setSelectedNCliente={setSelectedNCliente}
                />

                <MailClientesForm
                    selectedNCliente={selectedNCliente}
                    selectedEmpresa={selectedEmpresa}
                />
        </HocRestricted>
    )
}