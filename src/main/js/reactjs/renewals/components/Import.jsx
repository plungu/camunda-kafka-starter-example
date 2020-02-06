const React = require('react');

const ImportRenewalMain = require('src/main/js/reactjs/renewals/components/ImportRenewalMain.jsx');
const ImportTenantMain = require('src/main/js/reactjs/renewals/components/ImportTenantMain.jsx');
const ImportCannedMessageMain = require('src/main/js/reactjs/renewals/components/ImportCannedMessageMain.jsx');

class Import extends React.Component{

  render(){
      return (
        <div>
          <h3>Formatting is important. Please follow the examples below to succesfully import CSV data.</h3>
          <h3>Headers are used to map the columns and should be printed exactly as shown.</h3>
          <br/>    
          <ImportTenantMain />              

          <br/>    
          <br/>    
          <ImportRenewalMain />

          <br/>    
          <br/>    
          <ImportCannedMessageMain />
          <br/>    
          <br/>  
        </div>
      )
  }
}

module.exports = Import;
