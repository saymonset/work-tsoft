import {ChangeEvent, useRef, useState} from "react";

export const useInput = () => {

    const [prices, setPrices] = useState<Record<string, string>>({});
    const inputRef = useRef<HTMLInputElement>(null);

    const handlePriceChange = (isin: string, event: ChangeEvent<HTMLInputElement>) => {
        const rawValue = event.target.value;
        const previousCursorPosition = event.target.selectionStart;

        if (rawValue === "") {
            setPrices(prevPrices => ({
                ...prevPrices,
                [isin]: '0.000000000000000'
            }));
            return;
        }

        let formattedValue = formatValue(rawValue);

        if (rawValue.charAt(previousCursorPosition! - 1) === "." && !formattedValue.includes(".")) {
            formattedValue = formattedValue.slice(0, formattedValue.length - 15) + "." + formattedValue.slice(-15);
        }

        setPrices(prevPrices => ({
            ...prevPrices,
            [isin]: formattedValue
        }));

        setTimeout(() => {
            if (inputRef.current) {
                if (rawValue.charAt(previousCursorPosition! - 1) !== ".") {
                    inputRef.current.setSelectionRange(previousCursorPosition, previousCursorPosition);
                } else {
                    inputRef.current.setSelectionRange(previousCursorPosition! + 1, previousCursorPosition! + 1);
                }
            }
        }, 0);
    };

    const formatValue = (value: string): string => {
        try {
            if (!value.includes('.')) {
                value = value.slice(0, value.length - 15) + '.' + value.slice(value.length - 15);
            }

            const parts = value.split('.');
            if (parts.length === 1) {
                return value + '.000000000000000';
            }
            const integerPart = parts[0];
            let decimalPart = parts[1];

            while (decimalPart.length < 15) {
                decimalPart += '0';
            }
            return integerPart + '.' + decimalPart.slice(0, 15);
        } catch (e) {
            return '00.000000000000000';
        }
    };

    const formatValueForDisplay = (value: string): string => {
        let [intPart, decPart = ""] = value.split(".");

        while (decPart.length < 15) {
            decPart += '0';
        }

        return intPart + '.' + decPart.slice(0, 15);
    };

    return {prices, inputRef, formatValueForDisplay, handlePriceChange}
}