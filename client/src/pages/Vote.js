import {useState} from 'react';
import axios from 'axios';
import './App.css';

export default function Vote(){
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
      <div className = "wrap">
      <div class = "head">
          <div className = "candidate">
            <div className = "candidatewrapper">
              <h4>ALICE BROWN</h4>
              <h5>CANDIDATE ID: 1</h5>
            </div>
            <div class = "candidatedescriptiontext">            
            <span>Alice would make an excellent president due to her strong leadership skills and ability to inspire others. Her extensive experience in public service and dedication to addressing pressing societal issues make her a capable candidate. </span>
            </div>
          </div>
          <div className = "candidate">
          <div className = "candidatewrapper">
              <h4>TOM HANKS</h4>
              <h5>CANDIDATE ID: 2</h5>
            </div>
            <div class = "candidatedescriptiontext">            
            <span>Tom will be a good president due to his extensive experience in public service, having served as a dedicated senator for over two decades, showcasing his deep understanding of legislative processes and policy development.</span>            
            </div>
          </div>

          <div className = "candidate">
          <div className = "candidatewrapper">
              <h4>KATHY GARCIA</h4>
              <h5>CANDIDATE ID: 3</h5>
            </div>
            <div class = "candidatedescriptiontext">            
            <span>Kathy possesses exceptional leadership qualities, exemplified by her ability to inspire and unite people from diverse backgrounds toward a common goal. </span>
            </div>
          </div>
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