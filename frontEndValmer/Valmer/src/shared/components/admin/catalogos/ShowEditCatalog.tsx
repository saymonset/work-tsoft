import React, {useState} from "react";
import {TitleDate} from "../../header";
import {EditCatalog} from "./EditCatalog";
import {ColumnEditCat, EditCatalogProps, StateShowEdit} from "../../../../model";

export const ShowEditCatalog = ({titleName, DataCatalog}: EditCatalogProps) => {

    const [state, setState] = useState<StateShowEdit>({
        showTable: true,
        nameCatalog: "",
        columns: [],
        edit: undefined,
    });

    const {
        showTable,
        nameCatalog,
        columns,
        edit } = state;

    let column1: Array<{text: string, columns: ColumnEditCat[], edit?: boolean}> = DataCatalog;
    let column2: Array<{text: string, columns: ColumnEditCat[], edit?: boolean}> = [];

    if (DataCatalog.length >= 10) {
        const midPoint = Math.ceil(DataCatalog.length / 2);
        column1 = DataCatalog.slice(0, midPoint);
        column2 = DataCatalog.slice(midPoint);
    }

    const handleClick = (text: string, columns: ColumnEditCat[], edit?: boolean) => {
        setState(prevState => ({
            ...prevState,
            nameCatalog: text,
            columns,
            edit: edit ?? prevState.edit,
            showTable: false,
        }));
    };

    return (
        <>
            <TitleDate title={titleName}/>

            {showTable ? (
                <table className="text-center w-full">
                    <tbody>
                    {column1.map((item, index) => (
                        <tr key={item.text}>
                            <td className="px-4 py-2 text-center text-blue-500 hover:text-blue-800">
                                <button onClick={() => handleClick(item.text, item.columns, item.edit)}>
                                    {item.text}
                                </button>
                            </td>
                            {column2[index] && (
                                <td
                                    className="px-4 py-2 text-center text-blue-500 hover:text-blue-800"
                                    onClick={() => handleClick(
                                        column2[index].text,
                                        column2[index].columns,
                                        column2[index].edit)}
                                >
                                    <button>{column2[index].text}</button>
                                </td>
                            )}
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <EditCatalog nameCatalog={nameCatalog}
                             setShowTable={() => setState(prevState => ({...prevState, showTable: true}))}
                             columns={columns}
                             edit={edit}/>
            )}
        </>
    );
}