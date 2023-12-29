import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {Catalogo} from "../../../../../../../../model";
import {useEffect, useState} from "react";
import {fetchDataGetRet} from "../../../../../../../../utils";
import {updateCatalogCalifCorp} from "../../../../../../../../redux";

export const useCalifAntNew = () => {

    const catalogCalif = useSelector((state: RootState<any, any, any>) =>
        state.catalogCalifCorp) as unknown as Catalogo[]

    const [loadingCalif, setLoadingCalif] = useState(false)

    const dispatch = useDispatch()


    useEffect(() => {
        const getCatalogStatic = async () => {
            setLoadingCalif(true)

            const fetches = [
                fetchDataGetRet(
                    "instrumentos/calificadoras",
                    " al obtener catalogos calificadoreas",
                    {}),
                fetchDataGetRet(
                    "/deuda/catalogos/calificaciones",
                    " al obtener catalogos financiera",
                    {}),
            ];

            try
            {
                const [respCalificadoras, respCatalog] = await Promise.all(fetches);

                const catalogo: Catalogo = {
                    catalogo: 'CALIFICADORAS',
                    registros: respCalificadoras?.body
                };

                const updatedCatalogs = respCatalog.body.catalogos.concat(catalogo);
                dispatch(updateCatalogCalifCorp(updatedCatalogs));
            }
            catch (error)
            {
                console.error("Error en la consulta de catalogos static:", error);
            }
            finally
            {
                setLoadingCalif(false);
            }
        };

        if (!catalogCalif || catalogCalif.length === 0) {
            getCatalogStatic().then();
        }
    }, []);


    const getCatalogsFin = (c: string) => {
        let selection = ""
        switch (c) {
            case "1":
                selection = "SP_N";
                break;
            case "2":
                selection = "FITCH";
                break;
            case "3":
                selection = "MOODY";
                break;
            case "4":
                selection = "HR";
                break;
            case "5":
                selection = "VERUM";
                break;
            case "6":
                selection = "DBRS";
                break;
            case "7":
                selection = "BEST";
                break;
            default:
                selection ="default";
                break;
        }

        let selectCatalog = catalogCalif.filter((item) => {
            return item.catalogo.toLowerCase().includes(selection.toLowerCase());
        });

        let selectedRecords = selectCatalog.map((item) => item.registros);
        let combinedRecords = Object.assign({}, ...selectedRecords);

        let sortedEntries = Object.entries(combinedRecords).sort((a, b) => {
            return (a[1] as string).localeCompare(b[1] as string);
        });

        let sanitizedEntries = sortedEntries.map(entry => [entry[0], String(entry[1])]);

        return sanitizedEntries || [];
    }


    return{
        catalogCalif,
        loadingCalif,
        getCatalogsFin,
    }
}