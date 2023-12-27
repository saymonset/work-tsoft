import {useState} from "react";
import {Catalogo, ElementCau, FolioCau} from "../../../../../../model";

export const useCauInitVar = () => {

    const [selectedEnterprise, setSelectedEnterprise] = useState("0")

    const [status, setStatus] = useState('Abiertos')

    const [isEdit, setIsEdit] = useState(false);

    const [loadingSave, setLoadingSave] = useState(false);

    const [loadingCatalogo, setLoadingCatalogo] = useState(false);

    const [loadingInitSol, setLoadingInitSol] = useState(false);

    const [loadingOpened, setLoadingOpened] = useState(false);

    const [loadingClosed, setLoadingClosed] = useState(false);

    const [loadingModifies, setLoadingModifies] = useState(false);

    const [loadingFolio, setLoadingFolio] = useState(false);

    const [catalog, setCatalog] = useState<Catalogo[]>([])

    const [tableOpened, setTableOpened] = useState<ElementCau[]>([])

    const [queryFolio, setQueryFolio] = useState<FolioCau>({} as FolioCau)


    return {
        selectedEnterprise, setSelectedEnterprise,
        status, setStatus,
        isEdit, setIsEdit,
        loadingSave, setLoadingSave,
        loadingCatalogo, setLoadingCatalogo,
        loadingInitSol, setLoadingInitSol,
        loadingOpened, setLoadingOpened,
        loadingClosed, setLoadingClosed,
        loadingModifies, setLoadingModifies,
        loadingFolio, setLoadingFolio,
        catalog, setCatalog,
        tableOpened, setTableOpened,
        queryFolio, setQueryFolio
    };
}