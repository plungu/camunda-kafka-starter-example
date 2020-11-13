const React = require('react');

const DisplayDate = require('src/main/js/reactjs/service-request/components/date/DisplayDate.jsx');
const UpdateNoteDialog = require('src/main/js/reactjs/service-request/components/note/UpdateNoteDialog.jsx');

class Info extends React.Component{
  constructor(props) {
      super(props);
      this.handleDelete = this.handleDelete.bind(this);
  }

  handleDelete(e){
      e.preventDefault;
      alert("Sure you want to delete this property? Press the [esc] button to cancel this action.");
      this.props.onDelete(this.props.renewal);
  }
  
  render(){
        console.log(this.props.task)
      return (   
       <div>   
            <div className="row">

              <div className="small-4 small-offset-1 columns">
                <div className="card" style={{width: "300px"}}>
                  <div className="card-divider text-center">
                    <h4>Task Info</h4>
                  </div>
                  <div className="card-section" style={{borderTop: "1px dashed #2199e8"}}>
                    <ul>
                      <li>{this.props.task.name}</li>
                      <li><span className="label">Task Id</span><span className="data">{this.props.task.id}</span></li>
                    </ul>
                  </div>
                </div>
              </div>

              <div className="small-6 small-offset-1 columns">
                <div className="card" style={{width: "400px"}}>
                  <div className="card-divider text-center">
                    <h4>Service Owner</h4>
                  </div>
                  <div className="card-section" style={{borderTop: "1px dashed #2199e8"}}>
                    {this.props.renewal.serviceOwner}
                  </div>
                </div>
              </div>

              {/*<div className="small-4 columns">*/}
              {/*  <div className="card" style={{width: "300px"}}>*/}
              {/*      <a className="button small float-right" key="last" onClick={this.handleDelete}>Delete</a>*/}
              {/*  </div>*/}
              {/*</div>*/}
            </div>
        
            <div className="row">
              <div className="small-4 small-offset-1 columns" >
                <div className="card" style={{width: "300px"}}>
                  <div className="card-divider text-center">
                    <h4>Status</h4>
                  </div>
                  <div className="card-section" style={{borderTop: "1px dashed #2199e8"}}>
                    <ul>
                      <li><span className="label">Assigned Date</span><span className="data">{this.props.renewal.acquiringDivision} </span></li>
                        <li><span className="label">Due Date</span><span className="data">{this.props.renewal.serviceDescription} </span></li>
                      <li><span className="label">RAG Status</span><span className="data">{this.props.renewal.sourcingManager}</span></li>
                    </ul>
                  </div>
                </div>
              </div>
              <div className="small-6 small-offset-1 columns">
                <div className="card" style={{width: "400px"}}>
                  <div className="card-divider text-center">
                    <h4>Service Info</h4>
                  </div>
                  <div className="card-section" style={{borderTop: "1px dashed #2199e8"}}>
                    <ul>
                      <li><span className="label">Service ID</span><span className="data">{this.props.renewal.serviceId}</span></li>
                      <li><span className="label">Service Category</span><span className="data">{this.props.renewal.serviceCategory}</span></li>
                      <li><span className="label">Sourcing Comments</span><span className="data">{this.props.renewal.sourcingComments}</span></li>
                    </ul>
                  </div>
                </div>
              </div>
            {/*  <div className="small-6 columns">*/}
            {/*   <div className="card" style={{width: "300px"}}>*/}
            {/*    <div className="card-divider">*/}
            {/*      <h4>Note</h4>*/}
            {/*    </div>*/}
            {/*    <div className="card-section">*/}
            {/*      <ul>*/}
            {/*        <li>*/}
            {/*          <UpdateNoteDialog renewal={this.props.renewal}*/}
            {/*          onUpdateNote={this.props.onUpdateNote}/>*/}
            {/*        </li>*/}
            {/*      </ul>*/}
            {/*    </div>*/}
            {/*  </div>*/}
            {/*</div>*/}
            </div>
        </div>
      )                 
  }
  
}
  
module.exports = Info;