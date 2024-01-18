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
import {buildIdPerfiles, procesarEvaluate, validRowClick} from "../../../../../helper";

export const useEditCatalog = ({nameCatalog, columns} : EditCatalogHookProps) => {

    const catalogStatic = useSelector((state: RootState<any, any, any>) =>
        state.catalogStatic) as unknown as Catalogo[];

    const [registroSeleccionado, setRegistroSeleccionado] =
        useState<RegistroEdit | null>(null);

    const [state, dispatchRed] = useReducer(reducer, initialState);

    const inputRefs = useRef<(HTMLInputElement | HTMLSelectElement | null)[]>([]);
    const selectRef = useRef<HTMLSelectElement>(null);

    const dispatch = useDispatch()

    const updateState = (newState: Partial<StateHookEdit>) => {
        dispatchRed({ type: 'UPDATE', payload: newState });
    };

    const sortedColumns = useMemo(() => {
        return columns
            .filter(column => !column.DisabledFieldForm)
            .sort((a, b) => (a.order ?? 0) - (b.order ?? 0));
    }, [columns]);

    const sortedTable = useMemo(() => {
        return columns
            .filter(column => !column.DisabledFieldTable)
    }, [columns]);

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

        const firstEditableIndex = sortedColumns.findIndex(column =>
            !column.DisabledFieldForm &&
            !column.isReadOnly &&
            column.type === "input"
        );

        if (firstEditableIndex !== -1 && inputRefs.current[firstEditableIndex]) {
            // @ts-ignore
            inputRefs.current[firstEditableIndex].focus();
            // @ts-ignore
            inputRefs.current[firstEditableIndex].scrollIntoView({ behavior: 'smooth' });
        }
    }, [nameCatalog]);


    const handleRowClick = useCallback(
        (registro: { id: string; [key: string]: string }) => {
            validRowClick(nameCatalog, catalogStatic, registro)
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

        const { ...request } = registroSeleccionado;

        if(nameCatalog.includes("PERFIL"))
        {
            await fetchDataPost(
                "/catalogos/perfil/borrar-catalogo",
                " al guardar catalogo",
                {},
                {
                    id: buildIdPerfiles(request, nameCatalog),
                    s_nombre_catalogo: nameCatalog.toLowerCase(),
                    s_user: userEncoded()}
            )
        }
        else
        {
            procesarEvaluate(nameCatalog, request, catalogStatic)

            await fetchDataPost(
                "/catalogos/borrar-catalogo",
                " al guardar catalogo",
                request,
                {
                    s_nombre_catalogo: nameCatalog.toLowerCase(),
                    s_user: userEncoded()}
            )
        }

        updateState({ loadingDelete: false });
        setRegistroSeleccionado(null)
        updateState({ catalogs: [], triggerCatalogs: true });

    }, [nameCatalog, registroSeleccionado]);

    const handleSave = async () => {
        if (!(await validarCampos())) {
            return;
        }

        updateState({ loadingSave: true });

        const {...request } = registroSeleccionado;

        procesarEvaluate(nameCatalog, request, catalogStatic);

        eraseRequest(request);

        const url = nameCatalog.includes("PERFIL") ?
            "/catalogos/perfil/guardar-catalogo" : "/catalogos/guardar-catalogo"

        await fetchDataPost(
            url,
            " al guardar catalogo",
            request,
            {
                s_nombre_catalogo: nameCatalog.toLowerCase(),
                s_user: userEncoded()
            }
        );

        updateState({ loadingSave: false });
        setRegistroSeleccionado(null);
        updateState({ catalogs: [], triggerCatalogs: true });
    };


    const validarCampos = async (): Promise<boolean> => {
        if (registroSeleccionado === null) {
            await showAlert('warning', 'Atencion', "No hay ningún registro seleccionado.");
            return false;
        }

        for (let column of columns) {
            const columnName = column.name.toLowerCase();

            if (column.DisabledFieldForm) {
                continue;
            }

            if (!(columnName in registroSeleccionado) || registroSeleccionado[columnName] === "") {
                await showAlert('warning', 'Atencion', `El campo ${column.name} está vacío.`);
                return false;
            }
        }
        return true;
    };

    const eraseRequest = (request: RegistroEdit): void => {
        if ('error' in request) {
            delete request.error;
        }
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

    // const validSelectValue = useCallback(
    //     (value: string) => {
    //         return registroSeleccionado?.[value] ?? "";
    //     },
    //     [registroSeleccionado]
    // );

    const validSelectValue = useCallback(
        (value: string, catalogName: string | undefined) => {

            const currentValue = registroSeleccionado?.[value];

            if (!isNaN(Number(currentValue))) {
                return currentValue;
            }

            const foundCatalog = catalogStatic.find(catalog => catalog.catalogo === catalogName);
            if (foundCatalog) {
                const foundKey = Object.keys(foundCatalog.registros).find(key => foundCatalog.registros[key] === currentValue);
                return foundKey ?? currentValue;
            }

            return currentValue ?? "";
        },
        [registroSeleccionado]
    );


    const registros = useMemo(() => {
        return state.catalogs?.map((catalog, index) => ({
            id: String(index + 1),
            ...columns
                .filter(column => !column.DisabledFieldTable)
                .reduce<Partial<RegistroEdit>>((acc, column) => {
                    const key = column.name.toLowerCase();
                    acc[key] = String(catalog[key]);
                    return acc;
                }, {})
        }));
    }, [state.catalogs, columns]);



    return {
        isNew: state.isNew,
        selectRef, inputRefs, sortedColumns, sortedTable,
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