import React, { useState } from "react";
import { fetchDataGetRet, fetchDataPostRet, userEncoded } from "../../../../../../../utils";

export const useModalProceso = (updateCatalogo: () => void) => {
  const [selectedNID, setSelectedNID] = useState<number | null>(null);
  const [selectedInstrumento, setSelectedInstrumento] = useState<string>("");
  const [selectedCurva, setSelectedCurva] = useState<string>("");
  const [loading1, setLoading1] = useState(false);
  const [loadingButtonNuevo, setLoadingButtonNuevo] = useState<boolean>(false);
  const [loadingButtonGuardar, setLoadingButtonguardar] = useState<boolean>(false);
  const [loadingButtonBorrar, setLoadingButtonBorrar] = useState<boolean>(false);
  const [changeCatalogPost, setChangeCatalogPost] = useState(false);

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
    setLoadingButtonguardar(true);

    const request = {
      n_id: selectedNID?.toString(),
      s_instrumento: selectedInstrumento,
      s_curva: selectedCurva,
    };
    setLoading1(true);

    await fetchDataPostRet(
      "/catalogos/guardar-catalogo",
      " al guardar datos",
      request,
      {
        s_nombre_catalogo: 'EURO_CURVAS',
        s_user: userEncoded(),
      }
    );

    setLoading1(false);
    setChangeCatalogPost(true);
    setLoadingButtonguardar(false);
    handleLimpiar();

    updateCatalogo();
  };

  const handleNuevo = async () => {
    setLoadingButtonNuevo(true);
    try {
      const newId = await fetchDataGetRet(
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
    setLoading1(true);

    await fetchDataPostRet(
      "/catalogos/borrar-catalogo",
      " al borrar datos",
      request,
      {
        s_nombre_catalogo: 'EURO_CURVAS',
        s_user: userEncoded(),
      }
    );

    setLoading1(false);
    setChangeCatalogPost(true);
    setLoadingButtonBorrar(false);
    handleLimpiar();

    updateCatalogo();
  };

  return {
    handleClick,
    handleLimpiar,
    handleInstrumentoChange,
    handleCurvaChange,
    handleGuardar,
    selectedNID,
    selectedInstrumento,
    selectedCurva,
    handleNuevo,
    loadingButtonNuevo,
    loadingButtonGuardar,
    handleBorrar,
    loadingButtonBorrar
  }
}