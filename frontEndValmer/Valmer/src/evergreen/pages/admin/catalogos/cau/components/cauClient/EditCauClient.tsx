import {HeadCauClient} from "./header/HeadCauClient";
import {fetchDataGetRet, generateUUID} from "../../../../../../../utils";
import React, {useEffect, useState} from "react";
import {DataForm} from "./form/DataForm";
import {ServiceLevelsCau} from "./form/ServiceLevelsCau";
import {Catalogo, RegistroConstCatAdmin, ResponseCauCLientes, ResponseConstCatAdmin} from "../../../../../../../model";
import {MoonLoader} from "react-spinners";

interface Column {
    name: string;
    type: string;
}

interface Props {
    nameCatalog: string;
    setShowTable: (show: boolean) => void;
    setSelectedOption: (show: string) => void;
    columns: Column[];
}

export const EditCauClient : React.FC<Props> = ({nameCatalog, setShowTable,
                                                    setSelectedOption, columns}) => {

    const [catalogs, setCatalogs] = useState<ResponseCauCLientes[]>([])
    const [catalogsCau, setCatalogsCau] = useState<Catalogo[]>([])
    const [loadingCatalog, setLoadingCatalog] = useState<boolean>(false)
    const [loadingCatalogCau, setLoadingCatalogCau] = useState<boolean>(false)
    const [triggerCatalogs, setTriggerCatalogs] = useState<boolean>(false)

    const goBack = () => {
        setShowTable(true)
        setSelectedOption('')
    }

    useEffect(() => {
        const getCatalogs = async () => {
            setLoadingCatalog(true)

            try {
                const response = await fetchDataGetRet(
                    "/catalogos/cau-cliente/consulta-catalogo",
                    " al obtener catalogos latam",
                    {
                        num_registros: 0,
                        posicion: 0,
                        s_nombre_catalogo: nameCatalog
                    }
                );

                const responseCau = await fetchDataGetRet(
                    "/latam/cr/mantenimiento-cau/catalogos",
                    " al obtener catalogos cr cau",
                    {}
                );

                setCatalogsCau(responseCau.body.catalogos)
                setCatalogs(response.body.registros)
                setTriggerCatalogs(false)

            } catch (error) {
                console.error('Error al obtener los catálogos:', error);
            }

            setLoadingCatalog(false)
        };

        if (!catalogs || catalogs.length === 0) {
            getCatalogs().then();
        }
    }, [catalogs, triggerCatalogs]);

    useEffect(() => {
        const getCatalogsCau = async () => {

            setLoadingCatalogCau(true)

            try {
                const responseCau = await fetchDataGetRet(
                    "/latam/cr/mantenimiento-cau/catalogos",
                    " al obtener catalogos cr cau",
                    {}
                );

                setCatalogsCau(responseCau.body.catalogos)
                setLoadingCatalogCau(false)

            } catch (error) {
                console.error('Error al obtener los catálogos:', error);
            }

            setLoadingCatalogCau(false)
        };

        if (!catalogsCau || catalogsCau.length === 0) {
            getCatalogsCau().then();
        }
    }, [catalogsCau]);

    if (loadingCatalog || loadingCatalogCau || !catalogs.length) {
        return (
            <div className="mt-24 flex justify-center items-center h-full">
                {loadingCatalog || loadingCatalogCau ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <>
            <div className="form-cols-2 mb-12">
                <div className="flex justify-start pr-2">
                    <button className="btn" onClick={() => goBack()}>
                        <i className="mr-2 fa-solid fa-arrow-left"></i>
                        <span>Regresar</span>
                    </button>
                    <button className="btn">
                        <span>Obtener CSV</span>
                    </button>
                </div>

                <div className="flex justify-end pr-2">
                    <button className="btn">
                        <span>Grabar</span>
                    </button>
                    <button className="btn">
                        <span>Nuevo</span>
                    </button>
                    <button className="btn">
                        <span>Borrar</span>
                    </button>
                </div>
            </div>

            <HeadCauClient catalogCau={catalogsCau}/>

            <div className="flex mb-8">
            <div className="flex-1 mt-8 ml-8 text-center text-cyan-700 text-2xl font-semibold">
                    Catalogo {nameCatalog}
                </div>
            </div>

            <div className="flex flex-col">
                <div className="overflow-x-auto">
                    <div style={{ maxHeight: "500px", overflowY: "scroll" }}>
                        <table className="min-w-full">
                            <thead>
                            <tr>
                                <th className="bg-cyan-700 text-white px-4 py-2">
                                    Cliente
                                </th>
                                <th className="bg-cyan-700 text-white px-4 py-2">
                                    Empresa
                                </th>
                                <th className="bg-cyan-700 text-white px-4 py-2">
                                    Nombre
                                </th>
                                <th className="bg-cyan-700 text-white px-4 py-2">
                                    Usuario
                                </th>
                                <th className="bg-cyan-700 text-white px-4 py-2">
                                    Password
                                </th>
                            </tr>
                            </thead>
                            <tbody className="tbody">
                            {catalogs.map((data) => {
                                return (
                                    <tr key={generateUUID()} >
                                        <td className="border px-4 py-2 flex items-center justify-center cursor-pointer">
                                            {data.n_cliente}
                                        </td>
                                        <td className="border px-4 py-2 items-center justify-center text-center">
                                            {data.s_nomcorto}
                                        </td>
                                        <td className="border px-4 py-2 items-center justify-center text-center">
                                            {data.s_nombre}
                                        </td>
                                        <td className="border px-4 py-2 items-center justify-center text-center">
                                            {data.s_usuario}
                                        </td>
                                        <td className="border px-4 py-2 items-center justify-center text-center">
                                            {data.s_password}
                                        </td>
                                    </tr>
                                )
                            })}
                            </tbody>
                        </table>
                    </div>
                </div>

                <DataForm/>

                <ServiceLevelsCau/>
            </div>
        </>
    )
}