import { useState } from "react";
import { FvCheckbox, FvContratos, ItemProduct } from "../../../../../model"

interface MenuProps {
    data: ItemProduct
    permisos: FvContratos
    setPermisos: React.Dispatch<React.SetStateAction<FvContratos>>
}

export const MenuProducts: React.FC<MenuProps> = ({ data, permisos, setPermisos }) => {

    const isObject = (item: ItemProduct[keyof ItemProduct]): item is ItemProduct => typeof item === 'object';
    const [checkbox, setCheckbox] = useState<FvCheckbox>({} as FvCheckbox)

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value, checked} = e.target
        const checkedValue = {...checkbox, [name]: !!checked}
        const permisosValue = {...permisos, [name]: checked ? value : ''}
        setCheckbox(checkedValue)
        setPermisos(permisosValue)
    }

    const isCheck = (value: string, key: string) => {
        if (value.includes("chkb_")) {
            const valuepart = value.split("_")
            const textCheck = valuepart[0]
            const label = valuepart[1]
            return (
                <li key={key} className="menu">
                    <input
                        type="checkbox"
                        id={textCheck + "_" + key}
                        name={textCheck + "_" + key}
                        value={key}
                        onChange={handleChange}
                    />
                    <label htmlFor={textCheck + "_" + key} className="ml-1">
                        {label}
                    </label>
                </li>
            )
        } else {
            return (
                <li key={key} className="menu">
                    {value}
                </li>
            )
        }
    }

    return (
        <ul className={`w-1/2 text-xs ${!data ? "border border-cyan-600" : ""}`}>
            {Object.entries(data).map(([key, value]) => (
                <li className="relative" key={key}>
                    <ul className="menu">
                        <span className="w-full">{key}</span>
                        <span className="fa fa-caret-right float-right mt-0.5"></span>
                    </ul>
                    <ul className="hidden absolute left-full top-0 mt-0 shadow-lg ml-0.5 z-10 w-56 border border-cyan-600">
                        {isObject(value) ? (
                            Object.entries(value).map(([key, value]) => (
                                isObject(value) ? (
                                    <>
                                        <MenuProducts 
                                            data={value}
                                            permisos={permisos}
                                            setPermisos={setPermisos}
                                        />
                                        <span className="fa fa-caret-right float-right mt-0.5" />
                                    </>
                                ):(
                                    isCheck(value, key)
                                )
                            ))
                        ):(
                            <ul>{value}</ul>
                        )}
                    </ul>
                </li>
            ))}
        </ul>
    );
}