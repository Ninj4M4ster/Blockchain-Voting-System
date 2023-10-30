import {useState} from 'react';
import axios from 'axios';
import './App.css';
 
export default function  ControlledComponent()  {
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
    console.log('elo');
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
      <div className = "container">
        <div id = "stylized">
          <form onSubmit={handleSubmit}>
            <label>Candidate ID:
              <input  type="text"  value={candidateId} onChange={handleCandidateIdChange} />
            </label>
            <label>Public key:
              <input  type="text"  value={publicKey} onChange={handlePublicKeyChange} />
            </label>
            <label>Private key signature:
              <input  type="text"  value={privateKeySignature} onChange={handlePrivateKeySignatureChange} />
            </label>
            <button type="submit">VOTE</button>
          </form>
        </div>
      </div>
    </body>
  )
};