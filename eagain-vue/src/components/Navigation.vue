<template>
<div class="Navigation">
    <Menu mode="horizontal" :theme="theme1" active-name="1">
        <MenuItem name="search">
            <Input search placeholder="输入搜索的内容..." />
        </MenuItem>
        <MenuItem name="register">
            <Button type="success" ghost  @click="moveToAsk()">提问</Button>
        </MenuItem>
        <MenuItem name="notification">
            <Button text @click="unreadNotifications()">
            <Icon type="ios-notifications-outline" />消息
            </Button>
            <Drawer title="Basic Drawer" :closable="false" v-model="showNotifications">
                <span v-show="notifications.length == 0">暂无新消息</span>
                <span v-for="(content, i) in notificationContents" :key=i>
                    {{content.content}}
                    <Button type="success" ghost size="small" @click="moveToLink(content.link)">查看>></Button>
                    <Divider/>
                </span>
                <br>
                <Button text @click="histroyNotifications()">查看历史消息</Button>
            </Drawer>
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
            theme1: 'light',
            showNotifications: false,
            notifications: [],
            notificationContents: []
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
    watch :{
  　'$route': function (to, from) {
　　        this.changePage();
　      }
    },
    methods: {
        moveToLink: function(link) {           
            this.$router.push({path: link});
            location.reload();
        },
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
        unreadNotifications: function() {
            this.showNotifications = true;
            var token = localStorage.getItem("token");
            this.$http.get("/notifications/unread", {headers:{'token':token}}).then(res=>{
                this.notifications = res.data;
                this.notificationContents.length = 0;               
                for (var i = 0; i < this.notifications.length; i++) {    
                    var element = this.notifications[i];                   
                    var content = element.senderName;
                    var link = '';
                    if (element.notificationType == "like")
                        content += "喜欢";
                    else if (element.notificationType == "comment")
                        content += "评论";
                    content += "了你的";
                    if (element.dbModelType == "question") {
                        content += "问题";
                        link += '/questions/';
                    }
                    else if (element.dbModelType == "answer") {
                        content += "回答";
                        link += '/answers/';
                    }
                    link += element.modelId;
                    this.notificationContents.push({
                        'content': content,
                        'link' : link
                    });
                }                
            });
            if (this.notifications.length > 0) {
                this.$http.get("/notifications/handle", {headers: {'token': token}}).then(res=>{

                })
            }
        },
        histroyNotifications: function() {
            this.$router.push({path: '/notifications'});
        }
    }
}
</script>