interface FormatArchProps {
    isHelp: boolean
}

export const FormatArch = (props: FormatArchProps) => {
    return (
        <div className={`w-full animate__animated ${props.isHelp ? "animate__fadeIn" : "animate__fadeOut hidden"}`}>
            <div
                className="bg-cyan-700 text-white px-1 mt-2 mx-1 text-center"
            >
                Formato de archivo (?)
            </div>
            <div className="mx-1 text-center border border-gray-400">
                <p className="text-xs my-1">Con cabecera, separados por coma</p>
                <p style={{wordSpacing: '1em'}}>TV EMISORA SERIE AAAA-MM-DD</p>
            </div>
        </div>
    )
}