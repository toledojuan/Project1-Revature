import React, { useState } from 'react'

import { Table, Button, Modal, Divider, Typography} from 'antd';
import {useHistory} from 'react-router-dom';
import axios from 'axios';
const { Text } = Typography;

const { Column } = Table;

function ReimbursementTable(props) {


    const [tempRecord, setTempRecord] = useState({});
    const [showModal, setShowModal] = useState(false);
    const [tempEmployee, setTempEmployee] = useState({});

    let history = useHistory();

    const handleSubmission = (resolution) => {
        let url = `http://localhost:8080/api/reimbursements/${tempRecord.id}`;

        axios({
            method: "PUT",
            url,
            data:{
                ...tempRecord, 
                "status": "Resolved",
                "resolution": resolution,
                "managerId": props.user.id
            }
        }).then((response) => {
            console.log(response);
            setShowModal(false);
            history.go(0);
        
        }).catch((error) => {
            console.log(error.response.data);
        })
    };


    const createModal = (record) => {
        let url = `http://localhost:8080/api/users/${record.employeeId}`;

        axios({
            method: "get",
            url
        }).then((response) => {
            setTempEmployee(response.data);
            setShowModal(true);
            setTempRecord(record);
        }).catch((error) => {
            console.log(error.response.data);
        })
    };


    const showUserInfo = (userId, userType) => {

        let url = `http://localhost:8080/api/users/${userId}`;

        axios({
            method: "get",
            url
        }).then((response) => {
            Modal.info({
                title: `${userType} Information`,
                content: (
                    <React.Fragment>
                        <Divider orientation="left">
                            Name
                        </Divider>
                        <p>{response.data.firstName} {response.data.lastName}</p>
                        <Divider orientation="left">
                            Email
                        </Divider>
                        <p>{response.data.email}</p>
                    </React.Fragment>
                )
            });
        }).catch((error) => {
            console.log(error.response.data);
        })
    }



    return (
        <React.Fragment>
            <Table dataSource={props.reimbursementList} rowKey="id">
                <Column title="Description" dataIndex="description" key="description" />
                <Column title="Cost" dataIndex="cost" key="cost" render={
                    (text, record) => (`$ ${record.cost}`)
                } />
                <Column title="Date" dataIndex="date" key="date" />
                {props.type === "Resolved" &&

                    <Column title="Resolution" dataIndex="resolution" key="resolution"
                        render={
                            (text, record) => {
                                if (record.resolution === "Approved") {
                                    return <Text type="success">{record.resolution}</Text>
                                } else {
                                    return <Text type="danger">{record.resolution}</Text>
                                }
                            }
                        }
                    />
                }
                {props.user.type === "Manager" && props.type === "Resolved" &&
                    <React.Fragment>
                        <Column title="Made By" dataIndex="employeeId" key="FemployeeId"
                            render={
                                (text, record) => (
                                    <Button type="primary" shape="round" onClick={() => showUserInfo(record.employeeId, "Employee")}>See</Button>
                                )
                            }
                        />

                        <Column title="Resolved By" dataIndex="managerId" key="managerId"
                            render={
                                (text, record) => (
                                    <Button type="primary" shape="round" onClick={() => showUserInfo(record.managerId, "Manager")}>See</Button>
                                )
                            }
                        />
                    </React.Fragment>
                }

                {props.user.type === "Manager" && props.type === "Pending" &&

                    <Column title="Action"
                        render={
                            (text, record) => (
                                <Button type="primary" shape="round" onClick={() => {
                                    createModal(record);
                                }}>Resolve</Button>
                            )
                        }
                    />

                }
            </Table>
            <Modal
                visible={showModal}
                title="Reimbursement Information"
                onCancel={() => setShowModal(false)}
                onOk={() => setShowModal(false)}
                footer={[
                    <Button key="Approved" type="primary"
                        onClick={() => {
                            handleSubmission("Approved");
                        }}
                    >
                        Approve
                    </Button>,
                    <Button
                        key="Denied"
                        type="danger"
                        onClick={() => {
                            handleSubmission("Denied");
                        }}
                    >
                        Deny
                    </Button>,
                    <Button key="back" onClick={() => setShowModal(false)}>
                        Return
                    </Button>,
                ]}
            >
                <Divider orientation="left">Description</Divider>
                <p>{tempRecord.description}</p>
                <Divider orientation="left">Cost</Divider>
                <p>$ {tempRecord.cost}</p>
                <Divider orientation="left">Date</Divider>
                <p>{tempRecord.date}</p>
                <Divider orientation="left">Employee</Divider>
                <p>{tempEmployee.firstName} {tempEmployee.lastName}</p>
            </Modal>
        </React.Fragment>

    )
}

export default ReimbursementTable
