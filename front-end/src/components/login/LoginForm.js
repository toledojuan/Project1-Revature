import React, { useState } from 'react'
import { Form,Alert, Button } from 'antd';
import axios from 'axios';
import {useHistory} from 'react-router-dom';
import UsernameInput from './UsernameInput';
import PasswordInput from './PasswordInput';

function LoginForm() {


    let history = useHistory();
    const [username,setUsername] = useState(null);
    const [password,setPassword] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);
    const [hasFailed, setHasFailed] = useState(false);

    const handleSubmit = (e) => {

        axios({
            method: "post",
            url: "http://localhost:8080/api/users/login",
            data: {
                username,
                password
            }
        }).then((response) => {
            const user = response.data;
            localStorage.setItem('user',JSON.stringify(user));
            history.push('menu');
        }).catch((error) => {
            setHasFailed(true);
            setErrorMessage(error.response.data.error);
        })
    }



    return (
        <Form
            layout="vertical"
            size="large"
            onFinish={handleSubmit}
        >
            <UsernameInput username={username} setUsername={setUsername}/>
            <PasswordInput password={password} setPassword={setPassword}/>
            {hasFailed &&
                <Form.Item>
                    <Alert type="error" message={errorMessage} banner />
                </Form.Item>
            }

            <Form.Item>
                <Button
                    type="primary"
                    htmlType="submit"
                    shape="round"
                >
                    Log In
                </Button>
            </Form.Item>

            
        </Form>
    )
}

export default LoginForm
