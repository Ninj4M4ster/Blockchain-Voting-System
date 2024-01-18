import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import Vote from './pages/Vote'
import Results from './pages/Results'
import UserPanel from './pages/UserPanel'
import AdminPanel from './pages/AdminPanel'


export default function App()  {
  return  (
    <BrowserRouter>
      <Routes>
        <Route index element = {<Login />} />
        <Route path = "/vote" element = {<Vote />} />
        <Route path = "/register" element = {<Register />} />
        <Route path = "/results" element = {<Results />} />
        <Route path = "/user" element = {<UserPanel />} />
        <Route path = "/admin" element = {<AdminPanel />} />
      </Routes>
    </BrowserRouter>
  )
};

