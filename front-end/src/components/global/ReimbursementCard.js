import React from 'react'
import {Card} from 'antd';
import ReimbursementTable from './ReimbursementTable';

function ReimbursementCard(props) {
    return (
        <Card title={`${props.listTitle} Reimbursements`}>
            <ReimbursementTable user={props.user} reimbursementList={props.reimbursementList} type={props.listTitle}/>
        </Card>
    )
}

export default ReimbursementCard
