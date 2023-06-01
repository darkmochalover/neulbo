import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mp3.neulbo.R
import com.mp3.neulbo.ShopActivity

class PurchaseActivity : AppCompatActivity() {
    private lateinit var stationery1Image: ImageButton
    private lateinit var stationery2Image: ImageButton
    private lateinit var stationery3Image: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_stationery)

        stationery1Image = findViewById(R.id.stationery1Image)
        stationery2Image = findViewById(R.id.stationery2Image)
        stationery3Image = findViewById(R.id.stationery3Image)

        stationery1Image.setOnClickListener { showConfirmationDialog(this, "stationery1") }
        stationery2Image.setOnClickListener { showConfirmationDialog(this, "stationery2") }
        stationery3Image.setOnClickListener { showConfirmationDialog(this, "stationery3") }
    }

    private fun showConfirmationDialog(context: Context, stationery: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("정말 구매하시겠습니까?")
            .setPositiveButton("예") { dialog: DialogInterface, _: Int ->
                // "예" 버튼을 클릭했을 때의 동작
                when (stationery) {
                    "stationery1" -> {
                        // stationery1을 구매하는 동작
                        val intent = Intent(context, ShopActivity::class.java)
                        context.startActivity(intent)
                    }
                    "stationery2" -> {
                        // stationery2를 구매하는 동작
                        val intent = Intent(context, ShopActivity::class.java)
                        context.startActivity(intent)
                    }
                    "stationery3" -> {
                        // stationery3을 구매하는 동작
                        val intent = Intent(context, ShopActivity::class.java)
                        context.startActivity(intent)
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("아니오") { dialog: DialogInterface, _: Int ->
                // "아니오" 버튼을 클릭했을 때의 동작
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
