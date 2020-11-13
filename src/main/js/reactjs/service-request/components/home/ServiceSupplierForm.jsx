var React = require('react');
const client = require('../client.jsx');
const DisplayDate = require('src/main/js/reactjs/service-request/components/date/DisplayDate.jsx');
const follow = require('../follow.jsx'); // function to hop multiple links by "rel"

class ServiceForm extends React.Component {
        
  constructor(props) {
    super(props);
    this.state = {

    };
    this.handleDone = this.handleDone.bind(this);
    this.handleSave = this.handleSave.bind(this);
  }


    handleSave(e){
        e.preventDefault();

        console.log("HandleSave: " )
    }

    handleDone(e){
        e.preventDefault();

        console.log("HandleDone: " )
    }

  render() {

    return (
      <div className="row">
          <div className="small-12 columns">

                <div className="row">
                    <div className="small-5 columns">
                        <div className="input-group">
                            <span className="input-group-label">Supplier</span>
                            <input className="input-group-field" type="text" ref="supplier"/>
                        </div>
                    </div>
                </div>

          </div>
      </div>

    );
  }
}

module.exports = ServiceForm;
