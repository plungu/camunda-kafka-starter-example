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

            DisplayDate: 'src/main/js/reactjs/renewals/components/date/DisplayDate.jsx',
            DisplayDateTime: 'src/main/js/reactjs/renewals/components/date/DisplayDateTime.jsx',

            FilterBar: 'src/main/js/reactjs/renewals/components/FilterBar.jsx',
            MarketingBar: 'src/main/js/reactjs/renewals/components/MarketingBar.jsx',
            FooterBar: 'src/main/js/reactjs/renewals/components/FooterBar.jsx',

            RenewalMain: 'src/main/js/reactjs/renewals/components/renewal/RenewalMain.jsx',
            RenewalLine: 'src/main/js/reactjs/renewals/components/renewal/RenewalLine.jsx',
            RenewalList: 'src/main/js/reactjs/renewals/components/renewal/RenewalList.jsx',
            RenewalDetail: 'src/main/js/reactjs/renewals/components/renewal/RenewalDetail.jsx',
            RenewalForm: 'src/main/js/reactjs/renewals/components/renewal/RenewalForm.jsx',
            RenewalInfo: 'src/main/js/reactjs/renewals/components/renewal/RenewalInfo.jsx',

            UpdateNoteDialog: 'src/main/js/reactjs/renewals/components/note/UpdateNoteDialog.jsx',

            MessageLine: 'src/main/js/reactjs/renewals/components/message/MessageLine.jsx',
            MessageDetail: 'src/main/js/reactjs/renewals/components/message/MessageDetail.jsx',
            MessageList: 'src/main/js/reactjs/renewals/components/message/MessageList.jsx',
            MessageFilterBar: 'src/main/js/reactjs/renewals/components/message/MessageFilterBar.jsx',

            TenantMain: 'src/main/js/reactjs/renewals/components/tenant/TenantMain.jsx',
            TenantLine: 'src/main/js/reactjs/renewals/components/tenant/TenantLine.jsx',
            TenantList: 'src/main/js/reactjs/renewals/components/tenant/TenantList.jsx',
            TenantDetail: 'src/main/js/reactjs/renewals/components/tenant/TenantDetail.jsx',
            TenantFilterBar: 'src/main/js/reactjs/renewals/components/tenant/TenantFilterBar.jsx',
            Tenant: 'src/main/js/reactjs/renewals/components/tenant/Tenant.jsx',

            CannedMessageMain: 'src/main/js/reactjs/renewals/components/canned-message/CannedMessageMain.jsx',
            CannedMessageLine: 'src/main/js/reactjs/renewals/components/canned-message/CannedMessageLine.jsx',
            CannedMessageList: 'src/main/js/reactjs/renewals/components/canned-message/CannedMessageList.jsx',
            CannedMessageDetail: 'src/main/js/reactjs/renewals/components/canned-message/CannedMessageDetail.jsx',

            Import: 'src/main/js/reactjs/renewals/components/Import.jsx',
            ImportRenewalForm: 'src/main/js/reactjs/renewals/components/import/ImportRenewalForm.jsx',
            ImportTenantForm: 'src/main/js/reactjs/renewals/components/import/ImportTenantForm.jsx',
            ImportCannedMessageForm: 'src/main/js/reactjs/renewals/components/import/ImportCannedMessageForm.jsx',
            ImportRenewalMain: 'src/main/js/reactjs/renewals/components/import/ImportRenewalMain.jsx',
            ImportTenantMain: 'src/main/js/reactjs/renewals/components/import/ImportTenantMain.jsx',
            ImportCannedMessageMain: 'src/main/js/reactjs/renewals/components/import/ImportCannedMessageMain.jsx',

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
