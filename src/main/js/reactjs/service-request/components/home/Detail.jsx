// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom')
const client = require('../client.jsx');
const follow = require('../follow.jsx'); // function to hop multiple links by "rel"

const ServiceForm = require('src/main/js/reactjs/service-request/components/home/ServiceForm.jsx');
const ServiceDetailForm = require('src/main/js/reactjs/service-request/components/home/ServiceDetailForm.jsx');
const ServiceSupplierForm = require('src/main/js/reactjs/service-request/components/home/ServiceSupplierForm.jsx');
const Info = require('src/main/js/reactjs/service-request/components/home/Info.jsx');
const FilterBar = require('src/main/js/reactjs/service-request/components/home/FilterBar.jsx');

const root = 'http://localhost:8080/';

// end::vars[]

class Detail extends React.Component{
  constructor(props) {
      super(props);
      this.state = {
        serviceRequest: null,
        displayDetail: "block",
        displayServiceForm: "block",
        displayServiceDetailForm: "none",
        displayServiceSupplierForm: "none"
      };
      this.toggleForm = this.toggleForm.bind(this)
      this.uuidv4 = this.uuidv4.bind(this);
      this.handleDone = this.handleDone.bind(this);
      this.handleSave = this.handleSave.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
      this.post = this.post.bind(this);
  }

    toggleForm(form){
      if(form == "service") {
          this.setState({
              displayServiceForm: "block",
              displayServiceDetailForm: "none",
              displayServiceSupplierForm: "none"
          });
      }else if (form == "detail"){
          this.setState({
              displayServiceForm: "none",
              displayServiceDetailForm: "block",
              displayServiceSupplierForm: "none"
          });
      }else if (form == "supplier"){
          this.setState({
              displayServiceForm: "none",
              displayServiceDetailForm: "none",
              displayServiceSupplierForm: "block"
          });
      }
    }

    uuidv4() {
        return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
        );
    }

    handleSubmit(e){
        e.preventDefault();

        var serviceRequest = { "serviceId": this.uuidv4() , "serviceCategory": 2, "serviceDescription": "Test", "serviceOwner": "Vipin Gupta", "serviceOwnerMSID": "guptvipi", "sourcingManager": "Narayan", "sourcingManagerMSID": "ramamoor",     "acquiringDivision": "CFT",  "buContractingService": "Gabba Gabba Wee", "leContractingServiceCode": 12345, "additionalReviewer": "", "additionalReviewerMSID": null, "additionalReviewerNotes": "Gabba Gabba Wee",  "sourcingComments": "Gabba Gabba Wee", "applicationName": "Gabba Gabba Wee", "eonId": null, "estimatedAnnualSpend": null, "serviceDetailsComments": "" };

        console.log("HandleSubmit: " + serviceRequest)

        //post the object to the endpoint
        this.post(serviceRequest, "sr/start");

    }

    handleSave(e){
        e.preventDefault();

        var serviceRequest = { "serviceId": this.uuidv4() , "serviceCategory": 2, "serviceDescription": "Test", "serviceOwner": "Vipin Gupta", "serviceOwnerMSID": "guptvipi", "sourcingManager": "Narayan", "sourcingManagerMSID": "ramamoor",     "acquiringDivision": "CFT",  "buContractingService": "Gabba Gabba Wee", "leContractingServiceCode": 12345, "additionalReviewer": "", "additionalReviewerMSID": null, "additionalReviewerNotes": "Gabba Gabba Wee",  "sourcingComments": "Gabba Gabba Wee", "applicationName": "Gabba Gabba Wee", "eonId": null, "estimatedAnnualSpend": null, "serviceDetailsComments": "" };

        console.log("HandleSave: " + serviceRequest)
        //post the object to the endpoint
        this.post(serviceRequest, "sr/save");
        // clear out the dialog's inputs
        this.refs.supplier.value = '';
    }

    handleDone(e){
        e.preventDefault();

        var serviceRequest = { "serviceId": this.uuidv4() , "serviceCategory": 2, "serviceDescription": "Test", "serviceOwner": "Vipin Gupta", "serviceOwnerMSID": "guptvipi", "sourcingManager": "Narayan", "sourcingManagerMSID": "ramamoor",     "acquiringDivision": "CFT",  "buContractingService": "Gabba Gabba Wee", "leContractingServiceCode": 12345, "additionalReviewer": "", "additionalReviewerMSID": null, "additionalReviewerNotes": "Gabba Gabba Wee",  "sourcingComments": "Gabba Gabba Wee", "applicationName": "Gabba Gabba Wee", "eonId": null, "estimatedAnnualSpend": null, "serviceDetailsComments": "" };

        console.log("HandleDone: " + serviceRequest)
        //post the object to the endpoint
        this.post(serviceRequest, "sr/start");
        // clear out the dialog's inputs
        this.refs.supplier.value = '';
    }

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

      var displayServiceForm = this.state.displayServiceForm;
      var displayServiceDetailForm = this.state.displayServiceDetailForm;
      var displayServiceSupplierForm = this.state.displayServiceSupplierForm;

      var info = "";
      if (this.state.serviceRequest !== null) {
         info =  <div style={{display: this.props.displayInfo}}>
                    <Info
                        renewal={this.props.serviceRequest}/>
                    </div>
      }

    return (
      <div>

        <FilterBar toggleForm={this.toggleForm}
                     title=""/>

        {/*{info}*/}

        <form onSubmit={this.handleSubmit}>
          <div className="small-12 columns" style={{display: displayServiceForm}}>
            <ServiceForm serviceRequest={this.props.serviceRequest}
                         handleDone={this.state.handleDone}
                         handleSave={this.state.handleSave}
            />
          </div>

          <div className="small-12 columns" style={{display: displayServiceDetailForm}}>
            <ServiceDetailForm serviceRequest={this.props.serviceRequest}
                    cannedMessages={this.props.cannedMessages}/>
          </div>

          <div className="small-12 columns" style={{display: displayServiceSupplierForm}}>
            <ServiceSupplierForm renewal={this.props.serviceRequest}
                    cannedMessages={this.props.cannedMessages}/>
          </div>

          <div className="small-12 columns">
            {/*  Buttons for handling save and starting the service request  */}
            <div className="small-4 small-offset-8 large-4 large-offset-0 columns button-group ">
                <label htmlFor="save" className="button ">Save</label>
                <input type="submit" id="save" className="show-for-sr" />
            </div>

            <div className="small-1 small-offset-2 large-1 large-offset-2 columns">
                <label htmlFor="done" className="button ">Done</label>
                <input type="submit" id="done" className="show-for-sr" />
            </div>
          </div>

        </form>

      </div>
    )
  }
}

module.exports = Detail;
