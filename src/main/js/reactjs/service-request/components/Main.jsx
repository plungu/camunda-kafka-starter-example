'use strict';

// tag::vars[]
const React = require('react');

const Nav = require('Nav');
const FilterBar = require('FilterBar');
var MarketingBar = require('MarketingBar');
var FooterBar = require('FooterBar');

// tag::renewal[]
class Main extends React.Component{
    render() {
        return (
           <div>
            <MarketingBar/>
            <Nav/>
            <div className="row">
              <div className="small-12 columns">
                  {this.props.children}
              </div>
            </div>
            <FooterBar/>
          </div>
        )
    }
}
// end::renewal[]

module.exports = Main;
