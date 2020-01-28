var path = require('path');
var webpack = require('webpack');

module.exports = {
    entry: [
	    'script!jquery/dist/jquery.min.js',
	    'script!foundation-sites/dist/foundation.min.js',
	    './src/main/js/reactjs/renewals/app.jsx'
	  ],
	externals: {
		jquery: 'jQuery'
	},
	plugins: [
	  new webpack.ProvidePlugin({
		  '$': 'jquery',
		  'jQuery': 'jquery'
	  })
    ],
    cache: false,
    debug: true,
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    resolve: {
        root: __dirname,
        alias: {
            Main: 'src/main/js/reactjs/renewals/components/Main.jsx',
            Nav: 'src/main/js/reactjs/renewals/components/Nav.jsx',
            IFrame: 'src/main/js/reactjs/renewals/components/IFrame.jsx',
            DisplayDate: 'src/main/js/reactjs/renewals/components/DisplayDate.jsx',
            DisplayDateTime: 'src/main/js/reactjs/renewals/components/DisplayDateTime.jsx',
            FilterBar: 'src/main/js/reactjs/renewals/components/FilterBar.jsx',
            MarketingBar: 'src/main/js/reactjs/renewals/components/MarketingBar.jsx',
            FooterBar: 'src/main/js/reactjs/renewals/components/FooterBar.jsx',
            LeaseMain: 'src/main/js/reactjs/renewals/components/LeaseMain.jsx',
            LeaseLine: 'src/main/js/reactjs/renewals/components/LeaseLine.jsx',
            LeaseList: 'src/main/js/reactjs/renewals/components/LeaseList.jsx',
            LeaseDetail: 'src/main/js/reactjs/renewals/components/LeaseDetail.jsx',
            LeaseForm: 'src/main/js/reactjs/renewals/components/LeaseForm.jsx',
            LeaseInfo: 'src/main/js/reactjs/renewals/components/LeaseInfo.jsx',
            UpdateNoteDialog: 'src/main/js/reactjs/renewals/components/UpdateNoteDialog.jsx',
            MessageLine: 'src/main/js/reactjs/renewals/components/MessageLine.jsx',
            MessageDetail: 'src/main/js/reactjs/renewals/components/MessageDetail.jsx',
            MessageList: 'src/main/js/reactjs/renewals/components/MessageList.jsx',
            MessageFilterBar: 'src/main/js/reactjs/renewals/components/MessageFilterBar.jsx',
            TenantMain: 'src/main/js/reactjs/renewals/components/TenantMain.jsx',
            TenantLine: 'src/main/js/reactjs/renewals/components/TenantLine.jsx',
            TenantList: 'src/main/js/reactjs/renewals/components/TenantList.jsx',
            TenantDetail: 'src/main/js/reactjs/renewals/components/TenantDetail.jsx',
            TenantFilterBar: 'src/main/js/reactjs/renewals/components/TenantFilterBar.jsx',
            Tenant: 'src/main/js/reactjs/renewals/components/Tenant.jsx',
            CannedMessageMain: 'src/main/js/reactjs/renewals/components/CannedMessageMain.jsx',
            CannedMessageLine: 'src/main/js/reactjs/renewals/components/CannedMessageLine.jsx',
            CannedMessageList: 'src/main/js/reactjs/renewals/components/CannedMessageList.jsx',
            CannedMessageDetail: 'src/main/js/reactjs/renewals/components/CannedMessageDetail.jsx',
            Import: 'src/main/js/reactjs/renewals/components/Import.jsx',
            ImportLeaseForm: 'src/main/js/reactjs/renewals/components/ImportLeaseForm.jsx',
            ImportTenantForm: 'src/main/js/reactjs/renewals/components/ImportTenantForm.jsx',
            ImportCannedMessageForm: 'src/main/js/reactjs/renewals/components/ImportCannedMessageForm.jsx',
            ImportLeaseMain: 'src/main/js/reactjs/renewals/components/ImportLeaseMain.jsx',
            ImportTenantMain: 'src/main/js/reactjs/renewals/components/ImportTenantMain.jsx',
            ImportCannedMessageMain: 'src/main/js/reactjs/renewals/components/ImportCannedMessageMain.jsx',
            uriListConverter: 'src/main/js/api/uriListConverter.js',
            uriTemplateInterceptor: 'src/main/js/api/uriTemplateInterceptor.js',
            applicaitonStyles: 'src/main/resources/static/app.css'
        },
        extensions: ['', '.js', '.jsx']
    },
    module: {
        loaders: [
            {
                loader: 'babel-loader',
                query: {
                    cacheDirectory: false,
                    presets: ['es2015', 'react', 'stage-0']
                },
                test: path.join(__dirname, '.'),
                exclude: /(node_modules|bower_components)/,
            },
            {loaders: ['style', 'css', 'sass'], test: /\.scss$/ }
        ]
    },
    devtool: ['cheap-module-source-map', 'sourcemaps']
};
