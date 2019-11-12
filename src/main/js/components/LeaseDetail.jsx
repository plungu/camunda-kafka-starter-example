// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom')
const client = require('./client');
const follow = require('./follow'); // function to hop multiple links by "rel"

const MessageList = require('MessageList');
const MessageDetail = require('MessageDetail');
const LeaseForm = require('LeaseForm');
const Tenant = require('Tenant');
const LeaseInfo = require('LeaseInfo');
const LeaseLine = require('LeaseLine');

// end::vars[]

class LeaseDetail extends React.Component{
  constructor(props) {
      super(props);
      this.state = {
        message: null,
        messages: [],
        attributes: [],
        pageSize: 20,
        links: {},
        displayDetail: "none",
        displayList: "block"
      };
      this.updatePageSize = this.updatePageSize.bind(this);
      this.handleSelectedItem = this.handleSelectedItem.bind(this);
      this.handleBackClick = this.handleBackClick.bind(this);
  }

  // tag::update-page-size[]
  updatePageSize(pageSize) {
      if (pageSize !== this.state.pageSize) {
          this.loadFromServer(pageSize);
      }
  }
  // end::update-page-size[]

  handleSelectedItem(message){
    this.setState({
      message: message,
      displayDetail: "block",
      displayList: "none"
    });
  }

  handleBackClick(e){
    this.setState({
      displayDetail: "none",
      displayList: "block"
    });
  }

  render(){
    var item = "";
    if (this.state.message !== null) {
      item = <MessageDetail message={this.state.message}/>
    }
    var displayForm = this.props.displayForm;
    var displayMessages = this.props.displayMessages;

    var tenants = this.props.lease.tennants.map(tenant =>
        <Tenant key={tenant.email}
            tenant={tenant}/>
    );

    return (
      <div>

        <div style={{display: this.props.displayLine}}>
            <div className="row">
                <div className="small-12 columns">
                    <table className="hover stack">
                        <thead>
                            <tr>
                                <th>Property</th>
                                <th width="105">End Date</th>
                                <th width="105">Show Date</th>
                                <th>Current Rent</th>
                                <th>1 Year Offer</th>
                                <th>2 Year Offer</th>
                                <th>Status</th>
                                <th>State</th>
                                <th>Renewing</th>
                                <th>Note</th>
                             </tr>
                        </thead>
                        <tbody>
                            <LeaseLine key={this.props.lease._links.self.href}
                                lease={this.props.lease}
                                onUpdateNote={this.props.onUpdateNote}
                                onSelectItem={this.props.onSelectItem}/>
                        </tbody>
                    </table>
                </div>
            </div>    
        </div>
                                
        <div style={{display: this.props.displayInfo}}>
            <LeaseInfo 
                tenants={tenants}
                lease={this.props.lease}
                onUpdateNote={this.props.onUpdateNote}
                onDelete={this.props.onDelete}/>
        </div>
                            
        <div style={{display: displayMessages}}>
          <hr />
          <div style={{display: this.state.displayList}}>
              <MessageList messages={this.props.messages}
                  lease={this.props.lease}
                  links={this.state.links}
                  pageSize={this.state.pageSize}
                  onNavigate={this.onNavigate}
                  updatePageSize={this.updatePageSize}
                  onSelectItem={this.handleSelectedItem}
                  onRefreshMessages={this.props.onRefreshMessages}/>
          </div>
          <div style={{display: this.state.displayDetail}}>
            <div className="top-bar show-for-medium small-12 columns">
             <div className="top-bar-left">
               <ul className="menu">
                 <li className="topbar-title">
                   Message Detail
                 </li>
               </ul>
             </div>
             <div className="top-bar-right">
               <ul className="menu">
                 <li className="topbar-title">
                   <a className="button" onClick={this.handleBackClick}>&lt;&lt;&lt;Back</a>
                 </li>
               </ul>
             </div>
            </div>
            <div className="row">
                <div className="small-12 columns">
                  {item}
                </div>
            </div>
          </div>
        </div>
                  
        <div style={{display: displayForm}}>
          <hr />
          <LeaseForm lease={this.props.lease}
                   cannedMessages={this.props.cannedMessages}/>
        </div>

      </div>
    )
  }
}

module.exports = LeaseDetail;
