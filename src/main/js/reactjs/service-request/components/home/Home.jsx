'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('../client.jsx');
const follow = require('../follow.jsx'); // function to hop multiple links by "rel"

const Detail = require('src/main/js/reactjs/service-request/components/home/Detail.jsx');

const root = 'http://localhost:8080/api';
// end::vars[]

// tag::app[]
class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
          displayDetail: "block",
          callUpdate: function (pageSize, that) {that.loadAllFromServer(pageSize)}
        };
    }

    // tag::follow-1[]
    componentDidMount() {
        this.loadAllFromServer(this.state.pageSize);
    }
    // end::follow-1[]


    // tag::follow-2[]
    loadAllFromServer(pageSize) {
        follow(client, root, [
            {rel: 'renewals', params: {size: pageSize}}]
        ).then(renewalCollection => {
            return client({
                method: 'GET',
                path: renewalCollection.entity._links.profile.href,
                headers: {'Accept': 'application/schema+json'}
            }).then(schema => {
                this.schema = schema.entity;
                return renewalCollection;
            });
        }).done(renewalCollection => {
            this.setState({
                renewals: renewalCollection.entity._embedded.renewals,
                attributes: Object.keys(this.schema.properties),
                pageSize: pageSize,
                links: renewalCollection.entity._links});
        });
    }
    // end::follow-2[]

    render() {


      return (
          <div>

                <div className="top-bar show-for-medium small-12 columns">
                   <div className="top-bar-left">
                     <ul className="menu">
                       <li className="topbar-title">
                         Create New Service
                       </li>
                     </ul>
                   </div>
                </div>

                <div>
                  <Detail />
              </div>

          </div>
      )
    }
}
// end::app[]

module.exports = Home;
