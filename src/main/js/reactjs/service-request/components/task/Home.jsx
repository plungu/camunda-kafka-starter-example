'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('../client.jsx');
const follow = require('../follow.jsx'); // function to hop multiple links by "rel"

const List = require('src/main/js/reactjs/service-request/components/task/List.jsx');
const Detail = require('src/main/js/reactjs/service-request/components/task/Detail.jsx');

const root = 'http://localhost:8080/api';
// end::vars[]

// tag::app[]
class home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
          renewal: null,
          renewals: [],
          task: null,
          attributes: [],
          pageSize: 10,
          links: {},
          displayDetail: "none",
          displayList: "block",
          displayInfo: "none",
          displayLine: "block",
          toggleDetailInfo: "off",
          callUpdate: function (pageSize, that) {that.loadAllFromServer(pageSize)}
        };
        this.updatePageSize = this.updatePageSize.bind(this);
        this.onNavigate = this.onNavigate.bind(this);
        // this.onUpdateNote = this.onUpdateNote.bind(this);
        this.handleSelectedItem = this.handleSelectedItem.bind(this);
        this.handleBackClick = this.handleBackClick.bind(this);
        this.handleToggleClick = this.handleToggleClick.bind(this);
        // this.handlefilterState = this.handlefilterState.bind(this);
        // this.handleFilterPriority = this.handleFilterPriority.bind(this);
        this.handleFilterAll = this.handleFilterAll.bind(this);
        // this.handleFilterStarted = this.handleFilterStarted.bind(this);
        this.handleRefreshMessages = this.handleRefreshMessages.bind(this);
        // this.onDelete = this.onDelete.bind(this);
    }

    // tag::update-page-size[]
    updatePageSize(pageSize) {
        if (pageSize !== this.state.pageSize) {
          this.state.callUpdate(pageSize, this);
        }
    }
    // end::update-page-size[]

    // tag::follow-1[]
    componentDidMount() {
        this.loadAllFromServer(this.state.pageSize);
        // this.loadTaskFromServer(this.state.renewals.serviceId);
    }
    // end::follow-1[]

    // tag::follow-2[]
    loadAllFromServer(pageSize) {
        follow(client, root, [
            {rel: 'serviceRequestEntities', params: {size: pageSize}}]
        ).then(renewalCollection => {
            return client({
                method: 'GET',
                path: renewalCollection.entity._links.profile.href,
                headers: {'Accept': 'application/schema+json'}
            }).then(schema => {
                this.schema = schema.entity;
                return renewalCollection;
            });
        }).done(renewalCollection => {
            this.setState({
                renewals: renewalCollection.entity._embedded.serviceRequestEntities,
                attributes: Object.keys(this.schema.properties),
                pageSize: pageSize,
                links: renewalCollection.entity._links});
        });
    }
    // end::follow-2[]

    // tag::navigate[]
    onNavigate(navUri) {
        client({method: 'GET', path: navUri}).done(renewalCollection => {
            this.setState({
                renewals: renewalCollection.entity._embedded.renewals,
                attributes: this.state.attributes,
                pageSize: this.state.pageSize,
                links: renewalCollection.entity._links
            });
        });
    }
    // end::navigate[]

    // handleFilterPriority(pageSize){
    //   this.loadPriorityFromServer(pageSize);
    //   this.setState({
    //     callUpdate: function (pageSize, that) {that.loadPriorityFromServer(pageSize)}
    //   });
    // }

    handleFilterAll(pageSize){
      this.loadAllFromServer(pageSize);
      this.setState({
        callUpdate: function (pageSize, that) {that.loadAllFromServer(pageSize)}
      });
    }

    // handleFilterStarted(pageSize){
    //   this.loadStartedFromServer(pageSize);
    //   this.setState({
    //     callUpdate: function (pageSize, that) {that.loadStartedFromServer(pageSize)}
    //   });
    // }

    // handlefilterState(pageSize){
    //   this.loadStateFromServer(pageSize);
    //   this.setState({
    //     callUpdate: function (pageSize, that) {that.loadStateFromServer(pageSize)}
    //   });
    // }

    handleRefreshMessages(renewal){
      this.loadMessagesFromServer(renewal._links.messages.href);
    }

    // loadTaskFromServer(businessKey){
    //   client({
    //     method: 'GET',
    //     path: root+"/engine-rest/task",
    //     params: {processInstanceBusinessKey: businessKey},
    //   }).done(task => {
    //       console.log(task);
    //       this.setState(
    //         {
    //           task: task,
    //         }
    //       );
    //   });
    // }

    // // tag::follow-2[]
    // loadCannedMessageFromServer() {
    //     follow(client, root, [
    //         {rel: 'cannedMessages'}]
    //     ).then(cannedMessageCollection => {
    //         return client({
    //             method: 'GET',
    //             path: cannedMessageCollection.entity._links.profile.href,
    //             headers: {'Accept': 'application/schema+json'}
    //         }).then(schema => {
    //             this.schema = schema.entity;
    //             return cannedMessageCollection;
    //         });
    //     }).done(cannedMessageCollection => {
    //         this.setState({
    //             cannedMessages: cannedMessageCollection.entity._embedded.cannedMessages,
    //             attributes: Object.keys(this.schema.properties),
    //             links: cannedMessageCollection.entity._links});
    //     });
    // }
    // end::follow-2[]
    
    // loadPriorityFromServer(pageSize){
    //   client({
    //     method: 'GET',
    //     path: root+"/renewals/search/findRenewalsByRenewalStartedAndRenewalCompletedAndWorkflowStateOrderByShowDateAsc",
    //     params: {size: pageSize, renewalStarted: true, renewalCompleted: false, workflowState: "Confirm Renewal State"},
    //   }).done(response => {
    //       this.setState(
    //         {
    //           renewals: response.entity._embedded.renewals,
    //           pageSize: pageSize,
    //           links: response.entity._links
    //         }
    //       );
    //   });
    // }

    // loadStartedFromServer(pageSize){
    //   client({
    //     method: 'GET',
    //     path: root+"/renewals/search/findRenewalsByRenewalStartedAndRenewalCompletedOrderByRenewalStartedAsc",
    //     params: {size: pageSize, renewalStarted: true, renewalCompleted: false},
    //   }).done(response => {
    //       this.setState(
    //         {
    //           renewals: response.entity._embedded.renewals,
    //           pageSize: pageSize,
    //           links: response.entity._links
    //         }
    //       );
    //   });
    // }

    handleSelectedItem(renewal, task) {

        this.setState({
            renewal: renewal,
            task: task,
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

    handleToggleClick(e){
        if (this.state.toggleDetailInfo === "off"){
            this.setState({
              toggleDetailInfo: "on",
              displayInfo: "block",
              displayLine: "none"
            });
        }else {
            this.setState({
                toggleDetailInfo: "off",
                displayInfo: "none",
                displayLine: "block"
              });
    
        }
    }

    // onUpdateNote(renewal, updatedRenewal) {
    //     debugger
    //     client({
    //       method: 'POST',
    //       path: "/updateNote",
    //       entity: updatedRenewal,
    //       headers: {'Content-Type': 'multipart/form-data'}
    //     }).done(response => {
    //       if (response.status.code === 200) {
    //           alert('SUCCESS: Note Updated');
    //       }
    //       if (response.status.code === 412) {
    //           alert('DENIED: Unable to update ' +
    //               renewal._links.self.href + ' METHOD NOT ALLOWED');
    //       }
    //       if (response.status.code === 412) {
    //           alert('DENIED: Unable to update ' +
    //               renewal._links.self.href + '. Your copy is stale.');
    //       }
    //       if (response.status.code === 500) {
    //         alert('DENIED: Unable to update ' +
    //           renewal._links.self.href + '. Internal Server Error.');
    //       }
    //     });
    // }


    // // tag::on-delete[]
    // onDelete(renewal) {
    //     client(
    //        {method: 'DELETE', path: renewal._links.self.href}
    //     ).done(response => {
    //        /* let the websocket handle updating the UI */
    //        alert("You successfully deleted " +renewal.property+ "Use the back button and refresh the Renewal list to view the changes");
    //     },
    //     response => {
    //         if (response.error){
    //              alert("Somthing went wrong "+ reponse.error)
    //         }else if (response.status.code === 400) {
    //             alert('SERVER ERROR: You request was not completed ' +
    //                     renewal._links.self.href);
    //         }else if (response.status.code === 403) {
    //             alert('ACCESS DENIED: You are not authorized to delete ' +
    //                     renewal._links.self.href);
    //         }else{
    //             alert("Somthing went wrong "+ reponse.error)
    //         }
    //     });
    // }
    // // end::on-delete[]
    
    render() {
      var item = "";
      if (this.state.renewal !== null) {
        item = <Detail renewal={this.state.renewal}
                       task={this.state.task}
                    messages={this.state.messages}
                    cannedMessages={this.state.cannedMessages}
                    onRefreshMessages={this.handleRefreshMessages}
                    displayForm={this.state.displayForm}
                    displayMessages={this.state.displayMessages}
                    onUpdateNote={this.onUpdateNote}
                    onSelectItem={this.handleSelectedItem}
                    displayInfo={this.state.displayInfo}
                    displayLine={this.state.displayLine}
                    onDelete={this.onDelete}/>           
      }

      return (
          <div>

            <div style={{display: this.state.displayList}}>
              <List renewals={this.state.renewals}
                links={this.state.links}
                pageSize={this.state.pageSize}
                onNavigate={this.onNavigate}
                onUpdateNote={this.onUpdateNote}
                updatePageSize={this.updatePageSize}
                onSelectItem={this.handleSelectedItem}
                onFilterPriority={this.handleFilterPriority}
                onFilterAll={this.handleFilterAll}
                onFilterStarted={this.handleFilterStarted}
                onFilterState={this.handlefilterState}/>
            </div>

            <div style={{display: this.state.displayDetail}}>
              <div className="top-bar show-for-medium small-12 columns">
               <div className="top-bar-left">
                 <ul className="menu">
                   <li className="topbar-title">
                     Task Detail
                   </li>
                 </ul>
               </div>
               <div className="top-bar-right">
                 <ul className="menu">
                   {/*<li className="topbar-title">*/}
                   {/*  <a className="button small" onClick={this.handleToggleClick}>&lt;Details&gt;</a>*/}
                   {/*</li>                   */}
                   <li className="topbar-title">
                     <a className="button" onClick={this.handleBackClick}>Back</a>
                   </li>
                 </ul>
               </div>
              </div>
              <div>
                  {item}
              </div>
            </div>

          </div>
      )
    }
}
// end::app[]

module.exports = home;
