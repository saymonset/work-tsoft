import { useState } from "react";
import { fetchDataGetRet } from "../../../../../utils";
import { ILiqLatam, RegistrosLiqLatam } from '../Models'
import { Base64 } from 'js-base64'
import fileDownload from 'js-file-download'

export const useliqLatamCat = () => {
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

  const [tableData, setTableData] = useState<ILiqLatam>(InitialData)
  const [loadingData, setLoadingData] = useState<boolean>(false)
  const [totaPages, setTotalPages] = useState(10);
  const [isOpenCarga, setOpenCarga] = useState(false);
  const [isOpenDelete, setOpenDelete] = useState(false);
  const [n_cbo_pais, setN_cbo_pais] = useState(0);
  const [isOpenEdit, setOpenEdit] = useState(false);
  const [registro, setRegistro] = useState<RegistrosLiqLatam>(regInicial);
  const [textSearch, setTextSearch] = useState('')

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
  
  const deletebyIsin = async (pais: number, sIsin: string): Promise<boolean> => {
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
    try {
      const response = await fetchDataGetRet(
        "/reuters/liquidez/descargar-csv/?n_cbo_pais=" + n_cbo_pais,
        " al descargar archivo csv",
        {}
      )
      const cvs = Base64.decode(response.body.contenido)
      fileDownload(cvs, response.body.nombreCompleto)
      return true
    } catch (error: any) {
      console.log('el error es : --->> ' + error)
      return false
    }

  }

  const handleOpenCarga = () => {
    setOpenCarga(true);
  }

  const handleCloseCarga = () => {
    setOpenCarga(false);
  }

  const handleOpenDelete = (e: React.MouseEvent<HTMLElement>) => {
    setOpenDelete(true);
  }

  const handleCloseDelete = () => {
    setOpenDelete(false);
  }

  const handleOpenEdit = (e: React.MouseEvent<HTMLElement>) => {
    const sIsin: string | null = e.currentTarget.getAttribute("data-sisin")
    const instrumento: string | null = e.currentTarget.getAttribute("data-instrumento")
    const ric: string | null = e.currentTarget.getAttribute("data-sric")
    const stipo: string | null = e.currentTarget.getAttribute("data-stipo")
    setOpenEdit(true);
    setRegistro({
      id_reu_formato: n_cbo_pais,
      s_formato: n_cbo_pais == 1055 ? 'LIQUIDEZ_CR' : n_cbo_pais == 1056 ? 'LIQUIDEZ_PAN' : '',
      s_ric: ric ?? '',
      isin: sIsin ?? '',
      s_tipo: stipo ?? '',
      s_instrumento: instrumento ? instrumento : ''
    })
  }

  const handleCloseEdit = () => {
    setOpenEdit(false);
  }

  const HandleChangeBuscar = async (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault()
    const text = e.currentTarget.value
    setTextSearch(text)
  }

  const searchText = async () => {
    await getDataTable(n_cbo_pais, 12, 0, textSearch)
  }

  const HandleChangePais = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const value = Number(e.currentTarget.value)
    setN_cbo_pais(value)
  }

  const handleChangeForm = (e: React.FormEvent<HTMLInputElement>) => {
    e.preventDefault()
    const nameInput: string = e.currentTarget.name
    const value: string = e.currentTarget.value
    setRegistro({ ...registro, [nameInput]: value })
  }

  const handleSubmitForm = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setOpenEdit(false)
    await saveDataUser(registro, n_cbo_pais)
    await getDataTable(n_cbo_pais, 12, 0, textSearch)
  }

  const deleteByISIN = async () => {
    setOpenDelete(false);
    setOpenEdit(false);
    await deletebyIsin(n_cbo_pais, registro.isin)
    await getDataTable(n_cbo_pais, 12, 0, textSearch)
  }

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      searchText();
    }
  };

  return {
    tableData,
    loadingData,
    totaPages,
    getDataTable,
    downloadCsvFile,
    isOpenCarga,
    handleOpenCarga,
    handleCloseCarga,
    handleOpenDelete,
    isOpenDelete,
    handleCloseDelete,
    n_cbo_pais,
    isOpenEdit,
    handleCloseEdit,
    registro,
    HandleChangeBuscar,
    handleOpenEdit,
    searchText,
    HandleChangePais,
    handleChangeForm,
    handleSubmitForm,
    deleteByISIN,
    textSearch,
    handleKeyDown
  }
}