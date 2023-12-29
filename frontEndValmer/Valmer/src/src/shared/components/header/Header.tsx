import {useNavigate} from 'react-router-dom';
import {DropdownMenu} from "./DropdownMenu";
import React from "react";

interface HeaderProps {
    onClick: () => void;
}

export const Header: React.FC<HeaderProps> = ({ onClick }) => {

    const navigate = useNavigate();

    const onHome = () => {
        navigate('/home', {
            replace: true
        });
    }

    return (
        <header className={`sticky top-0 bg-white border-b border-slate-200 z-10 text-sm`}>
            <div className="px-4 sm:px-6 lg:px-8">
                <div className="flex items-center justify-between h-18 -mb-px">
                    <div className="flex items-center">
                        <button
                            className="text-cyan-700 hover:text-green-600 px-4"
                            aria-controls="sidebar"
                            onClick={onClick}
                        >
                            <span className="fa-solid fa-bars fa-xl"></span>
                        </button>

                        <button className="px-4">
                            <img className="w-130 h-12 py-1" src="/img/logoValmer__int_new_.jpg" alt=""/>
                        </button>

                        <button className="text-cyan-700 hover:text-green-600 font-bold"
                                onClick={onHome}>
                            Home
                        </button>
                    </div>

                    {/* Header: Right side */}
                    <div className="flex items-center">
                        <a href="https://www.valmer.com.mx/" target="_blank"
                           className="text-cyan-700 hover:text-green-600 text-xs font-bold py-2">WEB VALMER
                        </a>
                        <hr className="w-px h-6 bg-slate-200 mx-3"/>
                        <a href="http://www.mexder.com.mx/wb3/wb/MEX" target="_blank"
                           className="text-cyan-700 hover:text-green-600 text-sm font-bold py-2">MEXDER
                        </a>
                        <hr className="w-px h-6 bg-slate-200 mx-3"/>
                        <a href="http://www.asigna.com.mx" target="_blank"
                           className="text-cyan-700 hover:text-green-600 text-sm font-bold py-2">ASIGNA
                        </a>
                        <hr className="w-px h-6 bg-slate-200 mx-3"/>
                        <a href="https://www.indeval.com.mx" target="_blank"
                           className="text-cyan-700 hover:text-green-600 text-sm font-bold py-2">INDEVAL
                        </a>
                        <hr className="w-px h-6 bg-slate-200 mx-3"/>
                        <a href="https://www.contraparte-central.com.mx" target="_blank"
                           className="text-cyan-700 hover:text-green-600 text-sm font-bold py-2">CVV
                        </a>
                        <hr className="w-px h-6 bg-slate-200 mx-3"/>
                        <a href="https://www.sif.com.mx" target="_blank"
                           className="text-cyan-700 hover:text-green-600 text-sm font-bold py-2">SIF
                        </a>
                        <hr className="w-px h-6 bg-slate-200 mx-3"/>
                        <a href="https://www.bmv.com.mx" target="_blank"
                           className="text-cyan-700 hover:text-green-600 text-sm font-bold py-2">BOLSA MEXICANA
                        </a>
                        <hr className="w-px h-6 bg-slate-200 mx-3"/>
                        <DropdownMenu/>
                    </div>
                </div>
            </div>
        </header>
    )
}