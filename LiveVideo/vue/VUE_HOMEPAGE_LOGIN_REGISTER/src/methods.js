import {getPhoto} from "@/utils";

export default {
    showTime() {
        console.log("你好")
    },
    async getUserInfo() {
        let userForm;
        this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
        await this.$axios.get('http://localhost:8082/api/user').then(res => {
            // 拿到结果
            let result = res.data.result;
            let message = res.data.msg;
            userForm = result;
            userForm.photoUrl = getPhoto(userForm.photoUrl);
            if (result) {
                /*登陆成功*/

                /*跳转页面*/
                console.log(userForm.userName)
            } else {
                /*打印错误信息*/
                alert(message);
            }
        })
        return userForm
    }
}
