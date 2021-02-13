import Vue from 'vue'
import Vuex from 'vuex'
import Router from 'vue-router'
import Home from '@/views/Home'
import Login from '@/views/Login'
import Register from '@/views/Register'
import Ask from '@/views/Ask'
import Question from '@/views/Question'
import Notification from '@/views/Notification'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

Vue.use(Router)
Vue.use(Vuex)
Vue.use(mavonEditor)

export default new Router({
  routes: [
    { path: '/', redirect: '/home' },
    { path: '/home', name: 'Home', component: Home },
    { path: '/login', name: 'Login', component: Login },
    { path: '/register', name: 'Register', component: Register },
    { path: '/ask', name: 'Ask', component: Ask },
    { path: '/questions/:id', name: 'Question', component: Question},
    { path: '/notifications', name : 'Notification', component: Notification}
  ]
})
