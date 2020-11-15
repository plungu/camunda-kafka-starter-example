var React = require('react');
var {Link, IndexLink} = require('react-router');
const Nav = require('Nav');

var MarketingBar = React.createClass({
  render: function(){
    return (
      <div>
        <div className="title-bar" data-responsive-toggle="realEstateMenu" data-hide-for="small">
            <button className="menu-icon" type="button" data-toggle></button>
            <div className="title-bar-title">Menu</div>
        </div>

        <div className="top-bar">
          <div className="top-bar-left">
            <ul className="menu">
              <li className="menu-text">
                <small>Service Request - Camunda - PoC</small>
              </li>
              <li className="menu-text">
                <medium>VERA</medium>
              </li>
            </ul>
          </div>

          <div className="small-4 large-6 columns">
              <div className="button-group">
                <IndexLink to="/" activeClassName="active" className="button round secondary medium" activeStyle={{fontWeight: 'bold'}}>Questionnaire</IndexLink>
                <Link to="/tasks" activeClassName="active" className="button radius secondary medium" activeStyle={{fontWeight: 'bold'}}>Review Tasks</Link>
              </div>
          </div>

          <div className="top-bar-right">
            <ul className="menu">
                <li><a href="#">My Account</a></li>
                <li><a className="button">Login</a></li>
            </ul>
          </div>
        </div>

        <div className="columns" style={{borderBottom: "1px solid white"}}></div>

      </div>
    );
  }
})

module.exports = MarketingBar;
