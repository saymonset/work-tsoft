import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {CargaArchivoContent, UseChargeOptions} from "../../../../../../../../model";
import {updateFileWallet} from "../../../../../../../../redux";
import {useChargeFileGeneric} from "../../../hooks/useChargeFileGeneric";

export const useChargeFile = () => {

    const fechaEurobonos = useSelector((state: RootState<any, any, any>) =>
        state.fechaEurobonos) as unknown as string;

    const fileWallet = useSelector((state: RootState<any, any, any>) =>
        state.fileWallet) as unknown as CargaArchivoContent;

    const options: UseChargeOptions = {
        apiUrl: "/internacional/proceso-eurobonos/carga-cartera-manual",
        apiErrorMessage: " al guardar archivo carga-cartera-manual",
        data: fileWallet,
        method: updateFileWallet,
        params: {d_fecha_val: fechaEurobonos}
    };

    const {
        selectedFile,
        loading,
        setFileBase64,
        setSelectedFile,
        handleCharge} = useChargeFileGeneric(options)

    return {
        selectedFile,
        fileWallet,
        loading,
        handleCharge,
        setFileBase64,
        setSelectedFile
    }
}