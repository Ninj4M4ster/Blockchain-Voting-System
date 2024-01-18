import {useState, useEffect, React} from 'react';
import axios from 'axios';
import './App.css';
import Chart from './MyChart'
import Logo from './logoblockchain.png'

export default function AdminPanel()  {

    const  [email, setEmailValue] =  useState('');
    const  [password, setPasswordValue] =  useState('');
    const  [error, setError] = useState({error: ""});
  
      const handleEmailChange = (event) => {
          setEmailValue(event.target.value);
      };
  
    const handlePasswordChange = (event) => {
          setPasswordValue(event.target.value);
      };
  
    const handleSubmit = e => {
      e.preventDefault();
  
      const userData = {
        email,
        password
      }
  
      axios
        .post('http://localhost:8080/login', userData)
        .then(() => {
          setError({error: ""});
        })
        .catch(err => {
          if(err.response.status == 401){
            setError({error: "Email or password is incorrect. Please check your credentials."});
          } else{
            setError({error: "Error occured. Please try again later."});
          }
  
        });
    }

    return  (
        <body>
        <div className = "wrapUserPanel">
           <div>
           </div>
           <div className = "userPanelInfo">
              <h2 className = "logoheaderh2">
                 Hello, <b>admin</b>!
              </h2>
           </div>
           <div className = "adminPanelGrid">
              <div className = "adminPanelColumn">
                 <div className = "adminPanelCell">
                    <h3 className="logoheaderh3admin">Add candidate</h3>
                    <div className = "addcandidate">
                       <form onSubmit={handleSubmit} className = "addCandidate">
                          <br/>
                          <label>CANDIDATE NAME:
                          <input  type="text" value={email} onChange={handleEmailChange} />
                          </label>
                          <label>CANDIDATE DESCRIPTION:
                          <input  type="text" value={password} onChange={handlePasswordChange} />
                          </label>
                          <div className = "errorHeaderAdmin">
                             <h6 className = "errorh">{error.error}</h6>
                          </div>
                          <div className = "buttonwrapperaddCandidate">
                             <button type="submit">ADD</button>
                          </div>
                       </form>
                    </div>
                 </div>
                 <div className = "adminPanelCell">
                    <h3 className="logoheaderh3admin">Grant voting privileges</h3>
                    <div className = "addcandidate">
                       <form onSubmit={handleSubmit} className = "addCandidate">
                          <br/>
                          <label>EMAIL:
                          <input  type="text" value={email} onChange={handleEmailChange} />
                          </label>
                          <div className = "errorHeaderAdmin">
                             <h6 className = "errorh">{error.error}</h6>
                          </div>
                          <div className = "buttonwrapperaddCandidate">
                             <button type="submit">GRANT</button>
                          </div>
                       </form>
                    </div>
                 </div>
              </div>
              <div className = "adminPanelGrid2">
                <div className = "adminPanelCell">
                    <h3 className="logoheaderh3admin">See candidates</h3>
                    <div className = "addcandidate">
                        <form onSubmit={handleSubmit} className = "addCandidate">
                        <br/>
                        <div className = "buttonwrapperaddCandidate">
                            <button type="submit">REFRESH</button>
                        </div>
                        <div className = "errorHeaderAdmin">
                            <h6 className = "errorh">{error.error}</h6>
                        </div>
                        </form>
                    </div>
                </div>
                <div className = "adminPanelCell">
                    <h3 className="logoheaderh3admin">Publish results</h3>
                    <div className = "addcandidate">
                        <form onSubmit={handleSubmit} className = "addCandidate">
                        <br/>
                        <div className = "buttonwrapperaddCandidate">
                            <button type="submit">REFRESH</button>
                        </div>
                        <div className = "errorHeaderAdmin">
                            <h6 className = "errorh">{error.error}</h6>
                        </div>
                        </form>
                    </div>
                </div>

              </div>
              
              <div className = "adminPanelCell">
                 <h3 className="logoheaderh3admin">Check results</h3>
                 <div className = "addcandidate">
                    <form onSubmit={handleSubmit} className = "addCandidate">
                       <br/>
                       <div className = "buttonwrapperaddCandidate">
                          <button type="submit">REFRESH</button>
                       </div>
                       <div className = "errorHeaderAdmin">
                          <h6 className = "errorh">{error.error}</h6>
                       </div>
                    </form>
                 </div>
              </div>
           </div>
        </div>
     </body>
    )
  };