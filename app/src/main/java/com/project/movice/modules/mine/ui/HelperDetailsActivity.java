package com.project.movice.modules.mine.ui;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.project.movice.R;
import com.project.movice.base.activity.BaseActivity;
import com.project.movice.modules.home.adappter.BaseRecyclerAdapter;
import com.project.movice.modules.mine.adapter.HelperDetailsAdapter;
import com.project.movice.modules.mine.bean.BeanHelperDetails;
import com.project.movice.modules.mine.bean.BeanMyLoan;
import com.project.movice.modules.mine.contract.HelperDetailsContract;
import com.project.movice.modules.mine.presenter.HelperDetailsPresenter;
import com.project.movice.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 助手详情
 * Created by wy on 2018/1/27 13:50.
 */


public class HelperDetailsActivity extends BaseActivity<HelperDetailsPresenter> implements HelperDetailsContract.View, BaseRecyclerAdapter.OnItemClickListener, HelperDetailsAdapter.OnClickListener {

    public static final int TYPE_AGREE = 1;
    public static final int TYPE_DISAGREE = 2;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private String titleName;
    private String id;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    HelperDetailsAdapter adapter;
    List<BeanHelperDetails.HelperDetails> list = new ArrayList<>();

    private String getAppName() {
        return getResources().getString(R.string.app_name);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.helper_details;
    }


    String json1 = "{\n" +
            "\t\"data\": [{\n" +
            "\t\t\t\"title\": \"User seperti apa yang dapat mengajukan permohonan pinjaman?\",\n" +
            "\t\t\t\"content\": \"DompetPinjaman tidak membatasi pekerjaan, pendapatan, atau kondisi Anda yang lainnya. Selama umur Anda sudah mencapai 20 tahun dan Anda memiliki pendapatan dan nomor ponsel yang tetap, anda dapat mengajukan permohonan pinjaman kepada DompetPinjaman.\",\n" +
            "\t\t\t\"id\": \"2357657567\",\n" +
            "\t\t\t\"agree\": 122,\n" +
            "\t\t\t\"disagree\": 33\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"title\": \"Bagaimana cara mengajukan permohonan pinjama?\",\n" +
            "\t\t\t\"content\": \"Silahkan buka Google Play dan cari app DompetPinjaman, kemudian download app tersebut. Setelah selesai mendownload, buka app tersebut dan Anda dapat memulai proses permohonan pinjaman.\",\n" +
            "\t\t\t\"id\": \"2357657567\",\n" +
            "\t\t\t\"agree\": 122,\n" +
            "\t\t\t\"disagree\": 33\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"title\": \"Dokumen apa saja yang perlu dipersiapkan?  \",\n" +
            "\t\t\t\"content\": \"Anda hanya perlu mengikuti petunjuk sistem kami untuk mengisi informasi dasar (Informasi pribadi, informasi pekerjaan, informasi kontak, dan upload foto Anda), mohon pastikan untuk mengisi informasi asli Anda. Sistem akan melakukan analisa mahadata, multi-dimensi kalkulasi skor kredit, dan skor gabungan komprehensif berdasarkan informasi yang telah Anda berikan untuk mengkalkulasi hasil review pinjaman.\",\n" +
            "\t\t\t\"id\": \"2357657567\",\n" +
            "\t\t\t\"agree\": 122,\n" +
            "\t\t\t\"disagree\": 33\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"title\": \"Bagaimana cara menghitung tanggal jatuh tempo?  \",\n" +
            "\t\t\t\"content\": \"Tanggal jatuh tempo dijadwalkan 7 hari atau 14 hari setelah tanggal pengeluaran pinjaman (berdasarkan jangka waktu pinjaman yang telah Anda pilih sebelumnya). Setelah pinjaman berhasil, Anda dapat memeriksa tanggal jatuh tempo pinjaman pada halaman utama app.\",\n" +
            "\t\t\t\"id\": \"2357657567\",\n" +
            "\t\t\t\"agree\": 122,\n" +
            "\t\t\t\"disagree\": 33\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";


