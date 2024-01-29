import {useState} from "react";
import {MenuItem} from "./MenuItem";
import {subMenuClose, generateUUID} from "../../../utils";


export const SidebarMenu = ({item, setOpenMenu, isOpen}: any) => {

    const [openSubMenu, setOpenSubMenu] = useState<string|null>(null);
    const handleClick = () => {
        setOpenMenu((prevOpenMenu:any) => (prevOpenMenu === item.label ? null: item.label));
    }
    return (
        <li className={`hover:bg-gradient-to-l hover:from-cyan-900 ${isOpen ? 'bg-gradient-to-l from-cyan-900' : ''}`}>
            <button className={`block text-slate-200 p-2 w-full hover:border-l-4 hover:border-l-green-600 hover:text-slate-50 
                        ${isOpen ? 'border-l-4 border-l-green-600' : ''}`}
                    onClick={handleClick}>

                <div className="flex items-center">
                    <div className="flex items-center ml-6 flex-grow">
                        <i className={`text-2xl ${item.icon}`}></i>
                        <span className="text-md font-medium ml-3">
                                {item.label}
                            </span>
                        <div className="text-sm ml-auto mr-4">
                            <i className={subMenuClose(item, isOpen)}></i>
                        </div>
                    </div>
                </div>

            </button>
            <div className="lg:block">
                <ul className="pl-9 mt-1 bg-gradient-to-l from-cyan-700">
                    {isOpen && item.subMenu.map((item: any) => {
                        return (
                            <MenuItem item={item} key={generateUUID()} setOpenSubMenu={setOpenSubMenu}
                                      isOpen={openSubMenu === item.label}/>
                        )
                    })}
                </ul>
            </div>
        </li>
    );


}