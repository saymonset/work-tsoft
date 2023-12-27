import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { useGetCatalogoEnviosMailGrupos } from "../hooks/useGetCatalogoEnviosMailGrupos";
import { useNuevoEnvioMailClientes } from "../hooks/useNuevoEnvioMailClientes";
import { useBorrarEnvioMailClientes } from "../hooks/useBorrarEnvioMailClientes";

interface GroupsFormProps {
    formData: {
        idCliente: string;
        empresa: string;
        nombreCliente: string;
        mailCliente: string;
        grupo: string;
    };
    setFormData: (data: any) => void;
}

export const GroupsForm: React.FC<GroupsFormProps> = ({
    formData,
    setFormData
    }) => 
{
    const [selectedGrupo, setSelectedGrupo] = useState("");
    const { catalogoEnviosMailGrupos } = useGetCatalogoEnviosMailGrupos();
    const { fetchDataInfoMail } = useNuevoEnvioMailClientes();
    const { fetchDeleteDataInfoMail } = useBorrarEnvioMailClientes();
    const [selectedNGrupo, setSelectedNGrupo] = useState<number | null>(null);
    
    const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const selectedValue = e.target.value;
        const selectedOption = catalogoEnviosMailGrupos.body.registros.find(
            (grupo) => grupo.s_descripcion === selectedValue
        );

        if (selectedOption) {
            setSelectedGrupo(selectedValue);
            setSelectedNGrupo(selectedOption.n_grupo);
        } else {
            setSelectedGrupo("");
            setSelectedNGrupo(null);
        }
    };

    const handleClickAgregar = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        if (selectedGrupo) {
            const grupos = formData.grupo.split(", ");
            if (!grupos.includes(selectedGrupo)) {
                const updatedGrupo = formData.grupo ? `${formData.grupo}, ${selectedGrupo}` : selectedGrupo;
                setFormData({
                    ...formData,
                    grupo: updatedGrupo,
                });
                fetchDataInfoMail(formData.idCliente, selectedNGrupo).then();
            }
        }
    };        

    const handleClickQuitar = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        if (selectedGrupo) {
            const grupos = formData.grupo.split(", ");
            const updatedGrupos = grupos.filter((grupo) => grupo !== selectedGrupo);
            const updatedGrupo = updatedGrupos.join(", ");
            setFormData({
                ...formData,
                grupo: updatedGrupo,
            });
        }
        if (selectedNGrupo !== null) {
            fetchDeleteDataInfoMail(formData.idCliente, selectedNGrupo).then();
        }
    };

    return (
        <div className="form-cols-1 content-start">
            <span className="form-title">SELECCIONAR GRUPO</span>
            <div className="form-cols-1 -my-3">
            <div className="form-select">
                <select
                    name="grupo"
                    id="grupo"
                    value={selectedGrupo}
                    onChange={handleSelectChange}
                >
                    <option value="">...</option>
                    {catalogoEnviosMailGrupos.body?.registros
                        ? [...catalogoEnviosMailGrupos.body.registros]
                            .sort((a, b) => a.s_descripcion.localeCompare(b.s_descripcion))
                            .map((grupo) => (
                            <option key={grupo.n_grupo} value={grupo.s_descripcion}>
                                {grupo.s_descripcion}
                            </option>
                            ))
                        : null
                    }
                </select>
                <label htmlFor="grupo">Grupo</label>
            </div>
        </div>
        <div className="form-cols-2">
            <button className="btn" onClick={handleClickAgregar}>Agregar</button>
            <button className="btn" onClick={handleClickQuitar}>Quitar</button>
        </div>
        <div className="form-cols-1 text-center">
            <NavLink to="/admin/mail/grupos">
                <span className="btn-link">Nuevo Grupo</span>
            </NavLink>
        </div>
    </div>
    );
}