var React = require('react');
var {Link, IndexLink} = require('react-router');

var Nav = React.createClass({
  render: function(){
    return (
      <nav className="columns is-at-top is-stuck">
        <div className="">
          <ul className="menu vertical">
            <li>
              <IndexLink to="/" activeClassName="active" className="hollow button" activeStyle={{fontWeight: 'bold'}}>Renewals</IndexLink>
            </li>
            <li>
              <Link to="/tenants" activeClassName="active" className="hollow button" activeStyle={{fontWeight: 'bold'}}>Tenants</Link>
            </li>
              <li>
              <Link to="/cannedMessages" activeClassName="active" className="hollow button" activeStyle={{fontWeight: 'bold'}}>Canned Messages</Link>
            </li>
          </ul>
          <br/>
          <ul className="menu vertical">
            <li>
              <Link to="/import" activeClassName="active" className="hollow button" activeStyle={{fontWeight: 'bold'}}>Import</Link>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
})

module.exports = Nav;
