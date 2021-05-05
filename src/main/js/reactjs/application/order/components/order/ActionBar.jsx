const React = require('react');

// tag::vars[]
const apiHost = process.env.API_HOST != "" ? `${process.env.API_HOST}:${process.env.API_PORT}/` : "/";
const apiRoot = `${apiHost}${process.env.API_ROOT}`;
// end::vars[]

class ActionBar extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
        };
        this.handleConfirm = this.handleConfirm.bind(this);
    }


    handleConfirm(e){
        e.preventDefault();

        var obj = this.props.order;

        console.log("Order -> ActionBar -> handleConfirm: " + JSON.stringify(obj));
        //post the object to the endpoint
        this.props.post("PATCH", {started: true}, `api/orders/${obj.id}`)
        this.props.post('POST', {"messageName" : "start-pm-order-approval", "businessKey" : obj.orderKey }, `engine-rest/message`);

        this.props.toggleConfirm();

    }

  render(){

    return (
        <div className="top-bar">
            <div className="top-bar-right columns">
                <ul className="menu my-bar">
                    <li>
                        <a className="button small my-button" key="confirm" onClick={this.handleConfirm}>Confirm</a>
                    </li>
                </ul>
            </div>
        </div>
    )
  }
}

module.exports = ActionBar;
