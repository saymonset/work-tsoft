import { MoonLoader } from "react-spinners"
import { ButtonsCau, DatosCau, FiltrosCauCerrados, TableCau } from "./components"
import { useGetCatalogs, useHandleDataCerrados } from "./hooks"
import { HocRestricted } from "../../restrictedAccess"

export const CerradosCau = () => {
    const {
        loadingCatalog,
        catalog
    } = useGetCatalogs()

    const {
        loadingDataTable,
        dataTable,
        empresa,
        servicio,
        area,
        numRegistros,
        posicion,
        search,
        infoCau,
        loadingInfoCau,
        setNumRegistros,
        setPosicion,
        setSearch,
        setFolio,
        setTriggerInfoCau,
        setEmpresa,
        setServicio,
        setArea,
        setTriggerDataTable,
        setInfoCau
    } = useHandleDataCerrados()

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

    const title = "Mantenimiento CAU"

    return (
        <HocRestricted title={title} view={title}>
            <ButtonsCau title={title + " Cerrados"} />

            <div className="form-cols-1">
                <FiltrosCauCerrados
                    catalog={catalog}
                    empresa={empresa}
                    servicio={servicio}
                    area={area}
                    setEmpresa={setEmpresa}
                    setServicio={setServicio}
                    setArea={setArea}
                    setTriggerData={setTriggerDataTable}
                    setData={setInfoCau}
                />

                <TableCau
                    cau="cierre"
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
                        cau="cierre"
                        catalog={catalog}
                        data={infoCau}
                        loading={loadingInfoCau}
                    />
                </div>
            </div>
        </HocRestricted>
    )
}