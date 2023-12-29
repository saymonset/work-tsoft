import {capitalizeDate} from "../../../../utils";
import React from "react";

export const TitleDate = ({title}: any) => {
    return (
        <>
            <div className="flex">
                <div className="flex-1  ml-8 text-cyan-700 text-2xl font-semibold">
                    {title}
                </div>
                <div className="flex-1 mr-6  text-right text-cyan-700 text-lg font-bold">
                    {capitalizeDate()}
                </div>
            </div>
            <div className="grid grid-cols-1 gap-2">
                <div className="border-gray-300 border my-0"></div>
            </div>
        </>
    )
}