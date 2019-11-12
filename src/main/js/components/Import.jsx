const React = require('react');

const ImportLeaseMain = require('ImportLeaseMain');
const ImportTenantMain = require('ImportTenantMain');
const ImportCannedMessageMain = require('ImportCannedMessageMain');

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
          <ImportLeaseMain />

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
