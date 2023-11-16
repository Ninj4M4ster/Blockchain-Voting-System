import {useState} from 'react';
import axios from 'axios';
import './App.css';
import Lock from './lock.svg';
import Logo from './logoblockchain.png'

export default function App()  {

  const  [email, setEmailValue] =  useState('');
  const  [password, setPasswordValue] =  useState('');


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
      .then(() => console.log('Data sent.'))
      .catch(err => {
        console.log('Error detected');
        console.error(err);
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