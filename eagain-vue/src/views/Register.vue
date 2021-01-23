<template>
    <div style="background:#00ffc9;">
        <Row type="flex" justify="center">
            <Col span="8" style="height:200px;"></Col>
        </Row>
        <Row type="flex" justify="center">
            <Col span="8" style="height:600px;">
                <Layout>
                    <Content style="display:flex; justify-content:center;background:#00ffc9">
                        <Card class="Card">
                            <br>
                            <Row type="flex" justify="center">
                                <h1>注册新用户</h1>
                                <Button type="text" @click="moveToLogin()">使用已有帐号登录</Button>                               
                            </Row>
                            <br>
                            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                                <FormItem label="邮箱" prop="email">
                                    <Input v-model="formValidate.email" placeholder="请输入邮箱"></Input>
                                </FormItem>
                                <FormItem label="验证码" prop="code">
                                    <Input v-model="formValidate.code" placeholder="请输入验证码"></Input>
                                    <Button type="success" ghost @click="getCode()">获取验证码</Button>
                                </FormItem>
                                <FormItem label="用户名" prop="name">
                                    <Input v-model="formValidate.name" placeholder="请输入用户名"></Input>
                                </FormItem>
                                <FormItem label="密码" prop="password">
                                    <Input type="password" v-model="formValidate.password" password placeholder="请输入密码" style="width: 200px" ></Input>                                    
                                </FormItem>                         
                             </Form>                             
                             <Row type="flex" justify="center" style="color:red" v-show="errMsg != ''">
                                 {{errMsg}}
                             </Row>
                             <Row type="flex" justify="center" style="color:green" v-show="mailSendMsg != ''">
                                {{mailSendMsg}}
                             </Row>
                             <br>
                             <Row type="flex" justify="center">
                                <Button type="success" long @click="register()">注册</Button>       
                             </Row>
                        </Card>
                    </Content>
                </Layout>
            </Col>
        </Row>
        <Row>
              <Col span="8" style="height:200px;"></Col>
        </Row>
    </div>
</template>


<script>
    export default {
        name: 'Register',
        data () {
            return {
                errMsg: '',
                mailSendMsg: '',
                formValidate: {
                    email: '',
                    code: '',
                    name: '',
                    password: ''
                },
                ruleValidate: {
                    email :[
                        { required: true, message : '邮箱不能为空', trigger: 'blur' }
                    ],
                    code :[
                        { required: true, message : '请输入验证码~', trigger: 'blur' }
                    ],
                    name: [
                        { required: true, message: '用户名不能为空', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '密码不能为空', trigger: 'blur' }                   
                    ],
                  
                  }
            }
        },
        methods: {
            register: function() {
                if (this.formValidate.email.length == 0) {
                    this.errMsg = '邮箱不能为空';
                }
                else if (this.formValidate.code.length == 0) {
                    this.errMsg = '请输入验证码~';
                }
                else if (this.formValidate.name.length == 0) {
                    this.errMsg = '用户名不能为空';
                }
                else if (this.formValidate.password.length == 0) {
                    this.errMsg = '密码不能为空';
                }
                else {
                   var app = this;
                   this.$http.post('/register', {
                       username: this.formValidate.name,
                       email: this.formValidate.email,
                       code: this.formValidate.code,
                       password: this.formValidate.password
                   }).then(
                       function(response) {                           
                            if (response.data.success == false) {
                                app.errMsg = response.data.errorMessage;
                            }
                            else {
                                //注册成功
                                app.errMsg = '';
                                //登录
                                app.$http.post('/login', {
                                    username: app.formValidate.name,
                                    password: app.formValidate.password
                                }).then(
                                    function(response){                         
                                        var token = response.headers.token;
                                        if (token) {
                                            app.errMsg = '';
                                            localStorage.setItem('token', token);      
                                            app.$router.push({ path: '/home'});        
                                        }
                                        else {
                                            app.errMsg = '用户名或密码错误';
                                        }
                                    },
                                    function(err) {
        
                                    }
                                );
                           }
                       },
                       function(err) {

                       }
                   );

                }
            },

            getCode: function() {
                //获取验证码                
                if (this.formValidate.email.length == 0) {
                    this.errMsg = '请输入邮箱~';
                    return;
                }
                this.$http.post('/sendCode', {
                    email: this.formValidate.email
                }).then(res=>{
                    if (res.data.success == false) {
                        this.errMsg = '邮件走丢了, 要不过会再试试~';
                    }
                    else {
                        this.errMsg = '';
                        this.mailSendMsg = '验证码发送成功，请前往邮箱查收';
                        setTimeout(this.removeMailSendMsg, 3000);
                    }
                })
            },

            removeMailSendMsg: function() {
                this.mailSendMsg = '';
            },

            moveToLogin: function() {
                this.$router.push({path: '/login'});
            }
           
        }
    }
</script>

<style scoped>
.Card {
    border-radius: 30px;
    width: 370px;
    box-shadow: 5px 5px 5px 5px #cccccc;
}
</style>