import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {RespTableClients} from "../../../../../../../../../../model";
import React, {useState} from "react";
import {fetchDataGetRet} from "../../../../../../../../../../utils";
import {updateTableClients} from "../../../../../../../../../../redux";

export const useClientesActuales = () => {
    const tableClients = useSelector((state: RootState<any, any, any>) =>
        state.tableClients) as unknown as RespTableClients;

    const selectedTv = useSelector((state: RootState<any, any, any>) =>
        state.selectedTvInter) as unknown as string;

    const selectedEmisora = useSelector((state: RootState<any, any, any>) =>
        state.selectedEmisoraInter) as unknown as string;

    const selectedSerie = useSelector((state: RootState<any, any, any>) =>
        state.selectedSerieInter) as unknown as string;

    const [selectedClient, setSelectedClient] = useState<string>("")
    const [loadingClients, setLoadingClients] = useState<boolean>(false)

    const dispatch = useDispatch();
    const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedClient(event.target.value);
    };

    const handleNewClient = async () => {
        if (selectedClient) {
            setLoadingClients(true)
            const response = await fetchDataGetRet(
                "/instrumentos/eurobonos/agregar-cliente-instrumento",
                " al obtener agregar-cliente-instrumento",
                {
                    idCliente: selectedClient,
                    sTv: selectedTv,
                    sEmisora: selectedEmisora,
                    sSerie: selectedSerie
                }
            );
    
            const updatedTableClients = {
                ...tableClients,
                body: {
                    ...tableClients.body,
                    clientes_actuales: response.body.clientes_actuales
                }
            };
    
            dispatch(updateTableClients(updatedTableClients));
    
            setLoadingClients(false)
        }
    }

    const handleDeleteClient = async (value: string) => {
        setLoadingClients(true)
        const response = await fetchDataGetRet(
            "/instrumentos/eurobonos/eliminar-cliente-instrumento",
            " al obtener agregar-cliente-instrumento",
            {
                idCliente: value,
                sTv: selectedTv,
                sEmisora: selectedEmisora,
                sSerie: selectedSerie
            }
        );

        const updatedTableClients = {
            ...tableClients,
            body: {
                ...tableClients.body,
                clientes_actuales: response.body.clientes_actuales
            }
        };

        dispatch(updateTableClients(updatedTableClients));

        setLoadingClients(false)
    }

    return {
        loadingClients,
        tableClients,
        handleNewClient,
        handleDeleteClient,
        handleSelectChange
    }
}