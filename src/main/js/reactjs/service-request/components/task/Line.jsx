'use strict';

var {Link, IndexLink} = require('react-router');

const client = require('../client.jsx');

const root = 'http://localhost:8080';

// tag::vars[]
const React = require('react');
const DisplayDate = require('src/main/js/reactjs/service-request/components/date/DisplayDate.jsx');
const UpdateNoteDialog = require('src/main/js/reactjs/service-request/components/note/UpdateNoteDialog.jsx');

// tag::renewal[]
class Line extends React.Component{

  constructor(props) {
      super(props);
      this.state = {
        task: null
      };
      this.loadTaskFromServer = this.loadTaskFromServer.bind(this);
  }

    // tag::follow-1[]
    componentDidMount() {
        this.loadTaskFromServer(this.props.renewal.serviceId);
    }
    // end::follow-1[]

  loadTaskFromServer(businessKey){
    client({
        method: 'GET',
        path: root+"/engine-rest/task",
        params: {processInstanceBusinessKey: businessKey},
        headers: {'Accept': 'application/json'}
    }).done(response => {
        this.setState({
            task: response.entity[0]
        });
    });
  }

  render() {

      var task = this.state.task;
      var name = "";
      if (task !== null){
          name = task.name;
      }

      return (
          <tr >
              <td onClick={this.props.onSelectItem.bind(null, this.props.renewal, this.state.task)}>{name}</td>
              <td onClick={this.props.onSelectItem.bind(null, this.props.renewal)}>{this.props.renewal.serviceOwner}</td>
              <td onClick={this.props.onSelectItem.bind(null, this.props.renewal)}>{this.props.renewal.sourcingManager}</td>
              <td onClick={this.props.onSelectItem.bind(null, this.props.renewal)}>{this.props.renewal.serviceDescription}</td>
              <td onClick={this.props.onSelectItem.bind(null, this.props.renewal)}>{this.props.renewal.acquiringDivision}</td>
              <td onClick={this.props.onSelectItem.bind(null, this.props.renewal)}>{this.props.renewal.serviceCategory}</td>
              <td onClick={this.props.onSelectItem.bind(null, this.props.renewal)}>{this.props.renewal.sourcingComments}</td>
              <td onClick={this.props.onSelectItem.bind(null, this.props.renewal)}>{this.props.renewal.serviceId}</td>
          </tr>
      )
  }
}
// end::renewal[]

module.exports = Line;
