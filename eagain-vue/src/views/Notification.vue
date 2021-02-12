<template>
    <div>
        <Navigation></Navigation>
        <List>
            <ListItem v-for="(content, i) in notificationContents" :key = i>
                {{content.content}}
                <Button type="success" ghost size="small" @click="moveToLink(content.link)">查看>></Button>
            </ListItem>
            <ListItem>
                <Button long @click="more()">查看更多...</Button>
            </ListItem>
        </List>
    </div>
</template>
<script>
import Navigation from '@/components/Navigation'
import jwt from 'jsonwebtoken';
export default {
    name: 'Notification',
    components : {
        Navigation
    },
    data() {
        return {
            login: false,
            username: null,
            userId: 0,
            notificationContents: [],
            lastPage: 0
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
        this.lastPage = 1;
        this.getNotifications(this.lastPage, token);
    },
    methods: {
        moveToLink: function(link) {          
            this.$router.push({path: link});
            location.reload();
        },
        more: function(){
            var token = localStorage.getItem("token");
            this.lastPage++;
            this.getNotifications(this.lastPage, token);
        },
        getNotifications: function(pageNo, token) {
            this.$http.get("/notifications/read?pageNo="+pageNo+"&numPerPage=5", {headers:{'token':token}}).then(res=>{         
            var notifications = res.data;                          
            for (var i = 0; i < notifications.length; i++) {    
                var element = notifications[i];                   
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
        }
    }
}
</script>