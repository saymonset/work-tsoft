import React, {useContext} from "react";
import {AuthContext} from "../../../auth";
import {useNavigate} from "react-router-dom";
import {useDropMenu} from "./hooks";

export const DropdownMenu: React.FC = () => {

    const {buttonRef, showOptions, handleButtonClick} = useDropMenu();
    const navigate = useNavigate();
    const {logout} = useContext(AuthContext);


    const onLogout = () => {
        logout()
        navigate('/login', {
            replace: true
        });
    }

    const handleProfile = () => {
        // Código para cerrar sesión
    };

    return (
        <div className="relative inline-block text-left">
            <div className="relative inline-flex">
                <button
                    ref={buttonRef}
                    type="button"
                    onClick={handleButtonClick}
                    className="inline-flex justify-center items-center group"
                    aria-haspopup="true"
                >
                    <div className="flex items-center truncate">
                        <span className="text-cyan-800 hover:text-green-600 fa fa-gear"></span>
                        <svg className="w-3 h-3 shrink-0 ml-1 fill-current text-slate-400" viewBox="0 0 12 12">
                            <path d="M5.9 11.4L.5 6l1.4-1.4 4 4 4-4L11.3 6z" />
                        </svg>
                    </div>
                </button>
            </div>

            {showOptions && (
                <div className="origin-top-right absolute right-0 mt-2 w-40 rounded-md shadow-lg bg-white ring-1
                ring-black ring-opacity-5 focus:outline-none"
                     role="menu"
                     aria-orientation="vertical"
                     aria-labelledby="options-menu">
                    <div className="py-1" role="menu">
                        <button
                            type="button"
                            onClick={handleProfile}
                            className="w-full block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100
                            hover:text-gray-900"
                            role="menuitem">Ver Perfil</button>
                    </div>
                    <div className="py-1" role="menu">
                        <button
                            type="button"
                            onClick={onLogout}
                            className="w-full block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100
                            hover:text-gray-900"
                            role="menuitem">Cerrar sesión</button>
                    </div>
                </div>
            )}
        </div>
    );
};