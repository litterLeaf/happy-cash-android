package com.yinshan.happycash.view.me.view.impl

import android.content.Intent
import android.view.View
import android.widget.RelativeLayout

import com.yinshan.happycash.R

import com.yinshan.happycash.framework.BaseSingleNoScrollActivity
import com.yinshan.happycash.view.me.view.impl.support.HelpCenterListViewAdapter

import kotlinx.android.synthetic.main.activity_help_center.*

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *        ┃　　　┃   兽神保佑
 *        ┃　　　┃   代码无BUG！
 *        ┃　　　┗━━━┓
 *        ┃　　　　　　　┣┓
 *        ┃　　　　　　　┏┛
 *        ┗┓┓┏━┳┓┏┛
 *           ┃┫┫　┃┫┫
 *           ┗┻┛　┗┻┛
 *
 *    描述：
 *    创建人：     admin
 *    创建时间：2018/9/14
 *
 */
class HelpCenterActivity : BaseSingleNoScrollActivity(), View.OnClickListener {

    lateinit var mAdapter: HelpCenterListViewAdapter

    override fun bindTitle(): String {
        return resources.getString(R.string.help_center)
    }

    override fun bindDownLayout(): Int {
        return R.layout.activity_help_center
    }

    override fun secondInit() {

        lowestBg.setBackgroundColor(resources.getColor(R.color.app_white))
        mAdapter = HelpCenterListViewAdapter()
        mAdapter.addList("1.User seperti apa yang dapat mengajukan pinjaman?", "HappyCash tidak membatasi pekerjaan, pendapatan, atau syarat lainnya. Selama umur anda sudah mencapai 21 tahun dan anda memiliki pendapatan dan nomor ponsel yang tetap, anda dapat mengajukan pinjaman kepada HappyCash.")
        mAdapter.addList("2.Bagaimana cara mengajukan pinjaman?", "Silahkan buka Google Play dan cari app \"HappyCash\", kemudian download app tersebut. Setelah selesai mendownload, buka app tersebut dan anda dapat memulai proses pinjaman. ")
        mAdapter.addList("3.Bagaimana cara mengembalikan pinjaman? Apakah dapat mengembalikan pinjaman lebih awal?", "Kami akan mengirimkan sms pada anda satu hari sebelum tanggal jatuh tempo untuk mengingatkan anda agar mengembalikan pinjaman. Anda dapat mengembalikan pinjaman melalui Alfamart atau melalui e-banking, mobile banking, dan transfer ATM bank besar. Setelah sukses mentransfer, app HappyCash akan secara otomatis mendapatkan sinyal pengembalian pinjaman dan melunasi pinjaman anda. Kami mendukung pengembalian pinjaman lebih awal.")
        mAdapter.addList("4.Dokumen apa saja yang perlu dipersiapkan? Bagaimana proses reviewnya?", "Anda hanya perlu mengikuti petunjuk sistem kami untuk mengisi 4 halaman informasi (Informasi pribadi, informasi pekerjaan, informasi kontak, dan upload foto anda), tidak memerlukan data lain apapun mohon pastikan untuk mengisi informasi asli anda. Sistem akan melakukan analisa mahadata, multi-dimensi kalkulasi skor kredit, dan skor gabungan komprehensif berdasarkan informasi yang telah anda berikan untuk mengkalkulasi hasil review pinjaman.")
        mAdapter.addList("5.Apa itu masa tenggang? Berapa biaya pada masa tenggang?",
                "Masa tenggang adalah hari pertama sampai ke tujuh setelah tanggal jatuh tempo. Masa tenggang tidak mempengaruhi catatan kredit anda, kami tidak akan mengupload informasi anda pada sistem informasi peminjam Bank Indonesia. Tetapi, anda akan dikenakan denda harian 2% dari jumlah pinjaman anda. Jika pinjaman anda Rp. 600.000, maka denda anda berjumlah Rp. 12.000 per hari. ")
        mAdapter.addList("6.Apa itu biaya konsultasi manajemen?",
                "Biaya konsultasi manajemen merupakan jaminan pelunasan yang kami simpan, yaitu 10% dari jumlah pinjaman. Jika cicilan Anda tidak melewati batas jatuh tempo, biaya konsultasi manajemen akan dikembalikan secara penuh setelah pinjaman Anda lunas.")
        mAdapter.addList("7.Jika user tidak dapat mengembalikan pinjaman pada tepat waktu, apa konsekuensinya?",
                "Kami akan mengambil langkah-langkah legal untuk menagih pinjaman dan kami juga akan mengenakan denda dan biaya manajemen extra. Jumlah denda dan biaya manajemen adalah 2% dari jumlah pinjaman anda. Untuk persyaratan detailnya anda dapat membaca perjanjian pinjaman kami. Jika anda melakukan pelanggaran serius, kami dapat mengungkapkan informasi pribadi anda, dan anda tidak akan dapat mengajukan pinjaman lagi pada kami. Kami juga akan mengupload catatan kredit anda pada SLIK (Sistem Layanan Informasi Keuangan).")
        mAdapter.addList("8.Mengapa verifikasi pinjaman saya tidak lolos? Apakah saya boleh mengajukan pinjaman lagi?",
                "Mohon maaf, karena skor komprehensif anda tidak mencukupi, untuk sementara pinjaman anda belum dapat disetujui. Sistem akan melakukan analisa mahadata dan mengaudit secara otomatis. Karena alasan pengendalian risiko, kami tidak dapat memberikan rincian skor kredit anda dan alasan detil pemohonan anda ditolak. Jika informasi anda tidak mengalami perubahan, kami tidak menganjurkan anda untuk mengajukan permohonan lagi dalam waktu dekat. Jika ada keperluan untuk meminjam, silahkan mencoba lagi minimal 14 hari kemudian.")
        mAdapter.addList("9.Apakah informasi saya akan bocor ?",
                "Kami berjanji bahwa kami tidak akan mengungkapkan informasi pribadi anda pada pihak ketiga tanpa persetujuan dari anda (dikecualikan pengembalian terlambat dan kebutuhan layanan)")
        mAdapter.addList("10.Bagaimana cara menghitung tanggal jatuh tempo? Bagaimana cara memeriksa tanggal jatuh tempo pinjaman ?",
                "Tanggal jatuh tempo dijadwalkan satu bulan setelah tanggal pengeluaran pinjaman (berdasarkan jangka waktu pinjaman yang telah anda pilih sebelumnya). Setelah pinjaman berhasil, anda dapat memeriksa tanggal jatuh tempo pinjaman pada halaman utama app.")
        lv_helpcenter!!.adapter = mAdapter
        btnBorrow.setOnClickListener(this)
        btnBorrow.requestFocus()
        btnLoan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnBorrow -> startActivity(Intent(this, BorrowStrategyActivity::class.java))
            R.id.btnLoan -> startActivity(Intent(this, RepaymentStrategyActivity::class.java))
        }
    }
}
