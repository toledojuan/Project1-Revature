

import * as actions from '../actions/actionTypes';

const initialState = {
    user: null
}


export default function reducer(state = initialState, action){
    const updatedState = {
        ...state,
        user: {
            ...state.user
        } 
    }
    switch(action.type){
        case actions.USER_LOGGED_IN:
            updatedState.user = {
                ...updatedState.user,
                isLoggedIn: action.payload.isLoggedIn
            }
            return updatedState;

        case actions.USER_LOGGED_OUT:
            updatedState.user = {
                ...updatedState.user,
                isLoggedIn: action.payload.isLoggedIn
            }
            return updatedState;   
        default:
            return updatedState;
    }
}