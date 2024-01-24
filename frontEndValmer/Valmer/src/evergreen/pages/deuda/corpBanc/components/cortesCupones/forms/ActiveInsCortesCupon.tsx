import React, {useState} from "react";
import {ResponseCorteCupon} from "../../../../../../../model";
import {generateUUID} from "../../../../../../../utils";
import {CortesFlujosTable} from "./CortesFlujosTable";
const AccordionHeader = ({ isActive, handleButtonClick, label } : any) => (
    <div className={`acordeon-header ${isActive ? "acordeon-header-active" : "acordeon-header-inactive"}`}>
        <button className="acordeon-button" onClick={handleButtonClick}>
            <i className={`acordeon-button-icon ${isActive ? "fa fa-caret-down" : "fa fa-caret-right"}`}></i>
            <span>{label}</span>
        </button>
    </div>
);

export const ActiveInsCortesCupon = ({fijos, flotantes}: {fijos: ResponseCorteCupon, flotantes: ResponseCorteCupon}) => {

    const accordionItems = [
        { name: 'fijos', label: 'Fijos', data: fijos},
        { name: 'flotantes', label: 'Flotantes', data: flotantes},
    ];

    return (
        <>
            {accordionItems.map(({ name, label, data }) =>
            {
                const isActive = true;

                return (
                    <div key={generateUUID()}>
                        <AccordionHeader
                            isActive={isActive}
                            iconName={isActive ? "fa fa-caret-down" : "fa fa-caret-right"}
                            label={label}
                        />
                            <CortesFlujosTable data={data}/>
                    </div>
                );
            })}
        </>
    );
}