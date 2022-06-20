import React from 'react';
import 'App.css';
import ProvenanceLogs from 'components/homepage/elements/ProvenanceLogs'

type BodyProps = {
};

function Body(props: BodyProps) {
  return (
    <div className="Body">
        <ProvenanceLogs />
    </div>
  );
}

export default Body;
