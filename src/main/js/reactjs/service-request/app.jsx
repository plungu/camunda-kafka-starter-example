'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom')
const {Route, Router, IndexRoute, hashHistory} = require('react-router');
const Main = require('Main');
const Home = require('./components/home/Home');
const Task = require('./components/task/Home');
const TenantMain = require('./components/tenant/TenantMain');
const ImportData = require('./components/import/Import');
const Detail = require('./components/home/Detail');
const TenantDetail = require('./components/tenant/TenantDetail');
const CannedMessageMain = require('./components/canned-message/CannedMessageMain');

// tag::styles[]
require('style!css!foundation-sites/dist/foundation.css');
$(document).foundation();

//require('style!css!../../main/resources/static/app.css');
// end::styles[]

// tag::render[]
ReactDOM.render(
      <Router history={hashHistory}>
        <Route path="/" component={Main}>
          <IndexRoute component={Home}/>
          <Route path="home" component={Detail}/>
          <Route path="service" component={Detail}/>
          <Route path="tasks" component={Task}/>
          <Route path="tenants" component={TenantMain}/>
          <Route path="tenant" component={TenantDetail}/>
          <Route path="cannedMessages" component={CannedMessageMain}/>
          <Route path="import" component={ImportData}/>
        </Route>
      </Router>,
      document.getElementById('react')
)
// end::render[]
