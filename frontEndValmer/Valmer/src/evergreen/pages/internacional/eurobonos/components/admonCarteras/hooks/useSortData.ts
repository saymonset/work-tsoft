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

    let sortedData = [...(respClientWallet?.body || [])];

    if (sortField && sortDirection) {
        sortedData.sort((a, b) => {
            const aValue = a[sortField];
            const bValue = b[sortField];
            const directionModifier = sortDirection === 'asc' ? 1 : -1;
    
            const numA = parseFloat(aValue);
            const numB = parseFloat(bValue);
    
            const bothNumbers = !isNaN(numA) && !isNaN(numB);
    
            if (bothNumbers) {
                return (numA - numB) * directionModifier;
            } else {
                if (aValue < bValue) return -1 * directionModifier;
                if (aValue > bValue) return 1 * directionModifier;
                return 0;
            }
        });
    }
    
    return {
        sortField,
        sortDirection,
        sortedData,
        setSortDirection,
        setSortField
    }

}