import {useState, useEffect, React} from 'react';
import axios from 'axios';
import './App.css';
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";
import Logo from './icons/logoblockchain.png'

export default function UserPanel()  {

  axios.defaults.headers.post['Content-Type'] ='application/json';

  const [user, setUser] = useState([]);

  const navigate = useNavigate();

  const  [voteError, setVoteError] = useState({voteError: ""});
  const  [resultsError, setResultsError] = useState({resultsError: ""});

  const getTokenAndSetUser = () => {
    const token = localStorage.getItem("jwt_token")
    const decodedToken = jwtDecode(token)
    const decodedEmail = decodedToken.email
    const decodedUserName = decodedEmail.substring(0, decodedEmail.indexOf("@"));
    setUser(decodedUserName)
  }

  const logOut = e => {
    console.log("JESTEM")
    localStorage.setItem("jwt_token","")
    navigate('/');
  }

  const areResultsPublished = e => {
    const token = localStorage.getItem("jwt_token");
    const config = {
      headers: { 
        Authorization: `Bearer ${token}`,
      }
    }

    axios
    .get('http://localhost:8080/results/published', config)
    .then((response) => {
      console.log(response);
      setResultsError({resultsError: ""});
      navigate('/results');
    })
    .catch(err => {
      setResultsError({resultsError: "Results are not published yet!"});
    });
  }

  const canUserVote = e => {
    const token = localStorage.getItem("jwt_token");
    const config = {
      headers: { 
        Authorization: `Bearer ${token}`,
      }
    }

    axios
    .post('http://localhost:8080/canUserVote', token, config)
    .then((response) => {
      console.log(response);
      setVoteError({voteError: ""});
      navigate('/vote');
    })
    .catch(err => {
      setVoteError({voteError: "You are not allowed to vote!"});
    });
  }
  
  useEffect(() => {
    getTokenAndSetUser();
  }, []);

    return  (
      <body>
          <div className = "wrapUserPanel">
            <div>

            </div>
              <div className = "userPanelInfo">
                <h2 className = "logoheaderh2">
                  Hello, user <b>{user}</b>!
                </h2>
                <div className = "imagelogowrapperUserPanel">
                  <img src = {Logo}/>
                </div>
                <h3 className = "logoheaderh3">Voting has never been easier!</h3>
                <h3 className = "logoheaderh3">Just choose an option and check it out!</h3>
              </div>

              <div className = "userPanelGrid">

              <div className = "userPanelCell" onClick={e => canUserVote()}>
                  <h3 className = "logoheaderh3userCell">VOTE</h3>
                  <h3 className = "logoheaderh3">If you have rights to vote, you will be redirected to specified page.</h3>
                  <h3 className = "logoheaderh3error">{voteError.voteError}</h3>
                </div>

                <div className = "userPanelCell" onClick={e => areResultsPublished()}>
                  <h3 className = "logoheaderh3userCell">CHECK RESULTS</h3>
                  <h3 className = "logoheaderh3">The results can be checked only when they are officially published.</h3>
                  <h3 className = "logoheaderh3error">{resultsError.resultsError}</h3>
                </div>

                <div className = "userPanelCell">
                  <h3 className = "logoheaderh3userCell">MORE ABOUT</h3>
                  <h3 className = "logoheaderh3">Familiarize yourself with the system's rules.</h3>
                  <h3 className = "logoheaderh3">Find out why elections under this system are secure and secret.</h3>
                </div>

                <div className = "userPanelCell" onClick={e => logOut()}>
                  <h3 className = "logoheaderh3userCell">LOG OUT</h3>
                  <h3 className = "logoheaderh3">Thank you for using Blockchain Voting System.</h3>
                  <h3 className = "logoheaderh3">If you want to see the results, come back later.</h3>
                </div>


              </div>
            </div>
    </body>
    )
  };