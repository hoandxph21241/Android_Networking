const {model} = require('mongoose');
var db = require('./db');

// Truyện Tranh
const TruyentranhSchema = new db.mongoose.Schema(
    //truyền vào đối tượng định nghĩa cấu trúc
    {
        name: {type: String, required : true}, // required : true : dữ liệu bắt buộc nhập
        other: {type: String, required : false},
        mota: {type: String, required : false},
        binhluan:{type: db.mongoose.Schema.Types.ObjectId, ref: "BinhLuanModel"},
        img: {type: String, required : false},
        namxuatban: {type: String, required : false},
        anhtruyen:{type: db.mongoose.Schema.Types.ObjectId, ref: "AnhTruyenModel"},
    },
    {
        collection: "TruyenTranh"
    }
);
// định nghĩa model
let TruyenTranhModel = db.mongoose.model('TruyenTranhModel', TruyentranhSchema);

// User
const UserSchema = new db.mongoose.Schema(
    //truyền vào đối tượng định nghĩa cấu trúc
    {
        username: {type: String, required : true}, // required : true : dữ liệu bắt buộc nhập
        fullname: {type: String, required : false},
        password: {type: String, required : false},
        email: {type: String, required : false},
    },
    {
        collection: "User"
    }
);
// định nghĩa model
let UserModel = db.mongoose.model('UserModel', UserSchema);

// Bình Luận 
const BinhLuanSchema = new db.mongoose.Schema(
    //truyền vào đối tượng định nghĩa cấu trúc
    {
        noidung: {type: String, required : true}, // required : true : dữ liệu bắt buộc nhập
        idTruyen:{type: db.mongoose.Schema.Types.ObjectId, ref: "TruyenTranhModel"},
        idNguoidung:{type: db.mongoose.Schema.Types.ObjectId, ref: "UserModel"},
        date: {type: String, required : false},
    },
    {
        collection: "BinhLuan"
    }
);
// định nghĩa model
let BinhLuanModel = db.mongoose.model('BinhLuanModel', BinhLuanSchema);

// User
const AnhtruyenSchema = new db.mongoose.Schema(
    //truyền vào đối tượng định nghĩa cấu trúc
    {
        img1: {type: String, required : true}, // required : true : dữ liệu bắt buộc nhập
        img2: {type: String, required : false},
        img3: {type: String, required : false},
        img4: {type: String, required : false},
        img5: {type: String, required : false},
        idTruyen:{type: db.mongoose.Schema.Types.ObjectId, ref: "TruyenTranhModel"},
    },
    {
        collection: "image"
    }
);
// định nghĩa model
let AnhtruyenModel = db.mongoose.model('AnhTruyenModel', AnhtruyenSchema);

// Exports model
module.exports = {TruyenTranhModel,UserModel,BinhLuanModel,AnhtruyenModel};