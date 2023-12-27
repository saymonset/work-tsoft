import React from "react";
import {TitleDate} from "../../../../shared";

export const RestrictedAccess = (props: { title: string }) => {

    const user = localStorage.getItem('user');

    return (
        <>
            <TitleDate title={props.title} />

            <div className="animate__animated animate__fadeIn">
                <div className="mt-20 text-center text-2xl font-bold text-cyan-700">
                    ACCESO RESTRINGIDO
                </div>
                <div className="mt-4 text-center text-2xl font-bold text-cyan-700">
                    {user}
                </div>
            </div>
        </>
    );
};