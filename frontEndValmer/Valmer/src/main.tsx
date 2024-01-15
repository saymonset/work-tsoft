import React from 'react'
import ReactDOM from 'react-dom/client'
import {BrowserRouter} from "react-router-dom";
import {EvergreenApp} from "./EvergreenApp";

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
    <React.StrictMode>
        <BrowserRouter>
            <EvergreenApp/>
        </BrowserRouter>
    </React.StrictMode>,
)
                        