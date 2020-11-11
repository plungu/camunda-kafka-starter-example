'use strict';

// tag::vars[]
const React = require('react');

const Line = require('src/main/js/reactjs/service-request/components/task/Line.jsx');

const FilterBar = require('src/main/js/reactjs/service-request/components/task/FilterBar.jsx');

// tag::renewal-list[]
class List extends React.Component{
  constructor(props) {
      super(props);
  }

  render() {
		var renewals = this.props.renewals.map(renewal =>
			<Line key={renewal._links.self.href}
        renewal={renewal}
		onUpdateNote={this.props.onUpdateNote}
        onSelectItem={this.props.onSelectItem}/>
		);

		return (
		    <div>
				<FilterBar links={this.props.links}
                                   pageSize={this.props.pageSize}
                                   onNavigate={this.props.onNavigate}
		                           updatePageSize={this.props.updatePageSize}
                                   onFilterPriority={this.props.onFilterPriority}
                                   onFilterAll={this.props.onFilterAll}
                                   onFilterStarted={this.props.onFilterStarted}
                                   onFilterState={this.props.onFilterState}
                                   title="Filter Tasks"/>

				<div>
					<table className="hover stack">
					  <thead>
						<tr>
							<th>Task Name</th>
							<th width="105">RAG Status</th>
							<th width="105">Assigned Date</th>
							<th>Due Date</th>
							<th>Task Id</th>
							<th>Service Id</th>
							<th>Service Name</th>
							<th>Supplier Name</th>
                        </tr>
					  </thead>
					  <tbody>
                        {renewals}
                      </tbody>
					</table>
				</div>

    		</div>
		)
	}
}
// end::renewal-list[]

module.exports = List;
