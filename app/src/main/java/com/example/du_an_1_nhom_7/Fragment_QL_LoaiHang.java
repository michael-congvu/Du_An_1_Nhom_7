package com.example.du_an_1_nhom_7;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.du_an_1_nhom_7.Adapter.LoaiHangAdapter;
import com.example.du_an_1_nhom_7.DAO.LoaiHangDAO;
import com.example.du_an_1_nhom_7.DTO.LoaiHangDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Fragment_QL_LoaiHang extends Fragment {
    RecyclerView rc_loai_hang;
    ArrayList<LoaiHangDTO> list;
    FloatingActionButton fab_loai_hang;
    Dialog dialog;
    TextInputEditText tiedt_add_maLH, tiedt_add_tenLH, tiedt_add_thue;
    Button btn_addLH, btn_huy_addLH;
    LoaiHangDAO loaiHangDAO;
    LoaiHangAdapter loaiHangAdapter;
    LoaiHangDTO loaiHangDTO;

    LinearLayoutManager linearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__q_l__loai_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rc_loai_hang = view.findViewById(R.id.rc_loai_hang);
        fab_loai_hang = view.findViewById(R.id.fab_loai_hang);
        loaiHangDAO = new LoaiHangDAO(getActivity());
        capNhatLv();
        fab_loai_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });



        rc_loai_hang.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab_loai_hang.hide();
                } else {
                    fab_loai_hang.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    public void openDialog(final Context context, final int type){
        dialog = new Dialog((context));
        dialog.setContentView(R.layout.dialog_loai_hang);

        tiedt_add_maLH = dialog.findViewById(R.id.tiedt_add_maLH);
        tiedt_add_tenLH = dialog.findViewById(R.id.tiedt_add_tenLH);
        tiedt_add_thue = dialog.findViewById(R.id.tiedt_add_thue);
        btn_addLH = dialog.findViewById(R.id.btn_addLH);
        btn_huy_addLH = dialog.findViewById(R.id.btn_huy_addLH);


        tiedt_add_maLH.setEnabled(false);

        btn_huy_addLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_addLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiHangDTO = new LoaiHangDTO();
                loaiHangDTO.setTen_loai_hang(tiedt_add_tenLH.getText().toString());
                loaiHangDTO.setThue(tiedt_add_thue.getText().toString());
                if (validate() > 0) {
                    if (type == 0) {
                        if (loaiHangDAO.insert(loaiHangDTO) > 0) {
                            Toast.makeText(context, "Thêm thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại.", Toast.LENGTH_SHORT).show();

                        }
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }

        });
        dialog.show();
    }


    private int validate() {
        int check = 1;

        String ten = tiedt_add_tenLH.getText().toString();
        String thue = tiedt_add_thue.getText().toString();

        if (ten.isEmpty()) {
            tiedt_add_tenLH.setError("Vui lòng nhập tên loại hàng!");
            check = -1;
        } else if (thue.isEmpty()) {
            tiedt_add_thue.setError("Vui lòng nhập thuế!");
            check = -1;
        }
        return check;


    }

    private void capNhatLv() {
        list = (ArrayList<LoaiHangDTO>) loaiHangDAO.getAll();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rc_loai_hang.setLayoutManager(linearLayoutManager);

        loaiHangAdapter = new LoaiHangAdapter(getActivity(), list);
        rc_loai_hang.setAdapter(loaiHangAdapter);
    }
}