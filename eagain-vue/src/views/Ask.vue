<template>
    <div>
        <Navigation></Navigation>
        <div style="padding: 20px">
                
            <h2><Icon type="ios-add-circle-outline" size="30"/>发起问题</h2>
            <Divider />
            <h3>问题标题: </h3>
            <Input v-model="title" placeholder="输入问题标题..." style="width: 600px" />
            <span style="color:red;" v-show="titleEmpty">请输入标题</span>
            <Divider />
            <div class="markdownpro">
                <mavon-editor v-model="content"/>
            </div>
            <br>
            <Button type="text" @click="showTags=true" v-show="!showTags"><Icon type="md-add" size="20"/> 添加标签</Button>
            <Button type="text" @click="showTags=false" v-show="showTags"><Icon type="ios-arrow-up" size="20"/>收起</Button>
            <Tag v-for="(id, i) in selectedTagIds" :key=i :name="id" closable @on-close="removeTag">{{tagsId2Name[id]}}</Tag>
            <div v-show="showTags">
                <Tabs>
                    <TabPane v-for="(item, i) in tags" :key=i :label="item.type">
                        <Tag v-for="(tag, j) in  item.tags" :key=j :color="tag.color" size="large">
                            <Button type="text" @click="selectTag(tag.id)">{{tag.title}}</Button>
                        </Tag>
                    </TabPane>
                </Tabs>
            </div>
            <Button type="success" style="float:right" @click="ask()">发起问题</Button>
        </div>
    </div>
</template>
<script>
import Navigation from '@/components/Navigation'
import jwt from 'jsonwebtoken';
export default {
    name: 'Ask',
    components : {
        Navigation
    },
    data() {
        return {
            login: false,
            username: null,
            userId: 0,
            title: '',
            content: '',
            showTags: false,
            tags: [],
            tagsId2Name: [],
            selectedTagIds: [],
            titleEmpty: false          
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
        if (this.login == false) {
            this.$router.push({path: '/home'});
        }
        else {            
            this.$http.get('/tags/list', {headers:{'token':token}}).then(res=>{
                this.tags = res.data;
                this.tagsId2Name = new Array();
                
                var i, j;
                for (i = 0; i < this.tags.length; i++) {
                    for (j = 0; j < this.tags[i].tags.length; j++) {
                        var tag = this.tags[i].tags[j];
                        this.tagsId2Name[tag.id] = tag.title;
                    }
                }                
            })
        }
    },
    methods: {
        selectTag: function(tagId) {
            if (this.selectedTagIds.indexOf(tagId) == -1)
                this.selectedTagIds.push(tagId);
        },
        removeTag: function(event, name) {
            const index = this.selectedTagIds.indexOf(name);
            this.selectedTagIds.splice(index, 1);
        },

        ask: function() {
            if (this.title.length == 0) {
                this.titleEmpty = true;
            }
            else {
                this.$http.post('/questions/new', {
                    'creatorId': this.userId,
                    'title': this.title,
                    'content': this.content,
                    'tagIds': this.selectedTagIds
                }, {headers:{'token': localStorage.getItem("token")}}).then(res=>{
                    this.$router.push({path:'/questions/'+res.data});
                });
            }
        }
    }
}
</script>