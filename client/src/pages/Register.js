import {useState} from 'react';
import axios from 'axios';
import RegisterIcon from './icons/register.svg'
import Logo from './icons/logoblockchain.png'

export default function App()  {

  const  [email, setEmailValue] =  useState('');
  const  [password, setPasswordValue] =  useState('');
  const  [repeatedPassword, setRepeatedPasswordValue] =  useState('');
  const  [error, setError] = useState({error: ""});

	const handleEmailChange = (event) => {
		setEmailValue(event.target.value);
	};

  const handlePasswordChange = (event) => {
		setPasswordValue(event.target.value);
	};

  const handleRepeatedPasswordChange = (event) => {
		setRepeatedPasswordValue(event.target.value);
	};


  const handleSubmit = e => {
    e.preventDefault();

    const userData = {
      email,
      password,
      repeatedPassword
    }

    axios
      .post('http://localhost:8080/register', userData)
      .then(() => {
        setError({error: ""});
      })
      .catch(err => {
        if(err.response.status == 403){
          setError({error: "Passwords don't match or email is invalid."});
        } else if(err.response.status == 409){
          setError({error: "This email is already registered."});
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
                    <img className = "lock" width = "100" src = {RegisterIcon}/>
                  </div>
                  <br/>
                  <label>EMAIL:
                    <input  type="text" value={email} onChange={handleEmailChange} />
                  </label>
                  <label>LOGIN:
                    <input  type="text" value={password} onChange={handlePasswordChange} />
                  </label>
                  <label>PASSWORD:
                    <input  type="text" value={repeatedPassword} onChange={handleRepeatedPasswordChange} />
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