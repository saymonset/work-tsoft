import {ShowEditCatalog} from "../../../../../shared";
import {DataPerfilInstrumento} from "../../../../../utils";

export const Perfil = () => {
    return (
        <ShowEditCatalog titleName="Catálogo Perfil" DataCatalog={DataPerfilInstrumento}/>
    )
};