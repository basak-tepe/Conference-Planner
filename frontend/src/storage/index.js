import { defineStore } from 'pinia'

export const useLocalStorageStore = defineStore('localStorage', {
    state: () => ({
        username: '',
        password: '',
    }),
    actions: {
        setUsername(username) {
            this.username = username
            localStorage.setItem('username', username)
        },
        setPassword(password) {
            this.password = password
            localStorage.setItem('password', password)
        },
        getUsername() {
            this.username = localStorage.getItem('username')
            return this.username
        },
        getPassword() {
            this.password = localStorage.getItem('password')
            return this.password
        },
    },
})
