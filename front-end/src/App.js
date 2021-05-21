import React from 'react';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Redirect
} from "react-router-dom";
import 'antd/dist/antd.dark.css';
import {Layout} from 'antd';

import ErsMenu from './components/global/ErsMenu';
import Login from './components/login/Login';

const App = () => {


    return (
        <Layout style={{height: '100vh'}}>
            <Router>
                <Switch>
                    <Route path="/login">
                        <Login/>
                    </Route>
                    <Route path="/menu">
                        <ErsMenu/>
                    </Route>
                    <Route>
                        <Redirect to="/login"/>
                    </Route>
                </Switch>
            </Router>
        </Layout>
    )
}

export default App;
