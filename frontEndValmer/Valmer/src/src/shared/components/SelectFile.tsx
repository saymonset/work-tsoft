import { useState } from 'react';
import {handleFileChange} from "../../utils";

export const SelectFile = () => {

	const [selectedFile, setSelectedFile] = useState<File | null>(null);
	const [fileBase64, setFileBase64] = useState<string>("");

	// console.log(fileBase64);  // aquí puedes ver el resultado en base64

	return (
		<>
			<div className="text-xs text-cyan-700 my-3">
				<label htmlFor="file-upload" className="relative cursor-pointer bg-cyan-700 hover:bg-green-700 text-white py-2 px-4 rounded">
					<span className="text-sm">Examinar...</span>
					<input id="file-upload"
						   type="file"
						   className="hidden"
						   onChange={(e) => handleFileChange(e,
							   setFileBase64, setSelectedFile)} />
				</label>
				<span className="py-2 px-4 text-cyan-700">{selectedFile?.name ?? 'Ningún archivo seleccionado.'}</span>
			</div>
		</>
	);
}