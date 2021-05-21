import React, {useState} from 'react'

import { Card, Row, Col} from 'antd';
import ProfileInformation from './ProfileInformation';
import ProfileForm from './ProfileForm';


function Profile(props) {

    const [isEditing, setIsEditing] = useState(false);
    return (
        <Row justify="center" style={{margin: "50px"}}>
            <Col>
                <Card>
                    {isEditing
                        ? <ProfileForm user={props.user} setUser={props.setUser} setIsEditing={setIsEditing}/>
                        : <ProfileInformation user={props.user} setIsEditing={setIsEditing}/>
                    }
                    
                </Card>
            </Col>
        </Row >
    )
}

export default Profile
