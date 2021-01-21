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
                                <FormItem label="用户名" prop="name">
                                    <Input v-model="formValidate.name" placeholder="请输入用户名"></Input>
                                </FormItem>
                                <FormItem label="邮箱" prop="email">
                                    <Input v-model="formValidate.email" placeholder="请输入邮箱"></Input>
                                </FormItem>
                                <FormItem label="密码" prop="password">
                                    <Input type="password" v-model="formValidate.password" password placeholder="请输入密码" style="width: 200px" ></Input>                                    
                                </FormItem>                         
                             </Form>                             
                             <Row type="flex" justify="center" style="color:red" v-show="errMsg != ''">
                                 {{errMsg}}
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
                formValidate: {
                    name: '',
                    password: ''
                },
                ruleValidate: {
                    name: [
                        { required: true, message: '用户名不能为空', trigger: 'blur' }
                    ],
                    email :[
                        { required: true, message : '邮箱不能为空', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '密码不能为空', trigger: 'blur' }                   
                    ],
                  
                  }
            }
        },
        methods: {
            register: function() {
                if (this.formValidate.name.length == 0) {
                    this.errMsg = '用户名不能为空';
                }
                else if (this.formValidate.email.length == 0) {
                    this.errMsg = '邮箱不能为空';
                }
                else if (this.formValidate.password.length == 0) {
                    this.errMsg = '密码不能为空';
                }
                else {
                   var app = this;
                   this.$http.post('/register', {
                       username: this.formValidate.name,
                       email: this.formValidate.email,
                       password: this.formValidate.password
                   }).then(
                       function(response) {                           
                            if (response.data.usernameExists == true) {
                                app.errMsg = '该用户名已被注册，换一个试试~';
                            }
                            else if (response.data.success == true) {
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