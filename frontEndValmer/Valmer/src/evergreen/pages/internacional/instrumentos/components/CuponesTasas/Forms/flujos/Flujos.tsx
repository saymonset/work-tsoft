import React from 'react'
import { FlujosTable } from './FlujosTable'
import { Modal } from '../../../../../../../../shared'
import { useHandleDataFlujo } from './hooks'
import { BarLoader } from 'react-spinners'

export const Flujos = ({requeridos}: any) => {

	const {
		isModalOpen,
		titleModal,
		isDiaSig,
		loading,
		txtAreaCargaFlujos,
		handleModalClose,
		handleActualizaFlujo,
		handleActualizaDiaSig,
		handleActDiaSig,
		handleActFlujos,
		handleChangeTxtFlujos
	} = useHandleDataFlujo();

	return (
		<>
			<div className="px-3">
				<div className="bg-cyan-700 px-1 text-slate-50">
					<span>Flujos</span>
				</div>
				<div className="grid grid-cols-3 gap-4">
					<div className='col-start-2 flex justify-center mt-1'>
						<button
							type='button'
							className='bg-cyan-700 hover:bg-green-700 text-white font-bold py-1 px-4 rounded mx-1 w-1/2'
							onClick={handleActualizaFlujo}
						>
							<i className='fa fa-regular fa-calendar-plus'></i>
							<span className='ml-1'>Actualiza</span>
						</button>
						<button
							type='button'
							className='bg-cyan-700 hover:bg-green-700 text-white font-bold py-1 px-4 rounded mx-1 w-1/2'
							onClick={handleActualizaDiaSig}
						>
							<i className="fa fa-regular fa-calendar-plus"></i>
							<span className='ml-1'>Día Sig</span>
						</button>
					</div>
				</div>
				<div className="flex flex-col mt-3">
					<FlujosTable requeridos={requeridos} />
				</div>
			</div>
			<Modal isOpen={isModalOpen} onClose={handleModalClose} title={"Actualiza " + titleModal}>
				<div className="grid grid-cols-1 mt-1">
					
					<div className="form-title mb-5">Carga {titleModal}</div>
					<form onSubmit={isDiaSig ? handleActDiaSig : handleActFlujos}>
						<div className="form-text-area">
							<textarea 
								name="txtAreaCargaFlujos" 
								id="txtAreaCargaFlujos"
								value={txtAreaCargaFlujos}
								onChange={handleChangeTxtFlujos}
							/>
							<label htmlFor="txtAreaCargaFlujos">FECHA(YYYY-MM-DD),S_VN_PAGOS,N_AMORT_PORC</label>
						</div>
						{loading && <BarLoader className="w-full mt-2 mb-2" color="#059669" width={500} />}
						<div className='flex justify-end mt-4 px-3'>
							<button
								className='btn-icon'
								type='submit'
							>
								<i className="fa fa-floppy-disk"></i>
								<span>Actualiza</span>
							</button>
						</div>
					</form>
				</div>
			</Modal>
		</>
	);
}