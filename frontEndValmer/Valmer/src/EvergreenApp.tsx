import {AppRouter} from "./router/AppRouter";
import {AuthProvider} from "./auth";
import 'animate.css';
import './input.css'

export const EvergreenApp = () => {
    return (
        <AuthProvider>
            <AppRouter/>
        </AuthProvider>
    )
}