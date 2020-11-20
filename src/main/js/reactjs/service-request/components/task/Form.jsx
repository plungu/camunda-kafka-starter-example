/**
 * @author Paul Lungu
 * @type {{DOM, PropTypes, createElement, isValidElement, version, __spread, PureComponent, createMixin, createClass, Children, Component, createFactory, cloneElement}}
 */

'use strict';

// tag::nodeModules[]
var React = require('react');
const client = require('../client.jsx');

// tag::customComponents
const DisplayDate = require('src/main/js/reactjs/service-request/components/date/DisplayDate.jsx');
const follow = require('../follow.jsx'); // function to hop multiple links by "rel"

// tag::vars[]
const apiHost = process.env.API_HOST != "" ? `${process.env.API_HOST}:${process.env.API_PORT}/` : "/";
const apiRoot = `${apiHost}${process.env.API_ROOT}`;
// end::vars[]

class Form extends React.Component {
        
  constructor(props) {
    super(props);
    this.state = {

    };
      this.handleApprove = this.handleApprove.bind(this);
      this.handleReject = this.handleReject.bind(this);
      this.post = this.post.bind(this);
  }

    handleApprove(e){
        e.preventDefault();

        var serviceRequest = this.props.task.serviceRequest;

        console.log("HandleSubmit: " + serviceRequest)

        this.post(serviceRequest, "sr/task/approve");
        // clear out the dialog's inputs
        // this.post(serviceRequest, "sr/task/reject");
    }

    handleReject(e){
        e.preventDefault();

        var serviceRequest = this.props.task.serviceRequest;

        console.log("HandleSubmit: " + serviceRequest)

        this.post(serviceRequest, "sr/task/reject");
        // clear out the dialog's inputs
        // this.post(serviceRequest, "sr/task/reject");

    }

    post(obj, context) {
        console.log("POST Started")
        client({
            method: 'POST',
            path: apiHost+context,
            entity: obj,
            headers: {'Content-Type': 'application/json'}
        }).done(response => {
            console.log("POST Request Complete");
        });
    }

  
  render() {

    return (
      <div>

          <div className="row">
              <div className="small-6 small-offset-6 columns">
                {/*<form onSubmit={this.handleSubmit}>*/}

                    <div className="small-12 columns button-group ">
                        {/*  Buttons for handling save and starting the service request  */}
                        <div className="small-2 large-2 columns ">
                            <label htmlFor="reject" className="button" >Reject</label>
                            <button type="submit" id="reject" className="show-for-sr" onClick={this.handleReject} />
                        </div>

                        <div className="small-2 large-2 columns">
                            <label htmlFor="approve" className="button ">Approve</label>
                            <input type="submit" id="approve" className="show-for-sr" onClick={this.handleApprove}  />
                        </div>
                    </div>

                {/*</form>*/}
              </div>
          </div>

      </div>
    );
  }
}

module.exports = Form;
