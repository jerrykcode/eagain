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
                <span style="color: gray; font-size: small;"><Button text size="small">{{viewsCount}}阅读</Button> | 
                    <Button text size="small">{{answersCount}}回答</Button> | 
                    <Button text size="small">{{focusesCount}}关注</Button> | 
                    <Button text size="small" @click="likeQuestion()"><Icon type="ios-heart-outline"/>{{likesCount}}喜欢</Button>   发布于 {{dateCreate}} 最后编辑于 {{dateModified}}  </span>              
                <Divider />
                <Button type="success" ghost @click="answerQuestion=true"><Icon type="ios-create-outline" size="20" /> 回答问题</Button>
                <div class="markdownpro" v-show="answerQuestion">
                    <br>
                    <mavon-editor v-model="answerContent"/>
                    <br>
                    <span style="color:red;" v-show="answerContentEmpty">回答没有任何内容</span>
                    <Button type="success" style="float:right" @click="answerThisQuestion()"><Icon type="ios-send-outline" size="20"/>提交回答</Button>
                    <br>                    
                </div>
				<Button type="success" ghost @click="moveToQuestion()"><Icon type="ios-create-outline" size="20" /> 查看全部{{answersCount}}个回答</Button>
				<Divider />
                <Card>
                    <h3>{{answer.creatorName}}的回答</h3>
                    <div class="markdown-body">
                        <vue-markdown v-highlight="true" :source="answer.answer.content"></vue-markdown>
                    </div>
                    <span style="color: gray; font-size: small;">发布于{{datetimeFormat(answer.answer.gmtCreate)}} 
                        | {{answer.answer.viewsCount}}阅读 | 
                        {{answer.answer.likesCount}}喜欢<Button text size="small" @click="likeAnswer(answer.answer)"><Icon type="ios-heart-outline"/></Button> 
                        | {{answer.answer.collectionsCount}}收藏</span>
                </Card>
                   
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
    name: 'Answer',
    components: {
        Navigation,
        VueMarkdown
    },
    data() {
        return {
            split1: 0.7,
            questionId: 0,
			answerId: 0,
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
            answer: null,            
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
		this.answerId = this.$route.params.id;
		this.$http.get('/answers/query', {
			params :{
				'answerId': this.answerId
			}
		}).then(res=>{
			this.answer = res.data;
		});
        this.questionId = this.answer.answer.questionId;
		console.log('111');
		console.log(this.questionId);
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
       
    },
    methods: {
		moveToQuestion: function() {           
            this.$router.push({path: '/questions/' + this.questionId});
        },
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

        likeQuestion: function() {
            this.$http.post('/like/question/'+this.questionId, {}, {headers:{'token': localStorage.getItem('token')}}).then(res=>{              
                this.likesCount = res.data;
            });
        },

        likeAnswer: function(answer) {
            this.$http.post('/like/answer/'+answer.id, {}, {headers:{'token': localStorage.getItem('token')}}).then(res=>{
                answer.likesCount = res.data;
            });
        },

        answerThisQuestion: function() {
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
