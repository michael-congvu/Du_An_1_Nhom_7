package com.example.du_an_1_nhom_7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.du_an_1_nhom_7.DAO.NhanVienDAO;
import com.example.du_an_1_nhom_7.DTO.NhanVienDTO;
import com.google.android.material.navigation.NavigationView;

public class Activity_Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar tb_toolbar;
    NavigationView nav_menu;
    View headerView;
    TextView txt_headername;
    NhanVienDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        tb_toolbar = findViewById(R.id.tb_toolbar);
        nav_menu = findViewById(R.id.nav_menu);

        setSupportActionBar(tb_toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fr_framemain, new Frag_QL_Phieu_Muon()).commit();

        headerView = nav_menu.getHeaderView(0);
        txt_headername = headerView.findViewById(R.id.txt_headername);
        Intent i = getIntent();
        String user = i.getStringExtra("hoTen");
        thuThuDAO = new NhanVienDAO(this);
        NhanVienDTO thuThuDTO = thuThuDAO.getID(user);
        String username = thuThuDTO.getHo_ten();
        txt_headername.setText("Welcome " + username + "!");

        if (user.equalsIgnoreCase("admin")) {
            nav_menu.getMenu().findItem(R.id.item_themnd).setVisible(true);
        }
        nav_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
//                if (item.getItemId() == R.id.item_qlpm) {
//                    fragment = new Frag_QL_Phieu_Muon();
//                } else if (item.getItemId() == R.id.item_qlls) {
//                    fragment = new Frag_QL_Loai_Sach();
//                } else if (item.getItemId() == R.id.item_qls) {
//                    fragment = new Frag_QL_Sach();
//                } else if (item.getItemId() == R.id.item_qltv) {
//                    fragment = new Frag_QL_Thanh_Vien();
//                } else if (item.getItemId() == R.id.item_tktop10) {
//                    fragment = new Frag_TK_Top10();
//                } else if (item.getItemId() == R.id.item_tkdoanhthu) {
//                    fragment = new Frag_TK_Doanh_Thu();
//                } else if (item.getItemId() == R.id.item_themnd) {
//                    fragment = new Frag_Them_Nguoi_Dung();
//                } else if (item.getItemId() == R.id.item_doimk) {
//                    fragment = new Frag_Doi_Mat_Khau();
//                } else {
//                    fragment = new Frag_QL_Phieu_Muon();
//
//                }


                if (item.getItemId() == R.id.item_dangxuat) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Home.this);
                    builder.setMessage("Bạn có chắc chắn đăng xuất khỏi tài khoản?");

                    builder.setPositiveButton("Có, Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Activity_Home.this, Acticity_Dang_Nhap.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNeutralButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().cancel();
                        }
                    });
                    builder.create().show();

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fr_framemain, fragment).commit();
                getSupportActionBar().setTitle(item.getTitle());
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }
}