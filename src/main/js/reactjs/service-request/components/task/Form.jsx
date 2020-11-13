var React = require('react');
const client = require('../client.jsx');
const DisplayDate = require('src/main/js/reactjs/service-request/components/date/DisplayDate.jsx');
const follow = require('../follow.jsx'); // function to hop multiple links by "rel"

const root = 'http://localhost:8080/';

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

        var serviceRequest = this.props.renewal;

        console.log("HandleSubmit: " + serviceRequest)

        this.post(serviceRequest, "sr/task/approve");
        // clear out the dialog's inputs
        // this.post(serviceRequest, "sr/task/reject");
    }

    handleReject(e){
        e.preventDefault();

        var serviceRequest = this.props.renewal;

        console.log("HandleSubmit: " + serviceRequest)

        this.post(serviceRequest, "sr/task/reject");
        // clear out the dialog's inputs
        // this.post(serviceRequest, "sr/task/reject");

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
