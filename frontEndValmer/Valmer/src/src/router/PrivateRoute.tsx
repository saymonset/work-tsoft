import {Navigate, useLocation} from "react-router-dom";

export const PrivateRoute = ({children}: any) => {

    const { pathname, search } = useLocation();
    const lastPath = pathname + search;
    localStorage.setItem('lastPath', lastPath );

    const user = localStorage.getItem('user');
    const logged = !!user;

    return( logged ) ? children : <Navigate to="/login"/>
}
