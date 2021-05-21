import React from 'react'


import {Redirect} from 'react-router-dom';
import {Layout,Card, Row} from 'antd';
import AppTitle from '../global/AppTitle';
import LoginForm from './LoginForm';


const{Header, Content} = Layout;

function Login() {

    const user = JSON.parse(localStorage.getItem('user'));

    if(user){
        return(
            <Redirect to={`/${user.type}`}/>
        )
    }
    
    return (
        <React.Fragment>
            <Header>
                <AppTitle/>
            </Header>
            <Content>
                <Row justify="center" 
                    style={{margin: "50px 0px"}} 
                >
                    <Card 
                        title="LOG IN"
                    >
                        <LoginForm/>
                    </Card>
        
                </Row> 
            </Content>
        </React.Fragment> 
    )
}

export default Login
