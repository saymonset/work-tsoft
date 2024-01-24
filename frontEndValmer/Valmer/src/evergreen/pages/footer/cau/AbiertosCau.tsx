import { MoonLoader } from "react-spinners";
import { useGetCatalogs, useHandleDataAbiertos } from "./hooks";
import {TableCau, ButtonsCau, ComboEmpresa, DatosCau, PasswordExpires} from "./components";
import { HocRestricted } from "../../restrictedAccess";

export const AbiertosCau = () => {
    const {
        loadingCatalog,
        catalog
    } = useGetCatalogs();

    const {
        loadingDataTable,
        dataTable,
        empresa,
        numRegistros,
        posicion,
        search,
        infoCau,
        loadingInfoCau,
        validaUser,
        loadingValidaUser,
        dataPassExp,
        loadingPassExp,
        catUsr,
        loadingCatUsr,
        setEmpresa,
        setTriggerDataTable,
        setNumRegistros,
        setPosicion,
        setSearch,
        setFolio,
        setTriggerInfoCau,
        setTriggerPassExp,
        setInfoCau
    } = useHandleDataAbiertos()

    if (loadingCatalog || !catalog.length || loadingDataTable || !dataTable || loadingValidaUser) {
        return (
            <div className="flex justify-center items-center h-96">
                {loadingCatalog || loadingDataTable || loadingValidaUser ? (
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
            <ButtonsCau title={title + " Abiertos"} />

            <div className="form-cols-1">
                
                <ComboEmpresa 
                    catalog={catalog}
                    empresa={empresa}
                    setEmpresa={setEmpresa}
                    setTriggerData={setTriggerDataTable}
                    setNumRegistros={setNumRegistros}
                    setPosicion={setPosicion}
                    setSearch={setSearch}
                    setData={setInfoCau}
                />

                <TableCau
                    cau="abiertos"
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
                        cau="abiertos"
                        catalog={catalog}
                        data={infoCau}
                        loading={loadingInfoCau}
                        catUsr={catUsr}
                        loadingCatUsr={loadingCatUsr}
                        setData={setInfoCau}
                    />

                    {validaUser && (
                        <PasswordExpires 
                            data={dataPassExp} 
                            loading={loadingPassExp}
                            validaUser={validaUser}
                            setTriggerData={setTriggerPassExp}
                        />
                    )}
                </div>
            </div>
        </HocRestricted>
    )
}