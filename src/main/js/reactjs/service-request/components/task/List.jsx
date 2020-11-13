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
							<th>Task Id</th>
							<th width="105">Sourcing Manager</th>
							<th width="105">Service Description</th>
							<th>Acquiring Division</th>
							<th>Service Category</th>
							<th>Sourcing Comments</th>
							<th>Service Id</th>
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
