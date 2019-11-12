var path = require('path');
var webpack = require('webpack');

module.exports = {
    entry: [
	    'script!jquery/dist/jquery.min.js',
	    'script!foundation-sites/dist/foundation.min.js',
	    './src/main/js/app.jsx'
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
            Main: 'src/main/js/components/Main.jsx',
            Nav: 'src/main/js/components/Nav.jsx',
            IFrame: 'src/main/js/components/IFrame.jsx',
            DisplayDate: 'src/main/js/components/DisplayDate.jsx',
            DisplayDateTime: 'src/main/js/components/DisplayDateTime.jsx',
            FilterBar: 'src/main/js/components/FilterBar.jsx',
            MarketingBar: 'src/main/js/components/MarketingBar.jsx',
            FooterBar: 'src/main/js/components/FooterBar.jsx',
            LeaseMain: 'src/main/js/components/LeaseMain.jsx',
            LeaseLine: 'src/main/js/components/LeaseLine.jsx',
            LeaseList: 'src/main/js/components/LeaseList.jsx',
            LeaseDetail: 'src/main/js/components/LeaseDetail.jsx',
            LeaseForm: 'src/main/js/components/LeaseForm.jsx',
            LeaseInfo: 'src/main/js/components/LeaseInfo.jsx',
            UpdateNoteDialog: 'src/main/js/components/UpdateNoteDialog.jsx',
            MessageLine: 'src/main/js/components/MessageLine.jsx',
            MessageDetail: 'src/main/js/components/MessageDetail.jsx',
            MessageList: 'src/main/js/components/MessageList.jsx',
            MessageFilterBar: 'src/main/js/components/MessageFilterBar.jsx',
            TenantMain: 'src/main/js/components/TenantMain.jsx',
            TenantLine: 'src/main/js/components/TenantLine.jsx',
            TenantList: 'src/main/js/components/TenantList.jsx',
            TenantDetail: 'src/main/js/components/TenantDetail.jsx',
            TenantFilterBar: 'src/main/js/components/TenantFilterBar.jsx',
            Tenant: 'src/main/js/components/Tenant.jsx',
            CannedMessageMain: 'src/main/js/components/CannedMessageMain.jsx',
            CannedMessageLine: 'src/main/js/components/CannedMessageLine.jsx',
            CannedMessageList: 'src/main/js/components/CannedMessageList.jsx',
            CannedMessageDetail: 'src/main/js/components/CannedMessageDetail.jsx',
            Import: 'src/main/js/components/Import.jsx',
            ImportLeaseForm: 'src/main/js/components/ImportLeaseForm.jsx',
            ImportTenantForm: 'src/main/js/components/ImportTenantForm.jsx',
            ImportCannedMessageForm: 'src/main/js/components/ImportCannedMessageForm.jsx',
            ImportLeaseMain: 'src/main/js/components/ImportLeaseMain.jsx',
            ImportTenantMain: 'src/main/js/components/ImportTenantMain.jsx',
            ImportCannedMessageMain: 'src/main/js/components/ImportCannedMessageMain.jsx',
            uriListConverter: 'src/main/js/api/uriListConverter.js',
            uriTemplateInterceptor: 'src/main/js/api/uriTemplateInterceptor.js',
            applicaitonStyles: 'src/main/resources/static/main.css'
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
