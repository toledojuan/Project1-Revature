import React from 'react';
import {Row,Col, Card} from 'antd';
import ReimbursementForm from './ReimbursementForm';

function NewReimbursementCard(props) {
    return (
        <Row style={{marginTop: "50px"}} justify ="center" align="top" gutter={[24,24]}>
            <Col>
                <Card title="Create New Reimbursement">
                    <ReimbursementForm user={props.user}/>
                </Card>
            </Col>
        </Row>
    )
}

export default NewReimbursementCard
