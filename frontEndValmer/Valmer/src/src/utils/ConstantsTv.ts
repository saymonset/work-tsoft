export const customStyles = {
    control: (provided: any, state: { isFocused: any; }) => ({
        ...provided,
        backgroundColor: 'transparent',
        border: 'none',
        borderRadius: 'none',
        borderBottom: '2px solid #4b5563',
        boxShadow: 'none',
        "&:hover": {
            borderColor: state.isFocused ? '#0e7490' : '#111827'
        }
    }),
    dropdownIndicator: (provided: any) => ({
        ...provided,
        color: '#0e7490',
    }),
    option: (provided: any, state: { isFocused: any; }) => ({
        ...provided,
        backgroundColor: state.isFocused ? '#2563eb' : "#fff",
        color: state.isFocused ? '#ffffff' : '#111827',
    }),
    singleValue: (provided: any, state: any) => ({
        ...provided,
        color: '#111827',
    }),
    menu: (provided: any) => ({
        ...provided,
        backgroundColor: 'white',
    })
}