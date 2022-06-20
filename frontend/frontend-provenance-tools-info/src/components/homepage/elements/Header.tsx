import React from 'react';
import {
  Link
} from "react-router-dom";

function Header() {
  return (
    <div className="Header">
        <div className="logo">
            Provenance Tools
        </div>
        {/* <div className="links">
          <Link to="/" className='text-link'>Selected Work</Link>
          <Link to="/about" className='text-link'>About</Link>
          <Link to="/misc" className='text-link'>Misc</Link>
        </div> */}
    </div>
  );
}

export default Header;
