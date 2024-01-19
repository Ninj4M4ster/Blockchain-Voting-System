import {useState, useEffect} from 'react';
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import Lock from './icons/lock.svg';
import Logo from './icons/logoblockchain.png'
import './App.css'

export default function App()  {

  const navigate = useNavigate();

  const  [email, setEmailValue] =  useState('');
  const  [password, setPasswordValue] =  useState('');
  const  [error, setError] = useState({error: ""});

	const handleEmailChange = (event) => {
		setEmailValue(event.target.value);
	};

  const handlePasswordChange = (event) => {
		setPasswordValue(event.target.value);
	};

  const handleSubmit = e => {
    e.preventDefault();

    const userData = {
      email,
      password
    }

    axios
      .post('http://localhost:8080/login', userData)
      .then((response) => {
        console.log(response.data);
        localStorage.setItem("jwt_token", response.data.access_token)
        setError({error: ""});
        navigate('/user');
      })
      .catch(err => {
        if(err.response.status == 401){
          setError({error: "Email or password is incorrect. Please check your credentials."});
        } else{
          setError({error: "Error occured. Please try again later."});
        }
      });
  }

    return  (
      <body>
          <div className = "wrapplogin">
              <div className = "wraplogin">
                  <div className = "logo">
                      <div className = "empty">
                      </div>
                      <div className = "imagelogowrapper">
                          <img src = {Logo}/>
                      </div>
                      <div className = "logotextwrapper">
                          <h2 className = "logoheaderh2">BLOCKCHAIN VOTING SYSTEM</h2>
                          <h3 className = "logoheaderh3">Secure and transparent electoral system based on blockchain technology</h3>
                          <h3 className = "logoheaderh3">Organize your own secret elections!</h3>
                      </div>
                  </div>
                  <div className = "containerlogin">
                      <div id = "stylized">
                          <form onSubmit={handleSubmit}>
                              <div className = "lockwrapper">
                                  <img className = "lock" width = "100" src = {Lock}/>
                              </div>
                              <br/>
                              <label>LOGIN:
                              <input  type="text" value={email} onChange={handleEmailChange} />
                              </label>
                              <label>PASSWORD:
                              <input  type="text" value={password} onChange={handlePasswordChange} />
                              </label>
                              <div className = "errorHeader">
                                  <h6 className = "errorh">{error.error}</h6>
                              </div>
                              <div className = "buttonwrapper">
                                  <button type="submit">LOGIN</button>
                              </div>
                          </form>
                      </div>
                      <footer className = "menu">
                          <a href="#">REGISTER</a>
                          <a href="#">MORE ABOUT VOTING SYSTEM</a>
                      </footer>
                  </div>
              </div>
          </div>
      </body>
    )
  };

  function NoErrorHeader(){
    return(
      <h6 className = "errorh"></h6>
    )
  };

  function ErrorHeader(){
    return(
      <h6 className = "errorh">Error</h6>
    )
  };