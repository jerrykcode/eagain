<template>
    <div>
        <Navigation></Navigation>
        <div class="demo-split">
            <Split v-model="split1">                
                <div slot="left" class="demo-split-pane">
                    <Card>
                    <h2>问题列表</h2>
                    <br/>
                    <Card v-for="(question, i) in questions" :key="i">
                        <a :href="'/#/questions/'+question.questionId" style="font-size: larger;">{{question.title}}</a>
                        <br/>
                        <span style="color: gray; font-size: smaller;">
                            {{question.creatorName}}
                            发布于{{datetimeFormat(question.gmtCreate)}} | {{question.answersCount}}回答 | {{question.viewsCount}}阅读 |  {{question.likesCount}}喜欢
                        </span>
                        <br/>
                        <span style="font-size: small;">{{question.breviary}} ...</span>
                        <a :href="'/#/questions/'+question.questionId" style="font-size: small;">>></a>
                    </Card>
                    <Page :total="numQuestions" size="small" :page-size="numPerPage" :page-size-opts="[5, 10, 20]"
                        show-elevator show-sizer @on-change="changePage" @on-page-size-change = "changePageSize" />
                    </Card>
                </div>
                <div slot="right" class="demo-split-pane">
                    
                </div>
            </Split>
        </div>       
    </div>
</template>

<script>
import Navigation from '@/components/Navigation'
export default {
    name: 'Home',
    components: {
        Navigation
    },
    data() {
        return {
            split1: 0.7,
            numQuestions: 0,
            questions: [],
            numPerPage: 10,
            currentPageNo: 1,
        }
    },
    created() {
        this.$http.get("/questions/count/all").then(res=>{
            this.numQuestions = res.data;
        });
        this.getQuestions(this.currentPageNo);
    },
    methods: {
        getQuestions: function(pageNo) {
            this.$http.get("/questions/list?pageNo="+pageNo+"&numPerPage="+this.numPerPage).then(res=>{ 
                this.questions = res.data;
            });
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
        changePage: function(pageNo) {
            this.getQuestions(pageNo);
        },
        changePageSize: function(pageSize) {
            this.numPerPage = pageSize;
            this.getQuestions(1);
        }
    }
}
</script>
<style>
    .demo-split{
        height: 2000px;
        border: 1px solid #dcdee2;
    }
    .demo-split-left {
        padding: 20px;
    }
    .demo-split-right{
        padding: 20px;
    }
</style>
