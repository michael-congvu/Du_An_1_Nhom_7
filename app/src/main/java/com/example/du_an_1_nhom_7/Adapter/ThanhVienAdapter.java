package com.example.du_an_1_nhom_7.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1_nhom_7.DAO.ThanhVienDAO;
import com.example.du_an_1_nhom_7.DTO.ThanhVienDTO;
import com.example.du_an_1_nhom_7.Fragment_QL_ThanhVien;
import com.example.du_an_1_nhom_7.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.TViewHolder> {

    private Context context;
    ArrayList<ThanhVienDTO> list;
    TextInputEditText tiedt_add_maTV,tiedt_add_tenTV,tiedt_add_namSinh,tiedt_add_gioiTinh,tiedt_add_sodienthoai;
    Button btn_addTV,btn_huy_addTV;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVienDTO> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public TViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_thanh_vien, parent, false);
        return new TViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ThanhVienDTO thanhVienDTO = list.get(position);
        holder.txt_maTV.setText("Mã thành viên: " + thanhVienDTO.getMaTV());
        holder.txt_tenTV.setText("Tên thành viên: " + thanhVienDTO.getHo_ten());
        holder.txt_namSinh.setText("Năm sinh: " + thanhVienDTO.getNam_sinh());
        holder.txt_gioiTinh.setText("Giới tính: " + thanhVienDTO.getGioi_tinh());
        holder.txt_sodienThoai.setText("Số điện thoại: " + thanhVienDTO.getSo_dien_thoai());

        holder.imgbnt_deleteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa?");
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(true);

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                        int result = thanhVienDAO.delete(String.valueOf(thanhVienDTO.getMaTV()));
                        if (result > 0) {
                            Toast.makeText(context, "Xóa thành công.", Toast.LENGTH_SHORT).show();
                            list.remove(thanhVienDTO);
                            notifyDataSetChanged();
                            dialog.dismiss();

                        } else {
                            Toast.makeText(context, "Xóa thất bại hoặc không có dữ liệu để xóa.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_thanh_vien, null);
                builder.setView(view);
                Dialog dialog = builder.create();

                tiedt_add_maTV = view.findViewById(R.id.tiedt_add_maTV);
                tiedt_add_tenTV = view.findViewById(R.id.tiedt_add_tenTV);
                tiedt_add_namSinh = view.findViewById(R.id.tiedt_add_namSinh);
                tiedt_add_gioiTinh = view.findViewById(R.id.tiedt_add_gioiTinh);
                tiedt_add_sodienthoai = view.findViewById(R.id.tiedt_add_sodienthoai);
                btn_addTV = view.findViewById(R.id.btn_addTV);
                btn_huy_addTV = view.findViewById(R.id.btn_huy_addTV);

                tiedt_add_maTV.setText(String.valueOf(list.get(position).getMaTV()));
                tiedt_add_tenTV.setText(list.get(position).getHo_ten());
                tiedt_add_namSinh.setText(list.get(position).getNam_sinh());
                tiedt_add_gioiTinh.setText(list.get(position).getGioi_tinh());
                tiedt_add_sodienthoai.setText(list.get(position).getSo_dien_thoai());

                dialog.show();

                btn_huy_addTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                btn_addTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenTV = tiedt_add_tenTV.getText().toString();
                        String NS = tiedt_add_namSinh.getText().toString();
                        String GT = tiedt_add_gioiTinh.getText().toString();
                        String SDT = tiedt_add_sodienthoai.getText().toString();
                        thanhVienDTO.setHo_ten(tenTV);
                        thanhVienDTO.setNam_sinh(NS);
                        thanhVienDTO.setGioi_tinh(GT);
                        thanhVienDTO.setSo_dien_thoai(SDT);
                        boolean err = false;
                        if (tenTV.isEmpty()) {
                            tiedt_add_tenTV.setError("Vui lòng nhập tên thành viên!");
                            err = true;
                        }
                        if (NS.isEmpty()) {
                            tiedt_add_namSinh.setError("Vui lòng nhập năm sinh thành viên!");
                            err = true;
                        }
                        if (GT.isEmpty()) {
                            tiedt_add_gioiTinh.setError("Vui lòng nhập giới tính thành viên!");
                            err = true;
                        }
                        if (SDT.isEmpty()) {
                            tiedt_add_sodienthoai.setError("Vui lòng nhập số điện thoại thành viên!");
                            err = true;
                        }

                        if(!err){
                            int kq = holder.thanhVienDAO.update(thanhVienDTO);
                            if (kq > 0) {
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }
                    }
                });
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TViewHolder extends RecyclerView.ViewHolder {
        TextView txt_maTV, txt_tenTV, txt_namSinh, txt_gioiTinh, txt_sodienThoai;
        ImageButton imgbnt_deleteTV;
        ThanhVienDTO thanhVienDTO;
        ThanhVienDAO thanhVienDAO;
        ThanhVienAdapter thanhVienAdapter;
        Fragment_QL_ThanhVien FragmentQLThanhVien;

        public TViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_maTV = itemView.findViewById(R.id.txt_maTV);
            txt_tenTV = itemView.findViewById(R.id.txt_tenTV);
            txt_namSinh = itemView.findViewById(R.id.txt_namSinh);
            txt_gioiTinh = itemView.findViewById(R.id.txt_gioiTinh);
            txt_sodienThoai = itemView.findViewById(R.id.txt_sodienthoai);
            imgbnt_deleteTV = itemView.findViewById(R.id.imgbnt_deleteTV);

            thanhVienDTO = new ThanhVienDTO();
            thanhVienDAO = new ThanhVienDAO(context);
            thanhVienAdapter = new ThanhVienAdapter(context, list);
            FragmentQLThanhVien = new Fragment_QL_ThanhVien();
        }
    }
}
