import { Layout, Menu, Row } from 'antd';
import React, { useState } from 'react'
import { Redirect } from 'react-router-dom';
import AppTitle from './AppTitle';
import Dashboard from './Dashboard';
import NewReimbursementCard from '../employee/NewReimbursementCard';
import Profile from './Profile';
import { LogoutOutlined, DashboardOutlined, FormOutlined, UserOutlined ,FileSearchOutlined} from '@ant-design/icons';
import SearchEmployee from '../manager/SearchEmployee';


const { Sider, Content } = Layout;

function ErsMenu() {


    const [user, setUser] = useState(JSON.parse(localStorage.getItem('user')));

    const [currentTab, setCurrentTab] = useState("dashboard");

    if (!user) {
        return (
            <Redirect to="/login" />
        );
    }


    const menuClick = (e) => {
        if (e.key === "logout") {
            localStorage.removeItem('user');
            setCurrentTab(e.key)
        } else {
            setCurrentTab(e.key);
        }
    }

    const menuSwitch = () => {
        switch (currentTab) {
            case 'reimbursement':
                return (<NewReimbursementCard user={user} />);
            case 'profile':
                return (<Profile user={user} setUser={setUser} />);
            case 'searchByEmployee':
                return (<SearchEmployee user={user}></SearchEmployee>);
            case 'logout':
                return (<Redirect to="/" />);
            default:
                return (<Dashboard user={user} />);
        }
    }

    return (
        <React.Fragment>
            <Sider style={{overflow: 'hidden'}}>
                <Row justify="center">
                    <AppTitle>

                    </AppTitle>
                </Row>
                <Menu
                    mode="inline"
                    theme="dark"
                    defaultSelectedKeys={['dashboard']}
                    onClick={(e) => menuClick(e)}
                >
                    <Menu.Item key="dashboard" >
                        <DashboardOutlined />
                        Dashboard
                    </Menu.Item>
                    {user.type === "Employee" &&
                        <Menu.Item key="reimbursement">
                            <FileSearchOutlined />
                            New Reimbursement
                        </Menu.Item>
                    }
                    {user.type === "Manager" &&
                        <Menu.Item key="searchByEmployee">
                            <FormOutlined/>
                            All Employees
                        </Menu.Item>
                    
                    }
                    <Menu.Item key="profile">
                        <UserOutlined />
                        Profile
                    </Menu.Item>
                    <Menu.Item key="logout">
                        <LogoutOutlined />
                        Log out
                    </Menu.Item>
                </Menu>
            </Sider>
            <Layout style={{overflow: 'scroll'}}>
                <Content>
                    {menuSwitch()}
                </Content>
            </Layout>

        </React.Fragment>
    )
}

export default ErsMenu;
