# BÀI TẬP: QUẢN LÝ NHÂN SỰ — JAVA + SQLITE

**Sinh viên:** Hà Văn Duyệt  
**MSSV:** K23DTCN532

## Đề bài và chức năng
- Nhân sự gồm mã, họ tên, năm sinh, giới tính, quê quán, trình độ, ảnh, mức lương và phòng ban.
- 4 phòng ban: Hành chính, Phần mềm, Kỹ thuật, Dự án.
- Danh sách với ảnh/ten/mã/phòng ban/lương; xem chi tiết; thêm/sửa/xóa; tìm theo mã/tên/quê quán/phòng ban; lọc theo phòng ban; sắp xếp theo lương tăng hoặc giảm.
- Hiện **K23DTCN532 – Hà Văn Duyệt** trên cả ba giao diện.
- Dữ liệu lưu trong SQLite cục bộ: `nhansu.db` → bảng `employees`.

## Mở trong Android Studio
1. Giải nén ZIP; File > Open > chọn thư mục `QuanLyNhanSu_HaVanDuyet_Java`.
2. Đảm bảo đã cài JDK 17, Android SDK 35; để Gradle đồng bộ plugin Android Gradle 8.6.1.
3. Nếu Android Studio yêu cầu Gradle, dùng Gradle 8.7 (wrapper không kèm theo để gói nhẹ).
4. Run trên thiết bị Android 6.0 trở lên (API 23+).
5. Bấm THÊM NHÂN SỰ để thêm dữ liệu; chạm một dòng để xem và sửa/xóa.

## Database
```sql
CREATE TABLE employees (
 code TEXT PRIMARY KEY NOT NULL,
 name TEXT NOT NULL,
 birth_year INTEGER NOT NULL,
 gender TEXT NOT NULL,
 hometown TEXT NOT NULL,
 education TEXT NOT NULL,
 photo_uri TEXT,
 salary INTEGER NOT NULL CHECK(salary >= 0),
 department TEXT NOT NULL
);
```
Các hàm quan trọng: `EmployeeDb.onCreate()`, `add()`, `update()`, `delete()`, `search()`, `count()`, `totalSalary()`.

Ví dụ sắp xếp:
```sql
SELECT * FROM employees ORDER BY salary DESC, name COLLATE NOCASE ASC;
```
Ví dụ tìm kiếm:
```sql
SELECT * FROM employees WHERE name LIKE ? OR code LIKE ?;
```

## Tệp mã nguồn
- `Employee.java`: mô hình dữ liệu.
- `EmployeeDb.java`: cơ sở dữ liệu và truy vấn CRUD SQLite.
- `MainActivity.java`: danh sách, tìm kiếm, lọc, thống kê, sắp xếp.
- `EmployeeFormActivity.java`: nhập thông tin, kiểm tra và chọn ảnh.
- `EmployeeDetailActivity.java`: xem, sửa, xác nhận xóa.
- `EmployeeAdapter.java`: hiển thị danh sách.
- `res/layout/`: giao diện XML cho từng màn hình.

## Điểm cần nắm khi bảo vệ
- `SQLiteOpenHelper` tạo CSDL; `onCreate()` chạy khi file DB mới được tạo.
- `ContentValues` lưu cặp tên cột/giá trị khi insert, update.
- `Cursor` đọc từng dòng kết quả query, dùng try-with-resources đóng sau khi đọc.
- `WHERE code=?` + selectionArgs tránh nối dữ liệu trực tiếp vào câu SQL.
- `salary` là INTEGER biểu diễn **VNĐ nguyên**, giúp sắp xếp theo số thay vì chữ.
- Mã nhân sự là PRIMARY KEY; khi sửa mã bị khóa để tránh đổi khóa.
- Ảnh được chọn qua ACTION_OPEN_DOCUMENT, lưu URI và giữ quyền đọc lâu dài.

**Lưu ý:** Project chưa được biên dịch và chạy trên emulator trong môi trường tạo tệp; vui lòng thực hiện Gradle Sync/Run trên Android Studio trước khi nộp.
