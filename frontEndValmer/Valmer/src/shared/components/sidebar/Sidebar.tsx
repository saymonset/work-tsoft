import { SidebarMenu } from "./SidebarMenu";
import {SidebarData} from './SidebarData';
import {v4 as uuidv4} from "uuid";
import React from "react";

interface SidebarProps {
    isOpen: boolean;
}

export const Sidebar: React.FC<SidebarProps> = ({ isOpen }) => {

    const username = localStorage.getItem('user');

    return(
        <>
            {/* Sidebar backdrop (mobile only) */}
            <div
                className="fixed inset-0 bg-slate-900 bg-opacity-30 z-0 lg:hidden lg:z-auto transition-opacity duration-200 opacity-100 pointer-events-none"
                //aria-hidden="true"
            ></div>

            {/* Sidebar */}
            <div//ref={sidebar}
                className={`
                flex flex-col top-0 pt-4 overflow-y-scroll lg:overflow-y-auto bg-gradient-to-l from-cyan-950 to-cyan-800 fixed 
                            inset-y-0 left-0 z-0 w-64 transition-all duration-1000 
                            ${isOpen ? 'translate-x-0' : '-translate-x-64'}`}
            >
                {/* Sidebar header */}
                <div className="flex justify-between mt-16 mb-14 pr-3 sm:px-2">
                    {/* Logo */}
                    <div className="grid w-full justify-items-center">
                        <img src="/img/einstein.png" className="rounded-full w-16 text-center" alt=""/>
                        <h5 className="text-slate-300 font-bold mt-2">{username }</h5>
                    </div>
                </div>

                {/* Links */}
                <div className="space-y-8">
                    {/* Pages group */}
                    <div>
                        <ul className="my-10">
                            {/** MenuSidebarItem */
                            SidebarData.map((item, index) => {
                                return <SidebarMenu item={item} key={uuidv4()} />
                            })}
                        </ul>
                    </div>
                </div>

            </div>
        </>
    )
}