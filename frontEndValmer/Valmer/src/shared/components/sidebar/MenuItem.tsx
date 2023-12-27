import {NavLink} from "react-router-dom";
import {useState} from "react";
import {subMenuClose} from "../../../utils";
import {v4 as uuidv4} from 'uuid';

export const MenuItem = ({item}: any) => {

    const [isOpen, setIsOpen] = useState(false)
    const handleClick = () => setIsOpen(!isOpen)
    const redirect = () => { /* TODO document why this arrow function is empty */
    }

    return (
        <NavLink to={item.path} className={`hover:bg-gradient-to-l hover:from-cyan-800 
            ${isOpen ? 'bg-gradient-to-l from-cyan-800' : null}`}>
            <button className={`block text-slate-200 p-2 w-full hover:bg-gradient-to-l hover:from-green-600 
                ${isOpen ? 'bg-gradient-to-l from-cyan-800' : null}`}
                    onClick={item.subMenu ? handleClick : redirect}
            >
                <div className="flex items-center">
                    <div className="flex items-center flex-grow">
                        <i className={item.icon}></i>
                        <span className="text-sm font-medium ml-3 lg:opacity-100">
                                {item.label}
                        </span>
                        <div className="text-xs ml-auto mr-4">
                            <i className={subMenuClose(item, isOpen)}></i>
                        </div>
                    </div>
                </div>

            </button>
            <div className="lg:block">
                <ul className="pl-9">
                    {isOpen && item.subMenu.map((item: any) => {
                        return (
                            <li className={`hover:bg-gradient-to-l hover:from-green-600 `} key={uuidv4()}>
                                <NavLink
                                    end
                                    to={item.path}
                                    className="block text-slate-200 p-2 w-full">
                                        <span className="text-sm font-medium lg:opacity-100">
                                            {item.label}
                                        </span>
                                </NavLink>
                            </li>
                        )
                    })}
                </ul>
            </div>
        </NavLink>
    )
}