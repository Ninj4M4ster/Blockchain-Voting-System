import {useState, useEffect, React} from 'react';
import axios from 'axios';
import './App.css';
import Lock from './lock.svg';
import RegisterIcon from './register.svg'
import Logo from './logoblockchain.png'
import Chart from './MyChart'

export default function Results()  {

  const [data, setData] = useState([]);
 
    const getData = () => {
      axios.get("http://localhost:8080/results")
      .then((data) => {
          setData(data.data)
      });
    }

    const chartData = []
    data.forEach(element => {
      chartData.push({name: element.candidateName, value: element.voteCount});
    });
       
    useEffect(() => {
        getData();
    }, []);

    var result = data.map(item => 
      <div className = "candidateresult">
        <h3 className="resultsh3">{item.candidateName}</h3>
        <h4 className="resultsh4">CANDIDATE ID: {item.candidateId}</h4>
        <h2 className="resultsh2">VOTES: {item.voteCount}</h2>
      </div>
    )

    return  (
      <body>
          <div className = "wrapchart">
            <div class = "head2">
              <a href="#" className = "heada">LOG OUT</a>
              <a href="#" className = "heada">CHOOSE ELECTIONS</a>
              <a href="#" className = "heada">MORE ABOUT VOTING SYSTEM</a>
            </div>
            <div className = "chart">
              <Chart chartData = {chartData}/>
            </div>
            <div className = "containerresults">
              {result}
            </div>
          </div>
    </body>
    )
  };