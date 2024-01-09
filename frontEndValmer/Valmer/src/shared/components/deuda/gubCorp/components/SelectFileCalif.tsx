import React from "react";
import {SelectFileProps} from "../../../../../model";

export const SelectFileCalif = (props: SelectFileProps) => {
    return (
        <>
            <div className="text-xs text-cyan-700 my-0 flex items-center">
                <label className="relative cursor-pointer h-9 bg-cyan-700 hover:bg-green-700 text-white py-2 px-4 rounded">
                    <span className="text-sm">Examinar...</span>
                    <input
                        type="file"
                        className="hidden"
                        onChange={(e) => props.handleFileChange(e, props.section)}
                    />
                </label>
                <span className="px-4 text-cyan-700">{props.fileName ? props.fileName : 'Ningún archivo seleccionado.'}</span>
            </div>
        </>
    );
};