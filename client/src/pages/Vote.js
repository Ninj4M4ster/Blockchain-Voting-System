import {useState} from 'react';
import React, {Component} from 'react';
import axios from 'axios';
import './App.css';
import Candidates from './Candidates';

export default function Vote(){
  
	const  [candidateId, setCandidateIdInputValue] =  useState('');
  const  [publicKey, setPublicKeyInputValue] =  useState('');
  const  [privateKeySignature, setPrivateKeySignatureInputValue] =  useState('');
  const  [candidateShown, setCandidateShownValue] = useState(false)
  const  [error, setError] = useState({error: ""});

  const setCandidateHandler = () => {
		setCandidateShownValue(current => !current);
	};

	const handleCandidateIdChange = (event) => {
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

    const token = localStorage.getItem("jwt_token");
    const config = {
      headers: { 
        Authorization: `Bearer ${token}`,
      }
    }

    const voteData = {
      candidateId,
      publicKey,
      privateKeySignature
    }

    axios
      .post('http://localhost:8080/vote', voteData, config)
      .then(() => {
        setError({error: ""});
      })
      .catch(err => {
        console.log(err);
        setError({error: err.response.data});
        // if(err.response.status == 403){
        //   setError({error: "You don't have permissions to vote."});
        // } else{
        //   setError({error: "Error occured. Please try again later."});
        // }

      });
  }

  return  (
    <body className = "votebody">
      <div className = "wrap">
      <div class = "head" onClick = {setCandidateHandler}>
        <Candidates />
      </div>
      <div className = "container">
        <div id = "stylized">
          <form onSubmit={handleSubmit}>
          <h2 class = "formh2">VOTE FOR CANDIDATE</h2>
            <label>CANDIDATE ID:
              <input  type="text" value={candidateId} onChange={handleCandidateIdChange} />
              </label>
            <label>PUBLIC KEY:
              <input  type="text" value={publicKey} onChange={handlePublicKeyChange} />
            </label>
            <label>PRIVATE KEY SIGNATURE:
              <input  type="text" value={privateKeySignature} onChange={handlePrivateKeySignatureChange} />
            </label>
            <div className = "errorHeader">
              <h6 className = "errorh">{error.error}</h6>
            </div>
            <div className = "buttonwrapper">
            <button type="submit">VOTE</button>
            </div>

          </form>
        </div>
      </div>
      <footer className = "menu">
        <a href="#">SEE CANDIDATES</a>
        <a href="#">CHOOSE ELECTION</a>
        <a href="#">MORE ABOUT VOTING SYSTEM</a>
        <a href="#">LOG OUT</a>
      </footer>
      </div>
    </body>
  )
};

