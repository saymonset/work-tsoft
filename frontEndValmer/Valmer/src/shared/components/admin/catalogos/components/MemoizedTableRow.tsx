import React from "react";
import { MemoizedTableProps, RegistroEdit} from "../../../../../model";

export const MemoizedTableRow = React.memo(({
                                                registro,
                                                columns,
                                                handleRowClick,
                                                edit } : MemoizedTableProps) => {
    return (
        <tr>
            {edit && (
                <td key="editar"
                    className="border px-4 py-2 flex items-center justify-center">
                    <i className="fa-solid fa-pen-to-square"></i>
                </td>
            )}
            {columns.map((column, index) => (
                <td key={column.name} className="border px-4 py-2"
                    style={index === 0 ? {width: '100px'} : {}}
                    onClick={index === 0 ? () => handleRowClick(registro) : undefined}>
                <span className={`${index === 0 ? 'cursor-pointer text-blue-500 text-center' : ''}`}>
                    {(registro as RegistroEdit)[column.name.toLowerCase()]}
                </span>
                </td>
            ))}
        </tr>
    );
});