package com.hoandx.assm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hoandx.assm.Adapter.BinhluanAdapter;
import com.hoandx.assm.Adapter.TruyentranhAdapter;
import com.hoandx.assm.Object.Binhluan;
import com.hoandx.assm.Object.Truyentranh;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ManHinhChiTiet extends AppCompatActivity {
    TextView idnameCT, idotherCT, idnamxuatbanCT, idmotaCT;
    ProgressBar progressBar;
    ListView listView;
    ImageView id_imgCT;
    Button btn_back, btn_add, btn_doc;

    ArrayList<Binhluan> listbl = new ArrayList<>();

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chi_tiet);

        idnameCT = findViewById(R.id.idnameCT);
        idotherCT = findViewById(R.id.idotherCT);
        idnamxuatbanCT = findViewById(R.id.idnamxuatbanCT);
        idmotaCT = findViewById(R.id.idmotaCT);
        id_imgCT = findViewById(R.id.id_imgCT);
        listView = findViewById(R.id.idlistCT);
        progressBar = findViewById(R.id.progessbar);
        btn_add = findViewById(R.id.idthem_blCT);
        btn_back = findViewById(R.id.idbtn_backCT);
        btn_doc = findViewById(R.id.idbtn_docCT);


        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");

        ArrayList<Truyentranh> list = (ArrayList<Truyentranh>) intent.getSerializableExtra("dataList");
        Truyentranh clickedItem = null;
        for (Truyentranh item : list) {
            if (item.getId().equals(itemId)) {
                clickedItem = item;
                break;
            }
        }
        if (clickedItem != null) {
            idnameCT.setText(clickedItem.getName());
            idotherCT.setText(clickedItem.getOther());
            idnamxuatbanCT.setText(clickedItem.getNamxuatban());
            idmotaCT.setText(clickedItem.getMota());
            // Hiển thị ảnh
            String imageUrl = clickedItem.getImg();
            Glide.with(getApplicationContext()).load(imageUrl).into(id_imgCT);

        } else {
            // Không tìm thấy đối tượng Truyentranh tương ứng
            Toast.makeText(getApplicationContext(), "Không Tìm Thấy Giá Trị Tương Ứng", Toast.LENGTH_SHORT).show();
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManHinhChiTiet.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManHinhChiTiet.this, Doctruyen.class);
                intent.putExtra("itemId", itemId);
                intent.putExtra("dataList", list);
                startActivity(intent);
                finish();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBL();
            }
        });


        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBL();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        loadBL();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Binhluan clickedItem = (Binhluan) parent.getItemAtPosition(position);
                String itemId = clickedItem.getId();
                showEditDeleteDialog(position);
            }
        });
    }




    public void loadBL() {
        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");

        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(API_Class.API_LISTBL + itemId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    listbl.clear();

                    Intent intent = getIntent();
                    String fullnameBL = intent.getStringExtra("fullname");

                    JSONArray jsonArray = response;
                    Binhluan binhluan;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.has("_id") && object.has("idTruyen") && object.has("idNguoidung") && object.has("noidung") && object.has("date")) {
                            binhluan = new Binhluan(
                                    object.getString("_id"),
                                    object.getString("idTruyen"),
                                    object.getString("idNguoidung"),
                                    object.getString("noidung"),
                                    object.getString("date")
                            );
                            listbl.add(binhluan);
                        }
                    }
                    //log list
                    BinhluanAdapter binhluanAdapter = new BinhluanAdapter(getApplicationContext(), listbl, R.layout.layout_item_binhluan,fullnameBL);
                    listView.setAdapter(binhluanAdapter);


                    progressBar.setVisibility(View.INVISIBLE);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse: Lỗi", error.toString());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void addBL() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d(String.valueOf(getApplicationContext()), "addBL: Hàm đã được gọi");
        // Tạo một AlertDialog.Builder mới
        AlertDialog.Builder builder = new AlertDialog.Builder(ManHinhChiTiet.this);
        builder.setTitle("Thông Báo");
        builder.setMessage("Mời Nhập Bình Luận /3");
        View customView = getLayoutInflater().inflate(R.layout.item_bl, null);
        final EditText input = customView.findViewById(R.id.id_edt_addBL);
        builder.setView(customView);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String dateBL = day + "/" + month + "/" + year;

                Intent intent = getIntent();
                String itemId = intent.getStringExtra("itemId");

                // Lấy giá trị id từ SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                String idND = sharedPreferences.getString("id", "");
                String fullnameCT = sharedPreferences.getString("fullname", "");

                Log.d(String.valueOf(getApplicationContext()), itemId + idND + dateBL);

                try {
                    JSONObject object = new JSONObject();
                    object.put("idTruyen", itemId);
                    object.put("idNguoidung", idND);
                    object.put("noidung", input.getText().toString());
                    object.put("date", dateBL);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_Class.API_ADDBL + itemId, object, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Trả về thành công
                            Log.d(String.valueOf(getApplicationContext()), "Thành công: " + object + response);
                            Toast.makeText(getApplicationContext(), "Bình Luận Thành Công", Toast.LENGTH_SHORT).show();
                            input.setText("");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Trả về lỗi
                            Log.e(String.valueOf(getApplicationContext()), "Lỗi: " + error);
                            Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi nhấp vào nút Không
                Toast.makeText(getApplicationContext(), "Đã Hủy Bình Luận", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        // Tạo và hiển thị Dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showEditDeleteDialog(int position) {
        // Lấy đối tượng Binhluan tại vị trí được chọn
        Binhluan binhluan = listbl.get(position);

        // Kiểm tra idNguoidung của bình luận có trùng với idNguoidung của người dùng hiện tại không
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String idND = sharedPreferences.getString("id", "");
        if (binhluan.getIdNguoidung().equals(idND)) {
            // Hiển thị Dialog cho phép sửa hoặc xóa bình luận
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông Báo /3 ");
            builder.setMessage("Bạn muốn sửa hay xóa bình luận này?");
            builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Xử lý sửa bình luận tại đây
                    showEditCommentDialog(binhluan);
                    Toast.makeText(ManHinhChiTiet.this, "Thông Báo Sửa", Toast.LENGTH_SHORT).show();
                }
            });
