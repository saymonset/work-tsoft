import React, { useState } from "react";
import { fetchDataGetRet, fetchDataPostRet, userEncoded } from "../../../../../../../utils";

export const useModalProceso = (updateCatalogo: () => void) => {

  const [selectedNID, setSelectedNID] = useState<number | null>(null);
  const [selectedInstrumento, setSelectedInstrumento] = useState<string>("");
  const [selectedCurva, setSelectedCurva] = useState<string>("");
  const [loadingButtonNuevo, setLoadingButtonNuevo] = useState<boolean>(false);
  const [loadingButtonGuardar, setLoadingButtonGuardar] = useState<boolean>(false);
  const [loadingButtonBorrar, setLoadingButtonBorrar] = useState<boolean>(false);

  const handleClick = (e: React.MouseEvent<HTMLAnchorElement> | React.MouseEvent<HTMLButtonElement>, nID: number, instrumento: string, curva: string) => {
    e.preventDefault();
    setSelectedNID(nID);
    setSelectedInstrumento(instrumento);
    setSelectedCurva(curva);
  };

  const handleLimpiar = () => {
    setSelectedNID(null);
    setSelectedInstrumento("");
    setSelectedCurva("");
  };

  const handleInstrumentoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedInstrumento(e.target.value);
  };

  const handleCurvaChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedCurva(e.target.value);
  };

  const handleGuardar = async () => {
    setLoadingButtonGuardar(true);

    const request = {
      n_id: selectedNID?.toString(),
      s_instrumento: selectedInstrumento,
      s_curva: selectedCurva,
    };

    await fetchDataPostRet(
      "/catalogos/guardar-catalogo",
      " al guardar datos",
      request,
      {
        s_nombre_catalogo: 'EURO_CURVAS',
        s_user: userEncoded(),
      }
    );

    setLoadingButtonGuardar(false);
    handleLimpiar();

    updateCatalogo();
  };

  const handleNuevo = async () => {
    setLoadingButtonNuevo(true);
    try
    {
      const newId : { body: { N_ID: number; }; } = await fetchDataGetRet(
        "/catalogos/obtiene-nuevo-id",
        " al obtener nuevo id",
        { s_nombre_catalogo: "EURO_CURVAS" }
      );
      setSelectedNID(newId.body.N_ID);
    } catch (error) {
      console.error("Error al obtener el nuevo ID:", error);
    }
    setLoadingButtonNuevo(false);
  };

  const handleBorrar = async () => {
    setLoadingButtonBorrar(true);

    const request = {
      n_id: selectedNID?.toString(),
      s_instrumento: selectedInstrumento,
      s_curva: selectedCurva,
    };

    await fetchDataPostRet(
      "/catalogos/borrar-catalogo",
      " al borrar datos",
      request,
      {
        s_nombre_catalogo: 'EURO_CURVAS',
        s_user: userEncoded(),
      }
    );

    setLoadingButtonBorrar(false);
    handleLimpiar();

    updateCatalogo();
  };

  return {
    loadingButtonBorrar,
    loadingButtonNuevo,
    loadingButtonGuardar,
    selectedNID,
    selectedInstrumento,
    selectedCurva,
    handleClick,
    handleLimpiar,
    handleInstrumentoChange,
    handleCurvaChange,
    handleGuardar,
    handleNuevo,
    handleBorrar
  }
}