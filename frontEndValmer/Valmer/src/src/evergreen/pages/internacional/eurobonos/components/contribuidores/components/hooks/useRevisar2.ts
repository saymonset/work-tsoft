import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {
    Revisar2Response,
    SortableFieldsRevisar2
} from "../../../../../../../../model";
import React, {useState} from "react";
import {filterData, handleSort, sortData} from "../../../../../../../../utils";
import {updateSearchContributor2} from "../../../../../../../../redux";

export const useRevisar2 = () => {

    const dataRevisar2 = useSelector((state: RootState<any, any, any>) =>
        state.revisar2Data) as unknown as Revisar2Response;

    const searchContributor2 = useSelector((state: RootState<any, any, any>) =>
        state.searchContributor2) as unknown as string;

    const [sortField, setSortField]
        = useState<SortableFieldsRevisar2 | null>(null);

    const [sortDirection, setSortDirection]
        = useState<'asc' | 'desc' | null>(null);

    const dispatch = useDispatch()

    const setSearchContributor2 = (e: React.ChangeEvent<HTMLInputElement>) => {
        dispatch(updateSearchContributor2(e.target.value))
    }

    let sortedData = sortData(dataRevisar2, sortField, sortDirection);

    if (searchContributor2) {
        sortedData = filterData(sortedData, searchContributor2);
    }

    const handleSortR2 = (field: SortableFieldsRevisar2) => {
        handleSort(field, sortField, sortDirection, setSortField, setSortDirection)
    };

    return {
        searchContributor2,
        sortField,
        sortDirection,
        sortedData,
        handleSortR2,
        setSearchContributor2
    }
}


