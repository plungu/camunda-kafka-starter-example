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
const ServiceStartForm = require('src/main/js/reactjs/service-request/components/home/ServiceSelectForm.jsx');
const ServiceForm = require('src/main/js/reactjs/service-request/components/home/ServiceForm.jsx');
const ServiceDetailForm = require('src/main/js/reactjs/service-request/components/home/ServiceDetailForm.jsx');
const ServiceSupplierForm = require('src/main/js/reactjs/service-request/components/home/ServiceSupplierForm.jsx');
const Info = require('src/main/js/reactjs/service-request/components/home/Info.jsx');
const FilterBar = require('src/main/js/reactjs/service-request/components/home/FilterBar.jsx');

// tag::vars[]
const apiHost = process.env.API_HOST != "" ? `${process.env.API_HOST}:${process.env.API_PORT}/` : "/";
const apiRoot = `${apiHost}${process.env.API_ROOT}`;
// end::vars[]

class Detail extends React.Component{
  constructor(props) {
        super(props);
        this.state = {
        serviceRequest: {},
        serviceRequests: null,
        displayDetail: "block",
        displayServiceStartForm: "block",
        displayServiceForm: "block",
        displayServiceDetailForm: "block",
        displayServiceSupplierForm: "block",
        // task: null,
        // tasks: [],
        attributes: [],
        pageSize: 10,
        links: {},
        callUpdateAll: function (pageSize, that) {
            that.loadRejectedFromServer(pageSize)
        },
        callUpdateItem: function (serviceId, that) {
            that.loadItemFromServer(serviceId)
        }
      };
      this.toggleForm = this.toggleForm.bind(this)
      this.uuidv4 = this.uuidv4.bind(this);
      this.handleDone = this.handleDone.bind(this);
      this.handleUpdateState = this.handleUpdateState.bind(this);
      this.handleUpdateStartState = this.handleUpdateStartState.bind(this);
      this.post = this.post.bind(this);
  }

    // tag::follow-1[]
    componentDidMount() {
        this.state.callUpdateAll(this.state.pageSize, this);
    }
    // end::follow-1[]


    toggleForm(form){
      if(form == "service") {
          this.setState({
              displayServiceStartForm: "none",
              displayServiceForm: "block",
              displayServiceDetailForm: "none",
              displayServiceSupplierForm: "none"
          });
      }else if (form == "detail"){
          this.setState({
              displayServiceStartForm: "none",
              displayServiceForm: "none",
              displayServiceDetailForm: "block",
              displayServiceSupplierForm: "none"
          });
      }else if (form == "supplier"){
          this.setState({
              displayServiceStartForm: "none",
              displayServiceForm: "none",
              displayServiceDetailForm: "none",
              displayServiceSupplierForm: "block"
          });
      }else if (form == "start"){
          this.setState({
              displayServiceStartForm: "block",
              displayServiceForm: "none",
              displayServiceDetailForm: "none",
              displayServiceSupplierForm: "none"
          });
      }
    }

