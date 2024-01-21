import {useState, useEffect, React} from 'react';
import axios from 'axios';
import './App.css';

export default function AdminPanel()  {

    axios.defaults.headers.post['Content-Type'] ='application/json';

    const  [candidateName, setCandidateName] = useState({candidateName: ""});
    const  [candidateDescription, setCandidateDescription] = useState({candidateDescription: ""});
    const  [grantVoteRightsEmail, setGrantVoteRightsEmail] = useState({email: ""});

    const addRightsToVote = e => {
        console.log("elo");
        const token = localStorage.getItem("jwt_token");
        const config = {
          headers: { 
            Authorization: `Bearer ${token}`,
          }
        }
    
        axios
        .post('http://localhost:8080/voterights', grantVoteRightsEmail, config)
        .then(result => {
            console.log(result);
        })
        .catch(err => {
            console.log(err);
        });
      }
  
      const handleCandidateNameChange = (event) => {
          setCandidateName(event.target.value);
      };
  
    const handleCandidateDescriptionChange = (event) => {
          setCandidateDescription(event.target.value);
      };

    const handleGrantVoteRightsChange = (event) => {
        setGrantVoteRightsEmail(event.target.value);
    };
  
    const handleSubmit = e => {
      e.preventDefault();

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
                                    <input  type="text" value={candidateName.candidateName} onChange={handleCandidateNameChange} />
                                    </label>
                                    <label>CANDIDATE DESCRIPTION:
                                    <input  type="text" value={candidateDescription.candidateDescription} onChange={handleCandidateDescriptionChange} />
                                    </label>
                                    <div className = "errorHeaderAdmin">
                                        <h6 className = "errorh"></h6>
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
                                <form onSubmit={addRightsToVote} className = "addCandidate">
                                    <br/>
                                    <label>EMAIL:
                                    <input  type="text" value={grantVoteRightsEmail.email} onChange={handleGrantVoteRightsChange} />
                                    </label>
                                    <div className = "errorHeaderAdmin">
                                        <h6 className = "errorh"></h6>
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
                            <div className = "addCandidate2">
                                <form onSubmit={handleSubmit} className = "addCandidate">
                                    <br/>
                                    <div className = "buttonwrapperaddCandidate">
                                        <button type="submit">REFRESH</button>
                                    </div>
                                    <div className = "errorHeaderAdmin">
                                        <h6 className = "errorh"></h6>
                                    </div>
                                </form>
                                <div className = "candidateAdminWrapper">
                                    <div className = "candidateAdminCell">
                                        <div className = "candidateGridWrapper">
                                            <div className="candidateName">
                                                Tom
                                            </div>
                                            <div className = "candidateId">
                                                ID: <b>1</b>
                                            </div>
                                        </div>
                                        <div className = "candidateDescription">
                                            Alice would make an excellent president due to her strong leadership skills and ability to inspire others. Her extensive experience in public service and dedication to addressing pressing societal issues make her a capable candidate.
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell">
                                        <div className = "candidateGridWrapper">
                                            <div className="candidateName">
                                                Tom
                                            </div>
                                            <div className = "candidateId">
                                                ID: <b>1</b>
                                            </div>
                                        </div>
                                        <div className = "candidateDescription">
                                            Alice would make an excellent president due to her strong leadership skills and ability to inspire others. Her extensive experience in public service and dedication to addressing pressing societal issues make her a capable candidate.
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell">
                                        <div className = "candidateGridWrapper">
                                            <div className="candidateName">
                                                Tom
                                            </div>
                                            <div className = "candidateId">
                                                ID: <b>1</b>
                                            </div>
                                        </div>
                                        <div className = "candidateDescription">
                                            Alice would make an excellent president due to her strong leadership skills and ability to inspire others. Her extensive experience in public service and dedication to addressing pressing societal issues make her a capable candidate.
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell">
                                        <div className = "candidateGridWrapper">
                                            <div className="candidateName">
                                                Tom
                                            </div>
                                            <div className = "candidateId">
                                                ID: <b>1</b>
                                            </div>
                                        </div>
                                        <div className = "candidateDescription">
                                            Alice would make an excellent president due to her strong leadership skills and ability to inspire others. Her extensive experience in public service and dedication to addressing pressing societal issues make her a capable candidate.
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell">
                                        <div className = "candidateGridWrapper">
                                            <div className="candidateName">
                                                Tom
                                            </div>
                                            <div className = "candidateId">
                                                ID: <b>1</b>
                                            </div>
                                        </div>
                                        <div className = "candidateDescription">
                                            Alice would make an excellent president due to her strong leadership skills and ability to inspire others. Her extensive experience in public service and dedication to addressing pressing societal issues make her a capable candidate.
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell">
                                        <div className = "candidateGridWrapper">
                                            <div className="candidateName">
                                                Tom
                                            </div>
                                            <div className = "candidateId">
                                                ID: <b>1</b>
                                            </div>
                                        </div>
                                        <div className = "candidateDescription">
                                            Alice would make an excellent president due to her strong leadership skills and ability to inspire others. Her extensive experience in public service and dedication to addressing pressing societal issues make her a capable candidate.
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className = "adminPanelCell">
                            <h3 className="logoheaderh3admin">Publish results</h3>
                            <div className = "addcandidate">
                                <form onSubmit={handleSubmit} className = "addCandidate">
                                    <br/>
                                    <div className = "buttonwrapperaddCandidate">
                                        <button type="submit">PUBLISH/UNPUBLISH</button>
                                    </div>
                                    <div className = "errorHeaderAdmin">
                                        <h6 className = "errorh"></h6>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div className = "adminPanelGrid2">
                        <div className = "adminPanelCell">
                            <h3 className="logoheaderh3admin">Check results</h3>
                            <div className = "addcandidate">
                                <form onSubmit={handleSubmit} className = "addCandidate">
                                    <br/>
                                    <div className = "buttonwrapperaddCandidate">
                                        <button type="submit">REFRESH</button>
                                    </div>
                                    <div className = "errorHeaderAdmin">
                                        <h6 className = "errorh"></h6>
                                    </div>
                                </form>
                                <div className = "candidateAdminResultsWrapper">
                                    <div className = "candidateAdminCell2">
                                        <div className = "candidateGridResultsWrapper">
                                            <div className="candidateResultsName">
                                                Tom
                                            </div>
                                            <div className = "candidateResultsId">
                                                ID: <b>1</b>
                                            </div>
                                            <div className = "candidateResultsCount">
                                                VOTES: <b>200</b>
                                            </div>
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell2">
                                        <div className = "candidateGridResultsWrapper">
                                            <div className="candidateResultsName">
                                                Tom
                                            </div>
                                            <div className = "candidateResultsId">
                                                ID: <b>1</b>
                                            </div>
                                            <div className = "candidateResultsCount">
                                                VOTES: <b>200</b>
                                            </div>
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell2">
                                        <div className = "candidateGridResultsWrapper">
                                            <div className="candidateResultsName">
                                                Tom
                                            </div>
                                            <div className = "candidateResultsId">
                                                ID: <b>1</b>
                                            </div>
                                            <div className = "candidateResultsCount">
                                                VOTES: <b>200</b>
                                            </div>
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell2">
                                        <div className = "candidateGridResultsWrapper">
                                            <div className="candidateResultsName">
                                                Tom
                                            </div>
                                            <div className = "candidateResultsId">
                                                ID: <b>1</b>
                                            </div>
                                            <div className = "candidateResultsCount">
                                                VOTES: <b>200</b>
                                            </div>
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell2">
                                        <div className = "candidateGridResultsWrapper">
                                            <div className="candidateResultsName">
                                                Tom
                                            </div>
                                            <div className = "candidateResultsId">
                                                ID: <b>1</b>
                                            </div>
                                            <div className = "candidateResultsCount">
                                                VOTES: <b>200</b>
                                            </div>
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell2">
                                        <div className = "candidateGridResultsWrapper">
                                            <div className="candidateResultsName">
                                                Tom
                                            </div>
                                            <div className = "candidateResultsId">
                                                ID: <b>1</b>
                                            </div>
                                            <div className = "candidateResultsCount">
                                                VOTES: <b>200</b>
                                            </div>
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell2">
                                        <div className = "candidateGridResultsWrapper">
                                            <div className="candidateResultsName">
                                                Tom
                                            </div>
                                            <div className = "candidateResultsId">
                                                ID: <b>1</b>
                                            </div>
                                            <div className = "candidateResultsCount">
                                                VOTES: <b>200</b>
                                            </div>
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell2">
                                        <div className = "candidateGridResultsWrapper">
                                            <div className="candidateResultsName">
                                                Tom
                                            </div>
                                            <div className = "candidateResultsId">
                                                ID: <b>1</b>
                                            </div>
                                            <div className = "candidateResultsCount">
                                                VOTES: <b>200</b>
                                            </div>
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell2">
                                        <div className = "candidateGridResultsWrapper">
                                            <div className="candidateResultsName">
                                                Tom
                                            </div>
                                            <div className = "candidateResultsId">
                                                ID: <b>1</b>
                                            </div>
                                            <div className = "candidateResultsCount">
                                                VOTES: <b>200</b>
                                            </div>
                                        </div>
                                    </div>
                                    <div className = "candidateAdminCell2">
                                        <div className = "candidateGridResultsWrapper">
                                            <div className="candidateResultsName">
                                                Tom
                                            </div>
                                            <div className = "candidateResultsId">
                                                ID: <b>1</b>
                                            </div>
                                            <div className = "candidateResultsCount">
                                                VOTES: <b>200</b>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className = "adminPanelCell">
                            <h3 className="logoheaderh3admin">Log out</h3>
                            <div className = "addcandidate">
                                <form onSubmit={handleSubmit} className = "addCandidate">
                                    <br/>
                                    <div className = "buttonwrapperaddCandidate">
                                        <button type="submit">LOG OUT</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>
    )
  };