//            builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // Xử lý xóa bình luận tại đây
//                    deleteComment(binhluan.getId());
//                    Toast.makeText(ManHinhChiTiet.this, "Thông Báo Xóa", Toast.LENGTH_SHORT).show();
//                }
//            });
            builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Hiển thị Dialog xác nhận xóa
                    AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(ManHinhChiTiet.this);
                    confirmBuilder.setTitle("Xác nhận xóa");
                    confirmBuilder.setMessage("Bạn có chắc chắn muốn xóa bình luận này không?");
                    confirmBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Xử lý xóa bình luận tại đây
                            deleteComment(binhluan.getId());
                            Toast.makeText(ManHinhChiTiet.this, "Thông Báo Xóa", Toast.LENGTH_SHORT).show();
                        }
                    });
                    confirmBuilder.setNegativeButton("Không", null);
                    confirmBuilder.show();
                }
            });
            builder.setNeutralButton("Hủy", null);
            builder.show();
        }
    }
    private void deleteComment(String commentId) {
        // Tạo một RequestQueue mới
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Tạo một StringRequest mới
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, API_Class.API_DELETEBL + commentId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Xử lý khi nhận được phản hồi thành công từ máy chủ
                Log.d(String.valueOf(getApplicationContext()), "Đã xóa BL: "+response);
                Toast.makeText(getApplicationContext(), "Xóa bình luận thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý khi có lỗi xảy ra
                Log.d(String.valueOf(getApplicationContext()), "Lỗi xóa BL: "+error);
                Toast.makeText(getApplicationContext(), "Lỗi khi xóa bình luận", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private void showEditCommentDialog(Binhluan binhluan) {
        // Tạo một AlertDialog.Builder mới
        AlertDialog.Builder editBuilder = new AlertDialog.Builder(this);
        editBuilder.setTitle("Sửa bình luận");
        editBuilder.setMessage("Nhập nội dung bình luận mới:");

        // Tạo một EditText mới
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(binhluan.getNoidung());
        editBuilder.setView(input);

        // Thiết lập nút Đồng ý
        editBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy nội dung bình luận mới từ EditText
                String newContent = input.getText().toString();

                // Xử lý cập nhật bình luận trên máy chủ tại đây
                updateComment(binhluan.getId(), newContent);
            }
        });

        // Thiết lập nút Hủy
        editBuilder.setNegativeButton("Hủy", null);

        // Tạo và hiển thị Dialog
        editBuilder.show();
    }
    private void updateComment(String commentId, String newContent) {
        // Tạo một RequestQueue mới
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Tạo một đối tượng HashMap mới để chứa các tham số của yêu cầu
        Map<String, String> params = new HashMap<>();
        params.put("noidung", newContent);

        // Tạo một JsonObjectRequest mới
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, API_Class.API_EDITBL + commentId, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Xử lý khi nhận được phản hồi thành công từ máy chủ
                Toast.makeText(getApplicationContext(), "Cập nhật bình luận thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý khi có lỗi xảy ra
                Toast.makeText(getApplicationContext(), "Lỗi khi cập nhật bình luận", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }




}
