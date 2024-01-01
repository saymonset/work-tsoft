import {useState} from "react";
import {MenuItem} from "./MenuItem";
import {subMenuClose, generateUUID} from "../../../utils";


export const SidebarMenu = ({item, closeOtherMenus, closeSubMenus}: any) => {
// closeSubMenus={closeSubMenus}
    //const [isOpen, setIsOpen] = useState(false)

    const handleClick = (item: any) =>  {
        closeOtherMenus(item);
     //   setIsOpen(!isOpen);
      // console.log('Firstclick ='+ isOpen);
      
    }

    
  

    return (
        <>
            <li className={`hover:bg-gradient-to-l hover:from-cyan-900 ${item.isOpen ? 'bg-gradient-to-l from-cyan-900' : null}`}>
                <button className={`block text-slate-200 p-2 w-full hover:border-l-4 hover:border-l-green-600 hover:text-slate-50 
                        ${item.isOpen ? 'border-l-4 border-l-green-600' : null}`}
                        onClick={ () => handleClick(item)}>

                    <div className="flex items-center">
                        <div className="flex items-center ml-6 flex-grow">
                            <i className={`text-2xl ${item.icon}`}></i>
                            <span className="text-md font-medium ml-3">
                                {item.label +" XXXXX"}
                            </span>
                            <div className="text-sm ml-auto mr-4">
                                <i className={subMenuClose(item, item.isOpen)}></i>
                            </div>
                        </div>
                    </div>

                </button>
                <div className="lg:block">
                    <ul className="pl-9 mt-1 bg-gradient-to-l from-cyan-700">
                        {item.isOpen && item.subMenu.map((item: any) => {
                            return (
                                <MenuItem item={item} closeSubMenus={closeSubMenus}  key={generateUUID()}/>
                            )
                        })}
                    </ul>
                </div>
            </li>
        </>
    );


}