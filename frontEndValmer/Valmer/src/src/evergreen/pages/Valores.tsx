import {Link} from "react-router-dom";
import React from "react";

export const Valores = () => {
    return (
        <div>
            <h1>Valores</h1>
            <Link to="/home">
                <button
                    className="btn btn-primary">
                    Home
                </button>
            </Link>
        </div>
    )
}