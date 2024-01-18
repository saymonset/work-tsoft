import React, {useState} from "react";
import {fetchDataGetRet, showAlert} from "../../../../../../utils";
import {usePostGroup} from "./usePostGroup";
import {usePostDeleteGroup} from "./usePostDeleteGroup";

interface ResponseNewId {
    status: number;
    body: {
        N_GRUPO: string;
    };
}
export const useMailGrupos = () =>
{
    const [selectedGroup, setSelectedGroup] = useState({
        n_grupo: "",
        s_descripcion: ""
    });

    const { loadingCarga, fetchDataPostGroup, changeCatalogPost } = usePostGroup();
    const { loadingCargaDelete, fetchDataPostDeleteGroup, changeCatalogDelete } =
        usePostDeleteGroup(setSelectedGroup);
    const [loadingNew, setLoadingNew] = useState(false)

    const handleSelectedGroup = (group: any) => {
        setSelectedGroup(group);
    }

    const handleGuardarClick = async (e:  React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        const { n_grupo, s_descripcion } = selectedGroup;

        if (!n_grupo) {
            await showAlert('warning', 'Atencion', `El campo N_GRUPO está vacío.`);
            return;
        }

        if (!s_descripcion) {
            await showAlert('warning', 'Atencion', `El campo S_DESCRIPCION está vacío.`);
            return;
        }

        await fetchDataPostGroup(selectedGroup);
    }

    const handleDeleteClick = async (e:  React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        await fetchDataPostDeleteGroup(selectedGroup);
    }

    const handleClickNew = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setLoadingNew(true)

        setSelectedGroup({
            n_grupo: "",
            s_descripcion: "",
        });

        const { body: { N_GRUPO } }: ResponseNewId = await fetchDataGetRet(
            "/catalogos/obtiene-nuevo-id",
            " al obtener nuevo id",
            { s_nombre_catalogo: "ENVIOS_MAIL_GRUPOS" }
        );

        setSelectedGroup({
            n_grupo: N_GRUPO,
            s_descripcion: "",
        });

        setLoadingNew(false)
    }

    return {
        selectedGroup,
        changeCatalogDelete,
        changeCatalogPost,
        loadingNew,
        loadingCarga,
        loadingCargaDelete,
        setSelectedGroup,
        handleSelectedGroup,
        handleClickNew,
        handleGuardarClick,
        handleDeleteClick
    }

}