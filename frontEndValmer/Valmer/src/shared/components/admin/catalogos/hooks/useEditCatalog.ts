import React, {useCallback, useEffect, useMemo, useReducer, useRef, useState} from "react";
import {
    Catalogo,
    EditCatalogHookProps,
    RegistroEdit,
    ResponseConstCatAdmin,
    StateHookEdit
} from "../../../../../model";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {fetchDataGetRet, fetchDataPost, showAlert, userEncoded} from "../../../../../utils";
import {updateCatalogStatic} from "../../../../../redux";
import {initialState, reducer} from "./components";

export const useEditCatalog = ({nameCatalog, columns} : EditCatalogHookProps) => {

    const catalogStatic = useSelector((state: RootState<any, any, any>) =>
        state.catalogStatic) as unknown as Catalogo[];

    const [registroSeleccionado, setRegistroSeleccionado] =
        useState<RegistroEdit | null>(null);

    const [state, dispatchRed] = useReducer(reducer, initialState);

    const inputRef = useRef<HTMLInputElement>(null);
    const selectRef = useRef<HTMLSelectElement>(null);

    const dispatch = useDispatch()

    const updateState = (newState: Partial<StateHookEdit>) => {
        dispatchRed({ type: 'UPDATE', payload: newState });
    };

    useEffect(() => {
        const getCatalogs = async () => {
            updateState({ loadingCatalog: true });

            try {
                const response : ResponseConstCatAdmin = await fetchDataGetRet(
                    "/catalogos/consulta-catalogo",
                    " al obtener catalogos latam",
                    {
                        num_registros: 0,
                        posicion: 0,
                        s_nombre_catalogo: nameCatalog
                    }
                );

                updateState({
                    catalogs: response.body.registros,
                    loadingCatalog: false,
                    triggerCatalogs: false });

            } catch (error) {
                console.error('Error al obtener los catálogos:', error);
                updateState({ loadingCatalog: false });
            }
        };

        if (!state.catalogs || state.catalogs.length === 0) {
            getCatalogs().then();
        }
    }, [state.catalogs, state.triggerCatalogs, nameCatalog]);

    useEffect(() => {
        const getCatalogStatic = async () => {
            updateState({ loadingCatalogStatic: true });

            const fetches = [
                fetchDataGetRet(
                    "/catalogos/catalogos-static",
                    " al obtener catalogos estaticos",
                    {}),
                fetchDataGetRet(
                    "/deuda/catalogos",
                    " al obtener catalogos deuda",
                    {}),
                fetchDataGetRet(
                    "/latam/cr/mantenimiento-cau/catalogos",
                    " al obtener catalogos cr cau",
                    {})
            ];

            try
            {
                const [responseStatic, responseDeuda, responseCau] = await Promise.all(fetches);

                const mergedCatalogs = responseStatic.body.catalogos.concat(
                    responseDeuda.body.catalogos,
                    responseCau.body.catalogos
                );

                dispatch(updateCatalogStatic(mergedCatalogs));
            }
            catch (error)
            {
                console.error("Error en la consulta de catalogos static:", error);
            }
            finally
            {
                updateState({ loadingCatalogStatic: false });
            }
        };

        if (!catalogStatic || catalogStatic.length === 0) {
            getCatalogStatic().then();
        }
    }, []);

    const handleNew = useCallback(async () => {

        updateState({ loadingNewId: true, isNew: true });

        const response : ResponseConstCatAdmin = await fetchDataGetRet(
            "/catalogos/obtiene-nuevo-id",
            " al obtener nuevo id",
            {s_nombre_catalogo: nameCatalog}
        );

        updateState({ loadingNewId: false });

        const key = Object.keys(response.body)[0];

        setRegistroSeleccionado(({
            id: (response.body as any)[key].toString(),
            [key.toLowerCase()]: (response.body as any)[key].toString(),
        }));

        if(inputRef.current) {
            inputRef.current.focus();
            inputRef.current.scrollIntoView({ behavior: 'smooth' });
        } else if(selectRef.current) {
            selectRef.current.focus();
            selectRef.current.scrollIntoView({ behavior: 'smooth' });
        }
    }, [nameCatalog]);


    const handleRowClick = useCallback(
        (registro: { id: string; [key: string]: string }) => {
            updateState({ isNew: false});
            setRegistroSeleccionado({
                ...registro,
                id: String(registro.id),
            });
        },
        []
    );


    const handleChange = React.useCallback(
        async (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
            const {name, value} = e.target;

            setRegistroSeleccionado(prevRegistro => {
                if (prevRegistro === null) {
                    return null;
                }
                return {
                    ...prevRegistro,
                    [name.toLowerCase()]: value
                };
            });

            if (name === "n_tipo_emisor") {
                updateState({ loadingNomCorto: true});

                const response = await fetchDataGetRet("/catalogos/obtiene-nombre-corto",
                    " al obtener nombre corto",
                    {n_tipo_emisor: value})

                setRegistroSeleccionado(prevRegistro => {
                    if (prevRegistro === null) {
                        return null;
                    }
                    return {
                        ...prevRegistro,
                        [name.toLowerCase()]: value,
                        s_nomcorto: response.body?.s_nomcorto
                    };
                });

                updateState({ loadingNomCorto: false});
            }
        },
        []
    );


    const handleDelete = useCallback(async () => {

        if (registroSeleccionado === null) {
            await showAlert('warning', 'Atencion', "No hay ningún registro seleccionado.");
            return;
        }
        updateState({ loadingDelete: true });

        const { id, ...request } = registroSeleccionado;

        await fetchDataPost(
            "/catalogos/borrar-catalogo",
            " al guardar catalogo",
            request,
            {
                s_nombre_catalogo: nameCatalog.toLowerCase(),
                s_user: userEncoded()}
        )

        updateState({ loadingDelete: false });
        setRegistroSeleccionado(null)
        updateState({ catalogs: [], triggerCatalogs: true });

    }, [nameCatalog, registroSeleccionado]);

    const handleSave = async () => {
        if (registroSeleccionado === null) {
            await showAlert('warning', 'Atencion', "No hay ningún registro seleccionado.");
            return;
        }

        for (let column of columns) {
            const columnName = column.name.toLowerCase();

            if (!(columnName in registroSeleccionado) || registroSeleccionado[columnName] === "") {
                await showAlert('warning', 'Atencion', `El campo ${column.name} está vacío.`);
                return;
            }
        }

        updateState({ loadingSave: true });

        const { id, ...request } = registroSeleccionado;

        await fetchDataPost(
            "/catalogos/guardar-catalogo",
            " al guardar catalogo",
            request,
            {
                s_nombre_catalogo: nameCatalog.toLowerCase(),
                s_user: userEncoded()}
        )

        updateState({ loadingSave: false });
        setRegistroSeleccionado(null)
        updateState({ catalogs: [], triggerCatalogs: true });
    };

    const validInputValue = useCallback(
        (name: string) => {
            return registroSeleccionado && (registroSeleccionado[name] !== null
                && registroSeleccionado[name] !== undefined)
                ? registroSeleccionado[name]
                : ""
        },
        [registroSeleccionado]
    );

    const validSelectValue = useCallback(
        (name: string) => {
            return registroSeleccionado?.[name] ?? "";
        },
        [registroSeleccionado]
    );

    const registros = useMemo(() => {
        return state.catalogs?.map((catalog, index) => ({
            id: String(index + 1),
            ...columns.reduce<Partial<RegistroEdit>>((acc, column) => {
                const key = column.name.toLowerCase();
                acc[key] = String(catalog[key]);
                return acc;
            }, {})
        }));
    }, [state.catalogs, columns]);

    return {
        isNew: state.isNew,
        selectRef, inputRef,
        loadingNomCorto: state.loadingNomCorto,
        registros, loadingSave: state.loadingSave,
        loadingDelete: state.loadingDelete, loadingNewId: state.loadingNewId,
        loadingCatalogStatic: state.loadingCatalogStatic, loadingCatalog: state.loadingCatalog,
        catalogs: state.catalogs, catalogStatic,
        handleNew, handleRowClick,
        handleChange, handleSave,
        handleDelete, validInputValue,
        validSelectValue
    };
}