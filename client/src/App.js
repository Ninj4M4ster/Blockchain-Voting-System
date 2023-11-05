import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import Results from './pages/Results'
import Vote from './pages/Vote'

 
export default function App()  {
  return  (
    <BrowserRouter>
      <Routes>
        <Route index element = {<Login />} />
        <Route path = "/vote" element = {<Vote />} />
        <Route path = "/register" element = {<Register />} />
        <Route path = "/results" element = {<Results />} />
      </Routes>
    </BrowserRouter>
  )
};

