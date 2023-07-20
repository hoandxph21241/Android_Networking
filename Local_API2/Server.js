const express = require("express");
const mongoose = require("mongoose");

// Khởi tạo express
const app = express();
app.use(express.json());

// Kết nối tới MongoDB
mongoose
  .connect(

    //mongodb:127.0.0.1:27017
    "mongodb://127.0.0.1/Database",
    // "mongodb+srv://hoandxph21241:Xuanhoan23@androidnetwoking.pvs7la6.mongodb.net/Database",
    {
      useNewUrlParser: true,
      useUnifiedTopology: true,
    }
  )
  .then(() => {
    console.log("đã kết nối tới MongoDB");
  })
  .catch((error) => {
    console.error("lỗi kết nối", error);
  });

// Khởi chạy server
const port = 3000;
// app.listen(port, () => {
//   console.log(`server đang lắng nghe tại cổng ${port}`);
// });

// Khởi chạy server trên cổng 3000 và cổng 80
app.listen(3000, () => {
  console.log(`server đang lắng nghe tại cổng 3000`);
});
app.listen(80, () => {
  console.log(`server đang lắng nghe tại cổng 80`);
});

// Định nghĩa schema cho collection "Duleu_1"
const DlSchema = new mongoose.Schema(
  // Truyền vào đối tượng định nghĩa cấu trúc
  {
    name: { type: String, required: true }, // required : true : dữ liệu bắt buộc nhập
    price: { type: Number, required: false },
    title: { type: String, required: false },
  },
  {
    collection: "Duleu_1",
  }
);




// Tạo model từ schema
const Dl = mongoose.model("Dl", DlSchema);

// // Tìm tất cả documents trong collection "Duleu_1"
// Dl.find({})
//   .then((docs) => {
//     console.log(docs);
//   })
//   .catch((err) => {
//     console.error(err);
//   });

//Link 1
// app.get("/list", (req, res) => {
//   Dl.find({})
//     .then((docs) => {
//       res.json(docs);
//     })
//     .catch((err) => {
//       console.error(err);
//       res.status(500).json({ message: "Internal server error" });
//     });
// });

// Link 2
app.get("/list", async (req, res) => {
  //lấy tất cả dữ liệu từ MongoDB
  try {
    const Dls = await Dl.find({});
    res.json(Dls);
  } catch (err) {
    console.error("Error fetching users:", err);
    res.status(500).send("Internal Server Error");
  }
});
//Thêm dữ liệu
app.post("/list/post", (req, res) => {
  const newData = new User({
    name: req.body.name,
    price: req.body.price,
    title: req.body.title,
  });
  newData
    .save()
    .then(() => {
      res.status(200).json({ message: "post thành công" });
    })
    .catch((error) => {
      res.status(500).json({ error: "post không thành công" });
    });
});

// Update
app.put("/list/edit/:id", (req, res) => {
  const id = req.params.id;
  const updatData = {
    name: req.body.name,
    price: req.body.price,
    title: req.body.title,
  };
  User.findByIdAndUpdate(id, updatData, { new: true })
    .then((data) => {
      if (data) {
        res
          .status(200)
          .json({ message: "Dữ liệu đã được cập nhật thành công", data: data });
      } else {
        res.status(404).json({ error: "Không tìm thấy dữ liệu" });
      }
    })
    .catch((error) => {
      res.status(500).json({ error: "Đã xảy ra lỗi khi cập nhật dữ liệu" });
    });
});

//xóa theo id
app.delete("/list/delete/:id", (req, res) => {
  const idToDelete = req.params.id;
  // Tạo kết nối tới MongoDB
  User.findByIdAndRemove(idToDelete)
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
