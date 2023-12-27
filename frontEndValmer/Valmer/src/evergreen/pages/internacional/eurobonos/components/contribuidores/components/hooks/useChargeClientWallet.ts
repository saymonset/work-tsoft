import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {CargaArchivoContent, UseChargeOptions} from "../../../../../../../../model";
import {updateFileContributor} from "../../../../../../../../redux";
import {useChargeFileGeneric} from "../../../hooks/useChargeFileGeneric";

export const useChargeClientWallet = () => {

    const fileContributor = useSelector((state: RootState<any, any, any>) =>
        state.fileContributor) as unknown as CargaArchivoContent;

    const options: UseChargeOptions = {
        apiUrl: "/internacional/proceso-eurobonos/carga-cartera",
        apiErrorMessage: " al guardar archivo carga-cartera",
        data: fileContributor,
        method: updateFileContributor,
        params: {}
    };

    const {
        selectedFile,
        loading,
        setFileBase64,
        setSelectedFile,
        handleCharge} = useChargeFileGeneric(options)

    return {
        selectedFile,
        fileContributor,
        loading,
        setFileBase64,
        setSelectedFile,
        handleCharge
    }
}