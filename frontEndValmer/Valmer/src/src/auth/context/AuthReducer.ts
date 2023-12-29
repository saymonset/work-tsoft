import {types} from "../types/types";

export const AuthReducer = (state = {}, action: { type: any; payload?: any; } ) => {

    switch ( action.type ) {

        case types.login:
            return {
                ...state,
                logged: true,
                user: action.payload
            };

        case types.logout:
            return {
                logged: false,
            };

        default:
            return state;
    }
}