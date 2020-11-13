// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom')
const client = require('../client.jsx');
const follow = require('../follow.jsx'); // function to hop multiple links by "rel"

const MessageList = require('src/main/js/reactjs/service-request/components/message/MessageList.jsx');
const MessageDetail = require('src/main/js/reactjs/service-request/components/message/MessageDetail.jsx');
const Form = require('src/main/js/reactjs/service-request/components/task/Form.jsx');
const Tenant = require('src/main/js/reactjs/service-request/components/tenant/Tenant.jsx');
const Info = require('src/main/js/reactjs/service-request/components/task/Info.jsx');
const Line = require('src/main/js/reactjs/service-request/components/task/Line.jsx');

// end::vars[]

class Detail extends React.Component{
  constructor(props) {
      super(props);
      this.state = {
        message: null,
        messages: [],
        attributes: [],
        pageSize: 2,
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

    var tenants = [];
    // var tenants = this.props.renewal.tennants.map(tenant =>
    //     <Tenant key={tenant.email}
    //         tenant={tenant}/>
    // );

    return (
      <div>


                                
        <div>
            <Info
                tenants={tenants}
                renewal={this.props.renewal}
                task={this.props.task}
                onUpdateNote={this.props.onUpdateNote}
                onDelete={this.props.onDelete}/>
        </div>
                            

                  
        <div >
          <hr />
          <Form renewal={this.props.renewal} />
        </div>

      </div>
    )
  }
}

module.exports = Detail;
