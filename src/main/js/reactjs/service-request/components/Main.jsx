'use strict';

// tag::vars[]
const React = require('react');

const FilterBar = require('src/main/js/reactjs/service-request/components/home/FilterBar');
var MarketingBar = require('MarketingBar');
var StatusBar = require('StatusBar');
var FooterBar = require('FooterBar');

// tag::renewal[]
class Main extends React.Component{
    render() {
        return (
         <div className="row translucent-form-overlay">
           <div className="small-12 large-12 columns">
            <MarketingBar/>
            <StatusBar/>
            <div>
              {this.props.children}
            </div>
            <FooterBar/>
          </div>
         </div>
        )
    }
}
// end::renewal[]

module.exports = Main;
