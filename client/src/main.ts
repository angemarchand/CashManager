import { createApp } from 'vue'
import { createPinia } from 'pinia'

import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import piniaPluginPersistedstate from "pinia-plugin-persistedstate"
import App from './App.vue'
import router from './router'


const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
const vuetify = createVuetify({
  components,
  directives,
  theme: {
    defaultTheme: 'shopTheme',
    themes: {
      shopTheme: {
        dark: false,
        colors: {
          primary: '#d4fcc3',
          secondary: '#edff86',
          accent: '#F3C969',
          error: '#B00020',
          info: '#2196F3',
          success: '#4CAF50',
          warning: '#FB8C00',
          background: '#F5F5F5',
          surface: '#FFFFFF',
          buttonHover: '#FFF5B2',
          title: '#F3C969',
        },
      },
    },
  },
})
const app = createApp(App)
//mvn clean install

app.use(pinia)
app.use(vuetify)
app.use(router)

app.mount('#app')
