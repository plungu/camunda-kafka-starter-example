'use strict';

var {Link, IndexLink} = require('react-router');

// tag::vars[]
const React = require('react');
const DisplayDate = require('src/main/js/reactjs/service-request/components/date/DisplayDate.jsx');
const UpdateNoteDialog = require('src/main/js/reactjs/service-request/components/note/UpdateNoteDialog.jsx');

// tag::renewal[]
class Line extends React.Component{

  constructor(props) {
      super(props);
  }

  render() {
    var status = "Not Started";
    if (this.props.renewal.renewalStarted){
      status = "Renewal Started";
    }
    if (this.props.renewal.renewalCompleted){
      status = "Renewal Completed"
    }
    var renewing = "";
    if (this.props.renewal.renewing){
        renewing = "Yes";
    }else if(this.props.renewal.renewing === false && this.props.renewal.renewalCompleted){
        renewing = "No";
    }
        
      return (
          <tr >
              <td onClick={this.props.onSelectItem.bind(null, this.props.renewal)}>{this.props.renewal.serviceId}</td>
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
