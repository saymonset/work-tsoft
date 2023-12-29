import React from "react";
import {ColumnEditCat} from "../../../../../model";

export const MemoizedColumn = React.memo(({ column }: { column: ColumnEditCat }) => {
    const columnName = 'columnName' in column ? column.columnName : column.name;

    return (
        <th className="bg-cyan-700 text-white px-4 py-2">
            {columnName}
        </th>
    );
});
