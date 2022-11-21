package xyz.arifz.materialedittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import xyz.arifz.materialedittext.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.met.text = "This is a edit text"
        binding.met.apply {
            setTextColor("#FF0000")
            setTextFontFamily(R.font.poppins)
            setHintFontFamily(R.font.poppins)
            setBoxWidth(1)
        }

        binding.btn.setOnClickListener {
            val txt = binding.met.text?.toString()
            if (txt.isNullOrEmpty())
                binding.met.error = "Empty fields"
            else
                Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
        }
    }
    
}