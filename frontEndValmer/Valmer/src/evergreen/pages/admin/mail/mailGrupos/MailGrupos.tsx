import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';
import { BarLoader } from 'react-spinners';
import { TitleDate } from "../../../../../shared";
import { MailGruposTable } from './components/MailGruposTable';
import { MailGruposForm } from './components/MailGruposForm';
import { usePostGroup } from './hooks/usePostGroup';
import { useGetNewGroupId } from './hooks/useGetNewGroupId';
import { usePostDeleteGroup } from './hooks/usePostDeleteGroup';

export const MailGrupos = () => {
    const [selectedGroup, setSelectedGroup] = useState({
        n_grupo: "",
        s_descripcion: ""
      });

    const { loadingCarga, fetchDataPostGroup, changeCatalogPost } = usePostGroup();
    const { loadingCargaDelete, fetchDataPostDeleteGroup, changeCatalogDelete } = usePostDeleteGroup();
    const [modifiedDescription, setModifiedDescription] = useState(false);
    const { getNewGroupId } = useGetNewGroupId();
    
    const handleSelectedGroup = (group: any) => {
        setSelectedGroup(group);
    }
    
    const handleGuardarClick = (e:  React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        if (selectedGroup.n_grupo && modifiedDescription) {
            fetchDataPostGroup(selectedGroup).then();
        } 
    }

    const handleDeleteClick = (e:  React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        if (selectedGroup.n_grupo && modifiedDescription) {
            fetchDataPostDeleteGroup(selectedGroup).then();
        } 
    }

    const handleClickNew = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        try {
          const newId = await getNewGroupId();
          setSelectedGroup({
            n_grupo: newId,
            s_descripcion: "",
          });
        } catch (error) {
          console.error("Error al obtener el nuevo ID:", error);
        }
      }

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
                        <button className="btn" onClick={handleClickNew}>Nuevo</button>
                        <button className="btn" onClick={handleGuardarClick}>Guardar</button>
                        <button className="btn" onClick={handleDeleteClick}>Borrar</button>
                    </div>
                </div>
                {(loadingCarga || loadingCargaDelete) && (
                  <BarLoader
                    className="ml-2 w-full mt-2 mb-2"
                    color="#059669"
                    width={500}
                  />
                )}
                <div className="form-cols-2">
                    <MailGruposTable
                        onSelectedGroup={handleSelectedGroup}
                        changeCatalogPost={changeCatalogPost}
                        changeCatalogDelete={changeCatalogDelete}
                    />
                    <MailGruposForm
                        selectedGroup={selectedGroup}
                        setSelectedGroup={setSelectedGroup}
                        setModifiedDescription={setModifiedDescription}
                    />
                </div>
            </form>
        </>
    )
}