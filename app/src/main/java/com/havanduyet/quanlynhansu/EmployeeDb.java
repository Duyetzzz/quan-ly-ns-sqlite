package com.havanduyet.quanlynhansu;
import android.content.*;import android.database.*;import android.database.sqlite.*;import java.util.*;
public class EmployeeDb extends SQLiteOpenHelper {
 public static final String[] DEPARTMENTS={"Hành chính","Phần mềm","Kỹ thuật","Dự án"};
 private static final String TABLE="employees";
 public EmployeeDb(Context context){super(context,"nhansu.db",null,1);}
 @Override public void onCreate(SQLiteDatabase db){db.execSQL("CREATE TABLE employees (code TEXT PRIMARY KEY NOT NULL, name TEXT NOT NULL, birth_year INTEGER NOT NULL, gender TEXT NOT NULL, hometown TEXT NOT NULL, education TEXT NOT NULL, photo_uri TEXT, salary INTEGER NOT NULL CHECK(salary >= 0), department TEXT NOT NULL)");}
 @Override public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){ /* Add migrations here for future schema updates. */ }
 private ContentValues toValues(Employee e){ContentValues v=new ContentValues();v.put("code",e.code);v.put("name",e.name);v.put("birth_year",e.birthYear);v.put("gender",e.gender);v.put("hometown",e.hometown);v.put("education",e.education);v.put("photo_uri",e.photoUri);v.put("salary",e.salary);v.put("department",e.department);return v;}
 public boolean add(Employee e){return getWritableDatabase().insert(TABLE,null,toValues(e))!=-1;}
 public boolean update(String oldCode,Employee e){return getWritableDatabase().update(TABLE,toValues(e),"code=?",new String[]{oldCode})>0;}
 public boolean delete(String code){return getWritableDatabase().delete(TABLE,"code=?",new String[]{code})>0;}
 public Employee get(String code){try(Cursor c=getReadableDatabase().query(TABLE,null,"code=?",new String[]{code},null,null,null)){return c.moveToFirst()?read(c):null;}}
 private Employee read(Cursor c){return new Employee(c.getString(c.getColumnIndexOrThrow("code")),c.getString(c.getColumnIndexOrThrow("name")),c.getInt(c.getColumnIndexOrThrow("birth_year")),c.getString(c.getColumnIndexOrThrow("gender")),c.getString(c.getColumnIndexOrThrow("hometown")),c.getString(c.getColumnIndexOrThrow("education")),c.getString(c.getColumnIndexOrThrow("photo_uri")),c.getLong(c.getColumnIndexOrThrow("salary")),c.getString(c.getColumnIndexOrThrow("department")));}
 public List<Employee> search(String key,String department,String sort){List<Employee> result=new ArrayList<>();StringBuilder selection=new StringBuilder("1=1");List<String> args=new ArrayList<>();if(!key.trim().isEmpty()){selection.append(" AND (code LIKE ? OR name LIKE ? OR hometown LIKE ? OR department LIKE ?)");String val="%"+key.trim()+"%";for(int i=0;i<4;i++)args.add(val);}if(!"Tất cả phòng ban".equals(department)){selection.append(" AND department=?");args.add(department);}String order="name COLLATE NOCASE ASC";if("Lương tăng dần".equals(sort))order="salary ASC, name COLLATE NOCASE ASC";if("Lương giảm dần".equals(sort))order="salary DESC, name COLLATE NOCASE ASC";try(Cursor c=getReadableDatabase().query(TABLE,null,selection.toString(),args.toArray(new String[0]),null,null,order)){while(c.moveToNext())result.add(read(c));}return result;}
 public int count(){try(Cursor c=getReadableDatabase().rawQuery("SELECT COUNT(*) FROM employees",null)){c.moveToFirst();return c.getInt(0);}}
 public long totalSalary(){try(Cursor c=getReadableDatabase().rawQuery("SELECT COALESCE(SUM(salary),0) FROM employees",null)){c.moveToFirst();return c.getLong(0);}}
}
