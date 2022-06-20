import React, { useEffect, useState } from 'react';
import $ from 'jquery'; 
import 'App.css';
import Auth from 'utils/http/Auth'
import { url } from 'inspector';
import { callbackify } from 'util';


// function ProvenanceLogs(props) {
//   return (
//     <div className="Body">
//         {/* <Intro />
//         <SelectedWork /> */}
//     </div>
//   );
// }

type MyProps = { };
type Error = {
  message: string
}
type MyState = {
  error: Error | null,
  isLoaded: boolean,
  items: Array<string>
};

type ProvenanceLogsData = {
  status: string,
  logs: Array<string>
};


// const useFetch = (url: URL) => {
//   const [data, setData] = useState(null);

//   // empty array as second argument equivalent to componentDidMount
//   useEffect(() => {
//     async function fetchData() {
//       const response = await fetch(url);
//       const json = await response.json();
//       setData(json);
//     }
//     fetchData();
//   }, [url]);

//   return data;
// };

// const LOGS_URL = new URL("http://provenanceboba01.ddns.net/api/v1/logs/latest/pio-mainnet-1");
// const result = useFetch(LOGS_URL);

class ProvenanceLogs extends React.Component<MyProps, MyState> {
  constructor(props: MyProps) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      items: []
    };
  }

  callback(logs: Array<string>) {
    this.setState({
      isLoaded: true,
      items: logs
    });
  }

  componentDidMount() {
    $.ajax({
      url: "http://provenanceboba01.ddns.net/api/v1/logs/latest/pio-mainnet-1",
      type: "GET",
      headers: {
        "Authorization": Auth.getAuthorizationHeader("TODO", "TODO"),
        "Access-Control-Allow-Origin": "http://localhost:3000/"
      },
      crossDomain: true,
      context: document.body,
      success: function(data: ProvenanceLogsData) {
        console.log(data);
        this.setState({
          isLoaded: true,
          // error: null,
          logs: data.logs
        });
      }//s.bind(this)
    }).done(function(result: object) {
      console.log("DONE")
      // $( this ).addClass( "done" );
      // (result) => {
    });
  }

  render() {
    const { error, isLoaded, items } = this.state;
    if (error) {
      return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
      return <div>Loading...</div>;
    } else {
      return (
        <ul>
          {items.map((item, i) => (
            <li key={i}>
              {item}
            </li>
          ))}
        </ul>
      );
    }
  }
}
export default ProvenanceLogs;
