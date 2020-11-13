var React = require('react');
const client = require('../client.jsx');
const DisplayDate = require('src/main/js/reactjs/service-request/components/date/DisplayDate.jsx');
const follow = require('../follow.jsx'); // function to hop multiple links by "rel"

class Form extends React.Component {
        
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  render() {
    var i = 0;  

    return (
      <div className="row">
          <div className="small-12 columns">


                <div className="row">
                    <div className="small-5 columns">
                        <div className="input-group">
                            <span className="input-group-label">Service Owner</span>
                            <input className="input-group-field" type="text" ref="renewing" onChange={this.handleRenewedCheck}/>
                        </div>
                    </div>
                </div>


                <div className="row">
                    <div className="small-5 columns">
                        <div className="input-group">
                            <span className="input-group-label">Sourcing Manager</span>
                            <input className="input-group-field" type="text" ref="renewing" onChange={this.handleRenewedCheck}/>
                        </div>
                    </div>
                </div>

                <div className="row">
                <div className="small-5 columns">
                  <div className="input-group">
                    <span className="input-group-label">Acquiring BU</span>
                    <select className="input-group-field" ref="gracePeriod" defaultValue="">
                      <option value="">Please Select</option>
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
                        <div className="input-group">
                            <span className="input-group-label">Legal Entitiy</span>
                            <select className="input-group-field" ref="gracePeriod" defaultValue="">
                                <option value="">Please Select</option>
                                <option value="1">Category 1 </option>
                                <option value="2">Category 2 </option>
                                <option value="3">Category 3 </option>
                                <option value="5">Category 5 </option>
                            </select>
                        </div>
                    </div>
                </div>

          </div>
      </div>

    );
  }
}

module.exports = Form;
