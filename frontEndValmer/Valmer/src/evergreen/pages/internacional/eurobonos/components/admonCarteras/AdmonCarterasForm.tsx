import React from "react";
import {ButtonContent} from "../../../../../../shared";
import {useAdmonModal, useAdmonHandleData} from "./hooks";
import {MoonLoader} from "react-spinners";
import {AdmonModal, ChargeFile, TableWallet} from "./components";

export const AdmonCarterasForm = () => {

    const {
        catalogInter,
        selectedClient,
        triggerErase,
        showTableWallet,
        showFileWallet,
        searchClientWallet,
        loadingInter,
        loadingQueryClient,
        sortField,
        sortDirection,
        sortedData,
        handleSort,
        setSearchClient,
        handleSelectChange,
        handleQueryClient,
        handleShowFileWallet
    } = useAdmonHandleData()

    const {
        loading,
        isOpen,
        cliente,
        envioSFTP,
        envioEmail,
        handleOpenModal,
        handleCloseModal,
        handleInputChange,
        handleCheckboxChange,
        handleSave
    } = useAdmonModal()


    const getSortIcon = (field: string) => {
        if (sortField !== field) return <i className="ml-2 fa-solid fa-sort"></i>;
        if (sortDirection === "asc") return <i className="ml-2 fa-solid fa-sort-up"></i>;
        return <i className="ml-2 fa-solid fa-sort-down"></i>;
    };

    if (triggerErase) {
        return (
            <div className="flex justify-center items-center h-full"></div>
        )
    }

    if (loadingInter) {
        return (
            <div className="flex justify-center items-center h-full">
                <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60}/>
            </div>
        );
    }

    return (
        <>
            <table className="ml-8 mt-12 mb-10">
                <tbody>
                <tr>
                    <td valign="top">
                        <table>
                            <tbody>
                            <tr>
                                <td>
                                    <div className="flex items-center my-3">
                                        <div className="relative mr-2">
                                            <select name="euro_cliente" className="block py-2.5 px-0 w-64 text-sm text-gray-900
                                                    bg-transparent border-0 border-b-2 border-gray-200 appearance-none
                                                    dark:border-gray-600 dark:focus:border-cyan-700
                                                    focus:outline-none focus:ring-0 peer"
                                                    value={selectedClient}
                                                    onChange={handleSelectChange}
                                            >
                                                <option value="default">...</option>
                                                {
                                                    catalogInter?.body?.catalogos
                                                        ? (
                                                            catalogInter.body.catalogos.find(c =>
                                                                c.catalogo === "EURO_CLIENTE")?.registros
                                                            && Object.entries(
                                                                catalogInter.body.catalogos.find(c =>
                                                                    c.catalogo === "EURO_CLIENTE")?.registros ?? {})
                                                                .map(([key, value]) => (
                                                                    <option value={key} key={key}>{value}</option>
                                                                ))
                                                        )
                                                        : null
                                                }
                                            </select>
                                            <label htmlFor="euro_cliente"
                                                className="font-medium absolute text-base transform top-3 text-cyan-700
                                                scale-75 -translate-y-6 origin-[0]"
                                            >
                                                CLIENTE
                                            </label>
                                        </div>
                                        <button
                                            className="ml-4 px-3 py-1 bg-cyan-700 hover:bg-green-700
                                            text-white rounded-md" onClick={handleOpenModal}>
                                            +
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <button
                                        className="mt-8 w-44 bg-cyan-700 hover:bg-green-700
                                        text-white py-1 rounded-md px-3 mx-1"
                                        onClick={handleShowFileWallet}
                                    >
                                        <span>Cargar</span>
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <button
                                        className="w-44 bg-cyan-700 hover:bg-green-700
                                        text-white py-1 rounded-md px-3 mx-1"
                                        onClick={handleQueryClient}
                                    >
                                        <ButtonContent name="Consultar" loading={loadingQueryClient}/>
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                    <td align="center"></td>
                </tr>
                </tbody>
            </table>

            <div className="mt-0">
                {
                    showTableWallet ? (
                        <TableWallet searchClientWallet={searchClientWallet}
                                     sortedData={sortedData}
                                     handleSort={handleSort}
                                     getSortIcon={getSortIcon}
                                     setSearchClient={setSearchClient}/>
                    ) : null
                }
            </div>

            <div className="mt-2">
                {showFileWallet && <ChargeFile />}
            </div>


            <AdmonModal isOpen={isOpen} loading={loading} cliente={cliente} envioSFTP={envioSFTP}
                        envioEmail={envioEmail} handleCloseModal={handleCloseModal}
                        handleInputChange={handleInputChange} handleCheckboxChange={handleCheckboxChange}
                        handleSave={handleSave}/>
        </>
    );
};