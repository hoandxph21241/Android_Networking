var express = require('express');
var router = express.Router();
var list_api = require('../controller/api/list_api');

// url : get /api/... ==> lấy danh sách
router.get('/listTruyentranh', list_api.listTruyentranh);

// lấy chi tiết truyện theo id:
router.get('/listTruyentranh/chitiettruyen/:id',list_api.listChiTietTruyen);

// lấy ảnh truyện theo id:
router.get('/listTruyentranh/anhtruyen/:id',list_api.listAnhtruyen);

// lấy bình luận theo id:
router.get('/listTruyentranh/binhluan/:id',list_api.listBinhluan);

// Post bình luận 
router.post('/listTruyentranh/binhluan/:id',list_api.addBinhluan);

// Post User
router.post('/listTruyentranh/user',list_api.addUser);

// Get User
router.get('/listTruyentranh/user',list_api.listUser);

router.post('/list', list_api.Dladd);
router.post('/edit/:idsp', list_api.editDl);
router.delete('/delete/:idsp', list_api.deleteDl);

module.exports = router;