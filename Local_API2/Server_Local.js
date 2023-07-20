const express = require("express");
const { MongoClient } = require("mongodb");
const mongoose = require("mongoose");
const app = express();
const mongoUrl = "mongodb://127.0.0.1:27017";
app.use(express.json());
app.listen(3000, () => {
  console.log(`server đang lắng nghe tại cổng http://localhost:3000`);
});
app.listen(80, () => {
  console.log(`server đang lắng nghe tại cổng http://127.0.0.1:3000`);
});
function connectToColectionDB() {
  mongoose
    .connect("mongodb://127.0.0.1:27017/Database", {
      useNewUrlParser: true,
      useUnifiedTopology: true,
    })
    .then(() => {
      console.log("Đã kết nối tới MongoDB");
    })
    .catch((error) => {
      console.error("Lỗi kết nối MongoDB:", error);
    });

  // Tạo Schema bình luận
  const BinhluanSchema = new mongoose.Schema(
    // Truyền vào đối tượng định nghĩa cấu trúc
    {
      idUser: String,
      idTruyen: String,
      nameTruyen: String,
      date: String,
      noidung: String,
    },
    {
      collection: "BinhLuans",
    }
  );
  const Binhluan = mongoose.model("binhluan", BinhluanSchema);
  app.get("/api/list", async (req, res) => {
    //lấy tất cả dữ liệu từ MongoDB
    try {
      const Bls = await Binhluan.find({});
      res.json(Bls);
    } catch (err) {
      console.error("Error fetching users:", err);
      res.status(500).send("Internal Server Error");
    }
  });
  app.post("/api/binhluan", (req, res) => {
    const { idUser, idTruyen, nameTruyen, date, noidung } = req.body;
    const newBinhluan = new Binhluan({
      idUser,
      idTruyen,
      nameTruyen,
      date,
      noidung,
    });
    newBinhluan
      .save()
      .then(() => {
        res.status(201).json({ messgae: "Đối tượng đã lưu vào Monggo " });
      })
      .catch((error) => {
        console.log("Lỗi đối tượng vào Monggo ", error);
        res.status(500).json({ messgae: "Đã xảy ra lỗi với server " });
      });
  });
  app.put("/api/binhluan/edit/:id", (req, res) => {
    const id = req.params.id;
    
    console.log(""+id);
    const updatData = {
      idUser: req.body.idUser,
      idTruyen: req.body.idTruyen,
      nameTruyen: req.body.nameTruyen,
      date: req.body.date,
      noidung: req.body.noidung,
    };
    Binhluan.findByIdAndUpdate(id, updatData, { new: true })
      .then((data) => {
        if (data) {
          res.status(200).json({
            message: "Dữ liệu đã được cập nhật thành công",
            data: data,
          });
        } else {
          res.status(404).json({ error: "Không tìm thấy dữ liệu" });
        }
      })
      .catch((error) => {
        res.status(500).json({ error: "Đã xảy ra lỗi khi cập nhật dữ liệu" });
      });
  });
  app.delete("/api/binhluan/delete/:id", (req, res) => {
    const idToDelete = req.params.id;
    // Tạo kết nối tới MongoDB
    Binhluan.findByIdAndRemove(idToDelete)
      .then((data) => {
        if (data) {
          res
            .status(200)
            .json({ message: "Dữ liệu đã được xóa thành công", data: data });
        } else {
          res.status(404).json({ error: "Không tìm thấy dữ liệu" });
        }
      })
      .catch((error) => {
        res.status(500).json({ error: "Đã xảy ra lỗi khi xóa dữ liệu" });
      });
  });
}

connectToColectionDB();


