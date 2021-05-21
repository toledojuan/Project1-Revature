import React,{useState} from 'react'
import {Form,Input,Button} from 'antd';
import axios from 'axios';

function ProfileForm(props) {

    const [firstName,setFirstName] = useState(props.user.firstName);
    const [lastName,setLastName] = useState(props.user.lastName);
    const [username, setUsername] = useState(props.user.username);
    const [password,setPassword] = useState(props.user.password);
    const [email,setEmail] = useState(props.user.email);

    const saveChanges = () => {
        const type = props.user.type;
        const id = props.user.id;
        axios({
            method:"PUT",
            url: `http://localhost:8080/api/users/${props.user.id}`,
            data: {
                firstName,
                lastName,
                username,
                password,
                email,
                type
            }
        }).then((response)=>{
            const user = {
                id,
                firstName,
                lastName,
                username,
                password,
                email,
                type
            }
            props.setUser(user);
            localStorage.setItem('user',JSON.stringify(user));
            props.setIsEditing(false);
        }).catch((error)=>{
            console.log(error.response.data);
        })
        
    }

    const formItemLayout = {
        labelCol:{
            span: 10
        },
        wrapperCol: {
            span: 24
        }

    }

    return (
        <Form
            onFinish={saveChanges}
        >
            <Form.Item
                {...formItemLayout}
                label="First Name"
                rules={[
                    {
                        required: true,
                        message: "First Name field can't be empty"
                    }
                ]}
            >
                <Input
                    placeholder="First Name"
                    value={firstName}
                    onChange={
                        (e) =>{
                            setFirstName(e.target.value);
                        }
                    }
                >
                </Input>
            </Form.Item>
            <Form.Item
                {...formItemLayout}
                label="Last Name"
                rules={[
                    {
                        required: true,
                        message: "Last Name field can't be empty"
                    }
                ]}
            >
                <Input
                    placeholder="Last Name"
                    value={lastName}
                    onChange={
                        (e) => {
                            setLastName(e.target.value);
                        }
                    }
                >
                </Input>
            </Form.Item>
            <Form.Item
                {...formItemLayout}
                label="Email"
                rules={[
                    {
                        type: "email",
                        message: "The input is anot a valid email"
                    },
                    {
                        required: true,
                        message: "Email field can't be empty"
                    }
                ]}
            >
                <Input
                    placeholder="Email"
                    value = {email}
                    onChange={
                        (e)=>{
                            setEmail(e.target.value);
                        }
                    }
                >
                </Input>
            </Form.Item>
            <Form.Item
                {...formItemLayout}
                label="Username"
                rules={[
                    {
                        required: true,
                        message: "Username field can't be empty"
                    }
                ]}
            >
                <Input
                    placeholder="Username"
                    value={username}
                    onChange={
                        (e)=>{
                            setUsername(e.target.value);
                        }
                    }
                >
                </Input>
            </Form.Item>
            <Form.Item
                {...formItemLayout}
                label="Password"
                rules={[
                    {
                        required: true,
                        message: "Password field can't be empty"
                    }
                ]}
            >
                <Input
                    placeholder="Password"
                    value={password}
                    onChange={
                        (e)=>{
                            setPassword(e.target.value);
                        }
                    }
                    type="password"
                >
                </Input>
            </Form.Item>
            <Form.Item
                {...formItemLayout}
            >
                <Button
                    type="primary"
                    htmlType="submit"
                    shape="round"
                    style={{width: "100%"}}
                >
                    Save Changes
                </Button>
            </Form.Item>

        </Form>
    )
}

export default ProfileForm
