'use strict';
// plugin.js


// const React = require('react');
// const ReactDOM = require('react-dom')

import React from "react";
import ReactDOM from "react-dom";

let container;

export default {
    id: "myReactDemoPlugin",
    label: "React Hello World",
    pluginPoint: "cockpit.dashboard",
    priority: 9,

    render: (node, { api }) => {

    container = node;
    ReactDOM.render(
      React.createElement("div", null, "This is rendered in React: ", api.CSRFToken),
      container
    );
  },

  unmount: () => {
    ReactDOM.unmountComponentAtNode(container);
  }

};