import React from 'react';

interface ModalProps {
	isOpen: boolean;
	onClose: () => void;
	title: string;
	modalSize?: 'small' | 'medium' | 'large' | 'extra-large';
	children?: React.ReactNode;
	isEuroCurvas?: boolean;
}

export const Modal: React.FC<ModalProps> = ({
												isOpen,
												onClose,
												title,
												modalSize,
												children,
												isEuroCurvas
											}) => {

	let widthClasses = 'w-full md:w-3/4 lg:w-1/2';

	switch (modalSize) {
		case 'small':
			widthClasses = 'w-1/2 md:w-1/3 lg:w-1/4';
			break;
		case 'medium':
			widthClasses = 'w-3/4 md:w-2/3 lg:w-1/2';
			break;
		case 'large':
			widthClasses = 'w-3/4 md:w-1/2 lg:w-1/2 xl:w-2/3 l:w-1/2';
			break;
		case 'extra-large':
			widthClasses = 'w-4/4 md:w-2/3 lg:w-2/2 xl:w-4/5';
			break;
	}

	if (!isOpen) return null;

	return (
		<div className="fixed z-50 inset-0 overflow-y-auto">
			<div className="flex items-center justify-center">
				<div className="fixed inset-0 bg-black opacity-40"></div>
				<div className={`bg-white rounded-lg z-50 p-6 ${isEuroCurvas ? 'mt-8' : 'mt-40'} ${widthClasses} ${isOpen ?
					'animate__animated animate__bounceIn' : 'animate__animated animate__fadeInDown'}`}>

					<div className="flex justify-between">
						<div className="p-1 w-full text-center text-cyan-700 font-bold">
							<span>{title}</span>
						</div>
						<button className="text-gray-700 hover:text-gray-900" onClick={onClose}>
							<svg className="h-6 w-6 fill-current" viewBox="0 0 24 24">
								<path
									d="M18.3,5.71a1,1,0,0,0-1.42,0L12,10.59,7.12,5.71a1,1,0,1,0-1.42,1.42L10.59,12,
									5.71,16.88a1,1,0,0,0,1.42,1.42L12,13.41l4.88,4.88a1,1,0,0,0,1.42-1.42L13.41,12Z"
								/>
							</svg>
						</button>
					</div>
					{children}
				</div>
			</div>
		</div>
	);
}