import {useState, useEffect, React} from 'react';
import axios from 'axios';
import './App.css';
import Chart from './MyChart'
import { useNavigate } from "react-router-dom";

export default function Results()  {

  const [data, setData] = useState([]);

  const chartData = []


  const navigate = useNavigate();

  const backToMenu = e => {
    navigate('/user');
  }

    const getData = () => {

      const token = localStorage.getItem("jwt_token");

      const config = {
        headers: { 
          Authorization: `Bearer ${token}`,
        }
      }

      axios.get("http://localhost:8080/results", config)
      .then((data) => {
          setData(data.data)
      })
      .catch(err => {
        localStorage.setItem("jwt_token","")
        navigate("/")
      });
    }

    useEffect(() => {
        getData();
    }, []);

    if(data.length > 0){
      data.forEach(element => {
        chartData.push({name: element.candidateName, value: element.voteCount});
      });
    }

    if(data.length > 0){
      var result = data.map(item => 
        <div className = "candidateresult">
          <h3 className="resultsh3">{item.candidateName}</h3>
          <h4 className="resultsh4">CANDIDATE ID: {item.candidateId}</h4>
          <h2 className="resultsh2">VOTES: {item.voteCount}</h2>
        </div>
      )
    }

    return  (
      <body>
          <div className = "wrapchart">
            <div className = "wrapChartAndMenu">
              <div className = "chart">
                <Chart chartData = {chartData}/>
              </div>
            </div>
            <div className = "wrapContainerResults">
              <div className = "containerresults">
                {result}
              </div>
              <div className = "chartBackButton" onClick={e => backToMenu()}>
                <h4 className = "chartBackButtonText">BACK TO MENU</h4>
              </div>
            </div>
          </div>
    </body>
    )
  };