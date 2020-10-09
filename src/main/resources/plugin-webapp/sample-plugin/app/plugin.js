// plugin.js
export default {
  id: "myDemoPlugin",
  label: "Hello World",
  pluginPoint: "cockpit.dashboard",
  priority: 10,

  render: (node, { api }) => {
    node.innerHTML = `Hello World! `;
  }
};