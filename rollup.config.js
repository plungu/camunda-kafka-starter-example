// rollup.config.js
import babel from "@rollup/plugin-babel";
import resolve from "@rollup/plugin-node-resolve";
import commonjs from "@rollup/plugin-commonjs";
import replace from "@rollup/plugin-replace";

export default {
    input: "./src/main/js/cockpit/plugins/ReactSamplePlugin.jsx",
    output: {
        file: "./src/main/resources/plugin-webapp/react-sample-plugin/app/plugin.js"
    },
    plugins: [
        resolve(),
        babel({"presets": ["@babel/preset-react"]}),
        commonjs({
            include: "node_modules/**"
        }),
        replace({
            "process.env.NODE_ENV": JSON.stringify("production")
        })
    ]
};