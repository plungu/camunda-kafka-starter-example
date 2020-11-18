var React = require('react');
const client = require('../client.jsx');
const DisplayDate = require('src/main/js/reactjs/service-request/components/date/DisplayDate.jsx');
const follow = require('../follow.jsx'); // function to hop multiple links by "rel"

class ServiceForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {

        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(e){
        e.preventDefault();
        var serviceRequest = this.props.serviceRequest;
        serviceRequest.serviceCategory = this.refs.serviceCategory.value;
        serviceRequest.serviceDescription = this.refs.serviceDescription.value;

        console.log("handleChange: "+ JSON.stringify(serviceRequest));

        this.props.onUpdateState(serviceRequest);

    }

    render() {

    return (
      <div className="row">
          <div className="small-12 columns">

              <div className="row">
                <div className="small-5 columns">
                  <div className="input-group">
                    <span className="input-group-label">Service Category</span>
                    <select className="input-group-field"
                            ref="serviceCategory"
                            onChange={this.handleChange}
                            defaultValue={this.props.serviceCategory}>

                      <option defaultValue>Please Select</option>
                      <option value="1">Category 1 </option>
                      <option value="2">Category 2 </option>
                      <option value="3">Category 3 </option>
                      <option value="5">Category 5 </option>
                    </select>
                  </div>
                </div>
              </div>

                <div className="row">
                <div className="small-5 columns">
                  <textarea rows="5" ref="serviceDescription"
                            placeholder="Service Description"
                            onChange={this.handleChange}
                            defaultValue={this.props.serviceDescription}
                            value={this.props.serviceDescription}/>

                </div>
                </div>

                <div className="row">

              </div>
          </div>
      </div>

    );
  }
}

module.exports = ServiceForm;
