import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({  
  server: {
    host: '0.0.0.0',
    hmr: {
      clientPort: 3001,
    },
    port: 3000,
    watch: {
      usePolling: true,
    },
    proxy: {
      '/api': {
        target: "http://be-organize-matches:8081",
        changeOrigin: true,
        secure: false,
        ws: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      }
    }
  },
  plugins: [react()]
})
