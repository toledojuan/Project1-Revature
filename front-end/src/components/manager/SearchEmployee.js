import React, { useState, useEffect } from 'react'
import axios from 'axios';
import { Row, Col, Card, Table, Button } from 'antd';
import ReimbursementTable from '../global/ReimbursementTable';

const { Column } = Table;

function SearchEmployee(props) {

    const [employees, setEmployees] = useState([]);
    const [isSelected, setIsSelected] = useState(false);
    const [tempReimbursements, setTempReimbursements] = useState([]);


    useEffect(() => {

        axios({
            method: "GET",
            url: "http://localhost:8080/api/users"
        }).then((response) => {
            setEmployees(response.data.users.filter((temp) => temp.type === "Employee"));
        }).catch((error) => {
            console.log(error);
        });
    }, []);



    const getPendingReimbursements = (record) => {

        axios({
            method: "GET",
            url: `http://localhost:8080/api/reimbursements/employeeId/${record.id}`
        }).then((response) => {
            setTempReimbursements(response.data.reimbursements);
        }).catch((error) => {
            console.log(error.response.data);
        });
    };


    return (
        <Row justify="center" style={{ marginTop: "50px" }}>
            <Col>
                <Card>
                    {
                        !isSelected &&
                        <Table dataSource={employees} rowKey="id">
                            <Column title="First Name" dataIndex="firstName" key="firstName" />
                            <Column title="Last Name" dataIndex="lastName" key="lastName" />
                            <Column title="Email" dataIndex="email" key="email" />
                            <Column title="Action"
                                render={
                                    (text, record) => (
                                        <Button type="primary" shape="round"
                                            onClick={() => {
                                                setIsSelected(true);
                                                getPendingReimbursements(record);
                                            }}
                                        >
                                            See Pending Reimbursements
                                        </Button>
                                    )
                                }
                            />
                        </Table>
                    }

                    {
                        isSelected &&
                        <ReimbursementTable user={props.user} reimbursementList={tempReimbursements} type="Pending"/>
                    }

                </Card>
            </Col>
        </Row>
    )
}

export default SearchEmployee;
