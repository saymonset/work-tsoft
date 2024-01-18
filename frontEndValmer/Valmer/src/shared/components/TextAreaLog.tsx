import React from "react";

export function TextAreaLog({log}: Readonly<{ log: string[] }>) {
    return (
        <div
            className="bg-gray-900 text-green-500 p-2 mb-10 sm:w-1/8 md:w-2/4 lg:w-1/2 xl:w-full overflow-x-auto max-h-[30rem]"
            style={{minHeight: '30rem', maxWidth: '40rem'}}
            dangerouslySetInnerHTML={{__html: log}}
        />
    );
}