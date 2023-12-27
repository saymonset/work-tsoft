import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";
import {Revisar1Response, SortableFields, SortableFieldsRevisar1} from "../../../../../../../../model";
import React, {useState} from "react";
import {filterData, handleSort, sortData} from "../../../../../../../../utils";
import {updateSearchContributor} from "../../../../../../../../redux";

export const useRevisar = () => {
    const dataRevisar1 = useSelector((state: RootState<any, any, any>) =>
        state.revisar1Data) as unknown as Revisar1Response;

    const searchContributor = useSelector((state: RootState<any, any, any>) =>
        state.searchContributor) as unknown as string;

    const [sortField, setSortField] = useState<SortableFields | null>(null);

    const [sortDirection, setSortDirection]
        = useState<'asc' | 'desc' | null>(null);

    const dispatch = useDispatch()

    const setSearchContributor = (e: React.ChangeEvent<HTMLInputElement>) => {
        dispatch(updateSearchContributor(e.target.value))
    }

    let sortedData = sortData(dataRevisar1, sortField, sortDirection)

    if (searchContributor) {
        sortedData = filterData(sortedData, searchContributor);
    }

    const handleSortR = (field: SortableFieldsRevisar1) => {
        handleSort(field, sortField, sortDirection, setSortField, setSortDirection)
    };

    return {
        searchContributor,
        dataRevisar1,
        sortField,
        sortDirection,
        sortedData,
        handleSortR,
        setSearchContributor
    }
}