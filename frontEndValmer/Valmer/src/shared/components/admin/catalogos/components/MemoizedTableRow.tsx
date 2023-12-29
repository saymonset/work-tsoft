import React from "react";
import { MemoizedTableProps, RegistroEdit} from "../../../../../model";

export const MemoizedTableRow = React.memo(({
                                                registro,
                                                columns,
                                                handleRowClick,
                                                edit
                                            } : MemoizedTableProps) => {
    return (
        <tr>
            {edit && (
                <td key="editar"
                    className="border px-4 py-2 flex items-center justify-center cursor-pointer"
                    onClick={() => handleRowClick(registro)}>
                    <i className="fa-solid fa-pen-to-square text-blue-700"></i>
                </td>
            )}
            {columns.map((column, index) => {
                const isClickable = !edit && index === 0;
                return (
                    <td key={column.name} className={`border px-4 py-2 ${isClickable ? 'cursor-pointer text-blue-500' : ''}`}
                        onClick={isClickable ? () => handleRowClick(registro) : undefined}>
                        <span className={`${isClickable ? 'text-center' : ''}`}>
                            {(registro as RegistroEdit)[column.name.toLowerCase()]}
                        </span>
                    </td>
                );
            })}
        </tr>
    );
});
