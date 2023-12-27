import {AuthContext} from './AuthContext';
import React, {useCallback, useMemo, useReducer} from 'react';
import {AuthReducer} from './AuthReducer';
import {types} from '../types/types';

const init = () => {
    const u  = localStorage.getItem('user')
    let user;

    try {
        if (u) {
            user = JSON.parse(u);
        }
    } catch (error) {
        user = null;
    }

    return {
        logged: !!user,
        user: user,
    }
}

export const AuthProvider = ({children}: any) => {

    const [authState, dispatch] = useReducer(AuthReducer, {}, init);

    const login = useCallback((name = '') => {
        const user = { id: 'ABC', name }
        const action = { type: types.login, payload: user }
        dispatch(action);
    }, []);

    const logout = useCallback(() => {
        localStorage.removeItem('user');
        const action = { type: types.logout };
        dispatch(action);
    }, []);

    const foo = useMemo(
        () => ({...authState, login, logout}),
        [authState, login, logout]
    );

    return (
        <AuthContext.Provider value={foo}>{children}</AuthContext.Provider>
    );
}
