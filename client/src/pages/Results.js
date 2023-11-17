import {useState, useEffect, React} from 'react';
import axios from 'axios';
import './App.css';
import Lock from './lock.svg';
import RegisterIcon from './register.svg'
import Logo from './logoblockchain.png'
import Chart from './MyChart';

export default function Results()  {

    return  (
      <body>
          <div className = "wrapchart">
            <div class = "head2">
              <a href="#" className = "heada">LOG OUT</a>
              <a href="#" className = "heada">CHOOSE ELECTIONS</a>
              <a href="#" className = "heada">MORE ABOUT VOTING SYSTEM</a>
            </div>
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