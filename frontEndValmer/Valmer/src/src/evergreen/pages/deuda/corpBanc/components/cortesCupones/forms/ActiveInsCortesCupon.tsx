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

    const [activeForm, setActiveForm] = useState("fijos");

    const accordionItems = [
        { name: 'fijos', label: 'Fijos', data: fijos},
        { name: 'flotantes', label: 'Flotantes', data: flotantes},
    ];

    const handleButtonClickForm = (buttonId: string) => {
        setActiveForm(buttonId === activeForm ? "" : buttonId);
    };

    return (
        <>
            {accordionItems.map(({ name, label, data }) =>
            {
                const isActive = activeForm === name;

                return (
                    <div key={generateUUID()}>
                        <AccordionHeader
                            isActive={isActive}
                            handleButtonClick={() => handleButtonClickForm(name)}
                            iconName={isActive ? "fa fa-caret-down" : "fa fa-caret-right"}
                            label={label}
                        />

                        {isActive && (
                            <div
                                className={`acordeon-body ${isActive ? "animate__animated animate__fadeIn" : ""}`}
                            >
                                <CortesFlujosTable data={data}/>
                            </div>
                        )}
                    </div>
                );
            })}
        </>
    );
}