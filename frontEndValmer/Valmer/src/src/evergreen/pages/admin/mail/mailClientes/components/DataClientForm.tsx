import React from "react";

interface DataClientFormProps {
  formData: {
    idCliente: string;
    empresa: string;
    nombreCliente: string;
    mailCliente: string;
    grupo: string;
  };
  handleInputChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
}

const DataClientForm: React.FC<DataClientFormProps> = ({ formData, handleInputChange }) => {
  return (
    <div className="form-cols-1 -my-3">
      <div className="form-input">
        <label htmlFor="idCliente">Id Cliente</label>
        <input
          type="text"
          name="idCliente"
          id="idCliente"
          disabled
          value={formData.idCliente}
          onChange={handleInputChange}
        />
      </div>
      <div className="form-input">
        <label htmlFor="empresa">Empresa</label>
        <input
          type="text"
          name="empresa"
          id="empresa"
          disabled
          value={formData.empresa}
          onChange={handleInputChange}
        />
      </div>
      <div className="form-input">
        <label htmlFor="nombreCliente">Nombre Cliente</label>
        <input
          type="text"
          name="nombreCliente"
          id="nombreCliente"
          disabled
          value={formData.nombreCliente}
          onChange={handleInputChange}
        />
      </div>
      <div className="form-input">
        <label htmlFor="mailCliente">Mail Cliente</label>
        <input
          type="email"
          name="mailCliente"
          id="mailCliente"
          disabled
          value={formData.mailCliente}
          onChange={handleInputChange}
        />
      </div>
      <div className="form-input">
        <label htmlFor="mailCliente">Grupos</label>
        <input 
          type="text"
          name="grupo"
          id="grupo"
          disabled 
          value={formData.grupo}
          onChange={handleInputChange}/>
      </div>
    </div>
  );
};

export default DataClientForm;
