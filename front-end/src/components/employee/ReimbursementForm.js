import React, { useState } from 'react';
import { Form, Input, Button, Alert } from 'antd';
import axios from 'axios';

function ReimbursementForm(props) {

    const [description, setDescription] = useState(null);
    const [cost, setCost] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [isDisabled, setIsDisabled] = useState(false);
    const [hasSucceded, setHasSucceded] = useState(false);
    const [hasFailed, setHasFailed] = useState(false);
    const [responseMessage, setResponseMessage] = useState(null);


    const submitReimbursement = () => {
        setHasFailed(false);
        setHasSucceded(false);
        const employeeId = props.user.id;
        const status = "Pending";
        axios({
            method: "post",
            url: "http://localhost:8080/api/reimbursements",
            data: {
                description,
                cost,
                employeeId,
                status
            }
        }).then((response) => {
            setIsDisabled(true);
            setIsLoading(false);
            setHasSucceded(true);
            setResponseMessage(response.data.success);
        }).catch((error) => {
            setHasFailed(true);
            setIsLoading(false);
            setResponseMessage(error.response.data.error);
        })
    }

    return (
        <Form
            layout="vertical"
            labelCol={{ span: 16 }}
            onFinish={submitReimbursement}
        >
            <Form.Item
                label="Description"
                name="description"
                rules={[{ required: true, message: "Please insert a description of the Reimbursement" }]}
            >
                <Input
                    placeholder="Description"
                    onChange={(e) => {
                        setDescription(e.target.value);
                    }}
                />
            </Form.Item>
            <Form.Item
                label="Amount"
                name="amount"
                rules={[{ required: true, message: "Please insert the amount of the Reimbursement" }]}
            >
                <Input
                    placeholder="Enter the amount"
                    prefix="$"
                    onChange={(e) => {
                        setCost(e.target.value);
                    }}
                />
            </Form.Item>

            {hasFailed &&
                <Form.Item>
                    <Alert type="error" message={responseMessage} banner />
                </Form.Item>
            }

            {hasSucceded &&
                <Form.Item>
                    <Alert type="success" message={responseMessage} banner />
                </Form.Item>
            }

            <Form.Item>
                <Button type="primary" style={{ width: "100%" }} htmlType="submit"
                    loading={isLoading} onClick={() => setIsLoading(true)}
                    disabled={isDisabled}
                    shape="round"
                >
                    Submit
                </Button>
            </Form.Item>
        </Form>
    )
}

export default ReimbursementForm
