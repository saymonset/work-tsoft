import {Route, Routes} from "react-router-dom";
import {LipperFund} from "./lipperFund";
import { Provider } from "react-redux";
import { storeLipper } from "../../../../redux";

export const LipperFundRoutes = () => (
    <Routes>
        <Route 
            path="" 
            element={
                <Provider store={storeLipper}>
                    <LipperFund />
                </Provider>
            } 
        />
    </Routes>
);