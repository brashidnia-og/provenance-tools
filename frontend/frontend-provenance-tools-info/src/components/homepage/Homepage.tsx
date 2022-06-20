import React from 'react';
import 'App.css';
import Header from 'components/homepage/elements/Header';
import Body from 'components/homepage/elements/Body';

type HomepageProps = {
};
function Homepage(props: HomepageProps) {
  return (
    <div className="Homepage">
        <Header />
        <Body />
    </div>
  );
}

export default Homepage;
