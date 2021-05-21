import React from 'react'

import {Descriptions,Button} from 'antd';

function ProfileInformation(props) {
    return (
        <Descriptions title="Profile Information" bordered extra={
            <Button type="primary" onClick={()=>props.setIsEditing(true)}>Edit</Button>
        }>
            <Descriptions.Item label="First Name">{props.user.firstName}</Descriptions.Item>
            <Descriptions.Item label="Last Name" span={2}>{props.user.lastName}</Descriptions.Item>
            <Descriptions.Item label="Email" span={3}>{props.user.email}</Descriptions.Item>
            <Descriptions.Item label="Username" span={3}>{props.user.username}</Descriptions.Item>
            <Descriptions.Item label="Password" span={3}>**********</Descriptions.Item>          
        </Descriptions>
    )
}

export default ProfileInformation
