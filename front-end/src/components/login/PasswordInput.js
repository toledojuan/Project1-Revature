import React from 'react';

import {Form, Input} from 'antd';

import {LockOutlined} from '@ant-design/icons';

function PasswordInput(props) {
    return (
        <Form.Item
                label="Password"
                name="passowrd"
                rules={
                    [
                        {
                            required: true,
                            message: "Please input your password!"
                        }
                    ]
                }
            >
                <Input
                    placeholder="Password"
                    prefix={<LockOutlined className="site-form-item-icon" />}
                    type="password"
                    onChange={(e) => props.setPassword(e.target.value)}
                >
                </Input>
            </Form.Item>
    )
}

export default PasswordInput
