import React from 'react';

import {Form, Input} from 'antd';

import {UserOutlined} from '@ant-design/icons';

function UsernameInput(props) {
    return (
        <Form.Item
                label="Username"
                name="username"
                rules={
                    [
                        {
                            required: true,
                            message: "Please input your username!"
                        }
                    ]
                }
        >
            <Input
                placeholder="Username"
                prefix={<UserOutlined className="site-form-item-icon" />}
                onChange={(e) => props.setUsername(e.target.value)}
            />
        </Form.Item>
    )
}

export default UsernameInput
