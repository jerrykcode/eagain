<template>
<div class="Navigation">
    <Menu mode="horizontal" :theme="theme1" active-name="1">
        <MenuItem name="search">
            <Input search placeholder="输入搜索的内容..." />
        </MenuItem>
        <MenuItem name="register">
            <Button type="success" ghost  @click="moveToAsk()">提问</Button>
        </MenuItem>
        <MenuItem name="register" v-show="login == false">
            <Button type="success" ghost  @click="moveToRegister()">注册</Button>
        </MenuItem>
        <MenuItem name="login" v-show="login == false">
            <Button type="success" ghost  @click="moveToLogin()">登录</Button>
        </MenuItem>
        <Submenu name="center" v-show="login == true && username != null">
            <template slot="title">
                <Icon type="md-person" size="18"/>              
                {{username}}
            </template>
            <MenuGroup title="">
                <MenuItem>                    
                    <Button type="text">个人中心</Button>
                </MenuItem>
                <MenuItem>
                    <Button type="text" @click="logout()">退出登录</Button>
                </MenuItem>
            </MenuGroup>
        </Submenu>                            
    </Menu>
</div>
</template>

<script>
import jwt from 'jsonwebtoken';
export default {
    name: 'Navigation',
    data() {
        return {
            login: false,
            username: null,
            userId: 0,
            theme1: 'light'
        }
    },
    created() {
        var token = localStorage.getItem("token");
        if (token != null) {
            this.login = true;
            var jwtStr = jwt.decode(token); 
            this.username = jwtStr.username;
            this.userId = jwtStr.id;
        }
    },
    methods: {
        moveToRegister: function() {
            this.$router.push({path: '/register'});
        },
        moveToLogin: function() {
            this.$router.push({path: '/login'});
        },
        moveToAsk: function() {
            if (this.login) {
                this.$router.push({path: '/ask'});
            }
            else {
                alert('请先登录');
            }
        },
        logout: function() {
            var token = localStorage.getItem("token");
            this.$http.get("/logout", {headers:{'token':token}}).then(res=>{});
            localStorage.removeItem('token'); 
            this.$router.push({path:'/home'});
        },
    }
}
</script>