// store/index.js
import { createStore } from 'vuex';

const store = createStore({
    state: {
        username: '',
        password: '',
    },
    mutations: {
        setUsername(state, username) {
            state.username = username;
        },
        setPassword(state, password) {
            state.password = password;
        },
    },
    actions: {},
    getters: {
        getUsername: state => state.username,
        getPassword: state => state.password,
    },
});

export default store;
