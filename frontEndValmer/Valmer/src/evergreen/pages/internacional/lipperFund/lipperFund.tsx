import {ButtonContent, Modal, TitleDate} from "../../../../shared";
import React, {useState} from "react";
import {LipperFundForm, LipperFundFormHead} from "./components";
import {HocRestricted} from "../../restrictedAccess";
import { useDataHandle, useDataTable } from "./hooks";
import { MoonLoader } from "react-spinners";

export const LipperFund = () => {
    const title : string = "Lipper Fund";

    const {
        isOpen,
        txtAreaLipper,
        loadingSubmit,
        isTxtArea,
        selectedSalidas,
        selectedReuters,
        loadingGeneraArchivo,
        handleCloseModal,
        handleOpenModal,
        setTxtAreaLipper,
        handleActualizar,
        handleChangeCheckbox,
        handleGeneraArchivos
    } = useDataHandle()

    const {
        loading,
        loadingTableHead,
        consultaLipper,
    } = useDataTable()

    const [isDivVisible, setIsDivVisible] = useState(false);

    const toggleDivVisibility = () => {
        setIsDivVisible(!isDivVisible);
    };

    if(loading || loadingTableHead || !consultaLipper.body) {
        return (
            <div className="mt-12 flex justify-center items-center h-full">
                {loading || loadingTableHead ? (
                    <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80}/>
                ) : (
                    <div>No hay información</div>
                )}
            </div>
        );
    }

    return (
        <HocRestricted title={title} view={title}>
            <TitleDate title={title}/>

            <div className="flex w-full justify-end animate__animated animate__fadeIn">
                <div className="w-full">
                    <LipperFundFormHead/>
                </div>

                <div className={`w-full text-right text-cyan-700 font-bold transition-transform duration-900 
                                ${isDivVisible ? 'ease-in-out translate-x-0' : 'ease-in-out translate-x-100 '}`}>
                    <div>

                        <button 
                            className="btn"
                            onClick={handleOpenModal}
                        >
                            <span>Carga Info</span>
                        </button>

                        <button
                            className="btn"
                            onClick={toggleDivVisibility}
                        >
                            <span
                                className={`text-white ${isDivVisible ? 'fa-solid fa-lock-open' : 'fa-solid fa-lock'}`}>
                            </span>
                        </button>
                    </div>
                </div>

                <div className={`basis-4/6 text-right animate__animated 
                                ${isDivVisible ? 'animate__fadeInRight' : 'animate__fadeOutRight'}`}>

                    <button 
                        className="bg-cyan-700 hover:bg-green-700 text-white py-1 rounded-md px-3 mx-1"
                        onClick={handleGeneraArchivos}
                    >
                        <ButtonContent name="Genera archivos Lipper" loading={loadingGeneraArchivo}></ButtonContent>
                    </button>

                    <input
                        type="checkbox"
                        className="ml-2 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded focus:ring-cyan-700
                        dark:focus:ring-cyan-700 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700
                        dark:border-gray-600"
                        name="chk_solo_salidas"
                        checked={selectedSalidas}
                        onChange={handleChangeCheckbox}
                    />
                    <label htmlFor="chk_solo_salidas" className="ml-2 text-sm font-medium text-cyan-700">
                        Solo Salidas
                    </label>

                    <input
                        type="checkbox"
                        className="ml-2 w-4 h-4 text-cyan-700 bg-gray-100 border-gray-300 rounded focus:ring-cyan-700
                        dark:focus:ring-cyan-700 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700
                        dark:border-gray-600"
                        name="chk_consulta_ds"
                        checked={selectedReuters}
                        onChange={handleChangeCheckbox}
                    />
                    <label htmlFor="chk_consulta_ds" className="ml-2 text-sm font-medium text-cyan-700">
                        Consulta Reuters
                    </label>
                </div>
            </div>

            <LipperFundForm/>

            <Modal isOpen={isOpen} onClose={handleCloseModal} title="Carga Lipper" modalSize="small">
                <div className="text-center">
                    <div className="form-title text-center">Actualiza Info</div>
                    <textarea 
                        name="txt_area_act_lip"
                        className="border-2 border-gray-700 focus:border-cyan-700 focus:outline-none w-full mt-2" 
                        rows={5}
                        value={txtAreaLipper}
                        onChange={e => setTxtAreaLipper(e.target.value)}
                    />
                    {isTxtArea && (<span className="fontError animate__animated animate__fadeIn">
                                        Ingrese un valor</span>)}
                    <div className="line"></div>
                    <button 
                        type="button" 
                        className="btn"
                        onClick={handleActualizar}
                    >
                        <ButtonContent name="Actualizar" loading={loadingSubmit}></ButtonContent>
                    </button>
                </div>
            </Modal>
        </HocRestricted>
    )
}