const path = require('path');
const merge = require('webpack-merge');
const HtmlWebpackPlugin = require('html-webpack-plugin');

const { paths } = require('./webpack.config.constants');
const baseConfig = require('./webpack.config.base');

module.exports = merge.smart(baseConfig, {
  devtool: 'source-map',
  plugins: [
    new HtmlWebpackPlugin({
      template: path.join(paths.SRC, 'index.html'),
    }),
  ],
});
