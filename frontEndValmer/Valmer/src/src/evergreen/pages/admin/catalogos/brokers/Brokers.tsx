import {ShowEditCatalog} from "../../../../../shared";
import {DataCatBrokers} from "../../../../../utils";

export const Brokers = () => {
    return (
        <ShowEditCatalog titleName="Catálogo Brokers" DataCatalog={DataCatBrokers}/>
    )
}