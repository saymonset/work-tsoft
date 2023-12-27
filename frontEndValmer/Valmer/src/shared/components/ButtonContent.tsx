
interface ButtonProps {
    name: string;
    loading: boolean
    isLogin?: boolean
}

export const ButtonContent = (p: ButtonProps) => {
    if(p.loading) {
        return <i className="fa fa-spinner fa-spin"></i>;
    }
    if(p.isLogin) {
        return <><i className="fa fa-lock"></i> {p.name}</>;
    }
    return <>{p.name}</>;
}