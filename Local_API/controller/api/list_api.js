var model = require("../../models/sanpham_model");

///-------------------------------------------- User ----------------------------------------------------///
// --List User --//
exports.listUser = async (req, res, next) => {
  msg = "Danh sach Du Lieu Nguoi Dung";

  try {
    let list = await model.UserModel.find();
    console.log(list);
    //   return  res.status(200).json({msg: 'lấy địa chỉ thành công', data: list});
    return res.status(200).json(list);
    // Log List
  } catch (error) {
    return res.status(204).json({ msg: "không có dữ liệu" + error.message });
  }

  // res.status(200).json({msg});
};
// --Add User --//
exports.addUser = async (req, res, next) => {
  let list = await model.UserModel.find();
  if (req.method === "POST") {
    // Nếu các thông tin nhập hợp lệ, tiến hành tạo một object mới cho model và lưu vào CSDL
    let objSP = new model.UserModel({
      username: req.body.username,
      fullname: req.body.fullname,
      password: req.body.password,
      email: req.body.email,
    });

    try {
      let new_Ur = await objSP.save();
      console.log(new_Ur);
      msg = "Thêm mới thành công";
    } catch (error) {
      msg = "Lỗi " + error.message;
      console.log(error);
    }
    return res.status(200).json(list);
  }
};
///-------------------------------------------- End ----------------------------------------------------///

///---------------------------------------- Truyen Tranh -----------------------------------------------///
// --List Truyen Tranh --//
exports.listTruyentranh = async (req, res, next) => {
  msg = "Danh sach Du Lieu Truyen Tranh";
  try {
    let list = await model.TruyenTranhModel.find()
      .populate("binhluan")
      .populate("anhtruyen");
    let listBinhluan = await model.BinhLuanModel.find();
    console.log(list);
    //   return  res.status(200).json({msg: 'lấy địa chỉ thành công', data: list});
    return res.status(200).json(list);
    // Log List
  } catch (error) {
    console.log(error);
    return res.status(204).json({ msg: "không có dữ liệu" + error.message });
  }

  // res.status(200).json({msg});
};

exports.listChiTietTruyen = async (req, res, next) => {
  msg = "Danh sach Chi Tiet Truyen Tranh";
  let truyenId = req.params.id;
  try {
    //  let list = await model.TruyenTranhModel.find().populate("binhluan").populate("anhtruyen");
    let chitietList = await model.TruyenTranhModel.findById(truyenId).populate(
      "binhluan"
    );
    // let listBinhluan = await model.BinhLuanModel.find();
    console.log(chitietList);
    //   return  res.status(200).json({msg: 'lấy địa chỉ thành công', data: list});
    return res.status(200).json(chitietList);
    // Log List
  } catch (error) {
    console.log(error);
    return res.status(204).json({ msg: "không có dữ liệu" + error.message });
  }

  // res.status(200).json({msg});
};

exports.listAnhtruyen = async (req, res, next) => {
  let truyenId = req.params.id;
  try {
    let anhtruyenList = await model.AnhtruyenModel.find({ idTruyen: truyenId });
    console.log(anhtruyenList);
    return res.status(200).json(anhtruyenList);
  } catch (error) {
    console.log(error);
    return res.status(500).json({ msg: "Có lỗi xảy ra: " + error.message });
  }
};

///-------------------------------------------- End ----------------------------------------------------///

///------------------------------------------ Binh Luan ------------------------------------------------///
exports.listBinhluan = async (req, res, next) => {
  let truyenId = req.params.id;
  try {
    let binhluanList = await model.BinhLuanModel.find({ idTruyen: truyenId });
    console.log(binhluanList);
    return res.status(200).json(binhluanList);
  } catch (error) {
    console.log(error);
    return res.status(500).json({ msg: "Có lỗi xảy ra: " + error.message });
  }
};

exports.addBinhluan = async (req, res, next) => {
  let truyenId = req.params.id;
  let binhluanData = req.body;

  if (req.method == "POST") {
    try {
      let truyen = await model.TruyenTranhModel.findById(truyenId);
      if (!truyen) {
        return res
          .status(404)
          .json({ msg: "Không tìm thấy truyện có ID: " + truyenId });
      }
      let binhluan = new model.BinhLuanModel(binhluanData);
      binhluan.idTruyen = truyenId;
      await binhluan.save();
      return res.status(201).json(binhluan);
    } catch (error) {
      console.log(error);
      return res.status(500).json({ msg: "Có lỗi xảy ra: " + error.message });
    }
  }
};

///-------------------------------------------- End ----------------------------------------------------///

exports.Dladd = async (req, res, next) => {
  let list = await model.DlModel.find();
  if (req.method === "POST") {
    // Nếu các thông tin nhập hợp lệ, tiến hành tạo một object mới cho model và lưu vào CSDL
    let objSP = new model.DlModel({
      name: req.body.name,
      price: req.body.price,
      title: req.body.title,
    });

    try {
      let new_sp = await objSP.save();
      console.log(new_sp);
      msg = "Thêm mới thành công";
    } catch (error) {
      msg = "Lỗi " + error.message;
      console.log(error);
    }
    return res.status(200).json(list);
  }
};

exports.editDl = async (req, res, next) => {
  let msg = "";
  let idsp = req.params.idsp;
  try {
    let objSP = await model.DlModel.findById(idsp);
    if (!objSP) {
      return res.status(404).json({ message: "Không tìm thấy sản phẩm" });
    }

    if (req.method === "POST") {
      objSP.name = req.body.name;
      objSP.price = req.body.price;
      objSP.title = req.body.title;

      // lưu các thay đổi vào CSDL
      try {
        await objSP.save();
        msg = "Cập nhật thành công";
        console.log(objSP);
      } catch (error) {
        msg = "Lỗi " + error.message;
        console.log(error);
      }
    }

    return res.status(200).json({ message: msg, data: objSP });
  } catch (error) {
    return res.status(500).json({ message: "Lỗi server" });
  }
};
exports.deleteDl = async (req, res, next) => {
  let idsp = req.params.idsp;
  try {
    let objSP = await model.DlModel.findById(idsp);
    if (!objSP) {
      return res.status(404).json({ message: "Không tìm thấy sản phẩm" });
    }

    // xóa dữ liệu từ CSDL
    try {
      await model.DlModel.findByIdAndDelete(idsp);
      return res.status(200).json({ message: "Xóa thành công" });
    } catch (error) {
      console.log(error);
      return res.status(500).json({ message: "Lỗi server" });
    }
  } catch (error) {
    return res.status(500).json({ message: "Lỗi server" });
  }
};
