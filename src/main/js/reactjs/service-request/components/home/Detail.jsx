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
const ServiceStartForm = require('src/main/js/reactjs/service-request/components/home/ServiceStartForm.jsx');
const ServiceForm = require('src/main/js/reactjs/service-request/components/home/ServiceForm.jsx');
const ServiceDetailForm = require('src/main/js/reactjs/service-request/components/home/ServiceDetailForm.jsx');
const ServiceSupplierForm = require('src/main/js/reactjs/service-request/components/home/ServiceSupplierForm.jsx');
const Info = require('src/main/js/reactjs/service-request/components/home/Info.jsx');
const FilterBar = require('src/main/js/reactjs/service-request/components/home/FilterBar.jsx');

// tag::vars[]
// const root = 'http://'+process.env.API_SERVER_HOST+':'+process.env.API_SERVER_PORT+'/api';
// const root = 'http://localhost:8080/';
// const apiRoot = 'http://localhost:8080/api';
const root = '/';
const apiRoot = '/api';
// end::vars[]

class Detail extends React.Component{
  constructor(props) {
        super(props);
        this.state = {
        serviceRequest: {},
        serviceRequests: null,
        displayDetail: "block",
        displayServiceStartForm: "block",
        displayServiceForm: "none",
        displayServiceDetailForm: "none",
        displayServiceSupplierForm: "none",
        // task: null,
        // tasks: [],
        attributes: [],
        pageSize: 10,
        links: {},
        callUpdateAll: function (pageSize, that) {
            that.loadAllFromServer(pageSize)
        },
        callUpdateItem: function (serviceRequest, that) {
            that.loadItemFromServer(serviceRequest)
        }
      };
      this.toggleForm = this.toggleForm.bind(this)
      this.uuidv4 = this.uuidv4.bind(this);
      this.handleStart = this.handleStart.bind(this);
      this.handleDone = this.handleDone.bind(this);
      this.handleSave = this.handleSave.bind(this);
      this.handleUpdateState = this.handleUpdateState.bind(this);
      this.post = this.post.bind(this);
  }

    // tag::follow-1[]
    componentDidMount() {

        this.loadAllFromServer(this.state.pageSize);

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
        console.log("handleUpdateState service request: "+ JSON.stringify(serviceRequest))

        // this.state.callUpdateAll(this.state.pageSize, this);
        this.state.callUpdateItem(serviceRequest, this);

    }

    handleStart(e){
        e.preventDefault();

        var serviceRequest = {
            serviceId: this.uuidv4()
        }

        console.log("HandleDone: " + JSON.stringify(serviceRequest));
        //post the object to the endpoint to save the Service Request
        this.post(serviceRequest, "sr/create");

        this.state.callUpdateAll(this.state.pageSize, this);

        this.setState({
            serviceRequest: serviceRequest
        });
    }

    handleSave(e){
        e.preventDefault();
        console.log("HandleSave: " + JSON.stringify(this.state.serviceRequest));
        //post the object to the endpoint
        this.post(this.state.serviceRequest, "sr/save");
    }

    handleDone(e){
        e.preventDefault();

        var serviceRequest = this.state.serviceRequest;

        console.log("HandleDone: " + this.state.serviceRequest)
        //post the object to the endpoint to save the Service Request
        this.post(serviceRequest, "sr/save");

        //post the object to the endpoint to Start the workflow
        this.post(serviceRequest, "sr/start/workflow");

        // clear out the dialog's inputs
        // this.refs.supplier.value = '';
    }

    // tag::follow-2[]
    loadAllFromServer(pageSize) {
        follow(client, apiRoot, [
            {rel: 'serviceRequestEntities', params: {size: pageSize}}]
        ).then(itemCollection => {
            return client({
                method: 'GET',
                path: itemCollection.entity._links.profile.href,
                headers: {'Accept': 'application/schema+json'}
            }).then(schema => {
                this.schema = schema.entity;
                return itemCollection;
            });
        }).done(itemCollection => {
            // console.log("loaded serviceRequestEntities: "
            //     +JSON.stringify(itemCollection.entity._embedded.serviceRequestEntities))
            this.setState({
                serviceRequests: itemCollection.entity._embedded.serviceRequestEntities,
                attributes: Object.keys(this.schema.properties),
                pageSize: pageSize,
                links: itemCollection.entity._links});
        });
    }
    // end::follow-2[]

    // tag::on-delete[]
    loadItemFromServer(path) {
        client(
            {method: 'GET', path: path}
        ).done(response => {
                /* let the websocket handle updating the UI */
                console.log("Fetching Service Request: " +response.entity.serviceId);
                this.setState({serviceRequest: response.entity});
            },
            response => {
                if (response.error){
                    alert("Somthing went wrong "+ reponse.error);
                }
            });
    }

    // end::on-delete[]
    post(obj, context) {
        console.log("POST Started")
        client({
            method: 'POST',
            path: root+context,
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

        <FilterBar toggleForm={this.toggleForm} title=""/>

        {/*{info}*/}

        <form >
            <div className="small-12 columns" style={{display: displayServiceStartForm}}>
                <ServiceStartForm onUpdateState={this.handleUpdateState}
                                  onStart={this.handleStart}
                                  serviceRequests={this.state.serviceRequests}
                />
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
              <div className="small-4 small-offset-8 large-4 large-offset-0 columns button-group ">
                  <label htmlFor="save" className="button ">Save</label>
                  <input type="submit" id="save" className="show-for-sr"
                         onClick={this.handleSave}/>
              </div>

              <div className="small-1 small-offset-2 large-1 large-offset-2 columns">
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
