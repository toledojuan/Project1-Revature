import * as actions from '../actions/actionTypes';


export const userLoggedIn = () =>({
    type: actions.USER_LOGGED_IN,
    payload: {
        isLoggedIn: true
    }
})

export const userLoggedOut = () =>({
    type: actions.USER_LOGGED_OUT,
    payload: {
        isLoggedIn: false
    }
})