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
                <Button type="success" ghost @click="answerQuestion=true"><Icon type="ios-create-outline" size="20" /> 回答问题</Button>
                <div class="markdownpro" v-show="answerQuestion">
                    <br>
                    <mavon-editor v-model="answerContent"/>
                    <br>
                    <span style="color:red;" v-show="answerContentEmpty">回答没有任何内容</span>
                    <Button type="success" style="float:right" @click="answer()"><Icon type="ios-send-outline" size="20"/>提交回答</Button>
                    <br>                    
                </div>
                <Divider />
                <h3>{{answersCount}}个回答</h3>
                <Card v-for="(answer, i) in answers" :key=i>
                    <h3>{{answer.creatorName}}</h3>
                    <div class="markdown-body">
                        <vue-markdown v-highlight="true" :source="answer.answer.content"></vue-markdown>
                    </div>
                    <span style="color: gray; font-size: small;">发布于{{datetimeFormat(answer.answer.gmtCreate)}} 
                        | {{answer.answer.viewsCount}}阅读 | {{answer.answer.likesCount}}喜欢 
                        | {{answer.answer.collectionsCount}}收藏</span>
                </Card>
                    <Page :total="17" size="small" :page-size="numPerPage" :page-size-opts="[5, 10, 20]"
                         show-elevator show-sizer @on-change="changePage" @on-page-size-change = "changePageSize" />
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
            answersCount: 0,
            viewsCount: 0,
            likesCount: 0,
            focusesCount: 0,
            answerQuestion: false,
            answerContent: '',
            answerContentEmpty: false,
            answers: [],            
            numPerPage: 5
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
            this.creatorName = res.data.creatorName;
            this.title = res.data.title;
            this.content = res.data.content;
            this.tags = res.data.tags;            
            this.dateCreate = this.datetimeFormat(res.data.gmtCreate);
            this.dateModified = this.datetimeFormat(res.data.gmtModified);
            this.answersCount = res.data.answersCount;
            this.viewsCount = res.data.viewsCount;
            this.likesCount = res.data.likesCount;
            this.focusesCount = res.data.focusesCount;
        });        
    },
    mounted() {
        this.getAnswers(1);
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
        },

        answer: function() {
            if (this.answerContent.length == 0) {
                this.answerContentEmpty = true;
            }
            else {
                this.$http.post('/answers/new', {
                    'questionId': this.questionId,
                    'questionTitle': this.title,
                    'creatorId': this.userId,
                    'content': this.answerContent
                }, {headers:{'token': localStorage.getItem('token')}}).then(res=>{
                    this.answerQuestion = false;
                    this.getAnswers(1);
                })
            }
        },

        getAnswers: function(pageNo) {
            this.$http.get('/answers/list', {
                params :{
                    'questionId': this.questionId,
                    'pageNo': pageNo,
                    'numPerPage': this.numPerPage
                }
            }).then(res=>{
                this.answers = res.data;
            });            
        },

        changePage: function(pageNo) {
            this.getAnswers(pageNo);
        },

        changePageSize: function(pageSize) {
            this.numPerPage = pageSize;
            this.getAnswers(1);
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
