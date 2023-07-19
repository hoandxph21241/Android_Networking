const mongoose = require('mongoose');
//connect
// mongoose.connect('mongodb+srv://hoandxph21241:Xuanhoan23@androidnetwoking.pvs7la6.mongodb.net/Database')
mongoose.connect('mongodb://127.0.0.1/ASSM_AN')
.then(()=>{
    console.log("Đã Kết Nối ");
})
.catch((err) => {
    console.log("loi ket noi csdl");
    console.log('err');
});
module.exports = {mongoose}
