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

        console.log("Service Form: "+ JSON.stringify(this.props.serviceRequest));

    return (

        <div >

            <div className="small-7 large-7 columns">
              <div className="input-group">
                    <span className="input-group-label">Service Category</span>

                    <select className="input-group-field"
                            ref="serviceCategory"
                            onChange={this.handleChange}
                            value={this.props.serviceRequest.serviceCategory}>

                        <option defaultValue>Choose Category</option>
                        <option value="1">Category 1 </option>
                        <option value="2">Category 2 </option>
                        <option value="3">Category 3 </option>
                        <option value="5">Category 5 </option>
                    </select>

              </div>
            </div>

            <div className="row">
                <div className="small-7 large-7 columns">
                    <div className="input-group">
                        <textarea rows="5" ref="serviceDescription"
                            placeholder="Service Description"
                            onChange={this.handleChange}
                            value={this.props.serviceRequest.serviceDescription}/>
                    </div>
                </div>
            </div>

      </div>

    );
  }
}

module.exports = ServiceForm;
