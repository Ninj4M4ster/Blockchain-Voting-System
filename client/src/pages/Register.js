import {useState} from 'react';
import axios from 'axios';
import RegisterIcon from './icons/register.svg'
import Logo from './icons/logoblockchain.png'
import { useNavigate } from "react-router-dom";

export default function App()  {

  const  [email, setEmailValue] =  useState('');
  const  [password, setPasswordValue] =  useState('');
  const  [publicKey, setPublicKeyValue] =  useState('');
  const  [error, setError] = useState({error: ""});
  const navigate = useNavigate();

	const handleEmailChange = (event) => {
		setEmailValue(event.target.value);
	};

  const handlePasswordChange = (event) => {
		setPasswordValue(event.target.value);
	};

  const handlePublicKeyChange = (event) => {
		setPublicKeyValue(event.target.value);
	};

  const handleSubmit = e => {
    e.preventDefault();

    const userData = {
      email,
      password,
      publicKey
    }

    axios
      .post('http://localhost:8080/register', userData)
      .then(() => {
        setError({error: ""});
        navigate("/")
      })
      .catch(err => {
        setError({error: err.response.data});
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
                    <img className = "lock" width = "100" src = {RegisterIcon}/>
                  </div>
                  <br/>
                  <label>EMAIL:
                    <input  type="text" value={email} onChange={handleEmailChange} />
                  </label>
                  <label>PASSWORD:
                    <input  type="password" value={password} onChange={handlePasswordChange} />
                  </label>
                  <label>PUBLIC KEY:
                    <input  type="text" value={publicKey} onChange={handlePublicKeyChange} />
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
              <a href="#">LOG IN</a>
            <a href="#">MORE ABOUT VOTING SYSTEM</a>
          </footer>
            </div>
          </div>
        </div>
    </body>
    )
  };