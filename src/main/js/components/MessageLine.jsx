'use strict';

var {Link, IndexLink} = require('react-router');

// tag::vars[]
const React = require('react');
const DisplayDate = require('DisplayDateTime');

// tag::lease[]
class MessageLine extends React.Component{

  constructor(props) {
      super(props);
  }

  render() {
    return (
        <tr onClick={this.props.onSelectItem.bind(null, this.props.message)}>
            <td>{this.props.message.recipient}</td>
            <td>{this.props.message.sender}</td>
            <td>{this.props.message.subject}</td>
            <td><DisplayDate date={this.props.message.created} /></td>
        </tr>
    )
  }
}
// end::lease[]

module.exports = MessageLine;
