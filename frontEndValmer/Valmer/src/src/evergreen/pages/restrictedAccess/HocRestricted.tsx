import {getIdView} from "../../../utils";
import {RestrictedAccess} from "./pages";
import {MoonLoader} from "react-spinners";
import React from "react";
import {useRouteRestricted} from "./hooks";

interface AccessRestrictedProps {
    title: string;
    view: string;
    children: React.ReactNode;
}

export const HocRestricted = ({ title, view, children }: AccessRestrictedProps) => {

    const { IsRestricted, loading } = useRouteRestricted(getIdView(view));

    if (IsRestricted) {
        return (
            <RestrictedAccess title={title}/>
        );
    } else if (loading) {
        return (
            <div className="flex justify-center h-96 items-center mt-24 animate__animated animate__fadeIn">
                <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80} />
            </div>
        );
    }

    return <>{children}</>;
};