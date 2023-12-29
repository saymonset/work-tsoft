import { ModalLogin } from "./ModalLogin";
import {useHandleData} from "../hooks/useHandleData";
import "./LoginPage.css"
import {ButtonContent} from "../../shared";
import React from "react";

export const LoginPage = () => {

    const {
        user,
        password,
        errors,
        errorLogin,
        isLoading,
        isOpen,
        handleLogin,
        handleChange,
        handleOpenModal,
        handleCloseModal} = useHandleData()

    return (
        <div className="logged">
            <div className="flex justify-center items-center h-screen">
                <div className="w-96 p-6 shadow-lg bg-white rounded-md">

                    <center className="mt-1">
                        <img alt="" src='/img/logoValmer__int_new_.jpg'/>
                    </center>

                    <div className="mt-7 text-cyan-700">
                        <h1 className="text-xs block text-center font-semibold">
                            INTRANET VALMER
                        </h1>
                    </div>

                    <div className="line"></div>

                    <form onSubmit={handleLogin}>
                        <div className="mt-3">
                            <input name='user'
                                   placeholder="User ID"
                                   type="text"
                                   autoComplete="on"
                                   className="border w-full text-base px-2 py-1
                                   focus:ouline-none
                                   focus:ring-0
                                   focus:border-gray-600"
                                   value={user}
                                   onChange={handleChange}/>
                        </div>
                        {errors.user && <span className="fontError animate__animated animate__fadeIn">
                                {errors.user}</span>}
                        <div className="mt-3">
                            <input name='password'
                                   placeholder="Password"
                                   type="password"
                                   autoComplete="off"
                                   className="border w-full text-base px-2 py-1
                                   focus:ouline-none
                                   focus:ring-0
                                   focus:border-gray-600"
                                   value={password}
                                   onChange={handleChange}/>
                            {errors.password && <span className="fontError animate__animated animate__fadeIn">
                                    {errors.password}</span>}
                        </div>

                        <div className="mt-5">
                            <button
                                className="border-2 border-cyan-700 bg-cyan-700 text-white py-2 w-full rounded-md"
                                type="submit"
                                name="btnSubmit"
                            >
                                <ButtonContent name="Entrar" loading={isLoading} isLogin={true}></ButtonContent>
                            </button>
                        </div>

                        {errorLogin &&
                            <div className="animate__animated animate__fadeIn">
                                <div className="line"></div>

                                <div className="grid grid-cols-1 gap-4 text-center">
                                        <span className="fontError">
                                            Usuario o contraseña incorrecto!
                                        </span>
                                    <button
                                        className="button-link"
                                        type="button"
                                        onClick={handleOpenModal}
                                    >
                                        Recuperar contraseña
                                    </button>
                                </div>
                            </div>
                        }
                    </form>
                </div>

                <ModalLogin isOpen={isOpen} onClose={handleCloseModal}/>

            </div>
        </div>
    )
}