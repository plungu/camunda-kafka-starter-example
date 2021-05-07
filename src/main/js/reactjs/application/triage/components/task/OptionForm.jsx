/**
 * @author Paul Lungu
 * @type {{DOM, PropTypes, createElement, isValidElement, version, __spread, PureComponent, createMixin, createClass, Children, Component, createFactory, cloneElement}}
 */

'use strict';

// tag::nodeModules[]
var React = require('react');

// tag::customComponents

// tag::vars[]

// end::vars[]

class OptionForm extends React.Component {
        
  constructor(props) {
    super(props);
    this.state = {

    };
    this.handleChange = this.handleChange.bind(this);
  }

    handleChange(e){
        e.preventDefault();
        var policy = this.props.policy;

        var processing = this.refs.cpSendProcessing.value;
        var underwirting = this.refs.cpSendUnderwirting.value;

        console.log("Task -> OptionForm refs: " +processing+underwirting);
        // this.props.handleUpdatePolicy(policy);
    }

  render() {

    return (

        <div className="my-form option-form">
            <div className="small-12 large-12 small-offset-1 columns" >

                <div className="small-9 large-9">

                    <div className="input-group">
                        <span className="input-group-label">Cancelation of Policy</span>

                        <span className="input-label">Send to Processing</span>
                        <input className="input-group-field" type="checkbox"
                               ref="cpSendProcessing" onChange={this.handleChange}/>

                        <span className="input-label">Send to Underwriting</span>
                        <input className="input-group-field" type="checkbox"
                               ref="cpSendUnderwirting" onChange={this.handleChange}/>
                    </div>

                    <div className="input-group">
                        <span className="input-group-label">Extension Request</span>

                        <span className="input-label">Send to Processing</span>
                        <input className="input-group-field" type="checkbox"
                               ref="er-send-processing" onChange={this.handleChange}
                               value='' />
                        <span className="input-label">Send to Underwriting</span>
                        <input className="input-group-field" type="checkbox"
                               ref="er-send-underwirting" onChange={this.handleChange}
                               value='' />
                    </div>

                    <div className="input-group">
                        <span className="input-group-label">Loss Payee / Mortgage Changes</span>

                        <span className="input-label">Send to Processing</span>
                        <input className="input-group-field" type="checkbox"
                               ref="lpmc-send-processing" onChange={this.handleChange}
                               value='' />
                        <span className="input-label">Send to Underwriting</span>
                        <input className="input-group-field" type="checkbox"
                               ref="lpmc-send-underwirting" onChange={this.handleChange}
                               value='' />
                    </div>


                </div>

            </div>
        </div>

    );
  }
}

module.exports = OptionForm;
