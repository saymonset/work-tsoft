import { MoonLoader } from "react-spinners";
import { ButtonsCau, ComboEmpresa, DatosCau, TableCau } from "./components"
import { useGetCatalogs, useHandleDataModif } from "./hooks"
import { HocRestricted } from "../../restrictedAccess";

export const ModificacionCau = () => {

    const {
        loadingCatalog,
        catalog
    } = useGetCatalogs()

    const {
        loadingDataTable,
        dataTable,
        empresa,
        numRegistros,
        posicion,
        search,
        infoCau,
        loadingInfoCau,
        setEmpresa,
        setTriggerDataTable,
        setNumRegistros,
        setPosicion,
        setSearch,
        setFolio,
        setTriggerInfoCau
    } = useHandleDataModif()

    if (loadingCatalog || !catalog.length || loadingDataTable || !dataTable) {
        return (
            <div className="flex justify-center items-center h-96">
                {loadingCatalog || loadingDataTable ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={60}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    const title = "Mantenimiento CAU Modificación"

    return (
        <HocRestricted title={title} view={title}>
            <ButtonsCau title={title} />

            <div className="form-cols-1">
                <ComboEmpresa 
                    catalog={catalog}
                    empresa={empresa}
                    setEmpresa={setEmpresa}
                    setTriggerData={setTriggerDataTable}
                    setNumRegistros={setNumRegistros}
                    setPosicion={setPosicion}
                    setSearch={setSearch}
                />

                <TableCau
                    cau="modificacion"
                    data={dataTable} 
                    loading={loadingDataTable} 
                    numRegistros={numRegistros}
                    posicion={posicion}
                    search={search}
                    setNumRegistros={setNumRegistros}
                    setPosicion={setPosicion}
                    setSearch={setSearch}
                    setFolio={setFolio}
                    setTriggerInfo={setTriggerInfoCau}
                />

                <div className="form-cols-2">
                    <DatosCau
                        cau="modificacion"
                        catalog={catalog}
                        data={infoCau}
                        loading={loadingInfoCau}
                    />
                </div>
            </div>
        </HocRestricted>
    )
}