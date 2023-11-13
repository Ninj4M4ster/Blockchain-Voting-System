import {useState} from 'react';
import axios from 'axios';
import './App.css';
import Lock from './lock.svg';
import RegisterIcon from './register.svg'
import Logo from './logoblockchain.png'
import Chart from './MyChart';

export default function Results()  {

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
          <div className = "wrapchart">
            <div className = "chart">
              <Chart/>
            </div>
            <div className = "containerresults">
                <div className = "candidateresult">
                  <h3 className="resultsh3">CATHY GARCIA</h3>
                  <h4 className="resultsh4">Candidate ID: 3</h4>
                  <h2 className="resultsh2">VOTES: 150</h2>
                </div>
                <div className = "candidateresult">
                  <h3 className="resultsh3">TOM HANKS</h3>
                  <h4 className="resultsh4">Candidate ID: 2</h4>
                  <h2 className="resultsh2">VOTES: 100</h2>
                </div>
                <div className = "candidateresult">
                  <h3 className="resultsh3">ALICE BROWN</h3>
                  <h4 className="resultsh4">Candidate ID: 1</h4>
                  <h2 className="resultsh2">VOTES: 50</h2>
                </div>
            </div>
          </div>
    </body>
    )
  };