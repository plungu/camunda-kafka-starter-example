const React = require('react');
const DisplayDate = require('DisplayDate');
const UpdateNoteDialog = require('UpdateNoteDialog');

class LeaseInfo extends React.Component{
  constructor(props) {
      super(props);
      this.handleDelete = this.handleDelete.bind(this);
  }

  handleDelete(e){
      e.preventDefault;
      alert("Sure you want to delete this property? Press the [esc] button to cancel this action.");
      this.props.onDelete(this.props.lease);
  }
  
  render(){
      var status = "Not Started";
      if (this.props.lease.renewalStarted){
        status = "Renewal Started";
      }
      if (this.props.lease.renewalCompleted){
        status = "Renewal Completed"
      }  
      
      var renewing = "";
      if (this.props.lease.renewing){
          renewing = "Yes";
      }else if(this.props.lease.renewing === false && this.props.lease.renewalCompleted){
          renewing = "No";
      }

      var signed = "No";
      if (this.props.lease.signed){
          signed = "Yes"
      }
          
      return (   
       <div>   
            <div className="row">
              <div className="small-4 columns">
                <div className="card" style={{width: "300px"}}>
                  <div className="card-divider">
                    <h4>Property</h4>
                  </div>
                  <div className="card-section">
                    <ul>
                      <li>{this.props.lease.property}</li>
                    </ul>
                  </div>
                </div>
              </div>
              <div className="small-4 columns">
                <div className="card" style={{width: "400px"}}>
                  <div className="card-divider">
                    <h4>Tenants</h4>
                  </div>
                  <div className="card-section">
                    {this.props.tenants}
                  </div>
                </div>
              </div>
              <div className="small-4 columns">
                <div className="card" style={{width: "300px"}}>
                    <a className="button small float-right" key="last" onClick={this.handleDelete}>Delete</a>
                </div>
              </div>
            </div>
        
            <div className="row">
              <div className="small-6 columns">
                <div className="card" style={{width: "300px"}}>
                  <div className="card-divider">
                    <h4>Status</h4>
                  </div>
                  <div className="card-section">
                    <ul>
                      <li><span className="label">Start Date</span><span className="data"><DisplayDate date={this.props.lease.start} /></span></li>
                      <li><span className="label">End Date</span><span className="data"><DisplayDate date={this.props.lease.end} /></span></li>
                      <li><span className="label">Status</span><span className="data">{status}</span></li>
                      <li><span className="label">Worflow State</span><span className="data">{this.props.lease.workflowState}</span></li>
                      <li><span className="label">Show Date</span><span className="data"><DisplayDate date={this.props.lease.showDate} /></span></li>
                      <li><span className="label">Renewing</span><span className="data">{renewing}</span></li>
                      {/*<li><span className="label">Signed</span><span className="data">{signed}</span></li>*/}
                    </ul>
                  </div>
                </div>
              </div>
              <div className="small-6 columns">
                <div className="card" style={{width: "300px"}}>
                  <div className="card-divider">
                    <h4>Pricing</h4>
                  </div>
                  <div className="card-section">
                    <ul>
                      <li><span className="label">Current</span><span className="data">{this.props.lease.currentRent}</span></li>
                      <li><span className="label">1 Year</span><span className="data">{this.props.lease.oneYearOffer}</span></li>
                      <li><span className="label">2 Year</span><span className="data">{this.props.lease.twoYearOffer}</span></li>
                    </ul>
                  </div>
                </div>
              </div>
              <div className="small-6 columns">
               <div className="card" style={{width: "300px"}}>
                <div className="card-divider">
                  <h4>Note</h4>
                </div>
                <div className="card-section">
                  <ul>
                    <li>
                      <UpdateNoteDialog lease={this.props.lease}
                      onUpdateNote={this.props.onUpdateNote}/>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            </div>
        </div>
      )                 
  }
  
}
  
module.exports = LeaseInfo;