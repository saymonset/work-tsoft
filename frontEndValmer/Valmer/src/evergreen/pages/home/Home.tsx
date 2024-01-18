import React from "react";
import './css/Home.css'
import { SimplePieChart } from "./components/SimplePieChart";
import { Table } from './components/Table';
import {TitleDate} from "../../../shared";
import {MoonLoader} from "react-spinners";
import {useHome} from "./hooks";
import {SimpleBarChart} from "./components/SimpleBarChart";
import { Link } from "react-router-dom";

export const Home = () => {

    const {
        loadingHorarios,
        loadingBarras,
        horarios,
        horariosBarras,
        cau,
        loadingCau,
        userName,
        handleDownloadObjetivosCalidad,
        handleDownloadCodigoConducta,
        handleDownloadGuiaAtencionCau,
        previousDate} = useHome()

    return (
        <div>
            <div className="grid grid-cols-1">
                <TitleDate title={`Bienvenido ${userName}`}/>
            </div>
            <div className="animate__animated animate__fadeIn">
                <div className="grid grid-cols-2 gap-4">
                    <div className="card">
                        <div className="head">
                            <span>Vector</span>
                            <span>Horarios Vector</span>
                        </div>

                        {loadingBarras ? (
                            <div className="body flex items-center justify-center">
                                <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80}/>
                            </div>
                        ) : (
                            <div className="body">
                                <SimpleBarChart dataBody={horariosBarras}/>
                            </div>
                        )}
                    </div>
                    <div className="card">
                        <div className="head">
                            <span>CAU</span>
                        </div>

                        {loadingCau ? (
                            <div className="body flex items-center justify-center">
                                <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80}/>
                            </div>
                        ) : (
                            <div className="body">
                                <SimplePieChart dataBody={cau}/>
                            </div>
                        )}
                    </div>
                </div>

                <div className="grid grid-cols">
                    <div className="card">
                        <div className="head">
                            <span>HORARIOS VECTOR</span>
                            <span>FECHA : {previousDate()}</span>
                        </div>

                        {loadingHorarios ? (
                            <div className="body flex items-center justify-center">
                                <MoonLoader color="#0e7490" loading={true} speedMultiplier={0.5} size={80}/>
                            </div>
                        ) : (
                            <div className="body overflow-y-auto max-h-[300px]">
                                <Table dataBody={horarios}/>
                            </div>
                        )}
                    </div>
                </div>
                <div className="grid grid-cols-1 text-center mb-20">
                    <div className="space-x-4 mt-8">
                        <Link to="/cau/mantenimiento"
                              className="bg-green-700 hover:bg-green-900
                                text-white py-2 rounded-md px-2">
                            Mantenimiento Cau
                        </Link>
                        <a className="bg-green-700 hover:bg-green-900
                                text-white py-2 rounded-md px-2 hover:cursor-pointer"
                            onClick={handleDownloadGuiaAtencionCau}    
                        >
                            Guía para atención del CAU
                        </a>
                        <a className="bg-green-700 hover:bg-green-900
                                text-white py-2 rounded-md px-2 hover:cursor-pointer"
                            onClick={handleDownloadCodigoConducta}
                        >
                            Código de conducta Valmer
                        </a>
                        <a className="bg-green-700 hover:bg-green-900
                                text-white py-2 rounded-md px-2 hover:cursor-pointer"
                           onClick={handleDownloadObjetivosCalidad}
                        >
                            Objetivos de Calidad
                        </a>
                    </div>
                </div>
            </div>
        </div>
    )
}