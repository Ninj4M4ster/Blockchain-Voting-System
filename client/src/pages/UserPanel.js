import {useState, useEffect, React} from 'react';
import axios from 'axios';
import './App.css';
import Chart from './MyChart'
import Logo from './logoblockchain.png'

export default function UserPanel()  {

    return  (
      <body>
          <div className = "wrapUserPanel">
            <div className="userPanelSpacer"></div>
          <div>
              <div className = "userPanelInfo">
                <h2 className = "logoheaderh2">
                  Hello, user <b>postgres</b>!
                </h2>
                <div className = "imagelogowrapperUserPanel">
                  <img src = {Logo}/>
                </div>
                <h3 className = "logoheaderh3">Voting has never been easier!</h3>
                <h3 className = "logoheaderh3">Just choose an option and check it out!</h3>
              </div>
              
              <div className = "userPanelVote">
                <h3 className = "logoheaderh3"></h3>
                <div className = "userPanelVoteButton">
                  <h3 className = "voteheaderh3">VOTE</h3>
                  <h3 className = "logoheaderh3">If you have rights to vote, you should be redirected to specified page.</h3>
                </div>
              </div>

              <div className = "userPanelGrid">

                <div className = "userPanelCell">
                  <h3 className = "logoheaderh3userCell">CHECK RESULTS</h3>
                  <h3 className = "logoheaderh3">The results can be checked only when they are officially published.</h3>
                  <h3 className = "logoheaderh3">You can now see the results.</h3>
                </div>

                <div className = "userPanelCell">
                  <h3 className = "logoheaderh3userCell">MORE ABOUT</h3>
                  <h3 className = "logoheaderh3">Familiarize yourself with the system's rules.</h3>
                  <h3 className = "logoheaderh3">Find out why elections under this system are secure and secret.</h3>
                </div>

                <div className = "userPanelCell">
                  <h3 className = "logoheaderh3userCell">LOG OUT</h3>
                  <h3 className = "logoheaderh3">Thank you for using Blockchain Voting System.</h3>
                  <h3 className = "logoheaderh3">If you want to see the results, come back later.</h3>
                </div>
              </div>
            </div>
          </div>
    </body>
    )
  };