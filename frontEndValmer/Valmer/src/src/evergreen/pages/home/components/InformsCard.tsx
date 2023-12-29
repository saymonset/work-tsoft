import React from "react";

interface Props {
    titles: string[];
}

export const InformsCard: React.FC<Props> = ({ titles }) => {
    const maxHeightClass = titles.length > 4 ? "h-60" : "";

    return (
        <div className={`card`} >
            <div className="rounded-lg bg-gradient-to-l from-cyan-950 to-cyan-700
            w-full h-1/3 flex items-center justify-start">
                <h2 className="ml-2 text-white text-2xl font-medium border-slate-950">
                    VALMER INFORMS
                </h2>
            </div>
            <div className={`rounded-lg ${maxHeightClass} overflow-hidden p-4`} >
                <div className="overflow-auto" style={{ maxHeight: "calc(100% - 70px)" }}>
                    <ul>
                        {titles.map((title: string) => (
                            <li key={title} className="font-medium text-cyan-700 mb-2">
                                {title}
                                <i className={`text-green-600 text-md ml-8 mr-4 fa fa-angle-right`}></i>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}