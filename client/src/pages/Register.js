import {useState} from 'react';
import axios from 'axios';
import './App.css';
import Lock from './lock.svg';
import RegisterIcon from './register.svg'
import Logo from './logoblockchain.png'

export default function App()  {

  const  [candidateId, setCandidateIdInputValue] =  useState('');
  const  [publicKey, setPublicKeyInputValue] =  useState('');
  const  [privateKeySignature, setPrivateKeySignatureInputValue] =  useState('');


	const handleCandidateIdChange = (event) => {
    console.log('e')
		setCandidateIdInputValue(event.target.value);
	};

  const handlePublicKeyChange = (event) => {
		setPublicKeyInputValue(event.target.value);
	};

  const handlePrivateKeySignatureChange = (event) => {
		setPrivateKeySignatureInputValue(event.target.value);
	};

  const handleSubmit = e => {
    e.preventDefault();

    const voteData = {
      candidateId,
      publicKey,
      privateKeySignature
    }

    axios
      .post('http://localhost:3001/vote', voteData)
      .then(() => console.log('Vote send.'))
      .catch(err => {
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
                    <img className = "lock" width = "100" src = {RegisterIcon}/>
                  </div>
                  <br/>
                  <label>EMAIL:
                    <input  type="text" value={candidateId} onChange={handleCandidateIdChange} />
                  </label>
                  <label>LOGIN:
                    <input  type="text" value={publicKey} onChange={handlePublicKeyChange} />
                  </label>
                  <label>PASSWORD:
                    <input  type="text" value={publicKey} onChange={handlePublicKeyChange} />
                  </label>
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