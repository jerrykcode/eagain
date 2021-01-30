<template>
    <div>
        <Navigation></Navigation>
        <div class="demo-split">
        <Split v-model="split1">
            <div slot="left" class="demo-split-left">
                <Card>
                <h2>{{title}}</h2>
                <br>
                <Card>
                <div class="markdown-body">
                    <vue-markdown v-highlight="true" :source="content"></vue-markdown>
                </div>
                </Card>
                <br>
                <span v-show="tags.length > 0">
                    <Tag v-for="(tag, i) in tags" :key=i :color="tag.color">{{tag.title}}</Tag>
                </span>
                <br>
                <span style="color: gray; font-size: small;">阅读量:{{viewsCount}} | 关注数:{{focusesCount}}    发布于 {{dateCreate}} 最后编辑于 {{dateModified}}  </span>
                <Divider />
                </Card>
            </div>
            <div slot="right" class="demo-split-right">
                <h3>提问者</h3> {{creatorName}}
            </div>            
        </Split>
        </div>
    </div>
</template>

<script>
import Navigation from '@/components/Navigation'
import jwt from 'jsonwebtoken';
import VueMarkdown from 'vue-markdown'
export default {
    name: 'Question',
    components: {
        Navigation,
        VueMarkdown
    },
    data() {
        return {
            split1: 0.7,
            questionId: 0,
            login: false,
            username: null,
            userId: 0,
            creatorName: '',
            title: '',
            content: '',
            tags: [],
            dateCreate: '',
            dateModified: '',
            viewsCount: 0,
            likesCount: 0,
            focusesCount: 0
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
        this.questionId = this.$route.params.id;       
        this.$http.get('/questions/query/'+this.questionId).then(res=>{
            //console.log(res);
            this.creatorName = res.data.creatorName;
            this.title = res.data.title;
            this.content = res.data.content;
            this.tags = res.data.tags;            
            this.dateCreate = this.datetimeFormat(res.data.gmtCreate);
            this.dateModified = this.datetimeFormat(res.data.gmtModified);
            this.viewsCount = res.data.viewsCount;
            this.likesCount = res.data.likesCount;
            this.focusesCount = res.data.focusesCount;
        })
    },
    methods: {
        datetimeFormat: function(longTypeDate){
            var date = new Date(longTypeDate)
            var y = date.getFullYear()
            var m = date.getMonth() + 1
            m = m < 10 ? ('0' + m) : m
            var d = date.getDate()
            d = d < 10 ? ('0' + d) : d
            var h = date.getHours()
            h = h < 10 ? ('0' + h) : h
            var minute = date.getMinutes()
            var second = date.getSeconds()           
            minute = minute < 10 ? ('0' + minute) : minute
            second = second < 10 ? ('0' + second) : second
            return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second
        }
    }
}
</script>
<style>
    .demo-split{
        height: 800px;
        border: 1px solid #dcdee2;
    }
    .demo-split-left {
        padding: 20px;
    }
    .demo-split-right{
        padding: 20px;
    }
</style>
