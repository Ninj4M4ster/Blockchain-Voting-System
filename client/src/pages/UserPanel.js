import {useState, useEffect, React} from 'react';
import axios from 'axios';
import './App.css';
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";
import Logo from './icons/logoblockchain.png'

export default function UserPanel()  {

  const [user, setUser] = useState([]);

  const navigate = useNavigate();

  const getTokenAndSetUser = () => {
    const token = localStorage.getItem("jwt_token")
    const decodedToken = jwtDecode(token)
    console.log(decodedToken.email)
    setUser(decodedToken.email)
  }

  const logOut = e => {
    console.log("JESTEM")
    localStorage.setItem("jwt_token","")
    navigate('/');
  }

  const canUserVote = e => {
    const token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBbElGRkJBMTdJNTM4NGhpNXJ0RzZ6bm0yUGpYWl84cjRtREpreXcxTVdrIn0.eyJleHAiOjE3MDU2ODMwMTAsImlhdCI6MTcwNTY4MjcxMCwianRpIjoiZjRjN2M4NzItYmYyOS00MjYzLWEwYmYtYzgwYmViNTE4OGEyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDAwL3JlYWxtcy9teXJlYWxtIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjI2M2FmNTBjLTA1OTQtNDdkYy04MjVmLTMyZTZkN2Q2MjZjOSIsInR5cCI6IkJlYXJlciIsImF6cCI6Im15Y2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6ImViMTIwMTZiLWFlZjItNGMxZC1hNmFhLTBjZjAyNTYyNzgxZSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbXlyZWFsbSIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJVU0VSIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwic2lkIjoiZWIxMjAxNmItYWVmMi00YzFkLWE2YWEtMGNmMDI1NjI3ODFlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImR1bW15dXNlckBnbWFpbC5jb20iLCJnaXZlbl9uYW1lIjoiIiwiZmFtaWx5X25hbWUiOiIiLCJlbWFpbCI6ImR1bW15dXNlckBnbWFpbC5jb20ifQ.j0xZYEx39s9eoDyY83e8yvQ5BJs4KHxUTWayYpWi7RUomyl34kPOW62CowPHnEc5rSBQdKOk7w_74NL5kGhx32b45yuQ77C5dFNThfG6x6sYC_2CkjlpZt6h6cv2LEyB5PEDEW0XhBPrKz2paFeGs_zMC6ulCLX4OSDdJz0XgRhEEwueY8IJ3lh5ywKu02dWmUvoROIVYht8AkLgr6vb8FF16LHyPHH2KrlWiXs7c9icDM6L7ZPuUVCgaA-GZewoJkMn1OdKdxDaTsdeVjGXSc1kT2rxS0WSMp-eO9PmFmaFx4CnqTM-tCBHM-puq9fqaCC7es24rTcnqfkucJamlQ"
    //var token = localStorage.getItem("jwt_token");
    console.log(token)
    const userTokenData = {token}
    const config = {
      headers: { 'Authorization': `Bearer ${token}` }
    };

    axios
    .get('http://localhost:8080/results', config)
    .then((response) => {
      console.log(response);
    })
    .catch(err => {
      console.log("Error");
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
                  <h3 className = "logoheaderh3error">You are not allowed to vote!</h3>
                </div>

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