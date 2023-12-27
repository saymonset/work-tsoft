import { Catalogo } from "../../../../../../../../model";

export const getCatalogsSorted = (catalog: Catalogo[] | undefined, c: string | undefined) => {
    if (!catalog || !c) return [];

    const selectCatalog = catalog.find((item) => item.catalogo === c);
    if (!selectCatalog) return [];

    const entries = Object.entries(selectCatalog?.registros ?? {});
    
    entries.sort((a, b) => {
        if (a[1] < b[1]) return -1;
        if (a[1] > b[1]) return 1;

        const aIsUppercase = a[1] === a[1].toUpperCase();
        const bIsUppercase = b[1] === b[1].toUpperCase();

        if (aIsUppercase && !bIsUppercase) return -1;
        if (!aIsUppercase && bIsUppercase) return 1;
        
        return 0;
    });

    return entries;
};