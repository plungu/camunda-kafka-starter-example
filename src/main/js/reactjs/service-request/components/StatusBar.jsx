var React = require('react');
var {Link, IndexLink} = require('react-router');
const Nav = require('Nav');

var MarketingBar = React.createClass({
  render: function(){
    return (
      <div>

          <div className="top-bar">
              <div className="top-bar-left">
                  <ul className="menu">
                      <li className="menu-text">
                          <large><span style={{color: "#2199e8"}}>Status</span></large>
                      </li>
                  </ul>
              </div>

              <div className="top-bar-left">
                  <div className="menu small-4 large-4 columns text-center" style={{borderBottom: "1px dashed"}}>
                    <large>34</large>
                  </div>
                  <div className="menu small-4 large-4 columns text-center" style={{borderBottom: "1px dashed white"}}>
                    <large>6</large>
                  </div>
                  <div className="menu small-4 large-4 columns text-center" style={{borderBottom: "1px dashed"}}>
                      <large>12</large>
                  </div>
                  <div className="menu small-4 large-4 columns text-center">
                    <small>Tasks</small>
                  </div>
                  <div className="menu small-4 large-4 columns text-center">
                      <small>Overdue</small>
                  </div>
                  <div className="menu small-4 large-4 columns text-center">
                      <small>Groups</small>
                  </div>
              </div>
          </div>

        <div className="columns" style={{borderBottom: "1px solid white"}}></div>

      </div>
    );
  }
})

module.exports = MarketingBar;