    String json2 = "{\n" +
            "\t\"data\": [{\n" +
            "\t\t\t\"title\": \"Bagaimana cara mengembalikan pinjaman?  \",\n" +
            "\t\t\t\"content\": \"Kami akan mengirimkan pesan ke ponsel Anda satu hari sebelum tanggal jatuh tempo untuk mengingatkan Anda agar mengembalikan pinjaman. Anda dapat mengembalikan pinjaman melalui Alfamart atau melalui e-banking, mobile banking, dan transfer memalui ATM bank besar. Setelah berhasil mentransfer, app DompetPinjaman akan secara otomatis mendapatkan sinyal pengembalian pinjaman dan melunasi pinjaman Anda. Kami mendukung pengembalian pinjaman lebih awal.\",\n" +
            "\t\t\t\"id\": \"2357657567\",\n" +
            "\t\t\t\"agree\": 122,\n" +
            "\t\t\t\"disagree\": 33\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"title\": \"Bagaimana cara menghitung denda terlambat?\",\n" +
            "\t\t\t\"content\": \"Pinjaman Anda terlambat jika Anda tidak dapat mengembalikan pinjaman pada atau sebelum tanggal jatuh tempo. Perhitungan Denda :\n" +
            "Keterlambatan 1-3 hari : 7% Dana Pokok + 1% × Jumlah hari keterlambatan\n" +
            "Keterlambatan 4-7 hari : 10% Dana Pokok + 1% ×Jumlah hari keterlambatan\n" +
            "Keterlambatan 8-14 hari : 20% Dana Pokok + 1% × Jumlah hari keterlambatan\n" +
            "Keterlambatan 15-21 hari : 30% Dana Pokok + 1%×Jumlah hari keterlambatan\n" +
            "Keterlambatan 22-90 hari : 51% Dana Pokok + 1%×Jumlah hari keterlambatan\n" +
            "Keterlambatan 90 hari keatas, melaporkan data pengguna kredit macet ke pihak yang berwenang, dan tidak akan menerima biaya apapun dari pengguna tersebut.\n" +
            "Jumlah pelunasan saat overdue= Dana Pokok + (0.1%+0.4%) × Tenor Pinjaman + persentase denda dari dana pokok + 1% × jumlah hari keterlambatan\",\n" +
            "\t\t\t\"id\": \"2357657567\",\n" +
            "\t\t\t\"agree\": 122,\n" +
            "\t\t\t\"disagree\": 33\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"title\": \"Jika user tidak dapat mengembalikan pinjaman dengan tepat waktu, akan ada konsekuensi apa?\",\n" +
            "\t\t\t\"content\": \"Kami akan mengambil langkah-langkah legal untuk mengkoleksi pinjaman dan kami juga akan mengenakan denda dan biaya manajemen extra. Untuk Detail perhitungan denda berdasarkan pada poin 2.2 \n" +
            "Jika Anda melakukan pelanggaran serius, kami dapat mengungkapkan informasi pribadi Anda, dan Anda tidak akan dapat mengajukan permohonan pinjaman lagi pada kami.\n" +
            "Jika user tidak dapat mengembalikan pinjaman dengan tepat waktu, Kami juga akan mengupload catatan kredit Anda pada Arsip Kredit Nasional.\",\n" +
            "\t\t\t\"id\": \"2357657567\",\n" +
            "\t\t\t\"agree\": 122,\n" +
            "\t\t\t\"disagree\": 33}\n" +
            "\t]\n" +
            "}";

    String json3 = "{\n" +
            "\t\"data\": [{\n" +
            "\t\t\t\"title\": \"Mengapa review pinjaman saya tidak lolos? \",\n" +
            "\t\t\t\"content\": \"Mohon maaf, karena skor komprehensif Anda tidak mencukupi, untuk sementara permohonan pinjaman Anda belum dapat disetujui. Sistem akan melakukan analisa mahadata dan mengaudit secara otomatis. Karena alasan pengendalian risiko, kami tidak dapat memberikan rincian skor kredit Anda dan alasan detil pemohonan Anda ditolak. Jika informasi Anda tidak mengalami perubahan, kami tidak menganjurkan Anda untuk mengajukan permohonan lagi dalam waktu dekat. Jika Anda perlu meminjam, silakan mendaftar lagi dalam beberapa hari.\",\n" +
            "\t\t\t\"id\": \"2357657567\",\n" +
            "\t\t\t\"agree\": 122,\n" +
            "\t\t\t\"disagree\": 33\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";

    String json4 = "{\n" +
            "\t\"data\": [{\n" +
            "\t\t\t\"title\": \"Apakah informasi saya akan bocor ?\",\n" +
            "\t\t\t\"content\": \"Kami berjanji bahwa kami tidak akan mengungkapkan informasi pribadi Anda pada pihak ketiga tanpa persetujuan dari Anda (dikecualikan Anda tidak mau mengembalikan pinjaman)\",\n" +
            "\t\t\t\"id\": \"2357657567\",\n" +
            "\t\t\t\"agree\": 122,\n" +
            "\t\t\t\"disagree\": 33\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";


    String json = "";


    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            titleName = bundle.getString("title");
            id = bundle.getString("id");
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitle.setText(titleName);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());





        adapter = new HelperDetailsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnClickListener(this);

        initData();
    }


    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initEventAndData() {
//        Map<String, String> params = new HashMap<>();
//        params.put("id", id);
//        mPresenter.request024(params);
    }

    public void initData() {
        json1 = json1.replaceAll("UangBagus", getAppName());
        json2 = json2.replaceAll("UangBagus", getAppName());
        json3 = json3.replaceAll("UangBagus", getAppName());
        json4 = json4.replaceAll("UangBagus", getAppName());

//        getHelperDetails();

        if (id.equals("1")) {
            json = json1;
        } else if (id.equals("2")) {
            json = json2;
        } else if (id.equals("3")) {
            json = json3;
        } else if (id.equals("4")) {
            json = json4;
        }
        try {
            BeanHelperDetails bd = GsonUtil.GsonToBean(json, BeanHelperDetails.class);
            list.addAll(bd.getData());
            adapter.resetItem(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
            case R.id.left_text:
                finish();
                break;
        }
    }

    /**
     * 获取信息
     */


    /**
     * 点赞，点踩
     */
    private void agree(String id, int type) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("type", type + "");
        mPresenter.request025(params);
    }


    @Override
    public void onItemClick(int position, long itemId) {
        if (list.get(position).isShowDetails()) {
            list.get(position).setShowDetails(false);
        } else {
            list.get(position).setShowDetails(true);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position, int type) {
        BeanHelperDetails.HelperDetails details = list.get(position);
        if (type == TYPE_AGREE) {
            details.setAgree(true);
            details.setAgree(details.getAgree() + 1);
        } else {
            details.setDisagree(true);
            details.setDisagree(details.getDisagree() + 1);
        }
        adapter.notifyDataSetChanged();
        agree(details.getId(), type);
    }

    @Override
    public void get024(BeanHelperDetails response) {
        list.addAll(response.getData());
        adapter.resetItem(list);
    }

    @Override
    public void get025() {

    }
}
