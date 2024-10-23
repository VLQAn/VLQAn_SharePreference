package com.example.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnDangNhap;
    EditText etxtMsv, etxtPassword;
    CheckBox cbRemember,cbShowPassWord;

    //Share Preferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AnhXa();

        // getSharedPreferences() được truyền vào hai tham số là tên và mode. Tên được lưu vào bộ nhớ của ứng dụng (dạng Tên.xml). Save
        sharedPreferences = getSharedPreferences("DataLogin",MODE_PRIVATE);

        // Get gia tri sharedPreferences tu file xml DataLogin
        etxtMsv.setText(sharedPreferences.getString("taikhoan",""));
        etxtPassword.setText(sharedPreferences.getString("matkhau", ""));
        cbRemember.setChecked(sharedPreferences.getBoolean("checked", false));

        //Sự kiện click hiển thị mật khẩu
        cbShowPassWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    etxtPassword.setTransformationMethod(null);
                }else
                    etxtPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        //Gia lap qua trinh dang nhap
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masv = etxtMsv.getText().toString().trim();
                String password = etxtPassword.getText().toString().trim();

                if(masv.equals("22115053122301") && password.equals("21032004")){
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();
                    // neu dang nhap hop le va click vao checkbox thi luu thong tin dang nhap vao sharedPreferences la DataLogin.xml
                    if(cbRemember.isChecked()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("taikhoan", masv);// luu username vao file DataLogin
                        editor.putString("matkhau",password);// luu password vao file DataLogin
                        editor.putBoolean("checked", true); // Luu trang thai checkbox
                        editor.commit();// xac nhan
                    }
                    // neu dang nhap hop le va khong click vao checkbox thi bo luu thong tin dang nhap vao sharedPreferences la DataLogin.xml
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("taikhoan");
                        editor.remove("matkhau");
                        editor.remove("checked");
                        editor.commit();
                    }
                }else Toast.makeText(MainActivity.this, "Lỗi đăng nhập!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXa(){
        btnDangNhap = (Button) findViewById(R.id.buttonXacNhan);
        etxtPassword = (EditText) findViewById(R.id.editTextTextPassword);
        etxtMsv = (EditText) findViewById(R.id.editTextUserName);
        cbRemember = (CheckBox) findViewById(R.id.checkBoxRemember);
        cbShowPassWord = (CheckBox) findViewById(R.id.cbShowPassword);
    }
}