import { Routes, Route } from 'react-router-dom';
import { MailClientes } from './mailClientes';
import { MailGrupos } from './mailGrupos';
import { Provider } from 'react-redux';
import { storeClient } from '../../../../redux';

export const MailRoutes = () => (
    <Routes>
        <Route 
            path='/clientes/*'
            element={
                <Provider store={storeClient}>
                    <MailClientes />
                </Provider>
            }
        />
        <Route
            path='/grupos/*'
            element={
                <Provider store={storeClient}>
                    <MailGrupos/>
                </Provider>
            }
        />
    </Routes>
)