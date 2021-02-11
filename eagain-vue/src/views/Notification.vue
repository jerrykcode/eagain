<template>
    <div>
        <Navigation></Navigation>
        <List>
            <ListItem v-for="(content, i) in notificationContents" :key = i>
                {{content.content}}
                <Button type="success" ghost size="small" @click="moveToLink(content.link)">查看>></Button>
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
        this.$http.get("/notifications/read?pageNo=1&numPerPage=5", {headers:{'token':token}}).then(res=>{          
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
    },
    methods: {
        moveToLink: function(link) {          
            this.$router.push({path: link});
            location.reload();
        }
    }
}
</script>