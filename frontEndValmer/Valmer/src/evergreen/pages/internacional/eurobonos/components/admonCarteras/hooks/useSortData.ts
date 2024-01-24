import {useState} from "react";
import {DataClient, RespClientWallet} from "../../../../../../../model";
import {useSelector} from "react-redux";
import {RootState} from "@reduxjs/toolkit/dist/query/core/apiState";

export const useSortData = () => {

    const respClientWallet = useSelector((state: RootState<any, any, any>) =>
        state.respClientWallet) as unknown as RespClientWallet;

    const [sortField, setSortField] =
        useState<keyof DataClient | null>(null);

    const [sortDirection, setSortDirection] =
        useState<'asc' | 'desc' | null>(null);

    const compareFunction = (a: DataClient, b: DataClient) => {
        if (!sortField || !sortDirection) return 0;

        const aValue = a[sortField];
        const bValue = b[sortField];
        const directionModifier = sortDirection === 'asc' ? 1 : -1;

        const numA = parseFloat(aValue);
        const numB = parseFloat(bValue);

        if (!isNaN(numA) && !isNaN(numB)) {
            return (numA - numB) * directionModifier;
        } else {
            let result = 0;
            if (aValue < bValue) {
                result = -1;
            } else if (aValue > bValue) {
                result = 1;
            }
            return result * directionModifier;
        }
    };

    let sortedData = [...(respClientWallet?.body || [])].sort(compareFunction);

    return {
        sortField,
        sortDirection,
        sortedData,
        setSortDirection,
        setSortField
    }
}