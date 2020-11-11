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
        displayDetail: "block",
        displayServiceForm: "block",
        displayServiceDetailForm: "none",
        displayServiceSupplierForm: "none"
      };
      this.updatePageSize = this.updatePageSize.bind(this);
      this.handleSelectedItem = this.handleSelectedItem.bind(this);
      this.handleBackClick = this.handleBackClick.bind(this);
      this.toggleForm = this.toggleForm.bind(this)
  }

  // tag::update-page-size[]
  updatePageSize(pageSize) {
      if (pageSize !== this.state.pageSize) {
          this.loadFromServer(pageSize);
      }
  }
  // end::update-page-size[]

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

  handleSelectedItem(message){
    this.setState({
      message: message,
      displayDetail: "block",
    });
  }

  handleBackClick(e){
    this.setState({
      displayDetail: "none",
    });
  }

  render(){

      var displayServiceForm = this.state.displayServiceForm;
      var displayServiceDetailForm = this.state.displayServiceDetailForm;
      var displayServiceSupplierForm = this.state.displayServiceSupplierForm;

      var info = "";
      if (this.state.renewal !== null) {
         info =  <div style={{display: this.props.displayInfo}}>
                    <Info
                        renewal={this.props.renewal}
                        onUpdateNote={this.props.onUpdateNote}
                        onDelete={this.props.onDelete}/>
                    </div>
      }

    return (
      <div>

        <FilterBar toggleForm={this.toggleForm}
                     title=""/>

        {/*{info}*/}

        <div className="small-12 columns" style={{display: displayServiceForm}}>
            <ServiceForm renewal={this.props.renewal}
                   cannedMessages={this.props.cannedMessages}/>
        </div>

          <div className="small-12 columns" style={{display: displayServiceDetailForm}}>
            <ServiceDetailForm renewal={this.props.renewal}
                    cannedMessages={this.props.cannedMessages}/>
          </div>

          <div className="small-12 columns" style={{display: displayServiceSupplierForm}}>
            <ServiceSupplierForm renewal={this.props.renewal}
                    cannedMessages={this.props.cannedMessages}/>
          </div>

      </div>
    )
  }
}

module.exports = Detail;
