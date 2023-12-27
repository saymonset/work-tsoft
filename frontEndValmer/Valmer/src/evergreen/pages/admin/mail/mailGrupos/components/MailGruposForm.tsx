import React from "react";

interface MailGruposFormProps {
    selectedGroup: {
        n_grupo: string;
        s_descripcion: string;
    };
    setSelectedGroup: (group: any) => void;
    setModifiedDescription: (value: boolean) => void;
}

export const MailGruposForm: React.FC<MailGruposFormProps> = ({
                                                                  selectedGroup,
                                                                  setSelectedGroup,
                                                                  setModifiedDescription
                                                              }) => {
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const updatedGroup = {...selectedGroup, s_descripcion: e.target.value};
        setSelectedGroup(updatedGroup);
        setModifiedDescription(true);
    };

    return (
        <div className="form-cols-1 -my-3">
            <span className="form-title">DATOS</span>
            <div className="form-cols-1 -my-3">
                <div className="form-input">
                    <input type="text" name="n_grupo" id="n_grupo" placeholder="" value={selectedGroup.n_grupo}
                           disabled/>
                    <label htmlFor="n_grupo">N_GRUPO</label>
                </div>
            </div>
            <div className="form-cols-1 -my-3">
                <div className="form-input">
                    <input
                        type="text"
                        name="s_descripcion"
                        id="s_descripcion"
                        placeholder=""
                        value={selectedGroup.s_descripcion}
                        onChange={handleChange}
                    />
                    <label htmlFor="s_descripcion">S_DESCRIPCION</label>
                </div>
            </div>
        </div>
    );
};