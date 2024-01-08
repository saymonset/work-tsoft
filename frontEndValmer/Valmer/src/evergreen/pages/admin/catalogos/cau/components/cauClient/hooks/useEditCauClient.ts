import React, {useEffect, useReducer} from "react";
import {
    ConsultaDataEditCauClient,
    ParamsEditCauClient,
    ResponseApiEditCauClient,
    ResponseCat, StateHookEditCauClient,
}
    from "../../../../../../../../model";
import {
    DataCauLevels,
    downloadFiles, fetchDataGetRet, fetchDataPost, getCurrentDate,
    requiredFieldsEditCauClient,
    showAlert, transformToResponseEditCauClient, userEncoded
} from "../../../../../../../../utils";
import {initialStateEditCauClient, reducerEditCauClient} from "./components";
import {AxiosResponse} from "axios";
import {valmerApi} from "../../../../../../../../api";

export const useEditCauClient = ({nameCatalog}: { nameCatalog: string }) => {

    const [state, dispatch] =
        useReducer(reducerEditCauClient, initialStateEditCauClient);

    const updateState = (newState: Partial<StateHookEditCauClient>) => {
        dispatch({ type: 'UPDATE', payload: newState });
    };


    useEffect(() => {
        const getCatalogs = async () => {
            updateState({ loadingCatalog: true });

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

                updateState({
                    catalogsCau: responseCau.body.catalogos,
                    catalogs: response.body.registros,
                    triggerCatalogs: false  })

            } catch (error) {
                console.error('Error al obtener los catálogos:', error);
            }
            finally {
                updateState({ loadingCatalog: false });
            }
        };

        if (!state.catalogs || state.catalogs.length === 0) {
            getCatalogs().then();
        }
    }, [state.catalogs, state.triggerCatalogs]);

    useEffect(() => {
        const getCatalogsCau = async () => {
            updateState({ loadingCatalogCau: true });

            try {
                const responseCau = await fetchDataGetRet(
                    "/latam/cr/mantenimiento-cau/catalogos",
                    " al obtener catalogos cr cau",
                    {}
                );

                updateState({catalogsCau: responseCau.body.catalogos})

            } catch (error) {
                console.error('Error al obtener los catálogos:', error);
            }
            finally {
                updateState({ loadingCatalogCau: false });
            }
        };

        if (!state.catalogsCau || state.catalogsCau.length === 0) {
            getCatalogsCau().then();
        }
    }, [state.catalogsCau]);

    useEffect(() => {
        if (state.enterprise && state.enterprise !== 'default') {
            const foundCatalog = state.catalogsCau.find(catalog =>
                catalog.catalogo === "CAU_EMPRESA");
            const foundKey = foundCatalog?.registros[state.enterprise];
            const filtered = foundKey ? state.catalogs.filter(catalog =>
                catalog.s_nomcorto === foundKey) : state.catalogs;

            updateState({
                enterpriseFilteredCatalogs: filtered,
                filteredCatalogs: filtered,
                selectClient: "default"})

        } else {
            updateState({
                enterpriseFilteredCatalogs: state.catalogs,
                filteredCatalogs: state.catalogs,
                selectClient: "default"})
        }
    }, [state.enterprise, state.catalogs]);

    useEffect(() => {
        if (state.selectClient && state.selectClient !== 'default') {
            const furtherFiltered = state.enterpriseFilteredCatalogs.filter(catalog =>
                catalog.n_cliente === Number(state.selectClient));
            updateState({filteredCatalogs: furtherFiltered})
        } else {
            updateState({filteredCatalogs: state.enterpriseFilteredCatalogs})
        }
    }, [state.selectClient, state.enterpriseFilteredCatalogs]);


    useEffect(() => {
        if (state.enterprise && state.enterprise !== 'default') {
            getClientById().then();
        } else {
            updateState({
                enterpriseFilteredCatalogs: state.catalogs,
                filteredCatalogs: state.catalogs,
                selectClient: "default",
                clients: {}})
        }
    }, [state.enterprise]);

    const getClientById = async () => {
        updateState({ loadingClient: true });

        try {
            const response: ResponseCat = await fetchDataGetRet(
                "/catalogos/cau-cliente/consulta-clientes-empresa",
                " al obtener catalogo clientes empresa",
                {
                    n_emp: state.enterprise,
                }
            );

            updateState({ clients: response.body });

        } catch (error) {
            console.error('Error al obtener los catálogos:', error);
        }
        finally {
            updateState({ loadingClient: false });
        }
    };

    const handleClickClient = async (nCliente: number) => {

        updateState({
            loadingClientId: nCliente,
            consultaDataClient: {} as ConsultaDataEditCauClient,
            loadingClientById: true})

        try {

            const response: ResponseApiEditCauClient = await fetchDataGetRet(
                "/catalogos/cau-cliente/consulta-catalogo-id",
                " al obtener catalogo clientes empresa",
                {
                    id: nCliente,
                    s_nombre_catalogo: nameCatalog
                }
            );

            updateState({consultaDataClient: transformToResponseEditCauClient(response),});

        } catch (error) {
            console.error('Error al obtener los catálogos:', error);
        }
        finally {
            updateState({ loadingClientById: false });
        }
    }

    const handleGetCsv = async () => {
        updateState({ loadingCsv: true });

        let params: ParamsEditCauClient = {
            s_nombre_catalogo: nameCatalog
        };

        if (state.selectClient && state.selectClient !== "default") {
            params.n_cli = state.selectClient;
        }
        if (state.enterprise && state.enterprise !== "default") {
            params.n_emp = state.enterprise;
        }

        const response = await fetchDataGetRet(
            "/catalogos/cau-cliente/genera-csv",
            " se genero un error en servicio descarga log",
            params
        );

        if (response) {
            downloadFiles(response.body.contenido,
                response.body.nombreCompleto,
                'text/csv')
        } else {
            console.error('No se recibió un archivo válido');
        }

        updateState({ loadingCsv: false });
    }

    const handleNewId = async () => {
        updateState({ loadingNewId: true, consultaDataClient: {} as ConsultaDataEditCauClient });
        const response = await fetchDataGetRet(
            "/catalogos/obtiene-nuevo-id",
            " al obtener nuevo id",
            {s_nombre_catalogo: nameCatalog}
        );

        const newClienteId = response.body.n_cliente.toString();

        const nivelesServicio = DataCauLevels.reduce((acc: { [key: string]: string }, level) => {
            acc[level.value] = "2";
            return acc;
        }, {});

        updateState({
            consultaDataClient: {
                ...{} as ConsultaDataEditCauClient,
                N_CLIENTE: newClienteId,
                D_FECHA: getCurrentDate(),
                N_HITS: "0",
                N_STATUS: "2",
                ...nivelesServicio
            },
            loadingNewId: false,
        });
    };

    const handleChange = <T extends HTMLInputElement | HTMLSelectElement>(e: React.ChangeEvent<T>) => {
        const {name, value} = e.target;
        updateState({ consultaDataClient: { ...state.consultaDataClient, [name]: value } });
    }

    const handleSave = async () => {
        updateState({ loadingSave: true });

        for (const field of requiredFieldsEditCauClient) {
            const value = state.consultaDataClient[field.key as keyof ConsultaDataEditCauClient];

            if (value === undefined || value === "") {
                await showAlert('warning', 'Atención', `El campo ${field.key} está vacío.`);
                updateState({ loadingSave: false });
                return;
            }
        }

        try
        {
            let params = { s_nombre_catalogo: nameCatalog, s_user: userEncoded() }

            const response: AxiosResponse<any> =
                await valmerApi.post("/catalogos/cau-cliente/guardar-catalogo",
                state.consultaDataClient, {params});

            await showAlert("success", "Guardado",
                response?.data?.message ?? response?.data?.body?.message ?? response?.data?.body);

            updateState({
                consultaDataClient: {} as ConsultaDataEditCauClient,
                catalogs: [],
            });
        }
        catch (error: any) {
            if (error.message.includes('Network Error')) {
                await showAlert('error', 'Error', 'No hay conexión con el servidor');
            } else {
                await showAlert('error',
                    `Error al intentar guardar cliente: ${state.consultaDataClient.N_CLIENTE}`,
                    error.message);
            }
        }
        finally {
            updateState({ loadingSave: false });
        }
    };

    const handleErase = async () => {
        updateState({ loadingErase: true });
        try {
            await fetchDataPost(
                "/catalogos/cau-cliente/borrar-catalogo",
                " al intentar borrar cliente",
                state.consultaDataClient,
                { s_nombre_catalogo: nameCatalog, s_user: userEncoded() }
            );

            updateState({
                consultaDataClient: {} as ConsultaDataEditCauClient,
                catalogs: []
            });
        } catch (error) {
            console.error('Error al intentar borrar el cliente:', error);
        }
        finally {
            updateState({ loadingErase: false });
        }
    };

    const handleEnterprise = (e: React.ChangeEvent<HTMLSelectElement>) => {
        updateState({ enterprise: e.target.value });
    };

    const handleClient = (e: React.ChangeEvent<HTMLSelectElement>) => {
        updateState({ selectClient: e.target.value });
    };

    return {
        consultaDataClient: state.consultaDataClient,
        loadingClientId: state.loadingClientId,
        loadingClient: state.loadingClient,
        loadingCatalog: state.loadingCatalog,
        loadingCatalogCau: state.loadingCatalogCau,
        loadingClientById: state.loadingClientById,
        loadingSave: state.loadingSave,
        loadingErase: state.loadingErase,
        loadingNewId: state.loadingNewId,
        loadingCsv: state.loadingCsv,
        clients: state.clients,
        catalogs: state.catalogs,
        catalogsCau: state.catalogsCau,
        filteredCatalogs: state.filteredCatalogs,
        handleClickClient,
        handleEnterprise,
        handleSave,
        handleErase,
        handleChange,
        handleClient,
        handleNewId,
        handleGetCsv
    }
}