    uuidv4() {
        return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
        );
    }

    handleUpdateState(serviceRequest){
        console.log("handleUpdateState rejected: "+ JSON.stringify(serviceRequest))

        this.setState({
            serviceRequest: serviceRequest
        });

    }

    handleUpdateStartState(serviceId){
        console.log("handleUpdateStartState rejected: "+ JSON.stringify(serviceId));

        this.state.callUpdateItem(serviceId, this);

    }

    handleDone(e){
        e.preventDefault();

        var serviceRequest = this.state.serviceRequest;

        serviceRequest.rejected = false;

        console.log("Rejected HandleDone: " + JSON.stringify(this.state.serviceRequest))

        //post the object to the endpoint to save the Service Request
        this.post(serviceRequest, "sr/save");

        //post the object to the endpoint to Start the workflow
        this.post(serviceRequest, "sr/update/rejected");

        this.props.onRedirect("/tasks")
    }

    // tag::follow-2[]
    loadRejectedFromServer(pageSize) {
        follow(client, apiRoot, [
                {rel: 'serviceRequestEntities', params: {size: pageSize}},
                {rel: 'search'},
                {rel: 'findServiceRequestEntitiesByRejectedAndStarted', params: {rejected: true, started: true}}
            ]
        ).then(itemCollection => {
            return client({
                method: 'GET',
                path: itemCollection.entity._links.self.href,
                headers: {'Accept': 'application/json'}
            }).then(schema => {
                this.schema = schema.entity;
                return itemCollection;
            });
        }).done(itemCollection => {
            console.log("loaded rejectedServiceRequestEntities: "
                +JSON.stringify(itemCollection.entity._embedded.serviceRequestEntities))
            this.setState({
                serviceRequests: itemCollection.entity._embedded.serviceRequestEntities,
                // attributes: Object.keys(this.schema.properties),
                pageSize: pageSize,
                links: itemCollection.entity._links});
        });
    }
    // end::follow-2[]

    // tag::on-delete[]
    // tag::on-delete[]
    loadItemFromServer(serviceId) {
        follow(client, apiRoot, [
                {rel: 'serviceRequestEntities'},
                {rel: 'search'},
                {rel: 'findServiceRequestByServiceId', params: {serviceId: serviceId}}
            ]
        ).then(itemCollection => {
            return client({
                method: 'GET',
                path: itemCollection.entity._links.self.href,
                headers: {'Accept': 'application/json'}
            }).then(schema => {
                this.schema = schema.entity;
                return itemCollection;
            });
        }).done(itemCollection => {
            console.log("loadItemFromServer serviceRequestEntity: "
                +JSON.stringify(itemCollection.entity))
            this.setState({
                serviceRequest: itemCollection.entity,
                // attributes: Object.keys(this.schema.properties),
                // pageSize: pageSize,
                links: itemCollection.entity._links});
        });
    }


    // end::on-delete[]
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

  render(){

      var displayServiceStartForm = this.state.displayServiceStartForm;
      var displayServiceForm = this.state.displayServiceForm;
      var displayServiceDetailForm = this.state.displayServiceDetailForm;
      var displayServiceSupplierForm = this.state.displayServiceSupplierForm;

      var info = "";
      if (this.state.serviceRequest !== null) {
         info =  <div style={{display: this.props.displayInfo}}>
                    <Info
                        item={this.state.serviceRequest}/>
                    </div>
      }

    return (
      <div>

        {/*<FilterBar toggleForm={this.toggleForm} title=""/>*/}

        {/*{info}*/}

        <div></div>

        <form >
            <div className="small-12 columns" style={{display: displayServiceStartForm}}>
                <ServiceStartForm onUpdateStartState={this.handleUpdateStartState}
                                  onUpdateState={this.handleUpdateState}
                                  onStart={this.handleStart}
                                  serviceRequests={this.state.serviceRequests}
              onStart  />
            </div>

          <div className="small-12 columns" style={{display: displayServiceForm}}>
            <ServiceForm serviceRequest={this.state.serviceRequest}
                         onUpdateState={this.handleUpdateState}
            />
          </div>

          <div className="small-12 columns" style={{display: displayServiceDetailForm}}>
            <ServiceDetailForm serviceRequest={this.state.serviceRequest}
                               onUpdateState={this.handleUpdateState}
            />
          </div>

          <div className="small-12 columns" style={{display: displayServiceSupplierForm}}>
            <ServiceSupplierForm serviceRequest={this.state.serviceRequest}
                                 onUpdateState={this.handleUpdateState}
            />
          </div>

        </form>

          <div className="small-12 columns">
              {/*  Buttons for handling save and starting the service request  */}

              <div className="small-1 large-1 columns">
                  <label htmlFor="done" className="button ">Done</label>
                  <input type="submit" id="done" className="show-for-sr"
                         onClick={this.handleDone} />
              </div>
          </div>

      </div>
    )
  }
}

module.exports = Detail;
