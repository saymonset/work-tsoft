import React, { useState } from "react";
import {fetchDataGetRet, fetchDataGetSave} from "../../../../../utils";
import { ILiqLatam, RegistrosLiqLatam } from '../Models'
import { Base64 } from 'js-base64'
import fileDownload from 'js-file-download'
import Swal from "sweetalert2";

const InitialData: ILiqLatam = {
  status: 0,
  body: {
    total_registros: 0,
    total_paginas: 0,
    registros:
        [{
          id_reu_formato: 0,
          s_formato: "",
          s_ric: "",
          isin: "",
          s_tipo: "",
          s_instrumento: ""
        }
        ]
  }
}

const regInicial: RegistrosLiqLatam = {
  id_reu_formato: 0,
  s_formato: "",
  s_ric: "",
  isin: "",
  s_tipo: "",
  s_instrumento: ""
}

export const useLiqLatamCat = () => {

  const [tableData, setTableData] = useState<ILiqLatam>(InitialData)
  const [loadingData, setLoadingData] = useState<boolean>(false)
  const [totalPages, setTotalPages] = useState(10);
  const [isOpenCarga, setIsOpenCarga] = useState(false);
  const [isOpenDelete, setIsOpenDelete] = useState(false);
  const [n_cbo_pais, setN_cbo_pais] = useState(0);
  const [isOpenEdit, setIsOpenEdit] = useState(false);
  const [registro, setRegistro] = useState<RegistrosLiqLatam>(regInicial);
  const [textSearch, setTextSearch] = useState('')
  const [parametros, setParametros] = useState<string>('');
  const [loadingSubmit, setLoadingSubmit] = useState(false);
  const [isOpenModalConfirmCarga, setIsOpenModalConfirmCarga] = useState(false);

  const getDataTable = async (pais: number, numRegistros: number, position: number, txt_buscar: string) => {
    try {
      setLoadingData(true);
      const response = await fetchDataGetRet(
        "/reuters/liquidez/carga-info-tabla",
        "al obtener datos",
        {
          n_cbo_pais: pais,
          num_registros: numRegistros,
          posicion: position,
          txt_buscar: txt_buscar
        })
      if (response.status == 200) {
        setTableData(response)
        const totalP = Math.ceil(response.body.total_registros / numRegistros);
        setTotalPages(totalP)
      } else {
        setTableData(InitialData)
      }
      setLoadingData(false);
    } catch (error) {
      console.log(error)
    }
  }

  const saveDataUser = async (registro: RegistrosLiqLatam, pais: number): Promise<boolean> => {
    try {
      setLoadingData(true)
      await fetchDataGetRet(
        "/reuters/liquidez/inserta-actualiza-registro",
        " al intentar actualizar informacion",
        {
          n_cbo_pais: pais,
          s_instrumento: registro.isin,
          s_isin: registro.isin,
          s_ric: registro.s_ric,
          s_tipo: registro.s_tipo
        })
      setLoadingData(false)
      return true
    } catch (error: any) {
      console.log('el error es : --->> ' + error)
      return false
    }
  }
  
  const deleteByIsin = async (pais: number, sIsin: string): Promise<boolean> => {
    try {
      setLoadingData(true)
      await fetchDataGetRet(
        "/reuters/liquidez/elimina-isin",
        "Borrar",
        {
          n_cbo_pais: pais,
          s_isin: sIsin
        }
      )
      setLoadingData(false)
      return true
    } catch (error: any) {
      console.log('el error es : --->> ' + error)
      return false
    }

  }

  const downloadCsvFile = async (n_cbo_pais: number) => {
    try
    {
      const response = await fetchDataGetRet(
        "/reuters/liquidez/descargar-csv/",
        " al descargar archivo csv",
        {n_cbo_pais: n_cbo_pais}
      )
      const cvs = Base64.decode(response.body.contenido)
      fileDownload(cvs, response.body.nombreCompleto)
      return true
    }
    catch (error: any)
    {
      console.log('el error es : --->> ' + error)
      return false
    }
  }

  const handleTextareaChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setParametros(event.target.value);
  };

  const handleClick = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    
      setLoadingSubmit(true);

      await fetchDataGetSave('/reuters/liquidez/carga-masiva',
          ' al intentar cargar datos en carga masiva',
          {
            n_cbo_pais: n_cbo_pais,
            txt_info_carga: parametros
          });

      setLoadingSubmit(false);
      setIsOpenCarga(false);

      await getDataTable(n_cbo_pais, 12, 0, textSearch);

      setParametros('');
  };

  const determinarFormato = (n_cbo_pais: number) => {
    switch (n_cbo_pais) {
      case 1055:
        return 'LIQUIDEZ_CR';
      case 1056:
        return 'LIQUIDEZ_PAN';
      default:
        return '';
    }
  };

  const handleOpenDelete = (e: React.MouseEvent<HTMLElement>) => {
    const sIsin: string | null = e.currentTarget.getAttribute("data-sisin")
    const instrumento: string | null = e.currentTarget.getAttribute("data-instrumento")
    const ric: string | null = e.currentTarget.getAttribute("data-sric")
    const stipo: string | null = e.currentTarget.getAttribute("data-stipo")
    setIsOpenDelete(true);
    setRegistro({
      id_reu_formato: n_cbo_pais,
      s_formato: determinarFormato(n_cbo_pais),
      s_ric: ric ?? '',
      isin: sIsin ?? '',
      s_tipo: stipo ?? '',
      s_instrumento: instrumento ?? ''
    })
  }

  const handleOpenEdit = (e: React.MouseEvent<HTMLElement>) => {
    const sIsin: string | null = e.currentTarget.getAttribute("data-sisin")
    const instrumento: string | null = e.currentTarget.getAttribute("data-instrumento")
    const ric: string | null = e.currentTarget.getAttribute("data-sric")
    const stipo: string | null = e.currentTarget.getAttribute("data-stipo")
    setIsOpenEdit(true);
    setRegistro({
      id_reu_formato: n_cbo_pais,
      s_formato: determinarFormato(n_cbo_pais),
      s_ric: ric ?? '',
      isin: sIsin ?? '',
      s_tipo: stipo ?? '',
      s_instrumento: instrumento ?? ''
    })
  }

  const handleChangeBuscar = async (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault()
    setTextSearch(e.currentTarget.value)
  }

  const searchText = async () => {
    await getDataTable(n_cbo_pais, 12, 0, textSearch)
  }

  const handleChangePais = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setN_cbo_pais(Number(e.currentTarget.value))
  }

  const handleChangeForm = (e: React.FormEvent<HTMLInputElement>) => {
    e.preventDefault()
    setRegistro({ ...registro, [e.currentTarget.name]: e.currentTarget.value })
  }

  const handleSubmitForm = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setIsOpenEdit(false)
    await saveDataUser(registro, n_cbo_pais)
    await getDataTable(n_cbo_pais, 12, 0, textSearch)
  }

  const deleteByISIN = async () => {
    setIsOpenDelete(false);
    setIsOpenEdit(false);
    await deleteByIsin(n_cbo_pais, registro.isin)
    await getDataTable(n_cbo_pais, 12, 0, textSearch)
  }

  const handleKeyDown = async (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      await searchText();
    }
  };

  const openModalConfirmCarga = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    setIsOpenModalConfirmCarga(true);
  }

  const handleCloseConfirm = () => {
    setIsOpenModalConfirmCarga(false);
  }

  const handleCloseDelete = () => {
    setIsOpenDelete(false);
  }

  const handleOpenCarga = () => {
    setIsOpenCarga(true);
  }

  const handleCloseCarga = () => {
    setIsOpenCarga(false);
  }

  const handleCloseEdit = () => {
    setIsOpenEdit(false);
  }

  return {
    loadingData, loadingSubmit,
    tableData, totalPages,
    n_cbo_pais, registro, parametros,
    textSearch, isOpenEdit, isOpenCarga, isOpenDelete,
    isOpenModalConfirmCarga,
    getDataTable, downloadCsvFile,
    handleOpenCarga, handleCloseCarga,
    handleOpenDelete, handleCloseDelete, handleCloseEdit, handleChangeBuscar,
    handleOpenEdit, handleChangePais,
    handleChangeForm, handleSubmitForm, handleClick,
    handleTextareaChange, handleKeyDown, handleCloseConfirm,
    searchText, deleteByISIN,
    openModalConfirmCarga
  }
}