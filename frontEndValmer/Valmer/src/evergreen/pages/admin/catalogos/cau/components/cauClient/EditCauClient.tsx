import {getCatalogs} from "../../../../../../../utils";
import React from "react";
import {DataForm} from "./form/DataForm";
import {ServiceLevelsCau} from "./form/ServiceLevelsCau";
import {BarLoader, MoonLoader} from "react-spinners";
import {useEditCauClient} from "./hooks";
import {ButtonContent} from "../../../../../../../shared";

interface Props {
    nameCatalog: string;
    setShowTable: (show: boolean) => void;
    setSelectedOption: (show: string) => void;
}

export const EditCauClient : React.FC<Props> = ({
                                                    nameCatalog, setShowTable,
                                                    setSelectedOption}) => {

    const {
        consultaDataClient,
        loadingClientId,
        loadingClient,
        loadingCatalog,
        loadingCatalogCau,
        loadingClientById,
        loadingSave,
        loadingErase,
        loadingNewId,
        loadingCsv,
        clients,
        catalogs,
        catalogsCau,
        filteredCatalogs,
        handleClickClient,
        handleEnterprise,
        handleSave,
        handleErase,
        handleChange,
        handleClient,
        handleNewId,
        handleGetCsv
    } = useEditCauClient({nameCatalog})


    const goBack = () => {
        setShowTable(true)
        setSelectedOption('')
    }

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
                    <button onClick={handleGetCsv} className="btn">
                        <ButtonContent name="Obtener CSV" loading={loadingCsv}/>
                    </button>
                </div>

                <div className="flex justify-end pr-2">
                    <button className="btn" onClick={handleSave}>
                        <ButtonContent name="Grabar" loading={loadingSave}/>
                    </button>
                    <button className="btn" onClick={handleNewId}>
                        <ButtonContent name="Nuevo" loading={loadingNewId}/>
                    </button>
                    <button className="btn" onClick={handleErase}>
                        <ButtonContent name="Borrar" loading={loadingErase}></ButtonContent>
                    </button>
                </div>
            </div>

            <div className="ml-4 mr- mt-10 form-cols-2">
                <div className="relative z-0">
                    <select defaultValue="default"
                            name="n_emp"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            onChange={handleEnterprise}
                    >
                        <option value="default">...</option>
                        {getCatalogs(catalogsCau, "CAU_EMPRESA").map((column) => (
                            <option key={column[0]} value={column[0]}>
                                {column[1]}
                            </option>
                        ))}
                    </select>
                    <label
                        htmlFor="n_emp"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]"
                    >
                        Empresa
                    </label>
                </div>
                <div className="relative z-0">
                    <select defaultValue="default"
                            name="n_cli"
                            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent
                                        border-0 border-b-2 border-gray-200 appearance-none dark:border-gray-600
                                        dark:focus:border-cyan-700 focus:outline-none focus:ring-0 peer"
                            onChange={handleClient}
                    >
                        <option value="default">...</option>
                        {clients && Object.entries(clients).map(([key, value]) => (
                            <option key={key} value={key}>{value}</option>
                        ))}
                    </select>
                    <label
                        htmlFor="n_cli"
                        className="font-medium absolute text-sm transform top-3 text-cyan-700 scale-75
                                    -translate-y-6 origin-[0]">
                        Cliente
                    </label>
                    {loadingClient && <BarLoader className="w-full mt-2 mb-2" color="#059669" width={500}/>}
                </div>
            </div>

            <div className="flex mb-8">
                <div className="flex-1 mt-8 ml-8 text-center text-cyan-700 text-2xl font-semibold">
                    Catalogo {nameCatalog}
                </div>
            </div>

            <div className="flex flex-col">
                <div className="overflow-x-auto">
                    <div style={{maxHeight: "500px", overflowY: "scroll"}}>
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
                            {filteredCatalogs.map((data, index) => {

                                const isClientLoading = loadingClientById && loadingClientId === data.n_cliente;

                                return (
                                    <tr key={data.n_cliente + index}>
                                        <td className="border px-4 py-2 flex text-cyan-600 items-center justify-center cursor-pointer"
                                            onClick={() => handleClickClient(data.n_cliente)}>
                                            {isClientLoading ? (
                                                <i className="fa fa-spinner fa-spin"></i>
                                            ) : (
                                                data.n_cliente
                                            )}
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

                <DataForm Data={consultaDataClient} Catalog={catalogsCau} handleChange={handleChange}/>

                <ServiceLevelsCau Data={consultaDataClient} handleChange={handleChange}/>
            </div>
        </>
    )
}