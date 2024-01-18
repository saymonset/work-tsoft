import React from 'react';
import { NavLink } from 'react-router-dom';
import {ButtonContent, TitleDate} from "../../../../../shared";
import { MailGruposTable } from './components/MailGruposTable';
import { MailGruposForm } from './components/MailGruposForm';
import {useMailGrupos} from "./hooks";
export const MailGrupos = () => {

    const {
        selectedGroup,
        changeCatalogDelete,
        changeCatalogPost,
        loadingNew,
        loadingCarga,
        loadingCargaDelete,
        setSelectedGroup,
        handleSelectedGroup,
        handleClickNew,
        handleGuardarClick,
        handleDeleteClick
    } = useMailGrupos()

    return (
        <>
            <TitleDate title='Envíos Mail Grupos'/>

            <form className='animate__animated animate__fadeIn'>
                <div className="w-full flex justify-between">
                    <div className='mb-3'>
                        <NavLink to="/admin/mail/clientes">
                            <button className="btn btn-icon">
                                <i className="fa fa-arrow-left"></i>
                                <span>Regresar</span>
                            </button>
                        </NavLink>
                    </div>
                    <div className='mb-3'>
                        <button className="btn" onClick={handleClickNew}>
                            <ButtonContent name="Nuevo" loading={loadingNew}/>
                        </button>
                        <button className="btn" onClick={handleGuardarClick}>
                            <ButtonContent name="Grabar" loading={loadingCarga}/>
                        </button>
                        <button className="btn" onClick={handleDeleteClick}>
                            <ButtonContent name="Borrar" loading={loadingCargaDelete}/>
                        </button>
                    </div>
                </div>
                <div className="form-cols-2">
                    <MailGruposTable
                        onSelectedGroup={handleSelectedGroup}
                        changeCatalogPost={changeCatalogPost}
                        changeCatalogDelete={changeCatalogDelete}
                    />
                    <MailGruposForm
                        selectedGroup={selectedGroup}
                        setSelectedGroup={setSelectedGroup}
                    />
                </div>
            </form>
        </>
    )
}