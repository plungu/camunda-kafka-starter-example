var path = require('path');
var webpack = require('webpack');
var Dotenv = require('dotenv-webpack');

module.exports = {
    entry: [
        'babel-polyfill',
	    'script!jquery/dist/jquery.min.js',
	    'script!foundation-sites/dist/foundation.min.js',
	    './src/main/js/reactjs/application/app.jsx'
	  ],
	externals: {
		jquery: 'jQuery'
	},
	plugins: [
	  new webpack.ProvidePlugin({
		  '$': 'jquery',
		  'jQuery': 'jquery'
	  }),
      new Dotenv()
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
            //Generic Reusable Components
            client: 'src/main/js/reactjs/application/components/client.jsx',
            follow: 'src/main/js/reactjs/application/components/follow.jsx',
            IFrame: 'src/main/js/reactjs/application/components/IFrame.jsx',
            UpdateNoteDialog: 'src/main/js/reactjs/application/components/note/UpdateNoteDialog.jsx',
            DisplayDate: 'src/main/js/reactjs/application/components/date/DisplayDate.jsx',
            DisplayDateTime: 'src/main/js/reactjs/application/components/date/DisplayDateTime.jsx',

            MessageLine: 'src/main/js/reactjs/application/components/message/MessageLine.jsx',
            MessageDetail: 'src/main/js/reactjs/application/components/message/MessageDetail.jsx',
            MessageList: 'src/main/js/reactjs/application/components/message/MessageList.jsx',
            MessageFilterBar: 'src/main/js/reactjs/application/components/message/MessageFilterBar.jsx',

            TenantMain: 'src/main/js/reactjs/application/components/tenant/TenantMain.jsx',
            TenantLine: 'src/main/js/reactjs/application/components/tenant/TenantLine.jsx',
            TenantList: 'src/main/js/reactjs/application/components/tenant/TenantList.jsx',
            TenantDetail: 'src/main/js/reactjs/application/components/tenant/TenantDetail.jsx',
            TenantFilterBar: 'src/main/js/reactjs/application/components/tenant/TenantFilterBar.jsx',
            Tenant: 'src/main/js/reactjs/application/components/tenant/Tenant.jsx',

            Import: 'src/main/js/reactjs/application/components/Import.jsx',
            ImportRenewalForm: 'src/main/js/reactjs/application/components/import/ImportRenewalForm.jsx',
            ImportTenantForm: 'src/main/js/reactjs/application/components/import/ImportTenantForm.jsx',
            ImportCannedMessageForm: 'src/main/js/reactjs/application/components/import/ImportCannedMessageForm.jsx',
            ImportRenewalMain: 'src/main/js/reactjs/application/components/import/ImportRenewalMain.jsx',
            ImportTenantMain: 'src/main/js/reactjs/application/components/import/ImportTenantMain.jsx',
            ImportCannedMessageMain: 'src/main/js/reactjs/application/components/import/ImportCannedMessageMain.jsx',

            uriListConverter: 'src/main/js/api/uriListConverter.js',
            uriTemplateInterceptor: 'src/main/js/api/uriTemplateInterceptor.js',

            applicaitonStyles: 'src/main/resources/static/app.css',
            // END Generic Reusable Components

            // Use Case Components
            Main: 'src/main/js/reactjs/application/triage/components/Main.jsx',
            Nav: 'src/main/js/reactjs/application/triage/components/Nav.jsx',

            MarketingBar: 'src/main/js/reactjs/application/triage/components/MarketingBar.jsx',
            StatusBar: 'src/main/js/reactjs/application/triage/components/StatusBar.jsx',
            FooterBar: 'src/main/js/reactjs/application/triage/components/FooterBar.jsx',

            Home: 'src/main/js/reactjs/application/triage/components/home/Home.jsx',
            FilterBar: 'src/main/js/reactjs/application/triage/components/home/FilterBar.jsx',
            ActionBar: 'src/main/js/reactjs/application/triage/components/home/ActionBar.jsx',

            RejectHome: 'src/main/js/reactjs/application/triage/components/home/RejectHome.jsx',
            RejectDetail: 'src/main/js/reactjs/application/triage/components/home/RejectDetail.jsx',
            Line: 'src/main/js/reactjs/application/triage/components/home/Line.jsx',
            List: 'src/main/js/reactjs/application/triage/components/home/List.jsx',
            Detail: 'src/main/js/reactjs/application/triage/components/home/Detail.jsx',

            ServiceStartForm: 'src/main/js/reactjs/application/triage/components/home/ServiceStartForm.jsx',
            ServiceSelectForm: 'src/main/js/reactjs/application/triage/components/home/ServiceSelectForm.jsx',
            ServiceForm: 'src/main/js/reactjs/application/triage/components/home/ServiceForm.jsx',
            ServiceDetailForm: 'src/main/js/reactjs/application/triage/components/home/ServiceDetailForm.jsx',
            ServiceSupplierForm: 'src/main/js/reactjs/application/triage/components/home/ServiceSupplierForm.jsx',

            Info: 'src/main/js/reactjs/application/triage/components/home/Info.jsx',

            Task: 'src/main/js/reactjs/application/triage/components/task/Home.jsx',
            TaskLine: 'src/main/js/reactjs/application/triage/components/task/Line.jsx',
            TaskList: 'src/main/js/reactjs/application/triage/components/task/List.jsx',
            TaskDetail: 'src/main/js/reactjs/application/triage/components/task/Detail.jsx',
            TaskForm: 'src/main/js/reactjs/application/triage/components/task/Form.jsx',
            TaskInfo: 'src/main/js/reactjs/application/triage/components/task/Info.jsx',
            TaskFilterBar: 'src/main/js/reactjs/application/triage/components/task/FilterBar.jsx',
            // END Use Case Components

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
