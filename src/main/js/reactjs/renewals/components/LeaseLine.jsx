'use strict';

var {Link, IndexLink} = require('react-router');

// tag::vars[]
const React = require('react');
const DisplayDate = require('src/main/js/reactjs/renewals/components/DisplayDate.jsx');
const UpdateNoteDialog = require('src/main/js/reactjs/renewals/components/UpdateNoteDialog.jsx');

// tag::lease[]
class LeaseLine extends React.Component{

  constructor(props) {
      super(props);
  }

  render() {
    var status = "Not Started";
    if (this.props.lease.renewalStarted){
      status = "Renewal Started";
    }
    if (this.props.lease.renewalCompleted){
      status = "Renewal Completed"
    }
    var renewing = "";
    if (this.props.lease.renewing){
        renewing = "Yes";
    }else if(this.props.lease.renewing === false && this.props.lease.renewalCompleted){
        renewing = "No";
    }
        
      return (
          <tr >
            <td onClick={this.props.onSelectItem.bind(null, this.props.lease)}>{this.props.lease.property}</td>
            <td onClick={this.props.onSelectItem.bind(null, this.props.lease)}><DisplayDate date={this.props.lease.end} /></td>
            <td onClick={this.props.onSelectItem.bind(null, this.props.lease)}><DisplayDate date={this.props.lease.showDate} /></td>
            <td onClick={this.props.onSelectItem.bind(null, this.props.lease)}>{this.props.lease.currentRent}</td>
            <td onClick={this.props.onSelectItem.bind(null, this.props.lease)}>{this.props.lease.oneYearOffer}</td>
            <td onClick={this.props.onSelectItem.bind(null, this.props.lease)}>{this.props.lease.twoYearOffer}</td>
            <td onClick={this.props.onSelectItem.bind(null, this.props.lease)}>{status}</td>
            <td onClick={this.props.onSelectItem.bind(null, this.props.lease)}>{this.props.lease.workflowState}</td>
            <td onClick={this.props.onSelectItem.bind(null, this.props.lease)}>{renewing}</td>
            <td>
                <UpdateNoteDialog lease={this.props.lease}
                  onUpdateNote={this.props.onUpdateNote}/>
            </td>
          </tr>
      )
  }
}
// end::lease[]

module.exports = LeaseLine;
