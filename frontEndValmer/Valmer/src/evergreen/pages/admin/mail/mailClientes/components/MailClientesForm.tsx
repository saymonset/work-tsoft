import React, { useEffect, useState } from "react";
import { BarLoader } from "react-spinners";
import DataClientForm from "./DataClientForm";
import { GroupsForm } from "./GroupsForm";
import { useGetConsultaInfoMail } from "../hooks/useGetConsultaInfoMail";

interface MailClientesFormProps {
  selectedNCliente: number | null;
  selectedEmpresa: number | null;
}

export const MailClientesForm: React.FC<MailClientesFormProps> = ({
    selectedNCliente,
    selectedEmpresa,
  }) => {
    const { infoTableMail, loadingCarga, fetchDataInfoMail } = useGetConsultaInfoMail();

    const formDataEmpty = {
      idCliente: "",
      empresa: "",
      nombreCliente: "",
      mailCliente: "",
      grupo: ""
    }
    
    const [formData, setFormData] = useState(formDataEmpty);
  
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
      const { name, value } = e.target;
      setFormData({
        ...formData,
        [name]: value
      });
    };
  
    useEffect(() => {
      if (selectedNCliente) {
        fetchDataInfoMail(selectedNCliente);
      }
      setFormData(formDataEmpty);
    }, [selectedNCliente, selectedEmpresa]);
    
    useEffect(() => {
        if (infoTableMail?.body && infoTableMail.body.length > 0) {
            const grupos = infoTableMail.body.map((item) => item.s_grupos).join(", ");
            const lastItem = infoTableMail.body[infoTableMail.body.length - 1];
            setFormData({
                idCliente: lastItem.n_cliente.toString(),
                empresa: lastItem.n_empresa,
                nombreCliente: lastItem.s_nombre,
                mailCliente: lastItem.s_email,
                grupo: grupos,
            });
        } else {
            setFormData(formDataEmpty);
        }
    }, [infoTableMail]);

    return (
        <form className="animate__animated animate__fadeIn">
            <div className="flex justify-center my-5">
                <div className="form-cols-2 w-full">
                    <div className="form-cols-1">
                        <span className="form-title">DATOS DEL CLIENTE</span>
                        {loadingCarga && (
                            <BarLoader
                                className="ml-2 w-full mt-2 mb-2"
                                color="#059669"
                                width={500}
                            />
                        )}
                        <DataClientForm formData={formData} handleInputChange={handleInputChange}/>
                    </div>
                    <GroupsForm formData={formData} setFormData={setFormData}/>
                </div>
            </div>
        </form>
    );
};