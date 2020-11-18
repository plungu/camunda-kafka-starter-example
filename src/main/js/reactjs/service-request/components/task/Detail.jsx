/**
 * @author Paul Lungu
 * @type {{DOM, PropTypes, createElement, isValidElement, version, __spread, PureComponent, createMixin, createClass, Children, Component, createFactory, cloneElement}}
 */

'use strict';

// tag::nodeModules[]
const React = require('react');
const ReactDOM = require('react-dom')
const client = require('../client.jsx');
const follow = require('../follow.jsx'); // function to hop multiple links by "rel"

// tag::customComponents
const MessageList = require('src/main/js/reactjs/service-request/components/message/MessageList.jsx');
const MessageDetail = require('src/main/js/reactjs/service-request/components/message/MessageDetail.jsx');
const Form = require('src/main/js/reactjs/service-request/components/task/Form.jsx');
const Tenant = require('src/main/js/reactjs/service-request/components/tenant/Tenant.jsx');
const Info = require('src/main/js/reactjs/service-request/components/task/Info.jsx');
const Line = require('src/main/js/reactjs/service-request/components/task/Line.jsx');

// tag::vars[]
// end::vars[]

class Detail extends React.Component{
  constructor(props) {
      super(props);
      this.state = {
      };
  }

  render(){

    return (
      <div>

        <Info task={this.props.task}/>
        <hr />
        <Form task={this.props.task} />

    </div>
    )
  }
}

module.exports = Detail;
