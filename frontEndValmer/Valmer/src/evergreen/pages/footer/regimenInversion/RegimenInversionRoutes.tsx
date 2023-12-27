import { Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { RegimenInversion } from './RegimenInversion';
import { storeRegInv } from '../../../../redux/RegimenInversion';

export const RegimenInversionRoutes = () => (
    <Routes>
        <Route 
            path=""
            element={
                <Provider store={storeRegInv}>
                    <RegimenInversion />
                </Provider>
            }
        />
    </Routes>
)