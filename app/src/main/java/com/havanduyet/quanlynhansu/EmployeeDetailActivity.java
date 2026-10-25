package com.havanduyet.quanlynhansu;

import android.app.*;
import android.content.*;
import android.net.Uri;
import android.os.*;
import android.widget.*;

import java.text.*;
import java.util.*;

public class EmployeeDetailActivity extends Activity {

 private String code;
 private EmployeeDb db;

 @Override
 public void onCreate(Bundle b) {
  super.onCreate(b);
  setContentView(R.layout.activity_detail);

  db = new EmployeeDb(this);
  code = getIntent().getStringExtra("code");

  findViewById(R.id.btnBack).setOnClickListener(v -> finish());

  findViewById(R.id.btnEdit).setOnClickListener(v -> {
   Intent i = new Intent(this, EmployeeFormActivity.class);
   i.putExtra("code", code);
   startActivity(i);
  });

  findViewById(R.id.btnDelete).setOnClickListener(v ->
          new AlertDialog.Builder(this)
                  .setTitle("Xác nhận xóa")
                  .setMessage("Xóa nhân sự mã " + code + "? Thao tác không thể hoàn tác.")
                  .setNegativeButton("Hủy", null)
                  .setPositiveButton("Xóa", (d, which) -> {
                   db.delete(code);

                   Toast.makeText(
                           this,
                           "Đã xóa nhân sự",
                           Toast.LENGTH_SHORT
                   ).show();

                   finish();
                  })
                  .show()
  );
 }

 @Override
 protected void onResume() {
  super.onResume();

  Employee e = db.get(code);

  if (e == null) {
   finish();
   return;
  }

  TextView t = findViewById(R.id.txtDetails);

  String salary = NumberFormat
          .getNumberInstance(new Locale("vi", "VN"))
          .format(e.salary);

  t.setText(
          "Họ tên: " + e.name + "\n" +
                  "Mã nhân sự: " + e.code + "\n" +
                  "Năm sinh: " + e.birthYear + "\n" +
                  "Giới tính: " + e.gender + "\n" +
                  "Quê quán: " + e.hometown + "\n" +
                  "Trình độ: " + e.education + "\n" +
                  "Phòng ban: " + e.department + "\n" +
                  "Mức lương: " + salary + " VNĐ"
  );

  ImageView img = findViewById(R.id.imgAvatar);

  img.setImageResource(android.R.drawable.ic_menu_myplaces);

  if (e.photoUri != null && !e.photoUri.isEmpty()) {
   try {
    img.setImageURI(Uri.parse(e.photoUri));
   } catch (Exception ignored) {
   }
  }
 }
}