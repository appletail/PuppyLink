import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'

import App from '@/app/App'
import '@/styles/index.css'
import '@/index.css'

const container = document.getElementById('root') as HTMLElement

createRoot(container).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
