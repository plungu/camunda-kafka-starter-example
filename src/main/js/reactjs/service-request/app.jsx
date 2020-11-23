'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom')
const {Route, Router, IndexRoute, hashHistory} = require('react-router');
const Main = require('Main');
const Home = require('./components/home/Home');
const RejectHome = require('./components/home/RejectHome');
const Task = require('./components/task/Home');
const Detail = require('./components/home/Detail');
// const RejectDetail = require('./components/home/RejectDetail');

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
          <Route path="rejected" component={RejectHome}/>
        </Route>
      </Router>,
      document.getElementById('react')
)
// end::render[]
