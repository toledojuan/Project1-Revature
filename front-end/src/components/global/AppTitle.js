import React from 'react'

import {blue} from '@ant-design/colors';
import {Typography} from 'antd';

const {Title} =  Typography;

function AppTitle() {
    return (
        <Title style={{color: blue[6]}}>ERS</Title>
    )
}

export default AppTitle
