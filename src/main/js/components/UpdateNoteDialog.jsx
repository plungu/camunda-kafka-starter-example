const ReactDOM = require('react-dom');
const React = require('react');
const {Link, IndexLink} = require('react-router');

class UpdateNoteDialog extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        if(e.keyCode == 13){
            var updatedLease = {};
            if(this.refs.note.value){
                updatedLease.note = this.refs.note.value;
                updatedLease.leaseId = this.props.lease.id;
            }
            this.props.onUpdateNote(this.props.lease, updatedLease);
        }
    }

    handleHover(e){
        e.preventDefault();
        
    }
    
    render() {

        return (
            <div>
                <a>
                <form onKeyUp={this.handleSubmit}>
                    <input type="text" ref="note" defaultValue={this.props.lease.note} />
                </form>
                </a>
                <div className="note">{this.props.lease.note}</div>
            </div>    
        )
    }

};

module.exports = UpdateNoteDialog;
