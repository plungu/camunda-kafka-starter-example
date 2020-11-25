var path = require('path');
var webpack = require('webpack');
var Dotenv = require('dotenv-webpack');

module.exports = {
    entry: [
        'babel-polyfill',
	    'script!jquery/dist/jquery.min.js',
	    'script!foundation-sites/dist/foundation.min.js',
	    './src/main/js/reactjs/service-request/app.jsx'
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
            Main: 'src/main/js/reactjs/service-request/components/Main.jsx',
            Nav: 'src/main/js/reactjs/service-request/components/Nav.jsx',
            IFrame: 'src/main/js/reactjs/service-request/components/IFrame.jsx',

            DisplayDate: 'src/main/js/reactjs/service-request/components/date/DisplayDate.jsx',
            DisplayDateTime: 'src/main/js/reactjs/service-request/components/date/DisplayDateTime.jsx',

            MarketingBar: 'src/main/js/reactjs/service-request/components/MarketingBar.jsx',
            StatusBar: 'src/main/js/reactjs/service-request/components/StatusBar.jsx',
            FooterBar: 'src/main/js/reactjs/service-request/components/FooterBar.jsx',

            Home: 'src/main/js/reactjs/service-request/components/home/Home.jsx',
            RejectHome: 'src/main/js/reactjs/service-request/components/home/RejectHome.jsx',
            Line: 'src/main/js/reactjs/service-request/components/home/Line.jsx',
            List: 'src/main/js/reactjs/service-request/components/home/List.jsx',
            Detail: 'src/main/js/reactjs/service-request/components/home/Detail.jsx',
            RejectDetail: 'src/main/js/reactjs/service-request/components/home/RejectDetail.jsx',
            ServiceStartForm: 'src/main/js/reactjs/service-request/components/home/ServiceStartForm.jsx',
            ServiceSelectForm: 'src/main/js/reactjs/service-request/components/home/ServiceSelectForm.jsx',
            ServiceForm: 'src/main/js/reactjs/service-request/components/home/ServiceForm.jsx',
            ServiceDetailForm: 'src/main/js/reactjs/service-request/components/home/ServiceDetailForm.jsx',
            ServiceSupplierForm: 'src/main/js/reactjs/service-request/components/home/ServiceSupplierForm.jsx',
            Info: 'src/main/js/reactjs/service-request/components/home/Info.jsx',
            FilterBar: 'src/main/js/reactjs/service-request/components/home/FilterBar.jsx',

            Task: 'src/main/js/reactjs/service-request/components/task/Home.jsx',
            TaskLine: 'src/main/js/reactjs/service-request/components/task/Line.jsx',
            TaskList: 'src/main/js/reactjs/service-request/components/task/List.jsx',
            TaskDetail: 'src/main/js/reactjs/service-request/components/task/Detail.jsx',
            TaskForm: 'src/main/js/reactjs/service-request/components/task/Form.jsx',
            TaskInfo: 'src/main/js/reactjs/service-request/components/task/Info.jsx',
            TaskFilterBar: 'src/main/js/reactjs/service-request/components/task/FilterBar.jsx',

            UpdateNoteDialog: 'src/main/js/reactjs/service-request/components/note/UpdateNoteDialog.jsx',

            MessageLine: 'src/main/js/reactjs/service-request/components/message/MessageLine.jsx',
            MessageDetail: 'src/main/js/reactjs/service-request/components/message/MessageDetail.jsx',
            MessageList: 'src/main/js/reactjs/service-request/components/message/MessageList.jsx',
            MessageFilterBar: 'src/main/js/reactjs/service-request/components/message/MessageFilterBar.jsx',

            TenantMain: 'src/main/js/reactjs/service-request/components/tenant/TenantMain.jsx',
            TenantLine: 'src/main/js/reactjs/service-request/components/tenant/TenantLine.jsx',
            TenantList: 'src/main/js/reactjs/service-request/components/tenant/TenantList.jsx',
            TenantDetail: 'src/main/js/reactjs/service-request/components/tenant/TenantDetail.jsx',
            TenantFilterBar: 'src/main/js/reactjs/service-request/components/tenant/TenantFilterBar.jsx',
            Tenant: 'src/main/js/reactjs/service-request/components/tenant/Tenant.jsx',

            CannedMessageMain: 'src/main/js/reactjs/service-request/components/canned-message/CannedMessageMain.jsx',
            CannedMessageLine: 'src/main/js/reactjs/service-request/components/canned-message/CannedMessageLine.jsx',
            CannedMessageList: 'src/main/js/reactjs/service-request/components/canned-message/CannedMessageList.jsx',
            CannedMessageDetail: 'src/main/js/reactjs/service-request/components/canned-message/CannedMessageDetail.jsx',

            Import: 'src/main/js/reactjs/service-request/components/Import.jsx',
            ImportRenewalForm: 'src/main/js/reactjs/service-request/components/import/ImportRenewalForm.jsx',
            ImportTenantForm: 'src/main/js/reactjs/service-request/components/import/ImportTenantForm.jsx',
            ImportCannedMessageForm: 'src/main/js/reactjs/service-request/components/import/ImportCannedMessageForm.jsx',
            ImportRenewalMain: 'src/main/js/reactjs/service-request/components/import/ImportRenewalMain.jsx',
            ImportTenantMain: 'src/main/js/reactjs/service-request/components/import/ImportTenantMain.jsx',
            ImportCannedMessageMain: 'src/main/js/reactjs/service-request/components/import/ImportCannedMessageMain.jsx',

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
