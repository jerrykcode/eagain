// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import Axios from 'axios'
import ViewUI from 'view-design'
import 'view-design/dist/styles/iview.css'
import VueHighlightJS from 'highlight.js';

Vue.use(VueHighlightJS)
Vue.directive('highlight', (el) => {
  let blocks = el.querySelectorAll('pre code')
  blocks.forEach((block) => {
    VueHighlightJS.highlightBlock(block)
  })
})

Vue.config.productionTip = false
Vue.use(ViewUI)

Vue.prototype.$http=axios
Vue.config.productionTip = false
Axios.defaults.baseURL = '/api'
Axios.defaults.headers.post['Content-Type']='application/json'

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
