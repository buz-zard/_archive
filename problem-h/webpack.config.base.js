const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

const { paths } = require('./webpack.config.constants');

module.exports = {
  entry: path.join(paths.SRC, 'index.js'),
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: 'babel-loader',
      },
      {
        test: /\.scss$/,
        use: ExtractTextPlugin.extract({
          fallback: 'style-loader',
          use: ['css-loader', 'sass-loader'],
        }),
      },
    ],
  },
  plugins: [
    new ExtractTextPlugin('[name].[hash].css'),
    new webpack.EnvironmentPlugin(['NODE_ENV']),
  ],
};
