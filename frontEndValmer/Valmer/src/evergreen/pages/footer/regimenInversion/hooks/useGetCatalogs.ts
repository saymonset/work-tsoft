import { CatalogoRegInv } from "../../../../../model/RegimenInversion";

export const useGetCatalogs = (catalog: CatalogoRegInv[] | undefined, c: string | undefined) => {
    if (!catalog || !c) return [];

    const selectCatalog = catalog.find((item) => item.catalogo === c);

    if (!selectCatalog) return [];

    let entries: [string, string][] = [];

    if (selectCatalog?.registros) {
        entries = Object.entries(selectCatalog?.registros ?? {});
    } else if (selectCatalog?.listado) {
        entries = Object.entries(selectCatalog?.listado ?? {});
    }
    
    entries.sort((a, b) => {
        if (a[1] === 'SD' || b[1] === 'SD') return a[1] === 'SD' ? 1 : -1;
        return a[1].localeCompare(b[1]);
    });

    return entries;
};