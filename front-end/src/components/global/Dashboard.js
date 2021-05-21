import React, { useState, useEffect } from 'react'
import axios from 'axios';
import { Row, Col } from 'antd';
import ReimbursementCard from './ReimbursementCard';

function Dashboard(props) {

    const [pendingReimbursements, setPendingReimbursements] = useState([]);
    const [resolvedReimbursements, setResolvedReimbursements] = useState([]);

    

    useEffect(() => {
        const {user} = props;
        const {type,id} = user;
        let tempUrl = "";
        if (type === "Employee") {
            tempUrl = `http://localhost:8080/api/reimbursements/employeeId/${id}`;
        } else {
            tempUrl = "http://localhost:8080/api/reimbursements";
        }
        axios({
            method: "get",
            "url": tempUrl
        }).then((response) => {
            const tempList = response.data.reimbursements;
            setPendingReimbursements(tempList.filter((temp) => temp.status === "Pending"));
            setResolvedReimbursements(tempList.filter((temp) => temp.status === "Resolved"));
        }).catch((error) => {
            console.log(error.response.data);
        })
    }, [props])


    return (
        <Row style={{ marginTop: "50px" }} justify="center" align="top" gutter={[24, 24]}>
            <Col>
                <ReimbursementCard user={props.user} listTitle="Pending" reimbursementList={pendingReimbursements} />
            </Col>
            <Col>
                <ReimbursementCard user={props.user} listTitle="Resolved" reimbursementList={resolvedReimbursements} />
            </Col>
        </Row>
    )
}

export default Dashboard
