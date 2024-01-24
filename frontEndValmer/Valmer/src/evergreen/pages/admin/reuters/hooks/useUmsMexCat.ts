import { useState } from "react";
import {
  fetchDataGetRet,
  fetchDataGetSave,
  fetchDataPost,
  fetchDataPostAct,
  fetchDataPostRetNoAlert,
  showAlert
} from "../../../../../utils";
import { IUmsMexCat, RegistrosUMSMexCat } from '../Models'
import { Base64 } from 'js-base64'
import fileDownload from 'js-file-download'
import Swal from "sweetalert2";

export const useUmsMexCat = () => {
  const InitialData: IUmsMexCat = {
    status: 0,
    body: {
      total_registros: 0,
      total_paginas: 0,
      registros:
        [{
          S_ISIN: '',
          S_RIC_FORMATO: '',
          S_PROVEDOR: ''
        }
        ]
    }
  }

  const regInicial: RegistrosUMSMexCat = {
    S_ISIN: '',
    S_RIC_FORMATO: '',
    S_PROVEDOR: ''
  }

  const [tableData, setTableData] = useState<IUmsMexCat>(InitialData)
  const [loadingData, setLoadingData] = useState<boolean>(false)
  const [totaPages, setTotalPages] = useState(5);
  const [isOpenDelete, setOpenDelete] = useState(false);
  const [isOpenEdit, setOpenEdit] = useState(false);
  const [isOpenCarga, setOpenCarga] = useState(false);
  const [registro, setRegistro] = useState<RegistrosUMSMexCat>(regInicial)
  const [textSearch, setTextSearch] = useState('');
  const [isinToDelete, setIsinToDelete] = useState<string | null>(null);
  const [parametrosLatam, setParametrosLatam] = useState<string>('');
  const [parametrosMexCat, setParametrosMexCat] = useState<string>('');
  const [loadingSubmit, setLoadingSubmit] = useState(false);


  const getDataTable = async (numRegistros: number, position: number, txt_buscar: string, id_reu_formato: number) => {
    try {
      setLoadingData(true);
      const response = await fetchDataGetRet(
        "/reuters/carga-info-tabla/",
        "al obtener datos",
        {
          id_reu_formato: id_reu_formato,
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

  const saveDataUser = async (registro: RegistrosUMSMexCat, idReuFormato: number): Promise<boolean> => {
    try {
      setLoadingData(true)
      const dataPost = {}
      const resp = await fetchDataPost(
        "/reuters/inserta-actualiza-registro",
        " al intentar actualizar informacion",
        dataPost,
        {
          idReuFormato: idReuFormato,
          sIsin: registro.S_ISIN,
          sProveedor: registro.S_PROVEDOR,
          sRicFormato: registro.S_RIC_FORMATO
        })
      setLoadingData(false)
      return true
    } catch (error: any) {
      console.log('el error es : --->> ' + error)
      return false
    }
  }

  const deletebyIsin = async (idReuFormato: number, sIsin: string | null): Promise<boolean> => {
    try {
      setLoadingData(true)
      await fetchDataPostAct(
        "reuters/elimina-isin",
        "Borrar",
        " al borrar datos",
        [], {
        idReuFormato: idReuFormato,
        sIsin: sIsin
      }
      )
      setLoadingData(false)
      return true
    } catch (error: any) {
      console.log('el error es : --->> ' + error)
      return false
    }

  }

  const downloadCsvFile = async (idReuFormato: number) => {
    try {
      const response = await fetchDataPostRetNoAlert(
        "/reuters/carga-info-csv",
        " al descargar archivo csv",
        [], { idReuFormato: idReuFormato }
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

  const handleOpenDelete = (sisinToDelete: string) => {
    setIsinToDelete(sisinToDelete);
    setOpenDelete(true);
  };

  const handleCloseDelete = () => {
    setOpenDelete(false);
  }

  const handleOpenEdit = (e: React.MouseEvent<HTMLElement>) => {
    const sIsin: string | null = e.currentTarget.getAttribute("data-sisin")
    const sFormato: string | null = e.currentTarget.getAttribute("data-ricformato")
    const sProveedor: string | null = e.currentTarget.getAttribute("data-proveedor")
    setOpenEdit(true);
    setRegistro({
      S_ISIN: sIsin ? sIsin : '',
      S_RIC_FORMATO: sFormato ? sFormato : '',
      S_PROVEDOR: sProveedor ? sProveedor : ''
    })

  }

  const handleChangeForm = (e: React.FormEvent<HTMLInputElement>) => {
    e.preventDefault()
    const nameInput: string = e.currentTarget.name
    const value: string = e.currentTarget.value
    setRegistro({ ...registro, [nameInput]: value })
  }

  const HandleChangeBuscar = async (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault()
    const text = e.currentTarget.value
    setTextSearch(text)
  }

  const handleCloseEdit = () => {
    setOpenEdit(false);
  }

  const handleClickReutersLatam = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    const confirmationResult = await Swal.fire({
      title: 'Desea cargar a la BD',
      showCancelButton: true,
      confirmButtonText: 'Continuar',
      cancelButtonText: 'Cancelar',
      icon: 'question',
    });
    if (confirmationResult.isConfirmed){
      setLoadingSubmit(true);
      await fetchDataPost('/reuters/carga-masiva', 'Error al cargar datos','',
        {
          idReuFormato: 9018,
          sInfoCarga: parametrosLatam
        }
      );

      setLoadingSubmit(false);
      setOpenCarga(false);
      await getDataTable(12, 0, textSearch, 9018);
      setParametrosLatam('');
    }
  };
  const handleTextareaChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setParametrosLatam(event.target.value);
  };

  const handleClickReutersMexCat = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    const confirmationResult = await Swal.fire({
      title: 'Desea cargar a la BD',
      showCancelButton: true,
      confirmButtonText: 'Continuar',
      cancelButtonText: 'Cancelar',
      icon: 'question',
    });
    if (confirmationResult.isConfirmed){
      setLoadingSubmit(true);
      await fetchDataPost('/reuters/carga-masiva', 'Error al cargar datos','',
        {
          idReuFormato: 9017,
          sInfoCarga: parametrosMexCat
        }
      );

      setLoadingSubmit(false);
      setOpenCarga(false);
      await getDataTable(12, 0, textSearch, 9017);
      setParametrosMexCat('');
    }
  };
  const handleTextareaChangeMexCat = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setParametrosMexCat(event.target.value);
  };

  return {
    handleTextareaChange,
    handleClickReutersMexCat,
    parametrosMexCat,
    parametrosLatam,
    loadingSubmit,
    handleTextareaChangeMexCat,
    tableData,
    loadingData,
    totaPages,
    getDataTable,
    downloadCsvFile,
    textSearch,
    HandleChangeBuscar,
    handleClickReutersLatam,
    saveDataUser,
    deletebyIsin,
    handleOpenCarga,
    handleOpenDelete,
    handleOpenEdit,
    isOpenCarga,
    handleCloseCarga,
    isOpenEdit,
    handleCloseEdit,
    registro,
    handleChangeForm,
    isOpenDelete,
    handleCloseDelete,
    setOpenDelete,
    isinToDelete,
    setOpenEdit
  }
}