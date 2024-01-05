import React from 'react'

export const Title  = ({title}: any) => {
    return (
        <>
            <div className="flex">
                <div className="flex-1 ml-8 text-cyan-700 text-lg font-semibold">
                    {title}
                </div>
            </div>
            <div className="grid grid-cols-1 gap-2">
                <div className="border-gray-300 border my-2"></div>
            </div>
        </>
    )
}