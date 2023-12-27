import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {CalifEmisoraData} from "../../../../../../model";
import React, {useEffect, useState} from "react";
import {updateFormCalifEmi} from "../../../../../../redux/Calificaciones/Emisoras/actions";

export const SelectFileCalifEmi = ({ name, fieldB64 }: { name: string; fieldB64: string }) => {

    const formData = useSelector((state: RootState<any, any, any>) =>
        state.formCalifEmi) as unknown as CalifEmisoraData;

    const [selectedFile, setSelectedFile] = useState<string | null>(() =>
        formData?.[name] ?? null
    );

    const [fileBase64, setFileBase64] = useState<string>("");
    const dispatch = useDispatch();

    useEffect(() => {
        if (fileBase64) {

            const updatedFormData = {
                ...formData,
                [name]: selectedFile?.split('.')[0] ?? '',
                [fieldB64]: fileBase64,
            };

            dispatch(updateFormCalifEmi(updatedFormData));
        }
    }, [fileBase64]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            setSelectedFile(file.name);
            const reader = new FileReader();
            reader.onloadend = () => {
                setFileBase64(reader.result as string);
            }
            reader.readAsDataURL(file);
        }
    };

    return (
        <>
            <div className="text-xs text-cyan-700 my-3">
                <label className="relative cursor-pointer bg-cyan-700 hover:bg-green-700 text-white py-2 px-4 rounded">
                    <span className="text-sm">Examinar...</span>
                    <input id={`${name}-file-upload`}
                           type="file"
                           className="hidden"
                           name={name}
                           onChange={handleChange} />
                </label>
                <span className="py-2 px-4 text-cyan-700">{selectedFile ?? 'Ningún archivo seleccionado.'}</span>
            </div>
        </>
    );
};
