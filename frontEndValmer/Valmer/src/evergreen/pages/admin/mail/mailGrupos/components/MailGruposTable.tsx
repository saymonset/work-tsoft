import React, { useEffect } from "react";
import { useGetCatalogoEnviosMailGrupos } from "../../mailClientes";
import { MoonLoader } from "react-spinners";

interface MailGruposTableProps {
    onSelectedGroup: (group: any) => void;
    changeCatalogPost: boolean;
    changeCatalogDelete: boolean;
}
export const MailGruposTable: React.FC<MailGruposTableProps> = ({ onSelectedGroup, changeCatalogPost, changeCatalogDelete}) => {
    const { catalogoEnviosMailGrupos, loading, fetchDataGrupos } = useGetCatalogoEnviosMailGrupos();

    useEffect(() => {
        if (changeCatalogPost) {
            fetchDataGrupos().then();
            onSelectedGroup({
                n_grupo: "",
                s_descripcion: ""
            });
        }
    }, [changeCatalogPost]);

    useEffect(() => {
        if (changeCatalogDelete) {
            fetchDataGrupos().then();
            onSelectedGroup({
                n_grupo: "",
                s_descripcion: ""
            });
        }
    }, [changeCatalogDelete]);
    
    useEffect(() => {
            fetchDataGrupos().then();
    }, []);

    if (loading || !catalogoEnviosMailGrupos) {
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
        <div className="flex justify-center">
            <div className="max-h-64 overflow-y-scroll w-full">
                <table className="table w-full">
                    <thead className="thead">
                    <tr>
                        <th>N_GRUPO</th>
                        <th>S_DESCRIPCION</th>
                    </tr>
                    </thead>
                    <tbody className="tbody">
                    {catalogoEnviosMailGrupos?.body &&
                        catalogoEnviosMailGrupos.body.registros.map((grupo) => (
                            <tr key={grupo.n_grupo}>
                                <td className="btn-link">
                                    <button onClick={(e) => {
                                        e.preventDefault();
                                        onSelectedGroup(grupo);
                                    }}>
                                        {grupo.n_grupo}
                                    </button>
                                </td>
                                <td>{grupo.s_descripcion}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}