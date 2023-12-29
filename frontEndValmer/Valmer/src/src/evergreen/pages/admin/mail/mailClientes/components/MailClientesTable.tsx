import React, { useEffect, useState } from "react";
import { useGetTableMail } from "../hooks/useGetTableMail";
import { BarLoader, MoonLoader } from "react-spinners";
import {generateUUID} from "../../../../../../utils";

interface MailClientesTableProps {
    selectedEmpresa: number | null;
    setSelectedNCliente: (nCliente: number | null) => void;
}

export const MailClientesTable: React.FC<MailClientesTableProps> = ({ selectedEmpresa, setSelectedNCliente  }) => {
    const [ position, setPosition ] = useState(0);
    const numRegistros = 100;

    const { tableMail, loading, fetchData } = useGetTableMail();

    useEffect(() => {
        if (selectedEmpresa !== null) {
            fetchData(selectedEmpresa, tableMail.body.total_registros, 0).then();
            setPosition(0);
        } else {
            fetchData(selectedEmpresa, numRegistros, position).then();
        }
    }, [selectedEmpresa]);

    
    const totalPages = Math.ceil(tableMail?.body.total_registros / numRegistros);

    const goToPage = (newPosition: number) => {
        setPosition(newPosition);
        fetchData(selectedEmpresa,numRegistros,newPosition).then()
    };

    const handleNClienteClick = (e: React.MouseEvent<HTMLButtonElement>, nCliente: number) => {
        e.preventDefault();
        setSelectedNCliente(nCliente);
    };

    if (loading || !tableMail || !tableMail.body.registros) {
        return (
            <div className="flex justify-center items-center h-[256px]">
                {loading ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }
    
    return (
        <>
            <div className="flex justify-center animate__animated animate__fadeIn">
                <div className="max-h-64 overflow-y-scroll w-full">
                    <table className="table w-full">
                        <thead className="thead">
                            <tr>
                                <th>CLIENTE</th>
                                <th>EMPRESA</th>
                                <th>NOMBRE</th>
                                <th>MAIL</th>
                            </tr>
                        </thead>
                        <tbody className="tbody">
                            {loading && (
                                <BarLoader
                                    className="ml-2 w-full mt-2 mb-2"
                                    color="#059669"
                                    width={500}
                                />
                            )}
                            {tableMail.body.registros.map((registro) => (
                                <tr key={generateUUID()}>
                                    <td className="btn-link">
                                        <button onClick={(e) =>
                                        {handleNClienteClick(e, registro.n_cliente)}}>
                                            {registro.n_cliente}
                                        </button>
                                    </td>
                                    <td>{registro.s_nomcorto}</td>
                                    <td>{registro.s_nombre}</td>
                                    <td>{registro.s_email}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
            <div className="text-white bg-cyan-700 flex justify-center mt-10">
                <button 
                    className={`hover:text-black ${position === 0 ? 'disabled:text-inherit opacity-50 disabled:pointer-events-none' : ''}`} 
                    onClick={() => goToPage(position-numRegistros)}
                    disabled={position === 0}
                >
                    Anterior
                </button>
                <span className="mx-2">
                    Página {position / numRegistros + 1} de {totalPages}
                </span>
                <button
                    className={`hover:text-black ${totalPages === 1 ? 'disabled:text-inherit opacity-50 disabled:pointer-events-none' : ''}`} 
                    onClick={ () => goToPage(position+numRegistros)}
                    disabled={position+numRegistros >= tableMail.body.total_registros}
                >
                    Siguiente
                </button>
            </div>
        </>
    )
}