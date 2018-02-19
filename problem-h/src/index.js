import React from 'react';
import { render } from 'react-dom';
import * as OfflinePluginRuntime from 'offline-plugin/runtime';

import App from './app';

render(<App />, document.getElementById('app'));

OfflinePluginRuntime.install();
